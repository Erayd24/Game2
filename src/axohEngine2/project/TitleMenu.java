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
	private boolean getName = false;
	
	private ImageEntity _mainImage;
	private ImageEntity _secondary;
	private AnimatedSprite _titleArrow;
	private OPTION _option;
	
	private Font _simple;
	private Font _bold;
	private Font _bigBold;
	
	private int SCREENWIDTH;
	private int SCREENHEIGHT;
	
	public TitleMenu(ImageEntity mainImage, ImageEntity secondary,AnimatedSprite titleArrow, int screenWidth, int screenHeight, Font simple, Font bold, Font bigBold) {
		existingFiles = new File("C:/gamedata/saves/");
		_mainImage = mainImage;
		_secondary = secondary;
		_titleArrow = titleArrow;
		SCREENWIDTH = screenWidth;
		SCREENHEIGHT = screenHeight;
		_simple = simple;
		_bold = bold;
		_bigBold = bigBold;
		_option = OPTION.NONE;
	}
	
	public void render(JFrame frame, Graphics2D g2d, int titleX, int titleY, int titleX2, int titleY2) {
		g2d.drawImage(_mainImage.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT, frame);
		g2d.setColor(Color.BLACK);
		g2d.setFont(_bold);
		g2d.drawString("New Game", 660, 700);
		g2d.drawString("Load Game", 560, 800);
		g2d.setColor(Color.YELLOW);
		g2d.setFont(_bigBold);
		drawString(g2d, "The\n   Judgement", 500, 100);
		g2d.drawImage(_titleArrow.getImage(), titleX, titleY, _titleArrow.getSpriteSize(), _titleArrow.getSpriteSize(), frame);
		
		if(_option == OPTION.NEWGAME || _option == OPTION.LOADGAME){
			g2d.setColor(Color.BLACK);
			g2d.setFont(_simple);
			g2d.drawImage(_secondary.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT, frame);
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
			g2d.drawImage(_titleArrow.getImage(), titleX2, titleY2, _titleArrow.getSpriteSize(), _titleArrow.getSpriteSize(), frame);
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
	
	public String[] files() { return files; }
	public boolean isGetName() { return getName; }
	public void setGetName(boolean onOrOff) { getName = onOrOff; }
	
	void drawString(Graphics2D g2d, String text, int x, int y) {
       for (String line : text.split("\n"))
           g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
    }
}