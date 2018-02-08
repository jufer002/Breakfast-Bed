
package com.breakfast.core.mob;

import com.breakfast.core.gui.GameInterface;
import com.breakfast.core.gui.Gui;

/**
 * Player is a mob that is controlled by the user.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class Player extends Mob {
	public Player(String name, int x, int y) {
		super(name, x, y);
	}
	
	public Player(int x, int y) {
		super(x, y);
	}

	public Player(int x, int y, GameInterface gui) {
		super(x, y);
		addObserver((Gui) gui);
		this.gui = (Gui) gui;
		getRoom().addObserver((Gui) gui);
		setChanged();
		notifyObservers(getRoom());
	}
	
	public String printInventory() {
		return inventory.printInventory();
	}
}
