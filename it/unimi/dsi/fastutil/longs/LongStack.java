package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Stack;

public interface LongStack extends Stack<Long> {
	void push(long long1);

	long popLong();

	long topLong();

	long peekLong(int integer);

	@Deprecated
	default void push(Long o) {
		this.push(o.longValue());
	}

	@Deprecated
	default Long pop() {
		return this.popLong();
	}

	@Deprecated
	default Long top() {
		return this.topLong();
	}

	@Deprecated
	default Long peek(int i) {
		return this.peekLong(i);
	}
}
