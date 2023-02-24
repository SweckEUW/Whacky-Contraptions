package UI;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Sounds {

	public static MediaPlayer theme;
	public static MediaPlayer basketball;
	public static MediaPlayer tennisball;
	public static MediaPlayer beachball;
	public static MediaPlayer portal;
	public static MediaPlayer create;
	public static MediaPlayer delete;

	public static void stopSounds() {
		theme.stop();
	}
	
	public static void updateVolume() {
		theme.setVolume(Util.soundVolume*0.7f);
		basketball.setVolume(Util.soundVolume);
		tennisball.setVolume(Util.soundVolume);
		beachball.setVolume(Util.soundVolume);
		portal.setVolume(Util.soundVolume);
		create.setVolume(Util.soundVolume);
		delete.setVolume(Util.soundVolume);
	}

	public static void initSounds() {
		if(Util.devMode)
			Util.soundVolume = 0;
		initMainTheme();
		initTennisBallSound();
		initBasketBallSound();
		initBeachBallSound();
		initPortalSound();
		initDeleteSound();
		initCreateSound();
	}
	
	public static void initCreateSound() {
		Media sound = new Media(new File("res/Sounds/create.wav").toURI().toString());
		create = new MediaPlayer(sound);
		create.setVolume(Util.soundVolume);
		create.setOnEndOfMedia(new Runnable() {		
			@Override
			public void run() {
				create.stop();
			}
		});
	}
	
	public static void playCreateSound() {
		create.play();
	}
	
	public static void initDeleteSound() {
		Media sound = new Media(new File("res/Sounds/delete.wav").toURI().toString());
		delete = new MediaPlayer(sound);
		delete.setVolume(Util.soundVolume);
		delete.setOnEndOfMedia(new Runnable() {		
			@Override
			public void run() {
				delete.stop();
			}
		});
	}
	
	public static void playDeleteSound() {
		delete.play();
	}
	
		
	public static void initMainTheme() {
		Media sound = new Media(new File("res/Sounds/theme.mp3").toURI().toString());
		theme = new MediaPlayer(sound);
		theme.setCycleCount(MediaPlayer.INDEFINITE);
		theme.setVolume(Util.soundVolume*0.7f);
	}
	
	public static void playMainTheme() {
		theme.play();
	}
	
	public static void initBasketBallSound() {
		Media sound = new Media(new File("res/Sounds/basketball.wav").toURI().toString());
		basketball = new MediaPlayer(sound);
		basketball.setVolume(Util.soundVolume);
		basketball.setStopTime(Duration.millis(70));
		basketball.setStartTime(Duration.millis(0));
		basketball.setOnEndOfMedia(new Runnable() {		
			@Override
			public void run() {
				basketball.stop();
			}
		});
	}
	
	public static void playBasketBallSound() {
		basketball.play();
	}
	
	public static void initTennisBallSound() {
		Media sound = new Media(new File("res/Sounds/tennisball.wav").toURI().toString());
		tennisball = new MediaPlayer(sound);
		tennisball.setVolume(Util.soundVolume);
		tennisball.setOnEndOfMedia(new Runnable() {		
			@Override
			public void run() {
				tennisball.stop();
			}
		});
	}
	
	public static void playBeachBallSound() {
		beachball.play();
	}
	
	public static void initBeachBallSound() {
		Media sound = new Media(new File("res/Sounds/beachball.wav").toURI().toString());
		beachball = new MediaPlayer(sound);
		beachball.setVolume(Util.soundVolume);
		beachball.setOnEndOfMedia(new Runnable() {		
			@Override
			public void run() {
				beachball.stop();
			}
		});
	}
	
	public static void playTennisBallSound() {
		tennisball.play();
	}
	
	public static void initPortalSound() {
		Media sound = new Media(new File("res/Sounds/Portal.mp3").toURI().toString());
		portal = new MediaPlayer(sound);
		portal.setVolume(Util.soundVolume);
		portal.setStopTime(Duration.millis(800));
		portal.setStartTime(Duration.millis(600));
		portal.setOnEndOfMedia(new Runnable() {		
			@Override
			public void run() {
				portal.stop();
			}
		});
	}
	
	public static void playPortalSound() {
		portal.play();
	}
	
	public static void playPlaySound() {
		float random2 = (float)Math.random();
		if(random2>0.6f) {
			Media sound = new Media(new File("res/Sounds/Start/00.mp3").toURI().toString());
			
			float random = (float)Math.random()*8f;
			
			if(random>7)
				sound = new Media(new File("res/Sounds/Start/01.mp3").toURI().toString());
			else if(random>6)
				sound = new Media(new File("res/Sounds/Start/07.mp3").toURI().toString());
			else if(random>5)
				sound = new Media(new File("res/Sounds/Start/06.mp3").toURI().toString());
			else if(random>4)
				sound = new Media(new File("res/Sounds/Start/05.mp3").toURI().toString());
			else if(random>3)
				sound = new Media(new File("res/Sounds/Start/04.mp3").toURI().toString());
			else if(random>2)
				sound = new Media(new File("res/Sounds/Start/03.mp3").toURI().toString());
			else if(random>1)
				sound = new Media(new File("res/Sounds/Start/02.mp3").toURI().toString());
			
			AudioClip play = new AudioClip(sound.getSource());
			play.setVolume(Util.soundVolume);
			play.play();
		}
	}
	
	public static void playStopSound() {
		float random2 = (float)Math.random();
		if(random2>0.6f) {
			Media sound = new Media(new File("res/Sounds/Stop/00.mp3").toURI().toString());
			
			float random = (float)Math.random()*5f;
			
			if(random>4)
				sound = new Media(new File("res/Sounds/Stop/05.mp3").toURI().toString());
			else if(random>3)
				sound = new Media(new File("res/Sounds/Stop/04.mp3").toURI().toString());
			else if(random>2)
				sound = new Media(new File("res/Sounds/Stop/03.mp3").toURI().toString());
			else if(random>1)
				sound = new Media(new File("res/Sounds/Stop/02.mp3").toURI().toString());
			
			AudioClip stop = new AudioClip(sound.getSource());
			stop.setVolume(Util.soundVolume);
			stop.play();
		}
	}
	
	public static void playDoneSound() {
		Media sound = new Media(new File("res/Sounds/done/00.mp3").toURI().toString());
		
		float random = (float)Math.random()*11f;
		
		if(random>10)
			sound = new Media(new File("res/Sounds/done/10.mp3").toURI().toString());
		else if(random>9)
			sound = new Media(new File("res/Sounds/done/09.mp3").toURI().toString());
		else if(random>8)
			sound = new Media(new File("res/Sounds/done/08.mp3").toURI().toString());
		else if(random>7)
			sound = new Media(new File("res/Sounds/done/01.mp3").toURI().toString());
		else if(random>6)
			sound = new Media(new File("res/Sounds/done/07.mp3").toURI().toString());
		else if(random>5)
			sound = new Media(new File("res/Sounds/done/06.mp3").toURI().toString());
		else if(random>4)
			sound = new Media(new File("res/Sounds/done/05.mp3").toURI().toString());
		else if(random>3)
			sound = new Media(new File("res/Sounds/done/04.mp3").toURI().toString());
		else if(random>2)
			sound = new Media(new File("res/Sounds/done/03.mp3").toURI().toString());
		else if(random>1)
			sound = new Media(new File("res/Sounds/done/02.mp3").toURI().toString());
		
		
		AudioClip done = new AudioClip(sound.getSource());
		done.setVolume(Util.soundVolume);
		done.play();
	}

}
