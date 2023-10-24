//
#include <iostream>
#include <fstream>
#include <string>
#include "ShaderMaker.h"
#include "lib.h"
#include <GL/glew.h>
#include <GL/freeglut.h>
#include "GestioneTesto.h"
#include <ft2build.h>
#include FT_FREETYPE_H
unsigned int VAO_Text, VBO_Text;

static unsigned int programId, programId_text;
#pragma warning(disable:4996)
#define  PI   3.14159265358979323846
int frame_animazione = 0;
float angolo = 0, scaling = 1;
float angoloSommergibile = 0.0, dx_f = 0.0, dy_f = 0.0;
mat4 Projection;
GLuint MatProj, MatModel, loctime, locres, locCol1, locCol2, locCol3, locSceltafs;
int nv_P;
bool Coll = FALSE;
// viewport size
int width = 1280;
int height = 720;
float w_update, h_update;
float dx_t = 0, dy_t = 0;
float Tens = 0.0;
float Bias = 0.0;
float Cont = 0.0;
bool drawBB = FALSE;

int pval = 140;
float* t;

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
	//Matrice di Modellazione: Traslazione*Rotazione*Scala
	mat4 Model;
	int sceltaFs;
	char render;
	vec4 corner_b_obj;
	vec4 corner_t_obj;
	vec4 corner_b;
	vec4 corner_t;
	bool alive;
}Forma;

Forma  Farf = {}, Cuore = {}, piano = {}, Proiettile = {};
Forma Curva = {}, Poligonale = {}, Derivata = {}, shape = {};
float posx_Proiettile, posy_Proiettile, angoloCannone = 0.0;

vector<Forma> Scena;

void INIT_VAO_Text(void)
{
	// configure VAO/VBO for texture quads
	// -----------------------------------
	glGenVertexArrays(1, &VAO_Text);
	glGenBuffers(1, &VBO_Text);
	glBindVertexArray(VAO_Text);
	glBindBuffer(GL_ARRAY_BUFFER, VBO_Text);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float) * 6 * 4, NULL, GL_DYNAMIC_DRAW);
	glEnableVertexAttribArray(0);
	glVertexAttribPointer(0, 4, GL_FLOAT, GL_FALSE, 4 * sizeof(float), 0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindVertexArray(0);
}

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

void costruisci_cuore(float cx, float cy, float raggiox, float raggioy, Forma* fig) {
	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t;

	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(255.0 / 255.0, 75.0 / 255.0, 0.0, 1.0));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		fig->vertici.push_back(vec3(cx + raggiox * (16 * pow(sin(t), 3)), cy + raggioy * ((13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t))), 0.0));
		//Colore
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
	float xmax = 0;
	float xmin = 0;

	float ymax = 0;
	float ymin = 0;

	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(150.0 / 255.0, 75.0 / 255.0, 0.0, 1.0));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		xx = cx + raggiox * (sin(t) * (exp(cos(t)) - 2 * cos(4 * t)) + pow(sin(t / 12), 5));
		yy = cy + raggioy * (cos(t) * (exp(cos(t)) - 2 * cos(4 * t)) + pow(sin(t / 12), 5));
		fig->vertici.push_back(vec3(xx, yy, 0.0));
		//Colore
		fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0)); //Nota che la quarta componente corrisponde alla trasparenza del colore
	}

	fig->nv = fig->vertici.size();
	fig->sceltaFs = 1;
	fig->render = GL_TRIANGLE_FAN;
	fig->nv = fig->vertici.size();

	//Calcolo di xmin, ymin, xmax, ymax

	for (i = 1; i < fig->nv; i++)
		if (fig->vertici[i].x <= xmin)
			xmin = fig->vertici[i].x;

	for (i = 1; i < fig->nv; i++)
		if (fig->vertici[i].x > xmax)
			xmax = fig->vertici[i].x;

	for (i = 1; i < fig->nv; i++)
		if (fig->vertici[i].y <= ymin)
			ymin = fig->vertici[i].y;

	for (i = 1; i < fig->nv; i++)
		if (fig->vertici[i].y > ymax)
			ymax = fig->vertici[i].y;

	//Aggiorno i valori del corner più in basso a sinistra (corner_b) e del corner più in alto a destra (conrner_t)

	fig->corner_b_obj = vec4(xmin, ymin, 0.0, 1.0);
	fig->corner_t_obj = vec4(xmax, ymax, 0.0, 1.0);
	//Aggiungo i vertici della spezzata per costruire il bounding box
	fig->vertici.push_back(vec3(xmin, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmin, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmin, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));

	//Aggiorno il numero dei vertici della figura
	fig->nv = fig->vertici.size();
}

