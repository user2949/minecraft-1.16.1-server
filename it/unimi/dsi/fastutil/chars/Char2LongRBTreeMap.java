package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

public class Char2LongRBTreeMap extends AbstractChar2LongSortedMap implements Serializable, Cloneable {
	protected transient Char2LongRBTreeMap.Entry tree;
	protected int count;
	protected transient Char2LongRBTreeMap.Entry firstEntry;
	protected transient Char2LongRBTreeMap.Entry lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> entries;
	protected transient CharSortedSet keys;
	protected transient LongCollection values;
	protected transient boolean modified;
	protected Comparator<? super Character> storedComparator;
	protected transient CharComparator actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;
	private transient Char2LongRBTreeMap.Entry[] nodePath;

	public Char2LongRBTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = CharComparators.asCharComparator(this.storedComparator);
	}

	public Char2LongRBTreeMap(Comparator<? super Character> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Char2LongRBTreeMap(Map<? extends Character, ? extends Long> m) {
		this();
		this.putAll(m);
	}

	public Char2LongRBTreeMap(SortedMap<Character, Long> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Char2LongRBTreeMap(Char2LongMap m) {
		this();
		this.putAll(m);
	}

	public Char2LongRBTreeMap(Char2LongSortedMap m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Char2LongRBTreeMap(char[] k, long[] v, Comparator<? super Character> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Char2LongRBTreeMap(char[] k, long[] v) {
		this(k, v, null);
	}

	final int compare(char k1, char k2) {
		return this.actualComparator == null ? Character.compare(k1, k2) : this.actualComparator.compare(k1, k2);
	}

	final Char2LongRBTreeMap.Entry findKey(char k) {
		Char2LongRBTreeMap.Entry e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Char2LongRBTreeMap.Entry locateKey(char k) {
		Char2LongRBTreeMap.Entry e = this.tree;
		Char2LongRBTreeMap.Entry last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[64];
		this.nodePath = new Char2LongRBTreeMap.Entry[64];
	}

	public long addTo(char k, long incr) {
		Char2LongRBTreeMap.Entry e = this.add(k);
		long oldValue = e.value;
		e.value += incr;
		return oldValue;
	}

	@Override
	public long put(char k, long v) {
		Char2LongRBTreeMap.Entry e = this.add(k);
		long oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Char2LongRBTreeMap.Entry add(char k) {
		this.modified = false;
		int maxDepth = 0;
		Char2LongRBTreeMap.Entry e;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Char2LongRBTreeMap.Entry(k, this.defRetValue);
		} else {
			Char2LongRBTreeMap.Entry p = this.tree;
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
					e = new Char2LongRBTreeMap.Entry(k, this.defRetValue);
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
					e = new Char2LongRBTreeMap.Entry(k, this.defRetValue);
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
						Char2LongRBTreeMap.Entry y = this.nodePath[i - 1].right;
						if (this.nodePath[i - 1].succ() || y.black()) {
							if (!this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Char2LongRBTreeMap.Entry x = this.nodePath[i];
								y = x.right;
								x.right = y.left;
								y.left = x;
								this.nodePath[i - 1].left = y;
								if (y.pred()) {
									y.pred(false);
									x.succ(y);
								}
							}

							Char2LongRBTreeMap.Entry xx = this.nodePath[i - 1];
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
						Char2LongRBTreeMap.Entry y = this.nodePath[i - 1].left;
						if (this.nodePath[i - 1].pred() || y.black()) {
							if (this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Char2LongRBTreeMap.Entry x = this.nodePath[i];
								y = x.left;
								x.left = y.right;
								y.right = x;
								this.nodePath[i - 1].right = y;
								if (y.succ()) {
									y.succ(false);
									x.pred(y);
								}
							}

							Char2LongRBTreeMap.Entry xxx = this.nodePath[i - 1];
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
	public long remove(char k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Char2LongRBTreeMap.Entry p = this.tree;
			int i = 0;
			char kk = k;

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
				Char2LongRBTreeMap.Entry r = p.right;
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
						Char2LongRBTreeMap.Entry s = r.left;
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
						Char2LongRBTreeMap.Entry x = this.dirPath[i - 1] ? this.nodePath[i - 1].right : this.nodePath[i - 1].left;
						if (!x.black()) {
							x.black(true);
							break;
						}
					}

					if (!this.dirPath[i - 1]) {
						Char2LongRBTreeMap.Entry w = this.nodePath[i - 1].right;
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
								Char2LongRBTreeMap.Entry y = w.left;
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
						Char2LongRBTreeMap.Entry wx = this.nodePath[i - 1].left;
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
								Char2LongRBTreeMap.Entry y = wx.right;
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
		Char2LongRBTreeMap.ValueIterator i = new Char2LongRBTreeMap.ValueIterator();
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
	public boolean containsKey(char k) {
		return this.findKey(k) != null;
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
	public long get(char k) {
		Char2LongRBTreeMap.Entry e = this.findKey(k);
		return e == null ? this.defRetValue : e.value;
	}

	@Override
	public char firstCharKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	@Override
	public char lastCharKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> char2LongEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry>() {
				final Comparator<? super it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> comparator = (x, y) -> Char2LongRBTreeMap.this.actualComparator
						.compare(x.getCharKey(), y.getCharKey());

				public Comparator<? super it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> iterator() {
					return Char2LongRBTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> iterator(it.unimi.dsi.fastutil.chars.Char2LongMap.Entry from) {
					return Char2LongRBTreeMap.this.new EntryIterator(from.getCharKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() == null || !(e.getKey() instanceof Character)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Long) {
							Char2LongRBTreeMap.Entry f = Char2LongRBTreeMap.this.findKey((Character)e.getKey());
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
						if (e.getKey() == null || !(e.getKey() instanceof Character)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Long) {
							Char2LongRBTreeMap.Entry f = Char2LongRBTreeMap.this.findKey((Character)e.getKey());
							if (f != null && f.getLongValue() == (Long)e.getValue()) {
								Char2LongRBTreeMap.this.remove(f.key);
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
					return Char2LongRBTreeMap.this.count;
				}

				public void clear() {
					Char2LongRBTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry first() {
					return Char2LongRBTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry last() {
					return Char2LongRBTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> subSet(
					it.unimi.dsi.fastutil.chars.Char2LongMap.Entry from, it.unimi.dsi.fastutil.chars.Char2LongMap.Entry to
				) {
					return Char2LongRBTreeMap.this.subMap(from.getCharKey(), to.getCharKey()).char2LongEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> headSet(it.unimi.dsi.fastutil.chars.Char2LongMap.Entry to) {
					return Char2LongRBTreeMap.this.headMap(to.getCharKey()).char2LongEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> tailSet(it.unimi.dsi.fastutil.chars.Char2LongMap.Entry from) {
					return Char2LongRBTreeMap.this.tailMap(from.getCharKey()).char2LongEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public CharSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Char2LongRBTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public LongCollection values() {
		if (this.values == null) {
			this.values = new AbstractLongCollection() {
				@Override
				public LongIterator iterator() {
					return Char2LongRBTreeMap.this.new ValueIterator();
				}

				@Override
				public boolean contains(long k) {
					return Char2LongRBTreeMap.this.containsValue(k);
				}

				public int size() {
					return Char2LongRBTreeMap.this.count;
				}

				public void clear() {
					Char2LongRBTreeMap.this.clear();
				}
			};
		}

		return this.values;
	}

	@Override
	public CharComparator comparator() {
		return this.actualComparator;
	}

	@Override
	public Char2LongSortedMap headMap(char to) {
		return new Char2LongRBTreeMap.Submap('\u0000', true, to, false);
	}

	@Override
	public Char2LongSortedMap tailMap(char from) {
		return new Char2LongRBTreeMap.Submap(from, false, '\u0000', true);
	}

	@Override
	public Char2LongSortedMap subMap(char from, char to) {
		return new Char2LongRBTreeMap.Submap(from, false, to, false);
	}

	public Char2LongRBTreeMap clone() {
		Char2LongRBTreeMap c;
		try {
			c = (Char2LongRBTreeMap)super.clone();
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
			Char2LongRBTreeMap.Entry rp = new Char2LongRBTreeMap.Entry();
			Char2LongRBTreeMap.Entry rq = new Char2LongRBTreeMap.Entry();
			Char2LongRBTreeMap.Entry p = rp;
			rp.left(this.tree);
			Char2LongRBTreeMap.Entry q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Char2LongRBTreeMap.Entry e = p.left.clone();
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
					Char2LongRBTreeMap.Entry e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Char2LongRBTreeMap.EntryIterator i = new Char2LongRBTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Char2LongRBTreeMap.Entry e = i.nextEntry();
			s.writeChar(e.key);
			s.writeLong(e.value);
		}
	}

	private Char2LongRBTreeMap.Entry readTree(ObjectInputStream s, int n, Char2LongRBTreeMap.Entry pred, Char2LongRBTreeMap.Entry succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Char2LongRBTreeMap.Entry top = new Char2LongRBTreeMap.Entry(s.readChar(), s.readLong());
			top.pred(pred);
			top.succ(succ);
			top.black(true);
			return top;
		} else if (n == 2) {
			Char2LongRBTreeMap.Entry top = new Char2LongRBTreeMap.Entry(s.readChar(), s.readLong());
			top.black(true);
			top.right(new Char2LongRBTreeMap.Entry(s.readChar(), s.readLong()));
			top.right.pred(top);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Char2LongRBTreeMap.Entry top = new Char2LongRBTreeMap.Entry();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = s.readChar();
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
			Char2LongRBTreeMap.Entry e = this.tree;

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

	private static final class Entry extends BasicEntry implements Cloneable {
		private static final int BLACK_MASK = 1;
		private static final int SUCC_MASK = Integer.MIN_VALUE;
		private static final int PRED_MASK = 1073741824;
		Char2LongRBTreeMap.Entry left;
		Char2LongRBTreeMap.Entry right;
		int info;

		Entry() {
			super('\u0000', 0L);
		}

		Entry(char k, long v) {
			super(k, v);
			this.info = -1073741824;
		}

		Char2LongRBTreeMap.Entry left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Char2LongRBTreeMap.Entry right() {
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

		void pred(Char2LongRBTreeMap.Entry pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Char2LongRBTreeMap.Entry succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Char2LongRBTreeMap.Entry left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Char2LongRBTreeMap.Entry right) {
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

		Char2LongRBTreeMap.Entry next() {
			Char2LongRBTreeMap.Entry next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Char2LongRBTreeMap.Entry prev() {
			Char2LongRBTreeMap.Entry prev = this.left;
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

		public Char2LongRBTreeMap.Entry clone() {
			Char2LongRBTreeMap.Entry c;
			try {
				c = (Char2LongRBTreeMap.Entry)super.clone();
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
				java.util.Map.Entry<Character, Long> e = (java.util.Map.Entry<Character, Long>)o;
				return this.key == (Character)e.getKey() && this.value == (Long)e.getValue();
			}
		}

		@Override
		public int hashCode() {
			return this.key ^ HashCommon.long2int(this.value);
		}

		@Override
		public String toString() {
			return this.key + "=>" + this.value;
		}
	}

	private class EntryIterator extends Char2LongRBTreeMap.TreeIterator implements ObjectListIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> {
		EntryIterator() {
		}

		EntryIterator(char k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry previous() {
			return this.previousEntry();
		}
	}

	private final class KeyIterator extends Char2LongRBTreeMap.TreeIterator implements CharListIterator {
		public KeyIterator() {
		}

		public KeyIterator(char k) {
			super(k);
		}

		@Override
		public char nextChar() {
			return this.nextEntry().key;
		}

		@Override
		public char previousChar() {
			return this.previousEntry().key;
		}
	}

	private class KeySet extends it.unimi.dsi.fastutil.chars.AbstractChar2LongSortedMap.KeySet {
		private KeySet() {
			super(Char2LongRBTreeMap.this);
		}

		@Override
		public CharBidirectionalIterator iterator() {
			return Char2LongRBTreeMap.this.new KeyIterator();
		}

		@Override
		public CharBidirectionalIterator iterator(char from) {
			return Char2LongRBTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractChar2LongSortedMap implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		char from;
		char to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> entries;
		protected transient CharSortedSet keys;
		protected transient LongCollection values;

		public Submap(char from, boolean bottom, char to, boolean top) {
			if (!bottom && !top && Char2LongRBTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Char2LongRBTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Char2LongRBTreeMap.Submap.SubmapIterator i = new Char2LongRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(char k) {
			return (this.bottom || Char2LongRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Char2LongRBTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> char2LongEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> iterator(it.unimi.dsi.fastutil.chars.Char2LongMap.Entry from) {
						return Submap.this.new SubmapEntryIterator(from.getCharKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> comparator() {
						return Char2LongRBTreeMap.this.char2LongEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() == null || !(e.getKey() instanceof Character)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Long) {
								Char2LongRBTreeMap.Entry f = Char2LongRBTreeMap.this.findKey((Character)e.getKey());
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
							if (e.getKey() == null || !(e.getKey() instanceof Character)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Long) {
								Char2LongRBTreeMap.Entry f = Char2LongRBTreeMap.this.findKey((Character)e.getKey());
								if (f != null && Submap.this.in(f.key)) {
									Submap.this.remove(f.key);
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

					public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> subSet(
						it.unimi.dsi.fastutil.chars.Char2LongMap.Entry from, it.unimi.dsi.fastutil.chars.Char2LongMap.Entry to
					) {
						return Submap.this.subMap(from.getCharKey(), to.getCharKey()).char2LongEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> headSet(it.unimi.dsi.fastutil.chars.Char2LongMap.Entry to) {
						return Submap.this.headMap(to.getCharKey()).char2LongEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> tailSet(it.unimi.dsi.fastutil.chars.Char2LongMap.Entry from) {
						return Submap.this.tailMap(from.getCharKey()).char2LongEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public CharSortedSet keySet() {
			if (this.keys == null) {
				this.keys = new Char2LongRBTreeMap.Submap.KeySet();
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
		public boolean containsKey(char k) {
			return this.in(k) && Char2LongRBTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(long v) {
			Char2LongRBTreeMap.Submap.SubmapIterator i = new Char2LongRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				long ev = i.nextEntry().value;
				if (ev == v) {
					return true;
				}
			}

			return false;
		}

		@Override
		public long get(char k) {
			Char2LongRBTreeMap.Entry e;
			return this.in(k) && (e = Char2LongRBTreeMap.this.findKey(k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public long put(char k, long v) {
			Char2LongRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				long oldValue = Char2LongRBTreeMap.this.put(k, v);
				return Char2LongRBTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public long remove(char k) {
			Char2LongRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				return this.defRetValue;
			} else {
				long oldValue = Char2LongRBTreeMap.this.remove(k);
				return Char2LongRBTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Char2LongRBTreeMap.Submap.SubmapIterator i = new Char2LongRBTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Char2LongRBTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public CharComparator comparator() {
			return Char2LongRBTreeMap.this.actualComparator;
		}

		@Override
		public Char2LongSortedMap headMap(char to) {
			if (this.top) {
				return Char2LongRBTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Char2LongRBTreeMap.this.compare(to, this.to) < 0 ? Char2LongRBTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Char2LongSortedMap tailMap(char from) {
			if (this.bottom) {
				return Char2LongRBTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Char2LongRBTreeMap.this.compare(from, this.from) > 0 ? Char2LongRBTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Char2LongSortedMap subMap(char from, char to) {
			if (this.top && this.bottom) {
				return Char2LongRBTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Char2LongRBTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Char2LongRBTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Char2LongRBTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Char2LongRBTreeMap.Entry firstEntry() {
			if (Char2LongRBTreeMap.this.tree == null) {
				return null;
			} else {
				Char2LongRBTreeMap.Entry e;
				if (this.bottom) {
					e = Char2LongRBTreeMap.this.firstEntry;
				} else {
					e = Char2LongRBTreeMap.this.locateKey(this.from);
					if (Char2LongRBTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Char2LongRBTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Char2LongRBTreeMap.Entry lastEntry() {
			if (Char2LongRBTreeMap.this.tree == null) {
				return null;
			} else {
				Char2LongRBTreeMap.Entry e;
				if (this.top) {
					e = Char2LongRBTreeMap.this.lastEntry;
				} else {
					e = Char2LongRBTreeMap.this.locateKey(this.to);
					if (Char2LongRBTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Char2LongRBTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		@Override
		public char firstCharKey() {
			Char2LongRBTreeMap.Entry e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		@Override
		public char lastCharKey() {
			Char2LongRBTreeMap.Entry e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends it.unimi.dsi.fastutil.chars.AbstractChar2LongSortedMap.KeySet {
			private KeySet() {
				super(Submap.this);
			}

			@Override
			public CharBidirectionalIterator iterator() {
				return Submap.this.new SubmapKeyIterator();
			}

			@Override
			public CharBidirectionalIterator iterator(char from) {
				return Submap.this.new SubmapKeyIterator(from);
			}
		}

		private class SubmapEntryIterator
			extends Char2LongRBTreeMap.Submap.SubmapIterator
			implements ObjectListIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(char k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Char2LongRBTreeMap.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(char k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Char2LongRBTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Char2LongRBTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Char2LongRBTreeMap.this.locateKey(k);
						if (Char2LongRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
				if (!Submap.this.bottom && this.prev != null && Char2LongRBTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Char2LongRBTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Char2LongRBTreeMap.Submap.SubmapIterator implements CharListIterator {
			public SubmapKeyIterator() {
			}

			public SubmapKeyIterator(char from) {
				super(from);
			}

			@Override
			public char nextChar() {
				return this.nextEntry().key;
			}

			@Override
			public char previousChar() {
				return this.previousEntry().key;
			}
		}

		private final class SubmapValueIterator extends Char2LongRBTreeMap.Submap.SubmapIterator implements LongListIterator {
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
		Char2LongRBTreeMap.Entry prev;
		Char2LongRBTreeMap.Entry next;
		Char2LongRBTreeMap.Entry curr;
		int index = 0;

		TreeIterator() {
			this.next = Char2LongRBTreeMap.this.firstEntry;
		}

		TreeIterator(char k) {
			if ((this.next = Char2LongRBTreeMap.this.locateKey(k)) != null) {
				if (Char2LongRBTreeMap.this.compare(this.next.key, k) <= 0) {
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

		Char2LongRBTreeMap.Entry nextEntry() {
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

		Char2LongRBTreeMap.Entry previousEntry() {
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
				Char2LongRBTreeMap.this.remove(this.curr.key);
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

	private final class ValueIterator extends Char2LongRBTreeMap.TreeIterator implements LongListIterator {
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