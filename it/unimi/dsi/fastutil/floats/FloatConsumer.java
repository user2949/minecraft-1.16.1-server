package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

@FunctionalInterface
public interface FloatConsumer extends Consumer<Float>, DoubleConsumer {
	void accept(float float1);

	@Deprecated
	default void accept(double t) {
		this.accept(SafeMath.safeDoubleToFloat(t));
	}

	@Deprecated
	default void accept(Float t) {
		this.accept(t.floatValue());
	}

	default FloatConsumer andThen(FloatConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept(t);
		};
	}

	@Deprecated
	default FloatConsumer andThen(DoubleConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept((double)t);
		};
	}

	@Deprecated
	default Consumer<Float> andThen(Consumer<? super Float> after) {
		return super.andThen(after);
	}
}
