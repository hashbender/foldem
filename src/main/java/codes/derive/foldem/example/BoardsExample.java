package codes.derive.foldem.example;

import static codes.derive.foldem.Foldem.*;

import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.board.Street;

/**
 * An example that shows some basic usage of
 * {@link codes.derive.foldem.board.Board}.
 */
public class BoardsExample {

	public static void main(String... args) {
		
		// create a board
		Board a = board("AcAdAh");
		System.out.println(format(a));
		
		// convert the board to a turn
		Board b = Boards.convert(a, Street.TURN, card("3s"), card("3d"));
		System.out.println(format(b));

		// find out what AsKd is on our board
		System.out.println(value(hand("AsKd"), b));
		
		/*
		 * Should output:
		 * A♣, A♦, A❤
		 * A♣, A♦, A❤, 3♠
		 * FOUR_OF_A_KIND
		 */
		
	}
	
}
