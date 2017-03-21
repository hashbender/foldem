package codes.derive.foldem.board;

import codes.derive.foldem.Card;

public class GenericBoard extends AbstractBoard {

	private final Street street;
	
	public GenericBoard(Street street, Card... cards) {
		super(cards);
		this.street = street;
	}

	@Override
	public Street getStreet() {
		return street;
	}

}
