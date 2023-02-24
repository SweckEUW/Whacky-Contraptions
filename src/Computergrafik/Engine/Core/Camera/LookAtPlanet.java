package Computergrafik.Engine.Core.Camera;

import Computergrafik.Engine.MousePicker;
import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Planet.Core.Planet;

/**
 * LookAtPlanet is a mode where the user follows a planet and can rotate around the Planet to get a better view.
 * If the user clicks a planet lookAtPlanetMode will be activated and the camera will translate to the clicked planet. 
 * After that the Camera follows the planet and the user input will control the rotation around the clicked planet.
 * If desired the user can leave the LookAtPlanetMode to get back to the location where he clicked the planet.
 * 
 * @author Simon Weck
 *
 */
public class LookAtPlanet {
	
	private static float distanceToPlanet;
	
	private static boolean zoomOnPlanet;	//true if the user clicked a planet so the camera will smoothly tranlate to the position of the planet
	private static float t;					//blendfactor
	private static float zoomSpeed=0.04f;	//blend increment
	private static Matrix4f viewMatrix;
	private static Planet planetToZoomOn;
	
	private static CameraControls cameraControls;
	private static Camera camera;
	
	private static boolean leavePlanet;		//true if the user wants to leave LookAtPlanetMode
	
	//old camera settings. Needed to get back to the old camera position if the user leaves the LookAtPlanetMode
	private static Matrix4f oldViewMatrix;
	private static float oldRotationX;
	private static float oldRotationY;
	private static float oldRotationZ;
	private static float oldX;
	private static float oldY;
	private static float oldZ;
	
	/**
	 * updates the camera position in the current state.
	 * -ZoomOnPlanet state: 
	 * The user clicked a planet and the camera will smoothly translate to the position of the planet
	 * -LeavePlanet state:
	 * The user leaves LookAtPlanet mode and the camera will smoothly translate to the old position where it was before it got translated to the planet 
	 * -default state:
	 * The camera follows the planet that got clicked
	 */
	public static void update() {
		if (zoomOnPlanet) 
			zoomOnPlanet();
		else if (leavePlanet) {
			leavePlanet();
		}else {
			camera.setX(planetToZoomOn.getModel().getModelMatrix().m03);
			camera.setY(planetToZoomOn.getModel().getModelMatrix().m13);
			camera.setZ(planetToZoomOn.getModel().getModelMatrix().m23+distanceToPlanet);
		}		
	}
	
	/**
	 * Leaves LookAtPlanetMode
	 * initializes leavingPlanet state.
	 * -The user leaves LookAtPlanet mode and the camera will smoothly translate to the old position where it was before it got translated to the planet 
	 */
	public static void initLeavePlanet() {
		t=0;
		leavePlanet=true;
		cameraControls.stopCameraControls(true);
		viewMatrix=new Matrix4f(camera.getViewMatrix());
		camera.getCameraControls().setZinc(0.2f); //increase scroll speed
	}
	
	/**
	 * smoothly blends 2 matrices.
	 * the current view matrix and the view matrix of the camera before the user clicked a planet.
	 */
	private static void leavePlanet() {
		if (t<=1) {		
			Matrix4f newViewMatrix = new Matrix4f();
			
			Matrix4f currentViewMatrix=new Matrix4f(viewMatrix);
			
			Matrix4f goToMatrix = new Matrix4f(oldViewMatrix);
					
			currentViewMatrix.multiply(1-t);
			goToMatrix.multiply(t);
			newViewMatrix = goToMatrix.add(currentViewMatrix);
			camera.setViewMatrix(newViewMatrix);
			
			t+=zoomSpeed; //zoomSpeed
		}else {		
			//player left LookAtPlanetMode
			leavePlanet=false;
			camera.setX(oldX);
			camera.setY(oldY);
			camera.setZ(oldZ);
			
			camera.setRotateX(oldRotationX);
			camera.setRotateY(oldRotationY);
			camera.setRotateZ(oldRotationZ);
			
			cameraControls.stopCameraControls(false);
			camera.setLookAtPlanetMode(false);
			
			MousePicker.setUpdate(true); //enable Mouse picking again
		}	
	}
	
