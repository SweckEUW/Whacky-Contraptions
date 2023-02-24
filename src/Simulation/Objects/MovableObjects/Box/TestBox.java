package Simulation.Objects.MovableObjects.Box;

import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;

public class TestBox extends Box{

	private static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(1f), 16f);
	private static float depth = 50;
	
////////////////////
////Constructors////
////////////////////
	public TestBox(float width, float height, float x, float y) {
		super(width, height, depth, material, (float)Math.random(),(float)Math.random(),(float)Math.random(), x, y);
	}
	
	public TestBox(float size, float x, float y) {
		super(size, material, (float)Math.random(),(float)Math.random(),(float)Math.random(), x, y);
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		
	}
	
}
 