package entei;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;
import acm.graphics.*;

public class DuckPane extends GraphicsPane implements ActionListener {

	// instance singleton
	public static DuckPane INSTANCE;

	// objects
	private MainApplication program;
	private GImage background;
	public Duck duck;
	public Hat hat;
	private Timer timer;
	private Random rand;
	public BeachBall beachBall;
	public Bread bread;
	private GObject toDrag;
	private GObject clickedItem;
	public RightClickMenu optionsMenu;
	public Pool pool;
	private AchievementBar aBar;
	private Folder aFolder;
	private NotificationBar nBar;
	private Trophy trophy;

	public GLabel bossKeyMsg;
	private GOval bossKeyBubble;

	ArrayList<Bread> breads;
	ArrayList<GObject> onScreen;
	ArrayList<Duck> ducks;
	ArrayList<String> hatPaths;

	// static values
	public static int BALL_SIZE = 56;
	public static int HAT_WIDTH = 160;
	public static int HAT_HEIGHT = 80;
	public static int BREAD_SIZE = 56;
	public static int tickSpeed = 10;
	// primitive variables

	private int defaultDelay = 8000;
	private int currDelay;
	private int range = 5000;
	private int bossKeyCode;
	public int totalBreadEaten = 0;
	private int totalHatched = 0;
	private int totalHides = 0;

	double lastX;
	double lastY;
	double XforVel;
	double YforVel;

	private long clickTime;

	private boolean duckHide = true;
	private boolean bossKeySet = false;
	private boolean menuRefreshed = false;
	private boolean poolSpan = false;

	public DuckPane(MainApplication program) {

		this.program = program;
		INSTANCE = this;
		nBar = new NotificationBar();
		background = new GImage("media/winXPBG.jpg", 0, 0);
		duck = new Duck("media/duck/idle.gif", this);
		duck.setHatPath("media/hats/heli");
		hat = new Hat(duck.getHatPath() + "Center.gif", this);
		hat.setLocation(duck.getX(), duck.getY());
		hat.setSize(HAT_WIDTH, HAT_HEIGHT);
		hat.setVisible(false);

		currDelay = 0;
		timer = new Timer(tickSpeed, this);
		rand = new Random();
		optionsMenu = new RightClickMenu();

		bossKeyMsg = new GLabel("Press a key to set the boss key!", 175, 230);
		bossKeyMsg.setFont("Arial-Bold-20");
		bossKeyBubble = new GOval(100, 175, 500, 100);
		bossKeyBubble.setFilled(true);
		bossKeyBubble.setColor(java.awt.Color.white);
		bossKeyBubble.setFillColor(java.awt.Color.white);

		// initialize interactive objects
		hatPaths = new ArrayList<String>();
		beachBall = new BeachBall("media/equipment/ballRed.png", this);
		
		beachBall.setSize(BALL_SIZE, BALL_SIZE);
		trophy = new Trophy();
		breads = new ArrayList<Bread>();
		onScreen = new ArrayList<GObject>();
		ducks = new ArrayList<Duck>();
		aFolder = new Folder("Achievements");
		aBar = new AchievementBar();
		
		//pool = new Pool (this, 0,0);

		// Add objects to onScreen
		addHatPaths();
		onScreen.add(duck);
		onScreen.add(hat);
		// onScreen.add(hat);
		ducks.add(duck);
	}

	public void addNotification(String job) {
		nBar.addJob(job);
	}

	public void addHatPaths() {
		hatPaths.add("media/hats/heli");
		hatPaths.add("media/hats/johat");
	}

	public void spawnEgg(Duck duck) {
		Egg newEgg = new Egg(this, duck.getX(), duck.getY());
		onScreen.add(newEgg);
		program.add(newEgg);

	}

