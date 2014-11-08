package axohEngine2.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.SpriteSheet;

public class Tile extends AnimatedSprite {

	private boolean _solid;
	private boolean _slippery;
	private boolean _breakable;
	private boolean hasEvent;
	
	private Event event;
	
	Graphics2D _g2d;
	JFrame _frame;
	
	//Default constructor
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber) {
		super(frame, g2d, sheet, spriteNumber, name);
		_frame = frame;
		_g2d = g2d;
		_solid = false;
		_slippery = false;
		_breakable = false;
		hasEvent = false;
		
		setSprite(sheet, spriteNumber);
	}
	
	//Second constructor used for the most common element of solid
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber, boolean solid) {
		super(frame, g2d, sheet, spriteNumber, name);
		_frame = frame;
		_g2d = g2d;
		_solid = solid;
		_slippery = false;
		_breakable = false;
		hasEvent = false;
		
		if(solid) setSpriteType("wall");
		setSolid(solid); //Sprite super class solid
		
		setSprite(sheet, spriteNumber);
	}
	
	
	//Third constructor for less commn elements
	public Tile(JFrame frame, Graphics2D g2d, String name, SpriteSheet sheet, int spriteNumber, boolean solid, boolean slippery, boolean breakable) {
		super(frame, g2d, sheet, spriteNumber, name);
		_frame = frame;
		_g2d = g2d;
		_solid = solid;
		_slippery = slippery;
		_breakable = breakable;
		hasEvent = false;
		
		setSolid(_solid);
		setSprite(getSheet(), getSpriteNumber());
	}
	
	//Constructor for making a new tile from an existing tile for recreation
	public Tile(Tile tile) {
		super(tile._frame, tile._g2d, tile.getSheet(), tile.getSpriteNumber(), tile._name);
		_frame = tile._frame;
		_g2d = tile._g2d;
		_solid = tile.isSolid();
		_slippery = tile.isSlippery();
		_breakable = tile.isBreakable();
		
		setSolid(_solid);
		setSprite(tile.getSheet(), tile.getSpriteNumber());
	}
	
	public void addEvent(Event event) {
		this.event = event;
		hasEvent = true;
	}
	
	public Event event() { return event; }
	public boolean hasEvent() { return hasEvent; }
	public void endEvent() {
		event = null; 
		hasEvent = false;
	}
	
	public boolean isSolid() { return _solid; }
	public boolean isSlippery() { return _slippery; }
	public boolean isBreakable() { return _breakable; }
	
	public void loadAnim(int frames, int delay) {
		super.loadAnim(frames, delay);
	}
	
	public void renderTile(int x, int y) {
		_g2d.drawImage(getImage(), x, y, getSpriteSize(), getSpriteSize(), _frame);
	}
	
	public void drawTileBounds(Color c) {
		_g2d.setColor(c);
		_g2d.draw(getTileBounds());
	}
	
	public Rectangle getTileBounds() {
		Rectangle r;
		r = new Rectangle((int)entity.getX(), (int)entity.getY(), getSpriteSize(), getSpriteSize());
		return r;
	}
}
