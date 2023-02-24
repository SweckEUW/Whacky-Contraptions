package Bildverarbeitung.detector;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import Bildverarbeitung.dataclasses.LineData;
import Bildverarbeitung.dataclasses.PlanetData;
import Bildverarbeitung.dataclasses.Relation;
import Bildverarbeitung.functions.Function;

public class Detection {

    Function function = new Function ();

    public Detection () {
    }

    /**
     * detectPlanets detects all circles in a given picture and creates PlanetData objects for every single one. Also adds attributes dependant
     * on the color of the circles
     * @param SOURCE picture with circles that have to be detected for further processing
     * @return Arraylist with detected circles as PlanetData objects
     */
    public ArrayList<PlanetData> detectPlanets (Mat SOURCE) {
        Mat GRAY = new Mat ();
        Mat CIRCLES = new Mat ();
        Mat BLUR = new Mat ();

        //converting to grayscale image
        Imgproc.cvtColor(SOURCE, GRAY, Imgproc.COLOR_BGR2GRAY);
        //blurring image with median blur
        Imgproc.medianBlur(GRAY, BLUR, 5);
        //detecting circles
        Imgproc.HoughCircles(BLUR, CIRCLES, Imgproc.HOUGH_GRADIENT, 1, 50, 50, 25, 1, 199);
        //DEBUG printing circle data
        System.out.println("Mat:Circles ->" + CIRCLES.dump());

        //create ArrayList with data for detected planets
        ArrayList<PlanetData> DETECTED_PLANETS = new ArrayList<>();

        //converting to hsv image
        Mat HSV = new Mat ();
        Imgproc.cvtColor(SOURCE, HSV, Imgproc.COLOR_BGR2HSV);

        for (int i=0; i<CIRCLES.cols(); i++) {
            double[] TEMP_DATA = CIRCLES.get(0,i);

            int col = (int)TEMP_DATA[0];
            int row = (int)TEMP_DATA[1];
            double[] color = HSV.get(row, col);

            //Farbwert zwischen 0 und 180
            double hue = color[0];

            System.out.println("Planet "+i+" Hue: " + hue +" row: "+row+" col:"+col);

            int planetType = 69;

            if(hue>165 || hue<=15){
                planetType = 0; //red
            }
            else if(hue>15 && hue<=32){
                planetType = 1; //yellow
            }
            else if(hue>32 && hue<=77){
                planetType = 2; //green
            }
            else if(hue>77 && hue<=95){
                planetType = 3; //cyan
            }
            else if(hue>95 && hue<=135){
                planetType = 4; //blue
            }
            else if(hue>135 && hue<=165){
                planetType = 5; // magenta
            }

            /*
            //Farbwert wird in 6 gleich große Bereiche aufgeteilt
            double type = Math.round(hue/30);
            System.out.println(type);

            int planetType = (int) type;
            System.out.println(Math.round(type));


             * 0=red
             * 1=yellow
             * 2=green
             * 3=cyan
             * 4=blue
             * 5=magenta
             * 6=red
             * */
            if(planetType==6){
                planetType=0;
            }
            switch(planetType){
                case 0:
                    System.out.println("red / dark matter");
                    break;
                case 1:
                    System.out.println("yellow / Sun");
                    break;
                case 2:
                    System.out.println("green / Earth");
                    break;
                case 3:
                    System.out.println("cyan / Crystal");
                    break;
                case 4:
                    System.out.println("blue / Stone");
                    break;
                case 5:
                    System.out.println("magenta / Candy");
                    break;
            }
            DETECTED_PLANETS.add(new PlanetData (TEMP_DATA[0], TEMP_DATA[1], TEMP_DATA[2], planetType));
        }

        System.out.println("Number of circles: "+DETECTED_PLANETS.size());
        System.out.println(DETECTED_PLANETS);

        return DETECTED_PLANETS;
    }

    /**
     * Detects the biggest planet as the centre of the planet system
     * @param planets all detected planets as 'PlanetData' objects
     */
    public void findCentre (ArrayList<PlanetData> planets) {
        PlanetData biggest = new PlanetData (0, 0, -1, 0);

        for (PlanetData obj : planets) {
            if (obj.getR()>biggest.getR() && obj.getAncestor()==null && obj.getChildren().isEmpty() && !obj.isCentre()) {biggest = obj;}
        }

        biggest.setCentre();
    }

