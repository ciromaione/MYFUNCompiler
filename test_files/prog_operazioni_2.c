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
char _string_9[256];
char _string_10[256];
char _string_11[256];
char _string_12[256];
char _string_13[256];
char _string_14[256];
char _string_15[256];
char _string_16[256];
char _string_17[256];
char _string_18[256];
char _string_19[256];
char _string_20[256];
char _string_21[256];
char _string_22[256];
char _string_23[256];
char _string_24[256];
char _string_25[256];
char _string_26[256];
char _string_27[256];
char _string_28[256];
char _string_29[256];
char _string_30[256];
char _string_31[256];
char _string_32[256];
char _string_33[256];
char _string_34[256];
char _string_35[256];
char _string_36[256];
char _string_37[256];
char _string_38[256];
char _string_39[256];
char _string_40[256];
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
}int id_0() {
int id_1;
printf("%s\r\n","Scegli l'operazione da svolgere per continuare");
printf("%s\r\n","\t(1) Somma di due numeri");
printf("%s\r\n","\t(2) Moltiplicazione di due numeri");
printf("%s\r\n","\t(3) Divisione intera fra due numeri positivi");
printf("%s\r\n","\t(4) Elevamento a potenza");
printf("%s\r\n","\t(5) Successione di Fibonacci (ricorsiva)");
printf("%s\r\n","\t(6) Successione di Fibonacci (iterativa)");
printf("%s\r\n","\t(0) Esci");
printf("%s","--> ");
scanf("%d",&id_1);
return id_1;
}
void id_2() {
float id_3;
float id_4;
printf("%s\r\n","\n(1) SOMMA\n");
printf("%s","Inserisci il primo operando: ");
scanf("%f",&id_3);
printf("%s","Inserisci il secondo operando: ");
scanf("%f",&id_4);
printf("%s\r\n","");
printf("%s\r\n",str_concat(str_concat(str_concat(str_concat(str_concat("La somma tra ",real_to_string(id_3,_string_0),_string_1)," e ",_string_2),real_to_string(id_4,_string_3),_string_4)," vale ",_string_5),real_to_string((id_3+id_4),_string_6),_string_7));
}
void id_5() {
float id_3;
float id_4;
printf("%s\r\n","\n(2) MOLTIPLICAZIONE");
printf("%s","\nInserisci il primo operando: ");
scanf("%f",&id_3);
printf("%s","Inserisci il secondo operando: ");
scanf("%f",&id_4);
printf("%s\r\n","");
printf("%s\r\n",str_concat(str_concat(str_concat(str_concat(str_concat("La moltiplicazione tra ",real_to_string(id_3,_string_8),_string_9)," e ",_string_10),real_to_string(id_4,_string_11),_string_12)," vale ",_string_13),real_to_string((id_3*id_4),_string_14),_string_15));
}
void id_6() {
int id_3;
int id_4;
printf("%s\r\n","\n(3) DIVISIONE INTERA");
printf("%s","\nInserisci il primo operando: ");
scanf("%d",&id_3);
printf("%s","Inserisci il secondo operando: ");
scanf("%d",&id_4);
printf("%s\r\n","");
printf("%s\r\n",str_concat(str_concat(str_concat(str_concat(str_concat("La divisione intera tra ",int_to_string(id_3,_string_16),_string_17)," e ",_string_18),int_to_string(id_4,_string_19),_string_20)," vale ",_string_21),int_to_string(((int) id_3/id_4),_string_22),_string_23));
}
void id_7() {
float id_3;
float id_4;
printf("%s\r\n","\n(4) POTENZA");
printf("%s","\nInserisci la base: ");
scanf("%f",&id_3);
printf("%s","Inserisci l'esponente: ");
scanf("%f",&id_4);
printf("%s\r\n","");
printf("%s\r\n",str_concat(str_concat(str_concat(str_concat(str_concat("La potenza di ",real_to_string(id_3,_string_24),_string_25)," elevato a ",_string_26),real_to_string(id_4,_string_27),_string_28)," vale ",_string_29),real_to_string((pow(id_3,id_4)),_string_30),_string_31));
}
int id_8(int id_9) {
if((id_9==1)) {
return 0;

}
if((id_9==2)) {
return 1;

}
return (id_8((id_9-1))+id_8((id_9-2)));
}
int id_10(int id_9) {
if((id_9==1)) {
return 0;

}
if((id_9==2)) {
return 1;

}
if((id_9>2)) {
int id_11=3;
int id_12=1;
int id_13=0;
while((id_11<=id_9)) {
int id_14=id_12;
id_12=(id_12+id_13);
id_13=id_14;
id_11=(id_11+1);

}
return id_12;

}
return (- 1);
}
void id_15(int id_16) {
int id_9;
printf("%s\r\n","\n(5) FIBONACCI");
printf("%s","\nInserisci n: ");
scanf("%d",&id_9);
printf("%s\r\n","");
strcpy(_string_32, str_concat(str_concat("Il numero di Fibonacci in posizione ",int_to_string(id_9,_string_33),_string_34)," vale ",_string_35));
if(id_16) {
strcpy(_string_32, str_concat(_string_32,int_to_string(id_8(id_9),_string_36),_string_37));

}
else {
strcpy(_string_32, str_concat(_string_32,int_to_string(id_10(id_9),_string_38),_string_39));

}
printf("%s\r\n",_string_32);
}
void id_17(int id_1) {
if((id_1==1)) {
id_2();

}
else {
if((id_1==2)) {
id_5();

}
else {
if((id_1==3)) {
id_6();

}
else {
if((id_1==4)) {
id_7();

}
else {
if((id_1==5)) {
id_15(1);

}
else {
if((id_1==6)) {
id_15(0);

}

}

}

}

}

}
}
void id_18(int *id_19_out) {
int id_19=*id_19_out;
printf("%s","Vuoi continuare? (s/n) --> ");
scanf("%s",_string_40);
if((strcmp(_string_40, "s") == 0)) {
id_19=1;

}
else {
id_19=0;

}
*id_19_out=id_19;
}
int main() {
int id_1=0;
int id_19=1;
while(id_19) {
id_1=id_0();
if((id_1==0)) {
id_19=0;

}
else {
id_17(id_1);
id_18(&id_19);

}

}
return 0;
}