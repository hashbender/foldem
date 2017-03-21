package codes.derive.foldem.board;

import codes.derive.foldem.Card;

public class Turn extends AbstractBoard {

	public Turn(Card... cards) {
		super(cards);
	}

	@Override
	public Street getStreet() {
		return Street.TURN;
	}


}
