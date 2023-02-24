package UI.ObjectTransformer;

import java.util.ArrayList;
import java.util.Arrays;

import Simulation.Objects.GameObject;
import Simulation.RenderEngine.Core.Math.Vector2f;

public class ObjectTransformer {
	
    private RotationCircleUI circleUI;
    private ScaleSquareUI squareUI;

    public ObjectTransformer(GameObject gameObject) {
        float radius = calculateRadius(gameObject);
        circleUI = new RotationCircleUI(radius*1.5f, 30, 0.5f,0.5f,0.5f, gameObject.getX(), gameObject.getY());
        squareUI = new ScaleSquareUI(radius*2, 0.5f,0.5f,0.5f,gameObject.getX(), gameObject.getY());
    }

    //Radius des Kreises bestimmen, der um das GameObject gezogen wird
    public float calculateRadius(GameObject object) {
        //ausgegebener Radius kann auch die Länge eines Rechtecks werden
        float distance;

        ArrayList<Vector2f> vertices = new ArrayList<Vector2f>();

        float pointCenterDistance;
        float temporaryLongestDistance = 0;

        //Testen, ob GameObject Sphere oder Polygon ist
        if (object.getCollisionContext().getBoundingPolygons().length == 0) {
            distance = object.getCollisionContext().getBoundingCircle(0).getRadius();
            return distance;
        }

        else if (object.getCollisionContext().getBoundingCircles().length == 0) {
            for (int vertexAddCounter = 0; vertexAddCounter < object.getCollisionContext().getBoundingPolygons().length; vertexAddCounter++) 
                vertices.addAll(Arrays.asList(object.getCollisionContext().getBoundingPolygon(vertexAddCounter).getPoints()));
            
            for (int i = 0; i < vertices.size(); i++) {
                //Abstandsvektor zum Mittelpunkt berechnen
                if (i == 0) 
                    temporaryLongestDistance = Simulation.Util.getDistance(object.getX(), object.getY(), vertices.get(i).x, vertices.get(i).y);   
                

                pointCenterDistance = Simulation.Util.getDistance(object.getX(), object.getY(), vertices.get(i).x, vertices.get(i).y);   

                if (pointCenterDistance > temporaryLongestDistance) 
                    temporaryLongestDistance = pointCenterDistance;
              
            }

            return temporaryLongestDistance;
        }

        return 0;
    }

    public RotationCircleUI getCircleUI() {
        return circleUI;
    }

    public void setCircleUI(RotationCircleUI circleUI) {
        this.circleUI = circleUI;
    }

    public ScaleSquareUI getSquareUI() {
        return squareUI;
    }

    public void setSquareUI(ScaleSquareUI squareUI) {
        this.squareUI = squareUI;
    }

    public void setX(float x) {
        squareUI.setX(x);
        circleUI.setX(x); 
    }

    public void setY(float y) {
        squareUI.setY(y);
        circleUI.setY(y);
    }

    public void setScale(float scale) {
        squareUI.setScale(scale);
        circleUI.setScale(scale);
    }
}
