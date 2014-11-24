package axohEngine2.map;

import java.awt.Graphics2D;

import javax.swing.JFrame;


public class Map {
	
	private int mapHeight;
	private int mapWidth;
	Tile[] mapTiles;
	private int spriteSize;
	private String _name;
		
	public Map(JFrame frame, Graphics2D g2d, Tile[] tiles, int mapWidth, int mapHeight, String name) {
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		mapTiles = tiles;
		_name = name;
		
		for(int i = 0; i < mapTiles.length; i++) {
			mapTiles[i] = new Tile(mapTiles[i], frame, g2d);
		}
		spriteSize = tiles[0].getSpriteSize();
	}
	
	public void render(JFrame frame, Graphics2D g2d, int xx, int yy) {
		int xt = xx;
			for(int y = 0; y < mapHeight; y++) {
				for(int x = 0; x < mapWidth; x++) {
				mapTiles[x + y * mapWidth].renderTile(xx, yy, g2d, frame);
				if(mapTiles[x + y * mapWidth].hasMob()) mapTiles[x + y * mapWidth].mob().renderMob(xx, yy);
				xx = xx + spriteSize;
			}
			xx = xt;
			yy = yy + spriteSize;
		}
	}
	
	public Tile accessTile(int index) {
		return mapTiles[index];
	}
	
	public int getWidth() { return mapWidth; }
	public int getHeight() { return mapHeight; }
	public String mapName() { return _name; }
}