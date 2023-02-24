package Computergrafik.Engine.Planet.Core;

import java.util.ArrayList;

import Computergrafik.Engine.Core.Camera.Camera;
import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.CubicEarth.CubicEarth;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.Earth.Earth;

/**
 * A planet object manages the whole planet. It consists out of the main  planet mesh but also its transition in 3D space.
 * The planet object also has lists to hold various PlanetEntities and PlanetParts. A Planet also has an Orbit that represents its literal orbit in 3D space that can be enabled and disabled.
 * 
 * @author Simon Weck
 *
 */
public abstract class Planet {
		
	//static list that holds all planets that are created during runtime
	protected static ArrayList<Planet> planets = new ArrayList<Planet>();
	
	protected PlanetModel planetModel;	//main model of the planet containting its mesh and the transition of it in 3D space
	protected ArrayList<PlanetPart> planetparts;
	protected ArrayList<PlanetEntity> planetEntities;
	protected Planet planetToRotateArround;	//the planet can rotate around other planets, can also be null if the planet is the center of a universe
	protected ArrayList<Planet> attachedPlanets; //all planets that rotate around this planet are stored in this list
	protected boolean drawOrbit = false; //boolean controling if the orbit is renderd
	protected Orbit orbit;
	protected String name = PlanetNames.getRandomPlanetName(); // random planet name
	
	protected float entityRenderDistance = 3000;
		
	protected boolean isSelected;			//true if the mouse is hovered over the planet
	protected boolean useHighlightColor;	//true if the planet uses the highlight colors Array
	protected boolean useNormalColor=true;	//true if the planet uses its normal color values
	protected float[] hilightColorValues;	//Array holding modified colors of the planet to highlight it when the mouse hovers over it
	protected float[] normalColorValues;	//normal colors of the planet. Needed to switch between highlight colors and normal colors

	protected boolean generateRandom;		 //if true the planet will get generated randomly in the next frame
	
	protected boolean stopUpdate;			//stops the planet from moving and updating PlanetEntities and PlanetParts
	 	
	/**
	 * /**
	 * Creates a Planet out of a mesh and a planet to rotate around
	 * @param mesh
	 * 		-mesh defining the construction of the main planet
	 * @param planet
	 * 		-the planet will rotate around this planet
	 * @param material
	 * 		-material attributes of the main planet mesh
	 * @param translation
	 * 		-translation of the planet
	 * @param scale
	 * 		-scale of the planet (also influences the size of the entities and parts)
	 * @param orbitAxis
	 * 		-rotation axis the planet will rotate around
	 */
	public Planet(PlanetMesh mesh,Planet planet,Vector3f translation, float scale,Material material,Vector3f orbitAxis) {
		planetModel = new PlanetModel(mesh, new Vector3f(planet.getModel().getX(), planet.getModel().getY(), planet.getModel().getZ()),translation, scale,material,orbitAxis);
		this.planetToRotateArround = planet;
		attachedPlanets=new ArrayList<Planet>();	
		planetparts=new ArrayList<PlanetPart>();
		planetEntities = new ArrayList<PlanetEntity>();
		planet.attachPlanet(this);
		orbit= new Orbit(orbitAxis,planetModel.getCenter(),translation);
		setNormalColorValues(); 
		planets.add(this);
	} 
	
	/**
	 * Creates a planet out of a mesh but with no planet to rotate around. this planet is will be the center of a universe
	 * @param mesh
	 * 		-mesh defining the construction of the main planet
	 * @param material
	 * 		-material attributes of the main planet mesh
	 * @param translation
	 * 		-translation of the planet
	 * @param scale
	 * 		-scale of the planet (also influences the size of the entities and parts)
	 * @param orbitAxis
	 * 		-rotation axis the planet will rotate around
	 */
	public Planet(PlanetMesh mesh,Vector3f translation, float scale,Material material) {
		planetModel = new PlanetModel(mesh,translation, scale,material);
		this.planetToRotateArround = null;
		attachedPlanets=new ArrayList<Planet>();	
		planetparts=new ArrayList<PlanetPart>();
		planetEntities = new ArrayList<PlanetEntity>();
		setNormalColorValues();
		planets.add(this);
	}
	
