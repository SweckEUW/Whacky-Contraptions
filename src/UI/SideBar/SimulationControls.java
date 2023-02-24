package UI.SideBar;

import Simulation.SimulationControler;
import UI.Sounds;
import UI.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
 
public class SimulationControls extends VBox{

	protected float imageSize = 30;

	public SimulationControls(Scene mainScene,Stage primaryStage,VBox container) {
		super(10);
		 
		 Label title = new Label("Simulation");
		 title.setStyle("-fx-font: 18px 'Roboto';");
		 
		 Line line = new Line(0,0,110,0);
	     line.setStrokeWidth(3);
	     line.setStroke(Color.rgb(30, 30, 30));
	        
	     ImageView playImg = new ImageView(new Image("file:res/Images/play.png"));
	     playImg.setFitWidth(imageSize);
	     playImg.setFitHeight(imageSize);
	     ImageView pauseImg = new ImageView(new Image("file:res/Images/pause.png"));
	     pauseImg.setFitWidth(imageSize);
	     pauseImg.setFitHeight(imageSize);
		    	     
	     Label playpauseText = new Label("Play");
	     HBox playpause = new HBox(10);
	     playpause.setAlignment(Pos.CENTER_LEFT);
	     playpause.getStyleClass().add("fakeButton");
	     playpause.getChildren().addAll(playImg,playpauseText);
	     playpause.setOnMouseClicked(e->{
	    	 if(SimulationControler.isPlaying()) {
	    		 Sounds.playStopSound();
	    		 playpauseText.setText("Play");
	    		 SimulationControler.pause();
	    		 playpause.getChildren().set(0, playImg);
	    		 container.getChildren().remove(container.getChildren().size()-1);
	    	 }else {
	 			 Sounds.playPlaySound();
	    		 playpauseText.setText("Pause");
	    		 SimulationControler.play();
	    		 playpause.getChildren().set(0, pauseImg);
	    		 Util.objectSettings.removeUI();	    		
	    		 container.getChildren().add(new ObjectSpeed());
			}	        
	     });
	     
	     ImageView stopImg = new ImageView(new Image("file:res/Images/reset.png"));
	     stopImg.setFitWidth(imageSize);
	     stopImg.setFitHeight(imageSize);
	     
	     Label stopLabel = new Label("Restart");
	     HBox stop = new HBox(10);
	     stop.setAlignment(Pos.CENTER_LEFT);
	     stop.getStyleClass().add("fakeButton");
	     stop.getChildren().addAll(stopImg,stopLabel);
	     stop.setOnMouseClicked(e->{
	    	 if (SimulationControler.isPlaying()) {
	             SimulationControler.pause();
	             container.getChildren().remove(container.getChildren().size()-1);
	             playpauseText.setText("Play");
	             playpause.getChildren().set(0, playImg);
	         }
	         SimulationControler.restart();
	     });


	    Slider slider = new Slider();
	    slider.setOrientation(Orientation.VERTICAL);
	    slider.setMin(1);
	    slider.setMax(10);
	    slider.setValue(SimulationControler.getUpdateTime());
	    slider.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	         	SimulationControler.setUpdateTime(newValue.intValue());
	         }
	    });
	    	  
		Label speed = new Label("Playback");
		speed.setStyle("-fx-font: 15px 'Roboto';");
		Label speed2 = new Label("speed");
		speed2.setStyle("-fx-font: 15px 'Roboto';");
		
		VBox name = new VBox();
		name.getChildren().addAll(speed,speed2);
		name.setAlignment(Pos.CENTER_LEFT);
		
		Label one = new Label("2x");
		one.setStyle("-fx-font: 15px 'Roboto';");
		Label two = new Label("1x");
		two.setStyle("-fx-font: 15px 'Roboto';");
		Label three = new Label("1/2x");
		three.setStyle("-fx-font: 15px 'Roboto';");
		VBox counter = new VBox(50);
		counter.getChildren().addAll(one,two,three);
		
		HBox speedbox = new HBox();
		speedbox.getChildren().addAll(slider,counter,name);
		speedbox.setAlignment(Pos.CENTER_LEFT);
	    
	    this.setAlignment(Pos.BASELINE_LEFT);
	    this.getStyleClass().add("section");
	    this.getChildren().addAll(title,line,playpause,stop,speedbox);
	    this.getStylesheets().add("file:res/css/SideBar.css");
	}
	
}
