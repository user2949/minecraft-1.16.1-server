package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

public class Char2ReferenceRBTreeMap<V> extends AbstractChar2ReferenceSortedMap<V> implements Serializable, Cloneable {
	protected transient Char2ReferenceRBTreeMap.Entry<V> tree;
	protected int count;
	protected transient Char2ReferenceRBTreeMap.Entry<V> firstEntry;
	protected transient Char2ReferenceRBTreeMap.Entry<V> lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> entries;
	protected transient CharSortedSet keys;
	protected transient ReferenceCollection<V> values;
	protected transient boolean modified;
	protected Comparator<? super Character> storedComparator;
	protected transient CharComparator actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;
	private transient Char2ReferenceRBTreeMap.Entry<V>[] nodePath;

	public Char2ReferenceRBTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = CharComparators.asCharComparator(this.storedComparator);
	}

	public Char2ReferenceRBTreeMap(Comparator<? super Character> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Char2ReferenceRBTreeMap(Map<? extends Character, ? extends V> m) {
		this();
		this.putAll(m);
	}

	public Char2ReferenceRBTreeMap(SortedMap<Character, V> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Char2ReferenceRBTreeMap(Char2ReferenceMap<? extends V> m) {
		this();
		this.putAll(m);
	}

	public Char2ReferenceRBTreeMap(Char2ReferenceSortedMap<V> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Char2ReferenceRBTreeMap(char[] k, V[] v, Comparator<? super Character> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Char2ReferenceRBTreeMap(char[] k, V[] v) {
		this(k, v, null);
	}

	final int compare(char k1, char k2) {
		return this.actualComparator == null ? Character.compare(k1, k2) : this.actualComparator.compare(k1, k2);
	}

	final Char2ReferenceRBTreeMap.Entry<V> findKey(char k) {
		Char2ReferenceRBTreeMap.Entry<V> e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Char2ReferenceRBTreeMap.Entry<V> locateKey(char k) {
		Char2ReferenceRBTreeMap.Entry<V> e = this.tree;
		Char2ReferenceRBTreeMap.Entry<V> last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[64];
		this.nodePath = new Char2ReferenceRBTreeMap.Entry[64];
	}

	@Override
	public V put(char k, V v) {
		Char2ReferenceRBTreeMap.Entry<V> e = this.add(k);
		V oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Char2ReferenceRBTreeMap.Entry<V> add(char k) {
		this.modified = false;
		int maxDepth = 0;
		Char2ReferenceRBTreeMap.Entry<V> e;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Char2ReferenceRBTreeMap.Entry<>(k, this.defRetValue);
		} else {
			Char2ReferenceRBTreeMap.Entry<V> p = this.tree;
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
					e = new Char2ReferenceRBTreeMap.Entry<>(k, this.defRetValue);
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
					e = new Char2ReferenceRBTreeMap.Entry<>(k, this.defRetValue);
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
						Char2ReferenceRBTreeMap.Entry<V> y = this.nodePath[i - 1].right;
						if (this.nodePath[i - 1].succ() || y.black()) {
							if (!this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Char2ReferenceRBTreeMap.Entry<V> x = this.nodePath[i];
								y = x.right;
								x.right = y.left;
								y.left = x;
								this.nodePath[i - 1].left = y;
								if (y.pred()) {
									y.pred(false);
									x.succ(y);
								}
							}

							Char2ReferenceRBTreeMap.Entry<V> xx = this.nodePath[i - 1];
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
						Char2ReferenceRBTreeMap.Entry<V> y = this.nodePath[i - 1].left;
						if (this.nodePath[i - 1].pred() || y.black()) {
							if (this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Char2ReferenceRBTreeMap.Entry<V> x = this.nodePath[i];
								y = x.left;
								x.left = y.right;
								y.right = x;
								this.nodePath[i - 1].right = y;
								if (y.succ()) {
									y.succ(false);
									x.pred(y);
								}
							}

							Char2ReferenceRBTreeMap.Entry<V> xxx = this.nodePath[i - 1];
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
	public V remove(char k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Char2ReferenceRBTreeMap.Entry<V> p = this.tree;
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
				Char2ReferenceRBTreeMap.Entry<V> r = p.right;
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
						Char2ReferenceRBTreeMap.Entry<V> s = r.left;
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
						Char2ReferenceRBTreeMap.Entry<V> x = this.dirPath[i - 1] ? this.nodePath[i - 1].right : this.nodePath[i - 1].left;
						if (!x.black()) {
							x.black(true);
							break;
						}
					}

					if (!this.dirPath[i - 1]) {
						Char2ReferenceRBTreeMap.Entry<V> w = this.nodePath[i - 1].right;
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
								Char2ReferenceRBTreeMap.Entry<V> y = w.left;
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
						Char2ReferenceRBTreeMap.Entry<V> wx = this.nodePath[i - 1].left;
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
								Char2ReferenceRBTreeMap.Entry<V> y = wx.right;
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
	public boolean containsValue(Object v) {
		Char2ReferenceRBTreeMap<V>.ValueIterator i = new Char2ReferenceRBTreeMap.ValueIterator();
		int j = this.count;

		while (j-- != 0) {
			Object ev = i.next();
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
	public V get(char k) {
		Char2ReferenceRBTreeMap.Entry<V> e = this.findKey(k);
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
	public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>>() {
				final Comparator<? super it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> comparator = (x, y) -> Char2ReferenceRBTreeMap.this.actualComparator
						.compare(x.getCharKey(), y.getCharKey());

				public Comparator<? super it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> iterator() {
					return Char2ReferenceRBTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> iterator(
					it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> from
				) {
					return Char2ReferenceRBTreeMap.this.new EntryIterator(from.getCharKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() != null && e.getKey() instanceof Character) {
							Char2ReferenceRBTreeMap.Entry<V> f = Char2ReferenceRBTreeMap.this.findKey((Character)e.getKey());
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
						if (e.getKey() != null && e.getKey() instanceof Character) {
							Char2ReferenceRBTreeMap.Entry<V> f = Char2ReferenceRBTreeMap.this.findKey((Character)e.getKey());
							if (f != null && f.getValue() == e.getValue()) {
								Char2ReferenceRBTreeMap.this.remove(f.key);
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
					return Char2ReferenceRBTreeMap.this.count;
				}

				public void clear() {
					Char2ReferenceRBTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> first() {
					return Char2ReferenceRBTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> last() {
					return Char2ReferenceRBTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> subSet(
					it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> from, it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> to
				) {
					return Char2ReferenceRBTreeMap.this.subMap(from.getCharKey(), to.getCharKey()).char2ReferenceEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> headSet(it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> to) {
					return Char2ReferenceRBTreeMap.this.headMap(to.getCharKey()).char2ReferenceEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> tailSet(it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> from) {
					return Char2ReferenceRBTreeMap.this.tailMap(from.getCharKey()).char2ReferenceEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public CharSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Char2ReferenceRBTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ReferenceCollection<V> values() {
		if (this.values == null) {
			this.values = new AbstractReferenceCollection<V>() {
				@Override
				public ObjectIterator<V> iterator() {
					return Char2ReferenceRBTreeMap.this.new ValueIterator();
				}

				public boolean contains(Object k) {
					return Char2ReferenceRBTreeMap.this.containsValue(k);
				}

				public int size() {
					return Char2ReferenceRBTreeMap.this.count;
				}

				public void clear() {
					Char2ReferenceRBTreeMap.this.clear();
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
	public Char2ReferenceSortedMap<V> headMap(char to) {
		return new Char2ReferenceRBTreeMap.Submap('\u0000', true, to, false);
	}

	@Override
	public Char2ReferenceSortedMap<V> tailMap(char from) {
		return new Char2ReferenceRBTreeMap.Submap(from, false, '\u0000', true);
	}

	@Override
	public Char2ReferenceSortedMap<V> subMap(char from, char to) {
		return new Char2ReferenceRBTreeMap.Submap(from, false, to, false);
	}

	public Char2ReferenceRBTreeMap<V> clone() {
		Char2ReferenceRBTreeMap<V> c;
		try {
			c = (Char2ReferenceRBTreeMap<V>)super.clone();
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
			Char2ReferenceRBTreeMap.Entry<V> rp = new Char2ReferenceRBTreeMap.Entry<>();
			Char2ReferenceRBTreeMap.Entry<V> rq = new Char2ReferenceRBTreeMap.Entry<>();
			Char2ReferenceRBTreeMap.Entry<V> p = rp;
			rp.left(this.tree);
			Char2ReferenceRBTreeMap.Entry<V> q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Char2ReferenceRBTreeMap.Entry<V> e = p.left.clone();
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
					Char2ReferenceRBTreeMap.Entry<V> e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Char2ReferenceRBTreeMap<V>.EntryIterator i = new Char2ReferenceRBTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Char2ReferenceRBTreeMap.Entry<V> e = i.nextEntry();
			s.writeChar(e.key);
			s.writeObject(e.value);
		}
	}

	private Char2ReferenceRBTreeMap.Entry<V> readTree(ObjectInputStream s, int n, Char2ReferenceRBTreeMap.Entry<V> pred, Char2ReferenceRBTreeMap.Entry<V> succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Char2ReferenceRBTreeMap.Entry<V> top = new Char2ReferenceRBTreeMap.Entry<>(s.readChar(), (V)s.readObject());
			top.pred(pred);
			top.succ(succ);
			top.black(true);
			return top;
		} else if (n == 2) {
			Char2ReferenceRBTreeMap.Entry<V> top = new Char2ReferenceRBTreeMap.Entry<>(s.readChar(), (V)s.readObject());
			top.black(true);
			top.right(new Char2ReferenceRBTreeMap.Entry<>(s.readChar(), (V)s.readObject()));
			top.right.pred(top);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Char2ReferenceRBTreeMap.Entry<V> top = new Char2ReferenceRBTreeMap.Entry<>();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = s.readChar();
			top.value = (V)s.readObject();
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
			Char2ReferenceRBTreeMap.Entry<V> e = this.tree;

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

	private static final class Entry<V> extends BasicEntry<V> implements Cloneable {
		private static final int BLACK_MASK = 1;
		private static final int SUCC_MASK = Integer.MIN_VALUE;
		private static final int PRED_MASK = 1073741824;
		Char2ReferenceRBTreeMap.Entry<V> left;
		Char2ReferenceRBTreeMap.Entry<V> right;
		int info;

		Entry() {
			super('\u0000', null);
		}

		Entry(char k, V v) {
			super(k, v);
			this.info = -1073741824;
		}

		Char2ReferenceRBTreeMap.Entry<V> left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Char2ReferenceRBTreeMap.Entry<V> right() {
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

		void pred(Char2ReferenceRBTreeMap.Entry<V> pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Char2ReferenceRBTreeMap.Entry<V> succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Char2ReferenceRBTreeMap.Entry<V> left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Char2ReferenceRBTreeMap.Entry<V> right) {
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

		Char2ReferenceRBTreeMap.Entry<V> next() {
			Char2ReferenceRBTreeMap.Entry<V> next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Char2ReferenceRBTreeMap.Entry<V> prev() {
			Char2ReferenceRBTreeMap.Entry<V> prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		@Override
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Char2ReferenceRBTreeMap.Entry<V> clone() {
			Char2ReferenceRBTreeMap.Entry<V> c;
			try {
				c = (Char2ReferenceRBTreeMap.Entry<V>)super.clone();
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
				java.util.Map.Entry<Character, V> e = (java.util.Map.Entry<Character, V>)o;
				return this.key == (Character)e.getKey() && this.value == e.getValue();
			}
		}

		@Override
		public int hashCode() {
			return this.key ^ (this.value == null ? 0 : System.identityHashCode(this.value));
		}

		@Override
		public String toString() {
			return this.key + "=>" + this.value;
		}
	}

	private class EntryIterator
		extends Char2ReferenceRBTreeMap<V>.TreeIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> {
		EntryIterator() {
		}

		EntryIterator(char k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> previous() {
			return this.previousEntry();
		}
	}

	private final class KeyIterator extends Char2ReferenceRBTreeMap<V>.TreeIterator implements CharListIterator {
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

	private class KeySet extends AbstractChar2ReferenceSortedMap<V>.KeySet {
		private KeySet() {
			super(Char2ReferenceRBTreeMap.this);
		}

		@Override
		public CharBidirectionalIterator iterator() {
			return Char2ReferenceRBTreeMap.this.new KeyIterator();
		}

		@Override
		public CharBidirectionalIterator iterator(char from) {
			return Char2ReferenceRBTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractChar2ReferenceSortedMap<V> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		char from;
		char to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> entries;
		protected transient CharSortedSet keys;
		protected transient ReferenceCollection<V> values;

		public Submap(char from, boolean bottom, char to, boolean top) {
			if (!bottom && !top && Char2ReferenceRBTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Char2ReferenceRBTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Char2ReferenceRBTreeMap<V>.Submap.SubmapIterator i = new Char2ReferenceRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(char k) {
			return (this.bottom || Char2ReferenceRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Char2ReferenceRBTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> iterator(
						it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> from
					) {
						return Submap.this.new SubmapEntryIterator(from.getCharKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> comparator() {
						return Char2ReferenceRBTreeMap.this.char2ReferenceEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() != null && e.getKey() instanceof Character) {
								Char2ReferenceRBTreeMap.Entry<V> f = Char2ReferenceRBTreeMap.this.findKey((Character)e.getKey());
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
							if (e.getKey() != null && e.getKey() instanceof Character) {
								Char2ReferenceRBTreeMap.Entry<V> f = Char2ReferenceRBTreeMap.this.findKey((Character)e.getKey());
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

					public it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> subSet(
						it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> from, it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> to
					) {
						return Submap.this.subMap(from.getCharKey(), to.getCharKey()).char2ReferenceEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> headSet(it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> to) {
						return Submap.this.headMap(to.getCharKey()).char2ReferenceEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> tailSet(it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> from) {
						return Submap.this.tailMap(from.getCharKey()).char2ReferenceEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public CharSortedSet keySet() {
			if (this.keys == null) {
				this.keys = new Char2ReferenceRBTreeMap.Submap.KeySet();
			}

			return this.keys;
		}

		@Override
		public ReferenceCollection<V> values() {
			if (this.values == null) {
				this.values = new AbstractReferenceCollection<V>() {
					@Override
					public ObjectIterator<V> iterator() {
						return Submap.this.new SubmapValueIterator();
					}

					public boolean contains(Object k) {
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
			return this.in(k) && Char2ReferenceRBTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(Object v) {
			Char2ReferenceRBTreeMap<V>.Submap.SubmapIterator i = new Char2ReferenceRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				Object ev = i.nextEntry().value;
				if (ev == v) {
					return true;
				}
			}

			return false;
		}

		@Override
		public V get(char k) {
			Char2ReferenceRBTreeMap.Entry<V> e;
			return this.in(k) && (e = Char2ReferenceRBTreeMap.this.findKey(k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public V put(char k, V v) {
			Char2ReferenceRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				V oldValue = Char2ReferenceRBTreeMap.this.put(k, v);
				return Char2ReferenceRBTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public V remove(char k) {
			Char2ReferenceRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				return this.defRetValue;
			} else {
				V oldValue = Char2ReferenceRBTreeMap.this.remove(k);
				return Char2ReferenceRBTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Char2ReferenceRBTreeMap<V>.Submap.SubmapIterator i = new Char2ReferenceRBTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Char2ReferenceRBTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public CharComparator comparator() {
			return Char2ReferenceRBTreeMap.this.actualComparator;
		}

		@Override
		public Char2ReferenceSortedMap<V> headMap(char to) {
			if (this.top) {
				return Char2ReferenceRBTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Char2ReferenceRBTreeMap.this.compare(to, this.to) < 0 ? Char2ReferenceRBTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Char2ReferenceSortedMap<V> tailMap(char from) {
			if (this.bottom) {
				return Char2ReferenceRBTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Char2ReferenceRBTreeMap.this.compare(from, this.from) > 0 ? Char2ReferenceRBTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Char2ReferenceSortedMap<V> subMap(char from, char to) {
			if (this.top && this.bottom) {
				return Char2ReferenceRBTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Char2ReferenceRBTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Char2ReferenceRBTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Char2ReferenceRBTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Char2ReferenceRBTreeMap.Entry<V> firstEntry() {
			if (Char2ReferenceRBTreeMap.this.tree == null) {
				return null;
			} else {
				Char2ReferenceRBTreeMap.Entry<V> e;
				if (this.bottom) {
					e = Char2ReferenceRBTreeMap.this.firstEntry;
				} else {
					e = Char2ReferenceRBTreeMap.this.locateKey(this.from);
					if (Char2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Char2ReferenceRBTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Char2ReferenceRBTreeMap.Entry<V> lastEntry() {
			if (Char2ReferenceRBTreeMap.this.tree == null) {
				return null;
			} else {
				Char2ReferenceRBTreeMap.Entry<V> e;
				if (this.top) {
					e = Char2ReferenceRBTreeMap.this.lastEntry;
				} else {
					e = Char2ReferenceRBTreeMap.this.locateKey(this.to);
					if (Char2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Char2ReferenceRBTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		@Override
		public char firstCharKey() {
			Char2ReferenceRBTreeMap.Entry<V> e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		@Override
		public char lastCharKey() {
			Char2ReferenceRBTreeMap.Entry<V> e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends AbstractChar2ReferenceSortedMap<V>.KeySet {
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
			extends Char2ReferenceRBTreeMap<V>.Submap.SubmapIterator
			implements ObjectListIterator<it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V>> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(char k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry<V> previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Char2ReferenceRBTreeMap<V>.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(char k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Char2ReferenceRBTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Char2ReferenceRBTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Char2ReferenceRBTreeMap.this.locateKey(k);
						if (Char2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
				if (!Submap.this.bottom && this.prev != null && Char2ReferenceRBTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Char2ReferenceRBTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Char2ReferenceRBTreeMap<V>.Submap.SubmapIterator implements CharListIterator {
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

		private final class SubmapValueIterator extends Char2ReferenceRBTreeMap<V>.Submap.SubmapIterator implements ObjectListIterator<V> {
			private SubmapValueIterator() {
			}

			public V next() {
				return this.nextEntry().value;
			}

			@Override
			public V previous() {
				return this.previousEntry().value;
			}
		}
	}

	private class TreeIterator {
		Char2ReferenceRBTreeMap.Entry<V> prev;
		Char2ReferenceRBTreeMap.Entry<V> next;
		Char2ReferenceRBTreeMap.Entry<V> curr;
		int index = 0;

		TreeIterator() {
			this.next = Char2ReferenceRBTreeMap.this.firstEntry;
		}

		TreeIterator(char k) {
			if ((this.next = Char2ReferenceRBTreeMap.this.locateKey(k)) != null) {
				if (Char2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0) {
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

		Char2ReferenceRBTreeMap.Entry<V> nextEntry() {
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

		Char2ReferenceRBTreeMap.Entry<V> previousEntry() {
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
				Char2ReferenceRBTreeMap.this.remove(this.curr.key);
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

	private final class ValueIterator extends Char2ReferenceRBTreeMap<V>.TreeIterator implements ObjectListIterator<V> {
		private ValueIterator() {
		}

		public V next() {
			return this.nextEntry().value;
		}

		@Override
		public V previous() {
			return this.previousEntry().value;
		}
	}
}
