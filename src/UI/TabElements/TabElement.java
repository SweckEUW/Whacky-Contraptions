package UI.TabElements;

import Simulation.SimulationControler;
import Simulation.Objects.GameObject;
import UI.Sounds;
import UI.Util;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public abstract class TabElement extends VBox {

	 private ImageView icon;
	 private String imageURL;
	 private double size = 80;
	 private int ammount;
	 private int originalAmmount;
	 private Label ammountText;
	 
	 private GameObject object;
	 
    public TabElement(Pane glass,String name, String imageURL,int ammount) {
        super(10);
        this.setAlignment(Pos.BOTTOM_CENTER);
        
        this.ammount = ammount;
        this.originalAmmount=ammount;
        
        this.imageURL = imageURL;
        
        Label nameLabel = new Label(name);
        
        StackPane container = new StackPane();
        
        this.icon = new ImageView(new Image("file:res/TabImages/"+imageURL));
        icon.setFitWidth(size);
        icon.setFitHeight(size);
        
        StackPane wrapper = new StackPane();
        Circle circle = new Circle(15);
        circle.getStyleClass().add("circle");
        ammountText = new Label(Integer.toString(ammount));
        wrapper.getChildren().addAll(circle,ammountText);
        wrapper.setTranslateX(30);
        wrapper.setTranslateY(30);
        container.getChildren().addAll(icon,wrapper);
        
        this.getChildren().addAll(container, nameLabel);
        
        this.getStyleClass().add("TabElement");
		this.getStylesheets().add("file:res/css/EditorTabPane.css");
		
		addDragAndDropWithAmmount(glass);         
    }
    
    public TabElement(Pane glass,String name, String imageURL) {
        super(10);
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.imageURL = imageURL;
        
        Label nameLabel = new Label(name);
        
        this.icon = new ImageView(new Image("file:res/TabImages/"+imageURL));
        icon.setFitWidth(size);
        icon.setFitHeight(size);
        
        this.getChildren().addAll(icon, nameLabel);
        
        this.getStyleClass().add("TabElement");
        this.getStylesheets().add("file:res/css/EditorTabPane.css");
		
        addDragAndDrop(glass);         
    }
    
    protected void addDragAndDropWithAmmount(Pane glass) {
    	//DRAG AND DROP
    	ImageView clone = new ImageView(new Image("file:res/TabImages/"+imageURL));
		clone.setFitHeight(size);
		clone.setFitWidth(size);
		
		this.setOnDragDetected(e->{  
			if(ammount>0 && !SimulationControler.isPlaying()) {
				Util.dragMode = true;
	        	clone.relocate(e.getSceneX()-(size/2),e.getSceneY()-(size/2));
	        	glass.getChildren().add(clone);
	        	glass.toFront(); 
	        	decreaseAmmount();
			}			
        });
        
		this.setOnMouseDragged(e->{
    		clone.relocate(e.getSceneX()-(size/2),e.getSceneY()-(size/2));
    	});
    			
		this.setOnMouseReleased(e1->{
        	glass.toBack();
        	glass.getChildren().clear();    
	    	Util.canvasWrapper.setOnMouseEntered(e2->{    	
	    		if(Util.dragMode && !SimulationControler.isPlaying()) {
		            float x = Util.convertMouseX(e2.getX());
		            float y = Util.convertMouseY(e2.getY());
		            Sounds.playCreateSound();
		            this.object = createObject(x, y);
		            object.setTabPane(this);
		            Util.dragMode = false;
	    		}
	    	});			
        });
    }

    protected void addDragAndDrop(Pane glass) {
    	//DRAG AND DROP
    	ImageView clone = new ImageView(new Image("file:res/TabImages/"+imageURL));
		clone.setFitHeight(size);
		clone.setFitWidth(size);
		
		this.setOnDragDetected(e->{  
			if(!SimulationControler.isPlaying()) {
				Util.dragMode = true;
		        clone.relocate(e.getSceneX()-(size/2),e.getSceneY()-(size/2));
		        glass.getChildren().add(clone);
		        glass.toFront(); 	
			}
        });
        
		this.setOnMouseDragged(e->{
    		clone.relocate(e.getSceneX()-(size/2),e.getSceneY()-(size/2));
    	});
    			
		this.setOnMouseReleased(e1->{
        	glass.toBack();
        	glass.getChildren().clear();    
	    	Util.canvasWrapper.setOnMouseEntered(e2->{    	
	    		if(Util.dragMode) {
		            float x = Util.convertMouseX(e2.getX());
		            float y = Util.convertMouseY(e2.getY());
		            Sounds.playCreateSound();
		            createObject(x, y);
		            Util.dragMode = false;
	    		}
	    	});			
        });
    }
    
    
    protected void decreaseAmmount(){
    	ammount--;
    	ammountText.setText(Integer.toString(ammount));
    }

	protected abstract GameObject createObject(float x, float y);

	public void resetCounter() {
		this.ammount = originalAmmount;
		ammountText.setText(Integer.toString(ammount));
	}

}
