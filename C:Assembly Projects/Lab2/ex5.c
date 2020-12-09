
#include <stdio.h>

int main()
{
 int width, height;
 width=1;
 while(width != 0){
  printf("Please enter width and height:\n");
  scanf("%d",&width);
  if(width!=0){
   scanf("%d",&height);
   printf("+");
   for(int i=2;i<=width;i++){
    if(i==width){
     printf("+");
    }
    else {
     printf("-");
    }
   }
   printf("\n");
   for(int i=2;i<=height;i++){
    for(int j=1;j<=width;j++){
	if(j==1||j==width){
   	 if(i==height){
    		 printf("+");
	 }
	 else{
	 	printf("|");
	 }
	}
	else{
		if(i==height){
		 printf("-");
		}
		else{
		 printf("~");
		}
	}
    }
   printf("\n");
   }
  }
  else {
   printf("End\n");
  }
 }
}
