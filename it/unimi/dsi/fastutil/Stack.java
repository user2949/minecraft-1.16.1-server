package it.unimi.dsi.fastutil;

public interface Stack<K> {
	void push(K object);

	K pop();

	boolean isEmpty();

	default K top() {
		return this.peek(0);
	}

	default K peek(int i) {
		throw new UnsupportedOperationException();
	}
}
