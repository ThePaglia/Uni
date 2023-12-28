#pragma once
#include "Lib.h"
 
#include <string>




typedef struct {
	GLuint VAO;
	GLuint VBO_G;
	GLuint VBO_C;
	GLuint EBO_indici;
	int nTriangles;
	// Punti del piano e colori in cui viene disegnata la curva
	vector<vec3> vertici;
	vector<vec4> colors;
	// Punti del piano e colori che vnegono interpolati (Control Points)
	vector<vec3> CP;
	float s;
	vector<vec4> colCP;
	// Numero massimo di punti in cui viene disegnata la curva
	int nv;
	// Numero massimo di punti di interpolazione
	int ncp;
	vector<vec3> Derivata;
	vector<vec3> Poligonale;
	//Matrice di Modellazione: Traslazione*Rotazione*Scala
	mat4 Model;
	int sceltaFs;   //Intero che indica il fragment shader da utilizzare per la renderizzazione
	char render;	// Tipo di render da usare nella gl_Draw_Arrays,  GL_TRIANGLE_FAN, GL_TRAINGLES; etc.
	vec4 corner_b_obj;   //Corner Bottom del Boundng Box in coordinate dell'Oggetto
	vec4 corner_t_obj;   //Corner Top  del Boundng Box in coordinate dell'Oggetto
	vec4 corner_b;		//Corner Bottom del Boundng Box in coordinate del Mondo
	vec4 corner_t;		////Corner Top  del Boundng Box in coordinate dell'Oggetto
	bool alive;     //Booleano: true: in vita
}Forma;




struct Character {
	unsigned int TextureID; // ID handle of the glyph texture
	glm::ivec2   Size;      // Size of glyph
	glm::ivec2   Bearing;   // Offset from baseline to left/top of glyph
	unsigned int Advance;   // Horizontal offset to advance to next glyph
};
