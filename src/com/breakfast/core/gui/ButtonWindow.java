package com.breakfast.core.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.breakfast.core.Engine;
import com.breakfast.core.mob.Delivery;
import com.breakfast.core.mob.Mob;
import com.breakfast.core.mob.Player;
import com.breakfast.util.Pair;

/**
 * An encapsulation of a button interface to complement the main GUI.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class ButtonWindow implements Observer {
	public ButtonWindow(Gui gui, Player newPlayer) {
		player = newPlayer;
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		for (int i = 0; i < directionals.length; i++) {
			directionals[i] = Direction.values()[i].getButton();
			addListener(gui, directionals[i], Direction.values()[i]);
			panel.add(directionals[i]);
		}
		player.addObserver(this);
		gui.addObserver(this);
	}

	public void init() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		bar.setVisible(false);
		frame.add(bar, BorderLayout.SOUTH);
		frame.pack();
		frame.setFocusable(false);
		frame.setAutoRequestFocus(false);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void addListener(Gui gui, JButton button, Direction d) {
		switch (d) {
		case N:
			button.addActionListener(e -> {
				moveNorth(gui);
				gui.update();
				gui.focus();
			});
			break;
		case E:
			button.addActionListener(e -> {
				moveEast(gui);
				gui.update();
				gui.focus();
			});
			break;
		case S:
			button.addActionListener(e -> {
				moveSouth(gui);
				gui.update();
				gui.focus();
			});
			break;
		case W:
			button.addActionListener(e -> {
				moveWest(gui);
				gui.update();
				gui.focus();
			});
			break;
		default:
			System.err.println("Invalid direction in ButtonWindow::addListener()");
			break;
		}
	}

	public void setLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	public void moveNorth(Gui gui) {
		Pair<String, Boolean> action = player.move(Engine.getMap(), Mob.Direction.N);
		gui.print(action.first);
		if (action.second) {
			player.takeTurn();
		}
	}

	public void moveEast(Gui gui) {
		Pair<String, Boolean> action = player.move(Engine.getMap(), Mob.Direction.E);
		gui.print(action.first);
		if (action.second) {
			player.takeTurn();
		}
		
	}

	public void moveSouth(Gui gui) {
		Pair<String, Boolean> action = player.move(Engine.getMap(), Mob.Direction.S);
		gui.print(action.first);
		if (action.second) {
			player.takeTurn();
		}
	}

	public void moveWest(Gui gui) {
		Pair<String, Boolean> action = player.move(Engine.getMap(), Mob.Direction.W);
		gui.print(action.first);
		if (action.second) {
			player.takeTurn();
		}
	}

	private final String TITLE = "Button Window";
	private JFrame frame = new JFrame(TITLE);
	private JPanel panel = new JPanel();
	private JButton[] directionals = new JButton[4];
	private JProgressBar bar = new JProgressBar(0, Delivery.TURNS);
	private Player player;

	private enum Direction {
		N("north"), E("east"), S("south"), W("west");
		private Direction(String msg) {
			this.msg = msg;
		}

		public JButton getButton() {
			return new JButton(msg);
		}

		private final String msg;
	}

	@Override
	public void update(Observable obj, Object arg) {
		bar.setVisible(player.getDelivery() != null);
		if (player.getDelivery() != null) {
			bar.setValue(Delivery.TURNS - player.getDelivery().turnsRemaining());
			bar.setStringPainted(true);
			frame.pack();
		}
	}
}
