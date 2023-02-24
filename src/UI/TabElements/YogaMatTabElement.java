package UI.TabElements;

import Simulation.Objects.GameObject;
import Simulation.Objects.StaticObjects.StaticExternalObjects.YogaMat;
import javafx.scene.layout.Pane;

public class YogaMatTabElement extends TabElement {
    public YogaMatTabElement(Pane glass) {
        super(glass,"Yoga Mat", "YogaMat.png");
    }

    public YogaMatTabElement(Pane glass, int ammount) {
        super(glass,"Yoga Mat", "YogaMat.png",ammount);
    }

    @Override
    public GameObject createObject(float x, float y) {
        return new YogaMat(x, y);
    }
}
