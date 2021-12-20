#include "GMatrix.h"
#include "GColor.h"
#include "GMath.h"
#include "GPoint.h"
#include "GRect.h"
#include <math.h>
//using namespace GMatrix;

GMatrix::GMatrix() : GMatrix(1, 0, 0, 0, 1, 0){
};

GMatrix GMatrix::Translate(float tx, float ty){
    GMatrix m;
    m[TX] = tx;
    m[TY] = ty;
    return m;
};
GMatrix GMatrix::Scale(float sx, float sy){
    GMatrix m;
    m[SX] = sx;
    m[SY] = sy;
    return m;
};
GMatrix GMatrix::Rotate(float radians){
    GMatrix m;
    float cosValue = cos(radians);
    float sinValue = sin(radians);
    m[SX] = cosValue;
    m[SY] = cosValue;
    m[KX] = -sinValue;
    m[KY] = sinValue;
    return m;
};

/**
 * STATIC
*  Return the product of two matrices: a * b
*/
GMatrix GMatrix::Concat(const GMatrix& a, const GMatrix& b){
    GMatrix m;
    m[0] = a[0] * b[0] + a[1] * b[3];
    m[1] = a[0] * b[1] + a[1] * b[4];
    m[2] = a[0] * b[2] + a[1] * b[5] + a[2];
    m[3] = a[3] * b[0]+ a[4] * b[3];
    m[4] = a[3] * b[1]+ a[4] * b[4];
    m[5] = a[3] * b[2]+ a[4] * b[5] + a[5];
    return m;
};

    /*
     *  Compute the inverse of this matrix, and store it in the "inverse" parameter, being
     *  careful to handle the case where 'inverse' might alias this matrix.
     *
     *  If this matrix is invertible, return true. If not, return false, and ignore the
     *  'inverse' parameter.
     */
bool GMatrix::invert(GMatrix* inverse) const{
    float det = fMat[0] * fMat[4] - fMat[1] * fMat[3];
    if(det == 0){
        return false;
    }
    GMatrix newMat;
    if(det == 1){
        newMat[0] = fMat[4];
        newMat[3] = -fMat[3];
        newMat[1] = -fMat[1];
        newMat[4] = fMat[0];
        newMat[2] = fMat[1] * fMat[5] - fMat[2] * fMat[4];
        newMat[5] = -(fMat[0] * fMat[5] - fMat[3] * fMat[2]);
    }
    else {
        newMat[0] = fMat[4] / det;
        newMat[3] = (-fMat[3]) / det;
        newMat[1] = (-fMat[1]) / det;
        newMat[4] = fMat[0] / det;
        newMat[2] = (fMat[1] * fMat[5] - fMat[2] * fMat[4]) / det;
        newMat[5] = (-(fMat[0] * fMat[5] - fMat[3] * fMat[2])) / det;
    }
    *inverse = newMat;
    return true;
};

    /**
     *  Transform the set of points in src, storing the resulting points in dst, by applying this
     *  matrix. It is the caller's responsibility to allocate dst to be at least as large as src.
     *
     *  [ a  b  c ] [ x ]     x' = ax + by + c
     *  [ d  e  f ] [ y ]     y' = dx + ey + f
     *  [ 0  0  1 ] [ 1 ]
     *
     *  Note: It is legal for src and dst to point to the same memory (however, they may not
     *  partially overlap). Thus the following is supported.
     *
     *  GPoint pts[] = { ... };
     *  matrix.mapPoints(pts, pts, count);
     */
void GMatrix::mapPoints(GPoint dst[], const GPoint src[], int count) const{
    for(int i = 0; i < count; i++){
        float dx = fMat[0] * src[i].x() + fMat[1]*src[i].y() + fMat[2];
		dst[i].fY = fMat[3] * src[i].x() + fMat[4] * src[i].y() + fMat[5];
		dst[i].fX = dx;
    }
};
