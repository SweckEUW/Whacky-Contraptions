package Computergrafik.Engine.Planet.PlanetEntities;

import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Planet.Core.Planet;

/**
 * A PlanetEntity is an object that will be placed directly onto a planet mesh. 
 * It also calculates a rotation axis and rotation angle so the object will be aligned with the the surface normal.
 * A PlanetEntity can be build out of multiple models for example a Donut might have a base, a glaze and sprinkles. 
 * @author Simon Weck
 *
 */
public abstract class PlanetEntity {

	//amount of trys a object can have until its not rendered 
	protected static final int MAX_RECURSIONS = 2000;
	
	protected Planet planet;
	protected int instances; //number of instances we want to render
	protected int numberOfModels; 
	protected InstancedModel[] instancedModels;//list of all models
	protected BasicShader shader = new BasicShader("PlanetShader/PlanetEntity");
	protected boolean placed[]; //true if the instance got placed false if not
	
	protected float[][] hilightColorValues;
	protected float[][] normalColorValues;
	
	protected float[] angles;
	protected Vector3f[] axes;
	protected float maxHeight = 1.6f; //min height to be placed
	protected float minHeight = 1.3f; //max height to be placed
	protected float colorOffset = 0.8f; 
	
	protected float[] x1;
	protected float[] y1;
	protected float[] z1;
	
	/**
	 * initializes the models and calculates colors for them. 
	 * Also calculates a random triangle on which the object will get placed. The correct rotation axis and rotation angles for each instance 
	 * will be stored into the angles and axes arrays.
	 * @param planet
	 * @param numberOfModels
	 * @param instances
	 */
	public PlanetEntity(Planet planet,int numberOfModels,int instances) {
		this.numberOfModels = numberOfModels;
		this.instances=instances;
		this.planet=planet;
		initArrays();
		setModels(); //create model objects
		setScale(); //calculate random scale
		placeOnPlanet(); //calculate random placement and rotation axis and angles
		calculateColors(); //calculate colors using the colorOffset
 		setNormalColorValues(); //save default color values for the mouse picker
	}

	/**
	 * calculates the rotation axis and angle for each instance. All models than share the same data. 
	 * The Method tries to place the object within the min and max height range. If it fails it trys it again until the max recurision count is reached.
	 * For example a tree cant be placed onto an earth where only water is. It tires 2000 times to place it and stops then because there is no possible way
	 * to place it. The instance will be marked as notplaced (true in the notplaced[] and will not be rendered until this state changes)
	 */
	public void placeOnPlanet() { 
		for (int j = 0; j < instances; j++) {
			for (int i = 0; i < MAX_RECURSIONS; i++) {
				placed[j]=calculatePlacement(j);	
				if (placed[j]) 
					i=MAX_RECURSIONS;
			}
		}		
	}

	/**
	 * calculates the actual placement,rotation axis and angle of the instance
	 *
	 */
	private boolean calculatePlacement(int index) {
		float[] vertices= planet.getPlanetMesh().getMesh().getVertices();
		int[] indices=planet.getPlanetMesh().getMesh().getIndices();
			
		//get random triangle
		int randomIndex=(int)(Math.random()*indices.length);
		
		if (randomIndex%3==1) 
			randomIndex-=1;
		else if (randomIndex%3==2) 
			randomIndex-=2;
			
		Vector3f vertex1=new Vector3f(vertices[indices[randomIndex]*3],vertices[indices[randomIndex]*3+1],vertices[indices[randomIndex]*3+2]);
		Vector3f vertex2=new Vector3f(vertices[indices[randomIndex+1]*3],vertices[indices[randomIndex+1]*3+1],vertices[indices[randomIndex+1]*3+2]);
		Vector3f vertex3=new Vector3f(vertices[indices[randomIndex+2]*3],vertices[indices[randomIndex+2]*3+1],vertices[indices[randomIndex+2]*3+2]);
					
		Vector3f randomPointOnSurface = calculateRandomPointOnPlanet(vertex1,vertex2,vertex3);
		
		Vector3f surfaceNormal = calculateNormal(vertex1, vertex2, vertex3);
		
		//calculate rotation;
				
		//check if triangle is an empty triangle
		if (Double.isNaN(surfaceNormal.x) || surfaceNormal.length()==0) 
			return false;
		
		//check if entity got placed on the wrong height
		if (planet.getPlanetMesh().getHeights()[indices[randomIndex]*3] > maxHeight || planet.getPlanetMesh().getHeights()[indices[randomIndex]*3] < minHeight) 
			return false;
						
		calculateRotationAxis(surfaceNormal,index);
		calculateRotationAngle(surfaceNormal,index);
		setTranslation(randomPointOnSurface,index);								
		setScale();
		
		return true;
	}
	
	/**
	 * sets the translation of the instance based on a  random point on a random triangle
	 * @param randomPointOnSurface
	 * @param index
	 */
	private void setTranslation(Vector3f randomPointOnSurface,int index) {
		
		randomPointOnSurface = Vector3f.multiply(randomPointOnSurface, planet.getPlanetModel().getScale());
		
		x1[index] = randomPointOnSurface.x;
		y1[index] = randomPointOnSurface.y;
		z1[index] = randomPointOnSurface.z;
		
		randomPointOnSurface = Vector3f.add(randomPointOnSurface,planet.getPlanetModel().getTranslation());
		
		for (int i = 0; i < instancedModels.length; i++) {
			instancedModels[i].setX(index,planet.getPlanetModel().getTranslation().x);
			instancedModels[i].setY(index,planet.getPlanetModel().getTranslation().y);
			instancedModels[i].setZ(index,planet.getPlanetModel().getTranslation().z);
		}
	}
	
