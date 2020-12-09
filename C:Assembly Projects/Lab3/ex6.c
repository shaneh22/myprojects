#include <stdio.h>
int NchooseK(int n, int k){
if(k==0)return 1;
if(n==k)return 1;
if(n>k)return NchooseK(n-1,k-1) + NchooseK(n-1,k);
}
int main(){
int nInput, kInput, result;
do{
 printf("Enter two integers (for n and k) separated by space:\n");
 scanf("%d",&nInput);
 scanf("%d",&kInput);
 result = NchooseK(nInput,kInput);
 printf("%d\n",result);
} while (nInput!=0 || kInput !=0);
}
