#include <stdio.h>

int AA[100];
int BB[100];
int CC[100];
int M;
int multiplyMatrix(int row, int column){
    int dotProduct=0;
    for(int i=0; i<M;i++){
        dotProduct+= AA[(row*M)+ i] * BB[(i*M)+column];
    }
    CC[(row*M) + column] = dotProduct;
    printf("%d ",CC[(row*M) + column]);
    if(row == M-1 && column == M-1){
        //end
        printf("\n");
    }
    else if(column == M-1){
        printf("\n");
        multiplyMatrix(row+1,0);
    }
    else{
        multiplyMatrix(row,column+1);
    }
}
int main() {
        scanf("%d",&M);
        for(int i=0;i< M * M;i++){
            scanf("%d", &AA[i]);
        }
        for(int j=0; j< M*M; j++){
            scanf("%d", &BB[j]);
        }
        multiplyMatrix(0,0);
}
