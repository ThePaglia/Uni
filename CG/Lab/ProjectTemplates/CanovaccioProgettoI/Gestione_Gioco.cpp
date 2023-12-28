#include "Lib.h"
#include "Strutture.h"
extern int frame_animazione;
extern float angoloBarca,dx_f;
#define  PI   3.14159265358979323846
double  degtorad(double angle) {
	return angle * PI / 180;
}


void update_Barca(int value)
{
	//Aggiorno l'angolo di fluttuazione del cannone
	frame_animazione += 4;
	if (frame_animazione >= 360) {
		frame_animazione -= 360;

	}
	angoloBarca = cos(degtorad(frame_animazione)) * 10;
 
	glutTimerFunc(250, update_Barca, 0);
	glutPostRedisplay();
}



void update_f(int value)
{
	dx_f = dx_f + 0.01;

	glutTimerFunc(250, update_f, 0);
	glutPostRedisplay();
}
bool checkCollision(Forma obj1, Forma obj2) {
	// guardo collisioni su asse x
	bool collisionX = obj1.corner_b.x <= obj2.corner_t.x &&
		obj1.corner_t.x >= obj2.corner_b.x;

	// guardo collisioni su asse y
	bool collisionY = obj1.corner_b.y <= obj2.corner_t.y &&
		obj1.corner_t.y >= obj2.corner_b.y;
	//Si ha collisione se c'è collisione sia nella direzione x che nella direzione y

	return collisionX && collisionY;
}