package codes.derive.foldem.example;

import static codes.derive.foldem.Foldem.*;

/**
 * An example showing pretty print formatting of cards.
 */
public class PrettyCardsExample {

	public static void main(String... args) {
		
		/*
		 * Print some basic formatted information.
		 */
		System.out.println(format(card("Ac")));
		System.out.println(format(hand("AcAd")));
		System.out.println(format(board("Ac2s3h")));
		
		/*
		 * Should output:
		 * 	A♣
		 * 	A♣,A♦
		 * 	A♣, 2♠, 3❤
		 */
		
	}
	
}
