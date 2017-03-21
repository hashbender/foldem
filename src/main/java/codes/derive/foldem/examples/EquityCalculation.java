package codes.derive.foldem.examples;

import static codes.derive.foldem.Foldem.*;

import java.util.Map;

import codes.derive.foldem.Board;
import codes.derive.foldem.EquityCalculator;
import codes.derive.foldem.EquityCalculator.Equity;
import codes.derive.foldem.hand.Hand;
import codes.derive.foldem.hand.HandGroup;
import codes.derive.foldem.hand.HandRange;
import codes.derive.foldem.hand.SingleHandGroup;
import codes.derive.foldem.util.PrettyCards;

public class EquityCalculation {

	private static EquityCalculator calc = new EquityCalculator();
	
	public static void main(String... args) {
		
		// heads up pot
		printEquities(calc.calculate(hand("QsQh"), hand("JsJh")));
		
		// multi-way pot
		printEquities(calc.calculate(hand("AsQs"), hand("6s7s"), hand("2h7c")));
	
		// pot with a board
		Board board = board(card("As"), card("5h"), card("Td"));
		printEquities(calc.calculate(board, hand("AhAd"), hand("5d5c")));
		
		// group calculations
		HandRange range = new HandRange();
		range.define(hand("AsAd")); // TODO change once refactored
		range.define(hand("QsQd"));
		printGroupEquities(calc.calculate(range, new SingleHandGroup(hand("KhKs"))));
		
	}
	
	private static void printEquities(Map<Hand, Equity> e) {
		StringBuilder bldr = new StringBuilder();
		for (Hand h : e.keySet()) {
			bldr.append("[").append(PrettyCards.get(h)).append(" ");
			bldr.append((int) (e.get(h).win() * 100)).append("%] ");
		}
		System.out.println(bldr.toString());
	}
	
	private static void printGroupEquities(Map<HandGroup, Equity> e) {
		StringBuilder bldr = new StringBuilder();
		for (HandGroup g : e.keySet()) {
			bldr.append("[").append(g).append(" ");
			bldr.append((int) (e.get(g).win() * 100)).append("%] ");
		}
		System.out.println(bldr.toString());
	}
	
}
