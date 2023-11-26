//Editor di curve con il quale posso disegnare una curva e esportarla in un file di testo mycurve.txt
//Per visualizzarlo mettere Scena2D_Modellazione _Curve_Studenti e togliere EditorCurve

#include <iostream>
#include <fstream>
#include "ShaderMaker.h"
#include "Lib.h"

// viewport size
int width = 600;
int height = 600;
int selected_obj = -1;

// Inizializzazione Menu
int submenu_Opzioni_I, menu_id, submenu_SplineC1Interp, submenu_Bezier;

// Inizializzazioni
int mod_par_der = 0;  //(0) 1 : (non) si intende agire sui parametri T,B,C nel calcolo numerico della derivata nel vertice di controllo selezionato;
int visualizzaTg = 0; //(0) 1 : (non) si intende visualizzare graficamente la tangente nei vertici di controllo
int visualizzaPC = 0; //(0) 1 : (non) si intende visualizzare graficamente il poligono di controllo

// Inizializzazione parametri Tes, Bias, Cont per la modifica delle derivate agli estremi
float Tens = 0.0, Bias = 0.0, Cont = 0.0; // Questa inizializzazione 'rappresenta le derivate come semplice rapporto incrementale

int metodo = 1; // Metodo=1 --> interpolazione curve di Hermite;  Metodo=2 --> approssimazione di forma curva di Bezier

int M_I = 0;
static unsigned int programId;
mat4 Projection;
GLuint MatProj, MatModel;
unsigned int lsceltavs, lsceltafs, loc_time, loc_res, loc_mouse;
vec2 res, mouse;

int pval = 140;
float* t;
#define PI 3.14159265358979323846
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
	vector<vec4> colCP;
	// Numero massimo di punti in cui viene disegnata la curva
	int nv;
	// Numero massimo di punti di interpolazione
	int ncp;
	// Matrice di Modellazione: Traslazione*Rotazione*Scala
	mat4 Model;
} Forma;

Forma Curva, Poligonale, Derivata, tangenti, assi;

void crea_VAO_Vector(Forma* fig)
{
	glGenVertexArrays(1, &fig->VAO);
	glBindVertexArray(fig->VAO);
	// Genero , rendo attivo, riempio il VBO della geometria dei vertici
	glGenBuffers(1, &fig->VBO_G);
	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_G);
	glBufferData(GL_ARRAY_BUFFER, fig->nv * sizeof(vec3), NULL, GL_DYNAMIC_DRAW);

	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(0);

	// Genero , rendo attivo, riempio il VBO dei colori
	glGenBuffers(1, &fig->VBO_C);
	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_C);
	glBufferData(GL_ARRAY_BUFFER, fig->nv * sizeof(vec4), NULL, GL_DYNAMIC_DRAW);
	// Adesso carico il VBO dei colori nel layer 2
	glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(1);
}
void update_VAO_Vector(Forma* fig)
{
	glBindVertexArray(fig->VAO);
	// Genero , rendo attivo, riempio il VBO della geometria dei vertici

	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_G);
	glBufferSubData(GL_ARRAY_BUFFER, 0, fig->nv * sizeof(vec3), fig->vertici.data());

	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(0);

	// Genero , rendo attivo, riempio il VBO dei colori

	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_C);
	glBufferSubData(GL_ARRAY_BUFFER, 0, fig->nv * sizeof(vec4), fig->colors.data());
	// Adesso carico il VBO dei colori nel layer 2
	glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(1);
}

void crea_VAO_CP(Forma* fig)
{
	glGenVertexArrays(1, &fig->VAO);
	glBindVertexArray(fig->VAO);
	// Genero , rendo attivo, riempio il VBO della geometria dei vertici di COntrollo
	glGenBuffers(1, &fig->VBO_G);
	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_G);
	glBufferData(GL_ARRAY_BUFFER, fig->ncp * sizeof(vec3), NULL, GL_DYNAMIC_DRAW);

	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(0);

	// Genero , rendo attivo, riempio il VBO dei colori nei vertici di controllo
	glGenBuffers(1, &fig->VBO_C);
	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_C);
	glBufferData(GL_ARRAY_BUFFER, fig->ncp * sizeof(vec4), NULL, GL_DYNAMIC_DRAW);
	// Adesso carico il VBO dei colori nel layer 2
	glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(1);
}
void update_VAO_CP(Forma* fig)
{
	glBindVertexArray(fig->VAO);
	// Genero , rendo attivo, riempio il VBO della geometria dei vertici di COntrollo

	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_G);
	glBufferSubData(GL_ARRAY_BUFFER, 0, fig->CP.size() * sizeof(vec3), fig->CP.data());

	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(0);

	// Genero , rendo attivo, riempio il VBO dei colori nei vertici di controllo
	glBindBuffer(GL_ARRAY_BUFFER, fig->VBO_C);
	glBufferSubData(GL_ARRAY_BUFFER, 0, fig->colCP.size() * sizeof(vec4), fig->colCP.data());
	// Adesso carico il VBO dei colori nel layer 2
	glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 0, (void*)0);
	glEnableVertexAttribArray(1);
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
	// Nei vertici di controllo per i quali non sono stati modificati i parametri Tens, Bias, Cont il valore della derivata della componente x della curva � quello originale, altrimenti � quello che � stato modificato nella funzione
	// keyboardfunc  in seguito alla modifica dei valori Tens, Bias e Cont.

	if (Derivata.CP.at(i).x == 0)
		return dx(i, t, 0.0, 0.0, 0.0, &Poligonale);

	if (Derivata.CP.at(i).x != 0)
		return Derivata.CP.at(i).x;
}

