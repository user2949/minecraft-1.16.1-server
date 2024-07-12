package it.unimi.dsi.fastutil.ints;

import java.util.PrimitiveIterator.OfInt;
import java.util.function.Consumer;

public interface IntIterator extends OfInt {
	int nextInt();

	@Deprecated
	default Integer next() {
		return this.nextInt();
	}

	@Deprecated
	default void forEachRemaining(Consumer<? super Integer> action) {
		this.forEachRemaining(action::accept);
	}

	default int skip(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Argument must be nonnegative: " + n);
		} else {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextInt();
			}

			return n - i - 1;
		}
	}
}
