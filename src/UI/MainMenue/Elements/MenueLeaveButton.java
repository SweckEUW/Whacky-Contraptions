package UI.MainMenue.Elements;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenueLeaveButton extends Button{

	public MenueLeaveButton() {
		
		this.setGraphic(new ImageView(new Image("file:res/Images/close2.png")));
				
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().add("MenueElement");
		this.getStylesheets().add("file:res/css/MenueElements.css");
	}
	
}
