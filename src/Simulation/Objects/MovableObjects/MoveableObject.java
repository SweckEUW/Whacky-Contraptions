package Simulation.Objects.MovableObjects;

import Simulation.SimulationControler;
import Simulation.Collisions.DynamicCollisionContext;
import Simulation.Objects.GameObject;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Primitive;
import UI.SideBar.ObjectSpeedElement;

public abstract class MoveableObject extends GameObject{

	protected float velocityX,velocityY; //Geschwindigkeit
	protected float accelerationX,accelerationY; //Beschleunigung
	
	protected float originalAccelerationX,originalAccelerationY;
	
	protected boolean portable = true;
	
	protected ObjectSpeedElement element;
	
////////////////////
////Constructors////
////////////////////
	public MoveableObject(Primitive primitive, Material material,float r, float g,float b,float x, float y) {
		super(primitive, material, r,g,b, x, y);
	}
	
	public MoveableObject(Primitive primitive, Material material,String texture,float x, float y) {
		super(primitive, material, texture, x, y);
	}
	
	public MoveableObject(String[] files, Material material, float r, float g, float b, float x, float y) {
		super(files, material, r,g,b, x, y);
	}
	
	public MoveableObject(String file, Material material, float r, float g, float b, float x, float y) {
		super(file, material, r,g,b, x, y);
	}
	
	
///////////////
////Methods////
///////////////
	public void update() {	
		increaseAcceleration(0, -9.807f); //Gravitation
		
		increaseAcceleration(-velocityX*0.5f , -velocityY*0.5f); //air friction
		
		calculateVelocity();
		calculatePosition();
		
		increaseRotation(-velocityX*SimulationControler.getUpdateTimeInSeconds()*1000/5);
		resetAcceleration();
		
		((DynamicCollisionContext) collisionContext).checkCollisions();
	}

	public void calculatePosition() {
		//  s = s + v * t + 0,5 * a * t*t
		//  velocity     = m/s
		//  acceleration = m/s²
		//  time         = s
		float xNew = velocityX * SimulationControler.getUpdateTimeInSeconds() + 0.5f 
				* accelerationX * (float)Math.pow(SimulationControler.getUpdateTimeInSeconds(),2);
				
		float yNew = velocityY * SimulationControler.getUpdateTimeInSeconds() + 0.5f * 
				accelerationY * (float)Math.pow(SimulationControler.getUpdateTimeInSeconds(),2);
		
		xNew*=100;
		yNew*=100;
		
		x+=xNew;
		y+=yNew;
		
		setX(x);
		setY(y);
	}
	
	public void applyForce(float x,float y) {
		x/=mass;
		y/=mass;
		increaseAcceleration(x, y);
	}
	
	public void calculateVelocity() {
		//  v = v + a * t
		//  acceleration = m/s²
		//  time         = s
		//  velocity     = m/s
		
		velocityX = velocityX + accelerationX * SimulationControler.getUpdateTimeInSeconds();
		velocityY = velocityY + accelerationY * SimulationControler.getUpdateTimeInSeconds();
	
		if (Math.abs(velocityX) <0.01f) 
			setVelocityX(0);
		if (Math.abs(velocityY)  <0.001f) 
			setVelocityY(0);
	}
	
	public void increaseAcceleration(float dx,float dy) {
		this.accelerationX+=dx;
		this.accelerationY+=dy;
	}
	
	public void increaseVelocity(float dx, float dy) {
		this.velocityX +=dx;
		this.velocityY +=dy;
	}
	
	public void resetAcceleration() {
		this.accelerationX=0;
		this.accelerationY=0;
	}
	
	public void resetVelocity() {
		this.velocityX=0;
		this.velocityY=0;
	}
	
	public void reset() {
		super.reset();
		setVelocityX(originalAccelerationX);
		setVelocityY(originalAccelerationY);		
	}
	
/////////////////////////
////Getters & Setters////
/////////////////////////
	public float getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}


	public float getAccelerationX() {
		return accelerationX;
	}

	public void setAccelerationX(float accelerationX) {
		this.accelerationX = accelerationX;
	}

	public float getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(float accelerationY) {
		this.accelerationY = accelerationY;
	}

	public float getOriginalAccelerationX() {
		return originalAccelerationX;
	}

	public void setOriginalAccelerationX(float originalAccelerationX) {
		this.originalAccelerationX = originalAccelerationX;
	}

	public float getOriginalAccelerationY() {
		return originalAccelerationY;
	}

	public void setOriginalAccelerationY(float originalAccelerationY) {
		this.originalAccelerationY = originalAccelerationY;
	}

	public boolean isPortable() {
		return portable;
	}

	public void setPortable(boolean portable) {
		this.portable = portable;
	}

	public void updateElement() {
		element.update(this);
	}

	public void setElement(ObjectSpeedElement element) {
		this.element = element;
	}
	
	
}
