package axohEngine2;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.ImageEntity;

public class Judgement extends Game {
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
	
	public static void main(String[] args) {
		new Judgement();
	}
	
	public Judgement() {
		super(60, SCREENWIDTH, SCREENHEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
	}
	
	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
	}
	
	public void start() {
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

	void gameStartUp() {		
	}

	void gameTimedUpdate() {		
	}

	void gameRefreshScreen() {		
	}

	void gameShutDown() {		
	}

	void gameKeyDown(int KeyCode) {		
	}

	void gameKeyUp(int KeyCode) {		
	}

	void gameMouseDown() {		
	}

	void gameMouseUp() {		
	}

	void gameMouseMove() {		
	}

	void spriteUpdate(AnimatedSprite sprite) {		
	}

	void spriteDraw(AnimatedSprite sprite) {		
	}

	void spriteDying(AnimatedSprite sprite) {		
	}

	void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2) {		
	}
}
