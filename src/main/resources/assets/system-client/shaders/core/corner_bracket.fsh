#version 150

in vec2 FragCoord;
in vec4 FragColor;

uniform vec2 Size;
uniform float Radius;
uniform float Thickness;
uniform vec3 StrokeColor;
uniform float Alpha;
uniform float Offset;

out vec4 fragColor;

float sdRoundedBox(vec2 p, vec2 b, float r) {
    vec2 q = abs(p) - b + r;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;
}

void main() {
    vec2 pixelCoord = FragCoord * (Size + Offset * 2.0) - Offset;
    vec2 center = Size * 0.5;
    
    // Calculate distance to rounded box
    float dist = sdRoundedBox(pixelCoord - center, center, Radius);
    
    // Create corner brackets by masking out center and sides
    vec2 cornerDist = abs(pixelCoord - center) - (center - Radius - Thickness);
    bool isCorner = cornerDist.x > 0.0 && cornerDist.y > 0.0;
    
    if (!isCorner) {
        discard;
    }
    
    // Anti-aliased edge
    float alpha = 1.0 - smoothstep(-1.0, 1.0, abs(dist) - Thickness * 0.5);
    alpha *= Alpha;
    
    if (alpha < 0.01) {
        discard;
    }
    
    fragColor = vec4(StrokeColor, alpha);
}
