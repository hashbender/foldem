package codes.derive.foldem;

import java.util.Map;

import codes.derive.foldem.equity.Equity;
import codes.derive.foldem.equity.EquityCalculator;
import codes.derive.foldem.hand.BasicHandGroup;
import codes.derive.foldem.hand.Hand;
import codes.derive.foldem.hand.HandGroup;
import codes.derive.foldem.hand.HandRange;

// TODO tidy look @ mutability stuff
// TODO also, do we want this to be the only way to use the API?
public class Foldem {
	
	private static final EquityCalculator calc = new EquityCalculator();

	public static Deck deck() {
		return new Deck();
	}
	
	public static Card card(int value, Suit suit) {
		return new Card(value, suit);
	}
	
	public static Card card(String text) {
		return new Card(text);
	}
	
	public static Hand hand(Card... cards) {
		return new Hand(cards);
	}
	
	public static Hand hand(String cards) {
		return new Hand(card(cards.substring(0, 2)), card(cards.substring(2, 4)));
	}
	
	public static HandGroup basicGroup(String... hands) {
		Hand[] h = new Hand[hands.length];
		for (int i = 0; i < h.length; i++) {
			h[i] = hand(hands[i]);
		}
		return new BasicHandGroup(h);
	}
	
	public static HandGroup basicGroup(Hand... hands) {
		return new BasicHandGroup(hands);
	}

	public static HandRange rangeGroup() {
		return new HandRange();
	}
	
	public static Board board(Deck deck) {
		return new Board(deck);
	}
	
	public static Board board(Card... cards) {
		return new Board(cards);
	}

	public static Map<Hand, Equity> equity(Hand... hands) {
		return calc.calculate(hands); // TODO just a thought, replacing Board with 3 alternative objects, Flop, Turn, River all implementing a base type of Board
	}
	
	public static Map<Hand, Equity> equity(Board board, Hand... hands) {
		return null;
	}
	
	public static Map<HandGroup, Equity> equity(HandGroup... groups) {
		return null;
	}
	
	public static Map<HandGroup, Equity> equity(Board board, HandGroup... groups) {
		return null;
	}
	
}
