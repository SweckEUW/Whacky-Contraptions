package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.Ball.BasketBall;
import javafx.scene.layout.Pane;

public class BasketballTabElement extends TabElement{

	public BasketballTabElement(Pane glass) {
		super(glass,"BasketBall", "BasketBall.png");
	}
	
	public BasketballTabElement(Pane glass,int ammount) {
		super(glass,"BasketBall", "BasketBall.png",ammount);
	}

	@Override
	public GameObject createObject(float x, float y) {
		return new BasketBall(x,y);
	}

}
