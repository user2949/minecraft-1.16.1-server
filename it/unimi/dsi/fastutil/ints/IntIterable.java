package it.unimi.dsi.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;

public interface IntIterable extends Iterable<Integer> {
	IntIterator iterator();

	default void forEach(java.util.function.IntConsumer action) {
		Objects.requireNonNull(action);
		IntIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			action.accept(iterator.nextInt());
		}
	}

	@Deprecated
	default void forEach(Consumer<? super Integer> action) {
		this.forEach(action::accept);
	}
}
