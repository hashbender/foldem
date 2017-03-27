package codes.derive.foldem.example;

import static codes.derive.foldem.Foldem.*;

import java.util.Map;

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.range.Range;
import codes.derive.foldem.tool.EquityCalculationBuilder;
import codes.derive.foldem.tool.EquityCalculationBuilder.Equity;

public class EquitiesExample {

	public static void main(String... args) {
		
		// initialize a calculator
		EquityCalculationBuilder calc = calculationBuilder();
		
		/*
		 * Lets run some simulations with hands.
		 */
		
		// find out how well aces do against kings
		Hand aces = hand("AcAh");
		Hand kings = hand("KcKh");
		
		// run the calculation
		Map<Hand, Equity> equities = calc.calculate(aces, kings);
		System.out.println("Aces win " + percent(equities.get(aces).win()) + "%");
		System.out.println("Kings win " + percent(equities.get(kings).win()) + "%");
		
		// now lets see how well aces do when the tables are turned
		Board board = board("Ks7h6d");
		calc.useBoard(board);
		
		// run the calculation
		equities = calc.calculate(aces, kings);
		System.out.println("On board " + format(board) + ":");
		System.out.println("Aces win " + percent(equities.get(aces).win()) + "%");
		System.out.println("Kings win " + percent(equities.get(kings).win()) + "%");
		
		/*
		 * Should output:
		 * 	Aces win 82.59%
		 * 	Kings win 16.93%
		 * 	On board K♠, 7❤, 6♦:
		 * 	Aces win 8.59%
		 * 	Kings win 91.41%
		 */
		
		/*
		 * Now lets run some with ranges.
		 */
		
		// create a range that consists of aces and 72 off-suit.
		Range a = range().define(handGroup("AA")).define(handGroup("72o"));
	
		// create a range that only consists of kings.
		Range b = range().define(handGroup("KK"));
				
		// reset our board
		calc.useBoard(board());
		
		// run the calculation
		Map<Range, Equity> rangeEquities = calc.calculate(a, b);
		System.out.println("Aces/72o wins " + percent(rangeEquities.get(a).win()) + "%");
		System.out.println("Kings win " + percent(rangeEquities.get(b).win()) + "%");
		
		/*
		 * Should output:
		 * 	Aces/72o wins 34.99%
		 * 	Kings win 64.58%
		 */

	}
	
}
