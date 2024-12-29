#section vertex
#version 330 core
layout (location=0) in vec3 ShPos;
layout (location=1) in vec4 ShColor;
layout (location=2) in vec2 ShTextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec4 fColor;
out vec2 fTextureCoords;

void main(){
        fColor=ShColor;
        fTextureCoords=ShTextureCoords;
        gl_Position = projectionMatrix*viewMatrix* vec4(ShPos,1.0);
}
#section fragment
#version 330 core
uniform sampler2D texttr;
in vec4 fColor;
in vec2 fTextureCoords;

out vec4 color;

void main(){
        color=texture(texttr,fTextureCoords);

}