package flappyBird;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d= (Graphics2D) g;

        FlappyBird.flappyBird.repaint(g2d);
    }
}
