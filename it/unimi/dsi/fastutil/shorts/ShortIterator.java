package it.unimi.dsi.fastutil.shorts;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public interface ShortIterator extends Iterator<Short> {
	short nextShort();

	@Deprecated
	default Short next() {
		return this.nextShort();
	}

	default void forEachRemaining(ShortConsumer action) {
		Objects.requireNonNull(action);

		while (this.hasNext()) {
			action.accept(this.nextShort());
		}
	}

	@Deprecated
	default void forEachRemaining(Consumer<? super Short> action) {
		this.forEachRemaining(action::accept);
	}

	default int skip(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Argument must be nonnegative: " + n);
		} else {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextShort();
			}

			return n - i - 1;
		}
	}
}
