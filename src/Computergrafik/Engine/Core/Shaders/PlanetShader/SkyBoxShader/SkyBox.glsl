#Type Vertex Shader
#version 430

layout(location=0) in vec3 position; 
layout(location=3) in vec2 textureCoordinates; 

uniform mat4 projectionMatrix;
uniform mat4 modelViewProjectionMatrix;

out vec2 pass_textureCoords;

void main(void)
{
	gl_Position = projectionMatrix * modelViewProjectionMatrix * vec4(position,1);
	pass_textureCoords=textureCoordinates;
	
};


#Type Fragment Shader
#version 430

in vec2 pass_textureCoords;

layout (binding=0) uniform sampler2D textureSampler;

out vec4 finalColor;

void main(void)
{	
	finalColor=texture(textureSampler,pass_textureCoords);
	
};

