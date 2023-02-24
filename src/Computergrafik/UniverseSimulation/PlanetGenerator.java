package Computergrafik.UniverseSimulation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

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
import Computergrafik.Engine.Planet.ExportPlanetAsOBJ;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.Core.PlanetNames;
import Computergrafik.Engine.Planet.PlanetVariations.EarthLike.Earth.Earth;
import UniverseSimulationMain.MainMenue;
 
/**
 * Class for generating earhlike planets using multiple sliders.
 * @author Simon Weck
 *
 */
public class PlanetGenerator implements GLEventListener {

	private JFrame mainFrame;
	private JLayeredPane mainPane;
	private GLCanvas myCanvas;
	
	
	private Renderer renderer;
	private BasicShader planetShader;
	private Camera camera;
	private Matrix4f projectionMatrix; 
	private Earth planet; 
	private SkyBox skyBox;
	private SkyBoxShader skyboxShader;
	
	private boolean generate; 
	private int planetres=50;
	private float planetsize=1;
	private float noiseStrenght=1;
	private float noiseRoughness=2;
	private float noiseOffsetX=(float)Math.random()*9999;
	private float noiseOffsetY=(float)Math.random()*9999;
	private float noiseOffsetZ=(float)Math.random()*9999;
	private float randomNoiseOffsetX;
	private float randomNoiseOffsetY;
	private float randomNoiseOffsetZ;
	private int noiseLayers=4;
	private float noisePeristance=0.5f;
	private float noiseBaseRoughness=1;
	private float noiseMinValue;	
	private float colorOffsetf=0.7f;
	
	
		
	public PlanetGenerator(JFrame mainFrame) {
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
		
		if (generate) {
			generate=false;
			planet.generateRandom(planetres, planetsize, noiseStrenght, noiseRoughness, new Vector3f(noiseOffsetX, noiseOffsetY, noiseOffsetZ), generate,noiseLayers,noisePeristance,noiseBaseRoughness,noiseMinValue,colorOffsetf);
		}
		
		renderer.render(skyBox, skyboxShader);
				
		renderer.render(planet, planetShader);	
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		loadToGPU.cleanUp();
		Planet.getAllPlanets().clear();
		PointLight.getPointLights().clear();
		DirectionalLight.getDirectionalLights().clear();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		PlanetNames.initNames();
		Config.BACKGROUND_COLOR=new Vector3f(25f/255f);
		projectionMatrix=new Matrix4f();
		planetShader=new BasicShader("PlanetShader/Planet");
		skyboxShader=new SkyBoxShader();
		camera= new Camera(myCanvas,0,0,5,0,0,0);
		camera.getCameraControls().setZinc(0.01f);
		skyBox = new SkyBox();
		
		@SuppressWarnings("unused")
		AmbientLight ambientlight = new AmbientLight(1);
		
	                                    //new DirectionalLight(lightDirection,         diffuseColor,          speculaColor)
		@SuppressWarnings("unused")
		DirectionalLight directionallight=new DirectionalLight(new Vector3f(0, 0, -5), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));
			
			 				   //new Material(emissionColor,         ambientColor,                 diffuseColor,          specularColor,                shininess)
		Material basicMaterial = new Material(new Vector3f(0), new Vector3f(0.2f), new Vector3f(1), new Vector3f(0.3f,0.1f,0.1f), 16);
		
