package codes.derive.foldem.hand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codes.derive.foldem.util.RandomContext;

/**
 * A hand group that can represent a range of hands and has functionality for
 * having hands appear within the group at different frequencies. This type can
 * be useful for running advanced equity calculations with weighted ranges.
 */
public class HandRange implements HandGroup {

	/*
	 * A map containing weighted hands in lists mapped to their respective
	 * weights.
	 */
	private final Map<Double, List<Hand>> weighted = new HashMap<>();

	/* A list containing hands that always appear within this group. */
	private final List<Hand> constant = new ArrayList<>();

	/**
	 * Defines a hand in this range.
	 * @param hand
	 * 		The hand.
	 * @return
	 * 		The {@link HandRange} context, for chaining.
	 */
	public HandRange define(Hand hand) {
		if (contains(hand)) {
			throw new IllegalArgumentException("Hand already exists within range");
		}
		constant.add(hand);
		return this; // TODO for contains defined
	}
	
	/**
	 * Defines a list of hands in this range.
	 * @param hands
	 * 		The hands.
	 * @return
	 * 		The {@link HandRange} context, for chaining.
	 */
	public HandRange define(Hand... hands) {
		for (Hand hand : hands) {
			define(hand);
		}
		return this;
	}

	/**
	 * Defines a hand group in this range.
	 * @param group
	 * 		The group.
	 * @return
	 * 		The {@link HandRange} context, for chaining.
	 */
	public HandRange define(HandGroup group) {
		if (!contains(group)) {
			for (Hand hand : group.all()) {
				if (contains(hand)) {
					throw new IllegalArgumentException("Range contains one hand from the group but not all");
				}
			}
			constant.addAll(group.all());
		}
		return this;
	}
	
	/**
	 * Defines a list of hand groups in this range.
	 * @param groups
	 * 		The groups.
	 * @return
	 * 		The {@link HandRange} context, for chaining.
	 */
	public HandRange define(HandGroup... groups) {
		for (HandGroup group : groups) {
			define(group);
		}
		return this;
	}

	/**
	 * Defines a weighted hand in this range. TODO more weighting explanation
	 * 
	 * @param hand
	 *            The hand.
	 * @param weight
	 *            The weight, as a decimal. This will define how often the
	 *            specified hand should appear in the range.
	 * @return The {@link HandRange} context, for chaining.
	 */
	public HandRange define(Hand hand, double weight) {
		if (weight <= 0.0 || weight > 1.0) {
			throw new IllegalArgumentException("Weight out of bounds");
		}
		for (List<Hand> hands : weighted.values()) {
			if (hands.contains(hand)) {
				hands.remove(hand);
			}
		}

		if (!weighted.containsKey(weight)) {
			weighted.put(weight, new ArrayList<Hand>());
		}
		List<Hand> hands = weighted.get(weight);
		if (!hands.contains(hand)) {
			hands.add(hand);
		}
		return this;
	}

	/**
	 * Defines a weighted hand group in this range. TODO more weighting
	 * explanation
	 * 
	 * @param group
	 *            The group.
	 * @param weight
	 *            The weight, as a decimal. This will define how often the
	 *            specified group should appear in the range.
	 * @return The {@link HandRange} context, for chaining.
	 */
	public HandRange define(HandGroup group, double weight) {
		if (!contains(group)) {
			for (Hand hand : group.all()) {
				if (contains(hand)) {
					throw new IllegalArgumentException("Range contains one hand from the group but not all");
				}
			}
		}
		for (Hand h : group.all()) {
			define(h, weight);
		}
		return this;
	}

	/**
	 * Obtains whether or not the specified hand can appear within this range.
	 * Differs from (TODO explain distinction between contains(... and matches)
	 * (Issue #5)
	 * 
	 * @param hand
	 *            The hand.
	 * @return The {@link HandRange} context, for chaining.
	 */
	public boolean contains(Hand hand) {
		for (List<Hand> hands : weighted.values()) {
			if (hands.contains(hand)) {
				return true;
			}
		}
		return constant.contains(hand);
	}

	/**
	 * Obtains whether or not the specified hand group can appear within this
	 * range. Differs from (TODO explain distinction between contains(... and
	 * matches) (Issue #5)
	 * 
	 * @param group
	 *            The group.
	 * @return <code>true</code> if the specified hand group can appear within
	 *         this range, otherwise <code>false</code>.
	 */
	public boolean contains(HandGroup group) {
		for (List<Hand> hands : weighted.values()) {
			for (Hand h : group.all()) {
				if (hands.contains(h)) {
					return true;
				}
			}
		}
		return true;
	}

	/**
	 * Obtains the frequency at which the specified hand will appear within this
	 * range, as a decimal.
	 * 
	 * @param hand
	 *            The hand.
	 * @return The frequency at which the specified hand will appear within this
	 *         range, as a decimal.
	 */
	public double weight(Hand hand) {
		for (double weight : weighted.keySet()) {
			if (weighted.get(weight).contains(hand)) {
				return weight;
			}
		}
		return constant.contains(hand) ? 1.0 : 0;
	}

	/**
	 * Obtains the frequency at which the specified hand group will appear
	 * within this range, as a decimal.
	 * 
	 * @param group
	 *            The hand group.
	 * @return The frequency at which the specified hand group will appear
	 *         within this range, as a decimal.
	 */
	public double weight(HandGroup group) {
		return weight(group.get());
	}

	@Override
	public boolean match(Hand hand) {
		if (constant.contains(hand)) {
			return true;
		}
		for (double w : weighted.keySet()) {
			if (weighted.get(w).contains(hand) && RandomContext.get().nextDouble() <= w) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Hand get() {
		if (constant.size() == 0) {
			throw new IllegalStateException("There needs to be at least one constant hand");
		}

		/*
		 * Create a list of candidate hands containing hands with constant even
		 * weight.
		 */
		List<Hand> candidates = constant;

		/*
		 * Determine whether or not to use a weighted subset of hands.
		 */
		double p = Math.random(), c = 0.0;
		for (double w : weighted.keySet()) {
			c += w;
			if (p <= c) {
				candidates = weighted.get(w);
				break;
			}
		}

		/*
		 * Return a random hand from our group of candidates.
		 */
		return candidates.get(RandomContext.get().nextInt(candidates.size()));
	}

	@Override
	public Collection<Hand> all() {
		List<Hand> hands = new ArrayList<>();
		hands.addAll(constant);
		for (List<Hand> l : weighted.values()) {
			hands.addAll(l);
		}
		return Collections.unmodifiableCollection(hands);
	}

}
