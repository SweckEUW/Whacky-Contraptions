package Computergrafik.Engine.Planet.PlanetEntities.Trees;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;

public class Tree1 extends PlanetEntity{

	private static final String FILE = "PlanetEntities/Trees/Tree1";
	private static final int NUMBER_OF_MODELS = 2;
	
	public Tree1(Planet planet,int instances) {
		super(planet,NUMBER_OF_MODELS,instances);	
	}

	@Override
	public void calculateColors() {	
		float[] colors;
		
		
		//Stamm	
		colors= new float[3*instances];
		colorOffset=0.9f; 
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=222f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=184f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=135f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[0].getInstancedMesh().setColors(colors);
		
		
		//Krone
		colors= new float[3*instances];
		colorOffset=0.3f;
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=100f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=100f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[1].getInstancedMesh().setColors(colors);	
		
	}

	@Override
	protected void setModels() {
							   //new Material(ambientColor,       diffuseColor,    specularColor,               shininess)
		Material materialStamm = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 16);
		Material materialKrone = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 16);
		
		instancedModels[0] = new InstancedModel(FILE+"/Stamm",instances,materialStamm);
		instancedModels[1] = new InstancedModel(FILE+"/Krone",instances,materialKrone);
	}

	@Override
	protected void setScale() {
		float[] scale = new float[instances];
		for (int i = 0; i < instances; i++) {
			float randomSize = (0.005f+((float)Math.random()*(0.007f-0.005f)));
			scale[i] = randomSize*planet.getModel().getScaleX(); 
		}	
		instancedModels[0].setScale(scale);
		instancedModels[1].setScale(scale);
	}

}
