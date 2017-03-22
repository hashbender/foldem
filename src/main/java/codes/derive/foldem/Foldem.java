package codes.derive.foldem;

import java.util.Map;

import codes.derive.foldem.EquityCalculator.Equity;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.hand.BasicHandGroup;
import codes.derive.foldem.hand.Hand;
import codes.derive.foldem.hand.HandGroup;
import codes.derive.foldem.hand.HandRange;
import codes.derive.foldem.util.RandomContext;

/**
 * This class consists exclusively of static methods to assist in using this
 * library.
 */
public class Foldem {

	/* The equity calculator used in this class for lazy calculations. */
	private static final EquityCalculator calc = new EquityCalculator();

	/**
	 * Constructs a card with the specified card value and suit.
	 * 
	 * @param value
	 *            The card value, must be one of the card value constants
	 *            defined in {@link Card}.
	 * @param suit
	 *            The suit.
	 * @return A new card with the specified value and suit.
	 */
	public static Card card(int value, Suit suit) {
		return new Card(value, suit);
	}

	/**
	 * Constructs a new card using the specified shorthand string. For
	 * information on the shorthand format see
	 * {@link codes.derive.foldem.Card#Card(String)}.
	 * 
	 * @param text
	 *            The shorthand for the card.
	 * @return A new card using the specified shorthand.
	 * @see codes.derive.foldem.Card#Card(String)
	 */
	public static Card card(String text) {
		return new Card(text);
	}

	/**
	 * Constructs a new hand using the specified cards.
	 * 
	 * @param cards
	 *            The cards to use in the created hand.
	 * @return A new hand using the specified cards.
	 */
	public static Hand hand(Card... cards) {
		return new Hand(cards);
	}

	/**
	 * Constructs a new hand using specified cards shorthand text. For
	 * information on the format see
	 * {@link codes.derive.foldem.hand.Hand#Hand(String)}.
	 * 
	 * @param cards
	 *            The shorthand text.
	 * @return A new hand using the specified shorthand text.
	 */
	public static Hand hand(String cards) {
		return new Hand(cards);
	}

	/**
	 * Constructs a new basic hand group that will contain the specified hands.
	 * 
	 * @param hands
	 *            The hands to be contained by the group.
	 * @return A new hand group containing the specified hands.
	 */
	public static HandGroup basicGroup(Hand... hands) {
		return new BasicHandGroup(hands);
	}

	/**
	 * Constructs a new basic hand group using the specified hand shorthand
	 * text. For information on the shorthand format see
	 * {@link codes.derive.foldem.hand.Hand#Hand(String)}.
	 * 
	 * @param hands
	 *            The shorthand strings representing the cards to use.
	 * @return A new hand group for the specified hand strings.
	 * @see codes.derive.foldem.Card#Card(String)
	 */
	public static HandGroup basicGroup(String... hands) {
		Hand[] h = new Hand[hands.length];
		for (int i = 0; i < h.length; i++) {
			h[i] = hand(hands[i]);
		}
		return new BasicHandGroup(h);
	}

	/**
	 * Constructs a new empty range.
	 * 
	 * @return A new empty range.
	 */
	public static HandRange rangeGroup() {
		return new HandRange();
	}

	/**
	 * Constructs a new board using the specified cards.
	 * 
	 * @param cards
	 *            The cards to use.
	 * @return A new board using the specified cards.
	 */
	public static Board board(Card... cards) {
		return Boards.board(cards); // TODO consider removing or moving other functions
	}

	/**
	 * Constructs a new unshuffled deck with no cards drawn.
	 * 
	 * @return A new deck with no cards drawn.
	 */
	public static Deck deck() {
		return new Deck();
	}

	/**
	 * Constructs a new deck and shuffles it, returning it as a result.
	 * 
	 * @return The created shuffled deck.
	 */
	public static Deck shuffledDeck() {
		return new Deck().shuffle(RandomContext.get());
	}

	/**
	 * Obtains the equity that the specified hands have against each other,
	 * returning them as keys mapped to their calculated equity.
	 * 
	 * @param hands
	 *            The hands to calculate equity for.
	 * @return The hands mapped to their calculated equity.
	 */
	public static Map<Hand, Equity> equity(Hand... hands) {
		return calc.calculate(hands);
	}

	/**
	 * Obtains the equity that the specified hands have against each other on
	 * the specified board, returning them as keys mapped to their calculated
	 * equity.
	 * 
	 * @param board
	 *            The board to calculate equity on.
	 * @param hands
	 *            The hands to calculate equity for.
	 * @return The hands mapped to their calculated equity.
	 */
	public static Map<Hand, Equity> equity(Board board, Hand... hands) {
		return calc.calculate(board, hands);
	}

	/**
	 * Obtains the equity that the specified hand groups have against each
	 * other, returning them as keys mapped to their calculated equity.
	 * 
	 * @param hands
	 *            The hand groups to calculate equity for.
	 * @return The hand groups mapped to their calculated equity.
	 */
	public static Map<HandGroup, Equity> equity(HandGroup... groups) {
		return calc.calculate(groups);
	}

	/**
	 * Obtains the equity that the specified hand groups have against each other
	 * on the specified board, returning them as keys mapped to their calculated
	 * equities.
	 * 
	 * @param board
	 *            The board to calculate equity on.
	 * @param groups
	 *            The hand groups to calculate equity for
	 * @return The hand groups mapped to their calculated equity.
	 */
	public static Map<HandGroup, Equity> equity(Board board,
			HandGroup... groups) {
		return calc.calculate(board, groups);
	}

}
