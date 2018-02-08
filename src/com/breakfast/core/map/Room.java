package com.breakfast.core.map;


import java.util.Observable;
import java.util.Random;

import javax.swing.ImageIcon;

import com.breakfast.core.item.Inventory;
import com.breakfast.core.item.Item;
import com.breakfast.core.item.ItemFactory;
import com.breakfast.core.mob.Mob;

/**
 * Rooms have names, locations, descriptions, and inventories.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class Room extends Observable {
	public Room(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		description = DescriptionChooser.choose();
	}

	public Room(String name, String desc, String imgPath, int x, int y) {
		this.name = name;
		description = desc;
		image = new ImageIcon(imgPath);
		this.x = x;
		this.y = y;
	}

	/**
	 * This returns an item in the room's inventory
	 * 
	 * @param item
	 * @return
	 */
	public Item getItem(String item) {
		Item i = inventory.getItem(item);
		removeItem(item);
		setChanged();
		notifyObservers();
		return i;
	}
	
	public Room addMob(Mob mob, Map map) {
		map.addMob(mob);
		setChanged();
		notifyObservers();
		return this;
	}

	/**
	 * This method returns its caller so static rooms can be more easily
	 * customized on initialization.
	 * 
	 * @param item
	 *            item to be added to room
	 * @return self
	 */
	public Room addItem(Item item) {
		inventory.addItem(item);
		setChanged();
		notifyObservers();
		return this;
	}
	
	public Item getRandomItem() {
		if (inventory.size() == 0) {
			return null;
		}
		return inventory.getItem(new Random().nextInt(inventory.size()));
	}

	public void removeItem(String item) {
		inventory.removeItem(item);
		if (item.equalsIgnoreCase("Tom's favorite painting of one thousand beverages")) {
			setDescription("Tom's house has posters of Tom on the walls. He is frowning.");
			setImage("res/moose.jpg");
		}
		if (item.equals("mattress") && infiniteItem){
			addItem(ItemFactory.getItem("mattress"));
		}
		setChanged();
		notifyObservers();
	}
	
	public String printInventory() {
		return inventory.printInventory();
	}

	public String getName() {
		return name;
	}

	public void setDescription(String desc) {
		setChanged();
		notifyObservers();
		description = desc;
	}

	public String getDescription() {
		return description;
	}

	public Room setImage(String path) {
		image = new ImageIcon(path);
		return this;
	}

	public ImageIcon getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void infiteMattress(){
		infiniteItem = true;
	}
	
	public void setDeliveryRoom(boolean val) {
		deliveryRoom = true;
	}
	
	public boolean isDeliveryRoom() {
		return deliveryRoom;
	}

	private Inventory inventory = new Inventory();
	private final String name;
	private String description;
	private final int x, y;
	private boolean infiniteItem = false;
	private boolean deliveryRoom = false;
	private ImageIcon image;
}
