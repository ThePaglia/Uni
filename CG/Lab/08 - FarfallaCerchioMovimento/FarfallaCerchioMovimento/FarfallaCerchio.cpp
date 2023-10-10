//

#include <iostream>
#include "ShaderMaker.h"
#include "lib.h"

#include <GL/glew.h>
#include <GL/freeglut.h>
static unsigned int programId;

#define  PI   3.14159265358979323846

mat4 Projection;
GLuint MatProj, MatModel;
int width = 1280;
int height = 720;
float scaling = 1.0;
float dx = 0.0, dy = 0.0;
float angoloRotazione = 0.0;
float angoloCerchio = 0.0;

typedef struct {
	GLuint VAO;
	GLuint VBO_G;
	GLuint VBO_C;
	int nTriangles;
	// Vertici
	vector<vec3> vertici;
	vector<vec4> colors;
	// Numero vertici
	int nv;
	//Matrice di Modellazione: Traslazione*Rotazione*Scala
	mat4 Model;
} Figura;


Figura  Farf = {};
Figura Cerchio = {};
vector<Figura> Scena;

void crea_VAO_Vector(Figura* fig)
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


void costruisci_farfalla(float cx, float cy, float raggiox, float raggioy, Figura* fig) {

	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t, xx, yy;


	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(150.0 / 255.0, 75.0 / 255.0, 0.0, 0.2));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		xx = cx + raggiox * (sin(t) * (exp(cos(t)) - 2.0 * cos(4.0 * t)) + pow(sin(t / 12.0), 5.0));
		yy = cy + raggioy * (cos(t) * (exp(cos(t)) - 2.0 * cos(4.0 * t)) + pow(sin(t / 12.0), 5.0));
		fig->vertici.push_back(vec3(xx, yy, 0.0));
		//Colore 
		fig->colors.push_back(vec4(1.0, 0.0, 0.0, 0.5)); //Nota che la quarta componente corrisponde alla trasparenza del colore
	}

	fig->nv = fig->vertici.size();

}

void costruisci_cerchio(float cx, float cy, float raggiox, float raggioy, Figura* fig) {

	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t, xx, yy;


	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(150.0 / 255.0, 75.0 / 255.0, 0.0, 0.2));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		xx = cx + raggiox * sin(t);
		yy = cy + raggioy * cos(t);
		fig->vertici.push_back(vec3(xx, yy, 0.0));
		//Colore 
		fig->colors.push_back(vec4(1.0, 0.0, 0.0, 0.5)); //Nota che la quarta componente corrisponde alla trasparenza del colore
	}

	fig->nv = fig->vertici.size();

}

void INIT_SHADER(void)
{
	GLenum ErrorCheckValue = glGetError();

	char* vertexShader = (char*)"vertexShaderMatrix.glsl";
	char* fragmentShader = (char*)"fragmentShaderMatrix.glsl";

	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);
}

void INIT_VAO(void)
{
	Farf.nTriangles = 180;
	costruisci_farfalla(0.0, 0.0, 0.1, 0.1, &Farf);
	crea_VAO_Vector(&Farf);
	Scena.push_back(Farf);

	Cerchio.nTriangles = 50;
	costruisci_cerchio(0.0, 0.0, 1, 1, &Cerchio);
	crea_VAO_Vector(&Cerchio);
	Scena.push_back(Cerchio);

	Projection = ortho(0.0f, float(width), 0.0f, float(height));
	MatProj = glGetUniformLocation(programId, "Projection");
	MatModel = glGetUniformLocation(programId, "Model");
}

void drawScene(void)
{
	int k;
	float px = 400.0 + 200.0 * cos(angoloCerchio);
	float py = 400.0 + 200.0 * sin(angoloCerchio);

	glClearColor(0.0, 0.0, 0.0, 1.0);
	glClear(GL_COLOR_BUFFER_BIT);
	for (k = 0; k < Scena.size(); k++) {
		if (k == 0) {
			Scena[k].Model = mat4(1.0);
			Scena[k].Model = translate(Scena[k].Model, vec3(800.0 + dx, 400.0 + dy, 0.0)); //Muovere, ruotare e scalare
			//Scena[k].Model = translate(Scena[k].Model, vec3(px, py, 0.0));	//Fa girare la farfalla intorno al cerchio
			Scena[k].Model = scale(Scena[k].Model, vec3(300.0 * scaling, 300.0 * scaling, 1.0));
			Scena[k].Model = rotate(Scena[k].Model, radians(angoloRotazione), vec3(0.0f, 0.0f, 1.0f));
		}
		else {
			Scena[k].Model = mat4(1.0);
			Scena[k].Model = translate(Scena[k].Model, vec3(400.0, 400.0, 0.0));
			Scena[k].Model = scale(Scena[k].Model, vec3(100.0 * scaling, 100.0 * scaling, 1.0));
		}
		glBindVertexArray(Scena[k].VAO);
		glUniformMatrix4fv(MatProj, 1, GL_FALSE, value_ptr(Projection));
		glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[k].Model));
		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glDrawArrays(GL_TRIANGLE_FAN, 0, Scena[k].nv);
		glBindVertexArray(0);
	}
	glutSwapBuffers();
}

void updateAngoloCerchio(int value) {
	angoloCerchio += PI / 120;
	glutTimerFunc(50, updateAngoloCerchio, 0);
	glutPostRedisplay();
}

void updateScale(int value) {
	scaling = scaling * 1.1;
	if (scaling > 2) {
		scaling = 1.0;
	}
	glutTimerFunc(50, updateScale, 0);
	glutPostRedisplay();
}

void myKeyboard(unsigned char key, int x, int y) {
	switch (key)
	{
	case 'a':
		dx -= 5.0;
		break;
	case 'd':
		dx += 5.0;
		break;
	case 'w':
		dy += 5.0;
		break;
	case 's':
		dy -= 5.0;
		break;
	case 'q':
		angoloRotazione += 5.0;
		break;
	case 'e':
		angoloRotazione -= -5.0;
		break;
	default:
		break;
	}
	glutPostRedisplay();
}

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);

	glutInitContextVersion(4, 0);
	glutInitContextProfile(GLUT_CORE_PROFILE);

	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA);

	glutInitWindowSize(width, height);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("FarfallaCerchio OpenGL");
	glutDisplayFunc(drawScene);

	//glutTimerFunc(50, updateAngoloCerchio, 0); //Per far girare la farfalla intorno al cerchio
	//glutTimerFunc(50, updateScale, 0); //Per far pulsare
	//glutKeyboardFunc(myKeyboard); //Per muovere

	glewExperimental = GL_TRUE;
	glewInit();
	INIT_SHADER();
	INIT_VAO();

	//Per gestire i colori con trasparenza: mescola i colori di geometrie hce si sovrappongono
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glutMainLoop();
}


