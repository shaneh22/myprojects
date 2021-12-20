#include <memory>
#include "GPixel.h"
#include "GBitmap.h"
#include "GMatrix.h"
#include "GShader.h"

class MyTextureShader : public GShader {
    private:
    GMatrix tMat;
    GMatrix tMatInverse;
    GMatrix pMat;
    GPoint p0;
    GPoint p1;
    GPoint p2;
    GShader* realShader;

    public: 
    MyTextureShader(){}
    MyTextureShader(GShader* shader): realShader(shader){}

    void init(const GPoint texs[3], const GPoint pts[3]){
        p0 = pts[0];
        p1 = pts[1];
        p2 = pts[2];
        tMat = GMatrix(texs[1].fX - texs[0].fX, texs[2].fX - texs[0].fX, texs[0].fX, texs[1].fY - texs[0].fY, texs[2].fY - texs[0].fY, texs[0].fY);
        pMat = GMatrix(p1.fX - p0.fX, p2.fX - p0.fX, 0, p1.fY - p0.fY, p2.fY - p0.fY, 0);
        tMat.invert(&tMatInverse);
    }

    bool isOpaque() override {
        return realShader->isOpaque();
    }

    bool setContext(const GMatrix& ctm) override {
        return realShader->setContext(ctm * (pMat * tMatInverse));
    }

    void shadeRow(int x, int y, int count, GPixel row[]) override {
        realShader -> shadeRow(x, y, count, row);
    }

};