package Computergrafik.Engine.Planet.PlanetVariations.SinusPlanet;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Planet.Core.PlanetMesh;
import Computergrafik.Engine.Primitives.Plane;

public class SinPlanetMesh extends PlanetMesh{
	
	public SinPlanetMesh(float scale) {
		super(new SinNoise(),scale);
	}
	
	public void constructMesh() {
		colorOffset=0.7f;
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
	}

	protected void calculateColors() {
		colors=new float[heights.length];
		for (int i = 0; i < heights.length; i+=6) {
			
			colors[i]=1*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+1]=192f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+2]=203f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+3]=1*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+4]=192f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			colors[i+5]=203f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));	
			
			if (heights[i]<1.5f) {
				colors[i]=176f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=31f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=54f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=176f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=31f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=254f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			
			if (heights[i]<1.4f) {
				colors[i]=1*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=147f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=1*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=247f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			
			if (heights[i]<1.3f) {
				colors[i]=1*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=105f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=180f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=1*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=105f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=280f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			
			if (heights[i]<1.2f) {
				colors[i]=1*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=182f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=193f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=1*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=182f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=293f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			
	
		}
	}
	
	
}
