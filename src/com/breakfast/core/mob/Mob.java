package com.breakfast.core.mob;

import java.util.Observable;

import com.breakfast.core.Engine;
import com.breakfast.core.gui.Gui;
import com.breakfast.core.item.Inventory;
import com.breakfast.core.item.Item;
import com.breakfast.core.item.ItemFactory;
import com.breakfast.core.map.Map;
import com.breakfast.core.map.Room;
import com.breakfast.util.Pair;

/**
 * Mob has two types - Player and NonPlayer - that represent people in the game.
 * Mobs have inventories, names, and locations.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
/**
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
/**
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public abstract class Mob extends Observable {
	public enum Direction {
		N, E, S, W
	}

	public Mob(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Delivery getDelivery() {
		return delivery;
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		setChanged();
		notifyObservers(getRoom());
	}
	
	public void finishDelivery(NonPlayer recipient) {
		setDelivery(null);
		getRoom().setDeliveryRoom(false);
		recipient.setDesire(ItemFactory.getRandomItem().getName());
	}

	public Mob(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public float getMoney() {
		return money;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Item getItem(String item) {
		return inventory.getItem(item);
	}

	/**
	 * getItem allows the mob to add an item from the room's inventory to the
	 * mob's inventory.
	 * 
	 * @param map
	 * @param tmpItemName
	 */
	public String pickupItem(Map map, String[] tmpItemName) {
		String itemName = "";
		for (int i = 1; i < tmpItemName.length; i++) {
			itemName += tmpItemName[i] + " ";
		}
		Item item = map.getRoom(x, y).getItem(itemName.trim());
		if (item.getWeight() + getCurrentWeight() > carryWeight) {
			map.getRoom(x, y).addItem(item);
			return "You cannot lift the " + itemName.trim() + ". You're carrying too much.";
		}
		if (item.getName().equals("backpack")) {
			increaseCarryWeight(25);
		}
		String result = inventory.addItem(item);
		setChanged();
		notifyObservers(getRoom());
		return result;
	}
	
	public boolean give(Item item, NonPlayer other) {
		if (other == null || item == null) {
			return false;
		}
		if (other.getDesire().equalsIgnoreCase(item.getName().toLowerCase())) {
			removeItem(item);
			other.addItem(item);
			return true;
		} else {
			return false;
		}
	}
	
	protected void removeItem(Item item) {
		inventory.removeItem(item.getName());
	}
	
	protected void addItem(Item item) {
		inventory.addItem(item);
	}

	/**
	 * drop allows the mob to remove an item from their inventory, placing it
	 * back into the inventory of the room. It also returns a string alerting
	 * the user that item has been dropped. If the item cannot be found within
	 * the contents of the user's inventory, a string is returned, alerting the
	 * user of their devastating mistake.
	 * 
	 * @param map
	 * @param item
	 * @return
	 */
	public String drop(Map map, String[] input) {
		String item = "";
		for (int i = 1; i < input.length; i++) {
			item += input[i] + " ";
		}
		item = item.trim();
		if (inventory.contains(item)) {
			Item tmp = inventory.getItem(item);
			map.getRoom(x, y).addItem(tmp);
			inventory.removeItem(item);
			if (item.equals("backpack")) {
				increaseCarryWeight(-25);
			}
			setChanged();
			notifyObservers(getRoom());
			return name + " dropped the " + item + ".";
		}
		return name + ", you cannot drop the " + item + ".";
	}

	/**
	 * look displays the description of the room, its contents (including items
	 * and people) and its available exits
	 * 
	 * @param map
	 * @return
	 */
	public String look(Map map) {
		return "You are in " + map.getRoom(x, y).getName() + " (" + x + ", " + y + "). "
				+ map.getRoom(x, y).getDescription() + "\n" + map.getRoomMobs(x, y, this) + "\n"
				+ map.getRoom(x, y).printInventory() + "\n" + map.getExits(x, y);
	}

	public Pair<String, Boolean> move(Map map, Direction dir) {
		Pair<String, Boolean> result = new Pair<String, Boolean>("Nice direction pal.", false);
		if (!map.canMove(this, dir)) {
			result.first = "That way is blocked!";
			return result;
		} else {
			result.second = true;
		}
		if (gui != null) {
			getRoom().deleteObserver(gui);
		}
		switch (dir) {
		case N:
			y++;
			result.first = name + " moves north.";
			break;
		case E:
			x++;
			result.first = name + " moves east.";
			break;
		case S:
			y--;
			result.first = name + " moves south.";
			break;
		case W:
			x--;
			result.first = name + " moves west.";
			break;
		default:
			break;
		}
		if (gui != null) {
			getRoom().addObserver(gui);
		}
		setChanged();
		notifyObservers(getRoom());
		friend = null;
		return result;
	}

	public Pair<String, Boolean> move(Map map, String dir) {
		switch (dir.toLowerCase()) {
		case "north":
			return move(map, Direction.N);
		case "east":
			return move(map, Direction.E);
		case "south":
			return move(map, Direction.S);
		case "west":
			return move(map, Direction.W);
		default:
			return new Pair<String, Boolean>("Nice direction pal.", false);
		}
	}

	public Thread getThread() {
		return actThread;
	}

	/**
	 * @param thread
	 *            for mob's actThread to be set to
	 * @return actThread for easy access
	 */
	public Thread setThread(Thread thread) {
		actThread = thread;
		return actThread;
	}

	public Room getRoom() {
		return Engine.getMap().getRoom(x, y);
	}
	
	public String getFriend() {
		if (friend != null) {
			return friend.getName();
		}
		return "You aren't talking to anyone.";
	}
	
	public boolean hasFriend() {
		return friend != null;
	}
	
	public void setFriend(Mob other) {
		friend = other;
	}
	
	public void takeTurn() {
		if (delivery != null) {
			delivery.incrementTurns();
		}
		setChanged();
		notifyObservers(getRoom());
	}
	
	public void addMoney(float money) {
		this.money += money;
		setChanged();
		notifyObservers();
	}
	
	public void increaseCarryWeight(float amt) {
		carryWeight += amt;
	}
	
	public float getCurrentWeight() {
		return inventory.getWeight();
	}
	
	public float getMaxWeight() {
		return carryWeight;
	}
	
	public void join() {
		if (actThread == null) {
			return;
		}
		try {
			actThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected Gui gui;
	protected float carryWeight = 20f;
	protected Thread actThread;
	protected Inventory inventory = new Inventory();
	protected int x, y;
	protected Delivery delivery;
	protected String name = "";
	protected float money = 0f;
	/**
	 * A mob's friend is another mob that they are in conversation with.
	 * Facilitated by the "greet" command.
	 */
	protected Mob friend;
}
