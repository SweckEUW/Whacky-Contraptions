package Simulation.RenderEngine.Core.Models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Math.Vector3f;

/**
 * parses through an obj file and saving vertices indices and normals into arrays.
 * based on the OBJ parser from the book "computer graphics programming in opengl with java" from V.Scott Gordon and Jhon Clevenger
 * modified by Simon Weck
 *
 */
public class OBJParser {

	//stores data into this object because we cant return the values
	private float[] verticesArray;
	private float[] textureCordsArray;
	private float[] normalsArray;
	private int[] indicesArray;
	
	/**
	 * Parses the file and stores the data out of the obj into this object
	 * @param objFile
	 * 				-obj file path
	 */
	public OBJParser(String objFile) {
		parseOBJFile(objFile);
	}
	
	
	/**
	 * parses through an obj file and saves normals, vertices and indices into class attributes of this class if existing
	 * @param objFile
	 * 		-path of the obj file
	 */
	private void parseOBJFile(String objFile){
		ArrayList<Vector2f> textureCords = new ArrayList<Vector2f>();    
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();			
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();	
		
		String scannedLine=null;
		Scanner sc;
		try	{	
			sc = new Scanner(new File("res/"+objFile+".obj"));
			while (sc.hasNext()){
				scannedLine=sc.nextLine();
				String[] currentLine = scannedLine.split(" ");
				
				if(scannedLine.startsWith("v ")) 							
					vertices.add(new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])));		
				
				if(scannedLine.startsWith("vn ")) 
					normals.add(new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])));	
				
				if(scannedLine.startsWith("vt "))  
					textureCords.add(new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2])));		
				
				if(scannedLine.startsWith("f")) { 
					normalsArray = new float[vertices.size()*3];
					textureCordsArray = new float[vertices.size()*2];
					break;
				}	
			}		
			
			while (sc.hasNext()){
				if(scannedLine.startsWith("f")) { 
					String[] currentLine = scannedLine.split(" ");
					String[] vertex1 = currentLine[1].split("/");
					String[] vertex2 = currentLine[2].split("/");
					String[] vertex3 = currentLine[3].split("/");
					
					process(vertex1, indices, textureCords, normals, textureCordsArray, normalsArray);
					process(vertex2, indices, textureCords, normals, textureCordsArray, normalsArray);
					process(vertex3, indices, textureCords, normals, textureCordsArray, normalsArray);
					
					scannedLine=sc.nextLine();
				}else 
					break;
				
			}
					
			sc.close();	
			
		}catch (IOException e){	
			System.err.println("IOException reading file: " + e); 
		}		
		
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for (Vector3f v : vertices) {
			verticesArray[vertexPointer++] = v.x;
			verticesArray[vertexPointer++] = v.y;
			verticesArray[vertexPointer++] = v.z;
		}
		
		for (int i = 0; i < indices.size(); i++) {
			indicesArray[i]= indices.get(i); 
		}
		
	}

	
	private static void process(String[] vertextData, List<Integer> indices, List<Vector2f> textureCords, List<Vector3f> normals,float[] textureCordsArray,float[] normalsArray) {
		int currentVertexPointer = Integer.parseInt(vertextData[0]) -1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textureCords.get(Integer.parseInt(vertextData[1])-1);
		textureCordsArray[currentVertexPointer*2] = currentTex.x;
		textureCordsArray[currentVertexPointer*2+1] = currentTex.y;
		Vector3f currentNormal = normals.get(Integer.parseInt(vertextData[2])-1);
		normalsArray[currentVertexPointer*3] = currentNormal.x;
		normalsArray[currentVertexPointer*3+1] = currentNormal.y;
		normalsArray[currentVertexPointer*3+2] = currentNormal.z;
	}

	public float[] getVertices() {
		return verticesArray;
	}

	public float[] getTextures() {
		return textureCordsArray;
	}

	public float[] getNormals() {
		return normalsArray;
	}

	public int[] getIndices() {
		return indicesArray;
	}
	
	
}
