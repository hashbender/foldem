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
package codes.derive.foldem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import codes.derive.foldem.util.RandomContext;

/**
 * A type representing a range of hands.
 */
public class Range {

	/*
	 * A map containing weighted hands in lists mapped to their respective
	 * weights.
	 */
	private final Map<Double, List<Hand>> weighted = new HashMap<>();

	/* A list containing hands that always appear within this group. */
	private final List<Hand> constant = new ArrayList<>();

	/**
	 * Defines a {@link Hand} in this {@link Range}.
	 * 
	 * @param hand
	 *            The hand.
	 * @return The {@link Range} context, for chaining.
	 */
	public Range define(Hand hand) {
		if (contains(hand)) {
			throw new IllegalArgumentException("Hand already exists within range");
		}
		constant.add(hand);
		return this;
	}

	/**
	 * Defines a series of hands in this {@link Range}.
	 * 
	 * @param hands
	 *            The hands.
	 * @return The {@link Range} context, for chaining.
	 */
	public Range define(Hand... hands) {
		for (Hand hand : hands) {
			define(hand);
		}
		return this;
	}

	/**
	 * Defines a {@link java.util.Collection } of hands in this {@link Range}.
	 * 
	 * @param hands
	 *            The hands
	 * @return The {@link Range} context, for chaining.
	 */
	public Range define(Collection<Hand> hands) {
		return define(hands.toArray(new Hand[0]));
	}

	/**
	 * Defines a weighted {@link Hand} in this range.
	 * 
	 * <p>
	 * The weight of a {@link Hand} defines how often it should be used instead
	 * of skipped by {@link Range#sample(Random)}. A {@link Hand} with a weight
	 * of 1.0 will always be used, 0.5 half the time, 0.0 never.
	 * </p>
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
	 * <p>
	 * The weight of a {@link Hand} defines how often it should be used instead
	 * of skipped by {@link Range#sample(Random)}. A {@link Hand} with a weight
	 * of 1.0 will always be used, 0.5 half the time, 0.0 never.
	 * </p>
	 * 
	 * @param weight
	 *            The weight, as a decimal. This will define how often the
	 *            specified hands will appear in the range on a call to
	 *            {@link Range#sample()}.
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
	 * Defines the specified weighted hands in this range.
	 * 
	 * <p>
	 * The weight of a {@link Hand} defines how often it should be used instead
	 * of skipped by {@link Range#sample(Random)}. A {@link Hand} with a weight
	 * of 1.0 will always be used, 0.5 half the time, 0.0 never.
	 * </p>
	 * 
	 * @param weight
	 *            The weight, as a decimal. This will define how often the
	 *            specified hands will appear in the range.
	 * @param hands
	 *            The hands.
	 * @return The {@link Range} context, for chaining.
	 */
	public Range define(double weight, Collection<Hand> hands) {
		return define(weight, hands.toArray(new Hand[0]));
	}
	
	/**
	 * Obtains whether or not the specified {@link Hand} can appear
	 * within this range.
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
	 * Obtains the frequency at which the specified {@link Hand} will
	 * appear within this {@link Range}, as a decimal.
	 * 
	 * @param hand
	 *            The hand.
	 * @return The frequency at which the specified {@link Hand} will
	 *         appear within this {@link Range}, as a decimal.
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
	 * Obtains a {@link Hand} from this {@link Range}, excluding weighted hands
	 * at their associated frequencies.
	 * 
	 * @param random
	 *            The random context to use to generate random numbers for
	 *            deciding whether or not to include a specific weighted hand.
	 * @return The sampled {@link Hand}.
	 */
	public Hand sample(Random random) {
		if (constant.size() == 0) {
			throw new IllegalStateException(
					"There needs to be at least one constant hand");
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

	/**
	 * Obtains a {@link Hand} from this {@link Range}, excluding
	 * weighted hands at their correct frequencies.
	 * 
	 * <p>
	 * uses the random number generator specified in {@link RandomContext}.
	 * </p>
	 * 
	 * @return The sampled {@link Hand}.
	 */
	public Hand sample() {
		return sample(RandomContext.get());
	}

	/**
	 * Obtains an unmodifiable view containing all hands within this
	 * {@link Range} including weighted hands.
	 * 
	 * @return An unmodifiable view containing all hands within this
	 *         {@link Range}.
	 */
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
		} else if (!constant.containsAll(other.constant))
			return false;
		if (weighted == null) {
			if (other.weighted != null)
				return false;
		} else if (!weighted.equals(other.weighted))
			return false;
		return true;
	}

}