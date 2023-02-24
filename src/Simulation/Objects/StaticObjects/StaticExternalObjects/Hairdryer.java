package Simulation.Objects.StaticObjects.StaticExternalObjects;

import Simulation.Util;
import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Models.LineModel;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.PolygonLine;

public class Hairdryer extends StaticExternalObject {
    private static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(1f), 4f);

    private Vector2f rotationVector = new Vector2f(400, 87.5f);
    private Vector2f newRotationVector;
    private Vector2f pushStartPosition = new Vector2f(this.getX() + rotationVector.x*scale, this.getY() + rotationVector.y*scale);
    private Vector2f [] points;

    private Vector2f [] polygonVertices = new Vector2f [] {new Vector2f (-250,0), new Vector2f(250,100), new Vector2f(250,-100)};
    private PolygonLine coneLine = new PolygonLine(polygonVertices, 0);
    private LineModel cone = new LineModel(coneLine, 0, 0, 0, pushStartPosition.x, pushStartPosition.y);

    private Vector2f polygonAB;
    private Vector2f polygonBC;
    private float polygonABlength;
    private float polygonBClength;

    private float dmax;

    private float amax = 0.5f;
    private float distance;

    public Hairdryer(float x, float y) {
        super("hairdryer", "hairdryer", "HairdryerTexture.jpg", material, x, y);
        setScale(0.4f);
        //particleSystem = new ParticleSystem(new Material(new Vector3f(0.2f,0.2f,0.2f), new Vector3f(0.5f,0.5f,0.5f), new Vector3f(1.f, 1.f, 1.f), 10, 1f),
        //        pushStartPosition.x, pushStartPosition.y);
        //setParticlesActivated(true);
    }

    @Override
    public void update() {

        polygonAB = new Vector2f(points[1].x - points[0].x, points[1].y - points[0].y);
        polygonBC = new Vector2f(points[2].x - points[1].x, points[2].y - points[1].y);

        polygonABlength = (float) Util.calcVectorSize(polygonAB);
        polygonBClength = (float) Util.calcVectorSize(polygonBC)/2;
        float temporValue = (polygonABlength*polygonABlength) - (polygonBClength * polygonBClength);

        dmax = (float) Math.sqrt(temporValue);

        for (GameObject object: allObjects) {
            boolean collision = false;
            Vector2f connecting = new Vector2f (object.getX() - points[0].x, object.getY() - points[0].y);
            distance = (float) Util.calcVectorSize(connecting);

            Vector2f connectingNorm = new Vector2f(connecting.x/distance, connecting.y/distance);

            int next = 0;
            for (int current = 0; current < points.length; current++) {
                next = current + 1;
                if (next == points.length) {
                    next = 0;
                }

                Vector2f vc = points[current];    // c for "current"
                Vector2f vn = points[next];       // n for "next"

                if (((vc.y >= object.getY() && vn.y < object.getY()) || (vc.y < object.getY() && vn.y >= object.getY())) &&
                        (object.getX() < (vn.x-vc.x)*(object.getY()-vc.y) / (vn.y-vc.y)+vc.x)) {
                    collision = !collision;
                }
            }
            if (collision == true) {
               
                float force;

                if (distance == 0) {
                    force = amax;
                }
                else if (distance == dmax) {
                    force = 0;
                }
                else {
                    force = forceFunction((float)distance);
                }

                if (object instanceof MoveableObject) {
                    ((MoveableObject) object).applyForce(connectingNorm.x * force, connectingNorm.y * force);
                }
            }
        }
    }

    @Override
    public void onCollision() {

    }

    private float forceFunction(float distance) {
        return (dmax - distance) * amax;
    }

    private void merken() {
        /*
        pushStartPosition.x = this.getX() + 125 * this.getScale();
        pushStartPosition.y = this.getY() + 50 * this.getScale();

        pushStartPosition = Util.rotateArroundPoint(pushStartPosition.x, pushStartPosition.y, this.getRotation(), this.getX(), this.getY());

        System.out.println("Push");
        System.out.println(pushStartPosition);
        System.out.println("Kern:" + this.getX());
        System.out.println("Kern:" + this.getY());

        Vector2f directional = Util.rotate(1, 0, this.getRotation());
        System.out.println(directional.toString());
        //angle value between 0 and 180
        double thresholdAngle = Util.remap(5);
        int thresholdDistance = 200;


        for (GameObject object : allObjects) {
            if (object instanceof MoveableObject) {
                Vector2f connecting = new Vector2f (object.getX() - pushStartPosition.x, object.getY() - pushStartPosition.y);
                double distance = Util.calcVectorSize(connecting);
                double scalar = Util.calcScalar(directional, connecting);
                double directionalSize = Util.calcVectorSize(directional);
                double multipliedLengths = distance * directionalSize;

                double angle = Math.acos(scalar/multipliedLengths);
                Math.toDegrees(angle);

                if (angle < thresholdAngle && distance <= thresholdDistance) {
                    float force;

                    if (distance == 0) {
                        force = amax;
                    }
                    else if (distance == dmax) {
                        force = 0;
                    }
                    else {
                        force = forceFunction((float)distance);
                        System.out.println("Force");
                        System.out.println(force);
                    }
                    Vector2f initForceDirection = Util.rotate(1, 0, this.getRotation());
                    System.out.println(initForceDirection.getX()+"+"+initForceDirection.getY());
                    ((MoveableObject) object).applyForce(initForceDirection.getX()*force, initForceDirection.getY()*force);
                }
            }
        }
         */
    }

    public LineModel getCone() {
        return cone;
    }


    @Override
    public void setX(float x) {
        super.setX(x);

        newRotationVector = new Vector2f(rotationVector.x * scale, rotationVector.y * scale);

        points = new Vector2f [polygonVertices.length];
        for (int i = 0; i < polygonVertices.length; i++) {
            points[i] = new Vector2f(polygonVertices[i].x, polygonVertices[i].y);
        }

        for (Vector2f p : points) {
            p.x = p.x*scale;
            p.y = p.y*scale;
            p.x += newRotationVector.x;
            p.y += newRotationVector.y;
            p.rotate(rotation);
            p.x += x;
            p.y += y;
        }

        cone = new LineModel(new PolygonLine(points, 0), 0, 1, 0, 0, 0);
    }

    @Override
    public void setY(float y) {
        super.setY(y);

        newRotationVector = new Vector2f(rotationVector.x * scale, rotationVector.y * scale);

        points = new Vector2f [polygonVertices.length];
        for (int i = 0; i < polygonVertices.length; i++) {
            points[i] = new Vector2f(polygonVertices[i].x, polygonVertices[i].y);
        }

        for (Vector2f p : points) {
            p.x = p.x*scale;
            p.y = p.y*scale;
            p.x += newRotationVector.x;
            p.y += newRotationVector.y;
            p.rotate(rotation);
            p.x += x;
            p.y += y;
        }

        cone = new LineModel(new PolygonLine(points, 0), 0, 1, 0, 0, 0);
    }

    @Override
    public void setRotation(float angle) {
        super.setRotation(angle);

        newRotationVector = new Vector2f(rotationVector.x * scale, rotationVector.y * scale);

        points = new Vector2f [polygonVertices.length];
        for (int i = 0; i < polygonVertices.length; i++) {
            points[i] = new Vector2f(polygonVertices[i].x, polygonVertices[i].y);
        }

        for (Vector2f p : points) {
            p.x = p.x*scale;
            p.y = p.y*scale;
            p.x += newRotationVector.x;
            p.y += newRotationVector.y;
            p.rotate(rotation);
            p.x += x;
            p.y += y;
        }

        cone = new LineModel(new PolygonLine(points, 0), 0, 1, 0, 0, 0);
    }

    @Override
    public void setScale(float scale) {
        super.setScale(scale);

        newRotationVector = new Vector2f(rotationVector.x * scale, rotationVector.y * scale);

        points = new Vector2f [polygonVertices.length];
        for (int i = 0; i < polygonVertices.length; i++) {
            points[i] = new Vector2f(polygonVertices[i].x, polygonVertices[i].y);
        }

        for (Vector2f p : points) {
            p.x = p.x*scale;
            p.y = p.y*scale;
            p.x += newRotationVector.x;
            p.y += newRotationVector.y;
            p.rotate(rotation);
            p.x += x;
            p.y += y;
        }

        cone = new LineModel(new PolygonLine(points, 0), 0, 1, 0, 0, 0);
    }

    public void setAmax (float amax) {
        this.amax = amax;
    }

    public float getAmax() {
        return amax;
    }
}
