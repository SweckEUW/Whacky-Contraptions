package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Spinner;
import javafx.scene.layout.Pane;

public class SpinnerTabElement extends TabElement{

	public SpinnerTabElement(Pane glass,int ammount) {
		super(glass,"Spinner", "Spinner.png",ammount);
	}
	
	public SpinnerTabElement(Pane glass) {
		super(glass,"Spinner", "Spinner.png");
	}

	@Override
	public GameObject createObject(float x, float y) {
		return new Spinner(x, y);
	}

}
 