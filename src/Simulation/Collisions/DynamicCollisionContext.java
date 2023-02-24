package Simulation.Collisions;

import Simulation.Collisions.Boundings.BoundingCircle;
import Simulation.Collisions.Boundings.BoundingPolygon;
import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.Objects.StaticObjects.StaticObject;
import Simulation.RenderEngine.Core.Math.Vector2f;

public class DynamicCollisionContext extends CollisionContext{

	public float tmp = 0.5f;
////////////////////
////Constructors////
////////////////////
 public DynamicCollisionContext(GameObject gameObject) {
		super(gameObject);
	}
 
	public DynamicCollisionContext(GameObject gameObject,BoundingCircle[] boundingCircles, BoundingPolygon[] boundingPolygons) {
		super(gameObject, boundingCircles, boundingPolygons);
	}
	
	public DynamicCollisionContext(GameObject gameObject,BoundingCircle[] boundingCircles) {
		super(gameObject, boundingCircles);
	}

	public DynamicCollisionContext(GameObject gameObject,BoundingCircle boundingCircle) {
		super(gameObject, boundingCircle);
	}
	
	public DynamicCollisionContext(GameObject gameObject,BoundingPolygon[] boundingPolygons) {
		super(gameObject, boundingPolygons);
	}
	
	public DynamicCollisionContext(GameObject gameObject,BoundingPolygon boundingPolygon) {
		super(gameObject, boundingPolygon);
	}
	
	public void checkCollisions() {
		for (GameObject object : GameObject.allObjects) 
			if (object.getCollisionContext().getID()!=id && object.getCollisionContext().canCollide()) 
				checkCollision(object.getCollisionContext());							
	}
	
///////////////
////Methods////
///////////////
	public void checkCollision(CollisionContext context) {	
		checkCollisionCircleCircle(context);
		checkCollisionPolygonCircle(context);		
		checkCollisionPolygonPolygon(context);
	}
	

	public void collisionCircleCircle(BoundingCircle circle1 , BoundingCircle circle2, GameObject object) {					
		float r1 = circle1.getRadius();
		float r2 = circle2.getRadius();
		
		if(object instanceof MoveableObject) {
			CollisionCircleCircle.removeCollision((MoveableObject)gameObject, r1, (MoveableObject)object, r2);
			
			if(new Vector2f(((MoveableObject) this.getGameObject()).getVelocityX(), ((MoveableObject) this.getGameObject()).getVelocityY()).length() > tmp) {
				CollisionCircleCircle.elasticCollision((MoveableObject)gameObject, (MoveableObject)object);
				object.onCollision();
				this.getGameObject().onCollision();
			}
		}else {
			CollisionCircleCircle.removeCollision((MoveableObject)gameObject, r1, (StaticObject)object, r2);
			
			if(new Vector2f(((MoveableObject) this.getGameObject()).getVelocityX(), ((MoveableObject) this.getGameObject()).getVelocityY()).length() > tmp) {
				CollisionCircleCircle.elasticCollision((MoveableObject)gameObject, (StaticObject)object);
				object.onCollision();
				this.getGameObject().onCollision();
			}
		}
	}
	
	public void collisionCirclePolygon(BoundingPolygon polygon, BoundingCircle circle, GameObject object) {
		Vector2f p1 = null;
		Vector2f p2 = null;
		
		for (int i = 0; i < polygon.getPoints().length; i++) {
			 Vector2f testP1 = polygon.getPoints()[i];
			 Vector2f testP2 = polygon.getPoints()[i + 1 == polygon.getPoints().length ? 0 : i + 1];
			
			 if(circle.checkCollisionLineCircle(testP1,testP2)) {
				 p1 = testP1;
				 p2 = testP2;
				 i=polygon.getPoints().length;
			 }
		}
		
		if(object instanceof MoveableObject) {			
			CollisionCirclePolygon.removeCollision((MoveableObject)gameObject, (MoveableObject)object, p1, p2);
				
			if(new Vector2f(((MoveableObject) this.getGameObject()).getVelocityX(), ((MoveableObject) this.getGameObject()).getVelocityY()).length() > tmp) {
				CollisionCirclePolygon.elasticCollision((MoveableObject)gameObject, (MoveableObject)object, p1, p2,polygon,circle);
				object.onCollision();
				this.getGameObject().onCollision();
			}else {
				((MoveableObject)this.getGameObject()).setVelocityY(0);
			}
		}else {			
			CollisionCirclePolygon.removeCollision((MoveableObject)gameObject,(StaticObject)object, p1, p2);
			
			if(new Vector2f(((MoveableObject) this.getGameObject()).getVelocityX(), ((MoveableObject) this.getGameObject()).getVelocityY()).length() > tmp) {
				CollisionCirclePolygon.elasticCollision((MoveableObject)gameObject, (StaticObject)object, p1, p2);	
				object.onCollision();
				this.getGameObject().onCollision();	
			}else {
				((MoveableObject)this.getGameObject()).setVelocityY(0);
			}
				
		}
			
	}
	
