package codes.derive.foldem.board;

import codes.derive.foldem.Card;
import codes.derive.foldem.Deck;

public class Boards {

	public static Board board(Card... cards) {
		for (Street street : Street.values()) {
			if (cards.length == street.cardCount()) {
				return new GenericBoard(street, cards);
			}
		}
		throw new IllegalArgumentException("No board for card count " + cards.length);
	}
	
	public static Board flop(Card... cards) {
		return new Flop(cards);
	}
	
	public static Board turn(Card... cards) {
		return new Turn(cards);
	}
	
	public static Board river(Card... cards) {
		return new River(cards);
	}
	
	public static Board flop(Deck deck) {
		Card[] cards = new Card[3];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new Flop(cards);
	}
	
	public static Board turn(Deck deck) {
		Card[] cards = new Card[4];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new Turn(cards);
	}
	
	public static Board river(Deck deck) {
		Card[] cards = new Card[5];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new River(cards);
	}
	
}
