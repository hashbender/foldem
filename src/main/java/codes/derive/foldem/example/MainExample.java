package codes.derive.foldem.example;

import static codes.derive.foldem.Poker.*;

import java.util.Collection;
import java.util.Map;

import codes.derive.foldem.Card;
import codes.derive.foldem.Hand;
import codes.derive.foldem.Range;
import codes.derive.foldem.Suit;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.eval.HandValue;
import codes.derive.foldem.tool.EquityCalculationBuilder.Equity;
import codes.derive.foldem.tool.TextureAnalysisBuilder;

/**
 * The main example which is shown on the README on Github.
 */
public class MainExample {

	public static void main(String... args) {

		/* Create a card. */
		Card aceOfHearts = card(Card.ACE, Suit.HEARTS);

		/* Create another card, this time using shorthand. */
		Card aceOfSpades = card("As");

		/* Create a hand using the cards. */
		Hand aces = hand(aceOfHearts, aceOfSpades);

		/* Create another hand, this time using shorthand. */
		Hand kings = hand("KhKs");

		/* Calculate these hands' equity against each other. */
		Map<Hand, Equity> equities = equity(aces, kings);

		/* Print their equity against each other. */
		System.out.println(aces + ": " + format(equities.get(aces)));
		System.out.println(kings + ": " + format(equities.get(kings)));

		/*
		 * Output:
		 * AhAs: Win: 82.5% Lose: 16.95% Split: 0.55%
		 * KhKs: Win: 16.95% Lose: 82.5% Split: 0.55%
		 */

		/* Create a board. */
		Board board = board("Kc7d2h");

		/* Calculate equities again, this time on the board. */
		equities = calculationBuilder().useBoard(board).calculate(aces, kings);

		/* 
		 * Print their equity against each other, this time throwing in pretty
		 * formatting for style points.
		 */
		System.out.println("-- On board " + format(board));
		System.out.println(format(aces) + ": " + format(equities.get(aces)));
		System.out.println(format(kings) + ": " + format(equities.get(kings)));

		/*
		 * Output:
		 * 	-- On board K♣, 7♦, 2❤
		 * 	A❤,A♠: Win: 8.49% Lose: 91.51% Split: 0.0%
		 * 	K❤,K♠: Win: 91.51% Lose: 8.49% Split: 0.0%
		 */

		/* Create a hand group containing all combinations of aces. */
		Collection<Hand> allAces = handGroup("AA");

		/* Create a range with aces, and 72 off-suit. */
		Range a = range().define(allAces).define(handGroup("72o"));

		/* Create a range with kings, and queens with a 70% weight. */
		Range b = range().define(handGroup("KK")).define(0.7, handGroup("QQ"));

		/* Calculate their equity against each other. */
		Map<Range, Equity> rangeEquities = equity(a, b);

		/* Print their equities. */
		System.out.println("Range A: " + format(rangeEquities.get(a)));
		System.out.println("Range B: " + format(rangeEquities.get(b)));

		/*
		 * Output:
		 * Range A: Win: 34.86% Lose: 64.71% Split: 0.43%
		 * Range B: Win: 64.71% Lose: 34.86% Split: 0.43%
		 */

		/*
		 * Lets find out what kind of hands aces and 72 off-suit are going to
		 * make on a flop of 7h7dAc, and how often.
		 */

		/*
		 * First create a codes.derive.foldem.tool.TextureAnalysisBuilder
		 * context.
		 */
		TextureAnalysisBuilder bldr = new TextureAnalysisBuilder();

		/* Set it to use the board 7h7dAc. */
		bldr.useBoard(board("7h7dAc"));

		/*
		 * Create a calculation containing hand values mapped to their
		 * frequencies for range "a", from earlier in the examples.
		 */
		Map<HandValue, Double> frequencies = bldr.frequencies(a);

		/* Print our frequency information. */
		for (HandValue value : frequencies.keySet()) {
			double frequency = frequencies.get(value);
			System.out.println(value + ": " + percent(frequency) + "%");
		}

		/*
		 * Output:
		 * 	FLUSH: 0.0%
		 * 	STRAIGHT_FLUSH: 0.0%
		 * 	FOUR_OF_A_KIND: 0.0%
		 * 	PAIR: 0.0%
		 * 	THREE_OF_A_KIND: 66.41%
		 * 	FULL_HOUSE: 33.59%
		 * 	HIGH_CARD: 0.0%
		 * 	TWO_PAIR: 0.0%
		 * 	NONE: 0.0%
		 * 	STRAIGHT: 0.0%
		 */
		
	}

}
