#Type Vertex Shader
#version 430

layout(location=0) in vec3 position; 
layout(location=2) in vec3 color;

uniform mat4 projectionMatrix;
uniform mat4 modelViewProjectionMatrix;

out vec3 objectColor;

void main(void)
{
	gl_Position = projectionMatrix * modelViewProjectionMatrix * vec4(position,1);
	
	objectColor = color;
};


#Type Fragment Shader
#version 430

in vec3 objectColor;

out vec4 finalColor;

void main(void)
{	
	finalColor=vec4(objectColor,1.0);
};

