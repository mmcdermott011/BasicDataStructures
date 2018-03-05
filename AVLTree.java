package edu.miracosta.cs113;
/**
 * AVLTree.java: 
 * 			This class is an AVL tree that inherits the binary search tree with rotate.
 * 			overrides the add method and adds the rebalanceLeft and rebalanceRight methods
 * 
 * @author Michael McDermott <mmcdermott011@gmail.com>
 * @version 1.0
 */
public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
	//Data fields
	private boolean increase;

	
	/** add starter method.
	 * pre: the item to insert implements the Comparable interface
	 * @param item The item being inserted
	 * @return true if the object is inserted; false if the object already exists in the tree
	 * @throws ClassCastException if item is not Comparable
	 */	
	@Override
	public boolean add(E item) {
		increase = false;
		root = add((AVLNode<E>) root, item);
		return addReturn;
	}
	
	/** Recursive add method. Inserts the given object
	 * post: AddReturn is set to true if the item gets inserted
	 * @param localRoot the local root of the object
	 * @param item the object to be inserted
	 * @return the new local root of the subtree of the inserted item
	 */
	private AVLNode<E> add(AVLNode<E> localRoot, E item) {
		if (localRoot == null) {
			addReturn = true;
			increase = true;
			return new AVLNode<E>(item);
		}
		if (item.compareTo(localRoot.data) == 0) {
			//Item is already in the tree
			increase = false;
			addReturn = false;
			return localRoot;
		}
		else if(item.compareTo(localRoot.data) < 0) {
			//item < data
			localRoot.left = add((AVLNode<E>) localRoot.left, item);
		
			if(increase) {
				decrementBalance(localRoot);
				if(localRoot.balance < AVLNode.LEFT_HEAVY) {
					increase = false;
					System.out.println("rebalancing left...");
					return rebalanceLeft(localRoot);
				}
			}
		}
		else if(item.compareTo(localRoot.data) > 0) {
			//item > data
			localRoot.right = add((AVLNode<E>) localRoot.right, item);
			
			if(increase) {
				incrementBalance(localRoot);
				if(localRoot.balance > AVLNode.RIGHT_HEAVY) {
					increase = false;
					System.out.println("rebalancing right...");
					return rebalanceRight(localRoot);
				}
			}
			
		}
		return localRoot; // Rebalance not needed
	}
	
	private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
		//obtainReference to left child.
		AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
		// see whether left-right heavy
		if(leftChild.balance > AVLNode.BALANCED) {
			// obtain reference to left-right child
			AVLNode<E>leftRightChild = (AVLNode<E>) leftChild.right;
			// adjust the balances to be their new values after the rotations are done
			if(leftRightChild.balance < AVLNode.BALANCED) {
				leftChild.balance = AVLNode.BALANCED;
				leftRightChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.RIGHT_HEAVY;
			}
			else {
				leftChild.balance = AVLNode.LEFT_HEAVY;
				leftRightChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.BALANCED;
			}
			//Perform left rotation
			localRoot.left = rotateLeft(leftChild);
		}
		else { // left left case
			leftChild.balance = AVLNode.BALANCED;
			localRoot.balance = AVLNode.BALANCED;
		}
		return (AVLNode<E>) rotateRight(localRoot);
	}
	
	
	private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
		// obtain reference to right child
		AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
		// see whether right-left heavy
		if(rightChild.balance < AVLNode.BALANCED) {
			// obtain reference to the right right child
			AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
			// adjust the balances to be their new values after the rotations are done
			if(rightLeftChild.balance < AVLNode.BALANCED) {
				rightChild.balance = AVLNode.BALANCED;
				rightLeftChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.LEFT_HEAVY;
			}
			else {
				rightChild.balance = AVLNode.RIGHT_HEAVY;
				rightLeftChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.BALANCED;
			}
			//perform right rotation
			localRoot.right = rotateRight(rightChild);
		}
		else { // right right case
			rightChild.balance = AVLNode.BALANCED;
			localRoot.balance = AVLNode.BALANCED;
		}
		return (AVLNode<E>) rotateLeft(localRoot);
	}
	
	private void decrementBalance(AVLNode<E> node) {
		// decrement the balance
		node.balance--;
		if(node.balance == AVLNode.BALANCED) {
			increase = false;
		}
	}
	
	private void incrementBalance(AVLNode<E> node) {
		// increment the balance
		node.balance++;
		if(node.balance == AVLNode.BALANCED) {
			increase = false;
		}
	}
	
	/** Class to represent AVL Node. It extends the BinaryTree.Node by adding the balance field */
	private static class AVLNode<E> extends Node<E> {
		
		public static final int LEFT_HEAVY = -1;
		public static final int RIGHT_HEAVY = 1;
		public static final  int BALANCED = 0;
		private int balance;
		
		/**Construct a default node
		 * @param item the data in the field
		 */
		public AVLNode(E item) {
			super(item);
			balance = BALANCED;
		}
		
		/** Return a string representation of the object
		 * @return String representation
		 */
		@Override 
		public String toString() {
			return balance + ": " + super.toString();
		}		
	}
}
