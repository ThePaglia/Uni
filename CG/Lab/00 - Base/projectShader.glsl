void mainImage(out vec4 fragColor,in vec2 fragCoord)
{
    // Coordinata normalizzata del pixel rispetto all'altezza della finestra
    vec2 uv=fragCoord/iResolution.xy;
    
    // Colore iniziale (blu)
    vec3 startColor=vec3(0.,.3,1.);
    
    // Colore finale (nero)
    vec3 endColor=vec3(0.,0.,0.);
    
    // Calcola il colore interpolato usando la coordinata del gradiente
    vec3 finalColor=mix(startColor,endColor,uv.y);
    
    // Pseudorandom pattern for stars
    float stars=fract(sin(dot(uv,vec2(12.9898,78.233)))*43758.5453);
    
    // Adjust the threshold to control the density of stars
    float threshold=.996;
    
    // Add stars to the sky
    if(stars>threshold){
        
        finalColor=vec3(uv.y*2.);
    }
    
    // Imposta il colore del pixel
    fragColor=vec4(finalColor,1.);
}