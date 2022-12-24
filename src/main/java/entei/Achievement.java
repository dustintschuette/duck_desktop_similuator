package entei;

import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class Achievement{
	public static int ACHIEVEMENT_SIZE = 80;
	public static String LOCKED_PATH = "media/locked.png";
	
	private MainApplication program;
	private DuckPane game;
	
	
	private GImage icon;
	private GLabel name;
	private GCompound description;
	
	private String unlockedDes;
	private String iconPath;
	private double nameXoff = 0;
	private double nameYoff = 95;
	private double defDesLength = 18;
	public boolean unlocked = false;
	
	
	public Achievement(String iconPath, String name, String description) {
		icon = new GImage(LOCKED_PATH);
		this.iconPath = iconPath;
		unlockedDes = description;
		program = MainApplication.INSTANCE;
		game = DuckPane.INSTANCE;
		this.name = new GLabel(name);
		this.description = new GCompound();
		
		
		GRect bg = new GRect(defDesLength * 6, 20);
		bg.setFilled(true);
		this.description.add(bg);
		
		GLabel des = new GLabel("???? ?????????? ??", 3, 15 );
		des.setColor(Color.WHITE);
		this.description.add(des);
		
		icon.setSize(ACHIEVEMENT_SIZE, ACHIEVEMENT_SIZE);
		this.name.setLocation(nameXoff, nameYoff);
		this.name.setColor(Color.white);
		
	}
	
	public GRectangle getBounds() {
		return icon.getBounds();
	}
	
	public void move(double x, double y) {
		icon.move(x, y);
		name.move(x, y);
	}
	
	public void show() {
		program.add(icon);
		program.add(name);
	}
	
	public void hide() {
		program.remove(icon);
		program.remove(name);
		program.remove(description);
	}
	public void showDescription(double x, double y) {
		description.setLocation(x, y);
		program.add(description);
	}
	
	public void hideDescription() {
		program.remove(description);
	}
	public void unlock() {
		icon.setImage(iconPath);
		icon.setSize(ACHIEVEMENT_SIZE, ACHIEVEMENT_SIZE);
		GLabel des = (GLabel) description.getElement(1);
		des.setLabel(unlockedDes);
		
		GRect bg = (GRect) description.getElement(0);
		bg.setSize(des.getLabel().length()* 7, 20);
		unlocked = true;
		
		game.addNotification("Achievement Unlocked: " + name.getLabel());
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name.getLabel();
	}

}
