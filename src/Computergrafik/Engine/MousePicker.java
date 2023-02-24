package Computergrafik.Engine;

import java.util.ArrayList;

import Computergrafik.Engine.Core.Config;
import Computergrafik.Engine.Core.Camera.Camera;
import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector2f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Math.Vector4f;
import Computergrafik.Engine.Planet.Core.Planet;
// import Computergrafik.UniverseSimulation.UniverseUserInterface;

/**
 * The MousePicker handles the calculation of a 3D ray based on the mouse coordinates. It also checks for intersections between every planet in the universe.
 * 
 * @author Simon Weck
 *
 */
public class MousePicker {

	private static Vector3f currentRay; //current 3D ray 
	
	//matrices needed to calculate the ray 
	private static Matrix4f projectionMatrix;
	private static Camera camera; 
	
	
	private static ArrayList<Planet> planets;
	private static boolean update = true;
	private static boolean foundPlanet;
	
	/**
	 * initializes the MousePicker by saving matrices to be accessed later on
	 */
	public static void initMousePicker(Camera camera1,Matrix4f projectionMatrix1,ArrayList<Planet> planets1) {
		camera=camera1;
		projectionMatrix=projectionMatrix1;
		planets=planets1;
		update = true;
		foundPlanet=false;
	}
	
	/**
	 * calculates a 3D ray based on the current mouse location and checks intersections for each planet in the universe.
	 * if the ray intersects with a planet the planet will change its color and the name of the planet will be displayed.
	 */
	public static void update() {
		if (update) {
			currentRay=calculateMouseRay();
			for (int i = 0; i < planets.size(); i++) {
				if (checkIntersection(planets.get(i))) {
					planets.get(i).setIsSelected(true);
					foundPlanet=true;
					// UniverseUserInterface.changePlanetName(planets.get(i).getName());
					i=planets.size();
				}else {
					planets.get(i).setIsSelected(false);	
					foundPlanet=false;
				}
			}
			//change current planet name to null
			if (!foundPlanet) {
				foundPlanet=false;
				// UniverseUserInterface.changePlanetName("");
			}
			
		}
		
	}
	
	/**
	 * checks intersection between a line and a circle. The planet represents a circle.
	 * The method returns true if the current ray and the planet intersects.
	 * @param planet
	 * @return
	 */
	private static boolean checkIntersection(Planet planet) {
		
		Matrix4f viewMatrix = new Matrix4f(camera.getViewMatrix());
		viewMatrix.invert();
		
		Vector3f rayOirigin = new Vector3f(viewMatrix.m03,viewMatrix.m13,viewMatrix.m23);
		
		Matrix4f modelMatrix = new Matrix4f(planet.getModel().getModelMatrix());
		rayOirigin = Vector3f.subtract(rayOirigin, new Vector3f(modelMatrix.m03, modelMatrix.m13, modelMatrix.m23));
		float b= Vector3f.multiply(rayOirigin, currentRay);
			
		rayOirigin = new Vector3f(viewMatrix.m03,viewMatrix.m13,viewMatrix.m23);
		rayOirigin = Vector3f.subtract(rayOirigin, new Vector3f(modelMatrix.m03, modelMatrix.m13, modelMatrix.m23));
		float c = Vector3f.multiply(rayOirigin, rayOirigin);
		c-=planet.getRadius()*planet.getRadius();
		
		if (b*b-c>=0) 
			return true;
		
		return false;
	}

	/**
	 * calculates based on the mouse position a ray that gets shot throguh the scene.
	 * The Method walks backwards trough the viewing pipeline.
	 */
	private static Vector3f calculateMouseRay() {
		float mouseX = camera.getCameraControls().getMouseX(); 
		float mouseY = camera.getCameraControls().getMouseY();
		Vector2f normalizedCoordinates = convertToNormalizedDeviceCoordinates(mouseX, mouseY);
		Vector4f clipCoordinates = new Vector4f(normalizedCoordinates.x, normalizedCoordinates.y, -1, 1);
		Vector4f viewSpaceCoordinates = convertToViewSpace(clipCoordinates);
		Vector3f wordlSpaceCoordinates = convertToWorldSpace(viewSpaceCoordinates);
		return wordlSpaceCoordinates;
	}
	
	/**
	 * calculates based on the mouse coordinates the normalized device coordinates where the top left corner = (0,0).
	 * the normalized device coordinates create a coordinates system from -1 to 1 in x and y direction
	 */
	private static Vector2f convertToNormalizedDeviceCoordinates(float x,float y) {
		float newX= (2f*x)/Config.CANVAS_WIDTH-1;
		float newY= (2f*y)/Config.CANVAS_HEIGHT-1;
		return new Vector2f(newX, -newY);
	}
	
	/**
	 * converts the clipcoordinates to view  space coordinates by multiplying them with the inverse projection matrix
	 * @param clipCoordinates
	 * @return
	 */
	private static Vector4f convertToViewSpace(Vector4f clipCoordinates) {
		Matrix4f tmp = new Matrix4f(projectionMatrix);
		tmp.invert();
		Vector4f viewSpaceCoordinates = tmp.multiply(clipCoordinates);
		return new Vector4f(viewSpaceCoordinates.x, viewSpaceCoordinates.y, -1, 0); //z=-1
	}
	
	/**
	 * converts view space coordinates into model space coordinates by mutiplying them with the inverse view matrix
	 * @param viewSpaceCoordinates
	 * @return
	 */
	private static Vector3f convertToWorldSpace(Vector4f viewSpaceCoordinates) {
		Matrix4f viewMatrix = new Matrix4f(camera.getViewMatrix());
		viewMatrix.invert();
		Vector4f worldSpaceCoordinates = viewMatrix.multiply(viewSpaceCoordinates);
		return new Vector3f(worldSpaceCoordinates.x, worldSpaceCoordinates.y, worldSpaceCoordinates.z).normalize(); //normalize for later use
	}
	
	public static void setProjectionMatrix(Matrix4f projectionMatrix1) {
		projectionMatrix=projectionMatrix1;
	}
	
	public static void setUpdate(boolean update1) {
		update=update1;
	}
	
	public static Vector3f getCurrentRay() {
		return currentRay;
	}
	
}

