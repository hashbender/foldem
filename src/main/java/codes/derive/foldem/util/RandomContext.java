package codes.derive.foldem.util;

import java.util.Random;

// would rather just have a central set of static utilities for randomness
public class RandomContext {

	private static final Random instance = new Random();
	
	public static Random get() {
		return instance;
	}

}
