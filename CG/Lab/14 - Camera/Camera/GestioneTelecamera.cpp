// Movimento della telecamera
#include "Lib.h"
#include "Strutture.h"
extern ViewSetup SetupTelecamera;
extern float cameraSpeed;
extern int height, widht;
extern bool firstMouse;
extern float lastX, lastY, Theta, Phi;
void moveCameraForward()
{
	vec4 direction = SetupTelecamera.target - SetupTelecamera.position; // Direzione lungo cui si sposta la telecamera in coordinate del mondo
	SetupTelecamera.position += direction * cameraSpeed;
}

void moveCameraBack()
{
	vec4 direction = SetupTelecamera.target - SetupTelecamera.position; // Direzione lungo cui si sposta la telecamera in coordinate del mondo
	SetupTelecamera.position -= direction * cameraSpeed;
}

void moveCameraLeft()
{
	vec3 direction = SetupTelecamera.target - SetupTelecamera.position; // Direzione lungo cui si sposta la telecamera in coordinate del mondo
	vec3 slide_vector = cross(direction, vec3(SetupTelecamera.upVector)) * cameraSpeed;
	SetupTelecamera.position -= vec4(slide_vector, .0);
	SetupTelecamera.target -= vec4(slide_vector, .0);
}

void moveCameraRight()
{
	vec3 direction = SetupTelecamera.target - SetupTelecamera.position; // Direzione lungo cui si sposta la telecamera in coordinate del mondo
	vec3 slide_vector = cross(direction, vec3(SetupTelecamera.upVector)) * cameraSpeed;
	SetupTelecamera.position += vec4(slide_vector, .0);
	SetupTelecamera.target += vec4(slide_vector, .0);
}

void my_passive_mouse(int xpos, int ypos)
{
	float alfa = 0.05; // serve ridimensionare l'offset tra due posizioni successive del mosue
	ypos = height - ypos;
	if (firstMouse)
	{
		lastX = xpos;
		lastY = ypos;
		firstMouse = false;
	}

	float xoffset = xpos - lastX;
	float yoffset = ypos - lastY;
	lastX = xpos;
	lastY = ypos;

	xoffset *= alfa;
	yoffset *= alfa;
	Theta += xoffset; // Aggiorno l'angolo Theta sommandogli l'offset della posizione x del mouse
	Phi += yoffset;	  // Aggiorno l'angolo Phi sommandogli l'offset della posizione y del mouse

	// Facciamo si' che l'angolo di Phi vari tra -90 e 90.
	if (Phi > 89.0f)
		Phi = 89.0f;
	if (Phi < -89.0f)
		Phi = -89.0f;

	// Ricavo le coordinate x,y,z del punto che sulla sfera individuato dagli angoli Theta e Phi  //TO DO
	vec3 front;
	front.x = cos(radians(Theta)) * cos(radians(Phi));
	front.y = sin(radians(Phi));
	front.z = sin(radians(Theta)) * cos(radians(Phi));
	SetupTelecamera.direction = vec4(normalize(front), 0.0);					   // Aggiorno la direzione della telecamera
	SetupTelecamera.target = SetupTelecamera.position + SetupTelecamera.direction; // aggiorno il punto in cui guarda la telecamera

	glutPostRedisplay();
}