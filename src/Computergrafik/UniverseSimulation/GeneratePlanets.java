package Computergrafik.UniverseSimulation;

import java.util.ArrayList;

import Bildverarbeitung.dataclasses.PlanetData;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetVariations.CristalPlanet.CristalPlanet;
import Computergrafik.Engine.Planet.PlanetVariations.DarkMatterPlanet.DarkMatterPlanet;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.CubicEarth.CubicEarth;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.Earth.Earth;
import Computergrafik.Engine.Planet.PlanetVariations.SinusPlanet.SinPlanet;
import Computergrafik.Engine.Planet.PlanetVariations.StonePlanet.StonePlanet;
import Computergrafik.Engine.Planet.PlanetVariations.Sun.Sun;

public class GeneratePlanets {

	private static int planetCount=-1;
	
	public static void generatePlanets(ArrayList<PlanetData> data){	
		for (PlanetData planetData : data) 
			createPlanets(planetData,0);				
		planetCount=-1;		
	}
	
	private static void createPlanets(PlanetData data,int parentPlanetIndex) {
		if (data.hasParent()) {
			Vector3f randomAxis = calculateRandomAxis(); 
			Vector3f translation = calculateTranslation(randomAxis,data);
			Vector3f rotationAxis = calculateRotationAxis(randomAxis);
			createPlanet(Planet.getAllPlanets().get(parentPlanetIndex), translation,evaluateScale((float)data.getR()), rotationAxis, data.getTyp());
		}else 
			createPlanet(new Vector3f(0,0,0), evaluateScale((float)data.getR()), data.getTyp());
		
			
		planetCount++;
		
		int  fatherPlanetIndex = planetCount;
		
		//recursive for all children
		for (PlanetData children : data.getChildren()) 
			createPlanets(children,fatherPlanetIndex);	
	}
	
	
	private static Vector3f calculateRotationAxis(Vector3f randomAxis) {
		Vector3f rotationAxis = new Vector3f((float) Math.random(),(float) Math.random(),(float) Math.random());
		rotationAxis.normalize();
		float dot = Vector3f.multiply(rotationAxis, randomAxis);
		Vector3f approximation = new Vector3f((float) Math.random(),(float) Math.random(),(float) Math.random());
		approximation.normalize();
		float approximationDot = Vector3f.multiply(approximation, randomAxis);
		if (approximationDot<dot) {
			rotationAxis=approximation;		
			dot=approximationDot;
		}
		float tmpF = dot;
		Vector3f tmpV = Vector3f.multiply(randomAxis, tmpF);
		rotationAxis = Vector3f.subtract(rotationAxis, tmpV);
		return rotationAxis;
	}

	private static Vector3f calculateTranslation(Vector3f axis,PlanetData data) {
		Vector3f translation = new Vector3f();
		translation.x = -axis.x*(float)data.getDistance()*100;
		translation.y = -axis.y*(float)data.getDistance()*100;
		translation.z = -axis.z*(float)data.getDistance()*100;
		return translation;
	}

	private static Vector3f calculateRandomAxis() {
		float x = (float) Math.random()*100;
		float y = (float) Math.random()*100;
		float z = (float) Math.random()*100;
		Vector3f axis = new Vector3f(x,y,z);
		axis.normalize();
		return axis;
	}

	private static float evaluateScale(float scale) {
		return scale*20;
	}
	
	
	
	private static void createPlanet(Planet planet,Vector3f translation,float scale,Vector3f orbitAxe,int type) {
		switch (type) {
		case 0:
			new DarkMatterPlanet(planet,translation, scale, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 1, 1), 30), orbitAxe);
			break;
		case 1:
			new Sun(planet,translation, scale, new Material(new Vector3f(1, 1, 1), new Vector3f(), new Vector3f(), new Vector3f(), 0), orbitAxe);
			break;
		case 2:
			if (Math.random()>0.5f) 
				new Earth(planet,translation, scale, new Material(new Vector3f(0.1f), new Vector3f(1f), new Vector3f(0), 32), orbitAxe);
			else 
				new CubicEarth(planet,translation, scale, new Material(new Vector3f(0.1f), new Vector3f(1f), new Vector3f(0), 32), orbitAxe);
			break;
		case 3:
			new CristalPlanet(planet,translation, scale, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(0.1f, 1, 0.1f), 50,0.4f), orbitAxe);
			break;
		case 4:
			new StonePlanet(planet,translation, scale, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 1, 1), 0), orbitAxe);
			break;
		case 5:
			new SinPlanet(planet,translation, scale, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 0.5f, 0.7f), 200), orbitAxe);
			break;
		default:
			break;
		}
	}
	
	private static void createPlanet(Vector3f translation,float scale,int type) {
		switch (type) {
		case 0:
			new DarkMatterPlanet(translation, scale, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 1, 1), 30));
			break;
		case 1:
			new Sun(translation, scale, new Material(new Vector3f(1, 1, 1), new Vector3f(), new Vector3f(), new Vector3f(), 0));
			break;
		case 2:
			if (Math.random()>0.5f) 
				new Earth(translation, scale, new Material(new Vector3f(0.1f), new Vector3f(1f), new Vector3f(0), 32));
			else 
				new CubicEarth(translation, scale, new Material(new Vector3f(0.1f), new Vector3f(1f), new Vector3f(0), 32));
			break;
		case 3:
			new CristalPlanet(translation, scale, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(0.1f, 1, 0.1f), 50,0.6f));
			break;
		case 4:
			new StonePlanet(translation, scale, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 1, 1), 0));
			break;
		case 5:
			new SinPlanet(translation, scale, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 0.5f, 0.7f), 200));
			break;
		default:
			break;
		}
	}
}
