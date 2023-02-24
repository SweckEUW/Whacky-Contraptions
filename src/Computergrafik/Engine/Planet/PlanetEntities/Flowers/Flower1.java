package Computergrafik.Engine.Planet.PlanetEntities.Flowers;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;

public class Flower1 extends PlanetEntity{

	private static final String FILE_STIL = "PlanetEntities/Flowers/Flower1/Stil";
	private static final String FILE_BLOSSOM = "PlanetEntities/Flowers/Flower1/blossom";
	private static final String FILE_PETAL = "PlanetEntities/Flowers/Flower1/petal";
	private static final String FILE_BLATT = "PlanetEntities/Flowers/Flower1/Blatt";
	
	private static final int NUMBER_OF_MODELS = 4;
	
	public Flower1(Planet planet,int ammount) {
		super(planet,NUMBER_OF_MODELS,ammount);	
	}

	@Override
	public void calculateColors() {
		float[] colors;
		
		 
		//Stil
		colors= new float[3*instances];
		colorOffset=0.8f;
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=80f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=150f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[0].getInstancedMesh().setColors(colors);
			
		
		
		//Blüte
		colors= new float[3*instances];
		colorOffset=0.3f;
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=10f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[1].getInstancedMesh().setColors(colors);
		
		
		//Blatt
		colors= new float[3*instances];
		colorOffset=0.8f;
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=118f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=238f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[2].getInstancedMesh().setColors(colors);
		
		
		//BlütenBlätter
		colors= new float[3*instances];
		colorOffset=0.3f;
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=147f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[3].getInstancedMesh().setColors(colors);
	}

	@Override
	protected void setModels() {
							   //new Material(ambientColor,       diffuseColor,    specularColor,               shininess)
		Material material = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 16);
			
		instancedModels[0] = new InstancedModel(FILE_STIL,instances,material);
		instancedModels[1] = new InstancedModel(FILE_BLOSSOM,instances,material);
		instancedModels[2] = new InstancedModel(FILE_BLATT,instances,material);
		instancedModels[3] = new InstancedModel(FILE_PETAL,instances,material);
	}

	@Override
	protected void setScale() {
		
		float[] scale = new float[instances];
		for (int i = 0; i < instances; i++) {
			float randomSize = (0.02f+((float)Math.random()*(0.02f-0.016f)));
			scale[i] = randomSize*planet.getModel().getScaleX(); 
		}	
		instancedModels[0].setScale(scale);
		instancedModels[1].setScale(scale);
		instancedModels[2].setScale(scale);
		instancedModels[3].setScale(scale);
	}
}
