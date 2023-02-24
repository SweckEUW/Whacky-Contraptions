package Computergrafik.Engine.Core.Camera;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.jogamp.opengl.awt.GLCanvas;

import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
/**
 * Java class for handling the keyboard and mouse interaction.
 * Intented to be used for an OpenGL scene renderer.
 * @author Karsten Lehn modified by Simon Weck
 * 
 */
public class CameraControls implements KeyListener, MouseMotionListener, MouseWheelListener, MouseListener{

    private Camera camera; 				//camera that can be controled by this class

    //X,Y mouse position on the screen
    private float mouseX; 				
    private float mouseY; 				
      
    //increment of the specific coordinate
    private float CameraXInc = 1f;  
    private float CameraYInc= 1f;
    private float CameraZInc = 0.2f;
    
    //increment of the rotation
    private float CameraRotationXInc = 1f;
    private float CameraRotationYInc = 1f;
    
    private boolean leftMouseButtonPressed = false;
    private boolean rightMouseButtonPressed = false;
    
    private Point lastMouseLocation=new Point(0, 0);
    
    private final float mouseRotationFactor = 0.2f;
    private final float mouseTranslationFactor = 0.1f;
    private final float mouseWheelScrollFactor = 10f;

    //blocks the user from input
	private boolean stopCameraControls;
	private boolean stopRotationControl;
	private boolean stopTranslationControl;
	private boolean stopZControl;
	
	//true = translation,roation (Camera rotation arround(0,0)) false = rotation,translation (camera rotates free in space)
	private boolean cameraModeOrigin=true;

	/**
	 * creates mouselisteners to the canvas that control the camera
	 * @param camer
	 * 			-camera that will be controlled 
	 * @param myCanvas
	 * 			-canvas to add mouselisteners
	 */
    public CameraControls(Camera camera,GLCanvas myCanvas) {
		this.camera=camera;
		myCanvas.addMouseWheelListener(this);
		myCanvas.addMouseListener(this);
		myCanvas.addKeyListener(this);
		myCanvas.addMouseMotionListener(this);
	}
    
    @Override
    /**
     * sets gotMouseclicked to true if the mouse got clicked
     * 
     */
    public void mouseClicked(MouseEvent e) {
    	if (!stopCameraControls) 
    		camera.setMouseGotClicked(true);	
    }

    @Override
    /**
     * checks which mousebutton got pressed
     *
     */
    public void mousePressed(MouseEvent e) {
	       int pressedButton = e.getButton();
	       lastMouseLocation = e.getLocationOnScreen();
	        switch (pressedButton) {
	            case MouseEvent.BUTTON1:
	                leftMouseButtonPressed = true;
	                break;
	            case MouseEvent.BUTTON3:
	                rightMouseButtonPressed = true;
	                break;
	        }
    }

    @Override
    /**
     * checks if mousebutton got released
     * 
     */
    public void mouseReleased(MouseEvent e) {
	        int releasedButton = e.getButton();
	        switch (releasedButton) {
	            case MouseEvent.BUTTON1:
	                leftMouseButtonPressed = false;
	                break;
	            case MouseEvent.BUTTON3:
	                rightMouseButtonPressed = false;
	                break;
	        }
    }

  
    /**
     * increases camera rotation or position when if a mousebutton got clicked and mouse got dragged
     * 
     */
    @Override
    public void mouseDragged(MouseEvent e) {
	        Point currentMouseLocation = e.getLocationOnScreen();
	        float deltaX = currentMouseLocation.x - lastMouseLocation.x;
	        float deltaY = currentMouseLocation.y - lastMouseLocation.y;
	        lastMouseLocation = currentMouseLocation;
	        // holding the left mouse button rotates the scene
	        if (leftMouseButtonPressed && !stopRotationControl) {	
	        	camera.increaseRotationX(CameraRotationXInc * mouseRotationFactor * -deltaY);
	        	camera.increaseRotationY(CameraRotationYInc * mouseRotationFactor * -deltaX);
	        }
	        // holding the right mouse button translates the scene
	        if (rightMouseButtonPressed && !stopTranslationControl) {
	        	camera.increaseX(CameraXInc * mouseTranslationFactor * -deltaX);
	        	camera.increaseY(CameraYInc * mouseTranslationFactor * +deltaY);
	        }
    }

