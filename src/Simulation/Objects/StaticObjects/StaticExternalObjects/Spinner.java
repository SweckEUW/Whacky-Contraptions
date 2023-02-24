package Simulation.Objects.StaticObjects.StaticExternalObjects;

import Simulation.SimulationControler;
import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;

public class Spinner extends StaticExternalObject{

	private static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(1f), 4f);
	
	private float speed = 30f;
	
	public Spinner(float x, float y) {
		super("spinner","spinner","spinner.jpg", material, x, y);
		setScale(0.4f);
	}

	@Override
	public void update() {
		increaseRotation(speed * SimulationControler.getUpdateTimeInSeconds());
	}

	@Override
	public void onCollision() {
		
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return speed;
	}

}
