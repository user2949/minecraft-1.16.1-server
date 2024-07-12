package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2LongMap.BasicEntry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedMap;

public class Object2LongRBTreeMap<K> extends AbstractObject2LongSortedMap<K> implements Serializable, Cloneable {
	protected transient Object2LongRBTreeMap.Entry<K> tree;
	protected int count;
	protected transient Object2LongRBTreeMap.Entry<K> firstEntry;
	protected transient Object2LongRBTreeMap.Entry<K> lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> entries;
	protected transient ObjectSortedSet<K> keys;
	protected transient LongCollection values;
	protected transient boolean modified;
	protected Comparator<? super K> storedComparator;
	protected transient Comparator<? super K> actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;
	private transient Object2LongRBTreeMap.Entry<K>[] nodePath;

	public Object2LongRBTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = this.storedComparator;
	}

	public Object2LongRBTreeMap(Comparator<? super K> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Object2LongRBTreeMap(Map<? extends K, ? extends Long> m) {
		this();
		this.putAll(m);
	}

	public Object2LongRBTreeMap(SortedMap<K, Long> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Object2LongRBTreeMap(Object2LongMap<? extends K> m) {
		this();
		this.putAll(m);
	}

	public Object2LongRBTreeMap(Object2LongSortedMap<K> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Object2LongRBTreeMap(K[] k, long[] v, Comparator<? super K> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Object2LongRBTreeMap(K[] k, long[] v) {
		this(k, v, null);
	}

	final int compare(K k1, K k2) {
		return this.actualComparator == null ? ((Comparable)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
	}

	final Object2LongRBTreeMap.Entry<K> findKey(K k) {
		Object2LongRBTreeMap.Entry<K> e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Object2LongRBTreeMap.Entry<K> locateKey(K k) {
		Object2LongRBTreeMap.Entry<K> e = this.tree;
		Object2LongRBTreeMap.Entry<K> last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[64];
		this.nodePath = new Object2LongRBTreeMap.Entry[64];
	}

	public long addTo(K k, long incr) {
		Object2LongRBTreeMap.Entry<K> e = this.add(k);
		long oldValue = e.value;
		e.value += incr;
		return oldValue;
	}

	@Override
	public long put(K k, long v) {
		Object2LongRBTreeMap.Entry<K> e = this.add(k);
		long oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Object2LongRBTreeMap.Entry<K> add(K k) {
		this.modified = false;
		int maxDepth = 0;
		Object2LongRBTreeMap.Entry<K> e;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Object2LongRBTreeMap.Entry<>(k, this.defRetValue);
		} else {
			Object2LongRBTreeMap.Entry<K> p = this.tree;
			int i = 0;

			label123:
			while (true) {
				int cmp;
				if ((cmp = this.compare(k, p.key)) == 0) {
					while (i-- != 0) {
						this.nodePath[i] = null;
					}

					return p;
				}

				this.nodePath[i] = p;
				if (this.dirPath[i++] = cmp > 0) {
					if (!p.succ()) {
						p = p.right;
						continue;
					}

					this.count++;
					e = new Object2LongRBTreeMap.Entry<>(k, this.defRetValue);
					if (p.right == null) {
						this.lastEntry = e;
					}

					e.left = p;
					e.right = p.right;
					p.right(e);
				} else {
					if (!p.pred()) {
						p = p.left;
						continue;
					}

					this.count++;
					e = new Object2LongRBTreeMap.Entry<>(k, this.defRetValue);
					if (p.left == null) {
						this.firstEntry = e;
					}

					e.right = p;
					e.left = p.left;
					p.left(e);
				}

				this.modified = true;
				maxDepth = i--;

				while (true) {
					if (i <= 0 || this.nodePath[i].black()) {
						break label123;
					}

					if (!this.dirPath[i - 1]) {
						Object2LongRBTreeMap.Entry<K> y = this.nodePath[i - 1].right;
						if (this.nodePath[i - 1].succ() || y.black()) {
							if (!this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Object2LongRBTreeMap.Entry<K> x = this.nodePath[i];
								y = x.right;
								x.right = y.left;
								y.left = x;
								this.nodePath[i - 1].left = y;
								if (y.pred()) {
									y.pred(false);
									x.succ(y);
								}
							}

							Object2LongRBTreeMap.Entry<K> xx = this.nodePath[i - 1];
							xx.black(false);
							y.black(true);
							xx.left = y.right;
							y.right = xx;
							if (i < 2) {
								this.tree = y;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = y;
							} else {
								this.nodePath[i - 2].left = y;
							}

							if (y.succ()) {
								y.succ(false);
								xx.pred(y);
							}
							break label123;
						}

						this.nodePath[i].black(true);
						y.black(true);
						this.nodePath[i - 1].black(false);
						i -= 2;
					} else {
						Object2LongRBTreeMap.Entry<K> y = this.nodePath[i - 1].left;
						if (this.nodePath[i - 1].pred() || y.black()) {
							if (this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Object2LongRBTreeMap.Entry<K> x = this.nodePath[i];
								y = x.left;
								x.left = y.right;
								y.right = x;
								this.nodePath[i - 1].right = y;
								if (y.succ()) {
									y.succ(false);
									x.pred(y);
								}
							}

							Object2LongRBTreeMap.Entry<K> xxx = this.nodePath[i - 1];
							xxx.black(false);
							y.black(true);
							xxx.right = y.left;
							y.left = xxx;
							if (i < 2) {
								this.tree = y;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = y;
							} else {
								this.nodePath[i - 2].left = y;
							}

							if (y.pred()) {
								y.pred(false);
								xxx.succ(y);
							}
							break label123;
						}

						this.nodePath[i].black(true);
						y.black(true);
						this.nodePath[i - 1].black(false);
						i -= 2;
					}
				}
			}
		}

		this.tree.black(true);

		while (maxDepth-- != 0) {
			this.nodePath[maxDepth] = null;
		}

		return e;
	}

	@Override
	public long removeLong(Object k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Object2LongRBTreeMap.Entry<K> p = this.tree;
			int i = 0;
			K kk = (K)k;

			int cmp;
			while ((cmp = this.compare(kk, p.key)) != 0) {
				this.dirPath[i] = cmp > 0;
				this.nodePath[i] = p;
				if (this.dirPath[i++]) {
					if ((p = p.right()) == null) {
						while (i-- != 0) {
							this.nodePath[i] = null;
						}

						return this.defRetValue;
					}
				} else if ((p = p.left()) == null) {
					while (i-- != 0) {
						this.nodePath[i] = null;
					}

					return this.defRetValue;
				}
			}

			if (p.left == null) {
				this.firstEntry = p.next();
			}

			if (p.right == null) {
				this.lastEntry = p.prev();
			}

			if (p.succ()) {
				if (p.pred()) {
					if (i == 0) {
						this.tree = p.left;
					} else if (this.dirPath[i - 1]) {
						this.nodePath[i - 1].succ(p.right);
					} else {
						this.nodePath[i - 1].pred(p.left);
					}
				} else {
					p.prev().right = p.right;
					if (i == 0) {
						this.tree = p.left;
					} else if (this.dirPath[i - 1]) {
						this.nodePath[i - 1].right = p.left;
					} else {
						this.nodePath[i - 1].left = p.left;
					}
				}
			} else {
				Object2LongRBTreeMap.Entry<K> r = p.right;
				if (r.pred()) {
					r.left = p.left;
					r.pred(p.pred());
					if (!r.pred()) {
						r.prev().right = r;
					}

					if (i == 0) {
						this.tree = r;
					} else if (this.dirPath[i - 1]) {
						this.nodePath[i - 1].right = r;
					} else {
						this.nodePath[i - 1].left = r;
					}

					boolean color = r.black();
					r.black(p.black());
					p.black(color);
					this.dirPath[i] = true;
					this.nodePath[i++] = r;
				} else {
					int j = i++;

					while (true) {
						this.dirPath[i] = false;
						this.nodePath[i++] = r;
						Object2LongRBTreeMap.Entry<K> s = r.left;
						if (s.pred()) {
							this.dirPath[j] = true;
							this.nodePath[j] = s;
							if (s.succ()) {
								r.pred(s);
							} else {
								r.left = s.right;
							}

							s.left = p.left;
							if (!p.pred()) {
								p.prev().right = s;
								s.pred(false);
							}

							s.right(p.right);
							boolean color = s.black();
							s.black(p.black());
							p.black(color);
							if (j == 0) {
								this.tree = s;
							} else if (this.dirPath[j - 1]) {
								this.nodePath[j - 1].right = s;
							} else {
								this.nodePath[j - 1].left = s;
							}
							break;
						}

						r = s;
					}
				}
			}

			int maxDepth = i;
			if (p.black()) {
				for (; i > 0; i--) {
					if (this.dirPath[i - 1] && !this.nodePath[i - 1].succ() || !this.dirPath[i - 1] && !this.nodePath[i - 1].pred()) {
						Object2LongRBTreeMap.Entry<K> x = this.dirPath[i - 1] ? this.nodePath[i - 1].right : this.nodePath[i - 1].left;
						if (!x.black()) {
							x.black(true);
							break;
						}
					}

					if (!this.dirPath[i - 1]) {
						Object2LongRBTreeMap.Entry<K> w = this.nodePath[i - 1].right;
						if (!w.black()) {
							w.black(true);
							this.nodePath[i - 1].black(false);
							this.nodePath[i - 1].right = w.left;
							w.left = this.nodePath[i - 1];
							if (i < 2) {
								this.tree = w;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = w;
							} else {
								this.nodePath[i - 2].left = w;
							}

							this.nodePath[i] = this.nodePath[i - 1];
							this.dirPath[i] = false;
							this.nodePath[i - 1] = w;
							if (maxDepth == i++) {
								maxDepth++;
							}

							w = this.nodePath[i - 1].right;
						}

						if (!w.pred() && !w.left.black() || !w.succ() && !w.right.black()) {
							if (w.succ() || w.right.black()) {
								Object2LongRBTreeMap.Entry<K> y = w.left;
								y.black(true);
								w.black(false);
								w.left = y.right;
								y.right = w;
								w = this.nodePath[i - 1].right = y;
								if (w.succ()) {
									w.succ(false);
									w.right.pred(w);
								}
							}

							w.black(this.nodePath[i - 1].black());
							this.nodePath[i - 1].black(true);
							w.right.black(true);
							this.nodePath[i - 1].right = w.left;
							w.left = this.nodePath[i - 1];
							if (i < 2) {
								this.tree = w;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = w;
							} else {
								this.nodePath[i - 2].left = w;
							}

							if (w.pred()) {
								w.pred(false);
								this.nodePath[i - 1].succ(w);
							}
							break;
						}

						w.black(false);
					} else {
						Object2LongRBTreeMap.Entry<K> wx = this.nodePath[i - 1].left;
						if (!wx.black()) {
							wx.black(true);
							this.nodePath[i - 1].black(false);
							this.nodePath[i - 1].left = wx.right;
							wx.right = this.nodePath[i - 1];
							if (i < 2) {
								this.tree = wx;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = wx;
							} else {
								this.nodePath[i - 2].left = wx;
							}

							this.nodePath[i] = this.nodePath[i - 1];
							this.dirPath[i] = true;
							this.nodePath[i - 1] = wx;
							if (maxDepth == i++) {
								maxDepth++;
							}

							wx = this.nodePath[i - 1].left;
						}

						if (!wx.pred() && !wx.left.black() || !wx.succ() && !wx.right.black()) {
							if (wx.pred() || wx.left.black()) {
								Object2LongRBTreeMap.Entry<K> y = wx.right;
								y.black(true);
								wx.black(false);
								wx.right = y.left;
								y.left = wx;
								wx = this.nodePath[i - 1].left = y;
								if (wx.pred()) {
									wx.pred(false);
									wx.left.succ(wx);
								}
							}

							wx.black(this.nodePath[i - 1].black());
							this.nodePath[i - 1].black(true);
							wx.left.black(true);
							this.nodePath[i - 1].left = wx.right;
							wx.right = this.nodePath[i - 1];
							if (i < 2) {
								this.tree = wx;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = wx;
							} else {
								this.nodePath[i - 2].left = wx;
							}

							if (wx.succ()) {
								wx.succ(false);
								this.nodePath[i - 1].pred(wx);
							}
							break;
						}

						wx.black(false);
					}
				}

				if (this.tree != null) {
					this.tree.black(true);
				}
			}

			this.modified = true;
			this.count--;

			while (maxDepth-- != 0) {
				this.nodePath[maxDepth] = null;
			}

			return p.value;
		}
	}

	@Override
	public boolean containsValue(long v) {
		Object2LongRBTreeMap<K>.ValueIterator i = new Object2LongRBTreeMap.ValueIterator();
		int j = this.count;

		while (j-- != 0) {
			long ev = i.nextLong();
			if (ev == v) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void clear() {
		this.count = 0;
		this.tree = null;
		this.entries = null;
		this.values = null;
		this.keys = null;
		this.firstEntry = this.lastEntry = null;
	}

	@Override
	public boolean containsKey(Object k) {
		return this.findKey((K)k) != null;
	}

	@Override
	public int size() {
		return this.count;
	}

	@Override
	public boolean isEmpty() {
		return this.count == 0;
	}

	@Override
	public long getLong(Object k) {
		Object2LongRBTreeMap.Entry<K> e = this.findKey((K)k);
		return e == null ? this.defRetValue : e.value;
	}

	public K firstKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	public K lastKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> object2LongEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>>() {
				final Comparator<? super it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> comparator = (x, y) -> Object2LongRBTreeMap.this.actualComparator
						.compare(x.getKey(), y.getKey());

				public Comparator<? super it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> iterator() {
					return Object2LongRBTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> iterator(
					it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> from
				) {
					return Object2LongRBTreeMap.this.new EntryIterator(from.getKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getValue() != null && e.getValue() instanceof Long) {
							Object2LongRBTreeMap.Entry<K> f = (Object2LongRBTreeMap.Entry<K>)Object2LongRBTreeMap.this.findKey(e.getKey());
							return e.equals(f);
						} else {
							return false;
						}
					}
				}

				public boolean remove(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getValue() != null && e.getValue() instanceof Long) {
							Object2LongRBTreeMap.Entry<K> f = (Object2LongRBTreeMap.Entry<K>)Object2LongRBTreeMap.this.findKey(e.getKey());
							if (f != null && f.getLongValue() == (Long)e.getValue()) {
								Object2LongRBTreeMap.this.removeLong(f.key);
								return true;
							} else {
								return false;
							}
						} else {
							return false;
						}
					}
				}

				public int size() {
					return Object2LongRBTreeMap.this.count;
				}

				public void clear() {
					Object2LongRBTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> first() {
					return Object2LongRBTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> last() {
					return Object2LongRBTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> subSet(
					it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> from, it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> to
				) {
					return (ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>>)Object2LongRBTreeMap.this.subMap(from.getKey(), to.getKey())
						.object2LongEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> headSet(it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> to) {
					return (ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>>)Object2LongRBTreeMap.this.headMap(to.getKey()).object2LongEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> tailSet(it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> from) {
					return (ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>>)Object2LongRBTreeMap.this.tailMap(from.getKey()).object2LongEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Object2LongRBTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public LongCollection values() {
		if (this.values == null) {
			this.values = new AbstractLongCollection() {
				@Override
				public LongIterator iterator() {
					return Object2LongRBTreeMap.this.new ValueIterator();
				}

				@Override
				public boolean contains(long k) {
					return Object2LongRBTreeMap.this.containsValue(k);
				}

				public int size() {
					return Object2LongRBTreeMap.this.count;
				}

				public void clear() {
					Object2LongRBTreeMap.this.clear();
				}
			};
		}

		return this.values;
	}

	@Override
	public Comparator<? super K> comparator() {
		return this.actualComparator;
	}

	@Override
	public Object2LongSortedMap<K> headMap(K to) {
		return new Object2LongRBTreeMap.Submap(null, true, to, false);
	}

	@Override
	public Object2LongSortedMap<K> tailMap(K from) {
		return new Object2LongRBTreeMap.Submap(from, false, null, true);
	}

	@Override
	public Object2LongSortedMap<K> subMap(K from, K to) {
		return new Object2LongRBTreeMap.Submap(from, false, to, false);
	}

	public Object2LongRBTreeMap<K> clone() {
		Object2LongRBTreeMap<K> c;
		try {
			c = (Object2LongRBTreeMap<K>)super.clone();
		} catch (CloneNotSupportedException var7) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.allocatePaths();
		if (this.count == 0) {
			return c;
		} else {
			Object2LongRBTreeMap.Entry<K> rp = new Object2LongRBTreeMap.Entry<>();
			Object2LongRBTreeMap.Entry<K> rq = new Object2LongRBTreeMap.Entry<>();
			Object2LongRBTreeMap.Entry<K> p = rp;
			rp.left(this.tree);
			Object2LongRBTreeMap.Entry<K> q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Object2LongRBTreeMap.Entry<K> e = p.left.clone();
					e.pred(q.left);
					e.succ(q);
					q.left(e);
					p = p.left;
					q = q.left;
				} else {
					while (p.succ()) {
						p = p.right;
						if (p == null) {
							q.right = null;
							c.tree = rq.left;
							c.firstEntry = c.tree;

							while (c.firstEntry.left != null) {
								c.firstEntry = c.firstEntry.left;
							}

							c.lastEntry = c.tree;

							while (c.lastEntry.right != null) {
								c.lastEntry = c.lastEntry.right;
							}

							return c;
						}

						q = q.right;
					}

					p = p.right;
					q = q.right;
				}

				if (!p.succ()) {
					Object2LongRBTreeMap.Entry<K> e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Object2LongRBTreeMap<K>.EntryIterator i = new Object2LongRBTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Object2LongRBTreeMap.Entry<K> e = i.nextEntry();
			s.writeObject(e.key);
			s.writeLong(e.value);
		}
	}

	private Object2LongRBTreeMap.Entry<K> readTree(ObjectInputStream s, int n, Object2LongRBTreeMap.Entry<K> pred, Object2LongRBTreeMap.Entry<K> succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Object2LongRBTreeMap.Entry<K> top = new Object2LongRBTreeMap.Entry<>((K)s.readObject(), s.readLong());
			top.pred(pred);
			top.succ(succ);
			top.black(true);
			return top;
		} else if (n == 2) {
			Object2LongRBTreeMap.Entry<K> top = new Object2LongRBTreeMap.Entry<>((K)s.readObject(), s.readLong());
			top.black(true);
			top.right(new Object2LongRBTreeMap.Entry<>((K)s.readObject(), s.readLong()));
			top.right.pred(top);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Object2LongRBTreeMap.Entry<K> top = new Object2LongRBTreeMap.Entry<>();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = (K)s.readObject();
			top.value = s.readLong();
			top.black(true);
			top.right(this.readTree(s, rightN, top, succ));
			if (n + 2 == (n + 2 & -(n + 2))) {
				top.right.black(false);
			}

			return top;
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.setActualComparator();
		this.allocatePaths();
		if (this.count != 0) {
			this.tree = this.readTree(s, this.count, null, null);
			Object2LongRBTreeMap.Entry<K> e = this.tree;

			while (e.left() != null) {
				e = e.left();
			}

			this.firstEntry = e;
			e = this.tree;

			while (e.right() != null) {
				e = e.right();
			}

			this.lastEntry = e;
		}
	}

	private static final class Entry<K> extends BasicEntry<K> implements Cloneable {
		private static final int BLACK_MASK = 1;
		private static final int SUCC_MASK = Integer.MIN_VALUE;
		private static final int PRED_MASK = 1073741824;
		Object2LongRBTreeMap.Entry<K> left;
		Object2LongRBTreeMap.Entry<K> right;
		int info;

		Entry() {
			super(null, 0L);
		}

		Entry(K k, long v) {
			super(k, v);
			this.info = -1073741824;
		}

		Object2LongRBTreeMap.Entry<K> left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Object2LongRBTreeMap.Entry<K> right() {
			return (this.info & -2147483648) != 0 ? null : this.right;
		}

		boolean pred() {
			return (this.info & 1073741824) != 0;
		}

		boolean succ() {
			return (this.info & -2147483648) != 0;
		}

		void pred(boolean pred) {
			if (pred) {
				this.info |= 1073741824;
			} else {
				this.info &= -1073741825;
			}
		}

		void succ(boolean succ) {
			if (succ) {
				this.info |= Integer.MIN_VALUE;
			} else {
				this.info &= Integer.MAX_VALUE;
			}
		}

		void pred(Object2LongRBTreeMap.Entry<K> pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Object2LongRBTreeMap.Entry<K> succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Object2LongRBTreeMap.Entry<K> left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Object2LongRBTreeMap.Entry<K> right) {
			this.info &= Integer.MAX_VALUE;
			this.right = right;
		}

		boolean black() {
			return (this.info & 1) != 0;
		}

		void black(boolean black) {
			if (black) {
				this.info |= 1;
			} else {
				this.info &= -2;
			}
		}

		Object2LongRBTreeMap.Entry<K> next() {
			Object2LongRBTreeMap.Entry<K> next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Object2LongRBTreeMap.Entry<K> prev() {
			Object2LongRBTreeMap.Entry<K> prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		@Override
		public long setValue(long value) {
			long oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Object2LongRBTreeMap.Entry<K> clone() {
			Object2LongRBTreeMap.Entry<K> c;
			try {
				c = (Object2LongRBTreeMap.Entry<K>)super.clone();
			} catch (CloneNotSupportedException var3) {
				throw new InternalError();
			}

			c.key = this.key;
			c.value = this.value;
			c.info = this.info;
			return c;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<K, Long> e = (java.util.Map.Entry<K, Long>)o;
				return Objects.equals(this.key, e.getKey()) && this.value == (Long)e.getValue();
			}
		}

		@Override
		public int hashCode() {
			return this.key.hashCode() ^ HashCommon.long2int(this.value);
		}

		@Override
		public String toString() {
			return this.key + "=>" + this.value;
		}
	}

	private class EntryIterator extends Object2LongRBTreeMap<K>.TreeIterator implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> {
		EntryIterator() {
		}

		EntryIterator(K k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> previous() {
			return this.previousEntry();
		}
	}

	private final class KeyIterator extends Object2LongRBTreeMap<K>.TreeIterator implements ObjectListIterator<K> {
		public KeyIterator() {
		}

		public KeyIterator(K k) {
			super(k);
		}

		public K next() {
			return this.nextEntry().key;
		}

		@Override
		public K previous() {
			return this.previousEntry().key;
		}
	}

	private class KeySet extends AbstractObject2LongSortedMap<K>.KeySet {
		private KeySet() {
			super(Object2LongRBTreeMap.this);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return Object2LongRBTreeMap.this.new KeyIterator();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return Object2LongRBTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractObject2LongSortedMap<K> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		K from;
		K to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> entries;
		protected transient ObjectSortedSet<K> keys;
		protected transient LongCollection values;

		public Submap(K from, boolean bottom, K to, boolean top) {
			if (!bottom && !top && Object2LongRBTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Object2LongRBTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Object2LongRBTreeMap<K>.Submap.SubmapIterator i = new Object2LongRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(K k) {
			return (this.bottom || Object2LongRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2LongRBTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> object2LongEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> iterator(
						it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> from
					) {
						return Submap.this.new SubmapEntryIterator(from.getKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> comparator() {
						return Object2LongRBTreeMap.this.object2LongEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getValue() != null && e.getValue() instanceof Long) {
								Object2LongRBTreeMap.Entry<K> f = Object2LongRBTreeMap.this.findKey((K)e.getKey());
								return f != null && Submap.this.in(f.key) && e.equals(f);
							} else {
								return false;
							}
						}
					}

					public boolean remove(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getValue() != null && e.getValue() instanceof Long) {
								Object2LongRBTreeMap.Entry<K> f = Object2LongRBTreeMap.this.findKey((K)e.getKey());
								if (f != null && Submap.this.in(f.key)) {
									Submap.this.removeLong(f.key);
								}

								return f != null;
							} else {
								return false;
							}
						}
					}

					public int size() {
						int c = 0;
						Iterator<?> i = this.iterator();

						while (i.hasNext()) {
							c++;
							i.next();
						}

						return c;
					}

					public boolean isEmpty() {
						return !Submap.this.new SubmapIterator().hasNext();
					}

					public void clear() {
						Submap.this.clear();
					}

					public it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> subSet(
						it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> from, it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> to
					) {
						return (ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>>)Submap.this.subMap(from.getKey(), to.getKey()).object2LongEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> headSet(it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> to) {
						return Submap.this.headMap((K)to.getKey()).object2LongEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> tailSet(it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> from) {
						return Submap.this.tailMap((K)from.getKey()).object2LongEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = new Object2LongRBTreeMap.Submap.KeySet();
			}

			return this.keys;
		}

		@Override
		public LongCollection values() {
			if (this.values == null) {
				this.values = new AbstractLongCollection() {
					@Override
					public LongIterator iterator() {
						return Submap.this.new SubmapValueIterator();
					}

					@Override
					public boolean contains(long k) {
						return Submap.this.containsValue(k);
					}

					public int size() {
						return Submap.this.size();
					}

					public void clear() {
						Submap.this.clear();
					}
				};
			}

			return this.values;
		}

		@Override
		public boolean containsKey(Object k) {
			return this.in((K)k) && Object2LongRBTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(long v) {
			Object2LongRBTreeMap<K>.Submap.SubmapIterator i = new Object2LongRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				long ev = i.nextEntry().value;
				if (ev == v) {
					return true;
				}
			}

			return false;
		}

		@Override
		public long getLong(Object k) {
			Object2LongRBTreeMap.Entry<K> e;
			return this.in((K)k) && (e = Object2LongRBTreeMap.this.findKey((K)k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public long put(K k, long v) {
			Object2LongRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				long oldValue = Object2LongRBTreeMap.this.put(k, v);
				return Object2LongRBTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public long removeLong(Object k) {
			Object2LongRBTreeMap.this.modified = false;
			if (!this.in((K)k)) {
				return this.defRetValue;
			} else {
				long oldValue = Object2LongRBTreeMap.this.removeLong(k);
				return Object2LongRBTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Object2LongRBTreeMap<K>.Submap.SubmapIterator i = new Object2LongRBTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Object2LongRBTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public Comparator<? super K> comparator() {
			return Object2LongRBTreeMap.this.actualComparator;
		}

		@Override
		public Object2LongSortedMap<K> headMap(K to) {
			if (this.top) {
				return Object2LongRBTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Object2LongRBTreeMap.this.compare(to, this.to) < 0 ? Object2LongRBTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Object2LongSortedMap<K> tailMap(K from) {
			if (this.bottom) {
				return Object2LongRBTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Object2LongRBTreeMap.this.compare(from, this.from) > 0 ? Object2LongRBTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Object2LongSortedMap<K> subMap(K from, K to) {
			if (this.top && this.bottom) {
				return Object2LongRBTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Object2LongRBTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Object2LongRBTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Object2LongRBTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Object2LongRBTreeMap.Entry<K> firstEntry() {
			if (Object2LongRBTreeMap.this.tree == null) {
				return null;
			} else {
				Object2LongRBTreeMap.Entry<K> e;
				if (this.bottom) {
					e = Object2LongRBTreeMap.this.firstEntry;
				} else {
					e = Object2LongRBTreeMap.this.locateKey(this.from);
					if (Object2LongRBTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Object2LongRBTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Object2LongRBTreeMap.Entry<K> lastEntry() {
			if (Object2LongRBTreeMap.this.tree == null) {
				return null;
			} else {
				Object2LongRBTreeMap.Entry<K> e;
				if (this.top) {
					e = Object2LongRBTreeMap.this.lastEntry;
				} else {
					e = Object2LongRBTreeMap.this.locateKey(this.to);
					if (Object2LongRBTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Object2LongRBTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		public K firstKey() {
			Object2LongRBTreeMap.Entry<K> e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		public K lastKey() {
			Object2LongRBTreeMap.Entry<K> e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends AbstractObject2LongSortedMap<K>.KeySet {
			private KeySet() {
				super(Submap.this);
			}

			@Override
			public ObjectBidirectionalIterator<K> iterator() {
				return Submap.this.new SubmapKeyIterator();
			}

			@Override
			public ObjectBidirectionalIterator<K> iterator(K from) {
				return Submap.this.new SubmapKeyIterator(from);
			}
		}

		private class SubmapEntryIterator
			extends Object2LongRBTreeMap<K>.Submap.SubmapIterator
			implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K>> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(K k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<K> previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Object2LongRBTreeMap<K>.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(K k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Object2LongRBTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Object2LongRBTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Object2LongRBTreeMap.this.locateKey(k);
						if (Object2LongRBTreeMap.this.compare(this.next.key, k) <= 0) {
							this.prev = this.next;
							this.next = this.next.next();
						} else {
							this.prev = this.next.prev();
						}
					}
				}
			}

			@Override
			void updatePrevious() {
				this.prev = this.prev.prev();
				if (!Submap.this.bottom && this.prev != null && Object2LongRBTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Object2LongRBTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Object2LongRBTreeMap<K>.Submap.SubmapIterator implements ObjectListIterator<K> {
			public SubmapKeyIterator() {
			}

			public SubmapKeyIterator(K from) {
				super(from);
			}

			public K next() {
				return this.nextEntry().key;
			}

			@Override
			public K previous() {
				return this.previousEntry().key;
			}
		}

		private final class SubmapValueIterator extends Object2LongRBTreeMap<K>.Submap.SubmapIterator implements LongListIterator {
			private SubmapValueIterator() {
			}

			@Override
			public long nextLong() {
				return this.nextEntry().value;
			}

			@Override
			public long previousLong() {
				return this.previousEntry().value;
			}
		}
	}

	private class TreeIterator {
		Object2LongRBTreeMap.Entry<K> prev;
		Object2LongRBTreeMap.Entry<K> next;
		Object2LongRBTreeMap.Entry<K> curr;
		int index = 0;

		TreeIterator() {
			this.next = Object2LongRBTreeMap.this.firstEntry;
		}

		TreeIterator(K k) {
			if ((this.next = Object2LongRBTreeMap.this.locateKey(k)) != null) {
				if (Object2LongRBTreeMap.this.compare(this.next.key, k) <= 0) {
					this.prev = this.next;
					this.next = this.next.next();
				} else {
					this.prev = this.next.prev();
				}
			}
		}

		public boolean hasNext() {
			return this.next != null;
		}

		public boolean hasPrevious() {
			return this.prev != null;
		}

		void updateNext() {
			this.next = this.next.next();
		}

		Object2LongRBTreeMap.Entry<K> nextEntry() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.prev = this.next;
				this.index++;
				this.updateNext();
				return this.curr;
			}
		}

		void updatePrevious() {
			this.prev = this.prev.prev();
		}

		Object2LongRBTreeMap.Entry<K> previousEntry() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.next = this.prev;
				this.index--;
				this.updatePrevious();
				return this.curr;
			}
		}

		public int nextIndex() {
			return this.index;
		}

		public int previousIndex() {
			return this.index - 1;
		}

		public void remove() {
			if (this.curr == null) {
				throw new IllegalStateException();
			} else {
				if (this.curr == this.prev) {
					this.index--;
				}

				this.next = this.prev = this.curr;
				this.updatePrevious();
				this.updateNext();
				Object2LongRBTreeMap.this.removeLong(this.curr.key);
				this.curr = null;
			}
		}

		public int skip(int n) {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextEntry();
			}

			return n - i - 1;
		}

		public int back(int n) {
			int i = n;

			while (i-- != 0 && this.hasPrevious()) {
				this.previousEntry();
			}

			return n - i - 1;
		}
	}

	private final class ValueIterator extends Object2LongRBTreeMap<K>.TreeIterator implements LongListIterator {
		private ValueIterator() {
		}

		@Override
		public long nextLong() {
			return this.nextEntry().value;
		}

		@Override
		public long previousLong() {
			return this.previousEntry().value;
		}
	}
}