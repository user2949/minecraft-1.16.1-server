package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps.KeySet;
import com.google.common.collect.Maps.ViewCachingAbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
class FilteredEntryMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
	final Multimap<K, V> unfiltered;
	final Predicate<? super Entry<K, V>> predicate;

	FilteredEntryMultimap(Multimap<K, V> unfiltered, Predicate<? super Entry<K, V>> predicate) {
		this.unfiltered = Preconditions.checkNotNull(unfiltered);
		this.predicate = Preconditions.checkNotNull(predicate);
	}

	@Override
	public Multimap<K, V> unfiltered() {
		return this.unfiltered;
	}

	@Override
	public Predicate<? super Entry<K, V>> entryPredicate() {
		return this.predicate;
	}

	@Override
	public int size() {
		return this.entries().size();
	}

	private boolean satisfies(K key, V value) {
		return this.predicate.apply(Maps.immutableEntry(key, value));
	}

	static <E> Collection<E> filterCollection(Collection<E> collection, Predicate<? super E> predicate) {
		return (Collection<E>)(collection instanceof Set ? Sets.<E>filter((Set<E>)collection, predicate) : Collections2.filter(collection, predicate));
	}

	@Override
	public boolean containsKey(@Nullable Object key) {
		return this.asMap().get(key) != null;
	}

	@Override
	public Collection<V> removeAll(@Nullable Object key) {
		return MoreObjects.firstNonNull((Collection<V>)this.asMap().remove(key), this.unmodifiableEmptyCollection());
	}

	Collection<V> unmodifiableEmptyCollection() {
		return (Collection<V>)(this.unfiltered instanceof SetMultimap ? Collections.emptySet() : Collections.emptyList());
	}

	@Override
	public void clear() {
		this.entries().clear();
	}

	@Override
	public Collection<V> get(K key) {
		return filterCollection(this.unfiltered.get(key), new FilteredEntryMultimap.ValuePredicate(key));
	}

	@Override
	Collection<Entry<K, V>> createEntries() {
		return filterCollection(this.unfiltered.entries(), this.predicate);
	}

	@Override
	Collection<V> createValues() {
		return new FilteredMultimapValues<K, V>(this);
	}

	@Override
	Iterator<Entry<K, V>> entryIterator() {
		throw new AssertionError("should never be called");
	}

	@Override
	Map<K, Collection<V>> createAsMap() {
		return new FilteredEntryMultimap.AsMap();
	}

	@Override
	public Set<K> keySet() {
		return this.asMap().keySet();
	}

	boolean removeEntriesIf(Predicate<? super Entry<K, Collection<V>>> predicate) {
		Iterator<Entry<K, Collection<V>>> entryIterator = this.unfiltered.asMap().entrySet().iterator();
		boolean changed = false;

		while (entryIterator.hasNext()) {
			Entry<K, Collection<V>> entry = (Entry<K, Collection<V>>)entryIterator.next();
			K key = (K)entry.getKey();
			Collection<V> collection = filterCollection((Collection<V>)entry.getValue(), new FilteredEntryMultimap.ValuePredicate(key));
			if (!collection.isEmpty() && predicate.apply(Maps.immutableEntry(key, collection))) {
				if (collection.size() == ((Collection)entry.getValue()).size()) {
					entryIterator.remove();
				} else {
					collection.clear();
				}

				changed = true;
			}
		}

		return changed;
	}

	@Override
	Multiset<K> createKeys() {
		return new FilteredEntryMultimap.Keys();
	}

	class AsMap extends ViewCachingAbstractMap<K, Collection<V>> {
		public boolean containsKey(@Nullable Object key) {
			return this.get(key) != null;
		}

		public void clear() {
			FilteredEntryMultimap.this.clear();
		}

		public Collection<V> get(@Nullable Object key) {
			Collection<V> result = (Collection<V>)FilteredEntryMultimap.this.unfiltered.asMap().get(key);
			if (result == null) {
				return null;
			} else {
				result = FilteredEntryMultimap.filterCollection(result, FilteredEntryMultimap.this.new ValuePredicate(key));
				return result.isEmpty() ? null : result;
			}
		}

		public Collection<V> remove(@Nullable Object key) {
			Collection<V> collection = (Collection<V>)FilteredEntryMultimap.this.unfiltered.asMap().get(key);
			if (collection == null) {
				return null;
			} else {
				K k = (K)key;
				List<V> result = Lists.<V>newArrayList();
				Iterator<V> itr = collection.iterator();

				while (itr.hasNext()) {
					V v = (V)itr.next();
					if (FilteredEntryMultimap.this.satisfies(k, v)) {
						itr.remove();
						result.add(v);
					}
				}

				if (result.isEmpty()) {
					return null;
				} else {
					return (Collection<V>)(FilteredEntryMultimap.this.unfiltered instanceof SetMultimap
						? Collections.unmodifiableSet(Sets.newLinkedHashSet(result))
						: Collections.unmodifiableList(result));
				}
			}
		}

		@Override
		Set<K> createKeySet() {
			class 1KeySetImpl extends KeySet<K, Collection<V>> {
				_KeySetImpl/* $VF was: 1KeySetImpl*/() {
					super(AsMap.this);
				}

				@Override
				public boolean removeAll(Collection<?> c) {
					return FilteredEntryMultimap.this.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.in((Collection<? extends K>)c)));
				}

				@Override
				public boolean retainAll(Collection<?> c) {
					return FilteredEntryMultimap.this.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in((Collection<? extends K>)c))));
				}

				@Override
				public boolean remove(@Nullable Object o) {
					return AsMap.this.remove(o) != null;
				}
			}

			return new 1KeySetImpl();
		}

		@Override
		Set<Entry<K, Collection<V>>> createEntrySet() {
			class 1EntrySetImpl extends Maps.EntrySet<K, Collection<V>> {
				@Override
				Map<K, Collection<V>> map() {
					return AsMap.this;
				}

				public Iterator<Entry<K, Collection<V>>> iterator() {
					return new AbstractIterator<Entry<K, Collection<V>>>() {
						final Iterator<Entry<K, Collection<V>>> backingIterator = FilteredEntryMultimap.this.unfiltered.asMap().entrySet().iterator();

						protected Entry<K, Collection<V>> computeNext() {
							while (this.backingIterator.hasNext()) {
								Entry<K, Collection<V>> entry = (Entry<K, Collection<V>>)this.backingIterator.next();
								K key = (K)entry.getKey();
								Collection<V> collection = FilteredEntryMultimap.filterCollection((Collection<V>)entry.getValue(), FilteredEntryMultimap.this.new ValuePredicate(key));
								if (!collection.isEmpty()) {
									return Maps.immutableEntry(key, collection);
								}
							}

							return this.endOfData();
						}
					};
				}

				@Override
				public boolean removeAll(Collection<?> c) {
					return FilteredEntryMultimap.this.removeEntriesIf(Predicates.in((Collection<? extends Entry<K, Collection<V>>>)c));
				}

				@Override
				public boolean retainAll(Collection<?> c) {
					return FilteredEntryMultimap.this.removeEntriesIf(Predicates.not(Predicates.in((Collection<? extends Entry<K, Collection<V>>>)c)));
				}

				@Override
				public int size() {
					return Iterators.size(this.iterator());
				}
			}

			return new 1EntrySetImpl();
		}

		@Override
		Collection<Collection<V>> createValues() {
			class 1ValuesImpl extends Maps.Values<K, Collection<V>> {
				_ValuesImpl/* $VF was: 1ValuesImpl*/() {
					super(AsMap.this);
				}

				@Override
				public boolean remove(@Nullable Object o) {
					if (o instanceof Collection) {
						Collection<?> c = (Collection<?>)o;
						Iterator<Entry<K, Collection<V>>> entryIterator = FilteredEntryMultimap.this.unfiltered.asMap().entrySet().iterator();

						while (entryIterator.hasNext()) {
							Entry<K, Collection<V>> entry = (Entry<K, Collection<V>>)entryIterator.next();
							K key = (K)entry.getKey();
							Collection<V> collection = FilteredEntryMultimap.filterCollection((Collection<V>)entry.getValue(), FilteredEntryMultimap.this.new ValuePredicate(key));
							if (!collection.isEmpty() && c.equals(collection)) {
								if (collection.size() == ((Collection)entry.getValue()).size()) {
									entryIterator.remove();
								} else {
									collection.clear();
								}

								return true;
							}
						}
					}

					return false;
				}

				@Override
				public boolean removeAll(Collection<?> c) {
					return FilteredEntryMultimap.this.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.in((Collection<? extends Collection<V>>)c)));
				}

				@Override
				public boolean retainAll(Collection<?> c) {
					return FilteredEntryMultimap.this.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in((Collection<? extends Collection<V>>)c))));
				}
			}

			return new 1ValuesImpl();
		}
	}

	class Keys extends Multimaps.Keys<K, V> {
		Keys() {
			super(FilteredEntryMultimap.this);
		}

		@Override
		public int remove(@Nullable Object key, int occurrences) {
			CollectPreconditions.checkNonnegative(occurrences, "occurrences");
			if (occurrences == 0) {
				return this.count(key);
			} else {
				Collection<V> collection = (Collection<V>)FilteredEntryMultimap.this.unfiltered.asMap().get(key);
				if (collection == null) {
					return 0;
				} else {
					K k = (K)key;
					int oldCount = 0;
					Iterator<V> itr = collection.iterator();

					while (itr.hasNext()) {
						V v = (V)itr.next();
						if (FilteredEntryMultimap.this.satisfies(k, v)) {
							if (++oldCount <= occurrences) {
								itr.remove();
							}
						}
					}

					return oldCount;
				}
			}
		}

		@Override
		public Set<com.google.common.collect.Multiset.Entry<K>> entrySet() {
			return new Multisets.EntrySet<K>() {
				@Override
				Multiset<K> multiset() {
					return Keys.this;
				}

				public Iterator<com.google.common.collect.Multiset.Entry<K>> iterator() {
					return Keys.this.entryIterator();
				}

				public int size() {
					return FilteredEntryMultimap.this.keySet().size();
				}

				private boolean removeEntriesIf(Predicate<? super com.google.common.collect.Multiset.Entry<K>> predicate) {
					return FilteredEntryMultimap.this.removeEntriesIf(new Predicate<java.util.Map.Entry<K, Collection<V>>>() {
						public boolean apply(java.util.Map.Entry<K, Collection<V>> entry) {
							return predicate.apply(Multisets.immutableEntry((K)entry.getKey(), ((Collection)entry.getValue()).size()));
						}
					});
				}

				@Override
				public boolean removeAll(Collection<?> c) {
					return this.removeEntriesIf(Predicates.in((Collection<? extends com.google.common.collect.Multiset.Entry<K>>)c));
				}

				@Override
				public boolean retainAll(Collection<?> c) {
					return this.removeEntriesIf(Predicates.not(Predicates.in((Collection<? extends com.google.common.collect.Multiset.Entry<K>>)c)));
				}
			};
		}
	}

	final class ValuePredicate implements Predicate<V> {
		private final K key;

		ValuePredicate(K key) {
			this.key = key;
		}

		@Override
		public boolean apply(@Nullable V value) {
			return FilteredEntryMultimap.this.satisfies(this.key, value);
		}
	}
}
