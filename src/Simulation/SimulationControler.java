package Simulation;

import java.util.Timer;
import java.util.TimerTask;

import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.MoveableObject;
import UI.Util;
import javafx.application.Platform;
import javafx.scene.Cursor;

public class SimulationControler {

	private static int updateTime = 5;
	private static Timer simulationTimer;
	private static boolean isPlaying;

	public static void simulation() {
		  for (GameObject object : GameObject.allObjects) {
			  object.update();	
	    	  
	    	  Platform.runLater(new Runnable() {				
				@Override
				public void run() {
					if(object instanceof MoveableObject) 
			    		 ((MoveableObject) object).updateElement();
				}
			});
	    	  	  
		  }
	}
	
	public static void setUpdateTime(int updateTime) {
		SimulationControler.updateTime=updateTime;
	}

	public static void pause() {
		if(isPlaying) {			
			isPlaying=false;
			simulationTimer.cancel();
		}	
	}
	
	public static void play() {
		if(!isPlaying) {
			Util.mainScene.setCursor(Cursor.DEFAULT);
			isPlaying=true;
			simulationTimer = new Timer();
			simulationTimer.scheduleAtFixedRate(new TimerTask() {
			    public void run() {
				      simulation();	      				    				      
				    }
				},0,5);		
		}
		
		for (GameObject object : GameObject.allObjects) 
			object.unSelectObject();	
	}
	
	public static void restart() {
		 for (GameObject object : GameObject.allObjects) 
	    	  object.reset();
	}
	
	public static int getUpdateTime() {
		return updateTime;
	}
	
	public static float getUpdateTimeInSeconds() {
		return (float)updateTime/1000;
	}
	
	public static boolean isPlaying() {
		return isPlaying;
	}

}