		projectionMatrix.changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,myCanvas.getHeight(),myCanvas.getWidth());
		renderer = new Renderer(camera,projectionMatrix);		
			
		planet=new Earth(new Vector3f(), 1, basicMaterial);
		
		createControls();
		
		camera.getCameraControls().setStopTranslationControl(true);
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
		myCanvas.setSize(width, height);
	}
	
	public void createControls() {
		
		LookAndFeel originalLookAndFeel = UIManager.getLookAndFeel();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		JLabel BackFaceCullingText = new JLabel("Backface Culling");
		BackFaceCullingText.setForeground(Color.white);
		
		JCheckBox BackFaceCulling = new JCheckBox();
		BackFaceCulling.setBackground(new Color(0,0,0,255));
		BackFaceCulling.setSelected(true);
		BackFaceCulling.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (BackFaceCulling.isSelected()) {
					BackFaceCulling.setSelected(true);
					Config.BACK_FACE_CULLING=true;
				}else {
					BackFaceCulling.setSelected(false);
					Config.BACK_FACE_CULLING=false;
				}
			}
		});
		
		
		JPanel panelBackFaceCulling = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBackFaceCulling.setMaximumSize(new Dimension(300,40));
		panelBackFaceCulling.add(BackFaceCulling);
		panelBackFaceCulling.add(BackFaceCullingText);
		panelBackFaceCulling.setBackground(new Color(0,0,0,255));
		panelBackFaceCulling.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel wireframeText = new JLabel("Wireframe Mode");
		wireframeText.setForeground(Color.white);
		
		JCheckBox wireframe = new JCheckBox();
		wireframe.setBackground(new Color(0,0,0,255));
		wireframe.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (wireframe.isSelected()) {
					wireframe.setSelected(true);
					Config.WIREFRAME_MODE=true;
				}else {
					wireframe.setSelected(false);
					Config.WIREFRAME_MODE=false;
				}
			}
		});
		
		JPanel panelWireframe = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelWireframe.setMaximumSize(new Dimension(300, 40));
		panelWireframe.add(wireframe);
		panelWireframe.add(wireframeText);
		panelWireframe.setBackground(new Color(0,0,0,255));
		panelWireframe.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel stopText = new JLabel("Stop Rotation");
		stopText.setForeground(Color.white);
		
		JCheckBox stop = new JCheckBox();
		stop.setBackground(new Color(0,0,0,255));
		stop.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stop.isSelected()) 
					planet.setStopUpdate(true);
				else 
					planet.setStopUpdate(false);
				
			}
		});
		
		JPanel panelStop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelStop.setMaximumSize(new Dimension(300, 40));
		panelStop.add(stop);
		panelStop.add(stopText);
		panelStop.setBackground(new Color(0,0,0,255));
		panelStop.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel PlanetResolutionText = new JLabel("Planet resolution: 50");
		PlanetResolutionText.setForeground(Color.white);
		PlanetResolutionText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL,2,300,50);
		slider.setPaintTicks(true); 
		slider.setMinorTickSpacing(10);
		slider.setBackground(new Color(0,0,0,255));
		slider.setAlignmentX(Component.LEFT_ALIGNMENT);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				planetres=slider.getValue();
				PlanetResolutionText.setText("Planet Resolution: "+(slider.getValue()-1));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel PlanetSizeText = new JLabel("Planet Size: 1");
		PlanetSizeText.setForeground(Color.white);
		PlanetSizeText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderSize = new JSlider(JSlider.HORIZONTAL,0,20,10);
		sliderSize.setPaintTicks(true); 
		sliderSize.setMinorTickSpacing(1);
		sliderSize.setBackground(new Color(0,0,0,255));
		sliderSize.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				planet.setScale((float)sliderSize.getValue()/(float)10);
				PlanetSizeText.setText("Planet Size: "+((float)sliderSize.getValue()/(float)10));				
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noiseStrength = new JLabel("Noise Strength: 1");
		noiseStrength.setForeground(Color.white);
		noiseStrength.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderstrenght = new JSlider(JSlider.HORIZONTAL,0,40,10);
		sliderstrenght.setPaintTicks(true); 
		sliderstrenght.setMinorTickSpacing(1);
		sliderstrenght.setBackground(new Color(0,0,0,255));
		sliderstrenght.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderstrenght.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				noiseStrenght=(float)sliderstrenght.getValue()/(float)10;
				noiseStrength.setText("Noise Strength: "+((float)sliderstrenght.getValue()/(float)10));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noiseRoughnessT= new JLabel("Noise Roughness: 2");
		noiseRoughnessT.setForeground(Color.white);
		noiseRoughnessT.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderRoughness = new JSlider(JSlider.HORIZONTAL,0,40,20);
		sliderRoughness.setPaintTicks(true); 
		sliderRoughness.setMinorTickSpacing(1);
		sliderRoughness.setBackground(new Color(0,0,0,255));
		sliderRoughness.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderRoughness.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				noiseRoughness=(float)sliderRoughness.getValue()/(float)10;
				noiseRoughnessT.setText("Noise Roughness: "+((float)sliderRoughness.getValue()/(float)10));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noiseLayersT= new JLabel("Noise Layers: 4");
		noiseLayersT.setForeground(Color.white);
		noiseLayersT.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderNoiseLayers = new JSlider(JSlider.HORIZONTAL,0,10,4);
		sliderNoiseLayers.setPaintTicks(true); 
		sliderNoiseLayers.setMinorTickSpacing(1);
		sliderNoiseLayers.setBackground(new Color(0,0,0,255));
		sliderNoiseLayers.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderNoiseLayers.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				noiseLayers=sliderNoiseLayers.getValue();
				noiseLayersT.setText("Noise Layers: "+(float)sliderNoiseLayers.getValue());
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noisePersistanceT= new JLabel("Noise Persistance: 0.5");
		noisePersistanceT.setForeground(Color.white);
		noisePersistanceT.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderNoisePersistance = new JSlider(JSlider.HORIZONTAL,0,30,5);
		sliderNoisePersistance.setPaintTicks(true); 
		sliderNoisePersistance.setMinorTickSpacing(1);
		sliderNoisePersistance.setBackground(new Color(0,0,0,255));
		sliderNoisePersistance.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderNoisePersistance.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				noisePeristance=(float)sliderNoisePersistance.getValue()/(float)10;
				noisePersistanceT.setText("Noise Persistance: "+((float)sliderNoisePersistance.getValue()/(float)10));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noiseBaseRoughnessT= new JLabel("Noise base Roughness: 1");
		noiseBaseRoughnessT.setForeground(Color.white);
		noiseBaseRoughnessT.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderNoiseBaseRoughness = new JSlider(JSlider.HORIZONTAL,0,30,10);
		sliderNoiseBaseRoughness.setPaintTicks(true); 
		sliderNoiseBaseRoughness.setMinorTickSpacing(1);
		sliderNoiseBaseRoughness.setBackground(new Color(0,0,0,255));
		sliderNoiseBaseRoughness.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderNoiseBaseRoughness.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				noiseBaseRoughness=(float)sliderNoiseBaseRoughness.getValue()/(float)10;
				noiseBaseRoughnessT.setText("Noise base Roughness: "+((float)sliderNoiseBaseRoughness.getValue()/(float)10));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noiseMinValueT= new JLabel("Noise min Value: 0");
		noiseMinValueT.setForeground(Color.white);
		noiseMinValueT.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderNoiseMinValue = new JSlider(JSlider.HORIZONTAL,0,30,0);
		sliderNoiseMinValue.setPaintTicks(true); 
		sliderNoiseMinValue.setMinorTickSpacing(1);
		sliderNoiseMinValue.setBackground(new Color(0,0,0,255));
		sliderNoiseMinValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderNoiseMinValue.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				noiseMinValue=(float)sliderNoiseMinValue.getValue()/(float)10;
				noiseMinValueT.setText("Noise min Value: "+((float)sliderNoiseMinValue.getValue()/(float)10));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noiseX = new JLabel("Noise X: 0");
		noiseX.setForeground(Color.white);
		noiseX.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderX = new JSlider(JSlider.HORIZONTAL,0,300,0);
		sliderX.setPaintTicks(true); 
		sliderX.setMinorTickSpacing(10);
		sliderX.setBackground(new Color(0,0,0,255));
		sliderX.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderX.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {	
				noiseOffsetX=(float)sliderX.getValue()/(float)10+randomNoiseOffsetX;			
				noiseX.setText("Noise X: "+((float)sliderX.getValue()/(float)10));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noiseY = new JLabel("Noise Y: 0");
		noiseY.setForeground(Color.white);
		noiseY.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderY = new JSlider(JSlider.HORIZONTAL,0,300,0);
		sliderY.setPaintTicks(true); 
		sliderY.setMinorTickSpacing(10);
		sliderY.setBackground(new Color(0,0,0,255));
		sliderY.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderY.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				noiseOffsetY=(float)sliderY.getValue()/(float)10+randomNoiseOffsetY;
				noiseY.setText("Noise Y: "+((float)sliderY.getValue()/(float)10));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel noiseZ = new JLabel("Noise Z: 0");
		noiseZ.setForeground(Color.white);
		noiseZ.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider sliderZ = new JSlider(JSlider.HORIZONTAL,0,300,0);
		sliderZ.setPaintTicks(true); 
		sliderZ.setMinorTickSpacing(10);
		sliderZ.setBackground(new Color(0,0,0,255));
		sliderZ.setAlignmentX(Component.LEFT_ALIGNMENT);
		sliderZ.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				noiseOffsetZ=(float)sliderZ.getValue()/(float)10+randomNoiseOffsetZ;
				noiseZ.setText("Noise Z: "+((float)sliderZ.getValue()/(float)10));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JLabel colorOffsetT = new JLabel("Color offset: 0.7");
		colorOffsetT.setForeground(Color.white);
		colorOffsetT.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider colorOffset = new JSlider(JSlider.HORIZONTAL,0,100,70);
		colorOffset.setPaintTicks(true); 
		colorOffset.setMinorTickSpacing(100);
		colorOffset.setBackground(new Color(0,0,0,255));
		colorOffset.setAlignmentX(Component.LEFT_ALIGNMENT);
		colorOffset.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				colorOffsetf=(float)colorOffset.getValue()/(float)100;
				colorOffsetT.setText("Color offset: "+((float)colorOffset.getValue()/(float)100));
				PlanetGenerator.this.generate=true;
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 10, 300, 750);
		panel.setBackground(new Color(0,0,0,255));		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		
		try {
			UIManager.setLookAndFeel(originalLookAndFeel);
		} catch (UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}
	
		JButton generate = new JButton("Generate");
		generate.setForeground(new Color(255,0,0));
		generate.setBackground(new Color(0,0,0,255));
		generate.setMaximumSize(new Dimension(300, 40));
		generate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PlanetGenerator.this.generate=true;
				noiseOffsetX-=randomNoiseOffsetX;
				noiseOffsetY-=randomNoiseOffsetY;
				noiseOffsetZ-=randomNoiseOffsetZ;
				randomNoiseOffsetX=(float)Math.random()*9999;
				randomNoiseOffsetY=(float)Math.random()*9999;
				randomNoiseOffsetZ=(float)Math.random()*9999;		
				noiseOffsetX+=randomNoiseOffsetX;
				noiseOffsetY+=randomNoiseOffsetY;
				noiseOffsetZ+=randomNoiseOffsetZ;
				PlanetGenerator.this.generate=true;
			}
		});
		
		JButton generateRandom = new JButton("Generate Random");
		generateRandom.setForeground(new Color(255,0,0));
		generateRandom.setBackground(new Color(0,0,0,255));
		generateRandom.setMaximumSize(new Dimension(300, 40));
		generateRandom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PlanetGenerator.this.generate=true;
				noiseOffsetX-=randomNoiseOffsetX;
				noiseOffsetY-=randomNoiseOffsetY;
				noiseOffsetZ-=randomNoiseOffsetZ;
				randomNoiseOffsetX=(float)Math.random()*9999;
				randomNoiseOffsetY=(float)Math.random()*9999;
				randomNoiseOffsetZ=(float)Math.random()*9999;		
				noiseOffsetX+=randomNoiseOffsetX;
				noiseOffsetY+=randomNoiseOffsetY;
				noiseOffsetZ+=randomNoiseOffsetZ;
				
				planetsize=(float)(Math.random()*(1.3f-0.5f)+0.5f);
				sliderSize.setValue((int)(planetsize*10));			
				PlanetSizeText.setText("Planet Size: "+planetsize);
				
				noiseStrenght=(float)(Math.random()*(0.8f-0.3f)+0.3f);
				sliderstrenght.setValue((int)(noiseStrenght*10));
				noiseStrength.setText("Noise Strength: "+noiseStrenght);
				
				noiseRoughness=(float)(Math.random()*(2f-1.5f)+1.5f);
				sliderRoughness.setValue((int)(noiseRoughness*10));
				noiseRoughnessT.setText("Noise Roughness: "+noiseRoughness);
				
				noiseBaseRoughness=(float)(Math.random()*(1f-0.8f)+0.8f);
				sliderNoiseBaseRoughness.setValue((int)(noiseBaseRoughness*10));
				noiseBaseRoughnessT.setText("Noise base Roughness: "+noiseBaseRoughness);
				
				noiseLayers=(int)(Math.random()*(7-5)+5);
				sliderNoiseLayers.setValue(noiseLayers);
				noiseLayersT.setText("Noise Layers: "+noiseLayers);				
				
				noisePeristance=(float)(Math.random()*(0.7f-0.4f)+0.4f);
				sliderNoisePersistance.setValue((int)(noisePeristance*10));
				noisePersistanceT.setText("Noise Persistance: "+noisePeristance);
				
				noiseMinValue=(float)(Math.random()*(0.6f-0.2f)+0.2f);
				sliderNoiseMinValue.setValue((int)(noiseMinValue*10));
				noiseMinValueT.setText("Noise min Value: "+noiseMinValue);
				
				noiseMinValue=(float)(Math.random()*(0.6f-0.2f)+0.2f);
				sliderNoiseMinValue.setValue((int)(noiseMinValue*10));
				noiseMinValueT.setText("Noise min Value: "+noiseMinValue);
				
				colorOffsetf = (float)(Math.random()*(1f-0.6f)+0.6f);
				colorOffset.setValue((int)(colorOffsetf*100));
				colorOffsetT.setText("Color offset: "+colorOffsetf);
							
				planetres = (int)(planetsize*50);
				slider.setValue((int)(planetres));
				PlanetResolutionText.setText("Planet Resolution: "+planetres);
				
				PlanetGenerator.this.generate=true;
				
			}
		});
		
		JButton exportToObj = new JButton("Export Planet to Obj");
		exportToObj.setForeground(new Color(255,0,0));
		exportToObj.setBackground(new Color(0,0,0,255));
		exportToObj.setMaximumSize(new Dimension(300, 40));
		exportToObj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ExportPlanetAsOBJ.export(planet);
				} catch (IOException e1) {
					e1.printStackTrace();
				}		
			}
		});
		
		
			
		panel.add(PlanetResolutionText);
		panel.add(slider);
		panel.add(PlanetSizeText);
		panel.add(sliderSize);
		panel.add(noiseStrength);
		panel.add(sliderstrenght);
		panel.add(noiseRoughnessT);
		panel.add(sliderRoughness);	
		panel.add(noiseLayersT);
		panel.add(sliderNoiseLayers);
		panel.add(noisePersistanceT);
		panel.add(sliderNoisePersistance);
		panel.add(noiseBaseRoughnessT);
		panel.add(sliderNoiseBaseRoughness);
		panel.add(noiseMinValueT);
		panel.add(sliderNoiseMinValue);
		panel.add(noiseX);
		panel.add(sliderX);
		panel.add(noiseY);
		panel.add(sliderY);
		panel.add(noiseZ);
		panel.add(sliderZ);
		panel.add(colorOffsetT);
		panel.add(colorOffset);
		panel.add(panelWireframe);
		panel.add(panelBackFaceCulling);
		panel.add(panelStop);
		panel.add(generate);
		panel.add(Box.createVerticalStrut(10));
		panel.add(generateRandom);
		panel.add(Box.createVerticalStrut(10));
		panel.add(exportToObj);

		JLabel goBack = new JLabel(new ImageIcon("res/UserInterface/goBackNormal.png"));
		goBack.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {
				goBack.setIcon(new ImageIcon("res/UserInterface/goBackNormal.png"));}	
			public void mouseEntered(MouseEvent e) {
				goBack.setIcon(new ImageIcon("res/UserInterface/goBack.png"));}	
			public void mouseClicked(MouseEvent e) {
				mainFrame.getContentPane().removeAll();
				new MainMenue(mainFrame);
			}
		});
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(0, 995, 123, 50);
		panel2.setBackground(Color.black);
		panel2.add(goBack);
		
		
		mainPane.add(panel2);
		mainPane.add(panel);	
		mainFrame.revalidate();
		
		generateRandom.doClick();
		this.generate=true;
	}
	
	@SuppressWarnings("deprecation")
	private void initWindow() {			 
		myCanvas = new GLCanvas();
		myCanvas.setBounds(0,0,Config.CANVAS_WIDTH,Config.CANVAS_HEIGHT);	
		myCanvas.addGLEventListener(this);
		myCanvas.setFocusable(true);
			
		mainPane = new JLayeredPane();	
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
