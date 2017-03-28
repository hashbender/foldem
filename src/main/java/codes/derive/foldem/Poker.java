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
package codes.derive.foldem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.board.Street;
import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.eval.Evaluator;
import codes.derive.foldem.eval.HandValue;
import codes.derive.foldem.tool.EquityCalculationBuilder;
import codes.derive.foldem.tool.EquityCalculationBuilder.Equity;
import codes.derive.foldem.util.PrettyFormat;
import codes.derive.foldem.util.RandomContext;

/**
 * This class consists of static methods, a lot of them just aliases, to aid in
 * using this library.
 */
public class Poker {

	private Poker() { /* No external instantiation */ }

	/**
	 * Constructs a {@link Card} with the specified card value and suit.
	 * 
	 * @param value
	 *            The card value, must be one of the card value constants
	 *            defined in {@link Card}.
	 * @param suit
	 *            The suit.
	 * @return A new {@link Card} with the specified value and suit.
	 */
	public static Card card(int value, Suit suit) {
		return new Card(value, suit);
	}

	/**
	 * Constructs a new {@link Card} using the specified shorthand string. For
	 * information on the shorthand format see
	 * {@link codes.derive.foldem.Card#Card(String)}.
	 * 
	 * @param text
	 *            The shorthand for the card.
	 * @return A new {@link Card} using the specified shorthand.
	 * @see codes.derive.foldem.Card#Card(String)
	 */
	public static Card card(String text) {
		return new Card(text);
	}

	/**
	 * Constructs a new {@link Card} dealt from the specified {@link Deck}.
	 * 
	 * <p>
	 * Alias for {@link Deck.pop()}.
	 * </p>
	 * 
	 * @param deck
	 *            The deck to deal from
	 * @return The {@link Card} dealt from the specified deck.
	 */
	public static Card card(Deck deck) {
		return deck.pop();
	}

	/**
	 * Constructs a new {@link java.util.Collection} containing an unordered
	 * enumeration of all cards.
	 * 
	 * @return A {@link java.util.Collection} containing every {@link Card}, in
	 *         no specific order.
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
	 * Creates a {@link java.util.Collection} containing cards for the specified
	 * sequential shorthand. for example, "Ac2d3h4s", would return a
	 * {@link java.util.Collection} containing the ace of spades, deuce of
	 * diamonds, trey of hearts, and the four of spaces.
	 * 
	 * @param shorthand
	 *            The shorthand.
	 * @return A {@link java.util.Collection} containing the created cards.
	 */
	public static Collection<Card> cards(String shorthand) {
		if (shorthand.length() % 2 != 0) {
			throw new IllegalArgumentException("Invalid shorthand");
		}
		List<Card> cards = new ArrayList<>();
		for (int i = 0; i < shorthand.length(); i += 2) {
			cards.add(card(shorthand.substring(i, i + 2)));
		}
		return cards;
	}

	/**
	 * Constructs a new {@link Hand} using the specified cards.
	 * 
	 * @param cards
	 *            The cards to use in the created hand.
	 * @return A new hand using the specified cards.
	 */
	public static Hand hand(Card... cards) {
		return new Hand(cards);
	}

	/**
	 * Constructs a new {@link Hand} by dealing it from the specified
	 * {@link Deck}.
	 * 
	 * @param deck
	 *            The deck to deal the hand from.
	 * @return A {@link Hand} containing cards dealt from the specified deck.
	 */
	public static Hand hand(Deck deck) {
		return hand(deck.pop(), deck.pop());
	}

	/**
	 * Constructs a new {@link Hand} using specified cards shorthand text. For
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
	 * Constructs a new {@link java.util.Collection} containing an unordered
	 * enumeration of all hands.
	 * 
	 * @return A {@link java.util.Collection} containing every possible
	 *         {@link Hand}, in no specific order.
	 * 
	 */
	public static Collection<Hand> hands() {
		List<Hand> hands = new ArrayList<>();
		for (Card a : cards()) {
			for (Card b : cards()) {
				if (a.equals(b)) {
					continue;
				}
				hands.add(hand(a, b));
			}
		}
		return hands;
	}
	
