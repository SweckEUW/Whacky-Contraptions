package Computergrafik.Engine.Planet.PlanetEntities.Grass;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;

public class Grass1 extends PlanetEntity{

	private static final String FILE = "PlanetEntities/Grass/Grass1";
	private static final int NUMBER_OF_MODELS = 1;
	
	public Grass1(Planet planet,int instances) {
		super(planet,NUMBER_OF_MODELS,instances);	
	}

	@Override
	public void calculateColors() {	
		float[] colors;
			
		colors= new float[3*instances];
		colorOffset=0.3f;
		for (int i = 0; i < colors.length; i+=3) { 
			colors[i]=14f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=155f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=14f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[0].getInstancedMesh().setColors(colors);
			
		
	}

	@Override
	protected void setModels() {
							   //new Material(ambientColor,       diffuseColor,    specularColor,               shininess)
		Material materialStamm = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 16);
		
		instancedModels[0] = new InstancedModel(FILE,instances,materialStamm);
	}

	@Override
	protected void setScale() {
		float[] scale = new float[instances];
		for (int i = 0; i < instances; i++) {
			float randomSize = (0.008f+((float)Math.random()*(0.01f-0.008f)));
			scale[i] = randomSize*planet.getModel().getScaleX(); 
		}	
		instancedModels[0].setScale(scale);
	}


}
