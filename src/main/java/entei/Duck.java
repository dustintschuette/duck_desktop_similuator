package entei;

import java.util.Random;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;

public class Duck extends GImage {

	/**
	 * 
	 */
	//static
	private static final long serialVersionUID = 1L;
	public static int BREAD_RANGE = 200;
	public static int EATING_RANGE = 10;
	
	private MainApplication program;
	private int size =  160;
	public static int HAT_WIDTH = 160;
	public static int HAT_HEIGHT = 80;
	
	private static int STARTX = 1200;
	private static int STARTY = 800;
	private static float velocity = 2f;
	private DuckState currState;
	private DuckAnim currAnim;
	private float dx, dy;
	private double destX, destY;
	private GObject toChase;
	private DuckPane game;
	private Hat hat;
	
	//image paths for actions

	// image paths for actions
	private String leftImagePath = "media/duck/walkLeft.gif";
	private String eatImagePath = "media/duck/eat.gif";
	private String rightImagePath = "media/duck/walkRight.gif";
	private String idleImagePath = "media/duck/idle.gif";
	private String dragImagePath = "media/duck/idle.gif";
	private String sleepImagePath = "media/duck/sleep.gif";
	private String hatBasePath = "media/hats/heli";
	
	private int defaultTime = 1000;
	private int tickRate = 20;
	private int currentTick = defaultTime;
	private int defaultSleepTime = 10000;
	private int currentSleepTime = defaultSleepTime;
	private int breadEaten = 0;
	
	private boolean toggleWalk = false;
	private boolean hatToggle = false;
	
	public Duck(String string, DuckPane game) {
		super(string);
		super.setLocation(STARTX, STARTY);
		super.setSize(size, size);
		this.game = game;
		currState = DuckState.IDLE;
		currAnim = DuckAnim.IDLE;
		currentTick = 1000;
		// TODO Auto-generated constructor stub
	}
	public void setSize(int size) {
		this.size = size;
		super.setSize(size, size);
	}

	/*
	 * GETTERS and SETTERS
	 */
	public void setHatToggle(boolean toggle) {
		hatToggle = toggle;
	}
	
	public boolean getHatToggle() {
		return hatToggle;
	}
	
	public void setHatPath(String path) {
		hatBasePath = path;
		if(!(game.hat == null)) setAnim(currAnim);
	}
	
	public String getHatPath() {
		return hatBasePath;
	}
	public boolean getWalk() {
		return toggleWalk;
	}
	
	public void setWalk(boolean walk) {
		toggleWalk = walk;
	}
	
	public void toggleWalk() {
		toggleWalk = !toggleWalk;
		currState = DuckState.IDLE;
		setAnim(DuckAnim.IDLE);
	}

	public void setDuckHeight(String path) {
		hatBasePath = path;
	}

	public int getDuckHeight() {
		return size;
	}

	public int getDuckWidth() {
		return size;
	}

	public void setDuckLocation(float X, float Y) {
		super.setLocation(X, Y);
	}

	public GPoint getDuckLocation() {
		return super.getLocation();
	}

	public void setState(DuckState newState) {
		currState = newState;
		currentTick = defaultTime;
	}

	public DuckState getState() {
		return currState;
	}

	public void setDestination(double x, double y) {
		destX = x;
		destY = y;
	}
	


	public void update() {
		// switch statement for actions
		// TODO tickrate for states
		currentSleepTime -= DuckPane.tickSpeed;
		if(currentSleepTime < 10) {
			currState = DuckState.SLEEP;
		}
		switch (currState) {
		case IDLE:
			getNewAction();
			break;
		case WALK:
			moveToDest();
			break;
		case CHASE:
			chase();
			break;
			
			//duck should finish eating before looking for more bread
		case EAT:   eat();
			return;

		case SLEEP: sleep();
			break;
			
			//this statement returns because the duck should not stop to look for bread
		case DRAG: drag();
			return;

		case STOP:
			stayStill();
			break;
		
		}
		if (hatToggle) {
			if (currState == DuckState.SLEEP) {
				game.hat.setLocation(super.getX(), super.getY() - 8);
			}
			else {
				game.hat.setLocation(super.getX(), super.getY() - 16);
			}
			game.hat.setSize(HAT_WIDTH, HAT_HEIGHT);
		}
		checkForBread();
	}
	
	private void checkForBread() {
		game.checkForBread(this);

	}

	private void drag() {
		if (currAnim != DuckAnim.DRAG) {
			setAnim(DuckAnim.DRAG);
		}
		resetSleep();
		
	}

