package TESTS;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.MouseEvent;

import org.junit.Test;

import entei.DuckPane;
import entei.MainApplication;

public class MenuTests {

	@Test
	public void rightClickMenu() {
		//This test simulates a right click on the screen and checks if the options menu pops up
		
		MainApplication main = new MainApplication();
		DuckPane dPane = new DuckPane(main);
		MouseEvent testRight = mock(MouseEvent.class);
		when(testRight.isControlDown()).thenReturn(true);
		when(testRight.getX()).thenReturn(500);
		when(testRight.getY()).thenReturn(500);
		
		dPane.mouseClicked(testRight);
		assertTrue(dPane.optionsMenu.visible());
	}
	
	@Test
	public void closeMenu() {
		//This test simulates a left click when the menu is open and checks if the option menu closes
		MainApplication main = new MainApplication();
		DuckPane dPane = new DuckPane(main);
		//click to open menu first
		MouseEvent testRight = mock(MouseEvent.class);
		when(testRight.isControlDown()).thenReturn(true);
		when(testRight.getX()).thenReturn(500);
		when(testRight.getY()).thenReturn(500);
		
		dPane.mouseClicked(testRight);
		
		//now click off the menu
		when(testRight.isControlDown()).thenReturn(false);
		when(testRight.getX()).thenReturn(10);
		when(testRight.getY()).thenReturn(10);
		
		dPane.mouseClicked(testRight);
		assertFalse(dPane.optionsMenu.visible());
	}
	
	//clicking off screen should not open/close the menu
	@Test
	public void closeMenuOffScreen() {
		//This test simulates a left click off screen when the menu is open and checks if the option menu closes, it should not
		MainApplication main = new MainApplication();
		DuckPane dPane = new DuckPane(main);
		//click to open menu first
		MouseEvent testRight = mock(MouseEvent.class);
		when(testRight.isControlDown()).thenReturn(true);
		when(testRight.getX()).thenReturn(500);
		when(testRight.getY()).thenReturn(500);
		
		dPane.mouseClicked(testRight);
		
		//now click off the menu
		when(testRight.isControlDown()).thenReturn(false);
		when(testRight.getX()).thenReturn(-10);
		when(testRight.getY()).thenReturn(-10);
		
		dPane.mouseClicked(testRight);
		assertTrue(dPane.optionsMenu.visible());
	}

}
