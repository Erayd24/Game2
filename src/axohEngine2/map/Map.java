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
	
	public void render() {
		int xx = 0;
		int yy = 0;
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				mapTiles[x + y * mapWidth].renderTile(xx, yy, scale);
				xx = xx + spriteSize * scale;
				}
			xx = 0;
			yy = yy + spriteSize * scale;
		}
	}
}