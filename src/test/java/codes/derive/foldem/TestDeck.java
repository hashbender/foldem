/*
 * This file is part of Fold'em, a Java library for Texas Hold 'em Poker.
 *
 * Fold'em is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fold'em is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Fold'em.  If not, see <http://www.gnu.org/licenses/>.
 */
package codes.derive.foldem;

import static codes.derive.foldem.Poker.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TestDeck {

	/*
	 * This test is currently disabled because it causes Travis to fail because
	 * of CPU overuse. If you need to make sure your decks are secure you can
	 * re-activate it and run it yourself.
	 */
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
