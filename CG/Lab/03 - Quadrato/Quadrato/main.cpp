#include <iostream>
#include "ShaderMaker.h"
#include <GL/glew.h>
#include <GL/freeglut.h>

static unsigned int programId;

unsigned int VBO; // Vertex Buffer Object - contiene i dati dei vertici
unsigned int VAO; // Vertex Array Object - contiene i VBO
unsigned int EBO; // Element Buffer Object - contiene le informazioni per collegare i vertici

/*	Per creare un quadrato dobbiamo generare due triangoli.
	Facendo cos� per� avremmo dei vertici ripetuti, come scritto qui sotto (vertici scritti con i valori x, y, z, r, g, b, a):

	float vertices[] = {
		// Triangolo 1
		-0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,	// Vertice in basso a sinistra
		 0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,	// Vertice in basso a destra
		0.5f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 1.0f,	// Vertice in alto a destra
		// Triangolo 2
		0.5f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 1.0f,	// Vertice in alto a destra
		-0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,	// Vertice in alto a sinistra
		-0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f	// Vertice in basso a sinistra
	};
	Possiamo utilizzare 4 vertici e unire le linee tramite un vettore di indici che indica l'ordine da seguire
	per unire i punti con delle linee.
*/
float vertices[] = {
	// posizioni
	-0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, // vertice in basso a sinistra (rosso)
	-0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,	// vertice in basso a destra (bianco)
	0.5f, 0.5, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,	// vertice in alto a destra (rosso)
	0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,	// vertice in alto a sinistra (bianco)
};

unsigned int indices[] = {
	0, 1, 2, // Triangolo 1
	2, 3, 0	 // Triangolo 2
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

	glGenBuffers(1, &VBO);													   // Creo un VBO all'interno del VAO
	glBindBuffer(GL_ARRAY_BUFFER, VBO);										   // Faccio il bind (lo collego e lo attivo assegnadogli il tipo GL_ARRAY_BUFFER)
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW); // Carico i dati dei vertici sulla GPU

	// Configuro le coordinate posizionali e le faccio saltare di 7 in 7 (ogni vertice � x, y, z, r, g, b, z)
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 7 * sizeof(float), (void *)0);
	glEnableVertexAttribArray(0); // Abilita l'attributo delle coordinate posizionali

	// Configuro i colori con uno stride di 7 e li faccio partire dal valore in posizione 3 (perch� 0, 1 e 2 sono per x, y e z)
	glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 7 * sizeof(float), (void *)(3 * sizeof(float)));
	glEnableVertexAttribArray(1); // Abilita l'attributo dei colori

	glGenBuffers(1, &EBO);															 // Creo un EBO all'interno del VAO
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);										 // Faccio il bind
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW); // Carico i dati degli indici sul buffer
}

// Disegna i punti sulla finestra creata
void drawScene(void)
{
	glClearColor(0.0, 0.0, 0.0, 1.0);					 // Specifica il colore che la finestra deve assumere quando viene resettata
	glClear(GL_COLOR_BUFFER_BIT);						 // Pulisce il buffer del colore e setta il colore a quello definito prima
	glBindVertexArray(VAO);								 // Attiva il VAO
	glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0); // Disegna i triangoli avendo, in totale, 6 vertici
	glBindVertexArray(0);								 // Disattiva il VAO
	glutSwapBuffers();									 // Swap tra il front e back frame buffer durante l'animazione
}

int main(int argc, char *argv[])
{
	glutInit(&argc, argv);						  // Inizializza il FreeGLUT toolkit
	glutInitContextVersion(4, 0);				  // Imposta la versione del contesto (OpenGL 4.0)
	glutInitContextProfile(GLUT_CORE_PROFILE);	  // Imposta il profilo del contesto
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA); // Specifica che verr� allocato un Buffer Display unico e i colori sono in formato RGBA
	glutInitWindowSize(800, 800);				  // Imposta la dimensione della finestra (pixel x pixel)
	glutInitWindowPosition(100, 100);			  // Imposta la distanza dall'angolo in alto a sinistra dello schermo
	glutCreateWindow("Quadrato OpenGL");		  // Crea una finestra sullo schermo e gli d� un titolo
	glutDisplayFunc(drawScene);
	glewExperimental = GL_TRUE;
	glewInit();
	gestisci_shader();
	INIT_VAO();
	glutMainLoop();
}