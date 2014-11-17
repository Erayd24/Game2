package axohEngine2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.ImageEntity;
import axohEngine2.entities.Mob;
import axohEngine2.entities.SpriteSheet;
import axohEngine2.map.Map;
import axohEngine2.map.Tile;
import axohEngine2.project.InGameMenu;
import axohEngine2.project.MapDatabase;
import axohEngine2.project.OPTION;
import axohEngine2.project.STATE;
import axohEngine2.project.TitleMenu;

public class Judgement extends Game {
	private static final long serialVersionUID = 1L;
	
	static int SCREENWIDTH = 1600;
	static int SCREENHEIGHT = 900;
	static int CENTERX = SCREENWIDTH / 2;
	static int CENTERY = SCREENHEIGHT / 2;
	
	//enums, keys, and fonts variables
	boolean keyLeft, keyRight, keyUp, keyDown, keyInventory, keyAction, keyBack, keyEnter;
	Random random = new Random();
	STATE state;
	OPTION option;
	private Font simple = new Font("Arial", Font.PLAIN, 72);
	private Font bold = new Font("Arial", Font.BOLD, 72);
	private Font bigBold = new Font("Arial", Font.BOLD, 96);
	
	//player variables and scale
	private int scale;
	private int playerX;
	private int playerY;
	private int playerSpeed;
	
	//Map and collision variables
	private Map currentMap;
	private Map currentOverlay;
	private MapDatabase mapBase;
	private int inputWait = 5;
	
	//Menu variables
	private int inX = 90, inY = 90;
	private int inLocation;
	private int titleX = 530, titleY = 610;
	private int titleX2 = 340, titleY2 = 310;
	private int titleLocation;
	private String currentFile;
	
	//Game variables
	SpriteSheet extras1;
	SpriteSheet mainCharacter;
	
	ImageEntity inGameMenu;
	ImageEntity titleMenu;
	ImageEntity titleMenu2;
	
	TitleMenu title;
	InGameMenu inMenu;
	
	AnimatedSprite titleArrow;
	
	Mob playerMob;
	Mob randomNPC;
	
	public Judgement() {
		super(180, SCREENWIDTH, SCREENHEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	void gameStartUp() {
		state = STATE.TITLE;
		option = OPTION.NONE;
		scale = 4;
		playerX = -40;
		playerY = 0;
		playerSpeed = 3;
		
		//****Initialize spriteSheets*********************************************************************
		extras1 = new SpriteSheet("/textures/extras/extras1.png", 8, 8, 32, scale);
		mainCharacter = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32, scale);

		//****Initialize AnimatedSprites******************************************************************
		titleArrow = new AnimatedSprite(this, graphics(), extras1, 0, "arrow");
		titleArrow.loadAnim(4, 11);
		sprites().add(titleArrow);
		
		//****Initialize image entities*******************************************************************
		inGameMenu = new ImageEntity(this);
		titleMenu = new ImageEntity(this);
		titleMenu2 = new ImageEntity(this);
		inGameMenu.load("/menus/ingamemenu.png");
		titleMenu.load("/menus/titlemenu1.png");
		titleMenu2.load("/menus/titlemenu2.png");
		
		//*****Initialize Menus***************************************************************************
		title = new TitleMenu(titleMenu, titleMenu2, titleArrow, SCREENWIDTH, SCREENHEIGHT, simple, bold, bigBold);
		inMenu = new InGameMenu(inGameMenu, SCREENWIDTH, SCREENHEIGHT);
		
		//****Initialize Mobs*****************************************************************************
		playerMob = new Mob(this, graphics(), mainCharacter, 40, "player", "mainC", true);
		playerMob.setMultBounds(6, 50, 95, 37, 95, 62, 95, 54, 95);
		playerMob.loadMultAnim(32, 48, 40, 56, 3, 8);
		playerMob.setHealth(35);
		sprites().add(playerMob);
		
		//*****Set up first map and tiles******************************************************************
		mapBase = new MapDatabase(this, graphics(), scale);
		for(int i = 0; i < mapBase.maps.length; i++){
			if(mapBase.getMap(i) == null) continue;
			if(mapBase.getMap(i).mapName() == "city") currentMap = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == "cityO") currentOverlay = mapBase.getMap(i);
		}
		for(int i = 0; i < currentMap.getHeight() * currentMap.getHeight(); i++){
			addTile(currentMap.accessTile(i));
			addTile(currentOverlay.accessTile(i));
			currentMap.accessTile(i).getEntity().setX(-300);
			currentOverlay.accessTile(i).getEntity().setX(-300);
		}
		
		requestFocus();
		start();
	}
	
