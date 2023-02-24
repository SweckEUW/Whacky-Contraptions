package Bildverarbeitung.dataclasses;

import java.util.ArrayList;

public class PlanetData {
    private double x, y, r;
    private boolean isCentre = false;
    private double distanceToLast = 0;
    PlanetData ancestor;
    private ArrayList<PlanetData> children = new ArrayList<> ();
    private int type;

    public PlanetData(double x, double y, double r, int type) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.type = type;
    }
    public PlanetData getAncestor() { return ancestor; }

    public void setAncestor(PlanetData ancestor) {
        this.ancestor = ancestor;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public double getDistance () {return distanceToLast;}

    public boolean isCentre () {return this.isCentre;}

    public ArrayList<PlanetData> getChildren () {return children;}

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setCentre () {
        isCentre = true;
    }

    public void setDistanceToLast (double distance) { this.distanceToLast = distance; }

    public void addChild (PlanetData child) { children.add(child); };

    @Override
    public String toString () {
        return ""+x*y;
    }
	public boolean hasParent() {
		if (ancestor==null) 
			return false;		
		return true;		
	}
	public int getTyp() {
		return type;
	}
	
}
