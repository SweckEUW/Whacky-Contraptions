package Simulation.Objects.StaticObjects.StaticExternalObjects;

import Simulation.RenderEngine.Core.Math.Vector3f;
import Simulation.RenderEngine.Core.Shaders.Core.Material;

public class YogaMat extends StaticExternalObject{

    private static Material material = new Material(new Vector3f(0.2f), new Vector3f(0.5f), new Vector3f(0.5f), 1f);

    public YogaMat(float x, float y) {
        super("YogaMat","YogaMat","YogaMatTexture.jpg", material, x, y);
        setScale(0.4f);
        this.setCoefficientOfRestitution(0.1f);
    }

    @Override
    public void update() {

    }

    @Override
    public void onCollision() {

    }
}
