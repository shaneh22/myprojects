#include "GCanvas.h"
#include "GBitmap.h"
#include "GColor.h"
#include "GRect.h"
#include "GPaint.h"
#include "GBlendMode.h"
#include "GPoint.h"
#include "Edge.h"
#include "GMatrix.h"
#include "GShader.h"
#include "GPath.h"
#include "tricolor_shader.cpp"
#include "texture_shader.cpp"
#include "compose_shader.cpp"

#include "iostream"
#include <vector>

//Returns true if edge1 top is higher than edge2, using x as tiebreaker
bool compareEdges(const Edge &edge1, const Edge &edge2){
    if(edge1.getTop() != edge2.getTop()){
        return edge1.getTop() < edge2.getTop();
    }
    else if(edge1.getCurrX() != edge2.getCurrX()){
        return edge1.getCurrX() < edge2.getCurrX();
    }
    return edge1.getSlope() < edge2.getSlope();
}

bool compareEdgesCurrX(const Edge &edge1, const Edge &edge2){
    if(edge1.getCurrX() != edge2.getCurrX()){
        return edge1.getCurrX() < edge2.getCurrX();
    }
    return edge1.getSlope() < edge2.getSlope();
}

class MyCanvas : public GCanvas {
    public :
    MyCanvas(const GBitmap& device) : fDevice(device){
        ctms.push_back(GMatrix());
    }

    GPixel clearPixel = GPixel_PackARGB(0, 0, 0, 0);
    Edge sentinel = Edge(0, INT_MAX, 0, INT_MAX, 0);

    void drawPaint(const GPaint& paint) override{
        GBlendMode mode = paint.getBlendMode();
        if(mode == GBlendMode::kDst){return;}
        GShader* sh = paint.getShader();
        GPixel source;
        if(sh == nullptr){
            source = colorToPixel(paint.getColor());
            mode = simplifyBlendMode(source, mode);
            if(mode == GBlendMode::kDst){
                return;
            }
        }
        else{
            if((*sh).setContext(ctms.back()) == false){
                return;
            }
        }
        int cnt = fDevice.width();
        for(int height = 0; height < fDevice.height(); height++){
            if(sh == nullptr){
                blit(height, 0, cnt, mode, source);
            }
            else{
                GPixel shadedPixels [cnt];
                (*sh).shadeRow(0, height, cnt, shadedPixels);
                shadePixels(height, 0, cnt, mode, shadedPixels, (*sh).isOpaque());
            }
        }
    }

    GPixel colorToPixel(const GColor& color){
        GColor pinnedUnit = color.pinToUnit();
        GColor premultColor = {pinnedUnit.r * pinnedUnit.a, pinnedUnit.g * pinnedUnit.a, pinnedUnit.b * pinnedUnit.a, pinnedUnit.a};
        return GPixel_PackARGB(multiplyColor(premultColor.a), multiplyColor(premultColor.r), multiplyColor(premultColor.g), multiplyColor(premultColor.b));
    }

    float multiplyColor(float x){
        return GRoundToInt(x * 255);
    }

    void drawRect(const GRect& rect, const GPaint& paint) override{
        GPoint quad[4];
        quad[0] = {rect.left(), rect.top()};
        quad[1] = {rect.right(), rect.top()};
        quad[2] = {rect.right(), rect.bottom()};
        quad[3] = {rect.left(), rect.bottom()};
        this -> drawConvexPolygon(quad, 4, paint);
    }

    GBlendMode simplifyBlendMode(const GPixel& source, GBlendMode& blendMode){
        if(GPixel_GetA(source) == 255){
            return opaqueBlendMode(blendMode);
        }
        else if (GPixel_GetA(source) == 0){
            switch(blendMode){
                case GBlendMode::kSrcOver:
                    return GBlendMode::kDst;
                case GBlendMode::kDstOver:
                    return GBlendMode::kDst;
                case GBlendMode::kSrcIn:
                    return GBlendMode::kClear;
                case GBlendMode::kDstIn:
                    return GBlendMode::kClear;
                case GBlendMode::kSrcOut:
                    return GBlendMode::kClear;
                case GBlendMode::kDstOut:
                    return GBlendMode::kDst;
                case GBlendMode::kSrcATop:
                    return GBlendMode::kDst;
                case GBlendMode::kDstATop:
                    return GBlendMode::kSrcOut;
                case GBlendMode::kXor:
                    return GBlendMode::kDst;
                default:
                    return blendMode;
            }
        }
        return blendMode;
    }

