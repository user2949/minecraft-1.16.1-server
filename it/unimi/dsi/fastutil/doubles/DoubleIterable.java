package it.unimi.dsi.fastutil.doubles;

import java.util.Objects;
import java.util.function.Consumer;

public interface DoubleIterable extends Iterable<Double> {
	DoubleIterator iterator();

	default void forEach(java.util.function.DoubleConsumer action) {
		Objects.requireNonNull(action);
		DoubleIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			action.accept(iterator.nextDouble());
		}
	}

	@Deprecated
	default void forEach(Consumer<? super Double> action) {
		this.forEach(action::accept);
	}
}
