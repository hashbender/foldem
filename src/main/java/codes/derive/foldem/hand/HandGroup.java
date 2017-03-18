package codes.derive.foldem.hand;

import java.util.Collection;

public interface HandGroup {

	public boolean match(Hand h);
	
	public Hand get();
	
	public Collection<Hand> all();
	
}
