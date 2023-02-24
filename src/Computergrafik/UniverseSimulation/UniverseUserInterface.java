package Computergrafik.UniverseSimulation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.Timer;

import Computergrafik.Engine.Core.Config;
import Computergrafik.Engine.Core.Camera.Camera;
import Computergrafik.Engine.Core.Camera.CenterCamera;
import Computergrafik.Engine.Core.Camera.LookAtPlanet;
import Computergrafik.Engine.Planet.ExportPlanetAsOBJ;
import Computergrafik.Engine.Planet.Core.Planet;
import UniverseSimulationMain.MainMenue;

public class UniverseUserInterface {
	
	private Camera camera;
	
	private JLayeredPane mainPane;
	private JFrame mainFrame;
	
	private JPanel planetDescriptionPanel;
	private JPanel leavePanel;
	
	private static JLabel planetName;
	
	private boolean openGeneralMenue;
	private boolean openPlanetMenue; 
	
	public UniverseUserInterface(JLayeredPane mainPane,JFrame mainFrame,Camera camera) {
		this.mainFrame=mainFrame;
		this.mainPane=mainPane;
		try {
			addGeneralControls();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// addLeaveButton();
		
		this.camera = camera;
		
		// planetDescriptionPanel = new JPanel();
		// planetDescriptionPanel.setPreferredSize(new Dimension(800, 220));
		// planetDescriptionPanel.setBounds(1920, 430, 800, 220);
		// planetDescriptionPanel.setBackground(Color.black);	
		// planetDescriptionPanel.setLayout(new BoxLayout(planetDescriptionPanel, BoxLayout.X_AXIS));
		
		// leavePanel = new JPanel();
		// leavePanel.setBounds(0, -50, 50, 50);
		// leavePanel.setBackground(Color.black);	
		// leavePanel.setLayout(new BoxLayout(leavePanel, BoxLayout.Y_AXIS));
			
		// mainPane.add(planetDescriptionPanel);	
		// mainPane.add(leavePanel);
				
		// planetName = new JLabel("");
		// planetName.setFont(new Font("Arial", Font.PLAIN, 30));
		// planetName.setForeground(Color.white);
		// planetName.setBounds(1720, 10, 200, 40);
		// planetName.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// JPanel panelPlanetName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// panelPlanetName.setBounds(1720, 10, 200, 40);
		// panelPlanetName.add(planetName);
		// panelPlanetName.setBackground(Color.black);
		// panelPlanetName.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// mainPane.add(panelPlanetName);
	}
	
	private void addLeaveButton() {
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
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 995, 123, 50);
		panel.setBackground(Color.black);
		panel.add(goBack);
		
		mainPane.add(panel);
	}

