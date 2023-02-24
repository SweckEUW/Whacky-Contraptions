package Simulation;

import Simulation.RenderEngine.Core.Config;
import Simulation.RenderEngine.Core.Math.Vector2f;

public class Util {
	
	public static float round(float value) {
		return (float)Math.round(value * 100f) / 100f;
	}
	
	public static String getRoundedString(float value) {
		return Float.toString(round(value));
	}
	
	public static float getDistance(float x1,float y1,float x2,float y2) {
		return (float)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
	}
	
	public static float getDistance(Vector2f p1, Vector2f p2) {
		return (float)Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
	}
	
	public static Vector2f rotate(float x,float y,float angle) {
		float cos = (float)Math.cos(angle);
		float sin = (float)Math.sin(angle);
		float vx = cos * x - sin * y;
		float vy = sin * x + cos * y;
		return new Vector2f(vx, vy);
	}
	
	public static Vector2f rotateArroundPoint(float cx,float cy,float angle,float rx,float ry) {
		angle *= (float)Math.PI/(float)180;
		
		cx-=rx;
		cy-=ry;
		
		Vector2f newP = rotate(cx, cy, angle);
		
		return new Vector2f(newP.x+rx, newP.y+ry);
	}
	
	public static void copyVec2Array(Vector2f[] source,Vector2f[] dest) {
		dest = new Vector2f[source.length];
		for (int i = 0; i < dest.length; i++) 
			dest[i]= new Vector2f(source[i].x, source[i].y);
	}
	
	public static float getRandomVelocity(float scale) {
		if(Math.random() < 0.5)
			return -(float)Math.random() * scale;
		else 
			return (float)Math.random() * scale;
	}
	
	public static float getRandomPositionX() {
		if(Math.random() < 0.5)
			return -(float)Math.random() * Config.CANVAS_WIDTH/4;
		else 
			return (float)Math.random() * Config.CANVAS_WIDTH/4;
	}
	
	public static float getRandomPositionY() {
		if(Math.random() < 0.5)
			return -(float)Math.random() * Config.CANVAS_HEIGHT/4;
		else 
			return (float)Math.random() * Config.CANVAS_HEIGHT/4;
	}

	public static double calcVectorSize (Vector2f vector) {
		return Math.sqrt(Math.pow(vector.getX(),2)+Math.pow(vector.getY(),2));
	}

	public static double calcScalar (Vector2f a, Vector2f b) {
		return a.getX()*b.getX()+a.getY()*b.getY();
	}

	public static double remap (double value) {
		if (value >= 0 && value <= 180) {
			value /= 2;
			value /= 90;
			value *= 15;
		}
		return value;
	}

	public static Vector2f normVector (Vector2f vector) {
		double size = calcVectorSize(vector);
		return new Vector2f ((float)(vector.getX()/size), (float)(vector.getY()/size));
	}
}