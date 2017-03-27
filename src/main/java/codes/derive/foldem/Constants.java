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
