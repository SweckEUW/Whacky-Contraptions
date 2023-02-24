package Simulation.RenderEngine.Core.Math;

import java.util.Stack;
/**
 * Stack containing Matrices that can get multiplied to one matrix.
 * @author Simon Weck
 *
 */
public class MatrixStack4f {
	
	Stack<Matrix4f> matrixStack;
	
	/**
	 * creates empty matrixStack
	 */
	public MatrixStack4f() {
		matrixStack= new Stack<Matrix4f>();
	}
	
	/**
	 * adds a translation matrix to the stack
	 * @param x
	 * @param y
	 * @param z
	 */
	public void addTranslation(float x, float y,float z) {
		Matrix4f translation= new Matrix4f();
		translation.translate(x, y, z);
		matrixStack.add(translation);
	}
	
	/**
	 * adds 3 rotation matrices to the stack.One matrix for each rotation
	 * @param rotateX
	 * @param rotateY
	 * @param rotateZ
	 */
	public void addRotation(float rotateX,float rotateY,float rotateZ) {
		Matrix4f rotation= new Matrix4f();
		rotation.rotate(rotateX, new Vector3f(1,0,0));
		rotation.rotate(rotateY, new Vector3f(0,1,0));
		rotation.rotate(rotateZ, new Vector3f(0,0,1));
		matrixStack.add(rotation);
	}
	
	/**
	 * adds a scale matrix with a uniform scale to the 
	 * @param uniformScale
	 */
	public void addScale(float uniformScale) {
		Matrix4f scale= new Matrix4f();
		scale.scale(uniformScale);
		matrixStack.add(scale);
	}
	
	/**
	 * adds a scale matrix to the stack.
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 */
	public void addScale(float scaleX,float scaleY,float scaleZ) {
		Matrix4f scale= new Matrix4f();
		scale.scale(scaleX,scaleY,scaleZ);
		matrixStack.add(scale);
	}
	
	public void addMatrix(Matrix4f matrix) {
		matrixStack.add(matrix);
	}
	
	public int size() {
		return matrixStack.size();	
	}
	
	public Matrix4f pop() {
		return matrixStack.pop();
	}

	public Matrix4f get(int index) {
		return matrixStack.get(index);
	}
	
	public void clear() {
		matrixStack.clear();
	}
	
	public void addRotation(float angle,Vector3f axis) {
		Matrix4f rotation = new Matrix4f(); 
		rotation.rotate(angle, axis);
		matrixStack.add(rotation);
	}

	public void addRotationArroundPoint(Vector3f point, Vector3f angles) {
		Matrix4f rotation = new Matrix4f();
		rotation.rotateArroundPoint(point, angles);
		matrixStack.add(rotation);
	}
	
	public void addRotationArroundPoint(Vector3f point, Vector3f axis,float angle) {
		Matrix4f rotation = new Matrix4f();
		rotation.rotateArroundPoint(point, axis,angle);
		matrixStack.add(rotation);
	}
	
}
