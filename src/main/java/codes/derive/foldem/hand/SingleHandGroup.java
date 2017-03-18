package codes.derive.foldem.hand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SingleHandGroup implements HandGroup {

	private final Hand hand;
	
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
