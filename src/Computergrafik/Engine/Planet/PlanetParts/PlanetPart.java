package Computergrafik.Engine.Planet.PlanetParts;

import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Planet.Core.Planet;

/**
 * A PlanetPart is a special object that is connected to a planet. Each PlanetPart has a model and moves with the planet.
 * Every PlanetPart is different because they update differently. 
 * Water is a good example for a planetpart. It moves along the planet and its update function lets the water move. 
 * Contruction of a PlanerPart is similar to a PlanetEntity and uses nearly the same attributes.
 * @author Simon Weck
 *
 */
public abstract class PlanetPart {
	
	//same attributes as PlanetEntity. It uses normal models and not instanced models
	protected BasicShader shader;
	protected float[][] colors;
	protected float colorOffset = 0.5f;
	protected Model[] models;
	protected Planet planet;
	protected float[][] hilightColorValues;
	protected float[][] normalColorValues;
	protected int numberOfModels;
	
	public PlanetPart(int numberOfModels, Planet planet,BasicShader shader) {
		this.shader=shader;
		this.planet=planet;
		this.numberOfModels = numberOfModels;
		initArrays();
		setModels();
		setScale();
		calculateColors();
		setNormalColorValues(); 
	}
	
	public void initArrays() {
		colors = new float[numberOfModels][];
		hilightColorValues = new float[numberOfModels][];
		normalColorValues = new float[numberOfModels][];
		models = new Model[numberOfModels];	
	}

	protected abstract void setModels();

	public void useNormalColor() {	
		for (int i = 0; i < models.length; i++) {
			models[i].getMesh().setColorValues(normalColorValues[i]);
		}
	}
	
	public void useHighlightColor() {
		for (int i = 0; i < models.length; i++) 
			normalColorValues[i]=models[i].getMesh().getColors();
		
		for (int i = 0; i < models.length; i++) {
			hilightColorValues[i] = new float[models[i].getMesh().getColors().length];
			for (int j = 0; j < models[i].getMesh().getColors().length; j+=3) {
				hilightColorValues[i][j] = models[i].getMesh().getColors()[j]+0.9f;			
				hilightColorValues[i][j+1] = models[i].getMesh().getColors()[j+1]+0.1f;	
				hilightColorValues[i][j+2] = models[i].getMesh().getColors()[j+2]+0.6f;	
			}
			models[i].getMesh().setColorValues(hilightColorValues[i]);
		}

	}
	
	public void setNormalColorValues() {
		for (int i = 0; i < models.length; i++) 
			normalColorValues[i]=models[i].getMesh().getColors();
	}
		
	public abstract void update();
	
	public abstract void calculateColors();
	
	public Model[] getModels(){
		return models;
	}
	
	public BasicShader getShader() {
		return shader;
	}

	public abstract void setScale();
	
	public abstract void generateRandom();
	
}
