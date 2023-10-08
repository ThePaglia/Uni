#include <iostream>
#include "ShaderMaker.h"
#include <GL/glew.h>
#include <GL/freeglut.h>

static unsigned int programId;

unsigned int VBO; // Vertex Buffer Object - contiene i dati dei vertici
unsigned int VAO; // Vertex Array Object - contiene i VBO

// Coordinate normalizzate dei vertici
float vertices[] = {
	-0.5f, -0.5f, 0.0f,  // vertice in basso a sinistra
	0.5f, -0.5f, 0.0f,  // vertice in basso a destra
	0.0f,  0.5f, 0.0f // vertice in alto 
};

void gestisci_shader(void) {
	GLenum ErrorCheckValue = glGetError();
	char* vertexShader = (char*)"vertexShader.glsl";
	char* fragmentShader = (char*)"fragmentShader.glsl";
	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);
}

// Inizializza il VAO
void INIT_VAO(void)
{
	glGenVertexArrays(1, &VAO);	// Creo il VAO
	glBindVertexArray(VAO);		// Faccio il bind (lo collego e lo attivo)

	glGenBuffers(1, &VBO);		// Creo un VBO all'interno del VAO
	glBindBuffer(GL_ARRAY_BUFFER, VBO);	// Faccio il bind (lo collego e lo attivo assegnadogli il tipo GL_ARRAY_BUFFER)
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);	// Carico i dati dei vertici sulla GPU

	// Configurazione degli attributi letti dal buffer caricato sulla GPU
	/* Argomenti:
	* 1) Attributo/i da configurare:
	*		0) Coordinate posizionali
	*		1) Colori
	* 2) Dimensione dell'attributo
	* 3) Tipo di dati
	* 4) Booleano per indicare se i dati devono essere normalizzati
	* 5) Stride = offset (salto di bit) tra due attributi consecutivi
	*/
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);	// Abilita l'attributo del vertice (sono quelli di prima)
}

// Disegna i punti sulla finestra creata
void drawScene(void)
{
	glClearColor(0.0, 0.0, 0.0, 1.0);	// Specifica il colore che la finestra deve assumere quando viene resettata
	glClear(GL_COLOR_BUFFER_BIT);		// Pulisce il buffer del colore e setta il colore a quello definito prima
	glBindVertexArray(VAO);				// Attiva il VAO
	glDrawArrays(GL_TRIANGLES, 0, 3);	// Assembla a 3 a 3 i vertici di un triangolo (0 = posizione iniziale, 3 = numero di vertici)
	glBindVertexArray(0);				// Disattiva il VAO
	glutSwapBuffers();					// Swap tra il front e back frame buffer durante l'animazione
}

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);							// Inizializza il FreeGLUT toolkit
	glutInitContextVersion(4, 0);					// Imposta la versione del contesto (OpenGL 4.0)
	glutInitContextProfile(GLUT_CORE_PROFILE);		// Imposta il profilo del contesto
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA);	// Specifica che verrà allocato un Buffer Display unico e i colori sono in formato RGBA
	glutInitWindowSize(800, 800);					// Imposta la dimensione della finestra (pixel x pixel)
	glutInitWindowPosition(100, 100);				// Imposta la distanza dall'angolo in alto a sinistra dello schermo
	glutCreateWindow("Triangolo OpenGL");			// Crea una finestra sullo schermo e gli dà un titolo
	glutDisplayFunc(drawScene);
	glewExperimental = GL_TRUE;
	glewInit();
	gestisci_shader();
	INIT_VAO();
	glutMainLoop();
}