/* Example: analysis of text */

#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#define MAX 1000 /* The maximum number of characters in a line of input */

int main()
{
  char text[MAX], c;
  int i, j;
  int length;
  bool isPalindrome = true;
  puts("Type some text (then ENTER):");
  
  /* Save typed characters in text[]: */
  fgets(text, MAX, stdin);
  length = strlen(text) -1; 
  char newText[length];
  
  /* Analyse contents of text[]: */
    
  for (i = length -1; i >= 0; i--)
  {
    c = text[i];
    newText[j]=c;
    j++;
  }
  printf("Your input in reverse is:\n");
  for(i=0;i<length;i++){
   printf("%c",newText[i]);
   if(text[i]!=newText[i]){
    isPalindrome=false;
   }
  }
  printf("\n");
  if(isPalindrome){
   printf("Found a palindrome!\n");
  }
}
