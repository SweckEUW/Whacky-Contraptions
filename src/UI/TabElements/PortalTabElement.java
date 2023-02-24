package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Portal;
import javafx.scene.layout.Pane;

public class PortalTabElement extends TabElement{

	public PortalTabElement(Pane glass) {
		super(glass,"Portal", "Portal.png");
	}
	
	public PortalTabElement(Pane glass,int ammount) {
		super(glass,"Portal", "Portal.png",ammount);
	}

	@Override
	public GameObject createObject(float x, float y) {
		Portal portal1 = new Portal(x, y);
		Portal portal2 = new Portal(x+100, y+100);
		
		portal1.setPortal(portal2);
		portal2.setPortal(portal1);
		
		return portal1;
	}

}
