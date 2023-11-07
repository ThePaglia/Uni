#version 330 core
 
in vec4 ourColor;
uniform vec2 resolution;
uniform int scelta_fs;
uniform float time;
out vec4 FragColor; // Colore del fragment
 

float myrandom(vec2 co) {
    return fract(sin(dot(co, vec2(12.9898, 78.233))));
}


void main()
{
if (scelta_fs==1)
    FragColor=ourColor;
else
 
   FragColor=vec4(sin(gl_FragCoord.x*0.015),cos(gl_FragCoord.y*0.025), sin(gl_FragCoord.x*0.005)*cos(gl_FragCoord.y*0.005),1 );
    
 
}
 