float DY(int i, float* t)
{
	// Nei vertici di controllo per i quali non sono stati modificati i parametri Tens, Bias, Cont il valore della derivata della componente y della curva � quello originale, altrimenti � quello che � stato modificato nella funzione
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
	tangenti.CP.clear();

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

	if (visualizzaTg == 1)
	{
		for (size_t i = 0; i < Fig->CP.size(); i++)
		{
			tangenti.CP.push_back(vec3(Fig->CP[i].x + DX(i, t) / 50, Fig->CP[i].y + DY(i, t) / 50, 0.0));
			tangenti.colCP.push_back(vec4(0.0, 0.0, 1.0, 1.0));
			tangenti.CP.push_back(vec3(Fig->CP[i].x - DX(i, t) / 50, Fig->CP[i].y - DY(i, t) / 50, 0.0));
			tangenti.colCP.push_back(vec4(0.0, 0.0, 1.0, 1.0));
		}
	}
}

void mouseMotion(int x, int y)
{
	float c = -1.0;
	float d = 1.0;
	float mousex = (float)x;
	float mousey = (float)height - y;

	mousex = c + mousex * (d - c) / (float)width;
	mousey = c + mousey * (d - c) / (float)height;

	if (M_I == 1)
	{
		if (selected_obj >= 0)
		{
			printf("Ho selezionato il Punto selezionato %d \n", selected_obj);
			Curva.CP[selected_obj] = vec3(mousex, mousey, 0.0);
			Curva.colCP[selected_obj] = vec4(0.0, 0.0, 1.0, 1.0);
		}
	}
	glutPostRedisplay();
}
void onMouse(int button, int state, int x, int y)
{
	float dist, dist1;
	float TOLL = 0.1; // Tolleranza per la selezione del vertice di controllo da modificare
	float c = -1.0;
	float d = 1.0;
	int i;
	float mousex, mousey;

	if (state == GLUT_DOWN)
	{
		switch (button)
		{
		case GLUT_LEFT_BUTTON:
			float mousex = (float)x;
			float mousey = (float)height - y;
			// Mappo le coordinate del mouse nelle coordinate del mondo
			mousex = c + mousex * (d - c) / (float)width;
			mousey = c + mousey * (d - c) / (float)height;

			if (M_I == 1 || mod_par_der == 1)
			{
				selected_obj = 0;

				dist = sqrt((mousex - Curva.CP[0].x) * (mousex - Curva.CP[0].x) + (mousey - Curva.CP[0].y) * (mousey - Curva.CP[0].y));
				// Calcolo la distanza tra il punto in cui si trova attualmente il mouse e tutti i vertici di controllo sulla finestre
				// ed individuo l'indice selected_obj del vertice di controllo pi� vicino alla posizione del mouse
				for (i = 1; i < Curva.CP.size(); i++)
				{
					dist1 = sqrt((mousex - Curva.CP[i].x) * (mousex - Curva.CP[i].x) + (mousey - Curva.CP[i].y) * (mousey - Curva.CP[i].y));

					if (dist1 < dist)
					{
						selected_obj = i;
						dist = dist1;
					}
				}

				if (dist > TOLL)
					selected_obj = -1;
				Tens = 0.0;
				Cont = 0.0;
				Bias = 0.0;

				printf("Punto selezionato %d \n", selected_obj);
			}

			else if (M_I == 0)
			{
				Curva.CP.push_back(vec3(mousex, mousey, 0.0));
				Curva.colCP.push_back(vec4(1.0, 0.0, 0.0, 1.0));
				// Ad ogni vertice di controllo associo un valore vec3(0.0,0.0,0.0) per indicare che la derivata in quel vertice di controllo non � stata modificata
				Derivata.CP.push_back(vec3(0.0, 0.0, 0.0));
			}

			else if (M_I == 2)
			{
				// Elimino l'ultimo vertice di controllo introdotto.
				Curva.CP.pop_back();
				Curva.colCP.pop_back();
			}
		}
	}

	glutPostRedisplay();
}

