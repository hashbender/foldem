package codes.derive.foldem.tool;

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.range.Range;
import codes.derive.foldem.tool.EquityCalculationBuilder.Equity;

public class EquityCalculationTest {
	
	@Test
	public void testHandBasedEquityCalculation() {
		
		Map<Hand, Double> rates = new HashMap<>();
		rates.put(hand("AcAs"), 0.36);
		rates.put(hand("KcKs"), 0.18);
		rates.put(hand("QcQs"), 0.15);
		rates.put(hand("JcJs"), 0.12);
		rates.put(hand("TcTs"), 0.10);
		rates.put(hand("9c9s"), 0.09);
		
		Map<Hand, Equity> equities = equity(rates.keySet().toArray(new Hand[0]));
		for (Hand h : equities.keySet()) {
			Equity e = equities.get(h);
			assertEquals(1.0, e.win() + e.lose() + e.split(), 0.01);
			assertEquals(rates.get(h), e.win(), 1.0);
		}
		
	}
	
	@Test
	public void testRangeBasedEquityCalculationUnweighted() {
		Range a = range(hand("AcAh"), hand("QsQh"));
		Range b = range(hand("KsKh"), hand("JsJh"));
		
		Map<Range, Equity> equities = equity(a, b);
		
		Equity equityA = equities.get(a);
		assertEquals(1.0, equityA.win() + equityA.lose() + equityA.split(), 0.01);
		assertEquals(0.65, equityA.win(), 0.01);
		assertEquals(0.34, equityA.lose(), 0.01);

		Equity equityB = equities.get(b);
		assertEquals(1.0, equityB.win() + equityB.lose() + equityB.split(), 0.01);
		assertEquals(1.0-0.65, equityB.win(), 0.01);
		assertEquals(1.0-0.34, equityB.lose(), 0.01);
		
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testCauseRangeDuplicateException() {
		equity(range(hand("QsQd")), range(hand("QsAc"), hand("AcQs")));
	}
	
	
	@Test
	public void testDeadCards() {
		
		Hand a = hand("9s9h");
		Hand b = hand("TdTs");
		
		EquityCalculationBuilder bldr = calculationBuilder().useDeadCards(card("Tc"));
		Map<Hand, Equity> equities = bldr.calculate(a, b);
		
		Equity equityA = equities.get(a);
		assertEquals(1.0, equityA.win() + equityA.lose() + equityA.split(), 0.01);
		assertEquals(0.19, equityA.win(), 0.01);
		assertEquals(1.0 - 0.19, equityA.lose(), 0.01);
		
		Equity equityB = equities.get(b);
		assertEquals(1.0, equityB.win() + equityB.lose() + equityB.split(), 0.01);
		assertEquals(0.8, equityB.win(), 0.01);
		assertEquals(1.0 - 0.8, equityB.lose(), 0.01);
		
	}
	
	@Test
	public void testBoards() {
		
		Hand a = hand("9s9h");
		Hand b = hand("TdTs");
		
		EquityCalculationBuilder bldr = calculationBuilder();
		bldr.useBoard(Boards.flop(card("9c"), card("2c"), card("2h")));
		
		Map<Hand, Equity> equities = bldr.calculate(a, b);
		
		Equity equityA = equities.get(a);
		assertEquals(1.0, equityA.win() + equityA.lose() + equityA.split(), 0.01);
		assertEquals(0.91, equityA.win(), 0.01);
		assertEquals(1.0 - 0.91, equityA.lose(), 0.01);
		
		Equity equityB = equities.get(b);
		assertEquals(1.0, equityB.win() + equityB.lose() + equityB.split(), 0.01);
		assertEquals(0.09, equityB.win(), 0.01);
		assertEquals(1.0 - 0.09, equityB.lose(), 0.01);
		
	}

}
