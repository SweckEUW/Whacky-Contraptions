package UI.MainMenue.Elements.Slider;

import UI.Util;
import UI.MainMenue.Elements.MenueSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MenueContrastSlider extends MenueSlider{
	
	public MenueContrastSlider(){
		super("Contrast",(int)(Util.colorAdjust.getContrast()*100+50));

		slider.setMin(0);
		slider.setMax(100);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	        	Util.colorAdjust.setContrast(newValue.doubleValue()/100-0.5);
	         	sliderValue.setText(Integer.toString((int)slider.getValue()));
	         }
		});
				
	}
	
}
