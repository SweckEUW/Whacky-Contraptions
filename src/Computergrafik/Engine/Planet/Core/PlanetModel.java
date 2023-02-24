package Computergrafik.Engine.Planet.Core;

import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Shaders.Core.Material;

/**
 * Model of a planet holding the mesh of a planet and the translation information in 3D space.
 * Class is nearly identical to the model class but it also holds information about the rotation on the orbit of the planet
 * @author Simon Weck
 *
 */
public class PlanetModel {

	private PlanetMesh planetMesh;
	private Model model;
	//transformation information
	private float x,y,z; 
	private float scale; 
	private float rotationX,rotationY,rotationZ; 
	//center of the planet to rotate around
	private float centerX,centerY,centerZ;
	//random speed around itself
	private float speedX=(float)(Math.random()*(0.3f-0.01f)+0.01f);
	private float speedY=(float)(Math.random()*(0.3f-0.01f)+0.01f);
	private float speedZ=(float)(Math.random()*(0.3f-0.01f)+0.01f);
	private Vector3f orbitAxis;
	//random speed around the orbit
	private float speedOrbit=(float)(Math.random()*(0.2f-0.01f)+0.01f);
	private float orbitRotation; 
	
	
	/**
	 * creates model out of the given mesh and transformations
	 */
	public PlanetModel(PlanetMesh mesh,Vector3f center,Vector3f translation,float scale,Material material,Vector3f orbitAxis) {
		this.planetMesh = mesh;
		this.x=translation.x;
		this.y=translation.y;
		this.z=translation.z;
		this.scale=scale;
		this.centerX= center.x;
		this.centerY= center.y;
		this.centerZ= center.z;		
		model=new Model(planetMesh.getMesh(),x,y,z,scale,material);	
		this.orbitAxis= orbitAxis;
	}
	
	/**
	 * creates model out of the given mesh and transformations
	 */
	public PlanetModel(PlanetMesh mesh,Vector3f translation,float scale,Material material) {
		this.planetMesh = mesh;
		this.x=translation.x;
		this.y=translation.y;
		this.z=translation.z;
		this.scale=scale;
		this.centerX= translation.x;
		this.centerY= translation.y;
		this.centerZ= translation.z;
		model=new Model(planetMesh.getMesh(),x,y,z,scale,material);	
	}
	
	public void increaseRotation(float rotationX,float rotationY,float rotationZ) {
		this.rotationX+=rotationX;
		this.rotationX%=360;
		this.rotationY+=rotationY;
		this.rotationY%=360;
		this.rotationZ+=rotationZ;
		this.rotationZ%=360;
		model.setRotationX(this.rotationX);
		model.setRotationY(this.rotationY);
		model.setRotationZ(this.rotationZ);
	}
	
	public void increasePosition(float x,float y,float z) {
		this.x+=x;
		this.y+=y;
		this.z+=z;
		model.setX(this.x);
		model.setY(this.y);
		model.setZ(this.z);
	}
	
	public void increaseScale(float scale) {
		this.scale+=scale;
		model.setScale(this.scale);
	}
	
	public void setX(float x) {
		this.x=x;
		model.setX(this.x);
	}
	
	public void setY(float y) {
		this.y=y;
		model.setY(this.y);
	}
	
	public void setZ(float z) {
		this.z=z;
		model.setZ(this.z);
	}
	
	public void setScale(float scale) {
		this.scale=scale;
		model.setScale(this.scale);
	}
		
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getScale() {
		return scale;
	}

	public float getRotationX() {
		return rotationX;
	}

	public float getRotationY() {
		return rotationY;
	}

	public float getRotationZ() {
		return rotationZ;
	}
	
	public void addTranslation(float x, float y,float z) {
		model.getMatrixStack().addTranslation(x, y, z);		
	}
	
	public void addRotation(float rotateX,float rotateY,float rotateZ) {
		model.getMatrixStack().addRotation(rotateX, rotateY, rotateZ);		
	}
	
	public void addScale(float uniformScale) {
		model.getMatrixStack().addScale(uniformScale);
	}
	
	public void addScale(float scaleX,float scaleY,float scaleZ) {
		model.getMatrixStack().addScale(scaleX, scaleY, scaleZ);
	}
	
	public void addMatrix(Matrix4f matrix) {
		model.getMatrixStack().addMatrix(matrix);
	}

	public void addRotationArroundPoint() {
		model.addRotationArroundPoint(new Vector3f(centerX, centerY, centerZ), orbitAxis, orbitRotation);
	}
	
	public void addRotationArroundPoint(Vector3f point,Vector3f axis,float angle) {
		model.addRotationArroundPoint(point, axis, angle);
	}
	
	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX; 
	}

	public float getCenterZ() {
		return centerZ;
	}

	public void setCenterZ(float centerZ) {
		this.centerZ = centerZ;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}
	
	public Model getModel() {
		return model;
	}

	public Vector3f getCenter() {
		return new Vector3f(centerX, centerY, centerZ);
	}

	public PlanetMesh getPlanetMesh() {
		return planetMesh;
	}

	public void increaseRotation() {
		rotationX+=speedX;
		rotationY+=speedY;
		rotationZ+=speedZ;
		model.setRotationX(rotationX);
		model.setRotationY(rotationY);
		model.setRotationZ(rotationZ);
	}

	public void increaseOrbitRotation() {
		orbitRotation+=speedOrbit;
	}
	
	public float getOrbitRotation() {
		return orbitRotation;
	}

	public Vector3f getOrbitAxis() {
		return orbitAxis;
	}

	public void setOrbitAxis(Vector3f orbitAxis) {
		this.orbitAxis = orbitAxis;
	}

	public void setScale(float x, float y, float z) {
		model.setScale(x,y,z);	
	}
	
	public void generate() {
		planetMesh.generate();
	}

	public Vector3f getTranslation() {
		return new Vector3f(x,y,z);
	}
	
}
