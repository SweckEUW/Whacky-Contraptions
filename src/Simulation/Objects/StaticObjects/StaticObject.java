package Simulation.Objects.StaticObjects;

import Simulation.Objects.GameObject;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Primitive;

public abstract class StaticObject extends GameObject{
	
////////////////////
////Constructors////
////////////////////
	public StaticObject(Primitive primitive, Material material,float r,float g,float b, float x, float y) {
		super(primitive, material, r,g,b, x, y);
		mass = 999999999;
	}
	
	public StaticObject(Primitive primitive, String file,Material material, float x, float y) {
		super(primitive,material,file, x, y);
		mass = 999999999;
	}

	public StaticObject(String file, Material material, float r, float g, float b, float x, float y) {
		super(file, material, r,g,b, x, y);
		mass = 999999999;
	}
	
	public StaticObject(String file, String texture,Material material, float x, float y) {
		super(file,texture, material, x, y);		
		mass = 999999999;
	}
	
}
 