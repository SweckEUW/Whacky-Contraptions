package MAIN;

import java.io.File;

import Simulation.Simulation;
import UI.Sounds;
import UI.Util;
import UI.MainMenue.MainMenue;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class Main extends Application{


	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {	
	
		Simulation.initialize();

		Font.loadFont("file:res/Roboto.ttf", 10);
		
		Scene mainScene = new Scene(new Pane(),1480, 920);
		
		MainMenue menue = new MainMenue(mainScene,primaryStage);
		
		if(!Util.devMode) {				
			FadeTransition fadein = new FadeTransition(Duration.millis(1000), menue);
			fadein.setFromValue(0);  
			fadein.setToValue(1);  
	        
			Media media = new Media(new File("res/intro.mp4").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					fadein.play();
					menue.getChildren().remove(menue.getChildren().size()-1);
				}
			});
			mediaPlayer.play();
			MediaView intro = new MediaView(mediaPlayer);
	
			menue.getChildren().add(intro);
		}
		
		primaryStage.getIcons().add(new Image("file:res/icon.png"));
        primaryStage.setTitle("Visual Computing 2");
		primaryStage.setScene(mainScene);
		primaryStage.setMinWidth(1280);
		primaryStage.setMinHeight(920);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e->{
			Sounds.stopSounds();
		});
		
		Sounds.initSounds();
		Sounds.playMainTheme();
	}
	
}