#include "GCanvas.h"
#include "GPath.h"
#include "GShader.h"
#include "GFinal.h"
#include "GRect.h"

class MyFinal : public GFinal {
    /**
     *  Add contour(s) to the specified path that will draw a line from p0 to p1 with the specified
     *  width and CapType. Note that "width" is the distance from one side of the stroke to the
     *  other, ala its thickness.
     */
    void addLine(GPath* path, GPoint p0, GPoint p1, float width, GFinal::CapType type) override{
        float x = p1.fX - p0.fX;
        float y = p1.fY - p0.fY;
        float len = sqrt(x * x + y * y);
        float rad = width * .5;
        float xDash = x * rad / len;
        float yDash = y * rad / len;
        GPoint abt = {-yDash, xDash};
        GPoint pts[4];
        pts[0] = p0 + abt;
        pts[1] = p0 - abt;
        pts[2] = p1 - abt;
        pts[3] = p1 + abt;
        if(type == kRound){
            path->addCircle(p0, rad, GPath::kCCW_Direction);
            path->addPolygon(pts, 4);
            path->addCircle(p1, rad, GPath::kCCW_Direction);
        }
        else if(type == kSquare){
            path->addRect(GRect::MakeXYWH(p0.fX - rad, p0.fY - rad, width, width));
            path->addPolygon(pts, 4);
            path->addRect(GRect::MakeXYWH(p1.fX - rad, p1.fY - rad, width, width));
        }
        else{
            path->addPolygon(pts, 4);
        }
    }

};

std::unique_ptr<GFinal> GCreateFinal(){
    return std::unique_ptr<GFinal>(new MyFinal());
}