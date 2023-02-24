package Simulation.RenderEngine.Primitives;

public abstract class Primitive {

	protected float[] vertices;
	protected float[] textureCords;
	protected int[] indices;
	
	protected abstract void constructMesh();

	public float[] getVertices() {
		return vertices;
	}
	
	public float[] getTextureCords() {
		return textureCords;
	}

	public int[] getIndices() {
		return indices;
	}
}
