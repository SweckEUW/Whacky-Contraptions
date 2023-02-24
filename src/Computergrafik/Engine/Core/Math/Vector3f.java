package Computergrafik.Engine.Core.Math;

/**
 * 
 * 3 component Vector
 * @author Simon Weck
 */
public class Vector3f {

	public float x;
	public float y;
	public float z;
	
	/**
	 * creates empty 3 component vector (x,y,z = 0)
	 */
	public Vector3f() {
	}
	
	/**
	 * creates a vector where all components have the same value
	 * @param value
	 */
	public Vector3f(float value) {
		x=value;
		y=value;
		z=value;
	}

	/**
	 * creates a vector with given coodinates
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3f(float x,float y,float z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	/**
	 * normalizes this vector (changes its length to 1)
	 */
	public Vector3f normalize() {
		float magnitude = (float)Math.sqrt(x*x+y*y+z*z); //Euclidean distance (magnitude=length of the vector)
		x/=magnitude;
		y/=magnitude;
		z/=magnitude;
		return this;
	}
	
	/**
	 * normalizes a vector (changes its length to 1)
	 */
	public static Vector3f normalize(Vector3f v) {
		float magnitude = (float)Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z); //Euclidean distance (magnitude=length of the vector)
		Vector3f v2 = new Vector3f();
		v2.x=v.x/magnitude;
		v2.y=v.y/magnitude;
		v2.z=v.z/magnitude;
		return v2;
	}
	
	/**
	 * returns Euclidean distance of the vector
	 * @return
	 */
	public float length() {
		return (float)Math.sqrt(x*x+y*y+z*z);
	}
	
	/**
	 * Dot product (Skalarprodukt)
	 * Multiplies 2 vectors and returns a scalar. 
	 * 
	 * @param in1
	 * 			-First vector
	 * @param in2
	 * 			-Second vector
	 * @return
	 * 			-Scalar
	 */
	public static float multiply(Vector3f in1,Vector3f in2) {
		return in1.x*in2.x+in1.y*in2.y+in1.z*in2.z;
	}
	
	/**
	 * Scalar multiplication (Skalarmultiplikation)
	 * Multiplies this Vector with a scalar and returns a vector
	 * 
	 * @param in
	 * 			-Vector to be multiplied
	 * @param mult
	 * 			-Scalar that scales the vector
	 * @return
	 * 			-scaled vector
	 */
	public static Vector3f multiply(Vector3f in,float mult) {
		Vector3f out = new Vector3f();
		out.x=in.x*mult;
		out.y=in.y*mult;
		out.z=in.z*mult;
		return out;
	}
	
	/**
	 * Subtracts 1 vector from another
	 * 
	 * @param sub1
	 * 			-Vector 1
	 * @param sub2
	 * 			-Vector 2
	 * @return
	 * 			-newly created Vector as result
	 */
	public static Vector3f subtract(Vector3f sub1,Vector3f sub2) {
		Vector3f out = new Vector3f();
		out.x=sub1.x-sub2.x;
		out.y=sub1.y-sub2.y;
		out.z=sub1.z-sub2.z;
		return out;
	}
	
	/**
	 * Adds 3 vectors together and returns the result into a new vector
	 * 
	 * @param add1
	 * @param add2
	 * @param add3
	 * @return
	 */
	public static Vector3f add(Vector3f add1,Vector3f add2,Vector3f add3) {
		Vector3f out = new Vector3f();
		out.x+=add1.x+add2.x+add3.x;
		out.y+=add1.y+add2.y+add3.y;
		out.z+=add1.z+add2.z+add3.z;
		return out;
	}
	
	/**
	 * Adds 2 vectors together and returns the result into a new vector
	 * @param in
	 * @param add
	 * @return
	 */
	public static Vector3f add(Vector3f in,Vector3f add) {
		in.x+=add.x;
		in.y+=add.y;
		in.z+=add.z;
		return in;
	}
	
	/**
	 * Cross product (Kreuzprodukt)
	 * returns Result into a new vector
	 * 
	 * @param a
	 * 			-Vector a
	 * @param b
	 * 			-Vector b
	 * @return
	 * 			-result
	 */
	public static Vector3f cross(Vector3f a,Vector3f b) {
		Vector3f out = new Vector3f();
		out.x=a.y*b.z-a.z*b.y;
		out.y=a.z*b.x-a.x*b.z;
		out.z=a.x*b.y-a.y*b.x;
		return out;
	}
	
	public String toString() {
		return x+" "+y+" "+z;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Vector3f) 
			if (((Vector3f) o).x==this.x && ((Vector3f) o).y==this.y && ((Vector3f) o).z==this.z) 
				return true;
					
		return false;
	}
}
