package flappyBird;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, KeyListener {
    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 800;
    public Render render;
    public ArrayList<Tube> tubes;
    public Random rand;
    public int ticks, yMotion,score;
    public boolean gameOver, started;
    public Image backgroundImage, tubeImageUp , tubeImageDown;
    public Bird bird;



    FlappyBird(){
        flappyBird = this;
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20,this);
        try{

            BufferedImage bufferedImage = ImageIO.read(new File("resources/background.png"));
            backgroundImage = bufferedImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
            bufferedImage = ImageIO.read(new File("resources/PipeDown.png"));
            tubeImageDown = bufferedImage.getScaledInstance(100, 500, Image.SCALE_DEFAULT);
            bufferedImage = ImageIO.read(new File("resources/PipeUp.png"));
            tubeImageUp = bufferedImage.getScaledInstance(100, 500, Image.SCALE_DEFAULT);

        }catch (Exception e){
            e.printStackTrace();
        }

        render = new Render();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Flappy Bird");
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.add(render);
        jframe.addKeyListener(this);

        tubes = new ArrayList<>();
        rand = new Random();

        bird = new Bird(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
        timer.start();
        addTube(true);
        addTube(true);
        addTube(true);
        addTube(true);
    }
    public void repaint(Graphics g2d) {
        g2d.fillRect(0,0,WIDTH,HEIGHT);

        g2d.drawImage(backgroundImage, 0, 0, null);
        g2d.setColor(Color.orange);
        g2d.fillRect(0,HEIGHT-120,WIDTH,120);

        g2d.setColor(Color.green);
        g2d.fillRect(0,HEIGHT-120,WIDTH,20);

        bird.draw(g2d);

        for (Tube tube : tubes){
            paintColumn(g2d, tube);
        }

        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", 1, 100));

        if (gameOver){
            g2d.drawString("Game Over!", 100, HEIGHT/2 - 50);
        }
        if(!started){
            g2d.drawString("Click to start!", 100, HEIGHT/2 - 50);
        }
        if(!gameOver && started){
            g2d.drawString(String.valueOf(score), WIDTH/2 - 25, 100);
        }
    }
    public void addTube(boolean start){
        int space = 300;
        int width = 100;
        int height = 50 + rand.nextInt(300);

        if (start){
            tubes.add(new Tube(WIDTH + width + tubes.size() * 300, HEIGHT - height - 120, width, height));
            tubes.add(new Tube(WIDTH + width + (tubes.size()-1) * 300, 0, width, HEIGHT - height - space));
        }
        else{
            tubes.add(new Tube(tubes.get(tubes.size()-1).x + 600, HEIGHT - height - 120, width, height));
            tubes.add(new Tube(tubes.get(tubes.size()-1).x, 0, width, HEIGHT - height - space));
        }

    }
    public void paintColumn(Graphics g2d, Tube tube){
        //Thread thread = new Thread(tube);
        //thread.start();
        tube.draw(g2d);
        if (tube.y == 0){
            g2d.drawImage(tubeImageDown, tube.x, tube.height-500, null);
        }
        else{
            g2d.drawImage(tubeImageUp, tube.x, tube.y, null);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int speed =10;
        if (score>=5 &&score<20){
            speed = speed + score/2;
        }
        else if(score>=20){
            speed = 20;
        }

        ticks++;
        if(started){
            for (Tube tube : tubes) {
                tube.x -= speed;
            }
            if(ticks % 2 == 0 && yMotion < 15){
                yMotion += 2;
            }
            for(int i = 0; i< tubes.size(); i++){
                Tube tube = tubes.get(i);
                if (tube.x + tube.width < 0){
                    tubes.remove(tube);
                    if (tube.y == 0){
                        addTube(false);
                    }
                }
            }
            bird.y += yMotion;
            for (Tube tube : tubes){
                if (tube.y==0 && bird.x + bird.width/2 > tube.x + tube.width/2 - 10 && bird.x + bird.width/2 < tube.x + tube.width/2 + 10){
                    score++;
                }
                if (tube.intersects(bird)){
                    gameOver = true;
                    bird.x = tube.x - bird.width;
                }
            }
            if (bird.y >= HEIGHT - 150 || bird.y < 0){
                gameOver = true;
            }
            if (bird.y + yMotion >= HEIGHT - 120){
                bird.y = HEIGHT - 120 - bird.height;
            }
        }

        render.repaint();
    }

    public void jump() {
        if (gameOver){
            bird = new Bird(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
            tubes.clear();
            yMotion = 0;
            score = 0;
            addTube(true);
            addTube(true );
            addTube(true);
            addTube(true);

            gameOver=false;
        }
        if(!started){
            started = true;
        }
        else if(!gameOver){
            if (yMotion > 0){
                yMotion = 0;
            }
            yMotion -= 10;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public static void main(String[] args) {
        FlappyBird flappyBird = new FlappyBird();
    }
}
