package UI.SideBar;

import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.MoveableObject;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ObjectSpeed extends Pane{

	public ObjectSpeed() {
		VBox content = new VBox(10);
		content.setAlignment(Pos.TOP_LEFT);
		content.getStyleClass().add("section");      	
		content.getStylesheets().add("file:res/css/SideBar.css");
		
		Label title = new Label("Properties");
		title.setStyle("-fx-font: 18px 'Roboto';");

		content.getChildren().add(title);
	    
		for (GameObject object : GameObject.allObjects) {
			if(object instanceof MoveableObject) {
				ObjectSpeedElement element = new ObjectSpeedElement((MoveableObject) object);
				((MoveableObject) object).setElement(element);
				content.getChildren().add(element);
			}
		}
			
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(content);
		scrollPane.setMaxHeight(400);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
	    scrollPane.getStylesheets().add("file:res/css/EditorTabPane.css");
		
		this.getChildren().add(scrollPane);
	}
}
