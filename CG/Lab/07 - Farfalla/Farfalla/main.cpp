#include <iostream>
#include "ShaderMaker.h"
#include <GL/glew.h>
#include <GL/freeglut.h>

static unsigned int programId;

#define PI 3.141592653

unsigned int VBO;
unsigned int VAO;

unsigned int nTriangles = 1000;			// Più triangoli uso, più il cerchio sarà tondo
unsigned int nVertices = nTriangles + 2;	// Deve collegare anche il centro e l'ultimo vertice

// Struct di un vertice (pixel)
typedef struct {
	float x, y, z, r, g, b, a;
} Vertex;

Vertex* circle = new Vertex[nVertices];

// Disegna un cerchio di colore nero al centro che sfuma verso il blu andando verso l'esterno
// x(theta) = cx + raggiox * cos(theta)
// y(theta) = cy + raggioy * sin(theta)
void drawCircle(float cx, float cy, float rx, float ry, Vertex* circle) {
	float step = 2 * PI / nTriangles;	// Angolo progressivo per disegnare i triangoli
	circle[0].x = cx;
	circle[0].y = cy;
	circle[0].z = 0;
	circle[0].r = 0.0f;
	circle[0].g = 0.0f;
	circle[0].b = 0.0f;
	circle[0].a = 1.0f;
	unsigned int count = 1;
	for (int i = 0; i <= nVertices; i++) {
		float theta_i = (float)i * step;
		circle[count].x = cx + rx * (sin(theta_i) * (exp(cos(theta_i) - 2 * cos(4 * theta_i)) - pow(sin(theta_i / 12), 5)));
		circle[count].y = cy + ry * (cos(theta_i) * (exp(cos(theta_i) - 2 * cos(4 * theta_i)) - pow(sin(theta_i / 12), 5)));
		circle[count].z = 0;
		circle[count].r = 0.0f;
		circle[count].g = 0.0f;
		circle[count].b = 1.0f;
		circle[count].a = 1.0f;
		count++;
	}
}

void gestisci_shader(void) {
	GLenum ErrorCheckValue = glGetError();
	char* vertexShader = (char*)"vertexShaderSfumato.glsl";
	char* fragmentShader = (char*)"fragmentShaderSfumato.glsl";
	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);
}

// Inizializza il VAO
void INIT_VAO(void)
{
	drawCircle(0.0f, 0.0f, 0.05f, 0.05f, circle);
	glGenVertexArrays(1, &VAO);	// Creo il VAO
	glBindVertexArray(VAO);		// Faccio il bind (lo collego e lo attivo)

	glGenBuffers(1, &VBO);		// Creo un VBO per le posizioni all'interno del VAO
	glBindBuffer(GL_ARRAY_BUFFER, VBO);	// Faccio il bind (lo collego e lo attivo assegnadogli il tipo GL_ARRAY_BUFFER)
	glBufferData(GL_ARRAY_BUFFER, nVertices * sizeof(Vertex), &circle[0], GL_STATIC_DRAW);	// Carico i dati dei vertici sulla GPU

	// Configurazione delle posizioni (stride = 3 -> x, y, z)
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 7 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);

	// Configurazione dei colori (stride = 4 -> r, g, b, a)
	glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 7 * sizeof(float), (void*)(3 * sizeof(float)));
	glEnableVertexAttribArray(1);
}

// Disegna i punti sulla finestra creata
void drawScene(void)
{
	glBindVertexArray(0);				// Disattiva il VAO
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);	// Specifica il colore che la finestra deve assumere quando viene resettata
	glClear(GL_COLOR_BUFFER_BIT);		// Pulisce il buffer del colore e setta il colore a quello definito prima
	glBindVertexArray(VAO);				// Attiva il VAO
	glDrawArrays(GL_TRIANGLE_FAN, 0, nVertices);	// Disegna li triangoli
	glutSwapBuffers();					// Swap tra il front e back frame buffer durante l'animazione
}

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);							// Inizializza il FreeGLUT toolkit
	glutInitContextVersion(4, 0);					// Imposta la versione del contesto (OpenGL 4.0)
	glutInitContextProfile(GLUT_CORE_PROFILE);		// Imposta il profilo del contesto
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA);	// Specifica che verrà allocato un Buffer Display unico e i colori sono in formato RGBA
	glutInitWindowSize(700, 700);					// Imposta la dimensione della finestra (pixel x pixel)
	glutInitWindowPosition(400, 50);				// Imposta la distanza dall'angolo in alto a sinistra dello schermo
	glutCreateWindow("Farfalla OpenGL");			// Crea una finestra sullo schermo e gli dà un titolo
	glutDisplayFunc(drawScene);
	glewExperimental = GL_TRUE;
	glewInit();
	gestisci_shader();
	INIT_VAO();
	glutMainLoop();
}