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
 * Contains basic constants pertaining to cards.
 */
public class Constants {

	/** A standard deck size.  **/
	public static final int DECK_SIZE = 52;
	
	/** The number of cards in a standard Hold'em board. **/
	public static final int BOARD_SIZE = 5;

	/** The number of cards in a standard Hold'em hand. **/
	public static final int HAND_SIZE = 2;

	/** Contains an enumeration of off-suit card combinations. **/
	public static final Suit[][] OFFSUIT_COMBINATIONS = {
		{ Suit.SPADES, Suit.CLUBS }, { Suit.SPADES, Suit.HEARTS },
		{ Suit.SPADES, Suit.DIAMONDS }, { Suit.CLUBS, Suit.HEARTS },
		{ Suit.CLUBS, Suit.DIAMONDS }, { Suit.HEARTS, Suit.DIAMONDS } };

	
}
