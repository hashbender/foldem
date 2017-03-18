package codes.derive.foldem.util;

import codes.derive.foldem.Board;
import codes.derive.foldem.Card;
import codes.derive.foldem.Suit;
import codes.derive.foldem.hand.Hand;

public class NeatCards {
	
	public static char represent(Suit suit) {
		switch (suit) {
		case SPADES:
			return '\u2660';
		case CLUBS:
			return '\u2663';
		case DIAMONDS:
			return '\u2666';
		case HEARTS:
			return '\u2764';
		}
		throw new IllegalArgumentException("Invalid suit");
	}
	
	public static String represent(Card card) {
		return new StringBuilder().append(Card.LABEL[card.getValue()])
				.append(represent(card.getSuit())).toString();
	}
	
	public static String represent(Hand hand) {
		StringBuilder bldr = new StringBuilder();
		for (Card card : hand.cards()) {
			bldr.append(represent(card)).append(',');
		}
		return bldr.substring(0, bldr.length() - 1).toString();
		
	}
	
	public static String represent(Board board) {
		StringBuilder bldr = new StringBuilder();
		for (Card card : board.cards()) {
			bldr.append(represent(card)).append(", ");
		}
		return bldr.substring(0, bldr.length() - 2).toString();
	}

}
