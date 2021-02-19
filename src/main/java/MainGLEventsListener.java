import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.nio.FloatBuffer;

public class MainGLEventsListener implements GLEventListener {
    private final NurbsManager nurbsManager;
    private int LOD = 40; //level of detail
    private final float[] gKnots = {0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f};
//    private float[] gKnots;
    private final int gNumCvs;
    private final int gDegree = 3;
    private final int gOrder = gDegree + 1;
    private final int gNumKnots;
//    n = p - 1 - degree of polynoms

    public MainGLEventsListener(NurbsManager nurbsManager) {
        this.nurbsManager = nurbsManager;
        gNumCvs = nurbsManager.getControlPoints().length;
        gNumKnots = gNumCvs + gOrder;
//        HOW TO GENERATE gKnots???????????????????
//        gKnots = new float[gNumKnots];
//        int j = 0;
//        for (int i = (int) Math.floor(gNumKnots / 2) - 1; i < gNumKnots; i++) {
//            if (j < gOrder) j++;
//            gKnots[i] = (float) (j);
//        }
//        for (int i = 0; i < gKnots.length; i++) System.out.println(gKnots[i] + " ");
    }

    public void init(GLAutoDrawable glAutoDrawable) {

    }

    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        // clear the previous transform
        gl2.glLoadIdentity();

        Point[] controlPoints = nurbsManager.getControlPoints();

        gl2.glColor3f(1, 1, 0);
        gl2.glBegin(gl2.GL_LINE_STRIP);
        for(int i = 0; i != LOD; i++) {

            float t  = gKnots[gNumKnots-1] * i / (float)(LOD-1);

            if (i == LOD-1)
                t -= 0.001f;

            float[] OutPoint = new float[3];
            OutPoint[0] = 0;
            OutPoint[1] = 0;
            OutPoint[2] = 0;

            for(int j = 0; j != gNumCvs; j++) {

                // calculate the effect of this point on the curve
                float Val = CoxDeBoor(t, j, gOrder, gKnots);

                if(Val > 0.001f) {

                    // sum effect of CV on this part of the curve
                    OutPoint[0] += Val * controlPoints[j].getX();
                    OutPoint[1] += Val * controlPoints[j].getY();
                    OutPoint[2] += Val * controlPoints[j].getWeight();
                }
            }

            gl2.glVertex3fv(FloatBuffer.wrap(OutPoint));
        }
        gl2.glEnd();

        gl2.glColor3f(1, 0, 0);
        gl2.glPointSize(10);
        gl2.glBegin(GL2.GL_POINTS);
        for (Point point : controlPoints) {
            gl2.glVertex2d(point.getX(), point.getY());
        }
        gl2.glEnd();


    }

    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    private float CoxDeBoor(float u, int i, int k, float[] Knots) {
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
            Eq1 = ((u-Knots[i]) / Den1) * CoxDeBoor(u, i,k-1,Knots);
        }
        if (Den2 > 0) {
            Eq2 = (Knots[i+k]-u) / Den2 * CoxDeBoor(u,i+1,k-1,Knots);
        }
        return Eq1 + Eq2;
    }
}
