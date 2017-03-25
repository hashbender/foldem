package codes.derive.foldem.hand;

import static codes.derive.foldem.Foldem.*;
import static codes.derive.foldem.Card.*;
import static codes.derive.foldem.Suit.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestHand {
	
	@Test
	public void testSuited() {
		assertFalse(hand("AcAh").suited());
		assertTrue(hand("Ac2c").suited());
	}
	
	@Test
	public void testShorthand() {
		assertEquals(hand(card(ACE, SPADES), card(ACE, CLUBS)), hand("AsAc"));
	}

}
