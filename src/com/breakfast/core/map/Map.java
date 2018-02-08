package com.breakfast.core.map;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import com.breakfast.core.item.Item;
import com.breakfast.core.item.ItemFactory;
import com.breakfast.core.mob.Ai;
import com.breakfast.core.mob.Mob;
import com.breakfast.core.mob.NonPlayer;
import com.breakfast.core.mob.Player;

/**
 * Map contains rooms and list of all mobs.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class Map extends Observable implements Observer {
	/**
	 * Initializes ArrayList of Rooms
	 * 
	 * @param w
	 *            width of map
	 * @param h
	 *            height of map
	 */
	public Map(int w, int h) {
		width = w;
		height = h;
		rooms = new ArrayList<Room>();
		mobs = new ArrayList<Mob>();
	}

	/**
	 * Adds rooms to the map's array and sets their descriptions.
	 * 
	 * @param newRooms
	 *            array of rooms for map
	 * @param descriptions
	 *            array of descriptions in same order as rooms
	 */
	public void makeRooms(Room[] newRooms, String[] descriptions) {
		assert (newRooms.length == descriptions.length);
		for (int i = 0; i < newRooms.length; i++) {
			newRooms[i].setDescription(descriptions[i]);
			rooms.add(newRooms[i]);
		}
	}

	/**
	 * Creates random new rooms in the ArrayList
	 * 
	 */
	public void makeRandRooms() {
		for (int i = -width; i <= width; i++) {
			for (int j = -height; j <= height; j++) {
				// TODO Just change
				rooms.add(new Room(DescriptionChooser.chooseName(), i, j));
			}
		}
	}

	/**
	 * Randomly scatter an item in each of the rooms. A mob should not request
	 * an item that hasn't been placed in the map.
	 */
	public void scatterItems() {
		Stack<Item> items = new Stack<Item>();
		for (int i = 0; i < ItemFactory.size(); i++) {
			items.push(ItemFactory.getItem(i));
		}
		for (int i = -width; i <= width; i++) {
			for (int j = -height; j <= height; j++) {
				if (!roomAt(i, j) || (i == 0 && j == 0)) {
					continue;
				}
				if (items.isEmpty()) {
					break;
				}
				getRoom(i, j).addItem(items.pop());
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public Room getRoom(int x, int y) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getX() == x && rooms.get(i).getY() == y) {
				return rooms.get(i);
			}
		}
		return null;
	}

	private boolean roomAt(int x, int y) {
		return getRoom(x, y) != null;
	}

	/**
	 * Retrieves formatted string of the mobs in a room at a given coordinate.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return formatted string
	 */
	public String getRoomMobs(int x, int y, Mob self) {
		String people = "In this room, you see | ";
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).getX() == x && mobs.get(i).getY() == y && mobs.get(i).getName() != self.getName()
					&& mobs.get(i).getName() != "") {
				people += mobs.get(i).getName() + " | ";
			}
		}
		if (people.equals("In this room, you see | ")) {
			return "There is no one here.";
		} else {
			return people;
		}
	}

	public ArrayList<Player> getPlayersAt(Room room) {
		int x = room.getX();
		int y = room.getY();

		ArrayList<Player> result = new ArrayList<Player>();
		for (Mob m : mobs) {
			if (m instanceof Player && m.getX() == x && m.getY() == y) {
				result.add((Player) m);
			}
		}
		return result;
	}

	/**
	 * Checks whether the path the mob wants to move in is blocked.
	 * 
	 * @param mob
	 *            candidate for moving
	 * @param dir
	 *            direction mob is moving
	 * @return whether the mob can move in the given direction
	 */
	public boolean canMove(Mob mob, Mob.Direction dir) {
		switch (dir) {
		case N:
			return getRoom(mob.getX(), mob.getY() + 1) != null;
		case E:
			return getRoom(mob.getX() + 1, mob.getY()) != null;
		case S:
			return getRoom(mob.getX(), mob.getY() - 1) != null;
		case W:
			return getRoom(mob.getX() - 1, mob.getY()) != null;
		default:
			return false;
		}
	}

	/**
	 * Checks if it's possible to move from a given coordinate in a given
	 * direction.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param dir
	 *            direction
	 * @return whether the movement is possible
	 */
	public boolean canMove(int x, int y, Mob.Direction dir) {
		switch (dir) {
		case N:
			return getRoom(x, y + 1) != null;
		case E:
			return getRoom(x + 1, y) != null;
		case S:
			return getRoom(x, y - 1) != null;
		case W:
			return getRoom(x - 1, y) != null;
		default:
			return false;
		}
	}

	/**
	 * Assembles String that names all the directions a mob could move from a
	 * given coordinate.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return assembled String of directions
	 */
	public String getExits(int x, int y) {
		String dirs = "You can move ";
		if (canMove(x, y, Mob.Direction.N)) {
			dirs += "| north ";
		}
		if (canMove(x, y, Mob.Direction.E)) {
			dirs += "| east ";
		}
		if (canMove(x, y, Mob.Direction.S)) {
			dirs += "| south ";
		}
		if (canMove(x, y, Mob.Direction.W)) {
			dirs += "| west";
		}
		return dirs + " |";
	}

	/**
	 * Add mob to list.
	 * 
	 * @param mob
	 *            new mob
	 */
	public Room addMob(Mob mob) {
		mobs.add(mob);
		return getRoom(mob.getX(), mob.getY());
	}

	public void removeMob(Mob mob) {
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).equals(mob)) {
				mobs.remove(i);
				return;
			}
		}
	}

	public String greetMob(Player player, String[] inputArray) {
		String name = constructName(inputArray);
		for (Mob mob : mobs) {
			if (player.getX() == mob.getX() && player.getY() == mob.getY() && name.equalsIgnoreCase(mob.getName())) {
				player.setFriend(mob);
				return "Congratualations, you have greeted " + mob.getName() + ". You can now talk to them.";
			}
		}
		return "They're not here.";
	}

	public String speakTo(Player player) {
		if (!player.hasFriend()) {
			return "You need to greet a person before you can talk to them.";
		}
		if (player.getDelivery() != null) {
			return "You need to deliver your current deliveries "
					+ "before you can start delivering new deliveries!!!!!!";
		}
		return ((NonPlayer) getMob(player.getFriend())).assignDelivery(player);
	}
	
	/**
	 *  Add volitiouos nonplayers.
	 */
	public void addAgents() {
		NonPlayer travelingTarry = new NonPlayer("Traveling Tarry", 0, 2);
		NonPlayer travelingTamira = new NonPlayer("Traveling Tamira", -1, 2);
		NonPlayer java = new NonPlayer("Java", 3, -2);
		NonPlayer grumpyMan = new NonPlayer("Grumpy Man", 0, -3);
		
		travelingTarry.addObserver(this);
		travelingTamira.addObserver(this);
		java.addObserver(this);
		grumpyMan.addObserver(this);
		
		mobs.add(travelingTarry);
		mobs.add(travelingTamira);
		mobs.add(java);
		mobs.add(grumpyMan);
		
		travelingTarry.start();
		travelingTamira.start();
		java.start(() -> Ai.itemThrower(java));
		grumpyMan.setDesire("nothing!");
	}
	
	public void joinAgents() {
		for (Mob mob : mobs) {
			mob.join();
		}
	}
	
	@Override
	public void update(Observable mob, Object arg) {
		setChanged();
		notifyObservers();
	}

	private String constructName(String[] inputArray) {
		String[] nameArray = new String[inputArray.length - 1];
		for (int i = 1; i < inputArray.length; i++) {
			nameArray[i - 1] = inputArray[i];
		}
		String name = "";
		for (int i = 0; i < nameArray.length; i++) {
			name += nameArray[i] + " ";
		}
		return name.trim();
	}

	public Mob getMob(String name) {
		for (Mob mob : mobs) {
			if (mob.getName().equalsIgnoreCase(name)) {
				return mob;
			}
		}
		return null;
	}

	/**
	 * list of all mobs
	 * 
	 */
	private ArrayList<Mob> mobs;
	/**
	 * list of rooms
	 * 
	 */
	private ArrayList<Room> rooms;
	/**
	 * dimensions of map
	 * 
	 */
	private int width, height;
}
