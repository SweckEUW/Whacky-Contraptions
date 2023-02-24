package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.Ball.TennisBall;
import javafx.scene.layout.Pane;

public class TennisBallTabElement extends TabElement{

	public TennisBallTabElement(Pane glass) {
		super(glass,"TennisBall", "TennisBall.png");
	}
	
	public TennisBallTabElement(Pane glass,int ammount) {
		super(glass,"TennisBall", "TennisBall.png",ammount);
	}

	@Override
	public GameObject createObject(float x, float y) {
		return new TennisBall(x,y);
	}

}
