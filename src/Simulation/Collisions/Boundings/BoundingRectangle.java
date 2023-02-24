package Simulation.Collisions.Boundings;

import Simulation.RenderEngine.Core.Math.Vector2f;

public class BoundingRectangle extends BoundingPolygon{
	
	private float width,height;

////////////////////
////Constructors////
////////////////////
	public BoundingRectangle(float x,float y,float width,float height) {
		super(x, y,new Vector2f[] {new Vector2f(width/2, height/2),new Vector2f(width/2, -height/2),new Vector2f(-width/2, -height/2),new Vector2f(-width/2, height/2)});
		this.width=width;
		this.height=height;	
	}
	
	public BoundingRectangle(float x,float y,float size) {
		super(x, y,new Vector2f[] {new Vector2f(size/2, size/2),new Vector2f(size/2, -size/2),new Vector2f(-size/2, -size/2),new Vector2f(-size/2, size/2)});
		this.width=size;
		this.height=size;
	}
	
	
/////////////////
////Collision////
/////////////////
//	public boolean checkCollision(BoundingRectangle rectangle) {	
//		if (rotation == rectangle.getRotation()) {
//			Vector2f r1 = Util.rotate(x, y, rotation*(float)Math.PI/(float)180);
//			Vector2f r2 = Util.rotate(rectangle.getX(), rectangle.getY(), rectangle.getRotation()*(float)Math.PI/(float)180);
//		
//			return ( r2.x 	+ 	rectangle.getWidth()/2 		> 	 r1.x	-	width/2 					&&
//					 r2.y 	+ 	rectangle.getHeight()/2 	>	 r1.y	-	height/2					&&
//					 r1.x	+ 	width/2 					>	 r2.x 	-	rectangle.getWidth()/2 		&&
//					 r1.y	+ 	height/2 					>	 r2.y	-	rectangle.getHeight()/2);
//		}
//		
//		return false;
//	} 
	
	
/////////////////////////
////Getters & Setters////
/////////////////////////
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
}