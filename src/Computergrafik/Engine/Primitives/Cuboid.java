package Computergrafik.Engine.Primitives;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Mesh;

public class Cuboid extends Primitive {

	private float[] vertices;
	private float[] normals;
	private int vertexPointer;
	private int indexPointer;
	private int xPointer;
	private int yPointer;
		
	public Cuboid(int resolution) {
		super(resolution);		
		
		constructMesh();
		
		colors=new float[vertices.length];
		for (int i = 0; i < colors.length; i++) 
			colors[i]=1;	
		
		mesh = new Mesh(vertices, indices, normals,colors);
	}
		
	public void constructMesh() {
		vertexPointer=0;
		indexPointer=0;
		xPointer=0;
		yPointer=0;
		vertices = new float[(resolution*resolution+(resolution-2)*(resolution))*3*6];
		normals = new float[(resolution*resolution+(resolution-2)*(resolution))*3*6];
		indices = new int[(resolution-1)*(resolution-1)*6*6];
		constructSide(new Vector3f(0,0,1));	
		constructSide(new Vector3f(0,0,-1));	
		constructSide(new Vector3f(0,1,0));	
		constructSide(new Vector3f(0,-1,0));	
		constructSide(new Vector3f(1,0,0));	
		constructSide(new Vector3f(-1,0,0));
	}
	
	public void constructSide(Vector3f localUp) {
		Vector3f axisA = new Vector3f(localUp.y, localUp.z, localUp.x);
		Vector3f axisB = Vector3f.cross(localUp, axisA);
		
		for (int y = 0; y < resolution; y++) {
			for (int x = 0; x < resolution; x++) {		
				float percentX = (float)x/(resolution-1);
				float percentY = (float)y/(resolution-1);			
				Vector3f vectorA= Vector3f.multiply(axisA, (percentX -0.5f)*2);
				Vector3f vectorB= Vector3f.multiply(axisB, (percentY -0.5f)*2);				
				Vector3f pointOnFace = Vector3f.add(localUp,vectorA,vectorB);				
				
				vertices[vertexPointer]=pointOnFace.x;	
				vertices[vertexPointer+1]=pointOnFace.y;	
				vertices[vertexPointer+2]=pointOnFace.z;
				
				normals[vertexPointer]=localUp.x;
				normals[vertexPointer+1]=localUp.y;
				normals[vertexPointer+2]=localUp.z;
				
				if (x!=0 && x!=resolution-1 ) {
					vertices[vertexPointer+3]=	pointOnFace.x;	
					vertices[vertexPointer+4]=	pointOnFace.y;	
					vertices[vertexPointer+5]=	pointOnFace.z;
					
					normals[vertexPointer+3]=localUp.x;
					normals[vertexPointer+4]=localUp.y;
					normals[vertexPointer+5]=localUp.z;
					
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
							indices[indexPointer+2]=i+resolution+resolution-2;
								
							indices[indexPointer+3]=i+1;
							indices[indexPointer+4]=i+resolution+resolution-1;
							indices[indexPointer+5]=i+resolution+resolution-2;
						}						
					}else {
						if (x%2==0) {
							indices[indexPointer]=i;
							indices[indexPointer+1]=i+1;
							indices[indexPointer+2]=i+resolution+resolution-2;
								
							indices[indexPointer+3]=i+1;
							indices[indexPointer+4]=i+resolution+resolution-1;
							indices[indexPointer+5]=i+resolution+resolution-2;
						}else {
							indices[indexPointer]=i;
							indices[indexPointer+1]=i+resolution+resolution-1;
							indices[indexPointer+2]=i+resolution+resolution-2;
								
							indices[indexPointer+3]=i+1;
							indices[indexPointer+4]=i+resolution+resolution-1;
							indices[indexPointer+5]=i;
						}
					}					
					indexPointer+=6;
				}	
				xPointer++;
			}
			yPointer++;
		}
	}
	
	@Override
	public void updateVAO() {	
		mesh.setIndices(indices);
		mesh.setNormals(normals);
		mesh.setIndexCount(indices.length);	
		mesh.setVertices(vertices,true);
	}
		
	public void update(int resolution,float r,float g,float b) {
		if (this.resolution!=resolution) 
			setResolution(resolution);	
		if (colors[0]!=r || colors[1]!=g || colors[2]!=b) 
			setColors(r,g,b);
		
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
