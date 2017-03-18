package codes.derive.foldem.eval;

import codes.derive.foldem.Board;
import codes.derive.foldem.hand.Hand;

public interface Evaluator {

	public int rank(Hand h, Board b);
	
}
