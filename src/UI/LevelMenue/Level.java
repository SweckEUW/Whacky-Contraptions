package UI.LevelMenue;

import Simulation.Simulation;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Level {

	public Level(Scene mainScene,String level,Stage primaryStage) {	
		LevelMenue menue = new LevelMenue(mainScene,level,primaryStage);
		mainScene.setRoot(menue);	
		
		Simulation.start(level);
	}
	
}
