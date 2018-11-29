import java.util.Scanner;

public class console {

	public static void main(String[] args) throws InterruptedException {
		
		boolean play = true; //keep playing?
		
		while (play) {
			GoFish game = new GoFish();
			game.gameLoop();
			
			System.out.println("\n\n\nDo you want to play another round? (y/n): ");
			Scanner c = new Scanner(System.in);
			String ans = c.next();
			while(!ans.equals("y") & !ans.equals("n")) {
				System.out.println("Please enter y or n: ");
				ans = c.next();
			}
			c.close();
			
			play = ans.equals("y");
			
			System.out.println("\n\n\n");
		}

		System.out.println("Bye!");
	}
	
	
	
	
	
	
	
	
	
}
