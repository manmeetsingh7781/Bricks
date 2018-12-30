import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class App extends JFrame {
    private static JFrame frame = new JFrame();
    private static Game game = new Game();


    public static void main(String[] args) {
        frame.setVisible(true);
        frame.setSize(600,600);
        frame.setTitle("Java Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setResizable(false);

    }
}


class Game extends JPanel implements KeyListener, ActionListener {
    private static Timer timer;
    private static int ballX, ballY, ballXdir, ballYdir, ball_width, ball_height;
    private static int padX, padY, width, height;
    private int moveSpeed = 30;
    private boolean isStarted = false, isBallAlive = true, isTouched = false;







    Game(){
        int delay = 10;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);

        // Player
        width = 60;
        height = 5;
        padX = (600/2)-width;
        padY = 500;

        // Ball
        ball_width = 20;
        ball_height = 20;
        ballX = (600/2)-(ball_width+20);
        ballY = 300;


        // Ball speed direction
        ballXdir = 3;
        ballYdir = 3;

        timer.start();
    }




    public void paint(Graphics g){
        isStarted = true;
        g.setColor(Color.black);
        g.fillRect(0,0, super.getWidth(), super.getHeight());

        // ball
        g.setColor(Color.white);
        g.fillOval(ballX, ballY, ball_width, ball_height);

        // Padel
        g.setColor(Color.cyan);
        g.draw3DRect(padX, padY, width, height, true);



        if(!isBallAlive){
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
            g.drawString("YOU LOST", (600/2)-32*2, (400)-32*4);
        }
        g.dispose();


    }
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(isBallAlive) {
            ballY += ballYdir;


            if(ballX >= super.getWidth()-20){
                ballXdir = -ballXdir;
            }

            if(ballX <= 0 ){
                ballXdir = -ballXdir;
            }

            if(ballY <= 0 ){
                ballYdir = -ballYdir;
            }

        }

        // floor fallback
        if(ballY >= 600){
           isBallAlive = false;
        }





        if(isStarted && isBallAlive) {
            if (padX <= 0) {
                padX = 1;
            }

            if (padX >= super.getWidth() - width) {
                padX = super.getWidth() - (width + 5);

            }
        }


        // Collision ball and pad
        if((ballX) >= (padX-(ball_height-4)) && (ballX) <= (padX+width) && (ballY) >= (padY-ball_height) && (ballY-ball_height) <= (padY+height)){
            ballYdir = -ballYdir;
            isTouched = true;
        }

        if(isTouched && isBallAlive){
                 ballX += ballXdir;
        }

        // Re drawing after every delay timer
        repaint();
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        // Right Arrow
        if(( key == KeyEvent.VK_RIGHT  || key == KeyEvent.VK_D) && isBallAlive) {
            padX += moveSpeed;
        }

        // Left arrow
        if( ( key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A ) && isBallAlive) {
            padX -= moveSpeed;
        }
    }


    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
