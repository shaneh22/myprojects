

#include <stdio.h>

int main()
{
  int n;

  printf("Enter a number from 1 to 20:\n");
  scanf("%d", &n);
if(n>20||n<1){
  printf("Number is not in the range from 1 to 20\n");
}
else {
int i=0;
printf("Here are the first %d ordinal numbers:\n", n);
for(i=1;i<=n;i++){
  if(i==1){
    printf("1st\n");
  }
  else if(i==2){
    printf("2nd\n");
}
  else if(i==3){
    printf("3rd\n");
}
  else{
    printf("%dth\n", i);
}
}
}
}
