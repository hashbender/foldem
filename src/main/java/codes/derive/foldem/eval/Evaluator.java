package codes.derive.foldem.eval;

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;

/**
 * A type that performs evaluations by returning a rank for a specified
 * hand/board.
 */
public interface Evaluator {

	/**
	 * Ranks the specified hand on the specified board.
	 * 
	 * @param h
	 *            The hand to rank.
	 * @param b
	 *            The board to rank it on.
	 * @return TODO
	 */
	public int rank(Hand h, Board b);

	/**
	 * Obtains the value of the specified {@link Hand} on the specified
	 * {@link Board}.
	 * 
	 * @param hand
	 *            The hand to find the value of.
	 * @param board
	 *            The board to find the value of it on.
	 * @return The value of the specified {@link Hand} on the specified
	 *         {@link Board}.
	 */
	public HandValue value(Hand hand, Board board);

}
