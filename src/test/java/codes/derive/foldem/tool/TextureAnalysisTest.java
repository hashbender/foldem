package codes.derive.foldem.tool;

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import java.util.Map;

import codes.derive.foldem.eval.HandValue;
import codes.derive.foldem.range.Range;

public class TextureAnalysisTest {
	
	/*
	 * This test is currently disabled because it causes Travis to fail because
	 * of CPU overuse. If you need to make sure your results are accurate please
	 * run this manually.
	 */
	
	//@Test(expected=IllegalArgumentException.class)
	public void testNoViableHandException() {

		// create an analysis builder
		TextureAnalysisBuilder bldr = new TextureAnalysisBuilder();
		bldr.useBoard(board("QsQc2h"));

		// create a range containing only hands that will be found on the board
		Range range = range(hand("QsQc"), hand("Qs2h"));
		
		// run the analysis
		bldr.frequencies(range);
	}

	//@Test
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
