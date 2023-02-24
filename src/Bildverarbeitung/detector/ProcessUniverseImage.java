package Bildverarbeitung.detector;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import Bildverarbeitung.dataclasses.PlanetData;
import Bildverarbeitung.dataclasses.Relation;
import Bildverarbeitung.exceptions.NoRelationsException;

public class ProcessUniverseImage {


    /**
     * This is the main method of the image processing. This method reads the image, detects circles and relations and
     * organizes them for further processing
     * @param PATH path to the image that shall be processed
     * @return ArrayList containing all centres
     */
    public static ArrayList<PlanetData> process(BufferedImage image) {
        System.loadLibrary (Core.NATIVE_LIBRARY_NAME);
        
        Mat SOURCE_IMAGE = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        SOURCE_IMAGE.put(0, 0, data);
        
        Detection detector = new Detection ();
        Sorter sorter = new Sorter ();

        //detecting circles as PLANET_DATA
        ArrayList<PlanetData> PLANET_DATA = detector.detectPlanets(SOURCE_IMAGE);
        //detecting relations as RELATION_DATA
        ArrayList<Relation> RELATION_DATA = detector.detectRelations(SOURCE_IMAGE, PLANET_DATA, 5, SOURCE_IMAGE.cols(), SOURCE_IMAGE.rows());

        //detecting the centred planet
        detector.findCentre (PLANET_DATA);

        //organizing planet data for further processing
        ArrayList<PlanetData> ORGANIZED = sorter.organize(RELATION_DATA, PLANET_DATA);
        try {
            sorter.checkForSoloPlanets(ORGANIZED);
        }
        catch (NoRelationsException e) {
            System.out.println(e.getMessage());
        }

        //normalizing sizes
        sorter.normalize(ORGANIZED);

        //return ORGANIZED
        System.out.println("\nDetection completed.");
        return ORGANIZED;
    }
}