    GBlendMode opaqueBlendMode(GBlendMode& blendMode){
        switch(blendMode){
            case GBlendMode::kSrcOver:
                return GBlendMode::kSrc;
            case GBlendMode::kDstIn:
                return GBlendMode::kDst;
            case GBlendMode::kDstOut:
                return GBlendMode::kClear;
            case GBlendMode::kSrcATop:
                return GBlendMode::kSrcIn;
            case GBlendMode::kXor:
                return GBlendMode::kSrcOut;
            default:
                return blendMode;
        }
    }

    GPixel blendPixel(const GPixel& source, const GPixel& dest, GBlendMode& blendMode){
        switch(blendMode){
            case GBlendMode::kClear: 
                return clearPixel;
            case GBlendMode::kSrc:
                return source;
            case GBlendMode::kSrcOver:
                if(GPixel_GetA(dest) == 0 || GPixel_GetA(source) == 255){
                    return source;
                }
                else if(GPixel_GetA(source) == 0){
                    return dest;
                }
                return blendPixelSrcOver(source, dest);
            case GBlendMode::kDstOver:
                if(GPixel_GetA(source) == 0 || GPixel_GetA(dest) == 255){
                    return dest;
                }
                else if(GPixel_GetA(dest) == 0){
                    return source;
                }
                return blendPixelSrcOver(dest, source);
            case GBlendMode::kSrcIn:
                if(GPixel_GetA(dest) == 0 || GPixel_GetA(source) == 0) return clearPixel;
                else if(GPixel_GetA(dest) == 255) return source;
                return blendPixelSrcIn(source, dest);
            case GBlendMode::kDstIn:
                if(GPixel_GetA(source) == 0 || GPixel_GetA(dest) == 0) return clearPixel;
                else if(GPixel_GetA(source) == 255) return dest;
                return blendPixelSrcIn(dest, source);
            case GBlendMode::kSrcOut:
                if(GPixel_GetA(dest) == 255 || GPixel_GetA(source) == 0) return clearPixel;
                else if(GPixel_GetA(dest) == 0) return source;
                return blendPixelSrcOut(source, dest);
            case GBlendMode::kDstOut:
                if(GPixel_GetA(source) == 255 || GPixel_GetA(dest) == 0) return clearPixel;
                else if(GPixel_GetA(source) == 0) return dest;
                return blendPixelSrcOut(dest, source);
            case GBlendMode::kSrcATop:
                if(GPixel_GetA(source) == 0){
                    return dest;
                }
                else if(GPixel_GetA(dest) == 0){
                    return clearPixel;
                }
                else if(GPixel_GetA(dest) == 255){
                    if(GPixel_GetA(source) == 255){
                        return source;
                    }
                    return source + blendPixelSrcOut(dest, source);
                }
                else if(GPixel_GetA(source) == 255){
                    return blendPixelSrcIn(source, dest);
                }
                return blendPixelSrcATop(source, dest);
            case GBlendMode::kDstATop:
                if(GPixel_GetA(dest) == 0){
                    return source;
                }
                else if(GPixel_GetA(source) == 0){
                    return clearPixel;
                }
                else if(GPixel_GetA(source) == 255){
                    if(GPixel_GetA(dest) == 255){
                        return dest;
                    }
                    return dest + blendPixelSrcOut(source, dest);
                }
                else if(GPixel_GetA(dest) == 255){
                    return blendPixelSrcIn(dest, source);
                }
                return blendPixelSrcATop(dest, source);
            case GBlendMode::kXor:
                if(GPixel_GetA(dest) == 0){
                    if(GPixel_GetA(source) == 0){
                        return clearPixel;
                    }
                    return source;
                }
                else if(GPixel_GetA(source) == 0){
                    return dest;
                }
                else if(GPixel_GetA(dest) == 255){
                    if(GPixel_GetA(source) == 255){
                        return clearPixel;
                    }
                    return blendPixelSrcOut(dest, source);
                }
                else if(GPixel_GetA(source) == 255){
                    return blendPixelSrcOut(source, dest);
                }
                return blendPixelXor(source, dest);
            default:
                return dest;
        }
    }

