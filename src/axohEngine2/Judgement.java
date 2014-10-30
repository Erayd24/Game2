package axohEngine2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.SpriteSheet;
import axohEngine2.map.Map;
import axohEngine2.map.Tile;

public class Judgement extends Game {
	private static final long serialVersionUID = 1L;
	
	static int SCREENWIDTH = 1600;
	static int SCREENHEIGHT = 900;
	static int CENTERX = SCREENWIDTH / 2;
	static int CENTERY = SCREENHEIGHT / 2;
	
	//Sprite states
	static int SPRITE_NORMAL = 0;
	static int SPRITE_COLLIDED = 1;
	
	boolean showBounds = true;
	boolean collisionTesting = true;
	boolean runOnce = true;
	long collisionTimer = 0;
	
	private int scale;
	private int playerX;
	private int playerY;
	private int playerSpeed;
	
	Random random = new Random();
	//Collections collection;
	
	//ImageEntity background;
	AnimatedSprite player1;
	
	SpriteSheet sheet;
	SpriteSheet player;
	
	Map map;
	Tile gt;
	Tile ft;
	Tile bt;
			
	int frameCount = 0, frameRate = 0;
	long startTime = System.currentTimeMillis();
	
	boolean keyLeft, keyRight, keyUp, keyDown, keyInventory, keyAction, keyBack, keyEnter;
	
	//Load Sound effects
	public Judgement() {
		super(60, SCREENWIDTH, SCREENHEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	void gameStartUp() {
		scale = 4;
		playerX = 0;
		playerY = 0;
		playerSpeed = 5;
		//Initialize spriteSheets
		sheet = new SpriteSheet("/textures/environments/environments1.png", 16, 16, 16);
		player = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32);

		player1 = new AnimatedSprite(this, graphics(), "player");
		ft = new Tile(this, graphics(), "flower", sheet, 1);
		gt = new Tile(this, graphics(), "grass", sheet, 0);
		bt = new Tile(this, graphics(), "bricks", sheet, 16, true);
		//ft.loadAnim(2, 20);
		
		//background = new ImageEntity(this);
		//background.load("/field.png");
		
		//Player
		player1.setAnimSprite(player, 40);
		player1.loadAnim(4, 20);
				
		sprites().add(player1);
		sprites().add(ft);
		sprites().add(bt);
		sprites().add(gt);
		
		//collection = new Collections();
		//collection.Initialize();
		
		Tile[] mapTiles = {gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, gt, gt, gt, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, gt, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt,
						   gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt,
						   gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, bt, bt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt,
						   gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt,
						   gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt,
						   gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt};
		
		map = new Map(mapTiles, scale, 30, 30);
		
		requestFocus();
		start();
	}

	void gameTimedUpdate() {
		checkInput();
	}
	
	void gameRefreshScreen() {		
		Graphics2D g2d = graphics();
		g2d.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
				
		map.render(playerX, playerY);
		player1.render(graphics(), this, SCREENWIDTH / 2, SCREENHEIGHT / 2, 32, 32, scale);
		bt.drawBounds(Color.RED);
		//g2d.setFont(new Font("Arial", Font.PLAIN, 48));
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
		if(spr2.spriteType() == "wall" && spr1.name == "player") {
			//if(keyLeft) playerX = playerX - 1 - playerSpeed;
			//if(keyRight) playerX = playerX + 1 + playerSpeed;
			//if(keyUp) playerY = playerY - 1 - playerSpeed;
			//if(keyDown) playerY = playerY + 1 + playerSpeed;
		}
		if(spr1.spriteType() == "enemy" || spr2.spriteType() == "enemy") {
		}
		if(spr1.spriteType() == "npc" || spr2.spriteType() == "npc") {
		}
	}
	
	//Main
	public static void main(String[] args) {
		new Judgement();
	}
	
	/**********************************************************
	 * 
	 * 
	 *             Key events - Mouse events
	 *                            
	 ***********************************************************/
	public void checkInput() {
		if(keyLeft) playerX = playerX + 1 + playerSpeed;
		if(keyRight) playerX = playerX - 1 - playerSpeed;
		if(keyUp) playerY = playerY + 1 + playerSpeed;
		if(keyDown) playerY = playerY - 1 - playerSpeed;
	}
	
	void gameKeyDown(int keyCode) {
		switch(keyCode) {
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
        case KeyEvent.VK_I:
        	keyInventory = true;
        	break;
        case KeyEvent.VK_E:
        	keyAction = true;
        	break;
        case KeyEvent.VK_ENTER:
        	keyEnter = true;
        	break;
        case KeyEvent.VK_BACK_SPACE:
        	keyBack = true;
        	break;
        }
	}

	void gameKeyUp(int keyCode) {
		switch(keyCode) {
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
        case KeyEvent.VK_I:
	    	keyInventory = false;
	    	break;
	    case KeyEvent.VK_E:
	    	keyAction = false;
	    	break;
	    case KeyEvent.VK_ENTER:
	    	keyEnter = false;
	    	break;
	    case KeyEvent.VK_BACK_SPACE:
	    	keyBack = false;
	    	break;
		}
	}

	void gameMouseDown() {		
	}

	void gameMouseUp() {		
	}

	void gameMouseMove() {
	}
}