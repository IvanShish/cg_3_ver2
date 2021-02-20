public class NurbsManager {
    private Point[] controlPoints;

    private float[] gKnots;
    private final int gDegree = 3;
    private final int gOrder = gDegree + 1;


    public NurbsManager(Point[] controlPoints) {
        this.controlPoints = controlPoints;
        gKnots = new float[controlPoints.length + gOrder];
    }


    public float coxDeBoor(float u, int i, int k) {
        if (k == 1) {
            if (gKnots[i] <= u && u <= gKnots[i+1]) {
                return 1.0f;
            }
            return 0.0f;
        }
        float Den1 = gKnots[i + k - 1] - gKnots[i];
        float Den2 = gKnots[i + k] - gKnots[i + 1];
        float Eq1 = 0, Eq2 = 0;
        if (Den1 > 0) {
            Eq1 = ((u-gKnots[i]) / Den1) * coxDeBoor(u, i,k-1);
        }
        if (Den2 > 0) {
            Eq2 = (gKnots[i+k]-u) / Den2 * coxDeBoor(u,i+1,k-1);
        }
        return Eq1 + Eq2;
    }

    public void generateKnotVector() {
        if (gDegree + 1 > controlPoints.length || controlPoints.length == 0)
            return;

        int n = controlPoints.length;
        int m = n + gDegree + 1;
        int divisor = m - 1 - 2 * gDegree;

        gKnots[0] = 0;
        for (int i = 1; i < m; i++)
        {
            if (i <= gDegree)
                gKnots[i] = 0;
            else if (i >= m - gDegree - 1)
                gKnots[i] = 1;
            else {
                int dividend = i - gDegree;
                gKnots[i] = (float) dividend / divisor;
            }
        }
    }

    public Point[] getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(Point[] controlPoints) {
        this.controlPoints = controlPoints;
    }

    public int getgOrder() {
        return gOrder;
    }
}
