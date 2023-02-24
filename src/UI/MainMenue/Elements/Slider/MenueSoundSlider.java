package UI.MainMenue.Elements.Slider;

import UI.Sounds;
import UI.Util;
import UI.MainMenue.Elements.MenueSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MenueSoundSlider extends MenueSlider{
	
	public MenueSoundSlider(){
		super("Sound Volume",(int)(Util.soundVolume*100));

		slider.setMin(0);
		slider.setMax(100);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	         	Util.soundVolume = (float)slider.getValue()/100;
	         	sliderValue.setText(Integer.toString((int)slider.getValue()));
	         	Sounds.updateVolume();
	         }
		});
				
	}
	
}
