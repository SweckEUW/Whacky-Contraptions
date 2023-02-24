package Simulation.RenderEngine.Core.Math;

import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;

import Simulation.RenderEngine.Core.Config;
import Simulation.RenderEngine.Core.Camera.Camera;
import Simulation.RenderEngine.Core.Models.Model;

/**
 * 
 * 4*4 Float Matrix representation
 *
 * functions inspired by lwjgl and Joml Math functions
 * https://github.com/LWJGL/lwjgl/blob/master/src/java/org/lwjgl/util/vector/Matrix4f.java
 *
 */
public class Matrix4f {
	
	//Identity matrix by default
	public float m00=1, m01=0, m02=0, m03=0;
	public float m10=0, m11=1, m12=0, m13=0;
	public float m20=0, m21=0, m22=1, m23=0;
	public float m30=0, m31=0, m32=0, m33=1;
	
	/**
	 * creates a 4x4 matrix where every number is a float.
	 * The matrix starts as an identity matrix
	 */
	public Matrix4f() {
		
	}
	
	/**
	 * creates a 4x4 matrix where every number is a float.
	 * every entry of the matrix is the same as the input matrix m
	 * @param m
	 */
	public Matrix4f(Matrix4f m) {
		this.m00=m.m00;
		this.m01=m.m01;
		this.m02=m.m02;
		this.m03=m.m03;
		this.m10=m.m10;
		this.m11=m.m11;
		this.m12=m.m12;
		this.m13=m.m13;
		this.m20=m.m20;
		this.m21=m.m21;
		this.m22=m.m22;
		this.m23=m.m23;
		this.m30=m.m30;
		this.m31=m.m31;
		this.m32=m.m32;
		this.m33=m.m33; 
	}
	
	/**
	 * adds this matrix with the in matrix
	 */
	public Matrix4f add(Matrix4f in) {
		m00+=in.m00;
		m01+=in.m01;
		m02+=in.m02;
		m03+=in.m03;
		m10+=in.m10;
		m11+=in.m11;
		m12+=in.m12;
		m13+=in.m13;
		m20+=in.m20;
		m21+=in.m21;
		m22+=in.m22;
		m23+=in.m23;
		m30+=in.m30;
		m31+=in.m31;
		m32+=in.m32;
		m33+=in.m33;
		
		return this;
	}

	/**
	 * multiplies this matrix with a float
	 * @param m
	 */
	public void multiply(float m) {
		m00*=m;
		m01*=m;
		m02*=m;
		m03*=m;
		m10*=m;
		m11*=m;
		m12*=m;
		m13*=m;
		m20*=m;
		m21*=m;
		m22*=m;
		m23*=m;
		m30*=m;
		m31*=m;
		m32*=m;
		m33*=m;
	}
	
	/**
	 * rotates this matrix around a point with the give xyz angles
	 * @param point
	 * @param angles
	 */
	public void rotateArroundPoint(Vector3f point,Vector3f angles) {
		Matrix4f rotateArround = new Matrix4f();
		rotateArround.translate(point.x, point.y, point.z);
		rotateArround.rotate(angles.x, new Vector3f(1, 0, 0));
		rotateArround.rotate(angles.y, new Vector3f(0, 1, 0));
		rotateArround.rotate(angles.z, new Vector3f(0, 0, 1));
		rotateArround.translate(-point.x, -point.y, -point.z);
		this.multiply(rotateArround);
	}
	
	/**
	 * rotates this matrix around a point with a given axis and angle
	 * @param point
	 * @param axis
	 * @param angle
	 */
	public void rotateArroundPoint(Vector3f point,Vector3f axis,float angle ) {
		Matrix4f rotateArround = new Matrix4f();
		rotateArround.translate(point.x, point.y, point.z);
		rotateArround.rotate(angle, axis);
		rotateArround.translate(-point.x, -point.y, -point.z);
		this.multiply(rotateArround);
	}
	