void mykeyboard(unsigned char key, int x, int y)
{
	switch (key)
	{
	case 'T':
		Tens += 0.1;
		break;
	case 't':
		Tens -= 0.1;
		break;
	case 'C':
		Cont += 0.1;
		break;
	case 'c':
		Cont -= 0.1;
		break;
	case 'B':
		Bias += 0.1;
		break;
	case 'b':
		Bias -= 0.1;
		break;
	}

	// Modifico la derivata del vertice selezionato con valori Tens, cont e Bias selezionati da tastiera
	Derivata.CP.at(selected_obj).x = dx(selected_obj, t, Tens, Bias, Cont, &Poligonale);
	Derivata.CP.at(selected_obj).y = dy(selected_obj, t, Tens, Bias, Cont, &Poligonale);

	glutPostRedisplay();
}

void costruisci_formaHermite(vec4 color_top, vec4 color_bot, Forma* forma)
{
	Poligonale.CP = Curva.CP;
	Poligonale.colCP = Curva.colCP;
	update_VAO_CP(&Poligonale);

	if (Poligonale.CP.size() > 1)
	{
		t = new float[Poligonale.CP.size()];
		float step = 1.0 / (float)(Poligonale.CP.size() - 1);
		for (int i = 0; i < Poligonale.CP.size(); i++)
			t[i] = (float)i * step;

		InterpolazioneHermite(t, &Curva, color_top, color_bot);

		forma->nv = Curva.vertici.size();
	}
}

void INIT_VAO(void)
{
	Curva.nv = 140;

	crea_VAO_Vector(&Curva);

	Poligonale.ncp = 20;

	crea_VAO_CP(&Poligonale);

	tangenti.ncp = 20;

	crea_VAO_CP(&tangenti);

	assi.ncp = 4;
	assi.CP.push_back(vec3(0.0f, 1.0f, 0.0f));
	assi.CP.push_back(vec3(0.0f, -1.0f, 0.0f));
	assi.CP.push_back(vec3(-1.0f, 0.0f, 0.0f));
	assi.CP.push_back(vec3(1.0f, 0.0f, 0.0f));

	assi.colCP.push_back(vec4(0.0f, 0.0f, 0.0f, 1.0f));
	assi.colCP.push_back(vec4(0.0f, 0.0f, 0.0f, 1.0f));
	assi.colCP.push_back(vec4(0.0f, 0.0f, 0.0f, 1.0f));
	assi.colCP.push_back(vec4(0.0f, 0.0f, 0.0f, 1.0f));

	crea_VAO_CP(&assi);
	update_VAO_CP(&assi);

	//Projection = ortho(0.0f, float(width), 0.0f, float(height));
	Projection = ortho(-1.0f, 1.0f, -1.0f, 1.0f);

	MatProj = glGetUniformLocation(programId, "Projection");
	MatModel = glGetUniformLocation(programId, "Model");
	glViewport(0.0, 0.0, width, height);
}

void INIT_SHADER(void)
{
	GLenum ErrorCheckValue = glGetError();

	char* vertexShader = (char*)"vertexShader_M.glsl";
	char* fragmentShader = (char*)"fragmentShader_M.glsl";

	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);
}