	/**
	 * Constructs a new {@link java.util.Collection} containing a group of hands
	 * specified by shorthand with no suit information.
	 * 
	 * <p>
	 * The syntax is the same as creating hands, except you do not need to
	 * specify suit information, a hand with each combination of suits will be
	 * created for you. For example, shorthand "TT" would produce every
	 * combination of a hand containing two tens in
	 * </p>
	 * 
	 * <p>
	 * Additionally, you can specify hands be suited using the "s" modifier.
	 * This will produce only suited combinations of the specified hand. For
	 * example, "TJs" would produce TJ of hearts, spaces, clubs and diamonds
	 * only. Conversely "TJo" would produce only off-suited hands. TODO
	 * </p>
	 * 
	 * @param shorthand
	 *            The shorthand to use to generate the hands.
	 * @return A new {@link java.util.Collection} containing the hands specified
	 *         in shorthand format.
	 */
	public static Collection<Hand> handGroup(String shorthand) {
		List<Hand> hands = new ArrayList<>();

		/*
		 * Find the numeric values of the card labels provided.
		 */
		int a = -1, b = -1;
		for (int i = 0; i < Card.LABEL.length; i++) {
			if (Card.LABEL[i] == shorthand.charAt(0)) {
				a = i;
			}
			if (Card.LABEL[i] == shorthand.charAt(1)) {
				b = i;
			}
		}

		/*
		 * If our hand is suited.
		 */
		if (shorthand.length() == 3 && shorthand.charAt(2) == 's') {
			if (a == b) {
				throw new IllegalArgumentException(
						"A hand cannot have identical cards of the same suit");
			}
			for (Suit suit : Suit.values()) {
				hands.add(hand(card(a, suit), card(b, suit)));
			}
		} else {

			/*
			 * Add all off-suit combinations of the provided hand.
			 */
			for (Suit[] suits : Constants.OFFSUIT_COMBINATIONS) {
				hands.add(hand(card(a, suits[0]), card(b, suits[1])));

				/*
				 * We only need to reverse the suits if A and B aren't
				 * equivalent.
				 */
				if (a != b) {
					hands.add(hand(card(a, suits[1]), card(b, suits[0])));
				}
			}
		}
		return hands;
	}

	/**
	 * Constructs a new {@link Range} with no hands.
	 * 
	 * @return A new empty {@link Range}.
	 */
	public static Range range() {
		return new Range();
	}

	/**
	 * Constructs a new {@link Range} with the specified hands.
	 * 
	 * @param hands
	 *            The hands.
	 * @return The new {@link Range} containing the specified hands.
	 */
	public static Range range(Hand... hands) {
		return range().define(hands);

	}

	/**
	 * Constructs a new {@link Board} using the specified cards.
	 * 
	 * <p>
	 * Alias for {@link Boards#board(Card...)}.
	 * </p>
	 * 
	 * @param cards
	 *            The cards to use.
	 * @return A new {@link Board} using the specified cards.
	 */
	public static Board board(Card... cards) {
		return Boards.board(cards);
	}

	/**
	 * Constructs a new {@link Board} using the specified card shorthand.
	 * 
	 * <p>
	 * Alias for {@link Boards#board(String)}.
	 * </p>
	 * 
	 * @param cards
	 *            The cards shorthand, see {@link Boards#board(String)} for
	 *            information on formatting.
	 * @return A new {@link Board} using the specified cards.
	 */
	public static Board board(String cards) {
		return Boards.board(cards);
	}

	/**
	 * Constructs a new {@link Board}, dealing the cards from the specified
	 * {@link Deck}.
	 * 
	 * <p>
	 * Alias for {@link Boards#board(Deck, Street)}
	 * </p>
	 * 
	 * @param deck
	 *            The deck to deal from.
	 * @param street
	 *            The street to deal.
	 * @return A new {@link Board} using cards from the specified {@link Deck}.
	 */
	public static Board board(Deck deck, Street street) {
		return Boards.board(deck, street);
	}

	/**
	 * Constructs a new {@link Deck}.
	 * 
	 * @return A new {@link Deck} with no cards drawn.
	 */
	public static Deck deck() {
		return new Deck();
	}

	/**
	 * Constructs a new {@link Deck} and shuffles it.
	 * 
	 * @return A new shuffled {@link Deck} with no cards drawn.
	 */
	public static Deck shuffledDeck() {
		return new Deck().shuffle(RandomContext.get());
	}

