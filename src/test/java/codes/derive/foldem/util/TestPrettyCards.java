package codes.derive.foldem.util;

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestPrettyCards {

	@Test
	public void testPrettyCards() {
		assertEquals(format(card("Qh")), "Q❤");
	}
	
	@Test
	public void testPrettyHands() {
		assertEquals(format(hand("QhQc")), "Q❤,Q♣");
	}
	
	@Test
	public void testPrettyBoards() {
		// TODO
	}
	
}
