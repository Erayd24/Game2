package axohEngine2.data;

import java.io.Serializable;

import axohEngine2.map.Map;

public class Data implements Serializable {	
	private static final long serialVersionUID = -3008487369798218205L;
	
	private Map _currentMap;
	private Map _currentOverlay;
	private double _playerX;
	private double _playerY;
	
	public void update(Map currentMap, Map currentOverlay, double playerX, double playerY){
		_currentMap = currentMap;
		_currentOverlay = currentOverlay;
		_playerX = playerX;
		_playerY = playerY;
	}
	
	public Map getMap() {
		return _currentMap;
	}
	
	public Map getOverlay() {
		return _currentOverlay;
	}
	
	public double getPlayerX() {
		return _playerX;
	}
	
	public double getPlayerY() {
		return _playerY;
	}
}
