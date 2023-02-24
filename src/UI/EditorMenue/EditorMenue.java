package UI.EditorMenue;

import UI.Util;
import UI.ObjectTransformer.ObjectTransformationListeners;
import UI.SideBar.SideBarEditor;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EditorMenue extends StackPane{
	
	public EditorMenue(Scene mainScene,Stage primaryStage) {

		Pane glassPane = new Pane();

		//Editorpane
		EditorTabPane editorTabPane = new EditorTabPane(glassPane);
		
		//Simulation control
		SideBarEditor sideBar = new SideBarEditor(mainScene,primaryStage);			
		ObjectTransformationListeners.addListeners(sideBar.getObjectSettings());
		
		BorderPane layout = new BorderPane();
		layout.setStyle("-fx-background-color: transparent;");
		
		StackPane container = new StackPane(Util.canvasWrapper,editorTabPane);
		container.setAlignment(Pos.BOTTOM_CENTER);
		layout.setCenter(container);
		layout.setLeft(sideBar);
		
		ImageView background = new ImageView(new Image(Util.background));
		background.setOpacity(0.4);

		this.setStyle("-fx-background-color: transparent;");
		this.setEffect(Util.colorAdjust);
		this.getChildren().addAll(glassPane,background,layout);
	}

	
	
}
