package axohEngine2.map;

import axohEngine2.project.Item;
import axohEngine2.project.TYPE;

public class Event {
	
	//Warping variables
	private int newX, newY;
	private String _mapName;
	private String _overlayName;
	private TYPE type;
	private String _name;
	private Item _item;
		
	public Event(String name, TYPE type) {
		_name = name;
		this.type = type;
	}
	
	public void setWarp(String mapName, String overlayName, int x, int y) {
		_mapName = mapName;
		_overlayName = overlayName;
		newX = x;
		newY = y;
	}
	
	public void setItem(Item item) {
		_item = item;
	}
	
	public String getMapName() { return _mapName; }
	public String getOverlayName() { return _overlayName; }
	public int getNewX() { return newX; }
	public int getNewY() { return newY; }
	public TYPE getEventType() { return type; }
	public String getname() { return _name; }
	
	public Item getItem() { return _item; }
}
