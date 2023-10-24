#version 330 core
 
in vec4 ourColor;
uniform vec2 resolution;
uniform int scelta_fs;
uniform float time;
out vec4 FragColor; // Colore del fragment
vec3 finalColor;
    // Parametri delle onde
    const int numLayers = 5;
    const float amplitudes[numLayers] = float[](0.3, 0.4, 0.6, 0.8, 1.0);
    const float frequencies[numLayers] = float[](3.18, 2.34, 2.14, 1.4, 1.23);
    const float levels[numLayers] = float[](-0.8, -0.6, -0.4, -0.2, 0.0);

float onde(float x, float amplitude) {
	float n = sin(x*8.0 + time) * 0.05 +
		sin(x*27.3 + time*0.5) * 0.005 + 
		sin(time) * 0.01;
	return n * amplitude;
}

float myrandom(vec2 co) {
    return fract(sin(dot(co, vec2(12.9898, 78.233))));
}


void main()
{
if (scelta_fs==1)
    FragColor=ourColor;
else
{
//Mappiamo le coordinate del frammento nel sistema NDC (sistema di Cordinate Normalizzate di Device)
     vec2 ndc = vec2 ((gl_FragCoord.x / resolution.x- 0.5) * 2, (gl_FragCoord.y / resolution.y - 0.5) * 2);
 
const float waterColorFactor = 0.8;
 
 
    // Inizializzazione del colore del mare
    float seaColor = 0.0;

    // Verifica se il frammento è nella parte inferiore dello schermo
    if (ndc.y > -1.0 && ndc.y < -0.1) {
        // Generazione di rumore sinusoidale a diverse frequenze
        float noiseValues[numLayers];
        for (int i = 0; i < numLayers; i++) {
                 noiseValues[i] = onde(ndc.x,amplitudes[i]);
             }

        // Calcolo del colore del mare in base all'altezza in NDC
        for (int i = 0; i < numLayers; i++) {
            if (ndc.y < levels[i] - noiseValues[i]) {
                seaColor = float(i + 1) / float(numLayers);
                break;
            }
        }
         vec3 water_color = seaColor * vec3(0.2,0.1,1.0);;
		 finalColor = mix(vec3(ourColor.r,ourColor.g,ourColor.b),water_color,0.8);
         FragColor= vec4(finalColor, 1.0);
      }
      else
      {
      vec3  ColorS = vec3(0.13,0.44,0.70);
        int numCircles = 20;     // Numero di cerchi
        float cloudDensity = 0.2;  // Densità delle nuvole
         float cloudScale = 0.3;    // Scala delle nuvole
         for (int i = 0; i < numCircles; i++) {
            // Generiamo posizioni casuali per i cerchi
            vec2 circleCenter = vec2(myrandom(vec2(float(i), time)) * 2.0 - 1.0, myrandom(vec2(time, float(i))) * 2.0 - 1.0);
            // Calcoliamo la distanza dal centro del cerchio
            float distanceToCenter = length(ndc - circleCenter);
            
            // Calcoliamo l'altezza del cerchio basata sulla distanza dal centro
            float cloudHeight = smoothstep(cloudScale, 0.0, distanceToCenter);
            
            // Aggiungiamo l'altezza del cerchio al colore finale
            ColorS += vec3(cloudDensity) * cloudHeight;
    
            }
            FragColor= vec4(ColorS, 1.0);
       
       } 
    
}
}
 