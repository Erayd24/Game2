package axohEngine2.map;


public class Map {
	
	private int mapHeight;
	private int mapWidth;
	private int scale;
	Tile[] mapTiles;
	
	private int spriteSize;
		
	public Map(Tile[] tiles, int scale, int mapWidth, int mapHeight) {
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		this.scale = scale;
		mapTiles = tiles;
		spriteSize = tiles[0].getSpriteSize();
	}
	
	public void render(int xx, int yy) {
		int xt = xx;
		for(int x = mapWidth - 1; x > -1; x--) {
			for(int y = mapHeight - 1; y > -1; y--) {
				mapTiles[x + y * mapWidth].renderTile(xx, yy, scale);
				xx = xx + spriteSize * scale;
				}
			xx = xt;
			yy = yy + spriteSize * scale;
		}
	}
}