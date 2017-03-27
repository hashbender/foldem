package codes.derive.foldem;

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestCard {

	@Test
	public void testShorthand() {
		assertEquals(card("Ac"), card(Card.ACE, Suit.CLUBS));
	}
	
	@Test
	public void testDeal() {
		assertEquals(card(deck()), deck().pop());
	}
	
}
