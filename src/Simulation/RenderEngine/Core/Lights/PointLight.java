package Simulation.RenderEngine.Core.Lights;

import java.util.ArrayList;

import Simulation.RenderEngine.Core.Math.Vector3f;

/**
 * light that can get uploaded into the shader to light up models
 * Point light lights the models within a certain radius depending the attenuation values of the light
 * @author Simon Weck
 *
 */
public class PointLight {
//attenuation value for specific ranges
/*
	Range 	Constant 	Linear 		Quadratic
	3250, 	1.0, 		0.0014,		0.000007

	600,  	1.0, 		0.007,		0.0002

	325, 	1.0, 		0.014, 		0.0007

	200, 	1.0, 		0.022, 		0.0019

	160,	1.0, 		0.027,		0.0028

	100, 	1.0, 		0.045,		0.0075

	65, 	1.0, 		0.07, 		0.017

	50, 	1.0, 		0.09,		0.032

	32, 	1.0, 		0.14,		0.07
	
	20, 	1.0, 		0.22, 		0.20

	13, 	1.0, 		0.35,		0.44

	7, 		1.0, 		0.7, 		1.8
*/
	private static ArrayList<PointLight> pointLights = new ArrayList<PointLight>(); //containing all point lights
	
	private Vector3f position;
    private Vector3f diffuseColor;
    private Vector3f speculaColor;
    private Vector3f attenuation;	//vector containing the 3 attenuation values: Constant Linear Quadratic
       
    /**
     * Creates a point light with the given attributes.
     * The light gets automatically uploaded to the shaders.
     * 
     * @param position
     * @param diffuseColor
     * @param specularColor
     * @param attenuation
     * 		-Constant Linear Quadratic attenuate factor as 3f vector
     */
   public PointLight(Vector3f position,Vector3f diffuseColor, Vector3f specularColor, Vector3f attenuation) {
		this.position=position;
		this.diffuseColor=diffuseColor;
		this.speculaColor=specularColor;
		this.attenuation=attenuation;
		pointLights.add(this);
   }	
    
 /**
  * Creates a point light with the given attributes.
  * The light gets automatically uploaded to the shaders.
  * @param position
  * @param diffuse
  * @param specular
  * @param attenuation
  * 		-Constant Linear Quadratic attenuate factor as 3f vector
  */
   public PointLight(Vector3f position,float diffuse, float specular, Vector3f attenuation) {
		this.position=position;
		this.diffuseColor=new Vector3f(diffuse);
		this.speculaColor=new Vector3f(specular);
		this.attenuation=attenuation;
		pointLights.add(this);
  }	
   
   /**
    * Creates a point light with the given attributes.
    * The light gets automatically uploaded to the shaders.
    * Diffuse and specular strength is 1
    * @param position
    * @param attenuation
    * 		-Constant Linear Quadratic attenuate factor as 3f vector
    */
   public PointLight(Vector3f position, Vector3f attenuation) {
		this.position=position;
		this.diffuseColor=new Vector3f(1);
		this.speculaColor=new Vector3f(1);
		this.attenuation=attenuation;
		pointLights.add(this);
   }	
   
   /**
    * Creates a point light with the given attributes.
    * The light gets automatically uploaded to the shaders.
    * Diffuse and specular strength is 1.
    * Range of the light is 50 see table.
    * @param position
    */
   public PointLight(Vector3f position) {
		this.position=position;
		this.diffuseColor=new Vector3f(1);
		this.speculaColor=new Vector3f(1);
		this.attenuation=new Vector3f(1, 0.09f, 0.032f);
		pointLights.add(this);
  }
   
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
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

	public void setSpeculaColor(Vector3f speculaColor) {
		this.speculaColor = speculaColor;
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}

	public static ArrayList<PointLight> getPointLights(){
		return pointLights;
	}
	
}
