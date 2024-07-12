package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Collection;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public interface CharCollection extends Collection<Character>, CharIterable {
	@Override
	CharIterator iterator();

	boolean add(char character);

	boolean contains(char character);

	boolean rem(char character);

	@Deprecated
	default boolean add(Character key) {
		return this.add(key.charValue());
	}

	@Deprecated
	default boolean contains(Object key) {
		return key == null ? false : this.contains(((Character)key).charValue());
	}

	@Deprecated
	default boolean remove(Object key) {
		return key == null ? false : this.rem((Character)key);
	}

	char[] toCharArray();

	@Deprecated
	char[] toCharArray(char[] arr);

	char[] toArray(char[] arr);

	boolean addAll(CharCollection charCollection);

	boolean containsAll(CharCollection charCollection);

	boolean removeAll(CharCollection charCollection);

	@Deprecated
	default boolean removeIf(Predicate<? super Character> filter) {
		return this.removeIf((IntPredicate)(key -> filter.test(SafeMath.safeIntToChar(key))));
	}

	default boolean removeIf(IntPredicate filter) {
		boolean removed = false;
		CharIterator each = this.iterator();

		while (each.hasNext()) {
			if (filter.test(each.nextChar())) {
				each.remove();
				removed = true;
			}
		}

		return removed;
	}

	boolean retainAll(CharCollection charCollection);
}
