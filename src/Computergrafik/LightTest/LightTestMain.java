package Computergrafik.LightTest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
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

import Computergrafik.Engine.Core.Config;
import Computergrafik.Engine.Core.Camera.Camera;
import Computergrafik.Engine.Core.Lights.AmbientLight;
import Computergrafik.Engine.Core.Lights.DirectionalLight;
import Computergrafik.Engine.Core.Lights.PointLight;
import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Renderer.Renderer;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Core.Shaders.Core.Material;

/**
 * Class for testing Phong lighting with multiple point lights and directional light
 * @author Simon Weck
 *
 */
public class LightTestMain implements GLEventListener {

	private GLCanvas myCanvas;
	private JFrame mainFrame;
	private JLayeredPane mainPane;
	

	private Renderer renderer;
	private BasicShader shader;
	private Camera camera;
	private Matrix4f projectionMatrix;	
	
	private PointLight pointLight1;
	private Model cubePointLight;
	private ArrayList<PointLight> pointLights;
	private ArrayList<Model> pointLightCubes;
	
	private DirectionalLight directionalLight;
	private Model arrowDirectionalLight;
	
	private Model dragon;
	private int update=2; //1 = DirectionalLight , 2 = PointLight
	
	
	public static void main(String[] args) {
		new LightTestMain();
	}
	
