package Computergrafik.Engine.Core.Shaders.Core;

import static com.jogamp.opengl.GL2ES2.GL_COMPILE_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_LINK_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;
import static com.jogamp.opengl.GL3ES3.GL_GEOMETRY_SHADER;
import static com.jogamp.opengl.GL3ES3.GL_TESS_CONTROL_SHADER;
import static com.jogamp.opengl.GL3ES3.GL_TESS_EVALUATION_SHADER;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import Computergrafik.Engine.Core.CatchErrors.CatchGLSLErrors;
import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;

/**
 * 
 * Reads a glsl file and creates vertex,fragment,geometry and tesselation shaders if available in the file.
 * 1.create empty programm
 * 2.Read code from .glsl file and save it into the corresponding String array.
 * 3.Create shader out of the String array.
 * 4.compile shader
 * 5.link shaders to the programm
 * 
 * .glsl file:
 * All shaders should be located in one file. Different shaders are separated with  "#Type" (shader type e.g. "Vertex Shader" or "Fragment Shader").
 * For example "#Type Vertex Shader" indicates that the next lines contain the vertex Shader.
 * 
 * @author Simon Weck
 */
public abstract class ShaderProgram {
	
	//IDs for the shader
	private int programID;
	private int vertexShaderID; 
	private int tesselationControlShaderID;
	private int tesselationEvaluationShaderID;
	private int geometryShaderID;
	private int fragmentShaderID;
	
	//String Arrays that contains the GLSL code
	private String[] vertextSource;	
	private String[] tesselationControlSource;
	private String[] tesselationEvaluationSource;
	private String[] geometrySource;
	private String[] fragmentSource; 

	/**
	 * Reads a shaderfile and creates a OpenGL program containing the shaders that are specified by the file.
	 * All shaders should be located in one file. Different shaders are separated with  "#Type" (shader type e.g. "Vertex Shader" or "Fragment Shader").
	 * For example "#Type Vertex Shader" indicates that the next lines contain the vertex Shader.
	 * @param shaderFile
	 * 				-File path of the shaders
	 */
	protected ShaderProgram(String shaderFile){	
		GL4 gl = (GL4) GLContext.getCurrentGL();
		programID = gl.glCreateProgram(); //creates an empty program object and returns an integer ID for referencing later
		loadShaderFromFile(shaderFile); //Reads GLSL shader code from a file
		if(vertextSource!=null) //create shader if the shader was specified by the GLSL file
			vertexShaderID=createShader(GL_VERTEX_SHADER,vertextSource);
		if (tesselationControlSource!=null) 
			tesselationControlShaderID=createShader(GL_TESS_CONTROL_SHADER,tesselationControlSource);	
		if (tesselationEvaluationSource!=null) 
			tesselationEvaluationShaderID=createShader(GL_TESS_EVALUATION_SHADER,tesselationEvaluationSource);
		if (geometrySource!=null) 
			geometryShaderID=createShader(GL_GEOMETRY_SHADER,geometrySource);
		if(fragmentSource!=null)
			fragmentShaderID=createShader(GL_FRAGMENT_SHADER,fragmentSource);	
		finalizeProgram(programID);
		cleanUp();
		getAllUniformLocations();
		use();
	}
	
