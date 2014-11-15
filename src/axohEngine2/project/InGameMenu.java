package axohEngine2.project;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import axohEngine2.entities.ImageEntity;

public class InGameMenu {

	private ImageEntity _background;
	
	private OPTION _option;
	private int SCREENWIDTH;
	private int SCREENHEIGHT;
	
	public InGameMenu(ImageEntity background, int screenWidth, int screenHeight) {
		_background = background;
		SCREENWIDTH = screenWidth;
		SCREENHEIGHT = screenHeight;
	}
	
	public void update(OPTION option){
		_option = option;
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
}
