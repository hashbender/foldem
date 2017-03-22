package codes.derive.foldem;

/**
 * Represents a single card in a standard 52-card deck.
 */
public class Card {
	
	/** Labels for cards. **/
	public static final char[] LABEL = { 'A', '2', '3', '4', '5', '6', '7',
			'8', '9', 'T', 'J', 'Q', 'K' };
	
	/*
	 * I decided to do the enumeration like this instead of using an enum since
	 * it simplifies string notation and also resolves namespace conflict for
	 * the name "Card".
	 */

	/** Enumeration of card types. **/
	public static final int ACE = 0, DEUCE = 1, TREY = 2, FOUR = 3, FIVE = 4,
			SIX = 5, SEVEN = 6, EIGHT = 7, NINE = 8, TEN = 9, JACK = 10,
			QUEEN = 11, KING = 12;

	/* The card value. */
	private final int value;
	
	/* The suit of the card. */
	private final Suit suit;
	
	/**
	 * Constructs a new Card using the given card value and suit.
	 * 
	 * @param value
	 *            The card value.
	 * @param suit
	 *            The card suit.
	 */
	public Card(int value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	
	/**
	 * Constructs a new Card using the given string based shorthand
	 * representation.
	 * 
	 * <p>
	 * The shorthand format is based on the loose standard used by poker players
	 * for conveniently describing cards.
	 * </p>
	 * 
	 * <p>
	 * Using the format is relatively straightforward, each card's value is
	 * mapped to a single upper case character in "A23456789TJQK" and the card's
	 * suit is mapped to a single lower case letter in "hsdc" (hearts, spades,
	 * diamonds, clubs). An example for the ten of diamonds would be Td.
	 * </p>
	 * 
	 * @param text
	 *            A string shorthand for the card (eg. Ah would be the ace of
	 *            hearts, 7c the seven of clubs).
	 */
	public Card(String text) {
		char[] values = text.toCharArray();
		if (values.length != 2) {
			throw new IllegalArgumentException("Invalid hand length '" + text + "'");
		}
		
		// parse card value
		int value = -1;
		for (int i = 0; i < LABEL.length; i++) {
			if (LABEL[i] == values[0]) {
				value = i;
				break;
			}
		}
		if (value == -1) {
			throw new IllegalArgumentException("Invalid card shorthand '" + values[0] + "'");
		}
		this.value = value;
		
		// parse card suit
		Suit suit = null;
		for (Suit s : Suit.values()) {
			if (s.getShorthand() == values[1]) {
				suit = s;
				break;
			}
		}
		if (suit == null) {
			throw new IllegalArgumentException("Invalid suit shorthand '" + values[1] + "'");
		}
		this.suit = suit;
	}
	
	/**
	 * Obtains the value of the card.
	 * 
	 * @return The value of the card.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Obtains the suit of the card.
	 * 
	 * @return The suit of the card.
	 */
	public Suit getSuit() {
		return suit;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) {
			return false;
		}
		Card c = (Card) o;
		return c.suit == suit && c.value == value;
	}
	
	@Override
	public int hashCode() {
		return (31 + value) * 31 + suit.hashCode();
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(LABEL[value]).append(suit.getShorthand()).toString();
	}
	
}
