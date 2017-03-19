package codes.derive.foldem.hand;

import static codes.derive.foldem.Foldem.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import codes.derive.foldem.Card;
import codes.derive.foldem.Suit;
import codes.derive.foldem.util.RandomContext;

/**
 * Represents a basic hand group that can represent all combinations of a hand
 * specified in string notation without suit.
 */
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
	
	/**
	 * Constructs a new basic hand group using the specified hand in normal
	 * string notation. TODO explain notation
	 * 
	 * @param hand
	 *            The hand to create a group for, specified in normal string
	 *            notation for hands.
	 */
	public BasicHandGroup(String hand) {
		
		/*
		 * Find the numeric values of the card labels provided
		 */
		int a = -1, b = -1;
		for (int i = 0; i < Card.LABEL.length; i++) {
			if (Card.LABEL[i] == hand.charAt(0)) {
				a = i;
			}
			if (Card.LABEL[i] == hand.charAt(1)) {
				b = i;
			}
		}
		
		/*
		 * If our hand is suited
		 */
		if (hand.length() == 3 && hand.charAt(2) == 's') {
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
	
	/**
	 * Constructs a basic hand group using the specified hands.
	 * @param hands
	 * 		The hands to be contained within this hand group.
	 */
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
		return hands.get(RandomContext.get().nextInt(hands.size()));
	}
 
}
