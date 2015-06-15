package axohEngine2.data;

import java.io.Serializable;

public class Data implements Serializable {	
	private static final long serialVersionUID = -4668422157233446222L;
	
	private String _currentMapName;
	private String _currentOverlayName;
	private int _playerX;
	private int _playerY;
	
	public void update(String currentMapName, String currentOverlayName, int playerX, int playerY){
		_currentMapName = currentMapName;
		_currentOverlayName = currentOverlayName;
		_playerX = playerX;
		_playerY = playerY;
	}
	
	public String getMapName() {
		return _currentMapName;
	}
	
	public String getOverlayName() {
		return _currentOverlayName;
	}
	
	public int getPlayerX() {
		return _playerX;
	}
	
	public int getPlayerY() {
		return _playerY;
	}
}
