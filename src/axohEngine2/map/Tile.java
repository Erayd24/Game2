package axohEngine2.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.Mob;
import axohEngine2.entities.SpriteSheet;
import axohEngine2.project.TYPE;

public class Tile extends AnimatedSprite {
	
	private boolean _solid;
	private boolean _slippery;
	private boolean _breakable;
	private boolean hasEvent = false;
	private boolean hasMob = false;
	
	private Event event;
	private Mob mob;
	private boolean hasProperty = false;
		
	//Default constructor
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber) {
		super(frame, g2d, sheet, spriteNumber, name);
		_solid = false;
		_slippery = false;
		_breakable = false;
		
		setSprite(sheet, spriteNumber);
	}
	
	//Second constructor used for the most common element of solid
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber, boolean solid) {
		super(frame, g2d, sheet, spriteNumber, name);
		_solid = solid;
		_slippery = false;
		_breakable = false;
		
		if(solid) setSpriteType(TYPE.WALL);
		if(solid) hasProperty = true;
		setSolid(solid); //Sprite super class solid
		setSprite(sheet, spriteNumber);
	}
	
	
	//Third constructor for less commn elements
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber, boolean solid, boolean slippery, boolean breakable) {
		super(frame, g2d, sheet, spriteNumber, name);
		_solid = solid;
		_slippery = slippery;
		_breakable = breakable;
		
		setSolid(solid);
		if(solid || slippery || breakable) hasProperty = true;
		setSprite(getSheet(), getSpriteNumber());
	}
	
	//Constructor for making a new tile from an existing tile for recreation
	public Tile(Tile tile, JFrame frame, Graphics2D g2d) {
		super(frame, g2d, tile.getSheet(), tile.getSpriteNumber(), tile._name);
		_solid = tile.isSolid();
		_slippery = tile.isSlippery();
		_breakable = tile.isBreakable();
		hasEvent = tile.hasEvent();
		hasProperty = tile.hasProperty();
		event = tile.event();
		
		setSolid(_solid);
		setSprite(tile.getSheet(), tile.getSpriteNumber());
	}
	
	public void addEvent(Event event) {
		hasProperty = true;
		this.event = event;
		hasEvent = true;
	}
	
	public Event event() { return event; }
	public boolean hasEvent() { return hasEvent; }
	public void endEvent() {
		event = null; 
		hasEvent = false;
	}
	
	public Mob mob() { return mob; }
	public boolean hasMob() { return hasMob; }
	public void addMob(Mob mob) {
		hasMob = true;
		this.mob = mob;
	}
	
	public boolean isSolid() { return _solid; }
	public boolean isSlippery() { return _slippery; }
	public boolean isBreakable() { return _breakable; }
	public boolean hasProperty() { return hasProperty; }
	
	public void loadAnim(int frames, int delay) {
		super.loadAnim(frames, delay);
	}
	
	public void renderTile(int x, int y, Graphics2D g2d, JFrame frame) {
		g2d.drawImage(getImage(), x, y, getSpriteSize(), getSpriteSize(), frame);
		getEntity().setX(x);
		getEntity().setY(y);
		if(hasMob) mob.updateMob();
	}
	
	public void drawTileBounds(Color c, Graphics2D g2d) {
		g2d.setColor(c);
		g2d.draw(getTileBounds());
	}
	
	public Rectangle getTileBounds() {
		Rectangle r;
		r = new Rectangle((int)entity.getX(), (int)entity.getY(), getSpriteSize(), getSpriteSize());
		return r;
	}
}
