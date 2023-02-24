package UI.EditorMenue.Tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class EditorTab extends Tab{

	protected HBox content;
	
	public EditorTab(Pane glass,String icon,String name) {
		//Tab Name
		VBox nameAndIcon = new VBox();
		Label nameLabel = new Label(name); 
		nameLabel.setAlignment(Pos.BOTTOM_CENTER);
		
		//Tab Icon
//	    ImageView iconImageView = new ImageView(new Image("file:res/TabImages/"+icon));
//	    iconImageView.setFitWidth(25); 
//	    iconImageView.setFitHeight(25);  
		
	    nameAndIcon.getChildren().addAll(nameLabel);
	    this.setGraphic(nameAndIcon);
			    
	    content = new HBox(10);
		content.setAlignment(Pos.CENTER_LEFT);
		content.setStyle("-fx-background-color: transparent;");
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(content);
		scrollPane.setMinHeight(120);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    scrollPane.getStylesheets().add("file:res/css/EditorTabPane.css");
	    
		this.setContent(scrollPane);
		createContent(glass);
	}
	
	protected abstract void createContent(Pane glass);
	
}
