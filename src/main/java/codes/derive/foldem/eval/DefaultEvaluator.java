package codes.derive.foldem.eval;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codes.derive.foldem.Card;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.hand.Hand;

/**
 * A hand evaluator using Cactus Kev's 5 card system adopted for 7 card hands
 * using the 21-combinations method.
 */
public class DefaultEvaluator implements Evaluator {
	
	/* Bitmask for suited 5-card hand hashes. */
	private static final int SUITED_MASK = 0x80000000;
	
	/* The number of possible distinct 5 card hands. */
	private static final int DISTINCT_VALUES = 7462;
	
	/* Prime value mappings for cards. */
	private static final int PRIME_DEUCE = 2;
	private static final int PRIME_TREY = 3;
	private static final int PRIME_FOUR = 5;
	private static final int PRIME_FIVE = 7;
	private static final int PRIME_SIX = 11;
	private static final int PRIME_SEVEN = 13;
	private static final int PRIME_EIGHT = 17;
	private static final int PRIME_NINE = 19;
	private static final int PRIME_TEN = 23;
	private static final int PRIME_JACK = 29;
	private static final int PRIME_QUEEN = 31;
	private static final int PRIME_KING = 37;
	private static final int PRIME_ACE = 41;

	/* Contains card values ordered by rank. */
	protected static final int[] CARD_RANKS = { PRIME_ACE, PRIME_DEUCE,
			PRIME_TREY, PRIME_FOUR, PRIME_FIVE, PRIME_SIX, PRIME_SEVEN,
			PRIME_EIGHT, PRIME_NINE, PRIME_TEN, PRIME_JACK, PRIME_QUEEN,
			PRIME_KING };
	
	/* Contains ranking values mapped to their respective hashes. */
	private static final Map<Integer, Short> rankings = new HashMap<>();

	static {
		
		InputStream in;
		
		// Try to find a stream for our rank data.
		Path path = Paths.get("rank_data");
		if (Files.exists(path)) {
			try {
				in = Files.newInputStream(path);
			} catch (IOException e) {
				throw new RuntimeException("Could not load rank_data from local file", e);
			}
		} else {
			in = DefaultEvaluator.class.getResourceAsStream("rank_data");
		}
		
		// Read the rank data from the stream and store it
		try (DataInputStream din = new DataInputStream(in)) {
			for (short i = 0; i < DISTINCT_VALUES; i++) {
				rankings.put(din.readInt(), i);
			}
			din.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not load rank_data resource, "
					+ "make sure Foldem was built correctly", e);
		}
	}
	
	@Override
	public int rank(Hand h, Board b) {
		
		/*
		 * Create a list to hold 21 5 card hands created from our 7 card hands.
		 */
		List<Card[]> hands = new ArrayList<>(21);

		/*
		 * Group our community cards with the cards in our hand to create a base
		 * combination.
		 */
		List<Card> original = new ArrayList<>();
		original.addAll(h.cards());
		original.addAll(b.cards());
		
		/*
		 * Initialize two size indicating constants for our k-combination.
		 */
		final int n = original.size();
		final int k = 5;
		
		/*
		 * Initialize our helper array to hold offset information.
		 */
		int[] bitVector = new int[k + 1];
		for (int i = 0; i <= k; i++) {
			bitVector[i] = i;
		}
		
		/*
		 * Begin creating combinations.
		 */
		int endIndex = 1;
		while (!((endIndex == 0 || (k > n)))) {
			Card[] currentCombination = new Card[k];
			for (int i = 1; i <= k; i++) {
				int index = bitVector[i] - 1;
				currentCombination[i - 1] = original.get(index);
				
			}
			endIndex = k;
			while (bitVector[endIndex] == n - k + endIndex) {
				endIndex--;
				if (endIndex == 0) {
					break;
				}
			}
			bitVector[endIndex]++;
			for (int i = endIndex + 1; i <= k; i++) {
				bitVector[i] = bitVector[i - 1] + 1;
			}
			hands.add(currentCombination);
		}
		
		/*
		 * Find the hand within the combination with the highest rank, which
		 * translates to the lowest rank number.
		 */
		int rank = DISTINCT_VALUES;
		for (Card[] cards : hands) {
			boolean suited = true;
			
			/*
			 * Find the encoded value of our hand for the lookup table.
			 */
			int value = CARD_RANKS[cards[0].getValue()];
			for (int i = 1; i < cards.length; i++) {
				value *= CARD_RANKS[cards[i].getValue()];
				if (!cards[i].getSuit().equals(cards[0].getSuit())) {
					suited = false;
				}
			}
	
			/*
			 * If our hand is suited, apply a bit mask to our hand's encoded
			 * value indicating that it is.
			 */
			if (suited) {
				value |= SUITED_MASK;
			}

			/*
			 * Finally, we obtain our rank from the hash map.
			 */
			int r = rankings.get(value);
			if (r < rank) {
				rank = r;
			}
		}
		return rank;
	}

}
