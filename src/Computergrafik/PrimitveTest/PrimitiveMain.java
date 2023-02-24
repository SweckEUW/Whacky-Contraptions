package Computergrafik.PrimitveTest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
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
import Computergrafik.Engine.Core.Math.Matrix4f;
import Computergrafik.Engine.Core.Math.Vector3f;
import Computergrafik.Engine.Core.Models.Model;
import Computergrafik.Engine.Core.Renderer.Renderer;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Core.Shaders.Core.Material;
import Computergrafik.Engine.Primitives.Cuboid;
import Computergrafik.Engine.Primitives.Cylinder;
import Computergrafik.Engine.Primitives.Sphere;

/**
 * Class for testing basic primitves such as cubes or spheres.
 * @author Simon Weck
 *
 */
public class PrimitiveMain extends JFrame implements GLEventListener {

	private static final long serialVersionUID = 1L;//??
	
	private GLCanvas myCanvas;
	private Renderer renderer;
	private Model model;
	private BasicShader shader;
	private Camera camera;
	private Matrix4f projectionMatrix=new Matrix4f();
	private boolean update;
	private int modelType; //0 == cuboid, 1==sphere, 2==cylinder
	private float cylinderRadiusTop=5;
	private float cylinderRadiusBot=5;
	private int cylinderResolution = 10;
	private int cuboidResolution = 10;
	private float cylinderHeight=10;
	private int sphereResolution = 10;
	private float r=1;
	private float g=1;
	private float b=1;
	private Cylinder cylinder;
	private Cuboid cuboid;
	private Sphere sphere;
	private JPanel panel;
	private Material basicMaterial;
			
