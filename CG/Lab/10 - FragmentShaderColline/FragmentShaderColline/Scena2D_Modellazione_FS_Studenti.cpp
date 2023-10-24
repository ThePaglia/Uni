//

#include <iostream>
#include <fstream>
#include <string>

#include "ShaderMaker.h"
#include "lib.h"

#include <GL/glew.h>
#include <GL/freeglut.h>
static unsigned int programId;
#pragma warning(disable : 4996)
#define PI 3.14159265358979323846

float angolo = 0, scaling = 1;

mat4 Projection;
GLuint MatProj, MatModel, loctime, locres, locCol1, locCol2, locCol3, locSceltafs;
int nv_P;
// viewport size
int width = 1280;
int height = 720;
float w_update, h_update;
float Tens = 0.0;
float Bias = 0.0;
float Cont = 0.0;

int pval = 140;
float* t;

typedef struct
{
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
	// Matrice di Modellazione: Traslazione*Rotazione*Scala
	mat4 Model;
	int sceltaFs; // scelta del tipo di Fragmente Shader
	char render;  // Modalit� di rendering
} Forma;

Forma Farf = {}, Cuore = {}, piano = {};
Forma Curva = {}, Poligonale = {}, Derivata = {}, shape = {};

vector<Forma> Scena;

void crea_VAO_Vector(Forma* fig)
{
	glGenVertexArrays(1, &fig->VAO);
	glBindVertexArray(fig->VAO);
	// Genero , rendo attivo, riempio il VBO della geometria dei vertici
	glGenBuffers(1, &fig->VBO_G);
	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_G);
	glBufferData(GL_ARRAY_BUFFER, fig->vertici.size() * sizeof(vec3), fig->vertici.data(), GL_STATIC_DRAW);

	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(0);

	// Genero , rendo attivo, riempio il VBO dei colori
	glGenBuffers(1, &fig->VBO_C);
	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_C);
	glBufferData(GL_ARRAY_BUFFER, fig->colors.size() * sizeof(vec4), fig->colors.data(), GL_STATIC_DRAW);
	// Adesso carico il VBO dei colori nel layer 2
	glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(1);
}

void costruisci_piano(Forma* fig)
{
	int i;
	fig->vertici.push_back(vec3(-1.0, -1.0, 0.0));
	fig->vertici.push_back(vec3(1.0, -1.0, 0.0));
	fig->vertici.push_back(vec3(-1.0, 1.0, 0.0));
	fig->vertici.push_back(vec3(1.0, 1.0, 0.0));
	for (i = 0; i < fig->vertici.size(); i++)
		fig->colors.push_back(vec4(0.13, 0.44, 0.70, 1.0));
	fig->nv = fig->vertici.size();
	fig->sceltaFs = 0;
	fig->render = GL_TRIANGLE_STRIP;
}

void costruisci_cuore(float cx, float cy, float raggiox, float raggioy, Forma* fig)
{
	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t;

	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(255.0 / 255.0, 75.0 / 255.0, 0.0, 1.0));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		fig->vertici.push_back(vec3(cx + raggiox * (16 * pow(sin(t), 3)), cy + raggioy * ((13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t))), 0.0));
		// Colore
		fig->colors.push_back(vec4(1.0, 204.0 / 255.0, 0.0, 1.0));
	}
	fig->nv = fig->vertici.size();
	fig->sceltaFs = 1;
	fig->render = GL_TRIANGLE_FAN;
}
void costruisci_farfalla(float cx, float cy, float raggiox, float raggioy, Forma* fig)
{
	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t, xx, yy;

	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(150.0 / 255.0, 75.0 / 255.0, 0.0, 1.0));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		xx = cx + raggiox * (sin(t) * (exp(cos(t)) - 2 * cos(4 * t)) + pow(sin(t / 12), 5));
		yy = cy + raggioy * (cos(t) * (exp(cos(t)) - 2 * cos(4 * t)) + pow(sin(t / 12), 5));
		fig->vertici.push_back(vec3(xx, yy, 0.0));
		// Colore
		fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0)); // Nota che la quarta componente corrisponde alla trasparenza del colore
	}

	fig->nv = fig->vertici.size();
	fig->sceltaFs = 1;
	fig->render = GL_TRIANGLE_FAN;
}

