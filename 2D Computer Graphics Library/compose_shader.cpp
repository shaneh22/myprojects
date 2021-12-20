#include <memory>
#include "GPixel.h"
#include "GBitmap.h"
#include "GMatrix.h"
#include "GShader.h"

class MyComposeShader : public GShader{
    private:
    GShader* shader1;
    GShader* shader2;
    public:
    
    MyComposeShader(GShader* s1, GShader* s2): shader1(s1), shader2(s2){}

    bool isOpaque() override{
        return shader1->isOpaque() && shader2->isOpaque();
    }

    bool setContext(const GMatrix& ctm) override {
        return shader1->setContext(ctm) && shader2->setContext(ctm);
    }

    void shadeRow(int x, int y, int count, GPixel row[]) override {
        GPixel temp[count];
        shader1 -> shadeRow(x, y, count, temp);
        shader2 -> shadeRow(x, y, count, row);
        for(int i=0; i < count; i++){
            float a = divide255(GPixel_GetA(temp[i]) * GPixel_GetA(row[i]));
            float r = divide255(GPixel_GetR(temp[i]) * GPixel_GetR(row[i]));
            float g = divide255(GPixel_GetG(temp[i]) * GPixel_GetG(row[i]));
            float b = divide255(GPixel_GetB(temp[i]) * GPixel_GetB(row[i]));
            row[i] = GPixel_PackARGB(a, r, g, b);
        }
    }

    unsigned divide255(unsigned value){
        return (value + 128) * 257 >> 16;
    }

};