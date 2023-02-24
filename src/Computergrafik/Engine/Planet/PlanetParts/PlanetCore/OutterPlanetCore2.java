package Computergrafik.Engine.Planet.PlanetParts.PlanetCore;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;

public class OutterPlanetCore2 extends PlanetPart{

	private static final String SHADER_FILE = "PlanetShader/Planet";
	private static final String FILE = "PlanetParts/OutterCore2";
	
	protected float speedX;
	protected float speedY;
	protected float speedZ;
	
	private static final float SIZE = 0.3f;
	private static final int NUMBER_OF_MODELS = 1;
	
	
	public OutterPlanetCore2(Planet planet) {
		super(NUMBER_OF_MODELS,planet,new BasicShader(SHADER_FILE));
		speedX=(float)Math.random() *0.5f; 
		speedY=(float)Math.random() *0.5f; 
		speedZ=(float)Math.random() *0.5f; 
		models[0].setX(planet.getModel().getX());
		models[0].setY(planet.getModel().getY());
		models[0].setZ(planet.getModel().getZ());
	}
	
	@Override
	public void update() {
		models[0].increaseRotation(speedX, speedY, speedZ);
	}
	
	@Override
	public void generateRandom() {
		calculateColors();
		setNormalColorValues(); 
		speedX=(float)Math.random() *0.5f; 
		speedY=(float)Math.random() *0.5f; 
		speedZ=(float)Math.random() *0.5f; 
	}
	
	@Override
	public void calculateColors() {
		colors[0] = new float[models[0].getMesh().getVertices().length];
		for (int i = 0; i < colors[0].length; i+=3) {
			colors[0][i]=133f/255f*(0.7f+((float)Math.random()*(1f-0.7f)));
			colors[0][i+1]=23f/255f*(0.7f+((float)Math.random()*(1f-0.7f)));
			colors[0][i+2]=39f/255f*(0.7f+((float)Math.random()*(1f-0.7f)));
		}
		models[0].getMesh().setColorValues(colors[0]);
	}

	@Override
	protected void setModels() {
		Material material = new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1,0,0), 200);
		
		models[0] = new Model(FILE,material);
		models[0].setScale(SIZE);
	}

	@Override
	public void setScale() {
			models[0].setScale(planet.getPlanetModel().getScale()*SIZE);
	}
}
