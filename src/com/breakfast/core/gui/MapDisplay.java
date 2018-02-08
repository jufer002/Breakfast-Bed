package com.breakfast.core.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.breakfast.core.Engine;
import com.breakfast.core.mob.Player;
import com.breakfast.util.Pair;

/**
 * Created by Julian Fernandez on 10/29/2016.
 */
public class MapDisplay extends JPanel implements Observer {
	private static final long serialVersionUID = -6046980842178224494L;
	
	private static Color BACKGROUND = new Color(161, 161, 211, 200);
	private static Color FOREGROUND = new Color(106, 0, 85);
	private static Color CURRENT = new Color(203, 191, 201, 255);
	private static Color DELIVERY_ROOM = new Color(212, 180, 204, 150);
	private static Color VISITED = new Color(107, 89, 103, 255);

	public MapDisplay() {
		setBackground(BACKGROUND);
		setForeground(FOREGROUND);

		addRoom(0, 0);
	}

	public void addRoom(int x, int y) {
		Pair<Integer, Integer> pair = new Pair<>(x, y);
		if (!roomCords.contains(pair)) {
			roomCords.add(pair);
		}
	}

	public void drawRooms(Graphics2D g) {
		int oldX = getWidth() / 2 + currX * 2 * scale, oldY = getHeight() / 2 - currY * 2 * scale;
		int x, y;
		for (Pair<Integer, Integer> loc : roomCords) {
			x = getWidth() / 2 + loc.first * 2 * scale;
			y = getHeight() / 2 - loc.second * 2 * scale;

			if (loc.first == currX && loc.second == currY) {
				g.setColor(CURRENT);
			} else {
				if (Engine.getMap().getRoom(loc.first, loc.second).isDeliveryRoom()) {
					g.setColor(DELIVERY_ROOM);
				} else {
					g.setColor(VISITED);
				}
			}
			
			g.fillRect(x, y, scale, scale);
			//connectRoom(g, x, y, oldX, oldY);
			oldX = x;
			oldY = y;
		}
	}

	private void connectRoom(Graphics2D g, int x, int y, int oldX, int oldY) {
		if (roomCords.size() == 1) {
			return;
		}
		g.setColor(BACKGROUND);
		if (oldY > y) {
			g.drawLine(x + scale / 2, oldY, x + scale / 2, y + scale);
		}
		if (oldX > x) {
			g.drawLine(oldX, oldY + scale / 2, x + scale, oldY + scale / 2);
		} else if (oldX < x) {
			g.drawLine(oldX, oldY + scale / 2, x + scale, oldY + scale / 2);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.fillRect(0, 0, getWidth(), getHeight());

		drawRooms(g2);

		g2.dispose();
	}

	@Override
	public void update(Observable obj, Object arg) {
		Player player = (Player) obj;
		if (currX != player.getX() || currY != player.getY()) {
			addRoom(player.getX(), player.getY());
			currX = player.getX();
			currY = player.getY();
			repaint();
		}
	}

	private LinkedList<Pair<Integer, Integer>> roomCords = new LinkedList<>();
	private int scale = 10;
	private int currX = 0, currY = 0;
}