    GPixel blendPixelXor(const GPixel& source, const GPixel& dest){
        int destA = GPixel_GetA(dest);
        int sourceA = GPixel_GetA(source);
        int blendedA = (255 - sourceA) * destA + (255 - destA) * sourceA;
        int blendedR = (255 - sourceA) * GPixel_GetR(dest) + (255 - destA) * GPixel_GetR(source);
        int blendedG = (255 - sourceA) * GPixel_GetG(dest) + (255 - destA) * GPixel_GetG(source);
        int blendedB = (255 - sourceA) * GPixel_GetB(dest) + (255 - destA) * GPixel_GetB(source);
        return GPixel_PackARGB(divide255(blendedA), divide255(blendedR), divide255(blendedG), divide255(blendedB));
    }

    GPixel blendPixelSrcATop(const GPixel& source, const GPixel& dest){
        int destA = GPixel_GetA(dest);
        int sourceA = GPixel_GetA(source);
        int blendedA = destA * sourceA + (255 - sourceA) * destA;
        int blendedR = destA * GPixel_GetR(source) + (255 - sourceA) * GPixel_GetR(dest);
        int blendedG = destA * GPixel_GetG(source) + (255 - sourceA) * GPixel_GetG(dest);
        int blendedB = destA * GPixel_GetB(source) + (255 - sourceA) * GPixel_GetB(dest);
        return GPixel_PackARGB(divide255(blendedA), divide255(blendedR), divide255(blendedG), divide255(blendedB));
    }

    GPixel blendPixelSrcIn(const GPixel& source, const GPixel& dest){
        int destA = GPixel_GetA(dest);
        int blendedA = divide255(destA * GPixel_GetA(source));
        int blendedR = divide255(destA * GPixel_GetR(source));
        int blendedG = divide255(destA * GPixel_GetG(source));
        int blendedB = divide255(destA * GPixel_GetB(source));
        return GPixel_PackARGB(blendedA, blendedR, blendedG, blendedB);
    }

    GPixel blendPixelSrcOut(const GPixel& source, const GPixel& dest){
        int destA = 255 - GPixel_GetA(dest);
        int blendedA = divide255(destA * GPixel_GetA(source));
        int blendedR = divide255(destA * GPixel_GetR(source));
        int blendedG = divide255(destA * GPixel_GetG(source));
        int blendedB = divide255(destA * GPixel_GetB(source));
        return GPixel_PackARGB(blendedA, blendedR, blendedG, blendedB);
    }

    GPixel blendPixelSrcOver(const GPixel& source, const GPixel& dest){
        int diff = 255 - GPixel_GetA(source);
        int blendedA = GPixel_GetA(source) + divide255(diff * GPixel_GetA(dest));
        int blendedR = GPixel_GetR(source) + divide255(diff * GPixel_GetR(dest));
        int blendedG = GPixel_GetG(source) + divide255(diff * GPixel_GetG(dest));
        int blendedB = GPixel_GetB(source) + divide255(diff * GPixel_GetB(dest));
        return GPixel_PackARGB(blendedA, blendedR, blendedG, blendedB);
    }

    unsigned divide255(unsigned value){
        return (value + 128) * 257 >> 16;
    }