////////////////////////////////////// Disegna geometria //////////////////////////////////////
// Per Curve di hermite
#define PHI0(t) (2.0 * t * t * t - 3.0 * t * t + 1)
#define PHI1(t) (t * t * t - 2.0 * t * t + t)
#define PSI0(t) (-2.0 * t * t * t + 3.0 * t * t)
#define PSI1(t) (t * t * t - t * t)
float dx(int i, float* t, float Tens, float Bias, float Cont, Forma* Fig)
{
	if (i == 0)
		return 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP[i + 1].x - Fig->CP[i].x) / (t[i + 1] - t[i]);
	if (i == Fig->CP.size() - 1)
		return 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP[i].x - Fig->CP[i - 1].x) / (t[i] - t[i - 1]);

	if (i % 2 == 0)
		return 0.5 * (1 - Tens) * (1 + Bias) * (1 + Cont) * (Fig->CP.at(i).x - Fig->CP.at(i - 1).x) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).x - Fig->CP.at(i).x) / (t[i + 1] - t[i]);
	else
		return 0.5 * (1 - Tens) * (1 + Bias) * (1 - Cont) * (Fig->CP.at(i).x - Fig->CP.at(i - 1).x) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 + Cont) * (Fig->CP.at(i + 1).x - Fig->CP.at(i).x) / (t[i + 1] - t[i]);
}
float dy(int i, float* t, float Tens, float Bias, float Cont, Forma* Fig)
{
	if (i == 0)
		return 0.5 * (1.0 - Tens) * (1.0 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
	if (i == Fig->CP.size() - 1)
		return 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]);

	if (i % 2 == 0)
		return 0.5 * (1 - Tens) * (1 + Bias) * (1 + Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
	else
		return 0.5 * (1 - Tens) * (1 + Bias) * (1 - Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 + Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
}

float DX(int i, float* t)
{
	// Nei vertici di controllo per i quali non sono stati modificati i parametri Tens, Bias, Cont il valore della derivata della componente x della curva   quello originale, altrimenti   quello che   stato modificato nella funzione
	// keyboardfunc  in seguito alla modifica dei valori Tens, Bias e Cont.

	if (Derivata.CP.at(i).x == 0)
		return dx(i, t, 0.0, 0.0, 0.0, &Poligonale);

	if (Derivata.CP.at(i).x != 0)
		return Derivata.CP.at(i).x;
}

float DY(int i, float* t)
{
	// Nei vertici di controllo per i quali non sono stati modificati i parametri Tens, Bias, Cont il valore della derivata della componente y della curva   quello originale, altrimenti   quello che   stato modificato nella funzione
	// keyboardfunc  in seguito alla modifica dei valori Tens, Bias e Cont.

	if (Derivata.CP.at(i).y == 0)
		return dy(i, t, 0.0, 0.0, 0.0, &Poligonale);

	if (Derivata.CP.at(i).y != 0)
		return Derivata.CP.at(i).y;
}

void InterpolazioneHermite(float* t, Forma* Fig, vec4 color_top, vec4 color_bot)
{
	float p_t = 0, p_b = 0, p_c = 0, x, y;
	float passotg = 1.0 / (float)(pval - 1);

	float tg = 0, tgmapp, ampiezza;
	int i = 0;
	int is = 0; // indice dell'estremo sinistro dell'intervallo [t(i),t(i+1)] a cui il punto tg
	// appartiene

	Fig->vertici.clear();
	Fig->colors.clear();
	Fig->vertici.push_back(vec3(-0.1, -0.7, 0.0));
	Fig->colors.push_back(color_bot);

	for (tg = 0; tg <= 1; tg += passotg)
	{
		if (tg > t[is + 1])
			is++;

		ampiezza = (t[is + 1] - t[is]);
		tgmapp = (tg - t[is]) / ampiezza;

		x = Fig->CP[is].x * PHI0(tgmapp) + DX(is, t) * PHI1(tgmapp) * ampiezza + Fig->CP[is + 1].x * PSI0(tgmapp) + DX(is + 1, t) * PSI1(tgmapp) * ampiezza;
		y = Fig->CP[is].y * PHI0(tgmapp) + DY(is, t) * PHI1(tgmapp) * ampiezza + Fig->CP[is + 1].y * PSI0(tgmapp) + DY(is + 1, t) * PSI1(tgmapp) * ampiezza;

		Fig->vertici.push_back(vec3(x, y, 0.0));
		Fig->colors.push_back(color_top);
	}
}
void costruisci_formaHermite(vec4 color_top, vec4 color_bot, Forma* forma)
{
	Poligonale.CP = Curva.CP;
	Poligonale.colCP = Curva.colCP;

	if (Poligonale.CP.size() > 1)
	{
		t = new float[Curva.CP.size()];
		int i;
		float step = 1.0 / (float)(Curva.CP.size() - 1);

		for (i = 0; i < Curva.CP.size(); i++)
			t[i] = (float)i * step;

		InterpolazioneHermite(t, &Curva, color_top, color_bot);

		forma->nv = Curva.vertici.size();
	}
}

void INIT_SHADER(void)
{
	GLenum ErrorCheckValue = glGetError();

	char* vertexShader = (char*)"vertexShader_M.glsl";
	char* fragmentShader = (char*)"fragmentShader_M.glsl";

	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);
}
void crea_punti_forma_da_file()
{
	int i;
	struct Dati
	{
		float x;
		float y;
		float z;
	};

	FILE* file = fopen("stivale.txt", "r");
	if (file == NULL)
	{
		perror("Impossibile aprire il file");
	}

	// Vettore per memorizzare i dati
	struct Dati dati[1000]; // Supponiamo che ci siano al massimo 100 righe nel file

	int riga = 0;
	while (fscanf(file, "%f %f %f", &dati[riga].x, &dati[riga].y, &dati[riga].z) == 3)
	{
		// Incrementa l'indice della riga
		riga++;

		// Puoi aggiungere un controllo qui per evitare il superamento dell'array dati
		if (riga >= 1000)
		{
			printf("Troppe righe nel file. L'array dati   stato completamente riempito.\n");
			break;
		}
	}

	// Chiudi il file
	fclose(file);

	for (int i = 0; i < riga; i++)
	{
		Curva.CP.push_back(vec3(dati[i].x, dati[i].y, dati[i].z));
		Derivata.CP.push_back(vec3(0.0, 0.0, 0.0));
	}
}
void reshape(int w, int h)
{
	Projection = ortho(0.0f, (float)width, 0.0f, (float)height);

	float AspectRatio_mondo = (float)(width) / (float)(height); // Rapporto larghezza altezza di tutto ci  che   nel mondo
	// Se l'aspect ratio del mondo   diversa da quella della finestra devo mappare in modo diverso
	// per evitare distorsioni del disegno
	if (AspectRatio_mondo > w / h) // Se ridimensioniamo la larghezza della Viewport
	{
		glViewport(0, 0, w, w / AspectRatio_mondo);
		w_update = (float)w;
		h_update = w / AspectRatio_mondo;
	}
	else
	{ // Se ridimensioniamo la larghezza della viewport oppure se l'aspect ratio tra la finestra del mondo
		// e la finestra sullo schermo sono uguali
		glViewport(0, 0, h * AspectRatio_mondo, h);
		w_update = h * AspectRatio_mondo;
		h_update = (float)h;
	}
}
void INIT_VAO(void)
{
	costruisci_piano(&piano);
	crea_VAO_Vector(&piano);
	Scena.push_back(piano);

	Farf.nTriangles = 180;
	Farf.s = 0.2;
	costruisci_farfalla(0.0, 0.0, Farf.s, Farf.s, &Farf);
	crea_VAO_Vector(&Farf);
	Scena.push_back(Farf);

	Cuore.nTriangles = 180;
	Cuore.s = 0.1;
	costruisci_cuore(0.0, 0.0, Cuore.s, Cuore.s, &Cuore);
	crea_VAO_Vector(&Cuore);
	Scena.push_back(Cuore);

	vec4 color_top = vec4(0.58, 0.29, 0.0, 1.0);
	vec4 color_bot = vec4(0.5, 0.2, 0.0, 1.0);
	crea_punti_forma_da_file();
	costruisci_formaHermite(color_top, color_bot, &Curva);
	crea_VAO_Vector(&Curva);
	Curva.render = GL_TRIANGLE_FAN;
	Curva.sceltaFs = 1;

	// Projection   la matrice che mappa la finestra sul mondo nelle coordinate NDC (cormalizzate di device che trariano tra (-1,1) ed (1,1)

	Projection = ortho(0.0f, float(width), 0.0f, float(height));

	// Viene ricavata la location della variabile Uniform Projection presente nel fragment shader
	MatProj = glGetUniformLocation(programId, "Projection");
	// Viene ricavata la location della variabile Uniform Model presente nel fragment shader
	MatModel = glGetUniformLocation(programId, "Model");
	loctime = glGetUniformLocation(programId, "time");
	locres = glGetUniformLocation(programId, "resolution");
	locCol1 = glGetUniformLocation(programId, "mountainColor1"); // Colore della prima montagna
	locCol2 = glGetUniformLocation(programId, "mountainColor1"); // Colore della seconda montagna
	locCol3 = glGetUniformLocation(programId, "skyColor");
	; // Colore del cielo
	locSceltafs = glGetUniformLocation(programId, "scelta_fs");

	glViewport(0, 0, width, height); // Mappa le coordinate normlaizzate di device nella finestra sullo schermo
}