	/**
	 * updates the planet and all objects associated to it.
	 * First it rotates around a planet if there is one and than updates all objects that are connected with it to rotate around the planet as well. 
	 */
	public void update() {	
		if (!stopUpdate) {		
			if (planetToRotateArround!=null) {
				Matrix4f m = planetToRotateArround.getModel().getModelMatrix();
				float newCenterX = m.m03;
				float newCenterY = m.m13;
				float newCenterZ = m.m23;
				
				setCenterX(newCenterX);
				setCenterY(newCenterY);
				setCenterZ(newCenterZ);
							
				//let parts rotate around the planet
				for (PlanetPart part : planetparts) 
					for (int i = 0; i < part.getModels().length; i++) 	
						part.getModels()[i].addRotationArroundPoint(new Vector3f(newCenterX, newCenterY, newCenterZ),planetModel.getOrbitAxis(),planetModel.getOrbitRotation());
						
				//let orbit rotate around the planet
				orbit.getModel().addRotationArroundPoint(new Vector3f(newCenterX, newCenterY, newCenterZ),planetModel.getOrbitAxis(),planetModel.getOrbitRotation());
				
				//let all attached planets rotate around the planet
				updateAttachedPlanets(new Vector3f(newCenterX, newCenterY, newCenterZ),planetModel.getOrbitAxis(),planetModel.getOrbitRotation());	
				
				increaseOrbitRotation();
				addRotationArroundPoint();
			}
			
			//rotate planet around itself
			increaseRotation();
			
			//call update function of the planetparts and entities
			for (PlanetPart part : planetparts) 
				part.update();
			for (PlanetEntity entity : planetEntities) 
				entity.update();
		}
		
		//let entities rotate around the planet 
		if (planetToRotateArround!=null) {
			for (PlanetEntity entity : planetEntities) 
				for (int i = 0; i < entity.getInstancedModels().length; i++) 	
					for (int j = 0; j < entity.getInstances(); j++) 
						entity.getInstancedModels()[i].addRotationArroundPoint(planetModel.getCenter(),planetModel.getOrbitAxis(),planetModel.getOrbitRotation(),j);
		}
		
		//change colors if the planet is selected
		updateColors();
	
		//check if the planet needs to be generated
		if (generateRandom) {
			generateRandom=false;
			generateRandom();
		}	
	}
		
	/**
	 * Updates the color vbo of the PlanetEntities PlanetParts and the main planet mesh, if the user hovers over the planet
	 */
	protected void updateColors() {
		if (isSelected) { //mouse hovered over planet = use highlight colors
			if (!useHighlightColor) {
				useHighlightColor=true;
				useHighlightColor();
				for (int i = 0; i < planetparts.size(); i++) 
					planetparts.get(i).useHighlightColor();
				for (int i = 0; i < planetEntities.size(); i++) 
					planetEntities.get(i).useHighlightColor();
			}
			useNormalColor=false;
		}else { //mouse hovered not over planet = use normal colors
			if (!useNormalColor) {
				useNormalColor=true;
				useNormalColor();
				for (int i = 0; i < planetparts.size(); i++) 
					planetparts.get(i).useNormalColor();
				for (int i = 0; i < planetEntities.size(); i++) 
					planetEntities.get(i).useNormalColor();
			}
			useHighlightColor=false;
		}
	}

	/**
	 * lets all attached planets rotate around the given point with the given axis and rotation (in angles)
	 * @param center
	 * 		-point to rotate around
	 * @param axis
	 * 		-axis to rotate
	 * @param angle
	 * 		-amount of rotation (in angles)
	 */
	public void updateAttachedPlanets(Vector3f center, Vector3f axis,float angle) {				
		for (Planet planet : attachedPlanets) {			
			//call method recursive for all attached planet of the attached planets
			planet.updateAttachedPlanets(center,axis,angle);	
			//rotate planet
			planet.addRotationArroundPoint(center,axis,angle);
			//rotate orbit
			planet.getOrbit().getModel().addRotationArroundPoint(center,axis,angle);
			//rotate parts
			for (int i = 0; i < planet.getPlanetParts().size(); i++) 
				for (int j = 0; j < planet.getPlanetParts().get(i).getModels().length; j++) 
					planet.getPlanetParts().get(i).getModels()[j].addRotationArroundPoint(center, axis, angle);
			//rotate entities
			for (PlanetEntity entity : planet.getPlanetEntities()) 
				for (int i = 0; i < entity.getInstancedModels().length; i++) 	
					for (int j = 0; j < entity.getInstances(); j++) 
						entity.getInstancedModels()[i].addRotationArroundPoint(center, axis, angle,j);		
		}		
	}
	
	/**
	 * updates color VBO of the planet
	 */
	public void useNormalColor() {	
		getPlanetMesh().getMesh().setColorValues(normalColorValues);
	}
	
