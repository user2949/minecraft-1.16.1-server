package it.unimi.dsi.fastutil.booleans;

import java.util.Objects;
import java.util.function.Consumer;

public interface BooleanIterable extends Iterable<Boolean> {
	BooleanIterator iterator();

	default void forEach(BooleanConsumer action) {
		Objects.requireNonNull(action);
		BooleanIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			action.accept(iterator.nextBoolean());
		}
	}

	@Deprecated
	default void forEach(Consumer<? super Boolean> action) {
		this.forEach(action::accept);
	}
}
