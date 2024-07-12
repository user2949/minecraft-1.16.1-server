package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet.Indexed;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Collection;
import javax.annotation.Nullable;

@GwtCompatible(
	serializable = true
)
class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
	static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset<>(ImmutableList.of());
	private final transient com.google.common.collect.Multisets.ImmutableEntry<E>[] entries;
	private final transient com.google.common.collect.Multisets.ImmutableEntry<E>[] hashTable;
	private final transient int size;
	private final transient int hashCode;
	@LazyInit
	private transient ImmutableSet<E> elementSet;

	RegularImmutableMultiset(Collection<? extends Entry<? extends E>> entries) {
		int distinct = entries.size();
		com.google.common.collect.Multisets.ImmutableEntry<E>[] entryArray = new com.google.common.collect.Multisets.ImmutableEntry[distinct];
		if (distinct == 0) {
			this.entries = entryArray;
			this.hashTable = null;
			this.size = 0;
			this.hashCode = 0;
			this.elementSet = ImmutableSet.of();
		} else {
			int tableSize = Hashing.closedTableSize(distinct, 1.0);
			int mask = tableSize - 1;
			com.google.common.collect.Multisets.ImmutableEntry<E>[] hashTable = new com.google.common.collect.Multisets.ImmutableEntry[tableSize];
			int index = 0;
			int hashCode = 0;
			long size = 0L;

			for (Entry<? extends E> entry : entries) {
				E element = Preconditions.checkNotNull((E)entry.getElement());
				int count = entry.getCount();
				int hash = element.hashCode();
				int bucket = Hashing.smear(hash) & mask;
				com.google.common.collect.Multisets.ImmutableEntry<E> bucketHead = hashTable[bucket];
				com.google.common.collect.Multisets.ImmutableEntry<E> newEntry;
				if (bucketHead == null) {
					boolean canReuseEntry = entry instanceof com.google.common.collect.Multisets.ImmutableEntry
						&& !(entry instanceof RegularImmutableMultiset.NonTerminalEntry);
					newEntry = canReuseEntry
						? (com.google.common.collect.Multisets.ImmutableEntry)entry
						: new com.google.common.collect.Multisets.ImmutableEntry<>(element, count);
				} else {
					newEntry = new RegularImmutableMultiset.NonTerminalEntry<>(element, count, bucketHead);
				}

				hashCode += hash ^ count;
				entryArray[index++] = newEntry;
				hashTable[bucket] = newEntry;
				size += (long)count;
			}

			this.entries = entryArray;
			this.hashTable = hashTable;
			this.size = Ints.saturatedCast(size);
			this.hashCode = hashCode;
		}
	}

	@Override
	boolean isPartialView() {
		return false;
	}

	@Override
	public int count(@Nullable Object element) {
		com.google.common.collect.Multisets.ImmutableEntry<E>[] hashTable = this.hashTable;
		if (element != null && hashTable != null) {
			int hash = Hashing.smearedHash(element);
			int mask = hashTable.length - 1;

			for (com.google.common.collect.Multisets.ImmutableEntry<E> entry = hashTable[hash & mask]; entry != null; entry = entry.nextInBucket()) {
				if (Objects.equal(element, entry.getElement())) {
					return entry.getCount();
				}
			}

			return 0;
		} else {
			return 0;
		}
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public ImmutableSet<E> elementSet() {
		ImmutableSet<E> result = this.elementSet;
		return result == null ? (this.elementSet = new RegularImmutableMultiset.ElementSet()) : result;
	}

	@Override
	Entry<E> getEntry(int index) {
		return this.entries[index];
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	private final class ElementSet extends Indexed<E> {
		private ElementSet() {
		}

		@Override
		E get(int index) {
			return RegularImmutableMultiset.this.entries[index].getElement();
		}

		@Override
		public boolean contains(@Nullable Object object) {
			return RegularImmutableMultiset.this.contains(object);
		}

		@Override
		boolean isPartialView() {
			return true;
		}

		public int size() {
			return RegularImmutableMultiset.this.entries.length;
		}
	}

	private static final class NonTerminalEntry<E> extends com.google.common.collect.Multisets.ImmutableEntry<E> {
		private final com.google.common.collect.Multisets.ImmutableEntry<E> nextInBucket;

		NonTerminalEntry(E element, int count, com.google.common.collect.Multisets.ImmutableEntry<E> nextInBucket) {
			super(element, count);
			this.nextInBucket = nextInBucket;
		}

		@Override
		public com.google.common.collect.Multisets.ImmutableEntry<E> nextInBucket() {
			return this.nextInBucket;
		}
	}
}
