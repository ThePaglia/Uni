#include <iostream>
#include "ShaderMaker.h"
#include <GL/glew.h>
#include <GL/freeglut.h>

static unsigned int programId;

unsigned int VBO_P, VBO_C; // VBO per posizioni (P) e colori (C) dei vertici
unsigned int VAO;

// Coordinate normalizzate dei vertici
float vertices[] = {
	-0.5f, -0.5f, 0.0f, // vertice in basso a sinistra
	0.5f, -0.5f, 0.0f,	// vertice in basso a destra
	0.0f, 0.5f, 0.0f	// vertice in alto
};

// Colori dei vertici
float colors[] = {
	1.0f, 0.0f, 0.0f, 1.0f, // vertice in basso a sinistra rosso
	0.0f, 1.0f, 0.0f, 1.0f, // vertice in basso a destra verde
	0.0f, 0.0f, 1.0f, 1.0f	// vertice in alto blu
};

void gestisci_shader(void)
{
	GLenum ErrorCheckValue = glGetError();
	char *vertexShader = (char *)"vertexShaderSfumato.glsl";
	char *fragmentShader = (char *)"fragmentShaderSfumato.glsl";
	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);
}

// Inizializza il VAO
void INIT_VAO(void)
{
	glGenVertexArrays(1, &VAO); // Creo il VAO
	glBindVertexArray(VAO);		// Faccio il bind (lo collego e lo attivo)

	glGenBuffers(1, &VBO_P);												   // Creo un VBO per le posizioni all'interno del VAO
	glBindBuffer(GL_ARRAY_BUFFER, VBO_P);									   // Faccio il bind (lo collego e lo attivo assegnadogli il tipo GL_ARRAY_BUFFER)
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW); // Carico i dati dei vertici sulla GPU
	// Configurazione delle posizioni (stride = 3 -> x, y, z)
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void *)0);
	glEnableVertexAttribArray(0);

	glGenBuffers(1, &VBO_C);											   // Creo un VBO per i colori all'interno del VAO
	glBindBuffer(GL_ARRAY_BUFFER, VBO_C);								   // Faccio il bind (lo collego e lo attivo assegnadogli il tipo GL_ARRAY_BUFFER)
	glBufferData(GL_ARRAY_BUFFER, sizeof(colors), colors, GL_STATIC_DRAW); // Carico i dati dei colori sulla GPU
	// Configurazione dei colori (stride = 4 -> r, g, b, a)
	glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 4 * sizeof(float), (void *)0);
	glEnableVertexAttribArray(1);
}

// Disegna i punti sulla finestra creata
void drawScene(void)
{
	glClearColor(0.0, 0.0, 0.0, 1.0); // Specifica il colore che la finestra deve assumere quando viene resettata
	glClear(GL_COLOR_BUFFER_BIT);	  // Pulisce il buffer del colore e setta il colore a quello definito prima
	glBindVertexArray(VAO);			  // Attiva il VAO
	glDrawArrays(GL_TRIANGLES, 0, 3); // Assembla a 3 a 3 i vertici di un triangolo (0 = posizione iniziale, 3 = numero di vertici)
	glBindVertexArray(0);			  // Disattiva il VAO
	glutSwapBuffers();				  // Swap tra il front e back frame buffer durante l'animazione
}

int main(int argc, char *argv[])
{
	glutInit(&argc, argv);						   // Inizializza il FreeGLUT toolkit
	glutInitContextVersion(4, 0);				   // Imposta la versione del contesto (OpenGL 4.0)
	glutInitContextProfile(GLUT_CORE_PROFILE);	   // Imposta il profilo del contesto
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA);  // Specifica che verr� allocato un Buffer Display unico e i colori sono in formato RGBA
	glutInitWindowSize(700, 700);				   // Imposta la dimensione della finestra (pixel x pixel)
	glutInitWindowPosition(400, 50);			   // Imposta la distanza dall'angolo in alto a sinistra dello schermo
	glutCreateWindow("Triangolo Colorato OpenGL"); // Crea una finestra sullo schermo e gli d� un titolo
	glutDisplayFunc(drawScene);
	glewExperimental = GL_TRUE;
	glewInit();
	gestisci_shader();
	INIT_VAO();
	glutMainLoop();
}