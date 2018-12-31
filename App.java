import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

interface varibles {
    int screen_width = 800;
    int screen_height = 600;
}
public class App extends JFrame implements varibles {

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
        ImageIcon img_icon = new ImageIcon("C:\\Users\\Honey Singh\\IdeaProjects\\Java_Course_Udamy\\src\\bricks-game.jpg");

        // Getting the image preview from the ImageIcon and setting it up
        frame.setIconImage(img_icon.getImage());

        // Setting Window resizable to false
        frame.setResizable(false);

    }
}


class Game extends JPanel implements KeyListener, ActionListener, varibles {

    // These are intilizers for the game so we could able to use it everywhere in the class
    private static Timer timer;

    // Ball
    private static int ballX, ballY, ballXdir, ballYdir, ball_width, ball_height;

    // Player
    private static int padX, padY, width, height, moveSpeed = 30;


    // Image
    private static ArrayList<Integer> image_x = new ArrayList<>(), image_y = new ArrayList<>();

    // Game
    private boolean isStarted = false, isBallAlive = true, isTouched = false, isReady = false;

    private static  String img1 = "C:\\Users\\Honey Singh\\IdeaProjects\\Java_Course_Udamy\\src\\images\\brick-1.png";
    private static  String img2 = "C:\\Users\\Honey Singh\\IdeaProjects\\Java_Course_Udamy\\src\\images\\brick-2_0.png";
    private static  String img3 = "C:\\Users\\Honey Singh\\IdeaProjects\\Java_Course_Udamy\\src\\images\\brick-1.png";

    private static  int[]   img1_size = {loadImage(img1).getIconWidth(),loadImage(img1).getIconHeight() };
    private static  int[]  img2_size = {loadImage(img2).getIconWidth(),loadImage(img2).getIconHeight() };
    private static  int[]  img3_size = {loadImage(img3).getIconWidth(),loadImage(img3).getIconHeight() };




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


        // Drawing bricks pattern across the screen
        for(int x = 40; x <= screen_height*1.2;x+=(screen_width/40)) {
            for(int y = 40; y <= 180; y+=60){
                image_x.add(x);
                image_y.add(y);
                if( x== screen_height*1.2 && y <= 180){
                   isReady = true;
                }
            }
        }



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
        ImageIcon img = new ImageIcon(input);
        return img;

    }
    public void paint(Graphics g){

        isStarted = true;
        g.setColor(Color.black);
        g.fillRect(0,0, super.getWidth(), super.getHeight());

        // ball
        g.setColor(Color.white);
        g.fillOval(ballX, ballY, ball_width, ball_height);

        // Padel
        g.setColor(Color.ORANGE);
        g.fillOval(padX, padY, width, height);

        // if all the numbers has been added then draw the images
        if(isReady){



            for(int x: image_x) {
                for (int y : image_y) {
                    if (x < 300) {
                        g.drawImage(loadImage(img1).getImage(), x, y, null);
                    }

                    if (x < 500 && x > 300) {
                        g.drawImage(loadImage(img2).getImage(), x, y, null);

                    }

                    if (x > 500) {
                        g.drawImage(loadImage(img3).getImage(), x, y, null);
                    }

                    // Ball Collision with tile
                    if((ballX) >= (y) && (ballX) <= (y+width) && (ballY) >= (x-ball_height) && (ballY-ball_height) <= (x+height)){
                        ballYdir = -ballYdir;
                        System.out.println("TOUCHED THE TILE");
                    }

                }
            }
        }

        // If ball drops then game over
        if(!isBallAlive){
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
            g.drawString("YOU LOST", (screen_width/2)-32*2, (screen_height-200)-32*4);
            PlayMusic("C:\\Users\\Honey Singh\\IdeaProjects\\Java_Course_Udamy\\src\\sounds\\oops.wav");
            timer.stop();
        }
        g.dispose();

    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
//        ballY = 190;
//        ballX = 40;

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


        // Collision ball and pad
        if((ballX) >= (padX-(ball_height-4)) && (ballX) <= (padX+width) && (ballY) >= (padY-ball_height) && (ballY-ball_height) <= (padY+height)){
            ballYdir = -ballYdir;
            isTouched = true;
            PlayMusic("C:\\Users\\Honey Singh\\IdeaProjects\\Java_Course_Udamy\\src\\sounds\\yelp.wav");
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