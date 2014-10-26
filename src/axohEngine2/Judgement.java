package axohEngine2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.ImageEntity;

public class Judgement extends Game {
	private static final long serialVersionUID = 1L;
	
	static int SCREENWIDTH = 1920;
	static int SCREENHEIGHT = 1080;
	static int CENTERX = SCREENWIDTH / 2;
	static int CENTERY = SCREENHEIGHT / 2;
	JFrame frame;
	Color back;
	
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
		super(60, SCREENWIDTH,SCREENHEIGHT);
		//Setting up JFrame
		frame = new JFrame();
		back = new Color(1);
		frame.setBackground(back);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
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
		background = new ImageEntity(this);
		//background.load("GameScreen-Galaxy.png");
		//background[0] = new ImageEntity(this);
	}

	void gameTimedUpdate() {		
	}

	void gameRefreshScreen() {		
		Graphics2D g2d = graphics();

        //draw the background
        g2d.drawImage(background.getImage(),0,0,SCREENWIDTH-1,SCREENHEIGHT-1,this);
        
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
	
	public static void main(String[] args) {
		new Judgement();
	}
}

