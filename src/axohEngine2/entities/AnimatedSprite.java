package axohEngine2.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class AnimatedSprite extends Sprite {

	private ImageEntity animImage;
	
    BufferedImage image;
    Graphics2D tempSurface;
    public String _name;
    
    private int currFrame;
    private int totFrames;
    private int delay;
    private int tempDelay;
    private boolean animating;
    private boolean playOnce = false;
    public boolean changeBack = false;
    
	public int leftAnim;
	public int rightAnim;
	public int upAnim;
	public int downAnim;
	public int walkFrames;
	public int walkDelay;
	
	//For unsheathing/ed and attacking
	public int swordLeft;
	public int swordRight;
	public int swordUp;
	public int swordDown;
	public int attackLeft;
	public int attackRight;
	public int attackUp;
	public int attackDown;
	public int unsheathedFrames;
	public int attackFrames;
	public int unsheathedDelay;
	public int attackDelay;

    public AnimatedSprite(JFrame frame, Graphics2D g2d, SpriteSheet sheet, int spriteNumber, String name) {
        super(frame, g2d);
        animImage = new ImageEntity(frame);
        currFrame = 0;
        totFrames = 0;
        animating = false;
        _name = name;
        delay = 1;
        tempDelay = 1;
        
        setSheet(sheet);
        setSpriteNumber(spriteNumber);
        setAnimSprite();
    }
        
    public void load(String filename, int width, int height) {
        //load animation bitmap
        animImage.load(filename);

        //frame image is passed to parent class for drawing
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        tempSurface = image.createGraphics();
        setImage(image);
    }
    
    public void loadAnim(int frames, int delay) {
    	currFrame = getSpriteNumber();
        if(frames > 0) {
        	setTotalFrames(frames);
        	animating = true;
        }
       	if(delay > 0) setDelay(delay);
        tempDelay = delay;
    }

    public void loadMultAnim(int spriteNumLeft, int spriteNumRight, int spriteNumUp, int spriteNumDown, int frames, int delay) {
		leftAnim = spriteNumLeft;
		rightAnim = spriteNumRight;
		upAnim = spriteNumUp;
		downAnim = spriteNumDown;
		walkFrames = frames;
		walkDelay = delay;
		
		currFrame = getSpriteNumber();
        if(frames > 0) {
        	setTotalFrames(frames);
        	animating = true;
        }
       	if(delay > 0) setDelay(delay);
        tempDelay = delay;
	}
    
    public void loadSwordAnim(int unsheathLeft, int unsheathRight, int unsheathUp, int unsheathDown, int swipeLeft, int swipeRight, int swipeUp, int swipeDown, int unsheathFrames, int unsheathDelay, int attackingFrames, int attackingDelay) {
    	swordLeft = unsheathLeft;
    	swordRight = unsheathRight;
    	swordUp = unsheathUp;
    	swordDown = unsheathDown;
    	attackLeft = swipeLeft;
    	attackRight = swipeRight;
    	attackUp = swipeUp;
    	attackDown = swipeDown;
    	unsheathedFrames = unsheathFrames;
    	unsheathedDelay = unsheathDelay;
    	attackFrames = attackingFrames;
    	attackDelay = attackingDelay;
    }
    
    public void setFullAnim(int startFrame, int totalFrames, int delay, int tempDelay){
    	setAnimTo(startFrame);
		setTotalFrames(totalFrames);
		setDelay(delay);
		setTempDelay(tempDelay);
    }
    
    public void setFrame(int frame) { 
    	currFrame = frame; 
		animImage.setImage(setSprite(getSheet(), currentFrame()));
    }
    
    public void setAnimTo(int frame) {
    	currFrame = frame;
    	setSpriteNumber(frame);
		animImage.setImage(setSprite(getSheet(), currentFrame()));
    }
    
    public void stopAnim() { 
    	animating = false; 
    	currFrame = getSpriteNumber();
		animImage.setImage(setSprite(getSheet(), currentFrame()));
    }
    
    public void startAnim() { animating = true; }
    public boolean animating() { return animating; }
    public void playOnce() { playOnce = true; }
    
    public void setDelay(int delay) { this.delay = delay; }
    public int delay() { return delay; }
    public void setTempDelay(int delay) { tempDelay = delay; }
    public void setTotalFrames(int total) { totFrames = total; }
    public void setAnimating(boolean state) { animating = state; }
    public void setAnimImage(Image image) { animImage.setImage(image); }
    
    public int totalFrames() { return totFrames; }
    public int currentFrame() { return currFrame; }
    public Image getAnimImage() { return animImage.getImage(); }
    
    public SpriteSheet getSheet() { return super.sheet; }
    public int getSpriteNumber() { return super.spriteNumber; }
    public int getSpriteSize() { return super.spriteSize; }
    public int getScale() { return super.scale; }
    
    public void setSheet(SpriteSheet sheet) { super.sheet = sheet; }
    public void setSpriteSize(int spriteSize) { super.spriteSize = spriteSize; }
    public void setScale(int scale) { super.scale = scale; }
    public void setSpriteNumber(int spriteNumber) { super.spriteNumber = spriteNumber; }
    
    public void setAnimSprite() {
    	animImage.setImage(setSprite(getSheet(), getSpriteNumber())); 
    	setScale(getSheet().getScale());
    	setSpriteSize(getSheet().getSpriteSize() * getScale());
    	newBound = spriteSize;
    	currFrame = getSpriteNumber();
    }

    public void render(JFrame frame, Graphics2D g2d, int x, int y) {
    	entity.setX(x);
    	entity.setY(y);
		g2d.drawImage(getAnimImage(), x, y, getSpriteSize(), getSpriteSize(), frame);	
    }
    
    public void updateFrame() {
    	if(animating) {
	    	tempDelay--;
	    	if(tempDelay % delay == 0) {
	    		if(currentFrame() == getSpriteNumber() - 1 + totalFrames()) {
	    			currFrame = getSpriteNumber();
	    			animImage.setImage(setSprite(getSheet(), currentFrame()));
			    	tempDelay = delay;
			    	if(playOnce) changeBack = true;
			    	playOnce = false;
	    			return;
	    		}
		    	currFrame++;
		    	tempDelay = delay;
		    	animImage.setImage(setSprite(getSheet(), currentFrame())); 
	    	}
    	}
    }
}