    void drawConvexPolygon(const GPoint points [], int count, const GPaint& paint) override{
        GBlendMode mode = paint.getBlendMode();
        if(mode == GBlendMode::kDst){
            return;
        }
        GShader* sh = paint.getShader();
        GPixel source;
        if(sh == nullptr){
            source = colorToPixel(paint.getColor());
            mode = simplifyBlendMode(source, mode);
            if(mode == GBlendMode::kDst){
                return;
            }
        }
        else{
            if((*sh).setContext(ctms.back()) == false){
                return;
            }
        }

        GPoint mappedPoints [count];
        ctms.back().mapPoints(mappedPoints, points, count);

        std::vector<Edge> edges;
        for(int i = 0; i < count - 1; i++){
            clipPoints(mappedPoints[i], mappedPoints[i+1], edges);
        }
        clipPoints(mappedPoints[count - 1], mappedPoints[0], edges);
        std::sort(edges.begin(), edges.end(), compareEdges);
        if(edges.size() <= 0){
            return;
        }
        int minY = edges[0].getTop();
        int maxY = edges.back().getBottom();
        for(int y = minY; y < maxY; y++){
            int left = GRoundToInt(edges[0].getCurrX());
            int right = GRoundToInt(edges[1].getCurrX());
            if (left > right){
                std::swap(left, right);
            }
            if(sh == nullptr){
                blit(y, left, right, mode, source);
            }
            else {
                int cnt = right - left;
                assert(cnt >= 0);
                GPixel shadedPixels [cnt];
                (*sh).shadeRow(left, y, cnt, shadedPixels);
                shadePixels(y, left, cnt, mode, shadedPixels, (*sh).isOpaque());
            }
            
            if(edges[1].getBottom() == y + 1){
                edges.erase(edges.begin() + 1);
            }
            else {
                edges[1].addToCurrX(edges[1].getSlope());
            }
            if(edges[0].getBottom() == y + 1){
                edges.erase(edges.begin());
            }
            else{
                edges[0].addToCurrX(edges[0].getSlope());
            }
        }
    }

    //clipping is only adding max one edge, because they have same y values often
    void clipPoints(GPoint p1, GPoint p2, std::vector<Edge>& edges){
        //Focus on vertical first then horizontal clipping 
        float slope = calculateSlope(p1, p2);
        if(p1.y() < 0){
            if(p2.y() < 0){ return; }
            p1.fX = clippedX(slope, p1, 0);
            p1.fY = 0;
        }
        else if(p2.y() < 0){
            p2.fX = clippedX(slope, p2, 0);
            p2.fY = 0;
        }
        if(p1.y() > fDevice.height()){
            if(p2.y() > fDevice.height()){ return; }
            p1.fX = clippedX(slope, p2, fDevice.height());
            p1.fY = fDevice.height(); 
        }
        else if(p2.y() > fDevice.height()){
            p2.fX = clippedX(slope, p1, fDevice.height());
            p2.fY = fDevice.height();
        }
        float dySlope = (p2.y() - p1.y()) / (p2.x() - p1.x());
        //Horizontal clipping
        if(p1.x() < 0){
            if(p2.x() < 0){
                p1.fX = 0;
                p2.fX = 0;
                makeEdge(edges, p1, p2, 0);
                return;
            }
            GPoint projPoint = GPoint::Make(0, p1.y());
            p1.fY = clippedY(dySlope, p1, 0);
            p1.fX = 0;
            makeEdge(edges, p1, projPoint, 0);
        }
        else if(p2.x() < 0){
            GPoint projPoint = GPoint::Make(0, p2.y());
            p2.fY = clippedY(dySlope, p2, 0);
            p2.fX = 0;
            makeEdge(edges, p2, projPoint, 0);
        }

        if(p1.x() > fDevice.width()){
            if(p2.x() > fDevice.width()){
                p1.fX = fDevice.width();
                p2.fX = fDevice.width();
                makeEdge(edges, p1, p2, 0);
                return; 
            }
            GPoint projPoint = GPoint::Make(fDevice.width(),p1.y());
            p1.fY = clippedY(dySlope, p2, fDevice.width());
            p1.fX = fDevice.width();
            makeEdge(edges, p1, projPoint, 0);
        }
        else if(p2.x() > fDevice.width()){
            GPoint projPoint = GPoint::Make(fDevice.width(), p2.y());
            p2.fY = clippedY(dySlope, p1, fDevice.width());
            p2.fX = fDevice.width();
            makeEdge(edges, p2, projPoint, 0);
        }

        makeEdge(edges, p1, p2, slope);
    }

