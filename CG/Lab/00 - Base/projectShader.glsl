void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    // Normalized pixel coordinate with respect to window height
    vec2 uv = fragCoord / iResolution.xy;

    // Initial color (blue)
    vec3 startColor = vec3(0., 0.3, 1.);

    // Final color (black)
    vec3 endColor = vec3(0., 0., 0.);

    // Calculate interpolated color using the gradient coordinate
    vec3 finalColor = mix(startColor, endColor, uv.y);

    // Pseudorandom pattern for stars
    float stars = fract(sin(dot(uv, vec2(12.9898, 78.233))) * 43758.5453);

    // Adjust the threshold to control the density of stars
    float threshold = 0.996;

    // Add a twinkling effect using a smoothstep function
    stars += smoothstep(0.0, 2.0, sin(iTime * 1.0)) * 0.013;
    
    // Add stars to the sky and make them fall
    if (stars > threshold) {
        finalColor = vec3(uv.y);
    }

    // Set the pixel color
    fragColor = vec4(finalColor, 1.0);
}
