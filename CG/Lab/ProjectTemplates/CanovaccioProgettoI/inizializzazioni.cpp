#include "Lib.h"
#include "Strutture.h"
#include "geometria.h"
#include "Hermite.h"
#include "ShaderMaker.h"
#include "Hermite.h"
#include "Gestione_VAO.h"

extern unsigned int programId, programId_text;
 
extern Forma piano, Farf, Proiettile, Curva;
extern vector<Forma> Scena;
extern unsigned int VAO_Text, VBO_Text;
#pragma warning(disable:4996)
 
//Viene ricavata la location della variabile Uniform Projection presente nel fragment shader
extern mat4 Projection;
extern GLuint MatProj, MatModel, loctime, locres, locCol1, locCol2, locCol3, locSceltafs;

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
	crea_punti_forma_da_file("sommergibile.txt","r",&Curva);
	vec3 centro = vec3(0.0, -0.2, 0.0);
	costruisci_formaHermite(centro, color_top, color_bot, &Curva);
	crea_VAO_Vector(&Curva);
	Curva.render = GL_TRIANGLE_FAN;
	Curva.sceltaFs = 1;
	Scena.push_back(Curva);
	Curva.CP.clear();
	Curva.Derivata.clear();
	Curva.vertici.clear();
 

	//Proiettile
	color_top = vec4(1.0, 0.0, 0.0, 1.0);
	color_bot = vec4(1.0,1.0,0.0, 1.0);
	crea_punti_forma_da_file("fiore.txt", "r", &Curva);
	centro = vec3(-0.1, 0.1, 0.0);

	costruisci_formaHermite(centro, color_top, color_bot, &Curva);
	crea_VAO_Vector(&Curva);
	Curva.render = GL_TRIANGLE_FAN;
	Curva.sceltaFs = 1;
	Scena.push_back(Curva);

	 
	
	//Projection   la matrice che mappa la finestra sul mondo nelle coordinate NDC (cormalizzate di device che trariano tra (-1,1) ed (1,1)


	//Viene ricavata la location della variabile Uniform Projection presente nel fragment shader
	MatProj = glGetUniformLocation(programId, "Projection");
	//Viene ricavata la location della variabile Uniform Model presente nel fragment shader
	MatModel = glGetUniformLocation(programId, "Model");
	loctime = glGetUniformLocation(programId, "time");
	locres = glGetUniformLocation(programId, "resolution");
	 
	locSceltafs = glGetUniformLocation(programId, "scelta_fs");

}