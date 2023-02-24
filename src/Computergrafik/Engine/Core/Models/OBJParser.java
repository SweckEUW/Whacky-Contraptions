package Computergrafik.Engine.Core.Models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		ArrayList<Float> textureCordsWrongOrder = new ArrayList<Float>();         //List that stores all data read from the obj file but it is in the wrong order for opengl
		ArrayList<Float> normalsWrongOrder = new ArrayList<Float>();
		
		ArrayList<Float> textureCordsTextureRightOrder = new ArrayList<Float>();  //List where we store the read data from the obj file but this time in the right order 
		ArrayList<Float> normalsRightOrder = new ArrayList<Float>(); 			  //(vertex positions are already in the right order because we sort the other lists in order to match the vertex position list)
		
		ArrayList<Float> vertices = new ArrayList<Float>();
		ArrayList<Integer> indices = new ArrayList<Integer>();	
		
		String scannedLine=null;
		Scanner sc;
		try	{	
			sc = new Scanner(new File("res/"+objFile+".obj"));
			while (sc.hasNext()){	//scan through the file	
				scannedLine=sc.nextLine();
				if(scannedLine.startsWith("v "))    //search for v -> v stands for vertex (3d position)
					//cuts the first 2 characters of the String because that will be v,vt,vn etc.
					//Splits the String everytime " " appears (after every coordinate) the splitting creates an String[] that we can iterate over with the for loop					
					for(String s:(scannedLine.substring(2).split(" "))) 
						vertices.add(Float.valueOf(s)); //extract the Float value of every splitted String inside the array						
				if(scannedLine.startsWith("vt"))  //search for vt -> vt stands for texture coordinates	
					for(String s:(scannedLine.substring(3).split(" "))) 
						textureCordsWrongOrder.add(Float.valueOf(s)); 
				if(scannedLine.startsWith("vn"))  //search for vn -> vn stands for vertex normal
					for(String s:(scannedLine.substring(3).split(" "))) 
						normalsWrongOrder.add(Float.valueOf(s)); 
				//1 f line represents 1 face (1 triangle)
				//1 f line contains 3 groups that each contain 3 values 
				//These 3 groups represent the data that make up a triangle
				//The 3 values that each group contains represent the index of the used value (vertices/texture/normal)
				//This shows us which data this particular triangle is using 
				if(scannedLine.startsWith("f")) { //search for f -> f stands for face
					if (scannedLine.substring(2).split(" ").length==4) { //if length == 4 the obj file works with quads (Maya)
						if(scannedLine.startsWith("f")) { 
							for (int i = 0; i < 2; i++) {
								int fCount=0;
								for (String s : scannedLine.substring(2).split(" ")) { 						
									String vertex=s.split("/")[0]; //splits the part of the group into the 3 values and instantly addresses the 0 value
									int vertexReference=(Integer.valueOf(vertex)-1); //get the integer value of the String (-1 because obj starts with 1 and the arrayList starts with 0)
									if (i==0) {
										if (fCount==0 || fCount==1 || fCount==2) 
											indices.add(vertexReference);										
									}else {
										if (fCount==0 || fCount==2 || fCount==3) {
											indices.add(vertexReference);		
										}
									}
									fCount++;		
									
									String texture=s.split("/")[1];
									if(!texture.equals("") && i==0) { //check if there are no texture cords for this model
										int textureReference=(Integer.valueOf(texture)-1)*2;
										textureCordsTextureRightOrder.add(textureCordsWrongOrder.get(textureReference));  //get the values of the given texture coordinates and put them in the right order
										textureCordsTextureRightOrder.add(textureCordsWrongOrder.get(textureReference)+1); //to match the position data 
									}
									
									String normal=s.split("/")[2];
									if(!normal.equals("")&& i==0) {
										int normalReference=(Integer.valueOf(normal)-1)*3;
										normalsRightOrder.add(normalsWrongOrder.get(normalReference));
										normalsRightOrder.add(normalsWrongOrder.get(normalReference+1));
										normalsRightOrder.add(normalsWrongOrder.get(normalReference+2));	
									}						
								}	
								fCount=0;
							}
										
						}	
					}else {
						for (String s : scannedLine.substring(2).split(" ")) { //separate into the 3 groups
							
							String vertex=s.split("/")[0]; //splits the part of the group into the 3 values and instantly addresses the 0 value
							int vertexReference=(Integer.valueOf(vertex)-1); //get the integer value of the String (-1 because obj starts with 1 and the arrayList starts with 0)						
							indices.add(vertexReference);
							
							String texture=s.split("/")[1];
							if(!texture.equals("")) { //check if there are no texture cords for this model
								int textureReference=(Integer.valueOf(texture)-1)*2;  //get the integer value of the String (-1 because obj starts with 1 and the arrayList starts with 0)	(*2 because 2 texture floats per reference)	
								textureCordsTextureRightOrder.add(textureCordsWrongOrder.get(textureReference));  //get the values of the given texture coordinates and put them in the right order
								textureCordsTextureRightOrder.add(textureCordsWrongOrder.get(textureReference)+1); //to match the position data 
							}
							
							String normal=s.split("/")[2];
							if(!normal.equals("")) {
								int normalReference=(Integer.valueOf(normal)-1)*3;  //get the integer value of the String (-1 because obj starts with 1 and the arrayList starts with 0)	(*3 because 3 normal floats per reference)
								normalsRightOrder.add(normalsWrongOrder.get(normalReference)); //order them into the right order
								normalsRightOrder.add(normalsWrongOrder.get(normalReference+1));
								normalsRightOrder.add(normalsWrongOrder.get(normalReference+2));	
							}						
						}
					}
				}	
			}		
			sc.close();	
		}catch (IOException e){	
			System.err.println("IOException reading file: " + e); 
		}	
		//convert to array to load it into a VAO
		verticesArray=convertToArray(vertices);
		textureCordsArray=convertToArray(textureCordsTextureRightOrder);
		normalsArray=convertToArray(normalsRightOrder);	
		indicesArray= new int[indices.size()]; //convert indices list into array
		for (int i = 0; i < indices.size(); i++) 
			indicesArray[i]=indices.get(i);		
	}
	
	/**
	 * converts an arraylist to an array
	 */
	private float[] convertToArray(List<Float> list) {
		float[] output = new float[list.size()];
		for (int i = 0; i < list.size(); i++) 
			output[i]=list.get(i);	
		return output;
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
