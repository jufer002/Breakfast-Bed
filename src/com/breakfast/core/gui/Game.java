package com.breakfast.core.gui;


/**
 * Interface that GameInterface's must implement
 * 
 * @author Zoe Jacobson and Julian Fernandez
 * 
 */
public interface Game {


	/**
	 * Welcomes player and gets their name.
	 * 
	 * @return first player's name
	 */
	public void startup();
	
	public void init();

	/**
	 * Frees resources
	 * 
	 * @return goodbye message
	 */
	public String shutdown();

	/**
	 * Prints output to the player
	 * 
	 * @param output
	 */
	public void print(String output);

	/**
	 * Prints surroundings to player
	 * 
	 * @param output
	 */
	public void printSurroundings(String output);
}
