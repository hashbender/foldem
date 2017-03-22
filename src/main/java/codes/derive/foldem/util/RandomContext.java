package codes.derive.foldem.util;

import java.util.Random;

/**
 * Contains a singleton {@link java.util.Random} context used internally by
 * Fold'em. The context is initialized using the default seeding and does not
 * change.
 */
public class RandomContext {

	/* The singleton Random instance. */
	private static final Random instance = new Random();
	
	/**
	 * Obtains the singleton {@link java.util.Random} context.
	 * @return
	 * 		The singleton {@link java.util.Random} context.
	 */
	public static Random get() {
		return instance;
	}

}
