package Breaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Breaker extends JPanel implements KeyListener, ActionListener {
    private boolean play = true;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;

    private int playerX = 310;
    private float ballPosX = 120;
    private float ballPosY = 350;
    private float ballXDir = -2;
    private float ballYDir = -4;

    private MapGenerator map;

    private String font = "Helvetica";

    public Breaker() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D)g);

        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 12);

        g.setColor(Color.RED);  // ball color
        g.fillOval((int)ballPosX, (int)ballPosY, 20, 20);

        g.setColor(Color.black);
        g.setFont(new Font(font, Font.BOLD, 25));
        g.drawString("Score: " + score, 520, 30);

        if (totalBricks <= 0) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;

            g.setColor(new Color(0XFF6464));
            g.setFont(new Font(font, Font.BOLD, 30));
            g.drawString("You Won, Score: " + score, 190, 300);

            g.setFont(new Font(font, Font.BOLD, 20));
            g.drawString("Press Enter to Restart." ,230, 350);
        }

        if (ballPosY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.BLACK);
            g.setFont(new Font(font, Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font(font, Font.BOLD, 20));
            g.drawString("Press Enter to Restart." ,230, 350);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        timer.start();
        if (play) {
            Rectangle ballRectangle = new Rectangle((int)ballPosX, (int)ballPosY, 20, 20);
            if (ballRectangle.intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
                if (ballXDir > 0 && ballRectangle.intersects(new Rectangle(playerX, 550, 40, 8))) {
                    ballXDir *= (float) -1.1;
                }
                if (ballXDir < 0 && ballRectangle.intersects(new Rectangle(playerX + 60, 550, 40, 8))) {
                    ballXDir *= (float) -1.1;
                }
            }

            for(int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle((int)ballPosX, (int)ballPosY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 1;

                            if (ballPosX + 19 <= brickRect.x || ballPosX >= brickRect.x + brickRect.width) {
                                ballXDir = -ballXDir;
                            }
                            if ((ballPosY + 19 >= brickRect.y || ballPosY <= brickRect.y + brickRect.height)) {
                                ballYDir = -ballYDir;
                            }
                        }
                    }
                }
            }

            ballPosX += ballXDir;
            ballPosY += ballYDir;
            if (ballPosX < 0) {
                ballXDir = -ballXDir;
            }
            if (ballPosY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballPosX > 670) {
                ballXDir = -ballXDir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }

    public void moveRight() { // paddle moves right by 50 pixels
        play = true;
        playerX += 50;
    }
    public void moveLeft() { // paddle moves left by 50 pixels
        play = true;
        playerX -= 50;
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }
}