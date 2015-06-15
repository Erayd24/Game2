package axohEngine2.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet extends Object {
	
	BufferedImage[] sprites;
    String fileName;
    
    public int sheetHeight;
    public int sheetWidth;
    private int spriteSize;
    private int scale;
    
    public SpriteSheet(String filename, int sheetWidth, int sheetHeight, int spriteSize, int scale) {
    	this.scale = scale;
    	this.spriteSize = spriteSize;
    	this.sheetHeight = sheetHeight;
    	this.sheetWidth = sheetWidth;
    	setSheet(filename, sheetWidth, sheetHeight, spriteSize);
    }
    
    public void setSheet(String filename, int sheetWidth, int sheetHeight, int spriteSize) {
    	fileName = filename;
    	try{
    		BufferedImage spriteSheet = ImageIO.read(getClass().getResource(filename));
    		buildSprites(spriteSheet, sheetWidth, sheetHeight, spriteSize);
    	}catch(IOException e){}
    }
    
    private void buildSprites(BufferedImage spriteSheet, int sheetWidth, int sheetHeight, int spriteSize){
        sprites = new BufferedImage[sheetHeight * sheetWidth];
	    for(int x = 0; x < sheetWidth; x++){
	        for(int y = 0; y < sheetHeight; y++){
	          	sprites[x + y * sheetWidth] = spriteSheet.getSubimage(x * (spriteSize), y * (spriteSize), spriteSize, spriteSize);
            }
	    }
    }
    
    public BufferedImage getSprite(int imageNumber){
        return sprites[imageNumber];
        
    }

    public int getSpriteSize() { return spriteSize; }
    public int getScale() { return scale; }
}