import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JPanel;

/**
 * This class contains the paintable objects such as the enemyList,
 * turret, and missile. It also keeps track of the 
 * 
 * @author Garrett Finley
 * @date 4/15/2022
 */
public class GamePanel extends JPanel {
	//Instance properties
	public static int totalScore;
	public static int highScore;
	private boolean isNextEnemyBig;
	private Turret turret;
	private ArrayList<Enemy> enemyList;
	private ArrayList<Missile> missileList;
	
	/**
	 * Default constructor for GamePanel
	 */
	public GamePanel() {
		totalScore = 0;
		isNextEnemyBig = true;
		turret = new Turret();
		turret.setBounds(370, 663, 60, 60);
		missileList = new ArrayList();
		enemyList = new ArrayList();
		enemyList.add(new BigEnemy());
		enemyList.add(new SmallEnemy());
		
		Scanner in = null;
		try {
			File highScoreData = new File("highscore.txt");
			in = new Scanner(highScoreData);
			highScore = in.nextInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
	}
	
	/**
	 * Paints the enemyList and missileList when called and also paints
	 * the background of the panel White.
	 */
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		
		//Paints the background black
		g.setColor(Color.black); 
		g.fillRect(0, 0, this.getWidth(), this.getHeight()); 
		
		//Paints the turret
		turret.paintComponent(g);
		
		//Paints the missiles shot from the turret
		for (int i = 0; i < missileList.size(); i++) {
			missileList.get(i).paintComponent(g);
		}
		
		//Paints the enemies
		for (int i = 0; i < enemyList.size(); i++) {
			if(enemyList.get(i) instanceof BigEnemy) {
				enemyList.get(i).paintComponentBig(g);
			} else {
				enemyList.get(i).paintComponentSmall(g);
			}
		}
	} 
	
	/**
	 * Method that allows you to set the bounds of the turret from another class
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void turretSetBounds(int x, int y, int width, int height) {
		turret.setBounds(x, y, width, height);
	}
	
	/**
	 * Getter method for the turret X value
	 * @return int
	 */
	public int getTurretX() {
		return turret.getX();
	}
	
	/**
	 * Getter method for the turret Y value
	 * @return Y
	 */
	public int getTurretY() {
		return  turret.getY();
	}
	
	/**
	 * Move method that will call the missile and enemy respective
	 * move methods in their own class
	 */
	public void move() {
		//Will move every missile in the missileList array list
		for (int i = 0; i < missileList.size(); i++) {
			missileList.get(i).move(12, 12, missileList, i);
		}
		
		//Will move every enemy in the enemyList array list
		for (int i = 0; i < enemyList.size(); i++) {
			if(enemyList.get(i) instanceof BigEnemy) {
				enemyList.get(i).move(enemyList.get(i).getWidth(), enemyList.get(i).getHeight());
			} else {
				enemyList.get(i).move(enemyList.get(i).getWidth(), enemyList.get(i).getHeight());
			}
		}
	}
	
	/**
	 * Method that adds the missile to the missile array list with the coordinates based on the current
	 * turret location
	 */
	public void addMissile() {
		Missile missile = new Missile();
		missile.setBounds(turret.getX() + 24, turret.getY(), 12, 12);
		missileList.add(missile);
	}

	/**
	 * Will add a new enemy to the enemy array list based on whether or not
	 * it is a big or small enemy
	 */
	public void addEnemy() {
		if (isNextEnemyBig) {
			BigEnemy bigEnemy = new BigEnemy();
			enemyList.add(bigEnemy);
			isNextEnemyBig = false;
		} else {
			SmallEnemy smallEnemy = new SmallEnemy();
			enemyList.add(smallEnemy);
			isNextEnemyBig = true;
		}
	}
	
	/**
	 * Getter method that will return the users total score
	 * @return int
	 */
	public int getTotalScore() {
		return totalScore;
	}
	
	/**
	 * Getter method that will return the game's highscore
	 * @return int
	 */
	public int getHighScore() {
		return highScore;
	}
	
	/**
	 * Method detects the collision of the missile and all the enemyList. This is done by
	 * drawing invisible rectangles around the enemyList and missileList, if they intersect, then 
	 * they collide.
	 */
	public void detectCollision() {
		// Create temporary rectangles for every enemy and missile on the screen currently       
		for(int i = 0; i < enemyList.size(); i++) {
			Rectangle enemyRec = enemyList.get(i).getBounds();
			for(int j = 0; j < missileList.size(); j++) {
				Rectangle missileRec = missileList.get(j).getBounds();
				if(missileRec.intersects(enemyRec)) {
					(enemyList.get(i)).processCollision(enemyList, i);
					missileList.remove(j); 
				}
			}
		}
	}

}
