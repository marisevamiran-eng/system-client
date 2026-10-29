#version 150

in vec2 FragCoord;
in vec4 FragColor;

uniform vec2 Size;
uniform vec2 Offset;
uniform float CornerRadius;
uniform float BorderThickness;
uniform float BorderSoftness;
uniform float Margin;
uniform float GlobalAlpha;
uniform vec4 BorderColor;

out vec4 fragColor;

// Signed distance function for rounded rectangle
float sdRoundedBox(vec2 p, vec2 b, float r) {
    vec2 q = abs(p) - b + r;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;
}

void main() {
    // Calculate pixel position in local space
    vec2 pixelPos = FragCoord * (Size + Offset * 2.0) - Offset;
    vec2 halfSize = Size * 0.5;
    
    // Calculate distance from center
    vec2 localPos = pixelPos - halfSize;
    
    // Outer edge (full size with corner radius)
    float outerDist = sdRoundedBox(localPos, halfSize, CornerRadius);
    
    // Inner edge (reduced by border thickness)
    float innerRadius = max(0.0, CornerRadius - BorderThickness);
    vec2 innerSize = halfSize - vec2(BorderThickness);
    float innerDist = sdRoundedBox(localPos, innerSize, innerRadius);
    
    // Create sharp border with anti-aliasing
    float outerEdge = smoothstep(BorderSoftness, -BorderSoftness, outerDist);
    float innerEdge = smoothstep(BorderSoftness, -BorderSoftness, innerDist);
    
    // Border is the difference between outer and inner
    float borderMask = outerEdge * (1.0 - innerEdge);
    
    // Apply colors and alpha
    vec4 color = vec4(BorderColor.rgb, BorderColor.a * borderMask * GlobalAlpha);
    
    // Discard fully transparent pixels
    if (color.a < 0.001) {
        discard;
    }
    
    fragColor = color;
}