    float clippedX(float slope, GPoint point, float clipHeight){
        return point.x() + slope * (clipHeight - point.y());
    }
    
    float clippedY(float slope, GPoint point, float clipWidth){
        return point.y() + slope * (clipWidth - point.x());
    }

    void blit(int y, int left, int right, GBlendMode mode, GPixel source){
        for(int x = left; x < right; x++){
            *fDevice.getAddr(x, y) = blendPixel(source,  *fDevice.getAddr(x, y), mode);
        }
    }

    void shadePixels(int y, int x, int count, GBlendMode mode, GPixel shadedPixels [], bool isOpaque){
        if(isOpaque){
            mode = opaqueBlendMode(mode);
            if(mode == GBlendMode::kDst) return;
        }
        for(int i = 0; i < count; i++){
            *fDevice.getAddr(x + i, y) = blendPixel(shadedPixels[i], *fDevice.getAddr(x + i, y), mode);
        }
    }

    //Use & to change the original variable
    void makeEdge(std::vector<Edge>& edges, const GPoint p1, const GPoint p2, float slope){
        int top = GRoundToInt(p1.y());
        int bottom = GRoundToInt(p2.y());
        int wind;
        if(top == bottom){ return; }
        if(top > bottom){
            std::swap(top, bottom);
            wind = -1;
        }
        else {
            wind = 1;
        }
        float currX = p1.x() + slope * (top - p1.y() + .5);
        Edge edge(slope, top, bottom, currX, wind);
        edges.push_back(edge);
    }
    //actually calculates dx/dy not actually slope
    float calculateSlope(GPoint p1, GPoint p2){
        return (p2.x() - p1.x()) / (p2.y() - p1.y());
    }

