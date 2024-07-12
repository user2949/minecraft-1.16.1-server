package it.unimi.dsi.fastutil.longs;

import java.util.PrimitiveIterator.OfLong;
import java.util.function.Consumer;

public interface LongIterator extends OfLong {
	long nextLong();

	@Deprecated
	default Long next() {
		return this.nextLong();
	}

	@Deprecated
	default void forEachRemaining(Consumer<? super Long> action) {
		this.forEachRemaining(action::accept);
	}

	default int skip(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Argument must be nonnegative: " + n);
		} else {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextLong();
			}

			return n - i - 1;
		}
	}
}