	/**
	 * calculates a random point inside the triangle that would be created out of the 3 vertices
	 */
	private Vector3f calculateRandomPointOnPlanet(Vector3f vertex1,Vector3f vertex2,Vector3f vertex3) {		
		float random1=(float)Math.random()*9999;
		float random2=(float)Math.random()*9999;
		float random3=(float)Math.random()*9999;
			
		float randomSum=random1+random2+random3;
		random1/=randomSum;
		random2/=randomSum;
		random3/=randomSum;
			
		vertex1=Vector3f.multiply(vertex1, random1);
		vertex2=Vector3f.multiply(vertex2, random2);
		vertex3=Vector3f.multiply(vertex3, random3);
			
		Vector3f randomPointOnSurface = Vector3f.add(vertex1, vertex2, vertex3);
			
		return randomPointOnSurface;
	}
	
	/**
	 *calculates the rotation angle of the surface normal of a triangle and the up vector of the object (always 0,1,0) using the dot product.
	 *the angle will then be calculated to degree to use it in the math functinos
	 */
	private void calculateRotationAngle(Vector3f surfaceNormal,int index) {
		Vector3f upVector = new Vector3f(0,1,0);
		
		float angle =Vector3f.multiply(upVector,surfaceNormal);
		angle=(float)Math.acos(angle);
		angle=-(float)Math.toDegrees(angle);
		if (Double.isNaN(angle)) 
			angle=0;
		
		angles[index] = angle;
	}
	
	/**
	 * calculates the rotation axis of the instance using the cross product of the up vector and the surface normal of the triangle
	 */
	private void calculateRotationAxis(Vector3f surfaceNormal,int index) {
		Vector3f upVector = new Vector3f(0,1,0);
		
		Vector3f axis = Vector3f.cross(surfaceNormal,upVector);		
		if (Double.isNaN(axis.x) || axis.length() == 0 || axis==null) 
			axis = new Vector3f(0, 0, 1);
		
		axes[index] = axis;
	}
	
	/**
	 *calculates the surface normal of the triangle using the cross product
	 */
	private Vector3f calculateNormal(Vector3f vertex1,Vector3f vertex2,Vector3f vertex3) {
		Vector3f v = Vector3f.subtract(vertex3, vertex1);
		Vector3f u = Vector3f.subtract(vertex2, vertex1);	
					
		Vector3f surfaceNormal = Vector3f.cross(u, v).normalize();
		
		return surfaceNormal;
	}
	
	/**
	 * rotates the entity with the plant around itself
	 */
	public void update() {
		float[] roationX = new float[instances];
		float[] roationY = new float[instances];
		float[] roationZ = new float[instances];
		
		for (int i = 0; i <instancedModels.length; i++) {
			for (int j = 0; j < instances; j++) {
				roationX[j]=planet.getPlanetModel().getRotationX();
				roationY[j]=planet.getPlanetModel().getRotationY();
				roationZ[j]=planet.getPlanetModel().getRotationZ();
			}	
			instancedModels[i].setRotationX(roationX);
			instancedModels[i].setRotationY(roationY);
			instancedModels[i].setRotationZ(roationZ);
		}			
	}
	 
	public void useNormalColor() {	
		for (int i = 0; i < instancedModels.length; i++) 
				instancedModels[i].getInstancedMesh().setColors(normalColorValues[i]);			
	}
	
	public void useHighlightColor() {
		setNormalColorValues();			
		for (int i = 0; i < instancedModels.length; i++) {
			for (int j = 0; j < instances*3; j+=3) {
				hilightColorValues[i][j] = normalColorValues[i][j]+0.9f;			
				hilightColorValues[i][j+1] = normalColorValues[i][j+1]+0.1f;	
				hilightColorValues[i][j+2] = normalColorValues[i][j+2]+0.6f;	
			}
			instancedModels[i].getInstancedMesh().setColors(hilightColorValues[i]);
		}	
	}
	
	public void setNormalColorValues() {
		for (int i = 0; i < instancedModels.length; i++) 
			for (int j = 0; j < instances*3; j++) 
				normalColorValues[i][j]=instancedModels[i].getInstancedMesh().getColors()[j];				
	}
	
	
	protected void initArrays() {
		placed = new boolean[instances];
		hilightColorValues= new float[numberOfModels][instances*3];
		normalColorValues = new float[numberOfModels][instances*3];
		instancedModels = new InstancedModel[numberOfModels];
		angles = new float[instances];
		axes = new Vector3f[instances];
		x1= new float[instances];
		y1= new float[instances];
		z1= new float[instances];
	}
	
	protected abstract void setModels();
	
	protected abstract void setScale();
	
	protected abstract void calculateColors();
	
	public float[] getAngles() {
		return angles;
	}

	public Vector3f[] getAxes() {
		return axes;
	}
	
	public boolean isPlaced(int index) {
		return placed[index];
	}

	
	public int getInstances() {
		return instances;
	}

	public BasicShader getShader() {
		return shader;
	}

	public InstancedModel[] getInstancedModels() {
		return instancedModels;
	}

	public void generateRandom() {
		setScale();
		placeOnPlanet();
		calculateColors();
		setNormalColorValues();
	}
	
	public float getX1(int index) {
		return x1[index];
	}
	
	public float getY1(int index) {
		return y1[index];
	}
	
	public float getZ1(int index) {
		return z1[index];
	}
}
