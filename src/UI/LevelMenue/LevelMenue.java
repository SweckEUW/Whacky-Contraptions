package UI.LevelMenue;

import UI.Util;
import UI.ObjectTransformer.ObjectTransformationListeners;
import UI.SideBar.SideBarLevel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LevelMenue extends StackPane{
	
	public LevelMenue(Scene mainScene,String level,Stage primaryStage) {

		Pane glassPane = new Pane();

		//Editorpane
		LevelTabPane levelTabPane = new LevelTabPane(glassPane,level);

		//Simulation controls
		SideBarLevel leftSideUI = new SideBarLevel(mainScene,primaryStage);
		ObjectTransformationListeners.addListeners(leftSideUI.getObjectSettings());
		
		BorderPane layout = new BorderPane();
		layout.setStyle("-fx-background-color: transparent;");

		StackPane container = new StackPane(Util.canvasWrapper,levelTabPane);
		container.setAlignment(Pos.BOTTOM_CENTER);
		layout.setCenter(container);
		layout.setLeft(leftSideUI);
		
		ImageView background = new ImageView(new Image(Util.background));
		background.setOpacity(0.4);


		this.setStyle("-fx-background-color: transparent;");
		this.setEffect(Util.colorAdjust);
		this.getChildren().addAll(glassPane,background,layout);
	}
	
}
