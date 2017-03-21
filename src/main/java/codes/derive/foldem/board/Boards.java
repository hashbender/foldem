package codes.derive.foldem.board;

import codes.derive.foldem.Card;
import codes.derive.foldem.Deck;

/**
 * Helper class containing functions for working with boards.
 */
public class Boards {

	/**
	 * Constructs a new board containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A board containing the specified card.
	 */
	public static Board board(Card... cards) {
		for (Street street : Street.values()) {
			if (cards.length == street.cardCount()) {
				return new GenericBoard(street, cards);
			}
		}
		throw new IllegalArgumentException("No board for card count "
				+ cards.length);
	}

	/**
	 * Constructs a new flop containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new flop containing the specified cards.
	 */
	public static Board flop(Card... cards) {
		return new Flop(cards);
	}

	/**
	 * Constructs a new turn containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new turn containing the specified cards.
	 */
	public static Board turn(Card... cards) {
		return new Turn(cards);
	}

	/**
	 * Constructs a new river containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new river containing the specified cards.
	 */
	public static Board river(Card... cards) {
		return new River(cards);
	}

	/**
	 * Constructs a new flop sourced from cards popped from the specified deck.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new flop sourced from cards popped from the specified deck.
	 */
	public static Board flop(Deck deck) {
		Card[] cards = new Card[3];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new Flop(cards);
	}

	/**
	 * Constructs a new turn sourced from cards popped from the specified deck.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new turn sourced from cards popped from the specified deck.
	 */
	public static Board turn(Deck deck) {
		Card[] cards = new Card[4];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new Turn(cards);
	}

	/**
	 * Constructs a new river sourced from cards popped from the specified deck.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new river sourced from cards popped from the specified deck.
	 */
	public static Board river(Deck deck) {
		Card[] cards = new Card[5];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new River(cards);
	}

}
