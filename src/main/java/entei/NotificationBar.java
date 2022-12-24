package entei;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;

public class NotificationBar {
	
	
	//objects
	MainApplication program;
	private ArrayList<String> jobs;
	private GImage bg;
	private ArrayList<GLabel> text;
	private Font OSD;
	
	//primitives
	private int animationLength = 500;	//time it takes for bar to finish popping up
	private int animCounter;
	private int defaultDelay = 2000;	//time (ms) of total animation
	private int delay;					//time (ms) notification stays on screen
	private double maxWidth = 600;		//size of box when grown
	private boolean opening = false;
	private boolean closing = false;
	private double xGrowSpeed;			//speed at which the box grows
	private double xMoveSpeed;			//speed at which the box has to move right to keep center
	private double spawnX = 320;		//x value the box should be at when fully open
	private double currentSize = 0;
	
	
	NotificationBar(){
		//initialize objects
		program = MainApplication.INSTANCE;
		jobs = new ArrayList<String>();
	
		bg = new GImage("media/textBox1.png", spawnX + (maxWidth/2), 30);
		bg.setSize(0, 75);
		
		//initializing counters to default
		resetTimers();
		setText();
		
		double numTicks = animationLength/DuckPane.tickSpeed;
		xGrowSpeed = (maxWidth) / numTicks;
		
		xMoveSpeed = (maxWidth/2) / numTicks;
	}
	
	private void setText() {
		try {
		    //create the font to use. Specify the size!
		    OSD = Font.createFont(Font.TRUETYPE_FONT, new File("media/fonts/OSD.ttf")).deriveFont(20f);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}

		

	}
	
	private void resetTimers() {
		delay = defaultDelay;
		animCounter = animationLength;
	}
	
	
	public void addJob(String job) {
		jobs.add(job);
	}
	
	private void addText() {
		for(GLabel temp: text) {
			program.add(temp);
		}
	}
	
	private void removeText() {
		for(GLabel temp: text) {
			program.remove(temp);
		}
	}
	
	public void update() {
		if(jobs.size() < 1) return;
		
		
		if(!opening && !closing && currentSize != maxWidth) {
			opening = true;
			text = Util.fixAchievementLength(jobs.get(0), OSD);
			program.add(bg);
			
		}
		
		if(opening) {
			animCounter -= DuckPane.tickSpeed;
			currentSize += xGrowSpeed;
			bg.setSize(currentSize, bg.getHeight());
			bg.move(-1 * xMoveSpeed, 0);
			
			if(animCounter < 10) {
				animCounter = 0;
				bg.setLocation(spawnX, bg.getY());
				bg.setSize(maxWidth, bg.getHeight());
				currentSize = maxWidth;
				opening = false;
				addText();
			}
		}
		else if(closing) {
			animCounter += DuckPane.tickSpeed;
			currentSize -= xGrowSpeed;
			bg.setSize(currentSize, bg.getHeight());
			bg.move( xMoveSpeed, 0);
			
			if(animCounter > animationLength -10) {
				animCounter = animationLength;
				bg.setLocation(spawnX + (maxWidth/2), bg.getY());
				bg.setSize(0, bg.getHeight());
				currentSize = 0;
				closing = false;
				program.remove(bg);
				jobs.remove(0);
			}
			
		}else {
			delay -= DuckPane.tickSpeed;
			if(delay < 10) {
				closing = true;
				delay = defaultDelay;
				removeText();
				
			}
		}
		
		
	}

}
