package it.unimi.dsi.fastutil.floats;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public interface FloatIterator extends Iterator<Float> {
	float nextFloat();

	@Deprecated
	default Float next() {
		return this.nextFloat();
	}

	default void forEachRemaining(FloatConsumer action) {
		Objects.requireNonNull(action);

		while (this.hasNext()) {
			action.accept(this.nextFloat());
		}
	}

	@Deprecated
	default void forEachRemaining(Consumer<? super Float> action) {
		this.forEachRemaining(action::accept);
	}

	default int skip(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Argument must be nonnegative: " + n);
		} else {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextFloat();
			}

			return n - i - 1;
		}
	}
}
