package Simulation.Collisions;

import Simulation.Collisions.Boundings.BoundingCircle;
import Simulation.Collisions.Boundings.BoundingPolygon;
import Simulation.Objects.GameObject;

public class CollisionContext {
	
	protected static int counter;
	protected int id;
	
	protected BoundingCircle[] boundingCirlces = new BoundingCircle[0];
	protected BoundingPolygon[] boundingPolygons = new BoundingPolygon[0];
	
	protected GameObject gameObject;
	
	protected boolean canCollide = true;

////////////////////
////Constructors////
////////////////////
	public CollisionContext(GameObject gameObject) {
		this.boundingCirlces= new BoundingCircle[0];
		this.boundingPolygons = new BoundingPolygon[0];
		id=counter;
		counter++;
		this.gameObject=gameObject;
	}

	public CollisionContext(GameObject gameObject,BoundingCircle[] circles, BoundingPolygon[] boundingPolygons) {
		this.boundingCirlces= circles;
		this.boundingPolygons = boundingPolygons;
		id=counter;
		counter++;
		this.gameObject=gameObject;
	}
	
	public CollisionContext(GameObject gameObject,BoundingCircle[] boundingCircles) {
		this.boundingCirlces= boundingCircles;
		this.boundingPolygons = new BoundingPolygon[0];
		id=counter;
		counter++;
		this.gameObject=gameObject;
	}

	public CollisionContext(GameObject gameObject,BoundingCircle boundingCircle) {
		this.boundingCirlces = new BoundingCircle[1];
		this.boundingCirlces[0]=boundingCircle;
		this.boundingPolygons = new BoundingPolygon[0];
		id=counter;
		counter++;
		this.gameObject=gameObject;
	}
	
	public CollisionContext(GameObject gameObject,BoundingPolygon[] boundingPolygons) {
		this.boundingPolygons = boundingPolygons;
		this.boundingCirlces = new BoundingCircle[0];
		id=counter;
		counter++;
		this.gameObject=gameObject;
	}
	
	public CollisionContext(GameObject gameObject,BoundingPolygon boundingPolygon) {
		this.boundingPolygons = new BoundingPolygon[1];
		this.boundingPolygons[0]=boundingPolygon;
		this.boundingCirlces = new BoundingCircle[0];
		id=counter;
		counter++;
		this.gameObject=gameObject;
	}
		

/////////////////////////
////Getters & Setters////
/////////////////////////
	public BoundingCircle[] getBoundingCircles() {
		return boundingCirlces;
	}
	
	public BoundingPolygon[] getBoundingPolygons() {
		return boundingPolygons;
	}

	public BoundingPolygon getBoundingPolygon (int index) {
			return boundingPolygons[index];
	}

	public BoundingCircle getBoundingCircle(int index) {
		return boundingCirlces[index];
	}
	
	public void setBoundingCirlces(BoundingCircle[] boundingCirlces) {
		this.boundingCirlces = boundingCirlces;
	}

	public void setBoundingPolygons(BoundingPolygon[] boundingPolygons) {
		this.boundingPolygons = boundingPolygons;
	}

	public int getID() {
		return id;
	}
	
	public GameObject getGameObject() {
		return gameObject;
	}
	
	public void setX(float x) {
		for (BoundingCircle circle : boundingCirlces) 
			circle.setX(x);
	
		for (BoundingPolygon polygon : boundingPolygons) 
			polygon.setX(x);	
	}
	
	public void setY(float y) {
		for (BoundingCircle circle : boundingCirlces) 
			circle.setY(y);
	
		for (BoundingPolygon polygon : boundingPolygons) 
			polygon.setY(y);	
	}
	
	public void setRotation(float rotation) {
		for (BoundingCircle circle : boundingCirlces) 
			circle.setRotation(rotation);
	
		for (BoundingPolygon polygon : boundingPolygons) 
			polygon.setRotation(rotation);
	}

	public void setScale(float scale) {
		for (BoundingCircle circle : boundingCirlces) 
			circle.setScale(scale);
	
		for (BoundingPolygon polygon : boundingPolygons) 
			polygon.setScale(scale);
	}
		
	public boolean canCollide() {
		return canCollide;
	}
	
	public void canCollide(boolean canCollide) {
		this.canCollide = canCollide;
	}

}