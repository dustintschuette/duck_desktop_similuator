package entei;

import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class AchievementBar {

	//Objects
	ArrayList<Achievement> achievements;
	GImage bg = new GImage("media/emptyWindow.png");
	MainApplication program = MainApplication.INSTANCE;
	
	
	//primitives
	public boolean onScreen = false;
	private int xSpace = 200;
	private int ySpace = 160;
	private int xDef = 175;
	private int yDef = 70;
	
	
	
	public AchievementBar(){
		achievements = new ArrayList<Achievement>();
		
		//add achievements here
		
		achievements.add(new Achievement("media/achievements/eat_5_bread.png", "5 Bread", "Feed ducks 5 bread"));
		achievements.add(new Achievement("media/achievements/hatcher.png", "Hatcher", "Hatch 3 ducklings"));
		achievements.add(new Achievement("media/achievements/hoarder.png", "Hoarder", "Have all items on screen at once"));
		achievements.add(new Achievement("media/achievements/buffet.png", "Buffet", "Have 5 bread on screen at once"));
		achievements.add(new Achievement("media/achievements/hide.png", "Hide!", "Use the hide key 5 times"));
		
		for(int i = 0; i< achievements.size(); i++) {
			achievements.get(i).move((i % 4) * xSpace + xDef, yDef);
			if(i % 4 == 0 && i != 0) {
				achievements.get(i).move(0, ySpace);
			}
		}
	}
	
	public void checkHover(double x, double y) {
		GRectangle mouseHitBox = new GRectangle(x, y, 1, 1);
		for(Achievement temp: achievements) {
			if(Util.collision(mouseHitBox, temp.getBounds())) {
				temp.showDescription(x, y);
			}else {
				temp.hideDescription();
			}
		}
		
		
	}
	
	public void toggle() {
		if(onScreen) {
			hide();
		}else {
			show();
		}
		onScreen = !onScreen;
	}
	
	public void show(){
		program.add(bg);
		for(Achievement temp: achievements) {
			temp.show();
		}
		onScreen = true;
	}
	
	public void hide() {
		program.remove(bg);
		for(Achievement temp: achievements) {
			temp.hide();
		}
		onScreen = false;
	}
	
	public GRectangle getExit() {
		return new GRectangle(bg.getX() + 830, bg.getY() + 10, 30, 30);
	}
	
	public void unlock(String target) {
		for(Achievement temp: achievements) {
			if(temp.getName().equals(target) && !temp.unlocked) {
				temp.unlock();
			}
		}
	}
	public boolean unlockTrophy() {
		for(Achievement temp: achievements) {
			if(!temp.unlocked) {
				return false;
			}
		}
		return true;
	}
	public GRectangle getBounds() {
		return bg.getBounds();
	}
	
}
