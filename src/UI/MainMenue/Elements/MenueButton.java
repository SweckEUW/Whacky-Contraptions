package UI.MainMenue.Elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MenueButton extends HBox{

	public MenueButton(String text) {
		super(10);
		
		ImageView unchecked = new ImageView(new Image("file:res/Images/unchecked.png"));
		unchecked.setFitHeight(20);
		unchecked.setFitWidth(20);
		ImageView checked = new ImageView(new Image("file:res/Images/checked.png"));
		checked.setFitHeight(20);
		checked.setFitWidth(20);
		
		Label btnText = new Label(text);
		btnText.setMinWidth(200);
		
		this.getChildren().addAll(unchecked,btnText);		
		this.setAlignment(Pos.CENTER);
		
		this.getStyleClass().add("MenueElement");
		this.getStylesheets().add("file:res/css/MenueElements.css");
		
		this.setOnMouseEntered(e->{
			this.getChildren().set(0, checked);			
		});
		
		this.setOnMouseExited(e->{
			this.getChildren().set(0, unchecked);			
		});
	}
	
}
