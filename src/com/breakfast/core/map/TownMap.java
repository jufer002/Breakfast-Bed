package com.breakfast.core.map;

import com.breakfast.core.item.Item;
import com.breakfast.core.mob.NonPlayer;

public class TownMap {
	private static Map townMap = new Map(3, 3);
	
	public static Map getTown() {
		townMap.makeRooms(rooms, descriptions);
		townMap.scatterItems();
		townMap.getRoom(0,1).infiteMattress();
		return townMap;
	}
	
	// Rooms must be the same length as descriptions, and its order must be maintained.
	private static Room[] rooms = {
			
				//The Help Room
				new Room("The Help Room", 0, 0)
				.setImage("res/logo2.jpg"),
				
		
				new Room("Street", 1, 0)
				.addItem(new Item("egg", 5f, 1f))
				.setImage("res/the-most-dust_250x250.jpg"),
				
				
				new Room("Mattress Store", 0, 1)
				.addMob(new NonPlayer("Mattress Mac", 0, 1), townMap)
				.addItem(new Item("mattress", 150f, 20f))
				.setImage("res/mattress_Discounters_250x250.jpeg"),
			new Room("Tom's house", 1, 1)
				.setImage("res/MUGS.jpg")
				.addMob(new NonPlayer("Tom", 1, 1), townMap)
				.addItem(new Item("Tom's favorite painting of one thousand beverages", 10000f)),
			new Room("Flim's house", -1, 1)
				.addMob(new NonPlayer("Flim", -1, 1), townMap)
				.setImage("res/flimHouse_250x250.jpg"),
				
			new Room("Streets", 0, 2)
				.addItem(new Item("egg", 5f, 1f))
				.setImage("res/dust.resized.jpg"),
			new Room("Snomp's House", 1, 2)
				.addMob(new NonPlayer("Snomp", 1, 2), townMap)
				.addItem(new Item("ketchup", 2f))
				.setImage("res/ketchup.jpg"),
			new Room("The Garbage Factory", -1, 2)
				.addItem(new Item("garbage", 100f))
				.addItem(new Item("garbage", 1f))
				.setImage("res/garbageMountain.jpg"),
			
			new Room("Dusty Streets", 0, 3)
				.addMob(new NonPlayer("Dusty Dimp", 0, 3), townMap)
				.addMob(new NonPlayer("Dusty Delilah", 0, 3), townMap)
				.addItem(new Item("dust", 0f))
				.setImage("res/redDust.resized.jpg"),
			new Room("Abandoned Classroom", 1, 3)
				.addMob(new NonPlayer("The Ghost of Teachers past", 1, 3), townMap)
				.addItem(new Item("chalk", 0f))
				.addItem(new Item("Julian's exam", 1.5f))
				.setImage("res/abandoned.jpg"),
			new Room("The Diner", -1, 3)
				.addItem(new Item("moldy eggs", 3f))
				.addItem(new Item("a poster of Rosie the Riveter", 7f))
				.setImage("res/diner.jpg"),	
			new Room("The Old Creek", -2, 3)
				.addItem(new Item("backpack", 1f))
				.setImage("res/creek.jpg"),
				
			new Room("The Billowing Room", 0, -1)
				.addItem(new Item("curtains", 75f))
				.setImage("res/billow.jpg"),
			new Room("The Dark Room", 1, -1)
				.setImage("res/dark.jpg"),
			new Room("Beach Scene", 2, -1)
				.addItem(new Item("Flotsam", 5f))
				.addItem(new Item("Jetsam", 5f))
				.addItem(new Item ("Sand", 0f))
				.setImage("res/beach1.jpg"),
			new Room("The Ant Room", -1, -1)
				.setImage("res/ants.jpg")
				.addItem(new Item("ants", 0f)),
			new Room("The Eating Room", -2, -1)
				.addMob(new NonPlayer("Flom", -2, -1), townMap)
				.addMob(new NonPlayer("Fom, Tom's Cousin", -2, -1), townMap)
				.addItem(new Item("spaghetti", 10f, 3f))
				.setImage("res/marinara.jpg"),
			
			new Room("The Girl's Room", 0, -2)
				.addMob(new NonPlayer("Amanda", 0, -2), townMap)
				.setImage("res/Amanda.jpg")
				.addItem(new Item("Gossip", 15f)),
			new Room("Another Ant Room", 1, -2)
				.addMob(new NonPlayer("A myrmecologist, one that studies ants", 1, -2), townMap)
				.addItem(new Item("ants", 1f))
				.setImage("res/manyAnts.jpg"),
			new Room("The Sensibilities Room", 2, -2)
				.addItem(new Item("ants", 1f))
				.setImage("res/someAnts.jpg"),
			new Room("The Planetarium Room", 3, -2)
				.addMob(new NonPlayer("Astrophysasist Neil deGrasse Tyson", 3, -2), townMap)
				.addItem(new Item("planets", 1000f))
				.setImage("res/planetarium.jpg"),
				
			
			new Room("The Outside", 0, -3)
				.addItem(new Item("trees", 100f))
				.setImage("res/forest.jpg"),	
			new Room("Salt mine", 1, -3)
				.addItem(new Item("salt", 1f))
				.setImage("res/salt.jpg")
				
			
	};

