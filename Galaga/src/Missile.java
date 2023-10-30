import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * This class creates the missile objects and both
 * paints them on the screen and moves them accordingly
 * 
 * @author Garrett Finley
 * @date 4/15/2022
 */
public class Missile extends JComponent {
	//Instance Properties
	private int missileSpeed;
	
	//Default Constructor for Missile
	public Missile() {
		missileSpeed = -23;
	}
	
	//Painter method that will paint the missile on screen
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
	}
	
	/**
	 * Method that will move the missile across the screen 
	 * @param width
	 * @param height
	 * @param list
	 * @param missile
	 */
	public void move(int width, int height, ArrayList<Missile> list, int missile) {		
		Missile mover = list.get(missile);
		mover.setBounds(mover.getX(), mover.getY() + missileSpeed, width, height);
	}
}
