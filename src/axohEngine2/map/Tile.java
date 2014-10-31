package axohEngine2.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.SpriteSheet;

public class Tile extends AnimatedSprite {

	private boolean solid;
	private boolean slippery;
	private boolean breakable;
		
	private int spriteSize;
	public SpriteSheet sheet;
	private int scale;
	
	Graphics2D g2d;
	JFrame frame;
	
	//Default constructor
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber) {
		super(frame, g2d, name);
		this.frame = frame;
		this.g2d = g2d;
		this.sheet = sheet;
		spriteSize = sheet.getSpriteSize();
		solid = false;
		slippery = false;
		breakable = false;
		
		setSprite(sheet, spriteNumber);
	}
	
	//Second constructor used for the most common element of solid
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber, boolean solid) {
		super(frame, g2d, name);
		this.frame = frame;
		this.sheet = sheet;
		spriteSize = sheet.getSpriteSize();
		this.g2d = g2d;
		this.solid = solid;
		slippery = false;
		breakable = false;
		
		if(solid) setSpriteType("wall");
		setSolid(solid);
		setSprite(sheet, spriteNumber);
	}
	
	
	//Third constructor for less commn elements
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber, boolean solid, boolean slippery, boolean breakable) {
		super(frame, g2d, name);
		this.frame = frame;
		this.g2d = g2d;
		this.sheet = sheet;
		spriteSize = sheet.getSpriteSize();
		this.solid = solid;
		this.slippery = slippery;
		this.breakable = breakable;
		
		setSolid(solid);
		setSprite(sheet, spriteNumber);
	}
	
	//Getters
	public boolean isSolid() { return solid; }
	public boolean slippery() { return slippery; }
	public boolean breakable() { return breakable; }
	public int getSpriteSize() { return spriteSize; }
	
	public void loadAnim(int frames, int delay) {
		super.setSheet(sheet);
		super.loadAnim(frames, delay);
	}
	
	public void update(int x, int y) {
		entity.setX(x);
		entity.setY(y);
	}
	
	public void renderTile(int x, int y, int scale) {
		this.scale = scale;
		entity.scale = scale;
		g2d.drawImage(getImage(), x, y, spriteSize * scale, spriteSize * scale, frame);
	}
	
	public Rectangle getTileBounds() {
		Rectangle r;
		r = new Rectangle((int)entity.getX(), (int)entity.getY(), spriteSize * scale, spriteSize * scale);
		return r;
	}
}
