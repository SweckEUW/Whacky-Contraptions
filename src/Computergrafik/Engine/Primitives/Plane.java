package Computergrafik.Engine.Primitives;


import Computergrafik.Engine.Core.Math.Vector3f;

public class Plane {

	private Vector3f localUp;
	private Vector3f axisA;
	private Vector3f axisB;
	private boolean normalizePoints;
	
	private int resolution;
	private float[] vertices;
	private int[] indices;

		
	public Plane(int resolution, Vector3f localUp,boolean normalizePoints) {
		this.resolution=resolution;
		this.localUp = localUp;			
		axisA = new Vector3f(localUp.y, localUp.z, localUp.x);
		axisB = Vector3f.cross(localUp, axisA);
		this.normalizePoints=normalizePoints;
		constructMesh();	
	}
		
	
	/**
	 * constructs a plane square triangle mesh
	 */
	private void constructMesh() {
		vertices = new float[(resolution*resolution+(resolution-2)*(resolution))*3];
		indices = new int[(resolution-1)*(resolution-1)*6];
		int vertexPointer=0;
		int indexPointer=0;
		int xPointer=0;
		int yPointer=0;
		for (int y = 0; y < resolution; y++) {
			for (int x = 0; x < resolution; x++) {		
				float percentX = (float)x/(resolution-1);
				float percentY = (float)y/(resolution-1);			
				Vector3f vectorA= Vector3f.multiply(axisA, (percentX -0.5f)*2);
				Vector3f vectorB= Vector3f.multiply(axisB, (percentY -0.5f)*2);				
				Vector3f pointOnFace = Vector3f.add(localUp,vectorA,vectorB);				
				if (normalizePoints) 
					pointOnFace.normalize();
				
				vertices[vertexPointer]=pointOnFace.x;	
				vertices[vertexPointer+1]=pointOnFace.y;	
				vertices[vertexPointer+2]=pointOnFace.z;
								
				if (x!=0 && x!=resolution-1 ) {
					vertices[vertexPointer+3]=	pointOnFace.x;	
					vertices[vertexPointer+4]=	pointOnFace.y;	
					vertices[vertexPointer+5]=	pointOnFace.z;
	
					vertexPointer+=3;
				}
				vertexPointer+=3;
					
				int i=xPointer*2-yPointer*2;
				if (y!=resolution-1 && x!=resolution-1) {				
					if (y%2==0) { 
						if (x%2==0) {
							indices[indexPointer]=i;
							indices[indexPointer+1]=i+resolution+resolution-1;
							indices[indexPointer+2]=i+resolution+resolution-2;
								
							indices[indexPointer+3]=i+1;
							indices[indexPointer+4]=i+resolution+resolution-1;
							indices[indexPointer+5]=i;
						}else {
							indices[indexPointer]=i;
							indices[indexPointer+1]=i+1;
							indices[indexPointer+2]=i+resolution+resolution-2;;
								
							indices[indexPointer+3]=i+resolution+resolution-2;
							indices[indexPointer+4]=i+1;
							indices[indexPointer+5]=i+resolution+resolution-1;
						}						
					}else {
						if (x%2==0) {
							indices[indexPointer]=i;
							indices[indexPointer+1]=i+1;
							indices[indexPointer+2]=i+resolution+resolution-2;
								
							indices[indexPointer+3]=i+resolution+resolution-2;
							indices[indexPointer+4]=i+1;
							indices[indexPointer+5]=i+resolution+resolution-1;
						}else {
							indices[indexPointer]=i;
							indices[indexPointer+1]=i+resolution+resolution-1;
							indices[indexPointer+2]=i+resolution+resolution-2;
								
							indices[indexPointer+3]=i;
							indices[indexPointer+4]=i+1;
							indices[indexPointer+5]=i+resolution+resolution-1;
						}
					}					
					indexPointer+=6;
				}	
				xPointer++;
			}
			yPointer++;
		}
	}
	
	
	public void setResolution(int resolution) {
		this.resolution=resolution;
		constructMesh();
	}
		
	public float[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}
		
}
