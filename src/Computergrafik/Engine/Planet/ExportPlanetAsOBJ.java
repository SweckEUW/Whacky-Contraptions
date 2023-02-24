package Computergrafik.Engine.Planet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Computergrafik.Engine.Core.Math.Vector4f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetParts.Water.Water;

public class ExportPlanetAsOBJ {

	private static int partCounter;
	private static int entityCounter;
	private static int planetCounter;
	private static int vertexCount;
	
	public static void export(Planet planet) throws IOException {
		partCounter=0;
		entityCounter=0;
		planetCounter=0;
		vertexCount=0;
		FileOutputStream planetEntitiesFile = new FileOutputStream(System.getProperty("user.home")+ "/Desktop/PlanetEntities.obj");
		FileOutputStream planetFile = new FileOutputStream(System.getProperty("user.home")+ "/Desktop/Planet.obj");
		FileOutputStream planetPartsFile = new FileOutputStream(System.getProperty("user.home")+ "/Desktop/PlanetParts.obj");
		
		planetEntitiesFile.write("#Made by SWeck for Visual Computing 1".getBytes());
		planetEntitiesFile.write("\n".getBytes());
		planetEntitiesFile.write("#Grüße gehen raus an Lehn".getBytes());
		planetEntitiesFile.write("\n".getBytes());
		planetEntitiesFile.write("\n".getBytes());
		planetFile.write("#Made by SWeck for Visual Computing 1".getBytes());
		planetFile.write("\n".getBytes());
		planetFile.write("#Grüße gehen raus an Lehn".getBytes());
		planetFile.write("\n".getBytes());
		planetFile.write("\n".getBytes());
		planetPartsFile.write("#Made by SWeck for Visual Computing 1".getBytes());
		planetPartsFile.write("\n".getBytes());
		planetPartsFile.write("#Grüße gehen raus an Lehn".getBytes());
		planetPartsFile.write("\n".getBytes());
		planetPartsFile.write("\n".getBytes());
		
		for (int i = 0; i < planet.getPlanetEntities().size(); i++) 			
				for (int j = 0; j < planet.getPlanetEntities().get(i).getInstances(); j++) {
					entityCounter++;						
					writeModelIntoFile(planetEntitiesFile, planet.getPlanetEntities().get(i).getInstancedModels(),j);
				}
						
		vertexCount=0;
		for (int i = 0; i < planet.getPlanetParts().size(); i++) 
			for (int j = 0; j < planet.getPlanetParts().get(i).getModels().length; j++) {
				partCounter++;
				planetEntitiesFile.write("\n".getBytes());
				planetPartsFile.write(("g Part"+partCounter).getBytes());
				planetPartsFile.write("\n".getBytes());		
				if (planet.getPlanetParts().get(i)instanceof Water ) 
					writeWaterInToOBJ(planetPartsFile, (Water) planet.getPlanetParts().get(i));
				else
					writeModelIntoFile(planetPartsFile, planet.getPlanetParts().get(i).getModels()[j]);
			}
		
		vertexCount=0;
		planetCounter++;
		planetEntitiesFile.write("\n".getBytes());
		planetFile.write(("g Planet"+planetCounter).getBytes());
		planetFile.write("\n".getBytes());	
		writeModelIntoFile(planetFile, planet.getModel());
		
		planetPartsFile.close();		
		planetEntitiesFile.close();		
		planetFile.close();		
	}
	
	public static void writeModelIntoFile(FileOutputStream out, Model model) throws IOException {		
		ArrayList<String> obj = convertModelToBOJArray(model);
		for (int i = 0; i < obj.size(); i++) {
			out.write(obj.get(i).getBytes());
			out.write("\n".getBytes());
		}
	}
	
	public static ArrayList<String> convertModelToBOJArray(Model model) {
		float[] vertices = model.getMesh().getVertices();
		float [] normals = model.getMesh().getNormals();
		int[] indices = model.getMesh().getIndices();
		
		ArrayList<String> obj = new ArrayList<String>();
		
		//add vertices	
		for (int i = 0; i < vertices.length; i+=3) {
			Vector4f vertexVector = model.getModelMatrix().multiply(new Vector4f(vertices[i],vertices[i+1], vertices[i+2],1));
			String vertex = "v "+vertexVector.x+" "+vertexVector.y+" "+vertexVector.z;
			obj.add(vertex);
		}
		
		//add normals
		for (int i = 0; i < normals.length; i+=3) {
			Vector4f normalVector = model.getModelMatrix().multiply(new Vector4f(normals[i],normals[i+1], normals[i+2],0));
			String normal = "vn "+normalVector.x+" "+normalVector.y+" "+normalVector.z;
			obj.add(normal);
		}
		
		//add faces
		for (int i = 0; i < indices.length; i+=3) {
			String face = "f "+(indices[i]+1+vertexCount)+"//"+(indices[i]+1+vertexCount)+" "+(indices[i+1]+1+vertexCount)+"//"+(indices[i+1]+1+vertexCount)+" "+(indices[i+2]+1+vertexCount)+"//"+(indices[i+2]+1+vertexCount);
			obj.add(face);
		}
		vertexCount+=indices.length;
		return obj;
	}
	
