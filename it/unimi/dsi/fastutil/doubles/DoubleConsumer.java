package it.unimi.dsi.fastutil.doubles;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface DoubleConsumer extends Consumer<Double>, java.util.function.DoubleConsumer {
	@Deprecated
	default void accept(Double t) {
		this.accept(t.doubleValue());
	}

	default DoubleConsumer andThen(java.util.function.DoubleConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept(t);
		};
	}

	@Deprecated
	default Consumer<Double> andThen(Consumer<? super Double> after) {
		return super.andThen(after);
	}
}
