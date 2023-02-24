package Simulation.BoundingCreator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import Simulation.RenderEngine.Core.Config;
import Simulation.RenderEngine.Core.Camera.Camera;
import Simulation.RenderEngine.Core.Lights.AmbientLight;
import Simulation.RenderEngine.Core.Lights.DirectionalLight;
import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Math.Vector4f;
import Simulation.RenderEngine.Core.Models.LineModel;
import Simulation.RenderEngine.Core.Models.TriangleModel;
import Simulation.RenderEngine.Core.Renderer.Renderer;
import Simulation.RenderEngine.Core.Shaders.Core.BasicShader;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.CircleLine;
import UI.Util;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application implements GLEventListener{

	private TriangleModel model;
	private ArrayList<ArrayList<Vector2f>> hulls = new ArrayList<ArrayList<Vector2f>>();
	private ArrayList<LineModel> hullsModels = new ArrayList<LineModel>();
	
	private ArrayList<Vector2f> currentHull = new ArrayList<Vector2f>();	
	private LineModel currentHullModel;
	
	private String fileName;
	private int state;
		
	//Basic 3D Scene
	private FPSAnimator animator;
	private GLJPanel canvas;
	private Renderer renderer;
	private BasicShader triangleShader;
	private BasicShader lineShader;
	private Camera camera;
	private LineModel tmp;
	

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		primaryStage.setTitle("Bounding Creator");
		primaryStage.setScene(new Scene(root, 900, 900));
		primaryStage.show();	
		
		HBox options = new HBox(10);
		options.setStyle("-fx-background-color: rgb(102,127,102);");
		
		root.setTop(options);
		
		Button reset = new Button("Reset");
		reset.setStyle("-fx-pref-height: 40px; -fx-pref-width: 150px;");
		reset.setOnAction(e->{
			hulls = new ArrayList<ArrayList<Vector2f>>();
			hullsModels = new ArrayList<LineModel>();
			currentHull = new ArrayList<Vector2f>();
			currentHullModel = null;
		});
		
		Button resetCamera = new Button("Reset Camera");
		resetCamera.setStyle("-fx-pref-height: 40px; -fx-pref-width: 150px;");
		resetCamera.setOnAction(e->{
			camera.setRotateX(0);
			camera.setRotateY(0);
			camera.setRotateZ(0);
			camera.setX(0);
			camera.setY(0);
			camera.setZ(1);
		});
		
		Button undo = new Button("Undo");
		undo.setStyle("-fx-pref-height: 40px; -fx-pref-width: 150px;");
		undo.setOnAction(e->{
			if(currentHull.size()>0) {
				currentHull.remove(currentHull.size()-1);
				float[] vertices = new float[currentHull.size()*3];	  				
	  			for (int i = 0; i < currentHull.size() ; i++) {
	  				vertices[i*3] = currentHull.get(i).x;
	  				vertices[i*3+1] = currentHull.get(i).y;
	  				vertices[i*3+2] = 0;
	  			}		  				
	  			currentHullModel= new LineModel(vertices, 0.5f, 0.5f, 0.5f, 0, 0);
			}
		});
		
		Button openFileButton = new Button("Open OBJ file");
		openFileButton.setStyle("-fx-pref-height: 40px; -fx-pref-width: 150px;");
		openFileButton.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/res"));
			fileChooser.setTitle("Open OBJ file");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("OBJ files","*.obj"));
			File file = fileChooser.showOpenDialog(primaryStage);					
			if(file!=null) {
				fileName = file.getAbsolutePath();				
				int beginIndex = fileName.lastIndexOf("res");
				int lastIndex = fileName.indexOf(".obj");				
				fileName = fileName.substring(beginIndex+4, lastIndex);
				
				model = new TriangleModel(fileName, new Material(new Vector3f(0.2f,0.2f,0.2f), new Vector3f(0.5f,0.5f,0.5f), new Vector3f(1.f, 1.f, 1.f), 0, 1), 1, 1, 1, 0, 0);
			}
		});
		
		Button save = new Button("Save");
		save.setStyle("-fx-pref-height: 40px; -fx-pref-width: 150px;");
		save.setOnAction(e->{
			try {
				FileOutputStream file = new FileOutputStream(System.getProperty("user.dir")+"/res/"+fileName+".txt");
				for (ArrayList<Vector2f> hull : hulls) {
					file.write("#New BoundingPolygon".getBytes());
					file.write("\n".getBytes());
					for (Vector2f p : hull) {
						String string = "v "+ p.x + " / " + p.y;
						file.write(string.getBytes());
						file.write("\n".getBytes());
					}
				}
				
				file.close();
				
			} catch (FileNotFoundException e1) {e1.printStackTrace();} catch (IOException e1) {e1.printStackTrace();}
		});
		
		options.getChildren().add(openFileButton);
		options.getChildren().add(resetCamera);
		options.getChildren().add(undo);
		options.getChildren().add(reset);
		options.getChildren().add(save);
		
		//JFX Code für Canvas
		final GLCapabilities capabilities = new GLCapabilities( GLProfile.getDefault());
		canvas = new GLJPanel(capabilities);	    
		SwingNode swingNode = new SwingNode();		 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		    	swingNode.setContent(canvas);	
		    }
		});	
		canvas.addGLEventListener(this);		
		animator = new FPSAnimator(canvas, 60);
	  	animator.start();
	  	
	  	root.setCenter(swingNode);


	  	canvas.addMouseListener(new MouseListener() {
	  		
	  		public void mouseClicked(MouseEvent e) {
	  			if(e.getButton() == MouseEvent.BUTTON1) {
	  				float x = Util.convertMouseX(e.getX(), camera);
					float y = Util.convertMouseY(e.getY(), camera);
		  			  			
		  			if(state>0) {
		  				if (currentHull.size() > 0) {
		  					currentHull.set(currentHull.size()-1,new Vector2f(x, y));
				  			currentHull.add(new Vector2f(x, y));
						}	
		  			}else {
		  				currentHull.add(new Vector2f(x, y));
		  				currentHull.add(new Vector2f(x, y));
					}
		  			
		  			float[] vertices = new float[currentHull.size()*3];	  				
		  			for (int i = 0; i < currentHull.size() ; i++) {
		  				vertices[i*3] = currentHull.get(i).x;
		  				vertices[i*3+1] = currentHull.get(i).y;
		  				vertices[i*3+2] = 0;
		  			}		  				
		  			currentHullModel= new LineModel(vertices,  1, 0.4f, 0.8f, 0, 0);
		  			
		  			state++;
		  		}
	  			
	  			if(e.getButton() == MouseEvent.BUTTON3) {
	  				currentHull.remove(currentHull.size()-1);
	  				
	  				float[] vertices = new float[currentHull.size()*3];	  				
		  			for (int i = 0; i < currentHull.size() ; i++) {
		  				vertices[i*3] = currentHull.get(i).x;
		  				vertices[i*3+1] = currentHull.get(i).y;
		  				vertices[i*3+2] = 0;
		  			}		  				
		  			LineModel model = new LineModel(vertices,  1, 1, 1, 0, 0);
		  			hullsModels.add(model);
		  			
		  			currentHullModel = null;
		  			
	  				hulls.add(currentHull);
	  				currentHull = new ArrayList<Vector2f>();
	  				state=0;
	  			}
			}

			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
		});
	  		
	  	canvas.addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent e) {
				if(currentHull.size()>0) {
					float x = Util.convertMouseX(e.getX(), camera);
					float y = Util.convertMouseY(e.getY(), camera);
		  					  		  			
		  			currentHull.set(currentHull.size()-1, new Vector2f(x, y));
		  			
		  			float[] vertices = new float[currentHull.size()*3];	  				
		  			for (int i = 0; i < currentHull.size() ; i++) {
		  				vertices[i*3] = currentHull.get(i).x;
		  				vertices[i*3+1] = currentHull.get(i).y;
		  				vertices[i*3+2] = 0;
		  			}		  				
		  			currentHullModel= new LineModel(vertices, 1, 0.4f, 0.8f, 0, 0);
				}
			}
			
			public void mouseDragged(MouseEvent e) {}
		});
	  	
	}
	
	
	@Override
	public void display(GLAutoDrawable arg0) {
		renderer.clear();			
		
		renderer.render(model, triangleShader); 
				
		for (LineModel model : hullsModels) 
			renderer.render(model, lineShader);
		
		if(currentHullModel!=null)
			renderer.render(currentHullModel, lineShader);
				
		renderer.render(tmp,lineShader);			
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		animator.stop();
	}

	@SuppressWarnings("unused")
	@Override
	public void init(GLAutoDrawable arg0) {
		Config.BACKGROUND_COLOR = new Vector4f(0.4f,0.5f,0.4f,1);
		Config.CANVAS_HEIGHT = canvas.getHeight();
		Config.CANVAS_WIDTH = canvas.getWidth();
		Config.LINE_WIDTH= 4;
		
		camera= new Camera(canvas);
		camera.setZ(1);
		
		renderer = new Renderer(camera);	
		
		triangleShader = new BasicShader("PhongColor");
		lineShader = new BasicShader("Line");
		
		AmbientLight ambientLight = new AmbientLight(1);    
		
		//								    new DirectionalLight(lightDirection,         diffuseColor,          speculaColor)
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));
		
		model = new TriangleModel("/Models/PlaneTriangles", new Material(new Vector3f(0.2f,0.2f,0.2f), new Vector3f(0.5f,0.5f,0.5f), new Vector3f(1.f, 1.f, 1.f), 0, 1), 1, 1, 1, 0, 0);
		
		tmp = new LineModel(new CircleLine(0,0),0,0,0,0,0);
	}
	

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glViewport(0, 0, width, height);
		Config.CANVAS_HEIGHT=height;
		Config.CANVAS_WIDTH=width;
		renderer.updateProjectionMatrix();
	}
	
}