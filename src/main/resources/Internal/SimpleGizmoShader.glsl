#section vertex
#version 330 core

layout(location = 0) in vec3 aPos;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform vec3 uColor;

out vec3 vColor;

void main() {
    gl_Position = projectionMatrix * viewMatrix * modelMatrix* vec4(aPos, 1.0);
    vColor = uColor;
}

#section fragment
#version 330 core

in vec3 vColor;
out vec4 FragColor;

void main() {
    FragColor = vec4(vColor, 1.0);
}