    /**
     *  Fill the path with the paint, interpreting the path using winding-fill (non-zero winding).
     */
    void drawPath(const GPath& path, const GPaint& paint) override{
        GBlendMode mode = paint.getBlendMode();
        if(mode == GBlendMode::kDst){
            return;
        }
        GShader* sh = paint.getShader();
        GPixel source;
        if(sh == nullptr){
            source = colorToPixel(paint.getColor());
            mode = simplifyBlendMode(source, mode);
            if(mode == GBlendMode::kDst){
                return;
            }
        }
        else{
            if((*sh).setContext(ctms.back()) == false){
                return;
            }
        }
        GPoint pts[GPath::kMaxNextPoints];
        GPath::Edger edger(path);
        GPath::Verb v;
        std::vector<Edge> edges;
        while((v = edger.next(pts)) != GPath::kDone){
            switch(v){
                case GPath::kLine:{
                    ctms.back().mapPoints(pts, 2);
                    clipPoints(pts[0], pts[1], edges);
                    break;
                }
                case GPath::kQuad:{
                    ctms.back().mapPoints(pts, 3);
                    GVector err;
                    err.fX = (-pts[0].fX + 2 * pts[1].fX - pts[2].fX) / 4;
                    err.fY = (- pts[0].fY + 2 * pts[1].fY - pts[2].fY) / 4;
                    int k = GCeilToInt(sqrt(err.length() * 4)); //tolerance is 1/4, |err| / tol
                    float invK = 1.0f / k;
                    float t = 0;
                    GPoint p0 = pts[0];
                    for(int i = 0; i < k; i++){
                        t += invK;
                        GPoint p1 = calcQuadPoint(pts[0], pts[1], pts[2], t);
                        clipPoints(p0, p1, edges);
                        p0 = p1;
                    }
                    clipPoints(p0, pts[2], edges);
                    break;
                }
                case GPath::kCubic:{
                    ctms.back().mapPoints(pts, 4);
                    GVector err, p, q;
                    p.fX = (-pts[0].fX + 2 * pts[1].fX - pts[2].fX) * .25;
                    p.fY = (- pts[0].fY + 2 * pts[1].fY - pts[2].fY) * .25;
                    q.fX = (-pts[1].fX + 2 * pts[2].fX - pts[3].fX) * .25;
                    q.fY = (- pts[1].fY + 2 * pts[2].fY - pts[3].fY) * .25;
                    err.fX = std::max(abs(p.fX), abs(q.fX));
                    err.fY = std::max(abs(p.fY), abs(q.fY));
                    int k = GCeilToInt(sqrt(err.length() * 3)); //tolerance is 1/4, |err| / tol
                    float invK = 1.0f / k;
                    float t = 0;
                    GPoint p0 = pts[0];
                    for(int i = 0; i < k; i++){
                        t += invK;
                        GPoint p1 = calcCubicPoint(pts[0], pts[1], pts[2], pts[3], t);
                        clipPoints(p0, p1, edges);
                        p0 = p1;
                    }
                    clipPoints(p0, pts[3], edges);
                    break;
                }
                default:{
                    break;
                }
            }
        }
        std::sort(edges.begin(), edges.end(), compareEdges);
        int count = edges.size();
        edges.push_back(sentinel);
        //loop through all y's until no more edges
        for (int y = edges[0].getTop(); count > 0;){
            int index = 0;
            int w = 0; //winding value
            //loop through the active edges
            int left, right;
            while (edges[index].getTop() <= y){
                assert(edges[index].getBottom() > y);
                // work with edge[index];

	            // check w (for left) → did we go from 0 to non-0
                // update w → w += edge[index].winding
                // check w (for right and blit) → did we go from non-0 to 0

                // if the edge is done, remove from array (and --count)
                // else update currX by slope (and ++index)
                if(w == 0){
                    left = GRoundToInt(edges[index].getCurrX());
                }
                w += edges[index].getWind();
                if(w == 0){
                    right = GRoundToInt(edges[index].getCurrX());
                    if(left > right){
                        std::swap(left, right);
                    }
                    if(sh == nullptr){
                        blit(y, left, right, mode, source);
                    }
                    else {
                        int cnt = right - left;
                        assert(cnt >= 0);
                        GPixel shadedPixels [cnt];
                        (*sh).shadeRow(left, y, cnt, shadedPixels);
                        shadePixels(y, left, cnt, mode, shadedPixels, (*sh).isOpaque());
                    }
                }

                if(edges[index].getBottom() == y + 1){
                    edges.erase(edges.begin() + index);
                    count--;
                }
                else {
                    edges[index].addToCurrX(edges[index].getSlope());
                    index++;
                }

            }
            //assert(w == 0);

            y++;
            //move index to include only edges that WILL be valid
            while(index < count && y == edges[index].getTop()){
                index += 1; 
            }

            //sort edges from [0...index) based only on currX
            if(index != 0) std::sort(edges.begin(), edges.begin() + index, compareEdgesCurrX);
        }

    }

    GPoint calcQuadPoint(GPoint p0, GPoint p1, GPoint p2, float t){
        float diff = 1 - t;
        return diff * diff * p0 + 2 * t * diff * p1 + t * t * p2;
    }

    GPoint calcCubicPoint(GPoint p0, GPoint p1, GPoint p2, GPoint p3, float t){
        float diff = 1 - t;
        float diffSquared = diff * diff;
        float tSquared = t * t;
        return diffSquared * diff * p0 + 3 * (diffSquared * t * p1 + tSquared * diff * p2) + tSquared * t * p3;
    }

