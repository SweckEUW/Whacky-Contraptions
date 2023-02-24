package Simulation.Objects.MovableObjects.Ball;

import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import UI.Sounds;

public class TennisBall extends Ball{
	
	public static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(1), 0f);

////////////////////
////Constructors////
////////////////////
	public TennisBall(float x, float y) {
		super(10, 50, material,"TennisBall.png", x, y);
		setCoefficientOfRestitution(0.9f);
		setMass(0.5f);
		models[0].setRotationY(30);
	}

	@Override
	public void onCollision() {
		Sounds.playTennisBallSound();
	}
	
}
 