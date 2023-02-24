package Simulation.Objects;

import java.util.ArrayList;

import Simulation.Collisions.CollisionContext;
import Simulation.Particles.ParticleSystem;
import Simulation.RenderEngine.Core.Models.Model;
import Simulation.RenderEngine.Core.Models.TriangleModel;
import Simulation.RenderEngine.Core.Shaders.Core.BasicShader;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Primitive;
import UI.Util;
import UI.ObjectTransformer.ObjectTransformer;
import UI.TabElements.TabElement;

public abstract class GameObject {

	public static ArrayList<GameObject> allObjects = new ArrayList<GameObject>();

	private static BasicShader shader = new BasicShader("GameObject");
	
	protected TriangleModel[] models;
	private boolean renderModel = true;
	
	protected float x,y;
	protected float rotation=0;
	protected float scale=1;	
	protected float CoefficientOfRestitution = 1;
	protected float mass = 1;
	
	protected CollisionContext collisionContext;
	protected boolean renderBounding = Util.devMode;
	
	protected float originalX,originalY;
	protected float originalrotation=0;
		
	protected boolean selected;
	protected boolean highlighted;

	protected ObjectTransformer objectTransformer;

	private boolean scalable = false;
	private boolean rotatable = false;
	private boolean moveable = false;

	private boolean editable = true;
	private boolean playable = false;
	
	protected TabElement tabPane;

	protected ParticleSystem particleSystem;

	
////////////////////
////Constructors////
////////////////////
	public GameObject(String[] files,Material material, float r, float g, float b,float x,float y) {
		this.x = x;
		this.y = y;
		originalX=x;
		originalY=y;
		
		models = new TriangleModel[files.length];
		for (int i = 0; i < models.length; i++) 
			models[i] = new TriangleModel("Models/"+files[i],material,r,g,b,x,y);		
		
		allObjects.add(this);
	}
	
	public GameObject(String[] files , String[] textures, Material material, float x,float y) {
		this.x = x;
		this.y = y;
		originalX=x;
		originalY=y;
		
		models = new TriangleModel[files.length];
		for (int i = 0; i < models.length; i++) 
			models[i] = new TriangleModel("Models/"+files[i],"Models/"+textures[i],material,x,y);		
		
		allObjects.add(this);
	}
	
	public GameObject(String file , String texture, Material material, float x,float y) {
		this.x = x;
		this.y = y;
		originalX=x;
		originalY=y;
		
		models = new TriangleModel[1];
		models[0] = new TriangleModel("Models/"+file,"Models/"+texture,material,x,y);		
		
		allObjects.add(this);		
	}
	
	public GameObject(String file,Material material, float r, float g, float b,float x,float y) {
		this.x = x;
		this.y = y;
		originalX=x;
		originalY=y;
		
		models = new TriangleModel[1];
		models[0] = new TriangleModel("Models/"+file,material,r,g,b,x,y);		
		
		allObjects.add(this);
	}
	
	public GameObject(Primitive primitive,Material material, float r, float g,float b, float x,float y) {
		this.x = x;
		this.y = y;
		originalX=x;
		originalY=y;
		
		models = new TriangleModel[1];
		models[0] = new TriangleModel(primitive,material,r,g,b,x,y);		
		
		allObjects.add(this);
	}
	
	public GameObject(Primitive primitive,Material material, String texture, float x,float y) {
		this.x = x;
		this.y = y;
		originalX=x;
		originalY=y;
		
		models = new TriangleModel[1];
		models[0] = new TriangleModel(primitive,material,"Models/"+texture,x,y);		
		
		allObjects.add(this);
	}
	
	
///////////////
////Methods////
///////////////
	public abstract void update();
	
	public void reset() {
		setY(originalY);
		setX(originalX);
		setRotation(originalrotation);
	}

	public void remove() {
		if(this.tabPane!=null)
			this.tabPane.resetCounter();
	}
	
	public abstract void onCollision();
/////////////////////////
////Getters & Setters////
/////////////////////////
	public void increasePosition(float dx,float dy) {
		
		if (dx!=0) {
			setX(this.x+=dx);
			collisionContext.setX(x);
		}
		
		if (dy!=0) {
			setY(this.y+=dy);		
			collisionContext.setY(y);
		}
		
		for (Model model : models) 
			model.increasePosition(dx, dy,0);	
	}
	
	public void increaseRotation(float dz) {
		if(dz!=0) {
			this.rotation+=dz;
			for (Model model : models) 
				model.increaseRotation(0, 0, dz);		
			collisionContext.setRotation(rotation);
		}
	}
	
	public TriangleModel[] getModels() {
		return models;
	}
	
	public void setModels(TriangleModel[] models) {
		this.models = models;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
		for (Model model : models) 
			model.setX(x);	
		collisionContext.setX(x);

		objectTransformer.setX(x);
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y=y;
		for (Model model : models) 
			model.setY(y);
		collisionContext.setY(y);

		objectTransformer.setY(y);
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
		for (Model model : models) 
			model.setRotationZ(rotation);
		collisionContext.setRotation(rotation);
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		for (Model model : models) 
			model.setScaleX(scale);
		for (Model model : models) 
			model.setScaleY(scale);	
		for (Model model : models) 
			model.setScaleZ(scale);

		objectTransformer.setScale(scale);
		collisionContext.setScale(scale);
	}
	
	public float getScale() {
		return scale;
	}
	

	public CollisionContext getCollisionContext() {
		return collisionContext;
	}

	public boolean renderBounding() {
		return renderBounding;
	}
	
	public void renderBounding(boolean renderBounding) {
		this.renderBounding = renderBounding;
	}
	
	public float getRotation() {
		return rotation;
	}

	public boolean renderModel() {
		return renderModel;
	}
	
	public void renderModel(boolean renderModel) {
		this.renderModel  = renderModel;
	}

	public float getOriginalX() {
		return originalX;
	}

	public void setOriginalX(float originalX) {
		this.originalX = originalX;
	}

	public float getOriginalY() {
		return originalY;
	}

	public void setOriginalY(float originalY) {
		this.originalY = originalY;
	}

	public float getOriginalrotation() {
		return originalrotation;
	}

	public void setOriginalrotation(float originalrotation) {
		this.originalrotation = originalrotation;
	}

	public float getMass() {
		return mass;
	}
	
	public void setMass(float mass) {
		this.mass = mass;
	}
	
	public void selectObject() {
		selected=true;
	}
	
	public void unSelectObject() {
		selected=false;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public ObjectTransformer getObjectTransformer() {
		return objectTransformer;
	}

	public void setObjectTransformer(ObjectTransformer objectTransformer) {
		this.objectTransformer = objectTransformer;
	}

	public BasicShader getShader() {
		return shader;
	}

	public boolean isScalable() {
		return scalable;
	}

	public void setScalable(boolean scalable) {
		this.scalable = scalable;
	}

	public boolean isRotatable() {
		return rotatable;
	}

	public void setRotatable(boolean rotatable) {
		this.rotatable = rotatable;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public void highlight(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	public boolean getHighlighted() {
		return highlighted;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isPlayable() {
		return playable;
	}

	public void setPlayable(boolean playable) {
		this.playable = playable;
	}
	
	public void setTabPane(TabElement tabPane) {
		this.tabPane = tabPane;
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}

	public float getCoefficientOfRestitution() {
		return CoefficientOfRestitution;
	}

	public void setCoefficientOfRestitution(float coefficientOfRestitution) {
		CoefficientOfRestitution = coefficientOfRestitution;
	}

}