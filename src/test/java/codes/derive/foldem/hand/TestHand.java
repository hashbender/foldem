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
package codes.derive.foldem.hand;

import static codes.derive.foldem.Foldem.*;
import static codes.derive.foldem.Card.*;
import static codes.derive.foldem.Suit.*;
import static org.junit.Assert.*;

import org.junit.Test;

import codes.derive.foldem.Deck;
import codes.derive.foldem.Hand;

public class TestHand {
	
	@Test
	public void testSuited() {
		assertFalse(hand("AcAh").suited());
		assertTrue(hand("Ac2c").suited());
	}
	
	@Test
	public void testShorthand() {
		assertEquals(hand(card(ACE, SPADES), card(ACE, CLUBS)), hand("AsAc"));
	}

	@Test
	public void testDeal() {
		Hand hand = hand(deck());
		Deck comparison = deck();
		assertTrue(hand.cards().contains(comparison.pop()));
		assertTrue(hand.cards().contains(comparison.pop()));
	}
	
}
