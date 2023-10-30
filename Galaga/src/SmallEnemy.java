import java.util.ArrayList;

/**
 * This class extends the enemy class and creates the small enemy object
 * as well as moves it across the screen.
 * 
 * @author Garrett Finley
 * @date 4/15/2022
 */
public class SmallEnemy extends Enemy {
	//Instance property
	private int count;
	
	//Default Constructor
	public SmallEnemy() {
		super(0,0,0,0,0,0);
		count = 0;
		setEnemyXSpeed(8);
		setEnemyYSpeed(1);
		
		//Spawns the enemies randomly at different heights
		int height = (int)(Math.random() * 3);
		int side = (int)(Math.random() * 3);
		
		if (side == 1) {
			side = 0;
		} else if (side == 2) {
			side = 734;
		}
		
		if (height == 1) {
			height = 300;
		} else if (height == 2) {
			height = 350;
		} else {
			height = 325;
		}
		setBounds(side, height, 70, 70);
	}

	/**
	 * Processes the collision of the missile and the enemy.
	 */
	@Override
	public void processCollision(ArrayList<Enemy> list, int enemy) {
		//The health of the small enemy is 5
		super.setBounds(super.getX()+10, super.getY()+10, super.getWidth()-20, super.getHeight()-20);
		repaint();
		if (count == 2) {
			if(list.get(enemy) instanceof SmallEnemy) {
				GamePanel.totalScore += 100;
			}
			list.remove(enemy);
		}
		count++;
	}

	/**
	 * Mover method that moves the big enemy across the screen and adds boundaries so that it will not go off
	 * the screen.
	 */
	public void move(int width, int height) {
		//Keeps the enemy from going of the screen left to right
		if(super.getX() < (0 - super.getEnemyXSpeed()) || (super.getX() + (super.getEnemyXSpeed())) > 743) {
			super.setEnemyXSpeed(super.getEnemyXSpeed() * -1);
		}
		//If the enemy reaches the bottom of the screen the game ends and the player loses
		if(super.getY() < (0 - super.getEnemyYSpeed()) || (super.getY() + (super.getEnemyYSpeed())) > 680) {
			Tester.setGameLose(true);
		}

		super.setBounds(super.getX() + (int)super.getEnemyXSpeed(), super.getY() + (int)super.getEnemyYSpeed(), width, height);
	}
}
