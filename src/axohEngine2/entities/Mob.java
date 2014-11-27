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
	
	private boolean _left = false;
	private boolean _right = false;
	private boolean _up = false;
	private boolean _down = false;
	private boolean wasRight = false;
	private boolean wasLeft = false;
	private boolean wasUp = false;
	private boolean wasDown = false;
	
	private boolean randUp = false;
	private boolean randDown = false;
	private boolean randLeft = false;
	private boolean randRight = false;
	
	private boolean waitOn = false;
	private int wait;
	
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
		int r = random.nextInt(7);
		
		if(wait <= 0) {
			waitOn = false;
			randRight = false;
			randUp = false;
			randDown = false;
			randLeft = false;
			_left = false;
			_right = false;
			_up = false;
			_down = false;
		}
		
		if(r == 0 && !waitOn) { //right
			randRight = true;
			waitOn = true;
			wait = random.nextInt(200);
		}
		if(r == 1 && !waitOn) { //left
			randLeft = true;
			waitOn = true;
			wait = random.nextInt(200);
		}
		if(r == 2 && !waitOn) { //up
			randUp = true;
			waitOn = true;
			wait = random.nextInt(200);
		}
		if(r == 3 && !waitOn) { //down
			randDown = true;
			waitOn = true;
			wait = random.nextInt(200);
		}
		if(r >= 4 && !waitOn) { //Not moving
			waitOn = true;
			wait = random.nextInt(200);
			stopAnim();
		}
		
		if(randRight) xa = speed;
		if(randLeft) xa = -speed;
		if(randUp) ya = speed;
		if(randDown) ya = -speed;
		
		if(randRight || randDown || randUp || randLeft) startAnim();
		move(xa, ya);
		if(waitOn) wait--;
	}
	
	private void search() {
		
	}
	
	private void chase() {
		
	}
	
	private void move(int xa, int ya) {
		if(xa < 0) { //left
			xx += xa; 
			
			if(!_left) setAnimTo(leftAnim);
			startAnim();
			_left = true;
		} else if(xa > 0) { //right
			xx += xa; 
			
			if(!_right) setAnimTo(rightAnim);
			startAnim();
			_right = true;
		}
		
		if(ya < 0) {  //up
			yy += ya;

			if(!_up) setAnimTo(upAnim);
			startAnim();
			_up = true;
		} else if(ya > 0) { //down
			yy += ya; 
			
			if(!_down) setAnimTo(downAnim);
			startAnim();
			_down = true;
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