package com.breakfast.core.mob;

import com.breakfast.core.Engine;
import com.breakfast.core.item.Item;

public abstract class Ai {
	public static void desperateSearch(NonPlayer mob) {
		while (Engine.getEngine().isRunning()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			mob.move(Engine.getMap(), mob.pickDirection());
		}
	}
	
	public static void itemThrower(NonPlayer mob) {
		while (Engine.getEngine().isRunning()) {
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			mob.move(Engine.getMap(), mob.pickDirection());
			Item item = Engine.getMap().getRoom(mob.getX(), mob.getY()).getRandomItem();
			if (item == null) {
				continue;
			}
			mob.pickupItem(Engine.getMap(), item);
			mob.move(Engine.getMap(), mob.pickDirection());
			mob.dropItem(Engine.getMap(), item);
		}
	}
	
	public static void helpful(NonPlayer mob, Player playerDelivery) {
		
	}
}
