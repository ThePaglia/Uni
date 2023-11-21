#include <iostream>
#include "ShaderMaker.h"
#include "Lib.h"
#include "geometria.h"
#include "Gestione_VAO.h"
#include "GestioneTesto.h"
#include "inizializzazioni.h"
#include "GestioneInterazioni.h"
#include "GestioneTelecamera.h"
#include <ft2build.h>
#include FT_FREETYPE_H
#define BUFFER_OFFSET(i) ((char *)NULL + (i))
// Inizializzazione Booleano per la visualizzazione dell'ancora
bool visualizzaAncora = FALSE;

// Impostazione larghezza ed altezza della finestra sullo schermo
int width = 1024;
int height = 800;
// Definizione stringhe da visualizzare sulla finestra
string stringa_asse;
string Operazione;

int cont_cubi = 0, cont_pir = 0;
int w_up = width;
int h_up = height;
mat4 Projection_text;

unsigned int programId, programId_text, MatrixProj, MatrixProj_txt, MatModel, MatView;
unsigned int VAO_Text, VBO_Text;
float raggio_sfera = 2.5; // Raggio sfera che circonda gli oggetti per facilitare la selezione
vec3 asse = vec3(0.0, 1.0, 0.0);
int selected_obj = 0; // Specifica la posizione dell'oggetto di Scena selezionato

float cameraSpeed = 0.1;

vector<Mesh> Scena;
vector<vec3> centri;
vector<float> raggi;

// Definzione SetupTelecamera e SetupProspettiva

ViewSetup SetupTelecamera;
PerspectiveSetup SetupProspettiva;

// Varibili per la gestione della direzione della telecamera tramite mouse
bool firstMouse = true;
float lastX = (float)width / 2;
float lastY = (float)height / 2;
float Theta = -90.0f;
float Phi = 0.0f;

mat4 Projection, Model, View;

void resize(int w, int h)
{
	// Imposto la matrice di Proiezione per il rendering del testo

	Projection_text = ortho(0.0f, (float)width, 0.0f, (float)height);

	// Imposto la matrice di proiezione per la scena da diegnare
	Projection = perspective(radians(SetupProspettiva.fov), SetupProspettiva.aspect, SetupProspettiva.near_plane, SetupProspettiva.far_plane);

	float AspectRatio_mondo = (float)(width) / (float)(height); // Rapporto larghezza altezza di tutto ci� che � nel mondo
	// Se l'aspect ratio del mondo � diversa da quella della finestra devo mappare in modo diverso
	// per evitare distorsioni del disegno
	if (AspectRatio_mondo > w / h) // Se ridimensioniamo la larghezza della Viewport
	{
		glViewport(0, 0, w, w / AspectRatio_mondo);
		w_up = (float)w;
		h_up = w / AspectRatio_mondo;
	}
	else
	{ // Se ridimensioniamo la larghezza della viewport oppure se l'aspect ratio tra la finestra del mondo
		// e la finestra sullo schermo sono uguali
		glViewport(0, 0, h * AspectRatio_mondo, h);
		w_up = h * AspectRatio_mondo;
		h_up = (float)h;
	}
}

