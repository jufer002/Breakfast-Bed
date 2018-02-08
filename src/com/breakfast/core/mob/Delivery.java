package com.breakfast.core.mob;

import com.breakfast.core.item.Item;
import com.breakfast.core.map.Map;

/**
 * Delivery fulfills a non-player's desire. The player has a finite amount of
 * turns available to make each delivery.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class Delivery {
	public Delivery(Item item, Mob recipient, int turns, float money) {
		this.item = item;
		this.recipient = recipient;
		turnsRemaining = turns;
		this.money = money;
	}

	public String attempt(Mob player, Map map, String[] input) {
		// Start index after "give" (the command)
		String itemName = "";
		int i = 1;
		while (!input[i].trim().equalsIgnoreCase("to") && i < 200) {
			// Construct item name.
			itemName += " " + input[i];
			i++;
		}
		String mobName = "";
		for (i = i + 1; i < input.length; i++) {
			mobName += " " + input[i];
		}
		NonPlayer mob = (NonPlayer) map.getMob(mobName.trim());
		if (failed) {
			player.finishDelivery(mob);
			return "You took too long!";
		}

		if (mob == null) {
			return "You can't give anything to someone who's not there.";
		}
		if (player.getItem(itemName.trim()) == null) {
			return "You can't give something that you don't have.";
		}
		if (!recipient.getName().equalsIgnoreCase(mob.getName())) {
			return mob.getName() + " doesn't want that!";
		}
		if (player.give(player.getItem(itemName.trim()), mob)) {
			player.finishDelivery(mob);

			float reward = money + TURNS - turnsRemaining;
			player.addMoney(reward);

			if (reward == 1.0f) {
				return "Thanks for the " + itemName.trim() + "!! Here is a dollar.";
			} else {
				return "Thanks for the " + itemName.trim() + "!! Here is " + reward + " dollars.";
			}

		} else {
			return "No thanks.";
		}
	}

	public int turnsRemaining() {
		return turnsRemaining;
	}

	public Item getItem() {
		return item;
	}

	public Mob getRecipient() {
		return recipient;
	}

	public float getMoney() {
		return money;
	}

	public void incrementTurns() {
		turnsRemaining--;
		if (turnsRemaining == 0) {
			failed = true;
		}
	}

	private Item item;
	private Mob recipient;
	private int turnsRemaining;
	private float money;
	private boolean failed = false;
	public final static int TURNS = 20;
}
