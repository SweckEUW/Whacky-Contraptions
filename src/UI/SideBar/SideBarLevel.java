package UI.SideBar;

import Simulation.SimulationControler;
import UI.Util;
import UI.LevelMenue.LevelSettings;
import UI.MainMenue.LevelSelectionMenue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class SideBarLevel extends AnchorPane{

	private ObjectSettingsLevel objectSettings;
	
	public SideBarLevel(Scene mainScene,Stage primaryStage) {

		VBox container = new VBox();
		
		SimulationControls simulationControls = new SimulationControls(mainScene,primaryStage,container);
		
		Line line1 = new Line(0,0,164,0);
		line1.setStrokeWidth(6);
		line1.setFill(Color.BLACK);
		
		LevelSettings levelSettings = new LevelSettings();
		
		Line line2 = new Line(0,0,164,0);
		line2.setStrokeWidth(6);
		line2.setFill(Color.BLACK);
				
		objectSettings = new ObjectSettingsLevel();
		Util.objectSettings = objectSettings;	
			
		container.setAlignment(Pos.TOP_LEFT);
		container.getChildren().addAll(simulationControls,line1,levelSettings,line2,objectSettings);
		
		Button exit = new Button("Exit");
		exit.setGraphic(new ImageView(new Image("file:res/Images/close.png")));
		exit.setOnAction(e->{
			new LevelSelectionMenue(mainScene, primaryStage);
			SimulationControler.pause();			
		});
		AnchorPane.setBottomAnchor(exit, 10.0);

		this.getChildren().addAll(container,exit);
		this.getStyleClass().add("vbox");
		this.getStylesheets().add("file:res/css/SideBar.css");
	}
	
	public ObjectSettingsLevel getObjectSettings() {
		return objectSettings;
	}
	
}