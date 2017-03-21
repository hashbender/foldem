package codes.derive.foldem.board;

import codes.derive.foldem.Card;

/**
 * A basic board type.
 */
public class GenericBoard extends AbstractBoard {

	/* The board's street. */
	private final Street street;

	/**
	 * Constructs a new board using the specified street and cards.
	 * 
	 * @param street
	 *            The street.
	 * @param cards
	 *            The cards.
	 */
	public GenericBoard(Street street, Card... cards) {
		super(cards);
		this.street = street;
	}

	@Override
	public Street getStreet() {
		return street;
	}

}
