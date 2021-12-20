#include <vector>
#include <algorithm>
#include <iostream>
#include "GMatrix.h"
#include "GPoint.h"
#include "GRect.h"
#include "GPath.h"

/**
 **  Append a new contour, made up of the 4 points of the specified rect, in the specified
 *  direction. The contour will begin at the top-left corner of the rect.
 */
GPath& GPath::addRect(const GRect& r, Direction dir){
    if(dir == kCW_Direction){
        return moveTo({r.left(), r.top()}).lineTo({r.right(), r.top()}).lineTo({r.right(), r.bottom()}).lineTo({r.left(), r.bottom()});
    }
    else{
        return moveTo({r.left(), r.top()}).lineTo({r.left(), r.bottom()}).lineTo({r.right(), r.bottom()}).lineTo({r.right(), r.top()});
    }
}

/**
 *  Append a new contour with the specified polygon. Calling this is equivalent to calling
 *  moveTo(pts[0]), lineTo(pts[1..count-1]).
 */
GPath& GPath::addPolygon(const GPoint pts[], int count){
    GPath& path = moveTo(pts[0]);
    for(int i = 1; i < count; i++){
        path = path.lineTo(pts[i]);
    }
    return path;
}

/**
 *  Append a new contour respecting the Direction. The contour should be an approximate
 *  circle (8 quadratic curves will suffice) with the specified center and radius.
 *
 *  Returns a reference to this path.
 */
GPath& GPath::addCircle(GPoint center, float radius, Direction dir){
    const float root2 = sqrt(2) * .5;
    const float h = tan(M_PI * .125f);
    if(dir == kCW_Direction){
        GPoint pts[16] = {{1,0}, {1, -h}, {root2, -root2}, {h, -1}, {0, -1}, {-h, -1}, {-root2, -root2}, {-1, -h}, {-1, 0}, {-1, h}, {-root2, root2}, {-h, 1}, {0, 1}, {h, 1}, {root2, root2}, {1, h} };
        GMatrix matrix = GMatrix::Concat(GMatrix::Translate(center.fX, center.fY), GMatrix::Scale(radius, radius));
        matrix.mapPoints(pts, 16);
        return moveTo(pts[0]).quadTo(pts[1], pts[2]).quadTo(pts[3], pts[4]).quadTo(pts[5], pts[6]).quadTo(pts[7], pts[8]).quadTo(pts[9], pts[10]).quadTo(pts[11], pts[12]).quadTo(pts[13], pts[14]).quadTo(pts[15], pts[0]);
    }
    else {
        GPoint pts[16] = {{1, 0}, {1, h}, {root2, root2}, {h, 1}, {0, 1}, {-h, 1}, {-root2, root2}, {-1, h}, {-1, 0}, {-1, -h}, {-root2, -root2}, {-h, -1}, {0, -1}, {h, -1}, {root2, -root2}, {1, -h}};
        GMatrix matrix = GMatrix::Concat(GMatrix::Translate(center.fX, center.fY), GMatrix::Scale(radius, radius));
        matrix.mapPoints(pts, 16);
        return moveTo(pts[0]).quadTo(pts[1], pts[2]).quadTo(pts[3], pts[4]).quadTo(pts[5], pts[6]).quadTo(pts[7], pts[8]).quadTo(pts[9], pts[10]).quadTo(pts[11], pts[12]).quadTo(pts[13], pts[14]).quadTo(pts[15], pts[0]);
    }
}

/**
 *  Return the bounds of all of the control-points in the path.
 *
 *  If there are no points, return {0, 0, 0, 0}
 */
GRect GPath::bounds() const{
    if(fPts.size() == 0){
        return GRect::MakeWH(0, 0);
    }
    else{
        GRect rect = GRect::MakeLTRB(fPts[0].x(), fPts[0].y(), fPts[0].x(), fPts[0].y());
        for(int i = 1; i < fPts.size(); i++){
            if(fPts[i].x() < rect.fLeft){
                rect.fLeft = fPts[i].x();
            }
            else if (fPts[i].x() > rect.fRight){
                rect.fRight = fPts[i].x();
            }
            if(fPts[i].y() < rect.fTop){
                rect.fTop = fPts[i].y();
            }
            else if (fPts[i].y() > rect.fBottom){
                rect.fBottom = fPts[i].y();
            }
        }
        return rect;
    }
}

/**
 *  Transform the path in-place by the specified matrix.
 */
void GPath::transform(const GMatrix& matrix){
    GPoint* array = &fPts[0];
    matrix.mapPoints(array, fPts.size());
}

/**
 *  Given 0 < t < 1, subdivide the src[] quadratic bezier at t into two new quadratics in dst[]
 *  such that
 *  0...t is stored in dst[0..2]
 *  t...1 is stored in dst[2..4]
 */
void GPath::ChopQuadAt(const GPoint src[3], GPoint dst[5], float t){
    float diff = 1 - t;
    dst[0] = src[0];
    dst[4] = src[2];
    dst[2] = diff * diff * src[0] + 2 * t * diff * src[1] + t * t * src[2];
    dst[1] = diff * src[0] + t * src[1];
    dst[3] = diff * src[1] + t * src[2];
}

/**
 *  Given 0 < t < 1, subdivide the src[] cubic bezier at t into two new cubics in dst[]
 *  such that
 *  0...t is stored in dst[0..3]
 *  t...1 is stored in dst[3..6]
 */
void GPath::ChopCubicAt(const GPoint src[4], GPoint dst[7], float t){
    float diff = 1 - t;
    float diffSquared = diff * diff;
    float tSquared = t * t;
    dst[0] = src[0];
    dst[6] = src[3];
    dst[3] = diffSquared * diff * src[0] + 3 * (diffSquared * t * src[1] + tSquared * diff * src[2]) + tSquared * t * src[3];
    dst[1] = diff * src[0] + t * src[1];
    dst[2] = diffSquared * src[0] + 2 * diff * t * src[1] + tSquared * src[2];
    dst[4] = diffSquared * src[1] + 2 * diff * t * src[2] + tSquared * src[3];
    dst[5] = diff * src[2] + t * src[3];
}