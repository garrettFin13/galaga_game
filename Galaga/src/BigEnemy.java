import java.util.ArrayList;

/**
 * This class extends the Enemy class and creates the Big enemy objects
 * as well as moves them across the screen.
 * 
 * @author Garrett Finley
 * @date 4/15/2022
 */
public class BigEnemy extends Enemy {
	//Instance property
	private int count;
	
	//Default Constructor
	public BigEnemy() {
		super(0,0,0,0,0,0);
		count = 0;
		setEnemyXSpeed(5);
		setEnemyYSpeed(1);
		
		//Spawns the enemies randomly at different heights
		int height = (int)(Math.random() * 3);
		int side = (int)(Math.random() * 3);
		
		if (side == 1) {
			side = 0;
		} else if (side == 2) {
			side = 635;
		}
		
		if (height == 1) {
			height = 40;
		} else if (height == 2) {
			height = 70;
		} else {
			height = 100;
		}
		setBounds(side, height, 150, 150);
	}
	
	/**
	 * Processes the collision of the missile and the enemy.
	 */
	@Override
	public void processCollision(ArrayList<Enemy> list, int enemy) {
		//The big enemy's health is 10 hits
		super.setBounds(super.getX()+10, super.getY()+10, super.getWidth()-20, super.getHeight()-20);
		repaint();
		if (count == 5) {
			if(list.get(enemy) instanceof BigEnemy) {
				//If big enemy a score of 200 will be added
				GamePanel.totalScore += 300;
			}
			//The enemy is then removed from the array once it is killed
			list.remove(enemy);
		}
		count++;
	}

	/**
	 * Mover method that moves the big enemy across the screen and adds boundaries so that it will not go off
	 * the screen.
	 */
	@Override
	public void move(int width, int height) {
		//Keeps the enemy from going of the screen left to right
		if(super.getX() < (0 - super.getEnemyXSpeed()) || (super.getX() + (super.getEnemyXSpeed())) > 635) {
			super.setEnemyXSpeed(super.getEnemyXSpeed() * -1);
		}
		//If the enemy reaches the bottom of the screen the game ends and the player loses
		if(super.getY() < (0 - super.getEnemyYSpeed()) || (super.getY() + (super.getEnemyYSpeed())) > 600) {
			Tester.setGameLose(true);
		}

		super.setBounds(super.getX() + (int)super.getEnemyXSpeed(), super.getY() + (int)super.getEnemyYSpeed(), width, height);
	}
}
