package axohEngine2.project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;

import axohEngine2.entities.ImageEntity;

public class InGameMenu {

	private ImageEntity _background;
	
	private OPTION _option;
	private int SCREENWIDTH;
	private int SCREENHEIGHT;
	private Random random = new Random();
	
	private LinkedList<Item> items;
	private LinkedList<Item> equipment;
	private int[] counts;
	private Item[] shownEquipment;
	private Item[] shownItems;
	private int itemLocation;
	private int sectionLoc;
	private int totalItems;
	private int totalEquipment;
	
	//Starting stat variables
	private int level = 1;
	private int maxHealth = 35;
	private int currHealth = maxHealth;
	private int magic = 5;
	private int attack = 8;
	private int defense = 4;
	private int experience;
	private int nextLevel = 20;
	private int nextExp = 25;
	
	/********************************************************************************************
	 * The Items algorithms need an explanation, starting with the countItems() method: The purpose
	 *  of this method is to count how many of each item is in the list of avaiable items to the 
	 *  player. Each index coincides with an item count of your choice. Because this count method is
	 *  updated each time an item is obtained, the count for every item is set to 0 first, Then 
	 *  populated by checking the name of an item in the list and adding 1 to it's index location.
	 *  
	 *  setItems(): First, since this is checked every time an item is obtained, the totalItems variable
	 *  which counts the currently showing items, is set to 0 and the repopulated. The purpose of
	 *  this method is to make sure only specific items are being shown in the menu and specifically not
	 *  duplicates. It populates an array of showable items if it exists, otherwise it skips over 
	 *  repeated names. The totalItems is only added to when a shownItems index is not null. This is
	 *  because some indexes may not be used.
	 *  
	 *  loadNextItems(): itemLocation, is the variable that is the start number for the items to be
	 *  displayed on screen, used in the main for loop. This method runs a check, starting wherever
	 *  the fourth item + 1 is found in the showItems array. This way the fourth item isn't counted for
	 *  when the check to make sure a fifth item exists to be displayed. 
	 *  
	 *  loadOldItems(): Simply subtract one from the current itemLocation if it isn't 0. These last two
	 *  methods are only called when the current cursor location kept track by the game itself, it near
	 *  the top or the bottom of the screen. 0 or 3.
	 *  
	 *  Main Rendering algorithm: A for loop begins at the current itemLocation and ends when it reaches
	 *  start + 4. Start begins equaling getStart(itemLocation) but is added to if the current index in 
	 *  shownItems returns null, thereby skipping that index. The first check is done to make sure that
	 *  no index out of bounds errors occur and stops the loop if they may. Any time the loop is stopped,
	 *  'y' is set back to 0 for the next rendering. The highlight line is then drawn only if the items
	 *  list is not empty. This starts at the current sectionLoc which is calculated based on up and
	 *  down movement in the menu, and ends at the end of the length of the current item name. Then the
	 *  current item name is drawn along with it's count and image next to it. The count is rendered
	 *  by changing the count[] index and changing it to an Integer, then making it in to a string. The
	 *  number render location is dependant on the length of the name of the item. The reason why a 
	 *  seperate variable 'y' is used to move down the rendered text is because 'i' could be very large
	 *  depending on if some items aren't obtained yet, which would mean their index would be null and 
	 *  'i' would increase because the item would be skipped. The last check is done in order to make
	 *  sure 'y' is correctly reset just in case 4 items are found immediately and the loop would end.
	 *  
	 *  itemLocation: The starting point in the list of items that will be shown, for example, if it is 2,
	 *  then you have 6 items, the first two arent being shown.
	 *  
	 *  sectionLoc: The item section you are highlighting currently. For example, if it is 3, then you
	 *  are highlighting the third item on screen.
	 *  
	 *  Using the above two variables, adding itemLocation and sectionLoc together will always net you the
	 *  correct item to be managed
	 *  
	 ********************************************************************************************/
	
