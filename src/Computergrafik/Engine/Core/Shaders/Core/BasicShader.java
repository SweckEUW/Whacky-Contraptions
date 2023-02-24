package Computergrafik.Engine.Core.Shaders.Core;

import java.util.ArrayList;

import Computergrafik.Engine.Core.Lights.AmbientLight;
import Computergrafik.Engine.Core.Lights.DirectionalLight;
import Computergrafik.Engine.Core.Lights.PointLight;
import Computergrafik.Engine.Core.Math.Matrix4f;

/**
 * Basic Shader that has methods to upload the following:
 * up to 50 point lights (an arraylist of point lights)
 * up to 50 directional lights (an arraylist of directional lights)
 * model matrix
 * view matrix 
 * model view projection matrix
 * a material with all its attributes
 * ambient light
 * 
 * @author Simon Weck
 *
 */
public class BasicShader extends ShaderInterface {

	private static final int MAX_POINTLIGHTS = 50;			
	private static final int MAX_DIRECTIONALLIGHTS = 50;
	
	//uniform locations
	protected int locationModelViewProjectionMatrix;	
	
	protected int locationModelMatrix;
	protected int locationProjectionMatrix;
	protected int locationViewMatrix;
	
	protected int locationMaterialEmissionColor;	
	protected int locationMaterialAmbientColor;	
	protected int locationMaterialDiffuseColor;
	protected int locationMaterialSpecularColor;
	protected int locationMaterialShininessValue;
	protected int locationMaterialAlphaValue;
	
	protected int LocationNumberOfDirectionalLights;
	protected int[] directionalLightLocations;
	
	protected int locationNumberOfPointLights;
	protected int[] poinLightLocations;
	
	protected int locationAmbientLightColor;
	
	/**
	 * creates a shader program out of a given glsl file
	 * @param shaderFile
	 * 			-location of a glsl file (all shaders should be in one .glsl file)
	 * 
	 * All shaders should be located in one file. Different shaders are separated with  "#Type" (shader type e.g. "Vertex Shader" or "Fragment Shader").
	 * For example "#Type Vertex Shader" indicates that the next lines contain the vertex Shader.
	 */
	public BasicShader(String file) {
		super(file);
	}

	@Override
	protected void getAllUniformLocations() {
		locationModelViewProjectionMatrix=getUniformLocation("modelViewProjectionMatrix");	
		
		locationModelMatrix=getUniformLocation("modelMatrix");
		locationProjectionMatrix=getUniformLocation("projectionMatrix");
		locationViewMatrix=getUniformLocation("viewMatrix");
		
		locationMaterialEmissionColor = getUniformLocation("material.emissionColor");	
		locationMaterialAmbientColor = getUniformLocation("material.ambientColor");	
		locationMaterialDiffuseColor = getUniformLocation("material.diffuseColor");
		locationMaterialSpecularColor = getUniformLocation("material.specularColor");
		locationMaterialShininessValue = getUniformLocation("material.shininessValue");	
		locationMaterialAlphaValue = getUniformLocation("material.alphaValue");	
					
		LocationNumberOfDirectionalLights = getUniformLocation("numberOfDirectionalLights");			
		directionalLightLocations = new int[MAX_DIRECTIONALLIGHTS*3];
		int tmp=0;
		for (int i = 0; i < MAX_DIRECTIONALLIGHTS; i++) {
			directionalLightLocations[tmp] = getUniformLocation("directionalLights["+i+"].direction");	
			directionalLightLocations[tmp+1] = getUniformLocation("directionalLights["+i+"].specularColor");
			directionalLightLocations[tmp+2] = getUniformLocation("directionalLights["+i+"].diffuseColor");
			tmp+=3;
		}
		
		locationNumberOfPointLights = getUniformLocation("numberOfPointLights");			
		poinLightLocations = new int[MAX_POINTLIGHTS*4];
		tmp=0;
		for (int i = 0; i < MAX_POINTLIGHTS; i++) {
			poinLightLocations[tmp] = getUniformLocation("pointLights["+i+"].position");	
			poinLightLocations[tmp+1] = getUniformLocation("pointLights["+i+"].diffuseColor");	
			poinLightLocations[tmp+2] = getUniformLocation("pointLights["+i+"].specularColor");	
			poinLightLocations[tmp+3] = getUniformLocation("pointLights["+i+"].attenuation");	
			tmp+=4;
		}
		
		
		locationAmbientLightColor= getUniformLocation("ambientLightColor");
	}
	
