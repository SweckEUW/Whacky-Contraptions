package Computergrafik.Engine.Planet.PlanetVariations.Sun;

import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;
import Computergrafik.Engine.Planet.PlanetParts.PlanetLight;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;
import Computergrafik.Engine.Planet.PlanetParts.SunAtmosphere.SunAtmosphere1;
import Computergrafik.Engine.Planet.PlanetParts.SunAtmosphere.SunAtmosphere2;

public class Sun extends Planet {

	private PlanetLight light;
	
	public Sun(Planet planet,Vector3f translation,float scale, Material material,Vector3f orbitAxis) {
		super(new SunMesh(scale), planet,translation,scale, material,orbitAxis);
		light=new PlanetLight(this);
		addPart(new SunAtmosphere1(this));
		addPart(new SunAtmosphere2(this));
	}

	public Sun(Vector3f translation,float scale, Material material) {
		super(new SunMesh(scale),translation,scale, material);
		light=new PlanetLight(this);
		addPart(new SunAtmosphere1(this));
		addPart(new SunAtmosphere2(this));
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
			
		light.update();
				
		if (generateRandom) {
			generateRandom=false;
			generateRandom();
		}		
		
	}
	
}