	/**
	 * Creates a shader object of the specified type and loads the GLSL code into it.
	 * It also compiles the shader and checks if any errors occurred.
	 * 
	 * @param shaderType
	 * 				-Type of shader to be created
	 * @param shaderSource
	 * 				-GLSL code as String array to load into the shader
	 * @return
	 * 				-Integer ID of the created shader
	 */
	private int createShader(int shaderType,String[] shaderSource){	
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] shaderCompiled = new int[1]; //Array to hold the object parameter for GL_COMPILE_STATUS
		int shaderID = gl.glCreateShader(shaderType); //Creates an empty shader object of the desired shaderType and returns an integer ID for referencing later
		gl.glShaderSource(shaderID, shaderSource.length, shaderSource, null, 0); //loads the GLSL code into the created shader object (shader object in which to store the shader//amount of lines//array of string containing the GLSL code//??/??)
		gl.glCompileShader(shaderID); //compiles the the shader
		//glGetShaderiv - return a parameter from a shader object
		//(reference to the shader//Specifies the object parameter. Accepted symbolic names are GL_SHADER_TYPE, GL_DELETE_STATUS, GL_COMPILE_STATUS, GL_INFO_LOG_LENGTH, GL_SHADER_SOURCE_LENGTH//List to store the object parameter//??)
		//If an error is generated nothing will save into the specified array (shaderCompilted)
		gl.glGetShaderiv(shaderID, GL_COMPILE_STATUS, shaderCompiled, 0);
		if (shaderCompiled[0] != 1){ //GL_COMPILTE_STATUS is an int reference //checks if glGetShaderiv returned a parameter into the array if not -> an error occurred.
			String errorString;
			switch (shaderType) {
				case GL_VERTEX_SHADER: 
					errorString=" Vertex";
					break;
				case GL_TESS_CONTROL_SHADER:
					errorString=" Tess Control";
					break;
				case GL_TESS_EVALUATION_SHADER:
					errorString=" Tess Eval";
					break;
				case GL_GEOMETRY_SHADER:
					errorString=" Geometry";
					break;
				case GL_FRAGMENT_SHADER:
					errorString=" Fragment";
					break;
				default:
					errorString=" unkown";
					break;
			};		
			System.out.println(errorString+"shader compilation error."); //print which shader caused the error
			CatchGLSLErrors.printShaderLog(shaderID); //print log of the shader
			gl.glDeleteShader(shaderID); //compilation of the shader failed. No need to keep it
		}
		gl.glAttachShader(programID, shaderID);  //attaches the compiled shader the program
		return shaderID;
	}
	
	/**
	 * Reads GLSL shader code from a file and loads the code into String Arrays of the specific shader Type.
	 *  
	 * @param shaderFile
	 * 				-Name of the file to be loaded
	 */
	private void loadShaderFromFile(String shaderFile){	
		Vector<String> vertexSourceVector=null;  //vectors to determin the length of the Array (because we dont know how long the GLSL code is yet)
		Vector<String> tesselationControlSourceVector=null; //could also use arraylist
		Vector<String> tesselationEvaluationSourceVector=null;
		Vector<String> geometrySourceVector=null;
		Vector<String> fragmentSourceVector=null;
		String scannedLine;
		Scanner sc;
		int currentShaderType=0; //current shader type that gets read
		try	{
			sc = new Scanner(new File("src/Computergrafik/engine/core/shaders/"+shaderFile+".glsl"));
			while (sc.hasNext()){	//scan through the file	
				scannedLine=sc.nextLine();
				if(scannedLine.startsWith("#Type")){	//checks if a shader is specified 
					//checks which shader is specified
					if(scannedLine.contains("Vertex Shader")) {
						currentShaderType = GL_VERTEX_SHADER;	//changes shader type that gets read
						vertexSourceVector = new Vector<String>(); //creates the vector where the code will get stored
					}
					if(scannedLine.contains("Tessellation Control Shader")) {
						currentShaderType = GL_TESS_CONTROL_SHADER;			
						tesselationControlSourceVector = new Vector<String>();
					}
					if(scannedLine.contains("Tessellation Evaluation Shader")) {
						currentShaderType = GL_TESS_EVALUATION_SHADER;			
						tesselationEvaluationSourceVector = new Vector<String>();
					}
					if(scannedLine.contains("Tessellation Evaluation Shader")) {
						currentShaderType = GL_TESS_EVALUATION_SHADER;			
						tesselationEvaluationSourceVector = new Vector<String>();
					}
					if(scannedLine.contains("Geometry Shader")) {
						currentShaderType = GL_GEOMETRY_SHADER;			
						geometrySourceVector = new Vector<String>();
					}
					if(scannedLine.contains("Fragment Shader")) {
						currentShaderType = GL_FRAGMENT_SHADER;			
						fragmentSourceVector = new Vector<String>();
					}
				}else { //adds the line to the current shader type vector if its not specifying the type of the shader
					switch (currentShaderType) {
						case GL_VERTEX_SHADER:
							vertexSourceVector.addElement(scannedLine);
							break;
						case GL_TESS_CONTROL_SHADER :
							tesselationControlSourceVector.addElement(scannedLine);
							break;
						case GL_TESS_EVALUATION_SHADER:
							tesselationEvaluationSourceVector.addElement(scannedLine);
							break;
						case GL_GEOMETRY_SHADER:
							geometrySourceVector.addElement(scannedLine);
							break;
						case GL_FRAGMENT_SHADER:
							fragmentSourceVector.addElement(scannedLine);
							break;
					};		
				}				
			}
			sc.close();		
			//copy all lists into Arrays so we can hand them to the shader program later
			vertextSource=copyIntoArray(vertexSourceVector);
			fragmentSource=copyIntoArray(fragmentSourceVector);	
			tesselationControlSource=copyIntoArray(tesselationControlSourceVector);	
			tesselationEvaluationSource=copyIntoArray(tesselationEvaluationSourceVector);	
			geometrySource=copyIntoArray(geometrySourceVector);	
			fragmentSource=copyIntoArray(fragmentSourceVector);	
		}catch (IOException e){	
			System.err.println("IOException reading file: " + e); 
			e.printStackTrace();
		}
	}

	/**
	 * Copies a String vector into a String array
	 * 
	 * @param source
	 * 			-String vector that should be copied
	 * @return
	 * 			-Array containing the content of the String vector
	 */
	private String[] copyIntoArray(Vector<String> source) {
		if(source==null) 
			return null; //return null if shader wasnt specified by the GLSL file
		String[] shaderSource = new String[source.size()]; //declaring size of array with length of vector
		for (int i = 0; i < source.size(); i++)
			shaderSource[i] = (String) source.elementAt(i) + "\n"; //copy Strings from the vector into an array 
		return shaderSource;
	}
		
	/**
	 * links all the shaders to one program and validates the program. The method also checks if any errors occurred during linking.
	 * 
	 * @param programID
	 * 				-ID of the program that should be linked
	 */
	private void finalizeProgram(int programID){	
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] linked = new int[1];
		gl.glLinkProgram(programID); //links the program together
		gl.glValidateProgram(programID); //Validates the program
		//glGetProgramiv - return a parameter from a program object
		//(reference to the program//Specifies the object parameter. Accepted symbolic names are GL_SHADER_TYPE, GL_DELETE_STATUS, GL_COMPILE_STATUS, GL_INFO_LOG_LENGTH, GL_SHADER_SOURCE_LENGTH.//List to store the object parameter//??)
		gl.glGetProgramiv(programID, GL_LINK_STATUS, linked, 0);
		if (linked[0] != 1){ //checks if glGetProgramiv returned a parameter into the array if not -> an error occurred.
			System.out.println("linking failed");
			CatchGLSLErrors.printProgramLog(programID); 
			cleanUp(); //linking failed no need to keep the program or the shaders
			gl.glDeleteProgram(programID);
		}
	}
	
	/**
	 *loads the program containing the shaders into the OpenGL pipeline stages(onto the GPU)
	 */
	public void use() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUseProgram(programID);
	}
	
	/**
	 * Detaches all shaders from the program and deletes the the shaders
	 */
	public void cleanUp() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		if (vertextSource!=null) { //checks if the shader is available
			gl.glDetachShader(programID, vertexShaderID);
			gl.glDeleteShader(vertexShaderID);
		}
		if (tesselationControlSource!=null) {
			gl.glDetachShader(programID, tesselationControlShaderID);
			gl.glDeleteShader(tesselationControlShaderID);
		}
		if (tesselationEvaluationSource!=null) {
			gl.glDetachShader(programID, tesselationEvaluationShaderID);
			gl.glDeleteShader(tesselationEvaluationShaderID);
		}
		if (geometrySource!=null) {
			gl.glDetachShader(programID, geometryShaderID);
			gl.glDeleteShader(geometryShaderID);
		}
		if (vertextSource!=null) {
			gl.glDetachShader(programID, fragmentShaderID);
			gl.glDeleteShader(fragmentShaderID);
		}
	}
	
	/**
	 * delete shader programm
	 */
	public void deleteShader() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glDeleteShader(programID);
	}
	
	/**
	 * uploads float as uniform variable to the uniform location
	 * @param location
	 * 		-location of the uniform
	 * @param value
	 * 		-float that will be uploaded
	 */
	protected void uploadFloat(int location,float value) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUniform1f(location, value);
	}
	
	/**
	 * uploads a matrix as uniform variable to the uniform location
	 * @param location
	 * 		-location of the uniform
	 * @param matrix
	 * 		-matrix that will be uploaded
	 */
	protected void uploadMatrix(int location,Matrix4f matrix) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUniformMatrix4fv(location, 1, false, matrix.getFloatBuffer());
	}
	
	/**
	 * uploads a vector as uniform variable to the uniform location
	 * @param location
	 * 		-location of the uniform
	 * @param vector
	 * 		-vector that will be uploaded
	 */
	protected void uploadVector(int location,Vector3f vector) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUniform3f(location, vector.x,vector.y,vector.z);
	}
	
	/**
	 * get the location of a uniform variable
	 * @param uniformName
	 * 		-name of the uniform variable
	 * @return
	 * 		-int location of the variable
	 */
	protected int getUniformLocation (String uniformName) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		return gl.glGetUniformLocation(programID, uniformName);
	}
	
	/**
	 * gets called in the constructor and retrieves all uniform locations that a shader needs
	 */
	protected abstract void getAllUniformLocations();
}
