package UI.MainMenue;

import java.io.File;
import java.util.Scanner;

import UI.Util;
import UI.LevelMenue.Level;
import UI.MainMenue.Elements.MenueLeaveButton;
import UI.MainMenue.Elements.MenueLevelButton;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LevelSelectionMenue extends StackPane{

	private boolean[] levelsDone;
	
	public LevelSelectionMenue(Scene mainScene,Stage primaryStage) {
		mainScene.setCursor(Cursor.DEFAULT);
		
		ImageView background = new ImageView(new Image("file:res/Images/background.jpg"));
		background.setFitWidth(this.getWidth());
		background.setFitHeight(this.getHeight());
		
		loadLevels();
			
		MenueLevelButton level1 = new MenueLevelButton("Level 1",levelsDone[0]);
		level1.setOnMouseClicked(e->{
			new Level(mainScene, "Level1",primaryStage);
			Util.currentLevel = 1;
		});
		MenueLevelButton level2 = new MenueLevelButton("Level 2",levelsDone[1]);
		level2.setOnMouseClicked(e->{
			new Level(mainScene, "Level2",primaryStage);
			Util.currentLevel = 2;
		});
		MenueLevelButton level3 = new MenueLevelButton("Level 3",levelsDone[2]);
		level3.setOnMouseClicked(e->{
			new Level(mainScene, "Level3",primaryStage);
			Util.currentLevel = 3;
		});
		MenueLevelButton level4 = new MenueLevelButton("Level 4",levelsDone[3]);
		level4.setOnMouseClicked(e->{
			new Level(mainScene, "Level4",primaryStage);
			Util.currentLevel = 4;
		});
		MenueLevelButton level5 = new MenueLevelButton("Level 5",levelsDone[4]);
		level5.setOnMouseClicked(e->{
			new Level(mainScene, "Level5",primaryStage);
			Util.currentLevel = 5;
		});
		MenueLevelButton level6 = new MenueLevelButton("Level 6",levelsDone[5]);
		level6.setOnMouseClicked(e->{
			new Level(mainScene, "Level6",primaryStage);
			Util.currentLevel = 6;
		});
		
		VBox levels1 = new VBox(40);
		levels1.setAlignment(Pos.CENTER);	
		levels1.getChildren().addAll(level1,level2,level3,level4,level5,level6);
				
		MenueLevelButton level7 = new MenueLevelButton("Level 7",levelsDone[6]);
		level7.setOnMouseClicked(e->{
			new Level(mainScene, "Level7",primaryStage);
			Util.currentLevel = 7;
		});
		MenueLevelButton level8 = new MenueLevelButton("Level 8",levelsDone[7]);
		level8.setOnMouseClicked(e->{
			new Level(mainScene, "Level8",primaryStage);
			Util.currentLevel = 8;
		});
		MenueLevelButton level9 = new MenueLevelButton("Level 9",levelsDone[8]);
		level9.setOnMouseClicked(e->{
			new Level(mainScene, "Level9",primaryStage);
			Util.currentLevel = 9;
		});
		MenueLevelButton level10 = new MenueLevelButton("Level 10",levelsDone[9]);
		level10.setOnMouseClicked(e->{
			new Level(mainScene, "Level10",primaryStage);
			Util.currentLevel = 10;
		});
		MenueLevelButton level11 = new MenueLevelButton("Level 11",levelsDone[10]);
		level11.setOnMouseClicked(e->{
			new Level(mainScene, "Level11",primaryStage);
			Util.currentLevel = 11;
		});
		MenueLevelButton level12 = new MenueLevelButton("Level 12",levelsDone[11]);
		level12.setOnMouseClicked(e->{
			new Level(mainScene, "Level12",primaryStage);
			Util.currentLevel = 12;
		});
		
		
		VBox levels2 = new VBox(40);
		levels2.setAlignment(Pos.CENTER);
		levels2.getChildren().addAll(level7,level8,level9,level10,level11,level12);
		
		HBox levelContainer = new HBox(40);
		levelContainer.setAlignment(Pos.CENTER);
		levelContainer.getChildren().addAll(levels1,levels2);
	
		MenueLeaveButton leave = new MenueLeaveButton();
		leave.setOnAction(e->{
			new MainMenue(mainScene,primaryStage);
		});
		
		VBox container = new VBox(40);
		container.getStyleClass().add("Container");
		container.setAlignment(Pos.CENTER);
		container.getStyleClass().add("LevelSelection");
		container.getChildren().addAll(levelContainer,leave);
		
		this.getChildren().addAll(background,container);		
		this.setEffect(Util.colorAdjust);
		this.getStyleClass().add("MainMenue");
		this.getStylesheets().add("file:res/css/MainMenue.css");	
		
		mainScene.setRoot(this);
	}
	
	public void loadLevels() {
		levelsDone= new boolean[12];
		try	{	
			
			Scanner sc = new Scanner(new File("res/level.txt"));
				
			int counter = 0;
			while (sc.hasNext()){	
				int scannedLine=Integer.valueOf(sc.nextLine());
				
				if(scannedLine==0)
					levelsDone[counter] = false;
				else
					levelsDone[counter] = true;
				
				counter++;
			}
			
		}catch (Exception e) {e.printStackTrace();}
	}
}