////////////////////////////////////// Disegna geometria //////////////////////////////////////
//Per Curve di hermite
#define PHI0(t)  (2.0*t*t*t-3.0*t*t+1)
#define PHI1(t)  (t*t*t-2.0*t*t+t)
#define PSI0(t)  (-2.0*t*t*t+3.0*t*t)
#define PSI1(t)  (t*t*t-t*t)
float dx(int i, float* t, float Tens, float Bias, float Cont, Forma* Fig)
{
	if (i == 0)
		return  0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP[i + 1].x - Fig->CP[i].x) / (t[i + 1] - t[i]);
	if (i == Fig->CP.size() - 1)
		return  0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP[i].x - Fig->CP[i - 1].x) / (t[i] - t[i - 1]);

	if (i % 2 == 0)
		return  0.5 * (1 - Tens) * (1 + Bias) * (1 + Cont) * (Fig->CP.at(i).x - Fig->CP.at(i - 1).x) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).x - Fig->CP.at(i).x) / (t[i + 1] - t[i]);
	else
		return  0.5 * (1 - Tens) * (1 + Bias) * (1 - Cont) * (Fig->CP.at(i).x - Fig->CP.at(i - 1).x) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 + Cont) * (Fig->CP.at(i + 1).x - Fig->CP.at(i).x) / (t[i + 1] - t[i]);
}
float dy(int i, float* t, float Tens, float Bias, float Cont, Forma* Fig)
{
	if (i == 0)
		return 0.5 * (1.0 - Tens) * (1.0 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
	if (i == Fig->CP.size() - 1)
		return  0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]);

	if (i % 2 == 0)
		return  0.5 * (1 - Tens) * (1 + Bias) * (1 + Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
	else
		return  0.5 * (1 - Tens) * (1 + Bias) * (1 - Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 + Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
}

float DX(int i, float* t)
{
	//Nei vertici di controllo per i quali non sono stati modificati i parametri Tens, Bias, Cont il valore della derivata della componente x della curva   quello originale, altrimenti   quello che   stato modificato nella funzione
	//keyboardfunc  in seguito alla modifica dei valori Tens, Bias e Cont.

	if (Derivata.CP.at(i).x == 0)
		return dx(i, t, 0.0, 0.0, 0.0, &Poligonale);

	if (Derivata.CP.at(i).x != 0)
		return Derivata.CP.at(i).x;
}

float DY(int i, float* t)
{
	// Nei vertici di controllo per i quali non sono stati modificati i parametri Tens, Bias, Cont il valore della derivata della componente y della curva   quello originale, altrimenti   quello che   stato modificato nella funzione
		//keyboardfunc  in seguito alla modifica dei valori Tens, Bias e Cont.

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
	int is = 0; //indice dell'estremo sinistro dell'intervallo [t(i),t(i+1)] a cui il punto tg
	//appartiene

	Fig->vertici.clear();
	Fig->colors.clear();
	Fig->vertici.push_back(vec3(0.0, -0.2, 0.0));
	Fig->colors.push_back(color_bot);

	for (tg = 0; tg <= 1; tg += passotg)
	{
		if (tg > t[is + 1]) is++;

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
double  degtorad(double angle) {
	return angle * PI / 180;
}
void update_Sommergibile(int value)
{
	//Aggiorno l'angolo di fluttuazione del cannone
	frame_animazione += 4;
	if (frame_animazione >= 360) {
		frame_animazione -= 360;
	}
	angoloSommergibile = cos(degtorad(frame_animazione)) * 10;
	dx_t += 1.0;
	glutTimerFunc(250, update_Sommergibile, 0);
	glutPostRedisplay();
}

void costruisci_proiettile(float cx, float cy, float raggiox, float raggioy, Forma* fig) {
	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t;
	float xmax = 0;
	float xmin = 0;

	float ymax = 0;
	float ymin = 0;

	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(255.0 / 255.0, 75.0 / 255.0, 0.0, 1.0));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		fig->vertici.push_back(vec3(cx + raggiox * cos(t), cy + raggioy * sin(t), 0.0));
		//Colore
		fig->colors.push_back(vec4(1.0, 204.0 / 255.0, 0.0, 1.0));
	}

	fig->nv = fig->vertici.size();

	//Costruisco xmin,ymin, xmax,ymax peer identificare il Bounding Box del Proiettile
	for (i = 1; i < fig->nv; i++)
	{
		if (fig->vertici[i].x < xmin)
			xmin = fig->vertici[i].x;
	}

	for (i = 1; i < fig->nv; i++)
	{
		if (fig->vertici[i].x > xmax)
			xmax = fig->vertici[i].x;
	}
	for (i = 1; i < fig->nv; i++)
	{
		if (fig->vertici[i].y <= ymin)
			ymin = fig->vertici[i].y;
	}

	for (i = 1; i < fig->nv; i++)
	{
		if (fig->vertici[i].y > ymax)
			ymax = fig->vertici[i].y;
	}
	//Aggiorno i valori del corner più in basso a sinistra (corner_b) e del corner più in alto a destra (conrner_t)

	fig->corner_b_obj = vec4(xmin, ymin, 0.0, 1.0);
	fig->corner_t_obj = vec4(xmax, ymax, 0.0, 1.0);
	//Aggiungo i vertici della spezzata per costruire il bounding box
	fig->vertici.push_back(vec3(xmin, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmin, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmin, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));

	//Aggiorno il numero dei vertici della figura
	fig->nv = fig->vertici.size();
	fig->sceltaFs = 1;
	fig->render = GL_TRIANGLE_FAN;
}
void updateProiettile(int value)
{
	//Ascissa del proiettile durante lo sparo
	posx_Proiettile = 0;
	//Ordinata del proettile durante lo sparo
	posy_Proiettile += 4;

	//L'animazione deve avvenire finchè  l'ordinata del proiettile raggiunge un certo valore fissato
	if (posy_Proiettile <= 800)
		glutTimerFunc(5, updateProiettile, 0);
	else
		posy_Proiettile = 0;
	//printf("posx posy %f %f \n", posx_Proiettile, posy_Proiettile);
	glutPostRedisplay();
}

void INIT_SHADER(void)
{
	GLenum ErrorCheckValue = glGetError();

	char* vertexShader = (char*)"vertexShader_M.glsl";
	char* fragmentShader = (char*)"fragmentShader_Onde_Nuvole.glsl";

	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);

	//Generazione del program shader per la gestione del testo
	vertexShader = (char*)"VertexShader_Text.glsl";
	fragmentShader = (char*)"FragmentShader_Text.glsl";

	programId_text = ShaderMaker::createProgram(vertexShader, fragmentShader);
}
bool checkCollision(Forma obj1, Forma obj2) {
	bool colx = obj1.corner_b.x <= obj2.corner_t.x && obj1.corner_t.x >= obj2.corner_b.x;
	bool coly = obj1.corner_b.y <= obj2.corner_t.y && obj1.corner_t.y >= obj2.corner_b.y;
	return colx && coly;
}
void crea_punti_forma_da_file()
{
	int i;
	struct Dati {
		float x;
		float y;
		float z;
	};

	FILE* file = fopen("sommergibile.txt", "r");
	if (file == NULL) {
		perror("Impossibile aprire il file");
	}

	// Vettore per memorizzare i dati
	struct Dati dati[1000]; // Supponiamo che ci siano al massimo 100 righe nel file

	int riga = 0;
	while (fscanf(file, "%f %f %f", &dati[riga].x, &dati[riga].y, &dati[riga].z) == 3) {
		// Incrementa l'indice della riga
		riga++;

		// Puoi aggiungere un controllo qui per evitare il superamento dell'array dati
		if (riga >= 1000) {
			printf("Troppe righe nel file. L'array dati   stato completamente riempito.\n");
			break;
		}
	}

	// Chiudi il file
	fclose(file);

	for (int i = 0; i < riga; i++) {
		Curva.CP.push_back(vec3(dati[i].x, dati[i].y, dati[i].z));
		Derivata.CP.push_back(vec3(0.0, 0.0, 0.0));
	}
}
void reshape(int w, int h)
{
	Projection = ortho(0.0f, (float)width, 0.0f, (float)height);

	float AspectRatio_mondo = (float)(width) / (float)(height); //Rapporto larghezza altezza di tutto ci  che   nel mondo
	//Se l'aspect ratio del mondo   diversa da quella della finestra devo mappare in modo diverso
	//per evitare distorsioni del disegno
	if (AspectRatio_mondo > w / h)   //Se ridimensioniamo la larghezza della Viewport
	{
		glViewport(0, 0, w, w / AspectRatio_mondo);
		w_update = (float)w;
		h_update = w / AspectRatio_mondo;
	}
	else {  //Se ridimensioniamo la larghezza della viewport oppure se l'aspect ratio tra la finestra del mondo
		//e la finestra sullo schermo sono uguali
		glViewport(0, 0, h * AspectRatio_mondo, h);
		w_update = h * AspectRatio_mondo;
		h_update = (float)h;
	}
}

