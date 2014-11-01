package axohEngine2.entities;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class Mob extends AnimatedSprite{
	
	private boolean hostile;
	
	private int health;
	private String ai;
	private int xx;
	private int yy;
	
	private Graphics2D g2d;
	private JFrame frame;
	
	public Mob(JFrame frame, Graphics2D g2d, String name, boolean hostility, SpriteSheet sheet, int spriteNumber, String ai) {
		super(frame, g2d, sheet, spriteNumber, name);
		this.frame = frame;
		this.g2d = g2d;
		hostile = hostility;
		setName(name);
		this.ai = ai;
		health = 0;
		setSolid(true);
		setAlive(true);
		if(hostile) setSpriteType("enemy");
		if(!hostile) setSpriteType("npc");
	}
	
	public String getName() { return super._name; }
	public void setHealth(int health) { this.health = health; }
	
	public void setName(String name) { super._name = name; }
	
	public void loadAnim(int frames, int delay) {
		super.loadAnim(frames, delay);
	}
	
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
	
	public void renderMob(int x, int y, int scale) {
		g2d.drawImage(getImage(), x + xx, y + yy, getSpriteSize(), getSpriteSize(), frame);
		entity.setX(x + xx);
		entity.setY(y + yy);
	}
}
