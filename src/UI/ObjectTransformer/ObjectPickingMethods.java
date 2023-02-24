package UI.ObjectTransformer;

import Simulation.Collisions.Boundings.BoundingCircle;
import Simulation.Collisions.Boundings.BoundingPolygon;
import Simulation.Objects.GameObject;
import Simulation.RenderEngine.Core.Math.Vector2f;
import javafx.scene.input.MouseEvent;

public class ObjectPickingMethods {

    public static float calculateCircleDistance(MouseEvent e, GameObject object) {
        float x = UI.Util.convertMouseX(e.getX());
        float y = UI.Util.convertMouseY(e.getY());

        float distX = x - object.getX();
        float distY = y - object.getY();

        float distance = (float) Math.sqrt((distX*distX) + (distY*distY));
        return distance;
    }

    public static boolean detectCircleMouseCollision(BoundingCircle circle, float distance) {
        if (distance <= circle.getRadius())        
            return true;   
        else 
            return false;
    }

    public static boolean detectPolygonMouseCollision(MouseEvent e, BoundingPolygon polygon) {
        float px = UI.Util.convertMouseX(e.getX());
        float py = UI.Util.convertMouseY(e.getY());

        boolean collision = false;
        int next = 0;
     
        for (int i = 0; i < polygon.getPoints().length; i++) {
            next = i + 1;

            if (next == polygon.getPoints().length) 
                next = 0;
            
            Vector2f vc = polygon.getPoints()[i];
            Vector2f vn =  polygon.getPoints()[next];

            if (((vc.y > py) != (vn.y > py)) && (px < (vn.x - vc.x) * (py - vc.y) / (vn.y - vc.y) + vc.x)) {
                collision = !collision;
            }
        }

        return collision;
    }

    public static boolean detectInitialPolygonMouseCollision(MouseEvent e, BoundingPolygon polygon,GameObject object) {
        float px = UI.Util.convertMouseX(e.getX());
        float py = UI.Util.convertMouseY(e.getY());

        boolean collision = false;
        int next = 0;
        float scale = 1.1f;
     
        for (int i = 0; i < polygon.getPoints().length; i++) {
            next = i + 1;

            if (next == polygon.getPoints().length) 
                next = 0;
            
            Vector2f original1 = polygon.getPoints()[i];
            Vector2f original2 = polygon.getPoints()[next];
            Vector2f vc = new Vector2f((original1.x-object.getX())*scale+object.getX(), (original1.y-object.getY())*scale+object.getY());
            Vector2f vn = new Vector2f((original2.x-object.getX())*scale+object.getX(), (original2.y-object.getY())*scale+object.getY());

            if (((vc.y > py) != (vn.y > py)) && (px < (vn.x - vc.x) * (py - vc.y) / (vn.y - vc.y) + vc.x)) 
                collision = !collision;
            
        }

        return collision;
    }

    public static boolean chooseCircleLine(MouseEvent e, RotationCircleUI object) {
        float x = UI.Util.convertMouseX(e.getX());
        float y = UI.Util.convertMouseY(e.getY());

        float distX = x - object.getCircleModel().getX();
        float distY = y - object.getCircleModel().getY();

        float distance = (float) Math.sqrt((distX*distX) + (distY*distY));

        if (distance >= (object.getRadius()*0.9f) && distance <= (object.getRadius()) * 1.1) 
        	return true;
        
        return false;
    }

    
    public static boolean chooseSquareUI(MouseEvent e ,ScaleSquareUI squareUI) {
        float px = UI.Util.convertMouseX(e.getX());
        float py = UI.Util.convertMouseY(e.getY());
      
        float rx = squareUI.getVerticesBigger().get(1).x;
        float rx2 = squareUI.getVerticesBigger().get(0).x;
        float ry = squareUI.getVerticesBigger().get(1).y;
        float ry2 = squareUI.getVerticesBigger().get(2).y;

        if (px > rx &&  px < rx2 && py < ry && py > ry2) { 

        	 rx = squareUI.getVerticesSmaller().get(1).x;
             rx2 = squareUI.getVerticesSmaller().get(0).x;
             ry = squareUI.getVerticesSmaller().get(1).y;
             ry2 = squareUI.getVerticesSmaller().get(2).y;
        	
             if (!(px >= rx &&  px <= rx2 && py <= ry && py >= ry2)) 
            	 return true;
   
        }
                
        return false;
    }
    
    public static boolean chooseObject(MouseEvent e ,ScaleSquareUI squareUI) {
    	
        float px = UI.Util.convertMouseX(e.getX());
        float py = UI.Util.convertMouseY(e.getY());
        
        float rx = squareUI.getVerticesSmaller().get(1).x;
        float rx2 = squareUI.getVerticesSmaller().get(0).x;
        float ry = squareUI.getVerticesSmaller().get(1).y;
        float ry2 = squareUI.getVerticesSmaller().get(2).y;

        if (px > rx &&         
            px < rx2 &&    	
            py < ry &&         
            py > ry2) 		
        	   return true;
        
        return false;
    }
}
