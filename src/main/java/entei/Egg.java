package entei;

import java.util.Random;

import acm.graphics.GImage;

public class Egg extends GImage {
	
	private final static String imagePath = "media/egg.gif";
	private final static int size = 75;
	private int clicksToHatch;
	DuckPane game;

	public Egg(DuckPane game, double x, double y) {
		super(imagePath);
		this.game = game;
		super.setLocation(x, y);
		super.setSize(size,	size);
		Random rand = new Random();
		clicksToHatch = rand.nextInt(5) + 1;
	}
	
	
	public void click() {
		clicksToHatch --;
		if(clicksToHatch < 1) {
			game.spawnBabyDuck(this);
		}
		
	}
	

}
