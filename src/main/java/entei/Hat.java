package entei;

import acm.graphics.GImage;

public class Hat extends GImage implements Chaseable{
	private DuckPane game;
	private Duck duck;
	private double dx, dy;
	
	public Hat(String name, DuckPane game) {
		super(name);
		//this.game = game;
		super.setSize(DuckPane.HAT_WIDTH, DuckPane.HAT_HEIGHT);
		// TODO Auto-generated constructor stub
	}
	public void resetHatSize() {
		super.setSize(DuckPane.HAT_WIDTH, DuckPane.HAT_HEIGHT);
	}
}