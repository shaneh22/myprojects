/******************************************************************************
/* Example: analysis of text */
//
#include <ctype.h>
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
  char backText[MAX];
  char newText[MAX];
  char newBackText[MAX];
  
  /* Analyse contents of text[]: */
    
  for (i = length -1; i >= 0; i--)
  {
    c = text[i];
    backText[j]=c;
    j++;
  }
  int nextPos=0;
  for(i=0;i<length;i++){
   if(isalpha(text[i])){
    c=tolower(text[i]);
    newText[nextPos] = c;
    nextPos++;
   }
  }
  int newLength=nextPos;
  j=0;
  i= newLength;
  for (i = newLength; i >= 0; i--)
  {
    c = newText[i];
    newBackText[j]=c;
    j++;
  }
  printf("Your input in reverse is:\n");
  for(i=0;i<length;i++){
   printf("%c",backText[i]);
  }
  printf("\n");
  for(i=0;i<newLength;i++){
   if(newBackText[i+1]!=newText[i]){
    isPalindrome=false;
   }
  }
  if(isPalindrome){
   printf("Found a palindrome!\n");
  }
}
