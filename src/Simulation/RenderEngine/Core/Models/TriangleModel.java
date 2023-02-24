package Simulation.RenderEngine.Core.Models;

import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Primitive;

public class TriangleModel extends Model{
	
	private Material material;
	

	public TriangleModel(Primitive primitive, Material material, float r,float g,float b, float x, float y) {
		super(primitive, r,g,b, x, y);
		this.material=material;
	}
	
	public TriangleModel(Primitive primitive, Material material, String texture, float x, float y) {
		super(primitive, texture, x, y);
		this.material=material;
	}

	public TriangleModel(String file, Material material,  float r,float g,float b, float x, float y) {
		super(file, r,g,b, x, y);
		this.material=material;
	}
	
	public TriangleModel(String file,String texture, Material material, float x, float y) {
		super(file,texture, x, y);
		this.material=material;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
}