package codes.derive.foldem.hand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codes.derive.foldem.util.Probability;

public class HandRange implements HandGroup {

	private final Map<Double, List<Hand>> weighted = new HashMap<>();
	
	private final List<Hand> constant = new ArrayList<>();
	
	public HandRange() { }
	
	public void define(Hand hand) {
		if (contains(hand)) {
			throw new IllegalArgumentException("Hand already exists within range");
		}
		constant.add(hand);
	}
	
	public boolean define(HandGroup group) {
		if (!contains(group)) {
			for (Hand hand : group.all()) {
				if (contains(hand)) {
					throw new IllegalArgumentException("Range contains one hand from the group but not all");
				}
			}
			return constant.addAll(group.all());
		}
		return false;
	}
	
	public boolean define(Hand hand, double weight) {
		if (weight <= 0.0 || weight > 1.0) {
			throw new IllegalArgumentException("Weight out of bounds");
		}
		for (List<Hand> hands : weighted.values()) {
			if (hands.contains(hand)) {
				if (!hands.remove(hand)) {
					return false;
				}
			}
		}
		
		if (!weighted.containsKey(weight)) {
			weighted.put(weight, new ArrayList<Hand>());
		}
		List<Hand> hands = weighted.get(weight);
		if (!hands.contains(hand)) {
			return hands.add(hand);
		}
		return false;
	}

	public boolean define(HandGroup group, double weight) {
		if (!contains(group)) {
			for (Hand hand : group.all()) {
				if (contains(hand)) {
					throw new IllegalArgumentException("Range contains one hand from the group but not all");
				}
			}
		}
		for (Hand h : group.all()) {
			if (!define(h, weight)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean contains(Hand hand) {
		for (List<Hand> hands : weighted.values()) {
			if (hands.contains(hand)) {
				return true;
			}
		}
		return constant.contains(hand);
	}
	
	
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

	public double weight(Hand hand) {
		for (double weight : weighted.keySet()) {
			if (weighted.get(weight).contains(hand)) {
				return weight;
			}
		}
		return constant.contains(hand) ? 1.0 : 0;
	}
	
	public double weight(HandGroup group) {
		return weight(group.get());
	}
	
	@Override
	public boolean match(Hand hand) {
		if (constant.contains(hand)) {
			return true;
		}
		for (double weight : weighted.keySet()) {
			if (weighted.get(weight).contains(hand) && Probability.random() <= weight) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Hand get() {
		if (constant.size() == 0) {
			throw new IllegalStateException("There needs to be at least one constant hand to take a random hand from the range");
		} // FIXME not really
		
		/*
		 * Create a list of candidate hands containing hands with constant even weight.
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
		return candidates.get(Probability.random(candidates.size()));
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
