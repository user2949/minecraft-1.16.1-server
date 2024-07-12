package it.unimi.dsi.fastutil.booleans;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface BooleanConsumer extends Consumer<Boolean> {
	void accept(boolean boolean1);

	@Deprecated
	default void accept(Boolean t) {
		this.accept(t.booleanValue());
	}

	default BooleanConsumer andThen(BooleanConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept(t);
		};
	}

	@Deprecated
	default Consumer<Boolean> andThen(Consumer<? super Boolean> after) {
		return super.andThen(after);
	}
}
