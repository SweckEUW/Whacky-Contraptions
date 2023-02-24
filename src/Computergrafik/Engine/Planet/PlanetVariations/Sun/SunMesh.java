package Computergrafik.Engine.Planet.PlanetVariations.Sun;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Planet.Core.PlanetMesh;
import Computergrafik.Engine.Primitives.Plane;

public class SunMesh extends PlanetMesh{

	public SunMesh(float scale) {
		super(new SunNoise(),scale);
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

	protected void calculateColors() {
		colorOffset=0.5f;
		colors=new float[heights.length];
		for (int i = 0; i < heights.length; i+=6) {
			//grey
			if (heights[i]<5) {
				colors[i]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=99f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=71f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=99f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=71f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			if (heights[i]<3) {
				colors[i]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=69f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=69f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (heights[i]<2.5) {
				colors[i]=128/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=128/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}		
			if (heights[i]<2.3) {
				colors[i]=178f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=34f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=34f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=178f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=34f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=34f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (heights[i]<2.2) {
				colors[i]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=140f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=140f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
						
			//green
			if (heights[i]<2.1) {
				colors[i]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=165f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=165f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			if (heights[i]<2f) {
				colors[i]=205f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=92f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=92f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=205f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=92f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=92f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			if (heights[i]<1.9f) {
				colors[i]=139f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=139f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=0/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}					
		}
	}
	
	
}
