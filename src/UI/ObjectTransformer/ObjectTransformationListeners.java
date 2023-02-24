package UI.ObjectTransformer;

import Simulation.SimulationControler;
import Simulation.Objects.GameObject;
import UI.Util;
import UI.SideBar.ObjectSettingsLevel;
import javafx.scene.Cursor;

public class ObjectTransformationListeners {

    public static void addListeners(ObjectSettingsLevel objectSettings) {
    	
        Util.canvasWrapper.setOnMouseClicked(e-> {
        	if(!SimulationControler.isPlaying()) {
        		
	        	int slectedObjectIndex=-1;
	        	boolean objectGotSelected = false;
	        	
	        	//Unselect Object
	            for (int first = 0; first < GameObject.allObjects.size(); first++) {
	            	
	            	if(GameObject.allObjects.get(first).isEditable()) {
	            				            
		            	float distance = ObjectPickingMethods.calculateCircleDistance(e,GameObject.allObjects.get(first));
		            	
		            	if (GameObject.allObjects.get(first).isSelected()) {
		            		objectGotSelected=true;
		            		slectedObjectIndex=first;
		                    if (!(distance <= GameObject.allObjects.get(first).getObjectTransformer().getCircleUI().getRadius()*1.5f)) {
		                    	GameObject.allObjects.get(first).unSelectObject();
		                		objectSettings.removeUI();		                		
		                    }

		                    break;
		            	}	
		            	objectGotSelected=false;
	            	}    	
	            }
	            
	          //Initial Object picking
	            	for (int first = 0; first < GameObject.allObjects.size(); first++) {
	            		
	            		if(GameObject.allObjects.get(first).isEditable()) {
	            			
		                for (int second = 0; second < GameObject.allObjects.get(first).getCollisionContext().getBoundingPolygons().length; second++)
		                	if(!GameObject.allObjects.get(first).isSelected() && ObjectPickingMethods.detectInitialPolygonMouseCollision(e,GameObject.allObjects.get(first).getCollisionContext().getBoundingPolygons()[second],GameObject.allObjects.get(first))) {
		                		if(objectGotSelected && slectedObjectIndex != first) {
		                			GameObject.allObjects.get(slectedObjectIndex).unSelectObject();
			                		objectSettings.removeUI();	                		
		                		}		     
	                			GameObject.allObjects.get(first).selectObject();
		                		objectSettings.addUI(GameObject.allObjects.get(first));
		                		break;
		                	}
		                
		                float distance = ObjectPickingMethods.calculateCircleDistance(e,GameObject.allObjects.get(first));
		     
		                for (int third = 0; third < GameObject.allObjects.get(first).getCollisionContext().getBoundingCircles().length; third++)  
		                	if(!GameObject.allObjects.get(first).isSelected() && ObjectPickingMethods.detectCircleMouseCollision(GameObject.allObjects.get(first).getCollisionContext().getBoundingCircles()[third],distance)) {
		                		if(objectGotSelected && slectedObjectIndex != first) {
		                			GameObject.allObjects.get(slectedObjectIndex).unSelectObject();
			                		objectSettings.removeUI();		                		
		                		}
		                		GameObject.allObjects.get(first).selectObject();
		                		objectSettings.addUI(GameObject.allObjects.get(first));
		                		break;
		                	}
		                
	            		}
	            	}  
    		}
 
        });


        //Highlight Object On Mouse over
        Util.canvasWrapper.setOnMouseMoved(e-> {
             

            if(!SimulationControler.isPlaying()) {         
	            for (int first = 0; first < GameObject.allObjects.size(); first++) {
	            	
	               	if(GameObject.allObjects.get(first).isEditable()) {
	               		               	            	
		            	float distance = ObjectPickingMethods.calculateCircleDistance(e,GameObject.allObjects.get(first));
		            	 
		                for (int second = 0; second < GameObject.allObjects.get(first).getCollisionContext().getBoundingPolygons().length; second++)
		                	if(ObjectPickingMethods.detectInitialPolygonMouseCollision(e,GameObject.allObjects.get(first).getCollisionContext().getBoundingPolygons()[second],GameObject.allObjects.get(first))) {   
		                		GameObject.allObjects.get(first).highlight(true);
		                		Util.mainScene.setCursor(Cursor.HAND);
		                		first=GameObject.allObjects.size()-1;
		                		break;
		                	}else {
		                		GameObject.allObjects.get(first).highlight(false);
		                		Util.mainScene.setCursor(Cursor.DEFAULT);
		                	}
		     
		                for (int third = 0; third < GameObject.allObjects.get(first).getCollisionContext().getBoundingCircles().length; third++)  
		                	if(ObjectPickingMethods.detectCircleMouseCollision(GameObject.allObjects.get(first).getCollisionContext().getBoundingCircles()[third],distance))  {
		                		GameObject.allObjects.get(first).highlight(true);
		                		Util.mainScene.setCursor(Cursor.HAND);
		                		first=GameObject.allObjects.size()-1;
		                		break;
		                	} else { 
		                    	GameObject.allObjects.get(first).highlight(false);
		                		Util.mainScene.setCursor(Cursor.DEFAULT);
	               			}
	               	}
	            }
        	}
            
            //Change Move Rotate Scale State of Object On Mouse clicked
            for (int objectCounter = 0; objectCounter < GameObject.allObjects.size(); objectCounter++) {
                if(GameObject.allObjects.get(objectCounter).isSelected() && ObjectPickingMethods.chooseCircleLine(e, GameObject.allObjects.get(objectCounter).getObjectTransformer().getCircleUI())){
                    GameObject.allObjects.get(objectCounter).setScalable(false);
                    GameObject.allObjects.get(objectCounter).setRotatable(true);
                    GameObject.allObjects.get(objectCounter).setMoveable(false);
                    Util.mainScene.setCursor(Cursor.CLOSED_HAND);
                    
                }else if (GameObject.allObjects.get(objectCounter).isSelected() && ObjectPickingMethods.chooseSquareUI(e, GameObject.allObjects.get(objectCounter).getObjectTransformer().getSquareUI())){
                    GameObject.allObjects.get(objectCounter).setScalable(true);
                    GameObject.allObjects.get(objectCounter).setRotatable(false);
                    GameObject.allObjects.get(objectCounter).setMoveable(false);
                    Util.mainScene.setCursor(Cursor.E_RESIZE);
                    
                } else if(GameObject.allObjects.get(objectCounter).isSelected() && ObjectPickingMethods.chooseObject(e, GameObject.allObjects.get(objectCounter).getObjectTransformer().getSquareUI())) {
                    GameObject.allObjects.get(objectCounter).setScalable(false);
                    GameObject.allObjects.get(objectCounter).setRotatable(false);
                    GameObject.allObjects.get(objectCounter).setMoveable(true);
                    Util.mainScene.setCursor(Cursor.MOVE);
                    
                }else {
	            	GameObject.allObjects.get(objectCounter).setScalable(false);
                    GameObject.allObjects.get(objectCounter).setRotatable(false);
                    GameObject.allObjects.get(objectCounter).setMoveable(false);
                    
                    
                }
            }

        });


        //Move Rotate Scale Object On Mouse Dragged
        Util.canvasWrapper.setOnMouseDragged(e-> {
        	if(!SimulationControler.isPlaying()) {
	            for (int objectCounter = 0; objectCounter < GameObject.allObjects.size(); objectCounter++) {
	            	
	                if(GameObject.allObjects.get(objectCounter).isRotatable()){
	                    ObjectTransformationModes.rotateObject(objectCounter, GameObject.allObjects, e);
	                    objectSettings.updateUI(GameObject.allObjects.get(objectCounter));
	                    
	                }else if (GameObject.allObjects.get(objectCounter).isScalable()){
	                    ObjectTransformationModes.scaleObject(objectCounter, GameObject.allObjects, e);
	                    objectSettings.updateUI(GameObject.allObjects.get(objectCounter));
	                    
	                }else if (GameObject.allObjects.get(objectCounter).isMoveable()) {	                	
	                    ObjectTransformationModes.moveObject(objectCounter, GameObject.allObjects, e);
	                    objectSettings.updateUI(GameObject.allObjects.get(objectCounter));
	                }	                
	                
	            }
        	}
        });
    }
    
}