package com.breakfast.core.item;

import java.util.Random;

public class ItemFactory {
	public static Item getItem(String name) {
		switch (name.toLowerCase()) {
		case "egg":
			return egg;
		case "oatmeal":
			return oatmeal;
		case "mattress":
			return mattress;
		case "yogurt":
			return yogurt;
		case "toast":
			return toast;
		case "fruit":
			return fruit;
		case "cereal":
			return cereal;
		case "spaghetti":
			return spaghetti;
		case "raw toast":
			return rawToast;
		case "water bed":
			return waterBed;
		case "jello bed":
			return jelloBed;
		case "hammock":
			return hammock;
		case "sleeping bag":
			return sleepingBag;
		case "air mattress":
			return airMattress;
		case "cot":
			return cot;
		default:
			return null;
		}
	}

	public static Item getRandomItem() {
		return items[new Random().nextInt(items.length)];
	}

	private static Item egg = new Item("egg", 2f, 1f);
	private static Item oatmeal = new Item("oatmeal", 7f, 2f);
	private static Item mattress = new Item("mattress", 500f, 20f);
	private static Item yogurt = new Item("yogurt", 4f, 2f);
	private static Item toast = new Item("toast", 3f, 1f);
	private static Item fruit = new Item("fruit", 10f, 3f);
	private static Item cereal = new Item("cereal", 2f, 3f);
	private static Item spaghetti = new Item("spaghetti", 10f, 3f);
	private static Item rawToast = new Item("raw toast", 2.5f, 1f);
	private static Item waterBed = new Item("water bed", 200f, 20f);
	private static Item jelloBed = new Item("jello bed", 400f, 20f);
	private static Item hammock = new Item("hammock", 100f, 15f);
	private static Item sleepingBag = new Item("sleeping bag", 50f, 10f);
	private static Item airMattress = new Item("air mattress", 100f, 12f);
	private static Item cot = new Item("cot", 30f, 15f);

	private static Item[] items = { 
			egg, oatmeal, mattress, yogurt, toast, 
			fruit, cereal, spaghetti, rawToast,
			waterBed, hammock, sleepingBag,
			airMattress, cot
	};

	public static int size() {
		return items.length;
	}
	
	public static Item getItem(int i) {
		return items[i];
	}

}
