package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Plane;
import javafx.scene.layout.Pane;

public class PlaneTabElement extends TabElement{

	public PlaneTabElement(Pane glass,int ammount) {
		super(glass,"Plane", "StaticPlane.png",ammount);
	}
	
	public PlaneTabElement(Pane glass) {
		super(glass,"Plane", "StaticPlane.png");
	}

	@Override
	public GameObject createObject(float x, float y) {
		return new Plane(x, y);
	}

}
