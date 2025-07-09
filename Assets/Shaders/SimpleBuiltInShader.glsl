#section vertex
#version 330 core
layout (location=0) in vec3 ShPos;
layout (location=1) in vec4 ShColor;
layout (location=2) in vec2 ShTextureCoords;
layout (location=3) in vec3 ShVertexNormal;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;


out vec4 fColor;
out vec2 fTextureCoords;
out vec3 fVertexNormal;
out vec3 fVertexPos;

void main(){
        fColor=ShColor;
        fTextureCoords=ShTextureCoords;
        vec4 mvPose = viewMatrix* vec4(ShPos,1.0);
        gl_Position = projectionMatrix*viewMatrix* mvPose;
        fVertexNormal = normalize(viewMatrix * vec4(ShVertexNormal, 0.0)).xyz;
        fVertexPos = mvPose.xyz;
}
#section fragment
#version 330 core

struct Attenuation
{
        float constant;
        float linear;
        float exponent;
};

struct PointLight
{
        vec3 colour;
        vec3 position;
        float intensity;
        Attenuation att;
};

struct Material
{
        vec4 ambient;
        vec4 diffuse;
        vec4 specular;
        //int hasTexture;
        float reflectance;
};

uniform sampler2D texttr;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
//uniform PointLight pointLight;
//uniform vec3 camera_pos;

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

                ambientC = texture(texttr, textCoord);
                diffuseC = ambientC;
                speculrC = ambientC;

}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal)
{
        vec4 diffuseColour = vec4(0, 0, 0, 0);
        vec4 specColour = vec4(0, 0, 0, 0);

        // Diffuse Light
        vec3 light_direction = light.position - position;
        vec3 to_light_source  = normalize(light_direction);
        float diffuseFactor = max(dot(normal, to_light_source ), 0.0);
        diffuseColour = diffuseC * vec4(light.colour, 1.0) * light.intensity * diffuseFactor;

        // Specular Light
        vec3 camera_direction = normalize(-position);
        vec3 from_light_source = -to_light_source;
        vec3 reflected_light = normalize(reflect(from_light_source, normal));
        float specularFactor = max( dot(camera_direction, reflected_light), 0.0);
        specularFactor = pow(specularFactor, specularPower);
        specColour = speculrC * specularFactor * material.reflectance * vec4(light.colour, 1.0);

        // Attenuation
        float distance = length(light_direction);
        float attenuationInv = light.att.constant + light.att.linear * distance +
        light.att.exponent * distance * distance;
        return (diffuseColour + specColour) / attenuationInv;
}

void main(){

        setupColours(material, fTextureCoords);

        //vec4 diffuseSpecularComp = calcPointLight(pointLight, fVertexPos, fVertexNormal);

        color = ambientC * vec4(ambientLight, 1);


}