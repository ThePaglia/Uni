#include <GL/glut.h>
#include <iostream>

const int screenWidth = 800;
const int screenHeight = 800;

const double xMin = -2.0;
const double xMax = 1.0;
const double yMin = -1.5;
const double yMax = 1.5;
const int maxIterations = 100;

void mandelbrotSet() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(xMin, xMax, yMin, yMax);
    glMatrixMode(GL_MODELVIEW);

    glBegin(GL_POINTS);

    for (int i = 0; i < screenWidth; i++) {
        for (int j = 0; j < screenHeight; j++) {
            double x = xMin + (xMax - xMin) * i / (screenWidth - 1);
            double y = yMin + (yMax - yMin) * j / (screenHeight - 1);

            double real = x;
            double imag = y;

            int iterations = 0;
            while (iterations < maxIterations) {
                double real2 = real * real;
                double imag2 = imag * imag;

                if (real2 + imag2 > 4.0) {
                    break;
                }

                imag = 2.0 * real * imag + y;
                real = real2 - imag2 + x;

                iterations++;
            }

            float color = static_cast<float>(iterations) / maxIterations;
            glColor3f(color, color, color);
            glVertex2d(x, y);
        }
    }

    glEnd();
    glFlush();
}

void display() {
    glClear(GL_COLOR_BUFFER_BIT);
    mandelbrotSet();
    glutSwapBuffers();
}

void reshape(int width, int height) {
    glViewport(0, 0, width, height);
    glutPostRedisplay();  // Richiedi un nuovo rendering quando la finestra viene ridimensionata
}

void idle() {
    // Esegui il calcolo del frattale ad ogni iterazione
    mandelbrotSet();
    glutPostRedisplay();  // Richiedi un nuovo rendering
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize(screenWidth, screenHeight);
    glutCreateWindow("Frattale di Mandelbrot");
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(idle);  // Imposta la funzione idle per il calcolo iterativo

    glClearColor(0.0, 0.0, 0.0, 1.0);

    glutMainLoop();
    return 0;
}
