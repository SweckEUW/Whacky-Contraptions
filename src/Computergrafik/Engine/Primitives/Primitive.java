package Computergrafik.Engine.Primitives;

import Computergrafik.Engine.Core.Models.Mesh;

public abstract class Primitive {

	protected Mesh mesh;
	protected int resolution;
	protected float[] vertices;
	protected float[] colors;
	protected int[] indices;
	
	protected Primitive(int resolution) {
		this.resolution=resolution;
	}
	
	protected abstract void constructMesh();

	protected void generate() {
		constructMesh();
		setColors(colors[0], colors[1], colors[2]);
		updateVAO();
	}
	
	protected void updateVAO() {
		mesh.setColorValues(colors);
		mesh.setIndices(indices);
		mesh.setIndexCount(indices.length);	
		mesh.setVertices(vertices,true);			
	}
		
	public void setResolution(int resolution) {
		this.resolution=resolution;
		generate();
	}
	
	public int getResolution() {
		return resolution;
	}

	public Mesh getMesh() {
		return mesh;
	}
	
	public abstract void setColors(float r,float g, float b);
	
}
