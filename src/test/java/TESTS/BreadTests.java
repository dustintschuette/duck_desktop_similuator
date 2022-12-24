package TESTS;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.MouseEvent;

import org.junit.Test;

import entei.DuckPane;
import entei.DuckState;
import entei.MainApplication;

public class BreadTests {

	@Test
	public void spawnBread() {
		MainApplication program = new MainApplication();
		DuckPane dPane = new DuckPane(program);
		//This test opens the options menu then clicks the spawn bread option and then checks if the bread is moved to the right location
		//open menu
		MouseEvent testRight = mock(MouseEvent.class);
		when(testRight.isControlDown()).thenReturn(true);
		when(testRight.getX()).thenReturn(500);
		when(testRight.getY()).thenReturn(500);
		dPane.mouseClicked(testRight);
		//click spawn bread
		when(testRight.isControlDown()).thenReturn(false);
		when(testRight.getX()).thenReturn(510);
		when(testRight.getY()).thenReturn(526);
		dPane.mouseClicked(testRight);
		
		double x = dPane.bread.getX();
		double y = dPane.bread.getY();
		assertTrue(x == 510 && y == 526);
	}
	@Test
	public void dragBread() {
		MainApplication program = new MainApplication();
		DuckPane dPane = new DuckPane(program);
		//spawn bread
		MouseEvent testRight = mock(MouseEvent.class);
		when(testRight.isControlDown()).thenReturn(true);
		when(testRight.getX()).thenReturn(500);
		when(testRight.getY()).thenReturn(500);
		dPane.mouseClicked(testRight);
		//click spawn bread
		when(testRight.isControlDown()).thenReturn(false);
		when(testRight.getX()).thenReturn(510);
		when(testRight.getY()).thenReturn(526);
		dPane.mouseClicked(testRight);
		
		//click on bread to drag it
		when(testRight.isControlDown()).thenReturn(false);
		when(testRight.getX()).thenReturn(510);
		when(testRight.getY()).thenReturn(526);
		dPane.mousePressed(testRight);
		
		when(testRight.getX()).thenReturn(600);
		when(testRight.getY()).thenReturn(600);
		//drag the bread
		dPane.mouseDragged(testRight);
		
		double x = dPane.bread.getX();
		double y = dPane.bread.getY();
		assertTrue(x == 600 && y == 600);
	}
	
	@Test
	public void feedBread() {
		MainApplication program = new MainApplication();
		DuckPane dPane = new DuckPane(program);
		//open menu
		MouseEvent testRight = mock(MouseEvent.class);
		when(testRight.isControlDown()).thenReturn(true);
		when(testRight.getX()).thenReturn(500);
		when(testRight.getY()).thenReturn(500);
		dPane.mouseClicked(testRight);
		
		//click spawn bread
		when(testRight.isControlDown()).thenReturn(false);
		when(testRight.getX()).thenReturn(510);
		when(testRight.getY()).thenReturn(526);
		dPane.mouseClicked(testRight);
		
		//click on bread to drag it
		when(testRight.isControlDown()).thenReturn(false);
		when(testRight.getX()).thenReturn(510);
		when(testRight.getY()).thenReturn(526);
		dPane.mousePressed(testRight);
		
		//drag the bread to duck
		when(testRight.getX()).thenReturn((int) dPane.getDuck().getX());
		when(testRight.getY()).thenReturn((int) dPane.getDuck().getY());
		dPane.mouseDragged(testRight);
		dPane.mouseReleased(testRight);
		
		assertTrue(dPane.getDuck().getState() == DuckState.EAT);
	}
	

}
