package Computergrafik.Engine.Planet.Core.Noise;

/**
 * class that calculates a noise value between 0 and 1 depending on the x,y,z coordinates
 * @author Simon Weck
 *
 */
public abstract class Noise {
	
	//settings to process the noise value
	protected int noiseLayers;
	protected float noisePeristance;
	protected float noiseBaseRoughness;
	protected float noiseStrength;
	protected float noiseRoughness;
	protected float noiseOffsetX;
	protected float noiseOffsetY;
	protected float noiseOffsetZ;
	protected float noiseMinValue;
	
	public Noise() {
		randomizeNoiseValues();
	}
	
	/**
	 * calculates a noise Value base on the simplex Noise algorithm and the class attributes.
	 * The simplex Noise algorithm takes the x,y,z position of a point and calculates a value between [-1,1].
	 */
	public abstract float calculateNoiseValue(float x,float y,float z);

	/**
	 * sets random parameters for the class attributes.
	 * The calculateNoiseValue() Method calculates a value based on the class attributes.
	 */
	public abstract void randomizeNoiseValues();
	
	public void setNoiseStrength(float noiseStrength) {
		this.noiseStrength = noiseStrength;
	}
 
	public void setNoiseRoughness(float noiseRoughness) {
		this.noiseRoughness = noiseRoughness;
	}

	public void setNoiseOffsetX(float noiseOffsetX) {
		this.noiseOffsetX = noiseOffsetX;
	}

	public void setNoiseOffsetY(float noiseOffsetY) {
		this.noiseOffsetY = noiseOffsetY;
	}

	public void setNoiseOffsetZ(float noiseOffsetZ) {
		this.noiseOffsetZ = noiseOffsetZ;
	}

	public float getNoiseStrength() {
		return noiseStrength;
	}

	public float getNoiseRoughness() {
		return noiseRoughness;
	}

	public float getNoiseOffsetX() {
		return noiseOffsetX;
	}

	public float getNoiseOffsetY() {
		return noiseOffsetY;
	}

	public float getNoiseOffsetZ() {
		return noiseOffsetZ;
	}

	public int getNoiseLayers() {
		return noiseLayers;
	}

	public void setNoiseLayers(int noiseLayers) {
		this.noiseLayers = noiseLayers;
	}

	public float getNoisePeristance() {
		return noisePeristance;
	}

	public void setNoisePeristance(float noisePeristance) {
		this.noisePeristance = noisePeristance;
	}

	public float getNoiseBaseRoughness() {
		return noiseBaseRoughness;
	}

	public void setNoiseBaseRoughness(float noiseBaseRoughness) {
		this.noiseBaseRoughness = noiseBaseRoughness;
	}

	public float getNoiseMinValue() {
		return noiseMinValue;
	}

	public void setNoiseMinValue(float noiseMinValue) {
		this.noiseMinValue = noiseMinValue;
	}

}
