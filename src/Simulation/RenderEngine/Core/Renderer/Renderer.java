package Simulation.RenderEngine.Core.Renderer;

import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LINEAR;
import static com.jogamp.opengl.GL.GL_LINEAR_MIPMAP_LINEAR;
import static com.jogamp.opengl.GL.GL_LINE_LOOP;
import static com.jogamp.opengl.GL.GL_LINE_SMOOTH;
import static com.jogamp.opengl.GL.GL_MULTISAMPLE;
import static com.jogamp.opengl.GL.GL_NICEST;
import static com.jogamp.opengl.GL.GL_POINTS;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;
import static com.jogamp.opengl.GL2GL3.GL_POLYGON_SMOOTH;
import static com.jogamp.opengl.GL2GL3.GL_POLYGON_SMOOTH_HINT;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import Simulation.Collisions.Boundings.BoundingCircle;
import Simulation.Collisions.Boundings.BoundingPolygon;
import Simulation.Objects.GameObject;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Hairdryer;
import Simulation.RenderEngine.Core.Config;
import Simulation.RenderEngine.Core.Camera.Camera;
import Simulation.RenderEngine.Core.Lights.AmbientLight;
import Simulation.RenderEngine.Core.Lights.DirectionalLight;
import Simulation.RenderEngine.Core.Lights.PointLight;
import Simulation.RenderEngine.Core.Math.Matrix4f;
import Simulation.RenderEngine.Core.Models.InstancedModel;
import Simulation.RenderEngine.Core.Models.LineModel;
import Simulation.RenderEngine.Core.Models.TriangleModel;
import Simulation.RenderEngine.Core.Models.loadToGPU;
import Simulation.RenderEngine.Core.Shaders.Core.BasicShader;

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
	public Renderer(Camera camera) {
		modelViewProjectionMatrix=new Matrix4f();
		this.camera=camera;
		this.projectionMatrix=new Matrix4f();
		projectionMatrix.changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,Config.CANVAS_HEIGHT,Config.CANVAS_WIDTH);	
		updateProjectionMatrix();
	}
	
	/**
	 * Clears the frame.
	 * Clears color and depth buffers and clears the frame with the color in the config class. by default its white
	 * Also activates wireframe mode or backfaceculling if settings in the config class changed.
	 */
	public void clear() {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glClear(GL_COLOR_BUFFER_BIT);  //Clears color buffer
		gl.glClear(GL_DEPTH_BUFFER_BIT);//Clears the depth buffer
		
		gl.glClearColor(Config.BACKGROUND_COLOR.x, Config.BACKGROUND_COLOR.y,Config.BACKGROUND_COLOR.z,Config.BACKGROUND_COLOR.w); //Clears every pixel with a specific color
	
		wireframeMode(); //checks if wireframe mode is enabled
		backFacCulling(); //checks if backfaceculling is enabled
		
		//start settings of the renderer
				gl.glEnable(GL_DEPTH_TEST); 
				gl.glEnable(GL_BLEND);	
				
				gl.glEnable(GL_POLYGON_SMOOTH);
				gl.glEnable(GL_LINE_SMOOTH);
				
				gl.glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
				gl.glEnable(GL_MULTISAMPLE);

				gl.glLineWidth(Config.LINE_WIDTH);
				gl.glPointSize(Config.POINT_SIZE);
				
				gl.glTexParameteri(GL_TEXTURE_2D, GL_LINEAR_MIPMAP_LINEAR , GL_LINEAR);
	}
	

	@SuppressWarnings("exports")
	public void render(GameObject object,BasicShader shader) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		
		if (object.renderBounding()) {
			gl.glLineWidth(1);
			
			for (BoundingCircle circle : object.getCollisionContext().getBoundingCircles()) 
				render(circle.getModel(), circle.getShader());	
			for (BoundingPolygon polygon : object.getCollisionContext().getBoundingPolygons()) 
				render(polygon.getModel(), polygon.getShader());
			
			gl.glLineWidth(8);
		}
		
		if(object.renderModel()) {
			shader.use(); //activate shader before rendering
			shader.uploadSelectionHighlight(object.isSelected(),object.getHighlighted());
			for (TriangleModel model : object.getModels()) 
				render(model,shader);
			
		}

		if (object.isSelected()) {
			render(object.getObjectTransformer().getCircleUI().getCircleModel(), object.getObjectTransformer().getCircleUI().getShader());
			render(object.getObjectTransformer().getSquareUI().getRectangleLine(), object.getObjectTransformer().getSquareUI().getShader());
		}
		if(object instanceof Hairdryer) {
			gl.glLineWidth(1);
			render(((Hairdryer)object).getCone(), object.getObjectTransformer().getCircleUI().getShader());
			gl.glLineWidth(8);
		}

		/*
		if (object.areParticlesActivated()) {
			object.getParticleSystem().update();
			render(object.getParticleSystem().particles, object.getParticleSystem().getBasicShader());
		}
		 */
	}

	
	public void render(TriangleModel model,BasicShader shader) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
