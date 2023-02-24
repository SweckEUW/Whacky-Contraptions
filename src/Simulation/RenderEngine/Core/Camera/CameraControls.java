package Simulation.RenderEngine.Core.Camera;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.awt.GLJPanel;

import Simulation.RenderEngine.Core.Config;
/**
 * Java class for handling the keyboard and mouse interaction.
 * Intented to be used for an OpenGL scene renderer.
 * @author Karsten Lehn modified by Simon Weck
 * 
 */
public class CameraControls implements KeyListener, MouseMotionListener, MouseWheelListener, MouseListener{

    private Camera camera;//camera that can be controled by this class
			
    //increment of the specific coordinate
    private float CameraXInc = 0.5f;  
    private float CameraYInc= 0.5f;
    private float CameraZInc = 0.01f;
    
    //increment of the rotation
    private float CameraRotationXInc = 0.2f;
    private float CameraRotationYInc = 0.2f;
    
//    private boolean leftMouseButtonPressed = false;
    private boolean middleMouseButtonPressed = false;  
    private boolean rightMouseButtonPressed = false;
    
    private Point lastMouseLocation=new Point(0, 0);

	/**
	 * creates mouselisteners to the canvas that control the camera
	 * @param camer
	 * 			-camera that will be controlled 
	 * @param myCanvas
	 * 			-canvas to add mouselisteners
	 */
    public CameraControls(Camera camera,GLJPanel canvas) {
		this.camera=camera;
		canvas.addMouseWheelListener(this);
		canvas.addMouseListener(this);
		canvas.addKeyListener(this);
		canvas.addMouseMotionListener(this);
	}
    
    public CameraControls(Camera camera,GLWindow canvas) {
		this.camera=camera;
	}
    
    public void mousePressed(MouseEvent e) {
	       int pressedButton = e.getButton();
	       lastMouseLocation = e.getLocationOnScreen();
	        switch (pressedButton) {
	            case MouseEvent.BUTTON2:
	            	middleMouseButtonPressed = true;
	                break;
	            case MouseEvent.BUTTON3:
	                rightMouseButtonPressed = true;
	                break;
	        }
    }

    public void mouseReleased(MouseEvent e) {
    	int releasedButton = e.getButton();
    	switch (releasedButton) {
	    	case MouseEvent.BUTTON2:
	    		middleMouseButtonPressed = false;    		
	    		camera.setX(0);
	    		camera.setY(0);
	    		break;
	    	case MouseEvent.BUTTON3:
	    		rightMouseButtonPressed = false;
	    		camera.setRotateX(0);
	    		camera.setRotateY(0);
	    		break;
    	}
    }

    public void mouseDragged(MouseEvent e) {
    	if(!Config.stopCameraRotation) {
	    	Point currentMouseLocation = e.getLocationOnScreen();
		    float deltaX = currentMouseLocation.x - lastMouseLocation.x;
		    float deltaY = currentMouseLocation.y - lastMouseLocation.y;
		    lastMouseLocation = currentMouseLocation;
		        
		     // holding the left mouse button rotates the scene
		     if (rightMouseButtonPressed) {	
		        camera.increaseRotationX(CameraRotationXInc * -deltaY);
		        camera.increaseRotationY(CameraRotationYInc * -deltaX);
		     }
		        
		    // holding the right mouse button translates the scene
		    if (middleMouseButtonPressed) {
		        camera.increaseX(CameraXInc * -deltaX);
		        camera.increaseY(CameraYInc * +deltaY);
		    }
    	}
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
    	if(!Config.stopCameraZoom)
    		camera.increaseZ(CameraZInc * (float)e.getPreciseWheelRotation());
    }
  
	public void keyReleased(KeyEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	
}