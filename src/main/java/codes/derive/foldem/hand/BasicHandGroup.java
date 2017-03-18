package codes.derive.foldem.hand;

import static codes.derive.foldem.Foldem.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import codes.derive.foldem.Card;
import codes.derive.foldem.Suit;
import codes.derive.foldem.util.Probability;

public class BasicHandGroup implements HandGroup {
	
	/* Would rather just do this than do tricky OTF combinatorics */
	private static final Suit[][] OFFSUIT_COMBINATIONS = {
		{ Suit.SPADES, Suit.CLUBS },
		{ Suit.SPADES, Suit.HEARTS },
		{ Suit.SPADES, Suit.DIAMONDS },
		{ Suit.CLUBS, Suit.HEARTS },
		{ Suit.CLUBS, Suit.DIAMONDS },
		{ Suit.HEARTS, Suit.DIAMONDS }
	};

	private final List<Hand> hands = new ArrayList<>();
	
	public BasicHandGroup(String name) { // TODO should high level logic be here or elsewhere?
		
		/*
		 * Find the numeric values of the card labels provided
		 */
		int a = -1, b = -1;
		for (int i = 0; i < Card.LABEL.length; i++) {
			if (Card.LABEL[i] == name.charAt(0)) {
				a = i;
			}
			if (Card.LABEL[i] == name.charAt(1)) {
				b = i;
			}
		}
		
		/*
		 * If our hand is suited
		 */
		if (name.length() == 3 && name.charAt(2) == 's') {
			if (a == b) {
				throw new IllegalArgumentException("A hand cannot have identical cards of the same suit");
			}
			for (Suit suit : Suit.values()) {
				hands.add(hand(card(a, suit), card(b, suit)));
			}
		} else {
			
			/*
			 * Add all off-suit combinations of the provided hand.
			 */
			for (Suit[] suits : OFFSUIT_COMBINATIONS) {
				hands.add(hand(card(a, suits[0]), card(b, suits[1])));
				
				/*
				 * We only need to reverse the suits if A and B aren't equivalent.
				 */
				if (a != b) {
					hands.add(hand(card(a, suits[1]), card(b, suits[0])));
				}
			}
		}
	}
	
	public BasicHandGroup(Hand... hands) {
		this.hands.addAll(Arrays.asList(hands));
	}
	
	@Override
	public Collection<Hand> all() {
		return Collections.unmodifiableCollection(hands);
	}

	@Override
	public boolean match(Hand h) {
		return hands.contains(h);
	}

	@Override
	public Hand get() {
		return hands.get(Probability.random(hands.size()));
	}
 
}
