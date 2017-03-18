package codes.derive.foldem.util;

import java.util.Random;

// would rather just have a central set of static utilities for randomness
// TODO consider renaming to RandomContext and having RandomContext.get()
public class Probability {

	public static final Random RNG = new Random();
	
	public static int random(int bound) {
		return RNG.nextInt(bound);
	}
	
	public static double random() {
		return RNG.nextDouble();
	}
	
}