void updateScale(int value)
{
	angolo += .1;
	scaling = scaling * 1.1;

	if (scaling > 2)
		scaling = 1.0;

	glutTimerFunc(100, updateScale, 0);

	glutPostRedisplay();
}

void drawScene(void)
{
	int k;

	glClearColor(0.0, 0.0, 0.5, 1.0);
	glClear(GL_COLOR_BUFFER_BIT);

	float time = glutGet(GLUT_ELAPSED_TIME) / 1000.0f;

	vec2 resolution = vec2(w_update, h_update);

	glUniform1f(loctime, time);
	glUniform2f(locres, resolution.x, resolution.y);
	vec3 col1(0.545, 0.270, 0.074);
	glUniform3f(locCol1, col1.r, col1.g, col1.b);
	vec3 col2(0.0, 0.9, 0.0);
	glUniform3f(locCol2, col2.r, col2.g, col2.b);
	vec3 col3(0.529, 0.808, 0.980);
	glUniform3f(locCol3, col3.r, col3.g, col3.b);

	for (k = 0; k < Scena.size(); k++)
	{
		if (k == 0)
		{
			Scena[k].Model = mat4(1.0);
			Scena[k].Model = translate(Scena[k].Model, vec3(0.5, 0.5, 0.0));
			Scena[k].Model = scale(Scena[k].Model, vec3((float)width, (float)height, 1.0));
		}

		if (k == 1)
		{
			Scena[k].Model = mat4(1.0);
			Scena[k].Model = translate(Scena[k].Model, vec3(1000.0, 400.0, 0.0));
			Scena[k].Model = scale(Scena[k].Model, vec3(200.0 * scaling, 200.0 * scaling, 1.0));
		}
		if (k == 2)
		{
			Scena[k].Model = mat4(1.0);
			Scena[k].Model = translate(Scena[k].Model, vec3(400.0, 400.0, 0.0));
			Scena[k].Model = scale(Scena[k].Model, vec3(100.0 * scaling, 100.0 * scaling, 1.0));
		}
		glUniformMatrix4fv(MatProj, 1, GL_FALSE, value_ptr(Projection));	  // comunica i valori della variabile uniform Projection al vertex shader
		glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[k].Model)); // comunica i valori della variabile uniform Model  al vertex shader
		glUniform1i(locSceltafs, Scena[k].sceltaFs);
		glBindVertexArray(Scena[k].VAO);
		glDrawArrays(Scena[k].render, 0, Scena[k].nv);
		glBindVertexArray(0);
	}
	Curva.Model = mat4(1.0);
	Curva.Model = translate(Curva.Model, vec3(700, 400, 0.0));
	Curva.Model = scale(Curva.Model, vec3(300, 300, 0.0));
	// glUniformMatrix4fv(MatProj, 1, GL_FALSE, value_ptr(Projection));  //comunica i valori della variabile uniform Projection al vertex shader
	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Curva.Model));
	glUniform1i(locSceltafs, Curva.sceltaFs);

	glBindVertexArray(Curva.VAO);
	glDrawArrays(Curva.render, 0, Curva.nv);
	glBindVertexArray(0);
	glutSwapBuffers();
}

void update(int value)
{
	glutTimerFunc(250, update, 0);
}

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);

	glutInitContextVersion(4, 0);
	glutInitContextProfile(GLUT_CORE_PROFILE);

	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA);

	glutInitWindowSize(width, height);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("Modellazione Scena 2D");
	glutDisplayFunc(drawScene);
	glutReshapeFunc(reshape); // EVENTO RESHAPE
	glutTimerFunc(250, updateScale, 0);

	// glutTimerFunc(250, updateScale, 0);  //gestione evento oziosit : viene richiamata la funzione updateScale ogni 250 millisecondi che aggiorna i parametri di scalatura e forza il ridisegno
	glewExperimental = GL_TRUE;
	glewInit();
	INIT_SHADER();
	INIT_VAO();

	// Per gestire i colori con trasparenza: mescola i colori di geometrie che si sovrappongono
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glutMainLoop();
}