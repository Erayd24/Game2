package axohEngine2.map;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.SpriteSheet;

public class Tile extends AnimatedSprite {

	private boolean solid;
	private boolean slippery;
	private boolean breakable;
		
	private int spriteSize;
	public SpriteSheet sheet;
	
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
		
		if(solid) setSpriteType("wall");
		setSolid(solid);
		setSprite(sheet, spriteNumber);
	}
	
	//Getters
	public boolean solid() { return solid; }
	public boolean slippery() { return slippery; }
	public boolean breakable() { return breakable; }
	public int getSpriteSize() { return spriteSize; }
	
	public void loadAnim(int frames, int delay) {
		super.setSheet(sheet);
		super.loadAnim(frames, delay);
	}
	
	public void renderTile(int x, int y, int scale) {
		entity.setX(x);
		entity.setY(y);
		g2d.drawImage(getImage(), x, y, spriteSize * scale, spriteSize * scale, frame);
	}
}
