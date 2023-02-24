package Computergrafik.Engine.Core.Camera;

import Computergrafik.Engine.Core.Math.Matrix4f;

/**
 * Class that takes control over the camera and sets it at the starting position
 * @author Simon Weck
 */
public class CenterCamera {

	private static Camera camera;
	private static float t; 				//blendfactor
	private static float tspeed=0.04f;		//blend increment
	private static Matrix4f oldViewMatrix;	//old view Matrix (current view matrix)
	private static Matrix4f toGoMatrix;		//matrix containing the original position of the camera
	
	/**
	 * initialize to set the camera at the starting position
	 * @param camera1
	 */
	public static void initCenterCamera(Camera camera1) {		
		camera = camera1;
		camera.setCenterCamera(true);
		camera.getCameraControls().stopCameraControls(true);
		t=0;
		oldViewMatrix = new Matrix4f(camera.getViewMatrix());
		//init goToMatrix
		toGoMatrix = new Matrix4f();
		toGoMatrix.m23=-80;
	}	
	
	public static void update() {
		centerCamera();
	}
	
	/**
	 * blends between the current view matrix and the goToMatrix to make the transition smoother
	 */
	private static void centerCamera() {
		//blends between the 2 matrices until t is >1
		if (t<=1) {		
			Matrix4f newViewMatrix = new Matrix4f();
			
			Matrix4f tmpToGoMatrix=new Matrix4f(toGoMatrix);

			Matrix4f tmpViewMatrix = new Matrix4f(oldViewMatrix);
					
			tmpViewMatrix.multiply(1-t);
			tmpToGoMatrix.multiply(t);
			newViewMatrix = tmpViewMatrix.add(tmpToGoMatrix);
			camera.setViewMatrix(newViewMatrix);
			
			t+=tspeed; //zoomSpeed
		}else {
			//camera is now at the starting position (0,0,80) looking down the negative z axis
			camera.setX(0);
			camera.setY(0);
			camera.setZ(80);
			
			camera.setRotateX(0);
			camera.setRotateY(0);
			camera.setRotateZ(0);
			
			camera.getCameraControls().stopCameraControls(false);
			camera.setCenterCamera(false);
		}
	}
	
}
