package codes.derive.foldem;

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class FormatTest {

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
