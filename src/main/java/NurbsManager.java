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
}
