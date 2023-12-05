#include "Lib.h"
void modifyModelMatrix(vec3 translation_vector, vec3 rotation_vector, GLfloat angle, GLfloat scale_factor);
void keyboardPressedEvent(unsigned char key, int x, int y);
void keyboardReleasedEvent(unsigned char key, int x, int y);
vec3 get_ray_from_mouse(float mouse_x, float mouse_y);
bool ray_sphere(vec3 ray_origin_wor, vec3 ray_direction_wor, vec3 sphere_centre_wor, float sphere_radius, float* intersection_distance);
void mouse(int button, int state, int x, int y);
void mouseActiveMotion(int x, int y);
vec3 getTrackBallPoint(float x, float y);
