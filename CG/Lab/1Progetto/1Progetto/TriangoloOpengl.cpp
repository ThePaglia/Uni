// TriangoloOpengl.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

// Triangolo_OpenGL.cpp : Questo file contiene la funzione 'main', in cui inizia e termina l'esecuzione del programma.
//

#include <iostream>
#include "ShaderMaker.h"
#include <GL/glew.h>
#include <GL/freeglut.h>

static unsigned int programId;

unsigned int VAO;
unsigned int VBO;


//Vertici del triangolo nel sistema di coordinate normalizzate

float vertices[] = {
	// posizioni         // colori
	 -0.5f, -0.5f, 0.0f,  // vertice in basso a sinistra
	  0.5f, -0.5f, 0.0f,  // vertice in basso a destra
	 0.0f,  0.5f, 0.0f // vertice in alto 
};

void gestisci_shader(void)

{
	GLenum ErrorCheckValue = glGetError();

	char* vertexShader = (char*)"vertexShader.glsl";
	char* fragmentShader = (char*)"fragmentShader.glsl";

	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);

}

	void INIT_VAO(void)
{
	//Genero un VAO
	glGenVertexArrays(1, &VAO);
	//Ne faccio il bind (lo collego, lo attivo)
	glBindVertexArray(VAO);

	//AL suo interno genero un VBO
	glGenBuffers(1, &VBO);
	//Ne faccio il bind (lo collego, lo attivo, assegnandogli il tipo GL_ARRAY_BUFFER)
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	//Carico i dati vertices sulla GPU
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

	// Configurazione dell'attributo posizione: informo il vertex shader su: dove trova le informazioni sulle posizioni e come le deve leggere
	//dal buffer caricato sulla GPU
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);

	glClearColor(0.0, 0.0, 0.0, 1.0);





}
void drawScene(void)
{

	glClear(GL_COLOR_BUFFER_BIT);

	glBindVertexArray(VAO);
	glDrawArrays(GL_TRIANGLES, 0, 3);

	glutSwapBuffers();



}

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);

	glutInitContextVersion(4, 0);
	glutInitContextProfile(GLUT_CORE_PROFILE);

	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA);

	glutInitWindowSize(800, 800);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("Triangolo OpenGL");
	glutDisplayFunc(drawScene);


	glewExperimental = GL_TRUE;
	glewInit();
	gestisci_shader();
	INIT_VAO();

	glutMainLoop();
}

