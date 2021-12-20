#include <memory>
#include "GPixel.h"
#include "GBitmap.h"
#include "GMatrix.h"
#include "GShader.h"

class MyShader : public GShader {
    public:
    MyShader(const GBitmap& device, const GMatrix& lm, const GShader::TileMode& tileMode): fDevice(device), localMatrix(lm), mode(tileMode){}

    // Return true iff all of the GPixels that may be returned by this shader will be opaque.
    bool isOpaque() override{
        return fDevice.isOpaque();
    }

    // The draw calls in GCanvas must call this with the CTM before any calls to shadeSpan().
    bool setContext(const GMatrix& ctm) override{
        GMatrix newInv = ctm * localMatrix;
        return newInv.invert(&fInv);
    }

    /**
     *  Given a row of pixels in device space [x, y] ... [x + count - 1, y], return the
     *  corresponding src pixels in row[0...count - 1]. The caller must ensure that row[]
     *  can hold at least [count] entries.
     */
    void shadeRow(int x, int y, int count, GPixel row[]) override{
        float fX = x + .5;
        float fY = y + .5;
        float newX = fInv[0] * fX + fInv[1] * fY + fInv[2]; 
        float newY = fInv[3] * fX + fInv[4] * fY + fInv[5];
        switch(mode){
            case TileMode::kClamp:
                for(int i = 0; i < count; i++){
                    int iX = GFloorToInt(std::min(std::max(0.f, newX), (float) fDevice.width() - 1));
                    int iY = GFloorToInt(std::min(std::max(0.f, newY), (float) fDevice.height() - 1));
                    row[i] = *fDevice.getAddr(iX, iY);
                    newX += fInv[0];
                    newY += fInv[3];
                }
                break;
            case TileMode::kRepeat:
                for(int i = 0; i < count; i++){
                    int iX = fmod(newX, fDevice.width());
                    if(iX < 0){
                        iX += fDevice.width() - 1;
                    }
                    int iY = fmod(newY, fDevice.height());
                    if(iY < 0){
                        iY += fDevice.height() - 1;
                    }
                    row[i] = *fDevice.getAddr(iX, iY);
                    newX += fInv[0];
                    newY += fInv[3];
                }
                break;
            case TileMode::kMirror:
                for(int i = 0; i < count; i++){
                    int floorX = GFloorToInt(newX);
                    int iX = fmod(newX, fDevice.width());
                    if(iX < 0){
                        iX += fDevice.width() - 1;
                    }
                    if(floorX % (2 * fDevice.width()) > fDevice.width()){
                        iX = fDevice.width() - 1 - iX;
                    }
                    int floorY = GFloorToInt(newY);
                    int iY = fmod(newY, fDevice.height());
                    if(iY < 0){
                        iY += fDevice.height() - 1;
                    }
                    if(floorY % (2 * fDevice.height()) > fDevice.height()){
                        iY = fDevice.height() - 1 - iY;
                    }
                    row[i] = *fDevice.getAddr(iX, iY);
                    newX += fInv[0];
                    newY += fInv[3];
                }
                break;
        }
    }
    
    private:
    const GBitmap fDevice;
    const GMatrix localMatrix;
    GMatrix fInv;
    const TileMode mode;
};

/**
 *  Return a subclass of GShader that draws the specified bitmap and the local matrix.
 *  Returns null if the either parameter is invalid.
 */
std::unique_ptr<GShader> GCreateBitmapShader(const GBitmap& bm, const GMatrix& localMatrix, GShader::TileMode mode){
    if(bm.pixels() == NULL){
        return NULL;
    }
    return std::unique_ptr<GShader>(new MyShader(bm, localMatrix, mode));
};