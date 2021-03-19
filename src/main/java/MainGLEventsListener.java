import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.nio.FloatBuffer;

public class MainGLEventsListener implements GLEventListener {
    private final NurbsManager nurbsManager;

    public MainGLEventsListener(NurbsManager nurbsManager) {
        this.nurbsManager = nurbsManager;
        nurbsManager.generateKnotVector();
    }

    public void init(GLAutoDrawable glAutoDrawable) {

    }

    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl2.glLoadIdentity();

        Point[] controlPoints = nurbsManager.getControlPoints();

        gl2.glColor3f(1, 1, 0);
        gl2.glBegin(gl2.GL_LINE_STRIP);

        for (float t = 0; t <= 1; t += 0.0001) {
            float x, y;
            x = 0;
            y = 0;
            double rationalWeight = 0d;

            for (int i = 0; i < nurbsManager.getControlPoints().length; i++)
            {
                double temp = nurbsManager.coxDeBoor(t, i, nurbsManager.getgOrder()) * nurbsManager.getControlPoints()[i].getWeight();
                rationalWeight += temp;
            }

            for (int i = 0; i < nurbsManager.getControlPoints().length; i++)
            {
                double temp = nurbsManager.coxDeBoor(t, i, nurbsManager.getgOrder());
                x += nurbsManager.getControlPoints()[i].getX() * nurbsManager.getControlPoints()[i].getWeight() * temp/rationalWeight;
                y += nurbsManager.getControlPoints()[i].getY() * nurbsManager.getControlPoints()[i].getWeight() * temp / rationalWeight;
            }
            gl2.glVertex2f(x, y);
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




}
