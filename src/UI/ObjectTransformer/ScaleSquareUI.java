package UI.ObjectTransformer;


import java.util.ArrayList;

import Simulation.RenderEngine.Core.Config;
import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Models.LineModel;
import Simulation.RenderEngine.Core.Shaders.Core.BasicShader;
import Simulation.RenderEngine.Primitives.RectangleLine;

public class ScaleSquareUI {
	
    private LineModel rectangleLine;
    public static BasicShader shader = new BasicShader("Line");

    private ArrayList<Vector2f> verticesSmaller = new ArrayList<Vector2f>();
    private ArrayList<Vector2f> verticesBigger = new ArrayList<Vector2f>();
    private ArrayList<Vector2f> originalVertices = new ArrayList<Vector2f>();
    
    private float smaller = 0.8f;
    private float bigger = 1.2f;
    private float scale=1;

    public ScaleSquareUI (float size, float r, float g, float b, float x, float y) {
        rectangleLine = new LineModel(new RectangleLine(size, 0), r, g, b, x, y);
        
        verticesSmaller.add(null);
        verticesSmaller.add(null);
        verticesSmaller.add(null);
        verticesSmaller.add(null);       
        verticesBigger.add(null);
        verticesBigger.add(null);
        verticesBigger.add(null);
        verticesBigger.add(null);     
       
        calculateVertices();
   }

    public void calculateVertices() {
    	originalVertices.clear();
    	for (int i = 0; i < rectangleLine.getMesh().getVertices().length; i+=3) 
    		originalVertices.add(new Vector2f(rectangleLine.getMesh().getVertices()[i]*scale,rectangleLine.getMesh().getVertices()[i+1]*scale));
    	   
        verticesSmaller.set(0, new Vector2f((originalVertices.get(0).x * Config.CANVAS_WIDTH)*smaller+rectangleLine.getX(),(originalVertices.get(0).y *Config.CANVAS_HEIGHT)*smaller+rectangleLine.getY()));
        verticesSmaller.set(1, new Vector2f((originalVertices.get(1).x * Config.CANVAS_WIDTH)*smaller+rectangleLine.getX(),(originalVertices.get(1).y *Config.CANVAS_HEIGHT)*smaller+rectangleLine.getY()));
        verticesSmaller.set(2, new Vector2f((originalVertices.get(2).x * Config.CANVAS_WIDTH)*smaller+rectangleLine.getX(),(originalVertices.get(2).y *Config.CANVAS_HEIGHT)*smaller+rectangleLine.getY()));
        verticesSmaller.set(3, new Vector2f((originalVertices.get(3).x * Config.CANVAS_WIDTH)*smaller+rectangleLine.getX(),(originalVertices.get(3).y *Config.CANVAS_HEIGHT)*smaller+rectangleLine.getY()));

        verticesBigger.set(0, new Vector2f((originalVertices.get(0).x * Config.CANVAS_WIDTH)*bigger+rectangleLine.getX(),(originalVertices.get(0).y *Config.CANVAS_HEIGHT)*bigger+rectangleLine.getY()));
        verticesBigger.set(1, new Vector2f((originalVertices.get(1).x * Config.CANVAS_WIDTH)*bigger+rectangleLine.getX(),(originalVertices.get(1).y *Config.CANVAS_HEIGHT)*bigger+rectangleLine.getY()));
        verticesBigger.set(2, new Vector2f((originalVertices.get(2).x * Config.CANVAS_WIDTH)*bigger+rectangleLine.getX(),(originalVertices.get(2).y *Config.CANVAS_HEIGHT)*bigger+rectangleLine.getY()));
        verticesBigger.set(3, new Vector2f((originalVertices.get(3).x * Config.CANVAS_WIDTH)*bigger+rectangleLine.getX(),(originalVertices.get(3).y *Config.CANVAS_HEIGHT)*bigger+rectangleLine.getY()));  
    }

    public LineModel getRectangleLine() {
        return rectangleLine;
    }

    public void setRectangleLine(LineModel rectangleLine) {
        this.rectangleLine = rectangleLine;
    }

    public static BasicShader getShader() {
        return shader;
    }

    public static void setShader(BasicShader shader) {
        ScaleSquareUI.shader = shader;
    }

    public ArrayList<Vector2f> getVerticesSmaller() {
        return verticesSmaller;
    }

    public ArrayList<Vector2f> getVerticesBigger() {
        return verticesBigger;
    }
   
    public void setScale(float scale) {
    	this.scale=scale;
        rectangleLine.setScale(scale);
        calculateVertices();
    }
    
    public void setX(float x) {
    	rectangleLine.setX(x);
    	calculateVertices();
    }
    
    public void setY(float y) {
    	rectangleLine.setY(y);
    	calculateVertices();
    }
    
}
