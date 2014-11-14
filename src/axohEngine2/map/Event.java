package axohEngine2.map;

public class Event {
	
	//Warping variables
	private int newX, newY;
	private String _mapName;
	private String _overlayName;
	private String eventType;
	private String _name;
		
	public Event(String name, String type) {
		_name = name;
		eventType = type;
	}
	
	public void setWarp(String mapName, String overlayName, int x, int y) {
		_mapName = mapName;
		_overlayName = overlayName;
		newX = x;
		newY = y;
	}
	
	public String getMapName() { return _mapName; }
	public String getOverlayName() { return _overlayName; }
	public int getNewX() { return newX; }
	public int getNewY() { return newY; }
	public String getEventType() { return eventType; }
	public String getname() { return _name; }
}
