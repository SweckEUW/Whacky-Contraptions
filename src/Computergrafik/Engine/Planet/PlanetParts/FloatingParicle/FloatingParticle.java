package Computergrafik.Engine.Planet.PlanetParts.FloatingParicle;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;

public abstract class FloatingParticle extends PlanetPart{

	private static final String FILE = "PlanetShader/Planet";
	protected float speed;
	protected float lifeTime;
	protected float currentLifeTime;
	
	protected float roationSpeedX;
	protected float roationSpeedY;
	protected float roationSpeedZ;
	
	protected Vector3f positionOnPlanet;
	
	public FloatingParticle(Planet planet,int numberOfModels) {
		super(numberOfModels,planet,new BasicShader(FILE));
		resetParticle();
		lifeTime=planet.getModel().getScaleX()/4f*(60f*3f+((float)Math.random()*(60f*7f-60f*3f)));
		speed=planet.getModel().getScaleX()/4f*(0.01f+((float)Math.random()*(0.015f-0.01f)));
		roationSpeedX=(float)Math.random() *0.5f;
		roationSpeedY=(float)Math.random() *0.5f;
		roationSpeedZ=(float)Math.random() *0.5f;
	}
	
	public abstract void calculateColors();

	
	@Override
	public void update() {
		currentLifeTime++;
		if (currentLifeTime>lifeTime) {
			resetParticle();
			currentLifeTime=0;
		}else {		
			models[0].setX(models[0].getX()-speed*positionOnPlanet.x);
			models[0].setY(models[0].getY()-speed*positionOnPlanet.y);
			models[0].setZ(models[0].getZ()-speed*positionOnPlanet.z);
		}	
		models[0].increaseRotation(roationSpeedX, roationSpeedY, roationSpeedZ);
		models[0].getMaterial().setAlpha(currentLifeTime/lifeTime);
	}
	
	private void resetParticle() {
		float[] vertices= planet.getPlanetMesh().getMesh().getVertices();
		int[] indices=planet.getPlanetMesh().getMesh().getIndices();
		
		int randomIndex=(int)(Math.random()*indices.length);
		
		if (randomIndex%3==1) 
			randomIndex-=1;
		else if (randomIndex%3==2) 
			randomIndex-=2;
			
		Vector3f vertex1=new Vector3f(vertices[indices[randomIndex]*3],vertices[indices[randomIndex]*3+1],vertices[indices[randomIndex]*3+2]);
		Vector3f vertex2=new Vector3f(vertices[indices[randomIndex+1]*3],vertices[indices[randomIndex+1]*3+1],vertices[indices[randomIndex+1]*3+2]);
		Vector3f vertex3=new Vector3f(vertices[indices[randomIndex+2]*3],vertices[indices[randomIndex+2]*3+1],vertices[indices[randomIndex+2]*3+2]);
		
		float random1=(float)Math.random()*9999;
		float random2=(float)Math.random()*9999;
		float random3=(float)Math.random()*9999;
			
		float randomSum=random1+random2+random3;
		random1/=randomSum;
		random2/=randomSum;
		random3/=randomSum;
			
		vertex1=Vector3f.multiply(vertex1, random1);
		vertex2=Vector3f.multiply(vertex2, random2);
		vertex3=Vector3f.multiply(vertex3, random3);
			
		Vector3f randomPointOnSurface = Vector3f.add(vertex1, vertex2, vertex3);
		
		positionOnPlanet = randomPointOnSurface;
		
		randomPointOnSurface = Vector3f.multiply(randomPointOnSurface, planet.getPlanetModel().getScale()*1.5f);
		randomPointOnSurface = Vector3f.add(randomPointOnSurface,planet.getPlanetModel().getTranslation());
		
		models[0].setX(randomPointOnSurface.x);
		models[0].setY(randomPointOnSurface.y);
		models[0].setZ(randomPointOnSurface.z);
	}

	@Override
	public void generateRandom() {
		calculateColors();
		setNormalColorValues(); 		
		resetParticle();
		lifeTime=planet.getModel().getScaleX()/4f*(60f*3f+((float)Math.random()*(60f*7f-60f*3f)));
		speed=planet.getModel().getScaleX()/4f*(0.01f+((float)Math.random()*(0.015f-0.01f)));
		roationSpeedX=(float)Math.random() *0.5f;
		roationSpeedY=(float)Math.random() *0.5f;
		roationSpeedZ=(float)Math.random() *0.5f;
	}

}
