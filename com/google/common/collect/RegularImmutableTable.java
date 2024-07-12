package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet.Indexed;
import com.google.common.collect.Table.Cell;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class RegularImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {
	abstract Cell<R, C, V> getCell(int integer);

	@Override
	final ImmutableSet<Cell<R, C, V>> createCellSet() {
		return (ImmutableSet<Cell<R, C, V>>)(this.isEmpty() ? ImmutableSet.of() : new RegularImmutableTable.CellSet());
	}

	abstract V getValue(int integer);

	@Override
	final ImmutableCollection<V> createValues() {
		return (ImmutableCollection<V>)(this.isEmpty() ? ImmutableList.of() : new RegularImmutableTable.Values());
	}

	static <R, C, V> RegularImmutableTable<R, C, V> forCells(
		List<Cell<R, C, V>> cells, @Nullable Comparator<? super R> rowComparator, @Nullable Comparator<? super C> columnComparator
	) {
		Preconditions.checkNotNull(cells);
		if (rowComparator != null || columnComparator != null) {
			Comparator<Cell<R, C, V>> comparator = new Comparator<Cell<R, C, V>>() {
				public int compare(Cell<R, C, V> cell1, Cell<R, C, V> cell2) {
					int rowCompare = rowComparator == null ? 0 : rowComparator.compare(cell1.getRowKey(), cell2.getRowKey());
					if (rowCompare != 0) {
						return rowCompare;
					} else {
						return columnComparator == null ? 0 : columnComparator.compare(cell1.getColumnKey(), cell2.getColumnKey());
					}
				}
			};
			Collections.sort(cells, comparator);
		}

		return forCellsInternal(cells, rowComparator, columnComparator);
	}

	static <R, C, V> RegularImmutableTable<R, C, V> forCells(Iterable<Cell<R, C, V>> cells) {
		return forCellsInternal(cells, null, null);
	}

	private static final <R, C, V> RegularImmutableTable<R, C, V> forCellsInternal(
		Iterable<Cell<R, C, V>> cells, @Nullable Comparator<? super R> rowComparator, @Nullable Comparator<? super C> columnComparator
	) {
		Set<R> rowSpaceBuilder = new LinkedHashSet();
		Set<C> columnSpaceBuilder = new LinkedHashSet();
		ImmutableList<Cell<R, C, V>> cellList = ImmutableList.copyOf(cells);

		for (Cell<R, C, V> cell : cells) {
			rowSpaceBuilder.add(cell.getRowKey());
			columnSpaceBuilder.add(cell.getColumnKey());
		}

		ImmutableSet<R> rowSpace = rowComparator == null
			? ImmutableSet.copyOf(rowSpaceBuilder)
			: ImmutableSet.copyOf(ImmutableList.sortedCopyOf(rowComparator, rowSpaceBuilder));
		ImmutableSet<C> columnSpace = columnComparator == null
			? ImmutableSet.copyOf(columnSpaceBuilder)
			: ImmutableSet.copyOf(ImmutableList.sortedCopyOf(columnComparator, columnSpaceBuilder));
		return forOrderedComponents(cellList, rowSpace, columnSpace);
	}

	static <R, C, V> RegularImmutableTable<R, C, V> forOrderedComponents(
		ImmutableList<Cell<R, C, V>> cellList, ImmutableSet<R> rowSpace, ImmutableSet<C> columnSpace
	) {
		return (RegularImmutableTable<R, C, V>)((long)cellList.size() > (long)rowSpace.size() * (long)columnSpace.size() / 2L
			? new DenseImmutableTable<>(cellList, rowSpace, columnSpace)
			: new SparseImmutableTable<>(cellList, rowSpace, columnSpace));
	}

	private final class CellSet extends Indexed<Cell<R, C, V>> {
		private CellSet() {
		}

		public int size() {
			return RegularImmutableTable.this.size();
		}

		Cell<R, C, V> get(int index) {
			return RegularImmutableTable.this.getCell(index);
		}

		@Override
		public boolean contains(@Nullable Object object) {
			if (!(object instanceof Cell)) {
				return false;
			} else {
				Cell<?, ?, ?> cell = (Cell<?, ?, ?>)object;
				Object value = RegularImmutableTable.this.get(cell.getRowKey(), cell.getColumnKey());
				return value != null && value.equals(cell.getValue());
			}
		}

		@Override
		boolean isPartialView() {
			return false;
		}
	}

	private final class Values extends ImmutableList<V> {
		private Values() {
		}

		public int size() {
			return RegularImmutableTable.this.size();
		}

		public V get(int index) {
			return RegularImmutableTable.this.getValue(index);
		}

		@Override
		boolean isPartialView() {
			return true;
		}
	}
}
