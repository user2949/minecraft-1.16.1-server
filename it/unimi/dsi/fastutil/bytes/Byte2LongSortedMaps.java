package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry;
import it.unimi.dsi.fastutil.bytes.Byte2LongMaps.EmptyMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongSortedMap.FastSortedEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Byte2LongSortedMaps {
	public static final Byte2LongSortedMaps.EmptySortedMap EMPTY_MAP = new Byte2LongSortedMaps.EmptySortedMap();

	private Byte2LongSortedMaps() {
	}

	public static Comparator<? super java.util.Map.Entry<Byte, ?>> entryComparator(ByteComparator comparator) {
		return (x, y) -> comparator.compare(((Byte)x.getKey()).byteValue(), ((Byte)y.getKey()).byteValue());
	}

	public static ObjectBidirectionalIterator<Entry> fastIterator(Byte2LongSortedMap map) {
		ObjectSortedSet<Entry> entries = map.byte2LongEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static ObjectBidirectionalIterable<Entry> fastIterable(Byte2LongSortedMap map) {
		ObjectSortedSet<Entry> entries = map.byte2LongEntrySet();
		return (ObjectBidirectionalIterable<Entry>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static Byte2LongSortedMap singleton(Byte key, Long value) {
		return new Byte2LongSortedMaps.Singleton(key, value);
	}

	public static Byte2LongSortedMap singleton(Byte key, Long value, ByteComparator comparator) {
		return new Byte2LongSortedMaps.Singleton(key, value, comparator);
	}

	public static Byte2LongSortedMap singleton(byte key, long value) {
		return new Byte2LongSortedMaps.Singleton(key, value);
	}

	public static Byte2LongSortedMap singleton(byte key, long value, ByteComparator comparator) {
		return new Byte2LongSortedMaps.Singleton(key, value, comparator);
	}

	public static Byte2LongSortedMap synchronize(Byte2LongSortedMap m) {
		return new Byte2LongSortedMaps.SynchronizedSortedMap(m);
	}

	public static Byte2LongSortedMap synchronize(Byte2LongSortedMap m, Object sync) {
		return new Byte2LongSortedMaps.SynchronizedSortedMap(m, sync);
	}

	public static Byte2LongSortedMap unmodifiable(Byte2LongSortedMap m) {
		return new Byte2LongSortedMaps.UnmodifiableSortedMap(m);
	}

	public static class EmptySortedMap extends EmptyMap implements Byte2LongSortedMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public ByteComparator comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry> byte2LongEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Byte, Long>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ByteSortedSet keySet() {
			return ByteSortedSets.EMPTY_SET;
		}

		@Override
		public Byte2LongSortedMap subMap(byte from, byte to) {
			return Byte2LongSortedMaps.EMPTY_MAP;
		}

		@Override
		public Byte2LongSortedMap headMap(byte to) {
			return Byte2LongSortedMaps.EMPTY_MAP;
		}

		@Override
		public Byte2LongSortedMap tailMap(byte from) {
			return Byte2LongSortedMaps.EMPTY_MAP;
		}

		@Override
		public byte firstByteKey() {
			throw new NoSuchElementException();
		}

		@Override
		public byte lastByteKey() {
			throw new NoSuchElementException();
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap headMap(Byte oto) {
			return this.headMap(oto.byteValue());
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap tailMap(Byte ofrom) {
			return this.tailMap(ofrom.byteValue());
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap subMap(Byte ofrom, Byte oto) {
			return this.subMap(ofrom.byteValue(), oto.byteValue());
		}

		@Deprecated
		@Override
		public Byte firstKey() {
			return this.firstByteKey();
		}

		@Deprecated
		@Override
		public Byte lastKey() {
			return this.lastByteKey();
		}
	}

	public static class Singleton extends Byte2LongMaps.Singleton implements Byte2LongSortedMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ByteComparator comparator;

		protected Singleton(byte key, long value, ByteComparator comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(byte key, long value) {
			this(key, value, null);
		}

		final int compare(byte k1, byte k2) {
			return this.comparator == null ? Byte.compare(k1, k2) : this.comparator.compare(k1, k2);
		}

		@Override
		public ByteComparator comparator() {
			return this.comparator;
		}

		@Override
		public ObjectSortedSet<Entry> byte2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry(this.key, this.value), Byte2LongSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Byte, Long>> entrySet() {
			return this.byte2LongEntrySet();
		}

		@Override
		public ByteSortedSet keySet() {
			if (this.keys == null) {
				this.keys = ByteSortedSets.singleton(this.key, this.comparator);
			}

			return (ByteSortedSet)this.keys;
		}

		@Override
		public Byte2LongSortedMap subMap(byte from, byte to) {
			return (Byte2LongSortedMap)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Byte2LongSortedMaps.EMPTY_MAP);
		}

		@Override
		public Byte2LongSortedMap headMap(byte to) {
			return (Byte2LongSortedMap)(this.compare(this.key, to) < 0 ? this : Byte2LongSortedMaps.EMPTY_MAP);
		}

		@Override
		public Byte2LongSortedMap tailMap(byte from) {
			return (Byte2LongSortedMap)(this.compare(from, this.key) <= 0 ? this : Byte2LongSortedMaps.EMPTY_MAP);
		}

		@Override
		public byte firstByteKey() {
			return this.key;
		}

		@Override
		public byte lastByteKey() {
			return this.key;
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap headMap(Byte oto) {
			return this.headMap(oto.byteValue());
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap tailMap(Byte ofrom) {
			return this.tailMap(ofrom.byteValue());
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap subMap(Byte ofrom, Byte oto) {
			return this.subMap(ofrom.byteValue(), oto.byteValue());
		}

		@Deprecated
		@Override
		public Byte firstKey() {
			return this.firstByteKey();
		}

		@Deprecated
		@Override
		public Byte lastKey() {
			return this.lastByteKey();
		}
	}

	public static class SynchronizedSortedMap extends SynchronizedMap implements Byte2LongSortedMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Byte2LongSortedMap sortedMap;

		protected SynchronizedSortedMap(Byte2LongSortedMap m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Byte2LongSortedMap m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public ByteComparator comparator() {
			synchronized (this.sync) {
				return this.sortedMap.comparator();
			}
		}

		@Override
		public ObjectSortedSet<Entry> byte2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.byte2LongEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Byte, Long>> entrySet() {
			return this.byte2LongEntrySet();
		}

		@Override
		public ByteSortedSet keySet() {
			if (this.keys == null) {
				this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ByteSortedSet)this.keys;
		}

		@Override
		public Byte2LongSortedMap subMap(byte from, byte to) {
			return new Byte2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Byte2LongSortedMap headMap(byte to) {
			return new Byte2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Byte2LongSortedMap tailMap(byte from) {
			return new Byte2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
		}

		@Override
		public byte firstByteKey() {
			synchronized (this.sync) {
				return this.sortedMap.firstByteKey();
			}
		}

		@Override
		public byte lastByteKey() {
			synchronized (this.sync) {
				return this.sortedMap.lastByteKey();
			}
		}

		@Deprecated
		@Override
		public Byte firstKey() {
			synchronized (this.sync) {
				return this.sortedMap.firstKey();
			}
		}

		@Deprecated
		@Override
		public Byte lastKey() {
			synchronized (this.sync) {
				return this.sortedMap.lastKey();
			}
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap subMap(Byte from, Byte to) {
			return new Byte2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap headMap(Byte to) {
			return new Byte2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap tailMap(Byte from) {
			return new Byte2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
		}
	}

	public static class UnmodifiableSortedMap extends UnmodifiableMap implements Byte2LongSortedMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Byte2LongSortedMap sortedMap;

		protected UnmodifiableSortedMap(Byte2LongSortedMap m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public ByteComparator comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry> byte2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.byte2LongEntrySet());
			}

			return (ObjectSortedSet<Entry>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Byte, Long>> entrySet() {
			return this.byte2LongEntrySet();
		}

		@Override
		public ByteSortedSet keySet() {
			if (this.keys == null) {
				this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ByteSortedSet)this.keys;
		}

		@Override
		public Byte2LongSortedMap subMap(byte from, byte to) {
			return new Byte2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
		}

		@Override
		public Byte2LongSortedMap headMap(byte to) {
			return new Byte2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.headMap(to));
		}

		@Override
		public Byte2LongSortedMap tailMap(byte from) {
			return new Byte2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.tailMap(from));
		}

		@Override
		public byte firstByteKey() {
			return this.sortedMap.firstByteKey();
		}

		@Override
		public byte lastByteKey() {
			return this.sortedMap.lastByteKey();
		}

		@Deprecated
		@Override
		public Byte firstKey() {
			return this.sortedMap.firstKey();
		}

		@Deprecated
		@Override
		public Byte lastKey() {
			return this.sortedMap.lastKey();
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap subMap(Byte from, Byte to) {
			return new Byte2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap headMap(Byte to) {
			return new Byte2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.headMap(to));
		}

		@Deprecated
		@Override
		public Byte2LongSortedMap tailMap(Byte from) {
			return new Byte2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.tailMap(from));
		}
	}
}
