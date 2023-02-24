#Type Vertex Shader
#version 430

layout(location=0) in vec3 position; 

uniform mat4 projectionMatrix;
uniform mat4 modelViewProjectionMatrix;

void main(void)
{
	gl_Position = projectionMatrix * modelViewProjectionMatrix * vec4(position,1);
};


#Type Fragment Shader
#version 430

out vec4 finalColor;

void main(void)
{	
	finalColor=vec4(1,0,0,1.0);	 //Orbit color
};

