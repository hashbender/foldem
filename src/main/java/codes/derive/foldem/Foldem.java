package codes.derive.foldem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import codes.derive.foldem.EquityCalculator.Equity;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.range.Range;
import codes.derive.foldem.util.PrettyCards;
import codes.derive.foldem.util.RandomContext;

/**
 * This class consists exclusively of static methods to assist in using this
 * library.
 */
public class Foldem {

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
	 * Creates a collection containing an unordered enumeration of all cards.
	 * 
	 * @return A collection containing every card.
	 */
	public static Collection<Card> cards() {
		List<Card> cards = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (int value = Card.ACE; value <= Card.KING; value++) {
				cards.add(new Card(value, suit));
			}
		}
		return cards;
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
	 * {@link codes.derive.foldem.Hand#Hand(String)}.
	 * 
	 * @param cards
	 *            The shorthand text.
	 * @return A new hand using the specified shorthand text.
	 */
	public static Hand hand(String cards) {
		return new Hand(cards);
	}

	/**
	 * Constructs a new empty range.
	 * 
	 * @return A new empty range.
	 */
	public static Range range() {
		return new Range();
	}
	
	/**
	 * Constructs a new range with the specified hands.
	 * 
	 * @param hands
	 *            The hands.
	 * @return The new range containing the specified hands.
	 */
	public static Range range(Hand... hands) {
		return new Range().define(hands);

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
		return calculator().calculate(hands);
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
		return calculator().calculate(board, hands);
	}

	/**
	 * Obtains the equity that the specified hand groups have against each
	 * other, returning them as keys mapped to their calculated equity.
	 * TODO rename to range
	 * @param hands
	 *            The hand groups to calculate equity for.
	 * @return The hand groups mapped to their calculated equity.
	 */
	public static Map<Range, Equity> equity(Range... groups) {
		return calculator().calculate(groups);
	}

	/**
	 * Obtains the equity that the specified hand groups have against each other
	 * on the specified board, returning them as keys mapped to their calculated
	 * equities.
	 * TODO rename to range
	 * @param board
	 *            The board to calculate equity on.
	 * @param groups
	 *            The hand groups to calculate equity for
	 * @return The hand groups mapped to their calculated equity.
	 */
	public static Map<Range, Equity> equity(Board board,
			Range... groups) {
		return calculator().calculate(board, groups);
	}

	/**
	 * Formats the suit specified using pretty formatting. Is an alias for
	 * {@link codes.derive.foldem.util.PrettyCards#get(Suit)}
	 * 
	 * @param suit
	 *            The suit to format.
	 * @return A pretty formatted string representing the specified suit.
	 */
	public static char format(Suit suit) {
		return PrettyCards.get(suit);
	}

	/**
	 * Formats the card specified using pretty formatting. Is an alias for
	 * {@link codes.derive.foldem.util.PrettyCards#get(Card)}
	 * 
	 * @param card
	 *            The card to format.
	 * @return A pretty formatted string representing the specified card.
	 */
	public static String format(Card card) {
		return PrettyCards.get(card);
	}

	/**
	 * Formats the hand specified using pretty formatting. Is an alias for
	 * {@link codes.derive.foldem.util.PrettyCards#get(Hand)}
	 * 
	 * @param hand
	 *            The hand to format.
	 * @return A pretty formatted string representing the specified hand.
	 */
	public static String format(Hand hand) {
		return PrettyCards.get(hand);
	}

	/**
	 * Formats the board specified using pretty formatting. Is an alias for
	 * {@link codes.derive.foldem.util.PrettyCards#get(Board)}
	 * 
	 * @param board
	 *            The board to format.
	 * @return A pretty formatted string representing the specified board.
	 */
	public static String format(Board board) {
		return PrettyCards.get(board);
	}

	/**
	 * Formats the equity specified using percentages.
	 * 
	 * @param equity
	 *            The equity to format.
	 * @return A string containing the equities in a format of
	 *         "Win: ww.ww% Lose: ll.ll% Split: ss.ss%".
	 */
	public static String format(Equity equity) {
		StringBuilder b = new StringBuilder();
		b.append("Win: ").append(percent(equity.win())).append("% ");
		b.append("Lose: ").append(percent(equity.lose() * 100)).append("% ");
		b.append("Split: ").append(percent(equity.split() * 100)).append("%");
		return b.toString();
	}

	/**
	 * Represents the specified decimal as a percentage rounded to two decimal
	 * places.
	 * 
	 * <p>
	 * This may seem like a weird place to have this but it can be useful for
	 * quickly generating percentages from equities.
	 * </p>
	 * 
	 * @param d
	 *            The decimal to convert.
	 * @return The percentage.
	 */
	public static double percent(double d) {
		return new BigDecimal(d * 100).setScale(2, RoundingMode.HALF_UP)
				.doubleValue();
	}

	/**
	 * Creates a new equity calculator.
	 * 
	 * @return A new equity calculator.
	 */
	public static EquityCalculator calculator() {
		return new EquityCalculator();
	}

}
