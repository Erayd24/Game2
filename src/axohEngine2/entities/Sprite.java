package axohEngine2.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JFrame;

import axohEngine2.util.Point2D;

public class Sprite extends Object{
	
	protected ImageEntity entity;
	private Image image;
    protected Point2D pos;
    protected Point2D vel;
    protected double rotRate;
    protected int currentState;
    protected String sprType;
    protected boolean _collided;
    protected int _lifespan, _lifeage;
    
    private boolean solid = false;

    //constructor
    Sprite(JFrame frame, Graphics2D g2d) {
        entity = new ImageEntity(frame);
        image = null;
        entity.setGraphics(g2d);
        entity.setAlive(true);
        pos = new Point2D(0, 0);
        vel = new Point2D(0, 0);
        rotRate = 0.0;
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
    
    public Image getImage() { return image; }
    public void setSolid(boolean solid) { this.solid = solid; }
    public boolean solid() { return solid; }
    
    //load bitmap file for individual sprites
    public void load(String filename) {
        entity.load(filename);
    }
    
    //perform affine transformations
    public void transform() {
        entity.setX(pos.X());
        entity.setY(pos.Y());
        entity.transform();
    }

    //draw the image
    public void draw() {
        entity.g2d.drawImage(entity.getImage(), entity.at, entity.frame);
    }

    //draw bounding rectangle around sprite
    public void drawBounds(Color c) {
        entity.g2d.setColor(c);
        entity.g2d.draw(getBounds());
    }

    //methods related to automatic rotation factor
    public double rotationRate() { return rotRate; }
    public void setRotationRate(double rate) { rotRate = rate; }
    public void updateRotation() {
        setFaceAngle(faceAngle() + rotRate);
        if (faceAngle() < 0)
            setFaceAngle(360 - rotRate);
        else if (faceAngle() > 360)
            setFaceAngle(rotRate);
    }

    //generic sprite state variable (alive, dead, collided, etc)
    public int state() { return currentState; }
    public void setState(int state) { currentState = state; }

    //returns a bounding rectangle
    public Rectangle getBounds() { return entity.getBounds(); }

    //generic variable for selectively using sprites
    public boolean alive() { return entity.isAlive(); }
    public void setAlive(boolean alive) { entity.setAlive(alive); }

    //face angle indicates which direction sprite is facing
    public double faceAngle() { return entity.getFaceAngle(); }
    public void setFaceAngle(double angle) {
        entity.setFaceAngle(angle);
    }
    public void setFaceAngle(float angle) {
        entity.setFaceAngle((double) angle);
    }
    public void setFaceAngle(int angle) {
        entity.setFaceAngle((double) angle);
    }

    //move angle indicates direction sprite is moving
    public double moveAngle() { return entity.getMoveAngle(); }
    public void setMoveAngle(double angle) {
        entity.setMoveAngle(angle);
    }
    public void setMoveAngle(float angle) {
        entity.setMoveAngle((double) angle);
    }
    public void setMoveAngle(int angle) {
        entity.setMoveAngle((double) angle);
    }

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
    //check for collision with a point
    public boolean collidesWith(Point2D point) {
        return (getBounds().contains(point.X(), point.Y()));
    }

    public JFrame frame() { return entity.frame; }
    public Graphics2D graphics() { return entity.g2d; }
    public Image image() { return entity.image; }
    public void setImage(Image image) { entity.setImage(image); }

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