	public PrimitiveMain() {
		setTitle("Primitive Demo");
		setLocation(200,200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		myCanvas = new GLCanvas();
		myCanvas.setBounds(0,0,Config.CANVAS_WIDTH,Config.CANVAS_HEIGHT);
		setSize(Config.CANVAS_WIDTH,Config.CANVAS_HEIGHT);
		myCanvas.addGLEventListener(this);
		myCanvas.setFocusable(true);
				
		FPSAnimator animator =new FPSAnimator(myCanvas,Config.FRAME_RATE);
		
		this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	            	if (animator.isStarted()) 
	                        animator.stop();
	                System.exit(0);
	            }
		});
		
		createControls();
		
		this.setVisible(true);
		
		animator.start();
	}
		
	@Override
	public void display(GLAutoDrawable drawable) {	
		renderer.clear();	
		
		updateModel();
		cuboid.update(cuboidResolution,r,g,b);
		cylinder.update(cylinderResolution, cylinderRadiusTop, cylinderRadiusBot, cylinderHeight,r,g,b);
		sphere.update(sphereResolution,r,g,b);
		
//		model.increaseRotation(0.1f, 1, 0.04f);
		
		renderer.render(model, shader);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		projectionMatrix=new Matrix4f();
		shader=new BasicShader("Primitives");
		camera= new Camera(myCanvas);
		camera.setZ(5);
		
		@SuppressWarnings("unused")
		AmbientLight ambientLight = new AmbientLight(1);
		
		@SuppressWarnings("unused")     //new DirectionalLight(lightDirection,         diffuseColor,             speculaColor)
		DirectionalLight directionallight=new DirectionalLight(new Vector3f(0, 0, -4), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f));
		
					  //new Material(ambientColor,                 diffuseColor,           specularColor,             shininess)
		basicMaterial = new Material(new Vector3f(0.2f,0.2f,0.2f), new Vector3f(1f,1f,1f), new Vector3f(.3f, .3f, .9f), 5);
		
		projectionMatrix.changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,myCanvas.getHeight(),myCanvas.getWidth());
		renderer = new Renderer(camera,projectionMatrix);	
		cylinder=new Cylinder(cylinderResolution, cylinderRadiusBot, cylinderRadiusTop, cylinderHeight);
		cuboid= new Cuboid(cuboidResolution);
		sphere=new Sphere(sphereResolution);
		model =new Model(cuboid,basicMaterial);	
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glViewport(0, 0, width, height);
		renderer.getProjectionMatrix().changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,myCanvas.getHeight(),myCanvas.getWidth());
	}
	
	public static void main(String[] args) {
		new PrimitiveMain();
	}

	
	public void updateModel() {
		if (update) {
			if (modelType==0) {
				model= new Model(cuboid,basicMaterial);
				model.setScale(1);
			}
			if (modelType==1) {

				model = new Model(sphere,basicMaterial);
				model.setScale(1);
			}
			if (modelType==2) {			
				model = new Model(cylinder,basicMaterial);	
				model.setScale(0.2f);
			}
			update=false;
		}
	}
		
	
	public void createControls() {
		JLayeredPane layer = new JLayeredPane();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));	
		panel.setBounds(0, 10, 300, 500);
		panel.setBackground(new Color(255,255,255,255));
		
		JLabel BackFaceCullingText = new JLabel("Backface Culling");
		BackFaceCullingText.setForeground(Color.black);
		
		JCheckBox BackFaceCulling = new JCheckBox();
		BackFaceCulling.setBackground(new Color(255,255,255,255));
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
		panelBackFaceCulling.setBackground(new Color(255,255,255,255));
		panelBackFaceCulling.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel wireframeText = new JLabel("Wireframe Mode");
		wireframeText.setForeground(Color.black);
		
		JCheckBox wireframe = new JCheckBox();
		wireframe.setBackground(new Color(255,255,255,255));
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
		panelWireframe.setBackground(new Color(255,255,255,255));
		panelWireframe.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel rText = new JLabel("R-Value: 255");
		rText.setForeground(Color.black);
		rText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider rSlider = new JSlider(JSlider.HORIZONTAL,0,255,255);
		rSlider.setPaintTicks(true); 
		rSlider.setMinorTickSpacing(10);
		rSlider.setBackground(new Color(255,255,255,255));
		rSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		rSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				r=rSlider.getValue()/(float)255;
				rText.setText("R-Value: "+(rSlider.getValue()));
			}
		});
		
		JLabel gText = new JLabel("G-Value: 255");
		gText.setForeground(Color.black);
		gText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider gSlider = new JSlider(JSlider.HORIZONTAL,0,255,255);
		gSlider.setPaintTicks(true); 
		gSlider.setMinorTickSpacing(10);
		gSlider.setBackground(new Color(255,255,255,255));
		gSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		gSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				g=gSlider.getValue()/(float)255;
				gText.setText("G-Value: "+(gSlider.getValue()));
			}
		});
		
		JLabel bText = new JLabel("B-Value: 255");
		bText.setForeground(Color.black);
		bText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider bSlider = new JSlider(JSlider.HORIZONTAL,0,255,255);
		bSlider.setPaintTicks(true); 
		bSlider.setMinorTickSpacing(10);
		bSlider.setBackground(new Color(255,255,255,255));
		bSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		bSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				b=bSlider.getValue()/(float)255;
				bText.setText("B-Value: "+(bSlider.getValue()));
			}
		});
		
		JRadioButton cylinderButton = new JRadioButton("Cylinder");
		cylinderButton.setForeground(new Color(0,0,0));
		cylinderButton.setBackground(new Color(255,255,255,255));
		cylinderButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				addCylinderUI();
				panel.add(rText);
				panel.add(rSlider);				
				panel.add(gText);
				panel.add(gSlider);
				panel.add(bText);
				panel.add(bSlider);
				panel.add(panelWireframe);
				panel.add(panelBackFaceCulling);				
				panel.updateUI();
				modelType=2;
				update=true;
			}
		});
		
		JRadioButton sphereButton = new JRadioButton("Sphere");
		sphereButton.setForeground(new Color(0,0,0));
		sphereButton.setBackground(new Color(255,255,255,255));
		sphereButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				addSphereUI();
				panel.add(rText);
				panel.add(rSlider);
				panel.add(gText);
				panel.add(gSlider);
				panel.add(bText);
				panel.add(bSlider);
				panel.add(panelWireframe);
				panel.add(panelBackFaceCulling);
				panel.updateUI();
				modelType=1;
				update=true;
			}
		});
		
		JRadioButton cuboidButton = new JRadioButton("Cuboid");
		cuboidButton.setForeground(new Color(0,0,0));
		cuboidButton.setSelected(true);
		cuboidButton.setBackground(new Color(255,255,255,255));
		cuboidButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				addCuboidUI();
				panel.add(rText);
				panel.add(rSlider);
				panel.add(gText);
				panel.add(gSlider);
				panel.add(bText);
				panel.add(bSlider);
				panel.add(panelWireframe);
				panel.add(panelBackFaceCulling);
				panel.updateUI();
				modelType=0;
				update=true;
			}
		});
		
		JPanel panelRadioButton = new JPanel();
		panelRadioButton.setLayout(new BoxLayout(panelRadioButton, BoxLayout.X_AXIS));	
		panelRadioButton.setBounds(800, 10, 400, 30);
		panelRadioButton.setBackground(new Color(255,255,255,255));
		panelRadioButton.add(cuboidButton);
		panelRadioButton.add(sphereButton);
		panelRadioButton.add(cylinderButton);
		
		
		 ButtonGroup group = new ButtonGroup();
		 group.add(cylinderButton);
		 group.add(sphereButton);
		 group.add(cuboidButton);
		    
		addCuboidUI();
		panel.add(rText);
		panel.add(rSlider);
		panel.add(gText);
		panel.add(gSlider);
		panel.add(bText);
		panel.add(bSlider);
		panel.add(panelWireframe);
		panel.add(panelBackFaceCulling);
		
		layer.add(panelRadioButton);
		layer.add(panel);
		layer.add(myCanvas);
		
		this.add(layer);
	}
	
	
	public void addCylinderUI() {
		JLabel resolutionText = new JLabel("Resolution: 10");
		resolutionText.setForeground(Color.black);
		resolutionText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider resolutionSlider = new JSlider(JSlider.HORIZONTAL,3,200,10);
		resolutionSlider.setPaintTicks(true); 
		resolutionSlider.setMinorTickSpacing(10);
		resolutionSlider.setBackground(new Color(255,255,255,255));
		resolutionSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		resolutionSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				cylinderResolution=resolutionSlider.getValue();
				resolutionText.setText("Resolution: "+(resolutionSlider.getValue()));
			}
		});
	
		JLabel radiusBotText = new JLabel("Radius Bot: 5");
		radiusBotText.setForeground(Color.black);
		radiusBotText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider radiusBotSlider = new JSlider(JSlider.HORIZONTAL,0,40,5);
		radiusBotSlider.setPaintTicks(true); 
		radiusBotSlider.setMinorTickSpacing(5);
		radiusBotSlider.setBackground(new Color(255,255,255,255));
		radiusBotSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		radiusBotSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				cylinderRadiusBot=radiusBotSlider.getValue();
				radiusBotText.setText("Radius Bot: "+radiusBotSlider.getValue());
			}
		});
		
		JLabel radiusTopText = new JLabel("Radius Top: 5");
		radiusTopText.setForeground(Color.black);
		radiusTopText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider radiusTopSlider = new JSlider(JSlider.HORIZONTAL,0,40,5);
		radiusTopSlider.setPaintTicks(true); 
		radiusTopSlider.setMinorTickSpacing(5);
		radiusTopSlider.setBackground(new Color(255,255,255,255));
		radiusTopSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		radiusTopSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				cylinderRadiusTop=radiusTopSlider.getValue();
				radiusTopText.setText("Radius Top: "+radiusTopSlider.getValue());
			}
		});
		
		JLabel heightText = new JLabel("Height: 10");
		heightText.setForeground(Color.black);
		heightText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider heightSlider = new JSlider(JSlider.HORIZONTAL,0,50,10);
		heightSlider.setPaintTicks(true); 
		heightSlider.setMinorTickSpacing(5);
		heightSlider.setBackground(new Color(255,255,255,255));
		heightSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		heightSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {	
				cylinderHeight=(float)heightSlider.getValue();			
				heightText.setText("Height: "+heightSlider.getValue());
			}
		});
		
		panel.add(resolutionText);
		panel.add(resolutionSlider);
		panel.add(radiusBotText);
		panel.add(radiusBotSlider);
		panel.add(radiusTopText);
		panel.add(radiusTopSlider);
		panel.add(heightText);
		panel.add(heightSlider);
	}
	
	public void addSphereUI() {
		JLabel resolutionText = new JLabel("Resolution: 10");
		resolutionText.setForeground(Color.black);
		resolutionText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider resolutionSlider = new JSlider(JSlider.HORIZONTAL,2,30,10);
		resolutionSlider.setPaintTicks(true); 
		resolutionSlider.setMinorTickSpacing(10);
		resolutionSlider.setBackground(new Color(255,255,255,255));
		resolutionSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		resolutionSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				sphereResolution=resolutionSlider.getValue();
				resolutionText.setText("Resolution: "+(resolutionSlider.getValue()));
			}
		});
			
		panel.add(resolutionText);
		panel.add(resolutionSlider);
	}
	
	public void addCuboidUI() {
		JLabel resolutionText = new JLabel("Resolution: 10");
		resolutionText.setForeground(Color.black);
		resolutionText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSlider resolutionSlider = new JSlider(JSlider.HORIZONTAL,2,30,10);
		resolutionSlider.setPaintTicks(true); 
		resolutionSlider.setMinorTickSpacing(10);
		resolutionSlider.setBackground(new Color(255,255,255,255));
		resolutionSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		resolutionSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {				
				cuboidResolution=resolutionSlider.getValue();
				resolutionText.setText("Resolution: "+(resolutionSlider.getValue()));
			}
		});
			
		panel.add(resolutionText);
		panel.add(resolutionSlider);
		
	}
}
