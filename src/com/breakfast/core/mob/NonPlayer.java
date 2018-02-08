package com.breakfast.core.mob;

import java.util.Random;

import com.breakfast.core.Engine;
import com.breakfast.core.item.Item;
import com.breakfast.core.item.ItemFactory;
import com.breakfast.core.map.Map;

/**
 * NonPlayer is a type of Mob that is not controlled by the user. They have
 * desires that the Player fills by completing deliveries.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class NonPlayer extends Mob {
	public NonPlayer(String name, int x, int y) {
		super(name, x, y);
		setDesire(ItemFactory.getRandomItem().getName());
		r.setSeed(System.currentTimeMillis());
	}

	public String getDesire() {
		return desire;
	}

	public void setDesire(String desire) {
		this.desire = desire;
	}

	public String assignDelivery(Player player) {
		Item item = ItemFactory.getItem(desire);
		if (item == null) {
			return "I don't want anything. I am grumpy.";
		}
		player.setDelivery(new Delivery(item, this, Delivery.TURNS, item.getValue()));
		getRoom().setDeliveryRoom(true);
		return "Right... you gotta bring me " + getDesire() + ".";
	}
	
	public void start() {
		setThread(new Thread(() -> travel()));
		
		actThread.start();
	}
	
	public void start(Runnable r) {
		setThread(new Thread(r));
		
		actThread.start();
	}
	
	public void pickupItem(Map map, Item item) {
		if (item.getWeight() + getCurrentWeight() > carryWeight) {
			return;
		}
		
		inventory.addItem(map.getRoom(x, y).getItem(item.getName()));
		setChanged();
		notifyObservers();
	}
	
	public void dropItem(Map map, Item item) {
		if (inventory.contains(item.getName())) {
			Item tmp = inventory.getItem(item.getName());
			map.getRoom(x, y).addItem(tmp);
			inventory.removeItem(item.getName());
			setChanged();
			notifyObservers(getRoom());
		}
	}
	
	private void travel() {
		// Set a random sleep time between 1 and 6 seconds.
		sleepTime = (5 + r.nextInt(5)) * 1000;
		
		while (Engine.getEngine().isRunning()) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			move(Engine.getMap(), pickDirection());
		}
	}
	
	public Direction pickDirection() {
		switch (r.nextInt(4)) {
		case 0:
			return Direction.N;
		case 1:
			return Direction.E;
		case 2:
			return Direction.S;
		case 3:
			return Direction.W;
		default:
			System.err.println("NonPlayer::pickDirection needs to have its random object's bound fix.");
			return null;
		}
	}
	
	private int sleepTime = 1000;
	private Random r = new Random();
	private String desire;
}
