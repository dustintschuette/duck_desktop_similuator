package TESTS;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.MouseEvent;

import org.junit.Test;

import acm.graphics.GRectangle;
import entei.DuckPane;
import entei.MainApplication;
import entei.RightClickMenu;

public class BallTests {

	@Test
	public void spawnBallDuckPane() {
		//This test opens the options menu then clicks the spawn ball option and then checks if the ball is moved to the right location
		MainApplication program = new MainApplication();
		DuckPane dPane = new DuckPane(program);
		//open menu
		MouseEvent testRight = mock(MouseEvent.class);
		when(testRight.isControlDown()).thenReturn(true);
		when(testRight.getX()).thenReturn(500);
		when(testRight.getY()).thenReturn(500);
		dPane.mouseClicked(testRight);
		//click spawn ball
		when(testRight.isControlDown()).thenReturn(false);
		when(testRight.getX()).thenReturn(510);
		when(testRight.getY()).thenReturn(526);
		dPane.mouseClicked(testRight);
		
		
		double x = dPane.beachBall.getX();
		double y = dPane.beachBall.getY();
		assertTrue(x == 510 && y == 526);
		
		
	}
	
	@Test
	public void spawnBallMenuCheck() {
		//This test checks to see if the function spawnball() is called when given a click
		//on the spawn ball button in the right click menu
		MainApplication program = new MainApplication();
		DuckPane dPane = mock(DuckPane.class);
		RightClickMenu menu = new RightClickMenu();
		
		GRectangle mousehb = new GRectangle(0, 26, 2, 2);
		
		menu.click(mousehb);
		//click spawn ball
		verify(dPane, times(1)).spawnBall(0, 26);
		
	}

}
