package com.breakfast.core.map;

import java.util.Random;

/** Retrieves random descriptions for rooms from a static set of data.
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class DescriptionChooser {
	private static Random random = new Random(System.currentTimeMillis());
	/** Retrieve random room description.
	 * @return description
	 */
	public static String choose() {
		return descriptions[random.nextInt(descriptions.length)];
	}
	/** Retrieves random room name.
	 * @return room name
	 */
	public static String chooseName() {
		return names[random.nextInt(names.length)];
	}
	
	public static String[] names = {
		"the Mattress Room",
		"the Green Room",
		"the Blue Room",
		"the Girl's Room",
		"the Eating Room (for dining purposes ONLY)",
		"the Praying Mantis room",
		"the Sense Room",
		"the Sensibilities Room",
		"the Reading Room",
		"the Bath Room",
		"the Pepper Room",
		"the Salt Room"
	};
	
	public static String[] descriptions = {
		"This room is really dark and no one can see anything, and no"
		+ "body has any eyes. Even you.",
		"This room's pretty decent. Got some couches.\n"
		+ "Some nice, lovely, sheer curtains. They are billowing from the open window. "
		+ "75.00$.",
		"This room has some spaghetti smattered all across the walls.\n"
		+ "It looks very old but also very good.",
		"This room is Mattress Discounters.\nThe fluorescent lighting hurts your "
		+ "gentle sensibilities.",
		"This appears to be an abandoned classroom.\n"
		+ "You see turned over desks and old markings on the dusty whiteboard.",
		"This room just is full of ants.",
		"You enter a women's bathroom. You see Amanda Bynes and two other girls.",
		"This room has a few thousand ants.",
		"This room has one hundred ants, but that's it.",
		"This room has a piece of lettuce in it but whenever you touch it it just slips through your fingers.",
		"This room is just... you know.",
		"Imagine just a big room with trees. This room is outside.",
		"This room is a planetarium. It's pretty good and you're on a tour.",
		"The ant in this room is large, and the smaller ants in this room are ants.",
		"Caterpillar.",
		"World record for most ants.",
		"Large bird in this room."
	};
}
