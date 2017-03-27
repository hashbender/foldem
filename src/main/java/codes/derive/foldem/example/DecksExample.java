package codes.derive.foldem.example;

import static codes.derive.foldem.Foldem.*;

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
