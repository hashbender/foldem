package codes.derive.foldem.tool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.board.Street;
import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.eval.Evaluator;
import codes.derive.foldem.eval.HandValue;
import codes.derive.foldem.range.Range;

/**
 * A type that can analyze a board texture and provide an estimation of what
 * types of hands will occur and what their frequencies will be on a given board
 * for a {@link Range}.
 */
public class TextureAnalysisBuilder {

	/* The default sample size to use. */
	private static final int DEFAULT_SAMPLE_SIZE = 25000;

	/* The default evaluator to use. */
	private static final Evaluator DEFAULT_EVALUATOR = new DefaultEvaluator();

	/* The board to use. */
	private Board board = Boards.board();

	/* The sample size to use. */
	private int sampleSize = DEFAULT_SAMPLE_SIZE;

	/* The evaluator to use. */
	private Evaluator evaluator = DEFAULT_EVALUATOR;

	/**
	 * Finds the frequencies of different hand values on the currently set
	 * {@link Board} using hands in the specified {@link Range}.
	 * 
	 * @param range
	 *            The range.
	 * @return Hand values mapped to their respective frequencies.
	 */
	public Map<HandValue, Double> frequencies(Range range) {

		/*
		 * Make sure our board is usable.
		 */
		if (board.getStreet().equals(Street.PREFLOP)) {
			throw new IllegalStateException("Board is not set to a postflop board");
		}

		/*
		 * Make sure we have at least one hand in our range that is disjoint
		 * from the pre-set board.
		 */
		boolean usable = false;
		for (Hand hand : range.all()) {
			if (Collections.disjoint(hand.cards(), board.cards())) {
				usable = true;
				break;
			}
		}
		if (!usable) {
			throw new IllegalArgumentException("No viable hands in range to use on the board");
		}

		/*
		 * Initialize our results map.
		 */
		Map<HandValue, Double> results = new HashMap<>();
		for (HandValue value : HandValue.values()) {
			results.put(value, 0.0);
		}

		/*
		 * Create a Random context for deck shuffling that uses a hash of our
		 * input for seeding. This allows for output continuity between
		 * calculations.
		 */
		Random random = new Random(range.hashCode());

		/*
		 * Begin running simulations.
		 */
		for (int i = 0; i < sampleSize; i++) {

			/*
			 * Generate a hand from a sample in our range.
			 */
			Hand hand = range.sample(random);
			
			/*
			 * If our hand collides with the board, try again.
			 */
			if (!Collections.disjoint(hand.cards(), board.cards())) {
				i -= 1;
				continue;
			}

			/*
			 * Find the value of our hand and add it to our results.
			 */
			HandValue value = evaluator.value(hand, board);
			results.put(value, results.get(value) + 1.0);
		}

		/*
		 * Divide our results by the sample size to get their frequency.
		 */
		for (HandValue value : results.keySet()) {
			results.put(value, results.get(value) / sampleSize);
		}

		return results;
	}

	/**
	 * Sets the number of boards to simulate for analysis. By default this value
	 * is specified by {@link DEFAULT_SAMPLE_SIZE }.
	 * 
	 * @param sampleSize
	 *            The number of boards to simulate for analysis.
	 * @return The {@link EquityCalculationBuilder} instance, for chaining.
	 */
	public TextureAnalysisBuilder useSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
		return this;
	}

	/**
	 * Sets the evaluator to be used to evaluate hand values during simulations.
	 * By default this value is specified by <code>DEFAULT_EVALUATOR</code>.
	 * 
	 * @param evaluator
	 *            The evaluator to be used to evaluate hands during simulations.
	 * @return The {@link EquityCalculationBuilder} instance, for chaining.
	 */
	public TextureAnalysisBuilder useEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
		return this;
	}

	/**
	 * Makes the analysis use the specified board.
	 * 
	 * @param board
	 *            The board to use during analysis.
	 * @return The {@link EquityCalculationBuilder} instance, for chaining.
	 */
	public TextureAnalysisBuilder useBoard(Board board) {
		this.board = board;
		return this;
	}

}
