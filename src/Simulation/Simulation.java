package Simulation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ConcurrentModificationException;
import java.util.Properties;

import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import Simulation.Objects.GameObject;
import Simulation.RenderEngine.Core.Config;
import Simulation.RenderEngine.Core.Camera.Camera;
import Simulation.RenderEngine.Core.Lights.AmbientLight;
import Simulation.RenderEngine.Core.Lights.DirectionalLight;
import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Math.Vector4f;
import Simulation.RenderEngine.Core.Models.LineModel;
import Simulation.RenderEngine.Core.Renderer.Renderer;
import Simulation.RenderEngine.Core.Shaders.Core.BasicShader;
import UI.Util;
import javafx.embed.swing.SwingNode;

public class Simulation {
 
	private static FPSAnimator animator;
	private static GLJPanel canvas;
	private static Renderer renderer;
	private static Camera camera;
	
	private static LineModel tmp;
	private static BasicShader tmpShader;
	
	private static String level;
	private static boolean importLevel = false;
	
	public static void initialize() {
		Util.canvasWrapper = new SwingNode();
		Util.canvasWrapper.setStyle("-fx-translate-x: 270; -fx-scale-x: 1.4; -fx-scale-y: 1.4;");		
//		Util.canvasWrapper.setStyle("-fx-translate-x: 150;");	
		createGLEventListener();
		animator = new FPSAnimator(canvas, Config.FRAME_RATE);
		
		Properties props = System.getProperties(); 
		props.setProperty("swing.jlf.contentPaneTransparent", "true");
	}
	
	public static void start() {
		animator.start();
	}
	
	public static void start(String levelpath) {
		importLevel = true;
		level = levelpath;
		animator.start();
	}
		
	public static void createGLEventListener() {
		GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
		capabilities.setAlphaBits(8);
		capabilities.setBackgroundOpaque(false);
		capabilities.setSampleBuffers(true);
		capabilities.setNumSamples(4);
		canvas = new GLJPanel(capabilities);	   
		canvas.setOpaque(false);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Util.canvasWrapper.setContent(canvas);	
		    }
		});	
	
		canvas.addGLEventListener(new GLEventListener() {
			
			@Override
			public void display(GLAutoDrawable drawable) {
				renderer.clear();

				if(importLevel) {
					importLevel=false;
					LevelExportImport.ImportLevel(level);	
				}
				
				try {
					for (GameObject object : GameObject.allObjects) 
						renderer.render(object, object.getShader()); 
				} catch (ConcurrentModificationException e) {}
				
				renderer.render(tmp,tmpShader);
			}
			
			@Override
			public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
				if (width != height) {
					if (width < height) 
						height = width;		
					else
						width = height; 
				}
				
//				GL4 gl=(GL4)GLContext.getCurrentGL();
//				gl.glViewport(0, 0, width, height);
				Config.CANVAS_HEIGHT=height;
				Config.CANVAS_WIDTH=width;
				canvas.setSize(width, height);
				renderer.updateProjectionMatrix();
			}
			
			@Override
			public void init(GLAutoDrawable drawable) {
				Config.BACK_FACE_CULLING = false;
				Config.BACKGROUND_COLOR = new Vector4f(1,1,1,0.0f);
				Config.CANVAS_HEIGHT =canvas.getHeight();
				Config.CANVAS_WIDTH = canvas.getWidth();
					
				camera= new Camera(canvas);
				camera.setZ(1f);
				
				renderer = new Renderer(camera);	

				DirectionalLight.getDirectionalLights().clear();
				
				new AmbientLight(1);    
				
			  //new DirectionalLight(lightDirection,         diffuseColor,          speculaColor)
				new DirectionalLight(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));
				
				tmp = new LineModel(new float[]{0,0,0}, 0, 0, 0, -1000, -10000); 
				tmpShader= new BasicShader("Line");
				
				canvas.addKeyListener(new KeyListener() {
					public void keyTyped(KeyEvent e) {}
					public void keyReleased(KeyEvent e) {}
					
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_SPACE) {
							camera.setRotateX(0);
							camera.setRotateY(0);
							camera.setRotateZ(0);
							camera.setZ(1);
							camera.setX(0);
							camera.setY(0);					
						}
					}
				});
			}
			
			@Override
			public void dispose(GLAutoDrawable drawable) {				
				SimulationControler.pause();
				GameObject.allObjects.clear();
				animator.stop();
			}
						
		});	
	}
	
}
