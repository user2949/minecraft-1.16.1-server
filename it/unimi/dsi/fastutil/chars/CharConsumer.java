package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

@FunctionalInterface
public interface CharConsumer extends Consumer<Character>, IntConsumer {
	void accept(char character);

	@Deprecated
	default void accept(int t) {
		this.accept(SafeMath.safeIntToChar(t));
	}

	@Deprecated
	default void accept(Character t) {
		this.accept(t.charValue());
	}

	default CharConsumer andThen(CharConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept(t);
		};
	}

	@Deprecated
	default CharConsumer andThen(IntConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept(t);
		};
	}

	@Deprecated
	default Consumer<Character> andThen(Consumer<? super Character> after) {
		return super.andThen(after);
	}
}
