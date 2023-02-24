package Computergrafik.Engine.Planet.PlanetVariations.EarthLike.CubicEarth;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Planet.Core.PlanetMesh;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.EarthNoise;
import Computergrafik.Engine.Primitives.Plane;

public class CubicEarthMesh extends PlanetMesh{


	
	public CubicEarthMesh(float scale) {
		super(new EarthNoise(),scale);
	}
	
	public void constructMesh() {
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
			//grey
			if (heights[i]<3) {
				colors[i]=0.82f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.82f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.82f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.82f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.82f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.82f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			if (heights[i]<2.3) {
				colors[i]=0.74f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.74f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.74f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.74f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.74f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.74f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (heights[i]<2.1) {
				colors[i]=0.66f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.66f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.66f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.66f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.66f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.66f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}		
			if (heights[i]<1.9) {
				colors[i]=0.5f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.5f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.5f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.5f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.5f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.5f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (heights[i]<1.8) {
				colors[i]=0.41f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.41f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.41f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.41f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.41f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.41f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
						
			//green
			if (heights[i]<1.7) {
				colors[i]=0.18f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.3f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.3f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.18f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.3f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.3f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			if (heights[i]<1.6f) {
				colors[i]=0.23f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.54f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.13f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.23f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.54f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.13f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			if (heights[i]<1.5f) {
				colors[i]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.8f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.8f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}					
			if (heights[i]<1.3f) {
				colors[i]=0.6f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.99f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.1f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.6f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.99f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.1f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			
			//sand
			if (heights[i]<1.25f) {
				colors[i]=0.99f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.99f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.4f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.99f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.99f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.4f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			
			//blue
			if (heights[i]<1.2f) {
				colors[i]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.7f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.7f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			if (heights[i]<1.1f) {
				colors[i]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.4f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.4f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			if (heights[i]<1.01) {
				colors[i]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0.3f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0.3f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}		
		}
	}
	
	
}
