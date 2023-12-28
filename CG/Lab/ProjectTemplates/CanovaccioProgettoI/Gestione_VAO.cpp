#include "Lib.h"
#include "Strutture.h"

void crea_VAO_Vector(Forma* fig)
{

glGenVertexArrays(1, &fig->VAO);
glBindVertexArray(fig->VAO);
//Genero , rendo attivo, riempio il VBO della geometria dei vertici
glGenBuffers(1, &fig->VBO_G);
glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_G);
glBufferData(GL_ARRAY_BUFFER, fig->vertici.size() * sizeof(vec3), fig->vertici.data(), GL_STATIC_DRAW);

glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, (void*)0);
glEnableVertexAttribArray(0);

//Genero , rendo attivo, riempio il VBO dei colori
glGenBuffers(1, &fig->VBO_C);
glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_C);
glBufferData(GL_ARRAY_BUFFER, fig->colors.size() * sizeof(vec4), fig->colors.data(), GL_STATIC_DRAW);
//Adesso carico il VBO dei colori nel layer 2
glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 0, (void*)0);
glEnableVertexAttribArray(1);

}