package Computergrafik.Engine.Planet.PlanetEntities.Sweets;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;

public class Lolipop2 extends PlanetEntity{

	private static final String FILE1 = "PlanetEntities/Sweets/Lolipop2/Head";
	private static final String FILE2 = "PlanetEntities/Sweets/Lolipop2/Stil";
	private static final int NUMBER_OF_MODELS = 2;
	
	public Lolipop2(Planet planet,int instances) {
		super(planet,NUMBER_OF_MODELS,instances);	
	}

	@Override
	public void calculateColors() {	
		float[] colors;
		
		
		//Head	
		colors= new float[3*instances];
		colorOffset=0.01f; 
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[0].getInstancedMesh().setColors(colors);
		
		
		//Stil
		colors= new float[3*instances];
		colorOffset=0.9f;
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[1].getInstancedMesh().setColors(colors);	
		
	}

	@Override
	protected void setModels() {
							   //new Material(ambientColor,       diffuseColor,    specularColor,               shininess)
		Material materialStamm = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 16);
		Material materialKrone = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 16);
		
		instancedModels[0] = new InstancedModel(FILE1,instances,materialStamm);
		instancedModels[1] = new InstancedModel(FILE2,instances,materialKrone);
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
	}

}
