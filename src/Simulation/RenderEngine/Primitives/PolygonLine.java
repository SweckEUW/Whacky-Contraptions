package Simulation.RenderEngine.Primitives;

import Simulation.RenderEngine.Core.Config;
import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Math.Vector3f;

public class PolygonLine extends Primitive{

	Vector2f[] points;
	float z;

	public PolygonLine(Vector2f[] points) {
		this.points=points;
		z = Config.BOUNDING_DISTANCE;
		constructMesh();
	}

	public PolygonLine(Vector2f[] points, float z) {
		this.points=points;
		this.z = z;
		constructMesh();
	}
	
	@Override
	protected void constructMesh() {
		vertices = new float[points.length*3];
		for (int i = 0; i < vertices.length; i+=3) {
			vertices[i] = points[i/3].x / Config.CANVAS_WIDTH;
			vertices[i+1] = points[i/3].y / Config.CANVAS_WIDTH;
			vertices[i+2] = z;
		}
	}
}
