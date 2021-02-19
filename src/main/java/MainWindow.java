import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JComboBox<Point> pointSelector;
    private JSlider xSlider;
    private JSlider ySlider;
    private JSlider wSlider;

    private Point selectedPoint;

    public MainWindow() throws HeadlessException {
        super();

        //Настройка для NURBS
        NurbsManager nurbsManager = new NurbsManager(new Point[]{
                new Point(-0.8, -0.5, 0, 1), new Point(-0.5, 0, 1, 1), new Point(0, 0.4, 2, 1),
                new Point(0.3, 0.2, 3, 1), new Point(0.6, -0.2, 4, 1), new Point(0.8, 0, 5, 1),
                new Point(0.9, 0.5, 6, 1)});

        //Главная панель
        JPanel grid = new JPanel();
        GridLayout layout = new GridLayout(1, 2);
        grid.setLayout(layout);

        //Панель для рисования
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLJPanel gljpanel = new GLJPanel(capabilities);
        MainGLEventsListener evensListener = new MainGLEventsListener(nurbsManager);
        gljpanel.addGLEventListener(evensListener);
        gljpanel.setSize(500, 500);
        grid.add(gljpanel);

        //Панель для кнопок
        JPanel buttonsPanel = new JPanel();
        BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS);
        buttonsPanel.setLayout(buttonsLayout);
        grid.add(buttonsPanel);

        //Управление
        pointSelector = new JComboBox<>(nurbsManager.getControlPoints());
        pointSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, pointSelector.getMinimumSize().height));
        pointSelector.addActionListener((e) -> {
            selectedPoint = nurbsManager.getControlPoints()[pointSelector.getSelectedIndex()];
            xSlider.setValue((int)(100 * selectedPoint.getX()));
            ySlider.setValue((int)(100 * selectedPoint.getY()));
            wSlider.setValue((int)(100 * selectedPoint.getWeight()));
        });
        buttonsPanel.add(pointSelector);
        selectedPoint = nurbsManager.getControlPoints()[0];

        xSlider = new JSlider(-100, 100, (int)(selectedPoint.getX() * 100));
        ySlider = new JSlider(-100, 100, (int)(selectedPoint.getY() * 100));
        wSlider = new JSlider(0, 100, (int)(selectedPoint.getWeight() * 100));
        xSlider.addChangeListener((e) -> {
            selectedPoint.setX(xSlider.getValue()/100f);
            gljpanel.display();
        });
        ySlider.addChangeListener((e) -> {
            selectedPoint.setY(ySlider.getValue()/100f);
            gljpanel.display();
        });
        wSlider.addChangeListener((e) -> {
            selectedPoint.setWeight(wSlider.getValue()/100f);
            gljpanel.display();
        });
        buttonsPanel.add(xSlider);
        buttonsPanel.add(ySlider);
        buttonsPanel.add(new JLabel("Weight"));
        buttonsPanel.add(wSlider);

        this.getContentPane().add(grid);
        this.setSize(1000, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
