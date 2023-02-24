package Simulation.RenderEngine.Core.Models;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DYNAMIC_DRAW;
import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * 
 * loads mesh data into VAOs and VBOs and stores ids of them them into arrays. (to delete later ?)
 * 
 * Vertex Buffer Object structure:
 * Slot 0: Vertices
 * Slot 1: Normals
 * Slot 2: Colors
 * Slot 3: Texture coordinates
 * 
 * @author Simon Weck
 * 
 */
public class loadToGPU {
	
	private static int[] vaos = new int[10000];
	private static int[] vbos = new int[10000*4]; // 4 times the size because we need 4 vbos to store the data 
	private static int vaoCounter; //needed to store values correctly into the arrays
	private static int vboCounter;
	
	/**
	 * Creates a VAO(Vertex Array Object) and stores mesh data into it.
	 * Stores the position data into the attribute 0 of the VAO.
	 * Stores normals into the attribute 1 of the VAO.
	 * Stores colors into the attribute 2 of the VAO.
	 * Stores texture coordinates into the attribute 3 of the VAO.
	 * 
	 * @param mesh
	 * 			-mesh that gets stored into the VAO
	 */
	public static void loadMeshToGPU(Mesh mesh) { 
		createVAO(); //create VAO
		mesh.setVaoID(vaos[vaoCounter-1]); //set ID of the VAO to access it later if it needs to be updated
			
		//Store data into VAO (Create VBO and store data into the VBO)
		if (mesh.getIndices()!=null) { //checks if the mesh has this data before loading it into the gpu
			connectIndicesWithVBO(mesh.getIndices());
			mesh.setIndexVBOID(vbos[vboCounter-1]); //save ids to access it later
		}
		if (mesh.getVertices()!=null) {
			connectDataWithVAO(mesh.getVertices(),0,3);
			mesh.setVertexVBOID(vbos[vboCounter-1]);	
		}
		if (mesh.getNormals()!=null) {
			connectDataWithVAO(mesh.getNormals(),1,3);
			mesh.setNormalVBOID(vbos[vboCounter-1]);
		}
		if (mesh.getColors()!=null) {
			connectDataWithVAO(mesh.getColors(),2,3);	
			mesh.setColorVBOID(vbos[vboCounter-1]);
		}		
		if (mesh.getTextureCoordinates()!=null) {
			connectDataWithVAO(mesh.getTextureCoordinates(),3,2);	
			mesh.setTextureCoordinatesID(vbos[vboCounter-1]);
		}	
		if (mesh.getTextureFilePath()!=null) {
			loadTexture(mesh.getTextureFilePath());
			mesh.setTextureID(vbos[vboCounter-1]);
		}
	}

	/**
	 * Creates a VAO(Vertex Array Object) and stores mesh data into it.
	 * Stores the position data into the attribute 0 of the VAO.
	 * Stores normals into the attribute 1 of the VAO.
	 * Stores the model matrices into the attributes 2-5 of the Vao (4* 3 float attributes)
	 * Stores colors into the attribute 6 of the VAO.
	 * 
	 * Stores the coresponding VBO ids into the instancedMesh of the model
	 * @param model
	 * 			-InstancedModel that gets stored into the VAO
	 */
	public static void loadinstancedMeshToGPU(InstancedModel model) {
		Mesh mesh = model.getMesh();
		
		createVAO(); 
		
		mesh.setVaoID(vaos[vaoCounter-1]);
		
		connectIndicesWithVBO(mesh.getIndices());
		mesh.setIndexVBOID(vbos[vboCounter-1]); 
		
		connectDataWithVAO(mesh.getVertices(),0,3);
		mesh.setVertexVBOID(vbos[vboCounter-1]);	
		
		connectDataWithVAO(mesh.getNormals(),1,3);
		mesh.setNormalVBOID(vbos[vboCounter-1]);
		
		//create instanced ModelMatrix vbo
		createEmptyVBO(model.getInstances()*16); //float count is 16(16 floats in a matrix) * the instances of instances 
		setUpEmptyVBO(2, 4, 16, 0);
		setUpEmptyVBO(3, 4, 16, 4);
		setUpEmptyVBO(4, 4, 16, 8);
		setUpEmptyVBO(5, 4, 16, 12);
		model.setMatrixVBOID(vbos[vboCounter-1]);
		
		//create instanced Color vbo
		createEmptyVBO(model.getInstances()*3); //3 floats for color (rgb)
		setUpEmptyVBO(6, 3, 0, 0);
		mesh.setColorVBOID(vbos[vboCounter-1]);
		loadToGPU.updateVBO(mesh.getColorVBOID(),mesh.getColors());
	}
	
	/**
	 * Creates a VAO and activates it
	 */
	private static void createVAO() {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glGenVertexArrays(1,vaos,vaoCounter); //creates a VAO(number of VAOs created//array that holds the ID//Offset)
		gl.glBindVertexArray(vaos[vaoCounter]); //activates the VAO so created VBOs will be associated with the VAO
		vaoCounter++; //increase counter because a vao got created
	}
	
