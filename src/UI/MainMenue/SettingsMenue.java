package UI.MainMenue;

import UI.Util;
import UI.MainMenue.Elements.MenueChangeBackground;
import UI.MainMenue.Elements.MenueCheckBox;
import UI.MainMenue.Elements.MenueLeaveButton;
import UI.MainMenue.Elements.MenueWindowSizeDropDown;
import UI.MainMenue.Elements.Slider.MenueBrightnessSlider;
import UI.MainMenue.Elements.Slider.MenueContrastSlider;
import UI.MainMenue.Elements.Slider.MenueFPSSlider;
import UI.MainMenue.Elements.Slider.MenueSoundSlider;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SettingsMenue extends StackPane{

	private String filePath;

	public SettingsMenue(Scene mainScene,Stage primaryStage) {
		ImageView background = new ImageView(new Image("file:res/Images/background.jpg"));
		background.setFitWidth(this.getWidth());
		background.setFitHeight(this.getHeight());
		
		VBox container = new VBox(30);
		container.getStyleClass().add("Vbox");
		
		MenueCheckBox fullscreen = new MenueCheckBox("Fullscreen");
		fullscreen.setChecked(Util.fullscreen);
		fullscreen.setOnMouseClicked(e->{
			if(fullscreen.isChecked()) {
				Util.fullscreen=false;
				fullscreen.setChecked(false);
				primaryStage.setFullScreen(false);
			}else {
				Util.fullscreen=true;
				fullscreen.setChecked(true);
				primaryStage.setFullScreen(true);
			}
		});
		MenueWindowSizeDropDown windowsize = new MenueWindowSizeDropDown(primaryStage);
				
		MenueFPSSlider fps = new MenueFPSSlider();
		
		MenueSoundSlider soundValue = new MenueSoundSlider();
		
		MenueBrightnessSlider brightness = new MenueBrightnessSlider();
		MenueContrastSlider contrast = new  MenueContrastSlider();
		
		MenueChangeBackground changeBackground = new MenueChangeBackground(primaryStage);
		
		MenueLeaveButton leave = new MenueLeaveButton();
		leave.setOnAction(e->{
			new MainMenue(mainScene,primaryStage);
		});
			
		container.getChildren().addAll(fullscreen,windowsize,fps,soundValue,brightness,contrast,changeBackground,leave);
		container.setAlignment(Pos.CENTER);	
		container.getStyleClass().add("Settings");
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(background,container);		
		this.setEffect(Util.colorAdjust);
		this.getStyleClass().add("MainMenue");
		this.getStylesheets().add("file:res/css/MainMenue.css");	
		
		mainScene.setRoot(this);
	}

	public void saveData () throws  IOException{
		FileWriter write = new FileWriter (filePath, false);
		PrintWriter printer = new PrintWriter (write);

		printer.printf("%s" + "%n", "test");
	}
	
}