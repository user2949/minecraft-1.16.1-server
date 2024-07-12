package it.unimi.dsi.fastutil.chars;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public interface CharIterable extends Iterable<Character> {
	CharIterator iterator();

	default void forEach(IntConsumer action) {
		Objects.requireNonNull(action);
		CharIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			action.accept(iterator.nextChar());
		}
	}

	@Deprecated
	default void forEach(Consumer<? super Character> action) {
		this.forEach(new IntConsumer() {
			public void accept(int key) {
				action.accept((char)key);
			}
		});
	}
}