    /**
     * Algorithm to detect relations between planets due to only partial line detection and possible detection mistakes. The algorithm utilises
     * a kind of ray tracing. A 'ray' (vector) gets shot in direction of a detected line and detects the first planet it pierces.
     * @param SOURCE source image containing circles and relationlines (should be the same as in method 'detectPlanets')
     * @param PLANETS all detected circles as a PlanetData Arraylist (from the 'detectPlanets' method)
     * @param SAMPLE_SIZE size of the ray-vector. If sample_size is bigger, the algorithm is faster but less precise (may miss a small planet).
     *                    If its smaller the algorithm becomes slower but more accurate
     * @param WIDTH width of the source image
     * @param HEIGTH heigth of the source image
     * @return arraylist containing all detected relations
     */
    public ArrayList<Relation> detectRelations (Mat SOURCE, ArrayList<PlanetData> PLANETS, int SAMPLE_SIZE, int WIDTH, int HEIGTH) {
        //initializing mats
        Mat GRAY = new Mat();
        Mat EDGES = new Mat();
        Mat LINES = new Mat();


        //processing the image/ detecting possible relations/lines
        //converting to grayscale
        Imgproc.cvtColor(SOURCE, GRAY, Imgproc.COLOR_BGR2GRAY);
        //using the Canny-Edge-Detector
        Imgproc.Canny(GRAY, EDGES, 15, 35);
        //detecting lines (probabilistic)
        Imgproc.HoughLinesP(EDGES, LINES, 1, Math.PI / 180, 5, 0);

        //saving lines as LineData in ArrayList
        ArrayList<LineData> LINE_DATA = new ArrayList<>();
        for (int i = 0; i < LINES.rows(); i++) {
            double[] points = LINES.get(i, 0);
            LINE_DATA.add(new LineData(points[0], points[1], points[2], points[3]));
        }

        System.out.println("Lines in LINE_DATA: " + LINE_DATA);
        ArrayList<Relation> RELATIONS = new ArrayList<> ();

        for (LineData LINE : LINE_DATA) {
            //creating vector from line- start to -end
            double[] VECTOR = {LINE.getEnd()[0]-LINE.getStart()[0], LINE.getEnd()[1]-LINE.getStart()[1]};
            double NORM = function.norm(VECTOR);
            VECTOR[0] = (VECTOR[0]/NORM)*SAMPLE_SIZE;
            VECTOR[1] = (VECTOR[1]/NORM)*SAMPLE_SIZE;

            //creating reference point
            double[] REFERENCE_1 = {LINE.getStart()[0]+VECTOR[0]/2, LINE.getStart()[1]+VECTOR[1]/2};
            double[] REFERENCE_2 = {LINE.getStart()[0]+VECTOR[0]/2, LINE.getStart()[1]+VECTOR[1]/2};
            boolean FOUND_PLANET_1 = false;
            PlanetData FOUND_A = null, FOUND_B;
            boolean FOUND_PLANET_2 = false;

            while (REFERENCE_1[0]>0 && REFERENCE_1[0]<WIDTH && REFERENCE_1[1]>0 && REFERENCE_1[1]<HEIGTH && FOUND_PLANET_1==false) {
                for (PlanetData PLANET : PLANETS) {
                    double[] PLANET_POSITION = {PLANET.getX(), PLANET.getY()};
                    double[] RAD_VEC = {PLANET.getR(), PLANET.getR()};
                    if (PLANET_POSITION[0]-RAD_VEC[0] < REFERENCE_1[0] && PLANET_POSITION[0]+RAD_VEC[0] > REFERENCE_1[0]) {
                        if (PLANET_POSITION[1]-RAD_VEC[1] < REFERENCE_1[1] && PLANET_POSITION[1]+RAD_VEC[1] > REFERENCE_1[1]) {
                            FOUND_PLANET_1 = true;
                            FOUND_A = PLANET;
                        }
                    }
                }
                REFERENCE_1[0] = REFERENCE_1[0]+VECTOR[0];
                REFERENCE_1[1] = REFERENCE_1[1]+VECTOR[1];
            }

            while (REFERENCE_2[0]>0 && REFERENCE_2[0]<WIDTH && REFERENCE_2[1]>0 && REFERENCE_2[1]<HEIGTH && FOUND_PLANET_2==false) {
                for (PlanetData PLANET : PLANETS) {
                    double[] PLANET_POSITION = {PLANET.getX(), PLANET.getY()};
                    double[] RAD_VEC = {PLANET.getR(), PLANET.getR()};
                    if (PLANET_POSITION[0]-RAD_VEC[0] < REFERENCE_2[0] && PLANET_POSITION[0]+RAD_VEC[0] > REFERENCE_2[0]) {
                        if (PLANET_POSITION[1]-RAD_VEC[1] < REFERENCE_2[1] && PLANET_POSITION[1]+RAD_VEC[1] > REFERENCE_2[1]) {
                            FOUND_PLANET_2 = true;
                            if (FOUND_PLANET_1 && FOUND_PLANET_2) {
                                FOUND_B = PLANET;
                                double[] DISTANCE = {FOUND_B.getX()-FOUND_A.getX(), FOUND_B.getY()-FOUND_A.getY()};
                                RELATIONS.add(new Relation(FOUND_A, FOUND_B, function.norm(DISTANCE)));
                            }
                        }
                    }
                }
                REFERENCE_2[0] = REFERENCE_2[0]-VECTOR[0];
                REFERENCE_2[1] = REFERENCE_2[1]-VECTOR[1];
            }
        }
        return RELATIONS;
    }

