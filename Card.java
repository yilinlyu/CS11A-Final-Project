/**
 * The card object is defined in this file
 */

public class Card extends CardSuitsValues{
	
	
	// Fields
	public String suit;
	public int value;
	
	
	// Constructor
	/**
	 * This method constructs a new card. 
	 * @param suit suit of the card ("hearts", "clubs", "spades", "diamonds")
	 * @param value value of the card (1:13)
	 */
	public Card(String suit, int value) {
		if (value<1 || value>13) {
			throw new IllegalArgumentException("Illegal value");
		}
		if (!suit.equals(this.h) & !suit.equals(this.c) & !suit.equals(this.s) & !suit.equals(this.d)) {
			throw new IllegalArgumentException("Illegal suit");
		}
		this.suit = suit;
		this.value = value;
	}
	
	
	// Methods
	/**
	 * This method returns a String representation of the card.
	 */
	public String toString() {
		char symbol = ' ';
		switch(this.suit) {
		case "hearts":
			symbol = (char)this.hearts;
			break;
		case "clubs":
			symbol = (char)this.clubs;
			break;
		case "spades":
			symbol = (char)this.spades;
			break;
		case "diamonds":
			symbol = (char)this.diamonds;
			break;
		}
		
		String val = "";
		switch(this.value) {
		case 1:
			val = "A";
			break;
		case 11:
			val = "J";
			break;
		case 12:
			val = "Q";
			break;
		case 13:
			val = "K";
			break;
		default:
			val = ""+this.value;
			break;
		}
		
		return symbol+val;
	}
	

}
