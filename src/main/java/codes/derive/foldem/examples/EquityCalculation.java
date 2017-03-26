package codes.derive.foldem.examples;

import static codes.derive.foldem.Foldem.*;

import java.util.Map;

import codes.derive.foldem.board.Board;
import codes.derive.foldem.range.Range;
import codes.derive.foldem.tool.EquityCalculationBuilder;
import codes.derive.foldem.tool.EquityCalculationBuilder.Equity;

public class EquityCalculation {

	private static EquityCalculationBuilder calc = new EquityCalculationBuilder();
	
	public static void main(String... args) {
		
		// heads up pot
		printEquities(calc.calculate(hand("QsQh"), hand("JsJh")));
		
		// multi-way pot
		printEquities(calc.calculate(hand("AsQs"), hand("6s7s"), hand("2h7c")));
	
		// pot with a board
		Board board = board(card("Ah"), card("5h"), card("Td"));
		//printEquities(calc.calculate(board, hand("AhAd"), hand("5d5c")));
		
		// group calculations
		Range range = new Range();
		range.define(hand("AsAd")); // TODO change once refactored
		range.define(hand("QsQd"));
		//printEquities(calc.calculate(range, new SingleHandGroup(hand("KhKs"))));
		
	}
	
	private static <T> void printEquities(Map<T, Equity> e) {
		StringBuilder bldr = new StringBuilder();
		for (T h : e.keySet()) {
			bldr.append("[").append(h).append(" ");
			bldr.append((int) (e.get(h).win() * 100)).append("%] ");
		}
		System.out.println(bldr.toString());
	}
	
}
