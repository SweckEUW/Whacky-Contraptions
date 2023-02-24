package Computergrafik.Engine.Core.Renderer;

import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LINE_STRIP;
import static com.jogamp.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL.GL_SRC_ALPHA;
import static com.jogamp.opengl.GL.GL_TEXTURE0;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import Computergrafik.Engine.SkyBox;
import Computergrafik.Engine.Core.Config;
import Computergrafik.Engine.Core.Camera.Camera;
import Computergrafik.Engine.Core.Lights.AmbientLight;
import Computergrafik.Engine.Core.Lights.DirectionalLight;
import Computergrafik.Engine.Core.Lights.PointLight;
import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.InstancedModel;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Models.loadToGPU;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Planet.Core.Orbit;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetEntities.PlanetEntity;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;
import Computergrafik.Engine.Planet.PlanetParts.SunAtmosphere.SunAtmosphere;
import Computergrafik.Engine.Planet.PlanetVariations.CristalPlanet.CristalPlanet;

/**
 * 
 * Handles the rendering of a model to the screen. 
 * @author Simon Weck
 * 
 */
public class Renderer {

	private Matrix4f modelViewProjectionMatrix; //combination of model view and projection matrix
	private Matrix4f projectionMatrix;
	private Camera camera; 
	
	/**
	 * creates a renderer and sets it up depending on the config based on the Config.java class
	 */
	public Renderer(Camera camera,Matrix4f projectionMatrix4f) {
		GL4 gl=(GL4)GLContext.getCurrentGL(); 
		modelViewProjectionMatrix=new Matrix4f();
		this.camera=camera;
		this.projectionMatrix=projectionMatrix4f; 
	 
		//start settings of the renderer
		gl.glEnable(GL_DEPTH_TEST); 
		gl.glEnable(GL_BLEND);
		gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		gl.glLineWidth(0.5f);
	}
	
	/**
	 * Clears the frame.
	 * Clears color and depth buffers and clears the frame with the color in the config class. by default its white
	 * Also activates wireframe mode or backfaceculling if settings in the config class changed.
	 */
	public void clear() {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glClearColor(Config.BACKGROUND_COLOR.x, Config.BACKGROUND_COLOR.y,Config.BACKGROUND_COLOR.z,1); //Clears every pixel with a specific color
		gl.glClear(GL_COLOR_BUFFER_BIT);  //Clears color buffer
		gl.glClear(GL_DEPTH_BUFFER_BIT);//Clears the depth buffer
		wireframeMode(); //checks if wireframe mode is enabled
		backFacCulling(); //checks if backfaceculling is enabled
	}
	
	/**
	 * Renders a model to the screen with the current active shader program.
	 * 
	 * 0.activate shader
	 * 1.upload material of the model to the shader.
	 * 2.upload all lights that are available to the shader.
	 * 3.check if model moved. if yes recalculate model matrix using the transformations of the model. if no use unchanged model matrix from the model.
	 * 4.upload model matrix to the shader.
	 * 5.check if camera moved. if yes recalculate view matrix using the transformations of the camera. if no use unchanged view matrix from the camera.
	 * 6.upload view matrix to the shader.
	 * 7.concatenate model view and projection matrix to one matrix and upload it to the shader.
	 * 8.check if the mesh is a triangle mesh or line mesh
	 * 9.Activate vao of the model and render it using index buffer. (glDrawElements)
	 * 
	 * @param model
	 * 			-Model to be rendered
	 * @param shader
	 * 			-Shader of the model
	 */	
	public void render(Model model,BasicShader shader) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		
		shader.use(); //activate shader before rendering
		shader.uploadMaterial(model.getMaterial());		
		shader.uploadAmbientLight(AmbientLight.getAmbientLight());
		shader.uploadPointLights(PointLight.getPointLights());
		shader.uploadDirectionalLight(DirectionalLight.getDirectionalLights());
			
		if (model.getMatrixUpdate()) {
			model.getModelMatrix().changeToModelMatrix(model);
			model.setMatrixUpdate(false);
		}
		shader.uploadModelMatrix(model.getModelMatrix());
		