	//Change count[] and shownItem[] size to adjust for even more items in game
	public InGameMenu(ImageEntity background, int screenWidth, int screenHeight) {
		_background = background;
		SCREENWIDTH = screenWidth;
		SCREENHEIGHT = screenHeight;
		items = new LinkedList<Item>();
		equipment = new LinkedList<Item>();
		counts = new int[100];
		shownItems = new Item[100];
		shownEquipment = new Item[100];
	}
	
	public void update(OPTION option, int sectionLocation, int health){
		_option = option;
		sectionLoc = sectionLocation;
		currHealth = health;
		levelUp();
	}
	
	public void addItem(Item item) {
		items.add(item);
		countItems();
		setItems();
	}
	
	public void addEquipment(Item item) {
		equipment.add(item);
		countItems();
		setItems();
	}
	
	//Each new item and equipment for the game needs to be added to what needs to be counted
	private void countItems() {
		for(int i = 0; i < counts.length; i++){
			counts[i] = 0;
		}
		for(int i = 0; i < items.size(); i++){ //Add items to be counted here
			if(items.get(i).getName().equals("Potion")) counts[0]++;
			if(items.get(i).getName().equals("Mega Potion")) counts[1]++;
		}
		for(int i = 0; i < equipment.size(); i++){ //add equipment to be counted here.
			
		}
	}
	
