package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.Ball.BeachBall;
import javafx.scene.layout.Pane;

public class BeachBallTabElement extends TabElement{

	public BeachBallTabElement(Pane glass) {
		super(glass,"BeachBall", "BeachBall.png");
	}
	
	public BeachBallTabElement(Pane glass,int ammount) {
		super(glass,"BeachBall", "BeachBall.png",ammount);
	}

	@Override
	public GameObject createObject(float x, float y) {
		return new BeachBall(x,y);
	}

}
