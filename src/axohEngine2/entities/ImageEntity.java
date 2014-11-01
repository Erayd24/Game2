package axohEngine2.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;



public class ImageEntity extends BaseGameEntity {
	
	protected Image image;
	protected JFrame frame;
	protected AffineTransform at;
	protected Graphics2D g2d;
	protected int width;
	protected int height;
	public int scale;
	
	//Constructor
	public ImageEntity(JFrame frame) {
		this.frame = frame;
		setImage(null);
		setAlive(true);
	}
	
	public Image getImage() { return image; }
	
	public void setImage(Image image) {
		this.image = image;
		if(image != null) {
			width = image.getHeight(frame);
			height = image.getWidth(frame);
			double x = frame.getSize().width / 2 - width() / 2;
			double y = frame.getSize().height / 2 - height() / 2;
			at = AffineTransform.getTranslateInstance(x, y);
		}
	}
	
	public int width() {
		if(image != null) 
			return image.getWidth(frame);
		else
			return 0;
	}
	
	public int height() {
		if(image != null) 
			return image.getHeight(frame);
		else
			return 0;
	}
	
	public double getCenterX() {
		return getX() + width() / 2;
	}
	public double getCenterY() {
		return getY() + height() / 2;
	}
	
	public void setGraphics(Graphics2D g) {
		g2d = g;
	}
	
	public void load(String filename) {
		try{	
			System.out.print("Trying to load: " + filename + " ... ");
			image = ImageIO.read(getClass().getResource(filename));
			System.out.println(" succeeded!");
		} catch  (IOException e) {
			e.printStackTrace();
		  } catch (Exception e) {
			  System.err.println(" failed!");
		  }
	}
		
	//Bounding rectangle
	public Rectangle getBounds(int spriteSize) {
		Rectangle r;
		r = new Rectangle((int)getX(), (int)getY(), spriteSize, spriteSize);
		return r;
	}
}
