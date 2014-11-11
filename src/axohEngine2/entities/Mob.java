package axohEngine2.entities;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class Mob extends AnimatedSprite{
	
	private boolean hostile;
	private int health;
	private String ai;
	private int xx;
	private int yy;
	
	boolean _left = false;
	boolean _right = false;
	boolean _up = false;
	boolean _down = false;
	
	private Graphics2D g2d;
	private JFrame frame;
	
	public Mob(JFrame frame, Graphics2D g2d, String name, boolean hostility, SpriteSheet sheet, int spriteNumber, String ai) {
		super(frame, g2d, sheet, spriteNumber, name);
		this.frame = frame;
		this.g2d = g2d;
		this.ai = ai;
		
		hostile = hostility;
		setName(name);
		health = 0;
		setSolid(true);
		setAlive(true);
		if(hostile) setSpriteType("enemy");
		if(!hostile) setSpriteType("npc");
		if(ai == "player") setSpriteType(ai);
	}
	
	public String getName() { return super._name; }
	
	public void setHealth(int health) { this.health = health; }
	public void setAi(String ai) { this.ai = ai; }
	public void setName(String name) { super._name = name; }
	
	public void updateMob() {
		if(ai == "RandomPath") {
			randomPath();
		}
		
		if(ai == "Search") {
			search();
		}
		
		if(ai == "Chase") {
			chase();
		}
		
		if(ai == "player") {
		
		}
		if(hostile && health < 0) {
			setAlive(false);
		}
	}
	
	private void randomPath() {
		
	}
	
	private void search() {
		
	}
	
	private void chase() {
		
	}
	
	public void updatePlayer(boolean left, boolean right, boolean up, boolean down) {
		if(left) {
			if(!_left) setAnimTo(leftAnim);
			startAnim();
			_left = true;
		} else if (right) {
			if(!_right) setAnimTo(rightAnim);
			startAnim();
			_right = true;
		} else if (up) {
			if(!_up) setAnimTo(upAnim);
			startAnim();
			_up = true;
		} else if (down) {
			if(!_down) setAnimTo(downAnim);
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
	public void setLoc(int x, int y) {
		xx = xx + x;
		yy = yy + y;
	}

	public void renderMob(int x, int y) {
		g2d.drawImage(getImage(), x + xx, y + yy, getSpriteSize(), getSpriteSize(), frame);
		entity.setX(x + xx);
		entity.setY(y + yy);
	}
}