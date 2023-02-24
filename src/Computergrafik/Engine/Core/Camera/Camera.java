package Computergrafik.Engine.Core.Camera;

import com.jogamp.opengl.awt.GLCanvas;

import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Planet.Core.Planet;
// import Computergrafik.UniverseSimulation.UniverseUserInterface;

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
	
	private boolean mouseGotClicked; 					//true if mouse got clicked 
	
	private boolean exploreMode;						//true if exlorerMode is enabled 
	
	private CameraControls cameraControls;				//controling the camera

	private boolean lookAtPlanetMode; 					//true if lookAtPlanetMode is enabled
	private boolean stopMousePicking; 					//stops the mouse picker and the highlighting of the planets 
	
	private boolean centerCamera; 						//true if the user clicks the center camera button
	
	//under construction
	private ExploreMode mode;
	
	/**
	 * Creates a camera in (0,0,0) with 0 rotation looking down the negative z axis
	 * @param canvas
	 * 			-needed to add mouselisteners to control the camera
	 */
	public Camera(GLCanvas canvas) {
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
	public Camera(GLCanvas canvas,float x,float y,float z,float rotateX,float rotateY, float rotateZ) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.rotateX=rotateX;
		this.rotateY=rotateY;
		this.rotateZ=rotateZ;
		cameraControls=new CameraControls(this,canvas);
		viewMatrix.changeToViewMatrix(this);			
	}
	
	/**
	 * updates the camera.
	 * Zooms on a planet when a player clicks a planet
	 * Centers the camera when a player clicks the center camera button.
	 * 
	 * @param userInterface
	 * 			-userInterface to add or remove elements when a player clicks on a planet
	 */
	// public void update(UniverseUserInterface userInterface) {
	public void update() {
		//if mouse got clicked check if the player hovers over a planet
		if (mouseGotClicked) {
			mouseGotClicked=false;
			checkIfPlanetGotClicked();	
		}
		
		//update look at planet mode if enabled
		if (lookAtPlanetMode) 
			LookAtPlanet.update();	
		
		//center camera if enabled
		if (centerCamera) 
			CenterCamera.update();		
	}
	
	/**
	 * Gets called when the mouse got clicked. 
	 * if the player hovers over a planet and clicks it:
	 * -activation of look at planet mode
	 * -disable mouse picker
	 * -adding planet statistics to the user interface 
	 * 
	 * @param userInterface
	 * 		-userInterface to add or remove elements when a player clicks on a planet
	 */
	// private void checkIfPlanetGotClicked(UniverseUserInterface userInterface) {
	private void checkIfPlanetGotClicked() {
		for (int i = 0; i < Planet.getAllPlanets().size(); i++) 
			if (Planet.getAllPlanets().get(i).isSelected()) {
				LookAtPlanet.initZoomOnPlanet( Planet.getAllPlanets().get(i), this);		
				// userInterface.addPlanetUI( Planet.getAllPlanets().get(i));	
				for (Planet planet :  Planet.getAllPlanets()) {
					planet.useNormalColor();
					for (int k = 0; k < planet.getPlanetParts().size(); k++) 
						planet.getPlanetParts().get(k).useNormalColor();
					for (int m = 0; m < planet.getPlanetEntities().size(); m++) 
						planet.getPlanetEntities().get(m).useNormalColor();
				}
				stopMousePicking=true;
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
	
	public ExploreMode getExploreMode() {
		return mode;
	}
	
	public boolean isExploreModeEnabled() {
		return exploreMode;
	}
	
	public CameraControls getCameraControls() {
		return cameraControls;
	}

	public boolean isMouseGotClicked() {
		return mouseGotClicked;
	}

	public void setMouseGotClicked(boolean mouseGotClicked) {
		this.mouseGotClicked = mouseGotClicked;
	}

	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix=viewMatrix;	
	}

	public void setLookAtPlanetMode(boolean lookAtPlanetMode) {
		this.lookAtPlanetMode = lookAtPlanetMode;
	}

	public boolean getLookAtPlanetMode() {
		return lookAtPlanetMode;
	}

	public boolean stopMousePicking() {
		return stopMousePicking;
	}

	public boolean isCenterCamera() {
		return centerCamera;
	}

	public void setCenterCamera(boolean centerCamera) {
		this.centerCamera = centerCamera;
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
