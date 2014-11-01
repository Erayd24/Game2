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
	
	//player variables and scale
	private int scale;
	private int playerX;
	private int playerY;
	private int playerSpeed;
	private boolean upCollision = false;
	private boolean downCollision = false;
	private boolean leftCollision = false;
	private boolean rightCollision = false;
	
	Random random = new Random();
	
	SpriteSheet sheet;
	SpriteSheet player;
	AnimatedSprite player1;
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
		sheet = new SpriteSheet("/textures/environments/environments1.png", 16, 16, 16, scale);
		player = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32, scale);

		player1 = new AnimatedSprite(this, graphics(), player, 40, "player");
		ft = new Tile(this, graphics(), "flower", sheet, 1);
		gt = new Tile(this, graphics(), "grass", sheet, 0);
		bt = new Tile(this, graphics(), "bricks", sheet, 16, true);
		
		//Player
		player1.loadAnim(4, 20);
		sprites().add(player1);
		
		//Map generating
		Tile[] mapTiles = {bt, gt, gt, gt, bt, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft, gt, gt, gt, gt, ft,
						   gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
						   bt, ft, ft, ft, gt, gt, gt, gt, gt, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt, gt, ft, ft, ft, gt,
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
						   gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, bt, bt, bt, bt, bt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt, gt,
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
		
		map = new Map(mapTiles, 30, 30);
		for(int i = 0; i < 30 * 30; i++){
			tiles().add(map.accessTile(i));
		}
		map.accessTile(5).loadAnim(2, 30);
		
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
		player1.render(this, graphics(), SCREENWIDTH / 2, SCREENHEIGHT / 2);
		
		map.accessTile(0).drawTileBounds(Color.RED);
		player1.drawBounds(Color.BLUE);
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
		if(spr2.spriteType() == "wall" && spr1._name == "player") {
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
	
	void tileCollision(AnimatedSprite spr, Tile tile) {
		upCollision = true;
	}
	
	public void movePlayer(int xa, int ya) {
		if(!leftCollision && xa > 0) playerX += xa; //left
		if(!rightCollision && xa < 0) playerX += xa; //right
		if(!upCollision && ya > 0) playerY += ya; //up
		if(!downCollision && ya < 0) playerY += ya; //down
		
		System.out.println("up: " + upCollision + "          down: " + downCollision + " left: " + leftCollision + " right: " + rightCollision);
		
		upCollision = false;
		downCollision = false;
		leftCollision = false;
		rightCollision = false;
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
		int xa = 0;
		int ya = 0;
		if(keyLeft) xa = xa + 1 + playerSpeed;
		if(keyRight) xa = xa - 1 - playerSpeed;
		if(keyUp) ya = ya + 1 + playerSpeed;
		if(keyDown) ya = ya - 1 - playerSpeed;
		movePlayer(xa, ya);
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