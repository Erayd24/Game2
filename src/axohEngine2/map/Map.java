package axohEngine2.map;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import axohEngine2.entities.Mob;
import axohEngine2.project.TYPE;



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
	
	//Get a tile based on a location and direction of a mob
	//playerX and playerY only matter if the mob in the first parameter is a player, otherwise they dont matter
	public Tile getFrontTile(Mob mob, int playerX, int playerY){
		System.out.println(playerX + " sprite size ");
		int xx = (int) (Math.abs(mob.getXLoc()%spriteSize));
		int yy = (int) (Math.abs(mob.getYLoc()%spriteSize));
		if(mob.getType() == TYPE.PLAYER){
			xx = Math.abs(playerX%spriteSize);
			yy = Math.abs(playerY%spriteSize);
		}
		System.out.println(xx + "ufiusfhsidu");
		if(mob.facingLeft) return mapTiles[(xx - 1) + yy * mapWidth];
		if(mob.facingRight) return mapTiles[(xx + 1) + yy * mapWidth];
		if(mob.facingUp) return mapTiles[xx + (yy - 1) * mapWidth];
		if(mob.facingDown) return mapTiles[xx + (yy + 1) * mapWidth];
		return mapTiles[xx + yy * mapWidth];
	}
	
	public int getWidth() { return mapWidth; }
	public int getHeight() { return mapHeight; }
	public String mapName() { return _name; }
}