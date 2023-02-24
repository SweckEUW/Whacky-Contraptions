package Computergrafik.Engine.Core.Math;

/**
 * 
 * 2 component vector
 * @author Simon Weck
 */
public class Vector2f {
	
	public float x;
	public float y;
	
	public Vector2f(float x,float y) {
		this.x=x;
		this.y=y;
	}

	/**
	 * divides each component through the devisor
	 * @param devisor
	 */
	public void divide(float devisor) {
		x/=devisor;
		y/=devisor;
	}
}
