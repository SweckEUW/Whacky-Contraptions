package Computergrafik.Engine;

import Computergrafik.Engine.Core.Models.Mesh;
import Computergrafik.Engine.Core.Models.Model;

/**
 * A skybox is a cube with 6 sides where each side has a texture. The textures of the sides translate smoothly to one another to simulate the look of a Sky.
 * This Skyboxes switches randomly between 2 sets of textures that simulate a universe with stars and galaxys.
 * @author Simon Weck
 *
 */
public class SkyBox {
	
	private Model[] sides;
	private float scale=1000; //scale = farplane distance
	// random rotation of the skybox
	private float rotationXinc = (float)Math.random()*0.01f;
	private float rotationYinc = (float)Math.random()*0.01f;
	private float rotationZinc = (float)Math.random()*0.01f;
	
	/**
	 * creates a 6 sided textured cube and uploads it to the gpu. 
	 * Each side has its own model because each model has 1 of 6 textures to simulate the universe.
	 */
	public SkyBox() {
			
		float[] textureCoordinates = new float[]{
				0,0,
				0,1,
				1,1,
				1,0
		};
		float[] textureCoordinates2 = new float[]{
				1,0,
				1,1,
				0,1,
				0,0
		};
		float[] textureCoordinates3 = new float[]{
				0,1,
				0,0,
				1,0,
				1,1						
		};
		int[] indices= new int[] {
				0,2,1,
				0,3,2
		};

		int[] indices2= new int[] {
				1,2,0,
				2,3,0
		};
		float[] verticesFront= new float[] {
				-0.5f*scale,+0.5f*scale,scale/2,
				-0.5f*scale,-0.5f*scale,scale/2,
				+0.5f*scale,-0.5f*scale,scale/2,
				+0.5f*scale,+0.5f*scale,scale/2
		};
		float[] verticesBack= new float[] {
				-0.5f*scale,+0.5f*scale,-scale/2,
				-0.5f*scale,-0.5f*scale,-scale/2,
				+0.5f*scale,-0.5f*scale,-scale/2,
				+0.5f*scale,+0.5f*scale,-scale/2
		};
		float[] verticesTop= new float[] {
				-0.5f*scale,scale/2,-0.5f*scale,
				-0.5f*scale,scale/2,+0.5f*scale,
				+0.5f*scale,scale/2,+0.5f*scale,
				+0.5f*scale,scale/2,-0.5f*scale
		};
		float[] verticesBottom= new float[] {
				-0.5f*scale,-scale/2,-0.5f*scale,
				-0.5f*scale,-scale/2,+0.5f*scale,
				+0.5f*scale,-scale/2,+0.5f*scale,
				+0.5f*scale,-scale/2,-0.5f*scale
		};
		float[] verticesLeft= new float[] {
				-scale/2,+0.5f*scale,+0.5f*scale,
				-scale/2,-0.5f*scale,+0.5f*scale,
				-scale/2,-0.5f*scale,-0.5f*scale,
				-scale/2,+0.5f*scale,-0.5f*scale
		};
		float[] verticesRight= new float[] {
				scale/2,+0.5f*scale,+0.5f*scale,
				scale/2,-0.5f*scale,+0.5f*scale,
				scale/2,-0.5f*scale,-0.5f*scale,
				scale/2,+0.5f*scale,-0.5f*scale
		};
	
		//random set of textures
		String folder;
		double random = Math.random()*2;
		if (random>1) 
			folder="SkyBox2";
		else 
			folder="SkyBox1";
			
		sides=new Model[6];
		sides[0]=new Model(new Mesh(indices, verticesFront, textureCoordinates,"res/SkyBoxes/"+folder+"/front.png"));
		sides[1]=new Model(new Mesh(indices2, verticesBack, textureCoordinates2,"res/SkyBoxes/"+folder+"/back.png"));			
		sides[2]=new Model(new Mesh(indices, verticesRight, textureCoordinates,"res/SkyBoxes/"+folder+"/right.png"));
		sides[3]=new Model(new Mesh(indices2, verticesLeft, textureCoordinates2,"res/SkyBoxes/"+folder+"/left.png"));
		sides[4]=new Model(new Mesh(indices, verticesTop, textureCoordinates,"res/SkyBoxes/"+folder+"/bottom.png"));
		sides[5]=new Model(new Mesh(indices2, verticesBottom, textureCoordinates3,"res/SkyBoxes/"+folder+"/top.png"));
			
	}

	public Model[] getSides() {
		return sides;
	}
	
	public void update() {
		for (Model model : sides) {
			model.increaseRotation(rotationXinc,rotationYinc, rotationZinc);
		}
	}
}
