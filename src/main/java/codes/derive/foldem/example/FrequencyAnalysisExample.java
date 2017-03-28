package codes.derive.foldem.example;

import static codes.derive.foldem.Poker.*;

import java.util.Map;

import codes.derive.foldem.Range;
import codes.derive.foldem.eval.HandValue;
import codes.derive.foldem.tool.TextureAnalysisBuilder;

public class FrequencyAnalysisExample {

	public static void main(String... args) {
		
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
		
		/* Create a range with aces, and 72 off-suit. */
		Range a = range().define(handGroup("AA")).define(handGroup("72o"));
		
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
