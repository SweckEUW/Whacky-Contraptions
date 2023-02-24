package Simulation.Objects.MovableObjects.Ball;


import Simulation.Collisions.DynamicCollisionContext;
import Simulation.Collisions.Boundings.BoundingCircle;
import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Sphere;
import UI.ObjectTransformer.ObjectTransformer;

public abstract class Ball extends MoveableObject{
	
	protected float radius;
	
////////////////////
////Constructors////
////////////////////
	public Ball(float radius,int resolution, Material material, float r, float g,float b, float x, float y) {
		super(new Sphere(resolution,radius), material, r,g,b, x, y);
		this.radius=radius;	
		collisionContext = new DynamicCollisionContext(this,new BoundingCircle(x, y,radius));
		objectTransformer = new ObjectTransformer(this);
	}
	
	public Ball(float radius,int resolution, Material material, String texture, float x, float y) {
		super(new Sphere(resolution,radius), material,texture, x, y);
		this.radius=radius;
		collisionContext = new DynamicCollisionContext(this,new BoundingCircle(x, y,radius));
		objectTransformer = new ObjectTransformer(this);
	}
	
	
/////////////////////////
////Getters & Setters////
/////////////////////////
	public float getRadius() {
		return radius;
	}
	
}
