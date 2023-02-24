package Simulation.Objects.MovableObjects.Box;

import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;

public class MovingBox extends Box{

	public static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(0.1f,0.1f,0.1f), 0f);
	
	public MovingBox(float x, float y) {
		super(30, material, "MovingBox.jpg", x, y);
		setCoefficientOfRestitution(0.3f);
	}

	@Override
	public void update() {
		super.update();
		setRotation(0);
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		
	}
}
