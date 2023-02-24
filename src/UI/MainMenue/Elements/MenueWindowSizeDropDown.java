package UI.MainMenue.Elements;

import UI.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MenueWindowSizeDropDown extends HBox{
	
	public MenueWindowSizeDropDown(Stage primaryStage){	
		ObservableList<String> options =FXCollections.observableArrayList(
			        "1080 x 720",
			        "1920 x 1280"
			    );
		ComboBox<String> comboBox = new ComboBox<String>(options);
		comboBox.setOnAction(e->{
			if(comboBox.getValue().toString().startsWith("1080")) {
				primaryStage.setWidth(1080);
				primaryStage.setHeight(720);
				Util.screenSize=1;
			}else {
				primaryStage.setWidth(1920);
				primaryStage.setHeight(1280);
				Util.screenSize=2;
			}		
		});
		
		if(Util.screenSize==1)
			comboBox.setValue("1080 x 720");
		else 
			comboBox.setValue("1920 x 1280");
		
		
		Label btnText = new Label("Window Size");
		btnText.setMinWidth(230);
		
		this.getChildren().addAll(btnText,comboBox);		
		this.setAlignment(Pos.CENTER);
		
		this.getStyleClass().add("MenueElement");
		this.getStylesheets().add("file:res/css/MenueElements.css");		
	}

}
