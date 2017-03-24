package codes.derive.foldem;

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import codes.derive.foldem.hand.Hand;

public class TestDeck {

	@Test
	public void testShuffle() {

		/*
		 * create a sampling of how often each card appears at the start of a
		 * shuffled deck.
		 */
		List<Card> cards = new ArrayList<>(cards());
		int[] occurances = new int[52];
		for (int i = 0; i < 100000; i++) {
			occurances[cards.indexOf(shuffledDeck().toArray()[0])]++;
		}
		
		/*
		 * calculate the rate on average any given card appears at the first
		 * position of the deck. this should be roughly 1 in 52 (raising the
		 * sample size creates more accuracy).
		 */
		double rate = 0;
		for (int count : occurances) {
			rate += 100000D / count;
		}
		rate /= 52.0;

		// it just werks
		assertEquals(rate, 52.0, 1.0);
	}
	
	@Test
	public void testPeeking() {
		Deck deck = deck();
		
		// see if peek() equals pop() for all indices
		while (deck.remaining() > 0) {
			assertEquals(deck.peek(), deck.pop());
		}
	}
	
	@Test
	public void testSelectivePopping() {
		Deck deck = deck();
		
		// pop a a hand
		Hand hand = hand("8c8d");
		deck.pop(hand);
		
		// pop the other eights
		deck.pop(card("8s"));
		deck.pop(card("8h"));
		
		// make sure the right number of cards is left
		assertEquals(48, deck.remaining());
		
		// see if everything was popped
		while (deck.remaining() > 0) {
			assertNotEquals(deck.pop().getValue(), Card.EIGHT);
		}
	}
	
	@Test
	public void testDealt() {
		Deck deck = deck();
		assertTrue(deck.dealt(deck.pop()));
		assertFalse(deck.dealt(deck.peek()));
	}
	
}
