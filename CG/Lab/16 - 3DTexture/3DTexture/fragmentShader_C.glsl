#version 330 core
in vec4 ourColor;
out vec4 FragColor;  

in vec3 N, L,R,V;
  uniform float time;
 uniform vec2 resolution;
uniform int sceltaVS;

struct PointLight{
	vec3 position;
	vec3 color;
	float power;
 };

 //definizione di una variabile uniform che ha la struttura PointLight
uniform PointLight light;
struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
}; 
uniform Material material;

 #define pi 3.14159235659
 uniform sampler2D id_tex;
 uniform sampler2D id_tex1;
 in vec2 frag_coord_st;

void main()
{
      if (sceltaVS==0 || sceltaVS==3)  //No shading, oppure shading interpolativo senza texture
         FragColor=ourColor;

     if(sceltaVS==1 || sceltaVS==4)  //Caso no-shading, shading interpolativo, shading bandiera
      FragColor = mix(texture(id_tex1,frag_coord_st),texture(id_tex,frag_coord_st), 0.3);
      //FragColor= mix(ourColor,texture(id_tex,frag_coord_st),0.3);
   
   
       if (sceltaVS==2) //Shading di Phong: il modello di illuminazione viene implementato nel fragment shader
	 {
            vec3 ambient = light.power * material.ambient;

            //diffuse
            float coseno_angolo_theta= max(dot(L,N), 0);

            vec3 diffuse = light.power * light.color * coseno_angolo_theta * material.diffuse;

            //speculare
            float coseno_angolo_alfa =  pow(max(dot(V,R),0),material.shininess);

            vec3 specular =  light.power * light.color * coseno_angolo_alfa * material.specular;

            FragColor = vec4(ambient + diffuse + specular, 1.0);      
    }   

    

}