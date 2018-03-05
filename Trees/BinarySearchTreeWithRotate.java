package edu.miracosta.cs113;
/**
 * BinarySearchTreeWithRotate.java: 
 * 			This class is a binary search tree that has the ability to rotate
 * 
 * @author Michael McDermott <mmcdermott011@gmail.com>
 * @version 1.0
 */
public class BinarySearchTreeWithRotate<E extends Comparable<E>> extends BinaryTree<E> {
	//Data fields
	protected boolean addReturn;	
	protected E deleteReturn;
		
	/** Method to perform a right rotation
	 * pre: the root is the root of a binary search tree.
	 * post: root.right is the root of a binary search tree
	 * 		root.right.right is raised one level,
	 * 		root.right.left does not change levels,
	 * 		root.left is lowered one level,
	 * 		the new root is returned
	 * @param root the root of the binary tree to be rotated
	 * @return the new root of the rotated tree
	 */
	protected Node<E> rotateRight(Node<E> root) {
		Node<E> temp = root.left;
		root.left = temp.right;
		temp.right = root;
		return temp;
	}
	
	/** Method to perform a left rotation
	 * @param root the root of the binary tree to be rotated
	 * @return the new root of the rotated tree
	 */
	protected Node<E> rotateLeft(Node<E> root) {
		Node<E> temp = root.right;
		root.right = temp.left;
		temp.left = root;
		return temp;	
	}
		
	/** Starter method find.
	 * pre: the target object must implement the Comparable interface
	 * @param target The Comparable object being sought
	 * @return The object, if found, otherwise null
	 */
	public E find(E target) {
		return find(root, target);
	}
	
	/** Recursive find method
	 * @param localRoot the local subtrees root
	 * @param target the obnject being sought
	 * @return the object if found, otherwise null
	 */
	private E find (Node<E> localRoot, E target) {
		if(localRoot == null) {
			return null;
		}
		
		//Compare the target with the data field at the root
		int compResult = target.compareTo(localRoot.data);
		if(compResult == 0) {
			return localRoot.data;
		}
		else if(compResult < 0) {
			return find(localRoot.left, target);
		}
		else {
			return find(localRoot.right, target);
		}
	}
	
	/** Starter add method
	 * pre: the object to insert must implement the Comparable interface
	 * @param item the object being inserted
	 * @return true if the object is inserted, false if the object already exists in the tree
	 */
	public boolean add(E item) {
		root = add(root, item);
		return addReturn;
	}
	
	/** Recursive add method
	 * post: The data field addReturn is set true if the item is added to the tree,
	 * 		false if the item is already in the tree
	 * @param localRoot the local root of the subtree
	 * @param item The object to be inserted
	 * @return The new local root that now contains the inserted item
	 */
	private Node<E> add(Node<E> localRoot, E item) {
		if(localRoot == null) {
			// item is not in the tree - insert it
			addReturn = true;
			return new Node<E>(item);
		}
		else if(item.compareTo(localRoot.data) == 0) {
			// item is equal to localRoot.data
			addReturn  = false;
			return localRoot;
		}
		else if (item.compareTo(localRoot.data) < 0) {
			// item is less than localRoot.data
			localRoot.left = add(localRoot.left, item);
			return localRoot;
		}
		else {
			// item is greater than localRoot.data
			localRoot.right = add(localRoot.right, item);
			return localRoot;
		}
	}
	
	/** Starter delete method
	 * post: the object is not in the tree
	 * @param target the object to be deleted
	 * @return the object deleted from the tree
	 * 			or null if the object was not in the tree
	 * @throws ClassCastException if target does not implement Comparable
	 */
	public E delete(E target) {
		root = delete(root, target);
		return deleteReturn;
	}
	
	/** Recursive delete method
	 * post: the item is not in the tree;
	 * 		deleteReturn is equal to the deleted item
	 * 		as it was stored in the tree or null if the item was not found.
	 * @param localRoot the root of the current subtree
	 * @param item the item to be deleted
	 * @return the modified local root that does not contain the item
	 */
	private Node<E> delete(Node<E> localRoot, E item) {
		if(localRoot == null) {
			// item is not in the tree.
			deleteReturn = null;
			return localRoot;
		}	
		// Search for item to delete
		int compResult = item.compareTo(localRoot.data);
		if(compResult < 0) {
			// item is smaller than localRoot.data
			localRoot.left = delete(localRoot.left, item);
			return localRoot;
		}
		else if(compResult > 0) {
			// item is larger than localRoot.data
			localRoot.right = delete(localRoot.right, item);
			return localRoot;
		}
		else {
			// item is at the local root
			deleteReturn = localRoot.data;
			if(localRoot.left == null) {
				// if there is no left child, return the right child which can also be null
				return localRoot.right;
			}
			else if(localRoot.right == null) {
				// if theres no right child, return the left child
				return localRoot.left;
			}
			else {
				//Node being deleted has two children, replace the data with inorder predecessor
				if(localRoot.left.right == null) {
					// the left child has no right child
					//replace the data with the data in the left child
					localRoot.data = localRoot.left.data;
					// replace the left child with its left child
					localRoot.left = localRoot.left.left;
					return localRoot;
				}
				else {
					//Search for in order predecessor(IP) and replace deleted nodes data with IP
					localRoot.data = findLargestChild(localRoot.left);
					return localRoot;
				}
			}
		}
	}
	
	/** find the node that is the inorder predecessor and replace it with its left child(if any)
	 * post: The inorder predecessor is removed from the tree
	 * @param paren The parent of possible inorder predecessor(ip)
	 * @return The data in the ip
	 */
	private E findLargestChild(Node<E> parent) {
		//if the right child has no right child, it is the inorder predecessor(ip)
		if(parent.right.right == null) {
			E returnValue = parent.right.right.data;
			parent.right = parent.right.left;
			return returnValue;
		}
		else {
			return findLargestChild(parent.right);
		}
	}
}
