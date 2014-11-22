package axohEngine2.project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JFrame;

import axohEngine2.entities.ImageEntity;

public class InGameMenu {

	private ImageEntity _background;
	
	private OPTION _option;
	private int SCREENWIDTH;
	private int SCREENHEIGHT;
	private LinkedList<Item> items;
	private int[] counts;
	private Item[] shownItems;
	private int itemLocation;
	private int sectionLoc;
	private int totalItems;
	private int y = 0;
	
	private int level = 1;
	private int health = 50;
	private int attack = 8;
	private int defense = 4;
	private int experience;
	private int nextLevel;
	
	public InGameMenu(ImageEntity background, int screenWidth, int screenHeight) {
		_background = background;
		SCREENWIDTH = screenWidth;
		SCREENHEIGHT = screenHeight;
		items = new LinkedList<Item>();
		counts = new int[100]; //Counts for 100 possible seperate items in game
		shownItems = new Item[100]; //Possible items to show matched with their count in count[]
	}
	
	public void update(OPTION option, int sectionLocation){
		_option = option;
		sectionLoc = sectionLocation;
	}
	
	public void addItem(Item item) {
		items.add(item);
		countItems();
		setItems();
	}
	
	//Set up the count method to count specific items in the list. Each index in the count array is a different item
	private void countItems() {
		for(int i = 0; i < counts.length; i++){
			counts[i] = 0;
		}
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).getName().equals("Potion")) counts[0]++;
			if(items.get(i).getName().equals("Mega Potion")) counts[1]++;
		}
	}
	
	//Each index coincides with the count index. This is used in order to only show 1 of each item in the menu,
	// followed by it's current count in the system. Each new item will need to be added here as well.
	private void setItems() {
		totalItems = 0;
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).getName() == "Potion"){ shownItems[0] = items.get(i); }
			if(items.get(i).getName() == "Mega Potion"){ shownItems[1] = items.get(i); }
		}
		for(int i = 0; i < shownItems.length; i++){
			if(shownItems[i] == null) continue;
			totalItems++;
		}
	}
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
	 ********************************************************************************************/
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
			for(int i = itemLocation; i < itemLocation + 4; i++){
				if(shownItems[i] == null) continue;
				g2d.setColor(Color.YELLOW);
				if(!items.isEmpty()) g2d.drawLine(670, 410 + sectionLoc * 110, 670 + shownItems[i].getName().length() * 37, 410 + sectionLoc * 110);
				g2d.setColor(Color.BLACK);
				g2d.drawString(shownItems[i].getName(), 670, 400 + y * 110);
				g2d.drawString(" x" + new Integer(counts[i]).toString(), 700 + shownItems[i].getName().length() * 37,  400 + y * 110);
				shownItems[i].render(frame, g2d, 600, 340 + y * 110);
				y++;
				if(i == itemLocation + 1) y = 0;
			}
		}
		if(_option == OPTION.EQUIPMENT){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Equipment", 900, 200);		
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
			g2d.drawString("Health: " + health, 600, 675);
			g2d.drawString("Experience: " + experience + " / " + nextLevel, 600, 775);
		}
		if(_option == OPTION.SAVE){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Save Game", 880, 200);
		}
	}
		
	public void loadNextItems() {
		if(shownItems[itemLocation + 1] != null) itemLocation++;
	}
	public void loadOldItems() {
		if(itemLocation > 0) itemLocation--;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
	
	public void setItemLoc(int location) { itemLocation = location; }
}
