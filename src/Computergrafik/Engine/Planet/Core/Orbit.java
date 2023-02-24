package Computergrafik.Engine.Planet.Core;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Mesh;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Shaders.PlanetShader.PlanetOrbitShader.PlanetOrbitShader;

/**
 * An orbit is a circle in 3D space that represents the literal orbit of a planet
 * @author Simon Weck
 *
 */
public class Orbit {

	private Model model;
	private float[] vertices;
	private int resolution=70; //resolution of the circle
	private Vector3f axis;
	private Vector3f pointToRotateArround;
	private Vector3f translation;
	private PlanetOrbitShader shader;
	
	/**
	 * Constructs a Planet Orbit (a circle in 3D space that describes the orbit of a planet.
	 * @param axis
	 * 		-Axis to rotate (of the planet)
	 * @param pointtoRotateArround
	 * 		- center of the planet
	 * @param translation
	 * 		- translation of the planet
	 */
	public Orbit(Vector3f axis,Vector3f pointtoRotateArround, Vector3f translation) {
		this.translation=translation;
		this.axis=axis;
		this.pointToRotateArround=pointtoRotateArround;
		model = new Model(new Mesh(new float[0]),translation.x,translation.y,translation.z);
		constructOrbit();	
		model.getMesh().setVertices(vertices, false);
		shader=new PlanetOrbitShader();
	}
	
	/**
	 * constructs a circle based on the resolution attribute. 
	 * The circle is conctructed out of a line and should be rendered as GL_LINE_STRIP
	 */
	private void constructOrbit() {
		float angle;
		vertices=new float[(resolution+1)*3];
		int verticesIndex = 0;
		for (int i = 0; i < resolution+1; i++) {
			angle=(float)Math.toDegrees(i/(float)resolution*(float)Math.PI*2);
			model.addRotationArroundPoint(pointToRotateArround, axis, angle);
			model.getModelMatrix().changeToModelMatrix(model);	
			
			vertices[verticesIndex]=model.getModelMatrix().m03-translation.x;
			vertices[verticesIndex+1]=model.getModelMatrix().m13-translation.y;
			vertices[verticesIndex+2]=model.getModelMatrix().m23-translation.z;
			verticesIndex+=3;
		}
	}


	public Model getModel() {
		return model;
	}
	
	public PlanetOrbitShader getOrbitShader() {
		return shader;
	}
	
}
