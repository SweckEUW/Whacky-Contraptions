package Computergrafik.Engine.Planet.PlanetVariations.DarkMatterPlanet;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetParts.FloatingParicle.FloatingParticle1;
import Computergrafik.Engine.Planet.PlanetParts.FloatingParicle.FloatingParticle2;
import Computergrafik.Engine.Planet.PlanetParts.FloatingParicle.FloatingParticle3;

public class DarkMatterPlanet extends Planet {
	
	private int NUMBER_OF_FLOATING_PARTICLE = 40;
	
	public DarkMatterPlanet(Planet planet,Vector3f translation,float scale, Material material,Vector3f orbitAxis) {
		super(new DarkMatterPlanetMesh(scale), planet,translation,scale, material,orbitAxis);
		addPlanetParts();		
	}

	public DarkMatterPlanet(Vector3f translation,float scale, Material material) {
		super(new DarkMatterPlanetMesh(scale),translation,scale, material);
		addPlanetParts();
	} 
	

	private void addPlanetParts() {
		for (int i = 0; i < NUMBER_OF_FLOATING_PARTICLE; i++) {
			addPart(new FloatingParticle1(this));
			addPart(new FloatingParticle2(this));
			addPart(new FloatingParticle3(this));
		}
	}

}