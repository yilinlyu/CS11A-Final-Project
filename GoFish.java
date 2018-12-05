import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Game basics of Go Fish is outlined here
 */

public class GoFish extends CardSuitsValues {
	
	public static final int NUMOFCARDS = 5;
	public static final String[] NAMES = {"Bill","Becky","Bella","Ben"};
	
	// Fields
	private SLL<PlayerGF> players;
	private SLL<Card> pool;
	
	
	// Constructor
	/**
	 * This method starts a new game
	 * @throws InterruptedException 
	 */
	public GoFish() throws InterruptedException {
		introduction();
		this.players = setPlayers();
		TimeUnit.SECONDS.sleep(2);
		System.out.println("The players are all set!\n");
		TimeUnit.SECONDS.sleep(2);
		System.out.println("Shuffling and dealing cards...\n");
		TimeUnit.SECONDS.sleep(2);
		this.pool = setCards(generateDeck());
		deal();
		System.out.println("The cards are all set! Now each of you have 5 cards in hand...\n");
		TimeUnit.SECONDS.sleep(2);
		System.out.println("Now let's play!\n");
		TimeUnit.SECONDS.sleep(2);
	}
	
	
	// Methods
	/**
	 * This methods prints out info about the game
	 */
	public void introduction() {
		Scanner console = new Scanner(System.in);
		System.out.println("Hi, welcome to the Go Fish game!\n");
		System.out.print("If you are unfamiliar with the game, you can choose to read the game rules. \nDo you want to skip game rules? (y/n): ");
		String skipRule = console.next();
		while (!skipRule.equals("y") & !skipRule.equals("n")) {
			System.out.print("Please enter y or n: ");
			skipRule = console.next();
		}
		System.out.println();
		if (skipRule.equals("n")) {
			System.out.println("The game is played with a standard 52-card deck, and you can play with 1-4 other players.");
			System.out.println("At the begining of the game, each player gets 5 cards, and the rest of the cards are refered to as the pool.");
			System.out.println("The player whose turn it is asks the next player for a particular face value. To ask, the player must have \nat least one card of the same value.");
			System.out.println("The next player must hand over all of his/her cards of the same face value, or say \"Go Fish!\" if there's none, \nand the current player draws a card from the pool.");
			System.out.println("In either case, if the player gets what he/she asked, regardless from another player or the pool, he/she \ngets another turn.");
			System.out.println("At any point of the game, if a player gets all four cards of the same face value, a book is formed and removed \nfrom the player's hands.");
			System.out.println("The game ends when all cards have formed complete books, and the person with the most books win!\n");
			System.out.println("PS. J, Q, and K are sometimes displayed as 11, 12, and 13 repectively in this game, and when prompted \nto enter card value, you should also enter numbers only!\n");
		}
	}
	
	/**
	 * This method sets up the players
	 * @return all the players in a circular SLL
	 */
	public SLL<PlayerGF> setPlayers() {
		System.out.println("Now let's set up for the game...\n");
		SLL<PlayerGF> players = new SLL<>();
		Scanner console = new Scanner(System.in);
		System.out.print("How many players are there including you? ");
		int numOfPlayers = console.nextInt();
		while (numOfPlayers<2 || numOfPlayers>5) {
			System.out.print("Please enter a integer between 2 and 5 inclusive: ");
			numOfPlayers = console.nextInt();
		}
		System.out.print("Your name: ");
		String name = console.next();
		players.insert(new PlayerGF(name, 1));
		for (int i=1; i<numOfPlayers; i++) {
			players.insert(new PlayerGF(NAMES[i-1], 0));
			System.out.println();
		}
		players.tail.next = players.head;
		return players;
	}
	
	/**
	 * This method shuffles a given array of cards
	 * @param cards array of cards to be shuffled
	 */
	public void shuffle(Card[] cards) {
		for (int i=0; i<cards.length; i++) {
			int randInd = (int)(Math.random()*cards.length);
			Card temp = cards[i];
			cards[i] = cards[randInd];
			cards[randInd] = temp;
		}
	}
	
	/**
	 * This method generates a complete deck of sorted cards
	 * @return an array of 52 cards, sorted
	 */
	public Card[] generateDeck() {
		Card[] cards = new Card[52];
		for (int i=1; i<=13; i++) {
			Card c1 = new Card(this.h, i);
			Card c2 = new Card(this.c, i);
			Card c3 = new Card(this.d, i);
			Card c4 = new Card(this.s, i);
			cards[(i-1)*4+0] = c1;
			cards[(i-1)*4+1] = c2;
			cards[(i-1)*4+2] = c3;
			cards[(i-1)*4+3] = c4;
		}
		return cards;
	}
	