	/**
	 * rotates this matrix around a given axis with the given angle
	 * @param angle
	 * @param axis
	 */
	public void rotate(float angle, Vector3f axis) {
		
//		axis.normalize();
//		
//		float angle1=(float)Math.atan2(axis.x,axis.z);
//		angle1 = (float)Math.toDegrees(angle1);
//		
//		float tmp=(float)Math.sqrt(axis.y*axis.y+axis.z*axis.z);
//		float angle2= (float)Math.atan2(axis.y, tmp);
//		angle2 = (float)Math.toDegrees(angle2);
//		
//		Matrix4f all = new Matrix4f();
//		
//		all.translate(-axis.x, -axis.y, -axis.z);
//		
//		all.rotateY(-angle1);
//		all.rotateX(angle2);
//		
//		all.rotateZ(angle);
//		
//		all.rotateX(-angle2);
//		all.rotateY(angle1);
//		
//		all.translate(axis.x, axis.y, axis.z);
//		
//		this.multiply(all);
		
		axis.normalize();
		angle=(float)Math.toRadians(angle);
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		
		float oneminusc = 1.0f - c;
		float xy = axis.x*axis.y;
		float yz = axis.y*axis.z;
		float xz = axis.x*axis.z;
		float xs = axis.x*s;
		float ys = axis.y*s;
		float zs = axis.z*s;

		float f00 = axis.x*axis.x*oneminusc+c;
		float f01 = xy*oneminusc-zs;
		float f02 = xz*oneminusc+ys;
		
		float f10 = xy*oneminusc+zs;
		float f11 = axis.y*axis.y*oneminusc+c;
		float f12 = yz*oneminusc-xs;
		
		float f20 = xz*oneminusc-ys;
		float f21 = yz*oneminusc+xs;
		float f22 = axis.z*axis.z*oneminusc+c;
		

		Matrix4f out=new Matrix4f();
		out.m00=f00;
		out.m01=f01;
		out.m02=f02;
		
		out.m10=f10;
		out.m11=f11;
		out.m12=f12;
		
		out.m20=f20;
		out.m21=f21;
		out.m22=f22;

		this.multiply(out);
	}
	
	/**
	 * Transposes this matrix
	 */
	public void transpose() {
		float m01=this.m01;
		float m02=this.m02;
		float m03=this.m03;
		float m12=this.m12;
		float m13=this.m13;
		float m23=this.m23;
		this.m01=this.m10;
		this.m02=this.m20;
		this.m03=this.m30;
		this.m12=this.m21;
		this.m13=this.m31;
		this.m23=this.m32;
		this.m10=m01;
		this.m20=m02;
		this.m30=m03;
		this.m21=m12;
		this.m31=m13;
		this.m32=m23;
	}
	

	/**
	 * Multiplies the model and view matrices together and stores it into one modelViewMatrix
	 * 
	 * @param camera
	 * 				-camera to get view matrix
	 * @param model
	 * 				-Model to get model matrix
	 * @param modelViewMatrix
	 * 				-modelViewMatrix matrix (matrix to save the created matrix)
	 */
	public static void changeToModelViewMatrix(Camera camera,Model model,Matrix4f modelViewMatrix) {
		modelViewMatrix.setIdentityMatrix(); //reset destination matrix						
		modelViewMatrix.multiply(camera.getViewMatrix()); 
		modelViewMatrix.multiply(model.getModelMatrix());	
	}
	
	/**
	 * Multiplies the model, view and projection matrices together and stores it into one modelViewProjectionMatrix
	 * 
	 * @param viewMatrix
	 * @param modelMatrix
	 * @param projectionMatrix
	 * @param modelViewProjectionMatrix
	 * 				-modelViewProjectionMatrix matrix (matrix to save the created matrix)
	 */
	public static void changeToModelViewProjectionMatrix(Matrix4f viewMatrix,Matrix4f modelMatrix,Matrix4f projectionMatrix,Matrix4f modelViewProjectionMatrix) {
		modelViewProjectionMatrix.setIdentityMatrix(); //reset destination matrix	
		//modelViewProjectionMatrix.multiply(projectionMatrix);	
		modelViewProjectionMatrix.multiply(viewMatrix);
		modelViewProjectionMatrix.multiply(modelMatrix);		
	}
	
	/**
	 * Resets the matrix and changes it into a view matrix
	 * The view matrix gets constructed out of the transformations of the camera
	 * 
	 * @param camera
	 * 				-Camera to get needed transformations
	 */
	public void changeToViewMatrix(Camera camera) {
		this.setIdentityMatrix();//reset

		this.translate(-camera.getX(), -camera.getY(), -camera.getZ()); 
		
		this.rotateX(-camera.getRotateX());										
		this.rotateY(-camera.getRotateY());
		this.rotateZ(-camera.getRotateZ());		
	}
	
