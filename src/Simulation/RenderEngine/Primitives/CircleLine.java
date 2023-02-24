package Simulation.RenderEngine.Primitives;

import Simulation.RenderEngine.Core.Config;

public class CircleLine extends Primitive{

	private float radius;
	private int resolution;
	private float z;
	
	public CircleLine(float radius, int resolution, float z) {
		this.resolution = resolution;
		this.radius = radius / Config.CANVAS_WIDTH;
		this.z = z;
		constructMesh();
	}

	public CircleLine(float radius, int resolution) {
		this.resolution = resolution;
		this.radius = radius / Config.CANVAS_WIDTH;
		this.z = Config.BOUNDING_DISTANCE;
		constructMesh();
	}
	
	
	@Override
	protected void constructMesh() {
		vertices = new float[resolution*3];
		
		for (int i = 0; i < vertices.length; i+=3) {
			float angle = (float)i/vertices.length*(float)Math.PI*2;
			vertices[i]=(float)Math.cos(angle)*radius;
			vertices[i+1]=(float)Math.sin(angle)*radius;
			vertices[i+2]=z;
		}

	}
}