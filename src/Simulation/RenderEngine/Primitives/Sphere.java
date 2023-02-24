package Simulation.RenderEngine.Primitives;

import static java.lang.StrictMath.PI;

import java.util.ArrayList;

import Simulation.RenderEngine.Core.Config;

public class Sphere extends Primitive {

	private int resolution;
	private float radius;

	public Sphere(int resolution, float radius) {
		this.resolution = resolution;
		this.radius = radius / Config.CANVAS_WIDTH;
		constructMesh();
	}

	public void constructMesh () {
		ArrayList<Float> vertexList = new ArrayList<Float>();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		ArrayList<Float> textureList = new ArrayList<Float>();

		float x, y, z, xy;

		int sectors = resolution;
		int stacks = resolution/2;

		float sectorStep = (float) (2* PI / sectors);
		float stackStep = (float) (PI / stacks);
		float sectorAngle;
		float stackAngle;

		float s;
		float t;

		for (int i = 0; i <= stacks; i++) {
			stackAngle = (float) PI / 2 - i * stackStep;
			xy = (float) (radius * Math.cos(stackAngle));
			z = (float) (radius * Math.sin(stackAngle));

			for (int j = 0; j <= sectors; j++) {
				sectorAngle = j * sectorStep;

				x = (float) (xy * Math.cos (sectorAngle));
				y = (float) (xy * Math.sin (sectorAngle));

				vertexList.add(x);
				vertexList.add(y);
				vertexList.add(z);

				s = (float) j / sectors;
				t = (float) i / stacks;
				textureList.add(s);
				textureList.add(t);
			}
		}
		float[] vertices = new float [vertexList.size()];
		int i = 0;

		for (Float f : vertexList) {
			vertices[i++] = (f != null ? f : Float.NaN);
		}

		this.vertices = vertices;

		float[] textureCoordinates = new float [textureList.size()];
		int m = 0;

		for (Float n : textureList) {
			textureCoordinates[m++] = (n != null ? n : Float.NaN);
		}
		
		this.textureCords = textureCoordinates;

		int k1, k2;
		for(int k = 0; k < stacks; ++k)  {
			k1 = k * (sectors + 1);     // beginning of current stack
			k2 = k1 + sectors + 1;      // beginning of next stack

			for(int o = 0; o < sectors; ++o, ++k1, ++k2) {
				// 2 triangles per sector excluding first and last stacks
				// k1 => k2 => k1+1
				if(k != 0)
				{
					indexList.add(k1);
					indexList.add(k2);
					indexList.add(k1 + 1);
				}

				// k1+1 => k2 => k2+1
				if(k != (stacks-1))
				{
					indexList.add(k1 + 1);
					indexList.add(k2);
					indexList.add(k2 + 1);
				}
			}
		}

		int[] indices = new int[indexList.size()];
		for (int v = 0; v < indices.length; v++)
		{
			indices[v] = indexList.get(v).intValue();
		}

		this.indices = indices;
	}
}
