package Simulation.RenderEngine.Core.Models;

import Simulation.RenderEngine.Core.Math.Vector3f;

/**
 * Represents a raw triangle mesh. The mesh will get uploaded once to the gpu but gets rendered multiple times depending on the instance amount.
 * Each instance shares vertices normals and indices but has different color values. Each instance can have 1 rgb color.
 * @author Simon Weck
 *
 */
public class InstancedMesh {

	private int instances; //amount of times the mesh gets rendered to the screen

	//ids to access VAO and VBOs
	private int vaoID;
	private int vertexVBOID;
	private int indexVBOID;
	private int normalVBOID;
	private int colorVBOID; //one ID for every instance

	//mesh data
	private int indexCount;
	private float[] baseVertices;
	private float[] vertices; //instances share the same vertices, normals and indices
	private int[] indices;
	private float[] normals;
	private float[] colors;

	/**
	 * Creates an instancedMesh out of an OBJ file. The OBJ parser reads the file and creates a triangle mesh with normals,indices and vertices out of it.
	 * The triangle mesh will get uploaded to the gpu.
	 * @param file
	 * 		-OBJ file path
	 * @param instances
	 * 		-ammount of instances to be rendered to the screen.
	 */
	public InstancedMesh(String file,int instances) {
		this.instances=instances;
		colors = new float[3*instances];
		OBJParser parser=new OBJParser(file);
		vertices=parser.getVertices();
		baseVertices=parser.getVertices();
		indices=parser.getIndices();
		indexCount=indices.length;
		normals=parser.getNormals(); 
	}

	/**
	 * Calculates the normals out of vertices and indices and places them in the normals[]
	 */
	public void calculateNormals() {
		normals=new float[vertices.length];

		for (int i = 0; i < indices.length; i+=3) {
			Vector3f v1= new Vector3f(vertices[indices[i]*3], vertices[indices[i]*3+1], vertices[indices[i]*3+2]);
			Vector3f v2= new Vector3f(vertices[indices[i+1]*3], vertices[indices[i+1]*3+1], vertices[indices[i+1]*3+2]);
			Vector3f v3= new Vector3f(vertices[indices[i+2]*3], vertices[indices[i+2]*3+1], vertices[indices[i+2]*3+2]);
			Vector3f v = Vector3f.subtract(v3, v1);
			Vector3f u = Vector3f.subtract(v2, v1);
			Vector3f normal= Vector3f.cross(u, v);

			normals[indices[i]*3]+=normal.x;
			normals[indices[i]*3+1]+=normal.y;
			normals[indices[i]*3+2]+=normal.z;

			normals[indices[i+1]*3]+=normal.x;
			normals[indices[i+1]*3+1]+=normal.y;
			normals[indices[i+1]*3+2]+=normal.z;

			normals[indices[i+2]*3]+=normal.x;
			normals[indices[i+2]*3+1]+=normal.y;
			normals[indices[i+2]*3+2]+=normal.z;
		}
	}

	/**
	 * sets the vertices of this mesh and updates the corresponding VBO
	 * @param vertices
	 * 						-vertices that should be updated
	 * @param calculateNormals
	 * 						-true= calculate normals and updates the corresponding VBO // false= no calculation of the normals
	 */
	public void setVertices(float[] vertices,boolean calculateNormals) {
		this.vertices = vertices;
		if (calculateNormals) {
			calculateNormals();
			loadToGPU.updateVBO(normalVBOID, normals);	 //updates the normal VBO
		}
		loadToGPU.updateVBO(vertexVBOID, vertices);
	}


	/**
	 * Sets normals of this mesh and updates the corresponding VBO
	 */
	public void setNormals(float[] normals) {
		this.normals = normals;
		loadToGPU.updateVBO(normalVBOID, normals);
	}

	public int getVaoID() {
		return vaoID;
	}

	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

	public float[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public float[] getNormals() {
		return normals;
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void setNormalVBOID(int normalVBOID) {
		this.normalVBOID = normalVBOID;
	}

	public void setIndexVBOID(int indexVBOID) {
		this.indexVBOID = indexVBOID;
	}

	public void setVertexVBOID(int vertexVBOID) {
		this.vertexVBOID = vertexVBOID;
	}

	public int getVertexVBOID() {
		return vertexVBOID;
	}

	public int getIndexVBOID() {
		return indexVBOID;
	}

	public int getNormalVBOID() {
		return normalVBOID;
	}

	public void setIndexCount(int indexCount) {
		this.indexCount=indexCount;
	}

	public float[] getColors() {
		return colors;
	}

	/**
	 * updates the color[] and uploads the updated colors to the gpu. (updates the corresponding VBO)
	 * @param colors
	 */
	public void setColors(float[] colors) {
		this.colors=colors;
		loadToGPU.updateVBO(colorVBOID, colors);
	}

	public void setColorVBOID(int colorValueVBOID) {
		this.colorVBOID = colorValueVBOID;
	}

	public int getColorVBOID() {
		return colorVBOID;
	}

	public int getInstances() {
		return instances;
	}

	public float[] getBaseVertices() {
		return baseVertices;
	}

}
