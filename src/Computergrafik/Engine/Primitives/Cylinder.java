package Computergrafik.Engine.Primitives;

import Computergrafik.Engine.Core.Models.Mesh;

public class Cylinder extends Primitive {

    private float radiusBot;
    private float radiusTop;
    private float height;
    private int indexPointer;
    private int vertexPointer;
    
	public Cylinder(int resolution,float radiusBot,float radiusTop,float height) {
		super(resolution);
		this.radiusBot=radiusBot;
		this.radiusTop=radiusTop;
		this.height=height;
		
		constructMesh();
				
		colors=new float[vertices.length];
		for (int i = 0; i < colors.length; i++) 
			colors[i]=1;	
		
		mesh=new Mesh(vertices,colors,indices);
	}
	
	@Override
	protected void constructMesh() {
		indexPointer=0;
		vertexPointer=0;
		vertices = new float[(resolution+1)*6];
		indices = new int[resolution*resolution*6];
		if (height==0) {
			createCircelTop();
			createCircelBot();	
		}else {
			createCircelTop();
			createCircelBot();			
			createCoat();
		}	
	}
	
	private void createCircelTop() {		
		float yValue=height/2;
		
		vertices[vertexPointer]=0;
		vertices[vertexPointer+1]=yValue;
		vertices[vertexPointer+2]=0;
		vertexPointer+=3;
		
		for (int i = 1; i < resolution+1; i++) {
			vertices[vertexPointer]=(float)Math.sin((float)((float)(i)/(float)resolution+1)*(float)2*(float)Math.PI)*radiusTop;
			vertices[vertexPointer+1]=yValue;
			vertices[vertexPointer+2]=(float)Math.cos((float)((float)(i)/(float)resolution+1)*(float)2*(float)Math.PI)*radiusTop;
			vertexPointer+=3;
			if (i==resolution) {
				indices[indexPointer]=i;
				indices[indexPointer+1]=1;
				indices[indexPointer+2]=0;
			}else {
				indices[indexPointer]=i;
				indices[indexPointer+1]=i+1;
				indices[indexPointer+2]=0;
			}
			indexPointer+=3;
		}
	}
	
	private void createCircelBot() {	
		float yValue=-height/2;
		
		vertices[vertexPointer]=0;
		vertices[vertexPointer+1]=yValue;
		vertices[vertexPointer+2]=0;
		vertexPointer+=3;
		
		for (int i = 1; i < resolution+1; i++) {
			vertices[vertexPointer]=(float)Math.sin((float)((float)(i)/(float)resolution+1)*(float)2*(float)Math.PI)*radiusBot;
			vertices[vertexPointer+1]=yValue;
			vertices[vertexPointer+2]=(float)Math.cos((float)((float)(i)/(float)resolution+1)*(float)2*(float)Math.PI)*radiusBot;
			vertexPointer+=3;
			if (i==resolution) {
				indices[indexPointer]=(resolution+1);
				indices[indexPointer+1]=1+(resolution+1);
				indices[indexPointer+2]=i+(resolution+1);
			}else {
				indices[indexPointer]=(resolution+1);
				indices[indexPointer+1]=i+1+(resolution+1);
				indices[indexPointer+2]=i+(resolution+1);
			}
			indexPointer+=3;
		}
	}
	
	private void createCoat() {
		for (int i = 0; i < resolution; i++) {
			if (i==resolution-1) {
				indices[indexPointer]=i+1;
				indices[indexPointer+1]=i+(resolution+1)+1;
				indices[indexPointer+2]=1;
				
				indices[indexPointer+3]=1;
				indices[indexPointer+4]=i+(resolution+1)+1;
				indices[indexPointer+5]=i+3;
			}else {
				indices[indexPointer]=i+1;
				indices[indexPointer+1]=i+(resolution+1)+1;
				indices[indexPointer+2]=i+2;
				
				indices[indexPointer+3]=i+(resolution+1)+1;
				indices[indexPointer+4]=i+(resolution+1)+2;
				indices[indexPointer+5]=i+2;
			}			
			indexPointer+=6;
		}
	}
	
	public void update(int resolution,float radiusTop, float radiusBot,float height,float r,float g,float b) {
		if (this.resolution!=resolution) 
			setResolution(resolution);
		
		if(this.radiusBot!=radiusBot)
			setRadiusBot(radiusBot);
		
		if(this.radiusTop!=radiusTop)
			setRadiusTop(radiusTop);
		
		if (this.height!=height) 
			setHeight(height);
		
		if (colors[0]!=r || colors[1]!=g || colors[2]!=b) 
			setColors(r,g,b);
	}

	public float getRadiusBot() {
		return radiusBot;
	}

	public void setRadiusBot(float radiusBot) {
		this.radiusBot = radiusBot;
		generate();
	}

	public float getRadiusTop() {
		return radiusTop;
	}

	public void setRadiusTop(float radiusTop) {
		this.radiusTop = radiusTop;
		generate();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		generate();
	}
	
	public void setColors(float r,float g,float b) {
		colors=new float[vertices.length];
		for (int i = 0; i < colors.length; i+=3) {
			colors[i]=r;
			colors[i+1]=g;
			colors[i+2]=b;
		}
		mesh.setColorValues(colors);
	}
	
}
