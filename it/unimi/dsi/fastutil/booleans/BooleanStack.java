package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.Stack;

public interface BooleanStack extends Stack<Boolean> {
	void push(boolean boolean1);

	boolean popBoolean();

	boolean topBoolean();

	boolean peekBoolean(int integer);

	@Deprecated
	default void push(Boolean o) {
		this.push(o.booleanValue());
	}

	@Deprecated
	default Boolean pop() {
		return this.popBoolean();
	}

	@Deprecated
	default Boolean top() {
		return this.topBoolean();
	}

	@Deprecated
	default Boolean peek(int i) {
		return this.peekBoolean(i);
	}
}
