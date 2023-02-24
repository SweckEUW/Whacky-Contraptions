package Simulation.Objects.StaticObjects.StaticExternalObjects;

import Simulation.Collisions.DynamicCollisionContext;
import Simulation.Collisions.Boundings.BoundingPolygon;
import Simulation.Collisions.Boundings.BoundingReader;
import Simulation.Objects.StaticObjects.StaticObject;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Primitive;
import UI.ObjectTransformer.ObjectTransformer;

public abstract class StaticExternalObject extends StaticObject{

	public StaticExternalObject(Primitive primitive, String bounding, Material material,float r,float g,float b, float x, float y) {
		super(primitive, material, r,g,b, x, y);
		createCollsionContext(bounding);
	}

	public StaticExternalObject(String file, String bounding, Material material, float r, float g, float b, float x, float y) {
		super(file, material, r,g,b, x, y);
		createCollsionContext(bounding);
	}
	
	public StaticExternalObject(String file, String bounding, String texture,Material material, float x, float y) {
		super(file,texture, material, x, y);
		createCollsionContext(bounding);
	}


	protected void createCollsionContext(String bounding) {
		collisionContext = new DynamicCollisionContext(this);
		BoundingPolygon[] convexeHulls = BoundingReader.read(bounding, x, y);
		collisionContext.setBoundingPolygons(convexeHulls);
		objectTransformer = new ObjectTransformer(this);
	}
	
}
