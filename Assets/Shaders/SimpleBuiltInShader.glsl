#section
layout (location=0) in vec3 ShPos;
layout (location=1) in vec4 ShColor;

out vec4 fColor;

void main(){
        fColor=ShColor;
        gl_Position = vec4(ShPos,1.0);
}

#section
in vec4 fColor;
out vec4 color;

void main(){
        color=fColor;
}