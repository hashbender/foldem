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
package codes.derive.foldem.util;

import codes.derive.foldem.Card;
import codes.derive.foldem.Hand;
import codes.derive.foldem.Suit;
import codes.derive.foldem.board.Board;

/**
 * Contains functions for pretty string formatting cards, similar to the Deuces
 * Python evaluation library (<a>https://github.com/worldveil/deuces</a>).
 */
public class PrettyFormat {

	/**
	 * Obtains the unicode character for the specified suit.
	 * 
	 * @param suit
	 *            The suit.
	 * @return The unicode character for the specified suit.
	 */
	public static char get(Suit suit) {
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

	/**
	 * Gets a pretty formatted string representing the specified {@link Card}.
	 * 
	 * @param card
	 *            The card.
	 * @return The pretty formatted string for the specified {@link Card}.
	 */
	public static String get(Card card) {
		return new StringBuilder().append(Card.LABEL[card.getValue()])
				.append(get(card.getSuit())).toString();
	}

	/**
	 * Gets a pretty formatted string representing the specified {@link Hand}.
	 * 
	 * @param hand
	 *            The hand.
	 * @return The pretty formatted string for the specified {@link Hand}.
	 */
	public static String get(Hand hand) {
		StringBuilder bldr = new StringBuilder();
		for (Card card : hand.cards()) {
			bldr.append(get(card)).append(',');
		}
		return bldr.substring(0, bldr.length() - 1).toString();
	}

	/**
	 * Gets a pretty formatted string representing the specified {@link Board}.
	 * 
	 * @param board
	 *            The board.
	 * @return The pretty formatted string for the specified {@link Board}.
	 */
	public static String get(Board board) {
		StringBuilder bldr = new StringBuilder();
		for (Card card : board.cards()) {
			bldr.append(get(card)).append(", ");
		}
		return bldr.substring(0, bldr.length() - 2).toString();
	}

}