    /* First implementation of an efficient algorithm for relation detection. Failed to correctly determine
    the values of certain criteria. Was replaced with a less efficient but functioning algorithm
     */
    /*
    public ArrayList<Relation> detectRelations (Mat SOURCE, ArrayList<PlanetData> PLANETS, int WIDTH, int TOLERANCE) {
        //initializing mats
        Mat GRAY = new Mat();
        Mat EDGES = new Mat();
        Mat LINES = new Mat();

        //processing the image/ detecting possible relations/lines
        //converting to grayscale
        Imgproc.cvtColor(SOURCE, GRAY, Imgproc.COLOR_BGR2GRAY);
        //using the Canny-Edge-Detector
        Imgproc.Canny(GRAY, EDGES, 50, 75);
        //detecting lines (probabilistic)
        Imgproc.HoughLinesP(EDGES, LINES, 0.9, Math.PI / 180, 20, 20);

        //saving lines as LineData in ArrayList
        ArrayList<LineData> LINE_DATA = new ArrayList<> ();
        for (int i=0; i<LINES.rows(); i++) {
            double[] points = LINES.get(i,0);
            LINE_DATA.add(new LineData (points[0], points[1], points[2], points[3]));
        }

        System.out.println("Lines in LINE_DATA: "+LINE_DATA);


        //calculating max number of possible relations between circles
        int n = PLANETS.size(), r = 2;
        int SUM_COMBINATIONS = function.factorial(n) / (function.factorial(2) * function.factorial(n - r));
        PlanetData[][] PLANET_COMBINATIONS = new PlanetData[SUM_COMBINATIONS][2];

        System.out.println("Amount of possible relations: "+SUM_COMBINATIONS);

        //creating all possible relations
        int PLANET_POSITION = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                PLANET_COMBINATIONS[PLANET_POSITION][0] = PLANETS.get(i);
                PLANET_COMBINATIONS[PLANET_POSITION][1] = PLANETS.get(j);
                PLANET_POSITION += 1;
            }
        }

        System.out.println();

        //initializing final return ArrayList
        ArrayList<Relation> RELATIONS = new ArrayList<> ();

        //starting to check for relations
        for (int i=0; i<PLANET_COMBINATIONS.length; i++) {
            //saving planet coordinates
            PlanetData COMB[] = PLANET_COMBINATIONS[i];
            PlanetData PLANET_A = COMB[0];
            PlanetData PLANET_B = COMB[1];

            //creating vector from one planet to the other to determinate the orthogonal vector and for
            //rectangle calculation
            double[] CONNECTION_VECTOR = new double[2];
            CONNECTION_VECTOR[0] = PLANET_B.getX()-PLANET_A.getX();
            CONNECTION_VECTOR[1] = PLANET_B.getY()-PLANET_A.getY();

            System.out.println("connection vec: "+CONNECTION_VECTOR[0]+"|"+CONNECTION_VECTOR[1]);

            //create orthogonal vector, normalize and scale with factor "WIDTH/2"
            double[] ORTHOGONAL_VECTOR = function.createOrthogonalVec(CONNECTION_VECTOR);
            double NORM_ORTHOGONAL = function.norm(ORTHOGONAL_VECTOR);
            ORTHOGONAL_VECTOR[0] = (ORTHOGONAL_VECTOR[0]/NORM_ORTHOGONAL)*(WIDTH/2);
            ORTHOGONAL_VECTOR[1] = (ORTHOGONAL_VECTOR[1]/NORM_ORTHOGONAL)*(WIDTH/2);

            System.out.println("Orthogonal vector: "+ORTHOGONAL_VECTOR[0]+"|"+ORTHOGONAL_VECTOR[1]);

            //creating corner points for test rectangle and calculating length
            double[] CORNER_A = {PLANET_A.getX()+ORTHOGONAL_VECTOR[0], PLANET_A.getY()+ORTHOGONAL_VECTOR[1]};
            double[] CORNER_B = {PLANET_A.getX()-ORTHOGONAL_VECTOR[0], PLANET_A.getY()-ORTHOGONAL_VECTOR[1]};
            double[] CORNER_C = {PLANET_B.getX()+ORTHOGONAL_VECTOR[0], PLANET_B.getY()+ORTHOGONAL_VECTOR[1]};
            double[] CORNER_D = {PLANET_B.getX()-ORTHOGONAL_VECTOR[0], PLANET_B.getY()-ORTHOGONAL_VECTOR[1]};

            //calculating reference point for rectangle calculation using triangles
            double REF_X = PLANET_A.getX()+(CONNECTION_VECTOR[0]/2);
            double REF_Y = PLANET_A.getY()+(CONNECTION_VECTOR[1]/2);
            double[] REFERENCE = {REF_X, REF_Y};

            //checking for relations
            for (int j=0; j<LINE_DATA.size(); j++){
                LineData LINE = LINE_DATA.get(j);
                double[] LINE_START = LINE.getStart();
                //calculating first sum of triangles with line-starting-point
                System.out.println("Reference: "+REFERENCE[0]+"/"+REFERENCE[1]);
                System.out.println("new linestart: "+LINE_START[0]+"  "+LINE_START[1]);
                System.out.println("Corner A: "+CORNER_A[0]+"  "+CORNER_A[1]);
                System.out.println("Corner B: "+CORNER_B[0]+"  "+CORNER_B[1]);
                System.out.println("Corner C: "+CORNER_C[0]+"  "+CORNER_C[1]);
                System.out.println("Corner D: "+CORNER_D[0]+"  "+CORNER_D[1]);
                double TRIANGLE_SUM_1 =   function.calculateTriangleArea(CORNER_A, CORNER_B, LINE_START) +
                                        function.calculateTriangleArea(CORNER_B, CORNER_C, LINE_START) +
                                        function.calculateTriangleArea(CORNER_C, CORNER_D, LINE_START) +
                                        function.calculateTriangleArea(CORNER_D, CORNER_A, LINE_START);

                System.out.println("Sum first Triangles: "+TRIANGLE_SUM_1);

                double RECTANGLE_SUM = function.calculateTriangleArea(CORNER_A, CORNER_B, REFERENCE) +
                        function.calculateTriangleArea(CORNER_B, CORNER_C, REFERENCE) +
                        function.calculateTriangleArea(CORNER_C, CORNER_D, REFERENCE) +
                        function.calculateTriangleArea(CORNER_D, CORNER_A, REFERENCE);

                System.out.println("Sum RECTANGLE: "+RECTANGLE_SUM);

                System.out.println(RECTANGLE_SUM-TRIANGLE_SUM_1);
                if (RECTANGLE_SUM-TRIANGLE_SUM_1>0) {
                    double[] LINE_END = LINE.getEnd();
                    //calculating second sum of triangles with line-ending-point
                    double TRIANGLE_SUM_2 =   function.calculateTriangleArea(CORNER_A, CORNER_B, LINE_END) +
                            function.calculateTriangleArea(CORNER_B, CORNER_C, LINE_END) +
                            function.calculateTriangleArea(CORNER_C, CORNER_D, LINE_END) +
                            function.calculateTriangleArea(CORNER_D, CORNER_A, LINE_END);
                    System.out.println("_Sum second Triangles: "+TRIANGLE_SUM_2);

                    System.out.println(RECTANGLE_SUM-TRIANGLE_SUM_2);
                    if (RECTANGLE_SUM-TRIANGLE_SUM_2>0) {
                        System.out.println("!!!Relation found!!!");
                        //adding planets to new Relation
                        double[] POINT_A = {PLANET_A.getX(), PLANET_A.getY()};
                        double[] POINT_B = {PLANET_B.getX(), PLANET_B.getY()};
                        RELATIONS.add(new Relation(PLANET_A, PLANET_B, Math.floor(function.norm(function.calcVec(POINT_A, POINT_B)))));
                    }
                }
                System.out.println("__________________________________________");
            }
        }
        System.out.println("Detected Relations: "+RELATIONS.size());

        //checking if planets/ circles lay on relations

        return RELATIONS;
    }
    */
}
