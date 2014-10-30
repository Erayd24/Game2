package axohEngine2.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class AnimatedSprite extends Sprite {

	private ImageEntity animImage;
	
    BufferedImage image;
    Graphics2D tempSurface;
    
    private int currFrame;
    private int totFrames;
    public String name;
    
    private SpriteSheet sheet;
    private int spriteNumber;
    private int delay;
    private int tempDelay;
    private boolean animating;

    public AnimatedSprite(JFrame frame, Graphics2D g2d, String name) {
        super(frame, g2d);
        animImage = new ImageEntity(frame);
        currFrame = 0;
        totFrames = 0;
        animating = false;
        this.name = name;
        
        delay = 1;
        tempDelay = 1;
    }
        
    public void load(String filename, int width, int height) {
        //load animation bitmap
        animImage.load(filename);

        //frame image is passed to parent class for drawing
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        tempSurface = image.createGraphics();
        super.setImage(image);
    }
    
    public void loadAnim(int frames, int delay) {
        if(frames > 0) {
        	setTotalFrames(frames);
        	animating = true;
        }
       	if(delay > 0) setDelay(delay);
        tempDelay = delay;
    }

    public void setDelay(int delay) { this.delay = delay; }
    public int totalFrames() { return totFrames; }
    public void setTotalFrames(int total) { totFrames = total; }
    public int currentFrame() { return currFrame; }
    public void setAnimation(boolean state) { animating = state; }
    public void setSheet(SpriteSheet sheet) { this.sheet = sheet; }
    
    public Image getAnimImage() { return animImage.getImage(); }
    public void setAnimImage(Image image) { animImage.setImage(image); }
    
    public void setAnimSprite(SpriteSheet sheet, int spriteNumber) {
    	animImage.setImage(super.setSprite(sheet, spriteNumber)); 
    	this.sheet = sheet;
    	this.spriteNumber = spriteNumber;
    	currFrame = spriteNumber;
    }

    public void render(Graphics2D g2d, JFrame frame, int x, int y, int width, int height, int scale) {
    	entity.setX(x);
    	entity.setY(y);
		g2d.drawImage(getAnimImage(), x, y, width * scale, height * scale, frame);	
    }
    
    public void updateFrame() {
    	if(animating) {
	    	tempDelay--;
	    	if(tempDelay % delay == 0) {
	    		if(currentFrame() == spriteNumber - 1 + totalFrames()) {
	    			currFrame = spriteNumber;
	    			animImage.setImage(super.setSprite(sheet, currentFrame()));
			    	tempDelay = delay;
	    			return;
	    		}
		    	currFrame++;
		    	tempDelay = delay;
		    	animImage.setImage(super.setSprite(sheet, currentFrame())); 
	    	}
    	}
    }
}