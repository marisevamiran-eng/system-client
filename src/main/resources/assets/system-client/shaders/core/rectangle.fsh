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
uniform float GlobalAlpha;
uniform float NoiseStrength;
uniform vec2 RadialScale;
uniform float RadialRadius;
uniform vec2 RadialCenter;
uniform vec4 GradientColor1;
uniform vec4 GradientColor2;
uniform vec4 GradientColor3;
uniform vec4 GradientColor4;
uniform vec4 BorderColor1;
uniform vec4 BorderColor2;
uniform vec4 BorderColor3;
uniform vec4 BorderColor4;
uniform int RingMode;
uniform float InnerRadius;
uniform float OuterRadius;
uniform int SegmentCount;
uniform float GapAngle;
uniform float RotationOffset;
uniform int HoverSegment;
uniform int PendingSegment;
uniform int FlashSegment;
uniform float FlashProgress;
uniform int IsFlashDelete;
uniform vec4 RingBaseColor;
uniform vec4 RingHoverColor;
uniform vec4 RingPendingColor;
uniform vec4 RingFlashAddColor;
uniform vec4 RingFlashDeleteColor;

out vec4 fragColor;

void main() {
    // Оптимизированное вычисление alpha
    float alpha = ralpha(Size, FragCoord, CornerRadius, EdgeSoftness);
    
    if (alpha < 0.001) {
        discard;
    }
    
    // Оптимизированный градиент - вычисляем только если нужно
    vec2 uv = FragCoord;
    vec4 topColor = mix(GradientColor1, GradientColor2, uv.x);
    vec4 bottomColor = mix(GradientColor3, GradientColor4, uv.x);
    vec4 gradientColor = mix(topColor, bottomColor, uv.y);
    
    // Финальный цвет с оптимизированным умножением
    float finalAlpha = gradientColor.a * alpha * GlobalAlpha * FragColor.a;
    
    // Используем неиспользуемые uniform'ы минимально
    float unused = (Offset.x + Offset.y + BlurRadius + ShadowOffset + ShadowStrength + 
                   BorderThickness + BorderSoftness + DitherStrength + Margin + 
                   NoiseStrength + RadialScale.x + RadialScale.y + RadialRadius + 
                   RadialCenter.x + RadialCenter.y + GapAngle + RotationOffset + 
                   FlashProgress + InnerRadius + OuterRadius) * 0.0;
    
    vec4 unusedColor = (BorderColor1 + BorderColor2 + BorderColor3 + BorderColor4 + 
                       RingBaseColor + RingHoverColor + RingPendingColor + 
                       RingFlashAddColor + RingFlashDeleteColor) * 0.0;
    
    float unusedInt = float(RingMode + SegmentCount + HoverSegment + PendingSegment + 
                           FlashSegment + IsFlashDelete) * 0.0;

    fragColor = vec4(gradientColor.rgb + unusedColor.rgb + unused + unusedInt, finalAlpha);
}
