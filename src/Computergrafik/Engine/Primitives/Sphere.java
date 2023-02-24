package Computergrafik.Engine.Primitives;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Mesh;

public class Sphere extends Primitive {

	
	public Sphere(int resolution) {
		super(resolution);
		constructMesh();
		
		colors=new float[vertices.length];
		for (int i = 0; i < colors.length; i++) 
			colors[i]=1;
		
		mesh = new Mesh(vertices,colors,indices);
	}
		
	public void constructMesh() {
		Plane[] faces = new Plane[6];
		faces[0]=new Plane(resolution, new Vector3f(0, 1, 0),true);
		faces[1]=new Plane(resolution, new Vector3f(0, -1, 0),true);
		faces[2]=new Plane(resolution, new Vector3f(1, 0, 0),true);
		faces[3]=new Plane(resolution, new Vector3f(-1, 0, 0),true);
		faces[4]=new Plane(resolution, new Vector3f(0, 0, 1),true);
		faces[5]=new Plane(resolution, new Vector3f(0, 0, -1),true);
		
		//merge indices and vertices of all faces into one array 
		int indexVertices = 0;
		int indexIndices = 0;
		int faceIndex = 0;
		vertices = new float[faces[0].getVertices().length*6];
		indices = new int[faces[0].getVertices().length*6]; 
		for (Plane face : faces) {
			for (float f : face.getVertices()) {
				vertices[indexVertices]=f;
				indexVertices++;
			}
			for (int i : face.getIndices()) {
				indices[indexIndices] = i + face.getVertices().length/3*faceIndex;
				indexIndices++;
			}
			faceIndex++;
		}	
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
