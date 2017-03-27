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
package codes.derive.foldem.example;

import static codes.derive.foldem.Poker.*;

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
