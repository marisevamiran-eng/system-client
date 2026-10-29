#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;
uniform vec2 BlurDir;
uniform float Radius;

out vec4 fragColor;

void main() {
    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    float totalAlpha = 0.0;
    float totalSamples = 0.0;
    
    float r = Radius;
    
    for(float x = -r; x <= r; x += 1.0) {
        vec2 offset = BlurDir * x / InSize;
        vec4 sample = texture(DiffuseSampler, texCoord + offset);
        
        float strength = 1.0 - abs(x / r);
        
        blurred.rgb += sample.rgb * strength;
        totalStrength += strength;
        totalAlpha += sample.a * strength;
        totalSamples += 1.0;
    }
    
    fragColor = vec4(blurred.rgb / totalStrength, totalAlpha / totalStrength);
}
