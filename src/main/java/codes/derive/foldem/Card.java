/*
 * This file is part of Fold'em, a Java library for Texas Hold 'em Poker.
 *
 * Fold'em is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fold'em is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Fold'em.  If not, see <http://www.gnu.org/licenses/>.
 */
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

	/** An ace card type. **/
	public static final int ACE = 0;

	/** A deuce (2) card type. **/
	public static final int DEUCE = 1;
	
	/** A trey (3) card type. **/
	public static final int TREY = 2;
	
	/** A four card type. **/
	public static final int FOUR = 3;
	
	/** A five card type. **/
	public static final int FIVE = 4;
	
	/** A six card type. **/
	public static final int SIX = 5;
	
	/** A seven card type. **/
	public static final int SEVEN = 6;
	
	/** An eight card type. **/
	public static final int EIGHT = 7;
	
	/** A nine card type. **/
	public static final int NINE = 8;
	
	/** A ten card type. **/
	public static final int TEN = 9;
	
	/** A jack card type. **/
	public static final int JACK = 10;
	
	/** A queen card type. **/
	public static final int QUEEN = 11;
	
	/** A king card type. **/
	public static final int KING = 12;

	/* The card value. */
	private final int value;
	
	/* The suit of the card. */
	private final Suit suit;

	/**
	 * Constructs a new {@link Card} using the given card value and suit.
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
		
		/*
		 * Parse the card value.
		 */
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
		
		/*
		 * Parse the card suit.
		 */
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + value;
		return result;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(LABEL[value]).append(suit.getShorthand()).toString();
	}
	
}
