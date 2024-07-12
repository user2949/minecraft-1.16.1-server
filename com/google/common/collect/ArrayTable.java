package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps.IteratorBasedAbstractMap;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.Tables.AbstractCell;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@Beta
@GwtCompatible(
	emulated = true
)
public final class ArrayTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
	private final ImmutableList<R> rowList;
	private final ImmutableList<C> columnList;
	private final ImmutableMap<R, Integer> rowKeyToIndex;
	private final ImmutableMap<C, Integer> columnKeyToIndex;
	private final V[][] array;
	private transient ArrayTable<R, C, V>.ColumnMap columnMap;
	private transient ArrayTable<R, C, V>.RowMap rowMap;
	private static final long serialVersionUID = 0L;

	public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> rowKeys, Iterable<? extends C> columnKeys) {
		return new ArrayTable<>(rowKeys, columnKeys);
	}

	public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> table) {
		return table instanceof ArrayTable ? new ArrayTable<>((ArrayTable<R, C, V>)table) : new ArrayTable<>(table);
	}

	private ArrayTable(Iterable<? extends R> rowKeys, Iterable<? extends C> columnKeys) {
		this.rowList = ImmutableList.copyOf(rowKeys);
		this.columnList = ImmutableList.copyOf(columnKeys);
		Preconditions.checkArgument(!this.rowList.isEmpty());
		Preconditions.checkArgument(!this.columnList.isEmpty());
		this.rowKeyToIndex = Maps.indexMap(this.rowList);
		this.columnKeyToIndex = Maps.indexMap(this.columnList);
		V[][] tmpArray = (V[][])(new Object[this.rowList.size()][this.columnList.size()]);
		this.array = tmpArray;
		this.eraseAll();
	}

	private ArrayTable(Table<R, C, V> table) {
		this(table.rowKeySet(), table.columnKeySet());
		this.putAll(table);
	}

	private ArrayTable(ArrayTable<R, C, V> table) {
		this.rowList = table.rowList;
		this.columnList = table.columnList;
		this.rowKeyToIndex = table.rowKeyToIndex;
		this.columnKeyToIndex = table.columnKeyToIndex;
		V[][] copy = (V[][])(new Object[this.rowList.size()][this.columnList.size()]);
		this.array = copy;
		this.eraseAll();

		for (int i = 0; i < this.rowList.size(); i++) {
			System.arraycopy(table.array[i], 0, copy[i], 0, table.array[i].length);
		}
	}

	public ImmutableList<R> rowKeyList() {
		return this.rowList;
	}

	public ImmutableList<C> columnKeyList() {
		return this.columnList;
	}

	public V at(int rowIndex, int columnIndex) {
		Preconditions.checkElementIndex(rowIndex, this.rowList.size());
		Preconditions.checkElementIndex(columnIndex, this.columnList.size());
		return this.array[rowIndex][columnIndex];
	}

	@CanIgnoreReturnValue
	public V set(int rowIndex, int columnIndex, @Nullable V value) {
		Preconditions.checkElementIndex(rowIndex, this.rowList.size());
		Preconditions.checkElementIndex(columnIndex, this.columnList.size());
		V oldValue = this.array[rowIndex][columnIndex];
		this.array[rowIndex][columnIndex] = value;
		return oldValue;
	}

	@GwtIncompatible
	public V[][] toArray(Class<V> valueClass) {
		V[][] copy = (V[][])((Object[][])Array.newInstance(valueClass, new int[]{this.rowList.size(), this.columnList.size()}));

		for (int i = 0; i < this.rowList.size(); i++) {
			System.arraycopy(this.array[i], 0, copy[i], 0, this.array[i].length);
		}

		return copy;
	}

	@Deprecated
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	public void eraseAll() {
		for (V[] row : this.array) {
			Arrays.fill(row, null);
		}
	}

	@Override
	public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
		return this.containsRow(rowKey) && this.containsColumn(columnKey);
	}

	@Override
	public boolean containsColumn(@Nullable Object columnKey) {
		return this.columnKeyToIndex.containsKey(columnKey);
	}

	@Override
	public boolean containsRow(@Nullable Object rowKey) {
		return this.rowKeyToIndex.containsKey(rowKey);
	}

	@Override
	public boolean containsValue(@Nullable Object value) {
		for (V[] row : this.array) {
			for (V element : row) {
				if (Objects.equal(value, element)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
		Integer rowIndex = this.rowKeyToIndex.get(rowKey);
		Integer columnIndex = this.columnKeyToIndex.get(columnKey);
		return rowIndex != null && columnIndex != null ? this.at(rowIndex, columnIndex) : null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@CanIgnoreReturnValue
	@Override
	public V put(R rowKey, C columnKey, @Nullable V value) {
		Preconditions.checkNotNull(rowKey);
		Preconditions.checkNotNull(columnKey);
		Integer rowIndex = this.rowKeyToIndex.get(rowKey);
		Preconditions.checkArgument(rowIndex != null, "Row %s not in %s", rowKey, this.rowList);
		Integer columnIndex = this.columnKeyToIndex.get(columnKey);
		Preconditions.checkArgument(columnIndex != null, "Column %s not in %s", columnKey, this.columnList);
		return this.set(rowIndex, columnIndex, value);
	}

	@Override
	public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
		super.putAll(table);
	}

	@Deprecated
	@CanIgnoreReturnValue
	@Override
	public V remove(Object rowKey, Object columnKey) {
		throw new UnsupportedOperationException();
	}

	@CanIgnoreReturnValue
	public V erase(@Nullable Object rowKey, @Nullable Object columnKey) {
		Integer rowIndex = this.rowKeyToIndex.get(rowKey);
		Integer columnIndex = this.columnKeyToIndex.get(columnKey);
		return rowIndex != null && columnIndex != null ? this.set(rowIndex, columnIndex, null) : null;
	}

	@Override
	public int size() {
		return this.rowList.size() * this.columnList.size();
	}

	@Override
	public Set<Cell<R, C, V>> cellSet() {
		return super.cellSet();
	}

	@Override
	Iterator<Cell<R, C, V>> cellIterator() {
		return new AbstractIndexedListIterator<Cell<R, C, V>>(this.size()) {
			protected Cell<R, C, V> get(int index) {
				return ArrayTable.this.getCell(index);
			}
		};
	}

	@Override
	Spliterator<Cell<R, C, V>> cellSpliterator() {
		return CollectSpliterators.indexed(this.size(), 273, this::getCell);
	}

	private Cell<R, C, V> getCell(int index) {
		return new AbstractCell<R, C, V>() {
			final int rowIndex = index / ArrayTable.this.columnList.size();
			final int columnIndex = index % ArrayTable.this.columnList.size();

			@Override
			public R getRowKey() {
				return (R)ArrayTable.this.rowList.get(this.rowIndex);
			}

			@Override
			public C getColumnKey() {
				return (C)ArrayTable.this.columnList.get(this.columnIndex);
			}

			@Override
			public V getValue() {
				return (V)ArrayTable.this.at(this.rowIndex, this.columnIndex);
			}
		};
	}

	private V getValue(int index) {
		int rowIndex = index / this.columnList.size();
		int columnIndex = index % this.columnList.size();
		return this.at(rowIndex, columnIndex);
	}

	@Override
	public Map<R, V> column(C columnKey) {
		Preconditions.checkNotNull(columnKey);
		Integer columnIndex = this.columnKeyToIndex.get(columnKey);
		return (Map<R, V>)(columnIndex == null ? ImmutableMap.of() : new ArrayTable.Column(columnIndex));
	}

	public ImmutableSet<C> columnKeySet() {
		return this.columnKeyToIndex.keySet();
	}

	@Override
	public Map<C, Map<R, V>> columnMap() {
		ArrayTable<R, C, V>.ColumnMap map = this.columnMap;
		return map == null ? (this.columnMap = new ArrayTable.ColumnMap()) : map;
	}

	@Override
	public Map<C, V> row(R rowKey) {
		Preconditions.checkNotNull(rowKey);
		Integer rowIndex = this.rowKeyToIndex.get(rowKey);
		return (Map<C, V>)(rowIndex == null ? ImmutableMap.of() : new ArrayTable.Row(rowIndex));
	}

	public ImmutableSet<R> rowKeySet() {
		return this.rowKeyToIndex.keySet();
	}

	@Override
	public Map<R, Map<C, V>> rowMap() {
		ArrayTable<R, C, V>.RowMap map = this.rowMap;
		return map == null ? (this.rowMap = new ArrayTable.RowMap()) : map;
	}

	@Override
	public Collection<V> values() {
		return super.values();
	}

	@Override
	Iterator<V> valuesIterator() {
		return new AbstractIndexedListIterator<V>(this.size()) {
			@Override
			protected V get(int index) {
				return (V)ArrayTable.this.getValue(index);
			}
		};
	}

	@Override
	Spliterator<V> valuesSpliterator() {
		return CollectSpliterators.indexed(this.size(), 16, this::getValue);
	}

	private abstract static class ArrayMap<K, V> extends IteratorBasedAbstractMap<K, V> {
		private final ImmutableMap<K, Integer> keyIndex;

		private ArrayMap(ImmutableMap<K, Integer> keyIndex) {
			this.keyIndex = keyIndex;
		}

		public Set<K> keySet() {
			return this.keyIndex.keySet();
		}

		K getKey(int index) {
			return (K)this.keyIndex.keySet().asList().get(index);
		}

		abstract String getKeyRole();

		@Nullable
		abstract V getValue(int integer);

		@Nullable
		abstract V setValue(int integer, V object);

		@Override
		public int size() {
			return this.keyIndex.size();
		}

		public boolean isEmpty() {
			return this.keyIndex.isEmpty();
		}

		Entry<K, V> getEntry(int index) {
			Preconditions.checkElementIndex(index, this.size());
			return new AbstractMapEntry<K, V>() {
				@Override
				public K getKey() {
					return (K)ArrayMap.this.getKey(index);
				}

				@Override
				public V getValue() {
					return (V)ArrayMap.this.getValue(index);
				}

				@Override
				public V setValue(V value) {
					return (V)ArrayMap.this.setValue(index, value);
				}
			};
		}

		@Override
		Iterator<Entry<K, V>> entryIterator() {
			return new AbstractIndexedListIterator<Entry<K, V>>(this.size()) {
				protected Entry<K, V> get(int index) {
					return ArrayMap.this.getEntry(index);
				}
			};
		}

		@Override
		Spliterator<Entry<K, V>> entrySpliterator() {
			return CollectSpliterators.indexed(this.size(), 16, this::getEntry);
		}

		public boolean containsKey(@Nullable Object key) {
			return this.keyIndex.containsKey(key);
		}

		public V get(@Nullable Object key) {
			Integer index = this.keyIndex.get(key);
			return index == null ? null : this.getValue(index);
		}

		public V put(K key, V value) {
			Integer index = this.keyIndex.get(key);
			if (index == null) {
				throw new IllegalArgumentException(this.getKeyRole() + " " + key + " not in " + this.keyIndex.keySet());
			} else {
				return this.setValue(index, value);
			}
		}

		public V remove(Object key) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}
	}

	private class Column extends ArrayTable.ArrayMap<R, V> {
		final int columnIndex;

		Column(int columnIndex) {
			super(ArrayTable.this.rowKeyToIndex);
			this.columnIndex = columnIndex;
		}

		@Override
		String getKeyRole() {
			return "Row";
		}

		@Override
		V getValue(int index) {
			return ArrayTable.this.at(index, this.columnIndex);
		}

		@Override
		V setValue(int index, V newValue) {
			return ArrayTable.this.set(index, this.columnIndex, newValue);
		}
	}

	private class ColumnMap extends ArrayTable.ArrayMap<C, Map<R, V>> {
		private ColumnMap() {
			super(ArrayTable.this.columnKeyToIndex);
		}

		@Override
		String getKeyRole() {
			return "Column";
		}

		Map<R, V> getValue(int index) {
			return ArrayTable.this.new Column(index);
		}

		Map<R, V> setValue(int index, Map<R, V> newValue) {
			throw new UnsupportedOperationException();
		}

		public Map<R, V> put(C key, Map<R, V> value) {
			throw new UnsupportedOperationException();
		}
	}

	private class Row extends ArrayTable.ArrayMap<C, V> {
		final int rowIndex;

		Row(int rowIndex) {
			super(ArrayTable.this.columnKeyToIndex);
			this.rowIndex = rowIndex;
		}

		@Override
		String getKeyRole() {
			return "Column";
		}

		@Override
		V getValue(int index) {
			return ArrayTable.this.at(this.rowIndex, index);
		}

		@Override
		V setValue(int index, V newValue) {
			return ArrayTable.this.set(this.rowIndex, index, newValue);
		}
	}

	private class RowMap extends ArrayTable.ArrayMap<R, Map<C, V>> {
		private RowMap() {
			super(ArrayTable.this.rowKeyToIndex);
		}

		@Override
		String getKeyRole() {
			return "Row";
		}

		Map<C, V> getValue(int index) {
			return ArrayTable.this.new Row(index);
		}

		Map<C, V> setValue(int index, Map<C, V> newValue) {
			throw new UnsupportedOperationException();
		}

		public Map<C, V> put(R key, Map<C, V> value) {
			throw new UnsupportedOperationException();
		}
	}
}
