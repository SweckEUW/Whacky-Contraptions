package UI.MainMenue.Elements.Slider;

import Simulation.RenderEngine.Core.Config;
import UI.MainMenue.Elements.MenueSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MenueFPSSlider extends MenueSlider{
	
	public MenueFPSSlider(){
		super("FPS",Config.FRAME_RATE);

		slider.setMin(10);
		slider.setMax(120);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	         	Config.FRAME_RATE = (int)slider.getValue();
	         	sliderValue.setText(Integer.toString(Config.FRAME_RATE));
	         }
		});
				
	}
	
}
