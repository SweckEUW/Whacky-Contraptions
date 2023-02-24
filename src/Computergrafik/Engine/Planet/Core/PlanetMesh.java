package Computergrafik.Engine.Planet.Core;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Mesh;
import Computergrafik.Engine.Planet.Core.Noise.Noise;

/**
 * Mesh of a planet. contains methods to calculate colors and the main mesh of a planet. This class also contains a mesh so the data will get upload to the gpu.
 * This class is very similar to the mesh class but it contains methods to create the mesh itself based on a noise algorithm.
 * @author Simon Weck
 */
public abstract class PlanetMesh {

	protected float scale;
	protected Mesh mesh;
	protected int resolution=50; //resolution of the mesh
	protected float[] baseVertices;
	protected float[] vertices;
	protected int[] indices;
	protected float[] colors;
	protected float[] heights;
	protected float radius;
	protected Noise noise; //a noise that will deform the mesh in a special way
	protected float colorOffset=0.7f; //random deviation of the color for each triangle of the mesh  (1=no deviation from the original choosen colors, 0=high deviation from the original colors)
	
	/**
	 * changes the resolution based on the scale of the planet. After that it calculates the mesh of the planet and its colors. 
	 * It fills the basevertices, vertices, indices, colors and heights arrays with values.
	 * After all calculation is done it creates a mesh object out of the data, so it will get upload to the gpu.
	 * @param noise
	 * 		-noise to deform the mesh 
	 * @param scale
	 * 		-resolution of the planet will be based on this scale
	 */
	public PlanetMesh(Noise noise,float scale) { 
		this.scale=scale;
		this.noise=noise;
		resolution = Math.max((int)(scale*resolution),100);
		constructMesh(); 
		calculateHeights();
		mesh = new Mesh(vertices, colors, indices);
		calculateRadius();
	}

	/**
	 * creates the base mesh of the planet (usually a inflated cube that looks like a sphere)
	 */
	protected abstract void constructMesh();
	
	/**
	 * calculates the color of each vertex based on its height value
	 */
	protected abstract void calculateColors();

	/**
	 * uses the calculateNoiseValue method from the noise class to maniplulate the mesh to make it look spezial.
	 * the method also saves the height values to color them later on. 
	 */
	protected void calculateHeights() {
		heights=new float[baseVertices.length];
		vertices=new float[baseVertices.length];
		for (int j = 0; j < baseVertices.length; j+=3) {
			float noiseValue=noise.calculateNoiseValue(baseVertices[j],baseVertices[j+1],baseVertices[j+2]);			
			vertices[j]=baseVertices[j]*(noiseValue+1);		
			vertices[j+1]=baseVertices[j+1]*(noiseValue+1);	
			vertices[j+2]=baseVertices[j+2]*(noiseValue+1);	
			heights[j]=(noiseValue+1);
			heights[j+1]=(noiseValue+1);
			heights[j+2]=(noiseValue+1);
		}
		calculateColors();
	}
	
	/**
	 * calculates the radius of the planet using the average height of the planets vertices
	 */
	private void calculateRadius() {
		float allHeights=0;
		for (float f : heights) 
			allHeights+=f;
		radius=allHeights/heights.length;
	}
	
	/**
	 * uses the values from the arrays of this class to update the vao during runtime
	 */
	public void updateVAO() {
		mesh.setIndices(indices);
		mesh.setIndexCount(indices.length);
		mesh.setVertices(vertices,true);	
		mesh.setColorValues(colors);		
	}
	
	public void setResolution(int resolution) {
		this.resolution = resolution;
		constructMesh();
		calculateHeights();
		updateVAO();
	}
	
	public void setNoiseRoughness(float noiseRoughness) {
		noise.setNoiseRoughness(noiseRoughness);
		calculateHeights();
		calculateColors();
		updateVAO();
	}
	
	public void setNoiseStrengh(float noiseStrength) {
		noise.setNoiseStrength(noiseStrength);
		calculateHeights();
		calculateColors();
		updateVAO();
	}
	
	public void setNoiseCenter(float noiseOffsetX,float noiseOffsetY,float noiseOffsetZ) {
		noise.setNoiseOffsetX(noiseOffsetX);
		noise.setNoiseOffsetY(noiseOffsetY);
		noise.setNoiseOffsetZ(noiseOffsetZ);
		calculateHeights();
		calculateColors();
		updateVAO();
	}
	
	public void setNoiseLayers(int noiseLayers) {
		noise.setNoiseLayers(noiseLayers);
		calculateHeights();
		calculateColors();
		updateVAO();
	}
	
	public void setNoisePersistance(float noisePeristance) {
		noise.setNoisePeristance(noisePeristance);
		calculateHeights();
		calculateColors();
		updateVAO();
	}
	
	public void setNoiseBaseRoughness(float noiseBaseRoughness) {
		noise.setNoiseBaseRoughness(noiseBaseRoughness);
		calculateHeights();
		calculateColors();
		updateVAO();	
	}
	
	public void setNoiseMinValue(float noiseMinValue) {
		noise.setNoiseMinValue(noiseMinValue);
		calculateHeights();
		calculateColors();
		updateVAO();	
	}
	
	public void setNoise(Noise noise) {
		this.noise=noise;
		calculateHeights();
		calculateColors();
		updateVAO();
	}
	
	public void setColorOffset(float coloroffset) {
		this.colorOffset=coloroffset;
		calculateColors();	
		updateVAO();
	}
	
	public void generateRandom(int res,float size, float noiseStrength,float noiseRoughness, Vector3f noiseOffset,boolean update,int noiseLayers,float noisePeristance,float noiseBaseRoughness,float noiseMinValue,float coloroffset) {
		if (this.resolution!=res) 
			setResolution(res);
		
		if (noise.getNoiseStrength()!=noiseStrength) 
			setNoiseStrengh(noiseStrength);
		
		if (noise.getNoiseRoughness()!=noiseRoughness) 
			setNoiseRoughness(noiseRoughness);
		
		if (noise.getNoiseOffsetX()!=noiseOffset.x || noise.getNoiseOffsetY()!=noiseOffset.y || noise.getNoiseOffsetZ()!=noiseOffset.z) 
			setNoiseCenter(noiseOffset.x,noiseOffset.y,noiseOffset.z);
	
		if (noise.getNoiseLayers()!=noiseLayers) 
			setNoiseLayers(noiseLayers);
			
		if (noise.getNoisePeristance()!=noisePeristance) 
			setNoisePersistance(noisePeristance);
		
		if (noise.getNoiseBaseRoughness()!=noiseBaseRoughness) 
			setNoiseBaseRoughness(noiseBaseRoughness);
		
		if (noise.getNoiseMinValue()!=noiseMinValue) 
			setNoiseMinValue(noiseMinValue);	
		
		if (this.colorOffset!=coloroffset) 
			setColorOffset(coloroffset);
			
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public void generate() {
		noise.randomizeNoiseValues();
		constructMesh();	
		calculateHeights();
		updateVAO();
	}

	public float getRadius() {
		return radius;
	}
	
	public float[] getHeights(){
		return heights;
	}
	
	public int getResolution() {
		return resolution;
	}
	
	
}
