package UI.MainMenue;

import java.io.File;

import UI.Util;
import UI.EditorMenue.EditorMode;
import UI.LevelMenue.Level;
import UI.MainMenue.Elements.MenueButton;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainMenue extends StackPane{

	public MainMenue(Scene mainScene,Stage primaryStage) {		
		mainScene.setCursor(Cursor.DEFAULT);
		
		ImageView background = new ImageView(new Image("file:res/Images/background.jpg"));
		background.setFitWidth(this.getWidth());
		background.setFitHeight(this.getHeight());
		
		MenueButton exit = new MenueButton("Exit");
		exit.setOnMouseClicked(e->{
			Platform.exit();
	        System.exit(0);
		});
		
		MenueButton loadLevel = new MenueButton("Load Level");
		loadLevel.setOnMouseClicked(e->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/res/Levels"));
			fileChooser.setTitle("Open OBJ file");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("txt Files","*txt"));
			File file = fileChooser.showOpenDialog(primaryStage);			
			if(file!=null) {
				String fileName = file.getAbsolutePath();				
				int beginIndex = fileName.lastIndexOf("Levels");
				int lastIndex = fileName.indexOf(".txt");				
				fileName = fileName.substring(beginIndex+7, lastIndex);
				new Level(mainScene, fileName, primaryStage);
			}
		});
		
		MenueButton settings = new MenueButton("Settings");
		settings.setOnMouseClicked(e->{
			new SettingsMenue(mainScene,primaryStage);
		});
		
		MenueButton editor = new MenueButton("Level Editor");
		editor.setOnMouseClicked(e->{
			new EditorMode(mainScene,primaryStage);
		});
		
		MenueButton levelSelector = new MenueButton("Level Selection");
		levelSelector.setOnMouseClicked(e->{
			new LevelSelectionMenue(mainScene,primaryStage);
		});
	
		VBox container = new VBox(40);
		container.getStyleClass().add("Vbox");
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(levelSelector,editor,loadLevel,settings,exit);
		
		this.getChildren().addAll(background,container);
		this.setEffect(Util.colorAdjust);
		this.getStyleClass().add("MainMenue");
		this.getStylesheets().add("file:res/css/MainMenue.css");	
		
		mainScene.setRoot(this);		
		
		Util.primaryStage = primaryStage;
		Util.mainScene = mainScene;
		UI.Util.editorMode = false;
	}

}
