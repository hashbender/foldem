package codes.derive.foldem.equity;

import static codes.derive.foldem.Foldem.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import codes.derive.foldem.Board;
import codes.derive.foldem.Deck;
import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.eval.Evaluator;
import codes.derive.foldem.hand.Hand;

public class EquityCalculator {

	private static final int SAMPLE_SIZE = 10000;
	
	private static final Evaluator EVALUATOR = new DefaultEvaluator();
	
	// TODO document
	// TODO add functionality into Foldem class
	// TODO rename class
	// TODO refactor
	// TODO could move into main package
	
	public Map<Hand, Equity> calculate(Hand... hands) {
		Map<Hand, Equity> equities = new HashMap<>();
		for (Hand hand : hands) {
			equities.put(hand, new Equity());
		}
		long seed = 31L;
		for (Hand hand : hands) {
			seed *= hand.hashCode();
		}
		Random random = new Random(seed);
		for (int i = 0; i < SAMPLE_SIZE; i++) {
			Deck deck = deck().shuffle(random);
			for (Hand hand : hands) {
				deck.pop(hand);
			}
			Board board = board(deck);
			List<Hand> best = new LinkedList<>();
			int rank = Integer.MAX_VALUE;
			for (Hand hand : hands) {
				int r = EVALUATOR.rank(hand, board);
				if (r < rank) {
					best.clear();
					best.add(hand);
					rank = r;
				} else if (r == rank) {
					best.add(hand);
				}
			}
			for (Hand hand : hands) {
				if (!best.contains(hand)) {
					equities.get(hand).applySample(0.0, (1.0 / SAMPLE_SIZE), 0.0);
				}
			}
			if (best.size() > 1) {
				for (Hand hand : best) {
					equities.get(hand).applySample(0.0, 0.0, (1.0 / SAMPLE_SIZE));
				}
			} else {
				equities.get(best.get(0)).applySample((1.0 / SAMPLE_SIZE), 0.0, 0.0);
			}
		}
		return Collections.unmodifiableMap(equities);
	}
	
	
}
