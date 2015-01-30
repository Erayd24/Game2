package axohEngine2.project;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import axohEngine2.entities.Sprite;
import axohEngine2.entities.SpriteSheet;

public class Item extends Sprite{

	private int health;
	private int damage;
	private boolean healStatus = false;
	private boolean healItem = false;
	private boolean attackItem = false;
	private boolean keyItem = false;
	private boolean isEquipment;
	private String status;
	private String _name;
	
	public Item(JFrame frame, Graphics2D g2d, SpriteSheet sheet, int spriteNumber, String name, boolean equipment) {
		super(frame, g2d);
		setSprite(sheet, spriteNumber);
		this.sheet = sheet;
		isEquipment = equipment;
		_name = name;
	}
	
	public void render(JFrame frame, Graphics2D g2d, int x, int y) {
		g2d.drawImage(getImage(), x, y, getSpriteSize(), getSpriteSize(), frame);
	}
	
	public void setHealItem(int healingAmount, boolean healsStatus, String statusToHeal) {
		health = healingAmount;
		healStatus = healsStatus;
		status = statusToHeal;
		healItem = true;
	}
	
	public void setAttackItem(int damageAmount, String statusAilment) {
		damage = damageAmount;
		status = statusAilment;
		attackItem = true;
	}
	
	public void setKeyItem() {
		keyItem = true;
	}
	
	public String getName() {
		return _name;
	}
	
	public boolean isEquimpent() { return isEquipment; }
	
	//Remember to update the useItem() method under InGameMenu.java when adding a new group of items. All are handled seperately
	public int checkItem() { // TODO: Under construction
		if(keyItem) {
			return 1;
		}
		if(healItem) {
			return 2;
		}
		if(attackItem) {
			return 3;
		}
		return -1;
	}
	
	public int getHealth() { return health; }
}
