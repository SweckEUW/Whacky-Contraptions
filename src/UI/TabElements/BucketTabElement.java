package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Bucket;
import javafx.scene.layout.Pane;

public class BucketTabElement extends TabElement{

	public BucketTabElement(Pane glass) {
		super(glass,"Bucket", "Bucket.png");
	}
	
	public BucketTabElement(Pane glass,int ammount) {
		super(glass,"Bucket", "Bucket.png",ammount);
	}

	@Override
	public GameObject createObject(float x, float y) {
		return new Bucket(x,y);
	}

}