void myKeyboard(unsigned char key, int x, int y)
{
	{
		switch (key)
		{
		case ' ':
			updateProiettile(0);
			break;

		case 'a':
			dx_t -= 1;
			break;

		case 'd':
			dx_t += 1;
			break;

		case 'q':
			angolo += 0.1;
			break;
		case 'e':
			angolo -= 0.1;
			break;

		case 'b':
			drawBB = TRUE;
			break;
		default:
			break;
		}
	}
	glutPostRedisplay();
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
	Farf.render = GL_TRIANGLE_FAN;
	Farf.alive = TRUE;
	Scena.push_back(Farf);

	//Sommergibile
	vec4 color_top = vec4(0.0, 0.0, 0.0, 1.0);
	vec4 color_bot = vec4(126.0 / 255.0, 135.0 / 255.0, 146.0 / 255.0, 1.0);
	crea_punti_forma_da_file();
	costruisci_formaHermite(color_top, color_bot, &Curva);
	crea_VAO_Vector(&Curva);
	Curva.render = GL_TRIANGLE_FAN;
	Curva.sceltaFs = 1;
	Scena.push_back(Curva);

	Proiettile.nTriangles = 180;
	costruisci_proiettile(0.0, 0.0, 1.0, 1.0, &Proiettile);
	crea_VAO_Vector(&Proiettile);
	Proiettile.render = GL_TRIANGLE_FAN;
	Proiettile.alive = TRUE;
	Scena.push_back(Proiettile);

	//Projection   la matrice che mappa la finestra sul mondo nelle coordinate NDC (cormalizzate di device che trariano tra (-1,1) ed (1,1)

	Projection = ortho(0.0f, float(width), 0.0f, float(height));

	//Viene ricavata la location della variabile Uniform Projection presente nel fragment shader
	MatProj = glGetUniformLocation(programId, "Projection");
	//Viene ricavata la location della variabile Uniform Model presente nel fragment shader
	MatModel = glGetUniformLocation(programId, "Model");
	loctime = glGetUniformLocation(programId, "time");
	locres = glGetUniformLocation(programId, "resolution");

	locSceltafs = glGetUniformLocation(programId, "scelta_fs");

	glViewport(0, 0, width, height);  //Mappa le coordinate normlaizzate di device nella finestra sullo schermo
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
void keyboardReleasedEvent(unsigned char key, int x, int y)
{
	{
		switch (key)
		{
		case 'b':
			drawBB = FALSE;
			break;
		default:
			break;
		}
	}
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

	//Disegno lo sfondo e la farfalla
	for (k = 0; k < 1; k++)
	{
		if (k == 0)
		{
			Scena[k].Model = mat4(1.0);
			Scena[k].Model = translate(Scena[k].Model, vec3(0.5, 0.5, 0.0));
			Scena[k].Model = scale(Scena[k].Model, vec3((float)width, (float)height, 1.0));
		}
		glUniformMatrix4fv(MatProj, 1, GL_FALSE, value_ptr(Projection));  //comunica i valori della variabile uniform Projection al vertex shader
		glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[k].Model)); //comunica i valori della variabile uniform Model  al vertex shader
		glUniform1i(locSceltafs, Scena[k].sceltaFs);
		glBindVertexArray(Scena[k].VAO);
		glDrawArrays(Scena[k].render, 0, Scena[k].nv);
		glBindVertexArray(0);
	}
	//Matrice di Modellazione del sommergibile
	Scena[2].Model = mat4(1.0);
	Scena[2].Model = translate(Scena[2].Model, vec3(600.0 + dx_t, 300.0 + dy_t, 0.0));
	Scena[2].Model = rotate(Scena[2].Model, radians(angolo), vec3(0.0f, 0.0f, 1.0f));
	Scena[2].Model = rotate(Scena[2].Model, radians(angoloSommergibile), vec3(0.0f, 0.0f, 1.0f));

	/*Matrice di modellazione del proiettile*/

	//La matrice di Modellazione del proiettile si ottiene a partire da quella del sommergibile
	Scena[3].Model = translate(Scena[2].Model, vec3(posx_Proiettile, 60 + posy_Proiettile, 0.0));
	Scena[3].Model = scale(Scena[3].Model, vec3(20.5, 20.5, 1.0));
	//std::cout << glm::to_string(Scena[2].Model) << std::endl;
	//Il Bounding box del proiettile viene portato in coordinate del mondo, premoltiplicando le sue coordinate per la matrice di Modellazione
	Scena[3].corner_b = Scena[3].corner_b_obj;
	Scena[3].corner_t = Scena[3].corner_t_obj;
	Scena[3].corner_b = Scena[3].Model * Scena[3].corner_b;
	//printf("Proiettile \n");
	//std::cout << glm::to_string(Scena[2].corner_b) << std::endl;
	Scena[3].corner_t = Scena[3].Model * Scena[3].corner_t;
	//std::cout << glm::to_string(Scena[2].corner_t) << std::endl;
	 /*Update Scala Matrice di modellazione del cannone */

	//Viene aggiunta una scalatura alla matrice di modellazione del sommergibile
	Scena[2].Model = scale(Scena[2].Model, vec3(250.0, 250.0, 1.0));

	//Disegno Proettile

	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[3].Model));
	glUniform1i(locSceltafs, Scena[3].sceltaFs);
	glBindVertexArray(Scena[3].VAO);
	glDrawArrays(Scena[3].render, 0, Scena[3].nv - 6);

	if (drawBB == TRUE)
	{
		//Disegno Bounding Box
		glDrawArrays(GL_LINE_STRIP, Scena[3].nv - 6, 6);
		glBindVertexArray(0);
	}

	//Disegno Sommergibile

	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[2].Model));
	glUniform1i(locSceltafs, Scena[2].sceltaFs);
	glBindVertexArray(Scena[2].VAO);
	glDrawArrays(Scena[2].render, 0, Scena[2].nv);

	glBindVertexArray(0);

	/*Matrice di modellazione farfalla */
	Scena[1].Model = mat4(1.0);
	Scena[1].Model = translate(Scena[1].Model, vec3(900.0 + 50 * cos(dx_f), 500.0 + 50 * sin(dx_f), 0.0));
	Scena[1].Model = scale(Scena[1].Model, vec3(80.5, 80.5, 1.0));

	//Il Bounding box della farfalla viene portato in coordinate del mondo, premoltiplicando le sue coordinate per la matrice di Modellazione
	Scena[1].corner_b = Scena[1].corner_b_obj;
	Scena[1].corner_t = Scena[1].corner_t_obj;
	Scena[1].corner_b = Scena[1].Model * Scena[1].corner_b;
	Scena[1].corner_t = Scena[1].Model * Scena[1].corner_t;

	Coll = checkCollision(Scena[1], Scena[3]);
	std::cout << Coll << std::endl;
	if (Coll == TRUE) {
		Scena[1].alive = FALSE; 	 	  //Se collisione è TRUE, metto a FALSE il flag alive di Farfalla, che ha l'effetto di non farla disegnare più
	}

	//Disegno la farfalla fino a quando non sia avvenuta la prima collisione con la palla del cannone

	if (Scena[1].alive == TRUE)
	{
		glUniform1i(locSceltafs, Scena[1].sceltaFs);
		glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[1].Model));
		glBindVertexArray(Scena[1].VAO);
		glDrawArrays(GL_TRIANGLE_FAN, 0, Scena[1].nv - 6);
		if (drawBB == TRUE)
			//		//Disegno Bounding Box
			glDrawArrays(GL_LINE_STRIP, Scena[1].nv - 6, 6);
		glBindVertexArray(0);
	}
	else
		RenderText(programId_text, Projection, "GAME OVER", VAO_Text, VBO_Text, 100.0f, 600.0f, 1.0f, glm::vec3(1.0, 1.0f, 0.2f));
	glUseProgram(programId);

	glutSwapBuffers();
}

void update_f(int value)
{
	dx_f = dx_f + 0.01;

	glutTimerFunc(25, update_f, 0);
	glutPostRedisplay();
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
	glutReshapeFunc(reshape);
	glutKeyboardFunc(myKeyboard);
	glutKeyboardUpFunc(keyboardReleasedEvent);
	glutTimerFunc(250, update_Sommergibile, 0); //gestione evento oziosità : viene richiamata la funzione updateScale ogni 250 millisecondi che aggiorna i parametri di scalatura e forza il ridisegno
	glutTimerFunc(25, update_f, 0);  //Aggiornamento Farfalla

	glewExperimental = GL_TRUE;
	glewInit();
	INIT_SHADER();
	INIT_VAO();
	//Init VAO per la gestione del disegno

	INIT_VAO_Text();
	Init_Freetype();

	//Per gestire i colori con trasparenza: mescola i colori di geometrie che si sovrappongono
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glutMainLoop();
}