package it.unimi.dsi.fastutil.longs;

import java.util.Objects;
import java.util.function.Consumer;

public interface LongIterable extends Iterable<Long> {
	LongIterator iterator();

	default void forEach(java.util.function.LongConsumer action) {
		Objects.requireNonNull(action);
		LongIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			action.accept(iterator.nextLong());
		}
	}

	@Deprecated
	default void forEach(Consumer<? super Long> action) {
		this.forEach(action::accept);
	}
}
