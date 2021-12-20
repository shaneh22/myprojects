#include <memory>
#include "GPixel.h"
#include "GBitmap.h"
#include "GMatrix.h"
#include "GShader.h"

class MyLinearGradientShader : public GShader {
    private: 
    GMatrix localMatrix;
    GMatrix fInv;
    GPoint p0;
    GPoint p1;
    GColor* colors;
    int colorCount;
    GColor* colorDiffs;
    bool opaque;
    const TileMode mode;

    public: 

    MyLinearGradientShader(GPoint iP0, GPoint iP1, const GColor iColors[], int count, const GShader::TileMode& tileMode):
    p0(iP0), p1(iP1), colorCount(count), mode(tileMode){
        opaque = true;
        colors = new GColor[colorCount + 1];
        colors[colorCount] = {1, 1, 1, 1};
        colorDiffs = new GColor [colorCount];
        for(int i =  0; i < colorCount; i++){
            colors[i] = iColors[i].pinToUnit();
            if(colors[i].a != 1){
                opaque = false;
            }
        }
        for(int i = 0; i < colorCount; i++){
            colorDiffs[i] = colors[i+1] - colors[i];
        }
        float dx = p1.fX - p0.fX;
        float dy = p1.fY - p0.fY;
        localMatrix = GMatrix(dx, -dy, p0.fX, dy, dx, p0.fY);
    }

    bool isOpaque() override {
        return opaque;
    }

    bool setContext(const GMatrix& ctm) override{
        return (ctm * localMatrix).invert(&fInv);
    }

    void shadeRow(int x, int y, int count, GPixel row[]) override{
        if(colorCount == 1){
            for(int i = 0; i < count; i++){
                row[i] = colorToPixel(colors[0]);
            }
        }
        float newX = fInv[0] * (x + .5f) + fInv[1] * (y + .5f) + fInv[2];
        if(colorCount == 2){
            switch(mode){
                case TileMode::kClamp:
                    for(int i = 0; i < count; i++){
                        float fX = GPinToUnit(newX);
                        GColor color = colors[0] + fX * colorDiffs[0];
                        row[i] = colorToPixel(color);
                        newX += fInv[0];
                    }
                    break;
                case TileMode::kRepeat:
                    for(int i = 0; i < count; i++){
                        float fX = newX - GFloorToInt(newX);
                        assert(fX >= 0);
                        GColor color = colors[0] + fX * colorDiffs[0];
                        row[i] = colorToPixel(color);
                        newX += fInv[0];
                    }
                    break;
                case TileMode::kMirror:
                    for(int i = 0; i < count; i++){
                        float fX = calculateMirrorT(newX);
                        assert(fX >= 0);
                        GColor color = colors[0] + fX * colorDiffs[0];
                        row[i] = colorToPixel(color);
                        newX += fInv[0];
                    }
                    break;
            }
        }
        else {
            switch(mode){
                case TileMode::kClamp:
                    for(int i = 0; i < count; i++){
                        float fX = GPinToUnit(newX) * (colorCount - 1);
                        int index = GFloorToInt(fX);
                        float w = fX - index;
                        GColor color = colors[index] + w * colorDiffs[index];
                        row[i] = colorToPixel(color);
                        newX += fInv[0];
                    }
                    break;
                case TileMode::kRepeat:
                    for(int i = 0; i < count; i++){
                        float fX = (newX - GFloorToInt(newX)) * (colorCount - 1);
                        assert(fX >= 0);
                        int index = GFloorToInt(fX);
                        float w = fX - index;
                        GColor color = colors[index] + w * colorDiffs[index];
                        row[i] = colorToPixel(color);
                        newX += fInv[0];
                    }
                    break;
                case TileMode::kMirror:
                    for(int i = 0; i < count; i++){
                        float fX = (calculateMirrorT(newX)) * (colorCount - 1);
                        assert(fX >= 0);
                        int index = GFloorToInt(fX);
                        float w = fX - index;
                        GColor color = colors[index] + w * colorDiffs[index];
                        row[i] = colorToPixel(color);
                        newX += fInv[0];
                    }
                    break;
            }
            
        }
    }

    float calculateMirrorT(float x){
        int iX = GFloorToInt(x);
        float temp = x - iX;
        if(iX % 2 != 0){
            return 1 - temp;
        }
        return temp;
    }
    
    GPixel colorToPixel(const GColor& color){
        GColor pinnedUnit = color.pinToUnit();
        GColor premultColor = {pinnedUnit.r * pinnedUnit.a, pinnedUnit.g * pinnedUnit.a, pinnedUnit.b * pinnedUnit.a, pinnedUnit.a};
        return GPixel_PackARGB(GRoundToInt(premultColor.a * 255), GRoundToInt(premultColor.r * 255), GRoundToInt(premultColor.g * 255), GRoundToInt(premultColor.b * 255));
    }
    
};

std::unique_ptr<GShader> GCreateLinearGradient(GPoint p0, GPoint p1, const GColor colors[], int count, GShader::TileMode mode) {
    if(count < 1){
        return nullptr; 
    }
    return std::unique_ptr<GShader>(new MyLinearGradientShader(p0, p1, colors, count, mode));
}