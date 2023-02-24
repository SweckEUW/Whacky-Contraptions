package Computergrafik.UniverseSimulation;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import Bildverarbeitung.dataclasses.PlanetData;
import Computergrafik.Engine.MousePicker;
import Computergrafik.Engine.SkyBox;
import Computergrafik.Engine.Core.Config;
import Computergrafik.Engine.Core.Camera.Camera;
import Computergrafik.Engine.Core.Lights.AmbientLight;
import Computergrafik.Engine.Core.Lights.DirectionalLight;
import Computergrafik.Engine.Core.Lights.PointLight;
import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.loadToGPU;
import Computergrafik.Engine.Core.Renderer.Renderer;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Core.Shaders.PlanetShader.SkyBoxShader.SkyBoxShader;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.Core.PlanetNames;

public class UniverseSimulation implements GLEventListener {

	private JFrame mainFrame;
	private JLayeredPane mainPane;
	private GLCanvas myCanvas; 
	
	private ArrayList<PlanetData> data;
	
	private Renderer renderer;
	private BasicShader planetShader;
	private Camera camera;
	private Matrix4f projectionMatrix;
	private SkyBox skyBox;
	private SkyBoxShader skyBoxShader;
	private UniverseUserInterface userInterface;
	
	public UniverseSimulation(JFrame mainFrame,ArrayList<PlanetData> data) {
		this.mainFrame = mainFrame;
		this.data=data;
			
		initWindow();
			
		FPSAnimator animator =new FPSAnimator(myCanvas,Config.FRAME_RATE);
		
		mainFrame.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	            	if (animator.isStarted()) 
	                        animator.stop();
	            }
		});
			
		animator.start();	
	}
		
	
	@Override
	public void display(GLAutoDrawable drawable) {	
		renderer.clear();	
		
		renderer.render(skyBox, skyBoxShader);
		
		for (Planet planet : Planet.getAllPlanets()) 
			renderer.render(planet, planetShader);
	
		camera.update();
		
		MousePicker.update();
	}	
	

	@Override
	public void dispose(GLAutoDrawable drawable) {
		loadToGPU.cleanUp();
		Planet.getAllPlanets().clear();
		PointLight.getPointLights().clear();
		DirectionalLight.getDirectionalLights().clear();
	}

	@SuppressWarnings("unused")
	@Override
	public void init(GLAutoDrawable drawable) {	
		projectionMatrix=new Matrix4f();
		PlanetNames.initNames();
		Config.BACKGROUND_COLOR= new Vector3f();
		Config.BACK_FACE_CULLING=false;
		
		projectionMatrix=new Matrix4f();
		planetShader=new BasicShader("PlanetShader/Planet");
		skyBoxShader= new SkyBoxShader();
		skyBox=new SkyBox();
		camera= new Camera(myCanvas,0,5,130,-35,0,0);

		AmbientLight ambientLight=new AmbientLight(1);
				
		projectionMatrix.changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,myCanvas.getHeight(),myCanvas.getWidth());
		renderer = new Renderer(camera,projectionMatrix);		
		
		GeneratePlanets.generatePlanets(data);
		
		MousePicker.initMousePicker(camera, projectionMatrix, Planet.getAllPlanets());
		
		userInterface = new UniverseUserInterface(mainPane,mainFrame,camera);
		
		mainFrame.getContentPane().remove(0);
		mainFrame.revalidate();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glViewport(0, 0, width, height);
		Config.CANVAS_HEIGHT=height;
		Config.CANVAS_WIDTH=width;
		renderer.getProjectionMatrix().changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,myCanvas.getHeight(),myCanvas.getWidth());
		MousePicker.setProjectionMatrix(renderer.getProjectionMatrix());
		myCanvas.setSize(width, height);
	}
	
	@SuppressWarnings("deprecation")
	private void initWindow() {
		myCanvas = new GLCanvas();
		myCanvas.setBounds(0,0,Config.CANVAS_WIDTH,Config.CANVAS_HEIGHT);
		myCanvas.addGLEventListener(this);
		myCanvas.setFocusable(true);
		
		mainPane = new JLayeredPane();	
		mainPane.setBackground(Color.white);
		mainPane.addComponentListener(new ComponentListener() {			
			@Override
			public void componentShown(ComponentEvent e) {}		
			@Override
			public void componentResized(ComponentEvent e) {
				for (int i = 0; i < mainPane.getComponentCount(); i++) 
					if (mainPane.getComponent(i)instanceof GLCanvas ) 
						mainPane.getComponent(i).reshape(mainPane.getComponent(i).getX(), mainPane.getComponent(i).getY(), mainFrame.getWidth(), mainFrame.getHeight());}
			@Override
			public void componentMoved(ComponentEvent e) {}		
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		mainPane.add(myCanvas,new Integer(-100));
		
		mainFrame.getContentPane().add(mainPane);	
		mainFrame.revalidate();
	}
	
}
