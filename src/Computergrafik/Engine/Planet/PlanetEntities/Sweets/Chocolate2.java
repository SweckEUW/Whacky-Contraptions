package Computergrafik.Engine.Planet.PlanetEntities.Sweets;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;

public class Chocolate2 extends PlanetEntity{

	private static final String FILE = "PlanetEntities/Sweets/chocolate2";
	private static final int NUMBER_OF_MODELS = 1;
	
	public Chocolate2(Planet planet,int instances) {
		super(planet,NUMBER_OF_MODELS,instances);	
	}

	@Override
	public void calculateColors() {	
		float[] colors;
			
		colors= new float[3*instances];
		colorOffset=0.5f;
		for (int i = 0; i < colors.length; i+=3) { 
			colors[i]=160f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=82f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=45f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
		}
		instancedModels[0].getInstancedMesh().setColors(colors);		
	}

	@Override
	protected void setModels() {
							   //new Material(ambientColor,       diffuseColor,    specularColor,               shininess)
		Material materialStamm = new Material(new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.1f,0.1f,0.1f), 64);
		
		instancedModels[0] = new InstancedModel(FILE,instances,materialStamm);
	}

	@Override
	protected void setScale() {
		maxHeight = 20f; 
		minHeight = 0f;
		float[] scale = new float[instances];
		for (int i = 0; i < instances; i++) {
			float randomSize = (0.05f+((float)Math.random()*(0.08f-0.05f)));
			scale[i] = randomSize*planet.getModel().getScaleX(); 
		}	
		instancedModels[0].setScale(scale);
	}


}