	void gameTimedUpdate() {
		checkInput();
		if(state == STATE.TITLE) title.update(option, titleLocation);
		if(state == STATE.INGAMEMENU) inMenu.update(option);
		updateData(currentMap, currentOverlay, playerX, playerY);
		System.out.println(frameRate());
	}
	
	void gameRefreshScreen() {		
		Graphics2D g2d = graphics();
		g2d.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
		g2d.setFont(simple);
		
		if(state == STATE.GAME) {
			currentMap.render(this, g2d, (int)playerX, (int)playerY);
			currentOverlay.render(this, g2d, (int)playerX, (int)playerY);
			playerMob.renderMob(CENTERX, CENTERY);
			playerMob.drawBounds(Color.BLACK);
		}
		if(state == STATE.INGAMEMENU){
			inMenu.render(this, g2d, inX, inY);
		}
		if(state == STATE.TITLE) {
			title.render(this, g2d, titleX, titleY, titleX2, titleY2);
		}
	}

	void addTile(Tile tile) {
		if(tile.hasProperty()){
			tiles().add(tile);
		}
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
		}
		if(spr1.spriteType() == "enemy" || spr2.spriteType() == "enemy") {
		}
		if(spr1.spriteType() == "npc" || spr2.spriteType() == "npc") {
		}
	}
	
	void tileCollision(AnimatedSprite spr, Tile tile) {
		if(spr.spriteType() == "player" && tile.hasEvent()) {
			if(tile.event().getEventType().equals("warp")) {
				tiles().clear();
				for(int i = 0; i < mapBase.maps.length; i++){
					 if(mapBase.getMap(i) == null) continue;
					 if(tile.event().getMapName() == mapBase.getMap(i).mapName()) currentMap = mapBase.getMap(i);
					 if(tile.event().getOverlayName() == mapBase.getMap(i).mapName()) currentOverlay = mapBase.getMap(i);
				}
				for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){ //For differing widths and height of each map
					addTile(currentMap.accessTile(i));
					addTile(currentOverlay.accessTile(i));
				}
				playerX = tile.event().getNewX();
				playerY = tile.event().getNewY();
				return;
			}	
		}
		if(("player").equals(spr.spriteType())) {
			double leftOverlap = (spr.getX() + spr.getBoundSize() - tile.getX());
			double rightOverlap = (tile.getX() + tile.getBoundSize() - spr.getX());
			double topOverlap = (spr.getY() + spr.getBoundSize() - tile.getY());
			double botOverlap = (tile.getY() + tile.getBoundSize() - spr.getY());
			
			double smallestOverlap = Double.MAX_VALUE; 
			double shiftX = 0;
			double shiftY = 0;
			
			if(leftOverlap < smallestOverlap) { //Left
				smallestOverlap = leftOverlap;
				shiftX -= leftOverlap; 
				shiftY = 0;
			}
			if(rightOverlap < smallestOverlap){ //right
				smallestOverlap = rightOverlap;
				shiftX = rightOverlap;
				shiftY = 0;
			}
			if(topOverlap < smallestOverlap){ //up
				smallestOverlap = topOverlap;
				shiftX = 0;
				shiftY -= topOverlap;
			}
			if(botOverlap < smallestOverlap){ //down
				smallestOverlap = botOverlap;
				shiftX = 0;
				shiftY = botOverlap;
			}
			playerX -= shiftX;
			playerY -= shiftY;
		}

		if(spr instanceof Mob) {
			if(spr.spriteType() == "enemy" || spr.spriteType() == "npc") {
				spr = (Mob) spr;
				((Mob) spr).setLoc((int)((Mob) spr).getXLoc(), (int)((Mob) spr).getYLoc());
			}
		}
	}
	
	void movePlayer(int xa, int ya) {
		if(xa > 0) {
			playerX += xa; //left
		}
		if(xa < 0) {
			playerX += xa; //right
		}
		if(ya > 0) {
			playerY += ya; //up
		}
		if(ya < 0) {
			playerY += ya; //down
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
		int xa = 0;
		int ya = 0;
		//Special actions for in game
		if(state == STATE.GAME && inputWait < 0) { 
			if(keyLeft) {
				xa = xa + 1 + playerSpeed;
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			if(keyRight) {
				xa = xa - 1 - playerSpeed;
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			if(keyUp) {
				ya = ya + 1 + playerSpeed;
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			if(keyDown) {
				ya = ya - 1 - playerSpeed;
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			
			if(!keyLeft && !keyRight && !keyUp && !keyDown) {
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			movePlayer(xa, ya);
		
			if(keyInventory) {
				state = STATE.INGAMEMENU;
				inputWait = 5;
			}
		}
		
		 //Special actions for the title menu
		if(state == STATE.TITLE && inputWait < 0){
			if(option == OPTION.NONE){
				if(keyDown && titleLocation < 1) {
					titleX -= 105;
					titleY += 100;
					titleLocation++;
					inputWait = 5;
				}
				if(keyUp && titleLocation > 0){
					titleX += 105;
					titleY -= 100;
					titleLocation--;
					inputWait = 5;
				}
				if(keyEnter) {
					if(titleLocation == 0){
						option = OPTION.NEWGAME;
						titleLocation = 0;
						inputWait = 5;
						keyEnter = false;
					}
					if(titleLocation == 1){
						option = OPTION.LOADGAME;
						titleLocation = 0;
						inputWait = 5;
						keyEnter = false;
					}
				}
			}
			if(option == OPTION.NEWGAME || option == OPTION.LOADGAME){
				if(keyBack && !title.isGetName()){
					if(option == OPTION.NEWGAME) titleLocation = 0;
					if(option == OPTION.LOADGAME) titleLocation = 1;
					inputWait = 5;
					titleX2 = 340;
					titleY2 = 310;
					option = OPTION.NONE;
				}
				if(keyDown && titleLocation < 2) {
					titleLocation++;
					titleY2 += 160;
					inputWait = 7;
				}
				if(keyUp && titleLocation > 0) {
					titleLocation--;
					titleY2 -= 160;
					inputWait = 7;
				}
				if(keyEnter && !title.isGetName()) {
					if(option == OPTION.NEWGAME) {
						if(title.files().length - 1 < titleLocation) {
							title.enter();
							titleX2 += 40;
							inputWait = 5;
						}
					}
					if(option == OPTION.LOADGAME) {
						currentFile = title.enter();
						if(currentFile != "") {
							loadGame();
							inputWait = 5;
							option = OPTION.NONE;
							state = STATE.GAME;
						}
					}
				}
				//For typeSetting
				if(title.isGetName() == true) {
					title.setFileName(currentChar);
					currentChar = '\0';
					if(keyBack) {
						title.deleteChar();
						inputWait = 5;
					}
					if(keyBack && title.getFileName().length() == 0) {
						title.setGetName(false);
						titleX2 -= 40;
						inputWait = 5;
					}
					if(keyEnter && title.getFileName().length() > 0) {
						save.newFile(title.getFileName());
						title.setGetName(false);
						currentFile = title.getFileName();
						state = STATE.GAME;
						option = OPTION.NONE;
					}
				}
			}
		}
		
		 //Special actions for in game menu
		if(state == STATE.INGAMEMENU && inputWait < 0) {
			if(keyInventory) {
				state = STATE.GAME;
				option = OPTION.NONE;
				inLocation = 0;
				inY = 90;
				inputWait = 5;
			}
			if(option == OPTION.NONE){ 
				if(keyUp) {
					if(inLocation > 0) {
						inY -= 108;
						inLocation--;
						inputWait = 5;
					}
				}
				if(keyDown) {
					if(inLocation < 4) {
						inY += 108;
						inLocation++;
						inputWait = 5;
					}
				}
				if(keyEnter) {
					if(inLocation == 0){
						option = OPTION.ITEMS;
						inputWait = 5;
					}
					if(inLocation == 1){
						option = OPTION.EQUIPMENT;
						inputWait = 5;
					}
					if(inLocation == 2){
						option = OPTION.MAGIC;
						inputWait = 5;
					}
					if(inLocation == 3){
						option = OPTION.STATUS;
						inputWait = 5;
					}
					if(inLocation == 4){
						option = OPTION.SAVE;
						inputWait = 5;
						save.saveState(currentFile, data());
					}
				}
			}
			if(keyBack) {
				option = OPTION.NONE;
			}
		}
		inputWait--;
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
	
	 void drawString(Graphics2D g2d, String text, int x, int y) {
         for (String line : text.split("\n"))
             g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
     }
	 
	 void loadGame() {
		 if(currentFile != "") {
			 loadData(currentFile);
			 tiles().clear();
			 for(int i = 0; i < mapBase.maps.length; i++){
				 if(mapBase.getMap(i) == null) continue;
				 if(data().getMapName() == mapBase.getMap(i).mapName()) currentMap = mapBase.getMap(i);
				 if(data().getOverlayName() == mapBase.getMap(i).mapName()) currentOverlay = mapBase.getMap(i);
			 }
			 playerX = data().getPlayerX();
			 playerY = data().getPlayerY();
			 for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
					addTile(currentMap.accessTile(i));
					addTile(currentOverlay.accessTile(i));
			}
		 }
	 }
}