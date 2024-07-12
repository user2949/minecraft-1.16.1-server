package it.unimi.dsi.fastutil.shorts;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public interface ShortIterable extends Iterable<Short> {
	ShortIterator iterator();

	default void forEach(IntConsumer action) {
		Objects.requireNonNull(action);
		ShortIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			action.accept(iterator.nextShort());
		}
	}

	@Deprecated
	default void forEach(Consumer<? super Short> action) {
		this.forEach(new IntConsumer() {
			public void accept(int key) {
				action.accept((short)key);
			}
		});
	}
}
