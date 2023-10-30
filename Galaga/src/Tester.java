import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * 
 * To move use WASD and to shoot use K
 * (You unfortunately cannot move and shoot at the same
 *  time I didn't not have time to figure that out)
 * 
 * @author Garrett Finley
 * @date 4/15/2022
 */
public class Tester extends JFrame implements KeyListener{
	//Instance Variables
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 800;
	private int timer;
	private static boolean gameLose;
	private JLabel scoreLabel;
	private JFrame endGamePane;
	private GamePanel panel;
	private int velX = 0;
	private int velY = 0;
	
	
	/**
	 * Default constructor to control the game.
	 */
	public Tester() {
		//Initialize instance properties
		timer = 0;
		gameLose = false;
		scoreLabel = new JLabel();
		panel = new GamePanel();
		endGamePane = new JFrame();
		
		// Setup the initial JFrame elements
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		setTitle("Galaga");
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setResizable(false);
		setVisible(true);
		centerFrame(this);
	}
	
	/**
	 * This method is called to start the video game which then
	 * calls the infinite game loop for the game.
	 */
	public void start() {
		gameLoop();
	}

	/**
	 * Method contains the game loop to move enemies, manage score,
	 * and check if the game is finished.
	 */
	public void gameLoop() {
		//Display the panel on the screen
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.add(panel);
		scoreLabel.setText("HighScore: " + String.valueOf(panel.getHighScore()) + "   Score: 0");
		container.add(scoreLabel, BorderLayout.NORTH);
		//Actual Game loop
		while(true) {
			//If statement that will decide if the player won or lost the game
			if (gameLose == false) { 
				pauseGame();
				setTimer();
				panel.detectCollision();
				//Randomly spawns in an enemy if the random number equals 26
				if (timer == 26) {
					panel.addEnemy();
				}
				scoreLabel.setText("High Score: " + String.valueOf(panel.getHighScore()) + "   Score: " + String.valueOf(panel.getTotalScore()));
				panel.move();
				panel.repaint();
			} else {
				//If the player reaches 2000 score a pop up will appear saying they won
				if (gameLose == true) {
					//If an enemy reaches the bottom of the screen a pop up will appear saying
					//they lost
					JOptionPane.showMessageDialog(endGamePane, "You Lose!\nYour Score: " + String.valueOf(panel.getTotalScore()));
				}
				
				if (panel.getTotalScore() > panel.getHighScore()) {
					try {
						FileWriter pw = new FileWriter("highscore.txt");
						pw.append(String.valueOf(panel.getTotalScore()));
						pw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			}
		}  
		//After the user closes the end game pop up the game will restart in a new window
		if(endGamePane.isShowing() == false) {
			setVisible(false);
			Tester main = new Tester();
			main.start();
		}
	}

	/**
	 * Pauses the thread for 30ms to control the 
	 * speed of the animations.
	 */
	public void pauseGame() {
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method centers the frame in the middle of the screen.
	 * 
	 * @param frame to center with respect to the users screen dimensions.
	 */
	public void centerFrame(JFrame frame) {    
		int width = frame.getWidth();
		int height = frame.getHeight();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center = ge.getCenterPoint();

		int xPosition = center.x - width/2, yPosition = center.y - height/2;
		frame.setBounds(xPosition, yPosition, width, height);
		frame.validate();
	}

	/**
	 * Randomly assign a value to determine how soon a new Enemy should
	 * be created.
	 */
	public void setTimer() {
		if (panel.getTotalScore() <= 1000) {
			timer = (int)(Math.random() * 90);
		} else if (panel.getTotalScore() > 1000 && panel.getTotalScore() <= 2000) {
			timer = (int)(Math.random() * 83);
		} else if (panel.getTotalScore() > 2000 && panel.getTotalScore() <= 3000) {
			timer = (int)(Math.random() * 75);
		} else if (panel.getTotalScore() > 3000) {
			timer = (int)(Math.random() * 60);
		}
	}

	/**
	 * Setter for the boolean gameLose
	 * @param newGameLose
	 */
	public static void setGameLose(boolean newGameLose) {
		gameLose = newGameLose;
	}

	/**
	 * The main method to execute the program.
	 * 
	 * @param args Any inputs to the program when it starts.
	 */
	public static void main(String[] args) {
		Tester main = new Tester();
		main.start();
	}
	
	/**
	 * This method acts as the boundaries for the turret so the user cannot move
	 * it off of the screen.
	 * @param e
	 */
	public void actionPerformed(KeyEvent e) {
		if (panel.getTurretX() < 0) {
			velX = 0;
			panel.turretSetBounds(0, panel.getTurretY(), 60, 60);
		} else if (panel.getTurretX() > 720) {
			velX = 0;
			panel.turretSetBounds(720, panel.getTurretY(), 60, 60);
		} else if (panel.getTurretY() > 680) {
			velY = 0;
			panel.turretSetBounds(panel.getTurretX(), 680, 60, 60);
		} else if (panel.getTurretY() < 0) {
			velY = 0;
			panel.turretSetBounds(panel.getTurretX(), 0, 60, 60);
		}
		
		panel.turretSetBounds(panel.getTurretX() + velX, panel.getTurretY() + velY, 60, 60);
		panel.repaint();
	}

	/**
	 * This method allows the user to control the turret using the WASD keys
	 * while the keys are held down.
	 */
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		velX = 0;
		velY = 0;
		
		if(c == KeyEvent.VK_A) {
			velX = -10;
			actionPerformed(e);
		} else if (c == KeyEvent.VK_D) {
			velX = 10;
			actionPerformed(e);
		}
	}


	/**
	 * The method give function to the letter K on the keyboard which every time
	 * the key is released will shoot a missile out of the turret
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();
		
		if (c == KeyEvent.VK_K) {
			panel.addMissile();
		}
	}
	
	/**
	 * Empty method so the class will compile
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

}
