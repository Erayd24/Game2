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
import axohEngine2.map.Event;
import axohEngine2.map.Map;
import axohEngine2.map.Tile;
import axohEngine2.project.OPTION;
import axohEngine2.project.STATE;
import axohEngine2.project.TitleMenu;

public class Judgement extends Game {
	private static final long serialVersionUID = 1L;
	
	static int SCREENWIDTH = 1600;
	static int SCREENHEIGHT = 900;
	static int CENTERX = SCREENWIDTH / 2;
	static int CENTERY = SCREENHEIGHT / 2;
	
	//enums, keys, saves, and fonts variables
	boolean keyLeft, keyRight, keyUp, keyDown, keyInventory, keyAction, keyBack, keyEnter;
	Random random = new Random();
	STATE state;
	OPTION option;
	private Font simple = new Font("Arial", Font.PLAIN, 72);
	private Font bold = new Font("Arial", Font.BOLD, 72);
	private Font bigBold = new Font("Arial", Font.BOLD, 96);
	private String currentFile;
	
	//player variables and scale
	private int scale;
	private double playerX;
	private double playerY;
	private int playerSpeed;
	private double oldX;
	private double oldY;
	private boolean collision = false;
	private Map currentMap;
	private Map currentOverlay;
	private int inputWait = 5;
	
	//Menu variables
	private int inX = 90, inY = 90;
	private int inLocation;
	private int titleX = 530, titleY = 610;
	private int titleX2 = 340, titleY2 = 310;
	private int titleLocation;
	
	//Game variables -maps/sprites/tiles ... etc
	SpriteSheet misc;
	SpriteSheet buildings;
	SpriteSheet mainCharacter;
	SpriteSheet environment32;
	SpriteSheet extras1;
	
	ImageEntity inGameMenu;
	ImageEntity titleMenu;
	ImageEntity titleMenu2;
	
	TitleMenu title;
	AnimatedSprite titleArrow;
	
	Mob playerMob;
	Mob randomNPC;
	
	Map city;
	Map cityO;
	Map houses;
	Map housesO;
	Tile d;
	Tile g;
	Tile f;
	Tile b;
	Tile r;
	Tile e;
	Tile ro;
	Tile h;
	Tile hf;
	
	Event warp1;
	Event warp2;
		
	//Load Sound effects
	public Judgement() {
		super(95, SCREENWIDTH, SCREENHEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	void gameStartUp() {
		state = STATE.TITLE;
		option = OPTION.NONE;
		scale = 4;
		playerX = -40;
		playerY = 0;
		oldX = playerX;
		oldY = playerY;
		playerSpeed = 3;
		
		//****Initialize spriteSheets*********************************************************************
		misc = new SpriteSheet("/textures/environments/environments1.png", 16, 16, 16, scale);
		buildings = new SpriteSheet("/textures/environments/4x4buildings.png", 4, 4, 64, scale);
		mainCharacter = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32, scale);
		environment32 = new SpriteSheet("/textures/environments/32SizeEnvironment.png", 8, 8, 32,scale);
		extras1 = new SpriteSheet("/textures/extras/extras1.png", 8, 8, 32, scale);

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
		
		//****Initialize Mobs*****************************************************************************
		playerMob = new Mob(this, graphics(), "mainC", true, mainCharacter, 40, "player");
		playerMob.loadMultAnim(32, 48, 40, 56, 3, 8);
		playerMob.setHealth(35);
		sprites().add(playerMob);
		
		//****Initialize Tiles****************************************************************************
		d = new Tile(this, graphics(), "door", environment32, 0);
		f = new Tile(this, graphics(), "flower", misc, 1);
		g = new Tile(this, graphics(), "grass", misc, 0);
		b = new Tile(this, graphics(), "bricks", misc, 16, true);
		r = new Tile(this, graphics(), "walkWay", misc, 6);
		e = new Tile(this, graphics(), "empty", misc, 7);
		ro = new Tile(this, graphics(), "rock", misc, 2);
		h = new Tile(this, graphics(), "house", buildings, 0, true);
		hf = new Tile(this, graphics(), "floor", misc, 8);

		
		//Map generating 40 X 40
		Tile[] cityTiles = {b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
						    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
						    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b};
		
		Tile[] cityOTiles = {e, e, h, e, e, e, e, h, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, d, e, e, e, e, d, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, ro, ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e};
		
		Tile[] houseTiles = {hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf};
		
		Tile[] houseOTiles = {hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf};	
		
		//*****Initialize Maps***********************************************************************
		city = new Map(this, graphics(), cityTiles, 40, 40);
		cityO = new Map(this, graphics(), cityOTiles, 40, 40);
		houses = new Map(this, graphics(), houseTiles, 6, 6);
		housesO = new Map(this, graphics(), houseOTiles, 6, 6);
		
		//*****Set up events**************************************************************************
		warp1 = new Event("fromHouse", "warp");
		warp1.setWarp(city, cityO, 200, -50);
		warp2 = new Event("toHouse", "warp");
		warp2.setWarp(houses, housesO, 620, 250);
		
		//*****Add the events to their tile homes*****************************************************
		houses.accessTile(5).addEvent(warp1);
		city.accessTile(331).addEvent(warp2);
		
		//Add initial tiles to system for updating and set all initial locations before starting
		for(int i = 0; i < 40 * 40; i++){
			addTile(city.accessTile(i));
			addTile(cityO.accessTile(i));
			city.accessTile(i).getEntity().setX(-300);
			cityO.accessTile(i).getEntity().setX(-300);
		}
		
		//Set first map
		currentMap = city;
		currentOverlay = cityO;
		
		requestFocus();
		start();
	}
	
	void gameTimedUpdate() {
		checkInput();
		title.update(option, titleLocation);
		updateData(currentMap, currentOverlay, playerX, playerY);
		System.out.println(frameRate());
	}
	
	void gameRefreshScreen() {		
		Graphics2D g2d = graphics();
		g2d.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
		g2d.setFont(simple);
		
		if(state == STATE.GAME) {
			currentMap.render((int)playerX, (int)playerY, graphics(), this);
			currentOverlay.render((int)playerX, (int)playerY, graphics(), this);
			playerMob.renderMob(SCREENWIDTH / 2, SCREENHEIGHT / 2);
		}
		
		if(state == STATE.INGAMEMENU){
			g2d.drawImage(inGameMenu.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT, this);
			g2d.setColor(Color.BLACK);
			g2d.drawString("Items", 120, 170);
			g2d.drawString("Equipment", 120, 275);
			g2d.drawString("Magic", 120, 385);
			g2d.drawString("Status", 120, 490);
			g2d.drawString("Save Game", 120, 600);
			g2d.setColor(Color.YELLOW);
			g2d.drawRect(inX, inY, 435, 104);
			if(option == OPTION.ITEMS){
				g2d.setColor(Color.BLACK);
				g2d.drawString("Items", 920, 200);
			}
			if(option == OPTION.EQUIPMENT){
				g2d.setColor(Color.BLACK);
				g2d.drawString("Equipment", 900, 200);		
			}
			if(option == OPTION.MAGIC){
				g2d.setColor(Color.BLACK);
				g2d.drawString("Magic", 880, 200);
			}
			if(option == OPTION.STATUS){
				g2d.setColor(Color.BLACK);
				g2d.drawString("Status", 920, 200);
			}
			if(option == OPTION.SAVE){
				g2d.setColor(Color.BLACK);
				g2d.drawString("Save Game", 880, 200);
			}
		}
		
		if(state == STATE.TITLE) {
			title.renderTitleScreen(g2d, this, titleX, titleY, titleX2, titleY2);
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
				currentMap = tile.event().getMap();
				currentOverlay = tile.event().getOverlay();
				for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){ //For differing widths and height of each map
					addTile(currentMap.accessTile(i));
					addTile(currentOverlay.accessTile(i));
				}
				playerX = tile.event().getNewX();
				playerY = tile.event().getNewY();
				return;
			}	
		}
		
