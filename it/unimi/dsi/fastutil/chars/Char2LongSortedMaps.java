package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2LongMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2LongMaps.EmptyMap;
import it.unimi.dsi.fastutil.chars.Char2LongMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.chars.Char2LongMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.chars.Char2LongSortedMap.FastSortedEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Char2LongSortedMaps {
	public static final Char2LongSortedMaps.EmptySortedMap EMPTY_MAP = new Char2LongSortedMaps.EmptySortedMap();

	private Char2LongSortedMaps() {
	}

	public static Comparator<? super java.util.Map.Entry<Character, ?>> entryComparator(CharComparator comparator) {
		return (x, y) -> comparator.compare(((Character)x.getKey()).charValue(), ((Character)y.getKey()).charValue());
	}

	public static ObjectBidirectionalIterator<Entry> fastIterator(Char2LongSortedMap map) {
		ObjectSortedSet<Entry> entries = map.char2LongEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static ObjectBidirectionalIterable<Entry> fastIterable(Char2LongSortedMap map) {
		ObjectSortedSet<Entry> entries = map.char2LongEntrySet();
		return (ObjectBidirectionalIterable<Entry>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static Char2LongSortedMap singleton(Character key, Long value) {
		return new Char2LongSortedMaps.Singleton(key, value);
	}

	public static Char2LongSortedMap singleton(Character key, Long value, CharComparator comparator) {
		return new Char2LongSortedMaps.Singleton(key, value, comparator);
	}

	public static Char2LongSortedMap singleton(char key, long value) {
		return new Char2LongSortedMaps.Singleton(key, value);
	}

	public static Char2LongSortedMap singleton(char key, long value, CharComparator comparator) {
		return new Char2LongSortedMaps.Singleton(key, value, comparator);
	}

	public static Char2LongSortedMap synchronize(Char2LongSortedMap m) {
		return new Char2LongSortedMaps.SynchronizedSortedMap(m);
	}

	public static Char2LongSortedMap synchronize(Char2LongSortedMap m, Object sync) {
		return new Char2LongSortedMaps.SynchronizedSortedMap(m, sync);
	}

	public static Char2LongSortedMap unmodifiable(Char2LongSortedMap m) {
		return new Char2LongSortedMaps.UnmodifiableSortedMap(m);
	}

	public static class EmptySortedMap extends EmptyMap implements Char2LongSortedMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public CharComparator comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry> char2LongEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Character, Long>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public CharSortedSet keySet() {
			return CharSortedSets.EMPTY_SET;
		}

		@Override
		public Char2LongSortedMap subMap(char from, char to) {
			return Char2LongSortedMaps.EMPTY_MAP;
		}

		@Override
		public Char2LongSortedMap headMap(char to) {
			return Char2LongSortedMaps.EMPTY_MAP;
		}

		@Override
		public Char2LongSortedMap tailMap(char from) {
			return Char2LongSortedMaps.EMPTY_MAP;
		}

		@Override
		public char firstCharKey() {
			throw new NoSuchElementException();
		}

		@Override
		public char lastCharKey() {
			throw new NoSuchElementException();
		}

		@Deprecated
		@Override
		public Char2LongSortedMap headMap(Character oto) {
			return this.headMap(oto.charValue());
		}

		@Deprecated
		@Override
		public Char2LongSortedMap tailMap(Character ofrom) {
			return this.tailMap(ofrom.charValue());
		}

		@Deprecated
		@Override
		public Char2LongSortedMap subMap(Character ofrom, Character oto) {
			return this.subMap(ofrom.charValue(), oto.charValue());
		}

		@Deprecated
		@Override
		public Character firstKey() {
			return this.firstCharKey();
		}

		@Deprecated
		@Override
		public Character lastKey() {
			return this.lastCharKey();
		}
	}

	public static class Singleton extends Char2LongMaps.Singleton implements Char2LongSortedMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final CharComparator comparator;

		protected Singleton(char key, long value, CharComparator comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(char key, long value) {
			this(key, value, null);
		}

		final int compare(char k1, char k2) {
			return this.comparator == null ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
		}

		@Override
		public CharComparator comparator() {
			return this.comparator;
		}

		@Override
		public ObjectSortedSet<Entry> char2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry(this.key, this.value), Char2LongSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Character, Long>> entrySet() {
			return this.char2LongEntrySet();
		}

		@Override
		public CharSortedSet keySet() {
			if (this.keys == null) {
				this.keys = CharSortedSets.singleton(this.key, this.comparator);
			}

			return (CharSortedSet)this.keys;
		}

		@Override
		public Char2LongSortedMap subMap(char from, char to) {
			return (Char2LongSortedMap)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Char2LongSortedMaps.EMPTY_MAP);
		}

		@Override
		public Char2LongSortedMap headMap(char to) {
			return (Char2LongSortedMap)(this.compare(this.key, to) < 0 ? this : Char2LongSortedMaps.EMPTY_MAP);
		}

		@Override
		public Char2LongSortedMap tailMap(char from) {
			return (Char2LongSortedMap)(this.compare(from, this.key) <= 0 ? this : Char2LongSortedMaps.EMPTY_MAP);
		}

		@Override
		public char firstCharKey() {
			return this.key;
		}

		@Override
		public char lastCharKey() {
			return this.key;
		}

		@Deprecated
		@Override
		public Char2LongSortedMap headMap(Character oto) {
			return this.headMap(oto.charValue());
		}

		@Deprecated
		@Override
		public Char2LongSortedMap tailMap(Character ofrom) {
			return this.tailMap(ofrom.charValue());
		}

		@Deprecated
		@Override
		public Char2LongSortedMap subMap(Character ofrom, Character oto) {
			return this.subMap(ofrom.charValue(), oto.charValue());
		}

		@Deprecated
		@Override
		public Character firstKey() {
			return this.firstCharKey();
		}

		@Deprecated
		@Override
		public Character lastKey() {
			return this.lastCharKey();
		}
	}

	public static class SynchronizedSortedMap extends SynchronizedMap implements Char2LongSortedMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2LongSortedMap sortedMap;

		protected SynchronizedSortedMap(Char2LongSortedMap m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Char2LongSortedMap m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public CharComparator comparator() {
			synchronized (this.sync) {
				return this.sortedMap.comparator();
			}
		}

		@Override
		public ObjectSortedSet<Entry> char2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.char2LongEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Character, Long>> entrySet() {
			return this.char2LongEntrySet();
		}

		@Override
		public CharSortedSet keySet() {
			if (this.keys == null) {
				this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (CharSortedSet)this.keys;
		}

		@Override
		public Char2LongSortedMap subMap(char from, char to) {
			return new Char2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Char2LongSortedMap headMap(char to) {
			return new Char2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Char2LongSortedMap tailMap(char from) {
			return new Char2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
		}

		@Override
		public char firstCharKey() {
			synchronized (this.sync) {
				return this.sortedMap.firstCharKey();
			}
		}

		@Override
		public char lastCharKey() {
			synchronized (this.sync) {
				return this.sortedMap.lastCharKey();
			}
		}

		@Deprecated
		@Override
		public Character firstKey() {
			synchronized (this.sync) {
				return this.sortedMap.firstKey();
			}
		}

		@Deprecated
		@Override
		public Character lastKey() {
			synchronized (this.sync) {
				return this.sortedMap.lastKey();
			}
		}

		@Deprecated
		@Override
		public Char2LongSortedMap subMap(Character from, Character to) {
			return new Char2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
		}

		@Deprecated
		@Override
		public Char2LongSortedMap headMap(Character to) {
			return new Char2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
		}

		@Deprecated
		@Override
		public Char2LongSortedMap tailMap(Character from) {
			return new Char2LongSortedMaps.SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
		}
	}

	public static class UnmodifiableSortedMap extends UnmodifiableMap implements Char2LongSortedMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2LongSortedMap sortedMap;

		protected UnmodifiableSortedMap(Char2LongSortedMap m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public CharComparator comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry> char2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.char2LongEntrySet());
			}

			return (ObjectSortedSet<Entry>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Character, Long>> entrySet() {
			return this.char2LongEntrySet();
		}

		@Override
		public CharSortedSet keySet() {
			if (this.keys == null) {
				this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (CharSortedSet)this.keys;
		}

		@Override
		public Char2LongSortedMap subMap(char from, char to) {
			return new Char2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
		}

		@Override
		public Char2LongSortedMap headMap(char to) {
			return new Char2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.headMap(to));
		}

		@Override
		public Char2LongSortedMap tailMap(char from) {
			return new Char2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.tailMap(from));
		}

		@Override
		public char firstCharKey() {
			return this.sortedMap.firstCharKey();
		}

		@Override
		public char lastCharKey() {
			return this.sortedMap.lastCharKey();
		}

		@Deprecated
		@Override
		public Character firstKey() {
			return this.sortedMap.firstKey();
		}

		@Deprecated
		@Override
		public Character lastKey() {
			return this.sortedMap.lastKey();
		}

		@Deprecated
		@Override
		public Char2LongSortedMap subMap(Character from, Character to) {
			return new Char2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
		}

		@Deprecated
		@Override
		public Char2LongSortedMap headMap(Character to) {
			return new Char2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.headMap(to));
		}

		@Deprecated
		@Override
		public Char2LongSortedMap tailMap(Character from) {
			return new Char2LongSortedMaps.UnmodifiableSortedMap(this.sortedMap.tailMap(from));
		}
	}
}