void drawScene(void)
{
	int i;

	glClearColor(0.0, 1.0, 0.0, 1.0);
	glClear(GL_COLOR_BUFFER_BIT);

	vec4 col_bottom = vec4{ 0.5451, 0.2706, 0.0745, 1.0000 };
	vec4 col_top = vec4{ 1.0, 0.4980, 0.0353, 1.0000 };

	// Caso metodo=1 : costrusci curva di Hermite
	if (metodo == 1)
	{
		costruisci_formaHermite(col_top, col_bottom, &Curva);

		update_VAO_Vector(&Curva);
		update_VAO_CP(&Poligonale);
	}

	glUniformMatrix4fv(MatProj, 1, GL_FALSE, value_ptr(Projection));

	// la matrice di Modellazione della Curva coincide con la matrice identit�, perch� selezioniamo i punti con il mouse in coordinate del mondo
	Curva.Model = mat4(1.0);

	glPointSize(6.0);

	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Curva.Model));

	glUniformMatrix4fv(MatProj, 1, GL_FALSE, value_ptr(Projection));
	assi.Model = mat4(1.0);
	glPointSize(3.0);

	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(assi.Model));
	glBindVertexArray(assi.VAO);
	glDrawArrays(GL_LINES, 0, assi.CP.size());
	glBindVertexArray(0);
	// la matrice di Modellazione della Curva coincide con la matrice identit�, perch� selezioniamo i punti con il mouse in coordinate del mondo
	Curva.Model = mat4(1.0);

	// Dimensione in pixel di un punto dello schermo
	glPointSize(3.0);

	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Curva.Model));

	// Disegno la curva se ho pi� di 2 vertici di controllo
	if (Poligonale.CP.size() > 1)
	{
		glBindVertexArray(Curva.VAO);
		glDrawArrays(GL_LINE_STRIP, 0, Curva.vertici.size());
		glBindVertexArray(0);
	}

	// Disegno i vertici di controllo
	glBindVertexArray(Poligonale.VAO);
	glDrawArrays(GL_POINTS, 0, Poligonale.CP.size());
	glBindVertexArray(0);

	// Se ho selezionato la costruzione della curva interpolante di Hermite, visualizzo le tangenti se visualizzaTg=1
	if (metodo == 1)
	{
		if (visualizzaTg == 1)
		{
			update_VAO_CP(&tangenti);
			glBindVertexArray(tangenti.VAO);
			glDrawArrays(GL_LINES, 0, tangenti.CP.size());
			glBindVertexArray(0);
		}
	}

	glutSwapBuffers();
}

void salva_config(void)
{
	std::ofstream outputFile("mycurve.txt");

	if (outputFile.is_open())
	{
		for (int i = 0; i < Curva.CP.size(); i++)
			outputFile << Curva.CP[i].x << "  " << Curva.CP[i].y << "  " << Curva.CP[i].z << std::endl;

		outputFile.close();
		std::cout << "Dati scritti con successo." << std::endl;
	}
}

void menu_splineInterpC1(int num)
{
	metodo = 1;

	if (num == 1)
		salva_config();

	if (num == 2)
	{
		mod_par_der = 1;
		M_I = -1;
	}

	if (num == 3)
		mod_par_der = 0;

	if (num == 4)
		visualizzaTg = 1;

	if (num == 5)
		visualizzaTg = 0;

	if (num == 6)
		Curva.CP.clear();

	glutPostRedisplay();
}

void menu_M_I(int num)
{
	M_I = num;
	if (M_I == 1)
		selected_obj = -1;

	glutPostRedisplay();
}
void menu(int num)
{
	glutPostRedisplay();
}
void createMenu(void)
{
	// Creazione sottomenu da attaccare all'item del menu relativo alla scelta del tipo di inserimento

	submenu_Opzioni_I = glutCreateMenu(menu_M_I);
	glutAddMenuEntry("Inserisci", 0);
	glutAddMenuEntry("Sposta", 1);
	glutAddMenuEntry("Elimina", 2);

	submenu_SplineC1Interp = glutCreateMenu(menu_splineInterpC1);
	glutAddMenuEntry("Calcola Curva Interpolante", 0);
	glutAddMenuEntry("Salva Control Points", 1);
	glutAddMenuEntry("Attiva Modalita' modifica tangenti", 2);
	glutAddMenuEntry("Disattiva Modalita' modifica tangenti", 3);
	glutAddMenuEntry("Attiva Visualizzazione tangenti ", 4);
	glutAddMenuEntry("Disattiva Visualizzazione tangenti ", 5);
	glutAddMenuEntry("Inserisci Nuova Curva", 6);

	// Creazione del menu prinicpale
	menu_id = glutCreateMenu(menu);
	glutAddMenuEntry("Editor Curve", -1);
	glutAddSubMenu("Modalita inserimento", submenu_Opzioni_I);
	glutAddSubMenu("Curve cubiche di Hermite Interpolanti C1", submenu_SplineC1Interp);

	glutAttachMenu(GLUT_RIGHT_BUTTON);
}

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);

	glutInitContextVersion(4, 0);
	glutInitContextProfile(GLUT_CORE_PROFILE);

	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA);

	glutInitWindowSize(width, height);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("Editor Curve");
	glutDisplayFunc(drawScene);

	glutMouseFunc(onMouse);

	glutKeyboardFunc(mykeyboard);

	glewExperimental = GL_TRUE;
	glewInit();
	INIT_SHADER();
	INIT_VAO();
	createMenu();
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glutMotionFunc(mouseMotion);

	glutMainLoop();
}