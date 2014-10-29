package axohEngine2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.ImageEntity;
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
	long collisionTimer = 0;
	
	private int scale;
	private int xOffset = 0;
	private int yOffset = 0;
	private int playerSpeed = 5;
	
	Random random = new Random();
	//Collections collection;
	
	ImageEntity background;
	AnimatedSprite player1;
	
	SpriteSheet sheet;
	SpriteSheet player;
	
	Map map;
	Tile grassTile;
	Tile flowerTile;
			
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
		//Initialize spriteSheets
		sheet = new SpriteSheet("/textures/environments/environments1.png", 16, 16, 16);
		player = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32);

		player1 = new AnimatedSprite(this, graphics());
		flowerTile = new Tile(this, graphics(), sheet, 1);
		grassTile = new Tile(this, graphics(), sheet, 0);
		flowerTile.loadAnim(2, 20);
		
		background = new ImageEntity(this);
		background.load("/field.png");
		
		//Player
		player1.setAnimSprite(player, 40);
		player1.loadAnim(4, 20);
				
		sprites().add(player1);
		sprites().add(flowerTile);
		
		//collection = new Collections();
		//collection.Initialize();
		
		Tile[] mapTiles = {grassTile, grassTile, grassTile, grassTile, grassTile,
						   grassTile, flowerTile, flowerTile, flowerTile, grassTile,
						   grassTile, flowerTile, flowerTile, flowerTile, grassTile,
						   grassTile, flowerTile, flowerTile, flowerTile, grassTile,
						   grassTile, grassTile, grassTile, grassTile, grassTile};
		
		map = new Map(mapTiles, scale, 5, 5);
		
		requestFocus();
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
        
        if(keyLeft) xOffset = xOffset - 1 - playerSpeed;
        if(keyRight) xOffset = xOffset + 1 + playerSpeed;
        if(keyDown) yOffset = yOffset + 1 + playerSpeed;
        if(keyUp) yOffset = yOffset - 1 - playerSpeed;
        map.render();
        

        g2d.drawImage(player1.getAnimImage(), SCREENWIDTH / 2 + xOffset, SCREENHEIGHT / 2 + yOffset, 32 * scale, 32 * scale, this);
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
		if(keyLeft) System.out.println("Left");
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