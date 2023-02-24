package Computergrafik.Engine.Core.Models;

import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.MatrixStack4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Primitives.Primitive;

/**
 * 
 * representation of a virtual model that we can be moved, rotated scaled etc.
 * (Basically a mesh that gets transformed in 3D space)
 * The model can get rendered to the screen
 * @author Simon Weck
 */
public class Model  {
	
	private Material material;
	private Mesh mesh; //raw mesh that we can transform
		
	private float x,y,z; //position
	private float scaleX,scaleY,scaleZ; //scale
	private float rotationX,rotationY,rotationZ; //rotation around all axis
	
	private Matrix4f modelMatrix=new Matrix4f(); //transformation matrix describing all transformations

	private boolean updateMatrix;
	
	private MatrixStack4f matrixStack= new MatrixStack4f();
	
	/**
	 * Creates a model with given transformations.
	 * The material of the model has 0 shininess and emission, and white diffuse and ambient lightning
	 */
	public Model(Mesh mesh,float x,float y,float z,float rotationX, float rotationY,float rotationZ, float scale) {
		this.mesh=mesh;	
		this.x=x;
		this.y=y;
		this.z=z;
		scaleX=scale;
		scaleY=scale;
		scaleZ=scale;
		this.rotationX=rotationX;
		this.rotationY=rotationY;
		this.rotationZ=rotationZ;
		this.material=new Material();
		modelMatrix.changeToModelMatrix(this);
	}
	
	/**
	 * Creates a model with scale 1 at location 0,0,0 with no rotation
	 * The material of the model has 0 shininess and emission, and white diffuse and ambient lightning
	 */
	public Model(Mesh mesh) {
		this.mesh=mesh;
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=new Material();
	}
	
	/**
	 * Creates a model out of a mesh that gets created from an obj file.
	 * The model has the scale 1 is at location 0,0,0 and is not rotated
	 * The material of the model has 0 shininess and emission, and white diffuse and ambient lightning
	 * 
	 * @param file
	 * 			-Path to the obj file (inside the res folder)
	 */
	public Model(String file) {
		mesh=new Mesh(file); //creates mesh out the file
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=new Material();
	}
	
	/**
	 * Creates a model out of a mesh that gets created from an obj file with given rgb color values.
	 * The model has the scale 1 is at location 0,0,0 and is not rotated
	 * The material of the model has 0 shininess and emission, and white diffuse and ambient lightning
	 */
	public Model(String file,float[] colors) {
		mesh=new Mesh(file,colors); //creates mesh out the file
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=new Material();
	}
	
	
	/**
	 * Created a model out of a primitive
	 * The material of the model has 0 shininess and emission, and white diffuse and ambient lightning
	 */
	public Model(Primitive primitive) {
		this.mesh=primitive.getMesh();
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=new Material();
	}
	
	
	/**
	 * Creates a model with given transformations
	 * 
	 */
	public Model(Mesh mesh,float x,float y,float z,float rotationX, float rotationY,float rotationZ, float scale,Material material) {
		this.mesh=mesh;	
		this.x=x;
		this.y=y;
		this.z=z;
		scaleX=scale;
		scaleY=scale;
		scaleZ=scale;
		this.rotationX=rotationX;
		this.rotationY=rotationY;
		this.rotationZ=rotationZ;
		this.material=material;
		modelMatrix.changeToModelMatrix(this);
	}
	
	public Model(Mesh mesh,float x,float y,float z,float scale,Material material) {
		this.mesh=mesh;	
		this.x=x;
		this.y=y;
		this.z=z;
		scaleX=scale;
		scaleY=scale;
		scaleZ=scale;
		this.material=material;
		modelMatrix.changeToModelMatrix(this);
	}
	
	public Model(Mesh mesh,float x,float y,float z,Material material) {
		this.mesh=mesh;	
		this.x=x;
		this.y=y;
		this.z=z;
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=material;
		modelMatrix.changeToModelMatrix(this);
	}
	
	public Model(Mesh mesh,float x,float y,float z) {
		this.mesh=mesh;	
		this.x=x;
		this.y=y;
		this.z=z;
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=new Material();
		modelMatrix.changeToModelMatrix(this);
	}
	
	/**
	 * Creates a model with scale 1 at location 0,0,0 with no rotation
	 */
	public Model(Mesh mesh,Material material) {
		this.mesh=mesh;
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=material;
	}
	
	/**
	 * Creates a model out of a mesh that gets created from an obj file.
	 * The model has the scale 1 is at location 0,0,0 and is not rotated
	 * 
	 * @param file
	 * 			-Path to the obj file (inside the res folder)
	 */
	public Model(String file,Material material) {
		mesh=new Mesh(file); //creates mesh out the file
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=material;
	}
	
	public Model(String file,Vector3f scale) {
		mesh=new Mesh(file); //creates mesh out the file
		scaleX=scale.x;
		scaleY=scale.y;
		scaleZ=scale.z;
		this.material=new Material();
	}
	
	/**
	 * Created a model out of a primitive
	 *
	 */
	public Model(Primitive primitive,Material material) {
		this.mesh=primitive.getMesh();
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=material;
	}
	
	public Model(String file, float x, float y, float z) {
		this.mesh=new Mesh(file);
		this.x=x;
		this.y=y;
		this.z=z;
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=new Material();
		modelMatrix.changeToModelMatrix(this);	
	}
	
	public Model(String file,Vector3f translation,Vector3f scale) {
		this.mesh=new Mesh(file);
		this.x=translation.x;
		this.y=translation.y;
		this.z=translation.z;
		scaleX=scale.x;
		scaleY=scale.y;
		scaleZ=scale.z;
		this.material=new Material();
		modelMatrix.changeToModelMatrix(this);	
	}
	
	public Model(String file,Vector3f translation,Vector3f scale,Material material) {
		this.mesh=new Mesh(file);
		this.x=translation.x;
		this.y=translation.y;
		this.z=translation.z;
		scaleX=scale.x;
		scaleY=scale.y;
		scaleZ=scale.z;
		this.material=material;
		modelMatrix.changeToModelMatrix(this);	
	}
	
	public Model(String file, float x, float y, float z, Material material) {
		this.mesh=new Mesh(file);
		this.x=x;
		this.y=y;
		this.z=z;
		scaleX=1;
		scaleY=1;
		scaleZ=1;
		this.material=material;
		modelMatrix.changeToModelMatrix(this);	
	}

	public Model(String file,Vector3f scale,Material material) {
		mesh=new Mesh(file); //creates mesh out the file
		scaleX=scale.x;
		scaleY=scale.y;
		scaleZ=scale.z;
		this.material=material;
	}

	/**
	 * increases the current xyz position by dx,dy,dz
	 */
	public void increasePosition(float dx,float dy,float dz) {
		this.x+=dx;
		this.y+=dy;
		this.z+=dz;	
		updateMatrix=true;
	}
	
	/**
	 * increases the current xyz rotation by dx,dy,dz
	 * rotation in angle
	 */
	public void increaseRotation(float dx,float dy,float dz) {
		this.rotationX+=dx;
		this.rotationY+=dy;
		this.rotationZ+=dz;
		updateMatrix=true;
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
		rotationZ%=360;
		updateMatrix=true;
	}

	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}
	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material=material;
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
