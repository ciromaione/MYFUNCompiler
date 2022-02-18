#include <stdio.h>
#include <string.h>
#include <math.h>
char _string_0[256];
char _string_1[256];
char _string_2[256];
char _string_3[256];
char _string_4[256];
char _string_5[256];
char _string_6[256];
char _string_7[256];
char _string_8[256];
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

const char* bool_to_string(int a) {
    if(a) return "true";
    return "false";
}int id_0=(- 1);
void id_1() {
printf("%s\r\n","Scegli l'operazione da svolgere per continuare");
printf("%s\r\n","(1) Addizione");
printf("%s\r\n","(2) Sottrazione");
printf("%s\r\n","(3) Moltiplicazione");
printf("%s\r\n","(4) Divisione");
printf("%s\r\n","(0) Esci");
printf("%s","	 > ");
scanf("%d",&id_0);
}
void id_2() {
printf("%s\r\n","Vuoi continuare? (s/n)");
printf("%s","	 > ");
scanf("%s",_string_0);
if((strcmp(_string_0, "s") != 0)) {
id_0=0;

}
}
void id_3(float *id_4_out, float *id_5_out) {
float id_4=*id_4_out;
float id_5=*id_5_out;
printf("%s","Inserisci il primo operando	 > ");
scanf("%f",&id_4);
printf("%s","Inserisci il secondo operando	 > ");
scanf("%f",&id_5);
*id_4_out=id_4;
*id_5_out=id_5;
}
void id_6() {
float id_4;
float id_5;
float id_7;
id_3(&id_4,&id_5);
id_7=(id_4+id_5);
printf("%s\r\n",str_concat("Il risultato dell'addizione è ",real_to_string(id_7,_string_1),_string_2));
id_2();
}
void id_8() {
float id_4;
float id_5;
float id_7;
id_3(&id_4,&id_5);
id_7=(id_4-id_5);
printf("%s\r\n",str_concat("Il risultato della sottrazione è ",real_to_string(id_7,_string_3),_string_4));
id_2();
}
void id_9() {
float id_4;
float id_5;
float id_7;
id_3(&id_4,&id_5);
id_7=(id_4*id_5);
printf("%s\r\n",str_concat("Il risultato della moltiplicazione è ",real_to_string(id_7,_string_5),_string_6));
id_2();
}
void id_10() {
float id_4;
float id_5;
float id_7;
id_3(&id_4,&id_5);
id_7=(id_4/id_5);
printf("%s\r\n",str_concat("Il risultato della divisione è ",real_to_string(id_7,_string_7),_string_8));
id_2();
}
int main() {
while((id_0!=0)) {
id_1();
if((id_0==1)) {
id_6();

}
else {
if((id_0==2)) {
id_8();

}
else {
if((id_0==3)) {
id_9();

}
else {
if((id_0==4)) {
id_10();

}
else {
printf("%s\r\n","Valore inserito non valido, riprova!");

}

}

}

}

}
return 0;
}