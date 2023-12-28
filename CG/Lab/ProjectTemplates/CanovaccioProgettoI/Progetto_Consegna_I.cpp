#include <iostream>
#include <fstream>
#include <string>
#include "ShaderMaker.h"
#include "lib.h"
#include "Strutture.h"
#include "geometria.h"
#include "inizializzazioni.h"
#include "Gestione_VAO.h"
#include "Gestione_Gioco.h"
#include <GL/glew.h>
#include <GL/freeglut.h>
#include "GestioneTesto.h"
#include <ft2build.h>
#include FT_FREETYPE_H

unsigned int VAO_Text, VBO_Text;

unsigned int programId, programId_text;
int frame_animazione = 0;
float angolo = 0, scaling = 1;
float angoloBarca = 0.0, dx_f = 0.0, dy_f = 0.0;
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
 

Forma  Farf = {}, Cuore = {}, piano = {}, Proiettile = {};
//Forma Curva = {}, Poligonale = {}, Derivata = {}, shape = {};
Forma Curva = {};

float posx_Proiettile, posy_Proiettile, angoloCannone = 0.0;

vector<Forma> Scena;


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

 
void drawScene(void)
{
	int k;

	glClearColor(0.0, 0.0, 0.5, 1.0);
	glClear(GL_COLOR_BUFFER_BIT);

	float time = glutGet(GLUT_ELAPSED_TIME) / 1000.0f;

	vec2 resolution = vec2(w_update, h_update);

	glUniform1f(loctime, time);
	glUniform2f(locres, resolution.x, resolution.y);



	Scena[0].Model = mat4(1.0);
	Scena[0].Model = translate(Scena[0].Model, vec3(0.5, 0.5, 0.0));
	Scena[0].Model = scale(Scena[0].Model, vec3((float)width, (float)height, 1.0));
	glUniformMatrix4fv(MatProj, 1, GL_FALSE, value_ptr(Projection));  //comunica i valori della variabile uniform Projection al vertex shader
	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[0].Model)); //comunica i valori della variabile uniform Model  al vertex shader
	glUniform1i(locSceltafs, Scena[0].sceltaFs);
	glBindVertexArray(Scena[0].VAO);
	glDrawArrays(Scena[0].render, 0, Scena[0].nv);
	glBindVertexArray(0);
	
	//Matrice di Modellazione el sommergibile
	Scena[2].Model = mat4(1.0);
	Scena[2].Model = translate(Scena[2].Model, vec3(600.0 + dx_t, 300.0 + dy_t, 0.0));
	Scena[2].Model = rotate(Scena[2].Model, angoloCannone, vec3(0.0f, 0.0f, 1.0f));
	Scena[2].Model = rotate(Scena[2].Model, angolo, vec3(0.0f, 0.0f, 1.0f));
	//Scena[2].Model = scale(Scena[2].Model, vec3(5.0f, 5.0f, 5.0f));

	/*Matrice di modellazione del proiettile*/

	Scena[3].Model = translate(Scena[2].Model, vec3(posx_Proiettile+5, 60 + posy_Proiettile, 0.0));
	Scena[3].Model = scale(Scena[3].Model, vec3(80.0, 80.0, 1.0));

	//Agiornamento in coordinate del mondo delle coordinate del Bounding Box del proiettile
	Scena[3].corner_b = Scena[3].corner_b_obj;
	Scena[3].corner_t = Scena[3].corner_t_obj;
	Scena[3].corner_b = Scena[3].Model * Scena[3].corner_b;
	Scena[3].corner_t = Scena[3].Model * Scena[3].corner_t;
	
	Scena[2].Model = scale(Scena[2].Model, vec3(250.0, 250.0, 1.0));


	//Disegno Proettile

	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[3].Model));
	glUniform1i(locSceltafs, Scena[3].sceltaFs);
	glBindVertexArray(Scena[3].VAO);
	glDrawArrays(Scena[3].render, 0, Scena[3].nv - 6);

	if (drawBB == TRUE)
	{	//Disegno Bounding Box
		glDrawArrays(GL_LINE_STRIP, Scena[2].nv - 6, 6);
		glBindVertexArray(0);
	}


	//Disegno Sommergibile

	glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[2].Model));
	glUniform1i(locSceltafs, Scena[2].sceltaFs);
	glBindVertexArray(Scena[2].VAO);
	glDrawArrays(Scena[2].render, 0, Scena[2].nv-6);

	glBindVertexArray(0);


	/*Matrice di modellazione farfalla */
	Scena[1].Model = mat4(1.0);
	Scena[1].Model = translate(Scena[1].Model, vec3(900.0 + 50 * cos(dx_f), 500.0 + 50 * sin(dx_f), 0.0));
	Scena[1].Model = scale(Scena[1].Model, vec3(80.5, 80.5, 1.0));
	//Agiornamento in coordinate del mondo delle coordinate del Bounding Box della Farfalla
	Scena[1].corner_b = Scena[1].corner_b_obj;
	Scena[1].corner_t = Scena[1].corner_t_obj;
	Scena[1].corner_b = Scena[1].Model * Scena[1].corner_b;
	Scena[1].corner_t = Scena[1].Model * Scena[1].corner_t;
	
	Coll = checkCollision(Scena[1], Scena[3]);
	std::cout << Coll << std::endl;
	if (Coll == TRUE)
		Scena[1].alive = FALSE;

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
	glutTimerFunc(250, update_Barca, 0); //gestione evento oziosit : viene richiamata la funzione updateScale ogni 250 millisecondi che aggiorna i parametri di scalatura e forza il ridisegno
	glutTimerFunc(250, update_f, 0);

	//glutTimerFunc(250, updateScale, 0); 
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


