package Simulation.Collisions.Boundings;

import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Models.LineModel;
import Simulation.RenderEngine.Primitives.PolygonLine;

public class BoundingPolygon extends Bounding{
	
	Vector2f[] originalPoints;
	Vector2f[] points;

////////////////////
////Constructors////
////////////////////
	public BoundingPolygon(float x, float y,Vector2f[] points) {
		super(x, y);
		this.originalPoints = points;
		
		//copy Array and add current position
		this.points = new Vector2f[originalPoints.length];
		for (int i = 0; i < this.points.length; i++) 
			this.points[i]= new Vector2f(originalPoints[i].x+x, originalPoints[i].y+y);
		
		model = new LineModel(new PolygonLine(points),0,1,0,x,y);
	}

	
/////////////////
////Collision////
/////////////////
	
	//http://www.dyn4j.org/2010/01/sat/
	public boolean checkCollision(BoundingPolygon polygon) {
		for (BoundingPolygon polyontmp : new BoundingPolygon[] {this,polygon}) {
			
			//loop over all points
			for (int i = 0; i < polyontmp.getPoints().length; i++){
				
				//calculate normal
			    Vector2f p1 = polyontmp.getPoints()[i];
			    Vector2f p2 = polyontmp.getPoints()[i + 1 == polyontmp.getPoints().length ? 0 : i + 1];
	
			    Vector2f normal = new Vector2f(p2.y - p1.y, p1.x - p2.x);
	
			    //project Polygon1 onto axis
			    float minA = 0;
			    float maxA = 0;
			    for(Vector2f p : points){
			    	float projected = normal.x * p.x + normal.y * p.y;
			    	if (minA == 0 || projected < minA)
			    		minA = projected; //find most left Point of Polygon1
			    	if (maxA == 0 || projected > maxA)
			    		maxA = projected; //find most right Point of Polygon1
			    }
			    
			  //project Polygon2 onto axis
			    float minB = 0;
			    float maxB = 0;
			    for(Vector2f p : polygon.getPoints()){
			    	float projected = normal.x * p.x + normal.y * p.y;
			    	if (minB == 0 || projected < minB)
			    		minB = projected; //find most left Point of Polygon2
			    	if (maxB == 0 || projected > maxB)
			    		maxB = projected;  //find most right Point of Polygon2
			    }
			    
			    //check if there is space between left and right most Points of each polygon
			    if (maxA < minB || maxB < minA)
			    	return false;
			    
			}
		}
		
	 	return true;
	}
	
	
/////////////////////////
////Getters & Setters////
/////////////////////////
	public Vector2f[] getPoints() {
		return points;
	}
	
	public void setX(float x) {
		super.setX(x);	
		
		//copy Array
		points = new Vector2f[originalPoints.length];
		for (int i = 0; i < points.length; i++) 
			points[i]= new Vector2f(originalPoints[i].x, originalPoints[i].y);
		
		for (Vector2f p : points) {
			p.rotate(rotation);
			p.x=p.x*scale+x;		
			p.y=p.y*scale+y;
		}
		
		model = new LineModel(new PolygonLine(points),0,1,0,0,0);
	}
	
	public void setY(float y) {
		super.setY(y);
		
		//copy Array
		points = new Vector2f[originalPoints.length];
		for (int i = 0; i < points.length; i++) 
			points[i]= new Vector2f(originalPoints[i].x, originalPoints[i].y);
		
		for (Vector2f p : points) {
			p.rotate(rotation);
			p.x=p.x*scale+x;		
			p.y=p.y*scale+y;
		}
		
		model = new LineModel(new PolygonLine(points),0,1,0,0,0);
	}
	
	public void setRotation(float rotation) {
		super.setRotation(rotation);

		//copy Array
		points = new Vector2f[originalPoints.length];
		for (int i = 0; i < points.length; i++) 
			points[i]= new Vector2f(originalPoints[i].x, originalPoints[i].y);
		
		for (Vector2f p : points) {			
			p.rotate(rotation);
			p.x=p.x*scale+x;		
			p.y=p.y*scale+y;
		}

		model = new LineModel(new PolygonLine(points),0,1,0,0,0);
	}
	
	public void setScale(float scale) {
		super.setScale(scale);

		//copy Array
		points = new Vector2f[originalPoints.length];
		for (int i = 0; i < points.length; i++) 
			points[i]= new Vector2f(originalPoints[i].x, originalPoints[i].y);
		
		for (Vector2f p : points) {			
			p.rotate(rotation);
			p.x=p.x*scale+x;		
			p.y=p.y*scale+y;
		}
		
		model = new LineModel(new PolygonLine(points),0,1,0,0,0);
	}


	public boolean checkCollision(Vector2f a, Vector2f b,Vector2f c, Vector2f d) {
		    float denominator = ((b.x - a.x) * (d.y - c.y)) - ((b.y - a.y) * (d.x - c.x));
		    float numerator1 = ((a.y - c.y) * (d.x - c.x)) - ((a.x - c.x) * (d.y - c.y));
		    float numerator2 = ((a.y - c.y) * (b.x - a.x)) - ((a.x - c.x) * (b.y - a.y));

		    // Detect coincident lines (has a problem, read below)
		    if (denominator == 0) return numerator1 == 0 && numerator2 == 0;

		    float r = numerator1 / denominator;
		    float s = numerator2 / denominator;

		    return (r >= 0 && r <= 1) && (s >= 0 && s <= 1);
	}

}