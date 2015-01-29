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
import axohEngine2.project.TYPE;
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
	private int sectionLoc;
	private int titleX = 530, titleY = 610;
	private int titleX2 = 340, titleY2 = 310;
	private int titleLocation;
	private String currentFile;
	private boolean wasSaving = false;
	private int wait;
	private boolean waitOn = false;
	
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
	
	//Set up the super class Game and set the window to appear
	public Judgement() {
		super(130, SCREENWIDTH, SCREENHEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//This method is called only once by the Game class, for startup
	//Initialize all variables here
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
		titleArrow.loadAnim(4, 10);
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
		playerMob = new Mob(this, graphics(), mainCharacter, 40, TYPE.PLAYER, "mainC", true);
		playerMob.setMultBounds(6, 50, 95, 37, 88, 62, 92, 62, 96);
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
			if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
			if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
			currentMap.accessTile(i).getEntity().setX(-300);
			currentOverlay.accessTile(i).getEntity().setX(-300);
		}
		
		requestFocus(); //Make sure the game is focused on
		start(); //Start the game loop
	}
	
	//Special section that updates with the default game loop methods
	//Add game specific elements that need updating here
	void gameTimedUpdate() {
		checkInput();
		if(state == STATE.TITLE) title.update(option, titleLocation); //Title Menu update
		if(state == STATE.INGAMEMENU) inMenu.update(option, sectionLoc); //In Game Menu update
		updateData(currentMap, currentOverlay, playerX, playerY); //Update the current file data for saving later
		System.out.println(frameRate());
		if(waitOn) wait--;
	}
	
	//Obtain the graphics passed down by the super class Game and render objects on the screen here
	void gameRefreshScreen() {		
		Graphics2D g2d = graphics();
		g2d.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
		g2d.setFont(simple);
		
		if(state == STATE.GAME) {
			currentMap.render(this, g2d, playerX, playerY);
			currentOverlay.render(this, g2d, playerX, playerY);
			playerMob.renderMob(CENTERX, CENTERY);
			g2d.setColor(Color.GREEN);
			g2d.drawString("Health: " + inMenu.getHealth(), CENTERX - 780, CENTERY - 350);
			g2d.setColor(Color.BLUE);
			g2d.drawString("Magic: " + inMenu.getMagic(), CENTERX - 280, CENTERY - 350);
		}
		if(state == STATE.INGAMEMENU){
			inMenu.render(this, g2d, inX, inY);
		}
		if(state == STATE.TITLE) {
			title.render(this, g2d, titleX, titleY, titleX2, titleY2);
		}
		if(option == OPTION.SAVE){
			drawString(g2d, "Are you sure you\n      would like to save?", 660, 400);
		}
		if(wasSaving && wait > 0) {
			g2d.drawString("Game Saved!", 700, 500);
		}
	}
	
	//Set special actions for quitting out of a game window
	void gameShutDown() {		
	}

	void spriteUpdate(AnimatedSprite sprite) {		
	}

	void spriteDraw(AnimatedSprite sprite) {		
	}

	void spriteDying(AnimatedSprite sprite) {		
	}

	//Set handling for when a sprite contacts a sprite
	// hitDir is the hit found when coliding on a specific box on spr1 and hitDir2 is the same thing but for spr2
	void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2, int hitDir, int hitDir2) {
		double leftOverlap = (spr1.getBoundX(hitDir) + spr1.getBoundSize() - spr2.getBoundX(hitDir2));
		double rightOverlap = (spr2.getBoundX(hitDir2) + spr2.getBoundSize() - spr1.getBoundX(hitDir));
		double topOverlap = (spr1.getBoundY(hitDir) + spr1.getBoundSize() - spr2.getBoundY(hitDir2));
		double botOverlap = (spr2.getBoundY(hitDir2) + spr2.getBoundSize() - spr1.getBoundY(hitDir));
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

		if(spr1.spriteType() == TYPE.PLAYER && state == STATE.GAME){
			if(spr2 instanceof Mob) ((Mob) spr2).stop();
			playerX -= shiftX;
			playerY -= shiftY;
		}
	}
	
	//Set handling for when a sprite contacts a Tile, this is handy for
	// dealing with Tiles which contain Events. When specifying a new
	// collision method, check for the type of sprite and whether a tile is
	// solid or breakable, both, or even if it contains an event. This is
	// mandatory because the AxohEngine finds details on collision and then 
	// sends it here, which can be the handled in specific ways by the user.
	// AxohEngine doesn't check for solidity. The user must.
	void tileCollision(AnimatedSprite spr, Tile tile, int hitDir, int hitDir2) {
		double leftOverlap = (spr.getBoundX(hitDir) + spr.getBoundSize() - tile.getBoundX(hitDir2));
		double rightOverlap = (tile.getBoundX(hitDir2) + tile.getBoundSize() - spr.getBoundX(hitDir));
		double topOverlap = (spr.getBoundY(hitDir) + spr.getBoundSize() - tile.getBoundY(hitDir2));
		double botOverlap = (tile.getBoundY(hitDir2) + tile.getBoundSize() - spr.getBoundY(hitDir));
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
		
		if(tile.hasEvent()){
			if(spr.spriteType() == TYPE.PLAYER && tile.hasEvent()) {
				if(tile.event().getEventType() == TYPE.WARP) {
					tiles().clear();
					sprites().clear();
					sprites().add(playerMob);
					for(int i = 0; i < mapBase.maps.length; i++){
						 if(mapBase.getMap(i) == null) continue;
						 if(tile.event().getMapName() == mapBase.getMap(i).mapName()) currentMap = mapBase.getMap(i);
						 if(tile.event().getOverlayName() == mapBase.getMap(i).mapName()) currentOverlay = mapBase.getMap(i);
					}
					for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
						addTile(currentMap.accessTile(i));
						addTile(currentOverlay.accessTile(i));
						if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
						if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
					}
					playerX = tile.event().getNewX();
					playerY = tile.event().getNewY();
				}	
			}
			if(spr.spriteType() == TYPE.PLAYER && tile.event().getEventType() == TYPE.ITEM && keyAction){
				if((tile._name).equals("chest")) tile.setFrame(tile.getSpriteNumber() + 1);
				inMenu.addItem(tile.event().getItem()); //Add item to inventory
				tile.endEvent();
			}
		}
		
		if(spr.spriteType() == TYPE.PLAYER && tile.solid() && state == STATE.GAME) {
			playerX -= shiftX;
			playerY -= shiftY;
			return;
		}
		if(spr.spriteType() != TYPE.PLAYER && tile.solid() && state == STATE.GAME){
			if(spr instanceof Mob) {
				((Mob) spr).setLoc((int)shiftX, (int)shiftY);
				((Mob) spr).resetMovement();
			}
			
		}
	}
	
	//Method to call which moves the player. The player sprite itself is not actually
	// ever moved. In fact, the playerX and playerY variables are fed in to the map
	// so the map moves around the player sprite which is why the x and y variables
	// are opposites. If you want to move right, you subtract from X.
	void movePlayer(int xa, int ya) {
		if(xa > 0) playerX += xa; //left
		if(xa < 0) playerX += xa; //right
		if(ya > 0) playerY += ya; //up
		if(ya < 0) playerY += ya; //down
	}
	
	//Main
	public static void main(String[] args) { new Judgement(); }
	
	/**********************************************************
	 * The Depths of Judgement Lies Below
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
				inputWait = 10;
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
						inputWait = 10;
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
				if(keyDown && titleLocation < 2 && !title.isGetName()) {
					titleLocation++;
					titleY2 += 160;
					inputWait = 7;
				}
				if(keyUp && titleLocation > 0 && !title.isGetName()) {
					titleLocation--;
					titleY2 -= 160;
					inputWait = 7;
				}
				if(keyEnter && !title.isGetName()) {
					if(option == OPTION.NEWGAME) {
						if(title.files() != null){ //Make sure the location of a new game is greater than previous ones
							if(title.files().length - 1 < titleLocation) {
								title.enter();
								titleX2 += 40;
								inputWait = 5;
							}
						}
						if(title.files() == null) { //final check if there are no files made yet, to make the file anywhere
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
							setGameState(STATE.GAME);
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
						setGameState(STATE.GAME);
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
				inputWait = 8;
			}
			if(option == OPTION.NONE){ 
				if(wait == 0) wasSaving = false;
				if(keyUp) {
					if(inLocation > 0) {
						inY -= 108;
						inLocation--;
						inputWait = 10;
					}
				}
				if(keyDown) {
					if(inLocation < 4) {
						inY += 108;
						inLocation++;
						inputWait = 10;
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
						inputWait = 20;
					}
					keyEnter = false;
				}
			}
			
			if(option == OPTION.ITEMS) {
				if(keyUp){
					if(sectionLoc == 0) inMenu.loadOldItems();
					if(sectionLoc - 1 != -1) sectionLoc--;
					inputWait = 8;
				}
				if(keyDown) {
					if(sectionLoc == 3) inMenu.loadNextItems();
					if(inMenu.getTotalItems() > sectionLoc + 1 && sectionLoc < 3) sectionLoc++;
					inputWait = 8;
				}
			}
			
			if(option == OPTION.EQUIPMENT) {
				if(keyUp){
					if(sectionLoc == 0) inMenu.loadOldItems();
					if(sectionLoc - 1 != -1) sectionLoc--;
					inputWait = 8;
				}
				if(keyDown) {
					if(sectionLoc == 3) inMenu.loadNextEquipment();
					if(inMenu.getTotalEquipment() > sectionLoc + 1 && sectionLoc < 3) sectionLoc++;
					inputWait = 8;
				}
			}
			
			if(option == OPTION.SAVE){
				if(keyEnter){
					save.saveState(currentFile, data());
					inputWait = 20;
					wait = 200;
					waitOn = true;
					wasSaving = true;
					option = OPTION.NONE;
				}
			}
			
			if(keyBack && option != OPTION.NONE) {
				option = OPTION.NONE;
				inMenu.setItemLoc(0);
				sectionLoc = 0;
				inputWait = 8;
				keyBack = false;
			}
			if(keyBack && option == OPTION.NONE) {
				state = STATE.GAME;
				option = OPTION.NONE;
				inLocation = 0;
				sectionLoc = 0;
				inY = 90;
				inputWait = 8;
			}
		}
		inputWait--;
	}
	
	void gameKeyDown(int keyCode) {
		switch(keyCode) {
        case KeyEvent.VK_LEFT:
            keyLeft = true;
            break;
        case KeyEvent.VK_A:
        	keyLeft = true;
        	break;
        case KeyEvent.VK_RIGHT:
            keyRight = true;
            break;
        case KeyEvent.VK_D:
        	keyRight = true;
        	break;
        case KeyEvent.VK_UP:
            keyUp = true;
            break;
        case KeyEvent.VK_W:
        	keyUp = true;
        	break;
        case KeyEvent.VK_DOWN:
            keyDown = true;
            break;
        case KeyEvent.VK_S:
        	keyDown = true;
        	break;
        case KeyEvent.VK_I:
        	keyInventory = true;
        	break;
        case KeyEvent.VK_F:
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
        case KeyEvent.VK_A:
        	keyLeft = false;
        	break;
        case KeyEvent.VK_RIGHT:
            keyRight = false;
            break;
        case KeyEvent.VK_D:
        	keyRight = false;
        	break;
        case KeyEvent.VK_UP:
            keyUp = false;
            break;
        case KeyEvent.VK_W:
        	keyUp = false;
        	break;
        case KeyEvent.VK_DOWN:
            keyDown = false;
            break;
        case KeyEvent.VK_S:
        	keyDown = false;
        	break;
        case KeyEvent.VK_I:
	    	keyInventory = false;
	    	break;
	    case KeyEvent.VK_F:
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
	 
	 //From the title screen, load a game file by having the super class get the data,
	 // then handling where the pieces of the data will be assigned here.
	 void loadGame() {
		 if(currentFile != "") {
			 loadData(currentFile);
			 tiles().clear();
			 sprites().clear();
			 for(int i = 0; i < mapBase.maps.length; i++){
				 if(mapBase.getMap(i) == null) continue;
				 if(data().getMapName() == mapBase.getMap(i).mapName()) currentMap = mapBase.getMap(i);
				 if(data().getOverlayName() == mapBase.getMap(i).mapName()) currentOverlay = mapBase.getMap(i);
			 }
			 playerX = data().getPlayerX();
			 playerY = data().getPlayerY();
			 sprites().add(playerMob);
			 for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
					addTile(currentMap.accessTile(i));
					addTile(currentOverlay.accessTile(i));
					if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
					if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
			}
		 }
	 }
}