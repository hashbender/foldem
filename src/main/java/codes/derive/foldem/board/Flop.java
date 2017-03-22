package codes.derive.foldem.board;

import codes.derive.foldem.Card;

/**
 * Represents a flop.
 */
public class Flop extends AbstractBoard {

	/**
	 * Constructs a new flop.
	 * 
	 * @param cards
	 *            The cards.
	 */
	public Flop(Card... cards) {
		super(cards);
	}

	@Override
	public Street getStreet() {
		return Street.FLOP;
	}

}
