package Simulation.RenderEngine.Primitives;

import Simulation.RenderEngine.Core.Config;

public class Plane extends Primitive{

	private float width;
	private float height;
	
	private float z = 0;
		
	public Plane(float width,float height) {
		this.height=height/Config.CANVAS_HEIGHT;
		this.width=width/Config.CANVAS_WIDTH;
		constructMesh();	
	}
	
	public Plane(float width,float height,float z) {
		this.height=height/Config.CANVAS_HEIGHT;
		this.width=width/Config.CANVAS_WIDTH;
		this.z = z;
		constructMesh();	
	}
	
	public Plane(float size) {
		this.height=size/Config.CANVAS_HEIGHT;
		this.width=size/Config.CANVAS_WIDTH;
		constructMesh();	
	}
		
	
	protected void constructMesh() {
		float[] vertices = {
			-width/2,height/2,z,
			-width/2,-height/2,z,			
			width/2,height/2,z,				
			width/2,-height/2,z
		};		
		this.vertices=vertices;
			
		int[] indices ={
				0,1,2,
				2,1,3				
		};
		this.indices = indices;
		
		float[] textures= {
				0,1,
				1,1,
				0,0,
				1,0
		};
		this.textureCords = textures;	
	}
	
}
