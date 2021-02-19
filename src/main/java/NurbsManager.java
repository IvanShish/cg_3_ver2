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
}
