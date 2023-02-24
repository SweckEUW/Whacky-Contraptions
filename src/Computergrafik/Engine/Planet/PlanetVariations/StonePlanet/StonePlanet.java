package Computergrafik.Engine.Planet.PlanetVariations.StonePlanet;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.Stones.Stone1;
import Computergrafik.Engine.Planet.PlanetEntities.Stones.Stone2;
import Computergrafik.Engine.Planet.PlanetEntities.Stones.Stone3;
import Computergrafik.Engine.Planet.PlanetEntities.Stones.Stone4;
import Computergrafik.Engine.Planet.PlanetParts.FloatingStones.FloatingStone1;
import Computergrafik.Engine.Planet.PlanetParts.FloatingStones.FloatingStone2;
import Computergrafik.Engine.Planet.PlanetParts.FloatingStones.FloatingStone3;
import Computergrafik.Engine.Planet.PlanetParts.FloatingStones.FloatingStone4;
import Computergrafik.Engine.Planet.PlanetParts.FloatingStones.FloatingStone5;
import Computergrafik.Engine.Planet.PlanetParts.FloatingStones.FloatingStone6;

public class StonePlanet extends Planet {
	
	private int NUMBER_OF_STONES = 5000;
	
	
	public StonePlanet(Planet planet,Vector3f translation,float scale, Material material,Vector3f orbitAxis) {
		super(new StonePlanetMesh(scale), planet,translation,scale, material,orbitAxis);
		NUMBER_OF_STONES=(int)(Math.random()*NUMBER_OF_STONES);
		addEntities(); 
		addParts();
	}

	public StonePlanet(Vector3f translation,float scale, Material material) {
		super(new StonePlanetMesh(scale),translation,scale, material);
		NUMBER_OF_STONES=(int)(Math.random()*NUMBER_OF_STONES);
		addEntities(); 
	} 
	
	private void addParts() {
		addPart(new FloatingStone1(this));
		addPart(new FloatingStone1(this));
		addPart(new FloatingStone2(this));
		addPart(new FloatingStone3(this));
		addPart(new FloatingStone4(this));
		addPart(new FloatingStone5(this));
		addPart(new FloatingStone6(this));
		addPart(new FloatingStone6(this));
	}
	
	private void addEntities() {
		addStones();	
	}
	
	private void addStones() {
		int stone1=1;
		int stone2=1;
		int stone3=1;
		int stone4=1;
		for (int i = 0; i < NUMBER_OF_STONES; i++) {
			float randomNumber = (float)(Math.random()*6);
			if (randomNumber<1) 
				stone3++;
			else if (randomNumber<2) 
				stone2++;
			else if (randomNumber<2) 
				stone4++;
			else 
				stone1++;			
		}
		this.addEntity(new Stone1(this, stone1));
		this.addEntity(new Stone2(this, stone2));
		this.addEntity(new Stone3(this, stone3));
		this.addEntity(new Stone4(this, stone4));
	}
	
}