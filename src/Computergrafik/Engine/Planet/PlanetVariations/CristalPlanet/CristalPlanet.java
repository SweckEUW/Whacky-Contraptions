package Computergrafik.Engine.Planet.PlanetVariations.CristalPlanet;

import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;
import Computergrafik.Engine.Planet.PlanetParts.PlanetLight;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;
import Computergrafik.Engine.Planet.PlanetParts.PlanetCore.InnerPlanetCore;
import Computergrafik.Engine.Planet.PlanetParts.PlanetCore.OutterPlanetCore1;
import Computergrafik.Engine.Planet.PlanetParts.PlanetCore.OutterPlanetCore2;

public class CristalPlanet extends Planet {
	
	private PlanetLight light1;
	private PlanetLight light2;
	private PlanetLight light3;
	
	public CristalPlanet(Planet planet,Vector3f translation,float scale, Material material,Vector3f orbitAxis) {
		super(new CristalPlanetMesh(scale), planet,translation,scale, material,orbitAxis);
		addPlanetParts();
		light1=new PlanetLight(this, new Vector3f(1,0,0),  new Vector3f(1,0,0), new Vector3f(1, 0.045f, 0.0075f));
		light2=new PlanetLight(this, new Vector3f(0,1,0),  new Vector3f(0,1,0), new Vector3f(1, 0.045f, 0.0075f));
		light3=new PlanetLight(this, new Vector3f(1,1,0.7f),  new Vector3f(1,1,0.7f), new Vector3f(1, 0.045f, 0.0075f));
	}

	public CristalPlanet(Vector3f translation,float scale, Material material) {
		super(new CristalPlanetMesh(scale),translation,scale, material);
		addPlanetParts();
		light1=new PlanetLight(this, new Vector3f(1,0,0),  new Vector3f(1,0,0), new Vector3f(1, 0.045f, 0.0075f));
		light2=new PlanetLight(this, new Vector3f(0,1,0),  new Vector3f(0,1,0), new Vector3f(1, 0.045f, 0.0075f));
		light3=new PlanetLight(this, new Vector3f(1,1,0.7f),  new Vector3f(1,1,0.7f), new Vector3f(1, 0.045f, 0.0075f));
	} 
	
	private void addPlanetParts() {
		addPart(new InnerPlanetCore(this));
		addPart(new OutterPlanetCore1(this));
		addPart(new OutterPlanetCore2(this));
	}
	
	public void update() {			
		if (!stopUpdate) {		
			if (planetToRotateArround!=null) {
				Matrix4f m = planetToRotateArround.getModel().getModelMatrix();
				float newCenterX = m.m03;
				float newCenterY = m.m13;
				float newCenterZ = m.m23;
				
				setCenterX(newCenterX);
				setCenterY(newCenterY);
				setCenterZ(newCenterZ);
							
				for (PlanetPart part : planetparts) 
					for (int i = 0; i < part.getModels().length; i++) 	
						part.getModels()[i].addRotationArroundPoint(new Vector3f(newCenterX, newCenterY, newCenterZ),planetModel.getOrbitAxis(),planetModel.getOrbitRotation());
				for (PlanetEntity entity : planetEntities) 
					for (int i = 0; i < entity.getInstancedModels().length; i++) 	
						for (int j = 0; j < entity.getInstances(); j++) 
							entity.getInstancedModels()[i].addRotationArroundPoint(new Vector3f(newCenterX, newCenterY, newCenterZ),planetModel.getOrbitAxis(),planetModel.getOrbitRotation(),j);
						
						
				
				orbit.getModel().addRotationArroundPoint(new Vector3f(newCenterX, newCenterY, newCenterZ),planetModel.getOrbitAxis(),planetModel.getOrbitRotation());
								
				updateAttachedPlanets(new Vector3f(newCenterX, newCenterY, newCenterZ),planetModel.getOrbitAxis(),planetModel.getOrbitRotation());	
				
				increaseOrbitRotation();
				addRotationArroundPoint();
			}
			
			increaseRotation();
			
			for (PlanetPart part : planetparts) 
				part.update();
			for (PlanetEntity entity : planetEntities) 
				entity.update();
			
		}
		updateColors();
			
		light1.update(5,0,0);
		light2.update(-5,0,0);
		light3.update(0,10,0);
				
		if (generateRandom) {
			generateRandom=false;
			generateRandom();
		}		
		
	}
		
}