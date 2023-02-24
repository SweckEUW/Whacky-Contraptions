package Computergrafik.Engine.Planet.PlanetEntities.Sweets;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;

public class Lolipop3 extends PlanetEntity{

	private static final String FILE1 = "PlanetEntities/Sweets/Lolipop3/Stil";
	private static final String FILE2 = "PlanetEntities/Sweets/Lolipop3/layer1";
	private static final String FILE3= "PlanetEntities/Sweets/Lolipop3/layer2";
	private static final String FILE4 = "PlanetEntities/Sweets/Lolipop3/layer3";
	private static final String FILE5 = "PlanetEntities/Sweets/Lolipop3/layer4";
	private static final String FILE6 = "PlanetEntities/Sweets/Lolipop3/layer5";
	private static final int NUMBER_OF_MODELS = 6;
	
	public Lolipop3(Planet planet,int instances) {
		super(planet,NUMBER_OF_MODELS,instances);	
	}

	@Override
	public void calculateColors() {	
		float[] colors;
		
		
		//Stil
		colors= new float[3*instances];
		colorOffset=0.9f;
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[0].getInstancedMesh().setColors(colors);	
		
		
		//layers
		colors= new float[3*instances];
		colorOffset=0.01f;
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[1].getInstancedMesh().setColors(colors);	
		
		//layers
		colors= new float[3*instances];
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[2].getInstancedMesh().setColors(colors);	
		
		colors= new float[3*instances];
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[3].getInstancedMesh().setColors(colors);	
		
		colors= new float[3*instances];
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[4].getInstancedMesh().setColors(colors);
		
		colors= new float[3*instances];
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[5].getInstancedMesh().setColors(colors);	
	}

	@Override
	protected void setModels() {
							   //new Material(ambientColor,       diffuseColor,    specularColor,               shininess)
		Material materialStil = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 16);
		Material materialLayer = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 100);
		
		instancedModels[0] = new InstancedModel(FILE1,instances,materialStil);
		instancedModels[1] = new InstancedModel(FILE2,instances,materialLayer);
		instancedModels[2] = new InstancedModel(FILE3,instances,materialLayer);
		instancedModels[3] = new InstancedModel(FILE4,instances,materialLayer);
		instancedModels[4] = new InstancedModel(FILE5,instances,materialLayer);
		instancedModels[5] = new InstancedModel(FILE6,instances,materialLayer);
	}

	@Override
	protected void setScale() {
		maxHeight = 20f; 
		minHeight = 0f;
		float[] scale = new float[instances];
		for (int i = 0; i < instances; i++) {
			float randomSize = (0.008f+((float)Math.random()*(0.04f-0.008f)));
			scale[i] = randomSize*planet.getModel().getScaleX(); 
		}	
		instancedModels[0].setScale(scale);
		instancedModels[1].setScale(scale);
		instancedModels[2].setScale(scale);
		instancedModels[3].setScale(scale);
		instancedModels[4].setScale(scale);
		instancedModels[5].setScale(scale);
	}

}
