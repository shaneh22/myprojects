#include <stdio.h>
int NchooseK(int n, int k){
if(k==0)return 1;
if(n==k)return 1;
else return NchooseK(n-1,k-1) + NchooseK(n-1,k);
}
int main(){
int nInput, kInput, result;
do{
 scanf("%d",&nInput);
 if(nInput==0) break;
 scanf("%d",&kInput);
 result = NchooseK(nInput,kInput);
 printf("%d\n",result);
}while (nInput!=0);
}
