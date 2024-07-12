package it.unimi.dsi.fastutil.chars;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public interface CharIterator extends Iterator<Character> {
	char nextChar();

	@Deprecated
	default Character next() {
		return this.nextChar();
	}

	default void forEachRemaining(CharConsumer action) {
		Objects.requireNonNull(action);

		while (this.hasNext()) {
			action.accept(this.nextChar());
		}
	}

	@Deprecated
	default void forEachRemaining(Consumer<? super Character> action) {
		this.forEachRemaining(action::accept);
	}

	default int skip(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Argument must be nonnegative: " + n);
		} else {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextChar();
			}

			return n - i - 1;
		}
	}
}
