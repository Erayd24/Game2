package axohEngine2.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Sprite extends Object{
	
	private ImageEntity entity;
	protected Point pos;
	protected Point vel;
	protected double rotRate;
	protected int currentState;
	
	//Constructor
	Sprite(JFrame a, Graphics2D g2d) {
		entity = new ImageEntity(a);
		entity.setGraphics(g2d);
		entity.setAlive(false);
		pos = new Point(0, 0);
		vel = new Point(0, 0);
		rotRate = 0.0;
		currentState = 0;
	}
	
	//Load image file
	public void load(String filename) {
		entity.load(filename);
	}
	
	//Perform transformations
	public void transform() {
		entity.setX(pos.x);
		entity.setY(pos.y);
		entity.transform();
	}
	
	//Draw the image on screen
	public void draw() {
		entity.g2d.drawImage(entity.getImage(),entity.at, entity.frame);
	}
	
	//Bounding rectangle
	public void drawBounds(Color c) {
		entity.g2d.setColor(c);
		entity.g2d.draw(getBounds());
	}
	
	//Update a position based on a velocity
	public void updatePosition() {
		pos.x += vel.x;
		pos.y += vel.y;
	}
	
	//Methods for automatic rotation
	public double rotationRate() { return rotRate; }
	public void setRotationRate(double rate) { rotRate = rate; }
	
	public void updateRotation() {
		setFaceAngle(faceAngle() +rotRate);
		if(faceAngle() < 0)
			setFaceAngle(360 - rotRate);
		else if (faceAngle() > 360)
			setFaceAngle(rotRate);
	}
	
	//Sprite state variable - alive, dead, collided...
	public int state() { return currentState; }
	public void setState(int state) { currentState = state; }
	
	//Return a bounding rectangle
	public Rectangle getBounds() { return entity.getBounds(); }
	
	//Sprite position
	public Point position() { return pos; }
	public void setPosition(Point pos) { this.pos = pos; }
	
	//Sprite Movement velocity
	public Point velocity() { return vel; }
	public void setVelocity(Point vel) { this.vel = vel; }
	
	//Returns the center of the sprite as a Point value
	public Point center() {
		int x = (int)entity.getCenterX();
		int y = (int)entity.getCenterY();
		return(new Point(x, y));
	}
	
	//Selectively using sprites
	public boolean alive() { return entity.isAlive(); }
	public void setAlive(boolean alive) { entity.setAlive(alive); }
	
	//Face angle indicates the direction a sprite is facing
	public double faceAngle() { return entity.getFaceAngle(); }
	public void setFaceAngle(double angle) { entity.setFaceAngle(angle); }
	public void SetFaceAngle(float angle) { entity.setFaceAngle((double) angle); }
	public void setFaceAngle(int angle) { entity.setFaceAngle((double) angle); }
	
	//Move angle indicates the direction a sprite is moving
	public double moveAngle() { return entity.getMoveAngle(); }
	public void setMoveAngle(double angle) { entity.setMoveAngle(angle); }
	public void setMoveAngle(float angle) { entity.setMoveAngle((double) angle); }
	public void setMoveAngle(int angle) { entity.setMoveAngle((double) angle); }
	
	//Returns the source imagewidth and height
	public int imageWidth() { return entity.width(); }
	public int imageHeight() { return entity.height(); }
	
	//Check for collision with a rectangular shape
	public boolean collidesWith(Rectangle rect) { return (rect.intersects(getBounds())); }
	
	//Check for collision with another sprite
	public boolean collidesWith(Sprite sprite) { return (getBounds().intersects(sprite.getBounds())); }
	
	//Check for collision with a specific point
	public boolean collidesWith(Point point) { return (getBounds().contains(point.x, point.y)); }
	
	public JFrame frame() { return entity.frame; }
	public Graphics2D graphics() { return entity.g2d; }
	public Image image() { return entity.image; }
	public void setImage(Image image) {entity.setImage(image); }
	
}