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
		
		for(int i = 0; i < mapTiles.length; i++) {
			mapTiles[i] = new Tile(mapTiles[i]);
		}
	}
	
	public void render(int xx, int yy) {
		int xt = xx;
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				mapTiles[y + x * mapWidth].renderTile(xx, yy, scale);
				xx = xx + spriteSize * scale;
				}
			xx = xt;
			yy = yy + spriteSize * scale;
		}
	}
	
	public Tile accessTile(int index) {
		return mapTiles[index];
	}
}