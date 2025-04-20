import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    //Images Background Class
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //FlappyBird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;

    //class to hold the Bird values
    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;
        Bird (Image img){
            this.img = img;
        }
    }
    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }
    }

    //game logic
    Bird bird;
    int velocityX = -4; //moves pipe to the left speed
    int velocityY = 0; //basically defines the movement of the bird
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;

    //Constructor
    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.BLUE);
        setFocusable(true); //Takes in the key events
        addKeyListener(this);

        //loading the images     
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg= new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);

        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }

        });
        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
        
        // Add KeyListener for restart functionality
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
                    restartGame();
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    velocityY = -9;
                }
            }
        });
    }

    public void placePipes(){
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    //invokes JPanel
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("Game Over!", 100, 200);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", 100, 240);
            g.drawString("Score: " + (int)score, 100, 280);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Score: " + (int)score, 20, 30);
        }
    }
    
    public void draw(Graphics g){
        //background
        g.drawImage(backgroundImg, 0, 0, null);

        //bird
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);
        
        //pipes
        for (int i = 0; i<pipes.size(); i++){
            Pipe pipe = pipes.get (i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
    }
    
    public void move(){
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipes movement
        for (int i = 0; i <pipes.size(); i ++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
            if (!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5; // as there are 2 pipes
            }

            if(collision(bird, pipe)){
                gameOver = true;
            }
        }
        if(bird.y > boardHeight){
            gameOver = true;
        }
    }
    
    public void restartGame() {
        // Reset bird position and velocity
        bird.y = birdY;
        velocityY = 0;
        
        // Clear existing pipes
        pipes.clear();
        
        // Reset score
        score = 0;
        
        // Reset game state
        gameOver = false;
        
        // Restart timers
        placePipesTimer.start();
        gameLoop.start();
    }
    
    //Collision with pipes 
    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&
        a.x + a.width > b.x &&
        a.y < b.y + b.height &&
        a.y + a.height >b.y;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
        }
    } 
    
    @Override
    public void keyTyped(KeyEvent e) {}
       
    @Override
    public void keyReleased(KeyEvent e) {}
}