package com.breakfast.util;

/**
 * Utility pair class.
 * 
 * @author Zoe Jacobson and Julian Fernandez
 *
 * @param <F>
 *            first class
 * @param <S>
 *            second class
 */
public class Pair<F, S> {
	public F first;
	public S second;

	public Pair(F f, S s) {
		first = f;
		second = s;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair) {
			Pair other = (Pair) obj;
			return first.equals(other.first) && second.equals(other.second);
		}
		return false;
	}
}
