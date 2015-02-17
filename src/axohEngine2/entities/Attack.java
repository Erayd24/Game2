package axohEngine2.entities;

import java.awt.Rectangle;

public class Attack {

	//Variables for moving around with weapon or item out
	private int animUp;
	private int animDown;
	private int animLeft;
	private int animRight;
	private int totalAnimFrames;
	private int animDelay;
	
	//variables for taking out or putting away the weapon or item
	private int inOutAnimUp;
	private int inOutAnimDown;
	private int inOutAnimLeft;
	private int inOutAnimRight;
	private int totalInOutFrames;
	private int inOutDelay;
	
	//Variables for attacking
	private int attackUp;
	private int attackDown;
	private int attackLeft;
	private int attackRight;
	private int totalAttackFrames;
	private int attackDelay;
	
	//Variables having to do with item specific damage modifiers
	private int strengthDamage;
	private int magicDamage;
	
	//Four different hitboxes for possible attacks, 1 is the normal amount used
	private int hitBoxX, hitBoxY, boundSize;
	private int hitBoxX2, hitBoxY2, boundSize2;
	private int hitBoxX3, hitBoxY3, boundSize3;
	private int hitBoxX4, hitBoxY4, boundSize4;
	private int totalPossible = 4;
	private int hitBoxesUsed;
	
	//Variables that differentiate an attack from another
	private String attackName;
	private boolean hasMoveAnim = false;
	private boolean hasAttackAnim = false;
	private boolean hasInOutAnim = false;
	
	//Constructor
	//Use zeros for any damage multiplier you don't want to use
	//No attack can use both magic and strength dmagae
	public Attack(String name, int magicDam, int strengthDam){
		attackName = name;
		magicDamage = magicDam;
		strengthDamage = strengthDam;
	}
	
	//If the total hitBoxes possible needs to be increased in the code, the addition of where the variables are
	// assigned to will need to be added here. Lastly, this method is used in order to add a hit box to an attack
	// up to 4 are currently possible. The number of hitboxes will need to be edited under getHitBoxes()
	public void addHitBox(int x, int y, int size){
		if(hitBoxesUsed < totalPossible){
			if(hitBoxesUsed == 0){
				hitBoxX = x;
				hitBoxY = y;
				boundSize = size;
			}
			if(hitBoxesUsed == 1){
				hitBoxX2 = x;
				hitBoxY2 = y;
				boundSize2 = size;
			}
			if(hitBoxesUsed == 2){
				hitBoxX3 = x;
				hitBoxY3 = y;
				boundSize3 = size;			
			}
			if(hitBoxesUsed == 3){
				hitBoxX4 = x;
				hitBoxY4 = y;
				boundSize4 = size;
			}
			hitBoxesUsed++;
		} else return;
	}
	
	//The next three methods are used seperately in order to add specific types of animations to an attack
	public void addMovingAnim(int up, int down, int left, int right, int total, int delay){
		animUp = up;
		animDown = down;
		animLeft = left;
		animRight = right;
		totalAnimFrames = total;
		animDelay = delay;
		hasMoveAnim = true;
	}
	public void addInOutAnim(int up, int down, int left, int right, int total, int delay){
		inOutAnimUp = up;
		inOutAnimDown = down;
		inOutAnimLeft = left;
		inOutAnimRight = right;
		totalInOutFrames = total;
		inOutDelay = delay;
		hasInOutAnim = true;
	}
	public void addAttackAnim(int up, int down, int left, int right, int total, int delay){
		attackUp = up;
		attackDown = down;
		attackLeft = left;
		attackRight = right;
		totalAttackFrames = total;
		attackDelay = delay;
		hasAttackAnim = true;
	}
	
	//Get the damage this item or weapon does
	public int getDamage() {
		if(strengthDamage != 0) return strengthDamage;
		if(magicDamage != 0) return magicDamage;
		return 0;
	}
	
	//Takes a number for which box to check as a parameter and returns the bounds of that hit box
	public Rectangle getHitBox(Mob mob, int boxNumber){
		Rectangle r = null;
		if(boxNumber == 1) r = new Rectangle((int)mob.getXLoc() + hitBoxX, (int)mob.getYLoc() + hitBoxY, boundSize, boundSize);
		if(boxNumber == 2) r = new Rectangle((int)mob.getXLoc() + hitBoxX2, (int)mob.getYLoc() + hitBoxY2, boundSize2, boundSize2);
		if(boxNumber == 3) r = new Rectangle((int)mob.getXLoc() + hitBoxX3, (int)mob.getYLoc() + hitBoxY3, boundSize3, boundSize3);
		if(boxNumber == 4) r = new Rectangle((int)mob.getXLoc() + hitBoxX4, (int)mob.getYLoc() + hitBoxY4, boundSize4, boundSize4);
		return r;
	}
	
	//Use these three getters to return info on the moving animation
	public int getMoveTotal() { return totalAnimFrames; }
	public int getMoveDelay() { return animDelay; }
	public int getMovingAnim(DIRECTION direction){
		if(direction == DIRECTION.UP){
			return animUp;
		}
		if(direction == DIRECTION.DOWN){
			return animDown;
		}
		if(direction == DIRECTION.LEFT){
			return animLeft;
		}
		if(direction == DIRECTION.RIGHT){
			return animRight;
		}
		return -1;
	}
		
	//Use these three getters to return info on the in and out animation
	public int getInOutTotal() { return totalInOutFrames; }
	public int getInOutDelay() { return inOutDelay; }
	public int getInOutAnim(DIRECTION direction){
		if(direction == DIRECTION.UP){
			return inOutAnimUp;
		}
		if(direction == DIRECTION.DOWN){
			return inOutAnimDown;
		}
		if(direction == DIRECTION.LEFT){
			return inOutAnimLeft;
		}
		if(direction == DIRECTION.RIGHT){
			return inOutAnimRight;
		}
		return -1;
	}
	
	//Use these three getters to return info on the attack animation
	public int getAttackTotal() { return totalAttackFrames; }
	public int getAttackDelay() { return attackDelay; }
	public int getAttackAnim(DIRECTION direction){
		if(direction == DIRECTION.UP){
			return attackUp;
		}
		if(direction == DIRECTION.DOWN){
			return attackDown;
		}
		if(direction == DIRECTION.LEFT){
			return attackLeft;
		}
		if(direction == DIRECTION.RIGHT){
			return attackRight;
		}
		return -1;
	}
	
	//Check to see if an attack has a specific animation
	public boolean hasMoveAnim() { return hasMoveAnim; }
	public boolean hasInOutAnim() { return hasInOutAnim; }
	public boolean hasAttackAnim() { return hasAttackAnim; }
	
	//Return the name of the attack to be used
	public String getName() { return attackName; }
}
