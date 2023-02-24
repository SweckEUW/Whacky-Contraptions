package Computergrafik.Engine.Core.Lights;

import java.util.ArrayList;

import Computergrafik.Engine.Core.Math.Vector3f;

/**
 * light that can get uploaded into the shader to light up models
 * Directional light that lights the whole scene in a direction 
 * @author Simon Weck
 */
public class DirectionalLight {

	private static ArrayList<DirectionalLight> directionalLights = new ArrayList<DirectionalLight>(); //containing all directional lights
	
	private Vector3f lightDirection; //direction of the light
    private Vector3f diffuseColor;	//rgb strength of the diffuse color
    private Vector3f speculaColor;	//rgb strength of the specular color
	
    /**
     * creates a directional light with the given direction and diffuse and specular rgb strength.
     * The light gets automatically uploaded to the shaders.
     * @param lightDirection
     * 		-direction of the light
     * @param diffuseColor
     * 		-diffuse rgb strength
     * @param speculaColor
     * 		-specular rgb strength
     */
	public DirectionalLight(Vector3f lightDirection,Vector3f diffuse,Vector3f specula) {
		this.lightDirection=lightDirection;
		this.diffuseColor=diffuse;
		this.speculaColor=specula;
		directionalLights.add(this);
	}

	  /**
     * creates a directional light with the given direction and diffuse and specular rgb strength of 1.
     * The light gets automatically uploaded to the shaders.
     * @param lightDirection
     * 		-direction of the light
     */
	public DirectionalLight(Vector3f lightDirection) {
		this.lightDirection=lightDirection;
		this.diffuseColor=new Vector3f(1);
		this.speculaColor=new Vector3f(1);	
		directionalLights.add(this);
	}
		
	 /**
	  * * creates a directional light with the given direction and diffuse and specular rgb strength with the same 3 values.
	  * The light gets automatically uploaded to the shaders.
	  * @param lightDirection
	  * 		-direction of the light
	  * @param diffuseColor
	  * 		-diffuse rgb strength
	  * @param specularColor
	  * 		-specular rgb strength
	  */
	public DirectionalLight(Vector3f lightDirection, float diffuse,float specular) {
		this.lightDirection=lightDirection;
		this.diffuseColor=new Vector3f(diffuse, diffuse, diffuse);
		this.speculaColor=new Vector3f(specular, specular, specular);
		directionalLights.add(this);
	}
	
	public Vector3f getLightDirection() {
		return lightDirection;
	}

	public void setLightDirection(Vector3f lightDirection) {
		this.lightDirection = lightDirection;
	}
	
	public Vector3f getDiffuseColor() {
		return diffuseColor;
	}

	public void setDiffuseColor(Vector3f diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	public Vector3f getSpeculaColor() {
		return speculaColor;
	}

	public void setSpeculaColor(Vector3f speculaColorr) {
		this.speculaColor = speculaColorr;
	}

	public static ArrayList<DirectionalLight> getDirectionalLights(){
		return directionalLights;
	}
	
}
