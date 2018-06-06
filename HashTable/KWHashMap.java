package edu.miracosta.cs113;
/**
* An interface for HashMap
* @author Koffman and Wolfgang 
*/
public interface KWHashMap<K,V> {
	
	V get(Object key);
	
	boolean isEmpty();
	
	V put(K key, V value);
	
	V remove(Object key);
	
	int size();
	
}
