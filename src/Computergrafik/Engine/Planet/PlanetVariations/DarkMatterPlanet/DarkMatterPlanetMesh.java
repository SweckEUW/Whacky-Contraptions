package Computergrafik.Engine.Planet.PlanetVariations.DarkMatterPlanet;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Planet.Core.PlanetMesh;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.EarthNoise;
import Computergrafik.Engine.Primitives.Plane;

public class DarkMatterPlanetMesh extends PlanetMesh{
	
	
	public DarkMatterPlanetMesh(float scale) {
		super(new EarthNoise(),scale);
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

	@Override
	protected void calculateHeights() {		
		heights=new float[baseVertices.length];
		vertices=new float[baseVertices.length];
		for (int j = 0; j < baseVertices.length; j+=3) {
			vertices[j]=baseVertices[j];
			vertices[j+1]=baseVertices[j+1];
			vertices[j+2]=baseVertices[j+2];
			heights[j]=1;
			heights[j+1]=1;
			heights[j+2]=1;		
		}
		
		calculateColors();
	}
	
	protected void calculateColors() {
		colors=new float[heights.length];
		for (int i = 0; i < heights.length; i+=6) {
			float randomNumber = (float)Math.random()*5;
			if (randomNumber<1) {
				colors[i]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			if (randomNumber<2) {
				colors[i]=10f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=10f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=10f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=10f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=10f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=10f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (randomNumber<3) {
				colors[i]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=20f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}		
			if (randomNumber<4) {
				colors[i]=30f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=30f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=30f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=30f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=30f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=30f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (randomNumber<5) {
				colors[i]=40f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=40f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=40f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=40f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=40f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=40f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
		}
	}
	
	
}
