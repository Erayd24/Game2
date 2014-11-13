package axohEngine2.entities;

import java.io.Serializable;

public class BaseGameEntity extends Object implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected boolean alive;
	protected double x, y;
	protected double velX, velY;
	protected double moveAngle, faceAngle;
	
	public boolean isAlive() { return alive; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getVelX() { return velX; }
	public double getVelY() { return velY; }
	
	public void setAlive(boolean alive) { this.alive = alive; }
	public void setX(double x) { this.x = x; }
	public void incX(double i) { this.x += i; }
	public void setY(double y) { this.y = y; }
	public void incY(double i) { this.y += i; }
	public void setVelX(double velX) { this.velX = velX; }
	public void incVelX(double i) { this.velX += i; }
	public void setVelY(double velY) { this.velY = velY; }
	public void incVelY(double i) { this.velY += i; }
	public void setMoveAngle(double angle) { this.moveAngle = angle; }
	public void incMoveAngle(double i) { this.moveAngle += i; }
	public void setFaceAngle(double angle) { this.faceAngle = angle; }
	public void inFaceAngle(double i) { this.faceAngle += i; }
	
	//Constructor
	protected BaseGameEntity() {
		setAlive(false);
		setX(0.0);
		setY(0.0);
		setVelX(0.0);
		setVelY(0.0);
		setMoveAngle(0.0);
		setFaceAngle(0.0);
	}
}
