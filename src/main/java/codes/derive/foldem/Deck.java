package codes.derive.foldem;

import java.util.Random;

import codes.derive.foldem.hand.Hand;
import codes.derive.foldem.util.Probability;

public class Deck {

	private final Card[] cards = new Card[Constants.DECK_SIZE];

	private int currentIndex = 0;
	
	// TODO commented documentation
	
	protected Deck() {
		int offset = 0;
		for (Suit suit : Suit.values()) {
			for (int num = Card.ACE; num <= Card.KING; num++) {
				cards[offset++] = new Card(num, suit);
			}
		}
	}
	
	public Deck shuffle() {
		return shuffle(Probability.RNG);
	}
	
	public Deck shuffle(Random random)  {
		if (currentIndex > 0) {
			throw new IllegalStateException("Deck cannot be shuffled after a card has been pulled");
		}	
		for (int i = 0; i < Constants.DECK_SIZE; i++) {
			int swap = random.nextInt(Constants.DECK_SIZE);
			Card temp = cards[swap];
			cards[swap] = cards[i];
			cards[i] = temp;
		}
		return this;
		
	}

	public Card pop(Card c) {
		int index = 0;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].equals(c)) {
				index = i;
				break;
			}
		}
		if (index < currentIndex) {
			throw new IllegalArgumentException("Card already dealt");
		}
		if (index > 0) {
			Card temp = cards[index];
			cards[index] = cards[currentIndex];
			cards[currentIndex] = temp;
		}
		return pop();
	}
	
	public Hand pop(Hand h) {
		for (Card c : h.cards()) {
			pop(c);
		}
		return h;
	}
	
	public Card pop() {
		return currentIndex < cards.length ? cards[currentIndex++] : null;
	}
	
	public int remaining() {
		return Constants.DECK_SIZE - currentIndex;
	}
	
	// TODO toString, hashCode
	
}
