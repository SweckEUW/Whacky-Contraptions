package Computergrafik.Engine.Planet.PlanetVariations.SinusPlanet;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.Sweets.Chocolate1;
import Computergrafik.Engine.Planet.PlanetEntities.Sweets.Chocolate2;
import Computergrafik.Engine.Planet.PlanetEntities.Sweets.Donut;
import Computergrafik.Engine.Planet.PlanetEntities.Sweets.Lolipop1;
import Computergrafik.Engine.Planet.PlanetEntities.Sweets.Lolipop2;
import Computergrafik.Engine.Planet.PlanetEntities.Sweets.Lolipop3;
import Computergrafik.Engine.Planet.PlanetParts.Rings.Ring1;
import Computergrafik.Engine.Planet.PlanetParts.Rings.Ring2;

public class SinPlanet extends Planet {

	private int NUMBER_OF_CHOCOLATE = 400;
	private int NUMBER_OF_LOLIPOP = 400;
	private int NUMBER_OF_DONUT = 400;
	
	public SinPlanet(Planet planet,Vector3f translation,float scale, Material material,Vector3f orbitAxis) {
		super(new SinPlanetMesh(scale), planet,translation,scale, material,orbitAxis);
		this.addPart(new Ring1(this));
		this.addPart(new Ring2(this));
		NUMBER_OF_CHOCOLATE=(int)(Math.random()*NUMBER_OF_CHOCOLATE);
		NUMBER_OF_LOLIPOP=(int)(Math.random()*NUMBER_OF_LOLIPOP);
		NUMBER_OF_DONUT=(int)(Math.random()*NUMBER_OF_DONUT);
		addChocolate();
		addLolipop();
		addDonut();
	} 

	public SinPlanet(Vector3f translation, float scale, Material material) {
		super(new SinPlanetMesh(scale),translation,scale, material);
		this.addPart(new Ring1(this));
		this.addPart(new Ring2(this));
		NUMBER_OF_CHOCOLATE=(int)(Math.random()*NUMBER_OF_CHOCOLATE);
		NUMBER_OF_LOLIPOP=(int)(Math.random()*NUMBER_OF_LOLIPOP);
		NUMBER_OF_DONUT=(int)(Math.random()*NUMBER_OF_DONUT);
		addChocolate();
		addLolipop();
		addDonut();
	}
	
	private void addChocolate() {
		int chocolate1=1;
		int chocolate2=1;
		for (int i = 0; i < NUMBER_OF_CHOCOLATE; i++) {
			int randomNumber = (int)(Math.random()*2);
			if (randomNumber==0) 
				chocolate1++;
			else if (randomNumber==1) 
				chocolate2++;

		}
		this.addEntity(new Chocolate1(this, chocolate1));
		this.addEntity(new Chocolate2(this, chocolate2));
	}

	private void addLolipop() {
		int lolipop1=1;
		int lolipop2=1;
		int lolipop3=1;
		for (int i = 0; i < NUMBER_OF_LOLIPOP; i++) {
			int randomNumber = (int)(Math.random()*3);
			if (randomNumber==0) 
				lolipop1++;
			else if (randomNumber==1) 
				lolipop2++;
			else 
				lolipop3++;
			
		}
		this.addEntity(new Lolipop1(this, lolipop1));
		this.addEntity(new Lolipop2(this, lolipop2));
		this.addEntity(new Lolipop3(this, lolipop3));
	}
	
	private void addDonut() {
		this.addEntity(new Donut(this, NUMBER_OF_DONUT));
	}
	
}
