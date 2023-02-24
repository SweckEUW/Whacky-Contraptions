#Type Vertex Shader
#version 430

layout(location=0) in vec3 position;
layout(location=1) in vec3 normal;

uniform mat4 modelViewProjectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec3 modelSpacePosition;
out vec3 modelSpaceNormal;		
out vec3 cameraDirection;
												 
void main(void){
			
	gl_Position = projectionMatrix * modelViewProjectionMatrix * vec4(position,1);
			
	modelSpacePosition= (modelMatrix * vec4(position,1)).xyz;
	
	modelSpaceNormal = (inverse(transpose(modelMatrix)) * vec4(normal,0)).xyz; 		
	//modelSpaceNormal = (modelMatrix * vec4(normal,0)).xyz;	
														
	cameraDirection = (inverse(viewMatrix)*vec4(0,0,0,1)).xyz;
};


#Type Fragment Shader
#version 430

in vec3 modelSpaceNormal;
in vec3 modelSpacePosition;
in vec3 cameraDirection;

 
struct Material{
	vec3 emissionColor;
	vec3 ambientColor;
	vec3 diffuseColor;
	vec3 specularColor;
	float shininessValue;
};
uniform Material material;


struct DirectionalLight{
	vec3 direction;
	vec3 specularColor;
	vec3 diffuseColor;
};
#define MAX_DIRECTIONALLIGHTS 20
uniform DirectionalLight directionalLights[MAX_DIRECTIONALLIGHTS];
uniform float numberOfDirectionalLights;


struct PointLight{
	vec3 position;
	vec3 specularColor;
	vec3 diffuseColor;
	vec3 attenuation;
};
#define MAX_POINTLIGHTS 20
uniform PointLight pointLights[MAX_POINTLIGHTS];
uniform float numberOfPointLights;

uniform vec3 ambientLightColor;



out vec4 finalColor;


vec3 calculateDirectionalLight(DirectionalLight light,Material material, vec3 unitNormal, vec3 cameraDirection,vec3 modelSpacePosition){
	
	vec3 specular,diffuse;
		
	// diffuse
	vec3 unitLightDirection = -normalize(light.direction );	
	float diffuseFactor =  max(dot(unitNormal,unitLightDirection),0);	
	if(diffuseFactor > 0)
		diffuse = diffuseFactor * light.diffuseColor * material.diffuseColor;
		
	// specular														
	vec3 unitVectorToCamera = normalize(cameraDirection-modelSpacePosition);
	vec3 reflectedLightDirection= reflect(-unitLightDirection,unitNormal);
	float specularFactor = max(dot(reflectedLightDirection,unitVectorToCamera ),0);
	if(specularFactor > 0 && material.shininessValue>0)
		specular = pow(specularFactor,material.shininessValue) * material.specularColor * light.specularColor;
			
	// Blinn optimization
	//vec3 H = normalize(unitLightDirection+unitVectorToCamera);										
	//vec3 specular = pow(max(dot(H,unitNormal ),0), material.shininessValue) * light.specularColor * material.specularColor;	
	
	return(diffuse + specular);		
}

vec3 calculatePointLight(PointLight light,Material material, vec3 unitNormal, vec3 cameraDirection,vec3 modelSpacePosition){
	
	float distance = length(light.position - modelSpacePosition);
	float attenuation = 1.0/(light.attenuation.x + light.attenuation.y*distance + light.attenuation.z*(distance*distance));
	
	vec3 specular,diffuse;
	
	// diffuse
	vec3 unitLightDirection = normalize(light.position - modelSpacePosition);	
	float diffuseFactor =  max(dot(unitNormal,unitLightDirection),0) ;	
	if(diffuseFactor > 0)
		diffuse = diffuseFactor * light.diffuseColor * material.diffuseColor;
		
	// specular														
	vec3 unitVectorToCamera = normalize(cameraDirection-modelSpacePosition);
	vec3 reflectedLightDirection= reflect(-unitLightDirection,unitNormal);
	float specularFactor = max(dot(reflectedLightDirection,unitVectorToCamera ),0);
	if(specularFactor > 0 && material.shininessValue>0)
		specular = pow(specularFactor,material.shininessValue) * material.specularColor * light.specularColor;
			
	// Blinn optimization
	//vec3 H = normalize(unitLightDirection+unitVectorToCamera);										
	//vec3 specular = pow(max(dot(H,unitNormal ),0), material.shininessValue) * light.specularColor * material.specularColor;	
		
	diffuse *= attenuation;
	specular *= attenuation;
	
	return(diffuse + specular);	
}


void main(void){	
	
	vec3 unitNormal = normalize(modelSpaceNormal);
	
	vec3 fragmentColor;
	
	for(int i = 0; i < numberOfDirectionalLights ; i++)
		fragmentColor += calculateDirectionalLight(directionalLights[i],material,unitNormal,cameraDirection,modelSpacePosition);	
	for(int i = 0; i < numberOfPointLights ; i++)
		fragmentColor += calculatePointLight(pointLights[i],material,unitNormal,cameraDirection,modelSpacePosition);
	
	fragmentColor+=material.emissionColor;
	fragmentColor+=material.ambientColor*ambientLightColor;
	
	finalColor=vec4(fragmentColor,1);										
};




