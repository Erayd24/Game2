package axohEngine2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.entities.ImageEntity;

public class Judgement extends JFrame implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	
	static int SCREENWIDTH = 800;
	static int SCREENHEIGHT = 600;
	static int CENTERX = SCREENWIDTH / 2;
	static int CENTERY = SCREENHEIGHT / 2;
	
	//Sprite states
	static int SPRITE_NORMAL = 0;
	static int SPRITE_COLLIDED = 1;
	
	boolean showBounds = true;
	boolean collisionTesting = true;
	long collisionTimer = 0;
		
	Random random = new Random();
	ImageEntity background;
	
	//Keypresses
	boolean keyDown, keyUp, keyLeft, keyRight;
	
	int frameCount = 0, frameRate = 0;
	long startTime = System.currentTimeMillis();
	
	//Load Sound effects
	
	
	public Judgement() {
		backBuffer = new BufferedImage(SCREENWIDTH, SCREENHEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
		
		background = new ImageEntity(this);
		
		addKeyListener(this);
	}
	
	public void update(Graphics g) {
		//Framerate calculator
		
		
		g2d.drawImage(background.getImage(), 0, 0, SCREENWIDTH - 1, SCREENHEIGHT - 1, this);
		
		//Status information
		g2d.setColor(Color.WHITE);
		g2d.drawString("FPS: " + frameRate, 5, 10);
		
		if(showBounds) {
			g2d.setColor(Color.GREEN);
			g2d.drawString("Bounding Boxes", SCREENWIDTH - 150, 10);
		}
		
		if(collisionTesting) {
			g2d.setColor(Color.GREEN);
			g2d.drawString("COLLISSION TESTING", SCREENWIDTH - 150, 25);
		}
		
		paint(g);
	}

	public void paint(Graphics g) {
		g.drawImage(backBuffer, 0, 0, this);
	}
	
	public void run() {
		
			
	}
	
	public void start() {
		
	}
	
	
	
	private void gameUpdate() {
		checkInput();
		if(collisionTesting) checkCollisions();
	}
	
	public void checkCollisions() {
		
	}
	
	public void checkInput() {
		if(keyLeft) {
			
		}
		if(keyRight) {
			
		}
		if(keyUp) {
			
		}
		if(keyDown) {
			
		}
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			keyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = true;
			break;
		case KeyEvent.VK_UP:
			keyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			keyDown = true;
			break;
		case KeyEvent.VK_B:
			showBounds = !showBounds;
			break;
		case KeyEvent.VK_C:
			collisionTesting = !collisionTesting;
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				keyLeft = false;
				break;
			case KeyEvent.VK_RIGHT:
				keyRight = false;
				break;
			case KeyEvent.VK_UP:
				keyUp = false;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = false;
				break;
		}
	}
	
	public void keyTyped(KeyEvent e) { }
	
}
