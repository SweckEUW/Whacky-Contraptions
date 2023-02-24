package Computergrafik.Engine.Core;

import Computergrafik.Engine.Core.Math.Vector3f;

/**
 * Configuration class. This class configurates render settings but also canvas width height and framerate
 * @author Simon Weck
 *
 */
public class Config {
	
	//render settings
	public static boolean BACK_FACE_CULLING = true;
	public static boolean WIREFRAME_MODE = false;
	public static Vector3f BACKGROUND_COLOR = new Vector3f(1, 1, 1);
	
	public static final float FIELD_OF_VIEW=70;
	public static final float NEAR_PLANE=0.1f;
	public static final float FAR_PLANE=1000;
	
	public static int CANVAS_WIDTH = 1920;
	public static int CANVAS_HEIGHT = 1080;
	public static int FRAME_RATE = 60;
}
