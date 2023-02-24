package UI.MainMenue.Elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MenueLevelButton extends HBox{

	public MenueLevelButton(String text,boolean played) {
		super(10);
		
		if(played) {
			ImageView checked = new ImageView(new Image("file:res/Images/checked.png"));
			checked.setFitHeight(20);
			checked.setFitWidth(20);
			this.getStyleClass().add("checked");
			this.getChildren().add(checked);
		}else {
			ImageView unchecked = new ImageView(new Image("file:res/Images/unchecked.png"));
			unchecked.setFitHeight(20);
			unchecked.setFitWidth(20);
			this.getStyleClass().add("unchecked");
			this.getChildren().add(unchecked);
		}
		
		Label btnText = new Label(text);
		
		this.getChildren().add(btnText);		
		this.setAlignment(Pos.CENTER);
		
		this.getStyleClass().add("MenueElement");
		this.getStylesheets().add("file:res/css/MenueElements.css");
	}

}
