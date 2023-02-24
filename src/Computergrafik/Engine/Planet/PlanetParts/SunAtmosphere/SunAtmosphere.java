package Computergrafik.Engine.Planet.PlanetParts.SunAtmosphere;

import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;

public abstract class SunAtmosphere extends PlanetPart{

	private static final String FILE = "PlanetShader/Planet";
	protected float speedX;
	protected float speedY;
	protected float speedZ;
	
	public SunAtmosphere(Planet planet,int numberOfModels) {
		super(numberOfModels,planet,new BasicShader(FILE));
		speedX=(float)Math.random() *0.5f; 
		speedY=(float)Math.random() *0.5f; 
		speedZ=(float)Math.random() *0.5f; 
		models[0].setX(planet.getModel().getX());
		models[0].setY(planet.getModel().getY());
		models[0].setZ(planet.getModel().getZ());
		addNoise();
	}
	
	private void addNoise() {
		float[] oldVertices = models[0].getMesh().getBaseVertices();
		float[] newVertices = new float[models[0].getMesh().getVertices().length];
		for (int i = 0; i < newVertices.length; i+=3) {
			float randomNoise = 0.93f+((float)Math.random()*(1.07f-0.93f));
			newVertices[i] = oldVertices[i]*randomNoise;
			newVertices[i+1] = oldVertices[i+1]*randomNoise;
			newVertices[i+2] = oldVertices[i+2]*randomNoise;
			for (int j = 0; j < newVertices.length; j+=3) 
				if (oldVertices[j]==oldVertices[i] && oldVertices[j+1]==oldVertices[i+1] && oldVertices[j+2]==oldVertices[i+2]) {
					newVertices[j] = oldVertices[j]*randomNoise;
					newVertices[j+1] = oldVertices[j+1]*randomNoise;
					newVertices[j+2] = oldVertices[j+2]*randomNoise;
				}
		}
		models[0].getMesh().setVertices(newVertices, true);
	}

	public abstract void calculateColors();

	
	@Override
	public void update() {
		models[0].increaseRotation(speedX, speedY, speedZ);
	}
	
	@Override
	public void generateRandom() {
		calculateColors();
		setNormalColorValues(); 
		addNoise();
		speedX=(float)Math.random() *0.5f; 
		speedY=(float)Math.random() *0.5f; 
		speedZ=(float)Math.random() *0.5f; 
	}

}
