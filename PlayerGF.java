import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * The player object for playing go fish is defined here
 */

public class PlayerGF {
	
	// Fields
	private int role; //0=>computer, 1=>person
	private String name;
	private SLL<SLL<Card>> inHand;
	private SLL<Integer> book;
	private int numOfCard;
	
	
	// Constructor
	/**
	 * This method creates a new player
	 * @param name name of the player
	 * @param role system(0) vs. real person(1)
	 */
	public PlayerGF(String name, int role) {
		if (role!=0 & role!=1) {
			throw new IllegalArgumentException("What's the role???");
		}
		if (name==null) {
			name = "Anonymous";
		}
		this.role = role;
		this.name = name;
		this.book = new SLL<>();
		this.inHand = new SLL<>();
		this.numOfCard = 0;
	}
	
	
	// Methods	
	/**
	 * This method returns player's cards in hand
	 */
	public String toString() {
		String s = this.name+"'s cards: ";
		if (this.role==0) { //hidden cards
			for (int i=0; i<this.numOfCard; i++) {
				s += "__ ";
			}
		} else { //revealed cards
			s += this.inHand.toString();
		}
		
		s += "\n"+this.name+"'s books: "+this.book.toString();

		return s;
	}
	
	/**
	 * This method returns player's cards in hand for testing purpose only
	 */
	public String toString(int a) {
		String s = this.name+"'s cards: ";		
		s += this.inHand.toString();
		s += "\n"+this.name+"'s books: "+this.book.toString();
		return s;
	}
	
	/**
	 * This method returns the number of cards the player has
	 * @return number of cards
	 */
	public int getNumOfCard() {
		return this.numOfCard;
	}
	
	/**
	 * This method returns the full books of the player
	 * @return books of player
	 */
	public SLL<Integer> getBook() {
		return this.book;
	}
	
	/**
	 * This method returns the name of the player
	 * @return name of player
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * This method returns whether the player has cards in hand
	 * @return if player hand is empty
	 */
	public boolean haveNoCards() {
		return this.numOfCard==0;
	}
	
	/**
	 * This method allows the player to receive a card
	 * @param card new card received
	 */
	public void receiveCard(Card card) {
		if (card==null) {
			return;
		}
		
		this.numOfCard++;
		
		SLN<SLL<Card>> curr = this.inHand.head; //current series of cards
		boolean insert = false;
		while(!insert && curr!=null) {
			if(curr.data.head.data.value==card.value) { //if current series' value matches with new card, insert
				curr.data.insert(card);
				if(curr.data.size==4) { //check if a book is formed
					System.out.println("A book of "+curr.data.head.data.value+"s has formed!");
					this.inHand.remove(curr.data); //remove book
					this.book.insert(curr.data.head.data.value); //add to book field
					this.numOfCard -= 4;
				}
				insert = true;
			} else { //if doesn't match move on
				curr = curr.next;
			}
		}
		
		//if no matching value found, add to end of SLL
		if (!insert) {
			SLL<Card> newSLL = new SLL<>();
			newSLL.insert(card);
			this.inHand.insert(newSLL);
		}
	}

	/**
	 * This method allows the player to receive several cards of the same face value
	 * @param cards cards to be received
	 */
	public void receiveCard(SLL<Card> cards) {
		if (cards==null) {
			return;
		}
		
		SLN<Card> c = cards.head;
		
		while (c!=null) {
			this.receiveCard(c.data);
			c = c.next;
		}
		
	}
	
	/**
	 * This method allows the player to give out cards
	 * @param val rank of cards to give out
	 * @throws InterruptedException 
	 */
	public SLL<Card> giveCard(int val) throws InterruptedException {
		if (this.role==0) {
			SLL<Card> givenCards = sysGive(val);
			if (givenCards==null) {
				goFish();
			}
			return givenCards;
		} else {
			System.out.println(this.toString());
			TimeUnit.SECONDS.sleep(2);
			Scanner console = new Scanner(System.in);
			System.out.print("Please enter the number of cards you want to give; enter 0, if you have none: ");
			int vals = console.nextInt();
			
			SLL<Card> givenCard = sysGive(val);
			
			if (givenCard==null) {
				while(vals!=0) {
					System.out.print("Hint: you have nothing to give! Please enter again: ");
					vals = console.nextInt();
				}
				goFish();
				return null;
			}
			
			while(givenCard.size!=vals) {
				System.out.print("Are you sure? Please be honest! For example, if you want to give three 9s, enter 3: ");
				vals = console.nextInt();
			}
			
			return givenCard;
		}	
	}
	
	/**
	 * This method allows the system to give out cards
	 * @param val face value of cards to give out
	 * @return given cards
	 */
	public SLL<Card> sysGive(int val){
		SLN<SLL<Card>> curr = this.inHand.head;
		while (curr!=null) {
			if (curr.data.head.data.value==val) {
				this.numOfCard -= curr.data.size;
				return this.inHand.remove(curr.data).data;
			} else {
				curr = curr.next;
			}
		}
		return null;
	}
	
	/**
	 * This method allows the player to ask for a value
	 * @param next the player been asked
	 * @return value the user asked
	 * @throws InterruptedException 
	 */
	public int ask() throws InterruptedException {
		System.out.println(this.toString());
		TimeUnit.SECONDS.sleep(2);
		if(this.role==0) {
			return systemAsk();
		} else {
			Scanner console = new Scanner(System.in);
			System.out.print("What value would you like to ask for? ");
			int val = console.nextInt();
			while (!isAvailable(val)) {
				System.out.print("Please ask for a value you already have in hand: ");
				val = console.nextInt();
			}
			return val;
		}
	}
	
	/**
	 * This method allows the system to generate a number to ask for with 
	 * some certain strategies
	 * @return value asked for
	 */
	private int systemAsk() {
		//70% chance of asking for ranks that the player already have 3 of
		//20% chance of asking for ranks that the player has 2 of
		//10% chance of asking for ranks that the player has 1 of
		SLL<Integer> one = new SLL<>();
		SLL<Integer> two = new SLL<>();
		SLL<Integer> three = new SLL<>();
		SLN<SLL<Card>> curr = this.inHand.head;
		while (curr!=null) {
			if (curr.data.size==1) {
				one.insert(curr.data.head.data.value);
			} else if (curr.data.size==2) {
				two.insert(curr.data.head.data.value);
			} else {
				three.insert(curr.data.head.data.value);
			}
			curr = curr.next;
		}
		double ind1 = Math.random();
		if (ind1<0.7 & three.size>0) { //choose from three
			int ind2 = (int)(Math.random()*three.size);
			return three.remove(ind2).data;
		} else if (ind1<0.9 & two.size>0) { //choose from two
			int ind2 = (int)(Math.random()*two.size);
			return two.remove(ind2).data;
		} else { //choose from one
			int ind2 = (int)(Math.random()*one.size);
			return one.remove(ind2).data;
		}
	}

	/**
	 * This method determines whether a request made is valid or a request can be met
	 * @param val rank asked
	 * @return if request is valid or can be met
	 */
	public boolean isAvailable(int val) {
		SLN<SLL<Card>> curr = this.inHand.head;
		while(curr!=null) {
			if(val==curr.data.head.data.value) {
				return true;
			} else {
				curr = curr.next;
			}
		}
		return false;
	}
	
	/**
	 * This method allows the player to respond by saying Go Fish!
	 */
	public void goFish() {
		System.out.println(this.name+" says Go Fish!!!");
	}

	/**
	 * This method returns the identity of the player
	 * @return role of player
	 */
	public int getRole() {
		return this.role;
	}
	
}
