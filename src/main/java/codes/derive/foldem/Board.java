package codes.derive.foldem;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.hand.Hand;

/**
 * Represents a standard five card board.
 */
public class Board {
		
	/* The evaluator to be used for hand comparison on boards. */
	private static final DefaultEvaluator eval = new DefaultEvaluator();
	
	/* The cards represented on this board. */
	private final Card[] cards = new Card[Constants.BOARD_SIZE];

	/**
	 * Represents a street on a given board.
	 */
	public enum Street {
		
		/**
		 * Before the flop.
		 */
		PREFLOP(0),
		
		/**
		 * On the flop, 3 cards.
		 */
		FLOP(3),
		
		/**
		 * On the turn, 4 cards.
		 */
		TURN(4),
		
		/**
		 * On the river, 5 cards.
		 */
		RIVER(5);
		
		/* The number of cards available on this street */
		private final int cards;
		
		/**
		 * Constructs a street with the provided number of cards.
		 * @param cards
		 * 		The number of cards available on the street.
		 */
		private Street(int cards) {
			this.cards = cards;
		}
		
		/**
		 * Gets the number of cards available on the street.
		 * @return
		 * 		The number of cards available on the street.
		 */
		public int cardCount() {
			return cards;
		}
	}

	/**
	 * Constructs a new board, popping its cards from the deck specified.
	 * @param deck
	 * 		The deck to pop cards from.
	 */
	protected Board(Deck deck) {
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
	}
	
	/**
	 * Creates a new board with the specified cards.
	 * @param cards
	 * 		The cards to supply the board.
	 */
	protected Board(Card... cards) {
		if (cards.length > this.cards.length) {
			throw new IllegalArgumentException("A board can only represent " + Constants.BOARD_SIZE + " cards");
		}
		System.arraycopy(cards, 0, this.cards, 0, cards.length);
	}
	
	/**
	 * Compares two hands on this board with the result representing who wins.
	 * @param a
	 * 		The first hand to compare.
	 * @param b
	 *		The second hand to compare.
	 * @return Will return 1 if hand "a" wins and -1 if "b" wins, if it is a tie
	 *         then 0 will be returned.
	 */
	public int compare(Hand a, Hand b) {
		int aVal = eval.rank(a, this), bVal = eval.rank(b, this);
		if (aVal > bVal) {
			return 1;
		} else if (aVal < bVal) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Returns a Collection containing the cards on this board at a given street.
	 * @param street
	 * 		The street.
	 * @return
	 * 		A Collection containing the cards on this board at the street specified.
	 */
	public Collection<Card> cards(Street street) {
		Card[] cards = new Card[street.cardCount()];
		System.arraycopy(this.cards, 0, cards, 0, street.cardCount());
		return Arrays.asList(cards);
	}

	/**
	 * Returns all cards on this board.
	 * @return
	 * 		All cards on this board.
	 */
	public Collection<Card> cards() {
		return Collections.unmodifiableCollection(Arrays.asList(cards));
	}
	
	@Override
	public String toString() {
		StringBuilder bldr = new StringBuilder();
		for (Card card : cards) {
			bldr.append(card.toString());
		}
		return bldr.toString();
	}
	
}
