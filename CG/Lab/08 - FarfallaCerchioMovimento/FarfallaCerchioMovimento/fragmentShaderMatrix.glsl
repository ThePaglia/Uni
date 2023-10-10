#version 420 core

// Interpolated values from the vertex shaders

in vec4 ourColor;
out vec4 FragColor;  

void main(){
//Viene assegnato ad ogni frammento il colore interpolato
	FragColor=ourColor;

}