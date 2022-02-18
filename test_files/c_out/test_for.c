#include <stdio.h>
#include <string.h>
#include <math.h>
char _string_0[256];
char _string_1[256];
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
}int main() {
int _index_0;
for(_index_0=0;_index_0<10;++_index_0) {
printf("%s\r\n",str_concat("L'indice corrente e'",int_to_string(_index_0,_string_0),_string_1));

}
return 0;
}