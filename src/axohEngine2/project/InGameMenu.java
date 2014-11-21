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
	private int itemLocation;
	private int sectionLoc;
	private int y = 0;
	
	/**
	 * Use the counts[] to add the number of items to the inGameMenu and add a filter to not display any more 
	 * duplicates after that.
	 **/
	
	public InGameMenu(ImageEntity background, int screenWidth, int screenHeight) {
		_background = background;
		SCREENWIDTH = screenWidth;
		SCREENHEIGHT = screenHeight;
		items = new LinkedList<Item>();
		counts = new int[100]; //Counts for 100 possible seperate items in game
	}
	
	public void update(OPTION option, int sectionLocation){
		_option = option;
		sectionLoc = sectionLocation;
		countItems();
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	//Set up the count method to count specific items in the list. Each index in the count array is a different item
	private void countItems() {
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).getName().equals("Potion")) counts[0]++;
			if(items.get(i).getName().equals("Mega Potion")) counts[1]++;
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
			g2d.setColor(Color.YELLOW);
			if(items.size() > 0) g2d.drawLine(670, 410 + sectionLoc * 110, 670 + items.get(sectionLoc).getName().length() * 37, 410 + sectionLoc * 110);
			g2d.setColor(Color.BLACK);
			g2d.drawString("Items", 920, 200);
			for(int i = itemLocation; i < items.size(); i++){
				Item item = items.get(i);
				g2d.drawString(item.getName(), 670, 400 + y * 110);
				item.render(frame, g2d, 600, 340 + y * 110);
				if(i == itemLocation + 3 || i == items.size() - 1) {  //Reset variables at a possible end
					y = 0;
					break;
				}
				y++;
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
		}
		if(_option == OPTION.SAVE){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Save Game", 880, 200);
		}
	}
		
	public void loadNextItems() {
		if(items.size() - 3 > itemLocation + 1) itemLocation++;
		System.out.println(itemLocation + " ok");
	}
	public void loadOldItems() {
		if(itemLocation > 0) itemLocation--;
	}
	
	public LinkedList<Item> getItems() { return items; }
	public void setLocation(int location) { itemLocation = location; }
}
