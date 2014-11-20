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
	private int itemLocation;
	private int j = 0, k = 0;
	
	public InGameMenu(ImageEntity background, int screenWidth, int screenHeight) {
		_background = background;
		SCREENWIDTH = screenWidth;
		SCREENHEIGHT = screenHeight;
		items = new LinkedList<Item>();
	}
	
	public void update(OPTION option){
		_option = option;
	}
	
	public void addItem(Item item) {
		items.add(item);
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
			for(int i = itemLocation; i < items.size(); i++){
				if(itemLocation != 0) { 
					if(itemLocation == 9) {  //Reset variables at a possible end
						j = 0;
						k = 0;
						break;
					}
					if(i == itemLocation + 4) continue; //skip the fifth item to be shown
				}
				Item item = items.get(i);
				g2d.drawString(item.getName(), 720 + j * 500, 400 + k * 110);
				item.render(frame, g2d, 650 + j * 600, 340 + k * 110);
				k++;
				if(k == 4){
					k = 0;
					j = 1;
				}
				if(i == 8 && itemLocation == 0) { //Restart variables at possible end
					j = 0;
					k = 0;
					break;
				}
				if(i == items.size() - 1) { //Restart variables at possible end
					k = 0;
					j = 0;
				}
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
		if(items.size() < itemLocation + 1) itemLocation++;
	}
	public void loadOldItems() {
		if(itemLocation > 0) itemLocation--;
	}
}