	private void addGeneralControls() throws IOException {	
		JPanel panel = new JPanel();
		
		JLabel picLabel = new JLabel(new ImageIcon("res/UserInterface/arrow.jpg"));
		picLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				picLabel.setIcon(new ImageIcon("res/UserInterface/arrow.jpg"));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				picLabel.setIcon(new ImageIcon("res/UserInterface/clicked.jpg"));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (openGeneralMenue) {
					openGeneralMenue=false;
					new Timer(1, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							panel.setLocation(panel.getX(), panel.getY()-3);
							if (panel.getY() <= -240 ) {
								((Timer) e.getSource()).stop();
							}
			            }	
			         }).start();
				}else {
					openGeneralMenue=true;
					new Timer(1, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							panel.setLocation(panel.getX(), panel.getY()+3);
							if (panel.getY() >= 0 ) {
								((Timer) e.getSource()).stop();
							}
			            }	
			         }).start();
				}
							
			}
		});
		
		JLabel BackFaceCullingText = new JLabel("Backface Culling");
		BackFaceCullingText.setForeground(Color.white);
			
		JCheckBox BackFaceCulling = new JCheckBox();
		BackFaceCulling.setBackground(Color.black);
		BackFaceCulling.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (BackFaceCulling.isSelected()) {
					Config.BACK_FACE_CULLING=true;
				}else {
					Config.BACK_FACE_CULLING=false;
				}
			}
		});
			
			
		JPanel panelBackFaceCulling = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBackFaceCulling.setMaximumSize(new Dimension(300,40));
		panelBackFaceCulling.add(BackFaceCulling);
		panelBackFaceCulling.add(BackFaceCullingText);
		panelBackFaceCulling.setBackground(Color.black);
		panelBackFaceCulling.setAlignmentX(Component.LEFT_ALIGNMENT);
			
		JLabel wireframeText = new JLabel("Wireframe Mode");
		wireframeText.setForeground(Color.white);
			
		JCheckBox wireframe = new JCheckBox();
		wireframe.setBackground(Color.black);
		wireframe.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (wireframe.isSelected()) 
					Config.WIREFRAME_MODE=true;
				else 
					Config.WIREFRAME_MODE=false;
			}
		});
			
		JPanel panelWireframe = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelWireframe.setMaximumSize(new Dimension(300, 40));
		panelWireframe.add(wireframe);
		panelWireframe.add(wireframeText);
		panelWireframe.setBackground(Color.black);
		panelWireframe.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			
		JLabel drawOrbitText = new JLabel("Draw Orbits");
		drawOrbitText.setForeground(Color.white);
			
		JCheckBox drawOrbit = new JCheckBox();
		drawOrbit.setBackground(Color.black);
		drawOrbit.setSelected(false);
		drawOrbit.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (drawOrbit.isSelected()) 
					for (Planet planet : Planet.getAllPlanets()) 
						planet.setDrawOrbit(true);
						
				else 		
					for (Planet planet : Planet.getAllPlanets()) 
						planet.setDrawOrbit(false);
					
			}
		});
			
		JPanel panelDrawOrbit = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelDrawOrbit.setMaximumSize(new Dimension(300, 40));
		panelDrawOrbit.add(drawOrbit);
		panelDrawOrbit.add(drawOrbitText);
		panelDrawOrbit.setBackground(Color.black);
		panelDrawOrbit.setAlignmentX(Component.LEFT_ALIGNMENT);
			
		JButton centerCamera = new JButton("Center Camera");
		centerCamera.setForeground(new Color(255,0,0));
		centerCamera.setBackground(Color.black);
		centerCamera.setMaximumSize(new Dimension(300, 40));
		centerCamera.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				CenterCamera.initCenterCamera(camera);
			}
		});
		
		JButton generateRandom = new JButton("Generate all");
		generateRandom.setForeground(new Color(255,0,0));
		generateRandom.setBackground(Color.black);
		generateRandom.setMaximumSize(new Dimension(300, 40));
		generateRandom.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < Planet.getAllPlanets().size(); i++) 
					Planet.getAllPlanets().get(i).setGenerate(true);
			}
		});
		
		JLabel stoptext = new JLabel("Stop Universe");
		stoptext.setForeground(Color.white);
			
		JCheckBox stop = new JCheckBox();
		stop.setBackground(Color.black);
		stop.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stop.isSelected()) 
					for (int i = 0; i < Planet.getAllPlanets().size(); i++) 
						Planet.getAllPlanets().get(i).setStopUpdate(true);
				else 
					for (int i = 0; i < Planet.getAllPlanets().size(); i++) 
						Planet.getAllPlanets().get(i).setStopUpdate(false);
			}
		});
			
		JPanel stopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		stopPanel.setMaximumSize(new Dimension(300, 40));
		stopPanel.add(stop);
		stopPanel.add(stoptext);
		stopPanel.setBackground(Color.black);
		stopPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel centerText = new JLabel("Focus Camera on Origin");
		centerText.setForeground(Color.white);
			
		JCheckBox center = new JCheckBox();
		center.setBackground(Color.black);
		center.setSelected(true);
		center.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (center.isSelected()) 
					camera.getCameraControls().setCameraModeOrigin(true);
				else 
					camera.getCameraControls().setCameraModeOrigin(false);
			}
		});
			
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		centerPanel.setMaximumSize(new Dimension(300, 40));
		centerPanel.add(center);
		centerPanel.add(centerText);
		centerPanel.setBackground(Color.black);
		centerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		panel.setBounds(760, -240, 300, 290);
		panel.setBackground(Color.black);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				
		panel.add(panelWireframe);
		panel.add(panelBackFaceCulling);
		panel.add(panelDrawOrbit);
		panel.add(stopPanel);
		panel.add(centerPanel);
		panel.add(centerCamera);	
		panel.add(Box.createVerticalStrut(10));
		panel.add(generateRandom);	
		panel.add(picLabel);
		
		
		mainPane.add(panel);
	}

	public void addPlanetUI(Planet planet) {
		planetDescriptionPanel.removeAll();
		leavePanel.removeAll();
		addPlanetDescription(planet);	
		addLeaveLookAtMode();	
		mainFrame.setVisible(true);
	}

	private void addLeaveLookAtMode() {
		JLabel picLabel = new JLabel(new ImageIcon("res/UserInterface/x.jpg"));
		picLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				picLabel.setIcon(new ImageIcon("res/UserInterface/x.jpg"));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				picLabel.setIcon(new ImageIcon("res/UserInterface/xClicked.jpg"));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				LookAtPlanet.initLeavePlanet();
				openPlanetMenue=false;
				new Timer(1, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						leavePanel.setLocation(leavePanel.getX(), leavePanel.getY()-3);
						if (leavePanel.getY()<=-50 ) {
							((Timer) e.getSource()).stop();
						}
		            }	
		         }).start();
				
				new Timer(1, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						planetDescriptionPanel.setLocation(planetDescriptionPanel.getX() + 3, planetDescriptionPanel.getY());
						if (planetDescriptionPanel.getX()>= 1920 ) {
							((Timer) e.getSource()).stop();
						}
		            }	
		         }).start();
				
			}
		});
		
		leavePanel.add(picLabel);
		
		new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leavePanel.setLocation(leavePanel.getX(), leavePanel.getY()+3);
				if (leavePanel.getY()>=0 ) {
					((Timer) e.getSource()).stop();
				}
            }	
         }).start();
	}

	private void addPlanetDescription(Planet planet) {
		JPanel panel = new JPanel();
		panel.setBounds(1920, 430, 800, 220);
		panel.setBackground(Color.black);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
		JLabel entitiesText = new JLabel("Entities:                    "+String.valueOf(planet.getAmmountOfPlanetEntities()));
		entitiesText.setForeground(Color.white);
		entitiesText.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel sizeText = new JLabel("Size:                         "+String.format("%.6g%n", planet.getRadius()*2*1000)+"m");
		sizeText.setForeground(Color.white);
		sizeText.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel waterConcentrationText = new JLabel("Water concentration:  "+String.format("%.3g%n", planet.getWaterConcentration())+"%");
		waterConcentrationText.setForeground(Color.white);
		waterConcentrationText.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel highestPointText = new JLabel("Highest point:            "+String.format("%.6g%n", planet.getHighestPoint()*1000)+"m");
		highestPointText.setForeground(Color.white);
		highestPointText.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel lowestPointText = new JLabel("Lowest point:             "+String.format("%.6g%n", planet.getLowestPoint()*1000)+"m");
		lowestPointText.setForeground(Color.white);
		lowestPointText.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel averageHeightText = new JLabel("Average Height:         "+String.format("%.6g%n", planet.getAverageHeight()*1000)+"m");
		averageHeightText.setForeground(Color.white);
		averageHeightText.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JButton generateRandom = new JButton("Generate Random");
		generateRandom.setForeground(new Color(255,0,0));
		generateRandom.setBackground(Color.black);
		generateRandom.setMaximumSize(new Dimension(290, 40));
		generateRandom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				planet.setGenerate(true);
				entitiesText.setText("Entities: "+String.valueOf(planet.getAmmountOfPlanetEntities()));
				sizeText.setText("Size: "+String.format("%.6g%n", planet.getRadius()*2*1000)+"m");
				waterConcentrationText.setText("Water concentration: "+String.format("%.4g%n", planet.getWaterConcentration())+"%");
				highestPointText.setText("Highest point: "+String.format("%.6g%n", planet.getHighestPoint()*1000)+"m");
				lowestPointText.setText("Lowest point: "+String.format("%.6g%n", planet.getLowestPoint()*1000)+"m");
				averageHeightText.setText("Average Height: "+String.format("%.6g%n", planet.getAverageHeight()*1000)+"m");			
			}
		});
		
		JButton exportToOBJ = new JButton("Export to OBJ");
		exportToOBJ.setForeground(new Color(255,0,0));
		exportToOBJ.setBackground(Color.black);
		exportToOBJ.setMaximumSize(new Dimension(290, 40));
		exportToOBJ.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ExportPlanetAsOBJ.export(planet);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		panel.add(entitiesText);
		panel.add(sizeText);
		panel.add(waterConcentrationText);
		panel.add(highestPointText);
		panel.add(lowestPointText);
		panel.add(averageHeightText);
		panel.add(Box.createVerticalStrut(10));
		panel.add(generateRandom);
		panel.add(Box.createVerticalStrut(10));
		panel.add(exportToOBJ);
		
		JLabel picLabel = new JLabel(new ImageIcon("res/UserInterface/arrow2.jpg"));
		picLabel.addMouseListener(new MouseListener() {		
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}	
			@Override
			public void mouseExited(MouseEvent e) {
				picLabel.setIcon(new ImageIcon("res/UserInterface/arrow2.jpg"));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				picLabel.setIcon(new ImageIcon("res/UserInterface/clicked2.jpg"));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (openPlanetMenue) {
					openPlanetMenue=false;
					new Timer(1, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							planetDescriptionPanel.setLocation(planetDescriptionPanel.getX() - 4, planetDescriptionPanel.getY());
							if (planetDescriptionPanel.getX()<= 1550 ) {
								((Timer) e.getSource()).stop();
							}
			            }	
			         }).start();
				}else {
					openPlanetMenue=true;
					new Timer(1, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							planetDescriptionPanel.setLocation(planetDescriptionPanel.getX() + 4, planetDescriptionPanel.getY());
							if (planetDescriptionPanel.getX()>= 1870 ) {
								((Timer) e.getSource()).stop();
							}
			            }	
			         }).start();
				}			
			}
		});
		
		planetDescriptionPanel.add(picLabel);
		planetDescriptionPanel.add(panel);
		
		new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				planetDescriptionPanel.setLocation(planetDescriptionPanel.getX() - 4, planetDescriptionPanel.getY());
				if (planetDescriptionPanel.getX()<= 1550 ) {
					((Timer) e.getSource()).stop();
				}
            }	
         }).start();	
	}
	
	public static void changePlanetName(String name) {
		planetName.setText(name);
	}
	
}
