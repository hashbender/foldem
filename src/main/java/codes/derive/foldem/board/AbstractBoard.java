package codes.derive.foldem.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import codes.derive.foldem.Card;

public abstract class AbstractBoard implements Board {

	private final List<Card> cards = new ArrayList<>();
	
	public AbstractBoard(Card... cards) {
		if (cards.length != getStreet().cardCount()) {
			throw new IllegalArgumentException("Wrong number of cards");
		}
		this.cards.addAll(Arrays.asList(cards));
	}
	
	@Override
	public Collection<Card> cards() {
		return Collections.unmodifiableCollection(cards);
	}

}
