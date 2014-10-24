package axohEngine2.entities;

import java.awt.Rectangle;

public class Bullet extends VectorEntity {
	
	//Bounding rectangle
	public Rectangle getBounds() {
		Rectangle r;
		r = new Rectangle((int)getX(), (int)getY());
		return r;
	}
	
	//Constructor
	Bullet() {
		//Bullet shape
		setShape(new Rectangle(0, 0, 1, 1));
		setAlive(false);
	}
}