	/**
	 * uploads a matrix at the "modelViewProjectionMatrix" location
	 */
	public void uploadModelViewProjectionMatrix(Matrix4f matrix) {
		super.uploadMatrix(locationModelViewProjectionMatrix, matrix);
	}
	
	/**
	 * uploads all directional lights inside the arraylist
	 * the lights are in a glsl struct called directionalLight 
	 * attributes of the light can be accessed by accessing a directionalLight object inside glsl 
	 */
	public void uploadDirectionalLight(ArrayList<DirectionalLight> lights) {
		super.uploadFloat(LocationNumberOfDirectionalLights, lights.size()); //upload the amount of lights to upload so we only loop over the lights that are available
		
		for (int i = 0; i < lights.size(); i++) {
			super.uploadVector(directionalLightLocations[i*3], lights.get(i).getLightDirection());
			super.uploadVector(directionalLightLocations[i*3+1], lights.get(i).getSpeculaColor());
			super.uploadVector(directionalLightLocations[i*3+2], lights.get(i).getDiffuseColor());
		}	
	}
	
	/**
	 * uploads all point lights inside the arraylist
	 * the lights are in a glsl struct called pointLight
	 * attributes of the light can be accessed by accessing a pointLight object inside glsl 
	 */
	public void uploadPointLights(ArrayList<PointLight> lights) {		
		super.uploadFloat(locationNumberOfPointLights, lights.size()); //upload the amount of lights to upload so we only loop over the lights that are available
		
		for (int i = 0; i < lights.size(); i++) {
			super.uploadVector(poinLightLocations[i*4], lights.get(i).getPosition());
			super.uploadVector(poinLightLocations[i*4+1], lights.get(i).getDiffuseColor());
			super.uploadVector(poinLightLocations[i*4+2], lights.get(i).getSpeculaColor());
			super.uploadVector(poinLightLocations[i*4+3], lights.get(i).getAttenuation());	
		}	
	}
	
	/**
	 * uploads an ambientlight as 3 component vector
	 */
	public void uploadAmbientLight(AmbientLight light) {
		uploadVector(locationAmbientLightColor, light.getAmbientColor());
	}
	
	/**
	 * uploads a material with all its components inside a material struct in glsl
	 */
	public void uploadMaterial(Material material) {
		super.uploadVector(locationMaterialEmissionColor, material.getEmissionColor());
		super.uploadVector(locationMaterialAmbientColor, material.getAmbientColor());
		super.uploadVector(locationMaterialDiffuseColor, material.getDiffuseColor());
		super.uploadVector(locationMaterialSpecularColor, material.getSpecularColor());
		super.uploadFloat(locationMaterialShininessValue, material.getShininess());
		super.uploadFloat(locationMaterialAlphaValue, material.getAlpha());
	}
	
	/**
	 * uploads a matrix at the "modelMatrix" location
	 */
	public void uploadModelMatrix(Matrix4f matrix) {
		super.uploadMatrix(locationModelMatrix, matrix);
	}
	
	/**
	 * uploads a matrix at the "viewlMatrix" location
	 */
	public void uploadViewMatrix(Matrix4f matrix) {
		super.uploadMatrix(locationViewMatrix, matrix);
	}
		
	/**
	 * uploads a matrix at the "projectionMatrix" location
	 */
	public void uploadProjectionMatrix(Matrix4f projectionMatrix) {
		super.uploadMatrix(locationProjectionMatrix, projectionMatrix);
	}
}
