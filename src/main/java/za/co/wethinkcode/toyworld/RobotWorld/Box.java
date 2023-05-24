package za.co.wethinkcode.toyworld.RobotWorld;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Box extends Rectangle {
    private BufferedImage image;
    private int speed;

    public Box(int x, int y, int width, int height, String imagePath, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;

        try {

            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.y -= speed;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.y += speed;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.x -= speed;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.x += speed;
        }
    }
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveUp(int steps) {
    }

    public void moveDown(int steps) {
    }
}
