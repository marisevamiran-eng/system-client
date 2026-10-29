#version 150

#moj_import <system-client:common.glsl>

in vec2 FragCoord;
in vec4 FragColor;

uniform sampler2D Sampler0;
uniform vec2 Size;
uniform vec2 Offset;
uniform vec4 Radius;
uniform float Smoothness;
uniform float CornerSmoothness;
uniform float GlobalAlpha;
uniform float FresnelPower;
uniform float DistortStrength;
uniform float BlurRadius;
uniform float Brightness;
uniform float ShadowOffset;
uniform float ShadowStrength;
uniform float ShadowBlurRadius;

out vec4 fragColor;

float roundedBoxSDF(vec2 p, vec2 b, vec4 r, float smoothness) {
    r.xy = (p.x > 0.0) ? r.xy : r.zw;
    r.x = (p.y > 0.0) ? r.x : r.y;
    vec2 q = abs(p) - b + r.x;
    vec2 q_clamped = max(q, 0.0);
    float len = pow(pow(q_clamped.x, smoothness) + pow(q_clamped.y, smoothness), 1.0/smoothness);
    return min(max(q.x, q.y), 0.0) + len - r.x;
}

void main() {
    vec2 center = Size * 0.5;
    vec2 box_half_size = center - 1.0;
    vec2 pos = (FragCoord * Size) - center;

    float distance = roundedBoxSDF(-pos, box_half_size, Radius, CornerSmoothness);
    float alpha = 1.0 - smoothstep(1.0 - Smoothness, 1.0, distance);

    float distToEdge = abs(roundedBoxSDF(pos, box_half_size, Radius, CornerSmoothness));
    float max_dist_norm = min(box_half_size.x, box_half_size.y);
    float edge_gradient = 1.0 - clamp(distToEdge / max_dist_norm, 0.0, 1.0);

    float fresnel;
    if (FresnelPower > 20.0) {
        fresnel = exp(FresnelPower * log(clamp(edge_gradient, 0.001, 1.0)));
    } else {
        fresnel = pow(edge_gradient, FresnelPower);
    }
    fresnel = clamp(fresnel, 0.0, 1.0);

    vec2 dir = normalize(pos);
    
    // Calculate texture coordinates from gl_FragCoord
    vec2 texCoord = gl_FragCoord.xy / textureSize(Sampler0, 0);
    
    // Apply blur if needed
    vec3 texColor;
    if (BlurRadius > 0.5) {
        vec2 texelSize = 1.0 / textureSize(Sampler0, 0);
        vec3 blurred = vec3(0.0);
        float totalWeight = 0.0;
        
        const float DPI = 6.28318530718;
        const float STEP = DPI / 8.0;
        
        for (float d = 0.0; d < DPI; d += STEP) {
            for (float i = 0.25; i <= 1.0; i += 0.25) {
                vec2 offset = vec2(cos(d), sin(d)) * texelSize * BlurRadius * i;
                vec2 sampleCoord = texCoord + dir * fresnel * DistortStrength + offset;
                blurred += texture(Sampler0, sampleCoord).rgb;
                totalWeight += 1.0;
            }
        }
        texColor = blurred / totalWeight;
    } else {
        vec2 distortedTexCoord = texCoord + dir * fresnel * DistortStrength;
        texColor = texture(Sampler0, distortedTexCoord).rgb;
    }

    vec3 finalColor = texColor * FragColor.rgb * Brightness;
    float finalAlpha = alpha * GlobalAlpha;

    // Use unused uniforms to prevent optimization
    float unused = (Offset.x + Offset.y + ShadowOffset + ShadowStrength + ShadowBlurRadius) * 0.00001;
    finalColor += vec3(unused);

    if (finalAlpha < 0.001) {
        discard;
    }

    fragColor = vec4(finalColor, finalAlpha);
}
