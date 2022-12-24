package entei;

import java.io.File;
import java.util.ArrayList;

import acm.graphics.GImage;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1920;
	public static final int WINDOW_HEIGHT = 1080;
	public static final String MUSIC_FOLDER = "Sounds";
	//private static final String[] SOUND_FILES = { "Track0.wav", "Track1.wav" };
	
	public static MainApplication INSTANCE;

	public boolean quickPlay;
	DuckPane game;
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		INSTANCE = this;
		
	}

	public void run() {
		System.out.println("Initializing Game");
		setupInteractions();
		game = new DuckPane(this);
		switchToDuck();
		
	}
	
	public void switchToDuck() {
		switchToScreen(game);
	}
	
	public static void main(String[] args) {
		new MainApplication().start();
	}

}
