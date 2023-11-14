// Gestione eventi tastiera per il movimento della telecamera
#include "Lib.h"
#include "geometria.h"
#include "Gestione_VAO.h"
#include "GestioneTelecamera.h"
#include "enum.h"
extern bool visualizzaAncora;
extern int cont_cubi, cont_pir;
extern vector<Mesh> Scena;
extern string stringa_asse, Operazione;
extern vec3 asse;
extern mat4 Projection, View;
extern ViewSetup SetupTelecamera;

extern Mesh Cubo, Piramide, Piano;
extern int selected_obj;
extern int height, width;
extern float raggio_sfera;
extern PerspectiveSetup SetupProspettiva;

void modifyModelMatrix(vec3 translation_vector, vec3 rotation_vector, GLfloat angle, GLfloat scale_factor)
{
	// ricordare che mat4(1) costruisce una matrice identit� di ordine 4
	mat4 traslation = glm::translate(glm::mat4(1), translation_vector);
	mat4 scale = glm::scale(glm::mat4(1), glm::vec3(scale_factor, scale_factor, scale_factor));
	mat4 rotation = glm::rotate(glm::mat4(1), angle, rotation_vector);

	// Modifica la matrice di Modellazione dell'oggetto della scena selezionato postmolitplicando per le matrici scale*rotarion*traslation

	if (selected_obj > -1)
		Scena[selected_obj].Model = Scena[selected_obj].Model * scale * rotation * traslation;

	glutPostRedisplay();
}

void mouseWheelCallback(int wheel, int direction, int x, int y)
{
	if (direction > 0) {
		SetupProspettiva.fov -= 2.0f; // Restringere il campo visivo, equivale ad avvicinare la scena (quindi a farne uno zoom)
	}
	else {
		SetupProspettiva.fov += 2.0f; // Aumentare il campo visivo, equivale ad allontanare la scena (vederlanel suo insieme)
	}
	// Limita il FOV per evitare valori non desiderati
	if (SetupProspettiva.fov < 1.0f) {
		SetupProspettiva.fov = 1.0f;
	}
	if (SetupProspettiva.fov > 180.0f) {
		SetupProspettiva.fov = 180.0f;
	}
	// Richiede il ridisegno della scena
	glutPostRedisplay();
}
void keyboardPressedEvent(unsigned char key, int x, int y)
{
	char* intStr;
	string str;
	switch (key)
	{
	case 'a':
		moveCameraLeft();
		break;

	case 'd':
		moveCameraRight();
		break;

	case 'w':
		moveCameraForward();
		break;

	case 's':
		moveCameraBack();
		break;

	case 'v': // Visualizzazione ancora
		visualizzaAncora = TRUE;
	case 'C': // Si inserisce un cubo

		cont_cubi += 1;
		str = std::to_string(cont_cubi);

		Cubo.nome = "Cubo" + str;

		break;
	case 'P': // Si inserisce una piramide

		cont_pir += 1;
		str = std::to_string(cont_pir);

		Piramide.nome = "Piramide" + str;

		break;
	case 'g': // Si entra in modalit� di operazione traslazione
		OperationMode = TRASLATING;
		Operazione = "TRASLAZIONE";
		break;
	case 'r': // Si entra in modalit� di operazione rotazione
		OperationMode = ROTATING;
		Operazione = "ROTAZIONE";
		break;
	case 'S':
		OperationMode = SCALING; // Si entra in modalit� di operazione scalatura
		Operazione = "SCALATURA";
		break;
	case 27:
		glutLeaveMainLoop();
		break;
		// Selezione dell'asse
	case 'x':
		WorkingAxis = X; // Seleziona l'asse X come asse lungo cui effettuare l'operazione selezionata (tra traslazione, rotazione, scalatura)
		stringa_asse = " Asse X";
		break;
	case 'y':
		WorkingAxis = Y; // Seleziona l'asse Y come asse lungo cui effettuare l'operazione selezionata (tra traslazione, rotazione, scalatura)
		stringa_asse = " Asse Y";
		break;
	case 'z':
		WorkingAxis = Z;
		stringa_asse = " Asse Z"; // Seleziona l'asse Z come asse lungo cui effettuare l'operazione selezionata (tra traslazione, rotazione, scalatura)
		break;

	default:
		break;
	}

	// Selezione dell'asse per le trasformazioni
	switch (WorkingAxis)
	{
	case X:
		asse = glm::vec3(1.0, 0.0, 0.0);

		break;
	case Y:
		asse = glm::vec3(0.0, 1.0, 0.0);

		break;
	case Z:
		asse = glm::vec3(0.0, 0.0, 1.0);

		break;
	default:
		break;
	}

	// I tasti + e -  aggiornano lo spostamento a destra o a sinistra, la rotazione in segno antiorario o in senso orario, la scalatura come amplificazione o diminuizione delle dimensioni

	float amount = .01;
	if (key == '+')
		amount *= 1;

	if (key == '-')
		amount *= -1;

	switch (OperationMode)
	{
		// la funzione modifyModelMatrix(glm::vec3 translation_vector, glm::vec3 rotation_vector, GLfloat angle, GLfloat scale_factor)
		//  definisce la matrice di modellazione che si vuole postmoltiplicare alla matrice di modellazione dell'oggetto selezionato, per poterlo traslare, ruotare scalare.

	case TRASLATING:
		// si passa angle 0 e scale factor =1,
		modifyModelMatrix(asse * amount, asse, 0.0f, 1.0f);
		break;
	case ROTATING:
		// SI mette a zero il vettore di traslazione (vec3(0) e ad 1 il fattore di scale
		modifyModelMatrix(glm::vec3(0), asse, amount * 2.0f, 1.0f);
		break;
	case SCALING:
		// SI mette a zero il vettore di traslazione (vec3(0), angolo di rotazione a 0 e ad 1 il fattore di scala 1+amount.
		modifyModelMatrix(glm::vec3(0), asse, 0.0f, 1.0f + amount);
		break;
	}
	glutPostRedisplay();
}