	/**
	 * Changes this matrix into a perspective matrix.
	 * 
	 * @param fieldOfView
	 * 				-Field of view in angle
	 * @param nearPlane
	 * 				-Near clipping plane distance
	 * @param farPlane
	 * 				-Far clipping plane distance
	 */
	public void changeToPerspecitveMatrix(float fieldOfView, float nearPlane,float farPlane,int windowHeight,int windowWidth) {
		/* Perspective Matrix;
		 * 
		 * a   0   0   0
		 * 0   q   0   0
		 * 0   0   b   c
		 * 0   0  -1   0
		 * 
		*/
		this.setIdentityMatrix();//reset
		
		float q= 1/(float)(Math.tan(fieldOfView/2));
		float a=q/((float)windowWidth/(float)windowHeight);
		float b=(nearPlane+farPlane)/(nearPlane-farPlane);
		float c=(2*(nearPlane*farPlane))/(nearPlane-farPlane);
		
		m00=a;
		m11=q;
		m22=b;
		m23=c;
		m32=-1;
		m33=0;
		
//		changeToOrthographicMatrix(100,100,100,100,100,100);
	}
	
//	public void changeToOrthographicMatrix(float left,float right,float top,float bottom,float near,float far) {
//		this.setIdentityMatrix();//reset
//		
//		m00 = 2/(right-left);
//		m11 = 2/(top-bottom);
//		m22 = -2/(far-near);
//		
////		m03 = -(right+left)/(right-left);
////		m13 = -(top+bottom)/(top-bottom);
////		m23 = -(far+near)/(far-near);
//	}
	
	/**
	 * Resets this matrix and stores a newly created model matrix in this matrix.
	 * The model matrix gets created out of the transformations of the given model
	 * 
	 * @param
	 * 			-Model to get transformation attributes
	 */
	public void changeToModelMatrix(Model model) {
		this.setIdentityMatrix(); //reset matrix
				
		for (int i = 0; i < model.getMatrixStack().size(); i++) 
			model.getModelMatrix().multiply(model.getMatrixStack().pop()); 				//4 Matrix Stack
		
		//transformations in this order!
		translate(model.getX(),model.getY(),model.getZ()); 						//3 translate 	
		
		rotateX(model.getRotationX());
		rotateY(model.getRotationY());
		rotateZ(model.getRotationZ());

		scale(model.getScaleX(),model.getScaleY(),model.getScaleZ()); 				//1 scale		
	}
	
	
	/**
	 * sets the Matrix to the identity Matrix (Einheitsmatrix)
	 */
	public void setIdentityMatrix () {
		m00=1;	m01=0;	m02=0;	m03=0;
		m10=0;	m11=1;	m12=0;	m13=0;
		m20=0;	m21=0;	m22=1;	m23=0;
		m30=0;	m21=0;	m23=0;	m33=1;
	}
	
	/**
	 * Multiplies this Matrix with a scale matrix that gets constructed out of the scale components.
	 * Stores the result into this matrix
	 * 
	 * @param x
	 * 			-Scale amount in X direction
	 * @param y
	 * 			-Scale amount in Y direction
	 * @param z
	 * 			-Scale amount in Z direction
	 */
	public void scale(float x,float y,float z) {
		/* Scale matrix:
		 * 
		 * x   0   0   0
		 * 0   y   0   0
		 * 0   0   z   0
		 * 0   0   0   1
		 * 
		 * scale matrix multiplied with current matrix:
		 * (some multiplication lead up to 0 and are not listed here)
		 * could also construct new Matrix as scale matrix and use the multiply method with the new matrix (worse performance)
		*/		
		m00*=x;
		m01*=y;
		m02*=z;
		m10*=x;
		m11*=y;
		m12*=z;
		m20*=x;
		m21*=y;
		m22*=z;
		m30*=x;
		m31*=y;
		m32*=z;
	}
	
	/**
	 * uniform scale with 1 value
	 * 
	 * @param scale
	 * 			-uniform scale value
	 */
	public void scale(float scale) {
		m00*=scale;
		m01*=scale;
		m02*=scale;
		m10*=scale;
		m11*=scale;
		m12*=scale;
		m20*=scale;
		m21*=scale;
		m22*=scale;
		m30*=scale;
		m31*=scale;
		m32*=scale;
	}
	
