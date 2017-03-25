package codes.derive.foldem;

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import codes.derive.foldem.EquityCalculator.Equity;
import codes.derive.foldem.hand.Hand;

public class TestEquityCalculator {

	// I wish all unit tests were this ugly ¯\_(ツ)_/¯
	
	@Test
	public void testMultiway() {

		// initialize winning rates test cases
		Map<Hand, Double> winrate = new HashMap<>();
		winrate.put(hand("AcAs"), 36.0);
		winrate.put(hand("KcKs"), 18.0);
		winrate.put(hand("QcQs"), 15.0);
		winrate.put(hand("JcJs"), 12.0);
		winrate.put(hand("TcTs"), 10.0);
		winrate.put(hand("9c9s"), 9.0);

		// initialize losing rates test cases
		Map<Hand, Double> loserate = new HashMap<>();
		loserate.put(hand("AcAs"), 63.0);
		loserate.put(hand("KcKs"), 81.0);
		loserate.put(hand("QcQs"), 85.0);
		loserate.put(hand("JcJs"), 88.0);
		loserate.put(hand("TcTs"), 89.0);
		loserate.put(hand("9c9s"), 90.0);
		
		// calculate and check equity
		Map<Hand, Equity> e = equity(winrate.keySet().toArray(new Hand[0]));
		for (Hand h : e.keySet()) {
			Equity he = e.get(h);			
			assertEquals(1.0, he.win() + he.lose() + he.split(), 0.02);
			assertPercent(he, winrate.get(h), loserate.get(h), e.get(h).split());
		}

	}

	private void assertPercent(Equity e, double expectedWin,
			double expectedLose, double expectedSplit) {
		assertEquals(expectedWin, percent(e.win()), 1.0);
		assertEquals(expectedLose, percent(e.lose()), 1.0);
		assertEquals(expectedSplit, percent(e.split()), 1.0);
	}

}
