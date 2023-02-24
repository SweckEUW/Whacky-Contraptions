package Simulation.Collisions;

import Simulation.Util;
import Simulation.Collisions.Boundings.BoundingPolygon;
import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.Objects.StaticObjects.StaticObject;
import Simulation.RenderEngine.Core.Math.Vector2f;

public class CollisionPolygonPolygon {


/////////////////////////
////ELASTIC COLLISION////
/////////////////////////
	public static void elasticCollision(MoveableObject object1, Vector2f p3 , Vector2f p4, MoveableObject object2, Vector2f p1 , Vector2f p2,BoundingPolygon polygon1 , BoundingPolygon polygon2) {
		float length = Util.getDistance(p4.x, p4.y, p3.x, p3.y);
		float nx = (p4.x - p3.x)/length;
		float ny = (p4.y - p3.y)/length;

		float tx = -ny;
		float ty = nx;

		float dpTan1 = object1.getVelocityX()*tx + object1.getVelocityY()*ty;
		float dpTan2 = object2.getVelocityX()*tx + object2.getVelocityY()*ty;
		
		float dpNorm1 = object1.getVelocityX() * nx + object1.getVelocityY()*ny;
		float dpNorm2 = object2.getVelocityX() * nx + object2.getVelocityY()*ny;
			
		float m1 = (dpNorm1 * (object1.getMass() - object2.getMass()) + 2 * object2.getMass() *dpNorm2 ) / (object1.getMass() + object2.getMass());	
		float m2 = (dpNorm2 * (object2.getMass() - object1.getMass()) + 2 * object1.getMass() *dpNorm1 ) / (object1.getMass() + object2.getMass());
			
		float k = (object1.getCoefficientOfRestitution()+object2.getCoefficientOfRestitution())/2;
		
		object1.setVelocityX(-(tx * dpTan1 + nx * m1)*k);
		object1.setVelocityY(-(ty * dpTan1 + ny * m1)*k);
		object2.setVelocityX(-(tx * dpTan2 + nx * m2)*k);
		object2.setVelocityY(-(ty * dpTan2 + ny * m2)*k);
	}
	
	public static void elasticCollision(MoveableObject object1, StaticObject object2, Vector2f p1, Vector2f p2) {
		float length = Util.getDistance(p1.x, p1.y, p2.x, p2.y);
		float nx = (p2.x - p1.x)/length;
		float ny = (p2.y - p1.y)/length;
		
		float tx = -ny;
		float ty = nx;
		
		float dpTan1 = object1.getVelocityX()*tx + object1.getVelocityY()*ty;
		
		float dpNorm1 = object1.getVelocityX() * nx + object1.getVelocityY()*ny;
		
		float m1 = (dpNorm1 * (object1.getMass() - object2.getMass()) ) / (object1.getMass() + object2.getMass());	
		
		float k = (object1.getCoefficientOfRestitution()+object2.getCoefficientOfRestitution())/2;
		
		object1.setVelocityX((-(tx * dpTan1 + nx * m1))*k);
		object1.setVelocityY((-(ty * dpTan1 + ny * m1))*k);		
	}
	
	
////////////////////////
////REMOVE COLLISION////
////////////////////////
	public static void removeCollision(MoveableObject object1, MoveableObject object2, Vector2f p1 , Vector2f p2) {
		float distance = Util.getDistance(p1.x, p1.y, p2.x, p2.y);				
		float nx = (p2.y - p1.y)/distance;			
		float ny = (p2.x - p1.x)/distance;

		DynamicCollisionContext context1 = (DynamicCollisionContext) object1.getCollisionContext();
		DynamicCollisionContext context2 = (DynamicCollisionContext) object2.getCollisionContext();
		
		do {		
			float overlap = 0.001f;
			
			float object1X = object1.getX() - overlap*nx;
			float object1Y = object1.getY() - overlap*ny;
			
			float object2X = object2.getX() + overlap*nx;
			float object2Y = object2.getY() + overlap*ny;
			
			object1.setX(object1X);
			object1.setY(object1Y);
			
			object2.setX(object2X);
			object2.setY(object2Y);
			
		} while(context1.checkCollisionPolygonPolygon2(context2));
		
	}
	
	public static void removeCollision(MoveableObject object1, StaticObject object2, Vector2f p1, Vector2f p2) {	
		float length = Util.getDistance(p1.x, p1.y, p2.x, p2.y);				
		float nx = -(p2.y - p1.y)/length;			
		float ny = (p2.x - p1.x)/length;

		DynamicCollisionContext context1 = (DynamicCollisionContext) object1.getCollisionContext();
		CollisionContext context2 = object2.getCollisionContext();
		
		do {			
			float overlap = 0.001f;
			
			float object1X = object1.getX()+overlap*nx;
			float object1Y = object1.getY()+overlap*ny;
			
			object1.setX(object1X);
			object1.setY(object1Y);
		
		} while(context1.checkCollisionPolygonPolygon2(context2));
		
	}
	
}