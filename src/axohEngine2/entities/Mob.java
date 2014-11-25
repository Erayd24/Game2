package axohEngine2.entities;

import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.project.TYPE;

public class Mob extends AnimatedSprite{
	
	private Random random = new Random();
	private boolean hostile;
	private int health;
	private TYPE ai;
	private int xx;
	private int yy;
	private int speed = 2;
	
	boolean _left = false;
	boolean _right = false;
	boolean _up = false;
	boolean _down = false;
	boolean wasRight = false;
	boolean wasLeft = false;
	boolean wasUp = false;
	boolean wasDown = false;
	boolean randUp = false;
	boolean randDown = false;
	boolean randLeft = false;
	boolean randRight = false;
	
	boolean waitOn = false;
	int wait = 1;
	
	private Graphics2D g2d;
	private JFrame frame;
	
	public Mob(JFrame frame, Graphics2D g2d, SpriteSheet sheet, int spriteNumber, TYPE ai, String name, boolean hostility) {
		super(frame, g2d, sheet, spriteNumber, name);
		this.frame = frame;
		this.g2d = g2d;
		this.ai = ai;
		
		hostile = hostility;
		setName(name);
		health = 0;
		setSolid(true);
		setAlive(true);
		setSpriteType(ai);
	}
	
	public String getName() { return super._name; }
	
	public void setHealth(int health) { this.health = health; }
	public void setAi(TYPE ai) { this.ai = ai; }
	public void setName(String name) { super._name = name; }
	public void setSpeed(int speed) { this.speed = speed; }
	
	public void updateMob() {
		if(ai == TYPE.RANDOMPATH) {
			randomPath();
		}
		if(ai == TYPE.SEARCH) {
			search();
		}
		if(ai == TYPE.CHASE) {
			chase();
		}
		if(ai == TYPE.PLAYER) {
			//TODO: Do I need this?
		}
		if(hostile && health < 0) {
			setAlive(false);
		}
	}
	
	private void randomPath() {
		int xa = 0;
		int ya = 0;
		
		if(wait <= 0) {
			waitOn = false;
			randRight = false;
			randUp = false;
			randDown = false;
			randLeft = false;
			wait = 1;
		}
		
		if(random.nextInt(60) > 50 && !waitOn) { //right
			randRight = true;
			waitOn = true;
			wait = random.nextInt(300);
		}
		if(random.nextInt(60) > 50 && !waitOn) { //left
			randLeft = true;
			waitOn = true;
			wait = random.nextInt(300);
		}
		if(random.nextInt(60) > 50 && !waitOn) { //up
			randUp = true;
			waitOn = true;
			wait = random.nextInt(300);
		}
		if(random.nextInt(60) > 50 && !waitOn) { //down
			randDown = true;
			waitOn = true;
			wait = random.nextInt(300);
		}
		if(random.nextInt(60) > 40 && !waitOn) { //Not moving
			waitOn = true;
			wait = random.nextInt(300);
		}
		
		if(randRight) xa = speed;
		if(randLeft) xa = -speed;
		if(randUp) ya = speed;
		if(randDown) ya = -speed;
		
		move(xa, ya);
		
		if(xa == 0 && ya == 0) stopAnim();
		if(randRight || randDown || randLeft || randUp) wait--;
	}
	
	private void search() {
		
	}
	
	private void chase() {
		
	}
	
	private void move(int xa, int ya) {
		if(xa < 0) {
			xx += xa; //left
			
			if(!_left) {
				setAnimTo(leftAnim);
			}
			startAnim();
			_left = true;
		}
		if(xa > 0) {
			xx += xa; //right
			
			if(!_right) {
				setAnimTo(rightAnim);
			}
			startAnim();
			_right = true;
		}
		if(ya < 0) {
			yy += ya; //up
			
			if(!_up) {
				setAnimTo(upAnim);
			}
			startAnim();
			_up = true;
		}
		if(ya > 0) {
			yy += ya; //down
			
			if(!_down) {
				setAnimTo(downAnim);
			}
			startAnim();
			_down = true;
		}
		
		if(xa == 0 && _left || _right) {
			_left = false;
			_right = false;
		}
		if(ya == 0 && _up || _right) {
			_up = false;
			_down = false;
		}
	}
	
	public void updatePlayer(boolean left, boolean right, boolean up, boolean down) {
		if(left) {
			if(right || up || down) wasLeft = true;
			if(wasLeft && !up && !down && !right) {
				setAnimTo(leftAnim);
				wasLeft = false;
			}
			
			if(!_left) {
				setAnimTo(leftAnim);
				if(hasMultBounds) {
					toggleLeg(true);
					toggleLeft(false);
					toggleRight(false);
					toggleHead(false);
				}
			}
			startAnim();
			_left = true;
		} 
		if (right) {
			if(left || up || down) wasRight = true;
			if(wasRight && !up && !down && !left) {
				setAnimTo(rightAnim);
				wasRight = false;
			}
			
			if(!_right) {
				setAnimTo(rightAnim);
				if(hasMultBounds) {
					toggleLeg(true);
					toggleLeft(false);
					toggleRight(false);
					toggleHead(false);
				}
			}
			startAnim();
			_right = true;
		} 
		if (up) {
			if(left || right || down) wasUp = true;
			if(wasUp && !right && !down && !left) {
				setAnimTo(upAnim);
				wasUp = false;
			}
			
			if(!_up) {
				setAnimTo(upAnim);
				if(hasMultBounds) {
					toggleLeg(false);
					toggleLeft(true);
					toggleRight(true);
					toggleHead(false);
				}
			}
			startAnim();
			_up = true;
		} 
		if (down) {
			if(left || up || right) wasDown = true;
			if(wasDown && !up && !right && !left) {
				setAnimTo(downAnim);
				wasDown = false;
			}
			
			if(!_down) {
				setAnimTo(downAnim);
				if(hasMultBounds) {
					toggleLeg(false);
					toggleLeft(true);
					toggleRight(true);
					toggleHead(false);
				}
			}
			startAnim();
			_down = true;
		}
		
		if(!left) _left = false;
		if(!up) _up = false;
		if(!down) _down = false;
		if(!right) _right = false;
		
		if(!left && !right && !up && !down) {
			stopAnim();
		}
	}
	
	public double getXLoc() { return entity.getX(); }
	public double getYLoc() { return entity.getY(); }
	public void setLoc(int x, int y) { //Relative to current position
		xx = xx + x;
		yy = yy + y;
	}

	public void renderMob(int x, int y) {
		g2d.drawImage(getImage(), x + xx, y + yy, getSpriteSize(), getSpriteSize(), frame);
		entity.setX(x + xx);
		entity.setY(y + yy);
	}
}