package axohEngine2.data;

import java.io.Serializable;

public class Data implements Serializable {	
	private static final long serialVersionUID = -4668422157233446222L;
	
	private String _currentMapName;
	private String _currentOverlayName;
	private double _playerX;
	private double _playerY;
	
	public void update(String currentMapName, String currentOverlayName, double playerX, double playerY){
		_currentMapName = currentMapName;
		_currentOverlayName = currentOverlayName;
		_playerX = playerX;
		_playerY = playerY;
	}
	
	public String getMap() {
		return _currentMapName;
	}
	
	public String getOverlay() {
		return _currentOverlayName;
	}
	
	public double getPlayerX() {
		return _playerX;
	}
	
	public double getPlayerY() {
		return _playerY;
	}
}
