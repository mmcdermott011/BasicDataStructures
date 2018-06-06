package edu.miracosta.cs113;
/**
 * Class HashTabletest.java is a driver for the HashtableChain.java class.
 * Invariant: user input for keys is not checked for non-negative integers.
 *
 * ALGORITHM
 * 	WHILE exit is false
 * 		OUTPUT user menu
 * 		INPUT user choice
 * 		SWITCH
 * 			'A' PROMPT user to enter a key
 * 				PROMPT user to enter a value
 * 				PUT key and value in hash table
 * 			'B' PROMOPT user to enter key for removal
 * 				IF key does not exist
 * 					OUTPUT the key does not exist
 * 				ELSE
 * 					REMOVE the key and value from hash table
 * 			'C' OUTPUT hashtable with toString 
 * 			'D' PROMPT user to enter the key they are searching for
 * 				GET the value for the key from the hashtable
 * 			'E' CHANGE exit to TRUE
 * 				 Exit the while loop 
 * 
 * @author Michael McDermott <mmcdermott011@gmail.com>
 */
import java.util.Scanner;
public class HashTabletest {

	public static void main(String[] args) {
		HashtableChain<Integer,String> table = new HashtableChain<Integer,String>();
		char choice;
		String value;
		int key = 0;
		boolean exit = false;
		
		table.put(1,"DEFAULT");
	
		Scanner scan = new Scanner(System.in);
		while(exit != true) {
		scan.reset();
		boolean validInput = false;
		System.out.println("------HashTableChain Tester------");
		System.out.println("A) Add a key / value ");
		System.out.println("B) Remove a key / value ");
		System.out.println("C) Display set of keys and values");
		System.out.println("D) Search ");
		System.out.println("E) Exit ");
		System.out.println();
		
		choice = scan.next().toUpperCase().charAt(0);
		
		switch(choice) {
			case 'A': {
				while(!validInput) {
					System.out.println("Enter a Key with non-negative integers only");
					try {
						key = scan.nextInt();
						if(key >= 0) {
							validInput = true;
						}
						scan.nextLine();
					}
					catch(Exception e) {
						System.out.println("Error: Input required non-negative integer");
						System.exit(0);
					}
				}
				System.out.println("Enter the value you want to store in any format.");
				value = scan.nextLine();
				table.put(key, value);
				System.out.println( value + " was added to the table.");
				System.out.println();
				break;
			}
			case 'B': {
				validInput = true;
				while(validInput) {
					System.out.println("Enter the non-negative Integer key for the item you want to remove.");
					try {
						key = scan.nextInt();
						if(key >= 0) {
							validInput = false;
						}
						scan.nextLine();
					}
					catch(Exception e) {
						System.out.println("Error: Input required non-negative integer");
						System.exit(0);
					}
				}
				value = table.remove(key);
				if(value == null) {
					System.out.println("The value for " + key + " was not found or removed.");
				}
				else {
					System.out.println( value + " was removed from the table.");
				}
				System.out.println();
				break;
			}
			case 'C': {
				System.out.println("------HASHTABLE_CHAIN------");
				System.out.println("size: " + table.size()+ "\n");
				System.out.println(table.toString());
				System.out.println();
				break;
			}
			case 'D': {
				validInput = false;
				while(!validInput) {
					System.out.println("Enter the non-negative Integer key for the item you want to find");
					try {
						key = scan.nextInt();
						if(key >= 0) {
							validInput = true;
						}
						scan.nextLine();
					}
					catch(Exception e) {
						System.out.println("Error: Input required non-negative integer");
						System.exit(0);
					}
				}
				System.out.println(table.get(key));
				System.out.println();
				break;
			}
			case 'E': {
				exit = true;
				break;
			}
		} //  end of switch	
		}// end of while loop
		scan.close();
	}// end of main method

	
}// end of HasTabletest class
