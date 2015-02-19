package axohEngine2.entities;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.project.TYPE;

public class Mob extends AnimatedSprite{
	
	private Random random = new Random();
	private LinkedList<Attack> attacks;
	private boolean hostile;
	private int health;
	private TYPE ai;
	private int xx;
	private int yy;
	private int speed = 2;
	private boolean attacking;
	private boolean unsheathed = false;

	private boolean wasRight = false; //Which direction the player was pressing in order to correct animations
	private boolean wasLeft = false;
	private boolean wasUp = false;
	private boolean wasDown = false;

	private DIRECTION moveDir; //Which direction the player is moving
	private DIRECTION direction; //Which way the player is facing
	private DIRECTION randomDir;
	
	private boolean waitOn = false;
	private int wait;
	
	private Graphics2D g2d;
	private JFrame frame;
	
	public Mob(JFrame frame, Graphics2D g2d, SpriteSheet sheet, int spriteNumber, TYPE ai, String name, boolean hostility) {
		super(frame, g2d, sheet, spriteNumber, name);
		attacks = new LinkedList<Attack>();
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
	public TYPE getType() { return ai; }
	public void setSpeed(int speed) { this.speed = speed; }
	public void resetMovement() {
		randomDir = DIRECTION.NONE;
		wait = 0;
		waitOn = false;
		moveDir = DIRECTION.NONE;
	}
	
	public void stop() {
		if(ai == TYPE.RANDOMPATH){
			randomDir = DIRECTION.NONE;
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
			randomDir = DIRECTION.NONE;
			moveDir = DIRECTION.NONE;
		}
		
		if(r == 0 && !waitOn) { //right
			randomDir = DIRECTION.RIGHT;
			waitOn = true;
			wait = random.nextInt(200);
		}
		if(r == 1 && !waitOn) { //left
			randomDir = DIRECTION.LEFT;
			waitOn = true;
			wait = random.nextInt(200);
		}
		if(r == 2 && !waitOn) { //up
			randomDir = DIRECTION.UP;
			waitOn = true;
			wait = random.nextInt(200);
		}
		if(r == 3 && !waitOn) { //down
			randomDir = DIRECTION.DOWN;
			waitOn = true;
			wait = random.nextInt(200);
		}
		if(r >= 4 && !waitOn) { //Not moving
			waitOn = true;
			wait = random.nextInt(200);
			stopAnim();
		}
		
		if(randomDir == DIRECTION.RIGHT) xa = speed; startAnim();
		if(randomDir == DIRECTION.LEFT) xa = -speed; startAnim();
		if(randomDir == DIRECTION.UP) ya = speed; startAnim();
		if(randomDir == DIRECTION.DOWN) ya = -speed; startAnim();
		
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
				
			if(moveDir != DIRECTION.LEFT) setAnimTo(leftAnim);
			startAnim();
			moveDir = DIRECTION.LEFT;
		} else if(xa > 0) { //right
			xx += xa; 
			
			if(moveDir != DIRECTION.RIGHT) setAnimTo(rightAnim);
			startAnim();
			moveDir = DIRECTION.RIGHT;
		}
			
		if(ya < 0) {  //up
			yy += ya;

			if(moveDir != DIRECTION.UP) setAnimTo(upAnim);
			startAnim();
			moveDir = DIRECTION.UP;
		} else if(ya > 0) { //down
			yy += ya; 
			
			if(moveDir != DIRECTION.DOWN) setAnimTo(downAnim);
			startAnim();
			moveDir = DIRECTION.DOWN;
		}
		if(xa == 0 && ya == 0) stopAnim();
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
				direction = DIRECTION.LEFT;
			}
			
			if(moveDir != DIRECTION.LEFT) {
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
			moveDir = DIRECTION.LEFT;
			direction = DIRECTION.LEFT;
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
				direction = DIRECTION.RIGHT;
			}
			
			if(moveDir != DIRECTION.RIGHT) {
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
			moveDir = DIRECTION.RIGHT;
			direction = DIRECTION.RIGHT;
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
				direction = DIRECTION.UP;
			}
			
			if(moveDir != DIRECTION.UP) {
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
			moveDir = DIRECTION.UP;
			direction = DIRECTION.UP;
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
				direction = DIRECTION.DOWN;
			}
			
			if(moveDir != DIRECTION.DOWN) {
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
			moveDir = DIRECTION.DOWN;
			direction = DIRECTION.DOWN;
		}
		
		if(!playOnce) attacking = false;
		if(!left && !right && !up && !down) {
			stopAnim();
		}
	}
	
	//Check to see if a mob is currently attacking or change the state of whether it is attacking or not
	public void changeSheath() {
		unsheathed = !unsheathed;
		if(direction == DIRECTION.LEFT) {
			setFullAnim(unshLeft, unshFrames, unshDelay, unshDelay);
			if(unsheathed) playOnce(swordLeft, unsheathedDelay, unsheathedFrames, unshLeft + unshFrames);
			else playOnce(leftAnim, unsheathedDelay, unsheathedFrames, unshLeft + unshFrames);
		}
		if(direction == DIRECTION.RIGHT) {
			setFullAnim(unshRight, unshFrames, unshDelay, unshDelay);
			if(unsheathed) playOnce(swordRight, unsheathedDelay, unsheathedFrames, unshRight + unshFrames);
			else playOnce(rightAnim, unsheathedDelay, unsheathedFrames, unshRight + unshFrames);
		}
		if(direction == DIRECTION.UP) {
			setFullAnim(unshUp, unshFrames, unshDelay, unshDelay);
			if(unsheathed) playOnce(swordUp, unsheathedDelay, unsheathedFrames, unshUp + unshFrames);
			else playOnce(upAnim, unsheathedDelay, unsheathedFrames, unshUp + unshFrames);
		}
		if(direction == DIRECTION.DOWN) {
			setFullAnim(unshDown, unshFrames, unshDelay, unshDelay);
			if(unsheathed) playOnce(swordDown, unsheathedDelay, unsheathedFrames, unshDown + unshFrames);
			else playOnce(downAnim, unsheathedDelay, unsheathedFrames, unshDown + unshFrames);
		}
	}
	
	public boolean isUnsheathed() { return unsheathed; }
	public boolean attacking() { return attacking; }	
	public void attack() {
		attacking = true;
			if(direction == DIRECTION.LEFT) {
				setFullAnim(attackLeft, attackFrames, attackDelay, attackDelay);
				playOnce(swordLeft, unsheathedDelay, unsheathedFrames, attackLeft + attackFrames);
			}
			if(direction == DIRECTION.RIGHT) {
				setFullAnim(attackRight, attackFrames, attackDelay, attackDelay);
				playOnce(swordRight, unsheathedDelay, unsheathedFrames, attackRight + attackFrames);
			}
			if(direction == DIRECTION.UP) {
				setFullAnim(attackUp, attackFrames, attackDelay, attackDelay);
				playOnce(swordUp, unsheathedDelay, unsheathedFrames, attackUp + attackFrames);
			}
			if(direction == DIRECTION.DOWN) {
				setFullAnim(attackDown, attackFrames, attackDelay, attackDelay);
				playOnce(swordDown, unsheathedDelay, unsheathedFrames, attackDown + attackFrames);
			}
	}

	//List of attacks a mob could have
	public LinkedList<Attack> attacks() { return attacks; }
	
	public void takeDamage(int damage){
		health -= damage - random.nextInt(damage%5);
	}
	public int health() { return health; }
	
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