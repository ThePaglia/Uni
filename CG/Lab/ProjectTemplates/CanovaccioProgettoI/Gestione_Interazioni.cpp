#include "Lib.h"
extern bool pressing_left, pressing_right, moving;
extern float 	dx_t;
extern float 	dy_t; //velocita verticale (pixel per frame)
extern float angolo; // velocita di movimento orizzontale massima
extern bool drawBB;
extern float  posx_Proiettile, posy_Proiettile;
extern int frame_animazione;
extern float angoloBarca;
 


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

		case 'R':
			angolo += 0.1;
			break;
		case 'r':
			angolo -= 0.1;
			break;

		case 't':
			angolo += 0.1;
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