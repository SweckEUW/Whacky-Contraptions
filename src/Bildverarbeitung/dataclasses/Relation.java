package Bildverarbeitung.dataclasses;

public class Relation {
    private PlanetData PLANET_A, PLANET_B;
    private double DISTANCE;

    public Relation (PlanetData PLANET_A, PlanetData PLANET_B, double DISTANCE) {
        this.PLANET_A = PLANET_A;
        this.PLANET_B = PLANET_B;
        this.DISTANCE = DISTANCE;
    }

    public PlanetData getPlanetA () {
        return PLANET_A;
    }

    public PlanetData getPlanetB () {
        return PLANET_B;
    }

    public boolean contains (PlanetData planet) {
        if (PLANET_A.equals(planet)) { return true;}
        else if (PLANET_B.equals(planet)) { return true;}
        else return false;
    }

    public PlanetData returnPartner (PlanetData planet) {
        if (PLANET_A.equals(planet)) { return PLANET_B;}
        else if (PLANET_B.equals(planet)) { return PLANET_A;}
        else return null;
    }

    public double getDistance () {
        return DISTANCE;
    }

    public void setDistance (double NEW_DISTANCE) {
        this.DISTANCE = NEW_DISTANCE;
    }
}
