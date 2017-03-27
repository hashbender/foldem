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

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import org.junit.Test;

import codes.derive.foldem.Card;
import codes.derive.foldem.Deck;

public class TestBoard {

	@Test
	public void testStreet() {
		Board board = Boards.board(card("Ac"), card("Ad"), card("Ah"));
		assertEquals(Street.FLOP, board.getStreet());
	}
	
	@Test
	public void testConversion() {
		Board board = Boards.board(card("Ac"), card("Ad"), card("Ah"));
		
		// flop -> turn
		board = Boards.convert(board, Street.TURN, card("As"));
		assertEquals(Street.TURN, board.getStreet());
		
		// turn -> river
		board = Boards.convert(board, Street.RIVER, card("Kh"));
		assertEquals(Street.RIVER, board.getStreet());
		
		// turn <- river
		board = Boards.convert(board, Street.TURN);
		assertEquals(Street.TURN, board.getStreet());
	}
	
	@Test
	public void testShorthand() {
		Board a = Boards.board("AcAdAh");
		assertEquals(true, a.cards().contains(card("Ac")));
		assertEquals(true, a.cards().contains(card("Ad")));
		assertEquals(true, a.cards().contains(card("Ah")));
	}
	
	@Test
	public void testDeal() {
		Board board = Boards.board(deck(), Street.RIVER);
		Deck comparison = deck();
		for (Card card : board.cards()) {
			assertEquals(comparison.pop(), card);
		}
	}
	
}
