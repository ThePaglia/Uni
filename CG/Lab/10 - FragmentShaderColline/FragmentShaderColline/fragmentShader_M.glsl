#version 330 core
 
in vec4 ourColor;
uniform vec2 resolution;
uniform vec3 mountainColor1; // Colore della prima montagna
uniform vec3 mountainColor2; // Colore della seconda montagna
uniform vec3 skyColor; // Colore del cielo
uniform int scelta_fs;
out vec4 FragColor; // Colore del fragment

 
void main()
{
if (scelta_fs==1)
    FragColor=ourColor;
else
{
//Mappiamo le coordinate del frammento nel sistema NDC (sistema di Cordinate Normalizzate di Device)
     vec2 ndc = vec2 ((gl_FragCoord.x / resolution.x- 0.5) * 2, (gl_FragCoord.y / resolution.y - 0.5) * 2);
      
    // Calcoliamo l'altezza delle montagne in base alla coordinata x del fragment
    float mountainHeight1 = 0.5 * sin((ndc.x + 1.0)*5.0 ) + 0.5;
    float mountainHeight2 = 0.3 * sin((ndc.x + 1.0) * 7.5) + 0.7;

    // Verifichiamo se il fragment è al di sopra delle montagne o nel cielo
    if (ndc.y > mountainHeight1 && ndc.y > mountainHeight2) {
        // Il fragment è nel cielo, quindi lo coloriamo con il colore del cielo
        FragColor = vec4(skyColor, 1.0);
    } else {
        // Il fragment è all'interno delle montagne, quindi lo coloriamo con il colore della montagna
        vec3 finalColor =mix(mountainColor1, mountainColor2, ndc.x);
        FragColor = vec4(finalColor, 1.0);
        }
}  
}   


 