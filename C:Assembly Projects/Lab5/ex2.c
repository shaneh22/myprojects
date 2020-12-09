#include <stdio.h>

int AA[100];
int BB[100];
int CC[100];
int M;
int multiplyMatrix(int row, int column){
    int dotProduct=0;
    for(int i=0; i<M;i++){
        dotProduct+= AA[(row*3)+ i] * B[(i*3)+column];
    }
    C[row][column] = dotProduct;
    printf("%6d",C[row][column]);
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
	    scanf("%d", &AA[I]);
	}
	for(int i=0;i<M;i++){
	    scanf("%d", &BB[I]);
	}
	multiplyMatrix(0,0);
}
