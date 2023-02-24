package Simulation.RenderEngine.Primitives;

public class Cylinder extends Primitive {

    private float radiusBot;
    private float radiusTop;
    private float height;
    private int indexPointer;
    private int vertexPointer;
    private int resolution;
    
	public Cylinder(int resolution,float radiusBot,float radiusTop,float height) {
		this.radiusBot=radiusBot;
		this.radiusTop=radiusTop;
		this.height=height;
		this.resolution=resolution;
		
		constructMesh();
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

}
