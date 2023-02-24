package UI.EditorMenue.Tabs;

import UI.TabElements.MovingBoxTabElement;
import UI.TabElements.PlaneTabElement;
import javafx.scene.layout.Pane;

public class BlockTab extends EditorTab{

	public BlockTab(Pane glass) {
		super(glass,"StaticPlane.png","Blocks");
	}

	@Override
	protected void createContent(Pane glass) {
		PlaneTabElement plane = new PlaneTabElement(glass);
		MovingBoxTabElement movingBox = new MovingBoxTabElement(glass);
		
		content.getChildren().addAll(plane,movingBox);
	}
	
	
}
