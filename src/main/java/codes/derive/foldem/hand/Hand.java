package codes.derive.foldem.hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import codes.derive.foldem.Card;

/**
 * Represents a single hand.
 */
public class Hand {

	/* The cards contained within this hand. */
	private final List<Card> cards = new ArrayList<>(2);

	/**
	 * Creates a new hand with the specified cards.
	 * 
	 * @param cards
	 *            The cards to use.
	 */
	public Hand(Card... cards) {
		if (cards.length != 2) {
			throw new IllegalArgumentException("Hand length should be 2, " + cards.length + " given");
		}
		this.cards.addAll(Arrays.asList(cards));
	}
	
	/**
	 * Obtains an unmodifiable view of the cards within this hand.
	 * 
	 * @return An unmodifiable view of the cards within this hand.
	 */
	public Collection<Card> cards() {
		return Collections.unmodifiableCollection(cards);
	}
	
	/**
	 * Obtains whether or not the hand is suited.
	 * 
	 * @return <code>true</code> if both cards are the same suit, otherwise
	 *         <code>false</code>.
	 */
	public boolean suited() {
		return cards.get(0).getSuit().equals(cards.get(1).getSuit());
	}
	
	@Override
	public String toString() {
		return cards.get(0).toString().concat(cards.get(1).toString());
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Hand) && ((Hand) o).cards.equals(cards);
	}
	
}