	/**
	 * Multiplies this Matrix with a rotate matrix around the X-axis that gets constructed out of chosen angle.
	 * Stores the result into this matrix.
	 * 
	 * @param angle
	 * 			-float that describes the rotation angle in degree
	 */
	public void rotateX(float angle) {
		/* Rotation around X axis:
		 * 
		 * 1 		0 			0 			0
		 * 0 		+cos(angle)	-sin(angle) 0
		 * 0 		+sin(angle)	+cos(angle) 0
		 * 0 		0 			0 			1
		 * 
		 * rotation matrix around x axis multiplied with current matrix:
		 * (some multiplication lead up to 0 and are not listed here)
		 * could also construct new Matrix as translation matrix and use the multiply method with the new matrix (worse performance)
		*/
		float c=(float)Math.cos(Math.toRadians(angle));
		float s=(float)Math.sin(Math.toRadians(angle));
		
		Matrix4f rotateX = new Matrix4f();
		rotateX.m11=c;
		rotateX.m12=-s;
		rotateX.m21=s;
		rotateX.m22=c;
		
		this.multiply(rotateX);
	}
	
	/**
	 * Multiplies this Matrix with a rotate matrix around the Y-axis that gets constructed out of chosen angle.
	 * Stores the result into this matrix.
	 * 
	 * @param angle
	 * 			-float that describes the rotation angle in degree
	 */
	public void rotateY(float angle) {
		/* Rotation around Y axis:
		 * 
		 * +cos(angle) 	0	+sin(angle) 0
		 * 0 			1	0			0
		 * -sin(angle) 	0	+cos(angle) 0
		 * 0 			0 	0 			1
		 * 
		 * rotation matrix around Y axis multiplied with current matrix:
		 * (some multiplication lead up to 0 and are not listed here)
		 * could also construct new Matrix as translation matrix and use the multiply method with the new matrix (worse performance)
		*/
		float c=(float)Math.cos(Math.toRadians(angle));
		float s=(float)Math.sin(Math.toRadians(angle));
		
		Matrix4f rotateY = new Matrix4f();
		rotateY.m00=c;
		rotateY.m02=s;
		rotateY.m20=-s;
		rotateY.m22=c;
		
		this.multiply(rotateY);
	}
	
	/**
	 * Multiplies this Matrix with a rotate matrix around the Z-axis that gets constructed out of chosen angle.
	 * Stores the result into this matrix.
	 * 
	 * @param angle
	 * 			-float that describes the rotation angle in degree
	 */
	public void rotateZ(float angle) {
		/* Rotation around Z axis:
		 * 
		 * +cos(angle) 	-sin(angle) 0	0
		 * sin(angle) 	+cos(angle)	0	0
		 * 0 			0			1	0
		 * 0 			0 			0 	1
		 * 
		 * rotation matrix around Z axis multiplied with current matrix:
		 * (some multiplication lead up to 0 and are not listed here)
		 * could also construct new Matrix as translation matrix and use the multiply method with the new matrix (worse performance)
		*/
		float c=(float)Math.cos(Math.toRadians(angle));
		float s=(float)Math.sin(Math.toRadians(angle));
		
		Matrix4f rotateZ = new Matrix4f();
		rotateZ.m00=c;
		rotateZ.m01=-s;
		rotateZ.m10=s;
		rotateZ.m11=c;
		
		this.multiply(rotateZ);
	}
		
	/**
	 * Multiplies this Matrix with translation matrix that gets constructed out of the 3 translation components.
	 * Stores the result into this matrix
	 * 
	 * @param x
	 * 			-Translation amount in X direction
	 * @param y
	 * 			-Translation amount in Y direction
	 * @param z
	 * 			-Translation amount in Z direction
	 */
	public void translate(float x,float y,float z) {
		/* Translation matrix:
		 * 
		 * 1 	0 	0 	x
		 * 0 	1 	0 	y
		 * 0 	0 	1 	z
		 * 0 	0 	0 	1
		 * 
		 * translation matrix multiplied with current matrix:
		 * (some multiplication lead up to 0 and are not listed here)
		 * could also construct new Matrix as translation matrix and use the multiply method with the new matrix (worse performance)
		*/
		
		//Convert x,y to top left corner
//		x=x-Config.CANVAS_WIDTH/(float)4;
//		y=-y+Config.CANVAS_HEIGHT/(float)4;
		
		//divide by width and height to work with pixels as unit
		x/=Config.CANVAS_WIDTH;
		y/=Config.CANVAS_HEIGHT;
		
		
		m03 += m00 * x + m01 * y + m02 * z;
		m13 += m10 * x + m11 * y + m12 * z;
		m23 += m20 * x + m21 * y + m22 * z;
	}
	