//		shader.use();
		
		model.getMesh().update();	
		
		shader.uploadMaterial(model.getMaterial());		
		shader.uploadLights();
					
		model.updateMatrix();
		shader.uploadModelMatrix(model.getModelMatrix());
		
		camera.updateMatrix();
		shader.uploadViewMatrix(camera.getViewMatrix());
			
		Matrix4f.changeToModelViewProjectionMatrix(camera.getViewMatrix(), model.getModelMatrix(),projectionMatrix, modelViewProjectionMatrix);		
		shader.uploadModelViewProjectionMatrix(modelViewProjectionMatrix);
				
		shader.uploadProjectionMatrix(projectionMatrix);
		
		if(model.getMesh().getTextureFilePath() != null)
			gl.glBindTexture(GL_TEXTURE_2D, model.getMesh().getTextureID());
		
		gl.glBindVertexArray(model.getMesh().getVaoID());
		gl.glDrawElements(GL_TRIANGLES, model.getMesh().getIndexCount(), GL_UNSIGNED_INT, 0);  	
	}
	
	public void render(LineModel model,BasicShader shader) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		
		model.getMesh().update();	
		
		shader.use(); 
		shader.uploadLights();
					
		model.updateMatrix();
		shader.uploadModelMatrix(model.getModelMatrix());
		
		camera.updateMatrix();
		shader.uploadViewMatrix(camera.getViewMatrix());
			
		Matrix4f.changeToModelViewProjectionMatrix(camera.getViewMatrix(), model.getModelMatrix(),projectionMatrix, modelViewProjectionMatrix);		
		shader.uploadModelViewProjectionMatrix(modelViewProjectionMatrix);
				
		shader.uploadProjectionMatrix(projectionMatrix);
			
		gl.glBindVertexArray(model.getMesh().getVaoID());
		gl.glDrawArrays(GL_LINE_LOOP, 0,model.getMesh().getVertices().length/3);
		gl.glDrawArrays(GL_POINTS, 0,model.getMesh().getVertices().length/3);
	}
	

	public void render(InstancedModel model, BasicShader shader) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		
		model.update();
		
		float[] matrixData = new float[16*model.getInstances()];
			
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
		
		shader.uploadMaterial(model.getMaterial());		
		
		for (int j = 0; j < model.getInstances(); j++) {		
						
			//reset matrix
			model.getModelMatrix()[j].setIdentityMatrix();													
										
			//translation
			model.getModelMatrix()[j].translate(model.getX(j),model.getY(j),model.getZ(j)); 
					
			//rotation
			model.getModelMatrix()[j].rotateX(model.getRotationX()[j]);
			model.getModelMatrix()[j].rotateY(model.getRotationY()[j]);
			model.getModelMatrix()[j].rotateZ(model.getRotationZ()[j]);
					
			//scale
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
					
			loadToGPU.updateVBO(model.getMatrixVBOID(),matrixData);
										
			gl.glBindVertexArray(model.getMesh().getVaoID()); //activates the specific VAO
			gl.glDrawElementsInstanced(GL_TRIANGLES,  model.getMesh().getIndexCount(), GL_UNSIGNED_INT, 0, model.getInstances());			
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
	
	public void updateProjectionMatrix() {
		projectionMatrix.changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,Config.CANVAS_HEIGHT,Config.CANVAS_WIDTH);
	}
	
}
