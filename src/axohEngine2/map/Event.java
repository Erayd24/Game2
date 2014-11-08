package axohEngine2.map;


public class Event {

	//Warping variables
	private int newX, newY;
	private Map _map, _mapOverlay;
	private String eventType;
	private String _name;
		
	public Event(String name, String type) {
		_name = name;
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
	public String getname() { return _name; }
}
