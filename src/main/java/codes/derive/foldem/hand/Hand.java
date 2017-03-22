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
			throw new IllegalArgumentException("Illegal number of cards");
		}
		this.cards.addAll(Arrays.asList(cards));
	}
	
	/**
	 * Creates a new hand with the specified string shorthand.
	 * 
	 * <p>
	 * This uses the same format as single cards. For example, "AsAc" would
	 * create a hand with the ace of spaces and the ace of clubs. For more
	 * information on this format please refer to
	 * {@link codes.derive.foldem.Card#Card(String)}.
	 * </p>
	 * 
	 * @param cards
	 *            The cards represented as a shorthand string.
	 * @see codes.derive.foldem.Card#Card(String)
	 */
	public Hand(String cards) {
		this(new Card(cards.substring(0, 2)), new Card(cards.substring(2, 4)));
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