void drawScene(void)
{
	// Utilizzo il program shader per il disegno

	glUseProgram(programId);

	glClearColor(0.0, 0.0, 0.0, 1.0);

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Imposto la matrice di proiezione per la scena da diegnare
	Projection = perspective(radians(SetupProspettiva.fov), SetupProspettiva.aspect, SetupProspettiva.near_plane, SetupProspettiva.far_plane);
	glUniformMatrix4fv(MatrixProj, 1, GL_FALSE, value_ptr(Projection));
	// Costruisco la matrice di Vista che applicata ai vertici in coordinate del mondo li trasforma nel sistema di riferimento della camera.
	const float radius = 50.0f;
	// Commentare le righe seguenti per disabilitare la rotazione automatica della telecamera
	/*
	SetupTelecamera.position.x = sin(glutGet(GLUT_ELAPSED_TIME) / 5000.0f) * radius;
	SetupTelecamera.position.z = cos(glutGet(GLUT_ELAPSED_TIME) / 5000.0f) * radius;
	*/
	View = lookAt(vec3(SetupTelecamera.position), vec3(SetupTelecamera.target), vec3(SetupTelecamera.upVector));
	// Passo al Vertex Shader il puntatore alla matrice View, che sar� associata alla variabile Uniform mat4 Projection
	// all'interno del Vertex shader. Uso l'identificatio MatView

	glUniformMatrix4fv(MatView, 1, GL_FALSE, value_ptr(View));
	glPointSize(10.0);
	for (int k = 0; k < Scena.size(); k++)
	{
		glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[k].Model));
		glBindVertexArray(Scena[k].VAO);

		if (visualizzaAncora == TRUE)
		{
			// Visualizzo l'ancora dell'oggetto
			int ind = Scena[k].indici.size() - 1;

			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glDrawElements(GL_POINTS, 1, GL_UNSIGNED_INT, BUFFER_OFFSET(ind * sizeof(GLuint)));
		}
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glDrawElements(GL_TRIANGLES, (Scena[k].indici.size() - 1) * sizeof(GLuint), GL_UNSIGNED_INT, 0);

		glBindVertexArray(0);
	}

	// Imposto il renderizzatore del testo

	RenderText(programId_text, Projection_text, Operazione, VAO_Text, VBO_Text, 10.0f, 650.0f, 0.5f, glm::vec3(1.0, 0.0f, 0.2f));

	RenderText(programId_text, Projection_text, stringa_asse, VAO_Text, VBO_Text, 10.0f, 700.0f, 0.5f, glm::vec3(1.0, 0.0f, 0.2f));

	RenderText(programId_text, Projection_text, "Oggetto selezionato", VAO_Text, VBO_Text, 10.0f, 600.0f, 0.5f, glm::vec3(1.0, 0.0f, 0.2f));

	if (selected_obj > -1)
		RenderText(programId_text, Projection_text, Scena[selected_obj].nome.c_str(), VAO_Text, VBO_Text, 10.0f, 550.0f, 0.5f, glm::vec3(1.0, 0.0f, 0.2f));

	glutSwapBuffers();
}

void update(int value)
{
	glutTimerFunc(1, update, 0);

	glutPostRedisplay();
}

int main(int argc, char *argv[])
{
	glutInit(&argc, argv);

	glutInitContextVersion(4, 0);
	glutInitContextProfile(GLUT_CORE_PROFILE);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA);

	// Inizializzo finestra per il rendering della scena 3d con tutti i suoi eventi le sue inizializzazioni e le sue impostazioni

	glutInitWindowSize(width, height);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("Scena 3D");
	glutDisplayFunc(drawScene);
	glutReshapeFunc(resize);

	glutMouseFunc(mouse);
	glutKeyboardFunc(keyboardPressedEvent);
	glutKeyboardUpFunc(keyboardReleasedEvent);
	glutPassiveMotionFunc(my_passive_mouse);
	glutMouseWheelFunc(mouseWheelCallback);
	glewExperimental = GL_TRUE;

	// Inizializzazioni
	glewInit();
	INIT_SHADER();
	INIT_VAO();
	INIT_CAMERA_PROJECTION();
	INIT_VAO_Text();
	Init_Freetype();
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_BLEND);
	glEnable(GL_ALPHA_TEST);
	glutTimerFunc(300, update, 0);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	// Chiedo che mi venga restituito l'identificativo della variabile uniform mat4 Projection (in vertex shader).
	// QUesto identificativo sar� poi utilizzato per il trasferimento della matrice Projection al Vertex Shader
	MatrixProj = glGetUniformLocation(programId, "Projection");
	// Chiedo che mi venga restituito l'identificativo della variabile uniform mat4 Model (in vertex shader)
	// QUesto identificativo sar� poi utilizzato per il trasferimento della matrice Model al Vertex Shader
	MatModel = glGetUniformLocation(programId, "Model");
	// Chiedo che mi venga restituito l'identificativo della variabile uniform mat4 View (in vertex shader)
	// QUesto identificativo sar� poi utilizzato per il trasferimento della matrice View al Vertex Shader
	MatView = glGetUniformLocation(programId, "View");

	glutMainLoop();
}