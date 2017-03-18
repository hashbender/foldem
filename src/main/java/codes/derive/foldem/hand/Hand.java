package codes.derive.foldem.hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import codes.derive.foldem.Card;
import codes.derive.foldem.Constants;

public class Hand {

	private final List<Card> cards = new ArrayList<>();
	
	public Hand(Card... cards) {
		if (cards.length != Constants.HAND_SIZE) {
			throw new IllegalArgumentException("Hand length should be " + Constants.HAND_SIZE + ", " + cards.length + " given");
		}
		this.cards.addAll(Arrays.asList(cards));
	}
	
	public Collection<Card> cards() {
		return Collections.unmodifiableCollection(cards);
	}
	
	public boolean suited() {
		return cards.get(0).getSuit() == cards.get(1).getSuit();
	}
	
	@Override
	public String toString() {
		return cards.get(0).toString().concat(cards.get(1).toString());
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Hand) && ((Hand) o).cards.equals(cards);
	}
	
	
}
