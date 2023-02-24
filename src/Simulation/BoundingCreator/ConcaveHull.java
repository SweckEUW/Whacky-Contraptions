package Simulation.BoundingCreator;

import java.util.ArrayList;

import Simulation.Util;
import Simulation.RenderEngine.Core.Config;
import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Models.Model;

public class ConcaveHull {
	
	//private static int k = 3;
	//private static float buffer = 0.5f;
	
	public static Vector2f[] calculate(Model model,int k , float buffer){
		int step = 0;
		//liste aller punkte in einer Ebene
		ArrayList<Vector2f> points = getVector2fArray(model,buffer);
				
		
		//alle doppelten Punkte entfernen
		removeDuplicates(points);
		
		//hülle initialisieren
		ArrayList<Vector2f> hull = new ArrayList<Vector2f>();
		
		//punkt mit niegristem y wert finden
		Vector2f firstPoint = findPointWithLowestY(points);

		//punkt aus liste löschen und zur hülle hinzufügen
		
		points.remove(firstPoint);
		
		Vector2f currentPoint = firstPoint;
		Vector2f previousPoint = firstPoint;
		float previousAngle = 360;
		
		//while aktuellerpunkt != erster Punkt
		do {					
			
			hull.add(currentPoint);
			
			//Abbruchbedingung wenn nächster punkt der erste wäre
			if(step == 2)
				points.add(firstPoint);
					
			if(step!=0) {
				previousPoint = hull.get(hull.size()-2);
				previousAngle = calcAngle(currentPoint,previousPoint);
			}
			Vector2f leftMostPoint =  findLeftMostPoint(points,currentPoint,k,previousPoint,previousAngle,hull);
		
		
			//punkt aus liste löschen und zur hülle hinzufügen
			points.remove(leftMostPoint);
			currentPoint = leftMostPoint;
	
			step++;
			
			
		} while (points.size() != 0 && (currentPoint.x != firstPoint.x || currentPoint.y != firstPoint.y));
		
		Vector2f[] hullArray = new Vector2f[hull.size()];
		for (int i = 0; i < hullArray.length; i++) 
			hullArray[hullArray.length - i -1] = hull.get(i); 
				
		return hullArray;
	}
	
	
	private static Vector2f findLeftMostPoint(ArrayList<Vector2f> points, Vector2f currentPoint, int k, Vector2f previousPoint, float previousAngle,ArrayList<Vector2f> hull) {
		//suche k nächsten punkte
		Vector2f[]  kNearestPoints = getKnearestPoints(points,currentPoint,k);	
				
		//von k nächsten punkten punkt mit der höchsten rechtsdrehung finden
		
		Vector2f leftMostPoint = getLeftMostPoint(kNearestPoints,currentPoint,previousPoint,previousAngle,hull);
		
		if(leftMostPoint.x == -9999 && leftMostPoint.y == -9999)
			return findLeftMostPoint(points,currentPoint,k+1,previousPoint,previousAngle,hull);
		
		return leftMostPoint;
	}


	private static Vector2f getLeftMostPoint(Vector2f[] kNearestPoints,Vector2f currentPoint,Vector2f previousPoint,float previousAngle,ArrayList<Vector2f> hull) {
		Vector2f leftMostPoint = new Vector2f(-9999, -9999);

		float angle;
		float greatestAngle = -400;
		
		for (int i = 0; i < kNearestPoints.length; i++) {
			angle = previousAngle - calcAngle(currentPoint,kNearestPoints[i]);	
			
			if(angle < 0) 
				angle+=360;				
			
			if(angle > greatestAngle) {
			
				if(!checkForIntersections(hull,currentPoint, kNearestPoints[i])) {
					greatestAngle = angle;
					leftMostPoint = kNearestPoints[i];
				}
				
			}		
		}
		
		return leftMostPoint;
	}
	

	private static boolean checkForIntersections(ArrayList<Vector2f> hull,Vector2f currentPoint, Vector2f nextPoint) {
		if (hull.size() < 3) 
			return false;

		for (int i = 0; i < hull.size()-2; i++) 	
			if(checkLineLineIntersection(hull.get(i),hull.get(i+1),currentPoint,nextPoint)) 
				return true;
		return false;
	}

