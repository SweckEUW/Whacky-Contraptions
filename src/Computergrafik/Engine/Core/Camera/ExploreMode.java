package Computergrafik.Engine.Core.Camera;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Planet.Core.Planet;

/**
 * !! under construction not in the final project !!
 * 
 * The user can walk on a planet using the W A S D keys and can freely move the camera
 * The user will correctly collide with the terrain 
 * @author Simon Weck
 *
 */
public class ExploreMode {

	private Planet planet;
	private Camera camera;
	private int[] planetIndices;
	private float[] planetVertices;
	private float movementSpeedX=1f;
	private float movementSpeedY=1f;
	private float curentPositionX;
	private float curentPositionY;
	
	public ExploreMode(Planet planet,Camera camera) {
		this.planet=planet;
		this.camera=camera;
		planetVertices=planet.getPlanetMesh().getMesh().getVertices();
		planetIndices=planet.getPlanetMesh().getMesh().getIndices();
		Vector3f newPosition= findRandomPointOnPlanet();
		camera.setX(newPosition.x*1.05f);
		camera.setY(newPosition.y*1.05f);
		camera.setZ(newPosition.z*1.05f);	
	}
	
	public Vector3f findClosestPoint(float x,float y,float z) {
		Vector3f closestPoint = new Vector3f();
		float closestLength = 100;
		for (int i = 0; i < planetIndices.length; i+=3) {
			Vector3f newPoint = new Vector3f(planetVertices[planetIndices[i]]-x,planetVertices[planetIndices[i+1]]-y,planetVertices[planetIndices[i+2]]-z);
			float newPointLength= newPoint.length();
			if (newPointLength<closestLength) {
				closestPoint = new Vector3f(planetVertices[planetIndices[i]],planetVertices[planetIndices[i+1]],planetVertices[planetIndices[i+2]]);
				closestLength=newPointLength;
			}
		}
		return closestPoint;
	}
	
	public Vector3f findRandomPointOnPlanet() {		
		//get random triangle
		int randomIndex=(int)(Math.random()*planetIndices.length);

		Vector3f vertex1;
		Vector3f vertex2;
		Vector3f vertex3;
		 
		if (randomIndex%3==0) {
			vertex1=new Vector3f(planetVertices[planetIndices[randomIndex]*3],planetVertices[planetIndices[randomIndex]*3+1],planetVertices[planetIndices[randomIndex]*3+2]);
			vertex2=new Vector3f(planetVertices[planetIndices[randomIndex+1]*3],planetVertices[planetIndices[randomIndex+1]*3+1],planetVertices[planetIndices[randomIndex+1]*3+2]);
			vertex3=new Vector3f(planetVertices[planetIndices[randomIndex+2]*3],planetVertices[planetIndices[randomIndex+2]*3+1],planetVertices[planetIndices[randomIndex+2]*3+2]);
		}else if (randomIndex%3==1) {
			vertex1=new Vector3f(planetVertices[planetIndices[randomIndex-1]*3],planetVertices[planetIndices[randomIndex-1]*3+1],planetVertices[planetIndices[randomIndex-1]*3+2]);
			vertex2=new Vector3f(planetVertices[planetIndices[randomIndex]*3],planetVertices[planetIndices[randomIndex]*3+1],planetVertices[planetIndices[randomIndex]*3+2]);
			vertex3=new Vector3f(planetVertices[planetIndices[randomIndex+1]*3],planetVertices[planetIndices[randomIndex+1]*3+1],planetVertices[planetIndices[randomIndex+1]*3+2]);
		}else {
			vertex1=new Vector3f(planetVertices[planetIndices[randomIndex-2]*3],planetVertices[planetIndices[randomIndex-2]*3+1],planetVertices[planetIndices[randomIndex-2]*3+2]);
			vertex2=new Vector3f(planetVertices[planetIndices[randomIndex-1]*3],planetVertices[planetIndices[randomIndex-1]*3+1],planetVertices[planetIndices[randomIndex-1]*3+2]);
			vertex3=new Vector3f(planetVertices[planetIndices[randomIndex]*3],planetVertices[planetIndices[randomIndex]*3+1],planetVertices[planetIndices[randomIndex]*3+2]);	
		}
					
		//random point on triangle
		float random1=(float)Math.random()*99;
		float random2=(float)Math.random()*99;
		float random3=(float)Math.random()*99;
		
		float randomSum=random1+random2+random3;
		random1/=randomSum;
		random2/=randomSum;
		random3/=randomSum;
		
		vertex1=Vector3f.multiply(vertex1, random1);
		vertex2=Vector3f.multiply(vertex2, random2);
		vertex3=Vector3f.multiply(vertex3, random3);
		
		Vector3f randomPointOnSurface = Vector3f.add(vertex1, vertex2, vertex3);
		return randomPointOnSurface;
	}
	
	public Vector3f getPlanetCenter() {
		return planet.getCenter();
	}

	public float getMovementSpeedX() {
		return movementSpeedX;
	}

	public float getMovementSpeedY() {
		return movementSpeedY;
	}

	public float getCurentPositionX() {
		return curentPositionX;
	}

	public float getCurentPositionY() {
		return curentPositionY;
	}

	public void setCurentPositionX(float curentPositionX) {
		this.curentPositionX = curentPositionX;
		camera.setMatrixUpdate(true);
		
//		Vector3f newPoint=camera.getExploreMode().findClosestPoint(camera.getViewMatrix().m03,camera.getViewMatrix().m13,camera.getViewMatrix().m23);
//		camera.setX(newPoint.x*1.05f);
//		camera.setY(newPoint.y*1.05f);
//		camera.setZ(newPoint.z*1.05f);
	}

	public void setCurentPositionY(float curentPositionY) {
		this.curentPositionY = curentPositionY;
		camera.setMatrixUpdate(true);
		
//		Vector3f newPoint=camera.getExploreMode().findClosestPoint(camera.getViewMatrix().m03,camera.getViewMatrix().m13,camera.getViewMatrix().m23);
//		camera.setX(newPoint.x*1.05f);
//		camera.setY(newPoint.y*1.05f);
//		camera.setZ(newPoint.z*1.05f);
	}
		
}
