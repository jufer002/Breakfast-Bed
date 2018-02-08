package com.breakfast.core.item;

/** Item represents objects that can be bought, sold, desired between mobs.
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class Item {
	public Item(String name, float value) {
		this.name = name;
		this.value = value;
	}
	
	/** 
	 * @param name name of item
	 * @param value cost of item
	 */
	public Item(String name, float value, float weight) {
		this.name = name;
		this.value = value;
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public float getValue() {
		return value;
	}
	public float getWeight() {
		return weight;
	}
	private String name;
	private float value;
	private float weight = 0f;
}
