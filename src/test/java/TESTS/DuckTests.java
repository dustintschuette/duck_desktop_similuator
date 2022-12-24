package TESTS;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.MouseEvent;

import org.junit.Test;

import entei.Duck;
import entei.DuckPane;
import entei.DuckState;
import entei.MainApplication;

public class DuckTests {

	@Test
	public void duckUpdateTest() {
		//test to make sure duck is updating once per tick
		MainApplication main = new MainApplication();
		DuckPane dPane = new DuckPane(main);
		
		//making a fake duck for tracking
		Duck duck = mock(Duck.class);
		//injecting the fake duck into our pane
		dPane.testDuck(duck);
		//telling the fake duck how to respond if the getAction function is called
		when(duck.getState()).thenReturn(DuckState.IDLE);
		//manually force a tick on the duckPane
		dPane.actionPerformed(null);
		//see if the duck was called to update 1 time
		verify(duck, times(1)).update();
		
	}
	
	@Test
	public void draggingMockitoTest() {
		//this text is to check if the duck has changed positions by being dragged
		MainApplication main = new MainApplication();
		DuckPane dPane = new DuckPane(main);
		MouseEvent z = mock(MouseEvent.class);
		when(z.getX()).thenReturn(180);
		when(z.getY()).thenReturn(1220);
		dPane.mousePressed(z);
		when(z.getX()).thenReturn(500);
		dPane.mouseDragged(z);
		assertTrue(dPane.duck.getX() > 180 );

	}
	
	
	

}