		if (camera.getMatrixUpdate()) {
			camera.getViewMatrix().changeToViewMatrix(camera);
			camera.setMatrixUpdate(false);
		}
		
		shader.uploadViewMatrix(camera.getViewMatrix());
			
		Matrix4f.changeToModelViewProjectionMatrix(camera.getViewMatrix(), model.getModelMatrix(),projectionMatrix, modelViewProjectionMatrix);		
		shader.uploadModelViewProjectionMatrix(modelViewProjectionMatrix);
				
		shader.uploadProjectionMatrix(projectionMatrix);
		
		//Draw call
		if (!model.getMesh().isLine()) {
			gl.glBindVertexArray(model.getMesh().getVaoID()); //activates the specific VAO
			gl.glDrawElements(GL_TRIANGLES, model.getMesh().getIndexCount(), GL_UNSIGNED_INT, 0); //draws with the usage of indices 
		}else {
			gl.glBindVertexArray(model.getMesh().getVaoID());
			gl.glDrawArrays(GL_LINE_STRIP, 0,model.getMesh().getVertices().length);
		}		
	}
	
	/**
	 * renders a planet with all attributes (Planet parts, planet entites etc.) to the screen
	 * 
	 * 0.update the planet (also updates all planet parts and entities)
	 * 1.render the orbit of the planet as glLine
	 * 2.render planet mesh.
	 * 3.render planet parts (water,rings,particles etc.)
	 * 5.check the distance between camera and planet. If the distance to too much the entities wont render (Level of detail). If the camera is close enough to the planet the entities will get rendered
	 * 6.render planet entities (trees,stones,sweets)
	 * 
	 * if the planet is a cristalPlanet we first render the planet parts and than the planet mesh itself because the mesh itself is transparent
	 * the sun atmosphere will always get drawn as wireframe (design decision)
	 * @param planet
	 * 		-planet to be rendered
	 * @param shader
	 * 		-shader of the planet
	 */
	public void render(Planet planet, BasicShader shader) {
		GL4 gl=(GL4)GLContext.getCurrentGL(); 	
		
		planet.update();
		
		//Render Orbit
		if (planet.getDrawOrbit() && planet.getOrbit()!=null) 
			render(planet.getOrbit(), planet.getOrbit().getOrbitShader());
				
		if (planet instanceof CristalPlanet) {
			//Render PlanetParts
			for (int i = 0; i < planet.getPlanetParts().size(); i++) 
				for (int j = 0; j <  ((PlanetPart) planet.getPlanetParts().get(i)).getModels().length; j++) 
					render(((PlanetPart) planet.getPlanetParts().get(i)).getModels()[j], ((PlanetPart) planet.getPlanetParts().get(i)).getShader());	
			
			//Render Planet
			render(planet.getModel(), shader);
		} else {
			//Render Planet
			render(planet.getModel(), shader);
				
			//Render PlanetParts
			for (int i = 0; i < planet.getPlanetParts().size(); i++) {
				for (int j = 0; j <  ((PlanetPart) planet.getPlanetParts().get(i)).getModels().length; j++) {
					if ((PlanetPart) planet.getPlanetParts().get(i) instanceof SunAtmosphere) {							
						gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);		
						gl.glLineWidth(3);
						render(((PlanetPart) planet.getPlanetParts().get(i)).getModels()[j], ((PlanetPart) planet.getPlanetParts().get(i)).getShader());						
					}
					render(((PlanetPart) planet.getPlanetParts().get(i)).getModels()[j], ((PlanetPart) planet.getPlanetParts().get(i)).getShader());
				}
			}			
			gl.glLineWidth(0.5f);
			wireframeMode();
		}
				
		//Render PlanetEntities
		if (planet.checkIfEntitiesRender(camera)) 
			for (int i = 0; i <  planet.getPlanetEntities().size(); i++) 
					render(((PlanetEntity) planet.getPlanetEntities().get(i)));																
	}
	
	/**
	 * Renders a planetEntity (rendering a instancedModel)
	 * 
	 * 0.activate shader of the entity
	 * 1.upload all lights to the shader
	 * 2.upload view and projection matrix (its the same for all models and instances)
	 * for every model of the entity (each entity can have multiple models e.g. tree tribe and top of the tree build one tree entity) do the following:
	 * 		3. upload material of the model (each entity could also have its own material but I decided not to)
	 * 		for every instance (amount of times we want to render the entity) of the model do the following:
	 * 			4.check if the instance successfully got placed on a planet. (if there is too much water a tree cant be placed) dont render if its not placed
	 * 			5.Create model matrix out of the transformations of the current instance.
	 * 		6.upload all matrices as huge array so the shader can access a matrix for every instance
	 * 		7.activate vao and draw the model multiple times to the screen using the matrices with the useg of glDrawElementsInstanced
	 * 
	 * @param entity
	 */
	public void render(PlanetEntity entity) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		BasicShader shader = entity.getShader();
		int instances = entity.getInstances();
		float[] matrixData = new float[16*instances];
			
		//activate shader before rendering
		shader.use(); 
		//upload light
		shader.uploadAmbientLight(AmbientLight.getAmbientLight());
		shader.uploadPointLights(PointLight.getPointLights());
		shader.uploadDirectionalLight(DirectionalLight.getDirectionalLights());		
		
		//upload view matrix
		if (camera.getMatrixUpdate()) {
			camera.getViewMatrix().changeToViewMatrix(camera);
			camera.setMatrixUpdate(false);
		}
		shader.uploadViewMatrix(camera.getViewMatrix());
				
		//upload projection matrix
		shader.uploadProjectionMatrix(projectionMatrix);
		
		for (int i = 0; i < entity.getInstancedModels().length; i++) {
			shader.uploadMaterial(entity.getInstancedModels()[i].getMaterial());
			InstancedModel model = entity.getInstancedModels()[i];				
			
			for (int j = 0; j < instances; j++) {		
				if (entity.isPlaced(j)) {
					//check if it got placed 
						
					//create matrix
							
					//reset matrix
					model.getModelMatrix()[j].setIdentityMatrix();													
						
					//matrix stack of the instance
					for (int m = 0; m < model.getMatrixStack()[j].size(); m++) 
						model.getModelMatrix()[j].multiply(model.getMatrixStack()[j].pop()); 				
					//second translation of the instance (2 translations needed to place on planet)
					model.getModelMatrix()[j].translate(model.getX(j),model.getY(j),model.getZ(j)); 
					//rotation of the instance
					model.getModelMatrix()[j].rotateX(model.getRotationX()[j]);
					model.getModelMatrix()[j].rotateY(model.getRotationY()[j]);
					model.getModelMatrix()[j].rotateZ(model.getRotationZ()[j]);
					//first translation of the instance (2 translations needed to place on planet)
					model.getModelMatrix()[j].translate(entity.getX1(j),entity.getY1(j),entity.getZ1(j));
					//rotation around axis of instance  (needed to place on planet)
					if (entity.getAxes()[j]==null) 
						model.getModelMatrix()[j].rotate(entity.getAngles()[j], new Vector3f(0,1,0));
					else 
						model.getModelMatrix()[j].rotate(entity.getAngles()[j], entity.getAxes()[j]);
					//scale of instance		
					model.getModelMatrix()[j].scale(model.getScaleX()[j],model.getScaleY()[j],model.getScaleZ()[j]); 				
						
					//save matrix into array so it can get uploaded
					matrixData[j*16] = model.getModelMatrix()[j].m00; 
					matrixData[j*16+1] = model.getModelMatrix()[j].m10; 
					matrixData[j*16+2] = model.getModelMatrix()[j].m20; 
					matrixData[j*16+3] = model.getModelMatrix()[j].m30; 
					matrixData[j*16+4] = model.getModelMatrix()[j].m01; 
					matrixData[j*16+5] = model.getModelMatrix()[j].m11;  
					matrixData[j*16+6] = model.getModelMatrix()[j].m21; 
					matrixData[j*16+7] = model.getModelMatrix()[j].m31; 
					matrixData[j*16+8] = model.getModelMatrix()[j].m02; 
					matrixData[j*16+9] = model.getModelMatrix()[j].m12; 
					matrixData[j*16+10] = model.getModelMatrix()[j].m22; 
					matrixData[j*16+11] = model.getModelMatrix()[j].m32; 
					matrixData[j*16+12] = model.getModelMatrix()[j].m03; 
					matrixData[j*16+13] = model.getModelMatrix()[j].m13; 
					matrixData[j*16+14] = model.getModelMatrix()[j].m23; 
					matrixData[j*16+15] = model.getModelMatrix()[j].m33; 
				}
			}							
			loadToGPU.updateVBO(model.getMatrixVBOID(),matrixData);
										
			gl.glBindVertexArray(model.getInstancedMesh().getVaoID()); //activates the specific VAO
			gl.glDrawElementsInstanced(GL_TRIANGLES,  model.getInstancedMesh().getIndexCount(), GL_UNSIGNED_INT, 0, instances);			
		}
	}
	
	/**
	 * render a planet orbit. Normal render method but it gets rendered as line using glDrawArrays with GL_LINE_STRIP
	 */
	public void render(Orbit orbit,BasicShader shader) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		Model model = orbit.getModel();
		shader.use(); //activate shader before rendering
			
		if (model.getMatrixUpdate()) {
			model.getModelMatrix().changeToModelMatrix(model);
			model.setMatrixUpdate(false);
		}
		
		if (camera.getMatrixUpdate()) {
			camera.getViewMatrix().changeToViewMatrix(camera);
			camera.setMatrixUpdate(false);
		}
					
		Matrix4f.changeToModelViewProjectionMatrix(camera.getViewMatrix(), model.getModelMatrix(),projectionMatrix, modelViewProjectionMatrix);		
		shader.uploadModelViewProjectionMatrix(modelViewProjectionMatrix);
				
		shader.uploadProjectionMatrix(projectionMatrix);
		
		gl.glBindVertexArray(model.getMesh().getVaoID());
		gl.glDrawArrays(GL_LINE_STRIP, 0,model.getMesh().getVertices().length);
	}
	
	/**
	 *render a skybox. normal render method but the camera translations wont have any effect on the skybox so the camera will always be in the center of the box.
	 *The skybox also has a texture so we have to activate the texture and bind the texture before we draw.
	 */
	public void render(SkyBox skybox,BasicShader shader) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		skybox.update();
		for (Model model : skybox.getSides()) {
			
			shader.use(); //activate shader before rendering
			
			if (model.getMatrixUpdate()) {
				model.getModelMatrix().changeToModelMatrix(model);
				model.setMatrixUpdate(false);
			}
			
			if (camera.getMatrixUpdate()) {
				camera.getViewMatrix().changeToViewMatrix(camera);
				camera.setMatrixUpdate(false);
			}
			
			//change translation of the view matrix to 0 so the box wont translate 
			Matrix4f viewMatrix = new Matrix4f(camera.getViewMatrix());
			viewMatrix.m03=0;
			viewMatrix.m13=0;
			viewMatrix.m23=0;
			
			Matrix4f.changeToModelViewProjectionMatrix(viewMatrix, model.getModelMatrix(),projectionMatrix, modelViewProjectionMatrix);		
			shader.uploadModelViewProjectionMatrix(modelViewProjectionMatrix);
			shader.uploadProjectionMatrix(projectionMatrix);
			
			//activate texture and bin texture
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, model.getMesh().getTextureID());
				
			gl.glBindVertexArray(model.getMesh().getVaoID()); //activates the specific VAO
			gl.glDrawElements(GL_TRIANGLES, model.getMesh().getIndexCount(), GL_UNSIGNED_INT, 0); //draws with the usage of indices 
		}
	}
	
	/**
	 * sets the glPolygonMode to glLine or glFill depending on the setting inside the config class.
	 * drawing in glline mode is the wireframe mode
	 */
	private void wireframeMode() {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		if (Config.WIREFRAME_MODE) 
			gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);	
		else
			gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
	
	/**
	 * activates back face culling depending on the setting inside the config class.
	 */
	public void backFacCulling() {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		if (Config.BACK_FACE_CULLING) 
			gl.glEnable(GL_CULL_FACE);
		else
			gl.glDisable(GL_CULL_FACE);
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
}
