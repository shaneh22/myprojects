#include <memory>
#include "GPixel.h"
#include "GBitmap.h"
#include "GMatrix.h"
#include "GShader.h"

class MyTricolorShader : public GShader {
    private:
    GMatrix localMatrix;
    GMatrix fInv;
    GPoint p0;
    GPoint p1;
    GPoint p2;
    GColor colors [3];
    GColor dc1;
    GColor dc2;
    bool opaque;
    
    public:
    MyTricolorShader(){}

    void init(const GColor iColors[3], const GPoint pts[3]){
        p0 = pts[0];
        p1 = pts[1];
        p2 = pts[2];
        opaque = true;
        for(int i = 0; i < 3; i++){
            colors[i] = iColors[i].pinToUnit();
            if(colors[i].a != 1){
                opaque = false;
            }
        }
        localMatrix = GMatrix(p1.fX - p0.fX, p2.fX - p0.fX, 0, p1.fY - p0.fY, p2.fY - p0.fY, 0);
        dc1 = colors[1] - colors[0];
        dc2 = colors[2] - colors[0];
    }

    bool isOpaque() override {
        return opaque;
    }

    bool setContext(const GMatrix& ctm) override{
        return (ctm * localMatrix).invert(&fInv);
    }

    void shadeRow(int x, int y, int count, GPixel row[]) override{
        float fX = x + .5;
        float fY = y + .5;
        float newX = fInv[0] * fX + fInv[1] * fY + fInv[2]; 
        float newY = fInv[3] * fX + fInv[4] * fY + fInv[5];
        GColor c = newX * dc1 + newY * dc2 + colors[0];
        GColor dc = fInv[0] * dc1 + fInv[3] * dc2;
        for(int i = 0; i < count; i++){
            row[i] = colorToPixel(c);
            c += dc;
        }

    }

    GPixel colorToPixel(const GColor& color){
        GColor pinnedUnit = color.pinToUnit();
        GColor premultColor = {pinnedUnit.r * pinnedUnit.a, pinnedUnit.g * pinnedUnit.a, pinnedUnit.b * pinnedUnit.a, pinnedUnit.a};
        return GPixel_PackARGB(GRoundToInt(premultColor.a * 255), GRoundToInt(premultColor.r * 255), GRoundToInt(premultColor.g * 255), GRoundToInt(premultColor.b * 255));
    }

};