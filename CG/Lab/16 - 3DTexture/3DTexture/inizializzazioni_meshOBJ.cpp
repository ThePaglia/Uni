#include "ShaderMaker.h"
#include "Gestione_VAO.h"
#include "geometria.h"
#include "Strutture.h"
#include "Materiali_Base.h"
#include "load_meshes_assimp.hpp"
#include "gestioneTexture.h"

extern vector<MeshObj> Model3D;
extern vector<vector<MeshObj>> ScenaObj;

extern unsigned int programId, programId1, programIdr, programId_text, VAO_Text, VBO_Text;
Mesh Cubo, Piano, Piramide, oggetto;
extern vector<Mesh> Scena, Snowman;
extern int height, width;
extern ViewSetup SetupTelecamera;
extern PerspectiveSetup SetupProspettiva;
extern vector<Material> materials;
extern vector<Shader> shaders;
extern point_light light;
extern int texture, texture1, texture2;

string Meshdir = "Meshes/";
string Imagedir = "Textures/";

void INIT_SHADER(void)

{
	GLenum ErrorCheckValue = glGetError();

	char* vertexShader = (char*)"vertexShader_C.glsl";
	char* fragmentShader = (char*)"fragmentShader_C.glsl";

	programId = ShaderMaker::createProgram(vertexShader, fragmentShader);
	glUseProgram(programId);

	vertexShader = (char*)"vertexShader_CubeMap.glsl";
	fragmentShader = (char*)"fragmentShader_CubeMap.glsl";

	programId1 = ShaderMaker::createProgram(vertexShader, fragmentShader);
	// Generazione del program shader per la gestione del testo
	vertexShader = (char*)"VertexShader_Text.glsl";
	fragmentShader = (char*)"FragmentShader_Text.glsl";

	programId_text = ShaderMaker::createProgram(vertexShader, fragmentShader);

	vertexShader = (char*)"vertexShader_riflessione.glsl";
	fragmentShader = (char*)"fragmentShader_riflessione.glsl";

	programIdr = ShaderMaker::createProgram(vertexShader, fragmentShader);
}