    /**
     * saves the current mouse position on the screen into variables 
     * (needed for mousepicking)
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    	mouseX = e.getPoint().x;
    	mouseY = e.getPoint().y;
    }

    /**
     * lookAtPlanetMode:
     * 		-increases distance to the planet that is looked at when mouse wheel is moved
     * 
     * Camera not in Origin mode (camera is not rotating around (0,0):
     * 		-Camera position increases in the direction which is looked at when mouse wheel is moved
     * 
     * CameraInOriginMode (default) increasing when mouse wheel is moved
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    	if (!stopZControl) { //no movement if mouseControl is stopped
    		
    		if (camera.getLookAtPlanetMode()) {		//LookAtPlanetMode
    			LookAtPlanet.setDistanceToPlanet(LookAtPlanet.getDistanceToPlanet()+CameraZInc * mouseWheelScrollFactor * (float)e.getPreciseWheelRotation());	
    			
			}else if (!cameraModeOrigin) {			 //Camera not in Origin mode
				Vector3f viewVector = new Vector3f(camera.getViewMatrix().m20, camera.getViewMatrix().m21, camera.getViewMatrix().m22);
				
				Vector3f cameraTranslation = new Vector3f(camera.getX(),camera.getY(),camera.getZ());
						
				viewVector = Vector3f.multiply(viewVector, CameraZInc * (float)mouseWheelScrollFactor * (float)e.getPreciseWheelRotation());
				cameraTranslation = Vector3f.add(cameraTranslation, viewVector);
			
				camera.setX(cameraTranslation.x);
				camera.setY(cameraTranslation.y);
				camera.setZ(cameraTranslation.z);
				
			}else {									//CameraInOriginMode (default)
				camera.increaseZ(CameraZInc * mouseWheelScrollFactor * (float)e.getPreciseWheelRotation());
			}	 
    		
    	}
    }

    /**
     * Sets the camera mode to look at the origin (true) or to move freely (false)
     * 
     * @param cameraModeOrigin
     */
    public void setCameraModeOrigin(boolean cameraModeOrigin) {
		this.cameraModeOrigin = cameraModeOrigin;
		if (cameraModeOrigin) {
			CenterCamera.initCenterCamera(camera); //resets camera to look properly at 0,0
		}else {
			Matrix4f inverse = new Matrix4f(camera.getViewMatrix());
			inverse.invert();
			
			camera.setX(inverse.m03);
			camera.setY(inverse.m13);
			camera.setZ(inverse.m23);
		}
	}
    
    /**
     * stops the user from controlling the camera 
     * 
     * @param stopCameraControls
     * 			-true = stops control 
     * 			-false = user can control the camera
     */
    public void stopCameraControls(boolean stopCameraControls) {
		this.stopCameraControls=stopCameraControls;
		stopRotationControl=stopCameraControls;
		stopTranslationControl=stopCameraControls;
		stopZControl=stopCameraControls;
	}
    
    

	public void keyReleased(KeyEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	
	public float getMouseX() {
		return mouseX;
	}

	public float getMouseY() {
		return mouseY;
	}

	public boolean getStopRotationControl() {
		return stopRotationControl;
	}

	public void setStopRotationControl(boolean stopRotationControl) {
		this.stopRotationControl = stopRotationControl;
	}

	public boolean isStopTranslationControl() {
		return stopTranslationControl;
	}

	public void setStopTranslationControl(boolean stopTranslationControl) {
		this.stopTranslationControl = stopTranslationControl;
	}

	public boolean isStopZControl() {
		return stopZControl;
	}

	public void setStopZControl(boolean stopZControl) {
		this.stopZControl = stopZControl;
	}

	public boolean isCameraModeOrigin() {
		return cameraModeOrigin;
	}

	public void setZinc(float zinc) {
		this.CameraZInc = zinc;
	}
		
}