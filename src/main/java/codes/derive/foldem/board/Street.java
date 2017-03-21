package codes.derive.foldem.board;

/**
 * An enum representing a board street.
 */
public enum Street {

	/**
	 * Before the flop.
	 */
	PREFLOP(0),

	/**
	 * On the flop, 3 cards.
	 */
	FLOP(3),

	/**
	 * On the turn, 4 cards.
	 */
	TURN(4),

	/**
	 * On the river, 5 cards.
	 */
	RIVER(5);

	/* The number of cards available on this street */
	private final int cards;

	/**
	 * Constructs a street with the provided number of cards.
	 * 
	 * @param cards
	 *            The number of cards available on the street.
	 */
	private Street(int cards) {
		this.cards = cards;
	}

	/**
	 * Gets the number of cards available on the street.
	 * 
	 * @return The number of cards available on the street.
	 */
	public int cardCount() {
		return cards;
	}

}
