package Computergrafik.Engine.Planet.PlanetEntities.Sweets;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;

public class Donut extends PlanetEntity{

	private static final String FILE1 = "PlanetEntities/Sweets/Donut/DonutBase";
	private static final String FILE2 = "PlanetEntities/Sweets/Donut/DonutGlaze";
	private static final String FILE3 = "PlanetEntities/Sweets/Donut/DonutSprinkle";
	private static final int NUMBER_OF_MODELS = 3;
	
	public Donut(Planet planet,int instances) {
		super(planet,NUMBER_OF_MODELS,instances);	
	}

	@Override
	public void calculateColors() {	
		float[] colors;
		
		//base	
		colors= new float[3*instances];
		colorOffset=0.8f;
		for (int i = 0; i < colors.length; i+=3) { 
			colors[i]=241f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=177f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=102f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[0].getInstancedMesh().setColors(colors);		
		
		//glaze
		colors= new float[3*instances];
		colorOffset=0.01f;
		for (int i = 0; i < colors.length; i+=3) { 
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[1].getInstancedMesh().setColors(colors);		
		
		//sprinkles
		colors= new float[3*instances];
		for (int i = 0; i < colors.length; i+=3) { 
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[2].getInstancedMesh().setColors(colors);		
	}

	@Override
	protected void setModels() {
							   //new Material(ambientColor,       diffuseColor,    specularColor,               shininess)
		Material material1 = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 10);
		Material material2 = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 64);
		
		instancedModels[0] = new InstancedModel(FILE1,instances,material1);
		instancedModels[1] = new InstancedModel(FILE2,instances,material2);
		instancedModels[2] = new InstancedModel(FILE3,instances,material2);
	}

	@Override
	protected void setScale() {
		maxHeight = 20f; 
		minHeight = 0f;
		float[] scale = new float[instances];
		for (int i = 0; i < instances; i++) {
			float randomSize = (0.01f+((float)Math.random()*(0.05f-0.01f)));
			scale[i] = randomSize*planet.getModel().getScaleX(); 
		}	
		instancedModels[0].setScale(scale);
		instancedModels[1].setScale(scale);
		instancedModels[2].setScale(scale);
	}


}
