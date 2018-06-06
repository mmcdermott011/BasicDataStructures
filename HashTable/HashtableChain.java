package edu.miracosta.cs113;
/**
 * HashtableChain.java
 * 		This class implements the KWHashMap interface as described in the book and
 * 		instead of making the hashtable an open table, it uses the chain method by 
 * 		having a linked list as a member variable. 
 * @author Michael McDermott <mmcdermott011@gmail.com>
 */
import java.util.*;
public class HashtableChain<K,V> extends AbstractMap<K,V> implements KWHashMap<K,V> {
	/** the table */
	private LinkedList<Entry<K,V>>[] table;
	/** the number of keys in the table */
	private int numKeys;
	
	/** start capacity of the list */
	private static final int CAPACITY = 101;
	/** load threshhold so it knows when to expand */
	private static final double LOAD_THRESH = 3.0;
	
	public HashtableChain() {
		table = new LinkedList[CAPACITY];
	}
	
	/**returns the size of the list based on the number of keys, not the
	 * number of array indices. Number of keys should be the same number of entries.
	 * @return numKeys the number of keys
	 */
	public int size() {
		return numKeys ;
	}
		
	public java.util.Set<Map.Entry<K, V>> entrySet() { 
		return new EntrySet();
	}
	
	/** Method put for class HashtableChain.
	 * post: This key-value pair is inserted in the table and numKeys is incremented.
	 * If the key is already in the table, its value is changed to the argument
	 * value and numKeys is not changed.
	 * @param key The key of item being inserted
	 * @param value the value for this key
	 * @return the old value associated with this key if found; otherwise, null
	 */
	@Override
	public V put(K key, V value) {
		int index = key.hashCode() % table.length;
		if(index<0) {
			index += table.length;
		}
		if(table[index]== null) {
			//Create a new linked list at table[index]
			table[index] = new LinkedList<Entry<K,V>>();
		}
		
		// Search the list at table[index] to find the key.
		for(Entry<K,V> nextItem : table[index]) {
			//if the search is successful, replace the old value.
			if(nextItem.getKey().equals(key)) {
				//replace value for this key
				V oldVal = nextItem.getValue();
				nextItem.setValue(value);
				return oldVal;
			}
		}
		// assert: key is not in the table, add new item.
		table[index].addFirst(new Entry<K,V>(key, value));
		numKeys++;
		if(numKeys > (LOAD_THRESH * table.length)) {
			rehash();
		}
		return null;
	}
	
	/** Method get for class HashtableChain.
	 * If the key is not in the table, null is returned.
	 * If the key is in the table, the value is returned.
	 * @param key The key of item to search for
	 * @return the entrys value if it is in the table, or null if it is not in the table
	 */
	@Override
	public V get(Object key) {
		int index = key.hashCode() % table.length;
		if(index < 0) {
			index = index + table.length;
		}
		if(table[index] == null) {
			return null; // key is not in the table
		}
		//Search the list to find the key
		for(Entry<K,V> nextItem : table[index]) {
			if(nextItem.getKey().equals(key)) {
				return nextItem.getValue();
			}
		}
		// assert: key is not in the table
		return null;
	}
	
	/** Method remove for class HashtableChain.
	 * post: This value removed and number of keys is decremented.
	 * If the key is not in the table, null is returned
	 * @param key The key of item being removed
	 * @return the old value associated with this key if found; otherwise, null
	 */
	@Override
	public V remove(Object key) {
		int index = key.hashCode() % table.length;
		if(index < 0) {
			index = index + table.length;
		}
		if(table[index] == null) {
		// key is not in the table
			return null;
		}
		// Search the list at table[index] to find the key.
			for(Entry<K,V> nextItem : table[index]) {
				//if the search is successful, replace the old value.
				if(nextItem.getKey().equals(key)) {
					//replace value for this key
					V oldVal = nextItem.getValue();
					table[index].remove(nextItem);
					numKeys -- ;
					if(table[index].isEmpty()){
						table[index] = null;
					}
					return oldVal;
				}
			}
			return null;
	}
	
	/** 
	 * Returns a String of all the keys and values in the table. 
	 * Does this by looping through each index of the array of linked lists,
	 * in each linked list, it will iterate through the entries and append
	 * their keys and values to a StringBuilder which will be 
	 * turned into a String and returned. 
	 * @return the StringBuilder's toString method
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//goes through each index of the table array
		for(int i = 0 ; i < table.length; i++) {
			// can only search an index if its not null
			if(table[i] != null){
				// iterates through the linked list in that index
				for(Entry<K,V> nextItem : table[i]) {	
					sb.append(nextItem.toString());
				
					sb.append(", ");
				}
				sb.append("\n");
			 }		
		}
		return sb.toString();
	}

	
	/** 
	 * Expands table size when loadFactor exceeds LOAD_THRESHOLD @post the size of table is doubled and is an
	 * odd integer. Each entry from the original table is reinserted into the expanded table.
	// If you use the rehash of the HashtableOpen the difference is // in the reinsertion logic
	 */
	private void rehash() {
		// Save a reference to oldTable
		LinkedList<Entry<K,V>>[] oldTable = table;
		//Double the capacity of the table
		table = new LinkedList[2*oldTable.length +1];		
		//Reinsert all items in oldTable into expanded table.
		numKeys = 0;		
		for(int i = 0; i <oldTable.length; i++) {
			if (oldTable[i] != null) {
				//Insert entry in expanded table
				for(Entry<K,V> nextItem : oldTable[i]) {
					//takes each entry from the linked list in the arrays index
					put(nextItem.key,nextItem.value);
				}
			}
		}
	}

	
	/** Inner class to implement the set view. */ 
	private class EntrySet extends java.util.AbstractSet<Map.Entry<K, V>> { 
		/** Return the size of the set. */
		@Override
		public int size() {
			return numKeys; 
		}
		
		/** Return an iterator over the set. */ @Override
		public Iterator<Map.Entry<K, V>> iterator() {
			return new SetIterator(); 
		}
	} // end of EntrySet class
	
	private class SetIterator implements Iterator<Map.Entry<K, V>> {
		int index = 0;
		int lastItemReturned = 0;
		Iterator<Entry<K, V>> localIterator = null; 
		@Override
		public boolean hasNext() {
			if (localIterator != null) {
				if (localIterator.hasNext()) {
					return true; 
				} 
				else {
					localIterator = null;
					index++; 
				}
			}
			while(index < table.length && table[index] == null) { 
				  index++;
			}
			if (index == table.length) {
				return false; 
			}
			localIterator = table[index].iterator();
			return localIterator.hasNext(); 
		}
		
		@Override
		public Map.Entry<K, V> next() { 
			return null;
		}
		@Override
		public void remove() {
			
		}
	} // end of SetIterator Class
	
	/** Contains key-value pairs for a hash table. */
	private static class Entry<K,V> {
			
			/**The key */
			private K key;
			/** The value*/
			private V value;
			
			/**Creates a new key-value pair
			 * @param key the key
			 * @param value the value
			 */
			public Entry (K key, V value) {
				this.key = key;
				this.value = value;
			}
			
			/**Gets the Entrys key
			 * @return the key
			 */
			public K getKey() {
				return key;
			}
			
			/**Gets the Entrys value
			 * @return the value
			 */
			public V getValue() {
				return value;
			}
			
			/**Sets the value of the Entry
			 * @param val the new value
			 * @return oldVal the old value
			 */
			public V setValue(V val) {
				V oldVal = value;
				value = val;
				return oldVal;
			}
			
			public String toString() {
				return key + " = " + value;
			}
			
		}// end of Entry class
	
} // end of HasttableChain class
