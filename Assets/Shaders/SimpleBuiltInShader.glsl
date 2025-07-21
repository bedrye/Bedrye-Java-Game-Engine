#section vertex
#version 330 core
layout (location=0) in vec3 ShPos;
layout (location=1) in vec4 ShColor;
layout (location=2) in vec2 ShTextureCoords;
layout (location=3) in vec3 ShVertexNormal;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;


out vec4 fColor;
out vec2 fTextureCoords;
out vec3 fVertexNormal;
out vec3 fVertexPos;

void main(){
        fColor=ShColor;
        fTextureCoords=ShTextureCoords;
        vec4 mvPose = viewMatrix*modelMatrix* vec4(ShPos,1.0);
        gl_Position = projectionMatrix* mvPose;
        fVertexNormal = normalize(viewMatrix*modelMatrix * vec4(ShVertexNormal, 0.0)).xyz;
        fVertexPos = mvPose.xyz;
}
#section fragment
#version 330 core


struct DirectionalLight
{
        vec3 colour;
        vec3 position;
        float intensity;
};

struct Material
{
        vec4 ambient;
        vec4 diffuse;
        vec4 specular;
        int hasTexture;
        float reflectance;
};

uniform sampler2D texttr;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform DirectionalLight directionalLight;
uniform vec3 camera_pos;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

in vec4 fColor;
in vec2 fTextureCoords;
in vec3 fVertexNormal;
in vec3 fVertexPos;

out vec4 color;


void setupColours(Material material, vec2 textCoord)
{
        if(material.hasTexture == 1){
                ambientC = texture(texttr, textCoord);
                diffuseC = ambientC;
                speculrC = ambientC;
        }
        else {
                ambientC = material.ambient;
                diffuseC= material.diffuse;
                speculrC = material.specular;
        }

}

vec4 calcDirLight(vec3 light_colour, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal)
{
        vec4 diffuseColour = vec4(0, 0, 0, 0);
        vec4 specColour = vec4(0, 0, 0, 0);

        // Diffuse Light
        float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
        diffuseColour = diffuseC * vec4(light_colour, 1.0) * light_intensity * diffuseFactor;

        // Specular Light
        vec3 camera_direction = normalize(camera_pos-position);
        vec3 from_light_dir = -to_light_dir;
        vec3 reflected_light = normalize(reflect(from_light_dir , normal));
        float specularFactor = max( dot(camera_direction, reflected_light), 0.0);
        specularFactor = pow(specularFactor, specularPower);
        specColour = speculrC * light_intensity  * specularFactor * material.reflectance * vec4(light_colour, 1.0);

        return (diffuseColour + specColour);
}

vec4 calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal)
{
        return calcDirLight(light.colour, light.intensity, position, normalize(light.position), normal);
}


void main(){

        setupColours(material, fTextureCoords);

        vec4 diffuseSpecularComp = calcDirectionalLight(directionalLight, fVertexPos, fVertexNormal);

        color = ambientC * vec4(ambientLight, 1)+diffuseSpecularComp;


}