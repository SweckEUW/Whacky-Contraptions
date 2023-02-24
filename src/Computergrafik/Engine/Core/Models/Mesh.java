package Computergrafik.Engine.Core.Models;

import Computergrafik.Engine.Core.Math.Vector3f;

/**
 * 
 * Represents a raw triangle mesh. The Mesh can get uploaded to the gpu.
 * @author Simon Weck
 */
public class Mesh {
	
	//ids to access VAO and VBOs
	private int vaoID;
	private int vertexVBOID;
	private int indexVBOID;
	private int normalVBOID;
	private int colorVBOID;
	private int textureCoordinatesID;
	private int textureID;
	
	//mesh data 
	private int indexCount;
	private float[] colors; //r,g,b values for each vertex 
	private float[] vertices;
	private float[] baseVertices;
	private int[] indices;
	private float[] normals;
	private float[] textureCoordinates;
	private String textureFilePath;
	
	private boolean isLine;

	/**
	 * Constructor with vertices indices and normals
	 */
	public Mesh(float[] vertices, int[] indices, float[] normals) {
		baseVertices=vertices;
		this.vertices=vertices;
		this.indices=indices;
		this.indexCount = indices.length;
		this.normals=normals;
		loadToGPU.loadMeshToGPU(this); //loads the given data into a new VAO
	}
	
	/**
	 * Constructor with vertices indices normals and colors
	 */
	public Mesh(float[] vertices, int[] indices, float[] normals,float[] colors) {
		baseVertices=vertices;
		this.colors=colors;
		this.vertices=vertices;
		this.indices=indices;
		this.indexCount = indices.length;
		this.normals=normals;
		loadToGPU.loadMeshToGPU(this);
	}

	/**
	 * Constructor with vertices and indices
	 * (Normals getting calculated)
	 */
	public Mesh(float[] vertices, int[]indices ) {
		baseVertices=vertices;
		this.indexCount = indices.length;
		this.vertices=vertices;
		this.indices=indices;
		calculateNormals(); //calculates normals out of given vertices and indices
		loadToGPU.loadMeshToGPU(this);
	}
	
	/**
	 * Creates a mesh with only vertices. This will be rendered as a line.
	 * @param vertices
	 */
	public Mesh(float[] vertices) {
		baseVertices=vertices;
		this.vertices=vertices;
		isLine=true;
		loadToGPU.loadMeshToGPU(this);
	}
	
	
	/**
	 * Constructor with vertices colors and indices 
	 * (Normals getting calculated)
	 */
	public Mesh(float[] vertices, float[] colors, int[] indices) {
		baseVertices=vertices;
		this.indexCount = indices.length;
		this.vertices=vertices;
		this.indices=indices;
		this.colors=colors;
		calculateNormals();
		loadToGPU.loadMeshToGPU(this);
	}
	
	/**
	 * creates a mesh with a texture on it.
	 * @param textureCoordinates
	 * 		-array containing the texture coordinate of each vertex
	 * @param textureFilePath
	 * 		-path of the texture file
	 */
	public Mesh(int[]indices, float[] vertices, float[] textureCoordinates,String textureFilePath) {
		baseVertices=vertices;
		this.indexCount = indices.length;
		this.vertices=vertices;
		this.indices=indices;
		this.textureCoordinates=textureCoordinates;
		this.textureFilePath=textureFilePath;
		loadToGPU.loadMeshToGPU(this);
	}
	
	
	/**
	 * Constructs Mesh out of a obj file
	 * @param file
	 * 			-Path to the obj file (inside the res folder)
	 */
	public Mesh(String file) { 
		OBJParser parser=new OBJParser(file);
		vertices=parser.getVertices();
		baseVertices=parser.getVertices();
		indices=parser.getIndices();
		indexCount=indices.length;
		colors=new float[0];
		normals=parser.getNormals(); // normals dont get read properly
		calculateNormals();
		loadToGPU.loadMeshToGPU(this);
	}
	
	/**
	 * creates a mesh out of a obj file with a given rgb color values
	 */
	public Mesh(String file,float[] colors) { 
		OBJParser parser=new OBJParser(file);
		baseVertices=parser.getVertices();
		vertices=parser.getVertices();
		indices=parser.getIndices();
		indexCount=indices.length;
		this.colors=colors;
//		normals=parser.getNormals(); // normals dont get read properly
		calculateNormals();
		loadToGPU.loadMeshToGPU(this);
	}
	
	/**
	 * Calculates the normals out of vertices and indices
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
	 * Sets indices of this mesh and updates the corresponding VBO
	 */
	public void setIndices(int[] indices) {
		this.indices = indices;
		loadToGPU.updateIndexVBO(this);
	}
	
	/**
	 * Sets normals of this mesh and updates the corresponding VBO
	 */
	public void setNormals(float[] normals) {
		this.normals = normals;
		loadToGPU.updateVBO(normalVBOID, normals);
	}
	
	/**
	 * Sets colors of this mesh and updates the corresponding VBO
	 */
	public void setColorValues(float[] colors) {
		this.colors = colors;
		loadToGPU.updateVBO(colorVBOID, colors);
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

	public int getColorVBOID() {
		return colorVBOID;
	}

	public void setColorVBOID(int colorValueVBOID) {
		this.colorVBOID = colorValueVBOID;
	}
	
	public boolean isLine() {
		return isLine;
	}

	public float[] getTextureCoordinates() {
		return textureCoordinates;
	}

	public void setTextureCoordinates(float[] textureCoordinates) {
		this.textureCoordinates = textureCoordinates;
	}

	public void setTextureCoordinatesID(int textureCoordinatesID) {
		this.textureCoordinatesID=textureCoordinatesID;
	}
	
	public int getTextureCoordinatesID() {
		return textureCoordinatesID;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public String getTextureFilePath() {
		return textureFilePath;
	}

	public void setTextureFilePath(String textureFilePath) {
		this.textureFilePath = textureFilePath;
	}

	public float[] getBaseVertices() {
		return baseVertices;
	}
	
}

