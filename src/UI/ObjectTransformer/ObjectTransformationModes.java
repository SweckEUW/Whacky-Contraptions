package UI.ObjectTransformer;

import java.util.ArrayList;

import Simulation.Objects.GameObject;
import Simulation.RenderEngine.Core.Config;
import javafx.scene.input.MouseEvent;

public class ObjectTransformationModes {
	
    public static void rotateObject (int objectCounter, ArrayList<GameObject> allobjects, MouseEvent e) {
        float objectX = allobjects.get(objectCounter).getX() + Config.CANVAS_WIDTH/2;
        float objectY = Config.CANVAS_HEIGHT/2 - allobjects.get(objectCounter).getY();

        float mouseX = (float)e.getX();
        float mouseY = (float)e.getY();

        float rotation = -(float)Math.atan2(objectY-mouseY , objectX-mouseX) * 180/(float)Math.PI;
        allobjects.get(objectCounter).setRotation(rotation);
        allobjects.get(objectCounter).setOriginalrotation(rotation);
    }

    public static void scaleObject(int objectCounter, ArrayList<GameObject> allobjects, MouseEvent e) {
        float objectX = allobjects.get(objectCounter).getX() + Config.CANVAS_WIDTH/2; //Remappen der Objektkoordinaten
        float objectY = Config.CANVAS_HEIGHT/2 - allobjects.get(objectCounter).getY(); //Remappen der Objektkoordinaten

        float mouseX = (float)e.getX(); // die Mausposition
        float mouseY = (float)e.getY(); // die Mausposition

        float scale = (float)Math.sqrt(Math.pow((objectX-mouseX),2)+Math.pow((objectY-mouseY),2)) /100; //Skalierungsvektor;
        allobjects.get(objectCounter).setScale(scale);
    }

    public static void moveObject (int objectCounter, ArrayList<GameObject> allobjects, MouseEvent e) {
    	float x = UI.Util.convertMouseX(e.getX());
    	float y = UI.Util.convertMouseY(e.getY());
        allobjects.get(objectCounter).setX(x);
        allobjects.get(objectCounter).setY(y);
        allobjects.get(objectCounter).setOriginalX(x);
        allobjects.get(objectCounter).setOriginalY(y);
    }
    
}
