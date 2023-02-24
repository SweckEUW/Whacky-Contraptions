package UI.MainMenue.Elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class MenueSlider extends HBox{
	
	protected Slider slider;
	protected Label sliderValue;
	
	public MenueSlider(String name,int startValue){		
		HBox container = new HBox(10);
		
		sliderValue = new Label(Integer.toString(startValue));
		
		slider = new Slider();
		slider.setMin(10);
		slider.setMax(120);
		slider.setValue(startValue);
		
		container.getChildren().addAll(slider,sliderValue);
		
		Label btnText = new Label(name);
		btnText.setMinWidth(200);
		
		this.getChildren().addAll(btnText,container);		
		this.setAlignment(Pos.CENTER);
		
		this.getStyleClass().add("MenueElement");
		this.getStylesheets().add("file:res/css/MenueElements.css");			
	}
	
}
