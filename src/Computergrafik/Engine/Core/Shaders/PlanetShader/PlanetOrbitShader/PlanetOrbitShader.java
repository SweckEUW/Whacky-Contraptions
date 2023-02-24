package Computergrafik.Engine.Core.Shaders.PlanetShader.PlanetOrbitShader;

import Computergrafik.Engine.Core.Shaders.Core.BasicShader;

/**
 * Shaderprogram for the planet orbit. A planet orbit only needs model view and projection matrices to be rendered
 * @author Simon Weck
 *
 */
public class PlanetOrbitShader extends BasicShader {

	private static final String GLSL_FILE = "PlanetShader/PlanetOrbitShader/PlanetOrbit"; //File path
		
	public PlanetOrbitShader() {
		super(GLSL_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationModelViewProjectionMatrix=getUniformLocation("modelViewProjectionMatrix");	
		locationProjectionMatrix=getUniformLocation("projectionMatrix");	
	}
	
}
