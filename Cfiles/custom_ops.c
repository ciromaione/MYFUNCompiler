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
}