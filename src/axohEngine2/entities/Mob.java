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
	private boolean attacking = false;
	private boolean unsheathed = false;
	private int lastPressed;
	
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
	public void resetMovement() {
		randRight = false;
		randLeft = false;
		randUp = false;
		randDown = false;
		wait = 0;
		waitOn = false;
		_left = false;
		_right = false;
		_up = false;
		_down = false;
	}
	
	public void stop() {
		if(ai == TYPE.RANDOMPATH){
			randRight = false;
			randUp = false;
			randDown = false;
			randLeft = false;
			waitOn = true;
			wait = 100 + random.nextInt(200);
			stopAnim();
		}
	}
	
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
				
			if(!_left && !unsheathed) setAnimTo(leftAnim);
			if(!_left && unsheathed) setFullAnim(swordLeft, unsheathedFrames, unsheathedDelay, unsheathedDelay);
			startAnim();
			_left = true;
		} else if(xa > 0) { //right
			xx += xa; 
			
			if(!_right && !unsheathed) setAnimTo(rightAnim);
			if(!_right && unsheathed) setFullAnim(swordRight, unsheathedFrames, unsheathedDelay, unsheathedDelay);
			startAnim();
			_right = true;
		}
			
		if(ya < 0) {  //up
			yy += ya;

			if(!_up && !unsheathed) setAnimTo(upAnim);
			if(!_up && unsheathed) setFullAnim(swordUp, unsheathedFrames, unsheathedDelay, unsheathedDelay);
			startAnim();
			_up = true;
		} else if(ya > 0) { //down
			yy += ya; 
			
			if(!_down && !unsheathed) setAnimTo(downAnim);
			if(!_down && unsheathed) setFullAnim(swordDown, unsheathedFrames, unsheathedDelay, unsheathedDelay);
			startAnim();
			_down = true;
		}
	}
	
	public void updatePlayer(boolean left, boolean right, boolean up, boolean down) {
		if(left) {
			if(right || up || down) wasLeft = true;
			if(wasLeft && !up && !down && !right) {
				if(!unsheathed) setAnimTo(leftAnim);
				if(unsheathed) setAnimTo(swordLeft);
				toggleLeg(true);
				toggleLeft(false);
				toggleRight(false);
				toggleHead(false);
				wasLeft = false;
			}
			
			if(!_left) {
				if(!unsheathed) setAnimTo(leftAnim);
				if(unsheathed) setAnimTo(swordLeft);
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
				if(!unsheathed) setAnimTo(rightAnim);
				if(unsheathed) setAnimTo(swordRight);
				toggleLeg(true);
				toggleLeft(false);
				toggleRight(false);
				toggleHead(false);
				wasRight = false;
			}
			
			if(!_right) {
				if(!unsheathed) setAnimTo(rightAnim);
				if(unsheathed) setAnimTo(swordRight);
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
				if(!unsheathed) setAnimTo(upAnim);
				if(unsheathed) setAnimTo(swordUp);
				toggleLeg(false);
				toggleLeft(true);
				toggleRight(true);
				toggleHead(true);
				wasUp = false;
			}
			
			if(!_up) {
				if(!unsheathed) setAnimTo(upAnim);
				if(unsheathed) setAnimTo(swordUp);
				if(hasMultBounds) {
					toggleLeg(false);
					toggleLeft(true);
					toggleRight(true);
					toggleHead(true);
				}
			}
			startAnim();
			_up = true;
		} 
		if (down) {
			if(left || up || right) wasDown = true;
			if(wasDown && !up && !right && !left) {
				if(!unsheathed) setAnimTo(downAnim);
				if(unsheathed) setAnimTo(swordDown);
				toggleLeg(false);
				toggleLeft(true);
				toggleRight(true);
				toggleHead(true);
				wasDown = false;
			}
			
			if(!_down) {
				if(!unsheathed) setAnimTo(downAnim);
				if(unsheathed) setAnimTo(swordDown);
				if(hasMultBounds) {
					toggleLeg(false);
					toggleLeft(true);
					toggleRight(true);
					toggleHead(true);
				}
			}
			startAnim();
			_down = true;
		}
		
		if(!left) _left = false; lastPressed = 1;
		if(!up) _up = false; lastPressed = 3;
		if(!down) _down = false; lastPressed = 4;
		if(!right) _right = false; lastPressed = 2;
		
		if(!left && !right && !up && !down) {
			stopAnim();
		}
		
		if(changeBack) {
			if(unsheathed && attacking){
				if(_left) setFullAnim(swordLeft, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_right) setFullAnim(swordRight, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_up) setFullAnim(swordUp, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_down) setFullAnim(swordDown, unsheathedFrames, unsheathedDelay, unsheathedDelay);			
			}
			if(unsheathed && !attacking){
				if(_left) setFullAnim(swordLeft, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_right) setFullAnim(swordRight, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_up) setFullAnim(swordUp, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_down) setFullAnim(swordDown, unsheathedFrames, unsheathedDelay, unsheathedDelay);				
			}
			if(!unsheathed && !attacking){
				if(_left) setFullAnim(leftAnim, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_right) setFullAnim(rightAnim, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_up) setFullAnim(upAnim, unsheathedFrames, unsheathedDelay, unsheathedDelay);
				if(_down) setFullAnim(downAnim, unsheathedFrames, unsheathedDelay, unsheathedDelay);
			}
			attacking = false;
			changeBack = false;
		}
		System.out.println("left: " + _left + " right: " + _right + " up: " + _up + " down: " + _down + " unsheathed: " + unsheathed);
	}
	
	//Check to see if a mob is currently attacking or change the state of whether it is attacking or not
	public void changeSheath() {
		unsheathed = !unsheathed;
		if(_left) setFullAnim(unshLeft, unshFrames, unshDelay, unshDelay);
		if(_right) setFullAnim(unshRight, unshFrames, unshDelay, unshDelay);
		if(_up) setFullAnim(unshUp, unshFrames, unshDelay, unshDelay);
		if(_down) setFullAnim(unshDown, unshFrames, unshDelay, unshDelay);
		playOnce();
	}
	
	public boolean isUnsheathed() { return unsheathed; }
	public boolean attacking() { return attacking; }	
	public void attack() {
		attacking = true;
		if(_left) setFullAnim(attackLeft, attackFrames, attackDelay, attackDelay);
		if(_right) setFullAnim(attackRight, attackFrames, attackDelay, attackDelay);
		if(_up) setFullAnim(attackUp, attackFrames, attackDelay, attackDelay);
		if(_down) setFullAnim(attackDown, attackFrames, attackDelay, attackDelay);
		playOnce();
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