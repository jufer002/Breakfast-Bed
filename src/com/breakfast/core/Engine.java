package com.breakfast.core;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.breakfast.core.gui.Gui;
import com.breakfast.core.map.Map;
import com.breakfast.core.map.TownMap;
import com.breakfast.core.mob.Player;
import com.breakfast.util.Pair;

/**
 * The game's main class that handles interaction between the players and the
 * map, and output.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 * 
 */
public class Engine implements Observer {
	/**
	 * Private engine constructor, ensures that only one engine will be created.
	 * 
	 */
	private Engine() {
		map = TownMap.getTown();
		map.addObserver(this);
	}

	@Override
	public void update(Observable o, Object rawInput) {
		if (o instanceof Map) {
			updateGuis();
		}

		if (o instanceof Gui) {
			assert (rawInput instanceof String[]);
			Gui gui = (Gui) o;
			String[] input = (String[]) rawInput;

			sendInput(gui, input);
		}
	}

	public boolean isRunning() {
		return running;
	}

	/**
	 * Contains all input commands with their usage.
	 * 
	 * @author Zoe Jacobson and Julian Fernandez
	 *
	 */
	private enum Action {
		GET("get (item)"), DROP("drop (item)"), INVENTORY("inventory/i -checks inventory"), MOVE(
				"move (direction)"), EXIT("exit/quit -exits game"), HELP("help -lists commands"), LOGOUT(
						"logout -logs current player out"), GREET("greet (name) -initiate conversation"), SAY(
								"say (message) -speak to a mob that has been greeted"), GIVE(
										"give (item) to (mob's name) -give an item to a mob that has requested it delivered");
		public String getMessage() {
			return message;
		}

		private Action(String action) {
			message = action;
		}

		private final String message;
	}

	/**
	 * Adds a new Player-GameInterface pair to the engine's ArrayList.
	 * 
	 */
	public void newPlayer() {
		Player player = new Player(0, 0);
		Gui gui = new Gui(this, player);
		players.add(gui);
		map.addMob(player);
		// Update surroundings
		gui.printSurroundings(players.get(numPlayers).getPlayer().look(map));
		gui.init();
		gui.print("Hey, welcome to Breakfast & Bed!" + " Before you can start delivering your beds and breakfasts, "
				+ "we need to make a nametag. What's your name?");
		numPlayers++;

		updateGuis();
	}

	/**
	 * Creates a single player and starts the engine.
	 * 
	 */
	public void run() {
		running = true;
		map.addAgents();
		newPlayer();
		newPlayer();
	}

	/**
	 * @return engine the game's engine
	 */
	public static Engine getEngine() {
		return engine;
	}

	/**
	 * @return map the engine's map
	 */
	public static Map getMap() {
		return map;
	}

	/**
	 * Initializes engine and starts it.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		engine = new Engine();
		engine.run();
	}

	private void sendInput(Gui gui, String[] input) {
		if (playerAct(gui, input)) {
			gui.getPlayer().takeTurn();
		}
	}

	/**
	 * Gets input and determines uses it to act on player.
	 * 
	 * @param i
	 *            index of player-actor
	 * @return whether the player's action constituted a turn.
	 */
	private boolean playerAct(Gui gui, String[] input) {
		Player player = gui.getPlayer();
		int i = players.indexOf(gui);

		if (input == null) {
			return false;
		}

		// Lowercase all input.
		for (int j = 0; j < input.length; j++) {
			input[j] = input[j].toLowerCase();
		}

		boolean turnTaken = false;

		switch (input[0]) {
		case "look":
			gui.printSurroundings(player.look(map));
			turnTaken = true;
			break;
		case "get":
			if (input.length < 2) {
				invalidInput(i);
				return false;
			}
			gui.print(player.pickupItem(map, input));
			gui.printSurroundings(player.look(map));
			break;
		case "drop":
			if (input.length < 2) {
				invalidInput(i);
				return false;
			}
			gui.print(player.drop(map, input));
			gui.printSurroundings(player.look(map));
			break;
		case "inventory":
		case "i":
			gui.print(player.printInventory());
			break;
		case "move":
			if (input.length < 2) {
				invalidInput(i);
				return false;
			}
			Pair<String, Boolean> action = player.move(map, input[1]);
			gui.print(action.first);
			turnTaken = true;
			break;
		case "greet":
			if (input.length < 2) {
				invalidInput(i);
				return false;
			}
			gui.print(map.greetMob(player, input));
			break;
		case "say":
			if (input.length < 2) {
				invalidInput(i);
				return false;
			}
			gui.print(map.speakTo(player));
			break;
		case "give":
			if (input.length < 4) {
				invalidInput(i);
				return false;
			}
			if (player.getDelivery() == null) {
				gui.print("You can't deliver something when no one's asked for anything.");
				return false;
			}
			gui.print(player.getDelivery().attempt(player, map, input));
			return false;
		case "help":
			help(i);
			break;
		case "exit":
		case "quit":
			exit();
			break;
		case "logout":
			logout(i);
			break;
		default:
			invalidInput(i);
			break;
		}
		players.stream().forEach(p -> p.update());
		return turnTaken;
	}

	/**
	 * Informs player that their input is invalid.
	 * 
	 * @param i
	 *            index of player
	 */
	private void invalidInput(int i) {
		players.get(i)
				.print("Hey " + players.get(i).getPlayer().getName() + ", "
						+ "we all make mistakes. We all type things wrong.\n"
						+ "That's just straight up an invalid command. You gotta try harder!");
	}

	/**
	 * @param playerIndex
	 */
	private void help(int playerIndex) {
		String help = "";
		for (int i = 0; i < Action.values().length; i++) {
			help += Action.values()[i].getMessage() + "\n";
		}
		players.get(playerIndex).print(help);
	}

	/**
	 * Frees resources in TextInterface and tells engine to stop running.
	 * 
	 */
	private void exit() {
		players.stream().forEach(p -> p.shutdown());
		running = false;
		map.joinAgents();
		System.exit(0);
	}

	private void updateGuis() {
		for (Gui g : players) {
			synchronized (g) {
				g.update();
			}
		}
	}

	/**
	 * Lets a single player quit without shutting down game. The game will shut
	 * down however, if the player is the only player.
	 * 
	 * @param i
	 *            index of player logging out
	 */
	private void logout(int i) {
		players.get(i).shutdown();
		map.removeMob(players.get(i).getPlayer());
		players.remove(i);
		players.trimToSize();
		numPlayers--;
		if (players.isEmpty()) {
			exit();
		}
	}

	/**
	 * the game's main engine
	 * 
	 */
	private static Engine engine;
	/**
	 * the list of player-interface pairs
	 * 
	 */
	private ArrayList<Gui> players = new ArrayList<>();
	/**
	 * the engine's map
	 * 
	 */
	private static Map map;
	/**
	 * the current number of players
	 * 
	 */
	private volatile int numPlayers = 0;

	private boolean running = false;
}

