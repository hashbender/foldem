package codes.derive.foldem.board;

import codes.derive.foldem.Card;

public class Flop extends AbstractBoard {

	public Flop(Card... cards) {
		super(cards);
	}

	@Override
	public Street getStreet() {
		return Street.FLOP;
	}

}
