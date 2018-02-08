package com.breakfast.core.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.breakfast.core.Engine;
import com.breakfast.core.map.Room;
import com.breakfast.core.mob.Player;

/**
 * A gui represents the visual interface that users will interact with.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
/**
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
/**
 * @author Zoe Jacobson and Julian Fernandez
 *
 */
public class Gui extends GameInterface implements Game, Observer {
	/**
	 * Attaches Gui to engine, and player to Gui
	 * 
	 */
	public Gui(Engine engine, Player player) {
		this.player = player;
		this.addObserver(engine);
	}

	public void init() {
		buttonWindow = new ButtonWindow(this, player);
		inputField.requestFocus();
		startup();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.breakfast.core.input.Game#print(java.lang.String)
	 */
	@Override
	public void print(String output) {
		message.setText(output);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.breakfast.core.input.Game#printSurroundings(java.lang.String)
	 */
	@Override
	public synchronized void printSurroundings(String output) {
		output = output + "\nWhat do you wanna do now?";
		surroundings.setText(output);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.breakfast.core.input.Game#startup()
	 */
	@Override
	public void startup() {
		message.setForeground(Color.WHITE);
		message.setBackground(Color.DARK_GRAY);
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setEditable(false);
		message.setFont(font);
		surroundings.setLineWrap(true);
		surroundings.setWrapStyleWord(true);
		surroundings.setEditable(false);
		surroundings.setForeground(Color.WHITE);
		surroundings.setBackground(Color.DARK_GRAY);
		surroundings.setFont(font);
		inventoryField.setForeground(Color.WHITE);
		inventoryField.setBackground(Color.DARK_GRAY);
		inventoryField.setEditable(false);
		inventoryField.setFont(font);
		inventoryField.setLineWrap(true);
		inventoryField.setWrapStyleWord(true);
		setupInputField();
		setupImage();
		setupPanels();
		setupFrame();
		surroundings.setBorder(new EmptyBorder(10, 10, 10, 10));
		message.setBorder(new EmptyBorder(10, 10, 10, 10));
		imagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		inventoryField.setBorder(new EmptyBorder(10, 10, 10, 10));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		inputField.requestFocusInWindow();
		update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.breakfast.core.input.Game#shutdown()
	 */
	@Override
	public String shutdown() {
		frame.setVisible(false);
		frame.dispose();
		return "Goodbye";
	}
	
	public void focus() {
		frame.requestFocus();
		inputField.requestFocusInWindow();
	}

	/**
	 * Configures various parts of the JFrame
	 * 
	 */
	private void setupFrame() {
		frame.setBackground(new Color(106, 0, 85));
		frame.getContentPane().setBackground(new Color(106, 0, 85));
		frame.add(textPanel, BorderLayout.WEST);
		frame.add(infoPanel, BorderLayout.SOUTH);
		frame.add(imagePanel, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1024, 720));
	}

	private void setupImage() {
		imageLabel = new JLabel(new ImageIcon("res/logo2.jpg"));
	}

	/**
	 * Configures various panels of the JFrame
	 * 
	 */
	private void setupPanels() {
		messagePane = new JScrollPane(message);
		surroundingsPane = new JScrollPane(surroundings);
		inventoryPane = new JScrollPane(inventoryField);

		textPanel.add(inventoryPane);
		textPanel.add(surroundingsPane);
		textPanel.add(messagePane);

		layout = new BoxLayout(textPanel, BoxLayout.Y_AXIS);
		textPanel.setLayout(layout);

		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(new JScrollPane(inputField));

		imagePanel.add(imageLabel);
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
		imagePanel.setBackground(new Color(106, 0, 85));
		
		player.addObserver(mapDisplay);
		imagePanel.add(mapDisplay);
	}

	/**
	 * Configures input field for proper text entry.
	 * 
	 */
	private void setupInputField() {
		inputField.setForeground(Color.WHITE);
		inputField.setBackground(Color.DARK_GRAY);
		inputField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent key) {
				switch (key.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					key.consume();
					history.add(inputField.getText());
					historyIndex = 0;
					if (player.getName() == "" || player.getName() == null) {
						update();
						player.setName(inputField.getText());
						buttonWindow.init();
						buttonWindow.setLocation(frame.getX() + frame.getWidth() / 3, frame.getY() + frame.getHeight());
						print("So what do you wanna do now, " + player.getName().trim() + "?");
					} else {
						setChanged();
						notifyObservers(inputField.getText().trim().split(" "));
					}
					update();
					inputField.setText(null);
					inputField.setCaretPosition(0);
					break;
				case KeyEvent.VK_UP:
					key.consume();
					if (!history.isEmpty()) {
						inputField.setText(history.get(history.size() - 1 - historyIndex));
						if (historyIndex < history.size() - 1) {
							historyIndex++;
						}
					}
					break;
				case KeyEvent.VK_DOWN:
					key.consume();
					if (!history.isEmpty()) {
						if (historyIndex > 0) {
							historyIndex--;
						}
						inputField.setText(history.get(history.size() - 1 - historyIndex));
						if (historyIndex == 0) {
							historyIndex++;
						}
					}
					break;
				default:
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent key) {
			}

			@Override
			public void keyTyped(KeyEvent key) {
			}
		});
		inputField.setColumns(10);
		inputField.setEditable(true);
		inputField.setBorder(new EmptyBorder(10, 10, 10, 10));
		inputField.setFont(font);
	}

	public void updateImage(Room room) {
		imageLabel.setIcon(room.getImage());
	}

	public void updateInv(Player player) {
		synchronized (player) {
			inventoryField.setText("Your Inventory:\n" + player.printInventory() + 
					"\nYou have " + player.getMoney() + " dollars." +
					"\nYou are carrying " + player.getCurrentWeight() + "/" + player.getMaxWeight());
		}
	}

	public ButtonWindow getButtonWindow() {
		return buttonWindow;
	}

	public void update() {
		updateImage(player.getRoom());
		updateInv(player);
		printSurroundings(player.look(Engine.getMap()));
	}

	@Override
	public void update(Observable obj, Object rawRoom) {
		update();
	}

	public Player getPlayer() {
		return player;
	}

	private Player player;
	
	private MapDisplay mapDisplay = new MapDisplay();

	private ButtonWindow buttonWindow;
	/**
	 * input history
	 */
	private LinkedList<String> history = new LinkedList<String>();
	/**
	 * input history index
	 */
	private int historyIndex = 0;
	/**
	 * panel that contains message and surroundings text areas
	 */
	private JPanel textPanel = new JPanel();
	/**
	 * panel that contains information about surroundings
	 */
	private JPanel infoPanel = new JPanel();
	/**
	 * jframe that game takes place in
	 */
	private JFrame frame = new JFrame("Breakfast & Bed");
	/**
	 * jframe's layout
	 */
	private BoxLayout layout;
	/**
	 * all text will be printed with this font
	 */
	private Font font = new Font(Font.DIALOG, Font.ROMAN_BASELINE, 16);
	/**
	 * container for messages
	 */
	private JScrollPane messagePane;
	/**
	 * container for surroundings messages
	 */
	private JScrollPane surroundingsPane;
	/**
	 * message text area
	 */
	private JTextArea message = new JTextArea(10, 30);
	/**
	 * surroundings text area
	 */
	private JTextArea surroundings = new JTextArea(10, 30);
	/**
	 * container for image label
	 */
	private JPanel imagePanel = new JPanel();
	/**
	 * container for image
	 */
	private JLabel imageLabel = new JLabel();
	/**
	 * input text area
	 */
	private JTextArea inputField = new JTextArea();
	/**
	 * inventory text area
	 */
	private JTextArea inventoryField = new JTextArea(10, 30);
	private JScrollPane inventoryPane;
}
