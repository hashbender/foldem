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

import static codes.derive.foldem.Poker.*;

import codes.derive.foldem.Card;
import codes.derive.foldem.Deck;

/**
 * Helper class containing functions for working with {@link Board}.
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
	 * @param shorthand
	 *            The cards to use on the board, specified as shorthand.
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
	 * Constructs a new {@link Flop} containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new {@link Flop} containing the specified cards.
	 */
	public static Flop flop(Card... cards) {
		return new Flop(cards);
	}

	/**
	 * Constructs a new {@link Turn} containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new {@link Turn} containing the specified cards.
	 */
	public static Turn turn(Card... cards) {
		return new Turn(cards);
	}

	/**
	 * Constructs a new {@link River} containing the specified cards.
	 * 
	 * @param cards
	 *            The cards.
	 * @return A new {@link River} containing the specified cards.
	 */
	public static River river(Card... cards) {
		return new River(cards);
	}

	/**
	 * Constructs a new {@link Flop} sourced from cards popped from the
	 * specified {@link Deck}.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new {@link Flop} sourced from cards popped from the specified
	 *         {@link Deck}.
	 */
	public static Board flop(Deck deck) {
		Card[] cards = new Card[3];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new Flop(cards);
	}

	/**
	 * Constructs a new {@link Turn} sourced from cards popped from the
	 * specified {@link Deck}.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new {@link Turn} sourced from cards popped from the specified
	 *         {@link Deck}.
	 */
	public static Board turn(Deck deck) {
		Card[] cards = new Card[4];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new Turn(cards);
	}

	/**
	 * Constructs a new {@link River} sourced from cards popped from the
	 * specified {@link Deck}.
	 * 
	 * @param deck
	 *            The deck.
	 * @return A new {@link River} sourced from cards popped from the specified
	 *         {@link Deck}.
	 */
	public static Board river(Deck deck) {
		Card[] cards = new Card[5];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return new River(cards);
	}

	/**
	 * Moves the {@link Board} specified to the specified (previous)
	 * {@link Street}.
	 * 
	 * @param board
	 *            The board.
	 * @param street
	 *            The street to move the board.
	 * @return returns the {@link Board} at the new {@link Street}.
	 */
	public static Board convert(Board board, Street street) {
		if (street.cardCount() > board.getStreet().cardCount()) {
			throw new IllegalArgumentException("new street has more cards than input");
		}
		
		/*
		 * Create an arrow containing our new cards, truncating the old one if
		 * need be.
		 */
		Card[] original = board.cards().toArray(new Card[0]);
		Card[] cards = new Card[street.cardCount()];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = original[i];
		}

		return new GenericBoard(street, cards);
	}

	/**
	 * Moves the {@link Board} specified to the specified {@link Street} and
	 * uses the cards provided as the additional cards.
	 * 
	 * @param board
	 *            The board.
	 * @param street
	 *            The street to move the board to.
	 * @param cards
	 *            The cards to use
	 * @return The {@link Board} at the new {@link Street} with the specified
	 *         cards.
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
	 * Moves the {@link Board} specified to the specified {@link Street} and
	 * uses the specified {@link Deck} to add any new cards.
	 * 
	 * @param board
	 *            The board.
	 * @param street
	 *            The street to move the board to.
	 * @param deck
	 *            The deck to source cards from.
	 * @return The {@link Board} at the new {@link Street} with cards from the
	 *         specified {@link Deck}.
	 */
	public static Board convert(Board board, Street street, Deck deck) {
		if (street.cardCount() < board.getStreet().cardCount()) {
			return convert(board, street);
		}

		/*
		 * Obtain our new cards from the deck provided.
		 */
		Card[] cards = new Card[street.cardCount() - board.getStreet().cardCount()];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.pop();
		}
		return convert(board, street, cards);
	}

}