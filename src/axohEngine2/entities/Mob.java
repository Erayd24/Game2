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
	private int speed = 5;
	
	boolean _left = false;
	boolean _right = false;
	boolean _up = false;
	boolean _down = false;
	boolean wasRight = false;
	boolean wasLeft = false;
	boolean wasUp = false;
	boolean wasDown = false;
	
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
		if(60 % (random.nextInt(60) + 30) == 0) {
			xx += random.nextInt(speed);
			yy += random.nextInt(speed);
			System.out.println("inside");
		}
	}
	
	private void search() {
		
	}
	
	private void chase() {
		
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