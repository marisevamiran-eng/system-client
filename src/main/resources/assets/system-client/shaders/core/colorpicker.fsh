#version 150

in vec2 FragCoord;
in vec4 FragColor;

uniform vec2 Size;
uniform int PickerType;
uniform float Hue;
uniform float Saturation;
uniform float Brightness;
uniform float Alpha;
uniform float CornerRadius;

out vec4 fragColor;

// Оптимизированная HSV to RGB конвертация
vec3 hsv2rgb(float h, float s, float v) {
    float c = v * s;
    float x = c * (1.0 - abs(mod(h * 6.0, 2.0) - 1.0));
    float m = v - c;
    
    vec3 rgb;
    if (h < 0.166667) rgb = vec3(c, x, 0.0);
    else if (h < 0.333333) rgb = vec3(x, c, 0.0);
    else if (h < 0.5) rgb = vec3(0.0, c, x);
    else if (h < 0.666667) rgb = vec3(0.0, x, c);
    else if (h < 0.833333) rgb = vec3(x, 0.0, c);
    else rgb = vec3(c, 0.0, x);
    
    return rgb + m;
}

// Оптимизированная маска для скругленных углов
float roundedBoxMask(vec2 uv, vec2 size, float radius) {
    vec2 halfSize = size * 0.5;
    vec2 d = abs(uv * size - halfSize) - (halfSize - radius);
    float dist = length(max(d, 0.0)) + min(max(d.x, d.y), 0.0) - radius;
    return smoothstep(1.0, -1.0, dist);
}

void main() {
    vec2 uv = FragCoord;
    
    // Маска для скругленных углов
    float mask = roundedBoxMask(uv, Size, CornerRadius);
    
    if (mask < 0.01) {
        discard;
    }
    
    vec3 color;
    
    if (PickerType == 0) {
        // Saturation/Brightness picker
        color = hsv2rgb(Hue, uv.x, 1.0 - uv.y);
    } else if (PickerType == 1) {
        // Hue picker
        color = hsv2rgb(uv.x, 1.0, 1.0);
    } else {
        // Alpha picker
        vec3 baseColor = hsv2rgb(Hue, Saturation, Brightness);
        color = mix(vec3(0.5), baseColor, uv.x);
    }
    
    fragColor = vec4(color, Alpha * mask);
}
