package Simulation.RenderEngine.Core.Math;

/**
 * 
 * 4 component Vector
 * @author Simon Weck
 */
public class Vector4f {

	public float x;
	public float y;
	public float z;
	public float w;
	
	/**
	 * creates empty 4 component vector (x,y,z,w = 0)
	 */
	public Vector4f() {	
	}
	
	public Vector4f(float value) {	
		this.x=value;
		this.y=value;
		this.z=value;
		this.w=value;
	}
	
	public Vector4f(float x,float y,float z,float w) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}
	
}
