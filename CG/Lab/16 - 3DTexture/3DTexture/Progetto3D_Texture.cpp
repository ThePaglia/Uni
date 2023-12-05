#include <iostream>

#include "ShaderMaker.h"
#include "Lib.h"
#include "geometria.h"

#include "Gestione_VAO.h"
#include "GestioneTesto.h"
#include "inizializzazioni..h"
#include "GestioneInterazioni.h"
#include "GestioneTelecamera.h"
#include "load_meshes_assimp.hpp"
#include "gestioneTexture.h"
#include <ft2build.h>
#include FT_FREETYPE_H
#define BUFFER_OFFSET(i) ((char *)NULL + (i))
// Inizializzazione Booleano per la visualizzazione dell'ancora
bool visualizzaAncora = FALSE;
int last_mouse_pos_Y;
int last_mouse_pos_X;
bool moving_trackball = 0;
float angolo;
// Impostazione larghezza ed altezza della finestra sullo schermo
int width = 1024;
int height = 800;
// Definizione stringhe da visualizzare sulla finestra
string stringa_asse;
string Operazione;
vector<Material> materials;
vector<Shader> shaders;
LightShaderUniform light_unif = {};
vector<MeshObj> Model3D;
vector<vector<MeshObj>> ScenaObj;

int w_up = width;
int h_up = height;
mat4 Projection_text;
mat4 rotation_matrix = mat4(1.0);

unsigned int programId, programId1, programIdr, programId_text, MatrixProj, MatrixProj_txt, MatModel, MatView, locSceltaVs, loctime, MatViewS, MatrixProjS;
unsigned int loc_view_pos, locSceltaFs, VAO_Text, VBO_Text, loc_texture, loc_texture1;
int idTex, texture, texture1, texture2, cubemapTexture;
unsigned int MatModelR, MatViewR, MatrixProjR, loc_view_posR, loc_cubemapR;
float raggio_sfera = 2.5;
vec3 asse = vec3(0.0, 1.0, 0.0);
int selected_obj = 0;
float cameraSpeed = 0.1;
vector<Mesh> Scena, Snowman;
point_light light;

// Definzione SetupTelecamera e SetupProspettiva

ViewSetup SetupTelecamera;
PerspectiveSetup SetupProspettiva;

// Varibili per la gestione della direzione della telecamera tramite mouse
bool firstMouse = true;
float lastX = (float)width / 2;
float lastY = (float)height / 2;
float Theta = -90.0f;
float Phi = 0.0f;

string SkyboxDir = "SkyBox/";