	/**
	 * Finds the value of the specified {@link Hand} on the specified
	 * {@link Board}.
	 * 
	 * @param hand
	 *            The hand.
	 * @param board
	 *            The board.
	 * @return The specified hand's value on the specified board.
	 */
	public static HandValue value(Hand hand, Board board) {
		return new DefaultEvaluator().value(hand, board);
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
		return calculationBuilder().calculate(hands);
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
		return calculationBuilder().useBoard(board).calculate(hands);
	}

	/**
	 * Obtains the equity that the specified hand ranges have against each
	 * other, returning them as keys mapped to their calculated equity.
	 * 
	 * @param ranges
	 *            The hand ranges to calculate equity for.
	 * @return The hand ranges mapped to their calculated equity.
	 */
	public static Map<Range, Equity> equity(Range... ranges) {
		return calculationBuilder().calculate(ranges);
	}

	/**
	 * Obtains the equity that the specified hand ranges have against each other
	 * on the specified board, returning them as keys mapped to their calculated
	 * equity.
	 * 
	 * @param board
	 *            The board to calculate equity on.
	 * @param ranges
	 *            The hand ranges to calculate equity for
	 * @return The hand ranges mapped to their calculated equity.
	 */
	public static Map<Range, Equity> equity(Board board, Range... ranges) {
		return calculationBuilder().useBoard(board).calculate(ranges);
	}

	/**
	 * Formats the {@link Suit} specified using pretty formatting. Is an alias
	 * for {@link codes.derive.foldem.util.PrettyFormat#get(Suit)}
	 * 
	 * @param suit
	 *            The {@link Suit} to format.
	 * @return A pretty formatted {@link String} representing the specified {@link Suit}
	 *         .
	 */
	public static char format(Suit suit) {
		return PrettyFormat.get(suit);
	}

	/**
	 * Formats the {@link Card} specified using pretty formatting. Is an alias
	 * for {@link codes.derive.foldem.util.PrettyFormat#get(Card)}
	 * 
	 * @param card
	 *            The {@link Card} to format.
	 * @return A pretty formatted {@link String} representing the specified {@link Card}
	 *         .
	 */
	public static String format(Card card) {
		return PrettyFormat.get(card);
	}

	/**
	 * Formats the {@link Hand} specified using pretty formatting. Is an alias for
	 * {@link codes.derive.foldem.util.PrettyFormat#get(Hand)}
	 * 
	 * @param hand
	 *            The hand to format.
	 * @return A pretty formatted {@link String} representing the specified {@link Hand}.
	 */
	public static String format(Hand hand) {
		return PrettyFormat.get(hand);
	}

	/**
	 * Formats the {@link Board} specified using pretty formatting. Is an alias
	 * for {@link codes.derive.foldem.util.PrettyFormat#get(Board)}
	 * 
	 * @param board
	 *            The {@link Board} to format.
	 * @return A pretty formatted string representing the specified {@link Board}.
	 */
	public static String format(Board board) {
		return PrettyFormat.get(board);
	}

	/**
	 * Formats the {@link Equity} specified using percentages.
	 * 
	 * @param equity
	 *            The equity to format.
	 * @return A string containing the w/l/s in a format of
	 *         "Win: ww.ww% Lose: ll.ll% Split: ss.ss%".
	 */
	public static String format(Equity equity) {
		StringBuilder b = new StringBuilder();
		b.append("Win: ").append(percent(equity.win())).append("% ");
		b.append("Lose: ").append(percent(equity.lose())).append("% ");
		b.append("Split: ").append(percent(equity.split())).append("%");
		return b.toString();
	}

	/**
	 * Represents the specified decimal as a percentage rounded to two decimal
	 * places.
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
	 * Constructs a new evaluator using the {@link DefaultEvaluator} type
	 * provided with this library.
	 * 
	 * <p>
	 * Alias for {@link DefaultEvaluator#DefaultEvaluator}
	 * </p>
	 * 
	 * @return An evaluator.
	 */
	public static Evaluator evaluator() {
		return new DefaultEvaluator();
	}

	/**
	 * Constructs a new {@link EquityCalculationBuilder}.
	 * 
	 * @return A new {@link EquityCalculationBuilder} for use in equity
	 *         calculations.
	 */
	public static EquityCalculationBuilder calculationBuilder() {
		return new EquityCalculationBuilder();
	}

}
