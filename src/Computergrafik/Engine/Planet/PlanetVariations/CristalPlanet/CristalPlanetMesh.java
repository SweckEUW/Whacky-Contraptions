package Computergrafik.Engine.Planet.PlanetVariations.CristalPlanet;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Planet.Core.PlanetMesh;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.EarthNoise;
import Computergrafik.Engine.Primitives.Plane;

public class CristalPlanetMesh extends PlanetMesh{
	
	
	public CristalPlanetMesh(float scale) {
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
		
		copyMesh();
	}

	private void copyMesh() {
		int coppyAmmount = 2;
			
		float[] newBaseVertices = new float[baseVertices.length*coppyAmmount];
		for (int i = 0; i < baseVertices.length; i++) 
			for (int j = 0; j < coppyAmmount; j++) 
				newBaseVertices[i+j*baseVertices.length] = baseVertices[i];
			
		
		
		int[] newIndices = new int[indices.length*coppyAmmount];
		for (int i = 0; i < indices.length; i++) {
			for (int j = 0; j < coppyAmmount; j++) {
				newIndices[i+j*indices.length]=indices[i]+j*baseVertices.length/3;
			}
		}
	
		baseVertices = newBaseVertices;
		indices=newIndices;
	}

	@Override
	protected void calculateHeights() {		
		heights=new float[baseVertices.length];
		vertices=new float[baseVertices.length];
		for (int j = 0; j < baseVertices.length/2; j+=3) {
			float noiseValue = (1f+((float)Math.random()*(1.7f-1f)));

			vertices[j]=baseVertices[j]*noiseValue;
			vertices[j+1]=baseVertices[j+1]*noiseValue;
			vertices[j+2]=baseVertices[j+2]*noiseValue;
			heights[j]=noiseValue;
			heights[j+1]=noiseValue;
			heights[j+2]=noiseValue;
			for (int i = 0; i < baseVertices.length/2; i+=3) {
				if ((float)Math.round(baseVertices[i]*1000)/1000 == (float)Math.round(baseVertices[j]*1000)/1000 && (float)Math.round(baseVertices[i+1]*1000)/1000 == (float)Math.round(baseVertices[j+1]*1000)/1000 && (float)Math.round(baseVertices[i+2]*1000)/1000 == (float)Math.round(baseVertices[j+2]*1000)/1000) {
					vertices[i]=baseVertices[i]*noiseValue;
					vertices[i+1]=baseVertices[i+1]*noiseValue;
					vertices[i+2]=baseVertices[i+2]*noiseValue;
					heights[i]=noiseValue;
					heights[i+1]=noiseValue;
					heights[i+2]=noiseValue;
				}
			}		
		}
		
		for (int j = baseVertices.length/2; j < baseVertices.length; j+=3) {
			float noiseValue = (1.6f+((float)Math.random()*(1.9f-1.6f)));

			vertices[j]=baseVertices[j]*noiseValue;
			vertices[j+1]=baseVertices[j+1]*noiseValue;
			vertices[j+2]=baseVertices[j+2]*noiseValue;
			heights[j]=noiseValue;
			heights[j+1]=noiseValue;
			heights[j+2]=noiseValue;
			for (int i =  baseVertices.length/2; i < baseVertices.length; i+=3) {
				if ((float)Math.round(baseVertices[i]*1000)/1000 == (float)Math.round(baseVertices[j]*1000)/1000 && (float)Math.round(baseVertices[i+1]*1000)/1000 == (float)Math.round(baseVertices[j+1]*1000)/1000 && (float)Math.round(baseVertices[i+2]*1000)/1000 == (float)Math.round(baseVertices[j+2]*1000)/1000) {
					vertices[i]=baseVertices[i]*noiseValue;
					vertices[i+1]=baseVertices[i+1]*noiseValue;
					vertices[i+2]=baseVertices[i+2]*noiseValue;
					heights[i]=noiseValue;
					heights[i+1]=noiseValue;
					heights[i+2]=noiseValue;
				}
			}		
		}
			
		calculateColors();
	}
	
	protected void calculateColors() {
		colors=new float[heights.length];
		for (int i = 0; i < heights.length; i+=6) {
			//blue
			if (heights[i]<=1.9f) {
				colors[i]=126f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=83f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=126f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=83f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			if (heights[i]<1.8f) {
				colors[i]=65f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=69f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=232f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=65f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=69f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=232f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (heights[i]<1.7f) {
				colors[i]=71f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=124f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=71f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=124f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}		
			if (heights[i]<1.6f) {
				colors[i]=94f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=70f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=94f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=70f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}			
			if (heights[i]<1.5f) {
				colors[i]=52f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=128f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=128f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=52f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=128f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=128f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
						
			//green
			if (heights[i]<1.4f) {
				colors[i]=181f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=181f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			if (heights[i]<1.3f) {
				colors[i]=105f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=105f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}	
			if (heights[i]<1.2f) {
				colors[i]=83f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=203f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=204f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=83f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=203f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=204f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}					
			if (heights[i]<1.1f) {
				colors[i]=150f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+1]=224f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+3]=150f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+4]=224f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
				colors[i+5]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
			}
			
//			//sand
//			if (heights[i]<1.25f) {
//				colors[i]=94f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+1]=70f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i]=94f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+1]=70f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//			}
//			
//			//blue
//			if (heights[i]<1.2f) {
//				colors[i]=94f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+1]=70f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i]=94f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+1]=70f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+2]=255f/255f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//			}
//			if (heights[i]<1.1f) {
//				colors[i]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+1]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+2]=0.4f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+3]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+4]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+5]=0.4f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//			}
//			if (heights[i]<1.01) {
//				colors[i]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+1]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+2]=0.3f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+3]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+4]=0.01f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//				colors[i+5]=0.3f*(colorOffset+((float)Math.random()*(1f-colorOffset)));
//			}		
		}
	}
	
	
}
