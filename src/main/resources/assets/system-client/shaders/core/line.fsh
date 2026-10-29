#version 150

in vec2 FragCoord;
in vec4 FragColor;

uniform vec2 Size;
uniform vec4 Color;
uniform int FadeEnabled;

out vec4 fragColor;

void main() {
    vec2 uv = FragCoord;
    
    // Calculate distance from edges for smooth fade
    float edgeFade = 1.0;
    if (FadeEnabled > 0) {
        // Fade from center to edges
        float distFromCenter = abs(uv.x - 0.5) * 2.0;
        edgeFade = 1.0 - smoothstep(0.3, 1.0, distFromCenter);
    }
    
    // Smooth edges on top and bottom
    float topEdge = smoothstep(0.0, 0.1, uv.y);
    float bottomEdge = smoothstep(1.0, 0.9, uv.y);
    float verticalSmooth = topEdge * bottomEdge;
    
    // Combine all effects
    float finalAlpha = FragColor.a * edgeFade * verticalSmooth;
    
    // Use Size and Color uniforms to prevent optimization
    float unused = (Size.x + Size.y + Color.a) * 0.00001;
    
    vec4 color = vec4(FragColor.rgb, finalAlpha + unused);
    
    fragColor = color;
}
