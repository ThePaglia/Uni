#version 420 core

layout(location = 0) in vec3 aPos;
layout(location =1 ) in vec4 Color;
out vec4 colore_frag;

void main(){
    gl_Position = vec4(aPos.x,aPos.y,aPos.z,1.0);
    colore_frag= Color;
}