	public LightTestMain() {
	 
		initWindow();
		
		FPSAnimator animator =new FPSAnimator(myCanvas,Config.FRAME_RATE);
		
		mainFrame.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	            	if (animator.isStarted()) 
	                        animator.stop();
	                System.exit(0);
	            }
		});			
		
		animator.start();
	}
		
	@Override
	public void display(GLAutoDrawable drawable) {
		
		renderer.clear();	
		
		//render DirectionalLight and arrow
		if (update==1) {
			DirectionalLight.getDirectionalLights().clear();
			PointLight.getPointLights().clear();
			DirectionalLight.getDirectionalLights().add(directionalLight);
			renderer.render(arrowDirectionalLight, shader);
			
		}
		
		//render PointLights and cubes
		if (update==2) {
			DirectionalLight.getDirectionalLights().clear();
			PointLight.getPointLights().clear();
			for (PointLight pointLight : pointLights) 
				PointLight.getPointLights().add(pointLight);
			
			for (int i = 0; i < pointLightCubes.size(); i++) 
				renderer.render(pointLightCubes.get(i),shader);			
		}	
		
		renderer.render(dragon, shader);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		Config.BACK_FACE_CULLING=false;
		Config.BACKGROUND_COLOR= new Vector3f(0);	
		
		projectionMatrix=new Matrix4f();
		projectionMatrix.changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,myCanvas.getHeight(),myCanvas.getWidth());
		
		camera= new Camera(myCanvas,0,5,30,-20,0,0);		
		
		renderer = new Renderer(camera,projectionMatrix);
			
		shader=new BasicShader("Phong");
		
		@SuppressWarnings("unused")			
		AmbientLight ambientLight= new AmbientLight(new Vector3f(0, 1, 1));
		
											 //(lightDirection,         diffuseColor,    speculaColor)
		directionalLight = new DirectionalLight(new Vector3f(0, -1, 0), new Vector3f(1), new Vector3f(1));
		
						          //(position,             diffuseColor,    specularColor,  attenuation)
		pointLight1 = new PointLight(new Vector3f(0,11,3), new Vector3f(1), new Vector3f(1), new Vector3f(1, 0.09f, 0.032f));
		PointLight pointLight2 = new PointLight(new Vector3f(-5,5,5), new Vector3f(1,0,0), new Vector3f(1,0,0), new Vector3f(1, 0.09f, 0.032f)); 
		PointLight pointLight3 = new PointLight(new Vector3f(-5,13,5), new Vector3f(0,1,0), new Vector3f(0,1,0), new Vector3f(1, 0.09f, 0.032f));
		PointLight pointLight4 = new PointLight(new Vector3f(5,10,8), new Vector3f(0,0,1), new Vector3f(0,0,1), new Vector3f(1, 0.09f, 0.032f));
		PointLight pointLight5 = new PointLight(new Vector3f(-10,5,-5), new Vector3f(0,1,1), new Vector3f(0,1,1), new Vector3f(1, 0.09f, 0.032f)); 
		PointLight pointLight6 = new PointLight(new Vector3f(10,10,-5), new Vector3f(1,1,0), new Vector3f(1,1,0), new Vector3f(1, 0.09f, 0.032f));
		
		pointLights = new ArrayList<PointLight>();
		pointLights.add(pointLight1);
		pointLights.add(pointLight2);
		pointLights.add(pointLight3);
		pointLights.add(pointLight4);
		pointLights.add(pointLight5);
		pointLights.add(pointLight6);
		
		 		      //new Material(ambientColor,       diffuseColor,       specularColor,  shininess)
		Material basicMaterial = new Material(new Vector3f(0.1f), new Vector3f(0.5f), new Vector3f(1), 16);
		
		
		dragon= new Model("dragon",basicMaterial);
		dragon.setScale(2f);
		
		cubePointLight = new Model("cube",0,11,3,new Material(new Vector3f(1,1,1), new Vector3f(0),new Vector3f(0), new Vector3f(0), 0));	
		pointLightCubes=new ArrayList<Model>();
		pointLightCubes.add(cubePointLight);
		pointLightCubes.add(new Model("cube",-5,5,5,new Material(new Vector3f(1,0,0), new Vector3f(0),new Vector3f(0), new Vector3f(0), 0)));
		pointLightCubes.add(new Model("cube",-5,13,5,new Material(new Vector3f(0,1,0), new Vector3f(0),new Vector3f(0), new Vector3f(0), 0)));
		pointLightCubes.add(new Model("cube",5,10,8,new Material(new Vector3f(0,0,1), new Vector3f(0),new Vector3f(0), new Vector3f(0), 0)));
		pointLightCubes.add(new Model("cube",-10,5,-5,new Material(new Vector3f(0,1,1), new Vector3f(0),new Vector3f(0), new Vector3f(0), 0)));
		pointLightCubes.add(new Model("cube",10,10,-5,new Material(new Vector3f(1,1,0), new Vector3f(0),new Vector3f(0), new Vector3f(0), 0)));
		
		arrowDirectionalLight=new Model("arrow",0,0,10,new Material(new Vector3f(1,1,0), new Vector3f(0),new Vector3f(0), new Vector3f(0), 0));
		
		addUI();		
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
	

	public void addUI() {		
		JPanel panel = new JPanel();
		panel.setBounds(0, 10, 300, 210);
		panel.setBackground(new Color(0,0,0,255));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(300, 10, 200, 30);
		buttonPanel.setBackground(new Color(0,0,0,255));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
			
		JLabel wireframeText = new JLabel("Wireframe Mode");
		wireframeText.setForeground(Color.white);
		
		JCheckBox wireframe = new JCheckBox();
		wireframe.setBackground(new Color(0,0,0,0));
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
		panelWireframe.setBackground(new Color(0,0,0,0));
		panelWireframe.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		JLabel yrText = new JLabel("Light Rotation Z: 0");
		yrText.setForeground(Color.white);
		yrText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider yrSlider = new JSlider(JSlider.HORIZONTAL,-100,100,-0);
		yrSlider.setPaintTicks(true); 
		yrSlider.setMinorTickSpacing(90);
		yrSlider.setBackground(new Color(0,0,0,255));
		yrSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		yrSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				arrowDirectionalLight.setRotationZ((float)yrSlider.getValue()/100*-90);
				directionalLight.setLightDirection(new Vector3f(directionalLight.getLightDirection().x,directionalLight.getLightDirection().y,(float)yrSlider.getValue()/100));
				yrText.setText("Light Rotation Z: "+(float)yrSlider.getValue()/100);
			}
		});
		
		JLabel xrText = new JLabel("Light Rotation X: 0");
		xrText.setForeground(Color.white);
		xrText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider xrSlider = new JSlider(JSlider.HORIZONTAL,-100,100,0);
		xrSlider.setPaintTicks(true); 
		xrSlider.setMinorTickSpacing(90);
		xrSlider.setBackground(new Color(0,0,0,255));
		xrSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		xrSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				arrowDirectionalLight.setRotationX((float)xrSlider.getValue()/100*-90);
				directionalLight.setLightDirection(new Vector3f((float)xrSlider.getValue()/100,directionalLight.getLightDirection().y,directionalLight.getLightDirection().z));
				xrText.setText("Light Rotation X: "+(float)xrSlider.getValue()/100);
			}
		});
		
		JLabel zrText = new JLabel("Light Rotation Y: -1");
		zrText.setForeground(Color.white);
		zrText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider zrSlider = new JSlider(JSlider.HORIZONTAL,-100,100,-100);
		zrSlider.setPaintTicks(true); 
		zrSlider.setMinorTickSpacing(90);
		zrSlider.setBackground(new Color(0,0,0,255));
		zrSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		zrSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				arrowDirectionalLight.setRotationY((float)zrSlider.getValue()/100*-90-90);
				directionalLight.setLightDirection(new Vector3f(directionalLight.getLightDirection().x,(float)zrSlider.getValue()/100,directionalLight.getLightDirection().z));
				zrText.setText("Light Rotation Y: "+(float)zrSlider.getValue()/100);
			}
		});
				
		JLabel yText = new JLabel("Box y: 11");
		yText.setForeground(Color.white);
		yText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider ySlider = new JSlider(JSlider.HORIZONTAL,-3000,3000,1100);
		ySlider.setPaintTicks(true); 
		ySlider.setMinorTickSpacing(100);
		ySlider.setBackground(new Color(0,0,0,255));
		ySlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		ySlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				cubePointLight.setY((float)ySlider.getValue()/100);
				pointLight1.setPosition(new Vector3f(pointLight1.getPosition().x, (float)ySlider.getValue()/100, pointLight1.getPosition().z));
				yText.setText("Box y: "+(float)ySlider.getValue()/100);
			}
		});
		
		JLabel xText = new JLabel("Box x: 0");
		xText.setForeground(Color.white);
		xText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider xSlider = new JSlider(JSlider.HORIZONTAL,-3000,3000,0);
		xSlider.setPaintTicks(true); 
		xSlider.setMinorTickSpacing(100);
		xSlider.setBackground(new Color(0,0,0,255));
		xSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		xSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				cubePointLight.setX((float)xSlider.getValue()/100);
				pointLight1.setPosition(new Vector3f((float)xSlider.getValue()/100,pointLight1.getPosition().y, pointLight1.getPosition().z));
				xText.setText("Box x: "+(float)xSlider.getValue()/100);
			}
		});
		
		JLabel zText = new JLabel("Box z: 3");
		zText.setForeground(Color.white);
		zText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider zSlider = new JSlider(JSlider.HORIZONTAL,-3000,3000,300);
		zSlider.setPaintTicks(true); 
		zSlider.setMinorTickSpacing(100);
		zSlider.setBackground(new Color(0,0,0,255));
		zSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		zSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				cubePointLight.setZ((float)zSlider.getValue()/100);
				pointLight1.setPosition(new Vector3f(pointLight1.getPosition().x, pointLight1.getPosition().y,(float)zSlider.getValue()/100));
				zText.setText("Box z: "+(float)zSlider.getValue()/100);
			}
		});
		
		JLabel materialShininess = new JLabel("Material Shininess: 16");
		materialShininess.setForeground(Color.white);
		materialShininess.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider shininessSlider = new JSlider(JSlider.HORIZONTAL,0,200,16);
		shininessSlider.setPaintTicks(true); 
		shininessSlider.setMinorTickSpacing(100);
		shininessSlider.setBackground(new Color(0,0,0,255));
		shininessSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		shininessSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				Material tmpMaterial =  dragon.getMaterial();
				tmpMaterial.setShininess(shininessSlider.getValue());
				dragon.setMaterial(tmpMaterial);
				materialShininess.setText("Material Shininess: "+(float)shininessSlider.getValue());
			}
		});
		
		JRadioButton pointButton = new JRadioButton("Point Light");
		pointButton.setForeground(Color.white);
		pointButton.setSelected(true);
		pointButton.setBackground(new Color(0,0,0,255));
		pointButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.add(xText);
				panel.add(xSlider);
				panel.add(yText);
				panel.add(ySlider);
				panel.add(zText);
				panel.add(zSlider);
				panel.add(materialShininess);
				panel.add(shininessSlider);
				panel.add(panelWireframe);
				panel.updateUI();
				update=2;
			}
		});
		
		JRadioButton dirButton = new JRadioButton("Direction Light");
		dirButton.setForeground(Color.white);
		dirButton.setBackground(new Color(0,0,0,255));
		dirButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.add(xrText);
				panel.add(xrSlider);
				panel.add(yrText);
				panel.add(yrSlider);
				panel.add(zrText);
				panel.add(zrSlider);
				panel.add(materialShininess);
				panel.add(shininessSlider);
				panel.add(panelWireframe);
				panel.updateUI();
				update=1;
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(dirButton);
		group.add(pointButton);
		
		panel.add(xText);
		panel.add(xSlider);
		panel.add(yText);
		panel.add(ySlider);
		panel.add(zText);
		panel.add(zSlider);
		panel.add(materialShininess);
		panel.add(shininessSlider);
		panel.add(panelWireframe);
		
				
		buttonPanel.add(dirButton);
		buttonPanel.add(pointButton);
		
		
		mainPane.add(panel);
		mainPane.add(buttonPanel);
	}
	
	@SuppressWarnings("deprecation")
	private void initWindow() {
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
		 
		mainFrame = new JFrame();
		
		mainFrame.setTitle("Light Test");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT);
		
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
		
		mainFrame.setVisible(true);
	}
	
}
