#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define MAXSIZE 100

char* int2str(int value)
{
    char *result = (char*) malloc(sizeof(char) * MAXSIZE);
    sprintf(result, "%d", value);

    return result;
}

char* bool2str(bool value)
{
    if (value == true) {
        char *ret = "true";
        return ret;
    }
    else {
        char *ret = "false";
        return ret;
    }
}

char* plusPlus(char *left, char *right)
{
    int len_left, len_right;

    for (len_left = 0; left[len_left] != '\0'; len_left++);
    for (len_right = 0; right[len_right] != '\0'; len_right++);

    int result_length = len_left + len_right + 1;
    char *result = (char*) malloc(sizeof(char) * result_length);

    for (int i = 0; i < len_left; i++)
        result[i] = left[i];

    for (int j = 0; j < len_right; j++)
        result[j + len_left] = right[j];

    result[result_length] = '\0';

    return result;
}

void main()
{
    char *a = "uma ";
    char *b = "outra ";
    char *c = " coisa";
    char *d = " nova";
    bool outro = false;

    char *e = plusPlus(plusPlus(a, bool2str(outro)), plusPlus(int2str(500), bool2str(true)));

    for (int i = 0; e[i] != '\0'; i++)
        printf("%c\n", e[i]);
}
