package UniverseSimulationMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

// import com.github.sarxos.webcam.Webcam;
// import com.github.sarxos.webcam.WebcamPanel;

import Bildverarbeitung.dataclasses.PlanetData;
import Bildverarbeitung.detector.ProcessUniverseImage;
import Computergrafik.UniverseSimulation.DemoUniverse;
import Computergrafik.UniverseSimulation.PlanetGenerator;
import Computergrafik.UniverseSimulation.UniverseSimulation;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MainMenue {

	private JFrame mainFrame;
	private JLayeredPane mainPanel;
	
	private boolean camera;
	private boolean cameraFulscreen;
	// private WebcamPanel cameraPanel;
	
	public MainMenue(JFrame mainFrame) {
		this.mainFrame=mainFrame;

		mainPanel=new JLayeredPane();
		mainPanel.setBackground(Color.black);
		mainPanel.setOpaque(true);
		createMenue();
			
		mainFrame.getContentPane().removeAll();
		mainFrame.getContentPane().add(mainPanel);
		
		mainFrame.revalidate();	
		mainFrame.repaint();
	}

	private void createMenue() {
		addBackgroundVideo();
		addOverview();				
	}

	/**
	 * Method that receives a BufferedImage from a freshly shot photo or from a file on the computer
	 * 
	 * @param image
	 */
	private void processPicture(BufferedImage image){
		
		addLoadingScreen();
		
		
		ArrayList<PlanetData> data = ProcessUniverseImage.process(image);
		
		
		new UniverseSimulation(mainFrame,data);
		
		//write Image to desktop
//		try {
//			ImageIO.write(image, "PNG", new File(System.getProperty("user.home")+"/Desktop/test.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private void processFile(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		processPicture(image);
	}
	
	private void addLoadingScreen() {
		JLabel loading = new JLabel(new ImageIcon("res/UserInterface/loading.png"));
		loading.setBounds(0, 0, 1920, 1080);
		mainPanel.removeAll();
		mainPanel.add(loading);
		mainFrame.revalidate();
		mainFrame.setVisible(true);
	}
	
	private void addOverview() {		
		JLabel instructions = new JLabel(new ImageIcon("res/UserInterface/Anleitung.png"));
		instructions.setBounds(0, 0, 1920, 1080);
		
		JLabel us = new JLabel(new ImageIcon("res/UserInterface/universeSimulation.png"));
		us.setBounds(1780, -50, 100, 1080);
		addCamera();
		addUploadPictureButton();
		
		mainPanel.add(us);
		mainPanel.add(instructions);
		
		addCreatPlanetButton();
		addDemoUniverseButton();
	}

	private void addUploadPictureButton() {
		JLabel uploadImage = new JLabel(new ImageIcon("res/UserInterface/BilddateiOffnenNeutral.png"));
		uploadImage.setBounds(450, 943, 308, 45);
		uploadImage.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}		
			@Override
			public void mousePressed(MouseEvent e) {}		
			@Override
			public void mouseExited(MouseEvent e) {
				uploadImage.setIcon(new ImageIcon("res/UserInterface/BilddateiOffnenNeutral.png"));}		
			@Override
			public void mouseEntered(MouseEvent e) {
				uploadImage.setIcon(new ImageIcon("res/UserInterface/BilddateOffnenHover.png"));}
			@Override
			public void mouseClicked(MouseEvent e) {
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
				 
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
				chooser.setFileFilter(imageFilter);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setCurrentDirectory(new File("res/Example Pictures"));
				chooser.setPreferredSize(new Dimension(800,600));
				int state = chooser.showDialog(null,"Choose Picture");	
				if (state == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					processFile(file);
				}
				
				try {
					UIManager.setLookAndFeel(originalLookAndFeel);
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		mainPanel.add(uploadImage);
	}

	// Webcam webcam = Webcam.getDefault();
	
	private void addCamera() {		
		JLabel takePicture = new JLabel(new ImageIcon("res/UserInterface/FotoAufnehmenNeutral.png"));
		takePicture.setBounds(140, 943, 308, 41);
		takePicture.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}		
			@Override
			public void mousePressed(MouseEvent e) {}		
			@Override
			public void mouseExited(MouseEvent e) {
				takePicture.setIcon(new ImageIcon("res/UserInterface/FotoAufnehmenNeutral.png"));}		
			@Override
			public void mouseEntered(MouseEvent e) {
				takePicture.setIcon(new ImageIcon("res/UserInterface/FotoAufnehmenHover.png"));}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (camera) {
					if (cameraFulscreen) {
						// BufferedImage image = webcam.getImage();
						// processPicture(image);
					}else {
						cameraFulscreen=true;
						// cameraPanel.setBounds(0, 0, 1920, 1080);	
						takePicture.setBounds(950, 20,308, 41);;
					}
				}
			}
		});
					
		mainPanel.add(takePicture);
		
		
		// if (webcam!=null) {
		// 	camera=true;
			
		// 	webcam=Webcam.getWebcams().get(Webcam.getWebcams().size()-1);
			
		// 	cameraPanel = new WebcamPanel(webcam);
		// 	cameraPanel.setBounds(190, 810, 200, 135);
		// 	mainPanel.add(cameraPanel);
		// }
		
		// if (camera) {
		// 	cameraPanel.addMouseListener(new MouseListener() {				
		// 		@Override
		// 		public void mouseReleased(MouseEvent e) {}					
		// 		@Override
		// 		public void mousePressed(MouseEvent e) {}			
		// 		@Override
		// 		public void mouseExited(MouseEvent e) {}				
		// 		@Override
		// 		public void mouseEntered(MouseEvent e) {}	
		// 		@Override
		// 		public void mouseClicked(MouseEvent e) {
		// 			if (cameraFulscreen) {
		// 				cameraFulscreen=false;
		// 				cameraPanel.setBounds(190, 810, 200, 135);
		// 				takePicture.setBounds(140, 943, 308, 41);
		// 			}else {
		// 				cameraPanel.setBounds(0, 0, 1920, 1080);
		// 				takePicture.setBounds(950, 20,308, 41);;
		// 				cameraFulscreen=true;
		// 			}										
		// 		}
		// 	});
		// }	
		
	}

	
	private void addCreatPlanetButton() {
		JLabel editor = new JLabel(new ImageIcon("res/UserInterface/EditorNeutral.png"));
		editor.setBounds(1120, 760, 183, 41);
		editor.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}		
			@Override
			public void mousePressed(MouseEvent e) {}		
			@Override
			public void mouseExited(MouseEvent e) {
				editor.setIcon(new ImageIcon("res/UserInterface/EditorNeutral.png"));}		
			@Override
			public void mouseEntered(MouseEvent e) {
				editor.setIcon(new ImageIcon("res/UserInterface/EditorHover.png"));}
			@Override
			public void mouseClicked(MouseEvent e) {
				// if (camera) 
					// webcam.close();					
				addLoadingScreen();
				new PlanetGenerator(mainFrame);
			}
		});
			
		mainPanel.add(editor);
	}

	private void addDemoUniverseButton() {
		JLabel demo = new JLabel(new ImageIcon("res/UserInterface/DemoUniverseNeutral.png"));
		demo.setBounds(1100, 700, 226, 42);
		demo.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}		
			@Override
			public void mousePressed(MouseEvent e) {}		
			@Override
			public void mouseExited(MouseEvent e) {
				demo.setIcon(new ImageIcon("res/UserInterface/DemoUniverseNeutral.png"));}		
			@Override
			public void mouseEntered(MouseEvent e) {
				demo.setIcon(new ImageIcon("res/UserInterface/DemoUniverseHover.png"));}
			@Override
			public void mouseClicked(MouseEvent e) {
				// if (camera) 
					// webcam.close();
				addLoadingScreen();
				new DemoUniverse(mainFrame);
			}
		});

		mainPanel.add(demo);
	}
	
	@SuppressWarnings("deprecation")
	private void addBackgroundVideo() {
        JFXPanel videoPanel = new JFXPanel();
        videoPanel.setBounds(0, 0, 1920, 1080);
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            	File videoFile;
            	float random = (float)Math.random()*4;
            	if (random>3) 
            		videoFile = new File("res/UserInterface/Background1.mp4");
				else if (random>2) 
					videoFile = new File("res/UserInterface/Background4.mp4");				
				else if (random>1) 
					videoFile = new File("res/UserInterface/Background3.mp4");
				else  
					videoFile = new File("res/UserInterface/Background2.mp4");
						        	
            	String url = null;
            	try {
					url = videoFile.toURI().toURL().toString();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
            	Media media = new Media(url);
        	    final MediaPlayer player = new MediaPlayer(media);    
        	    player.setCycleCount(MediaPlayer.INDEFINITE);
        	    MediaView view = new MediaView(player);
         	    
            	Group  root  =  new  Group(view);
                Scene  scene  =  new  Scene(root);
                
                player.play();
                
                videoPanel.setScene(scene);
                videoPanel.setBounds(0, 0, 1920, 1080);             
        });

	    mainPanel.add(videoPanel,new Integer(-101));
	    	    
	}
	
	
	
}