	/**
	 * calculates based on the normal color values of the planet new color values that have an increased red and blue amount and updates the corresponding vbo with the new vlaues.
	 * The change in color indicates that the player hovers over the planet
	 */
	private void useHighlightColor() {
		normalColorValues=getPlanetMesh().getMesh().getColors();
		
		hilightColorValues = new float[getPlanetMesh().colors.length];
		for (int i = 0; i < getPlanetMesh().getMesh().getColors().length; i+=3) {
			hilightColorValues[i] = getPlanetMesh().getMesh().getColors()[i]+0.9f;			
			hilightColorValues[i+1] = getPlanetMesh().getMesh().getColors()[i+1]+0.1f;	
			hilightColorValues[i+2] = getPlanetMesh().getMesh().getColors()[i+2]+0.6f;	
		}
		getPlanetMesh().getMesh().setColorValues(hilightColorValues);
	}
	
	/**
	 * saves the normal colors of the planet for later use
	 */
	private void setNormalColorValues() {
		normalColorValues=getPlanetMesh().getMesh().getColors();
	}
	
	/**
	 * Renders planet entities based on the distance attribute of the planet. If the distance between the planet and the camer is too high the entities wont get rendered to the scren.
	 * @param camera
	 * 		-camera to calculate the distance between camera and this planet
	 * @return
	 * 		-true if the entities shouldnt be rendered = distance from camera to planet is higher than the distance attribute of the planet
	 */
	public boolean checkIfEntitiesRender(Camera camera) {
		Vector3f cameraPos = new Vector3f(camera.getX(), camera.getY(), camera.getZ());
		Vector3f entityPos = new Vector3f(planetModel.getModel().getModelMatrix().m03,planetModel.getModel().getModelMatrix().m13,planetModel.getModel().getModelMatrix().m23);
		Vector3f distance = Vector3f.subtract(cameraPos, entityPos);
		if (distance.length()>entityRenderDistance) 
			return false;
		return true;
	}
	
	public void increaseRotation(float rotationX,float rotationY,float rotationZ) {
		planetModel.increaseRotation(rotationX, rotationY, rotationZ);
	}
	
	public void increasePosition(float x,float y,float z) {
		planetModel.increasePosition(x, y, z);
	}
	
	public void increaseScale(float scale) {
		planetModel.increaseScale(scale);
	}
	
	public void setX(float x) {
		planetModel.setX(x);
	}
	
	public void setY(float y) {
		planetModel.setY(y);
	}
	
	public void setZ(float z) {
		planetModel.setZ(z);
	}
	
	public void setScale(float scale) {
		planetModel.setScale(scale);
		for (int i = 0; i < planetparts.size(); i++) 
			planetparts.get(i).setScale();
	}
	
	public void setScale(float x,float y,float z) {
		planetModel.setScale(x,y,z);
	}
		
	public void addTranslation(float x, float y,float z) {
		planetModel.getModel().getMatrixStack().addTranslation(x, y, z);		
	}
	
	public void addRotation(float rotateX,float rotateY,float rotateZ) {
		planetModel.getModel().addRotation(rotateX, rotateY, rotateZ);		
	}
	
	public void addRotation(Float angle,Vector3f axis) {
		planetModel.getModel().addRotation(angle,axis);		
	}
	
	public void addScale(float uniformScale) {
		planetModel.getModel().addScale(uniformScale);
	}
	
	public void addScale(float scaleX,float scaleY,float scaleZ) {
		planetModel.getModel().addScale(scaleX, scaleY, scaleZ);
	}
	
	public void addMatrix(Matrix4f matrix) {
		planetModel.getModel().addMatrix(matrix);
	}
	
	public void addRotationArroundPoint() {
		planetModel.addRotationArroundPoint();
	}
	
	public void addRotationArroundPoint(Vector3f point,Vector3f axis,float angle) {
		planetModel.addRotationArroundPoint(point,axis,angle);
	}
	
	public void setCenterX(float centerX) {
		planetModel.setCenterX(centerX);
	}

	public void setCenterZ(float centerZ) {
		planetModel.setCenterZ(centerZ);
	}

	public void setCenterY(float centerY) {
		planetModel.setCenterY(centerY);
	}
	public Vector3f getCenter() {
		return planetModel.getCenter();
	}
	
	public Vector3f getRotation() {
		return new Vector3f(planetModel.getRotationX(), planetModel.getRotationY(), planetModel.getRotationZ());
	}
	
	public Model getModel() {
		return planetModel.getModel();
	}
	
	public PlanetMesh getPlanetMesh() {
		return planetModel.getPlanetMesh();
	}

