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

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;

public class TestDefaultEvaluator {
	
	@Test
	public void testEvaluation() {
	
		// create a board for our test cases
		Board board = Boards.board(card("As"), card("2d"), card("7h"));
				
		// create a map containing our expected rankings on the board
		Map<Hand, Integer> testCases = new HashMap<>();
		testCases.put(hand("AcQs"), 3414);
		testCases.put(hand("2dQh"), 5979);
		testCases.put(hand("9c7s"), 4904);
		testCases.put(hand("4h2s"), 6012);
		testCases.put(hand("9c4d"), 6632);
		testCases.put(hand("AcAh"), 1664);
		
		// evaluate our test cases and compare with actual output
		Evaluator eval = new DefaultEvaluator();
		for (Hand hand : testCases.keySet()) {
			assertEquals((int) testCases.get(hand), eval.rank(hand, board));
		}

	}

	@Test
	public void testValueRanking() {
		Evaluator eval = new DefaultEvaluator();		
		assertEquals(HandValue.HIGH_CARD, eval.value(hand("AcTs"), board("2hQs4d")));
		assertEquals(HandValue.PAIR, eval.value(hand("AcTs"), board("ThQs4d")));
		assertEquals(HandValue.TWO_PAIR, eval.value(hand("AcTs"), board("ThAs4d")));
		assertEquals(HandValue.THREE_OF_A_KIND, eval.value(hand("AcTs"), board("ThTd4d")));
		assertEquals(HandValue.STRAIGHT, eval.value(hand("AcTs"), board("JdQsKd")));
		assertEquals(HandValue.FLUSH, eval.value(hand("AcTc"), board("2cQc4c")));
		assertEquals(HandValue.FULL_HOUSE, eval.value(hand("AcTs"), board("ThTdAh")));
		assertEquals(HandValue.FOUR_OF_A_KIND, eval.value(hand("AcTs"), board("AsAhAd")));
		assertEquals(HandValue.STRAIGHT_FLUSH, eval.value(hand("AcTc"), board("JcQcKc")));
	}
	
}
