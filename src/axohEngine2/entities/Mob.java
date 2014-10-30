package axohEngine2.entities;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class Mob extends AnimatedSprite{
	
	private String name;
	private boolean hostile;
	private SpriteSheet sheet;
	private int spriteSize;
	private boolean solid = true;
	
	private int health;
	private String ai;
	private int xx;
	private int yy;
	
	private Graphics2D g2d;
	private JFrame frame;
	
	public Mob(JFrame frame, Graphics2D g2d, String name, boolean hostility, SpriteSheet sheet, int spriteNumber, String ai) {
		super(frame, g2d, name);
		this.frame = frame;
		this.g2d = g2d;
		hostile = hostility;
		this.name = name;
		this.sheet = sheet;
		spriteSize = sheet.getSpriteSize();
		setSprite(sheet, spriteNumber);
		this.ai = ai;
		health = 0;
		solid = true;
		setAlive(true);
		if(hostile) setSpriteType("enemy");
		if(!hostile) setSpriteType("npc");
		setSolid(solid);
	}
	
	public String getName() { return name; }
	public void setHealth(int health) { this.health = health; }
	
	public void loadAnim(int frames, int delay) {
		super.setSheet(sheet);
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
		g2d.drawImage(getImage(), x + xx, y + yy, spriteSize * scale, spriteSize * scale, frame);
		entity.setX(x + xx);
		entity.setY(y + yy);
	}
}