    /**
     *  Draw a mesh of triangles, with optional colors and/or texture-coordinates at each vertex.
     *
     *  The triangles are specified by successive triples of indices.
     *      int n = 0;
     *      for (i = 0; i < count; ++i) {
     *          point0 = vertx[indices[n+0]]
     *          point1 = verts[indices[n+1]]
     *          point2 = verts[indices[n+2]]
     *          ...
     *          n += 3
     *      }
     *
     *  If colors is not null, then each vertex has an associated color, to be interpolated
     *  across the triangle. The colors are referenced in the same way as the verts.
     *          color0 = colors[indices[n+0]]
     *          color1 = colors[indices[n+1]]
     *          color2 = colors[indices[n+2]]
     *
     *  If texs is not null, then each vertex has an associated texture coordinate, to be used
     *  to specify a coordinate in the paint's shader's space. If there is no shader on the
     *  paint, then texs[] should be ignored. It is referenced in the same way as verts and colors.
     *          texs0 = texs[indices[n+0]]
     *          texs1 = texs[indices[n+1]]
     *          texs2 = texs[indices[n+2]]
     *
     *  If both colors and texs[] are specified, then at each pixel their values are multiplied
     *  together, component by component.
     */
    void drawMesh(const GPoint verts[], const GColor colors[], const GPoint texs[],
                              int count, const int indices[], const GPaint& paint) override{
        int n = 0;
        GPoint pts[3];
        GColor cs[3];
        GPoint txs[3];
        GShader* realShader = paint.getShader();
        MyTricolorShader tricolorShader = MyTricolorShader();
        MyTextureShader texShader = MyTextureShader(realShader);
        GPaint newPaint = paint;
        for(int i = 0; i < count; i++){
            pts[0] = verts[indices[n]];
            pts[1] = verts[indices[n+1]];
            pts[2] = verts[indices[n+2]];
            if(colors != nullptr){
                cs[0] = colors[indices[n]];
                cs[1] = colors[indices[n+1]];
                cs[2] = colors[indices[n+2]];
                tricolorShader.init(cs, pts);
                if(realShader != nullptr && texs != nullptr){
                    txs[0] = texs[indices[n]];
                    txs[1] = texs[indices[n+1]];
                    txs[2] = texs[indices[n+2]];
                    texShader.init(txs, pts);
                    GShader* s1 = &texShader;
                    GShader* s2 = &tricolorShader;
                    MyComposeShader composeShader = MyComposeShader(s1, s2);
                    newPaint.setShader(&composeShader);
                    drawConvexPolygon(pts, 3, newPaint);
                }
                else{
                    newPaint.setShader(&tricolorShader);
                    drawConvexPolygon(pts, 3, newPaint);
                }
            }
            else if(realShader != nullptr && texs != nullptr){
                txs[0] = texs[indices[n]];
                txs[1] = texs[indices[n+1]];
                txs[2] = texs[indices[n+2]];
                texShader.init(txs, pts);
                newPaint.setShader(&texShader);
                drawConvexPolygon(pts, 3, newPaint);
            }
            n+=3;
        }
    }
    
    void save() override {
        ctms.push_back(ctms.back());
    }
    void restore() override {
        if(ctms.size() > 1){
            ctms.pop_back();
        }
    }
    void concat(const GMatrix& matrix) override {
        ctms.back() = ctms.back() * matrix;
    }
    
    private: 
    const GBitmap fDevice;
    std::vector<GMatrix> ctms;
};

std::unique_ptr<GCanvas> GCreateCanvas(const GBitmap& device){
    return std::unique_ptr<GCanvas>(new MyCanvas(device));
}

std::string GDrawSomething(GCanvas* canvas, GISize dim){
    GColor red = {1, 0, 0, 1};
    GColor orange = {1, .647, 0, 1};
    GColor yellow = {1, 1, 0, 1};
    GColor green = {0, .5, 0, 1};
    GColor blue = {0, 0, 1, 1};
    GColor indigo = {.294, 0, .51, 1};
    GColor violet = {.9, .5, .9, 1};

    GColor colors [7];
    colors[0] = red;
    colors[1] = orange;
    colors[2] = yellow;
    colors[3] = green;
    colors[4] = blue;
    colors[5] = indigo;
    colors[6] = violet;

    auto sh = GCreateLinearGradient({0, 0}, {dim.width(), dim.height()}, colors, 7, GShader::TileMode::kRepeat);
    GPaint background(sh.get());
    canvas -> drawPaint(background);
    
    canvas->translate(75, 75);
    GColor increment = {.04, .04, .04, 1};
    GColor start = {.1, .1, .1, 1};
    GPaint paint(start);
    for(float i = 1.8; i < 3; i+= .3){
        canvas->rotate(.1745);
        canvas->drawRect(GRect::MakeWH(dim.width() / i, dim.height() / i), paint);
        start = start + increment;
        paint.setColor(start);
    }
    return "Civility";
}