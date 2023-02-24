package Simulation.RenderEngine.Core;

import Simulation.RenderEngine.Core.Math.Vector4f;

/**
 * Configuration class. This class configurates render settings but also canvas width height and framerate
 * @author Simon Weck
 *
 */
public class Config {
	
	//render settings
	public static boolean BACK_FACE_CULLING = true;
	public static boolean WIREFRAME_MODE = false;
	public static Vector4f BACKGROUND_COLOR = new Vector4f(1, 1, 1,1);
	
	public static final float FIELD_OF_VIEW= 40 * (float)Math.PI/180;
	public static final float NEAR_PLANE=0.1f;
	public static final float FAR_PLANE=10;
	
	//Canvas Settings
	public static int CANVAS_WIDTH = 1920;
	public static int CANVAS_HEIGHT = 1080;
	public static int FRAME_RATE = 60;
	
	
	public static float BOUNDING_DISTANCE = 0.1f;
	
	public static float LINE_WIDTH = 8f;
	public static float POINT_SIZE = 6f;
	
	
	//Camera Settings
	public static boolean stopCameraRotation;
	public static boolean stopCameraZoom;
}
