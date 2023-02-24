package Computergrafik.Engine.Core.Lights;

import Computergrafik.Engine.Core.Math.Vector3f;

/**
 * light that can get uploaded into the shader to light up models
 * Ambient light that lights the whole scene
 * @author Simon Weck
 *
 */
public class AmbientLight {

	private static AmbientLight ambientLight; //only 1 ambient light at a time
	
	private Vector3f ambientColor; //rgb strength of the ambient light
	
	/**
	 * creates an ambient light with the given rgb strength as a 3 float vector.
	 * The light gets automatically uploaded to the shaders.
	 * @param ambientColor
	 */
	public AmbientLight(Vector3f ambientColor) {
		this.ambientColor=ambientColor;
		ambientLight=this;
	}
	

	/**
	 * creates an ambient light with the given rgb strength where every rgb strength has the same value.
	 * The light gets automatically uploaded to the shaders.
	 * @param ambientColor
	 */
	public AmbientLight(float ambientColor) {
		this.ambientColor=new Vector3f(ambientColor);
		ambientLight=this;
	}

	public Vector3f getAmbientColor() {
		return ambientColor;
	}
	
	public static AmbientLight getAmbientLight() {
		return ambientLight;
	}
	
}
