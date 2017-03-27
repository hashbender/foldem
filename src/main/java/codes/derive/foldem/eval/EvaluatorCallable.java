/*
 * This file is part of Fold'em, a Java library for Texas Hold 'em Poker.
 *
 * Fold'em is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fold'em is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Fold'em.  If not, see <http://www.gnu.org/licenses/>.
 */
package codes.derive.foldem.eval;

import java.util.concurrent.Callable;

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;

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
