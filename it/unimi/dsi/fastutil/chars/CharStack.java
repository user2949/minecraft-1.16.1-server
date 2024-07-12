package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Stack;

public interface CharStack extends Stack<Character> {
	void push(char character);

	char popChar();

	char topChar();

	char peekChar(int integer);

	@Deprecated
	default void push(Character o) {
		this.push(o.charValue());
	}

	@Deprecated
	default Character pop() {
		return this.popChar();
	}

	@Deprecated
	default Character top() {
		return this.topChar();
	}

	@Deprecated
	default Character peek(int i) {
		return this.peekChar(i);
	}
}
