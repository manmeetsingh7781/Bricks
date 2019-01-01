import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

interface variables {
    int screen_width = 800;
    int screen_height = 600;
}
public class App extends JFrame implements variables {

    //  The Main frame
    private static JFrame frame = new JFrame();

    // Game Frame
    private static Game game = new Game();


    public static void main(String[] args) {

        // Setting  visibility of the frame
        frame.setVisible(true);

        // Setting Dimensions
        frame.setSize(screen_width, screen_height);

        // Title of the Main Frame
        frame.setTitle("Java Application");

        // The close method
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding game Panel into the frame
        frame.add(game);

        // Setting image Icon
        ImageIcon img_icon = new ImageIcon("src\\bricks-game.jpg");

        // Getting the image preview from the ImageIcon and setting it up
        frame.setIconImage(img_icon.getImage());

        // Setting Window resizable to false
        frame.setResizable(false);

    }
}


class Game extends JPanel implements KeyListener, ActionListener, variables {

    // These are intilizers for the game so we could able to use it everywhere in the class
    private static Timer timer;

    // Ball
    private static int ballX, ballY, ballXdir, ballYdir, ball_width, ball_height;

    // Player
    private int col = 7, row = 3;
    private int padX, padY, width, height, moveSpeed = 30, counter = 0, opacity = 35, tiles= col*row;

    // Game
    private boolean isStarted = false, isBallAlive = true, isTouched = false, isReady = false;

    private Map map;





    Game(){

        int delay = 10;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);

        // Player
        width = 60;
        height = 5;
        padX = (screen_width/2)-width;
        padY = screen_height-100;

        // Ball
        ball_width = 20;
        ball_height = 20;
        ballX = (screen_width/2)-(ball_width+20);
        ballY = screen_height-300;


        // Ball speed direction
        ballXdir = 3;
        ballYdir = 3;

        map = new  Map(col,row);
        timer.start();



    }

    private void PlayMusic(String input){

        try{
            // Read the Audio File
            File myAudio = new File(input);

            //  AudioSystem gets the audioStreaming data from the file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(myAudio);

            // Get the sound clip
            Clip clip =  AudioSystem.getClip();

            // Open the audio streamed clip
            clip.open(audioInputStream);

            // Start it
            clip.start();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static ImageIcon loadImage(String input){
        return new ImageIcon(input);

    }
    public void paint(Graphics g) {


        isStarted = true;
        g.setColor(Color.black);
        g.fillRect(0, 0, super.getWidth(), super.getHeight());

        // ball
        g.setColor(Color.white);
        g.fillOval(ballX, ballY, ball_width, ball_height);

        // Padel
        g.setColor(Color.ORANGE);
        g.drawRect(padX, padY, width, height);




        // If ball drops then game over
        if (!isBallAlive) {
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
            g.drawString("YOU LOST", (screen_width / 2) - 32 * 2, (screen_height - 200) - 32 * 4);
            PlayMusic("src\\sounds\\yelp.wav");
            timer.stop();
        }

        Rectangle ball = new Rectangle(ballX, ballY, ball_width, ball_height);
        Rectangle pad = new Rectangle(padX, padY, width, height);


        map.draw((Graphics2D) g);

        for(int col = 0; col < map.map.length; col++){
            for(int row = 0; row < map.map[0].length; row++){

                g.setColor(Color.cyan);
                g.fillRect(col*map.tileWidth +map.map[col][row], row*map.tileHeight + map.map[col][row], map.tileWidth, map.tileHeight);

                g.setColor(Color.black);
                g.drawRect(col*map.tileWidth +map.map[col][row], row*map.tileHeight + map.map[col][row], map.tileWidth, map.tileHeight);


                Rectangle rectangle = new Rectangle(col*map.tileWidth + map.map[col][row], row*map.tileHeight + map.map[col][row], map.tileWidth, map.tileHeight);
                Rectangle ballRect = new Rectangle(ballX, ballY, ball_width, ball_height);


                if(ballRect.intersects(rectangle)) {

                        ballYdir = -ballYdir;
                        tiles--;
                        map.ifCrashed(-100, col, row);
                }


            }
        }
        // Java code
        if (ball.intersects(pad)) {
            ballYdir = -ballYdir;
            isTouched = true;

            g.fillRect(padX, padY, width, height);

            PlayMusic("src\\sounds\\tap.wav");

        }
        // Collision ball and pad made by me
        //if((ballX) >= (padX-(ball_height-4)) && (ballX) <= (padX+width) && (ballY) >= (padY-ball_height) && (ballY-ball_height) <= (padY+height)){}
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
        if(ballY >= screen_height && ballY <= screen_height+1){
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