	public void increaseRotation() {
		planetModel.increaseRotation();
	}

	public void increaseOrbitRotation() {
		planetModel.increaseOrbitRotation();	
	}
	
	public float getOrbitRotation() {
		return planetModel.getOrbitRotation();
	}
	
	public Vector3f getOrbitAxis() {
		return planetModel.getOrbitAxis();
	}
	
	public ArrayList<PlanetPart> getPlanetParts(){
		return planetparts;
	}
	
	public void addPart(PlanetPart part) {
		planetparts.add(part);		
	}
	
	public void addEntity(PlanetEntity entity) {
		planetEntities.add(entity);
	}
	
	public ArrayList<PlanetEntity> getPlanetEntities(){
		return planetEntities;
	}
	
	public int getAmmountOfPlanetEntities() {
		int ammount = 0;
		for (int i = 0; i < planetEntities.size(); i++) 
				ammount+=planetEntities.get(i).getInstances()*planetEntities.get(i).getInstancedModels().length;
		return ammount;
	}
	
	public float getWaterConcentration() {
		if (this instanceof Earth || this instanceof CubicEarth) {
			int concentration = 0;
			for (int i = 0; i < this.getPlanetMesh().getHeights().length; i++) 
				if (this.getPlanetMesh().getHeights()[i] < 1.25f) 
					concentration++;
				
			return (float)concentration/(float)this.getPlanetMesh().getMesh().getVertices().length*100;		
		}
		return 0;
	}
	
	public float getHighestPoint() {
		float highestPoint = 0;
		for (int i = 0; i < this.getPlanetMesh().getHeights().length; i++) 
			if (this.getPlanetMesh().getHeights()[i]>highestPoint) 
				highestPoint=this.getPlanetMesh().getHeights()[i];
			
		return highestPoint;
	}
	
	public float getLowestPoint() {
		float highestPoint = 10;
		for (int i = 0; i < this.getPlanetMesh().getHeights().length; i++) 
			if (this.getPlanetMesh().getHeights()[i]<highestPoint) 
				highestPoint=this.getPlanetMesh().getHeights()[i];
			
		return highestPoint;
	}
	
	public float getAverageHeight() {
		float averageHeight = 0;
		for (int i = 0; i < this.getPlanetMesh().getHeights().length; i++) 
			averageHeight+=this.getPlanetMesh().getHeights()[i];
		
		return averageHeight/(float)this.getPlanetMesh().getHeights().length;
	}
	
	/**
	 * generates a random planet by randomizing the noise values.
	 */
	public void generateRandom() {
		planetModel.generate();
		for (int i = 0; i < planetEntities.size(); i++)	
			planetEntities.get(i).generateRandom();
		
		for (int i = 0; i < planetparts.size(); i++) 
			planetparts.get(i).generateRandom();
		
		setNormalColorValues();
	}
	
	public void attachPlanet(Planet planet) {
		attachedPlanets.add(planet);
	}
	
	public boolean getDrawOrbit() {
		return drawOrbit;
	}
	
	public void setDrawOrbit(boolean drawOrbit) {
		this.drawOrbit=drawOrbit;
	}

	public Orbit getOrbit() {
		return orbit;
	}
	
	public PlanetModel getPlanetModel() {
		return planetModel;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected=isSelected;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public String getName() {
		return name;
	}

	public float getRadius() {
		return getPlanetMesh().getRadius()*planetModel.getScale();
	}

	/**
	 * generates a random planet based on the parameters of the method.
	 */
	public void generateRandom(int res,float size, float noiseStrength,float noiseRoughness, Vector3f noiseOffset,boolean update,int noiseLayers,float noisePeristance,float noiseBaseRoughness,float noiseMinValue,float coloroffset) {	
		planetModel.getPlanetMesh().generateRandom(res, size, noiseStrength, noiseRoughness, noiseOffset, update,noiseLayers,noisePeristance,noiseBaseRoughness,noiseMinValue,coloroffset);
		for (int i = 0; i < planetEntities.size(); i++)				
			planetEntities.get(i).generateRandom();
		for (int i = 0; i < planetparts.size(); i++) 
			planetparts.get(i).generateRandom();
	}
	
	public static ArrayList<Planet> getAllPlanets(){
		return planets;
	}

	public void setGenerate(boolean b) {
		generateRandom = true;
	}
	
	public void setStopUpdate(boolean stop) {
		stopUpdate=stop;
	}
	
	public boolean getStopUpdate() {
		return stopUpdate;
	}
	
}
