#version 150

float rdist(vec2 pos, vec2 size, vec4 radius) {
    radius.xy = (pos.x > 0.0) ? radius.xy : radius.wz;
    radius.x  = (pos.y > 0.0) ? radius.x : radius.y;
    vec2 v = abs(pos) - size + radius.x;
    return min(max(v.x, v.y), 0.0) + length(max(v, 0.0)) - radius.x;
}

float ralpha(vec2 size, vec2 coord, vec4 radius, float smoothness) {
    vec2 center = size * 0.5;
    float dist = rdist(center - (coord * size), center - 1.0, radius);
    return 1.0 - smoothstep(1.0 - smoothness, 1.0, dist);
}

in vec2 FragCoord;
in vec4 FragColor;

uniform vec2 uSize;
uniform vec4 uRadius;
uniform float uSmoothness;

out vec4 fragColor;

void main() {
    float alpha = ralpha(uSize, FragCoord, uRadius, uSmoothness);
    vec4 color = vec4(FragColor.rgb, FragColor.a * alpha);

    if (color.a == 0.0) {
        discard;
    }

    fragColor = color;
}
