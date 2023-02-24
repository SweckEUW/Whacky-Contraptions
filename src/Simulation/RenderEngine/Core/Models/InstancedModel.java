package Simulation.RenderEngine.Core.Models;

import Simulation.RenderEngine.Core.Math.Matrix4f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Primitive;

/**
 * representation of a virtual model that we can be moved, rotated scaled etc.
 * (Basically a mesh that gets transformed in 3D space)
 * The InstancedModel gets rendered multiple times depending on the instance amount. Every instance has its own rotation scale and position and therefore its own model matrix.
 * Every instance shares one set of indices vertices and normals. We only update the position rotation and scale of each instance every frame.
 * The instances also have the same material
 *
 * @author Simon Weck
 *
 */
public class InstancedModel{

	private boolean createMesh;
	
	private Material material;
	private Mesh mesh;

	private int instances;

	private float[] x,y,z;
	private float[] scaleX,scaleY,scaleZ;
	private float[] rotationX,rotationY,rotationZ;

	private Matrix4f[] modelMatrix;
	private int matrixVBOID;

	private boolean updateMatrix;

	//private MatrixStack4f[] matrixStack;


	public InstancedModel(Primitive primitive,int instances,Material material,float[] colors) {
		this.instances = instances;
		initArrays();
		mesh=new Mesh(primitive,colors); //creates mesh out the file
		for (int i = 0; i < instances; i++) {
			scaleX[i]=1;
			scaleY[i]=1;
			scaleZ[i]=1;
		}
		this.material=material;
		
		createMesh=true;
	}

	public InstancedModel(String file,int instances,Material material,float[] colors) {
		this.instances = instances;
		initArrays();
		mesh=new Mesh(file,colors); //creates mesh out the file
		for (int i = 0; i < instances; i++) {
			scaleX[i]=1;
			scaleY[i]=1;
			scaleZ[i]=1;
		}
		this.material=material;
		
		createMesh=true;
	}

	public void update() {
		if (createMesh) 
			createMesh();
	}

	public void createMesh() {
		loadToGPU.loadinstancedMeshToGPU(this);
		this.createMesh = false;
	}
	
	/**
	 *increases the x,y,z position of each instance by dx,dy,dz
	 * @param dx
	 * 		-x coordinate increment
	 * @param dy
	 * 		-y coordinate increment
	 * @param dz
	 *		-z coordinate increment
	 */
	public void increasePosition(float dx,float dy,float dz) {
		for (int i = 0; i < instances; i++) {
			x[i]+=dx;
			y[i]+=dy;
			z[i]+=dz;
		}
		updateMatrix=true;
	}

	/**
	 * increases the x,y,z rotation of each instance by dx,dy,dz.
	 * Rotation in angles
	 * @param dx
	 * 		-Rotation in angles arround the x axis
	 * @param dy
	 *		 -Rotation in angles arround the y axis
	 * @param dz
	 * 		-Rotation in angles arround the z axis
	 */
	public void increaseRotation(float dx,float dy,float dz) {
		for (int i = 0; i < instances; i++) {
			rotationX[i]+=dx;
			rotationY[i]+=dy;
			rotationZ[i]+=dz;
		}
		updateMatrix=true;
	}

	private void initArrays() {
		x = new float[instances];
		y = new float[instances];
		z = new float[instances];
		scaleX= new float[instances];
		scaleY= new float[instances];
		scaleZ= new float[instances];
		rotationX = new float[instances];
		rotationY = new float[instances];
		rotationZ = new float[instances];

		modelMatrix = new Matrix4f[instances];
		for (int i = 0; i < modelMatrix.length; i++)
			modelMatrix[i] = new Matrix4f();

		/*
		matrixStack = new MatrixStack4f[instances];
		for (int i = 0; i < matrixStack.length; i++)
			matrixStack[i] = new MatrixStack4f();
		*/
	}

	public float getX(int index) {
		return x[index];
	}

	public void setX(int index,float x) {
		this.x[index]=x;
		updateMatrix=true;
	}

	public void setXconstant(int index,float x) {
		this.x[index]+=x;
		updateMatrix=true;
	}

	public float getY(int index) {
		return y[index];
	}

	public void setY(int index,float y) {
		this.y[index]=y;
		updateMatrix=true;
	}

	public void setYconstant(int index,float y) {
		this.y[index] +=y;
		updateMatrix=true;
	}

	public float getZ(int index) {
		return z[index];
	}

	public void setZ(int index,float z) {
		this.z[index]=z;
		updateMatrix=true;
	}

	public float[] getScaleX() {
		return scaleX;
	}

	public float[] getScaleY() {
		return scaleY;
	}

	public float[] getScaleZ() {
		return scaleZ;
	}

	public void setScale(int index, float scale) {
		scaleX[index]=scale;
		scaleY[index]=scale;
		scaleZ[index]=scale;
		updateMatrix=true;
	}

	public Matrix4f[] getModelMatrix() {
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

	public int getMatrixVBOID() {
		return matrixVBOID;
	}

	public void setMatrixVBOID(int matrixVBOID) {
		this.matrixVBOID = matrixVBOID;
	}

	public float[] getRotationX() {
		return rotationX;
	}

	public void setRotationX(float[] rotationX) {
		this.rotationX = rotationX;
	}

	public float[] getRotationY() {
		return rotationY;
	}

	public void setRotationY(float[] rotationY) {
		this.rotationY = rotationY;
	}

	public float[] getRotationZ() {
		return rotationZ;
	}

	public void setRotationZ(float[] rotationZ) {
		this.rotationZ = rotationZ;
	}

	public int getInstances() {
		return instances;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public boolean isCreateMesh() {
		return createMesh;
	}

	public void setCreateMesh(boolean createMesh) {
		this.createMesh = createMesh;
	}
	
	

	/*
	public MatrixStack4f[] getMatrixStack() {
		return matrixStack;
	}

	public void addTranslation(float x, float y,float z,int index) {
		matrixStack[index].addTranslation(x, y, z);
		updateMatrix=true;
	}

	public void addRotation(float rotateX,float rotateY,float rotateZ,int index) {
		matrixStack[index].addRotation(rotateX, rotateY, rotateZ);
		updateMatrix=true;
	}
	
	public void addScale(float uniformScale,int index) {
		matrixStack[index].addScale(uniformScale);
		updateMatrix=true;
	}
	
	public void addScale(float scaleX,float scaleY,float scaleZ,int index) {
		matrixStack[index].addScale(scaleX, scaleY, scaleZ);
		updateMatrix=true;
	}
	
	public void addMatrix(Matrix4f matrix,int index) {
		matrixStack[index].addMatrix(matrix);
		updateMatrix=true;
	}

	public void addRotation(float angle,Vector3f axis,int index) {
		matrixStack[index].addRotation(angle,axis);
		updateMatrix=true;
	}

	public void addRotationArroundPoint(Vector3f point, Vector3f angles,int index) {
		matrixStack[index].addRotationArroundPoint(point,angles);
		updateMatrix=true;
	}
	
	public void addRotationArroundPoint(Vector3f point, Vector3f axis,float angle,int index) {
		matrixStack[index].addRotationArroundPoint(point, axis, angle);
		updateMatrix=true;
	}
	*/
}
