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
package codes.derive.foldem.eval;

/**
 * Represents a no limit Hold 'em hand value.
 */
public enum HandValue {
	
	/** No qualifying hand. **/
	NONE(0),

	/** A high card. **/
	HIGH_CARD(1), 
	
	/** A pair. **/
	PAIR(2),
	
	/** Two-pair. **/
	TWO_PAIR(3), 
	
	/** Three of a kind. */
	THREE_OF_A_KIND(4),
	
	/** A straight. **/
	STRAIGHT(5), 
	
	/** A flush. **/
	FLUSH(6), 
	
	/** A full house. **/
	FULL_HOUSE(7),
	
	/** Four of a kind. **/
	FOUR_OF_A_KIND(8), 
	
	/** A straight-flush. **/
	STRAIGHT_FLUSH(9);

	/* The hand rank. */
	private final int rank;

	/**
	 * Constructs a new {@link HandValue} using the specified rank.
	 * 
	 * @param rank
	 *            The rank.
	 */
	private HandValue(int rank) {
		this.rank = rank;
	}

	/**
	 * Obtains the rank for this {@link HandValue}, where a straight flush (the
	 * best possible hand) is 9, and no hand (the worst possible hand) is is 0.
	 * 
	 * @return The rank of this {@link HandValue}.
	 */
	public int rank() {
		return rank;
	}

	/**
	 * Compares this {@link HandValue} value to another {@link HandValue} value.
	 * 
	 * @param v
	 *            The value to compare to.
	 * @return 0 if this.rank == v.rank, 1 if this.rank > v.rank and conversely
	 *         -1 if this.rank < v.rank .
	 */
	public int compare(HandValue v) {
		if (this.rank == v.rank) {
			return 0;
		}
		return this.rank > v.rank ? 1 : -1;
	}

}
