package codes.derive.foldem.example;

import static codes.derive.foldem.Foldem.*;

import java.util.Collection;

import codes.derive.foldem.Card;
import codes.derive.foldem.Deck;
import codes.derive.foldem.Hand;
import codes.derive.foldem.Suit;

/**
 * An example that shows some basic usage of
 * {@link codes.derive.foldem.Hand}.
 */
public class HandsExample {

	public static void main(String... args) {
		
		// create a hand
		Hand a = hand(card(Card.ACE, Suit.SPADES), card(Card.ACE, Suit.HEARTS));
		
		// create the same hand again using shorthand
		Hand b = hand("AsAh");
		
		// create the hand with no specific suit information
		Collection<Hand> c = handGroup("AA");
		
		// create a hand from a shuffled deck.
		Deck deck = shuffledDeck();
		Hand d = hand(deck.pop(), deck.pop());
		
		// print out hands
		System.out.println(a);
		System.out.println(b);
		for (Hand h : c) {
			System.out.println(h);
		}
		System.out.println(d);
		
		/*
		 * Should output:
		 * 	AsAh
		 *	AsAh
		 *	AsAc
		 *	AsAh
		 *	AsAd
		 * 	AcAh
		 *	AcAd
		 *	AhAd
		 *	<random>
		 */
		
	}
	
}
