package codes.derive.foldem.range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import codes.derive.foldem.Hand;
import codes.derive.foldem.util.RandomContext;

public class Range {

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
	 * 		The {@link Range} context, for chaining.
	 */
	public Range define(Hand hand) {
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
	 * 		The {@link Range} context, for chaining.
	 */
	public Range define(Hand... hands) {
		for (Hand hand : hands) {
			define(hand);
		}
		return this;
	}

	/**
	 * Defines a weighted hand in this range. TODO more weighting explanation
	 * 
	 * @param weight
	 *            The weight, as a decimal. This will define how often the
	 *            specified hand should appear in the range.
	 * @param hand
	 *            The hand.
	 * @return The {@link Range} context, for chaining.
	 */
	public Range define(double weight, Hand hand) {
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
	 * Defines the specified weighted hands in this range.
	 * 
	 * @param weight
	 *            The weight, as a decimal. This will define how often the
	 *            specified hands will appear in the range. TODO rephrase.
	 * @param hands
	 *            The hands.
	 * @return The {@link Range} context, for chaining.
	 */
	public Range define(double weight, Hand... hands) {
		for (Hand hand : hands) {
			define(weight, hand);
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
	 * @return The {@link Range} context, for chaining.
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

	public Hand sample(Random random) {
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
		return candidates.get(random.nextInt(candidates.size()));
	}
	
	public Hand sample() {
		return sample(RandomContext.get());
	}
	
	public Collection<Hand> all() {
		List<Hand> hands = new ArrayList<>();
		hands.addAll(constant);
		for (List<Hand> l : weighted.values()) {
			hands.addAll(l);
		}
		return Collections.unmodifiableCollection(hands);
	}
	
	@Override
	public String toString() {
		StringBuilder bldr = new StringBuilder().append(Range.class.getName());
		bldr.append("[ ");
		for (Hand hand : constant) {
			bldr.append(hand);
			bldr.append(",");
		}
		for (double weight : weighted.keySet()) {
			bldr.append(weighted.get(weight)).append(" ").append(weight);
			bldr.append(",");
		}
		bldr.deleteCharAt(bldr.length() - 1);
		return bldr.append("]").toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((constant == null) ? 0 : constant.hashCode());
		result = prime * result
				+ ((weighted == null) ? 0 : weighted.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Range other = (Range) obj;
		if (constant == null) {
			if (other.constant != null)
				return false;
		} else if (!constant.equals(other.constant))
			return false;
		if (weighted == null) {
			if (other.weighted != null)
				return false;
		} else if (!weighted.equals(other.weighted))
			return false;
		return true;
	}

}