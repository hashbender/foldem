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
