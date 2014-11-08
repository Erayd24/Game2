package axohEngine2.map;


public class Map {
	
	private int mapHeight;
	private int mapWidth;
	Tile[] mapTiles;
	private int spriteSize;
		
	public Map(Tile[] tiles, int mapWidth, int mapHeight) {
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		mapTiles = tiles;
		
		for(int i = 0; i < mapTiles.length; i++) {
			mapTiles[i] = new Tile(mapTiles[i]);
		}
		spriteSize = tiles[0].getSpriteSize();
	}
	
	public void render(int xx, int yy) {
		int xt = xx;
			for(int y = 0; y < mapHeight; y++) {
				for(int x = 0; x < mapWidth; x++) {
				mapTiles[x + y * mapWidth].renderTile(xx, yy);
				mapTiles[x + y * mapWidth].getEntity().setX(xx);
				mapTiles[x + y * mapWidth].getEntity().setY(yy);
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
}