	/**
	 * Multiplies this matrix with the given matrix and stores the result in this matrix
	 * 
	 * @param matrix
	 * 			-Matrix to multiply with
	 */
	public Matrix4f multiply (Matrix4f s) {
		float m00 = this.m00 * s.m00 + this.m01 * s.m10 + this.m02 * s.m20 + this.m03 * s.m30;
		
		float m01 = this.m00 * s.m01 + this.m01 * s.m11 + this.m02 * s.m21 + this.m03 * s.m31;
			
		float m02 = this.m00 * s.m02 + this.m01 * s.m12 + this.m02 * s.m22 + this.m03 * s.m32;
		
		float m03 = this.m00 * s.m03 + this.m01 * s.m13 + this.m02 * s.m23 + this.m03 * s.m33;
		
		
		float m10 = this.m10 * s.m00 + this.m11 * s.m10 + this.m12 * s.m20 + this.m13 * s.m30;
		
		float m11 = this.m10 * s.m01 + this.m11 * s.m11 + this.m12 * s.m21 + this.m13 * s.m31;
			
		float m12 = this.m10 * s.m02 + this.m11 * s.m12 + this.m12 * s.m22 + this.m13 * s.m32;
		
		float m13 = this.m10 * s.m03 + this.m11 * s.m13 + this.m12 * s.m23 + this.m13 * s.m33;
		
		
		float m20 = this.m20 * s.m00 + this.m21 * s.m10 + this.m22 * s.m20 + this.m23 * s.m30;
		
		float m21 = this.m20 * s.m01 + this.m21 * s.m11 + this.m22 * s.m21 + this.m23 * s.m31;
			
		float m22 = this.m20 * s.m02 + this.m21 * s.m12 + this.m22 * s.m22 + this.m23 * s.m32;
		
		float m23 = this.m20 * s.m03 + this.m21 * s.m13 + this.m22 * s.m23 + this.m23 * s.m33;
		
		
		float m30 = this.m30 * s.m00 + this.m31 * s.m10 + this.m32 * s.m20 + this.m33 * s.m30;
		
		float m31 = this.m30 * s.m01 + this.m31 * s.m11 + this.m32 * s.m21 + this.m33 * s.m31;
			
		float m32 = this.m30 * s.m02 + this.m31 * s.m12 + this.m32 * s.m22 + this.m33 * s.m32;
		
		float m33 = this.m30 * s.m03 + this.m31 * s.m13 + this.m32 * s.m23 + this.m33 * s.m33;
				
		this.m00 =m00;	this.m01 =m01;	this.m02 =m02;	this.m03 =m03;
		this.m10 =m10;	this.m11 =m11;	this.m12 =m12;	this.m13 =m13;
		this.m20 =m20;	this.m21 =m21;	this.m22 =m22;	this.m23 =m23;
		this.m30 =m30;	this.m31 =m31;	this.m32 =m32;	this.m33 =m33;
		
		return this;
	}
		
	/**
	 * Stores the matrix in a floatbuffer and returns the buffer.
	 * The values are stored in column major order.
	 * 
	 * @return
	 * 			-floatbuffer containing the matrix values
	 */
	public FloatBuffer getFloatBuffer() {
		FloatBuffer buffer =Buffers.newDirectFloatBuffer(16); //16 entrys for a 4x4 matrix
		//column-major = column after column not row after row so OpenGL can read it properly
		buffer.put(m00); //first column
		buffer.put(m10);
		buffer.put(m20);
		buffer.put(m30);
		buffer.put(m01); //second column
		buffer.put(m11);
		buffer.put(m21);
		buffer.put(m31);
		buffer.put(m02); //third column
		buffer.put(m12);
		buffer.put(m22);
		buffer.put(m32); 
		buffer.put(m03); //fourth column
		buffer.put(m13);
		buffer.put(m23);
		buffer.put(m33);
		buffer.flip();
		return buffer;
	}
	
	
	//inverse functions from lwjgl matrix functions
	//https://github.com/LWJGL/lwjgl/blob/master/src/java/org/lwjgl/util/vector/Matrix4f.java
	
