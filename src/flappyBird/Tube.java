package flappyBird;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tube extends Rectangle implements Runnable {
     private BufferedImage bufferedImageUp, bufferedImageDown;
    Image tubeImage;
        Tube(int x, int y, int width, int height) {
            super(x, y, width, height);

        }

        public void draw(Graphics g2d){
            g2d.setColor(Color.cyan.darker());
            g2d.drawRect(x+4,y,width-9,height);
            g2d.drawImage(tubeImage, x, y, null);
        }


        @Override
        public void run(){
            try{
                if (y==0){
                    bufferedImageUp = ImageIO.read(new File("resources/PipeDown.png"));
                    tubeImage = bufferedImageUp.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);
                }
                else {
                    bufferedImageDown = ImageIO.read(new File("resources/PipeUp.png"));
                    tubeImage = bufferedImageDown.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
