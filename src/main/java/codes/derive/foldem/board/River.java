package codes.derive.foldem.board;

import codes.derive.foldem.Card;

public class River extends AbstractBoard {

	public River(Card... cards) {
		super(cards);
	}
	
	@Override
	public Street getStreet() {
		return Street.RIVER;
	}

}
