package UI.EditorMenue.Tabs;

import UI.TabElements.*;
import javafx.scene.layout.Pane;

public class AllElementsTab extends EditorTab{

	public AllElementsTab(Pane glass) {
		super(glass,"basketball.png","All");
	}

	@Override
	protected void createContent(Pane glass) {
		 BasketballTabElement basketBall = new BasketballTabElement(glass);
		 HairdryerTabElement hairdryer = new HairdryerTabElement(glass);
		 YogaMatTabElement yogaMatTabElement = new YogaMatTabElement(glass);
		 PortalTabElement portal = new PortalTabElement(glass);
		 PlaneTabElement plane = new PlaneTabElement(glass);
		 BucketTabElement bucket = new BucketTabElement(glass);
		 SpinnerTabElement spinner = new SpinnerTabElement(glass);
		 MagnetTabElement magnetTab = new MagnetTabElement (glass);
		 BeachBallTabElement beachball = new BeachBallTabElement(glass);
		 TennisBallTabElement golfball = new TennisBallTabElement(glass);
		 MovingBoxTabElement movingBox = new MovingBoxTabElement(glass);
		 
		 content.getChildren().addAll(basketBall,beachball,golfball,plane,movingBox,bucket, hairdryer,spinner,magnetTab,portal, yogaMatTabElement);
	}
	
	
}
