package Simulation.Objects.StaticObjects.StaticExternalObjects;

import Simulation.LevelExportImport;
import Simulation.SimulationControler;
import Simulation.Objects.GameObject;
import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import UI.Sounds;
import UI.Util;
import UI.MainMenue.LevelSelectionMenue;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Bucket extends StaticExternalObject{

	private static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(1f), 4f);
	
	private float offset = 20;
	
	public Bucket(float x, float y) {
		super("bucket","bucket","PlaneTexture.jpg", material, x, y);
		setScale(0.4f);
	}

	@Override
	public void update() {
		if(!Util.editorMode)
			for (GameObject object : allObjects) 
				if(object.getCollisionContext().getID() != collisionContext.getID())
					if(object.getX() <= x+offset && object.getX() >= x-offset && object.getY() <= y+offset && object.getY() >= y-offset) {
						SimulationControler.pause();
						LevelExportImport.exportLevelProgress();
						Platform.runLater(new Runnable(){
	
							@Override
							public void run() {
								Sounds.playDoneSound();
								
								Stage primaryStage = Util.primaryStage;
								Scene mainScene = Util.mainScene;
								
								Effect effect = new GaussianBlur();
	
								mainScene.getRoot().setEffect(effect);
								
								Label text = new Label("Level Done!");
								Button exit = new Button("Exit");
																
								HBox container = new HBox(10);
								container.setAlignment(Pos.CENTER);
								container.getChildren().addAll(exit);
								
						        VBox root = new VBox(20);
						        root.setAlignment(Pos.CENTER);
						        root.getChildren().addAll(text,container);
						 
						        Scene secondScene = new Scene(root, 250, 150);
						 
						        Stage newWindow = new Stage();
						        newWindow.setTitle("Congratulations!");
						        newWindow.setScene(secondScene);
						        newWindow.setOnCloseRequest(e->{
						        	mainScene.getRoot().setEffect(null);
						        	newWindow.close();
						        });

						 	           
						        exit.setOnAction(e2->{
						        	new LevelSelectionMenue(mainScene, primaryStage);
						        	newWindow.close();
								});
			        
						        newWindow.initModality(Modality.WINDOW_MODAL);
						        newWindow.initOwner(primaryStage);
						 
						        newWindow.show();
							}
	
	
	
							
							});
						
					}
					
		
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		
	}

}
