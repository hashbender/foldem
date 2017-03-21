package codes.derive.foldem.board;

import java.util.Collection;

import codes.derive.foldem.Card;

public interface Board {

	public Collection<Card> cards();
	
	public Street getStreet();
	
}
