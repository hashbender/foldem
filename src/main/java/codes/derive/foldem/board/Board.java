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
