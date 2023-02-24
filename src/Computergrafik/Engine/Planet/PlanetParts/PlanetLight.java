package Computergrafik.Engine.Planet.PlanetParts;

import Computergrafik.Engine.Core.Lights.PointLight;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Planet.Core.Planet;
/**
 * A Point light which moves along with the planet.
 * @author Simon
 *
 */
public class PlanetLight {

	private PointLight pointLight;
	private Planet planet;
	
	public PlanetLight(Planet planet) {
		pointLight=new PointLight(planet.getPlanetModel().getTranslation(), new Vector3f(1), new Vector3f(1), new Vector3f(1, 0.007f, 0.0002f));	
		this.planet=planet;
	}
	
	public PlanetLight(Planet planet,Vector3f diffuseColor, Vector3f specularColor, Vector3f attenuation) {
		pointLight=new PointLight(planet.getPlanetModel().getTranslation(), diffuseColor, specularColor, attenuation);	
		this.planet=planet;
	}
	
	/**
	 * sets the position of the point light to the center of the planet its connected with.
	 */
	public void update() {
		pointLight.setPosition(new Vector3f(planet.getModel().getModelMatrix().m03, planet.getModel().getModelMatrix().m13, planet.getModel().getModelMatrix().m23));
	}
	
	/**
	 * sets the position of the point light to the center of the planet its connected with + an x,y,z offset.
	 */
	public void update(float xOffset, float yOffset,float zOffset) {
		pointLight.setPosition(new Vector3f(planet.getModel().getModelMatrix().m03+xOffset, planet.getModel().getModelMatrix().m13+yOffset, planet.getModel().getModelMatrix().m23+zOffset));
	}
}
