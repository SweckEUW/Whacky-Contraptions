package Computergrafik.UniverseSimulation;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

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
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Core.Shaders.PlanetShader.SkyBoxShader.SkyBoxShader;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.Core.PlanetNames;
import Computergrafik.Engine.Planet.PlanetVariations.CristalPlanet.CristalPlanet;
import Computergrafik.Engine.Planet.PlanetVariations.DarkMatterPlanet.DarkMatterPlanet;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.CubicEarth.CubicEarth;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.Earth.Earth;
import Computergrafik.Engine.Planet.PlanetVariations.SinusPlanet.SinPlanet;
import Computergrafik.Engine.Planet.PlanetVariations.StonePlanet.StonePlanet;
import Computergrafik.Engine.Planet.PlanetVariations.Sun.Sun;

/**
 * Demo universe containing all 6 planet types
 * @author Simon Weck
 *
 */
public class DemoUniverse implements GLEventListener {

	private JFrame mainFrame;
	private JLayeredPane mainPane;
	private GLCanvas myCanvas; 
	
	private Renderer renderer;
	private BasicShader planetShader;
	private Camera camera;
	private Matrix4f projectionMatrix;
	private SkyBox skyBox;
	private SkyBoxShader skyBoxShader;
	private UniverseUserInterface userInterface;
	
	
	public DemoUniverse(JFrame mainFrame) {
		this.mainFrame = mainFrame;
				
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
		
		camera.update();
		
		MousePicker.update();
		
		renderer.render(skyBox, skyBoxShader);
		
		for (Planet planet : Planet.getAllPlanets()) 
			renderer.render(planet, planetShader);	
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
		Config.BACK_FACE_CULLING=false;
		
		projectionMatrix=new Matrix4f();
		planetShader=new BasicShader("PlanetShader/Planet");;
		skyBoxShader= new SkyBoxShader();
		skyBox=new SkyBox();
		camera= new Camera(myCanvas,0,5,130,-35,0,0);

		AmbientLight ambientLight=new AmbientLight(1);
				
			 				   //new Material(ambientColor,       diffuseColor,  specularColor,  shininess)
		Material basicMaterial = new Material(new Vector3f(0.1f), new Vector3f(1f), new Vector3f(0), 32);
		
		projectionMatrix.changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,myCanvas.getHeight(),myCanvas.getWidth());
		renderer = new Renderer(camera,projectionMatrix);		
				
		Earth earth = new Earth(new Vector3f(0,0,0),2f,basicMaterial);
		
		Sun sun = new Sun(earth,new Vector3f(40,0,0), 4f, new Material(new Vector3f(1, 1, 1), new Vector3f(), new Vector3f(), new Vector3f(), 0),new Vector3f(0, 1, 0));
			
		CubicEarth cubicEarth = new CubicEarth(earth,new Vector3f(-60,0,0),1f,basicMaterial,new Vector3f(0, 1, 0));
		
		SinPlanet sinusPlanet = new SinPlanet(earth, new Vector3f(0, 0, -20), 1, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 0.5f, 0.7f), 200),new Vector3f(0, 1, 0));
					
		CristalPlanet cristalPlanet = new CristalPlanet(earth, new Vector3f(0, 0, -80), 2, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(0.1f, 1, 0.1f), 50,0.4f),new Vector3f(0, 1, 0));
		
		StonePlanet stonePlanet = new StonePlanet(earth, new Vector3f(100, 0, 0), 2, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 1, 1), 0),new Vector3f(0, 1, 0));
		
		DarkMatterPlanet darkMatterPlanet = new DarkMatterPlanet(earth, new Vector3f(0, 0, 120),3, new Material(new Vector3f(0.2f), new Vector3f(0.7f), new Vector3f(1, 1, 1), 30),new Vector3f(0, 1, 0));
		
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
