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
	
	//These next two methods, max or min X or Y are meant to return their respective constraints the map can go to
	//Gives back the maximum negative number the map can move to
	//maxX is far right border while minX is far left border
	public int getMaxX(int screenWidth) { return -1 * (mapWidth * spriteSize - screenWidth); }
	public int getMinX() { return 0; }
	
	//maxY is bottom edge while minY is top edge
	public int getMaxY(int screenHeight) { return -1 * (mapHeight * spriteSize - screenHeight); }
	public int getMinY() { return 0; }
	
	//Get a tile based on a location and direction of a mob
	//playerX and playerY only matter if the mob in the first parameter is a player, otherwise they dont matter
	public Tile getFrontTile(Mob mob, int playerX, int playerY, int centerX, int centerY){
		int xx = (int) Math.floor(Math.abs(mob.getXLoc())/spriteSize);
		int yy = (int) Math.floor(Math.abs(mob.getYLoc())/spriteSize);
		if(mob.getType() == TYPE.PLAYER){
			if(playerX < 0) xx = (int) Math.floor(Math.abs(playerX - centerX)/spriteSize); //width what about the black spaces
			if(playerX > 0) xx = (int) Math.floor((playerX + centerX)/spriteSize);
			if(playerY < 0) yy = (int) Math.floor(Math.abs(playerY - centerY)/spriteSize); //height
			if(playerY > 0) yy = (int) Math.floor((playerY + centerY)/spriteSize); //height
		}
		System.out.println((xx + " xx " + yy + " yy ") + " ufiusfhsidu " + spriteSize);
		if(mob.facingLeft) return mapTiles[(xx - 1) + yy * mapWidth]; //left tile
		if(mob.facingRight) return mapTiles[(xx + 1) + yy * mapWidth]; //right
		if(mob.facingUp) return mapTiles[xx + (yy - 1) * mapWidth]; //up
		if(mob.facingDown) return mapTiles[xx + (yy + 1) * mapWidth]; //down
		return mapTiles[xx + yy * mapWidth]; //This line should never run, it's here for formality
	}
	
	public int getWidth() { return mapWidth; }
	public int getHeight() { return mapHeight; }
	public String mapName() { return _name; }
}