void INIT_VAO(void)
{
	string name = "minnie.png";
	string path = Imagedir + name;
	texture = loadTexture(path.c_str(), 0);
	name = "muromattoni.jpg";
	path = Imagedir + name;
	texture1 = loadTexture(path.c_str(), 0);

	// Skybox

	crea_cubo(&oggetto);
	crea_VAO_Vector(&oggetto);

	Scena.push_back(oggetto);

	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();

	crea_sfera(&oggetto, vec4(1.0, 0.0, 1.0, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(-8.0, -1.0, 6.0));
	oggetto.Model = scale(oggetto.Model, vec3(0.5, 0.5, 0.5));
	oggetto.nome = "Sfera riflettente";

	oggetto.sceltaVS = 1; // Interpolative Shading
	oggetto.material = MaterialType::RED_PLASTIC;
	Scena.push_back(oggetto);

	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();

	// Crea piano di appoggio
	crea_piano_suddiviso(&oggetto, vec4(0.01, 0.5451, 0.1333, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(0.0, -1.5, 0.0));
	oggetto.Model = scale(oggetto.Model, vec3(100000.0, 1.0, 100000.0));
	oggetto.nome = "Terreno ";

	oggetto.sceltaVS = 0; // NO SHADING
	oggetto.material = MaterialType::EMERALD;
	Scena.push_back(oggetto);

	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();
	// Crea Bandiera
	crea_cilindro(&oggetto, vec4(0.0, 0.0, 1.0, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(-8.0, -1.5, 0.0));
	oggetto.Model = scale(oggetto.Model, vec3(0.2, 6.0, 0.2));
	oggetto.nome = "Bandiera";
	oggetto.sceltaVS = 1;
	oggetto.material = MaterialType::SLATE;

	Scena.push_back(oggetto);

	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();
	// Crea Telo
	crea_piano_suddiviso(&oggetto, vec4(1.0, 0.0, 0.0, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(-6.0, 2.0, 0.0));
	oggetto.Model = scale(oggetto.Model, vec3(5.0, 1.8, 1.0));
	oggetto.Model = rotate(oggetto.Model, radians(90.0f), vec3(1.0, 0.0, 0.0));

	oggetto.sceltaVS = 4; // Shading per bandiera sventolante
	oggetto.material = MaterialType::BRASS;
	oggetto.nome = "Telo";
	Scena.push_back(oggetto);

	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();

	// Sfera
	crea_sfera(&oggetto, vec4(1.0, 0.0, 1.0, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(4.0, 0.0, 0.0));
	oggetto.Model = scale(oggetto.Model, vec3(2.0, 2.0, 2.0));
	oggetto.nome = "Sfera";

	oggetto.sceltaVS = 1; // Interpolative Shading
	oggetto.material = MaterialType::RED_PLASTIC;
	Scena.push_back(oggetto);

	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();

	// Cono
	crea_cono(&oggetto, vec4(1.0, 0.0, 0.0, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(-1.0, 1.6, 1));
	oggetto.Model = scale(oggetto.Model, vec3(1.2, 1.4, 1.2));
	oggetto.Model = rotate(oggetto.Model, radians(-180.0f), vec3(1.0, 0.0, 0.0));

	oggetto.sceltaVS = 1;
	oggetto.nome = "Cono";
	oggetto.material = MaterialType::BRASS;

	Scena.push_back(oggetto);

	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();

	// TORO
	crea_toro(&oggetto, vec4(0.0, 1.0, 0.0, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(7.0, 2.0, 7.0));
	oggetto.Model = scale(oggetto.Model, vec3(1.0, 0.5, 1.0));
	oggetto.nome = "Toro";
	oggetto.material = MaterialType::YELLOW;
	oggetto.sceltaVS = 1;
	Scena.push_back(oggetto);
	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();
	// Cilindro
	crea_cilindro(&oggetto, vec4(0.0, 0.0, 1.0, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(7.0, -1.0, 7.0));
	oggetto.Model = scale(oggetto.Model, vec3(1.0, 4.0, 1.0));
	oggetto.nome = "Bandiera";
	oggetto.sceltaVS = 1;
	oggetto.material = MaterialType::EMERALD;
	Scena.push_back(oggetto);
	oggetto.vertici.clear();
	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();
	// PANNELLO
	crea_piano(&oggetto, vec4(0.2, 0.2, 0.9, 1.0));
	crea_VAO_Vector(&oggetto);
	oggetto.nome = "Pannello";
	oggetto.Model = mat4(1.0);
	oggetto.Model = translate(oggetto.Model, vec3(-2.0, 4.0, 7.0));
	oggetto.Model = scale(oggetto.Model, vec3(2.0f, 2.0f, 2.0f));
	oggetto.Model = rotate(oggetto.Model, radians(90.0f), vec3(1.0, 0.0, 0.0));
	oggetto.sceltaVS = 1;
	oggetto.material = MaterialType::EMERALD;
	Scena.push_back(oggetto);
	oggetto.vertici.clear();

	oggetto.colori.clear();
	oggetto.indici.clear();
	oggetto.normali.clear();
	oggetto.texCoords.clear();

	//Da qui al 3DModel.clear() si possono aggiungere altri oggetti alla scena
	bool obj;
	name = "Shine_Sprite.obj";
	path = Meshdir + name;
	obj = loadAssImp(path.c_str(), Model3D);

	printf("Model3D %d \n", Model3D.size());

	int nmeshes = Model3D.size();

	for (int i = 0; i < nmeshes; i++)
	{
		crea_VAO_Vector_MeshObj(&Model3D[i]);
		Model3D[i].ModelM = mat4(1.0);
		Model3D[i].ModelM = translate(Model3D[i].ModelM, vec3(2.0, 6.0, 12.0));
		Model3D[i].ModelM = scale(Model3D[i].ModelM, vec3(0.5, 0.5, 0.5));
		Model3D[i].nome = "Model3D";
		Model3D[i].sceltaVS = 3;

		vec3 ambiental = Model3D[i].materiale.ambient;
		vec3 diffusivo = Model3D[i].materiale.diffuse;
		vec3 speculare = Model3D[i].materiale.specular;
		Model3D[i].materiale.ambient = ambiental;
		Model3D[i].materiale.diffuse = diffusivo;
		Model3D[i].materiale.specular = speculare;
		Model3D[i].materiale.shininess = red_plastic_shininess;
	}

	ScenaObj.push_back(Model3D);
	Model3D.clear();

	name = "auto.obj";
	path = Meshdir + name;
	obj = loadAssImp(path.c_str(), Model3D);

	printf("Model3D %d \n", Model3D.size());

	nmeshes = Model3D.size();

	for (int i = 0; i < nmeshes; i++)
	{
		crea_VAO_Vector_MeshObj(&Model3D[i]);
		Model3D[i].ModelM = mat4(1.0);
		Model3D[i].ModelM = translate(Model3D[i].ModelM, vec3(2.0, -1.0, 16.0));
		Model3D[i].ModelM = scale(Model3D[i].ModelM, vec3(0.5, 0.5, 0.5));
		Model3D[i].nome = "Model3D";
		Model3D[i].sceltaVS = 3;

		vec3 ambiental = Model3D[i].materiale.ambient;
		vec3 diffusivo = Model3D[i].materiale.diffuse;
		vec3 speculare = Model3D[i].materiale.specular;
		Model3D[i].materiale.ambient = ambiental;
		Model3D[i].materiale.diffuse = diffusivo;
		Model3D[i].materiale.specular = speculare;
		Model3D[i].materiale.shininess = red_plastic_shininess;
	}
	ScenaObj.push_back(Model3D);
	Model3D.clear();
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

	SetupTelecamera.position = glm::vec4(0.0, 0.5, 20.0, 0.0);
	SetupTelecamera.target = glm::vec4(0.0, 0.0, 0.0, 0.0);
	SetupTelecamera.direction = SetupTelecamera.target - SetupTelecamera.position;
	SetupTelecamera.upVector = glm::vec4(0.0, 1.0, 0.0, 0.0);

	// Imposto la proiezione prospettica

	SetupProspettiva.aspect = (GLfloat)width / (GLfloat)height;
	SetupProspettiva.fovY = 45.0f;
	SetupProspettiva.far_plane = 2000.0f;
	SetupProspettiva.near_plane = 0.1f;
}

void INIT_Illuminazione()
{
	light.position = { 0.0, 3.0, 0.0 };
	light.color = { 1.0, 1.0, 1.0 };
	light.power = 1.f;

	// Setup dei materiali
	//  Materials setup
	materials.resize(6);
	materials[MaterialType::RED_PLASTIC].name = "Red Plastic";
	materials[MaterialType::RED_PLASTIC].ambient = red_plastic_ambient;
	materials[MaterialType::RED_PLASTIC].diffuse = red_plastic_diffuse;
	materials[MaterialType::RED_PLASTIC].specular = red_plastic_specular;
	materials[MaterialType::RED_PLASTIC].shininess = red_plastic_shininess;

	materials[MaterialType::EMERALD].name = "Emerald";
	materials[MaterialType::EMERALD].ambient = emerald_ambient;
	materials[MaterialType::EMERALD].diffuse = emerald_diffuse;
	materials[MaterialType::EMERALD].specular = emerald_specular;
	materials[MaterialType::EMERALD].shininess = emerald_shininess;

	materials[MaterialType::BRASS].name = "Brass";
	materials[MaterialType::BRASS].ambient = brass_ambient;
	materials[MaterialType::BRASS].diffuse = brass_diffuse;
	materials[MaterialType::BRASS].specular = brass_specular;
	materials[MaterialType::BRASS].shininess = brass_shininess;

	materials[MaterialType::SLATE].name = "Slate";
	materials[MaterialType::SLATE].ambient = slate_ambient;
	materials[MaterialType::SLATE].diffuse = slate_diffuse;
	materials[MaterialType::SLATE].specular = slate_specular;
	materials[MaterialType::SLATE].shininess = slate_shininess;

	materials[MaterialType::YELLOW].name = "Yellow";
	materials[MaterialType::YELLOW].ambient = yellow_ambient;
	materials[MaterialType::YELLOW].diffuse = yellow_diffuse;
	materials[MaterialType::YELLOW].specular = yellow_specular;
	materials[MaterialType::YELLOW].shininess = yellow_shininess;

	materials[MaterialType::NO_MATERIAL].name = "NO_MATERIAL";
	materials[MaterialType::NO_MATERIAL].ambient = glm::vec3(1, 1, 1);
	materials[MaterialType::NO_MATERIAL].diffuse = glm::vec3(0, 0, 0);
	materials[MaterialType::NO_MATERIAL].specular = glm::vec3(0, 0, 0);
	materials[MaterialType::NO_MATERIAL].shininess = 1.f;

	// Setup degli shader
	shaders.resize(5);
	shaders[ShaderOption::NONE].value = 0;
	shaders[ShaderOption::NONE].name = "NONE";
	shaders[ShaderOption::GOURAD_SHADING].value = 1;
	shaders[ShaderOption::GOURAD_SHADING].name = "GOURAD SHADING";
	shaders[ShaderOption::PHONG_SHADING].value = 2;
	shaders[ShaderOption::PHONG_SHADING].name = "PHONG SHADING";
	shaders[ShaderOption::PHONG_SHADING].value = 3;
	shaders[ShaderOption::NO_TEXTURE].name = "NO TEXTURE";
	shaders[ShaderOption::WAVE].value = 4;
	shaders[ShaderOption::WAVE].name = "WAVE";
}