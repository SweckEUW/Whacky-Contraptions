#Type Vertex Shader
#version 430

layout(location=0) in vec3 position;
layout(location=1) in vec3 normal;

uniform mat4 modelViewProjectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec3 vertexColor;
			
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
	vec3 ambientColor;
	vec3 diffuseColor;
};
#define MAX_DIRECTIONALLIGHTS 50
uniform DirectionalLight directionalLights[MAX_DIRECTIONALLIGHTS];
uniform float numberOfDirectionalLights;


struct PointLight{
	vec3 position;
	vec3 specularColor;
	vec3 ambientColor;
	vec3 diffuseColor;
	vec3 attenuation;
};
#define MAX_POINTLIGHTS 50
uniform PointLight pointLights[MAX_POINTLIGHTS];
uniform float numberOfPointLights;

		
vec3 calculateDirectionalLight(DirectionalLight light,Material material, vec3 unitNormal, vec3 cameraDirection,vec3 modelSpacePosition){
	
	vec3 specular,diffuse,ambient;
		
    ambient = light.ambientColor * material.ambientColor;

	// diffuse
	vec3 unitLightDirection = -normalize(light.direction );	
	float diffuseFactor =  max(dot(unitNormal,unitLightDirection),0);	
	if(diffuseFactor > 0){
		diffuse = diffuseFactor * light.diffuseColor * material.diffuseColor;
		
		// specular														
		vec3 unitVectorToCamera = normalize(cameraDirection-modelSpacePosition);
		vec3 reflectedLightDirection= reflect(-unitLightDirection,unitNormal);
		float specularFactor = max(dot(reflectedLightDirection,unitVectorToCamera ),0);
		if(specularFactor > 0)
			specular = pow(specularFactor,material.shininessValue) * material.specularColor * light.specularColor;
			
		// Blinn optimization
		//vec3 H = normalize(unitLightDirection+unitVectorToCamera);										
		//vec3 specular = pow(max(dot(H,unitNormal ),0), material.shininessValue) * light.specularColor * material.specularColor;	
	}
	return(ambient + diffuse + specular);		
}

vec3 calculatePointLight(PointLight light,Material material, vec3 unitNormal, vec3 cameraDirection,vec3 modelSpacePosition){
	
	float distance = length(light.position - modelSpacePosition);
	float attenuation = 1.0/(light.attenuation.x + light.attenuation.y*distance + light.attenuation.z*(distance*distance));
	
	vec3 specular,diffuse,ambient;
	
    ambient = light.ambientColor * material.ambientColor * attenuation;

	// diffuse
	vec3 unitLightDirection = normalize(light.position - modelSpacePosition);	
	float diffuseFactor =  max(dot(unitNormal,unitLightDirection),0) ;	
	if(diffuseFactor > 0){
		diffuse = diffuseFactor * light.diffuseColor * material.diffuseColor;
		
		// specular														
		vec3 unitVectorToCamera = normalize(cameraDirection-modelSpacePosition);
		vec3 reflectedLightDirection= reflect(-unitLightDirection,unitNormal);
		float specularFactor = max(dot(reflectedLightDirection,unitVectorToCamera ),0);
		if(specularFactor > 0)
			specular = pow(specularFactor,material.shininessValue) * material.specularColor * light.specularColor;
			
		// Blinn optimization
		//vec3 H = normalize(unitLightDirection+unitVectorToCamera);										
		//vec3 specular = pow(max(dot(H,unitNormal ),0), material.shininessValue) * light.specularColor * material.specularColor;	
		
		diffuse *= attenuation;
		specular *= attenuation;
	}
	return(ambient + diffuse + specular);		
}
			
												 
void main(void){
			
	gl_Position = projectionMatrix * modelViewProjectionMatrix * vec4(position,1);
			
	vec3 modelSpacePosition= (modelMatrix * vec4(position,1)).xyz;
	
	vec3 modelSpaceNormal = (inverse(transpose(modelMatrix)) * vec4(normal,0)).xyz; 		
	//vec3 modelSpaceNormal = (modelMatrix * vec4(normal,0)).xyz;	
														
	vec3 cameraDirection = (inverse(viewMatrix)*vec4(0,0,0,1)).xyz;
	
	vec3 unitNormal = normalize(modelSpaceNormal);
		
//	for(int i = 0; i < numberOfDirectionalLights ; i++)
//		vertexColor += calculateDirectionalLight(directionalLights[i],material,unitNormal,cameraDirection,modelSpacePosition);	
	for(int i = 0; i < numberOfPointLights ; i++)
		vertexColor += calculatePointLight(pointLights[i],material,unitNormal,cameraDirection,modelSpacePosition);
	
//	vertexColor+=material.emissionColor;
	//vertexColor= vec3(numberOfPointLights,0,0);
};



#Type Fragment Shader
#version 430

in vec3 vertexColor;

out vec4 finalColor;

void main(void){	
		
	finalColor=vec4(vertexColor,1);										
};