	public static void writeModelIntoFile(FileOutputStream out, InstancedModel[] models,int instance) throws IOException {
		for (int i = 0; i < models.length; i++) {
			out.write("\n".getBytes());
			out.write(("g Entity"+entityCounter+"Model"+(i+1)).getBytes());
			out.write("\n".getBytes());
			ArrayList<String> obj = convertModelToBOJArray(models[i],instance);
			for (int j = 0; j < obj.size(); j++) {
				out.write(obj.get(j).getBytes());
				out.write("\n".getBytes());
			}		
		}		
	}
	
	public static ArrayList<String> convertModelToBOJArray(InstancedModel model,int instance) {
		float[] vertices = new float[model.getInstancedMesh().getVertices().length];
		float[] normals = new float[model.getInstancedMesh().getVertices().length];
		for (int i = 0; i < normals.length; i++) {
			vertices[i] = model.getInstancedMesh().getVertices()[i];
			normals[i] = model.getInstancedMesh().getNormals()[i];
		}
		
		int[] indices = new int[model.getInstancedMesh().getIndices().length];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = model.getInstancedMesh().getIndices()[i];
		}
		
		
		ArrayList<String> obj = new ArrayList<String>();
		//add vertices	
		for (int i = 0; i < vertices.length; i+=3) {
			Vector4f vertexVector = model.getModelMatrix()[instance].multiply(new Vector4f(vertices[i],vertices[i+1], vertices[i+2],1));
			String vertex = "v "+vertexVector.x+" "+vertexVector.y+" "+vertexVector.z;
			obj.add(vertex);
		}
		//add normals
		for (int i = 0; i < normals.length; i+=3) {
			Vector4f normalVector = model.getModelMatrix()[instance].multiply(new Vector4f(normals[i],normals[i+1], normals[i+2],0));
			String normal = "vn "+normalVector.x+" "+normalVector.y+" "+normalVector.z;
			obj.add(normal);
		}
		
		//add faces
		for (int i = 0; i < indices.length; i+=3) {
			String face = "f "+(indices[i]+1+vertexCount)+"//"+(indices[i]+1+vertexCount)+" "+(indices[i+1]+1+vertexCount)+"//"+(indices[i+1]+1+vertexCount)+" "+(indices[i+2]+1+vertexCount)+"//"+(indices[i+2]+1+vertexCount);
			obj.add(face);
		}
		vertexCount+=indices.length;
		return obj;
	}
	
	private static void writeWaterInToOBJ(FileOutputStream out,Water water) throws IOException {

		float[] waveHeights = water.getWaveHeights();
		
		float[] vertices = new float[water.getModels()[0].getMesh().getVertices().length];
		float[] normals = new float[water.getModels()[0].getMesh().getVertices().length];
		for (int i = 0; i < normals.length; i++) {
			vertices[i] = water.getModels()[0].getMesh().getVertices()[i];
			normals[i] = water.getModels()[0].getMesh().getNormals()[i];
		}
		
		int[] indices = new int[water.getModels()[0].getMesh().getIndices().length];
		for (int i = 0; i < indices.length; i++) 
			indices[i] = water.getModels()[0].getMesh().getIndices()[i];
		
		
		
		ArrayList<String> obj = new ArrayList<String>();
		//add vertices	
		for (int i = 0; i < vertices.length/3; i++) {
			Vector4f vertexVector = water.getModels()[0].getModelMatrix().multiply(new Vector4f(vertices[i*3]*waveHeights[i],vertices[i*3+1]*waveHeights[i], vertices[i*3+2]*waveHeights[i],1));
			String vertex = "v "+vertexVector.x+" "+vertexVector.y+" "+vertexVector.z;
			obj.add(vertex);
		}
		//add normals
		for (int i = 0; i < normals.length/3; i++) {
			Vector4f normalVector = water.getModels()[0].getModelMatrix().multiply(new Vector4f(normals[i*3]*waveHeights[i],normals[i*3+1]*waveHeights[i], normals[i*3+2]*waveHeights[i],0));	
			String normal = "vn "+normalVector.x+" "+normalVector.y+" "+normalVector.z;
			obj.add(normal);
		}
		
		//add faces
		for (int i = 0; i < indices.length; i+=3) {
			String face = "f "+(indices[i]+1+vertexCount)+"//"+(indices[i]+1+vertexCount)+" "+(indices[i+1]+1+vertexCount)+"//"+(indices[i+1]+1+vertexCount)+" "+(indices[i+2]+1+vertexCount)+"//"+(indices[i+2]+1+vertexCount);
			obj.add(face);
		}
		vertexCount+=indices.length;
		
		
		for (int i = 0; i < obj.size(); i++) {
			out.write(obj.get(i).getBytes());
			out.write("\n".getBytes());
		}
	}
	
}
