package Computergrafik.Engine.Planet.PlanetVariations.StonePlanet;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Primitives.Plane;

public class Cube {
	
	private float[] vertices;
	private int[] indices;
	
	public Cube(int resolution,float scale, float x,float y,float z) {
		constructMesh(resolution, scale, x, y, z);
	}
	
	public void constructMesh(int resolution,float scale, float x,float y,float z) {
		Plane[] faces = new Plane[6];
		faces[0]=new Plane(resolution, new Vector3f(0, 1, 0),false);
		faces[1]=new Plane(resolution, new Vector3f(0, -1, 0),false);
		faces[2]=new Plane(resolution, new Vector3f(1, 0, 0),false);
		faces[3]=new Plane(resolution, new Vector3f(-1, 0, 0),false);
		faces[4]=new Plane(resolution, new Vector3f(0, 0, 1),false);
		faces[5]=new Plane(resolution, new Vector3f(0, 0, -1),false);
			
		//merge indices and vertices of all faces into one array 
		int indexVertices = 0;
		int indexIndices = 0;
		int faceIndex = 0;
		vertices = new float[faces[0].getVertices().length*6];
		indices = new int[faces[0].getVertices().length*6]; 
		for (Plane face : faces) {
			for (int i = 0; i < face.getVertices().length; i+=3) {
				vertices[indexVertices]=face.getVertices()[i]*scale+x;
				vertices[indexVertices+1]=face.getVertices()[i+1]*scale+y;
				vertices[indexVertices+2]=face.getVertices()[i+2]*scale+z;
				indexVertices+=3;
			}
						
			for (int i : face.getIndices()) {
				indices[indexIndices] = i + face.getVertices().length/3*faceIndex;
				indexIndices++;
			}
			faceIndex++;
		}	
	}
	
	public float[] getVertices() {
		return vertices;
	}
	
	public int[] getIndices() {
		return indices;
	}
	
}
