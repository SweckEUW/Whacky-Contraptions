package Simulation.RenderEngine.Primitives;

import Simulation.RenderEngine.Core.Config;

public class RectangleLine extends Primitive{

	float width;
	float height;
	float z;
		
	public RectangleLine(float width,float height, float z) {
		this.height=height/Config.CANVAS_HEIGHT;
		this.width=width/Config.CANVAS_WIDTH;
		this.z = z;
		constructMesh();	
	}
	
	public RectangleLine(float size, float z) {
		this.height=size/Config.CANVAS_HEIGHT;
		this.width=size/Config.CANVAS_WIDTH;
		this.z = z;
		constructMesh();	
	}

	public RectangleLine(float size) {
		this.height=size/Config.CANVAS_HEIGHT;
		this.width=size/Config.CANVAS_WIDTH;
		constructMesh();
	}
	
	protected void constructMesh() {
		float[] vertices = {
			width/2,height/2,z,
			-width/2,height/2,z,
			-width/2,-height/2,z,
			width/2,-height/2, z,
		};		
		this.vertices=vertices;
	}
}
