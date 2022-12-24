package entei;

import java.util.ArrayList;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRectangle;

public class RightClickMenu {
	
	//higher level objects
	MainApplication program;
	DuckPane game;
	Duck duck;
	//objects
	ArrayList<String> optionText;
	ArrayList<MenuOption> options;
	GImage menuBG;
	
	//primitive variables
	boolean visible;
	private int currentSubMenu = -1;
	
	//static numbers for size/placement
	public static int X_DEF = 40;	//default x for GLabel offset on boxes
	public static int Y_DEF = 15;	//default y for GLabel offset for boxes
	public static int OPTION_X_SIZE = 150;	//size of one options box
	public static int OPTION_Y_SIZE = 25;
	public static int OPTION_BOX_X = 155;	//size of whole menu
	private int menuHeight;
	
	
	
	
	public RightClickMenu(){
		
		//set higher level objects
		program = MainApplication.INSTANCE;
		game = DuckPane.INSTANCE;
		
		
		optionText = new ArrayList<String>();
		options = new ArrayList<MenuOption>();
		visible = false;
		
		//Setup options
		optionText.add("Hide");
		optionText.add("Spawn Item ->");
		optionText.add("Hat");
		optionText.add("Toggle Walking");
		optionText.add("Reset Boss Key");
		optionText.add("Despawn ducks");
		optionText.add("Despawn item");
		
		//initializing
		menuHeight = 25 * optionText.size() + 5;
		menuBG = new GImage("media/optionsBoxBG.png");
		menuBG.setSize(OPTION_BOX_X, menuHeight);
		
		
		
		//initialize GCompounds that represent menu options
		for(int i = 0; i < optionText.size(); i++) {
			GImage bg = new GImage("media/optionBG.png", 2 , i * OPTION_Y_SIZE);
			bg.setSize(OPTION_X_SIZE, OPTION_Y_SIZE);
			GLabel text = new GLabel(optionText.get(i), X_DEF, Y_DEF + (i * OPTION_Y_SIZE));
			MenuOption newOption = new MenuOption(null, 2 + OPTION_X_SIZE, i * OPTION_Y_SIZE);
			newOption.add(bg);
			newOption.add(text);
			options.add(newOption);
		}
		
		//set subMenu options
		optionText.clear();
		optionText.add("Spawn Ball");
		optionText.add("Spawn Bread");
		optionText.add("Spawn Pool");
		
		//use index to add submenu options
		options.get(1).buildSubMenu(optionText);
		
		optionText.clear();
	}
	public boolean visible() {
		return visible;
	}
	
	public void unlockTrophy() {
		optionText.add("Spawn Trophy");
		options.get(1).buildSubMenu(optionText);
	}
	
	
	public void show(double x, double y, boolean onItem) {
		double moveX = x - menuBG.getX();
		double moveY = y - menuBG.getY();
		
		menuBG.move(moveX, moveY);
		program.add(menuBG);
		for(int i = 0; i < options.size(); i++) {
			GLabel temp = (GLabel) options.get(i).getElement(1);
			if(temp.getLabel() != "Despawn item" || onItem) {
				
				program.add(options.get(i));
			}
			options.get(i).move(moveX, moveY);
		}
		visible = true;
	}
	public void hide() {
		program.remove(menuBG);
		for(int i = 0; i < options.size(); i++) {
			program.remove(options.get(i));
		}
		if(currentSubMenu != -1) {
			options.get(currentSubMenu).collapse();
			currentSubMenu = -1;
		}
		
		visible = false;
	}
	public void hlBoxes(double x, double y) {
		GRectangle clickBox = new GRectangle(x, y, 1, 1);
		
		for(int i = 0; i < options.size(); i++) {
			if(Util.collision(clickBox, options.get(i).getBounds())) {
				GImage box = (GImage) options.get(i).getElement(0);
				box.setImage("media/optionBoxLit.png");
				box.setSize(OPTION_X_SIZE, OPTION_Y_SIZE);
				if(currentSubMenu != -1) {
					options.get(currentSubMenu).collapse();
				}
				options.get(i).expand();
				currentSubMenu = i;
			}else {
				GImage box = (GImage) options.get(i).getElement(0);
				box.setImage("media/optionBG.png");
				box.setSize(OPTION_X_SIZE, OPTION_Y_SIZE);
			}
		}
		//check submenu for highlights
		if(currentSubMenu != -1) {
			options.get(currentSubMenu).hlBoxes(x, y);
		}
		
	}
	public ArrayList<GRectangle> getMenu() {
		
		
		ArrayList<GRectangle> openMenus = new ArrayList<GRectangle>();
		openMenus.add(menuBG.getBounds());
		
		if(currentSubMenu != -1) {
			ArrayList<GRectangle> subMenus = new ArrayList<GRectangle>();
			subMenus = options.get(currentSubMenu).getSubHB();
			
			if(subMenus == null) {
				return openMenus;
				
			}
			for(GRectangle temp: subMenus) {
				openMenus.add(temp);
			}
			
		}
		
		return openMenus;
	}
	
	
	public void click(GRectangle mouseHitBox) {
		
		String elementClicked = "";

		
		boolean clicked = false;
		//see which, if any, option was clicked then branch to respective functions
		for(int i = 0; i < options.size(); i++) {
			if(Util.collision(options.get(i).getBounds(), mouseHitBox)) {
				GLabel temp = (GLabel) options.get(i).getElement(1);
				clicked  = true;
				elementClicked = temp.getLabel();
				break;
			}
		}
		if(currentSubMenu != -1 && !clicked) {
			options.get(currentSubMenu).click(mouseHitBox);
		}
		if(!clicked) {
			return;
		}
		//if user clicked an option, call respective function
		game.executeMenuOption(elementClicked);
		hide();
	}
	

	
	

}
