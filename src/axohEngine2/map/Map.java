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
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				mapTiles[y + x * mapWidth].renderTile(xx, yy);
				mapTiles[y + x * mapWidth].getEntity().setX(xx);
				mapTiles[y + x * mapWidth].getEntity().setY(yy);
				xx = xx + spriteSize;
				}
			xx = xt;
			yy = yy + spriteSize;
		}
	}
	
	public Tile accessTile(int index) {
		return mapTiles[index];
	}
}