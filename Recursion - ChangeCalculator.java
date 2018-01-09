import java.util.*;
/**
 * ChangeCalculator.java: 
 * 			This class accepts a number from the user and interprets it as a dollar amount and prints out
 * 			all possible combinations of coin change using United States currency.
 * 			The purpose of this program is to use recursion to go through every possible
 * 			combination of coins until there are none left. 
 * Class Invariant: 
 * 			Assumption is made that the user will enter a non-negative number.
 * Main Algorithm:
 * 			PROMPT user to enter a number
 * 			INPUT number
 * 			CONVERT number to cents by multiplying by 100
 * 			ASSIGN number to amount variable
 * 			CALC combinations of change using calcChange
 * 			OUTPUT combinations of change
 * 
 * @author Michael McDermott <mmcdermott011@gmail.com>
 * @version 1.0
 */
public class ChangeCalculator {
	public static void main(String[] args) {
		final int PENNIES = 1;
		final int NICKELS = 5;
		final int DIMES = 10;
		final int QUARTERS = 25;
		int denominations [] =  {PENNIES, NICKELS, DIMES, QUARTERS};
		int amount;
		ArrayList<Integer> coinNum = new ArrayList<Integer>();
		coinNum.add(0); // puts a zero in for number of quarters used
		coinNum.add(0); // puts a zero in for number of dimes used
		coinNum.add(0); // puts a zero in for number of nickels used
		coinNum.add(0);	// puts a zero in for number of pennies used
		
		// OPTION TO TYPE YOUR OWN VALUE IN / HAVE A USER TYPE A VALUE IN
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter the amount of money you want the possible combinations of change for: (ex. 1.00) ");
			try{
				amount = (int)((Double.parseDouble(keyboard.nextLine())*100));
				if (amount < 0) {   
				     throw new IllegalArgumentException("Only a positive number can be entered"); 
				} 
			}
			catch(Exception ex) {
				System.out.println("Only a positive number can be entered. Please start over.");
				return;
			}
		    keyboard.close();

	    System.out.println("----- COMBINATIONS OF: " + amount + " cents -----\n");  
	    calcChange(amount, denominations.length-1, denominations, coinNum);	   
	
	    // HARD CODE USED FOR TESTING
	    /*
	   System.out.println(" -----------------------COMBINATIONS OF: 5 -----------------------\n");
	   calcChange(5, denominations.length-1, denominations, coinNum); 	  
	   System.out.println(" -----------------------COMBINATIONS OF: 10 -----------------------\n"); 
	   calcChange(10, denominations.length-1, denominations, coinNum);
	   System.out.println(" -----------------------COMBINATIONS OF: 13 -----------------------\n"); 
	   calcChange(15, denominations.length-1, denominations, coinNum);
	   System.out.println(" -----------------------COMBINATIONS OF: 27 -----------------------\n"); 
	   calcChange(27, denominations.length-1, denominations, coinNum);
	   System.out.println(" -----------------------COMBINATIONS OF: 75 -----------------------\n"); 
	   calcChange(75, denominations.length-1, denominations, coinNum);
	  */
	   
  } // end of main method
	
	/**Recursive method that takes in a cent amount along with a starting location,
	 * array of denominations, and list of coinNumbers that will start from the highest
	 * denomination and recursively call itself to calculate all possible combinations
	 * of change using that denomination and all lower denominations until there is no
	 * leftOver change to calculate and when it has reached the smallest denomination
	 * (the beginning index of the denomination array).
	 * 
	 * @param amnt		the change that is being calculated
	 * @param location	where the starting point is for denominations
	 * @param denom []	actual denominations of currency
	 * @param coinNum	list of the numbers of each coin to keep track of how many are used
	 * @return void
	 */
	static void calcChange(int amnt, int location, int[] denom, ArrayList<Integer> coinNum) {
		String coinNames[] = {"PENNIES", "NICKELS", "DIMES", "QUARTERS"};
		int mult = amnt/denom[location];
		int leftOver = amnt%denom[location];
		boolean canGoDown = (location>0);

		//each time the method is called, the multiple is calculated and the arraylist index is updated
		coinNum.set(location, mult);
	
		//BASE CASE - if you're at the bottom(pennies) and if there is no change left over to calculate
		if(leftOver == 0 && !(canGoDown)) {
			//always set the arraylist index
				coinNum.set(location, mult);
				//For loop to print out the rest of the arrayList that kept track of all the other denominations and their values
				for(int i = denom.length - 1; i>=0; i--) {
					System.out.println(coinNames[i]+ ": "+ coinNum.get(i));
				}
				//prints an extra line just to separate it from other arraylists that get printed out
				System.out.println();		
		}
		//else if we're not at the end of the denomination list or if the denomination has more than a multiple of zero
		else if (mult > 0) {
			//recurses and moves the location down a denomination to keep the original list going
			calcChange(leftOver,location-1,denom, coinNum);
			//the while loop decrements the multiple value and creates a new arraylist which is a deep copy of the original
			// then it will recurse using the leftover amount, moves down a location, and passes in the new arraylist
			while(mult >= 1 ) {	
				mult-=1;
				coinNum.set(location, mult);
				//since we're changing how many coins of the denomination we want to use, we need to update the leftOver amount
				leftOver = amnt-(mult*denom[location]);	
				ArrayList<Integer> newCoinNum = copyArray(coinNum);
				calcChange(leftOver,location-1,denom, newCoinNum);	
			}
		}	
		// if we haven't reached the base case, and the denomination we're on doesn't have any use for the change we're making
		else {
			// move down to the next denomination with the remaining leftover change to calculate.
			// this needs to be in an else statement because if it were outside, then the base case
			// would still recurse, making the index go out of bounds to -1
			calcChange(leftOver,location-1,denom, coinNum);	
		}
	} // end of calcChange method
	
	/**Helper method to create a copy of the list of coins used
	 * to keep possible combination lists separate from each other
	 * 
	 * @param source	the array of coins that needs to be copied
	 * @return void		no return
	 */
	private static ArrayList<Integer> copyArray(ArrayList<Integer> source) {
		ArrayList<Integer> newArray = new ArrayList<Integer>();
		for(int i = 0; i< source.size(); i++) {
			newArray.add(source.get(i));
		}	
		return newArray;
	} // end of copyArray
	
}// end of class


