package Computergrafik.OBJLoaderTest;

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

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

/**
 * Class for testing the obj loader with different models.
 * Used to mainly test the import of maya models which use quads instead of triangles as main primitive
 * @author Simon
 *
 */
public class OBJLoaderMain implements GLEventListener {

	private GLCanvas myCanvas;
	private JFrame mainFrame;
	private JLayeredPane mainPane;
	
	
	private Renderer renderer;
	private BasicShader shader;
	private Camera camera;
	private Matrix4f projectionMatrix;
	
	private Model model;
	private Model teapot;
	private Model dragon;
	private boolean update;
	private int modelType; //0==teapot //1==dragon
	
	
	public static void main(String[] args) {
		new OBJLoaderMain();
	}
	
	public OBJLoaderMain() {
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
		
		addUI();
		
		animator.start();
	}
		
	@Override
	public void display(GLAutoDrawable drawable) {
		
		renderer.clear();	
				
		if (update) {
			if (modelType==0) 		
				model= teapot;
			
			if (modelType==1) 
				model = dragon;
			
			update=false;
		}
		
		model.increaseRotation(0,0.5f,0);
		
		renderer.render(model, shader); 
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {			
		projectionMatrix=new Matrix4f();
		projectionMatrix.changeToPerspecitveMatrix(Config.FIELD_OF_VIEW, Config.NEAR_PLANE, Config.FAR_PLANE,myCanvas.getHeight(),myCanvas.getWidth());
		
		camera= new Camera(myCanvas,0,1,5,0,0,0);
		
		renderer = new Renderer(camera,projectionMatrix);	
		
		shader=new BasicShader("Phong");
	
	    @SuppressWarnings("unused")
		AmbientLight ambientLight = new AmbientLight(1);    
		
		@SuppressWarnings("unused") 	//new DirectionalLight(lightDirection,        diffuseColor,          speculaColor)
		DirectionalLight directionalLight=new DirectionalLight(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));
		
		 					   //new Material(emissionColor,         ambientColor,                 diffuseColor,                 specularColor,                shininess)
		Material basicMaterial = new Material(new Vector3f(0, 0, 0), new Vector3f(0.2f,0.2f,0.2f), new Vector3f(0.5f,0.5f,0.5f), new Vector3f(1.f, 1.f, 1.f), 16);
		
		teapot = new Model("teapot",basicMaterial);
		teapot.setScale(0.8f);
		
		dragon = new Model("dragon",basicMaterial);	
		dragon.setScale(0.3f);
		
		model=teapot;	
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
		panel.setBounds(0, 10, 300, 150);
		panel.setBackground(new Color(255,255,255,255));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
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
		
		
		JRadioButton teapotButton = new JRadioButton("Utah teapot");
		teapotButton.setSelected(true);
		teapotButton.setForeground(new Color(0));
		teapotButton.setBackground(new Color(255,255,255,255));
		teapotButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				modelType=0;
				update=true;
			}
		});
		
		JRadioButton dragonButton = new JRadioButton("Stanford dragon");
		dragonButton.setForeground(new Color(0));
		dragonButton.setBackground(new Color(255,255,255,255));
		dragonButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				modelType=1;
				update=true;
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(dragonButton);
		group.add(teapotButton);
		
		panel.add(teapotButton);
		panel.add(dragonButton);
		panel.add(panelWireframe);
		panel.add(panelBackFaceCulling);
		
		mainPane.add(panel);
		
		mainFrame.add(mainPane);
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
		
		mainFrame.setTitle("OBJLoader Test");
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
