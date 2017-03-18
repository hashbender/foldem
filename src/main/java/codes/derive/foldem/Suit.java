package codes.derive.foldem;

/**
 * Represents a suit for a card in a standard 52 card deck.
 */
public enum Suit {
	SPADES, CLUBS, HEARTS, DIAMONDS;
	
	/**
	 * Obtains the shorthand character for this suit. Equivalent to
	 * <code>name().toLowercase().charAt(0)</code>
	 * 
	 * @return The shorthand character for this suit.
	 */
	public char getShorthand() {
		return name().toLowerCase().charAt(0);
	}
	
}
