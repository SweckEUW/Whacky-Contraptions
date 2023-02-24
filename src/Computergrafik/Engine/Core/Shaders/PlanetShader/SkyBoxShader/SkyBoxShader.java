package Computergrafik.Engine.Core.Shaders.PlanetShader.SkyBoxShader;

import Computergrafik.Engine.Core.Shaders.Core.BasicShader;

/**
 * Shaderprogram for a skybox. A skybox only needs model view and projection matrices to be rendered
 * @author Simon Weck
 *
 */
public class SkyBoxShader extends BasicShader {

	private static final String GLSL_FILE = "PlanetShader/SkyBoxShader/SkyBox"; //File path
		
	public SkyBoxShader() {
		super(GLSL_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationModelViewProjectionMatrix=getUniformLocation("modelViewProjectionMatrix");	
		locationProjectionMatrix=getUniformLocation("projectionMatrix");	
	}
}
