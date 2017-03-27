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
package codes.derive.foldem.example;

import static codes.derive.foldem.Poker.*;

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
