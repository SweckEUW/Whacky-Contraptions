package UI.MainMenue.Elements;

import java.io.File;

import UI.Util;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MenueChangeBackground extends HBox{
	
	public MenueChangeBackground(Stage primaryStage){	
		
		ImageView texture = new ImageView(new Image(Util.background));
		texture.setPreserveRatio(true);
		texture.setFitHeight(200);
		texture.setFitWidth(200);
		
		Label btnText = new Label("Change Background");
		btnText.setMinWidth(200);
	
		this.setOnMouseClicked(e->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/res/Backgrounds"));
			fileChooser.setTitle("Open Image File");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
			File file = fileChooser.showOpenDialog(primaryStage);					
			if(file!=null) {
				String fileName = file.getAbsolutePath();				
				int beginIndex = fileName.lastIndexOf("res");			
				fileName = fileName.substring(beginIndex);
				
				Util.background = "file:"+fileName;
				texture.setImage(new Image(Util.background));
			}
		});
		
		this.getChildren().addAll(btnText,texture);		
		this.setAlignment(Pos.CENTER);		
		this.getStyleClass().add("MenueElement");
		this.getStylesheets().add("file:res/css/MenueElements.css");		
	}
	
}
