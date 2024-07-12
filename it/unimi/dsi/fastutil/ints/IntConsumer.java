package it.unimi.dsi.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface IntConsumer extends Consumer<Integer>, java.util.function.IntConsumer {
	@Deprecated
	default void accept(Integer t) {
		this.accept(t.intValue());
	}

	default IntConsumer andThen(java.util.function.IntConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept(t);
		};
	}

	@Deprecated
	default Consumer<Integer> andThen(Consumer<? super Integer> after) {
		return super.andThen(after);
	}
}
