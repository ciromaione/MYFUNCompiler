#include <stdio.h>
#include <string.h>
#include <math.h>
char _string_0[256];
char _string_1[256];
char _string_2[256];
char* str_concat(char *s1, char *s2, char *dest) {
    char _s1[256];
    char _s2[256];
    strcpy(_s1, s1);
    strcpy(_s2, s2);
    strcat(_s1, _s2);
    strcpy(dest, _s1);
    return dest;
}

char* int_to_string(int a, char *dest) {
    sprintf(dest, "%d", a);
    return dest;
}

char* real_to_string(float a, char *dest) {
    sprintf(dest, "%f", a);
    return dest;
}

char* bool_to_string(int a) {
    if(a) return "true";
    return "false";
}int id_0=100;
int main() {
int id_1;
printf("%s","inserisci i: ");
scanf("%d",&id_1);
if((id_1==0)) {
printf("%s\t","i uguale a 0");

}
else if((id_1==1)) {
strcpy(_string_0,"ciao");
printf("%s\t",str_concat("i uguale a 1 ",int_to_string(id_0,_string_1),_string_2));

}
else {
printf("%s\t","i diverso da 0 e 1");

}
if((id_1==0)) {

}
else if(0) {

}
if(0) {

}
if(0) {

}
else {

}
return 0;
}