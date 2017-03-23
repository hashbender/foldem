package codes.derive.foldem;

import java.util.Random;

import codes.derive.foldem.hand.Hand;
import codes.derive.foldem.util.RandomContext;

/**
 * Represents a standard 52-card deck of cards.
 * <p>
 * This type has stack-like functionality with the "pop" methods taking a card
 * from the next available position in the deck and moving the cursor to the
 * following card.
 * </p>
 */
public class Deck {

	/* Backing array containing cards held within this deck. */
	private final Card[] cards = new Card[Constants.DECK_SIZE];

	/* Current cursor offset in the backing array. */
	private int currentIndex = 0;

	/**
	 * Constructs a new deck with the cards placed in a basic uniform order.
	 */
	public Deck() {
		int offset = 0;
		for (Suit suit : Suit.values()) {
			for (int num = Card.ACE; num <= Card.KING; num++) {
				cards[offset++] = new Card(num, suit);
			}
		}
	}
	
	/**
	 * Orders the the deck randomly using the default random number generator.
	 * 
	 * @return The current {@link Deck} context, for chaining.
	 */
	public Deck shuffle() {
		return shuffle(RandomContext.get());
	}
	
	/**
	 * Orders the deck randomly using the provided Random context.
	 * 
	 * @param random
	 *            The random number generator to use to shuffle the deck.
	 * @return The current {@link Deck} context, for chaining.
	 */
	public Deck shuffle(Random random)  {
		if (currentIndex > 0) {
			throw new IllegalStateException("Deck cannot be shuffled after a card has been pulled");
		}	
		for (int i = 0; i < Constants.DECK_SIZE; i++) {
			int swap = random.nextInt(Constants.DECK_SIZE);
			Card temp = cards[swap];
			cards[swap] = cards[i];
			cards[i] = temp;
		}
		return this;
		
	}

	/**
	 * Pops the specified card from the deck, throwing an exception if the card
	 * has already been dealt. This function essentially moves the specified
	 * card to the front of the deck, and then pops it normally.
	 * 
	 * @param c
	 *            The card to pop from the deck.
	 * @return The card popped, should be the same as the card specified.
	 */
	public Card pop(Card c) {
		int index = 0;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].equals(c)) {
				index = i;
				break;
			}
		}
		if (index < currentIndex) {
			throw new IllegalArgumentException("Card already dealt");
		}
		if (index > 0) {
			Card temp = cards[index];
			cards[index] = cards[currentIndex];
			cards[currentIndex] = temp;
		}
		return pop();
	}
	
	/**
	 * Pops a specific hand from the deck, works in the same way as pop(Card)
	 * but with a hand.
	 * 
	 * @param h
	 *            The hand to pop.
	 * @return The hand popped, should be the same as the hand specified.
	 */
	public Hand pop(Hand h) {
		for (Card c : h.cards()) {
			pop(c);
		}
		return h;
	}

	/**
	 * Pops the next available card from the deck.
	 * 
	 * @return The next available card from the deck.
	 */
	public Card pop() {
		if (currentIndex >= cards.length) {
			throw new IllegalStateException("No cards, you can use remaining() to check");
		}
		return cards[currentIndex++];
	}

	/**
	 * Obtains the next available card on the deck without popping it.
	 * @return
	 * 		The next available card on the deck.
	 */
	public Card peek() {
		return cards[currentIndex];
	}
	
	/**
	 * Obtains the number of cards left in the deck that can be popped.
	 * 
	 * @return The number of cards left in the deck that can be popped.
	 */
	public int remaining() {
		return Constants.DECK_SIZE - currentIndex;
	}

	/**
	 * Checks to see if the specified card has already been dealt from this
	 * deck.
	 * 
	 * @param c
	 *            The card to check.
	 * @return <code>true</code> if the card has been dealt, <code>false</code>
	 *         otherwise.
	 */
	public boolean dealt(Card c) {
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].equals(c)) {
				return i < currentIndex;
			}
		}
		throw new AssertionError("Card " + c + " was not found in the deck");
	}
	
	@Override
	public String toString() {
		return Deck.class.getName() + " [dealt=" + currentIndex + "]";
	}

}
