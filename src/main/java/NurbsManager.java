public class NurbsManager {
    private Point[] controlPoints;

    public NurbsManager(Point[] controlPoints) {
        this.controlPoints = controlPoints;
    }

    public Point[] getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(Point[] controlPoints) {
        this.controlPoints = controlPoints;
    }

    public float coxDeBoor(float u, int i, int k, float[] Knots) {
        if (k == 1) {
            if (Knots[i] <= u && u <= Knots[i+1]) { //B_i,0(t) = 1, if t_i <= t < t_{i+1}, else = 0
                return 1.0f;
            }
            return 0.0f;
        }
        float Den1 = Knots[i + k - 1] - Knots[i];
        float Den2 = Knots[i + k] - Knots[i + 1];
        float Eq1 = 0, Eq2 = 0;
        if (Den1 > 0) {
            Eq1 = ((u-Knots[i]) / Den1) * coxDeBoor(u, i,k-1,Knots);
        }
        if (Den2 > 0) {
            Eq2 = (Knots[i+k]-u) / Den2 * coxDeBoor(u,i+1,k-1,Knots);
        }
        return Eq1 + Eq2;
    }

//    public double Nip(int i, int p, float[] U, double u)
//    {
//        double[] N = new double[p + 1];
//        double saved, temp;
//
//        int m = U.length - 1;
//        if ((i == 0 && u == U[0]) || (i == (m - p - 1) && u == U[m]))
//            return 1;
//
//        if (u < U[i] || u >= U[i + p + 1])
//            return 0;
//
//        for (int j = 0; j <= p; j++)
//        {
//            if (u >= U[i + j] && u < U[i + j + 1])
//                N[j] = 1d;
//            else
//                N[j] = 0d;
//        }
//
//        for (int k = 1; k <= p; k++)
//        {
//            if (N[0] == 0)
//                saved = 0d;
//            else
//                saved = ((u - U[i]) * N[0]) / (U[i + k] - U[i]);
//
//            for (int j = 0; j < p - k + 1; j++)
//            {
//                double Uleft = U[i + j + 1];
//                double Uright = U[i + j + k + 1];
//
//                if (N[j + 1] == 0)
//                {
//                    N[j] = saved;
//                    saved = 0d;
//                }
//                else
//                {
//                    temp = N[j + 1] / (Uright - Uleft);
//                    N[j] = saved + (Uright - u) * temp;
//                    saved = (u - Uleft) * temp;
//                }
//            }
//        }
//        return N[0];
//    }
}
