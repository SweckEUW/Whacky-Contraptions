package Bildverarbeitung.functions;

public class Function {

    public int factorial (int NUMBER) {
        int RESULT = 1;

        for (int FACTOR=2; FACTOR<=NUMBER; FACTOR++) {
            RESULT *= FACTOR;
        }
        return RESULT;
    }

    public double calculateRectangleArea (double[] VEC_A, double[] VEC_B) {
        return norm(VEC_A)*norm(VEC_B);
    }

    public double calculateTriangleArea (double[] POINT_A, double[] POINT_B, double[] POINT_P) {
        return 0.5*absValue((POINT_B[0]-POINT_A[0])*(POINT_P[1]*POINT_A[1])-(POINT_P[0]-POINT_A[0])*(POINT_B[1]-POINT_A[1]));
    }


    public double[] createOrthogonalVec (double[] VEC_A) {
        double[] VEC_ORTHOGONAL = new double[2];
        VEC_ORTHOGONAL[0] = VEC_A[1]*(-1);
        VEC_ORTHOGONAL[1] = VEC_A[0];

        return VEC_ORTHOGONAL;
    }

    public double norm (double[] VEC_A) {
        return absValue(Math.sqrt(Math.pow(VEC_A[0],2)+Math.pow(VEC_A[1],2)));
    }

    public double absValue (double a) {
        if (a<0) {
            return a*(-1);
        }
        else return a;
    }

    public double[] calcVec (double[] POINT_A, double[] POINT_B) {
        double[] NEW_VEC = {POINT_A[0]-POINT_B[0], POINT_A[1]-POINT_B[1]};
        return NEW_VEC;
    }

    public double calcScalar2D (double[] VEC_A, double[] VEC_B) {
        return VEC_A[0]*VEC_B[0]+VEC_A[1]*VEC_B[1];
    }
}
