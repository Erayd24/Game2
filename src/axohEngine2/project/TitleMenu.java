package axohEngine2.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;

import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.ImageEntity;

public class TitleMenu {
	
	private String[] files;
	private File existingFiles;
	private int location;
	private String _fileName = "";
	public boolean getName = false;
	
	ImageEntity titleMenu;
	ImageEntity titleMenu2;
	AnimatedSprite titleArrow;
	OPTION _option;
	
	private Font simple;
	private Font bold;
	private Font bigBold;
	
	private int SCREENWIDTH;
	private int SCREENHEIGHT;
	
	public TitleMenu(ImageEntity mainImage, ImageEntity secondary,AnimatedSprite titleArrow, int width, int height, Font simple, Font bold, Font bigBold) {
		existingFiles = new File("C:/gamedata/saves/");
		titleMenu = mainImage;
		titleMenu2 = secondary;
		this.titleArrow = titleArrow;
		SCREENWIDTH = width;
		SCREENHEIGHT = height;
		this.simple = simple;
		this.bold = bold;
		this.bigBold = bigBold;
		_option = OPTION.NONE;
	}
	
	public void renderTitleScreen(JFrame frame, Graphics2D g2d, int titleX, int titleY, int titleX2, int titleY2) {
		g2d.drawImage(titleMenu.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT, frame);
		g2d.setColor(Color.BLACK);
		g2d.setFont(bold);
		g2d.drawString("New Game", 660, 700);
		g2d.drawString("Load Game", 560, 800);
		g2d.setColor(Color.YELLOW);
		g2d.setFont(bigBold);
		drawString(g2d, "The\n   Judgement", 500, 100);
		g2d.drawImage(titleArrow.getImage(), titleX, titleY, titleArrow.getSpriteSize(), titleArrow.getSpriteSize(), frame);
		
		if(_option == OPTION.NEWGAME || _option == OPTION.LOADGAME){
			g2d.setColor(Color.BLACK);
			g2d.setFont(simple);
			g2d.drawImage(titleMenu2.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT, frame);
			if(files != null){
				for(int i = 0; i < files.length; i++){
					g2d.drawString(files[i], 540, 388 + i * 165);
				}
			}
			if(_option == OPTION.NEWGAME) {
				g2d.drawString("New Game", 620, 190); 
				g2d.drawString(_fileName, 540, 388 + location * 165);
			}
			if(_option == OPTION.LOADGAME) {
				g2d.drawString("Load Game", 620, 190); 
			}
			g2d.drawImage(titleArrow.getImage(), titleX2, titleY2, titleArrow.getSpriteSize(), titleArrow.getSpriteSize(), frame);
		}
	}
	
	public void update(OPTION option, int location) {
		_option = option;
		files = existingFiles.list();
		this.location = location;
	}
	
	public void setFileName(char currentChar) {
		if(currentChar == '\0') return;
		if(currentChar == '\b') return;
		if(currentChar == '\n') return;
		if(_fileName.length() < 11) _fileName += currentChar;
	}
	
	public void deleteChar() {
		if(_fileName.length() > 0) _fileName = _fileName.substring(0, _fileName.length() - 1);
	}
	
	public String enter() {
		if(_option == OPTION.NEWGAME) {
			getName = true;
			return "";
		}
		if(_option == OPTION.LOADGAME) {
			if(location <= files.length  - 1){
				if(files.length == 3) return (files[location]); 
				if(files.length == 2 && location <= 1) return (files[location]); 
				if(files.length == 1 && location == 0) return (files[location]); 
			}
		}
		return "";
	}
	
	public String getFileName() {
		return _fileName;
	}
	
	void drawString(Graphics2D g2d, String text, int x, int y) {
       for (String line : text.split("\n"))
           g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
    }
}