	/**
	 * Creates a empty VAO and activates it
	 */
	public static void createEmptyVBO(int floatCount) {
		GL4 gl=(GL4)GLContext.getCurrentGL();		
		gl.glGenBuffers(1, vbos, vboCounter); 
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbos[vboCounter]); 
		FloatBuffer buffer=Buffers.newDirectFloatBuffer(new float[floatCount]);
		gl.glBufferData(GL_ARRAY_BUFFER, floatCount*4,buffer, GL_DYNAMIC_DRAW);
		vboCounter++;
	}
	
	
	/**
	 * Stores indices of the model into the active VAO //where ? no actual slot given
	 * (Creates VBO -> Activates VBO -> Copy indices into a integer buffer -> Copy buffer into the active VBO)
	 * 
	 * @param indices
	 * 			-Index data
	 */
	private static void connectIndicesWithVBO(int[] indices) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glGenBuffers(1, vbos, vboCounter); //creates a VBO(number of VBOs created//array that holds the ID//Offset)
		gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbos[vboCounter]); //activates the VBO to associate Data with it(Type of VBO//ID)
		IntBuffer buffer=Buffers.newDirectIntBuffer(indices); //copy the data into a buffer
		gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer.limit()*4, buffer, GL_DYNAMIC_DRAW); //copy data into the active VBO(Type of the VBO// size in bytes of the buffer(Float=4 bytes) //buffer that should be loaded into the VBO//usage of the data)
		vboCounter++;
	}
	
	/**
	 * Stores the data into VBO and stores it into the given slot of the VAO
	 * Creates VBO -> Activates VBO (bind) -> Copy data into a float buffer -> Copy buffer into the active VBO -> activate slot ->Store active VBO into active VAO
	 * 
	 * @param data
	 * 			-data to be stored
	 * @param location
	 * 			-Attribute number of the VAO where the data gets stored
	 * @param vertexLength
	 * 			-length of the uploaded data (3 for color and normals (rgb,xyz), 2 for texture coordinates (uv)
	 */
	public static void connectDataWithVAO(float[] data,int location,int vertexLength) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glGenBuffers(1, vbos, vboCounter); //creates a VBO(number of VBOs created//array that holds the ID//Offset)
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbos[vboCounter]); //activates the VBO to associate Data with it(Type of VBO//array that holds the ID)
		FloatBuffer buffer = Buffers.newDirectFloatBuffer(data); //copy the data into a buffer
		gl.glBufferData(GL_ARRAY_BUFFER, buffer.limit()*4,buffer, GL_STATIC_DRAW); //copy data into the active VBO(Type of the VBO// size in bytes of the buffer(float=4 bytes)  //buffer that should be loaded into the VBO//usage of the data)
		gl.glEnableVertexAttribArray(location); //enable attribute 0
		gl.glVertexAttribPointer(location, vertexLength, GL_FLOAT, false, 0, 0); //sets the Layout of the VBO(location inside a VAO//length of the vertex//Type of data//normalized data(range from 0-1)////offset) //so it can be used in a shader
		vboCounter++;
	}
	
	/**
	 * creates an empty VBO  and prepares it for instance rendering
	 */
	public static void setUpEmptyVBO(int location,int vertexLength,int stride, int offset) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glVertexAttribPointer(location, vertexLength, GL_FLOAT, false, stride*4, offset*4); //*4 to convert float to bytes
		// By default the attribute divisor is 0 which tells OpenGL to update the content of the vertex attribute each iteration of the vertex shader
		// By setting this attribute to 1 we're telling OpenGL that we want to update the content of the vertex attribute when we start to render a new instance.
		gl.glVertexAttribDivisor(location, 1); //instance vbo
		gl.glEnableVertexAttribArray(location);
	}
	
	/**
	 * creates an opengl texture out of the file path and saves the texture id of it.
	 * @param file
	 * 		-path to the texture file
	 */
	public static void loadTexture(String file) {
		Texture texture = null;
		try {
			texture=TextureIO.newTexture(new File(file),false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int textureID=texture.getTextureObject();
	
		vbos[vboCounter]=textureID;
		vboCounter++;	
	}
	
	/**
	 * updates the index VBO of the corresponding mesh with the current index data inside the mesh
	 */
	public static void updateIndexVBO(Mesh mesh) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glBindVertexArray(mesh.getVaoID());
		gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndexVBOID());
		IntBuffer buffer=Buffers.newDirectIntBuffer(mesh.getIndices());
		gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer.limit()*4, buffer, GL_STATIC_DRAW);
	}
	
	/**
	 * updates a VBO with new data and uploads it to the gpu 
	 * @param vboID
	 * 		-id of the vbo to be updated
	 * @param data
	 * 		-new data that overwrites the old data
	 */
	public static void updateVBO(int vboID,float[] data) { 
		GL4 gl=(GL4)GLContext.getCurrentGL();
		FloatBuffer buffer=Buffers.newDirectFloatBuffer(data);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vboID);
		gl.glBufferData(GL_ARRAY_BUFFER, buffer.limit()*4, buffer, GL_DYNAMIC_DRAW);
	}
		
	/**
	 * Deletes all the VAOs and VBOs 
	 */
	public static void cleanUp() {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glDeleteBuffers(vbos.length, vbos,0);
		gl.glDeleteVertexArrays(vaos.length, vaos, 0);
		vaoCounter=0;
		vboCounter=0;
		vaos = new int[10000];
		vbos = new int[10000*4];
	}

	public static int getLatestID() {		
		return vbos[vboCounter-1];
	}

}
