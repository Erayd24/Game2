package axohEngine2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.ImageEntity;
import axohEngine2.input.Keyboard;

public class Judgement extends Game {
	private static final long serialVersionUID = 1L;
	
	static int SCREENWIDTH = 1920;
	static int SCREENHEIGHT = 1080;
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
	Keyboard input;
	
	//Keypresses
	boolean keyDown, keyUp, keyLeft, keyRight;
	
	int frameCount = 0, frameRate = 0;
	long startTime = System.currentTimeMillis();
	
	//Load Sound effects
	public Judgement() {
		super(60, SCREENWIDTH,SCREENHEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	

	void gameStartUp() {
		background = new ImageEntity(this);
		background.load("/field.png");
		
		
		start();
	}

	void gameTimedUpdate() {
		checkInput();
	}

	void gameRefreshScreen() {		
		Graphics2D g2d = graphics();
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 48));
        g2d.drawImage(background.getImage(), 0, 0, SCREENWIDTH-1, SCREENHEIGHT-1, this);
        g2d.drawString("Hellooooooooo!", 200, 200);
	}

	void gameShutDown() {		
	}

	void spriteUpdate(AnimatedSprite sprite) {		
	}

	void spriteDraw(AnimatedSprite sprite) {		
	}

	void spriteDying(AnimatedSprite sprite) {		
	}

	void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2) {		
	}
	
	//Key events
	public void checkInput() {
		input = input();
		
		if(keyLeft) {
			if(input.left) System.out.println("left");
		}
		if(keyRight) {
			
		}
		if(keyUp) {
			
		}
		if(keyDown) {
			
		}
	}
	public void keyTyped(KeyEvent e) { }
	
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
	
	
	//Main
	public static void main(String[] args) {
		new Judgement();
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}