	public void collisionPolygonPolygon(BoundingPolygon polygon1, BoundingPolygon polygon2, GameObject object) {
		Vector2f p1 = new Vector2f(0,0);
		Vector2f p2 = new Vector2f(0,0);
		Vector2f p3 = new Vector2f(0,0);
		Vector2f p4 = new Vector2f(0,0);
		
		for (int i = 0; i < polygon1.getPoints().length; i++) {
			Vector2f testP1 = polygon1.getPoints()[i];
			Vector2f testP2 = polygon1.getPoints()[i + 1 == polygon1.getPoints().length ? 0 : i + 1];
			 
			for (int j = 0; j < polygon2.getPoints().length; j++) {
				
				Vector2f testP3 = polygon2.getPoints()[j];
				Vector2f testP4 = polygon2.getPoints()[j + 1 == polygon2.getPoints().length ? 0 : j + 1];
			 
				 if(polygon1.checkCollision(testP1,testP2,testP3,testP4)) {
					 p1 = testP1;
					 p2 = testP2;
					 p3 = testP3;
					 p4 = testP4;
					 i=polygon1.getPoints().length;
					 j=polygon2.getPoints().length;
				 }
			}
		}

		if(object instanceof MoveableObject) {			
			CollisionPolygonPolygon.removeCollision((MoveableObject)gameObject, (MoveableObject)object, p3, p4);
			
			if(new Vector2f(((MoveableObject) this.getGameObject()).getVelocityX(), ((MoveableObject) this.getGameObject()).getVelocityY()).length() > tmp) {
				CollisionPolygonPolygon.elasticCollision((MoveableObject)gameObject, p1, p2,(MoveableObject)object,p3,p4,polygon1,polygon2);
				object.onCollision();
				this.getGameObject().onCollision();
			}
		}else {			
			CollisionPolygonPolygon.removeCollision((MoveableObject)gameObject,(StaticObject)object, p3, p4);
			
			if(new Vector2f(((MoveableObject) this.getGameObject()).getVelocityX(), ((MoveableObject) this.getGameObject()).getVelocityY()).length() > tmp) {
				CollisionPolygonPolygon.elasticCollision((MoveableObject)gameObject, (StaticObject)object, p3, p4);	
				object.onCollision();
				this.getGameObject().onCollision();	
			}
		}
			
	}
	
	public void checkCollisionCircleCircle(CollisionContext context) {
		for (BoundingCircle circle1 : boundingCirlces) 
			for (BoundingCircle circle2 : context.getBoundingCircles()) 
				if(circle1.checkCollision(circle2)) 
					collisionCircleCircle(circle1,circle2,context.getGameObject());
	}
	
	public void checkCollisionPolygonPolygon(CollisionContext context) {
		for (BoundingPolygon polygon1 : boundingPolygons) 
			for (BoundingPolygon polygon2 : context.getBoundingPolygons()) 			
				if(polygon1.checkCollision(polygon2)) 
					collisionPolygonPolygon(polygon1, polygon2, context.getGameObject());
	}
	
	public void checkCollisionPolygonCircle(CollisionContext context) {
		for (BoundingPolygon polygon : boundingPolygons) 
			for (BoundingCircle circle : context.getBoundingCircles()) 
				if(circle.checkCollision(polygon)) 
					collisionCirclePolygon(polygon,circle,context.getGameObject());
				
		for (BoundingPolygon polygon : context.getBoundingPolygons()) 
			for (BoundingCircle circle : boundingCirlces) 
				if(circle.checkCollision(polygon)) 
					collisionCirclePolygon(polygon,circle,context.getGameObject());
	}
	
	public boolean checkCollisionPolygonCircle2(CollisionContext context) {
		for (BoundingPolygon polygon : boundingPolygons) 
			for (BoundingCircle circle : context.getBoundingCircles()) 
				if(circle.checkCollision(polygon)) 
					return true;
				
		for (BoundingPolygon polygon : context.getBoundingPolygons()) 
			for (BoundingCircle circle : boundingCirlces) 
				if(circle.checkCollision(polygon)) 
					return true;
		
		return false;
	}
	
	public boolean checkCollisionPolygonPolygon2(CollisionContext context) {
		for (BoundingPolygon polygon1 : boundingPolygons) 
			for (BoundingPolygon polygon2 : context.getBoundingPolygons()) 			
				if(polygon1.checkCollision(polygon2)) 
					return true;
		
		return false;
	}
}
