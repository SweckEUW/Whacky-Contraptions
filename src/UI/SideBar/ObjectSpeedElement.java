package UI.SideBar;

import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.RenderEngine.Core.Math.Vector2f;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class ObjectSpeedElement extends VBox{

	private int imageSize = 20;
	
	private Label xPosition;
	private Label yPosition;
	private Label speed;
	private Label xDirection;
	private Label yDirection;
	 
	public ObjectSpeedElement(MoveableObject object) {
		super(5);

		Line line = new Line(0,0,110,0);
	    line.setStrokeWidth(3);
	    line.setStroke(Color.rgb(30, 30, 30));
	    
	    Label name = new Label(object.getClass().getSimpleName());
	    name.setStyle("-fx-font: 16px 'Roboto';");
	    
	    ImageView xImg = new ImageView(new Image("file:res/Images/object-settings/x.png"));
        xImg.setFitHeight(imageSize);
        xImg.setFitWidth(imageSize);
        xPosition = new Label(Simulation.Util.getRoundedString(object.getX()));
        Label xUnit = new Label("m");
        HBox xContainer = new HBox(5);
        xContainer.getChildren().addAll(xImg,xPosition,xUnit);
        Tooltip xToolTip = new Tooltip("X Position");
        xToolTip.setShowDelay(Duration.millis(0));
        Tooltip.install(xContainer,xToolTip);
	    
        ImageView yImg = new ImageView(new Image("file:res/Images/object-settings/y.png"));
        yImg.setFitHeight(imageSize);
        yImg.setFitWidth(imageSize);
        yPosition = new Label(Simulation.Util.getRoundedString(object.getY()));
        Label yUnit = new Label("m");
        HBox yContainer = new HBox(5);
        yContainer.getChildren().addAll(yImg,yPosition,yUnit);
        Tooltip yToolTip = new Tooltip("Y Position");
        yToolTip.setShowDelay(Duration.millis(0));
        Tooltip.install(yContainer,yToolTip);
        
        
        Vector2f directionVector = new Vector2f(((MoveableObject) object).getVelocityX(), ((MoveableObject) object).getVelocityY());
        
    	//SPEED
    	ImageView speedImg = new ImageView(new Image("file:res/Images/object-settings/speed.png"));
    	speedImg.setFitHeight(imageSize);
    	speedImg.setFitWidth(imageSize);
        speed = new Label(Simulation.Util.getRoundedString(directionVector.length()));
        Label speedUnit = new Label("m/s");
        HBox speedContainer = new HBox(5);
        speedContainer.getChildren().addAll(speedImg,speed,speedUnit);
        Tooltip speedToolTip = new Tooltip("Speed");
        speedToolTip.setShowDelay(Duration.millis(0));
        Tooltip.install(speedContainer,speedToolTip);
        
        
        directionVector.normalize();
        
        //DIRECTION
        ImageView xDirectionImg = new ImageView(new Image("file:res/Images/object-settings/dirx.png"));
        xDirectionImg.setFitHeight(imageSize);
        xDirectionImg.setFitWidth(imageSize);
        xDirection = new Label(Simulation.Util.getRoundedString(directionVector.x));
        HBox xDirectionContainer = new HBox(5);
        xDirectionContainer.getChildren().addAll(xDirectionImg,xDirection);
        Tooltip xDirToolTip = new Tooltip("X Direction");
        xDirToolTip.setShowDelay(Duration.millis(0));
        Tooltip.install(xDirectionContainer,xDirToolTip);
      
        
        ImageView yDirectionImg = new ImageView(new Image("file:res/Images/object-settings/diry.png"));
        yDirectionImg.setFitHeight(imageSize);
        yDirectionImg.setFitWidth(imageSize);
        yDirection = new Label(Simulation.Util.getRoundedString(directionVector.y));   	       
        HBox yDirectionContainer = new HBox(5);
        yDirectionContainer.getChildren().addAll(yDirectionImg,yDirection);
        Tooltip yDirToolTip = new Tooltip("Y Direction");
        yDirToolTip.setShowDelay(Duration.millis(0));
        Tooltip.install(yDirectionContainer,yDirToolTip);
       
        this.setOnMouseEntered(e->{
        	object.highlight(true);
        });
   
        this.setOnMouseExited(e->{
        	object.highlight(false);
        });
        
		this.setAlignment(Pos.TOP_LEFT);
	    this.getChildren().addAll(line,name,xContainer,yContainer,speedContainer,xDirectionContainer,yDirectionContainer);
	    this.getStylesheets().add("file:res/css/SideBar.css");
	}
	
	public void update(MoveableObject object) {
		xPosition.setText(Simulation.Util.getRoundedString(object.getX()));
		yPosition.setText(Simulation.Util.getRoundedString(object.getY()));
		
		Vector2f directionVector = new Vector2f(((MoveableObject) object).getVelocityX(), ((MoveableObject) object).getVelocityY());		  
		speed.setText(Simulation.Util.getRoundedString(directionVector.length()));

		directionVector.normalize();	
		
		xDirection.setText(Simulation.Util.getRoundedString(directionVector.x));
		yDirection.setText(Simulation.Util.getRoundedString(directionVector.y));
	}
	
}