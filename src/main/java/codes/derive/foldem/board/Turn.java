package codes.derive.foldem.board;

import codes.derive.foldem.Card;

/**
 * Represents a turn.
 */
public class Turn extends AbstractBoard {

	/**
	 * Constructs a new turn.
	 * 
	 * @param cards
	 * 		The cards.
	 */
	public Turn(Card... cards) {
		super(cards);
	}

	@Override
	public Street getStreet() {
		return Street.TURN;
	}


}