		if(spr.spriteType() == "player") {
			playerX = oldX - 0.5;
			playerY = oldY - 0.5;
		}

		if(spr instanceof Mob) {
			if(spr.spriteType() == "enemy" || spr.spriteType() == "npc") {
				spr = (Mob) spr;
				((Mob) spr).setLoc((int)((Mob) spr).getXLoc(), (int)((Mob) spr).getYLoc());
			}
		}
	}
	
	void movePlayer(int xa, int ya) {
		oldX = playerX;
		oldY = playerY;
		
		if(!collision && xa > 0) {
			playerX += xa; //left
		}
		if(!collision && xa < 0) {
			playerX += xa; //right
		}
		if(!collision && ya > 0) {
			playerY += ya; //up
		}
		if(!collision && ya < 0) {
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
		if(state == STATE.GAME && inputWait < 0) { //Special actions for in game
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
		
		if(state == STATE.TITLE && inputWait < 0){ //Special actions for the title menu
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
				if(keyBack && !title.getName){
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
				if(keyEnter && !title.getName) {
					if(option == OPTION.NEWGAME) {
						title.enter();
						titleX2 += 40;
						inputWait = 5;
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
				//For type setting
				if(title.getName == true) {
					title.setFileName(currentChar);
					currentChar = '\0';
					if(keyBack) {
						title.deleteChar();
						inputWait = 5;
					}
					if(keyBack && title.getFileName().length() == 0) {
						title.getName = false;
						titleX2 -= 40;
						inputWait = 5;
					}
					if(keyEnter && title.getFileName().length() > 0) {
						save.newFile(title.getFileName());
						title.getName = false;
						currentFile = title.getFileName();
						state = STATE.GAME;
						option = OPTION.NONE;
					}
				}
			}
		}
		
		if(state == STATE.INGAMEMENU && inputWait < 0) { //Special actions for in game menu
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
					if(inLocation == 0){ //Items
						option = OPTION.ITEMS;
						inputWait = 5;
					}
					if(inLocation == 1){ //Equipment
						option = OPTION.EQUIPMENT;
						inputWait = 5;
					}
					if(inLocation == 2){ //Magic
						option = OPTION.MAGIC;
						inputWait = 5;
					}
					if(inLocation == 3){ //Status
						option = OPTION.STATUS;
						inputWait = 5;
					}
					if(inLocation == 4){ //SaveGame
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
			 currentMap = data().getMap();
			 currentOverlay = data().getOverlay();
			 playerX = data().getPlayerX();
			 playerY = data().getPlayerY();
			 for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
					addTile(currentMap.accessTile(i));
					addTile(currentOverlay.accessTile(i));
			}
		 }
	 }
}