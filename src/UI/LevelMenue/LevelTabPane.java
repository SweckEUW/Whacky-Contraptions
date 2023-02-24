package UI.LevelMenue;

import java.io.File;
import java.util.Scanner;

import UI.TabElements.BasketballTabElement;
import UI.TabElements.BeachBallTabElement;
import UI.TabElements.BucketTabElement;
import UI.TabElements.HairdryerTabElement;
import UI.TabElements.MagnetTabElement;
import UI.TabElements.PlaneTabElement;
import UI.TabElements.PortalTabElement;
import UI.TabElements.SpinnerTabElement;
import UI.TabElements.TabElement;
import UI.TabElements.TennisBallTabElement;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LevelTabPane extends StackPane{

	protected HBox content;
	
	public LevelTabPane(Pane glass,String level) {
	   
	   	content = new HBox(10);
		content.setAlignment(Pos.CENTER_LEFT);
		content.setStyle("-fx-background-color: transparent;");
		content.getStyleClass().add("hbox");
		ScrollPane scollPane = new ScrollPane();
		
		scollPane.setContent(content);		
		scollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);	
		
		this.setMinHeight(120);
		this.getStyleClass().add("LevelTabPane");
		this.getStylesheets().add("file:res/css/LevelTabPane.css");
		this.getChildren().add(scollPane);
	   
		loadElements(glass, level);
	}
   
	public void loadElements(Pane glass,String level) {
	   try	{	
			
			Scanner sc = new Scanner(new File("res/levels/"+level+".txt"));

			while (sc.hasNext()){
				
				boolean playable = Boolean.parseBoolean(sc.nextLine());
				
				if(playable) {
					
					String type=sc.nextLine();
										
					TabElement element = null;
					
					switch (type) {
					case "BasketBall": {
						element = new BasketballTabElement(glass,1);
						break;
					}
					case "Plane": {
						element = new PlaneTabElement(glass, 1);
						break;
					}
					case "Bucket": {
						element = new BucketTabElement(glass, 1);
						break;
					}
					case "Spinner": {
						element = new SpinnerTabElement(glass, 1);
						break;
					}
					case "Magnet": {
						element = new MagnetTabElement(glass, 1);
						break;
					}
					case "Hairdryer": {
						element = new HairdryerTabElement(glass, 1);
						break;
					}
					case "Portal": {
						element = new PortalTabElement(glass, 1);
						break;
					}
					case "BeachBall": {
						element = new BeachBallTabElement(glass, 1);
						break;
					}
					case "TennisBall": {
						element = new TennisBallTabElement(glass, 1);
						break;
					}
					
					default:
						throw new IllegalArgumentException("Unexpected value: " + type);
					}
					
					content.getChildren().add(element);
														
				}else 	
					sc.nextLine();
				
				
				sc.nextLine();sc.nextLine();sc.nextLine();
				sc.nextLine();sc.nextLine();sc.nextLine();
			}
			
		}catch (Exception e) {e.printStackTrace();}
   }
  
}