mat4 Projection, Model, View;
void inizilizzaCubemap()
{
	vector<std::string> faces{
		/*"right.jpg",
			"left.jpg",
			"top.jpg",
			"bottom.jpg",
			"front.jpg",
			"back.jpg"*/
		SkyboxDir + "posx.jpg",
		SkyboxDir + "negx.jpg",
		SkyboxDir + "posy.jpg",
		SkyboxDir + "negy.jpg",
		SkyboxDir + "posz.jpg",
		SkyboxDir + "negz.jpg" };
	cubemapTexture = loadCubemap(faces, 0);
}
// loads a cubemap texture from 6 individual texture faces
// order:
// +X (right)
// -X (left)
// +Y (top)
// -Y (bottom)
// +Z (front)
// -Z (back)
//
void resize(int w, int h)
{
	// Imposto la matrice di Proiezione per il rendering del testo

	Projection_text = ortho(0.0f, (float)width, 0.0f, (float)height);

	// Imposto la matrice di proiezione per la scena da diegnare
	Projection = perspective(radians(SetupProspettiva.fovY), SetupProspettiva.aspect, SetupProspettiva.near_plane, SetupProspettiva.far_plane);

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

void main_menu_func(int option)
{
	switch (option)
	{
	case MenuOption::WIRE_FRAME:
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		break;
	case MenuOption::FACE_FILL:
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		break;

	default:
		break;
	}
	glutPostRedisplay();
}

void material_menu_function(int option)
{
	if (selected_obj > -1)
		Scena[selected_obj].material = (MaterialType)option;
	glutPostRedisplay();
}

void shader_menu_function(int option)
{
	if (selected_obj > -1)
		Scena[selected_obj].sceltaVS = shaders[option].value;

	glutPostRedisplay();
}

void buildOpenGLMenu()
{
	int materialSubMenu = glutCreateMenu(material_menu_function);

	glutAddMenuEntry(materials[MaterialType::EMERALD].name.c_str(), MaterialType::EMERALD);
	glutAddMenuEntry(materials[MaterialType::BRASS].name.c_str(), MaterialType::BRASS);
	glutAddMenuEntry(materials[MaterialType::SLATE].name.c_str(), MaterialType::SLATE);
	glutAddMenuEntry(materials[MaterialType::YELLOW].name.c_str(), MaterialType::YELLOW);

	int shaderSubMenu = glutCreateMenu(shader_menu_function);
	glutAddMenuEntry(shaders[ShaderOption::NONE].name.c_str(), ShaderOption::NONE);
	glutAddMenuEntry(shaders[ShaderOption::GOURAD_SHADING].name.c_str(), ShaderOption::GOURAD_SHADING);
	glutAddMenuEntry(shaders[ShaderOption::PHONG_SHADING].name.c_str(), ShaderOption::PHONG_SHADING);
	glutAddMenuEntry(shaders[ShaderOption::NO_TEXTURE].name.c_str(), ShaderOption::NO_TEXTURE);
	glutAddMenuEntry(shaders[ShaderOption::WAVE].name.c_str(), ShaderOption::WAVE);

	glutCreateMenu(main_menu_func);	 // richiama main_menu_func() alla selezione di una voce menu
	glutAddMenuEntry("Opzioni", -1); //-1 significa che non si vuole gestire questa riga
	glutAddMenuEntry("", -1);
	glutAddMenuEntry("Wireframe", MenuOption::WIRE_FRAME);
	glutAddMenuEntry("Face fill", MenuOption::FACE_FILL);
	glutAddSubMenu("Material", materialSubMenu);
	glutAddSubMenu("Shader", shaderSubMenu);
	glutAttachMenu(GLUT_MIDDLE_BUTTON);
}

void drawScene(void)
{
	int i, j, k;
	float traslax, traslay;

	glUniformMatrix4fv(MatrixProj, 1, GL_FALSE, value_ptr(Projection));
	View = lookAt(vec3(SetupTelecamera.position), vec3(SetupTelecamera.target), vec3(SetupTelecamera.upVector));

	float time = glutGet(GLUT_ELAPSED_TIME) / 1000.0;

	glClearColor(0.0, 0.0, 1.0, 1.0);

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Disegno Sky box

	glDepthMask(GL_FALSE);

	glUseProgram(programId1);
	glUniform1i(glGetUniformLocation(programId1, "skybox"), 0);
	glUniformMatrix4fv(MatrixProjS, 1, GL_FALSE, value_ptr(Projection));
	glUniformMatrix4fv(MatViewS, 1, GL_FALSE, value_ptr(View));
	// skybox cube
	glBindVertexArray(Scena[0].VAO);
	glBindTexture(GL_TEXTURE_CUBE_MAP, cubemapTexture);
	glDrawElements(GL_TRIANGLES, Scena[0].indici.size() * sizeof(GLuint), GL_UNSIGNED_INT, 0);
	glBindVertexArray(0);
	glDepthMask(GL_TRUE);

	glUseProgram(programIdr);
	//Utilizzo il program shader per il disegno
	glUniformMatrix4fv(MatrixProjR, 1, GL_FALSE, value_ptr(Projection));
	glUniformMatrix4fv(MatModelR, 1, GL_FALSE, value_ptr(Scena[1].Model));
	glUniformMatrix4fv(MatViewR, 1, GL_FALSE, value_ptr(View));
	glUniform3f(loc_view_posR, SetupTelecamera.position.x, SetupTelecamera.position.y, SetupTelecamera.position.z);
	glBindVertexArray(Scena[1].VAO);
	glBindTexture(GL_TEXTURE_CUBE_MAP, cubemapTexture);
	glDrawElements(GL_TRIANGLES, (Scena[1].indici.size() - 1) * sizeof(GLuint), GL_UNSIGNED_INT, 0);

	// Cambio program per renderizzare tutto il resto della scena
	glUseProgram(programId);
	// Costruisco la matrice di Vista che applicata ai vertici in coordinate del mondo li trasforma nel sistema di riferimento della camera.

	// Passo al Vertex Shader il puntatore alla matrice View, che sar� associata alla variabile Uniform mat4 Projection
	// all'interno del Vertex shader. Uso l'identificatio MatView
	glPointSize(10.0);
	glUniformMatrix4fv(MatView, 1, GL_FALSE, value_ptr(View));
	// Definizione colore luce, posizione ed intensit
	glUniform3f(light_unif.light_position_pointer, light.position.x + 80 * cos(radians(angolo)), light.position.y, light.position.z + 80 * sin(radians(angolo)));
	glUniform3f(light_unif.light_color_pointer, light.color.r, light.color.g, light.color.b);
	glUniform1f(light_unif.light_power_pointer, light.power);

	// Passo la posizione della camera
	glUniform3f(loc_view_pos, SetupTelecamera.position.x, SetupTelecamera.position.y, SetupTelecamera.position.z);
	glUniform1f(loctime, time);

	for (int k = 0; k < Scena.size(); k++)
	{
		// Trasformazione delle coordinate dell'ancora dal sistema di riferimento dell'oggetto in sistema
		// di riferimento del mondo premoltiplicando per la matrice di Modellazione.

		Scena[k].ancora_world = Scena[k].ancora_obj;
		Scena[k].ancora_world = Scena[k].Model * Scena[k].ancora_world;
		// Passo al Vertex Shader il puntatore alla matrice Model dell'oggetto k-esimo della Scena, che sar� associata alla variabile Uniform mat4 Projection
		// all'interno del Vertex shader. Uso l'identificatio MatModel
		glUniform1i(locSceltaVs, Scena[k].sceltaVS);

		glUniform3fv(light_unif.material_ambient, 1, glm::value_ptr(materials[Scena[k].material].ambient));
		glUniform3fv(light_unif.material_diffuse, 1, glm::value_ptr(materials[Scena[k].material].diffuse));
		glUniform3fv(light_unif.material_specular, 1, glm::value_ptr(materials[Scena[k].material].specular));
		glUniform1f(light_unif.material_shininess, materials[Scena[k].material].shininess);
		glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(Scena[k].Model));
		glBindVertexArray(Scena[k].VAO);

		if (visualizzaAncora == TRUE)
		{
			// Visualizzo l'ancora dell'oggetto
			int ind = Scena[k].indici.size() - 1;

			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glDrawElements(GL_POINTS, 1, GL_UNSIGNED_INT, BUFFER_OFFSET(ind * sizeof(GLuint)));
		}
		// Mixare pi� texture su uno stesso pixel

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture);

		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, texture1);

		glUniform1i(loc_texture, 0);
		glUniform1i(loc_texture1, 1);

		if (k < Scena.size() - 1)
		{
			glUniform1i(loc_texture, 0);
			glBindTexture(GL_TEXTURE_2D, texture1);
		}
		else
		{
			glUniform1i(loc_texture, 0);
			glBindTexture(GL_TEXTURE_2D, texture);
		}

		glDrawElements(GL_TRIANGLES, (Scena[k].indici.size() - 1) * sizeof(GLuint), GL_UNSIGNED_INT, 0);

		glBindVertexArray(0);
	}

	// Renderizzazione dei modelli obj
	for (int j = 0; j < ScenaObj.size(); j++)
	{
		for (int k = 0; k < ScenaObj[j].size(); k++)
		{
			glUniformMatrix4fv(MatModel, 1, GL_FALSE, value_ptr(ScenaObj[j][k].ModelM));
			glUniform1i(locSceltaVs, ScenaObj[j][k].sceltaVS);
			// Passo allo shader il puntatore ai materiali

			glUniform3fv(light_unif.material_ambient, 1, value_ptr(ScenaObj[j][k].materiale.ambient));
			glUniform3fv(light_unif.material_diffuse, 1, value_ptr(ScenaObj[j][k].materiale.diffuse));
			glUniform3fv(light_unif.material_specular, 1, value_ptr(ScenaObj[j][k].materiale.specular));
			glUniform1f(light_unif.material_shininess, ScenaObj[j][k].materiale.shininess);

			glBindVertexArray(ScenaObj[j][k].VAO);
			glDrawElements(GL_TRIANGLES, (ScenaObj[j][k].indici.size()) * sizeof(GLuint), GL_UNSIGNED_INT, 0);

			glBindVertexArray(0);
		}
	}
	// Imposto il renderizzatore del testo

	RenderText(programId_text, Projection_text, Operazione, VAO_Text, VBO_Text, 50.0f, 650.0f, 0.5f, glm::vec3(1.0, 0.0f, 0.2f));

	RenderText(programId_text, Projection_text, stringa_asse, VAO_Text, VBO_Text, 50.0f, 700.0f, 0.5f, glm::vec3(1.0, 0.0f, 0.2f));

	RenderText(programId_text, Projection_text, "Oggetto selezionato", VAO_Text, VBO_Text, 80.0f, 750.0f, 0.5f, glm::vec3(1.0, 0.0f, 0.2f));

	if (selected_obj > -1)
		RenderText(programId_text, Projection_text, Scena[selected_obj].nome.c_str(), VAO_Text, VBO_Text, 120.0f, 700.0f, 0.5f, glm::vec3(1.0, 0.0f, 0.2f));

	glutSwapBuffers();
}

