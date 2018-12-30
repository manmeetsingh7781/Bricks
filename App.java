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
    }
}


class Game extends JPanel implements KeyListener, ActionListener {
    private static Timer timer;
    private static int ballX= 10, ballY = 10, ballXdir = 3, ballYdir = 2;
    private static int width_divider = 10;
    private static int padX, padY, width, height;
    private int moveSpeed = 30;
    private int delay = 10;





    Game(){
        width = 60;
        height = 5;
        padX = (600/2)-(width);
        padY = 600-50;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }


    public void paint(Graphics g){



        g.setColor(Color.black);
        g.fillRect(0,0, super.getWidth(), super.getHeight());

        // ball
        g.setColor(Color.white);
        g.fillOval(ballX, ballY, 20, 20);

        // Padel
        g.setColor(Color.cyan);
        g.drawRect(padX, padY, width, height);

        g.dispose();


    }
    public void actionPerformed(ActionEvent e) {
       timer.start();
       ballX += ballXdir;
       ballY += ballYdir;
//       padX += moveSpeed;


       if(ballX >= super.getWidth()-20){
           ballXdir = -ballXdir;
       }

       if(ballX <= 0 ){
           ballXdir = -ballXdir;
       }


            // floor fallback
//        if(ballY >= super.getHeight()-25){
//            ballYdir = -ballYdir;
//        }

        if(ballY <= 0 ){
            ballYdir = -ballYdir;
        }



        if(padX <=0 ) {
           padX = 1;
        }
        if(padX >= super.getWidth()-width){
            padX = super.getWidth()-width;

        }


        // Collision ball and pad

        if((ballX) >= (padX-16) && (ballX) <= (padX+width) && (ballY) >= (padY-20) && (ballY-20) <= (padY+height)){
            ballYdir = -ballYdir;
            System.out.println("TOUCHED");
        }

        repaint();
    }





    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            padX += moveSpeed;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            padX -= moveSpeed;
        }
    }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {

                padX += moveSpeed;

        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            padX -= moveSpeed;
        }
    }
}
