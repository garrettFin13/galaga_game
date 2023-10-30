import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * This class creates the turret object and paints it
 * on the screen.
 * 
 * @author Garrett Finley
 * @date 4/15/2022
 */
public class Turret extends JComponent {
	//Instance Properties
	private BufferedImage turret;
	private Image scaledTurret;
	
	//Default Constructor
	public Turret() {		
		//Loads in an image for the turret
		try {
			turret = ImageIO.read(new File("galaga_lib/galagaFighter.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Painter method that will paint the image loaded in on the screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		scaledTurret = turret.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		g.drawImage(scaledTurret, super.getX(), super.getY(), null);
	}

}
