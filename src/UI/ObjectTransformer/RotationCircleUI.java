package UI.ObjectTransformer;

import Simulation.RenderEngine.Core.Models.LineModel;
import Simulation.RenderEngine.Core.Shaders.Core.BasicShader;
import Simulation.RenderEngine.Primitives.CircleLine;

//Bounding Klassen haben bereits Shader, einfach übernehmen

public class RotationCircleUI {
	
    private LineModel circleModel;
    private static BasicShader shader = new BasicShader("Line");

    private float originalRadius;
    private float radius;

    public RotationCircleUI (float radius, int resolution, float r, float g, float b, float x, float y) {
        this.radius = radius + 10;
        this.originalRadius = this.radius;
        circleModel = new LineModel(new CircleLine(this.radius, resolution, 0), r, g, b, x, y);
    }

    public static BasicShader getShader() {
        return shader;
    }

    public static void setShader(BasicShader shader) {
        RotationCircleUI.shader = shader;
    }

    public LineModel getCircleModel() {
        return circleModel;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setScale(float scale) {
        circleModel.setScale(scale);
        radius = originalRadius*scale;
    }
    
    public void setX(float x) {
    	circleModel.setX(x);   	
    }
    
    public void setY(float y) {
    	circleModel.setY(y);   	
    }
    
}
