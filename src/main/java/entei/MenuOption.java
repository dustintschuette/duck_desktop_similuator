package entei;

import java.util.ArrayList;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRectangle;

public class MenuOption extends GCompound{

	private static final long serialVersionUID = 8746696791405746416L;
	
	private MainApplication program;
	private DuckPane game;
	private ArrayList<MenuOption> subMenu;
	public boolean expanded = false;
	
	//statics
	public static int OPTION_X_SIZE = 150;	//size of one options box
	public static int OPTION_Y_SIZE = 25;
	public static int SUBMENU_WIDTH = 155;	//size of whole menu
	public static int X_LABEL_OFFSET = 40;	//default x for GLabel offset on boxes
	public static int Y_LABEL_OFFSET = 15;	//default y for GLabel offset for boxes
	
	
	//objects
	private GImage subMenuBG;
	
	
	//primatives
	private int subMenuHeight;
	private double spawnX;	//default x for GLabel offset on boxes
	private double spawnY;	//default y for GLabel offset for boxes
	private int currentSubMenu = -1;
	
	MenuOption(ArrayList<String> options, double spawnX, double spawnY){
		program = MainApplication.INSTANCE;
		game = DuckPane.INSTANCE;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		
		
		if(options == null) {
			return;
		}
		buildSubMenu(options);
		
		
	}
	
	public void buildSubMenu(ArrayList<String> options) {
		//making the submenu
		subMenuHeight = 25 * options.size() + 5;
		if(subMenu != null) subMenuHeight += 25 * subMenu.size();
		subMenuBG = new GImage("media/optionsBoxBG.png");
		subMenuBG.setSize(SUBMENU_WIDTH, subMenuHeight);
		
		
		//initialize ArrayList
		if(subMenu == null) {
			subMenu = new ArrayList<MenuOption>();
		}
		else {
			spawnY += subMenu.get(0).getY();
			spawnX += subMenu.get(0).getX();
		}
		subMenuBG.setLocation(spawnX, spawnY);
		
		for(int i = 0; i < options.size(); i++) {
			GImage bg = new GImage("media/optionBG.png", 3 + spawnX , spawnY + (subMenu.size() * OPTION_Y_SIZE  ));
			bg.setSize(OPTION_X_SIZE, OPTION_Y_SIZE);
			GLabel text = new GLabel(options.get(i), X_LABEL_OFFSET + spawnX, Y_LABEL_OFFSET + (subMenu.size() * OPTION_Y_SIZE) + spawnY);
			MenuOption newOption = new MenuOption(null, spawnX + OPTION_X_SIZE, spawnY + (subMenu.size() * OPTION_Y_SIZE));
			newOption.add(bg);
			newOption.add(text);
			subMenu.add(newOption);
		}
	}
	
	public void expand() {
		if(subMenu == null) {
			return;
		}
		if(expanded) return;
		for(MenuOption temp: subMenu) {
			program.add(temp);
		}
		expanded = true;
	}
	
	public void hlBoxes(double x, double y) {
		GRectangle clickBox = new GRectangle(x, y, 1, 1);
		
		if(subMenu == null) {
			return;
		}
		
		for(int i = 0; i < subMenu.size(); i++) {
			if(Util.collision(clickBox, subMenu.get(i).getBounds())) {
				GImage box = (GImage) subMenu.get(i).getElement(0);
				box.setImage("media/optionBoxLit.png");
				box.setSize(OPTION_X_SIZE, OPTION_Y_SIZE);
				if(currentSubMenu != -1) {
					subMenu.get(currentSubMenu).collapse();
				}
				subMenu.get(i).expand();
				currentSubMenu = i;
			}else {
				GImage box = (GImage) subMenu.get(i).getElement(0);
				box.setImage("media/optionBG.png");
				box.setSize(OPTION_X_SIZE, OPTION_Y_SIZE);
			}
		}
		//check submenu for highlights
		if(currentSubMenu != -1) {
			subMenu.get(currentSubMenu).hlBoxes(x, y);
		}
		
	}

	public void collapse() {
		if(subMenu == null) {
			return;
		}
		if(!expanded) return;
		for(MenuOption temp: subMenu) {
			program.remove(temp);
		}
		expanded = false;
		if(currentSubMenu != -1) {
			subMenu.get(currentSubMenu).collapse();
		}
		currentSubMenu = -1;
		
	}

	public ArrayList<GRectangle> getSubHB() {
		if(subMenuBG == null) {
			System.out.println("bg is null");
			return null;
		}else {
			System.out.println("bg is not null");
		}
		ArrayList<GRectangle> openMenus = new ArrayList<GRectangle>();
		openMenus.add(subMenuBG.getBounds());
		
		if(currentSubMenu != -1) {
			ArrayList<GRectangle> subMenus = new ArrayList<GRectangle>();
			subMenus = subMenu.get(currentSubMenu).getSubHB();
			
			if(subMenus == null) return openMenus;
			
			for(GRectangle temp: subMenus) {
				openMenus.add(temp);
			}
			
		}
		
		return openMenus;
	}

	public void click(GRectangle mouseHitBox) {
		// TODO Auto-generated method stub

		String elementClicked = "";

		
		boolean clicked = false;
		//see which, if any, option was clicked then branch to respective functions
		for(int i = 0; i < subMenu.size(); i++) {
			if(Util.collision(subMenu.get(i).getBounds(), mouseHitBox)) {
				GLabel temp = (GLabel) subMenu.get(i).getElement(1);
				clicked  = true;
				elementClicked = temp.getLabel();
				
				break;
			}
		}
		if(currentSubMenu != -1 && !clicked) {
			subMenu.get(currentSubMenu).click(mouseHitBox);
		}
		if(!clicked) {
			return;
		}
		//if user clicked an option, call respective function
		game.executeMenuOption(elementClicked);
		collapse();
		
	}
	@Override
	public void move(double x, double y) {
		super.move(x, y);
		
		if(subMenu != null) {
			for(MenuOption temp: subMenu) {
				temp.move(x, y);
			}
			subMenuBG.move(x, y);
		}
	}

}
