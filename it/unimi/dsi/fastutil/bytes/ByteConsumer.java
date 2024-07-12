package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

@FunctionalInterface
public interface ByteConsumer extends Consumer<Byte>, IntConsumer {
	void accept(byte byte1);

	@Deprecated
	default void accept(int t) {
		this.accept(SafeMath.safeIntToByte(t));
	}

	@Deprecated
	default void accept(Byte t) {
		this.accept(t.byteValue());
	}

	default ByteConsumer andThen(ByteConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept(t);
		};
	}

	@Deprecated
	default ByteConsumer andThen(IntConsumer after) {
		Objects.requireNonNull(after);
		return t -> {
			this.accept(t);
			after.accept(t);
		};
	}

	@Deprecated
	default Consumer<Byte> andThen(Consumer<? super Byte> after) {
		return super.andThen(after);
	}
}
