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
package codes.derive.foldem.example;

import static codes.derive.foldem.Poker.*;

import codes.derive.foldem.Card;
import codes.derive.foldem.Deck;
import codes.derive.foldem.Hand;
import codes.derive.foldem.Suit;

/**
 * An example that shows some basic usage of
 * {@link codes.derive.foldem.Deck}.
 */
public class DecksExample {

	public static void main(String... args) {
		
		// create a deck
		Deck deck = deck();
		
		// show the first card on it
		System.out.println(deck.peek());
		
		// shuffle it
		deck.shuffle();
		
		// deal the first card, should be random
		Card a = deck.pop();
		System.out.println(a);
		
		// deal every other suit of the card dealt
		for (Suit suit : Suit.values()) {
			if (!suit.equals(a.getSuit())) {
				deck.pop(card(a.getValue(), suit));
			}
		}
		
		// deal a hand
		Hand hand = hand(deck.pop(), deck.pop());
		System.out.println(hand);
		
		/*
		 * Should output:
		 * 	As
		 *  <random>
		 *  <random>
		 */
		
	}
	
}
