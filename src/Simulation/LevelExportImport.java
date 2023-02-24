package Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Simulation.Objects.GameObject;
import Simulation.Objects.MovableObjects.Ball.BasketBall;
import Simulation.Objects.MovableObjects.Ball.BeachBall;
import Simulation.Objects.MovableObjects.Ball.TennisBall;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Bucket;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Hairdryer;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Magnet;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Plane;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Portal;
import Simulation.Objects.StaticObjects.StaticExternalObjects.Spinner;
import Simulation.Objects.StaticObjects.StaticExternalObjects.YogaMat;
import UI.Util;

public class LevelExportImport {

	public static void ExportLevel(String fileName) {
		try {
			FileOutputStream file = new FileOutputStream(System.getProperty("user.dir")+"/res/Levels/"+fileName+".txt");
			
			for (GameObject	object : GameObject.allObjects) {	
				
				String playable = Boolean.toString(object.isPlayable());
				file.write(playable.getBytes());
				file.write("\n".getBytes());
				
				String name = object.getClass().getSimpleName();
				file.write(name.getBytes());
				file.write("\n".getBytes());
				
				String position = object.getX() +"/"+object.getY();
				file.write(position.getBytes());
				file.write("\n".getBytes());
				
				String scale = Float.toString(object.getScale());
				file.write(scale.getBytes());
				file.write("\n".getBytes());
				
				String rotation = Float.toString(object.getRotation());
				file.write(rotation.getBytes());
				file.write("\n".getBytes());
				
				String mass =  Float.toString(object.getMass());
				file.write(mass.getBytes());
				file.write("\n".getBytes());
													
				String direction = "direction" + " TODO ";
				file.write(direction.getBytes());
				file.write("\n".getBytes());
				
				String speed = "speed" + " TODO ";
				file.write(speed.getBytes());
				file.write("\n".getBytes());			
			}
			
			file.close();
			
		} catch (FileNotFoundException e1) {e1.printStackTrace();} catch (IOException e1) {e1.printStackTrace();}
	}
	
	public static void ImportLevel(String file) {
	try	{	
				
			Scanner sc = new Scanner(new File("res/levels/"+file+".txt"));
			int portalCounter = 0;
			GameObject object = null;
			GameObject oldObject = null;
			
			while (sc.hasNext()){
				boolean playable = Boolean.parseBoolean(sc.nextLine());
				if(!playable) {
					String type=sc.nextLine();
					String position = sc.nextLine();
					float x = Float.valueOf(position.split("/")[0]);
					float y = Float.valueOf(position.split("/")[1]);
					
					switch (type) {
					case "BasketBall": {
						object = new BasketBall(x,y);	
						break;
					}
					case "Plane": {
						object = new Plane(x,y);	
						break;
					}
					case "Bucket": {
						object = new Bucket(x,y);	
						break;
					}
					case "Spinner": {
						object = new Spinner(x,y);	
						break;
					}
					case "Magnet": {
						object = new Magnet(x,y);	
						break;
					}
					case "Hairdryer": {
						object = new Hairdryer(x,y);	
						break;
					}
					case "BeachBall": {
						object = new BeachBall(x,y);	
						break;
					}
					case "TennisBall": {
						object = new TennisBall(x,y);	
						break;
					}
					case "YogaMat": {
						object = new YogaMat(x,y);	
						break;
					}
					case "Portal": {	
						portalCounter++;
						object = new Portal(x,y);	
						if(portalCounter>1) {
							((Portal)object).setPortal((Portal)oldObject);
							((Portal)oldObject).setPortal((Portal)object);
							portalCounter=0;
						}
						
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + type);
					}
					
					oldObject = object;
					
					float scale = Float.valueOf(sc.nextLine());
					float rotation = Float.valueOf(sc.nextLine());
					
					String massString = sc.nextLine();
					float mass;
					if(massString == "1.0E9")
						mass = 999999999;
					else 
						mass = Float.valueOf(massString);
				
					sc.nextLine();
					sc.nextLine();
								
					object.setScale(scale);
					object.setRotation(rotation);
					object.setOriginalrotation(rotation);
					object.setMass(mass);
					object.setEditable(false);
					
				}else {
					sc.nextLine();sc.nextLine();sc.nextLine();sc.nextLine();
					sc.nextLine();sc.nextLine();sc.nextLine();
				}
				
			}
			
		}catch (Exception e) {e.printStackTrace();}
	}
	
	
	public static void exportLevelProgress() {
		try {

			ArrayList<Integer> oldValues = new ArrayList<Integer>();
			Scanner sc = new Scanner(new File("res/level.txt"));

			while (sc.hasNext())
				oldValues.add(Integer.valueOf(sc.nextLine()));
			
			FileOutputStream file = new FileOutputStream(System.getProperty("user.dir")+"/res/Level.txt");	
			for (int i = 0; i < oldValues.size(); i++) {
				if(i+1==Util.currentLevel)
					file.write(String.valueOf(1).getBytes());
				else 
					file.write(String.valueOf(oldValues.get(i)).getBytes());
				
				
				file.write("\n".getBytes());
				
			}
			file.close();
			
		} catch (FileNotFoundException e1) {e1.printStackTrace();} catch (IOException e1) {e1.printStackTrace();}
	}
}
