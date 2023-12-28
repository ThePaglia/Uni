
#include "Strutture.h"
extern Forma Curva;
extern int pval;
/// /////////////////////////////////// Disegna geometria //////////////////////////////////////
//Per Curve di hermite
#define PHI0(t)  (2.0*t*t*t-3.0*t*t+1)
#define PHI1(t)  (t*t*t-2.0*t*t+t)
#define PSI0(t)  (-2.0*t*t*t+3.0*t*t)
#define PSI1(t)  (t*t*t-t*t)
 

float dx(int i, float* t, float Tens, float Bias, float Cont, Forma* Fig)
{
	if (i == 0)
		return  0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP[i + 1].x - Fig->CP[i].x) / (t[i + 1] - t[i]);
	if (i == Fig->CP.size() - 1)
		return  0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP[i].x - Fig->CP[i - 1].x) / (t[i] - t[i - 1]);

	if (i % 2 == 0)
		return  0.5 * (1 - Tens) * (1 + Bias) * (1 + Cont) * (Fig->CP.at(i).x - Fig->CP.at(i - 1).x) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).x - Fig->CP.at(i).x) / (t[i + 1] - t[i]);
	else
		return  0.5 * (1 - Tens) * (1 + Bias) * (1 - Cont) * (Fig->CP.at(i).x - Fig->CP.at(i - 1).x) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 + Cont) * (Fig->CP.at(i + 1).x - Fig->CP.at(i).x) / (t[i + 1] - t[i]);
}
float dy(int i, float* t, float Tens, float Bias, float Cont, Forma* Fig)
{
	if (i == 0)
		return 0.5 * (1.0 - Tens) * (1.0 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
	if (i == Fig->CP.size() - 1)
		return  0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]);

	if (i % 2 == 0)
		return  0.5 * (1 - Tens) * (1 + Bias) * (1 + Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 - Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
	else
		return  0.5 * (1 - Tens) * (1 + Bias) * (1 - Cont) * (Fig->CP.at(i).y - Fig->CP.at(i - 1).y) / (t[i] - t[i - 1]) + 0.5 * (1 - Tens) * (1 - Bias) * (1 + Cont) * (Fig->CP.at(i + 1).y - Fig->CP.at(i).y) / (t[i + 1] - t[i]);
}

float DX(int i, float* t)
{
	//Nei vertici di controllo per i quali non sono stati modificati i parametri Tens, Bias, Cont il valore della derivata della componente x della curva   quello originale, altrimenti   quello che   stato modificato nella funzione 
	//keyboardfunc  in seguito alla modifica dei valori Tens, Bias e Cont.

	if (Curva.Derivata.at(i).x == 0)
		return dx(i, t, 0.0, 0.0, 0.0, &Curva);

	if (Curva.Derivata.at(i).x != 0)
		return Curva.Derivata.at(i).x;

}

float DY(int i, float* t)
{
	// Nei vertici di controllo per i quali non sono stati modificati i parametri Tens, Bias, Cont il valore della derivata della componente y della curva   quello originale, altrimenti   quello che   stato modificato nella funzione
		//keyboardfunc  in seguito alla modifica dei valori Tens, Bias e Cont.

	if (Curva.Derivata.at(i).y == 0)
		return dy(i, t, 0.0, 0.0, 0.0, &Curva);

	if (Curva.Derivata.at(i).y != 0)
		return Curva.Derivata.at(i).y;

}

 void InterpolazioneHermite(float* t, Forma * Fig, vec3 centro, vec4 color_top, vec4 color_bot)
	{
		float p_t = 0, p_b = 0, p_c = 0, x, y;
		float passotg = 1.0 / (float)(pval - 1);

		float tg = 0, tgmapp, ampiezza;
		int i = 0;
		int is = 0; //indice dell'estremo sinistro dell'intervallo [t(i),t(i+1)] a cui il punto tg
					//appartiene


		Fig->vertici.clear();
		Fig->colors.clear();
		Fig->vertici.push_back(centro);
		Fig->colors.push_back(color_bot);

		for (tg = 0; tg <= 1; tg += passotg)
		{
			if (tg > t[is + 1]) is++;

			ampiezza = (t[is + 1] - t[is]);
			tgmapp = (tg - t[is]) / ampiezza;

			x = Fig->CP[is].x * PHI0(tgmapp) + DX(is, t) * PHI1(tgmapp) * ampiezza + Fig->CP[is + 1].x * PSI0(tgmapp) + DX(is + 1, t) * PSI1(tgmapp) * ampiezza;
			y = Fig->CP[is].y * PHI0(tgmapp) + DY(is, t) * PHI1(tgmapp) * ampiezza + Fig->CP[is + 1].y * PSI0(tgmapp) + DY(is + 1, t) * PSI1(tgmapp) * ampiezza;

			Fig->vertici.push_back(vec3(x, y, 0.0));
			Fig->colors.push_back(color_top);
		}


	}
	



void costruisci_formaHermite(vec3 centro, vec4 color_top, vec4 color_bot, Forma* forma)
{
	float* t;
	int i;
	float xmin=0.0, xmax=0.0, ymin=0.0, ymax=0.0;

	if (forma->CP.size() > 1)
	{
		t = new float[forma->CP.size()];
		int i;
		float step = 1.0 / (float)(forma->CP.size() - 1);

		for (i = 0; i < Curva.CP.size(); i++)
			t[i] = (float)i * step;


		InterpolazioneHermite(t, forma, centro, color_top, color_bot);

		forma->nv = Curva.vertici.size();
	}
	//Costruisco xmin,ymin, xmax,ymax peer identificare il Bounding Box del Proiettile
	for (i = 1; i < forma->nv; i++)
	{

		if (forma->vertici[i].x < xmin)
			xmin = forma->vertici[i].x;
	}

	for (i = 1; i < forma->nv; i++)
	{

		if (forma->vertici[i].x > xmax)
			xmax = forma->vertici[i].x;
	}
	for (i = 1; i < forma->nv; i++)
	{
		if (forma->vertici[i].y <= ymin)
			ymin = forma->vertici[i].y;
	}

	for (i = 1; i < forma->nv; i++)
	{
		if (forma->vertici[i].y > ymax)
			ymax = forma->vertici[i].y;
	}
	//Aggiorno i valori del corner più in basso a sinistra (corner_b) e del corner più in alto a destra (conrner_t)

	forma->corner_b_obj = vec4(xmin, ymin, 0.0, 1.0);
	forma->corner_t_obj = vec4(xmax, ymax, 0.0, 1.0);
	//Aggiungo i vertici della spezzata per costruire il bounding box
	forma->vertici.push_back(vec3(xmin, ymin, 0.0));
	forma->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	forma->vertici.push_back(vec3(xmax, ymin, 0.0));
	forma->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	forma->vertici.push_back(vec3(xmax, ymax, 0.0));
	forma->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	forma->vertici.push_back(vec3(xmin, ymin, 0.0));
	forma->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	forma->vertici.push_back(vec3(xmin, ymax, 0.0));
	forma->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	forma->vertici.push_back(vec3(xmax, ymax, 0.0));
	forma->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));

	//Aggiorno il numero dei vertici della formaura
	forma->nv = forma->vertici.size();
	forma->sceltaFs = 1;
	forma->render = GL_TRIANGLE_FAN;
}