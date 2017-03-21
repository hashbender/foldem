package codes.derive.foldem.hand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a hand group containing a single hand.
 */
public class SingleHandGroup implements HandGroup {

	/* The hand represented by this group. */
	private final Hand hand;
	
	/**
	 * Creates a new hand group containing only the specified hand.
	 * 
	 * @param hand
	 *            The hand to be contained in this group.
	 */
	public SingleHandGroup(Hand hand) {
		this.hand = hand;
	}
	
	@Override
	public boolean match(Hand h) {
		return h.equals(hand);
	}

	@Override
	public Hand get() {
		return hand;
	}

	@Override
	public Collection<Hand> all() {
		List<Hand> h = new ArrayList<>();
		h.add(hand);
		return Collections.unmodifiableCollection(h);
	}

}
