package axohEngine2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.util.Point2D;

@SuppressWarnings("serial")
abstract class Game extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private Thread gameloop;
	
	private LinkedList<AnimatedSprite> _sprites;
	public LinkedList<AnimatedSprite> sprites() { return _sprites; }
	
	private BufferedImage backBuffer;
	private Graphics2D g2d;
	private int screenWidth, screenHeight;
	
	private Point2D mousePos = new Point2D(0, 0);
	private boolean mouseButtons[] = new boolean[4];
		
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
	abstract void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2);
	
	//Constructor
	public Game(int frameRate,int width, int height) {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setSize(size);
		
		desiredRate = frameRate;
		screenWidth = width;
		screenHeight = height;
		
		backBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        g2d = backBuffer.createGraphics();

        //create the internal lists
        _sprites = new LinkedList<AnimatedSprite>();
        
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        gameStartUp();
	}
	
	public Graphics2D graphics() { return g2d; }
	
	public int frameRate() { return _frameRate; }
	
	//Mouse events
	public boolean mouseButton(int btn) { return mouseButtons[btn]; }
	public Point2D mousePosition() { return mousePos; }
	
	public void update(Graphics g) {
		_frameCount++;
		if(System.currentTimeMillis() > startTime + 1000) {
			startTime = System.currentTimeMillis();
			_frameRate = _frameCount;
			_frameCount = 0;
			
			//Delete all dead sprites
			purgeSprites(); 
		}
		
		gameRefreshScreen();
				
		if(!gamePaused()) {
			drawSprites();
		}
		paint(g);
	}
	
	public void paint(Graphics g) {
		g.drawImage(backBuffer, 0, 0, this);
	}
	
	public void start() {
		gameloop = new Thread(this);
		gameloop.start();
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		
		while(t == gameloop) {
			try { 
				Thread.sleep(1000 / desiredRate);
			} catch(InterruptedException e) { e.printStackTrace(); }
			
			if(!gamePaused()) {
				updateSprites();
				testCollision();
			}
			
			gameTimedUpdate();
			update(graphics());
			repaint();
		}
	}
	
	public void stop() {
		gameloop = null;
		gameShutDown();
	}
	
	//Key Listener Methods
	public void keyTyped(KeyEvent k) { }
	
    public void keyPressed(KeyEvent k) {
        gameKeyDown(k.getKeyCode());
    }
    
    public void keyReleased(KeyEvent k) {
        gameKeyUp(k.getKeyCode());
    }
    
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
	
	
	//Miscelaneouse Other methods for games
	//
	//
	//-------------------------------------
	
	protected double calcAngleMoveX(double angle) {
		return (double) (Math.cos(angle * Math.PI / 180));
	}
	
	protected double calcAngleMoveY(double angle) {
		return (double) (Math.sin(angle * Math.PI / 180));
	}
	
	protected void updateSprites() {
		for(int i = 0; i < _sprites.size(); i++) {
			AnimatedSprite spr = (AnimatedSprite) _sprites.get(i);
			if(spr.alive()) {
				spr.updatePosition();
				spr.updateRotation();
				spriteUpdate(spr);
				spr.updateLifetime();
				if(!spr.alive()) {
					spriteDying(spr);
				}
			}
		}
	}
	
	protected void testCollision() {
		for(int first = 0; first < _sprites.size(); first++) {
			AnimatedSprite spr1 = (AnimatedSprite) _sprites.get(first);
			if(spr1.alive()) {
				for(int second = 0; second < _sprites.size(); second++) {
					if(first != second) {
						AnimatedSprite spr2 = (AnimatedSprite) _sprites.get(second);
						if(spr2.alive()) {
							if(spr2.collidesWith(spr1)) {
								spriteCollision(spr1, spr2);
								break;
							} else
								spr1.setCollided(false);
						}
					}
				}
			}
		}
	}
	
	protected void drawSprites() {
		for(int i = 0; i < _sprites.size(); i++) {
			AnimatedSprite spr = (AnimatedSprite) _sprites.get(i);
			if(spr.alive()) {
				spr.updateFrame();
				spr.transform();
				spr.draw();
				spriteDraw(spr);
			}
		}
	}
	
	private void purgeSprites() {
		for(int i = 0; i < _sprites.size(); i++) {
			AnimatedSprite spr = (AnimatedSprite) _sprites.get(i);
			if(!spr.alive()) {
				_sprites.remove(i);
			}
		}
	}
}
