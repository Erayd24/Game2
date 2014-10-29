package axohEngine2;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.ImageEntity;
import axohEngine2.entities.SpriteSheet;

@SuppressWarnings("serial")
public class Collections extends Judgement {

	//SpriteSheets
	SpriteSheet sheet;
	SpriteSheet player;
	
	//Animated Sprites
	AnimatedSprite grass;
	AnimatedSprite player1;
	
	//Backgrounds
	ImageEntity background;
	
	
	public void Initialize() {
		sheet = new SpriteSheet("/textures/environments/environments1.png", 16, 16, 16);
		player = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32);
		
		player1 = new AnimatedSprite(this, graphics());
		player1.setAnimSprite(player, 40);
		sprites().add(player1);

		grass = new AnimatedSprite(this, graphics());
		grass.setAnimSprite(sheet, 16);
		sprites().add(grass);
		
		//background = new ImageEntity(this);
		//background.load("/field.png");
	}
}
