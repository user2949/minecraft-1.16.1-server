package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2CharMap.BasicEntry;
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

public class Int2CharRBTreeMap extends AbstractInt2CharSortedMap implements Serializable, Cloneable {
	protected transient Int2CharRBTreeMap.Entry tree;
	protected int count;
	protected transient Int2CharRBTreeMap.Entry firstEntry;
	protected transient Int2CharRBTreeMap.Entry lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> entries;
	protected transient IntSortedSet keys;
	protected transient CharCollection values;
	protected transient boolean modified;
	protected Comparator<? super Integer> storedComparator;
	protected transient IntComparator actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;
	private transient Int2CharRBTreeMap.Entry[] nodePath;

	public Int2CharRBTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
	}

	public Int2CharRBTreeMap(Comparator<? super Integer> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Int2CharRBTreeMap(Map<? extends Integer, ? extends Character> m) {
		this();
		this.putAll(m);
	}

	public Int2CharRBTreeMap(SortedMap<Integer, Character> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Int2CharRBTreeMap(Int2CharMap m) {
		this();
		this.putAll(m);
	}

	public Int2CharRBTreeMap(Int2CharSortedMap m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Int2CharRBTreeMap(int[] k, char[] v, Comparator<? super Integer> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Int2CharRBTreeMap(int[] k, char[] v) {
		this(k, v, null);
	}

	final int compare(int k1, int k2) {
		return this.actualComparator == null ? Integer.compare(k1, k2) : this.actualComparator.compare(k1, k2);
	}

	final Int2CharRBTreeMap.Entry findKey(int k) {
		Int2CharRBTreeMap.Entry e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Int2CharRBTreeMap.Entry locateKey(int k) {
		Int2CharRBTreeMap.Entry e = this.tree;
		Int2CharRBTreeMap.Entry last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[64];
		this.nodePath = new Int2CharRBTreeMap.Entry[64];
	}

	public char addTo(int k, char incr) {
		Int2CharRBTreeMap.Entry e = this.add(k);
		char oldValue = e.value;
		e.value += incr;
		return oldValue;
	}

	@Override
	public char put(int k, char v) {
		Int2CharRBTreeMap.Entry e = this.add(k);
		char oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Int2CharRBTreeMap.Entry add(int k) {
		this.modified = false;
		int maxDepth = 0;
		Int2CharRBTreeMap.Entry e;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Int2CharRBTreeMap.Entry(k, this.defRetValue);
		} else {
			Int2CharRBTreeMap.Entry p = this.tree;
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
					e = new Int2CharRBTreeMap.Entry(k, this.defRetValue);
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
					e = new Int2CharRBTreeMap.Entry(k, this.defRetValue);
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
						Int2CharRBTreeMap.Entry y = this.nodePath[i - 1].right;
						if (this.nodePath[i - 1].succ() || y.black()) {
							if (!this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Int2CharRBTreeMap.Entry x = this.nodePath[i];
								y = x.right;
								x.right = y.left;
								y.left = x;
								this.nodePath[i - 1].left = y;
								if (y.pred()) {
									y.pred(false);
									x.succ(y);
								}
							}

							Int2CharRBTreeMap.Entry xx = this.nodePath[i - 1];
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
						Int2CharRBTreeMap.Entry y = this.nodePath[i - 1].left;
						if (this.nodePath[i - 1].pred() || y.black()) {
							if (this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Int2CharRBTreeMap.Entry x = this.nodePath[i];
								y = x.left;
								x.left = y.right;
								y.right = x;
								this.nodePath[i - 1].right = y;
								if (y.succ()) {
									y.succ(false);
									x.pred(y);
								}
							}

							Int2CharRBTreeMap.Entry xxx = this.nodePath[i - 1];
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
	public char remove(int k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Int2CharRBTreeMap.Entry p = this.tree;
			int i = 0;
			int kk = k;

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
				Int2CharRBTreeMap.Entry r = p.right;
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
						Int2CharRBTreeMap.Entry s = r.left;
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
						Int2CharRBTreeMap.Entry x = this.dirPath[i - 1] ? this.nodePath[i - 1].right : this.nodePath[i - 1].left;
						if (!x.black()) {
							x.black(true);
							break;
						}
					}

					if (!this.dirPath[i - 1]) {
						Int2CharRBTreeMap.Entry w = this.nodePath[i - 1].right;
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
								Int2CharRBTreeMap.Entry y = w.left;
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
						Int2CharRBTreeMap.Entry wx = this.nodePath[i - 1].left;
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
								Int2CharRBTreeMap.Entry y = wx.right;
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
	public boolean containsValue(char v) {
		Int2CharRBTreeMap.ValueIterator i = new Int2CharRBTreeMap.ValueIterator();
		int j = this.count;

		while (j-- != 0) {
			char ev = i.nextChar();
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
	public boolean containsKey(int k) {
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
	public char get(int k) {
		Int2CharRBTreeMap.Entry e = this.findKey(k);
		return e == null ? this.defRetValue : e.value;
	}

	@Override
	public int firstIntKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	@Override
	public int lastIntKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> int2CharEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry>() {
				final Comparator<? super it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> comparator = (x, y) -> Int2CharRBTreeMap.this.actualComparator
						.compare(x.getIntKey(), y.getIntKey());

				public Comparator<? super it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> iterator() {
					return Int2CharRBTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> iterator(it.unimi.dsi.fastutil.ints.Int2CharMap.Entry from) {
					return Int2CharRBTreeMap.this.new EntryIterator(from.getIntKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Character) {
							Int2CharRBTreeMap.Entry f = Int2CharRBTreeMap.this.findKey((Integer)e.getKey());
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
						if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Character) {
							Int2CharRBTreeMap.Entry f = Int2CharRBTreeMap.this.findKey((Integer)e.getKey());
							if (f != null && f.getCharValue() == (Character)e.getValue()) {
								Int2CharRBTreeMap.this.remove(f.key);
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
					return Int2CharRBTreeMap.this.count;
				}

				public void clear() {
					Int2CharRBTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.ints.Int2CharMap.Entry first() {
					return Int2CharRBTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.ints.Int2CharMap.Entry last() {
					return Int2CharRBTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> subSet(
					it.unimi.dsi.fastutil.ints.Int2CharMap.Entry from, it.unimi.dsi.fastutil.ints.Int2CharMap.Entry to
				) {
					return Int2CharRBTreeMap.this.subMap(from.getIntKey(), to.getIntKey()).int2CharEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> headSet(it.unimi.dsi.fastutil.ints.Int2CharMap.Entry to) {
					return Int2CharRBTreeMap.this.headMap(to.getIntKey()).int2CharEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> tailSet(it.unimi.dsi.fastutil.ints.Int2CharMap.Entry from) {
					return Int2CharRBTreeMap.this.tailMap(from.getIntKey()).int2CharEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public IntSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Int2CharRBTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public CharCollection values() {
		if (this.values == null) {
			this.values = new AbstractCharCollection() {
				@Override
				public CharIterator iterator() {
					return Int2CharRBTreeMap.this.new ValueIterator();
				}

				@Override
				public boolean contains(char k) {
					return Int2CharRBTreeMap.this.containsValue(k);
				}

				public int size() {
					return Int2CharRBTreeMap.this.count;
				}

				public void clear() {
					Int2CharRBTreeMap.this.clear();
				}
			};
		}

		return this.values;
	}

	@Override
	public IntComparator comparator() {
		return this.actualComparator;
	}

	@Override
	public Int2CharSortedMap headMap(int to) {
		return new Int2CharRBTreeMap.Submap(0, true, to, false);
	}

	@Override
	public Int2CharSortedMap tailMap(int from) {
		return new Int2CharRBTreeMap.Submap(from, false, 0, true);
	}

	@Override
	public Int2CharSortedMap subMap(int from, int to) {
		return new Int2CharRBTreeMap.Submap(from, false, to, false);
	}

	public Int2CharRBTreeMap clone() {
		Int2CharRBTreeMap c;
		try {
			c = (Int2CharRBTreeMap)super.clone();
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
			Int2CharRBTreeMap.Entry rp = new Int2CharRBTreeMap.Entry();
			Int2CharRBTreeMap.Entry rq = new Int2CharRBTreeMap.Entry();
			Int2CharRBTreeMap.Entry p = rp;
			rp.left(this.tree);
			Int2CharRBTreeMap.Entry q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Int2CharRBTreeMap.Entry e = p.left.clone();
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
					Int2CharRBTreeMap.Entry e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Int2CharRBTreeMap.EntryIterator i = new Int2CharRBTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Int2CharRBTreeMap.Entry e = i.nextEntry();
			s.writeInt(e.key);
			s.writeChar(e.value);
		}
	}

	private Int2CharRBTreeMap.Entry readTree(ObjectInputStream s, int n, Int2CharRBTreeMap.Entry pred, Int2CharRBTreeMap.Entry succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Int2CharRBTreeMap.Entry top = new Int2CharRBTreeMap.Entry(s.readInt(), s.readChar());
			top.pred(pred);
			top.succ(succ);
			top.black(true);
			return top;
		} else if (n == 2) {
			Int2CharRBTreeMap.Entry top = new Int2CharRBTreeMap.Entry(s.readInt(), s.readChar());
			top.black(true);
			top.right(new Int2CharRBTreeMap.Entry(s.readInt(), s.readChar()));
			top.right.pred(top);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Int2CharRBTreeMap.Entry top = new Int2CharRBTreeMap.Entry();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = s.readInt();
			top.value = s.readChar();
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
			Int2CharRBTreeMap.Entry e = this.tree;

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
		Int2CharRBTreeMap.Entry left;
		Int2CharRBTreeMap.Entry right;
		int info;

		Entry() {
			super(0, '\u0000');
		}

		Entry(int k, char v) {
			super(k, v);
			this.info = -1073741824;
		}

		Int2CharRBTreeMap.Entry left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Int2CharRBTreeMap.Entry right() {
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

		void pred(Int2CharRBTreeMap.Entry pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Int2CharRBTreeMap.Entry succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Int2CharRBTreeMap.Entry left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Int2CharRBTreeMap.Entry right) {
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

		Int2CharRBTreeMap.Entry next() {
			Int2CharRBTreeMap.Entry next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Int2CharRBTreeMap.Entry prev() {
			Int2CharRBTreeMap.Entry prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		@Override
		public char setValue(char value) {
			char oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Int2CharRBTreeMap.Entry clone() {
			Int2CharRBTreeMap.Entry c;
			try {
				c = (Int2CharRBTreeMap.Entry)super.clone();
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
				java.util.Map.Entry<Integer, Character> e = (java.util.Map.Entry<Integer, Character>)o;
				return this.key == (Integer)e.getKey() && this.value == (Character)e.getValue();
			}
		}

		@Override
		public int hashCode() {
			return this.key ^ this.value;
		}

		@Override
		public String toString() {
			return this.key + "=>" + this.value;
		}
	}

	private class EntryIterator extends Int2CharRBTreeMap.TreeIterator implements ObjectListIterator<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> {
		EntryIterator() {
		}

		EntryIterator(int k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.ints.Int2CharMap.Entry next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.ints.Int2CharMap.Entry previous() {
			return this.previousEntry();
		}
	}

	private final class KeyIterator extends Int2CharRBTreeMap.TreeIterator implements IntListIterator {
		public KeyIterator() {
		}

		public KeyIterator(int k) {
			super(k);
		}

		@Override
		public int nextInt() {
			return this.nextEntry().key;
		}

		@Override
		public int previousInt() {
			return this.previousEntry().key;
		}
	}

	private class KeySet extends it.unimi.dsi.fastutil.ints.AbstractInt2CharSortedMap.KeySet {
		private KeySet() {
			super(Int2CharRBTreeMap.this);
		}

		@Override
		public IntBidirectionalIterator iterator() {
			return Int2CharRBTreeMap.this.new KeyIterator();
		}

		@Override
		public IntBidirectionalIterator iterator(int from) {
			return Int2CharRBTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractInt2CharSortedMap implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		int from;
		int to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> entries;
		protected transient IntSortedSet keys;
		protected transient CharCollection values;

		public Submap(int from, boolean bottom, int to, boolean top) {
			if (!bottom && !top && Int2CharRBTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Int2CharRBTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Int2CharRBTreeMap.Submap.SubmapIterator i = new Int2CharRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(int k) {
			return (this.bottom || Int2CharRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Int2CharRBTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> int2CharEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> iterator(it.unimi.dsi.fastutil.ints.Int2CharMap.Entry from) {
						return Submap.this.new SubmapEntryIterator(from.getIntKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> comparator() {
						return Int2CharRBTreeMap.this.int2CharEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Character) {
								Int2CharRBTreeMap.Entry f = Int2CharRBTreeMap.this.findKey((Integer)e.getKey());
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
							if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Character) {
								Int2CharRBTreeMap.Entry f = Int2CharRBTreeMap.this.findKey((Integer)e.getKey());
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

					public it.unimi.dsi.fastutil.ints.Int2CharMap.Entry first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.ints.Int2CharMap.Entry last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> subSet(
						it.unimi.dsi.fastutil.ints.Int2CharMap.Entry from, it.unimi.dsi.fastutil.ints.Int2CharMap.Entry to
					) {
						return Submap.this.subMap(from.getIntKey(), to.getIntKey()).int2CharEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> headSet(it.unimi.dsi.fastutil.ints.Int2CharMap.Entry to) {
						return Submap.this.headMap(to.getIntKey()).int2CharEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> tailSet(it.unimi.dsi.fastutil.ints.Int2CharMap.Entry from) {
						return Submap.this.tailMap(from.getIntKey()).int2CharEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public IntSortedSet keySet() {
			if (this.keys == null) {
				this.keys = new Int2CharRBTreeMap.Submap.KeySet();
			}

			return this.keys;
		}

		@Override
		public CharCollection values() {
			if (this.values == null) {
				this.values = new AbstractCharCollection() {
					@Override
					public CharIterator iterator() {
						return Submap.this.new SubmapValueIterator();
					}

					@Override
					public boolean contains(char k) {
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
		public boolean containsKey(int k) {
			return this.in(k) && Int2CharRBTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(char v) {
			Int2CharRBTreeMap.Submap.SubmapIterator i = new Int2CharRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				char ev = i.nextEntry().value;
				if (ev == v) {
					return true;
				}
			}

			return false;
		}

		@Override
		public char get(int k) {
			Int2CharRBTreeMap.Entry e;
			return this.in(k) && (e = Int2CharRBTreeMap.this.findKey(k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public char put(int k, char v) {
			Int2CharRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				char oldValue = Int2CharRBTreeMap.this.put(k, v);
				return Int2CharRBTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public char remove(int k) {
			Int2CharRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				return this.defRetValue;
			} else {
				char oldValue = Int2CharRBTreeMap.this.remove(k);
				return Int2CharRBTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Int2CharRBTreeMap.Submap.SubmapIterator i = new Int2CharRBTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Int2CharRBTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public IntComparator comparator() {
			return Int2CharRBTreeMap.this.actualComparator;
		}

		@Override
		public Int2CharSortedMap headMap(int to) {
			if (this.top) {
				return Int2CharRBTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Int2CharRBTreeMap.this.compare(to, this.to) < 0 ? Int2CharRBTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Int2CharSortedMap tailMap(int from) {
			if (this.bottom) {
				return Int2CharRBTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Int2CharRBTreeMap.this.compare(from, this.from) > 0 ? Int2CharRBTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Int2CharSortedMap subMap(int from, int to) {
			if (this.top && this.bottom) {
				return Int2CharRBTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Int2CharRBTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Int2CharRBTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Int2CharRBTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Int2CharRBTreeMap.Entry firstEntry() {
			if (Int2CharRBTreeMap.this.tree == null) {
				return null;
			} else {
				Int2CharRBTreeMap.Entry e;
				if (this.bottom) {
					e = Int2CharRBTreeMap.this.firstEntry;
				} else {
					e = Int2CharRBTreeMap.this.locateKey(this.from);
					if (Int2CharRBTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Int2CharRBTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Int2CharRBTreeMap.Entry lastEntry() {
			if (Int2CharRBTreeMap.this.tree == null) {
				return null;
			} else {
				Int2CharRBTreeMap.Entry e;
				if (this.top) {
					e = Int2CharRBTreeMap.this.lastEntry;
				} else {
					e = Int2CharRBTreeMap.this.locateKey(this.to);
					if (Int2CharRBTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Int2CharRBTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		@Override
		public int firstIntKey() {
			Int2CharRBTreeMap.Entry e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		@Override
		public int lastIntKey() {
			Int2CharRBTreeMap.Entry e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends it.unimi.dsi.fastutil.ints.AbstractInt2CharSortedMap.KeySet {
			private KeySet() {
				super(Submap.this);
			}

			@Override
			public IntBidirectionalIterator iterator() {
				return Submap.this.new SubmapKeyIterator();
			}

			@Override
			public IntBidirectionalIterator iterator(int from) {
				return Submap.this.new SubmapKeyIterator(from);
			}
		}

		private class SubmapEntryIterator extends Int2CharRBTreeMap.Submap.SubmapIterator implements ObjectListIterator<it.unimi.dsi.fastutil.ints.Int2CharMap.Entry> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(int k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.ints.Int2CharMap.Entry next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.ints.Int2CharMap.Entry previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Int2CharRBTreeMap.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(int k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Int2CharRBTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Int2CharRBTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Int2CharRBTreeMap.this.locateKey(k);
						if (Int2CharRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
				if (!Submap.this.bottom && this.prev != null && Int2CharRBTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Int2CharRBTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Int2CharRBTreeMap.Submap.SubmapIterator implements IntListIterator {
			public SubmapKeyIterator() {
			}

			public SubmapKeyIterator(int from) {
				super(from);
			}

			@Override
			public int nextInt() {
				return this.nextEntry().key;
			}

			@Override
			public int previousInt() {
				return this.previousEntry().key;
			}
		}

		private final class SubmapValueIterator extends Int2CharRBTreeMap.Submap.SubmapIterator implements CharListIterator {
			private SubmapValueIterator() {
			}

			@Override
			public char nextChar() {
				return this.nextEntry().value;
			}

			@Override
			public char previousChar() {
				return this.previousEntry().value;
			}
		}
	}

	private class TreeIterator {
		Int2CharRBTreeMap.Entry prev;
		Int2CharRBTreeMap.Entry next;
		Int2CharRBTreeMap.Entry curr;
		int index = 0;

		TreeIterator() {
			this.next = Int2CharRBTreeMap.this.firstEntry;
		}

		TreeIterator(int k) {
			if ((this.next = Int2CharRBTreeMap.this.locateKey(k)) != null) {
				if (Int2CharRBTreeMap.this.compare(this.next.key, k) <= 0) {
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

		Int2CharRBTreeMap.Entry nextEntry() {
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

		Int2CharRBTreeMap.Entry previousEntry() {
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
				Int2CharRBTreeMap.this.remove(this.curr.key);
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

	private final class ValueIterator extends Int2CharRBTreeMap.TreeIterator implements CharListIterator {
		private ValueIterator() {
		}

		@Override
		public char nextChar() {
			return this.nextEntry().value;
		}

		@Override
		public char previousChar() {
			return this.previousEntry().value;
		}
	}
}
