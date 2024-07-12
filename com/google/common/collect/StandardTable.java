package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps.IteratorBasedAbstractMap;
import com.google.common.collect.Maps.ViewCachingAbstractMap;
import com.google.common.collect.Sets.ImprovedAbstractSet;
import com.google.common.collect.Table.Cell;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
class StandardTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
	@GwtTransient
	final Map<R, Map<C, V>> backingMap;
	@GwtTransient
	final Supplier<? extends Map<C, V>> factory;
	private transient Set<C> columnKeySet;
	private transient Map<R, Map<C, V>> rowMap;
	private transient StandardTable<R, C, V>.ColumnMap columnMap;
	private static final long serialVersionUID = 0L;

	StandardTable(Map<R, Map<C, V>> backingMap, Supplier<? extends Map<C, V>> factory) {
		this.backingMap = backingMap;
		this.factory = factory;
	}

	@Override
	public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
		return rowKey != null && columnKey != null && super.contains(rowKey, columnKey);
	}

	@Override
	public boolean containsColumn(@Nullable Object columnKey) {
		if (columnKey == null) {
			return false;
		} else {
			for (Map<C, V> map : this.backingMap.values()) {
				if (Maps.safeContainsKey(map, columnKey)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean containsRow(@Nullable Object rowKey) {
		return rowKey != null && Maps.safeContainsKey(this.backingMap, rowKey);
	}

	@Override
	public boolean containsValue(@Nullable Object value) {
		return value != null && super.containsValue(value);
	}

	@Override
	public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
		return rowKey != null && columnKey != null ? super.get(rowKey, columnKey) : null;
	}

	@Override
	public boolean isEmpty() {
		return this.backingMap.isEmpty();
	}

	@Override
	public int size() {
		int size = 0;

		for (Map<C, V> map : this.backingMap.values()) {
			size += map.size();
		}

		return size;
	}

	@Override
	public void clear() {
		this.backingMap.clear();
	}

	private Map<C, V> getOrCreate(R rowKey) {
		Map<C, V> map = (Map<C, V>)this.backingMap.get(rowKey);
		if (map == null) {
			map = (Map<C, V>)this.factory.get();
			this.backingMap.put(rowKey, map);
		}

		return map;
	}

	@CanIgnoreReturnValue
	@Override
	public V put(R rowKey, C columnKey, V value) {
		Preconditions.checkNotNull(rowKey);
		Preconditions.checkNotNull(columnKey);
		Preconditions.checkNotNull(value);
		return (V)this.getOrCreate(rowKey).put(columnKey, value);
	}

	@CanIgnoreReturnValue
	@Override
	public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
		if (rowKey != null && columnKey != null) {
			Map<C, V> map = Maps.safeGet(this.backingMap, rowKey);
			if (map == null) {
				return null;
			} else {
				V value = (V)map.remove(columnKey);
				if (map.isEmpty()) {
					this.backingMap.remove(rowKey);
				}

				return value;
			}
		} else {
			return null;
		}
	}

	@CanIgnoreReturnValue
	private Map<R, V> removeColumn(Object column) {
		Map<R, V> output = new LinkedHashMap();
		Iterator<Entry<R, Map<C, V>>> iterator = this.backingMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<R, Map<C, V>> entry = (Entry<R, Map<C, V>>)iterator.next();
			V value = (V)((Map)entry.getValue()).remove(column);
			if (value != null) {
				output.put(entry.getKey(), value);
				if (((Map)entry.getValue()).isEmpty()) {
					iterator.remove();
				}
			}
		}

		return output;
	}

	private boolean containsMapping(Object rowKey, Object columnKey, Object value) {
		return value != null && value.equals(this.get(rowKey, columnKey));
	}

	private boolean removeMapping(Object rowKey, Object columnKey, Object value) {
		if (this.containsMapping(rowKey, columnKey, value)) {
			this.remove(rowKey, columnKey);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Set<Cell<R, C, V>> cellSet() {
		return super.cellSet();
	}

	@Override
	Iterator<Cell<R, C, V>> cellIterator() {
		return new StandardTable.CellIterator();
	}

	@Override
	Spliterator<Cell<R, C, V>> cellSpliterator() {
		return CollectSpliterators.flatMap(
			this.backingMap.entrySet().spliterator(),
			rowEntry -> CollectSpliterators.map(
					((Map)rowEntry.getValue()).entrySet().spliterator(), columnEntry -> Tables.immutableCell(rowEntry.getKey(), columnEntry.getKey(), columnEntry.getValue())
				),
			65,
			(long)this.size()
		);
	}

	@Override
	public Map<C, V> row(R rowKey) {
		return new StandardTable.Row(rowKey);
	}

	@Override
	public Map<R, V> column(C columnKey) {
		return new StandardTable.Column(columnKey);
	}

	@Override
	public Set<R> rowKeySet() {
		return this.rowMap().keySet();
	}

	@Override
	public Set<C> columnKeySet() {
		Set<C> result = this.columnKeySet;
		return result == null ? (this.columnKeySet = new StandardTable.ColumnKeySet()) : result;
	}

	Iterator<C> createColumnKeyIterator() {
		return new StandardTable.ColumnKeyIterator();
	}

	@Override
	public Collection<V> values() {
		return super.values();
	}

	@Override
	public Map<R, Map<C, V>> rowMap() {
		Map<R, Map<C, V>> result = this.rowMap;
		return result == null ? (this.rowMap = this.createRowMap()) : result;
	}

	Map<R, Map<C, V>> createRowMap() {
		return new StandardTable.RowMap();
	}

	@Override
	public Map<C, Map<R, V>> columnMap() {
		StandardTable<R, C, V>.ColumnMap result = this.columnMap;
		return result == null ? (this.columnMap = new StandardTable.ColumnMap()) : result;
	}

	private class CellIterator implements Iterator<Cell<R, C, V>> {
		final Iterator<Entry<R, Map<C, V>>> rowIterator = StandardTable.this.backingMap.entrySet().iterator();
		Entry<R, Map<C, V>> rowEntry;
		Iterator<Entry<C, V>> columnIterator = Iterators.emptyModifiableIterator();

		private CellIterator() {
		}

		public boolean hasNext() {
			return this.rowIterator.hasNext() || this.columnIterator.hasNext();
		}

		public Cell<R, C, V> next() {
			if (!this.columnIterator.hasNext()) {
				this.rowEntry = (Entry<R, Map<C, V>>)this.rowIterator.next();
				this.columnIterator = ((Map)this.rowEntry.getValue()).entrySet().iterator();
			}

			Entry<C, V> columnEntry = (Entry<C, V>)this.columnIterator.next();
			return Tables.immutableCell((R)this.rowEntry.getKey(), (C)columnEntry.getKey(), (V)columnEntry.getValue());
		}

		public void remove() {
			this.columnIterator.remove();
			if (((Map)this.rowEntry.getValue()).isEmpty()) {
				this.rowIterator.remove();
			}
		}
	}

	private class Column extends ViewCachingAbstractMap<R, V> {
		final C columnKey;

		Column(C columnKey) {
			this.columnKey = Preconditions.checkNotNull(columnKey);
		}

		public V put(R key, V value) {
			return StandardTable.this.put(key, this.columnKey, value);
		}

		public V get(Object key) {
			return StandardTable.this.get(key, this.columnKey);
		}

		public boolean containsKey(Object key) {
			return StandardTable.this.contains(key, this.columnKey);
		}

		public V remove(Object key) {
			return StandardTable.this.remove(key, this.columnKey);
		}

		@CanIgnoreReturnValue
		boolean removeFromColumnIf(Predicate<? super Entry<R, V>> predicate) {
			boolean changed = false;
			Iterator<Entry<R, Map<C, V>>> iterator = StandardTable.this.backingMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<R, Map<C, V>> entry = (Entry<R, Map<C, V>>)iterator.next();
				Map<C, V> map = (Map<C, V>)entry.getValue();
				V value = (V)map.get(this.columnKey);
				if (value != null && predicate.apply(Maps.immutableEntry((R)entry.getKey(), value))) {
					map.remove(this.columnKey);
					changed = true;
					if (map.isEmpty()) {
						iterator.remove();
					}
				}
			}

			return changed;
		}

		@Override
		Set<Entry<R, V>> createEntrySet() {
			return new StandardTable.Column.EntrySet();
		}

		@Override
		Set<R> createKeySet() {
			return new StandardTable.Column.KeySet();
		}

		@Override
		Collection<V> createValues() {
			return new StandardTable.Column.Values();
		}

		private class EntrySet extends ImprovedAbstractSet<Entry<R, V>> {
			private EntrySet() {
			}

			public Iterator<Entry<R, V>> iterator() {
				return Column.this.new EntrySetIterator();
			}

			public int size() {
				int size = 0;

				for (Map<C, V> map : StandardTable.this.backingMap.values()) {
					if (map.containsKey(Column.this.columnKey)) {
						size++;
					}
				}

				return size;
			}

			public boolean isEmpty() {
				return !StandardTable.this.containsColumn(Column.this.columnKey);
			}

			public void clear() {
				Column.this.removeFromColumnIf(Predicates.alwaysTrue());
			}

			public boolean contains(Object o) {
				if (o instanceof Entry) {
					Entry<?, ?> entry = (Entry<?, ?>)o;
					return StandardTable.this.containsMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
				} else {
					return false;
				}
			}

			public boolean remove(Object obj) {
				if (obj instanceof Entry) {
					Entry<?, ?> entry = (Entry<?, ?>)obj;
					return StandardTable.this.removeMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
				} else {
					return false;
				}
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				return Column.this.removeFromColumnIf(Predicates.not(Predicates.in((Collection<? extends Entry<R, V>>)c)));
			}
		}

		private class EntrySetIterator extends AbstractIterator<Entry<R, V>> {
			final Iterator<Entry<R, Map<C, V>>> iterator = StandardTable.this.backingMap.entrySet().iterator();

			private EntrySetIterator() {
			}

			protected Entry<R, V> computeNext() {
				while (this.iterator.hasNext()) {
					final Entry<R, Map<C, V>> entry = (Entry<R, Map<C, V>>)this.iterator.next();
					if (((Map)entry.getValue()).containsKey(Column.this.columnKey)) {
						class 1EntryImpl extends AbstractMapEntry<R, V> {
							@Override
							public R getKey() {
								return (R)entry.getKey();
							}

							@Override
							public V getValue() {
								return (V)((Map)entry.getValue()).get(Column.this.columnKey);
							}

							@Override
							public V setValue(V value) {
								return (V)((Map)entry.getValue()).put(Column.this.columnKey, Preconditions.checkNotNull(value));
							}
						}

						return new 1EntryImpl();
					}
				}

				return this.endOfData();
			}
		}

		private class KeySet extends Maps.KeySet<R, V> {
			KeySet() {
				super(Column.this);
			}

			@Override
			public boolean contains(Object obj) {
				return StandardTable.this.contains(obj, Column.this.columnKey);
			}

			@Override
			public boolean remove(Object obj) {
				return StandardTable.this.remove(obj, Column.this.columnKey) != null;
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				return Column.this.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in((Collection<? extends R>)c))));
			}
		}

		private class Values extends Maps.Values<R, V> {
			Values() {
				super(Column.this);
			}

			@Override
			public boolean remove(Object obj) {
				return obj != null && Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo((V)obj)));
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.in((Collection<? extends V>)c)));
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in((Collection<? extends V>)c))));
			}
		}
	}

	private class ColumnKeyIterator extends AbstractIterator<C> {
		final Map<C, V> seen = (Map<C, V>)StandardTable.this.factory.get();
		final Iterator<Map<C, V>> mapIterator = StandardTable.this.backingMap.values().iterator();
		Iterator<Entry<C, V>> entryIterator = Iterators.emptyIterator();

		private ColumnKeyIterator() {
		}

		@Override
		protected C computeNext() {
			while (true) {
				if (this.entryIterator.hasNext()) {
					Entry<C, V> entry = (Entry<C, V>)this.entryIterator.next();
					if (!this.seen.containsKey(entry.getKey())) {
						this.seen.put(entry.getKey(), entry.getValue());
						return (C)entry.getKey();
					}
				} else {
					if (!this.mapIterator.hasNext()) {
						return (C)this.endOfData();
					}

					this.entryIterator = ((Map)this.mapIterator.next()).entrySet().iterator();
				}
			}
		}
	}

	private class ColumnKeySet extends StandardTable<R, C, V>.TableSet<C> {
		private ColumnKeySet() {
		}

		public Iterator<C> iterator() {
			return StandardTable.this.createColumnKeyIterator();
		}

		public int size() {
			return Iterators.size(this.iterator());
		}

		public boolean remove(Object obj) {
			if (obj == null) {
				return false;
			} else {
				boolean changed = false;
				Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();

				while (iterator.hasNext()) {
					Map<C, V> map = (Map<C, V>)iterator.next();
					if (map.keySet().remove(obj)) {
						changed = true;
						if (map.isEmpty()) {
							iterator.remove();
						}
					}
				}

				return changed;
			}
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			Preconditions.checkNotNull(c);
			boolean changed = false;
			Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();

			while (iterator.hasNext()) {
				Map<C, V> map = (Map<C, V>)iterator.next();
				if (Iterators.removeAll(map.keySet().iterator(), c)) {
					changed = true;
					if (map.isEmpty()) {
						iterator.remove();
					}
				}
			}

			return changed;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			Preconditions.checkNotNull(c);
			boolean changed = false;
			Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();

			while (iterator.hasNext()) {
				Map<C, V> map = (Map<C, V>)iterator.next();
				if (map.keySet().retainAll(c)) {
					changed = true;
					if (map.isEmpty()) {
						iterator.remove();
					}
				}
			}

			return changed;
		}

		public boolean contains(Object obj) {
			return StandardTable.this.containsColumn(obj);
		}
	}

	private class ColumnMap extends ViewCachingAbstractMap<C, Map<R, V>> {
		private ColumnMap() {
		}

		public Map<R, V> get(Object key) {
			return StandardTable.this.containsColumn(key) ? StandardTable.this.column((C)key) : null;
		}

		public boolean containsKey(Object key) {
			return StandardTable.this.containsColumn(key);
		}

		public Map<R, V> remove(Object key) {
			return StandardTable.this.containsColumn(key) ? StandardTable.this.removeColumn(key) : null;
		}

		@Override
		public Set<Entry<C, Map<R, V>>> createEntrySet() {
			return new StandardTable.ColumnMap.ColumnMapEntrySet();
		}

		@Override
		public Set<C> keySet() {
			return StandardTable.this.columnKeySet();
		}

		@Override
		Collection<Map<R, V>> createValues() {
			return new StandardTable.ColumnMap.ColumnMapValues();
		}

		class ColumnMapEntrySet extends StandardTable<R, C, V>.TableSet<Entry<C, Map<R, V>>> {
			public Iterator<Entry<C, Map<R, V>>> iterator() {
				return Maps.asMapEntryIterator(StandardTable.this.columnKeySet(), new Function<C, Map<R, V>>() {
					public Map<R, V> apply(C columnKey) {
						return StandardTable.this.column(columnKey);
					}
				});
			}

			public int size() {
				return StandardTable.this.columnKeySet().size();
			}

			public boolean contains(Object obj) {
				if (obj instanceof Entry) {
					Entry<?, ?> entry = (Entry<?, ?>)obj;
					if (StandardTable.this.containsColumn(entry.getKey())) {
						C columnKey = (C)entry.getKey();
						return ColumnMap.this.get(columnKey).equals(entry.getValue());
					}
				}

				return false;
			}

			public boolean remove(Object obj) {
				if (this.contains(obj)) {
					Entry<?, ?> entry = (Entry<?, ?>)obj;
					StandardTable.this.removeColumn(entry.getKey());
					return true;
				} else {
					return false;
				}
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				Preconditions.checkNotNull(c);
				return Sets.removeAllImpl(this, c.iterator());
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				Preconditions.checkNotNull(c);
				boolean changed = false;

				for (C columnKey : Lists.newArrayList(StandardTable.this.columnKeySet().iterator())) {
					if (!c.contains(Maps.immutableEntry(columnKey, StandardTable.this.column(columnKey)))) {
						StandardTable.this.removeColumn(columnKey);
						changed = true;
					}
				}

				return changed;
			}
		}

		private class ColumnMapValues extends Maps.Values<C, Map<R, V>> {
			ColumnMapValues() {
				super(ColumnMap.this);
			}

			@Override
			public boolean remove(Object obj) {
				for (Entry<C, Map<R, V>> entry : ColumnMap.this.entrySet()) {
					if (((Map)entry.getValue()).equals(obj)) {
						StandardTable.this.removeColumn(entry.getKey());
						return true;
					}
				}

				return false;
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				Preconditions.checkNotNull(c);
				boolean changed = false;

				for (C columnKey : Lists.newArrayList(StandardTable.this.columnKeySet().iterator())) {
					if (c.contains(StandardTable.this.column(columnKey))) {
						StandardTable.this.removeColumn(columnKey);
						changed = true;
					}
				}

				return changed;
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				Preconditions.checkNotNull(c);
				boolean changed = false;

				for (C columnKey : Lists.newArrayList(StandardTable.this.columnKeySet().iterator())) {
					if (!c.contains(StandardTable.this.column(columnKey))) {
						StandardTable.this.removeColumn(columnKey);
						changed = true;
					}
				}

				return changed;
			}
		}
	}

	class Row extends IteratorBasedAbstractMap<C, V> {
		final R rowKey;
		Map<C, V> backingRowMap;

		Row(R rowKey) {
			this.rowKey = Preconditions.checkNotNull(rowKey);
		}

		Map<C, V> backingRowMap() {
			return this.backingRowMap != null && (!this.backingRowMap.isEmpty() || !StandardTable.this.backingMap.containsKey(this.rowKey))
				? this.backingRowMap
				: (this.backingRowMap = this.computeBackingRowMap());
		}

		Map<C, V> computeBackingRowMap() {
			return (Map<C, V>)StandardTable.this.backingMap.get(this.rowKey);
		}

		void maintainEmptyInvariant() {
			if (this.backingRowMap() != null && this.backingRowMap.isEmpty()) {
				StandardTable.this.backingMap.remove(this.rowKey);
				this.backingRowMap = null;
			}
		}

		public boolean containsKey(Object key) {
			Map<C, V> backingRowMap = this.backingRowMap();
			return key != null && backingRowMap != null && Maps.safeContainsKey(backingRowMap, key);
		}

		public V get(Object key) {
			Map<C, V> backingRowMap = this.backingRowMap();
			return key != null && backingRowMap != null ? Maps.safeGet(backingRowMap, key) : null;
		}

		public V put(C key, V value) {
			Preconditions.checkNotNull(key);
			Preconditions.checkNotNull(value);
			return (V)(this.backingRowMap != null && !this.backingRowMap.isEmpty()
				? this.backingRowMap.put(key, value)
				: StandardTable.this.put(this.rowKey, key, value));
		}

		public V remove(Object key) {
			Map<C, V> backingRowMap = this.backingRowMap();
			if (backingRowMap == null) {
				return null;
			} else {
				V result = Maps.safeRemove(backingRowMap, key);
				this.maintainEmptyInvariant();
				return result;
			}
		}

		@Override
		public void clear() {
			Map<C, V> backingRowMap = this.backingRowMap();
			if (backingRowMap != null) {
				backingRowMap.clear();
			}

			this.maintainEmptyInvariant();
		}

		@Override
		public int size() {
			Map<C, V> map = this.backingRowMap();
			return map == null ? 0 : map.size();
		}

		@Override
		Iterator<Entry<C, V>> entryIterator() {
			Map<C, V> map = this.backingRowMap();
			if (map == null) {
				return Iterators.emptyModifiableIterator();
			} else {
				final Iterator<Entry<C, V>> iterator = map.entrySet().iterator();
				return new Iterator<Entry<C, V>>() {
					public boolean hasNext() {
						return iterator.hasNext();
					}

					public Entry<C, V> next() {
						return Row.this.wrapEntry((Entry<C, V>)iterator.next());
					}

					public void remove() {
						iterator.remove();
						Row.this.maintainEmptyInvariant();
					}
				};
			}
		}

		@Override
		Spliterator<Entry<C, V>> entrySpliterator() {
			Map<C, V> map = this.backingRowMap();
			return map == null ? Spliterators.emptySpliterator() : CollectSpliterators.map(map.entrySet().spliterator(), this::wrapEntry);
		}

		Entry<C, V> wrapEntry(Entry<C, V> entry) {
			return new ForwardingMapEntry<C, V>() {
				@Override
				protected Entry<C, V> delegate() {
					return entry;
				}

				@Override
				public V setValue(V value) {
					return (V)super.setValue(Preconditions.checkNotNull(value));
				}

				@Override
				public boolean equals(Object object) {
					return this.standardEquals(object);
				}
			};
		}
	}

	class RowMap extends ViewCachingAbstractMap<R, Map<C, V>> {
		public boolean containsKey(Object key) {
			return StandardTable.this.containsRow(key);
		}

		public Map<C, V> get(Object key) {
			return StandardTable.this.containsRow(key) ? StandardTable.this.row((R)key) : null;
		}

		public Map<C, V> remove(Object key) {
			return key == null ? null : (Map)StandardTable.this.backingMap.remove(key);
		}

		@Override
		protected Set<Entry<R, Map<C, V>>> createEntrySet() {
			return new StandardTable.RowMap.EntrySet();
		}

		class EntrySet extends StandardTable<R, C, V>.TableSet<Entry<R, Map<C, V>>> {
			public Iterator<Entry<R, Map<C, V>>> iterator() {
				return Maps.asMapEntryIterator(StandardTable.this.backingMap.keySet(), new Function<R, Map<C, V>>() {
					public Map<C, V> apply(R rowKey) {
						return StandardTable.this.row(rowKey);
					}
				});
			}

			public int size() {
				return StandardTable.this.backingMap.size();
			}

			public boolean contains(Object obj) {
				if (!(obj instanceof Entry)) {
					return false;
				} else {
					Entry<?, ?> entry = (Entry<?, ?>)obj;
					return entry.getKey() != null && entry.getValue() instanceof Map && Collections2.safeContains(StandardTable.this.backingMap.entrySet(), entry);
				}
			}

			public boolean remove(Object obj) {
				if (!(obj instanceof Entry)) {
					return false;
				} else {
					Entry<?, ?> entry = (Entry<?, ?>)obj;
					return entry.getKey() != null && entry.getValue() instanceof Map && StandardTable.this.backingMap.entrySet().remove(entry);
				}
			}
		}
	}

	private abstract class TableSet<T> extends ImprovedAbstractSet<T> {
		private TableSet() {
		}

		public boolean isEmpty() {
			return StandardTable.this.backingMap.isEmpty();
		}

		public void clear() {
			StandardTable.this.backingMap.clear();
		}
	}
}
