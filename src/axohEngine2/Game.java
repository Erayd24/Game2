package axohEngine2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import javax.swing.JFrame;

import axohEngine2.data.Data;
import axohEngine2.data.Save;
import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.Mob;
import axohEngine2.map.Map;
import axohEngine2.map.Tile;
import axohEngine2.project.STATE;
import axohEngine2.util.Point2D;

abstract class Game extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	//Game loop and Thread variable
	private transient Thread gameloop;
	
	//Game lists to keep track of game specific data
	private LinkedList<AnimatedSprite> _sprites;
	public LinkedList<AnimatedSprite> sprites() { return _sprites; }
	private LinkedList<Tile> _tiles;
	public LinkedList<Tile> tiles() { return _tiles; }
	
	//Set up graphics, synchronizing, screenwidth and height
	private transient BufferedImage backBuffer;
	private transient Graphics2D g2d;
	private transient Toolkit tk;
	private int screenWidth, screenHeight;
	private STATE state;
	public void setGameState(STATE state) { this.state = state; }
	
	//Mouse variables
	private transient Point2D mousePos = new Point2D(0, 0);
	private boolean mouseButtons[] = new boolean[4];
	protected char currentChar;
	
	//File variables
	private Data data;
	protected Save save;
		
	//Time and frame rate variables
	private int _frameCount = 0;
	private int _frameRate = 0;
	private int desiredRate;
	private long startTime = System.currentTimeMillis();
	
	//Pause game state
	private boolean _gamePaused = false;
	public boolean gamePaused() { return _gamePaused; }
	public void pauseGame() { _gamePaused = true; }
	public void resumeGame() { _gamePaused = false; }
		
	//Game event methods
	abstract void gameStartUp();
	abstract void gameTimedUpdate();
	abstract void gameRefreshScreen();
	abstract void gameShutDown();
	abstract void gameKeyDown(int keyCode);
    abstract void gameKeyUp(int keyCode);
    abstract void gameMouseDown();
    abstract void gameMouseUp();
    abstract void gameMouseMove();
	abstract void spriteUpdate(AnimatedSprite sprite);
	abstract void spriteDraw(AnimatedSprite sprite);
	abstract void spriteDying(AnimatedSprite sprite);
	abstract void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2, int hitDir);
	abstract void tileCollision(AnimatedSprite spr, Tile tile, int hitDir);
	
	//Constructor - Initialize the frame, the backBuffer, the game lists, and any other
	// background related objects. Add the listeners.
	public Game(int frameRate,int width, int height) {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setSize(size);
		pack();
		
		desiredRate = frameRate;
		screenWidth = width;
		screenHeight = height;
		
		backBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        g2d = backBuffer.createGraphics();
        tk = Toolkit.getDefaultToolkit();
        state = null;

        //create the internal lists
        _sprites = new LinkedList<AnimatedSprite>();
        _tiles = new LinkedList<Tile>();
        
        data = new Data();
		save = new Save();
        
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        gameStartUp();
	}
	
	//Get the graphics used in Game in sub-classes
	public Graphics2D graphics() { return g2d; }
	
	//Get the framerate Game is currently running at
	public int frameRate() { return _frameRate; }
	
	//Mouse events
	public boolean mouseButton(int btn) { return mouseButtons[btn]; }
	public Point2D mousePosition() { return mousePos; }
	
	//Override the frames update method and insert custom updating methods
	public void update(Graphics g) {
		_frameCount++;
		if(System.currentTimeMillis() > startTime + 1000) {
			startTime = System.currentTimeMillis();
			_frameRate = _frameCount;
			_frameCount = 0;
			
			purgeSprites(); 
		}
			drawSprites();
			paint(g);
			gameRefreshScreen();
	}
	
	//Override the frames Paint method, draw the backBuffer and sync with the system
	public void paint(Graphics g) {
		g.drawImage(backBuffer, 0, 0, this);
		tk.sync();
	}
	
	//Start the game loop - initialize the Thread
	public void start() {
		gameloop = new Thread(this);
		gameloop.start();
	}
	
	//Using Runnable, run a loop which calls update methods for specific things including
	// graphics and collision.
	public void run() {
		Thread t = Thread.currentThread();
		while(t == gameloop) {
			try { 
				Thread.sleep(1000 / desiredRate);
			} catch(InterruptedException e) { e.printStackTrace(); }
			
			if(!gamePaused()) {
				gameTimedUpdate();
				updateSprites();
				spriteCollision();
				tileCollision();
			}
			update(graphics());
			repaint();
		}
	}
	
	//Stop the game with this method call
	public void stop() {
		gameloop = null;
		gameShutDown();
	}
	
	//The loadData method takes a fileName as a parameter, finds that file, and then
	// attempts an unserialization. The data found is then assigned to the current Data object
	public void loadData(String fileName) {
		FileInputStream file_in = null;
		ObjectInputStream reader = null;
		Object obj = null;
		try {
			file_in = new FileInputStream("C:/gamedata/saves/" + fileName);
			reader = new ObjectInputStream (file_in);
			System.out.println("Load successful.");
			obj = reader.readObject();
		} catch(IOException | ClassNotFoundException e) {}
		if(obj instanceof Data) data = (Data) obj;
	}
	
	//Get the current Data class instance
	public Data data() { return data; }
	
	//Key Listener Methods
	public void keyTyped(KeyEvent k) { setKeyChar(k.getKeyChar()); }
    public void keyPressed(KeyEvent k) { gameKeyDown(k.getKeyCode()); }
    public void keyReleased(KeyEvent k) { gameKeyUp(k.getKeyCode()); }
    
    //Mouse Listener events
    private void checkButtons(MouseEvent e) {
        switch(e.getButton()) {
        case MouseEvent.BUTTON1:
            mouseButtons[1] = true;
            mouseButtons[2] = false;
            mouseButtons[3] = false;
            break;
        case MouseEvent.BUTTON2:
            mouseButtons[1] = false;
            mouseButtons[2] = true;
            mouseButtons[3] = false;
            break;
        case MouseEvent.BUTTON3:
            mouseButtons[1] = false;
            mouseButtons[2] = false;
            mouseButtons[3] = true;
            break;
        }
	}
	
	public void mousePressed(MouseEvent e) {
	    checkButtons(e);
	    mousePos.setX(e.getX());
	    mousePos.setY(e.getY());
	    gameMouseDown();
	}
	
	public void mouseReleased(MouseEvent e) {
	    checkButtons(e);
	    mousePos.setX(e.getX());
	    mousePos.setY(e.getY());
	    gameMouseUp();
	}
	
	public void mouseMoved(MouseEvent e) {
	    checkButtons(e);
	    mousePos.setX(e.getX());
	    mousePos.setY(e.getY());
	    gameMouseMove();
	}
	
	public void mouseDragged(MouseEvent e) {
	    checkButtons(e);
	    mousePos.setX(e.getX());
	    mousePos.setY(e.getY());
	    gameMouseDown();
	    gameMouseMove();
	}
	
	public void mouseEntered(MouseEvent e) {
	    mousePos.setX(e.getX());
	    mousePos.setY(e.getY());
	    gameMouseMove();
	}
	
	public void mouseExited(MouseEvent e) {
	    mousePos.setX(e.getX());
	    mousePos.setY(e.getY());
	    gameMouseMove();
	}
	
	public void mouseClicked(MouseEvent e) { }
	
	//Set the current key being pressed to the current char being pressed
	// get currentChar to obtain the char pressed.
	public void setKeyChar(char keyChar) { currentChar = keyChar; }

	//Return an angles X or Y value based on a degree and returned in radians
	protected double calcAngleMoveX(double angle) { return (double) (Math.cos(angle * Math.PI / 180)); }
	protected double calcAngleMoveY(double angle) { return (double) (Math.sin(angle * Math.PI / 180)); }
	
	//update all the sprites in the current list if they are alive
	protected void updateSprites() {
		for(int i = 0; i < _sprites.size(); i++) {
			AnimatedSprite spr = (AnimatedSprite) _sprites.get(i);
			if(spr.alive()) {
				spriteUpdate(spr);
				if(state == STATE.GAME) if(spr instanceof Mob) ((Mob) spr).updateMob();
			}
			spriteDying(spr);
		}
	}
	
	//Update the data object with all of the currently needed variables
	protected void updateData(Map currentMap, Map currentOverlay, int playerX, int playerY) {
		data.update(currentMap.mapName(), currentOverlay.mapName(), playerX, playerY);
	}
	
	//Detect when a sprite intersects a sprite and calls a handling method, currently only 
	// rectangles are used for detection
	protected void spriteCollision() {
		for(int i = 0; i < _sprites.size(); i++) {
			AnimatedSprite spr1 = _sprites.get(i);
			for(int j = 0; j < _sprites.size(); j++) {
				AnimatedSprite spr2 = _sprites.get(j);
				if(!spr1.hasMultBounds() && !spr2.hasMultBounds()){
					if(spr1.getBounds().intersects(spr2.getBounds())) spriteCollision(spr1, spr2, -1); //spr1 and spr2 have one bound
				} else {
					if(spr1.hasMultBounds() && !spr2.hasMultBounds()){ //spr1 has multiple bounds but not spr2
						if(spr1.checkLeftBound(spr2.getBounds())) spriteCollision(spr1, spr2, 0);
				   		if(spr1.checkRightBound(spr2.getBounds())) spriteCollision(spr1, spr2, 1);
				   		if(spr1.checkHeadBound(spr2.getBounds())) spriteCollision(spr1, spr2, 2);
				   		if(spr1.checkLegBound(spr2.getBounds())) spriteCollision(spr1, spr2, 3);
					}
					if(spr2.hasMultBounds() && !spr1.hasMultBounds()){ //spr2 has multiple bounds but not spr1
						if(spr2.checkLeftBound(spr1.getBounds())) spriteCollision(spr1, spr2, 0);
				   		if(spr2.checkRightBound(spr1.getBounds())) spriteCollision(spr1, spr2, 1);
				   		if(spr2.checkHeadBound(spr1.getBounds())) spriteCollision(spr1, spr2, 2);
				   		if(spr2.checkLegBound(spr1.getBounds())) spriteCollision(spr1, spr2, 3);
					}
					if(spr2.hasMultBounds() && spr1.hasMultBounds()){ //spr2 has multiple bounds as well as spr1
						if(spr1.checkLeftBound(spr2.getLeftBound())) spriteCollision(spr1, spr2, 0);
						if(spr1.checkLeftBound(spr2.getRightBound())) spriteCollision(spr1, spr2, 1);
						if(spr1.checkLeftBound(spr2.getHeadBound())) spriteCollision(spr1, spr2, 2);
						if(spr1.checkLeftBound(spr2.getLegBound())) spriteCollision(spr1, spr2, 3);

						if(spr1.checkRightBound(spr2.getLeftBound())) spriteCollision(spr1, spr2, 0);
						if(spr1.checkRightBound(spr2.getRightBound())) spriteCollision(spr1, spr2, 1);
						if(spr1.checkRightBound(spr2.getHeadBound())) spriteCollision(spr1, spr2, 2);
						if(spr1.checkRightBound(spr2.getLegBound())) spriteCollision(spr1, spr2, 3);
						
						if(spr1.checkHeadBound(spr2.getLeftBound())) spriteCollision(spr1, spr2, 0);
						if(spr1.checkHeadBound(spr2.getRightBound())) spriteCollision(spr1, spr2, 1);
						if(spr1.checkHeadBound(spr2.getHeadBound())) spriteCollision(spr1, spr2, 2);
						if(spr1.checkHeadBound(spr2.getLegBound())) spriteCollision(spr1, spr2, 3);
						
						if(spr1.checkLegBound(spr2.getLeftBound())) spriteCollision(spr1, spr2, 0);
						if(spr1.checkLegBound(spr2.getRightBound())) spriteCollision(spr1, spr2, 1);
						if(spr1.checkLegBound(spr2.getHeadBound())) spriteCollision(spr1, spr2, 2);
						if(spr1.checkLegBound(spr2.getLegBound())) spriteCollision(spr1, spr2, 3);
					}
				}
			}
		}
	}
	
	//Same as the above spriteCollision() method but instead the collision is between
	// a sprite and a Tile. Also, currently only with rectangles. The method gets a sprite
	// then gets a each tile, if it intersects any bounds made for that sprite the method 
	// calls a method that can handle an intersection seperately for specific properties.
	protected void tileCollision() {
		for(int i = 0; i < _sprites.size(); i++) {
			AnimatedSprite spr = _sprites.get(i);
			for(int j = 0; j < _tiles.size(); j++) {
				Tile tile = _tiles.get(j);
				if(!spr.hasMultBounds()) {
					if(tile.getTileBounds().intersects(spr.getBounds())) tileCollision(spr, tile, -1);
				} else {
			   		if(spr.checkLeftBound(tile.getTileBounds())) tileCollision(spr, tile, 0);
			   		if(spr.checkRightBound(tile.getTileBounds())) tileCollision(spr, tile, 1);
			   		if(spr.checkHeadBound(tile.getTileBounds())) tileCollision(spr, tile, 2);
			   		if(spr.checkLegBound(tile.getTileBounds())) tileCollision(spr, tile, 3);
				}
			} //end _tiles for loop
		} //end _sprites for loop
	}
	
	//Draw and update animated sprites automatically, they must be in the list
	protected void drawSprites() {
		for(int i = 0; i < _sprites.size(); i++) {
			AnimatedSprite spr = (AnimatedSprite) _sprites.get(i);
			if(spr.alive()) {
				spr.updateFrame();
				spriteDraw(spr);
			}
		}
		for(int i = 0; i < _tiles.size(); i++) _tiles.get(i).updateFrame();
	}
	
	//Delete the sprite that has been killed from the system
	private void purgeSprites() {
		for(int i = 0; i < _sprites.size(); i++) {
			AnimatedSprite spr = _sprites.get(i);
			if(spr.alive() == false) _sprites.remove(i);
		}
	}
	
	//Instead of just adding all of the tiles in a Map to the system for updating,
	// use this method to add a layer of choice(filter). This method currently only 
	// allows Tiles which have properties - solid, event, breakable, etc..
	void addTile(Tile tile) {
		if(tile.hasProperty()) tiles().add(tile);
	}
	
	//Special drawString method which takes an extra parameter. This allows for a
	// newLine character to be used which removes the need for seperate drawString
	// method calls in your code. '\n' makes a new line
	void drawString(Graphics2D g2d, String text, int x, int y) {
        for(String line : text.split("\n")) g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
    }
}