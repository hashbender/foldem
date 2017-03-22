package codes.derive.foldem.board;

import codes.derive.foldem.Card;

/**
 * Represents a river.
 */
public class River extends AbstractBoard {

	/**
	 * Constructs a new river.
	 * 
	 * @param cards
	 *            The cards.
	 */
	public River(Card... cards) {
		super(cards);
	}

	@Override
	public Street getStreet() {
		return Street.RIVER;
	}

}