	public void spawnBabyDuck(Egg egg) {
		// remove egg
		program.remove(egg);
		onScreen.remove(egg);
		// add new duck
		Duck chick = new Duck("media/duck/idle.gif", this);
		chick.setLocation(egg.getX(), egg.getY());
		chick.setSize(80);
		chick.setWalk(ducks.get(0).getWalk());

		onScreen.add(chick);
		program.add(chick);
		ducks.add(chick);
		totalHatched++;
	}

	public void wearHat() {
		duck.setHatToggle(!duck.getHatToggle());

		if (duck.getHatToggle()) {
			hat.setVisible(true);
		} else {
			hat.setVisible(false);
		}
		System.out.println("hat toggle:" + duck.getHatToggle());
	}

	public void toggleHide() {
		if (duckHide) {
			for (GObject temp : onScreen) {
				program.add(temp);
			}
		} else {
			for (GObject temp : onScreen) {
				program.remove(temp);
			}
			totalHides++;
		}
		duckHide = !duckHide;
	}

	public GRectangle getDuckHitBox() {
		return duck.getBounds();
	}

	public void toggleWalking() {

		for (Duck temp : ducks) {
			temp.toggleWalk();
		}

	}

	public void injectToDrag(GObject toDrag) {
		this.toDrag = toDrag;
	}

	public void testDuck(Duck testDuck) {
		duck = testDuck;
	}

	public void spawnBall(double x, double y) {
		beachBall.setLocation(x, y);
		program.add(beachBall);
		addToOnScreen(beachBall);
		System.out.println("spawn ball at " + x + ", " + y);
	}

	public void addToOnScreen(GObject item) {
		if (!onScreen.contains(item)) {
			onScreen.add(item);
			if (!duckHide) {
				program.add(item);
			}
		}
	}

	public void spawnPool(double x, double y) {
		poolSpan = true;
		if (pool == null) {
			pool = new Pool(this, x, y);
		} else {
			pool.add();
		}
		pool.setLocation(x, y);
		System.out.println("spawn pool at " + x + ", " + y);
	}

	public void spawnBread(double x, double y) {
		Bread newBread = new Bread("media/equipment/bread.png");

		newBread.setLocation(x, y);
		program.add(newBread);
		onScreen.add(newBread);
		System.out.println("spawn bread at " + x + ", " + y);
		breads.add(newBread);

		// System.out.println(breads);
		// System.out.println(breads.get(1));
		// System.out.println(breads.get(2));
	}

	public void despawnItem() {
		if (pool.checkPool(clickedItem)) {
			program.remove(pool);
			onScreen.remove(pool);

			program.remove(pool.getBack());
			onScreen.remove(pool.getBack());
		}
		program.remove(clickedItem);
		onScreen.remove(clickedItem);
	}

	public Duck getDuck() {
		return duck;
	}

	// Graphics Pane methods

	@Override
	public void showContents() {
		// TODO Auto-generated method stub

		program.add(background);
		program.add(aFolder);
		toggleHide();
		//spawnEgg(duck);
		program.add(bossKeyBubble);
		program.add(bossKeyMsg);

		timer.start();
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
	}

	private void checkAchievements() {
		if (menuRefreshed)
			return;

		if (totalBreadEaten > 4) {
			aBar.unlock("5 Bread");
		}
		if (totalHatched > 2) {
			aBar.unlock("Hatcher");
		}
		if (checkForAllObjects()) {
			aBar.unlock("Hoarder");
		}
		if (totalHides > 4) {
			aBar.unlock("Hide!");
		}
		if (breads.size() > 4) {
			aBar.unlock("Buffet");
		}

		// check for unlock
		if (aBar.unlockTrophy()) {
			optionsMenu.unlockTrophy();
			menuRefreshed = true;
		}

	}

	private boolean checkForAllObjects() {
		if (!onScreen.contains(pool))
			return false;
		if (!onScreen.contains(beachBall))
			return false;
		if (ducks.size() < 2)
			return false;

		boolean bread = false;
		boolean egg = false;
		for (GObject temp : onScreen) {
			if (temp instanceof Bread) {
				bread = true;
			} else if (temp instanceof Egg) {
				egg = true;
			}
		}
		return (bread && egg);
	}

