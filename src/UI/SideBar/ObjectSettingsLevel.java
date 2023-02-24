package UI.SideBar;

import Simulation.Util;
import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Hairdryer;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Magnet;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Spinner;
import Simulation.RenderEngine.Core.Math.Vector2f;
import UI.Sounds;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ObjectSettingsLevel extends VBox {
	
	private ObjectSettingsTextField xPosition;
	private ObjectSettingsTextField yPosition;
	private ObjectSettingsTextField scale;
	private ObjectSettingsTextField rotation;
	 
	protected VBox settings;
	protected HBox head;
	protected Line line;
	protected Label properties;
 
    public ObjectSettingsLevel() {
        super(10);
        
        this.getStyleClass().add("section");      
        this.setAlignment(Pos.BASELINE_LEFT);
        this.getStylesheets().add("file:res/css/SideBar.css");
    }

    public void addUI(GameObject object) {
        Label name = new Label(object.getClass().getSimpleName());    
        name.setStyle("-fx-font: 20px 'Roboto';");
         
        ImageView deleteImg = new ImageView(new Image("file:res/Images/delete2.jpg"));        
        deleteImg.setFitHeight(30);
        deleteImg.setFitWidth(30);
        StackPane delete = new StackPane(deleteImg);
        delete.getStyleClass().add("delete");
        deleteImg.setOnMouseClicked(e->{
        	Sounds.playDeleteSound();
        	removeUI();
        	object.remove();
        	GameObject.allObjects.remove(object);        	
        });
        head = new HBox(10);
        head.setAlignment(Pos.CENTER_LEFT);
        head.getChildren().addAll(name,delete);
     
        line = new Line(0,20,110,20);
	    line.setStrokeWidth(3);
	    line.setStroke(Color.rgb(30, 30, 30));
        
        properties = new Label("Properties:");
        properties.setStyle("-fx-font: 14px 'Roboto';");
        
        //XPOSITION
        xPosition = new ObjectSettingsTextField("x", object, "m", object.getX(),"X Position");
        xPosition.getTextField().textProperty().addListener(new ChangeListener<String>() {
        	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		      	if(xPosition.getTextField().getText().length() == 0) {
		      		object.setX(0);
		          	object.setOriginalX(0);
		      	}else {
		      		object.setX(Float.valueOf(xPosition.getTextField().getText()));
		          	object.setOriginalX(Float.valueOf(xPosition.getTextField().getText()));
		      	}	   	
        	}
        });
        
        //YPOSITION
        yPosition = new ObjectSettingsTextField("y", object, "m", object.getY(),"Y Position");
        yPosition.getTextField().textProperty().addListener(new ChangeListener<String>() {
        	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		      	if(yPosition.getTextField().getText().length() == 0) {
		      		object.setY(0);
		          	object.setOriginalY(0);
		      	}else {
		      		object.setY(Float.valueOf(yPosition.getTextField().getText()));
		          	object.setOriginalY(Float.valueOf(yPosition.getTextField().getText()));
		      	}	   	
        	}
        });

        //ROTATION
        rotation = new ObjectSettingsTextField("rotation", object, "°", object.getRotation(),"Rotation");
        rotation.getTextField().textProperty().addListener(new ChangeListener<String>() {
        	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		      	if(rotation.getTextField().getText().length() == 0) {
		      		object.setRotation(0);
            		object.setOriginalrotation(0);
		      	}else {
		      		object.setRotation(Float.valueOf(rotation.getTextField().getText()));
		          	object.setOriginalrotation(Float.valueOf(rotation.getTextField().getText()));
		      	}	   	
        	}
        });
                
        //SCALE
        scale = new ObjectSettingsTextField("scale", object, "%", object.getScale()*100,"Scale");
        scale.getTextField().textProperty().addListener(new ChangeListener<String>() {
        	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		      	if(scale.getTextField().getText().length() == 0) 
		      		object.setScale(1);
		      	else 
		      		object.setScale(Float.valueOf(scale.getTextField().getText())/100);    		   	
        	}
        });
       
        //MASS
        float massf = object.getMass();
        if(object.getMass()>99999)
        	massf = 999999;
        ObjectSettingsTextField mass = new ObjectSettingsTextField("mass", object, "kg", massf,"Mass");
        mass.getTextField().textProperty().addListener(new ChangeListener<String>() {
        	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		      	if(mass.getTextField().getText().length() == 0) 
		      		object.setMass(0);
		      	else 
		      		object.setMass(Float.valueOf(mass.getTextField().getText()));
        	}	   	       	
        });
        
        //ELASTICITY
        ObjectSettingsTextField elasticity = new ObjectSettingsTextField("elasticity", object, "%", object.getCoefficientOfRestitution()*100,"Elasticity");    
        elasticity.getTextField().textProperty().addListener(new ChangeListener<String>() {
        	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		      	if(elasticity.getTextField().getText().length() == 0) 
		      		object.setCoefficientOfRestitution(1);
		      	else 
		      		object.setCoefficientOfRestitution(Float.valueOf(elasticity.getTextField().getText())/100);
        	}	   	       	
        });

              
        settings = new VBox(5);
        settings.getChildren().addAll(xPosition,yPosition,scale,rotation,mass,elasticity);
        
        
        if(object instanceof MoveableObject) {
	
        	Vector2f directionVector = new Vector2f(((MoveableObject) object).getVelocityX(), ((MoveableObject) object).getVelocityY());
        	Vector2f directionVector2= new Vector2f(((MoveableObject) object).getVelocityX(), ((MoveableObject) object).getVelocityY());
        	directionVector2.normalize();     
        	
        	ObjectSettingsTextField xDirection = new ObjectSettingsTextField("dirx", object, "", directionVector2.x,"X Direction");  
        	ObjectSettingsTextField yDirection = new ObjectSettingsTextField("diry", object, "", directionVector2.y,"Y Direction");   
        	 
        	//SPEED
        	ObjectSettingsTextField speed = new ObjectSettingsTextField("speed", object, "m/s", directionVector.length(),"Velocity"); 
        	speed.getTextField().textProperty().addListener(new ChangeListener<String>() {
               	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
       		      	if(speed.getTextField().getText().length() == 0) {
	       		      	((MoveableObject) object).setAccelerationX(0);
	    	        	((MoveableObject) object).setAccelerationY(0);
	    	        	((MoveableObject) object).setOriginalAccelerationX(0);
	    	        	((MoveableObject) object).setOriginalAccelerationY(0);
       		      	}else {
	       		     	Vector2f directionVector3= new Vector2f(Float.valueOf(xDirection.getTextField().getText()),Float.valueOf(yDirection.getTextField().getText()));
	       	        	directionVector3.normalize();     

	       		      	((MoveableObject) object).setVelocityX(directionVector3.x*Float.parseFloat(speed.getTextField().getText()));
	    	        	((MoveableObject) object).setVelocityY(directionVector3.y*Float.parseFloat(speed.getTextField().getText()));
	    	        	((MoveableObject) object).setOriginalAccelerationX(directionVector3.x*Float.parseFloat(speed.getTextField().getText()));
	    	        	((MoveableObject) object).setOriginalAccelerationY(directionVector3.y*Float.parseFloat(speed.getTextField().getText()));
       		      	}	   	
               	}
            });
                
	        //XDIRECTION         
	        xDirection.getTextField().textProperty().addListener(new ChangeListener<String>() {
               	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
       		      	if(xDirection.getTextField().getText().length() == 0) {
	       		      	((MoveableObject) object).setAccelerationX(0);
	    	        	((MoveableObject) object).setOriginalAccelerationX(0);
       		      	}else {
       		      		Vector2f directionVector3= new Vector2f(Float.valueOf(xDirection.getTextField().getText()),Float.valueOf(yDirection.getTextField().getText()));	
	       	        	directionVector3.normalize();   

	       		        ((MoveableObject) object).setVelocityX(directionVector3.x*Float.parseFloat(speed.getTextField().getText()));          		
	    	        	((MoveableObject) object).setOriginalAccelerationX(directionVector3.x*Float.parseFloat(speed.getTextField().getText()));
       		      	}	   	
               	}
            });
	       
	        //YDIRECTION
	        yDirection.getTextField().textProperty().addListener(new ChangeListener<String>() {
           	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	   		      	if(yDirection.getTextField().getText().length() == 0) {
	       		      	((MoveableObject) object).setAccelerationY(0);
	    	        	((MoveableObject) object).setOriginalAccelerationY(0);
	   		      	}else {
	   		      		Vector2f directionVector3= new Vector2f(Float.valueOf(xDirection.getTextField().getText()),Float.valueOf(yDirection.getTextField().getText()));
	       	        	directionVector3.normalize();   
	       	        	
	       		        ((MoveableObject) object).setVelocityY(directionVector3.y*Float.parseFloat(speed.getTextField().getText()));	
	    	        	((MoveableObject) object).setOriginalAccelerationY(directionVector3.y*Float.parseFloat(speed.getTextField().getText()));
	   		      	}	   	
	           	}
	        });   	       
	  
	        
	        settings.getChildren().addAll(speed,xDirection,yDirection);
        }
        
        if(object instanceof Hairdryer) {
        	
        	ObjectSettingsTextField wind = new ObjectSettingsTextField("wind", object, "", ((Hairdryer)object).getAmax(),"Wind Speed");   
        	wind.getTextField().textProperty().addListener(new ChangeListener<String>() {
             	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
     		      	if(wind.getTextField().getText().length() == 0) 
     		      		((Hairdryer)object).setAmax(0);
     		      	else 
     		      		((Hairdryer)object).setAmax(Float.parseFloat(wind.getTextField().getText()));	
             	}
             });

	        
	        settings.getChildren().add(wind);
        }
        
        if(object instanceof Magnet) {
        	
        	ObjectSettingsTextField magnet = new ObjectSettingsTextField("magnetism", object, "", ((Magnet) object).getCharge(),"Magnet Power");  
        	magnet.getTextField().textProperty().addListener(new ChangeListener<String>() {
	         	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	 		      	if(magnet.getTextField().getText().length() == 0) 
	 		      		((Magnet) object).setCharge(0);
	 		      	else 
	 		      		((Magnet) object).setCharge(Float.parseFloat(magnet.getTextField().getText())); 	
	         	}
	         });

	        settings.getChildren().add(magnet);
        }
        
        if(object instanceof Spinner) {
        	
        	ObjectSettingsTextField spinner = new ObjectSettingsTextField("RotSpeed", object, "Grad/s", ((Spinner) object).getSpeed(),"Rotation speed");  
        	spinner.getTextField().textProperty().addListener(new ChangeListener<String>() {
	         	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	 		      	if(spinner.getTextField().getText().length() == 0) 
	 		      		((Spinner) object).setSpeed(0);
	 		      	else 
	 		      		((Spinner) object).setSpeed(Float.parseFloat(spinner.getTextField().getText()));
	         	}
	         });

	        settings.getChildren().add(spinner);
        }
           
        completeUI(object);
    }

    public void completeUI(GameObject object) {
    	 this.getChildren().addAll(head,line,properties,settings);
    }
    
    public void removeUI() {
    	this.getChildren().clear();
    }
    
    public void updateUI(GameObject object) {
    	xPosition.getTextField().setText(Util.getRoundedString(object.getX()));
    	yPosition.getTextField().setText(Util.getRoundedString(object.getY()));
    	scale.getTextField().setText(Util.getRoundedString(object.getScale()*100));
    	rotation.getTextField().setText(Util.getRoundedString(object.getRotation()));    		    
    }
    
    
}