void update(int value)
{
	angolo = angolo + 1;
	glutTimerFunc(200, update, 0);
	glutPostRedisplay();
}

int main(int argc, char* argv[])
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
	glutTimerFunc(200, update, 0);
	glutPassiveMotionFunc(my_passive_mouse);
	glutMotionFunc(mouseActiveMotion);
	glewExperimental = GL_TRUE;

	// Inizializzazioni
	glewInit();
	INIT_SHADER();
	INIT_VAO();
	INIT_CAMERA_PROJECTION();
	INIT_Illuminazione();
	INIT_VAO_Text();
	buildOpenGLMenu();
	inizilizzaCubemap();
	Init_Freetype();
	glEnable(GL_DEPTH_TEST);
	glCullFace(GL_BACK);

	glEnable(GL_BLEND);
	glEnable(GL_ALPHA_TEST);
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

	locSceltaVs = glGetUniformLocation(programId, "sceltaVS");
	locSceltaFs = glGetUniformLocation(programId, "sceltaFS");
	loctime = glGetUniformLocation(programId, "time");
	loc_view_pos = glGetUniformLocation(programId, "ViewPos");
	loc_texture = glGetUniformLocation(programId, "id_tex");
	loc_texture1 = glGetUniformLocation(programId, "id_tex1");

	light_unif.light_position_pointer = glGetUniformLocation(programId, "light.position");
	light_unif.light_color_pointer = glGetUniformLocation(programId, "light.color");
	light_unif.light_power_pointer = glGetUniformLocation(programId, "light.power");
	light_unif.material_ambient = glGetUniformLocation(programId, "material.ambient");
	light_unif.material_diffuse = glGetUniformLocation(programId, "material.diffuse");
	light_unif.material_specular = glGetUniformLocation(programId, "material.specular");
	light_unif.material_shininess = glGetUniformLocation(programId, "material.shininess");

	// location variabili uniformi per lo shader della gestione della cubemap
	MatrixProjS = glGetUniformLocation(programId1, "Projection");
	// Chiedo che mi venga restituito l'identificativo della variabile uniform mat4 Model (in vertex shader)
	// QUesto identificativo sar  poi utilizzato per il trasferimento della matrice Model al Vertex Shader

	// Chiedo che mi venga restituito l'identificativo della variabile uniform mat4 Model (in vertex shader)
	// QUesto identificativo sar  poi utilizzato per il trasferimento della matrice Model al Vertex Shader
	MatViewS = glGetUniformLocation(programId1, "View");
	MatModelR = glGetUniformLocation(programIdr, "Model");
	MatViewR = glGetUniformLocation(programIdr, "View");
	MatrixProjR = glGetUniformLocation(programIdr, "Projection");
	loc_view_posR = glGetUniformLocation(programIdr, "ViewPos");
	loc_cubemapR = glGetUniformLocation(programIdr, "cubemap");
	glutMainLoop();
}