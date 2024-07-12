package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableTable.SerializedForm;
import com.google.common.collect.Table.Cell;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class SparseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
	static final ImmutableTable<Object, Object, Object> EMPTY = new SparseImmutableTable<>(ImmutableList.of(), ImmutableSet.of(), ImmutableSet.of());
	private final ImmutableMap<R, Map<C, V>> rowMap;
	private final ImmutableMap<C, Map<R, V>> columnMap;
	private final int[] cellRowIndices;
	private final int[] cellColumnInRowIndices;

	SparseImmutableTable(ImmutableList<Cell<R, C, V>> cellList, ImmutableSet<R> rowSpace, ImmutableSet<C> columnSpace) {
		Map<R, Integer> rowIndex = Maps.indexMap(rowSpace);
		Map<R, Map<C, V>> rows = Maps.<R, Map<C, V>>newLinkedHashMap();

		for (R row : rowSpace) {
			rows.put(row, new LinkedHashMap());
		}

		Map<C, Map<R, V>> columns = Maps.<C, Map<R, V>>newLinkedHashMap();

		for (C col : columnSpace) {
			columns.put(col, new LinkedHashMap());
		}

		int[] cellRowIndices = new int[cellList.size()];
		int[] cellColumnInRowIndices = new int[cellList.size()];

		for (int i = 0; i < cellList.size(); i++) {
			Cell<R, C, V> cell = (Cell<R, C, V>)cellList.get(i);
			R rowKey = cell.getRowKey();
			C columnKey = cell.getColumnKey();
			V value = cell.getValue();
			cellRowIndices[i] = (Integer)rowIndex.get(rowKey);
			Map<C, V> thisRow = (Map<C, V>)rows.get(rowKey);
			cellColumnInRowIndices[i] = thisRow.size();
			V oldValue = (V)thisRow.put(columnKey, value);
			if (oldValue != null) {
				throw new IllegalArgumentException("Duplicate value for row=" + rowKey + ", column=" + columnKey + ": " + value + ", " + oldValue);
			}

			((Map)columns.get(columnKey)).put(rowKey, value);
		}

		this.cellRowIndices = cellRowIndices;
		this.cellColumnInRowIndices = cellColumnInRowIndices;
		ImmutableMap.Builder<R, Map<C, V>> rowBuilder = new ImmutableMap.Builder<>(rows.size());

		for (Entry<R, Map<C, V>> row : rows.entrySet()) {
			rowBuilder.put((R)row.getKey(), ImmutableMap.copyOf((Map<? extends C, ? extends V>)row.getValue()));
		}

		this.rowMap = rowBuilder.build();
		ImmutableMap.Builder<C, Map<R, V>> columnBuilder = new ImmutableMap.Builder<>(columns.size());

		for (Entry<C, Map<R, V>> col : columns.entrySet()) {
			columnBuilder.put((C)col.getKey(), ImmutableMap.copyOf((Map<? extends R, ? extends V>)col.getValue()));
		}

		this.columnMap = columnBuilder.build();
	}

	@Override
	public ImmutableMap<C, Map<R, V>> columnMap() {
		return this.columnMap;
	}

	@Override
	public ImmutableMap<R, Map<C, V>> rowMap() {
		return this.rowMap;
	}

	@Override
	public int size() {
		return this.cellRowIndices.length;
	}

	@Override
	Cell<R, C, V> getCell(int index) {
		int rowIndex = this.cellRowIndices[index];
		Entry<R, Map<C, V>> rowEntry = (Entry<R, Map<C, V>>)this.rowMap.entrySet().asList().get(rowIndex);
		ImmutableMap<C, V> row = (ImmutableMap<C, V>)rowEntry.getValue();
		int columnIndex = this.cellColumnInRowIndices[index];
		Entry<C, V> colEntry = (Entry<C, V>)row.entrySet().asList().get(columnIndex);
		return cellOf((R)rowEntry.getKey(), (C)colEntry.getKey(), (V)colEntry.getValue());
	}

	@Override
	V getValue(int index) {
		int rowIndex = this.cellRowIndices[index];
		ImmutableMap<C, V> row = (ImmutableMap<C, V>)this.rowMap.values().asList().get(rowIndex);
		int columnIndex = this.cellColumnInRowIndices[index];
		return (V)row.values().asList().get(columnIndex);
	}

	@Override
	SerializedForm createSerializedForm() {
		Map<C, Integer> columnKeyToIndex = Maps.indexMap(this.columnKeySet());
		int[] cellColumnIndices = new int[this.cellSet().size()];
		int i = 0;

		for (Cell<R, C, V> cell : this.cellSet()) {
			cellColumnIndices[i++] = (Integer)columnKeyToIndex.get(cell.getColumnKey());
		}

		return SerializedForm.create(this, this.cellRowIndices, cellColumnIndices);
	}
}