	//Each new item will need to be added here as well.
	//This is to make a list of what items are being shown
	private void setItems() {
		totalItems = 0;
		totalEquipment = 0;
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).getName() == "Potion"){ shownItems[0] = items.get(i); }
			if(items.get(i).getName() == "Mega Potion"){ shownItems[1] = items.get(i); }
		}
		for(int i = 0; i < equipment.size(); i++){ //Set items for showEquipment[] here
			
		}
		for(int i = 0; i < shownEquipment.length; i++){
			if(shownEquipment[i] == null) continue;
			totalEquipment++;
		}
		for(int i = 0; i < shownItems.length; i++){
			if(shownItems[i] == null) continue;
			totalItems++;
		}
	}
	
	public void render(JFrame frame, Graphics2D g2d, int inX, int inY) {
		g2d.drawImage(_background.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT, frame);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Items", 120, 170);
		g2d.drawString("Equipment", 120, 275);
		g2d.drawString("Magic", 120, 385);
		g2d.drawString("Status", 120, 490);
		g2d.drawString("Save Game", 120, 600);
		g2d.setColor(Color.YELLOW);
		g2d.drawRect(inX, inY, 435, 104);
		
		if(_option == OPTION.ITEMS){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Items", 920, 200);
			renderItems(frame, g2d, shownItems, items);
		}
		
		if(_option == OPTION.EQUIPMENT){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Equipment", 900, 200);
			renderItems(frame, g2d, shownEquipment, equipment);
		}
		
		if(_option == OPTION.MAGIC){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Magic", 880, 200);
		}
		
		if(_option == OPTION.STATUS){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Status", 920, 200);
			g2d.drawString("Level: " + level, 600, 375);
			g2d.drawString("Attack: " + attack, 600, 475);
			g2d.drawString("Defense: " + defense, 600, 575);
			g2d.drawString("Health: " + currHealth, 600, 675);
			g2d.drawString("Experience: " + experience + " / " + nextLevel, 600, 775);
		}
		
		if(_option == OPTION.SAVE){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Save Game", 880, 200);
		}
	}
	
	public void renderItems(JFrame frame, Graphics2D g2d, Item[] array, LinkedList<Item> list) {
		int start = getStart(itemLocation, array);
		int y = 0;
		for(int i = start; i < start + 4; i++){
			if(array[i] == null) {
				start++;
				if(start == array.length - 1) break;
				if(i == array.length - 1) break;
				continue;
			}
			g2d.setColor(Color.YELLOW);
			if(!list.isEmpty()) g2d.drawLine(670, 410 + sectionLoc * 110, 670 + array[i].getName().length() * 37, 410 + sectionLoc * 110);
			g2d.setColor(Color.BLACK);
			g2d.drawString(array[i].getName(), 670, 400 + y * 110);
			g2d.drawString(" x " + new Integer(counts[i]).toString(), 700 + array[i].getName().length() * 37,  400 + y * 110);
			array[i].render(frame, g2d, 600, 340 + y * 110);
			y++;
		}
	}
	
	//What needs to be done to level a character up, this is checked for at every update.
	private void levelUp(){
		if(experience >= nextLevel){
			level++;
			
			nextLevel = nextExp;
			if(level <= 25) nextExp = nextExp + 20 + random.nextInt(level + 10);
			if(level > 25 && level <= 50) nextExp = nextExp + 40 + random.nextInt(level + 20);
			if(level > 50 && level < 75) nextExp = nextExp + 80 + random.nextInt(level + 40);
			if(level >= 75) nextExp = nextExp + 80 + random.nextInt(level + 80);
			//Health
			if(level % 10 == 10) maxHealth += random.nextInt(level) + 1;
			if(level % 5 == 5) maxHealth += 2 + random.nextInt(4);
			if(level % 3 == 3) maxHealth += 3;
			if(level % 2 == 2) maxHealth += 1;
			if(level % 7 == 7) maxHealth += 5 + random.nextInt(4) - random.nextInt(3);
			//Attack
			if(level % 7 == 7) attack += 3;
			if(level % 8 == 8) attack += 1;
			if(level % 5 == 5) attack += 1;
			if(level % 12 == 12) attack += 6 - random.nextInt(3);
			//Defense
			if(level % 4 == 4) defense += 2;
			if(level % 3 == 3) defense += 1;
			if(level % 9 == 9) defense += 4 - random.nextInt(2);
			if(level % 6 == 6) defense += 2 + random.nextInt(3);
		}
	}
	
	public void getExp(int exp) { experience += exp; }
	
	//Return the starting location after a certain amount of items are counted
	private int getStart(int amount, Item[] array){
		int found = 0;
		for(int i = 0; i < array.length; i++){
			if(found == amount) {
				return i;
			}
			if(array[i] != null) {
				found++;
			}
		}
		return 0;
	}
	
	public void loadNextItems() {
		int start = 0;
		start = getStart(4, shownItems);
		for(int i = start; i < shownItems.length; i++){
			if(shownItems[i] != null) {
				itemLocation++;
				break;
			}
		}
	}
	
	public void loadNextEquipment() {
		int start = 0;
		start = getStart(4, shownEquipment);
		for(int i = start; i < shownEquipment.length; i++){
			if(shownEquipment[i] != null) {
				itemLocation++;
				break;
			}
		}
	}
	
	//This works with items and equipment
	public void loadOldItems() {
		if(itemLocation > 0) itemLocation--;
	}
	
	public int getTotalItems() { return totalItems; }
	public int getTotalEquipment() { return totalEquipment; }
	public void setItemLoc(int location) { itemLocation = location; }
	
	//Remember to update the item check inder Item.java when adding a new group of items. All are handled seperately
	public void useItem() { 
		for(int i = 0; i < items.size(); i++){
			if(shownItems[itemLocation + sectionLoc].getName() == items.get(i).getName()) {
				
				if(items.get(i).checkItem() == 1); //TODO: What to do with key items?
				
				if(items.get(i).checkItem() == 2) {
					if(currHealth + items.get(i).getHealth() >= maxHealth){
						currHealth = maxHealth;
						items.remove(i);
						countItems();
						setItems();
						break;
					}
					currHealth += items.get(i).getHealth();
				} //Heal Item
				
				if(items.get(i).checkItem() == 3); //TODO: Attack Item
				items.remove(i);
				countItems();
				setItems();
				break;
			}
		}
	}
	
	public int checkCount() { return counts[itemLocation + sectionLoc]; }
	public int getHealth() { return currHealth; }
	
	public int getMagic() { return magic; }
	public void setmagic(int magic) { this.magic = magic; }
}