	/**
	 * This method takes an array of cards, shuffle them, and put them in a SLL
	 * @param initial array of cards
	 * @return shuffled cards in a SLL
	 */
	public SLL<Card> setCards(Card[] cards) {
		shuffle(cards);
		SLL<Card> crds = new SLL<>();
		for (int i=0; i<cards.length; i++) {
			crds.insert(cards[i]);
		}
		return crds;
	}
	
	/**
	 * This method deals cards
	 */
	public void deal() {
		SLN<PlayerGF> curr = this.players.head;
		for (int i=0; i<NUMOFCARDS; i++) {
			for (int j=0; j<this.players.size; j++) {
				Card c = this.pool.remove().data;
				curr.data.receiveCard(c);
				curr = curr.next;
			}
		}
	}
	
	
	
	/**
	 * This method returns the last card in the pool
	 * @return card drawn
	 */
	public Card drawCard() {
		if (this.pool.size>0) {
			return this.pool.remove().data;
		} else {
			return null;
		}
	}
	
	/**
	 * This method returns whether the game has ended
	 * @return true if game finished, false if still in process
	 */
	public boolean isEnd() {
		SLN<PlayerGF> p = this.players.head;
		for (int i=0; i<this.players.size; i++) {
			if (!p.data.haveNoCards()) {
				return false;
			} else {
				p = p.next;
			}
		}
		return true;
	}
	
	/**
	 * This method completes a turn
	 * @param currP the player asking
	 * @param nextP the player been asked
	 * @throws InterruptedException 
	 */
	public void turn(PlayerGF currP, PlayerGF nextP) throws InterruptedException {
		if (!currP.haveNoCards()) {
			if (currP.getRole()==1) {
				System.out.println(nextP.toString());
				TimeUnit.SECONDS.sleep(2);
			}
			int askedVal = currP.ask();
			System.out.println(currP.getName()+" asked for "+askedVal+"...");
			TimeUnit.SECONDS.sleep(2);
			SLL<Card> givenCard = nextP.giveCard(askedVal);
			TimeUnit.SECONDS.sleep(2);
			if(givenCard!=null) {
				System.out.println(nextP.getName()+" gave "+currP.getName()+" "+givenCard.size+" card(s)...");
				TimeUnit.SECONDS.sleep(2);
				currP.receiveCard(givenCard);
				System.out.println(currP.getName()+" gets another turn!\n");
				TimeUnit.SECONDS.sleep(2);
				if (!isEnd()) {
					turn(currP, nextP);
				}
			} else {
				if (this.pool.size==0) {
					System.out.println("No cards left in the pool! Just keep playing...");
					TimeUnit.SECONDS.sleep(2);
				}
				System.out.println(currP.getName()+" drew a card from the pool...");
					TimeUnit.SECONDS.sleep(2);
				Card drawnCard = drawCard();
				currP.receiveCard(drawnCard);
				if (drawnCard.value==askedVal) {	
					System.out.println("It's the same as what "+currP.getName()+" asked! So he/she gets another turn...\n");
					TimeUnit.SECONDS.sleep(2);
					if (!isEnd()) {
						turn(currP, nextP);
					}
				} else {
					System.out.println();
					TimeUnit.SECONDS.sleep(2);
				}
			}		
		} else {
			System.out.println(currP.getName()+" has no cards in hand, he/she gets all "+this.pool.size+" cards left in the pool...");
			TimeUnit.SECONDS.sleep(2);
			currP.receiveCard(this.pool);
			this.pool = new SLL<>();
			if (!isEnd()) {
				turn(currP, nextP);
			}
		}
	}
	
	/**
	 * This method allows a complete game
	 * @throws InterruptedException
	 */
	public void gameLoop() throws InterruptedException {
		SLN<PlayerGF> curr = this.players.head;
		SLN<PlayerGF> next = curr.next;
		while (!isEnd()) {
			turn(curr.data, next.data);
			curr = curr.next;
			next = next.next;
		}
		announceWinner();
	}
	
	
	public void announceWinner() throws InterruptedException {
		System.out.println("Now we have a winner!");
		TimeUnit.SECONDS.sleep(2);
		SLN<PlayerGF> curr = this.players.head;
		PlayerGF winner = curr.data;
		int books = winner.getBook().size;
		for (int i=1; i<this.players.size; i++) {
			if (curr.next.data.getBook().size>books) {
				winner = curr.next.data;
			}
			curr = curr.next;
		}
		System.out.println("Congrats to "+winner.getName()+"!!!");
	}
	
	/**
	 * This method reveals game status for testing only
	 */
	public void gameStat() {
		System.out.println(this.pool.toString());
		
		SLN<PlayerGF> p = this.players.head;
		for (int i=0; i<this.players.size; i++) {
			System.out.println(p.data.toString(1));
			p = p.next;
		}
	}
}
