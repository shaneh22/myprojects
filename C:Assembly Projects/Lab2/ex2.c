
//nothing
#include <stdio.h>

int main()
{
  double sum, product, min, max;
  double p;
  product=1;
  printf("Enter 10 floating-point numbers:\n");
  for(int i=0;i<10;i++){
        scanf("%lf",&p);
	sum+=p;
	product*=p;
	if(p>max){
		max=p;
	}
	else if(p<min){
		min = p;
	}
  }
  printf("Sum is %3.5f\n", sum);
  printf("Min is %3.5f\n",min);
  printf("Max is %3.5f\n",max);
  printf("Product is %3.5f\n",product);
}
