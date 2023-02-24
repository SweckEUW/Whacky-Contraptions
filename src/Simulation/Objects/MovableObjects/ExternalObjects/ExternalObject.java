package Simulation.Objects.MovableObjects.ExternalObjects;

import Simulation.Collisions.DynamicCollisionContext;
import Simulation.Collisions.Boundings.BoundingPolygon;
import Simulation.Collisions.Boundings.BoundingReader;
import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import UI.ObjectTransformer.ObjectTransformer;

public abstract class ExternalObject extends MoveableObject{

	public ExternalObject(String[] files,String bounding, Material material, float r, float g, float b, float x, float y) {
		super(files, material, r,g,b, x, y);
		createCollsionContext(bounding);
	}
	
	public ExternalObject(String file, String bounding,Material material, float r, float g, float b, float x, float y) {
		super(file, material, r,g,b, x, y);
		createCollsionContext(bounding);
	}

	protected void createCollsionContext(String bounding) {
		collisionContext = new DynamicCollisionContext(this);
		BoundingPolygon[] convexeHulls = BoundingReader.read(bounding, x, y);
		collisionContext.setBoundingPolygons(convexeHulls);
		objectTransformer = new ObjectTransformer(this);
	}

}
