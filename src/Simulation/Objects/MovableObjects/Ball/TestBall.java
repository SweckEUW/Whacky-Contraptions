package Simulation.Objects.MovableObjects.Ball;

import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;

public class TestBall extends Ball{
	
	public static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(0.2f,0.1f,0.1f), 4f);

////////////////////
////Constructors////
////////////////////
	public TestBall(float radius,float x, float y) {
		super(radius, 30, material,(float)Math.random(),(float)Math.random(),(float)Math.random(), x, y);
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		
	}
	
}
 