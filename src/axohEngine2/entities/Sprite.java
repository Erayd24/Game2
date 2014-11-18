package axohEngine2.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Sprite extends Object {
	
	protected ImageEntity entity;
	private Image image;
	
    protected int currentState;
    protected String sprType;
    protected boolean _collided;
    protected int _lifespan, _lifeage;
    
    protected int spriteSize;
    protected int _boundLegX, _boundLegY;
    protected int _boundLeftX, _boundLeftY;
    protected int _boundRightX, _boundRightY;
    protected int _boundHeadX, _boundHeadY;
    protected boolean left = false, right = false, head = false, leg = false;
    protected boolean hasMultBounds = false;
    protected int newBound;
    protected int spriteNumber;
    protected SpriteSheet sheet;
    protected int scale;
    protected int nx, ny;
    protected int cx, cy;
    
    private boolean solid = false;

    //constructor
    public Sprite(JFrame frame, Graphics2D g2d) {
        entity = new ImageEntity(frame);
        image = null;
        entity.setGraphics(g2d);
        entity.setAlive(true);
        currentState = 0;
        _collided = false;
        _lifespan = 0;
        _lifeage = 0;
    }
    
    public Image setSprite(SpriteSheet sheet, int spriteNumber) {
    	image = (Image) sheet.getSprite(spriteNumber);
    	entity.setImage(image);
    	return image;
    }
    
    public void setBounds(int boundSize, int x, int y) {
    	newBound = boundSize * scale;
    	nx = x;
    	ny = y;
    }
    
    public void setMultBounds(int boundSize, int boundLegX, int boundLegY, int boundLeftX, int boundLeftY, int boundRightX, int boundRightY, int boundHeadX, int boundHeadY) {
    	newBound = boundSize * scale;
    	nx = boundLegX;
    	ny = boundLegY;
    	_boundLegX = boundLegX;
    	_boundLegY = boundLegY;
    	_boundLeftX = boundLeftX;
    	_boundLeftY = boundLeftY;
    	_boundRightX = boundRightX;
    	_boundRightY = boundRightY;
    	_boundHeadX = boundHeadX;
    	_boundHeadY = boundHeadY;
    	hasMultBounds = true;
    }
    
    public void toggleLeg(boolean change) { leg = change; }
    public void toggleLeft(boolean change) { left = change; }
    public void toggleRight(boolean change) { right = change; }
    public void toggleHead(boolean change) { head = change; }
   
    public void allBoundsOff() {
    	leg = false;
    	head = false;
    	left = false;
    	right = false;
    }
    
    public boolean checkLeftBound(Rectangle r) {
    	if(left){ if(r.intersects(entity.getBounds(newBound, _boundLeftX, _boundLeftY))) return true; }
    	return false;
    }
    public boolean checkRightBound(Rectangle r) {
    	if(right){ if(r.intersects(entity.getBounds(newBound, _boundRightX, _boundRightY))) return true; }
    	return false;
    }
    public boolean checkHeadBound(Rectangle r) {
    	if(head){ if(r.intersects(entity.getBounds(newBound, _boundHeadX, _boundHeadY))) return true; }
    	return false;
    }
    public boolean checkLegBound(Rectangle r) {
    	if(leg){ if(r.intersects(entity.getBounds(newBound, _boundLegX, _boundLegY))) return true; }
    	return false;
    }
    
    public Image getImage() { return image; }
    public void setSolid(boolean solid) { this.solid = solid; }
    public boolean solid() { return solid; }
    public int getBoundSize() { return newBound; }
    
    public double getBoundX(int hitDir) { 
    	if(hitDir == 0) return entity.getX() + _boundLeftX;
    	if(hitDir == 1) return entity.getX() + _boundRightX;
    	if(hitDir == 2) return entity.getX() + _boundHeadX;
    	if(hitDir == 3) return entity.getX() + _boundLegX;
    	return entity.getX() + nx;
    }
    public double getBoundY(int hitDir) {
    	if(hitDir == 0) return entity.getY() + _boundLeftY;
    	if(hitDir == 1) return entity.getY() + _boundRightY;
    	if(hitDir == 2) return entity.getY() + _boundHeadY;
    	if(hitDir == 3) return entity.getY() + _boundLegY;
    	return entity.getY() + ny; 
    }
    
    public int legBoundX() { return _boundLegX; }
    public int legBoundY() { return _boundLegY; }
    public int leftBoundX() { return _boundLeftX; }
    public int leftBoundY() { return _boundLeftY; }
    public int rightBoundX() { return _boundRightX; }
    public int rightBoundY() { return _boundRightY; }
    public int headBoundX() { return _boundHeadX; }
    public int headBoundY() { return _boundHeadY; }
    public boolean hasMultBounds() { return hasMultBounds; }

    //load bitmap file for individual sprites
    public void load(String filename) {
        entity.load(filename);
    }
    
    //draw bounding rectangle around sprite
    public void drawBounds(Color c) {
        entity.g2d.setColor(c);
        if(!hasMultBounds) entity.g2d.draw(getBounds());
        if(hasMultBounds) {
        	if(leg) entity.g2d.draw(getEntity().getBounds(newBound, _boundLegX, _boundLegY));
        	if(left) entity.g2d.draw(getEntity().getBounds(newBound, _boundLeftX, _boundLeftY));
        	if(right) entity.g2d.draw(getEntity().getBounds(newBound, _boundRightX, _boundRightY));
        	if(head) entity.g2d.draw(getEntity().getBounds(newBound, _boundHeadX, _boundHeadY));
        }
    }

    //generic sprite state variable (alive, dead, collided, etc)
    public int state() { return currentState; }
    public void setState(int state) { currentState = state; }

    //returns a bounding rectangle
    public Rectangle getBounds() { 
	    	if(nx != 0 || ny != 0) return entity.getBounds(newBound, nx, ny); 
	    	else return entity.getBounds(newBound);
    }
    
    //generic variable for selectively using sprites
    public boolean alive() { return entity.isAlive(); }
    public void setAlive(boolean alive) { entity.setAlive(alive); }

    //returns the source image width/height
    public int imageWidth() { return entity.width(); }
    public int imageHeight() { return entity.height(); }

    //check for collision with a rectangular shape
    public boolean collidesWith(Rectangle rect) {
        return (rect.intersects(getBounds()));
    }
    //check for collision with another sprite
    public boolean collidesWith(Sprite sprite) {
        return (getBounds().intersects(sprite.getBounds()));
    }

    public JFrame frame() { return entity.frame; }
    public Graphics2D graphics() { return entity.g2d; }
    public Image image() { return entity.image; }
    public void setImage(Image image) { entity.setImage(image); }
    public ImageEntity getEntity() { return entity; }

    public String spriteType() { return sprType; }
    public void setSpriteType(String type) { sprType = type; }

    public boolean collided() { return _collided; }
    public void setCollided(boolean collide) { _collided = collide; }

    public int lifespan() { return _lifespan; }
    public void setLifespan(int life) { _lifespan = life; }
    public int lifeage() { return _lifeage; }
    public void setLifeage(int age) { _lifeage = age; }
    public void updateLifetime() {
        //if lifespan is used, it must be > 0
        if (_lifespan > 0) {
            _lifeage++;
            if (_lifeage > _lifespan) { //Set a time limit for a sprite to exist
                setAlive(false);
                _lifeage = 0;
            }
        }
    }
}