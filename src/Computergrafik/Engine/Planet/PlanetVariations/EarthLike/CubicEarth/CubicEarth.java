package Computergrafik.Engine.Planet.PlanetVariations.EarthLike.CubicEarth;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.Flowers.Flower1;
import Computergrafik.Engine.Planet.PlanetEntities.Grass.Grass1;
import Computergrafik.Engine.Planet.PlanetEntities.Mushrooms.Mushroom1;
import Computergrafik.Engine.Planet.PlanetEntities.Mushrooms.Mushroom2;
import Computergrafik.Engine.Planet.PlanetEntities.Mushrooms.Mushroom3;
import Computergrafik.Engine.Planet.PlanetEntities.Mushrooms.Mushroom4;
import Computergrafik.Engine.Planet.PlanetEntities.Mushrooms.Mushroom5;
import Computergrafik.Engine.Planet.PlanetEntities.Trees.Tree1;
import Computergrafik.Engine.Planet.PlanetEntities.Trees.Tree2;
import Computergrafik.Engine.Planet.PlanetEntities.Trees.Tree3;
import Computergrafik.Engine.Planet.PlanetParts.Water.CubicWater;

public class CubicEarth extends Planet {

	private int NUMBER_OF_TREES = 800;
	private int NUMBER_OF_FLOWERS = 800;
	private int NUMBER_OF_MUSHROOMS = 800;
	private int NUMBER_OF_GRASS = 1000; 
	
	public CubicEarth(Planet planet,Vector3f translation,float scale, Material material,Vector3f orbitAxis) {
		super(new CubicEarthMesh(scale), planet,translation,scale, material,orbitAxis);
		NUMBER_OF_TREES=(int)(Math.random()*NUMBER_OF_TREES);
		NUMBER_OF_FLOWERS=(int)(Math.random()*NUMBER_OF_FLOWERS);
		NUMBER_OF_MUSHROOMS=(int)(Math.random()*NUMBER_OF_MUSHROOMS);
		NUMBER_OF_GRASS=(int)(Math.random()*NUMBER_OF_GRASS);
		addEntities();
		addParts();
	}

	public CubicEarth(Vector3f translation, float scale, Material material) {
		super(new CubicEarthMesh(scale),translation,scale, material);
		NUMBER_OF_TREES=(int)(Math.random()*NUMBER_OF_TREES);
		NUMBER_OF_FLOWERS=(int)(Math.random()*NUMBER_OF_FLOWERS);
		NUMBER_OF_MUSHROOMS=(int)(Math.random()*NUMBER_OF_MUSHROOMS);
		NUMBER_OF_GRASS=(int)(Math.random()*NUMBER_OF_GRASS);
		addEntities();
		addParts();
	}

	private void addEntities() {
//		addTemples();
		addTrees(); 
		addFolwers();
		addMushrooms();
		addGrass();
	}
	
//	private void addTemples() {
//		int temple=1;
//		for (int i = 0; i < 5; i++) {
//			float randomNumber = (float)Math.random()*6;
//			if (randomNumber>4) 
//				temple++;
//		}
//		addEntity(new Temple1(this,temple));
//	}

	private void addGrass() {
		int grass1=1;
		int grass2=1;
		for (int i = 0; i < NUMBER_OF_GRASS; i++) {
			int randomNumber = (int)(Math.random()*2);
			if (randomNumber==0) 
				grass1++;
			else if (randomNumber==1) 
				grass2++;

		}
		this.addEntity(new Grass1(this, grass1));
		this.addEntity(new Tree2(this, grass2));
	}

	private void addTrees() {
		int tree1=1;
		int tree2=1;
		int tree3=1;
		for (int i = 0; i < NUMBER_OF_TREES; i++) {
			int randomNumber = (int)(Math.random()*4);
			if (randomNumber==0) 
				tree1++;
			else if (randomNumber==1) 
				tree2++;
			else if (randomNumber==2) 
				tree3++;			
		}
		this.addEntity(new Tree1(this, tree1));
		this.addEntity(new Tree2(this, tree2));
		this.addEntity(new Tree3(this, tree3));
	}

	private void addMushrooms() {
		int mushroom1=1;
		int mushroom2=1;
		int mushroom3=1;
		int mushroom4=1;
		int mushroom5=1;
		
		for (int i = 0; i < NUMBER_OF_MUSHROOMS; i++) {
			int randomNumber = (int)(Math.random()*5);
			if (randomNumber==0) 
				mushroom1++;
			else if (randomNumber==1) 
				mushroom2++;
			else if (randomNumber==2) 
				mushroom3++;
			else if (randomNumber==3) 
				mushroom4++;
			else if (randomNumber==4) 
				mushroom5++;
		
		}
		this.addEntity(new Mushroom1(this, mushroom1));
		this.addEntity(new Mushroom2(this, mushroom2));
		this.addEntity(new Mushroom3(this, mushroom3));
		this.addEntity(new Mushroom4(this, mushroom4));
		this.addEntity(new Mushroom5(this, mushroom5));
	}
	
	public void addFolwers() {
		int flower1=1;
//		int flower2=0;
		for (int i = 0; i < NUMBER_OF_FLOWERS; i++) {
			int randomNumber = (int)(Math.random()*1);
			if (randomNumber==0) 
				flower1++;
//			else if (randomNumber==1) 
//				flower2++;
		}
		this.addEntity(new Flower1(this, flower1));
//		this.addEntity(new Flower2(this, flower2));
	}
	
	private void addParts() {
		this.addPart(new CubicWater(this));
	}

	
}