package UI.LevelMenue;

import Simulation.SimulationControler;
import Simulation.Objects.GameObject;
import UI.Sounds;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LevelSettings extends VBox{
	
	protected float imageSize = 30;
	
	public LevelSettings() {	
		super(10);
		
		 Label title = new Label("Level");
		 title.setStyle("-fx-font: 18px 'Roboto';");
		 
		 Line line = new Line(0,0,110,0);
	     line.setStrokeWidth(3);
	     line.setStroke(Color.rgb(30, 30, 30));
	     
	     ImageView clearImg = new ImageView(new Image("file:res/Images/clear.png"));
		 clearImg.setFitWidth(imageSize);
		 clearImg.setFitHeight(imageSize);
	     
	     Label clearLabel = new Label("Clear");
	     HBox clear = new HBox(10);
	     clear.setAlignment(Pos.CENTER_LEFT);
	     clear.getStyleClass().add("fakeButton");
	     clear.getChildren().addAll(clearImg,clearLabel);
	     clear.setOnMouseClicked(e->{
	    	 if (!SimulationControler.isPlaying()) {
	    		 Sounds.playDeleteSound();
	    		  for (int i = 0; i < GameObject.allObjects.size(); i++) 
			    	   if(GameObject.allObjects.get(i).isEditable()) {
						GameObject.allObjects.get(i).remove();
						GameObject.allObjects.remove(i);
					}
	    	 }     
	     });
	     
	 	this.setAlignment(Pos.BASELINE_LEFT);
		this.getStyleClass().add("section");
		this.getChildren().addAll(title,line,clear);
		this.getStylesheets().add("file:res/css/SideBar.css");
	}

}
