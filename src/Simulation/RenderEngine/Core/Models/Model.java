package Simulation.RenderEngine.Core.Models;

import Simulation.RenderEngine.Core.Math.Matrix4f;
import Simulation.RenderEngine.Core.Math.MatrixStack4f;
import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Primitives.Primitive;

/**
 * 
 * representation of a virtual model that we can be moved, rotated scaled etc.
 * (Basically a mesh that gets transformed in 3D space)
 * The model can get rendered to the screen
 * @author Simon Weck
 */
public abstract class Model {
	

	protected Mesh mesh; //raw mesh that we can transform
		
	protected float x,y,z; //position
	protected float scaleX,scaleY,scaleZ; //scale
	protected float rotationX,rotationY,rotationZ; //rotation around all axis
	
	protected Matrix4f modelMatrix=new Matrix4f(); //transformation matrix describing all transformations

	protected boolean updateMatrix;
	
	protected MatrixStack4f matrixStack= new MatrixStack4f();
	

	/**
	 * Creates a model out of a mesh that gets created from an obj file.
	 * The model has the scale 1 and is at location x,y,0 and is not rotated
	 * 
	 */
	public Model(String file,float[] colors ,float x,float y) {
		mesh=new Mesh(file,colors);
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.x = x;
		this.y = y;
		updateMatrix=true;
	}
	
	public Model(String file,float r,float g,float b ,float x,float y) {
		mesh=new Mesh(file,r,g,b);
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.x = x;
		this.y = y;
		updateMatrix=true;
	}
	
	public Model(String file,String texture,float x,float y) {
		mesh=new Mesh(file,texture);
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.x = x;
		this.y = y;
		updateMatrix=true;
	}
	
	
	/**
	 * Created a model out of a primitive (Sphere,Cube,Plane)
	 * The model has the scale 1 and is at location x,y,0 and is not rotated
	 *
	 */
	public Model(Primitive primitive,float r,float g,float b, float x,float y) {
		mesh = new Mesh(primitive,r,g,b);
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.x = x;
		this.y = y;
		updateMatrix=true;
	}
	
	public Model(Primitive primitive,String texture, float x,float y) {
		mesh = new Mesh(primitive,texture);
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.x = x;
		this.y = y;
		updateMatrix=true;
	}
	
	public Model(Primitive primitive,float[] colors, float x,float y) {
		mesh = new Mesh(primitive,colors);
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.x = x;
		this.y = y;
		updateMatrix=true;
	}
	
	public Model(float[] vertices,float r,float g,float b, float x,float y) {
		mesh = new Mesh(vertices,r,g,b);
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.x = x;
		this.y = y;
		updateMatrix=true;
	}
	
	/**
	 * increases the current xyz position by dx,dy,dz
	 */
	public void increasePosition(float dx,float dy,float dz) {
		if (dx!=0) this.x+=dx;
		if (dy!=0) this.y+=dy;
		if (dz!=0) this.z+=dz;	
		if (dx!=0 || dy!=0 || dz!=0) 
			updateMatrix=true;
		
	}
	
	/**
	 * increases the current xyz rotation by dx,dy,dz
	 * rotation in angle
	 */
	public void increaseRotation(float dx,float dy,float dz) {
		if (dx!=0) 	this.rotationX+=dx;
		if (dy!=0) this.rotationY+=dy;
		if (dz!=0) this.rotationZ+=dz;
		if (dx!=0 || dy!=0 || dz!=0)
			updateMatrix=true;
	}
	
	public void updateMatrix() {
		if(updateMatrix) {
			modelMatrix.changeToModelMatrix(this);
			updateMatrix=false;
		}		
	}
	
	public Mesh getMesh() {
		return mesh;
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

	public float getScaleX() {
		return scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}
	
	public float getScaleZ() {
		return scaleZ;
	}
	
	public void setScaleX(float scaleX) {
		this.scaleX=scaleX;
		updateMatrix=true;
	}
	
	public void setScaleY(float scaleY) {
		this.scaleY=scaleY;
		updateMatrix=true;
	}
	
	public void setScaleZ(float scaleZ) {
		this.scaleZ=scaleZ;
		updateMatrix=true;
	}

	public void setScale(float scale) {
		scaleX=scale;
		scaleY=scale;
		scaleZ=scale;
		updateMatrix=true;
	}

	public void setScale(float x,float y,float z) {
		scaleX=x;
		scaleY=y;
		scaleZ=z;
		updateMatrix=true;
	}
	
	public float getRotationX() {
		return rotationX;
	}

	/**
	 * rotation in angle
	 */
	public void setRotationX(float rotationX) {
		this.rotationX = rotationX;
		rotationX%=360;
		updateMatrix=true;
	}
	
	/**
	 * rotation in angle
	 */
	public float getRotationY() {
		return rotationY;
	}

	/**
	 * rotation in angle
	 */
	public void setRotationY(float rotationY) {
		this.rotationY = rotationY;
		rotationY%=360;
		updateMatrix=true;
	}

	public float getRotationZ() {
		return rotationZ;
	}

	/**
	 * rotation in angle
	 */
	public void setRotationZ(float rotationZ) {
		this.rotationZ = rotationZ;
		this.rotationZ%=360;
		updateMatrix=true;
	}

	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}
	
	public void setMatrixUpdate(boolean updateMatrix) {
		this.updateMatrix=updateMatrix;
	}
	
	public boolean getMatrixUpdate() {
		return updateMatrix;
	}

	public MatrixStack4f getMatrixStack() {
		return matrixStack;
	}

	public void setMatrixStack(MatrixStack4f matrixStack) {
		this.matrixStack = matrixStack;
		updateMatrix=true;
	}
	
	public void addTranslation(float x, float y,float z) {
		matrixStack.addTranslation(x, y, z);
		updateMatrix=true;
	}
	
	public void addRotation(float rotateX,float rotateY,float rotateZ) {
		matrixStack.addRotation(rotateX, rotateY, rotateZ);
		updateMatrix=true;
	}
	
	public void addScale(float uniformScale) {
		matrixStack.addScale(uniformScale);
		updateMatrix=true;
	}
	
	public void addScale(float scaleX,float scaleY,float scaleZ) {
		matrixStack.addScale(scaleX, scaleY, scaleZ);
		updateMatrix=true;
	}
	
	public void addMatrix(Matrix4f matrix) {
		matrixStack.addMatrix(matrix);
		updateMatrix=true;
	}

	public void addRotation(float angle,Vector3f axis) {
		matrixStack.addRotation(angle,axis);
		updateMatrix=true;
	}

	public void addRotationArroundPoint(Vector3f point, Vector3f angles) {
		matrixStack.addRotationArroundPoint(point,angles);
		updateMatrix=true;
	}
	
	public void addRotationArroundPoint(Vector3f point, Vector3f axis,float angle) {
		matrixStack.addRotationArroundPoint(point, axis, angle);
		updateMatrix=true;
	}
	
}