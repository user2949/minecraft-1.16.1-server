package it.unimi.dsi.fastutil.bytes;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public interface ByteIterable extends Iterable<Byte> {
	ByteIterator iterator();

	default void forEach(IntConsumer action) {
		Objects.requireNonNull(action);
		ByteIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			action.accept(iterator.nextByte());
		}
	}

	@Deprecated
	default void forEach(Consumer<? super Byte> action) {
		this.forEach(new IntConsumer() {
			public void accept(int key) {
				action.accept((byte)key);
			}
		});
	}
}