	public void resetSleep() {
		currentSleepTime = defaultSleepTime;
		if (currState == DuckState.SLEEP || currState == DuckState.DRAG) {
			currState = DuckState.IDLE;
		}
	}
	
	private void eat() {
		// Set state action gif
		if (currAnim != DuckAnim.EAT) {
			setAnim(DuckAnim.EAT);
			breadEaten++;
		}
		// wait
		currentTick -= tickRate;

		// see if action is over
		if (currentTick < 10) {
			currentTick = defaultTime;
			currState = DuckState.IDLE;
			resetSleep();
		}
		
		if(breadEaten == 3) {
			game.spawnEgg(this);
			breadEaten = 0;
		}
		
	}

	private void sleep() {
		if (currAnim != DuckAnim.SLEEP) {
			setAnim(DuckAnim.SLEEP);
			System.out.println("Duck is sleeping");
		}
	}
	
	public void setChaseObject(GObject toChase) {
		this.toChase = toChase;
	}

	private void chase() {
		if (toChase == null) {
			currState = DuckState.IDLE;
			return;
		}
		destX = toChase.getX();
		destY = toChase.getY();
		moveToDest();
		if (currState == DuckState.IDLE) {
			toChase = null;
			return;
		}
	}

	private void getNewAction() {
		if(currAnim != DuckAnim.IDLE) {
			currAnim = DuckAnim.IDLE;
			super.setImage(idleImagePath);
			super.setSize(size, size);
		}
		if(toggleWalk) return;
		currentTick -= tickRate;
		if (currentTick < 10) {
			currentTick = defaultTime;
		} else {
			return;
		}

		Random rand = new Random();
		destX = rand.nextInt((int) (1915 - super.getWidth()));
		destY = rand.nextInt((int) (1075 - super.getHeight()));
		setState(DuckState.WALK);
	}

	private void stayStill() {
		if (currAnim != DuckAnim.IDLE) {
			setAnim(DuckAnim.IDLE);
		}
	}

	/**
	 * moveToDest
	 */
	private void moveToDest() {
		if (super.getX() > destX) {
			dx = velocity * -1;
		} else {
			dx = velocity;
		}

		if (super.getY() > destY) {
			dy = velocity * -1;
		} else {
			dy = velocity;
		}

		if (Math.abs(super.getX() + dx - destX) < velocity + 0.05) {
			super.setLocation(destX, super.getY());
			dx = 0;
		}
		if (Math.abs(super.getY() + dy - destY) < velocity + 0.05) {
			super.setLocation(super.getX(), destY);
			dy = 0;
		}
		if (super.getX() == destX && super.getY() == destY) {
			currState = DuckState.IDLE;
			setAnim(DuckAnim.IDLE);
		} else if (dx > 0 && currAnim != DuckAnim.WALKRIGHT) {
			setAnim(DuckAnim.WALKRIGHT);
		} else if (dx < 0 && currAnim != DuckAnim.WALKLEFT) {
			setAnim(DuckAnim.WALKLEFT);
		}
		// System.out.println("dx: " + dx + "|| dy:" + dy);
		super.move(dx, dy);
	}
	
	//nice helper function for setting animation
	private void setAnim(DuckAnim newAnim) {
		switch(newAnim) {
		
		case WALKLEFT:
			super.setImage(leftImagePath);
			game.hat.setImage(hatBasePath + "Left.gif");
			currAnim = DuckAnim.WALKLEFT;
			break;
			
		case WALKRIGHT:
			super.setImage(rightImagePath);
			game.hat.setImage(hatBasePath + "Right.gif");
			currAnim = DuckAnim.WALKRIGHT;
			break;
			
		case EAT:
			super.setImage(eatImagePath);
			game.hat.setImage(hatBasePath + "Center.gif");
			currAnim = DuckAnim.EAT;
			break;

		case IDLE:
			super.setImage(idleImagePath);
			game.hat.setImage(hatBasePath + "Center.gif");
			currAnim = DuckAnim.IDLE;
			break;
			
		case DRAG:
			currAnim = DuckAnim.DRAG;
			game.hat.setImage(hatBasePath + "Center.gif");
			super.setImage(dragImagePath);
			break;
			
		case SLEEP:
			currAnim = DuckAnim.SLEEP;
			game.hat.setImage(hatBasePath + "Center.gif");
			super.setImage(sleepImagePath);
			break;
		}
		super.setSize(size, size);
		game.hat.resetHatSize();
	}

}
