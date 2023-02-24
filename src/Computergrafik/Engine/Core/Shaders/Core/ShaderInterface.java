package Computergrafik.Engine.Core.Shaders.Core;

import java.util.ArrayList;

import Computergrafik.Engine.Core.Lights.AmbientLight;
import Computergrafik.Engine.Core.Lights.DirectionalLight;
import Computergrafik.Engine.Core.Lights.PointLight;
import Computergrafik.Engine.Core.Math.Matrix4f;

/**
 * Shader interface with all methods a basicshader should implement for uploading uniforms
 * @author Simon Weck
 *
 */
public abstract class ShaderInterface extends ShaderProgram {

	/**
	 * creates a shader program out of a given glsl file
	 * @param shaderFile
	 * 			-location of a glsl file (all shaders should be in one .glsl file)
	 * 
	 * All shaders should be located in one file. Different shaders are separated with  "#Type" (shader type e.g. "Vertex Shader" or "Fragment Shader").
	 * For example "#Type Vertex Shader" indicates that the next lines contain the vertex Shader.
	 */
	protected ShaderInterface(String shaderFile) {
		super(shaderFile);
	}

	@Override
	protected abstract void getAllUniformLocations();
	
	public abstract void uploadModelViewProjectionMatrix(Matrix4f matrix);

	public abstract void uploadDirectionalLight(ArrayList<DirectionalLight> lights);
	
	public abstract void uploadPointLights(ArrayList<PointLight> lights);
	
	public abstract void uploadAmbientLight(AmbientLight light);
	 
	public abstract void uploadMaterial(Material material);
	
	public abstract void uploadModelMatrix(Matrix4f matrix);
	
	public abstract void uploadViewMatrix(Matrix4f matrix);
	
	public abstract void uploadProjectionMatrix(Matrix4f projectionMatrix);
	
}
