package codes.derive.foldem.eval;

import codes.derive.foldem.Board;
import codes.derive.foldem.hand.Hand;

/**
 * A type that performs evaluations by returning a rank for a specified
 * hand/board.
 */
public interface Evaluator {

	/**
	 * Ranks the specified hand on the specified board.
	 * @param h
	 * 		The hand to rank.
	 * @param b
	 * 		The board to rank it on.
	 * @return
	 * 		TODO
	 */
	public int rank(Hand h, Board b);
	
}
