package Simulation.Collisions.Boundings;

import Simulation.Util;
import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Models.LineModel;
import Simulation.RenderEngine.Primitives.CircleLine;

public class BoundingCircle  extends Bounding{

	private float radius;
	private float originalRadius;

////////////////////
////Constructors////
////////////////////
	public BoundingCircle(float x, float y,float radius) {
		super(x, y);
		this.radius = radius;
		originalRadius=radius;
		model = new LineModel(new CircleLine(radius, 30), 0, 0, 1, x,y);
	}
	

/////////////////
////Collision////
/////////////////
	public boolean checkCollision(BoundingCircle circle) {
		return circle.getRadius() + radius > Util.getDistance(circle.getX(), circle.getY(), x, y);
	}

	
	public boolean checkCollision(BoundingPolygon polygon) {
		for (int i = 0; i < polygon.getPoints().length; i++) {
			 Vector2f p1 = polygon.getPoints()[i];
			 Vector2f p2 = polygon.getPoints()[i + 1 == polygon.getPoints().length ? 0 : i + 1];
			
			 if(checkCollisionLineCircle(p1,p2))
				 return true;
		}
		
		return false;
	}
	
	public boolean checkCollisionLineCircle(Vector2f p1, Vector2f p2) {
		
		 if (checkCollisionPointCircle(p1) || checkCollisionPointCircle(p1)) 
			 return true;
		 
	    //get length of line;
	    float len = Util.getDistance(p1, p2);
	    
	    //get dot product of the line and circle
	    float dot = ( (x-p1.x)*(p2.x-p1.x) + (y-p1.y)*(p2.y-p1.y) ) / (float)Math.pow(len, 2); 
	    
	    // find the closest point on the line
	    float closestX = p1.x + (dot * (p2.x- p1.x));
	    float closestY = p1.y + (dot * (p2.y- p1.y));
	    
	    //check if closest point is on the line
	    // get distance from the point to the two ends of the line
	    float d1 = Util.getDistance(closestX, closestY,p1.x,p1.y);
	    float d2 = Util.getDistance(closestX, closestY,p2.x,p2.y);

	    // since floats are so minutely accurate, add
	    // a little buffer zone that will give collision
	    float buffer = 0.0001f;    // higher # = less accurate

	    // if the two distances are equal to the line's
	    // length, the point is on the line!
	    // note we use the buffer here to give a range,
	    // rather than one #
	    if (!(d1+d2 >= len-buffer && d1+d2 <= len+buffer)) 
	      return false;
	  	    
	    // get distance to closest point
	    float distX = closestX - x;
	    float distY = closestY - y;
	    float distance = (float)Math.sqrt( (distX*distX) + (distY*distY) );
	 		    
	   	if (distance < radius) 
			return true;
	   	
		return false;			
	}
	
	private boolean checkCollisionPointCircle(Vector2f p) {
		// get distance between the point and circle's center
		// using the Pythagorean Theorem
		float distX = p.x - x;
		float distY = p.y - y;
		float distance = (float)Math.sqrt( (distX*distX) + (distY*distY) );

		// if the distance is less than the circle's 
		// radius the point is inside!
		if (distance <= radius) 
			return true;
		  
		return false;
	}
	
	
	//Collision Circle-Rectangle
//	//https://www.gamedevelopment.blog/collision-detection-circles-rectangles-and-polygons/
//	public boolean checkCollision(BoundingRectangle rectangle) {
//			
//		Vector2f newP = Util.rotateArroundPoint(x, y, rectangle.getRotation(), rectangle.getX(), rectangle.getY());
//				
//		float distanceX = (float) Math.abs(newP.x - rectangle.getX());
//		float distanceY = (float) Math.abs(newP.y - rectangle.getY());
//
//		if (distanceX > rectangle.getWidth() / 2 + radius)		return false;
//		if (distanceY > rectangle.getHeight() / 2 + radius) 	return false;
//				
//		if (distanceX <= rectangle.getWidth() / 2)		return true;
//		if (distanceY <= rectangle.getHeight() / 2)		return true;
//				
//		float cornerDistance =(float) Math.pow((distanceX - rectangle.getWidth() / 2), 2) + (float) Math.pow((distanceY - rectangle.getHeight() / 2), 2);
//
//		return cornerDistance <= radius * radius;
//	}

	
/////////////////////////
////Getters & Setters////
/////////////////////////
	public float getRadius() {
		return radius;
	}
	
	public void setX(float x) { 
		super.setX(x);
		model = new LineModel(new CircleLine(radius, 30), 0, 0, 1, x,y);
	}
	
	public void setY(float y) { 
		super.setY(y);
		model = new LineModel(new CircleLine(radius, 30), 0, 0, 1, x,y);
	}
	
	public void setRotation(float rotation) {
		super.setRotation(rotation);
		model.setRotationZ(rotation);
	}
	
	public void setScale(float scale) {
		super.setScale(scale);
		this.radius=originalRadius*scale;
		model = new LineModel(new CircleLine(radius, 30), 0, 0, 1, x,y);
	}

}