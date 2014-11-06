package axohEngine2.map;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.SpriteSheet;

public class Event extends AnimatedSprite {

	//Warping variables
	private int newX, newY;
	private Map _map, _mapOverlay;
	private String eventType;
	
	private Graphics2D _g2d;
	private JFrame _frame;
	
	public Event(JFrame frame, Graphics2D g2d, SpriteSheet sheet, int spriteNumber, String name, String type) {
		super(frame, g2d, sheet, spriteNumber, name);
		_g2d = g2d;
		_frame = frame;
		eventType = type;
	}
	
	public void setWarp(Map map, Map overlay, int x, int y) {
		_map = map;
		_mapOverlay = overlay;
		newX = x;
		newY = y;
	}
	
	public Map getMap() { return _map; }
	public Map getOverlay() { return _mapOverlay; }
	public int getNewX() { return newX; }
	public int getNewY() { return newY; }
	public String getEventType() { return eventType; }

	public void renderEvent(int x, int y) {
		_g2d.drawImage(getImage(), x, y, getSpriteSize(), getSpriteSize(), _frame);
	}
}
