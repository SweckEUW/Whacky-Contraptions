package Simulation.RenderEngine.Core.Models;

import Simulation.RenderEngine.Primitives.Primitive;


public class LineModel extends Model{

	private float [] vertices;

	public LineModel(Primitive primitive,float r,float g,float b, float x,float y) {
		super(primitive, r, g, b, x, y);
		vertices = primitive.getVertices();
	}
	
	public LineModel(float[] vertices,float r,float g,float b, float x,float y) {
		super(vertices, r, g, b, x, y);
	}
	
	public void changeColor(float r, float g, float b) {
		float[] colors = new float[mesh.getVertices().length];
		for (int i = 0; i < colors.length; i+=3) {
			colors[i] = r;
			colors[i+1] = g;
			colors[i+2] = b;
		}
		mesh.setColorValues(colors);
	}

	public float [] getVertices () {
		return vertices;
	}

	public float getVertex (int index) {
		return vertices [index];
	}
}