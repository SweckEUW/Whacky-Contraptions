package UI.MainMenue.Elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MenueCheckBox extends HBox{
	
	private Boolean checkedBool = true;
	private ImageView unchecked;
	private ImageView checked;
	
	public MenueCheckBox(String name){	
		unchecked = new ImageView(new Image("file:res/Images/unchecked.png"));
		unchecked.setFitHeight(20);
		unchecked.setFitWidth(20);
		checked = new ImageView(new Image("file:res/Images/checked.png"));
		checked.setFitHeight(20);
		checked.setFitWidth(20);
		
		Label btnText = new Label(name);
		btnText.setMinWidth(350);
		
		this.getChildren().addAll(btnText,checked);		
		this.setAlignment(Pos.CENTER);		
		this.getStyleClass().add("MenueElement");
		this.getStylesheets().add("file:res/css/MenueElements.css");		
	}
	
	public boolean isChecked() {
		return checkedBool;
	}
	
	public void setChecked(boolean checkedBool) {
		this.checkedBool = checkedBool;
		
		if(!checkedBool)
			this.getChildren().set(1, unchecked);			
		else
			this.getChildren().set(1, checked);	
	}
	
}