void keyboardReleasedEvent(unsigned char key, int x, int y)
{
	{
		switch (key)
		{
		case 'v':
			visualizzaAncora = FALSE;
			break;
		default:
			break;
		}
	}
	glutPostRedisplay();
}

vec3 get_ray_from_mouse(float mouse_x, float mouse_y)
{
	// TO DO

	return vec3(1.0, 1.0, 1.0);
}

/*controlla se un raggio interseca una sfera. In caso negativo, restituisce false. Rigetta
le intersezioni dietro l'origine del raggio, e pone  intersection_distance all'intersezione p i� vicina.
*/

bool ray_sphere(vec3 ray_origin_wor, vec3 ray_direction_wor, vec3 sphere_centre_wor, float sphere_radius, float* intersection_distance)
{
	// TO DO

	return false;
}

void mouse(int button, int state, int x, int y)
{
	float xmouse = x;
	float ymouse = y;
	// Costruisce la direzione del raggio in cui punta il mouse
	vec3 ray_wor = get_ray_from_mouse(xmouse, ymouse);

	selected_obj = -1;
	float closest_intersection = 0.0f;
	for (int i = 0; i < Scena.size(); i++)
	{
		float t_dist = 0.0f;
		// Interseco il raggio che esce dalla camera nella direzione del mouse con la sfera centrata nell'ancora di tutti gli oggetti
		// posizionati nel mondo per individuare se c'� intersezione con l'oggetto
		if (ray_sphere(SetupTelecamera.position, ray_wor, Scena[i].ancora_world, raggio_sfera, &t_dist))
		{
			if (selected_obj == -1 || t_dist < closest_intersection)
			{
				selected_obj = i;
				closest_intersection = t_dist;
			}
		}
	}
	if (selected_obj > -1)
		printf("Oggetto selezionato %s \n", Scena[selected_obj].nome.c_str());

	glutPostRedisplay();
}