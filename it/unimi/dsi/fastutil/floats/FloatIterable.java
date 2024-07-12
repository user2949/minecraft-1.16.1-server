package it.unimi.dsi.fastutil.floats;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

public interface FloatIterable extends Iterable<Float> {
	FloatIterator iterator();

	default void forEach(DoubleConsumer action) {
		Objects.requireNonNull(action);
		FloatIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			action.accept((double)iterator.nextFloat());
		}
	}

	@Deprecated
	default void forEach(Consumer<? super Float> action) {
		this.forEach(new DoubleConsumer() {
			public void accept(double key) {
				action.accept((float)key);
			}
		});
	}
}