	// Action Listener Methods
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		beachBall.update();
		nBar.update();
		currDelay -= tickSpeed;

		if (currDelay <= 0) {
			// pick new action to do

			// reset delay to default +- random amount
			currDelay = defaultDelay + rand.nextInt(range);
		}
		// update the duck

		for (Duck temp : ducks) {
			temp.update();
		}
		checkAchievements();
	}

	// -----------------------------Mouse Listener
	// Methods----------------------------------------------------//

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() < 0 || e.getY() < 0)
			return;

		if (e.getX() > 1920 || e.getY() > 1080)
			return;

		lastX = e.getX();
		lastY = e.getY();

		// get click hit box for testing collision
		GRectangle mouseHitBox = new GRectangle(e.getX(), e.getY(), 1, 1);

		// Mac often uses control-click - isControlDown()
		// this code was from stackOverflow and is meant to detect right clicking on all
		// platforms
		if (e.isControlDown() || SwingUtilities.isRightMouseButton(e)) {
			clickedItem = program.getElementAt(e.getX(), e.getY());
			boolean itemSelected = false;
			if (clickedItem == null) {
			} else if (clickedItem instanceof Bread || clickedItem instanceof BeachBall
					|| clickedItem instanceof Pool) {
				itemSelected = true;
				System.out.println("Item right clicked");
			}
			if (!aBar.onScreen || !Util.collision(mouseHitBox, aBar.getBounds())) {
				optionsMenu.show(e.getX(), e.getY(), itemSelected);
			}

			return;
		}

		// ----------------------Double click------------------------//
		// code from stack overflow for detecting double click

		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("double clicked");

			toDrag = program.getElementAt(e.getX(), e.getY());
			if (toDrag instanceof Folder && !aBar.onScreen) {
				aBar.show();
			}

		}

		// ---------------------Left click--------------------------//

		if (aBar.onScreen) {
			if (Util.intersectGRectCircle(mouseHitBox, aBar.getExit())) {
				aBar.hide();
			}
		}

		// see if mouse collided with the options, store as bool
		boolean hitMenu = false;

		ArrayList<GRectangle> menuHB = optionsMenu.getMenu();
		for (GRectangle temp : menuHB) {
			if (temp != null && !hitMenu) {
				hitMenu = Util.collision(temp, mouseHitBox);
				System.out.println("clicked on menu: " + hitMenu);

			}
		}

		// see if the user clicked off the menu
		if (optionsMenu.visible() && !(hitMenu)) {
			optionsMenu.hide();
			return;

		}

		// See if user clicked an options on the menu
		if (optionsMenu.visible() && hitMenu) {
			System.out.println("click called on menu");
			optionsMenu.click(mouseHitBox);
			optionsMenu.hide();
			return;
		}

		GObject clicked = program.getElementAt(e.getX(), e.getY());
		if (clicked instanceof Egg) {
			Egg clickedEgg = (Egg) clicked;
			clickedEgg.click();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		toDrag = program.getElementAt(e.getX(), e.getY());
		if (toDrag == null)
			return;
		clickTime = System.currentTimeMillis();

		if (!(toDrag instanceof Duck) && !(toDrag.equals(beachBall)) && !(toDrag instanceof Bread)
				&& !(toDrag instanceof Egg) && !(toDrag instanceof Trophy)) {
			toDrag = null;
			return;
		}

		if (toDrag instanceof Chaseable && duck.getState() != DuckState.SLEEP) {
			duck.setChaseObject(toDrag);
			duck.setState(DuckState.CHASE);
		}
		if (toDrag instanceof Duck) {
			duck.setState(DuckState.DRAG);
		}

		lastX = e.getX();
		lastY = e.getY();
		XforVel = e.getX();
		YforVel = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (toDrag == null)
			return;

		double xMove = e.getX() - lastX;
		double yMove = e.getY() - lastY;

		// check if x movement is in bounds
		if (!(toDrag.getX() + xMove < 0 || toDrag.getX() + toDrag.getWidth() + xMove > 1920)) {
			toDrag.move(xMove, 0);
		}

		// check if y movement is in bounds
		if (!(toDrag.getY() + yMove < 0 || toDrag.getY() + toDrag.getHeight() + yMove > 1080)) {
			toDrag.move(0, yMove);
		}

		// toDrag.move(e.getX() - lastX, e.getY() - lastY);

		lastX = e.getX();
		lastY = e.getY();

		if (System.currentTimeMillis() - clickTime > 1000) {
			XforVel = e.getX();
			YforVel = e.getY();
			clickTime = System.currentTimeMillis();

		}

		if (toDrag instanceof Chaseable) {

			for (Duck temp : ducks) {
				if (temp.getState() != DuckState.SLEEP) {
					temp.setChaseObject(toDrag);
					temp.setState(DuckState.CHASE);
				}
			}

		} else if (toDrag instanceof Duck) {
			Duck duck = (Duck) toDrag;
			duck.setState(DuckState.IDLE);
		}

		if (duck.getState() == DuckState.SLEEP) {
			hat.setLocation(duck.getX(), duck.getY() - 8);
		} else {
			hat.setLocation(duck.getX(), duck.getY() - 16);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (toDrag instanceof BeachBall) {
			long deltaTime = System.currentTimeMillis() - clickTime;
			if (deltaTime == 0) {
				deltaTime = 10;
			}
			double dx = ((e.getX() - XforVel) / deltaTime) * 10;
			double dy = ((e.getY() - YforVel) / deltaTime) * 10;
			System.out.println("dx: " + dx + ", dy: " + dy);
			// player can not throw ball inside the duck
			if (!Util.collision(getDuckHitBox(), beachBall.getBounds())) {
				beachBall.setVelocity(dx, dy);
			}

		} else if (toDrag instanceof Bread) {
			GRectangle mouseHB = new GRectangle(e.getX(), e.getY(), 1, 1);

			for (int i = 0; i < ducks.size(); i++) {
				if (Util.collision(mouseHB, ducks.get(i).getBounds())) {
					// they fed the duck
					Bread temp = (Bread) toDrag;
					eatBread(temp);
					ducks.get(i).setState(DuckState.EAT);
					ducks.get(i).resetSleep();
					System.out.println("Player fed the duck");
					return;
				}
			}

		} else if (toDrag instanceof Duck) {
			Duck dragged = (Duck) toDrag;
			dragged.resetSleep();
			
			if (poolSpan) {

			if (Util.collision(pool.getBounds(), duck.getBounds())) {
				duck.setState(DuckState.IDLE);

				duck.toggleWalk();
				System.out.println("Duck in pool");
			} else {
				System.out.println("Duck out of pool");
				duck.toggleWalk();
			}
			
			}

		}

		toDrag = null;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (optionsMenu.visible()) {
			optionsMenu.hlBoxes(e.getX(), e.getY());
		}
		if (aBar.onScreen) {
			aBar.checkHover(e.getX(), e.getY());
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		
		if(!bossKeySet) {
			if(key == KeyEvent.VK_H) {
				addNotification("\"H\" cannot be assigned to boss key");
				return;
			}

			bossKeySet = true;
			bossKeyCode = key;
			program.remove(bossKeyBubble);
			program.remove(bossKeyMsg);
			return;
		}

		if (key == bossKeyCode) {
			toggleHide();
			return;
		}

		// switch between hats
		if (key == KeyEvent.VK_H) {
			for (int i = 0; i < hatPaths.size(); i++) {
				if (duck.getHatPath().equals(hatPaths.get(i))) {
					if (i + 1 == hatPaths.size()) {
						hat.setImage(hatPaths.get(0) + "Center.gif");
						duck.setHatPath(hatPaths.get(0));
						System.out.println("up\n");
					} else {
						hat.setImage(hatPaths.get(i++) + "Center.gif");
						duck.setHatPath(hatPaths.get(i++));
					}
				}
			}
		}

		switch (key) {

		case KeyEvent.VK_M:
			duck.toggleWalk();
			break;
		}
	}

	public void checkForBread(Duck duck) {
		// System.out.println("Checking for bread");
		double x = -1;
		double y = -1;
		double min = -1;
		for (int i = 0; i < breads.size(); i++) {
			double sideA;
			double sideB;

			// left of duck
			if (breads.get(i).getX() < duck.getX()) {
				sideA = duck.getX() - (breads.get(i).getX() + breads.get(i).getWidth() / 2);

				// right of duck
			} else {
				sideA = breads.get(i).getX() - (duck.getX() + duck.getWidth());
			}

			if (breads.get(i).getY() < duck.getY()) {
				sideB = duck.getY() - (breads.get(i).getY() + breads.get(i).getHeight() / 2);

				// below the duck
			} else {
				sideB = breads.get(i).getY() - (duck.getY() + duck.getHeight());
			}
			// find distance to bread see if it's in range
			double sideC = Math.sqrt((sideA * sideA) + (sideB * sideB));
			// System.out.println("Distance to bread #" + i + ": " + sideC);
			if (sideC <= Duck.BREAD_RANGE) {
				if (min == -1 || sideC < min) {
					min = sideC;
					if (min < Duck.EATING_RANGE) {
						eatBread(breads.get(i));
						duck.setState(DuckState.EAT);
						return;
					}
					x = breads.get(i).getX() + breads.get(i).getWidth() / 2;
					y = breads.get(i).getY() + breads.get(i).getHeight() / 2;
				}
			}

		}
		if (min != -1) {
			// System.out.println("Bread found");
			duck.setDestination(x, y);
			duck.setState(DuckState.WALK);
		}

	}

	public void removeAllDucks() {
		for (Duck duck : ducks) {
			program.remove(duck);
			onScreen.remove(duck);
		}
		program.remove(hat);
		onScreen.remove(hat);
	}

	public void removeBabyDucks() {
		Iterator<Duck> it = ducks.iterator();
		while (it.hasNext()) {
			Duck temp = it.next();
			if (!temp.equals(ducks.get(0))) {
				program.remove(temp);
				onScreen.remove(temp);
				it.remove();
			}

		}
		System.out.println("Removed Ducks");
	}

	public void addDucks() {
		for (Duck duck : ducks) {
			program.add(duck);
			onScreen.add(duck);
		}
		program.add(hat);
		onScreen.add(hat);
	}

	public void eatBread(Bread target) {
		totalBreadEaten++;
		program.remove(target);
		if (onScreen.remove(target))
			System.out.println("found bread in onscreen");
		System.out.println("number of breads: " + breads.size());
		breads.remove(target);

	}

	public void resetBossKey() {
		// TODO Auto-generated method stub
		bossKeySet = false;
		program.add(bossKeyBubble);
		program.add(bossKeyMsg);

	}

	public void executeMenuOption(String selection) {
		System.out.println("request to execute: " + selection);

		switch (selection) {

		case "":
			return;

		case "Spawn Ball":
			spawnBall(lastX, lastY);
			break;

		case "Spawn Bread":
			spawnBread(lastX, lastY);
			break;

		case "Spawn Pool":
			spawnPool(lastX, lastY);
			break;

		case "Hat":

			wearHat();
			break;

		case "Toggle Walking":
			toggleWalking();
			break;

		case "Hide":
			toggleHide();
			break;

		case "Despawn item":
			despawnItem();
			break;

		case "Reset Boss Key":
			resetBossKey();
			break;

		case "Despawn ducks":
			removeBabyDucks();
			break;

		case "Spawn Trophy":
			spawnTrophy(lastX, lastY);
			break;

		}

	}

	private void spawnTrophy(double x, double y) {
		trophy.setLocation(x, y);
		addToOnScreen(trophy);

	}
}
