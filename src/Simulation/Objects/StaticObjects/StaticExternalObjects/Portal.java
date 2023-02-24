package Simulation.Objects.StaticObjects.StaticExternalObjects;
import java.util.Timer;
import java.util.TimerTask;

import Simulation.Collisions.DynamicCollisionContext;
import Simulation.Collisions.Boundings.BoundingRectangle;
import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.Objects.StaticObjects.StaticObject;
import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Plane;
import UI.Sounds;
import UI.ObjectTransformer.ObjectTransformer;

public class Portal extends StaticObject{

	private float offset = 60;
	private float originalOffset = offset;
	private Portal portal;
	
	private static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(0.1f,0.1f,0.1f), 4f);

	public Portal(float x, float y) {
		super(new Plane(100, 100, 0), "portal.png", material, x, y);			
		collisionContext = new DynamicCollisionContext(this,new BoundingRectangle(x, y, 80));
		collisionContext.canCollide(false);
		objectTransformer = new ObjectTransformer(this);
		setRotation((float)Math.random()*360);
		setScale(0.8f);
	}

	@Override
	public void update() {
		models[0].increaseRotation(0, 0, 1);
	
		for (GameObject gameObject : allObjects) 
			if(gameObject instanceof MoveableObject) {
				MoveableObject object = (MoveableObject)gameObject;
					
				if(object.isPortable() && object.getX() <= x+offset && object.getX() >= x-offset && object.getY() <= y+offset && object.getY() >= y-offset) {
					Sounds.playPortalSound();
					
					object.setX(portal.getX());
					object.setY(portal.getY());
					
					object.setPortable(false);
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						public void run() {
							object.setPortable(true);
						   }
					},(int)(300));							
				}						
			}
	}
	
	public void setScale(float scale) {
		super.setScale(scale);
		this.offset= originalOffset * scale;
	}
	
	public void remove() {
		super.remove();
		GameObject.allObjects.remove(portal);
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		
	}

}
