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
package codes.derive.foldem.tool;

import static codes.derive.foldem.Poker.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import codes.derive.foldem.Range;
import codes.derive.foldem.eval.HandValue;

public class TextureAnalysisTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void testNoViableHandException() {

		// create an analysis builder
		TextureAnalysisBuilder bldr = new TextureAnalysisBuilder();
		bldr.useBoard(board("QsQc2h"));

		// create a range containing only hands that will be found on the board
		Range range = range(hand("QsQc"), hand("Qs2h"));
		
		// run the analysis
		bldr.frequencies(range);
	}

	@Test
	public void testAnalysis() {
		
		// create an analysis builder
		TextureAnalysisBuilder bldr = new TextureAnalysisBuilder();
		bldr.useBoard(board("QsQc2h"));
		
		// create a range to use
		Range range = range().define(handGroup("AQ")).define(handGroup("Q2"));
	
		// make sure the results are correct
		Map<HandValue, Double> frequencies = bldr.frequencies(range);
		assertEquals(frequencies.get(HandValue.THREE_OF_A_KIND), 0.54, 0.01);
		assertEquals(frequencies.get(HandValue.FULL_HOUSE), 0.45, 0.01);
		
		// make sure the rest of the values are 0
		frequencies.remove(HandValue.THREE_OF_A_KIND);
		frequencies.remove(HandValue.FULL_HOUSE);
		for (HandValue value : frequencies.keySet()) {
			assertEquals(frequencies.get(value), 0.0, 0.0);
		}

	}
	
}