	public float determinant() {
		float f =m00* ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32)- m13 * m22 * m31- m11 * m23 * m32- m12 * m21 * m33);
		f -= m01* ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32)- m13 * m22 * m30- m10 * m23 * m32- m12 * m20 * m33);
		f += m02* ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31)- m13 * m21 * m30- m10 * m23 * m31- m11 * m20 * m33);
		f -= m03* ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31)- m12 * m21 * m30- m10 * m22 * m31- m11 * m20 * m32);
		return f;
	}
	
	private static float determinant3x3(float t00, float t01, float t02,float t10, float t11, float t12,float t20, float t21, float t22){
		return   t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22)+ t02 * (t10 * t21 - t11 * t20);
	}
	
	public Matrix4f invert() {
		float determinant = determinant();

		if (determinant != 0) {
			float determinant_inv = 1f/determinant;

			// first row
			float t00 =  determinant3x3(m11, m12, m13, m21, m22, m23, m31, m32, m33);
			float t01 = -determinant3x3(m10, m12, m13, m20, m22, m23, m30, m32, m33);
			float t02 =  determinant3x3(m10, m11, m13, m20, m21, m23, m30, m31, m33);
			float t03 = -determinant3x3(m10, m11, m12, m20, m21, m22, m30, m31, m32);
			// second row
			float t10 = -determinant3x3(m01, m02, m03, m21, m22, m23, m31, m32, m33);
			float t11 =  determinant3x3(m00, m02, m03, m20, m22, m23, m30, m32, m33);
			float t12 = -determinant3x3(m00, m01, m03, m20, m21, m23, m30, m31, m33);
			float t13 =  determinant3x3(m00, m01, m02, m20, m21, m22, m30, m31, m32);
			// third row
			float t20 =  determinant3x3(m01, m02, m03, m11, m12, m13, m31, m32, m33);
			float t21 = -determinant3x3(m00, m02, m03, m10, m12, m13, m30, m32, m33);
			float t22 =  determinant3x3(m00, m01, m03, m10, m11, m13, m30, m31, m33);
			float t23 = -determinant3x3(m00, m01, m02, m10, m11, m12, m30, m31, m32);
			// fourth row
			float t30 = -determinant3x3(m01, m02, m03, m11, m12, m13, m21, m22, m23);
			float t31 =  determinant3x3(m00, m02, m03, m10, m12, m13, m20, m22, m23);
			float t32 = -determinant3x3(m00, m01, m03, m10, m11, m13, m20, m21, m23);
			float t33 =  determinant3x3(m00, m01, m02, m10, m11, m12, m20, m21, m22);

			// transpose and divide by the determinant
			m00 = t00*determinant_inv;
			m11 = t11*determinant_inv;
			m22 = t22*determinant_inv;
			m33 = t33*determinant_inv;
			m01 = t10*determinant_inv;
			m10 = t01*determinant_inv;
			m20 = t02*determinant_inv;
			m02 = t20*determinant_inv;
			m12 = t21*determinant_inv;
			m21 = t12*determinant_inv;
			m03 = t30*determinant_inv;
			m30 = t03*determinant_inv;
			m13 = t31*determinant_inv;
			m31 = t13*determinant_inv;
			m32 = t23*determinant_inv;
			m23 = t32*determinant_inv;
			return this;
		}
		return null;
			
	}
	
	public String toString() {		
		System.out.print(m00+" | ");	System.out.print(m01+" | ");	System.out.print(m02+" | ");	System.out.println(m03);
		System.out.println("---------------------");
		System.out.print(m10+" | ");	System.out.print(m11+" | ");	System.out.print(m12+" | ");	System.out.println(m13);
		System.out.println("---------------------");
		System.out.print(m20+" | ");	System.out.print(m21+" | ");	System.out.print(m22+" | ");	System.out.println(m23);
		System.out.println("---------------------");
		System.out.print(m30+" | ");	System.out.print(m31+" | ");	System.out.print(m32+" | ");	System.out.println(m33);
		System.out.println("---------------------");		
		return "*********************";
	}

	
	public Vector4f multiply(Vector4f v) {
		float x=m00*v.x+this.m01*v.y+this.m02*v.z+this.m03*v.w;
		float y=m10*v.x+this.m11*v.y+this.m12*v.z+this.m13*v.w;
		float z=m20*v.x+this.m21*v.y+this.m22*v.z+this.m23*v.w;
		float w=m30*v.x+this.m31*v.y+this.m32*v.z+this.m33*v.w;
		return new Vector4f(x, y, z, w);
	}	
	
}
