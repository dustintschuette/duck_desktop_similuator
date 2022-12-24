package entei;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Pool extends GImage{
	
	private static final long serialVersionUID = 1L;
	private static String pathFront = "media/equipment/poolFront.png";
	private static String pathBack = "media/equipment/poolBack.png";
	private DuckPane game;
	private GImage back;

	public Pool(DuckPane game, double x, double y) {
		super(pathFront);
		this.game = game;
		back = new GImage(pathBack);
		add();
		back.setLocation(x, y - 65);
		setLocation(x, y);
	}
	
	public void add() {
		game.removeAllDucks();
		game.addToOnScreen(back);
		game.addDucks();
		game.addToOnScreen(this);
	}
	@Override
	public void move(double x, double y) {
		back.setLocation(back.getX() + x, back.getY() + y - 65);
		super.setLocation(getX() + x, getY() + y);
	}
	
	@Override
	public void setLocation(double x, double y) {
		if(back == null) return;
		back.setLocation(x, y - 65);
		super.setLocation(x, y);
	}
	
	public boolean checkPool(GObject input) {
		if(back.equals(input) || equals(input)) {
			return true;
		}
		return false;
	}
	public GImage getBack() {
		return back;
	}

}
