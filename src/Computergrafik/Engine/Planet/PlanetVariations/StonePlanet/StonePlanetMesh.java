package Computergrafik.Engine.Planet.PlanetVariations.StonePlanet;

import java.util.ArrayList;

import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Math.Vector4f;
import Computergrafik.Engine.Planet.Core.PlanetMesh;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.EarthNoise;
import Computergrafik.Engine.Primitives.Plane;

public class StonePlanetMesh extends PlanetMesh{
		
	int planetVerticeLength;
	
	public StonePlanetMesh(float scale) {
		super(new EarthNoise(),scale);
	}
	
	public void constructMesh() {
		Plane[] faces = new Plane[6];
		faces[0]=new Plane(14, new Vector3f(0, 1, 0),true);
		faces[1]=new Plane(14, new Vector3f(0, -1, 0),true);
		faces[2]=new Plane(14, new Vector3f(1, 0, 0),true);
		faces[3]=new Plane(14, new Vector3f(-1, 0, 0),true);
		faces[4]=new Plane(14, new Vector3f(0, 0, 1),true);
		faces[5]=new Plane(14, new Vector3f(0, 0, -1),true);
			
		for (Plane plane : faces) 
			planetVerticeLength+=plane.getVertices().length;
		
		
		//merge indices and vertices of all faces into one array 
		int indexVertices = 0;
		int indexIndices = 0;
		int faceIndex = 0;
		baseVertices = new float[faces[0].getVertices().length*6];
		indices = new int[faces[0].getVertices().length*6]; 
		for (Plane face : faces) {
			for (float f : face.getVertices()) {
				baseVertices[indexVertices]=f;
				indexVertices++;
			}
			for (int i : face.getIndices()) {
				indices[indexIndices] = i + face.getVertices().length/3*faceIndex;
				indexIndices++;
			}
			faceIndex++;
		}	
				
		placeRandomCubes();
	}


	private void placeRandomCubes() {
		ArrayList<Cube> cubes = new ArrayList<Cube>();	
		for (int i = 0; i < resolution*7; i++) {
			float randomScale = (0.01f+((float)Math.random()*(0.2f-0.01f)));
			int randomResolution =(int)(2+(Math.random()*(5-2)));
			Vector3f position = findRandomPointOnSphere();
			cubes.add(new Cube(randomResolution, randomScale, position.x, position.y, position.z));
		}
		
		addRandomRotation(cubes);
		
		mergeIntoOneArray(cubes);
	}

	private void addRandomRotation(ArrayList<Cube> cubes) {
		for (Cube cube : cubes) {
			Matrix4f rotation = new Matrix4f();
			rotation.rotateX((float)Math.random()*360);
			rotation.rotateY((float)Math.random()*360);
			rotation.rotateZ((float)Math.random()*360);
			for (int i = 0; i < cube.getVertices().length; i+=3) {
				Vector4f point = new Vector4f(cube.getVertices()[i],cube.getVertices()[i+1],cube.getVertices()[i+2],1);
				point = rotation.multiply(point);
				cube.getVertices()[i] = point.x;
				cube.getVertices()[i+1] = point.y;
				cube.getVertices()[i+2] = point.z;		
			}
		}
	}

	private void mergeIntoOneArray(ArrayList<Cube> cubes) {
		int originalVerticesLength = baseVertices.length;
		int originalIndicesLength = indices.length;
		
		float[] orignialBaseVertices = baseVertices;
		int[] originalIndices = indices;
		
		int verticesLength = baseVertices.length;
		int indicesLength = indices.length;
		for (Cube cube : cubes) {
			verticesLength+=cube.getVertices().length;
			indicesLength+=cube.getIndices().length;
		}
		
		baseVertices = new float[verticesLength];
		indices = new int[indicesLength];
		
		int indexVertices = originalVerticesLength;
		int indexIndices = originalIndicesLength;
		int allLength=originalVerticesLength/3;
		
		for (int i = 0; i < originalVerticesLength; i++) 
			baseVertices[i]=orignialBaseVertices[i];
		
		for (int i = 0; i < originalIndicesLength; i++) 
			indices[i]=originalIndices[i];
		
		
		for (Cube cube : cubes) {
			for (float f : cube.getVertices()) {
				baseVertices[indexVertices]=f;
				indexVertices++;
			}
			
			for (int i : cube.getIndices()) {
				indices[indexIndices] = i + allLength;
				indexIndices++;
			}
			allLength+=cube.getVertices().length/3;
		}	
	}

	private Vector3f findRandomPointOnSphere() {		
		//get random triangle
		int randomIndex=(int)(Math.random()*indices.length);
		
		if (randomIndex%3==1) 
			randomIndex-=1;
		else if (randomIndex%3==2) 
			randomIndex-=2;
			
		Vector3f vertex1=new Vector3f(baseVertices[indices[randomIndex]*3],baseVertices[indices[randomIndex]*3+1],baseVertices[indices[randomIndex]*3+2]);
		Vector3f vertex2=new Vector3f(baseVertices[indices[randomIndex+1]*3],baseVertices[indices[randomIndex+1]*3+1],baseVertices[indices[randomIndex+1]*3+2]);
		Vector3f vertex3=new Vector3f(baseVertices[indices[randomIndex+2]*3],baseVertices[indices[randomIndex+2]*3+1],baseVertices[indices[randomIndex+2]*3+2]);
					
		float random1=(float)Math.random()*9999;
		float random2=(float)Math.random()*9999;
		float random3=(float)Math.random()*9999;
			
		float randomSum=random1+random2+random3;
		random1/=randomSum;
		random2/=randomSum;
		random3/=randomSum;
			
		vertex1=Vector3f.multiply(vertex1, random1);
		vertex2=Vector3f.multiply(vertex2, random2);
		vertex3=Vector3f.multiply(vertex3, random3);
			
		Vector3f randomPointOnSurface = Vector3f.add(vertex1, vertex2, vertex3);
		
		return randomPointOnSurface;
	}

	
	protected void calculateHeights() {		
		heights=new float[baseVertices.length];
		vertices=new float[baseVertices.length];		
		for (int i = 0; i < baseVertices.length; i+=3) {
			vertices[i]=baseVertices[i];
			vertices[i+1]=baseVertices[i+1];
			vertices[i+2]=baseVertices[i+2];
			heights[i]=(float)Math.random()*2+1;
			heights[i+1]=(float)Math.random()*2+1;
			heights[i+2]=(float)Math.random()*2+1;
		}
		
		calculateColors();
	}
	
	protected void calculateColors() {
		colors=new float[heights.length];
		for (int i = 0; i < heights.length; i+=6) {
			float randomNumber = (float)Math.random()*7;
			if (randomNumber<1) {
				colors[i]=129f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=138f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=148f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=129f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=138f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=148f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			if (randomNumber<2) {
				colors[i]=196f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=211f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=226f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=196f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=211f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=226f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (randomNumber<3) {
				colors[i]=95f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=122f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=100f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=95f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=122f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=100f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}		
			if (randomNumber<4) {
				colors[i]=199f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=226f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=235f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=199f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=226f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=235f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (randomNumber<5) {
				colors[i]=156f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=158f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=161f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=156f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=158f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=161f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
						
			if (randomNumber<6) {
				colors[i]=161f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=159f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=140f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=161f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=159f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=140f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}		
			if (randomNumber<=7) {
				colors[i]=243f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=243f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=239f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=243f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=243f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=239f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}		
		}
	}
	
	
}
