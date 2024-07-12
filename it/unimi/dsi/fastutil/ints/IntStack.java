package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Stack;

public interface IntStack extends Stack<Integer> {
	void push(int integer);

	int popInt();

	int topInt();

	int peekInt(int integer);

	@Deprecated
	default void push(Integer o) {
		this.push(o.intValue());
	}

	@Deprecated
	default Integer pop() {
		return this.popInt();
	}

	@Deprecated
	default Integer top() {
		return this.topInt();
	}

	@Deprecated
	default Integer peek(int i) {
		return this.peekInt(i);
	}
}
