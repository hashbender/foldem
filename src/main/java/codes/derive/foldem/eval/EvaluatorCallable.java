package codes.derive.foldem.eval;

import java.util.concurrent.Callable;

import codes.derive.foldem.board.Board;
import codes.derive.foldem.hand.Hand;

/**
 * An implementation of {@link Callable} that can be used to evaluate a hand.
 */
public class EvaluatorCallable implements Callable<Integer> {

	/* The evaluator to use. */
	private final Evaluator evaluator;
	
	/* The hand to evaluate. */
	private final Hand hand;
	
	/* The board to evaluate the hand on. */
	private final Board board;
	
	/**
	 * Constructs a new evaluator callable.
	 * @param evaluator
	 * 		The evaluator to use.
	 * @param hand
	 * 		The hand to evaluate.
	 * @param board
	 * 		The board to evaluate the hand on.
	 */
	public EvaluatorCallable(Evaluator evaluator, Hand hand, Board board) {
		this.evaluator = evaluator;
		this.hand = hand;
		this.board = board;
	}
	
	@Override
	public Integer call() throws Exception {
		return evaluator.rank(hand, board);
	}
	
}
