import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * This class creates the enemy object that then paints both the small and big enemies
 * on the screen
 * 
 * @author Garrett Finley
 * @date 4/15/2022
 */
public abstract class Enemy extends JComponent {
	//Instance Properties
	private double enemyXSpeed;
	private double enemyYSpeed;
	private BufferedImage bigEnemy;
	private BufferedImage smallEnemy;
	private Image scaledBigImage;
	private Image scaledSmallImage;
	
	//Workhorse Constructor
	public Enemy(double enemyXCoord, double enemyYCoord, int enemyHeight,
			int enemyWidth, double enemyXSpeed, double enemyYSpeed) {
		enemyXCoord = 0;
		enemyYCoord = 0;
		enemyHeight = 0;
		enemyWidth = 0;
		enemyXSpeed = 0.0;
		enemyYSpeed = 0.0;
		
		//Loads in an image to be used for the big enemy
		try {
			bigEnemy = ImageIO.read(new File("galaga_lib/galagaEnemyBig.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Loads in an image to be used for the small enemy
		try {
			smallEnemy = ImageIO.read(new File("galaga_lib/galagaEnemySmall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Requires this method to be used in all children classes
	 * @param list
	 * @param enemy
	 */
	public abstract void processCollision(ArrayList<Enemy> list, int enemy);
	
	/**
	 * Required this method to be used in all children classes
	 * @param width
	 * @param height
	 */
	public abstract void move(int width, int height);
	
	/**
	 * Paints the big enemy on the screen
	 * @param g
	 */
	public void paintComponentBig(Graphics g) {
		super.paintComponent(g);
		scaledBigImage = bigEnemy.getScaledInstance(super.getWidth(), super.getHeight(), Image.SCALE_SMOOTH);
		g.drawImage(scaledBigImage, super.getX(), super.getY(), null);
	}
	
	/**
	 * Paints the small enemy on the screen
	 * @param g
	 */
	public void paintComponentSmall(Graphics g) {
		super.paintComponent(g);
		scaledSmallImage = smallEnemy.getScaledInstance(super.getWidth(), super.getHeight(), Image.SCALE_SMOOTH);
		g.drawImage(scaledSmallImage, super.getX(), super.getY(), null);
	}
	
	/**
	 * Getter method for the enemies X speed
	 * @return double
	 */
	public double getEnemyXSpeed() {
		return enemyXSpeed;
	}

	/**
	 * Setter method for the enemies X speed
	 * @param enemySpeed
	 */
	public void setEnemyXSpeed(double enemySpeed) {
		this.enemyXSpeed = enemySpeed;
	}
	
	/**
	 * Getter method for the enemies Y speed
	 * @return double
	 */
	public double getEnemyYSpeed() {
		return enemyYSpeed;
	}

	/**
	 * Setter method for the enemies Y speed
	 * @param enemySpeed
	 */
	public void setEnemyYSpeed(double enemySpeed) {
		this.enemyYSpeed = enemySpeed;
	}

}
