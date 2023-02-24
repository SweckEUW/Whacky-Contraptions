package UI.EditorMenue;

import Simulation.LevelExportImport;
import Simulation.SimulationControler;
import Simulation.Objects.GameObject;
import UI.Sounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditorLevelSettings extends VBox{
	
	protected float imageSize = 30;
	
	public EditorLevelSettings(Stage primaryStage) {	
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
		        	GameObject.allObjects.clear();	
				}
	     });
	     		 
	     ImageView saveImg = new ImageView(new Image("file:res/Images/save.png"));
		 saveImg.setFitHeight(imageSize);
		 saveImg.setFitWidth(imageSize);
	     
	     Label saveLabel = new Label("Save");
	     HBox save = new HBox(10);
	     save.setAlignment(Pos.CENTER_LEFT);
	     save.getStyleClass().add("fakeButton");
	     save.getChildren().addAll(saveImg,saveLabel);
	     save.setOnMouseClicked(e->{
			
			Label text = new Label("Name your Level!");
			TextField textField = new TextField();
			textField.setMaxWidth(200);
			Button enter = new Button("Save");
			
	        VBox root = new VBox(20);
	        root.setAlignment(Pos.CENTER);
	        root.getChildren().addAll(text,textField,enter);
	 
	        Scene secondScene = new Scene(root, 250, 150);
	 
	        Stage newWindow = new Stage();
	        newWindow.setTitle("Enter a name!");
	        newWindow.setScene(secondScene);
	 	           
	        enter.setOnAction(e2->{
	        	LevelExportImport.ExportLevel(textField.getText());
	        	newWindow.close();
			});

	        newWindow.initModality(Modality.WINDOW_MODAL);
	        newWindow.initOwner(primaryStage);
	          
	        newWindow.setX(primaryStage.getX() + primaryStage.getWidth()/4);
	        newWindow.setY(primaryStage.getY() + primaryStage.getHeight()/4);
	 
	        newWindow.show();
	     
	    });
		
		this.setAlignment(Pos.BASELINE_LEFT);
		this.getStyleClass().add("section");
		this.getChildren().addAll(title,line,save,clear);
		this.getStylesheets().add("file:res/css/SideBar.css");
	}

}
