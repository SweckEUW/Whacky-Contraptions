package Simulation.RenderEngine.Core.Camera;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.awt.GLJPanel;

import Simulation.RenderEngine.Core.Math.Matrix4f;


/**
 * 
 * represents a virtual camera that can be moved in the scene.
 * @author Simon Weck
 */
public class Camera {

	private float x,y,z;								//position of camera
	private float rotateX,rotateY,rotateZ; 				//rotation of camera 
	
	private Matrix4f viewMatrix=new Matrix4f(); 		//view Matrix describing the camera transformation
	
	private boolean updateMatrix; 						//true if position or rotation got changed, if true the matrix will get calculate in the next draw call (better performance)
	 
	private CameraControls cameraControls;				//controling the camera

	
	/**
	 * Creates a camera in (0,0,0) with 0 rotation looking down the negative z axis
	 * @param canvas
	 * 			-needed to add mouselisteners to control the camera
	 */
	public Camera(GLJPanel canvas) {
		cameraControls=new CameraControls(this,canvas);
	}
	
	public Camera(GLWindow canvas) {
		cameraControls=new CameraControls(this,canvas);
	}
	

	/**
	 * Creates a camera in (x,y,z) with (rotateX,rotateY,rotateZ) rotation
	 * @param canvas
	 * 			-needed to add mouselisteners to control the camera
	 *  @param x
	 *  		-x coordinate of the camera
	 *  @param y
	 *  		-y coordinate of the camera
	 *  @param z
	 *  		-z coordinate of the camera
	 *  @param rotateX
	 *  		-rotation (in degrees) along the x axis
	 *  @param rotateY
	 *  		-rotation (in degrees) along the y axis
	 *  @param rotateZ
	 *  		-rotation (in degrees) along the z axis
	 */
	public Camera(GLJPanel canvas,float x,float y,float z,float rotateX,float rotateY, float rotateZ) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.rotateX=rotateX;
		this.rotateY=rotateY;
		this.rotateZ=rotateZ;
		cameraControls=new CameraControls(this,canvas);
		viewMatrix.changeToViewMatrix(this);			
	}
	
	public Camera(GLWindow canvas,float x,float y,float z,float rotateX,float rotateY, float rotateZ) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.rotateX=rotateX;
		this.rotateY=rotateY;
		this.rotateZ=rotateZ;
		cameraControls=new CameraControls(this,canvas);
		viewMatrix.changeToViewMatrix(this);			
	}
	
	public void updateMatrix() {
		if(updateMatrix) {
			viewMatrix.changeToViewMatrix(this);
			updateMatrix=false;
		}
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
		updateMatrix=true;		
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
		updateMatrix=true;
	}
	
	public float getZ() {
		return z;
	}
	
	public void setZ(float z) {
		this.z = z;
		updateMatrix=true;
	}
	
	public float getRotateX() {
		return rotateX;
	}
	
	public void setRotateX(float rotateX) {
		this.rotateX = rotateX;
		updateMatrix=true;
	}
	
	public float getRotateY() {
		return rotateY;
	}
	
	public void setRotateY(float rotateY) {
		this.rotateY = rotateY;
		updateMatrix=true;
	}
	
	public float getRotateZ() {
		return rotateZ;
	}
	
	public void setRotateZ(float rotateZ) {
		this.rotateZ = rotateZ;
		updateMatrix=true;
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public void setMatrixUpdate(boolean updateMatrix) {
		this.updateMatrix=updateMatrix;
	}
	
	public boolean getMatrixUpdate() {
		return updateMatrix;
	}
	
	public CameraControls getCameraControls() {
		return cameraControls;
	}

	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix=viewMatrix;	
	}


	public void increaseZ(float dz) {
		this.z+=dz;
		updateMatrix=true;	
	}
	
	public void increaseX(float dx) {
		this.x+=dx;
		updateMatrix=true;	
	}
	
	public void increaseY(float dy) {
		this.y+=dy;
		updateMatrix=true;	
	}
	
	public void increaseRotationX(float dx) {
		this.rotateX+=dx;
		updateMatrix=true;	
	}
	
	public void increaseRotationY(float dy) {
		this.rotateY+=dy;
		updateMatrix=true;	
	}
	
	public void increaseRotationZ(float dz) {
		this.rotateZ+=dz;
		updateMatrix=true;	
	}
	
}
