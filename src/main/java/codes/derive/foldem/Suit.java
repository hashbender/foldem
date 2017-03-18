package codes.derive.foldem;

/**
 * Represents a suit for a card in a standard 52 card deck.
 */
public enum Suit {
	SPADES,
	CLUBS,
	HEARTS,
	DIAMONDS;
	
	public char getShorthand() {
		return name().toLowerCase().charAt(0);
	}
	
}
