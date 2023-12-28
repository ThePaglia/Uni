#include "Strutture.h"
float dx(int i, float* t, float Tens, float Bias, float Cont, Forma* Fig);
float DX(int i, float* t);
float DY(int i, float* t);
void InterpolazioneHermite(float* t, Forma* Fig, vec3 centro, vec4 color_top, vec4 color_bot);
void costruisci_formaHermite(vec3 centro,vec4 color_top, vec4 color_bot, Forma* forma);
 