	/**
	 * Enters LookAtPlanetMode
	 * initializes ZoomOnPlanet state.
	 * -The user clicked a planet and the camera will smoothly translate from the current position to the position of the planet
	 * 
	 * @param planet
	 * 		-planet to zoom on
	 * @param camera1
	 * 		-camera to control
	 */
	public static void initZoomOnPlanet(Planet planet,Camera camera1) {
		MousePicker.setUpdate(false); //disable Mouse picking
		planetToZoomOn=planet;
		camera=camera1;
		cameraControls=camera.getCameraControls();
		
		distanceToPlanet=2.9f*planetToZoomOn.getAverageHeight()*planetToZoomOn.getModel().getScaleX(); //set distance to planet depending on planet scale 
		
		t=0;
		zoomOnPlanet=true;
		cameraControls.stopCameraControls(true);
		camera.setLookAtPlanetMode(true);
		viewMatrix=new Matrix4f(camera.getViewMatrix());
		
		//set old camera position
		oldViewMatrix = new Matrix4f(camera.getViewMatrix());
		oldRotationX = camera.getRotateX();
		oldRotationY = camera.getRotateY();
		oldRotationZ = camera.getRotateZ();
		oldX = camera.getX();
		oldY = camera.getY();
		oldZ = camera.getZ();		
		
		camera1.getCameraControls().setZinc(0.05f);
	}
	
	/**
	 * smoothly blends 2 matrices.
	 * the current view matrix and the matrix containing the translation of the  clicked planet.
	 */
	private static void zoomOnPlanet() {
		if (t<=1) {		
			Matrix4f newViewMatrix = new Matrix4f();
			
			Matrix4f planetLookAtMatrix=new Matrix4f();
			planetLookAtMatrix.m03 = planetToZoomOn.getModel().getModelMatrix().m03*-1;
			planetLookAtMatrix.m13 = planetToZoomOn.getModel().getModelMatrix().m13*-1;
			planetLookAtMatrix.m23 = planetToZoomOn.getModel().getModelMatrix().m23*-1 -distanceToPlanet;

			Matrix4f tmpViewMatrix = new Matrix4f(viewMatrix);
					
			tmpViewMatrix.multiply(1-t);
			planetLookAtMatrix.multiply(t);
			newViewMatrix = tmpViewMatrix.add(planetLookAtMatrix);
			camera.setViewMatrix(newViewMatrix);
			
			t+=zoomSpeed; //zoomSpeed
		}else {
			//user enters LookAtPlanetMode
			zoomOnPlanet=false;
			camera.setX(planetToZoomOn.getModel().getModelMatrix().m03);
			camera.setY(planetToZoomOn.getModel().getModelMatrix().m13);
			camera.setZ(planetToZoomOn.getModel().getModelMatrix().m23+distanceToPlanet);
			
			camera.setRotateX(0);
			camera.setRotateY(0);
			camera.setRotateZ(0);
			
			//enable only rotation and distance control
			cameraControls.setStopRotationControl(false);
			cameraControls.setStopZControl(false);
		}
	}
	
	public static Vector3f getCenter() {
		return new Vector3f(planetToZoomOn.getModel().getModelMatrix().m03, planetToZoomOn.getModel().getModelMatrix().m13, planetToZoomOn.getModel().getModelMatrix().m23);
	}

	public static Matrix4f getPlanetMatrix() {
		return planetToZoomOn.getModel().getModelMatrix();
	}
	
	public static float getDistanceToPlanet() {
		return distanceToPlanet;
	}

	public static void setDistanceToPlanet(float distanceToPlanet1) {
		distanceToPlanet=distanceToPlanet1;
	}
	
}
