#include "ShaderMaker.h"
#include "Gestione_VAO.h"
#include "geometria.h"
#include "Strutture.h"
extern unsigned int programId, programId_text, VAO_Text, VBO_Text;
Mesh Cubo, Piano, Piramide, Sfera, Toro;
extern vector<Mesh> Scena;
extern int height, width;
extern ViewSetup SetupTelecamera;
extern PerspectiveSetup SetupProspettiva;
void INIT_SHADER(void)
{
	GLenum ErrorCheckValue = glGetError();

	char* vertexShader = (char*)"vertexShader_C.glsl";
	char* fragmentShader = (char*)"fragmentShader_C.glsl";

	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);

	// Generazione del program shader per la gestione del testo
	vertexShader = (char*)"VertexShader_Text.glsl";
	fragmentShader = (char*)"FragmentShader_Text.glsl";

	programId_text = ShaderMaker::createProgram(vertexShader, fragmentShader);
}

void INIT_VAO(void)
{
	// CUBO
	crea_cubo(&Cubo);
	crea_VAO_Vector(&Cubo);
	Cubo.nome = "Cubo";

	// definizione della matrice di Modellazione
	Cubo.Model = mat4(1.0);
	Cubo.Model = translate(Cubo.Model, vec3(3.5, 1.5, 2.5));
	Cubo.Model = scale(Cubo.Model, vec3(1.5f, 1.5f, 2.0f));

	Scena.push_back(Cubo);

	// Piano

	crea_piano(&Piano);
	crea_VAO_Vector(&Piano);
	Piano.nome = "Piano";
	// definizione della matrice di Modellazione
	Piano.Model = mat4(1.0);
	Piano.Model = scale(Piano.Model, vec3(100.0f, 1.0f, 100.0f));
	Scena.push_back(Piano);

	// Piramide
	crea_piramide(&Piramide);
	crea_VAO_Vector(&Piramide);
	Piramide.nome = "Piramide";
	// definizione della matrice di Modellazione
	Piramide.Model = mat4(1.0);
	Piramide.Model = translate(Piramide.Model, vec3(-1.5, 0.0, 0.5));
	Piramide.Model = scale(Piramide.Model, vec3(2.5f, 2.5f, 2.5f));
	// Piramide.Model = translate(Piramide.Model, vec3(-0.5, 0.0, -0.5));
	Scena.push_back(Piramide);

	// Sfera
	crea_sfera(&Sfera, vec4(1.0f, 1.0f, 0.0f, 1.0f));
	crea_VAO_Vector(&Sfera);
	Sfera.nome = "Sfera";
	// definizione della matrice di Modellazione
	Sfera.Model = mat4(1.0);
	Sfera.Model = translate(Sfera.Model, vec3(0.0f, 3.0f, 0.0f));
	Sfera.Model = scale(Sfera.Model, vec3(1.0f, 1.0f, 1.0f));
	Scena.push_back(Sfera);

	// Toro
	crea_toro(&Toro, vec4(1.0f, 0.0f, 1.0f, 1.0f));
	crea_VAO_Vector(&Toro);
	Toro.nome = "Toro";
	// definizione della matrice di Modellazione
	Toro.Model = mat4(1.0);
	Toro.Model = translate(Toro.Model, vec3(-7.0f, 3.0f, 0.0f));
	Toro.Model = scale(Toro.Model, vec3(1.0f, 1.0f, 1.0f));
	Scena.push_back(Toro);
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

void INIT_CAMERA_PROJECTION(void)
{
	// Imposto la telecamera

	SetupTelecamera.position = glm::vec4(0.0, 0.5, 20.0, 1.0);
	SetupTelecamera.target = glm::vec4(0.0, 0.0, 0.0, 1.0);
	SetupTelecamera.direction = SetupTelecamera.target - SetupTelecamera.position;
	SetupTelecamera.upVector = glm::vec4(0.0, 1.0, 0.0, 0.0);

	// Imposto la proiezione prospettica

	SetupProspettiva.aspect = (GLfloat)width / (GLfloat)height;
	SetupProspettiva.fov = 45.0f;
	SetupProspettiva.far_plane = 2000.0f;
	SetupProspettiva.near_plane = 0.1f;
}