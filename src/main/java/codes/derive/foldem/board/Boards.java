package codes.derive.foldem.board;

import static codes.derive.foldem.Foldem.*;

import codes.derive.foldem.Card;
import codes.derive.foldem.Deck;

/**
 * Helper class containing functions for working with boards.
 */
public class Boards {

	/**
	 * Constructs a new {@link Board} containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A {@link Board} containing the specified cards.
	 */
	public static Board board(Card... cards) {
		for (Street street : Street.values()) {
			if (cards.length == street.cardCount()) {
				return new GenericBoard(street, cards);
			}
		}
		throw new IllegalArgumentException("No board for card count " + cards.length);
	}
	
	/**
	 * Constructs a new {@link Board} containing the specified cards represented
	 * in shorthand.
	 * 
	 * @param cards
	 *            The cards to use on the board.
	 * @return A {@link Board} containing the specified cards.
	 */
	public static Board board(String shorthand) {
		return board(cards(shorthand).toArray(new Card[0]));
	}

	/**
	 * Constructs a new {@link Board} containing cards dealt from the specified
	 * {@link Deck}.
	 * 
	 * @param deck
	 *            The deck to deal from.
	 * @param street
	 *            The street to deal.
	 * @return A new {@link Board} using cards from the specified {@link Deck}.
	 */
	public static Board board(Deck deck, Street street) {
		Card[] cards = new Card[street.cardCount()];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return board(cards);
	}
	
	/**
	 * Constructs a new flop containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new flop containing the specified cards.
	 */
	public static Board flop(Card... cards) {
		return new Flop(cards);
	}

	/**
	 * Constructs a new turn containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new turn containing the specified cards.
	 */
	public static Board turn(Card... cards) {
		return new Turn(cards);
	}

	/**
	 * Constructs a new river containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new river containing the specified cards.
	 */
	public static Board river(Card... cards) {
		return new River(cards);
	}

	/**
	 * Constructs a new flop sourced from cards popped from the specified deck.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new flop sourced from cards popped from the specified deck.
	 */
	public static Board flop(Deck deck) {
		Card[] cards = new Card[3];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new Flop(cards);
	}

	/**
	 * Constructs a new turn sourced from cards popped from the specified deck.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new turn sourced from cards popped from the specified deck.
	 */
	public static Board turn(Deck deck) {
		Card[] cards = new Card[4];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new Turn(cards);
	}

	/**
	 * Constructs a new river sourced from cards popped from the specified deck.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new river sourced from cards popped from the specified deck.
	 */
	public static Board river(Deck deck) {
		Card[] cards = new Card[5];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new River(cards);
	}
	
	// TODO consider other names (suggestions: to,switch,street,move)

	/**
	 * Moves the board specified to the specified (previous) street.
	 * 
	 * @param board
	 *            The board.
	 * @param street
	 *            The street to move the board.
	 * @return returns the board at the new street.
	 */
	public static Board convert(Board board, Street street) {
		if (street.cardCount() > board.getStreet().cardCount()) {
			throw new IllegalArgumentException("new street has more cards than input");
		}

		// create an array containing our new cards, truncating the old one if needed.
		Card[] original = board.cards().toArray(new Card[0]);
		Card[] cards = new Card[street.cardCount()];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = original[i];
		}

		return new GenericBoard(street, cards);
	}

	/**
	 * Moves the board specified to the specified street and uses the cards
	 * provided as the new cards on the street.
	 * 
	 * @param board
	 *            The board.
	 * @param street
	 *            The street to move the board to.
	 * @param cards
	 *            The cards to use
	 * @return The board at the new street with the specified cards.
	 */
	public static Board convert(Board board, Street street, Card... cards) {
		if (street.cardCount() < board.getStreet().cardCount()) {
			return convert(board, street);
		}

		/*
		 * create an array containing our new cards, copying our old cards and
		 * adding the new ones.
		 */
		Card[] original = board.cards().toArray(new Card[0]);
		Card[] newCards = new Card[street.cardCount()];
		for (int i = 0; i < original.length; i++) {
			newCards[i] = original[i];
		}
		for (int i = original.length, ib = 0; i < newCards.length; i++, ib++) {
			newCards[i] = cards[ib];
		}

		return new GenericBoard(street, newCards);
	}

	/**
	 * Moves the board specified to the specified street and uses the specified
	 * deck to add any new cards.
	 * 
	 * @param board
	 *            The board.
	 * @param street
	 *            The street to move the board to.
	 * @param deck
	 *            The deck to source cards from.
	 * @return The board at the new street with cards from the specified deck.
	 */
	public static Board convert(Board board, Street street, Deck deck) {
		if (street.cardCount() < board.getStreet().cardCount()) {
			return convert(board, street);
		}

		// obtain our new cards from the deck provided.
		Card[] cards = new Card[street.cardCount() - board.getStreet().cardCount()];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return convert(board, street, cards);
	}

}