package axohEngine2.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet extends Object{

	int _imageCount;
    int _frameCount;
    BufferedImage[] sprites;
    
    String fileName;
    
    public SpriteSheet(String filename, int columns, int rows, int spriteSize) {
    	setSheet(filename, columns, rows, spriteSize);
    }
    
    public void setSheet(String filename, int columns, int rows, int spriteSize) {
    	fileName = filename;
    	try{
    		BufferedImage spriteSheet = ImageIO.read(getClass().getResource(filename));
    		buildSprites(spriteSheet, columns, rows, spriteSize);
    	}catch(IOException e){}
    }
    
    private void buildSprites(BufferedImage spriteSheet, int columns, int rows, int spriteSize){
        sprites = new BufferedImage[rows * columns];
        for(int x = 0; x < columns; x++){
            for(int y = 0; y < rows; y++){
            	for(int i = 0; i < rows * columns; i++) {
            		sprites[i] = spriteSheet.getSubimage(x * (spriteSize), y * (spriteSize), spriteSize, spriteSize);
            		_imageCount++;
            	}
            }
        }
    }
    
    public BufferedImage getSprite(int imageNumber){
        return sprites[imageNumber];
    }

}