	// Descriptions must be the same length as rooms, and its order must be maintained.
	private static String[] descriptions = {
			
			//The Help Room
			"Welcome! If you ever need any help, simply type help to see commands available.",
			
			// Street
			"The street is dusty and theres a bunch of eggs on it.",
			// Mattress Store
						"This is the Mattress Room. \n"
						+ "This store is stacked to the brim with mattresses."
						+ "\nThe fluorescent lighting hurts your "
						+ "gentle sensibilities.",
			// Tom's house
			"Tom's house has posters of Tom on the walls. He is smiling.",
			// Flim's house
			"Flim's house has motivational posters on the walls. 'Shoot for the moon; you can't miss.'"
			+ "You have brains in your head. You have feet in your shoes. You can steer yourself any direction you choose.",
			// Streets (cont.)
			"The street is REALLY dusty. There's just so many eggs on it.",
			// Snomp's House
			"Snomp's house is just a giant hollowed out ketchup container. You resist the urge to touch the walls.",
			// The Garbage Factory
			"The Garbage Factory is where all the world's garbage is made. It looks great!",
			// Dusty Streets
			"These streets just are so covered with dust. There's so much dust covering these streets.",
			// Abandoned Classroom
			"This classroom is identical to a classroom, but it is abandoned. \n"
			+ "You see turned over desks and old markings on the dusty whiteboard.",
			// The Diner
			"It smells like the Old Creek in here. It smells like eggs too but the stench of the old creek beckons you from the west.",
			// The Old Creek
			"It REALLY smells like the Old Creek in here.",
			//The Billowing Room
			"This is the Green Room. This room's pretty decent. Got some couches.\n"
			+ "Some nice, lovely, sheer curtains. They are billowing from the open window. ",
			//The Dark Room
			"This is the Reading Room. This room is really dark and no one can see anything, and no"
			+ "body has any eyes. Even you.",
			//Beach Scene
			"You hear waves crashing onto the sand. You're feet are now \n"
			+ "covered in sand; nice going.",
			//The Ant Room
			"This room just is full of ants.",
			//The Eating Room
			"This is the Eating Room (for dining purposes ONLY) \n"
			+ "This room has some spaghetti smattered all across the walls.\n"
					+ "It looks very old but also very good.",
			//The Girl's Room
			"Welcome to the Girl's Room. You enter a women's bathroom. \n"
				+ "You see Amanda Bynes and two other girls.",
			//Another Ant Rooms
			"This is the Praying Mantis Room. This room has a few thousand ants.",
			//The Sensibilities Room
			"This is the Sensibilities Room. This room has one hundred ants, but that's it.",
			//The Planetarium
			"This is the Bath Room. This room is a planetarium. It's pretty good and you're on a tour.",
			//The Outside
			"This is the controversial room. Imagine just a big room with trees. This room is outside.",
			//the Salt mine
			"This room is not worth its salt."
		/*
			"the Blue Room",
			"the Sense Room",
			"the Pepper Room",
			"the Salt Room"
			"This room has a piece of lettuce in it but whenever you touch it it just slips through your fingers.",
			"This room is just... you know.",
			"The ant in this room is large, and the smaller ants in this room are ants.",
			"Caterpillar.",
			"World record for most ants.",
			"Large bird in this room."
			*/
	};
}
