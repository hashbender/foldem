package codes.derive.foldem.examples;

import java.util.Map;

import codes.derive.foldem.EquityCalculator;
import codes.derive.foldem.EquityCalculator.Equity;
import codes.derive.foldem.Foldem;
import codes.derive.foldem.hand.Hand;
import codes.derive.foldem.util.NeatCards;

public class BasicCalc {

	public static void main(String... args) {
		EquityCalculator ec = new EquityCalculator();
		Map<Hand, Equity> e = ec.calculate(Foldem.hand(Foldem.card("Qs"), Foldem.card("Qh")), Foldem.hand(Foldem.card("Js"), Foldem.card("Jh")));
		for (Hand h : e.keySet()) {
			System.out.println(NeatCards.represent(h) + " " + e.get(h));
		}
	}
	
	
	
	
}
