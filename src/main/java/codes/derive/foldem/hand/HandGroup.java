package codes.derive.foldem.hand;

import java.util.Collection;

/**
 * Represents a group of hands.
 */
public interface HandGroup {

	/**
	 * TODO rename or come up with a good explanation for terrible name
	 * @param h
	 * @return
	 */
	public boolean match(Hand h);
	
	/**
	 * Obtains a hand from the hand group, the output of this method should vary
	 * based on the type of group being represented.
	 * 
	 * @return A hand from the hand group.
	 */
	public Hand get();
	
	/**
	 * Obtains an unmodifiable view of all hands within this hand group.
	 * 
	 * @return An unmodifiable view of all hands within this hand group.
	 */
	public Collection<Hand> all();
	
}
