package Computergrafik.Engine.Planet.PlanetParts.SunAtmosphere;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;

public class SunAtmosphere1 extends SunAtmosphere {

	private static final String FILE = "PlanetParts/sunAtmosphere1";
	private static final float SIZE = 1.01f;
	private static final int NUMBER_OF_MODELS = 1;
	
	public SunAtmosphere1(Planet planet) {
		super(planet,NUMBER_OF_MODELS);
	}
	
	@Override
	public void calculateColors() {
		colors[0] = new float[models[0].getMesh().getVertices().length];
		for (int i = 0; i < colors[0].length; i+=3) {
			colors[0][i]=255f/255f*(0.9f+((float)Math.random()*(1f-0.9f)));
			colors[0][i+1]=255f/255f*(0.9f+((float)Math.random()*(1f-0.9f)));
			colors[0][i+2]=0/255f*(0.9f+((float)Math.random()*(1f-0.9f)));
		}
		models[0].getMesh().setColorValues(colors[0]);
	}

	@Override
	protected void setModels() {
		Material material = new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1,0,0), 200,0.2f);
		
		models[0] = new Model(FILE,material);
	}

	@Override
	public void setScale() {
		models[0].setScale(planet.getHighestPoint()*SIZE*planet.getModel().getScaleX());
	}

}
