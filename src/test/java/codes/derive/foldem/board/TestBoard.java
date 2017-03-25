package codes.derive.foldem.board;

import static codes.derive.foldem.Foldem.*;
import static org.junit.Assert.*;

import org.junit.Test;

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
	
}
