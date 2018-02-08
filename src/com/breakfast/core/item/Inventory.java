package com.breakfast.core.item;

import java.util.ArrayList;

/** Inventory is a container for items. Rooms and mobs both have one.
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class Inventory {
	public Inventory() {
		
	}
	/** Retrieve inventory as String.
	 * @return list of items as formatted String
	 */
	public String printInventory() {
		String result = "Here you see | ";
		for (int i = 0; i < items.size(); i++) {
			result += items.get(i).getName() + " | ";
		}
		if (result.equals("Here you see | ")) {
			return "There's nothing here.";
		}
		else {
			return result;
		}
	}
	
	public Item getItem(int i) {
		return items.get(i);
	}
	
	/** Goes through item list and retrieves an item object based on a string of its name.
	 * @param item String to search for
	 * @return the item or null.
	 */
	public Item getItem(String item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equalsIgnoreCase(item)) {
				return items.get(i);
			}
		}
		return null;
	}
	/** Adds item to item list.
	 * @param item item to add.
	 * @return congratulation message for method tester.
	 */
	public String addItem(Item item) {
		if (item == null) {
			return "That's not there buddy.";
		}
		weight += item.getWeight();
		items.add(item);
		return "Congratualations! You got " + item.getName() + ".";
	}
	/** Remove item from item list.
	 * @param item item to remove.
	 */
	public void removeItem(String item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equalsIgnoreCase(item)) {
				weight -= items.get(i).getWeight();
				items.remove(i);
			}
		}
	}
	/** Checks if item list contains item of a given name.
	 * @param item given name
	 * @return whether or not the list contains the item.
	 */
	public boolean contains(String item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equalsIgnoreCase(item)) {
				return true;
			}
		}
		return false;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public int size() {
		return items.size();
	}
	
	/** List of items.
	 * 
	 */
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private float weight = 0f;
}
