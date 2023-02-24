package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.Box.MovingBox;
import javafx.scene.layout.Pane;

public class MovingBoxTabElement extends TabElement{

	public MovingBoxTabElement(Pane glass,int ammount) {
		super(glass,"MovingBox", "MovingBox.png",ammount);
	}
	
	public MovingBoxTabElement(Pane glass) {
		super(glass,"MovingBox", "MovingBox.png");
	}

	@Override
	public GameObject createObject(float x, float y) {
		return new MovingBox(x, y);
	}

}
