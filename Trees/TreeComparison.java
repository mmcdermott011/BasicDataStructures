package edu.miracosta.cs113;
/**
 * TreeComparison.java: 
 * 			This class is the driver that compares an unbalanced binary search tree with 
 * 			a balanced AVL tree.
 * 			The in order traversal is done by using the BinaryTree toString method which has 
 * 			been changed to visit the left, node, then right.
 * 			I used an array to add numbers, but also commented out a random generator that 
 * 			can add 15 random numbers to the trees. They both work, i just preferred using
 * 			the array so I could verify the changes I made and compare to something consistent.
 * 
 * ALGORITHM
 * 		ADD numbers to both binary tree and avl tree
 * 		Display binary tree using inorder traversal
 * 		Display avl tree using inorder traversal
 * 
 * @author Michael McDermott <mmcdermott011@gmail.com>
 * @version 1.0
 */
import java.util.*;
public class TreeComparison {

	public static void main(String[] args) {
		BinarySearchTreeWithRotate<Integer> binaryTree = new BinarySearchTreeWithRotate<Integer>();
		AVLTree<Integer> balancingTree = new AVLTree<Integer>();
		
		int[] intAr = new int[]{5,10,1,30,24,50,4, 1, 1, 2, 3};
				
	/*		Random random = new Random();
		for(int i = 0; i < 15; i++) {
			int randInt = random.nextInt(50);
			System.out.println(randInt);		
			balancingTree.add(randInt);
			binaryTree.add(randInt);
		}
		*/	
		System.out.println("order of numbers being added:");
		for(int i = 0; i < intAr.length; i++) {
			
			System.out.println(intAr[i]);
			balancingTree.add(intAr[i]);
			binaryTree.add(intAr[i]);
			
		}	
		
		System.out.println("Unbalanced binary search tree inorder traversal");
		
		System.out.println(binaryTree.toString());	
		
		System.out.println("balanced AVL tree");
		
		System.out.println(balancingTree.toString());
		
		
	}

}
