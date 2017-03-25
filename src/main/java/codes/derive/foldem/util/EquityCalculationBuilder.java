package codes.derive.foldem.util;

import static codes.derive.foldem.Foldem.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import codes.derive.foldem.Card;
import codes.derive.foldem.Deck;
import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.board.Street;
import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.eval.Evaluator;
import codes.derive.foldem.range.Range;

/**
 * A type that can be used to calculate equity for hands and groups of hands
 * using Monte Carlo simulations.
 */
public class EquityCalculationBuilder {
	
	// TODO redo all comments here

	/* The default sample size to use for simulations. */
	private static final int DEFAULT_SAMPLE_SIZE = 25000;
	
	/* The default evaluator to use for simulations. */
	private static final Evaluator DEFAULT_EVALUATOR = new DefaultEvaluator();
	
	/* A list containing cards to remove from the deck during calculations. */
	private final List<Card> dead = new ArrayList<>();
	
	/* Base board. */
	private Board board = Boards.board();
	
	/* The sample size to use for simulations. */
	private int sampleSize = DEFAULT_SAMPLE_SIZE;
	
	/* The evaluator to use for simulations */
	private Evaluator evaluator = DEFAULT_EVALUATOR;

	/**
	 * Performs an equity calculation for the specified hands and return map
	 * containing each hand mapped to its calculated equity.
	 * 
	 * @param hands
	 *            The hands to calculate equity for.
	 * @return A map containing the specified hands mapped to their calculated
	 *         equity.
	 */
	
	public Map<Hand, Equity> calculate(Hand... hands) {
		
		// map base equities to respective hands
		Map<Hand, Equity> equities = new HashMap<>();
		for (Hand hand : hands) {
			equities.put(hand, new Equity());
		}
		
		// seed our RNG using the input hands for output continuity
		Random random = new Random(Arrays.hashCode(hands));
		
		// begin simulating boards and sampling results
		for (int i = 0; i < sampleSize; i++) {
			simulate(equities, random);
		}
		
		// 
		for (Equity equity : equities.values()) {
			equity.adjustForSample(); // TODO needed?
		}
		return equities;
	}

	public Map<Range, Equity> calculate(Range... groups) {
		
		// map base equities to respective groups
		Map<Range, Equity> equities = new HashMap<>();
		for (Range group : groups) {
			equities.put(group, new Equity());
		}
		
		// seed our RNG using the input hands for more output continuity
		Random random = new Random(Arrays.hashCode(groups));
		
		// begin simulating boards and sampling results
		for (int i = 0; i < sampleSize; i++) {
			
			// we should take one hand from each of our groups and use that for the simulation
			Map<Hand, Equity> hands = new HashMap<>();
			for (Range group : equities.keySet()) {
				hands.put(group.sample(), equities.get(group));
			}
			
			// run simulation with our hands
			simulate(hands, random);
		}
		
		//
		for (Equity equity : equities.values()) {
			equity.adjustForSample();
		}
		return equities;
	}
	
	/**
	 * Sets the number of boards to simulate for equity calculations. By default
	 * this value is specified by <code>DEFAULT_SAMPLE_SIZE</code>.
	 * 
	 * @param sampleSize
	 *            The number of boards to simulate for equity calculations.
	 * @return The {@link EquityCalculationBuilder} instance, for chaining.
	 */
	public EquityCalculationBuilder useSampleSize(int sampleSize) {
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
	public EquityCalculationBuilder useEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
		return this;
	}

	/**
	 * Makes the calculator remove the specified cards from the deck during
	 * calculations.
	 * 
	 * @param cards
	 *            The cards to be removed from the deck.
	 * @return The {@link EquityCalculationBuilder} instance, for chaining.
	 */
	public EquityCalculationBuilder useDeadCards(Card... cards) {
		for (Card card : cards) {
			dead.add(card);
		}
		return this;
	}
	
	/**
	 * Makes the calculator use the specified board during calculations.
	 * @param board
	 * 		The board to use during calculations.
	 * @return The {@link EquityCalculationBuilder} instance, for chaining.
	 */
	public EquityCalculationBuilder useBoard(Board board) {
		this.board = board;
		return this;
	}
	
	private void simulate(Map<Hand, Equity> equities, Random random) {
		
		// take our hands and board from a randomized deck
		Deck deck = deck().shuffle(random);
		
		// remove our hands
		for (Hand hand : equities.keySet()) {
			deck.pop(hand);
		}
		
		// remove our dead cards
		for (Card card : dead) {
			deck.pop(card);
		}
		
		// board
		Board board = Boards.convert(this.board, Street.RIVER, deck);
		
		// rank our hands in order for the sample
		List<Hand> best = new LinkedList<>();
		int currentBest = Integer.MAX_VALUE;
		for (Hand hand : equities.keySet()) {
			
			// see if this hand is the best one so far
			int r = evaluator.rank(hand, board);
			if (r < currentBest) {
				
				// clear the previous best hands and add this one
				best.clear();
				best.add(hand);
				
				// update our current best ranking
				currentBest = r;
			} else if (r == currentBest) {
				best.add(hand);
			}
		}
		
		// apply losing hands to the sample
		for (Hand hand : equities.keySet()) {
			if (!best.contains(hand)) {
				equities.get(hand).lose += 1;;
			}
		}
		
		// apply winning hands to the sample
		if (best.size() > 1) {
			for (Hand hand : best) {
				equities.get(hand).split += 1;
			}
		} else {
			equities.get(best.get(0)).win += 1;
		}
	}
	
	/**
	 * Represents a hand's equity in a pot. With decimals representing win, loss,
	 * and split pot rates.
	 */
	public class Equity {

		private double win = 0.0, lose = 0.0, split = 0.0;
		
		private Equity() { }

		/**
		 * Obtains how often the hand or hand group associated with this equity
		 * will win, as a decimal.
		 * 
		 * @return How often the hand or hand group associated with this equity
		 *         will win, as a decimal.
		 */
		public double win() {
			return win;
		}

		/**
		 * Obtains how often the hand or hand group associated with this equity
		 * will lose, as a decimal.
		 * 
		 * @return How often the hand or hand group associated with this equity
		 *         will lose, as a decimal.
		 */
		public double lose() {
			return lose;
		}

		/**
		 * Obtains how often the hand or hand group associated with this equity
		 * will split the pot, as a decimal.
		 * 
		 * @return How often the hand or hand group associated with this equity
		 *         will split the pot, as a decimal.
		 */
		public double split() {
			return split;
		}
		
		private void adjustForSample() {
			this.win /= sampleSize;
			this.lose /= sampleSize;
			this.split /= sampleSize;
		}
		
		@Override
		public String toString() {
			return new StringBuilder().append("[win=").append(win).append(" lose=")
					.append(lose).append(" split=").append(split).append("]")
					.toString();
		}

	}
	
}
