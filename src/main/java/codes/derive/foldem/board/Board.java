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

import java.util.Collection;

import codes.derive.foldem.Card;

/**
 * Represents a board.
 */
public interface Board {

	/**
	 * Obtains an unmodifiable view of the cards on this board.
	 * 
	 * @return An unmodifiable view of the cards on this board.
	 */
	public Collection<Card> cards();

	/**
	 * Represents the street that this board is on.
	 * 
	 * @return The street that this board is on.
	 */
	public Street getStreet();

}
