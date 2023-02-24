package Bildverarbeitung.detector;

import java.util.ArrayList;

import Bildverarbeitung.dataclasses.PlanetData;
import Bildverarbeitung.dataclasses.Relation;
import Bildverarbeitung.exceptions.NoRelationsException;
 
public class Sorter {
    Detection detector;

    public Sorter () {
        detector = new Detection ();
    }

    /**
     * Organizes PlanetData for further processing and gives the relations a better structure.
     * @param relations detected relations between planets as 'Relation' objects
     * @param planets all planets detected earlier as 'PlanetData' objects
     * @return ArrayList containing the centre planets
     */
    @SuppressWarnings("unchecked")
	public ArrayList<PlanetData> organize (ArrayList<Relation> relations, ArrayList<PlanetData> planets){
        ArrayList<PlanetData> result = new ArrayList<>();
        ArrayList<PlanetData> unchecked = new ArrayList<>();

        for (PlanetData planet : planets) {
            if (planet.isCentre()) {
                result.add(planet);
                unchecked.add(planet);
            }
        }

        ArrayList<PlanetData> iterator;
        boolean finished = false;

        while (!finished) {
            while (!unchecked.isEmpty()) {
                iterator = (ArrayList<PlanetData>) unchecked.clone();
                unchecked.clear();

                for (PlanetData toCheck : iterator) {
                    for (Relation possibleChild : relations) {
                        if (possibleChild.contains(toCheck)
                                && !toCheck.getChildren().contains(possibleChild.returnPartner(toCheck))
                                && possibleChild.returnPartner(toCheck).getAncestor() == null
                                && !toCheck.equals(possibleChild.returnPartner(toCheck))) {
                            PlanetData child = possibleChild.returnPartner(toCheck);
                            toCheck.addChild(possibleChild.returnPartner(toCheck));
                            child.setDistanceToLast(possibleChild.getDistance());
                            child.setAncestor(toCheck);
                            unchecked.add(child);
                        }
                    }

                    ArrayList<Relation> tempRelations = (ArrayList<Relation>) relations.clone();
                    for (Relation rel : relations) {
                        if (rel.contains(toCheck)) {
                            tempRelations.remove(rel);
                        }
                    }
                    relations = (ArrayList<Relation>) tempRelations.clone();
                }
            }

            //check if planets are left that aren't linked to any other planet and aren't a centre already
            boolean planetsLeft = false;
            for (PlanetData planet : planets) {
                if (planet.getAncestor()==null && planet.getChildren().isEmpty() && !planet.isCentre()) {
                    planetsLeft = true;
                }
            }

            //add new centre to unchecked planets and result ArrayLists
            if (planetsLeft) {
                detector.findCentre(planets);
                for (PlanetData planet : planets) {
                    if (planet.isCentre() && !result.contains(planet)) {
                        result.add(planet);
                        unchecked.add(planet);
                    }
                }
            }
            else {
                finished = true;
            }
        }
        System.out.println("sorted");
        return result;
    }

    /**
     * Checks if any planet is a centre without children
     * @param planets all organized centres
     * @throws NoRelationsException in case a centre planet hasn't any children
     */
    public void checkForSoloPlanets (ArrayList<PlanetData> planets) throws NoRelationsException{
        for (PlanetData centre : planets) {
            if (centre.getChildren().isEmpty()) {
                throw new NoRelationsException("WARNING: NoRelationsException - At least one centre doesn't have any relations");
            }
        }
    }

    /**
     * Normalizes the size of planets and the distance in between on a scale from 0 to 1 for better scaling further on.
     * @param planets alls detected planets as 'PlanetData' objects
     * @return returns the input but with updated values
     */
    @SuppressWarnings("unchecked")
	public ArrayList<PlanetData> normalize (ArrayList<PlanetData> planets) {
        ArrayList<PlanetData> unchecked = (ArrayList<PlanetData>)planets.clone();
        ArrayList<PlanetData> iterator;
        ArrayList<PlanetData> allPlanets = (ArrayList<PlanetData>)planets.clone();

        while (!unchecked.isEmpty()) {
            iterator = (ArrayList<PlanetData>) unchecked.clone();
            unchecked.clear();

            for (PlanetData obj : iterator) {
                unchecked.addAll(obj.getChildren());
                allPlanets.addAll(obj.getChildren());
            }
        }

        double biggest = -1;

        for (PlanetData planet : allPlanets) {
            if (planet.getR() > biggest) { biggest = planet.getR(); }
            if (planet.getDistance() > biggest) { biggest = planet.getDistance(); }
        }

        for (PlanetData obj : allPlanets) {
            obj.setR(obj.getR()/biggest);
            obj.setDistanceToLast(obj.getDistance()/biggest);
        }

        return planets;
    }
}
