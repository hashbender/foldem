package codes.derive.foldem.example;

import static codes.derive.foldem.Foldem.*;

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.eval.Evaluator;

/**
 * An example that shows how evaluation works.
 */
public class EvaluatorsExample {

	public static void main(String... args) {
		
		// use the default evaluator
		Evaluator eval = evaluator();
		
		// find the rank of a 5 high straight flush
		Board board = board("8s4d3d2d9s");
		Hand hand = hand("Ad5d");
		System.out.println(eval.value(hand, board));
		System.out.println(eval.rank(hand, board));
	
		/*
		 * Should output:
		 * 	STRAIGHT_FLUSH
		 *	9
		 */

	}
	
}
