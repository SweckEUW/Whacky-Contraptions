package Computergrafik.Engine.Planet.PlanetParts.Water;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;

public class SphericalWater extends Water {
	
	public SphericalWater(Planet planet) {
		super(planet);
	}
	
	@Override
	public void calculateColors() {
		colors[0] = new float[models[0].getMesh().getVertices().length];
		for (int i = 0; i < colors[0].length; i+=3) {
			colors[0][i]=10f/255f*(0.6f+((float)Math.random()*(1f-0.6f)));
			colors[0][i+1]=0.532f*(0.6f+((float)Math.random()*(1f-0.6f)));
			colors[0][i+2]=0.745f*(0.6f+((float)Math.random()*(1f-0.6f)));
		}
		models[0].getMesh().setColorValues(colors[0]);
	}

	@Override
	protected void setModels() {
					 	  //new Material(ambientColor,       diffuseColor,       specularColor,       shininess)
		Material material = new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1,1,1), 10);
		
		models[0] = new Model("Primitives/Cuboid",material);
		float[] k = new float[models[0].getMesh().getVertices().length];
		for (int i = 0; i < k.length; i+=3) {
			Vector3f kVector3f =new Vector3f(models[0].getMesh().getVertices()[i], models[0].getMesh().getVertices()[i+1], models[0].getMesh().getVertices()[i+2]);
			kVector3f.normalize();
			k[i]=kVector3f.x;
			k[i+1]=kVector3f.y;
			k[i+2]=kVector3f.z;
		}	
		models[0].getMesh().setVertices(k, true);
		
	}

}
