package Computergrafik.Engine.Planet.PlanetParts.Rings;

import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;

public abstract class Ring extends PlanetPart{

	private static final String FILE = "PlanetShader/Planet";
	protected float speedX;
	protected float speedY;
	protected float speedZ;
	
	public Ring(Planet planet,int numberOfModels) {
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
			float randomNoise = 0.1f+((float)Math.random()*(10f-0.1f));
			newVertices[i] = oldVertices[i];
			newVertices[i+1] = oldVertices[i+1]*randomNoise;
			newVertices[i+2] = oldVertices[i+2];
			for (int j = 0; j < newVertices.length; j+=3) 
				if (oldVertices[j]==oldVertices[i] && oldVertices[j+1]==oldVertices[i+1] && oldVertices[j+2]==oldVertices[i+2]) {
					newVertices[j] = oldVertices[j];
					newVertices[j+1] = oldVertices[j+1]*randomNoise;
					newVertices[j+2] = oldVertices[j+2];
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
