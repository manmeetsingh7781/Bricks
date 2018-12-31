
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
    private static int padX, padY, width, height, moveSpeed = 30, counter = 0, opacity = 35;


    // Image

    private static ArrayList<Integer> image_x = new ArrayList<Integer>(), image_y = new ArrayList<Integer>();

    // Game
    private boolean isStarted = false, isBallAlive = true, isTouched = false, isReady = false;

    private static  String img1 = "src\\main\\java\\images\\brick-1.png";
    private static  String img2 = "src\\main\\java\\images\\brick-2_0.png";
    private static  String img3 = img1;

    private static  int[]   img1_size = {loadImage(img1).getIconWidth(),loadImage(img1).getIconHeight() };
    private static  int[]  img2_size = {loadImage(img2).getIconWidth(),loadImage(img2).getIconHeight() };
    private static  int[]  img3_size = {loadImage(img3).getIconWidth(),loadImage(img3).getIconHeight() };

    private static Image loaded_img1 = loadImage(img1).getImage();
    private static Image loaded_img2 = loadImage(img2).getImage();
    private static Image loaded_img3 = loadImage(img3).getImage();





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

        drawBricks();


        timer.start();
    }


    private void drawBricks(){
        // Drawing bricks pattern across the screen
        for(int x = 1; x <= 31;x++) {
            for(int y = 1; y <= 3;y++) {
                image_x.add(x * 45);
                image_y.add(y * 40);
                isReady = true;

            }

        }





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



        Tiles((Graphics2D) g);
        // If ball drops then game over
        if(!isBallAlive){
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
            g.drawString("YOU LOST", (screen_width/2)-32*2, (screen_height-200)-32*4);
            PlayMusic("src\\main\\java\\sounds\\oops.wav");
            timer.stop();
        }
        g.dispose();

        // Collision ball and pad
        if((ballX) >= (padX-(ball_height-4)) && (ballX) <= (padX+width) && (ballY) >= (padY-ball_height) && (ballY-ball_height) <= (padY+height)){
            ballYdir = -ballYdir;
            isTouched = true;

            g.fillRect(padX, padY, width, height);

            PlayMusic("src\\main\\java\\sounds\\yelp.wav");
        }



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

    private void Tiles(Graphics2D g) {
        // Ball and Tile Collision
        for(int x = 0; x< image_x.size(); x++) {
            for(int y = 0; y < image_y.size();y++){


            }
        }
        // if all the numbers has been added then draw the images
        if (isReady) {

            for (int x : image_x) {
                for (int y : image_y) {

                    if ((ballX) >= (x - img1_size[0]) && (ballX) <= (x  + img1_size[0]) && (ballY) >= (y  - img1_size[0]) && (ballY - height) <= (y  + img1_size[1])) {

                        g.fillRect(x, y, img1_size[0], img1_size[1]);
                        ballYdir = -ballYdir;
                        System.out.println("TAPPED TILE " + x + " " + y);

                    }
















                    if (x < 300) {
                        //g.drawImage(loaded_img1, x, y, null);
                        g.setColor(Color.cyan);
                        g.drawRect(x, y, img1_size[0], img1_size[1]);

                    }

                    if (x < 500 && x > 300) {
                        //g.drawImage(loaded_img2, x, y, null);
                        g.setColor(Color.red);
                        g.drawRect(x, y, img1_size[0], img1_size[1]);


                    }

                    if (x > 500) {
                        //g.drawImage(loaded_img3, x, y, null);
                        g.setColor(Color.orange);
                        g.drawRect(x, y, img1_size[0], img1_size[1]);
                    }
                }

            }

        }
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
