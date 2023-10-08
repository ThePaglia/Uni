#version 420 core
// Viene preso in input il colore interpolato da assegnare al 
//frammento e viene passato in output inalterato 

in vec4 colore_frag;
out vec4 color;

void main(){
    //Viene assegnato lo stesso colore ad ogni pixel
    color = colore_frag;
}
