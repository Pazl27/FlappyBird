package flappyBird;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bird extends Rectangle {
    public Image birdImage;

    Bird(int x, int y, int width, int height){
        super(x,y,width,height);
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("resources/bird.png"));
            birdImage = bufferedImage.getScaledInstance(55, 55, Image.SCALE_DEFAULT);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics g2d){
        g2d.drawRect(x,y,width,height);
        g2d.drawImage(birdImage, x-18, y -18, null);
    }
}
