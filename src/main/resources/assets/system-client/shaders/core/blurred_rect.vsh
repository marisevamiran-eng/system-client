#version 150

const vec2[4] RECT_VERTICES_COORDS = vec2[] (
    vec2(0.0, 0.0),
    vec2(0.0, 1.0),
    vec2(1.0, 1.0),
    vec2(1.0, 0.0)
);

vec2 rvertexcoord(int id) {
    return RECT_VERTICES_COORDS[id % 4];
}

in vec3 Position;
in vec4 Color;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 FragCoord;
out vec4 FragColor;
out vec2 TexCoord;

void main() {
    FragCoord = rvertexcoord(gl_VertexID);
    FragColor = Color;
    TexCoord = UV0;

    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
