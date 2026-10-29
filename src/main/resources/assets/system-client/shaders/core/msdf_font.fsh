#version 150

in vec2 TexCoord;
in vec4 FragColor;

uniform sampler2D Sampler0;
uniform float Range;
uniform float Thickness;
uniform float Smoothness;
uniform int Outline;
uniform float OutlineThickness;
uniform vec4 OutlineColor;
uniform vec4 TextColor;
uniform int UseGradient;
uniform vec4 GradientColor1;
uniform vec4 GradientColor2;
uniform vec2 GradientCenter;

out vec4 fragColor;

float median(vec3 color) {
    return max(min(color.r, color.g), min(max(color.r, color.g), color.b));
}

void main() {
    float dist = median(texture(Sampler0, TexCoord).rgb) - 0.5 + Thickness;
    vec2 h = vec2(dFdx(TexCoord.x), dFdy(TexCoord.y)) * textureSize(Sampler0, 0);
    float pixels = Range * inversesqrt(h.x * h.x + h.y * h.y);
    float alpha = smoothstep(-Smoothness, Smoothness, dist * pixels);
    
    vec4 baseColor;
    
    if (UseGradient > 0) {
        // Use green channel from FragColor as gradient factor (0.0 at top, 1.0 at bottom)
        // Invert to make gradient go from top to bottom (GradientColor1 at top, GradientColor2 at bottom)
        float gradientFactor = 1.0 - FragColor.g;
        baseColor = mix(GradientColor1, GradientColor2, gradientFactor);
        // Use alpha from FragColor for proper transparency
        baseColor.a *= FragColor.a;
    } else {
        // When gradient is disabled, use TextColor and ignore FragColor gradient
        baseColor = TextColor;
    }
    
    vec4 color = vec4(baseColor.rgb, baseColor.a * alpha);

    if (Outline > 0) {
        float outlineAlpha = smoothstep(-Smoothness, Smoothness, (dist + OutlineThickness) * pixels);
        color = mix(vec4(OutlineColor.rgb, OutlineColor.a * outlineAlpha), color, alpha);
    }

    fragColor = color;
}
