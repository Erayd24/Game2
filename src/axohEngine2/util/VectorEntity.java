package axohEngine2.util;

import java.awt.Shape;

import axohEngine2.entities.BaseGameEntity;

public class VectorEntity extends BaseGameEntity {
	
	VectorEntity() {
		super();
		setShape(null);
	}

	private Shape shape;
	
	public Shape getShape() { return shape; }
	
	public void setShape(Shape shape) { this.shape = shape; }
	
}
