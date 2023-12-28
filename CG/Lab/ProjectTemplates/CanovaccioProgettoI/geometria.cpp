#include "Strutture.h"
#include "Lib.h"
#define  PI   3.14159265358979323846
#pragma warning(disable:4996)
extern Forma Curva, Derivata;

void costruisci_piano(Forma* fig)
{
	int i;
	fig->vertici.push_back(vec3(-1.0, -1.0, 0.0));
	fig->vertici.push_back(vec3(1.0, -1.0, 0.0));
	fig->vertici.push_back(vec3(-1.0, 1.0, 0.0));
	fig->vertici.push_back(vec3(1.0, 1.0, 0.0));
	for (i = 0; i < fig->vertici.size(); i++)
		fig->colors.push_back(vec4(0.13, 0.44, 0.70, 1.0));
	fig->nv = fig->vertici.size();
	fig->sceltaFs = 0;
	fig->render = GL_TRIANGLE_STRIP;
}

void costruisci_cuore(float cx, float cy, float raggiox, float raggioy, Forma* fig) {

	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t;


	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(255.0 / 255.0, 75.0 / 255.0, 0.0, 1.0));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		fig->vertici.push_back(vec3(cx + raggiox * (16 * pow(sin(t), 3)), cy + raggioy * ((13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t))), 0.0));
		//Colore 
		fig->colors.push_back(vec4(1.0, 204.0 / 255.0, 0.0, 1.0));


	}
	fig->nv = fig->vertici.size();
	fig->sceltaFs = 1;
	fig->render = GL_TRIANGLE_FAN;

}
void costruisci_farfalla(float cx, float cy, float raggiox, float raggioy, Forma* fig)
{

	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t, xx, yy;
	float xmax = 0;
	float xmin = 0;

	float ymax = 0;
	float ymin = 0;


	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(150.0 / 255.0, 75.0 / 255.0, 0.0, 1.0));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		xx = cx + raggiox * (sin(t) * (exp(cos(t)) - 2 * cos(4 * t)) + pow(sin(t / 12), 5));
		yy = cy + raggioy * (cos(t) * (exp(cos(t)) - 2 * cos(4 * t)) + pow(sin(t / 12), 5));
		fig->vertici.push_back(vec3(xx, yy, 0.0));
		//Colore 
		fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0)); //Nota che la quarta componente corrisponde alla trasparenza del colore
	}

	fig->nv = fig->vertici.size();
	fig->sceltaFs = 1;
	fig->render = GL_TRIANGLE_FAN;
	fig->nv = fig->vertici.size();

	//Calcolo di xmin, ymin, xmax, ymax

	for (i = 1; i < fig->nv; i++)
		if (fig->vertici[i].x <= xmin)
			xmin = fig->vertici[i].x;


	for (i = 1; i < fig->nv; i++)
		if (fig->vertici[i].x > xmax)
			xmax = fig->vertici[i].x;

	for (i = 1; i < fig->nv; i++)
		if (fig->vertici[i].y <= ymin)
			ymin = fig->vertici[i].y;


	for (i = 1; i < fig->nv; i++)
		if (fig->vertici[i].y > ymax)
			ymax = fig->vertici[i].y;

	//Aggiorno i valori del corner pi� in basso a sinistra (corner_b) e del corner pi� in alto a destra (conrner_t)

	fig->corner_b_obj = vec4(xmin, ymin, 0.0, 1.0);
	fig->corner_t_obj = vec4(xmax, ymax, 0.0, 1.0);
	//Aggiungo i vertici della spezzata per costruire il bounding box
	fig->vertici.push_back(vec3(xmin, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmin, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmin, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));

	//Aggiorno il numero dei vertici della figura
	fig->nv = fig->vertici.size();

}

void costruisci_proiettile(float cx, float cy, float raggiox, float raggioy, Forma* fig) {

	int i;
	float stepA = (2 * PI) / fig->nTriangles;
	float t;
	float xmax = 0;
	float xmin = 0;

	float ymax = 0;
	float ymin = 0;

	fig->vertici.push_back(vec3(cx, cy, 0.0));

	fig->colors.push_back(vec4(255.0 / 255.0, 75.0 / 255.0, 0.0, 1.0));

	for (i = 0; i <= fig->nTriangles; i++)
	{
		t = (float)i * stepA;
		fig->vertici.push_back(vec3(cx + raggiox * cos(t), cy + raggioy * sin(t), 0.0));
		//Colore 
		fig->colors.push_back(vec4(1.0, 204.0 / 255.0, 0.0, 1.0));
	}

	fig->nv = fig->vertici.size();

	//Costruisco xmin,ymin, xmax,ymax peer identificare il Bounding Box del Proiettile
	for (i = 1; i < fig->nv; i++)
	{

		if (fig->vertici[i].x < xmin)
			xmin = fig->vertici[i].x;
	}

	for (i = 1; i < fig->nv; i++)
	{

		if (fig->vertici[i].x > xmax)
			xmax = fig->vertici[i].x;
	}
	for (i = 1; i < fig->nv; i++)
	{
		if (fig->vertici[i].y <= ymin)
			ymin = fig->vertici[i].y;
	}

	for (i = 1; i < fig->nv; i++)
	{
		if (fig->vertici[i].y > ymax)
			ymax = fig->vertici[i].y;
	}
	//Aggiorno i valori del corner pi� in basso a sinistra (corner_b) e del corner pi� in alto a destra (conrner_t)

	fig->corner_b_obj = vec4(xmin, ymin, 0.0, 1.0);
	fig->corner_t_obj = vec4(xmax, ymax, 0.0, 1.0);
	//Aggiungo i vertici della spezzata per costruire il bounding box
	fig->vertici.push_back(vec3(xmin, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmin, ymin, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmin, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));
	fig->vertici.push_back(vec3(xmax, ymax, 0.0));
	fig->colors.push_back(vec4(1.0, 0.0, 0.0, 1.0));

	//Aggiorno il numero dei vertici della figura
	fig->nv = fig->vertici.size();
	fig->sceltaFs = 1;
	fig->render = GL_TRIANGLE_FAN;

}

void crea_punti_forma_da_file(const char* nomeFile, const char* modalita, Forma* Curva)
{
	int i;
	struct Dati {
		float x;
		float y;
		float z;
	};

	FILE* file = fopen(nomeFile, modalita);
	if (file == NULL) {
		perror("Impossibile aprire il file");
	}

	// Vettore per memorizzare i dati
	struct Dati dati[1000]; // Supponiamo che ci siano al massimo 100 righe nel file

	int riga = 0;
	while (fscanf(file, "%f %f %f", &dati[riga].x, &dati[riga].y, &dati[riga].z) == 3) {
		// Incrementa l'indice della riga
		riga++;

		// Puoi aggiungere un controllo qui per evitare il superamento dell'array dati
		if (riga >= 1000) {
			printf("Troppe righe nel file. L'array dati   stato completamente riempito.\n");
			break;
		}
	}

	// Chiudi il file
	fclose(file);


	for (int i = 0; i < riga; i++) {
		Curva->CP.push_back(vec3(dati[i].x, dati[i].y, dati[i].z));
		Curva->Derivata.push_back(vec3(0.0, 0.0, 0.0));
	}

}