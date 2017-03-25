package codes.derive.foldem.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import codes.derive.foldem.Card;

/**
 * An abstract board that provides a basic {@link Board#cards()} implementation,
 * using an {@link java.util.ArrayList } for backing.
 */
public abstract class AbstractBoard implements Board {

	/* A list containing the cards on this board. */
	private final List<Card> cards = new ArrayList<>();

	/**
	 * Constructs a new board with the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 */
	public AbstractBoard(Card... cards) {
		this.cards.addAll(Arrays.asList(cards));
	}

	@Override
	public Collection<Card> cards() {
		return Collections.unmodifiableCollection(cards);
	}

}
