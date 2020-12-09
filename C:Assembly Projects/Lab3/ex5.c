#include <stdio.h>
#include <string.h>
#define MAX_BUF 1024

int main () {

  char buf[MAX_BUF];
  int length;
  // other stuff
  int i;
  char c;
  do {
        // read a line
        // calculate its length
        // modify the line by switching characters
        // print the modified line
 fgets(buf,MAX_BUF,stdin);
 length = strlen(buf);
 i=0;
 while(buf[i]){
  c=buf[i];
  if(c=='e' || c=='E') buf[i]= '3';
  else if(c=='i' || c== 'I') buf[i] ='1';
  else if(c=='o' || c== 'O') buf[i] ='0';
  else if(c=='s' || c== 'S') buf[i] ='5';
  i++;
 }
 i=0;
if(length>1){
 while(buf[i]){
  printf("%c",buf[i]);
  i++;
 }
}
 } while (length > 1);

}

