package Simulation.Objects.StaticObjects.StaticExternalObjects;

import Simulation.Util;
import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.MoveableObject;
import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;

public class Magnet extends StaticExternalObject{

    private static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(1f), 4f);
    private static Vector2f negativeSrc, positiveSrc;
    private float offset = 65;
    private float charge = 550; //in kilo-Ampere/Meter

    public Magnet(float x, float y) {
        super("stabmagnet_tri","stabmagnet_tri","magnetTexture.png", material, x, y);
        //reference points for magnetic sources (both ends)
        negativeSrc = new Vector2f (x, y);
        positiveSrc = new Vector2f (x, y);

        setScale(1f);
    }

    @Override
    public void onCollision () {
    }

    @Override
    public void setScale (float scale) {
        super.setScale(scale);
        //reset position, set new offset
        negativeSrc.setX(this.getX()-(offset*scale));
        positiveSrc.setX(this.getX()+(offset*scale));
        negativeSrc.setY(this.getY());
        positiveSrc.setY(this.getY());
        //rotate correctly
        negativeSrc = Util.rotateArroundPoint(negativeSrc.getX(), negativeSrc.getY(), this.getRotation(), this.getX(), this.getY());
        positiveSrc = Util.rotateArroundPoint(positiveSrc.getX(), positiveSrc.getY(), this.getRotation(), this.getX(), this.getY());
    }

    @Override
    public void setRotation (float rotation) {
        float alpha = rotation-this.getRotation();

        negativeSrc = Util.rotateArroundPoint(negativeSrc.getX(), negativeSrc.getY(), alpha, this.getX(), this.getY());
        positiveSrc = Util.rotateArroundPoint(positiveSrc.getX(), positiveSrc.getY(), alpha, this.getX(), this.getY());

        super.setRotation(rotation);
    }

    @Override
    public void setX (float x) {
        float deltaX = x-this.getX();
        negativeSrc.setX(negativeSrc.getX()+deltaX);
        positiveSrc.setX(positiveSrc.getX()+deltaX);

        super.setX(x);
    }

    @Override
    public void setY (float y) {
        float deltaY = y-this.getY();
        negativeSrc.setY(negativeSrc.getY()+deltaY);
        positiveSrc.setY(positiveSrc.getY()+deltaY);

        super.setY(y);
    }

    @Override
    public void update() {for (GameObject object: allObjects) {
            if (object instanceof MoveableObject) {
                Vector2f repel = new Vector2f(negativeSrc.getX()-object.getX(), negativeSrc.getY()-object.getY());
                Vector2f attract = new Vector2f(object.getX()-positiveSrc.getX(), object.getY()-positiveSrc.getY());

                double attractDistance = Util.calcVectorSize(attract);
                double repelDistance = Util.calcVectorSize(repel);

                if (attractDistance < repelDistance) {
                    Vector2f attractNorm = Util.normVector(attract);
                    float accX = attractNorm.getX() * forceFunction(attractDistance);
                    float accY = attractNorm.getY() * forceFunction(attractDistance);
                    ((MoveableObject) object).applyForce(accX, accY);
                }

                else {
                    Vector2f repelNorm = Util.normVector(repel);
                    float accX = repelNorm.getX() * forceFunction(repelDistance);
                    float accY = repelNorm.getY() * forceFunction(repelDistance);
                    ((MoveableObject) object).applyForce(accX, accY);
                }
                }
            }
        }

    private float forceFunction (double r) {
        float perm = 1f; //permeability: depending on material, 1 full magnetism -> 0 no magnetism
        float e_Q = 1f; //charge in ampere/meter
        float f =  (float)((perm*charge*1000f*e_Q)/(4*Math.PI*(Math.pow(r,2))));

        return f;
    }

    public float getCharge () {
        return charge;
    }

    public void setCharge (float charge) {
        this.charge = charge;
    }


}