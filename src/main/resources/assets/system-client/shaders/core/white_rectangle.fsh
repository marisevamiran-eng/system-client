#version 150

#moj_import <system-client:common.glsl>

in vec2 FragCoord;
in vec4 FragColor;

uniform vec2 Size;
uniform vec2 Offset;
uniform vec4 CornerRadius;
uniform float BlurRadius;
uniform float ShadowOffset;
uniform float ShadowStrength;
uniform float BorderThickness;
uniform float BorderSoftness;
uniform float EdgeSoftness;
uniform float DitherStrength;
uniform float Margin;
uniform float NoiseStrength;
uniform float GlobalAlpha;
uniform vec4 GradientColor1;
uniform vec4 GradientColor2;
uniform vec4 GradientColor3;
uniform vec4 GradientColor4;
uniform vec3 BorderColor;

out vec4 fragColor;

void main() {
    // Use improved ralpha function for better anti-aliasing
    float alpha = ralpha(Size, FragCoord, CornerRadius, EdgeSoftness);
    
    // Use all uniforms to prevent optimization
    float unused = (Offset.x + Offset.y + BlurRadius + ShadowOffset + ShadowStrength + 
                   BorderThickness + BorderSoftness + DitherStrength + Margin + NoiseStrength) * 0.00001;
    
    vec4 gradientMix = (GradientColor1 + GradientColor2 + GradientColor3 + GradientColor4) * 0.00001;
    vec3 borderMix = BorderColor * 0.00001;
    
    vec4 color = vec4(FragColor.rgb + gradientMix.rgb + borderMix + unused, FragColor.a * alpha * GlobalAlpha);

    if (color.a < 0.001) {
        discard;
    }

    fragColor = color;
}
