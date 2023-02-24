package Simulation.RenderEngine.Primitives;

import Simulation.RenderEngine.Core.Config;

public class Cube extends Primitive {

	float width;
	float height;
	float depth;
	
	public Cube(float width,float height,float depth) {
		this.width=width/Config.CANVAS_WIDTH;
		this.height=height/Config.CANVAS_HEIGHT;
		this.depth=depth/Config.CANVAS_HEIGHT;
		constructMesh();
	}
	
	public Cube(float size) {
		this.width=size/Config.CANVAS_WIDTH;
		this.height=size/Config.CANVAS_HEIGHT;
		this.depth=size/Config.CANVAS_HEIGHT;
		constructMesh();
	}
		
	protected void constructMesh() {
		float[] vertices = {
			-width/2,height/2,depth/2,
			-width/2,-height/2,depth/2,
			
			width/2,height/2,depth/2,				
			width/2,-height/2,depth/2,

			width/2,-height/2,-depth/2,
			width/2,height/2,-depth/2,	
			
			-width/2,height/2,-depth/2,
			-width/2,-height/2,-depth/2
		};		
		this.vertices=vertices;
		
		int[] indices ={
				//front
				0,1,2,
				2,1,3,
				
				//rechts
				2,3,5,
				3,4,5,
				
				//hinten
				4,7,5,
				7,6,5,
				
				//links
				6,7,1,
				0,6,1,		
				
				//oben
				6,0,2,
				2,5,6,
				
				//unten
				1,7,3,
				3,7,4
		};
		this.indices = indices;
		
		float[] textures= {
				0,0,
				1,0,
				1,1,
				0,1
		};
		this.textureCords = textures;	
	}
	
}