	private static boolean checkLineLineIntersection(Vector2f a,Vector2f b,Vector2f d,Vector2f c) {
		
		// calculate the distance to intersection point
		float uA = ((d.x-c.x)*(a.y-c.y) - (d.y-c.y)*(a.x-c.x)) / ((d.y-c.y)*(b.x-a.x) - (d.x-c.x)*(b.y-a.y));
		float uB = ((b.x-a.x)*(a.y-c.y) - (b.y-a.y)*(a.x-c.x)) / ((d.y-c.y)*(b.x-a.x) - (d.x-c.x)*(b.y-a.y));

		// if uA and uB are between 0-1, lines are colliding
		if (uA > 0 && uA < 1 && uB > 0 && uB < 1) 
			return true;
		
		  
		return false;
	}
	

	private static float calcAngle(Vector2f p1 , Vector2f p2) {
		float angle = (float)Math.atan2(p2.y-p1.y , p2.x-p1.x) * 180/(float)Math.PI;
		if (angle < 0)
			angle+=360;
		return angle;
	}


	private static Vector2f[] getKnearestPoints(ArrayList<Vector2f> points , Vector2f currentPoint,int k) {
		Vector2f[] kNearestPoints = k > points.size() ? new Vector2f[points.size()]:new Vector2f[k];
		
		Vector2f[] unsortedPoints = new Vector2f[points.size()];
		for (int i = 0; i < unsortedPoints.length; i++) 
			unsortedPoints[i] = points.get(i);
		
		sort(unsortedPoints, 0, unsortedPoints.length-1, currentPoint);
			
		for (int i = 0; i < kNearestPoints.length; i++) 
			kNearestPoints[i] = unsortedPoints[i];
		
		
		return kNearestPoints;
	}


	private static ArrayList<Vector2f> getVector2fArray(Model model,float buffer) {
		ArrayList<Vector2f> points = new ArrayList<Vector2f>();
		for (int i = 0; i < model.getMesh().getVertices().length; i+=3) 
			if (model.getMesh().getVertices()[i+2] <= buffer &&  model.getMesh().getVertices()[i+2] >= -buffer) 
				points.add(new Vector2f(model.getMesh().getVertices()[i] * Config.CANVAS_WIDTH,model.getMesh().getVertices()[i+1] * Config.CANVAS_HEIGHT));
				
		return points;
	}
	
	private static void removeDuplicates(ArrayList<Vector2f> points) {
		for (int i = 0; i < points.size(); i++) 
			for (int j = 0; j < points.size(); j++) 
				if (points.get(i).x == points.get(j).x && points.get(i).y == points.get(j).y && j!=i) {
					points.remove(j);	
					j--;
				}
		
	}
	
	private static Vector2f findPointWithLowestY(ArrayList<Vector2f> points) {
		float lowestY = 100;
		
		Vector2f point = null;
		
		for (int i = 0; i < points.size(); i++) 
			if (points.get(i).y < lowestY) {
				lowestY = points.get(i).y;
				point = points.get(i);
			}
		
		return point;
	}
	
	
	private static void merge(Vector2f[] arr, int l, int m, int r,Vector2f currentPoint) { 
		// Find sizes of two subarrays to be merged 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        /* Create temp arrays */
        Vector2f[] L = new Vector2f[n1]; 
        Vector2f[] R = new Vector2f[n2]; 
  
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i) 
            L[i] = arr[l + i]; 
        for (int j=0; j<n2; ++j) 
            R[j] = arr[m + 1+ j]; 
  
  
        /* Merge the temp arrays */
  
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0; 
  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < n1 && j < n2) 
        { 
        	if (Util.getDistance(L[i],currentPoint) <= Util.getDistance(R[j],currentPoint)) 
            { 
                arr[k] = L[i]; 
                i++; 
            } 
            else
            { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
  
        /* Copy remaining elements of L[] if any */
        while (i < n1) 
        { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        } 
  
        /* Copy remaining elements of R[] if any */
        while (j < n2) 
        { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
	}
	
	private static void sort(Vector2f[] arr, int l, int r,Vector2f currentPoint) { 
		  if (l < r) 
	        { 
	            // Find the middle point 
	            int m = (l+r)/2; 
	  
	            // Sort first and second halves 
	            sort(arr, l, m,currentPoint); 
	            sort(arr , m+1, r,currentPoint); 
	  
	            // Merge the sorted halves 
	            merge(arr, l, m, r,currentPoint); 
	        } 
	} 
	    
}
