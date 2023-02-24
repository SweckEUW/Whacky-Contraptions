package UniverseSimulationMain;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Computergrafik.Engine.Core.Config;

public class MainWindow {

	private JFrame mainFrame;
	
	public MainWindow() {
		initWindow();
				
		showTitel();	
	}
	

	private void createMainMenue() {
		new MainMenue(mainFrame);
	}

	private void initWindow() {		
		mainFrame = new JFrame();
		mainFrame.setTitle("Universe Simulation");
		mainFrame.setIconImage(new ImageIcon("res/UserInterface/icon.png").getImage());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(Config.CANVAS_WIDTH,Config.CANVAS_HEIGHT);
		mainFrame.setBackground(Color.white);
		mainFrame.setVisible(true);
	}
	
	
	private void showTitel() {
		
		JPanel titelPanel = new JPanel();
		titelPanel.setBackground(Color.white);
		titelPanel.setLayout(new BorderLayout());
			
		ImageIcon hshlLogoIcon = new ImageIcon("res/UserInterface/hshlLogo.gif");
		JLabel hshlLogo = new JLabel(hshlLogoIcon);
		titelPanel.add(hshlLogo);
		
		mainFrame.getContentPane().add(titelPanel);
		mainFrame.setVisible(true);
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	createMainMenue();
		            }
		        }, 
		        2000 
		);
		
	}
}
