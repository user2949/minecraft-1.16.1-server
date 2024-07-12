package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleMap.BasicEntry;
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

public class Double2DoubleRBTreeMap extends AbstractDouble2DoubleSortedMap implements Serializable, Cloneable {
	protected transient Double2DoubleRBTreeMap.Entry tree;
	protected int count;
	protected transient Double2DoubleRBTreeMap.Entry firstEntry;
	protected transient Double2DoubleRBTreeMap.Entry lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> entries;
	protected transient DoubleSortedSet keys;
	protected transient DoubleCollection values;
	protected transient boolean modified;
	protected Comparator<? super Double> storedComparator;
	protected transient DoubleComparator actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;
	private transient Double2DoubleRBTreeMap.Entry[] nodePath;

	public Double2DoubleRBTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
	}

	public Double2DoubleRBTreeMap(Comparator<? super Double> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Double2DoubleRBTreeMap(Map<? extends Double, ? extends Double> m) {
		this();
		this.putAll(m);
	}

	public Double2DoubleRBTreeMap(SortedMap<Double, Double> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Double2DoubleRBTreeMap(Double2DoubleMap m) {
		this();
		this.putAll(m);
	}

	public Double2DoubleRBTreeMap(Double2DoubleSortedMap m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Double2DoubleRBTreeMap(double[] k, double[] v, Comparator<? super Double> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Double2DoubleRBTreeMap(double[] k, double[] v) {
		this(k, v, null);
	}

	final int compare(double k1, double k2) {
		return this.actualComparator == null ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
	}

	final Double2DoubleRBTreeMap.Entry findKey(double k) {
		Double2DoubleRBTreeMap.Entry e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Double2DoubleRBTreeMap.Entry locateKey(double k) {
		Double2DoubleRBTreeMap.Entry e = this.tree;
		Double2DoubleRBTreeMap.Entry last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[64];
		this.nodePath = new Double2DoubleRBTreeMap.Entry[64];
	}

	public double addTo(double k, double incr) {
		Double2DoubleRBTreeMap.Entry e = this.add(k);
		double oldValue = e.value;
		e.value += incr;
		return oldValue;
	}

	@Override
	public double put(double k, double v) {
		Double2DoubleRBTreeMap.Entry e = this.add(k);
		double oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Double2DoubleRBTreeMap.Entry add(double k) {
		this.modified = false;
		int maxDepth = 0;
		Double2DoubleRBTreeMap.Entry e;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Double2DoubleRBTreeMap.Entry(k, this.defRetValue);
		} else {
			Double2DoubleRBTreeMap.Entry p = this.tree;
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
					e = new Double2DoubleRBTreeMap.Entry(k, this.defRetValue);
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
					e = new Double2DoubleRBTreeMap.Entry(k, this.defRetValue);
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
						Double2DoubleRBTreeMap.Entry y = this.nodePath[i - 1].right;
						if (this.nodePath[i - 1].succ() || y.black()) {
							if (!this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Double2DoubleRBTreeMap.Entry x = this.nodePath[i];
								y = x.right;
								x.right = y.left;
								y.left = x;
								this.nodePath[i - 1].left = y;
								if (y.pred()) {
									y.pred(false);
									x.succ(y);
								}
							}

							Double2DoubleRBTreeMap.Entry xx = this.nodePath[i - 1];
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
						Double2DoubleRBTreeMap.Entry y = this.nodePath[i - 1].left;
						if (this.nodePath[i - 1].pred() || y.black()) {
							if (this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Double2DoubleRBTreeMap.Entry x = this.nodePath[i];
								y = x.left;
								x.left = y.right;
								y.right = x;
								this.nodePath[i - 1].right = y;
								if (y.succ()) {
									y.succ(false);
									x.pred(y);
								}
							}

							Double2DoubleRBTreeMap.Entry xxx = this.nodePath[i - 1];
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
	public double remove(double k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Double2DoubleRBTreeMap.Entry p = this.tree;
			int i = 0;
			double kk = k;

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
				Double2DoubleRBTreeMap.Entry r = p.right;
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
						Double2DoubleRBTreeMap.Entry s = r.left;
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
						Double2DoubleRBTreeMap.Entry x = this.dirPath[i - 1] ? this.nodePath[i - 1].right : this.nodePath[i - 1].left;
						if (!x.black()) {
							x.black(true);
							break;
						}
					}

					if (!this.dirPath[i - 1]) {
						Double2DoubleRBTreeMap.Entry w = this.nodePath[i - 1].right;
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
								Double2DoubleRBTreeMap.Entry y = w.left;
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
						Double2DoubleRBTreeMap.Entry wx = this.nodePath[i - 1].left;
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
								Double2DoubleRBTreeMap.Entry y = wx.right;
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
	public boolean containsValue(double v) {
		Double2DoubleRBTreeMap.ValueIterator i = new Double2DoubleRBTreeMap.ValueIterator();
		int j = this.count;

		while (j-- != 0) {
			double ev = i.nextDouble();
			if (Double.doubleToLongBits(ev) == Double.doubleToLongBits(v)) {
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
	public boolean containsKey(double k) {
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
	public double get(double k) {
		Double2DoubleRBTreeMap.Entry e = this.findKey(k);
		return e == null ? this.defRetValue : e.value;
	}

	@Override
	public double firstDoubleKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	@Override
	public double lastDoubleKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> double2DoubleEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry>() {
				final Comparator<? super it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> comparator = (x, y) -> Double2DoubleRBTreeMap.this.actualComparator
						.compare(x.getDoubleKey(), y.getDoubleKey());

				public Comparator<? super it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> iterator() {
					return Double2DoubleRBTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> iterator(it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry from) {
					return Double2DoubleRBTreeMap.this.new EntryIterator(from.getDoubleKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() == null || !(e.getKey() instanceof Double)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Double) {
							Double2DoubleRBTreeMap.Entry f = Double2DoubleRBTreeMap.this.findKey((Double)e.getKey());
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
						if (e.getKey() == null || !(e.getKey() instanceof Double)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Double) {
							Double2DoubleRBTreeMap.Entry f = Double2DoubleRBTreeMap.this.findKey((Double)e.getKey());
							if (f != null && Double.doubleToLongBits(f.getDoubleValue()) == Double.doubleToLongBits((Double)e.getValue())) {
								Double2DoubleRBTreeMap.this.remove(f.key);
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
					return Double2DoubleRBTreeMap.this.count;
				}

				public void clear() {
					Double2DoubleRBTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry first() {
					return Double2DoubleRBTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry last() {
					return Double2DoubleRBTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> subSet(
					it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry from, it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry to
				) {
					return Double2DoubleRBTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2DoubleEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> headSet(it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry to) {
					return Double2DoubleRBTreeMap.this.headMap(to.getDoubleKey()).double2DoubleEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> tailSet(it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry from) {
					return Double2DoubleRBTreeMap.this.tailMap(from.getDoubleKey()).double2DoubleEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public DoubleSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Double2DoubleRBTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public DoubleCollection values() {
		if (this.values == null) {
			this.values = new AbstractDoubleCollection() {
				@Override
				public DoubleIterator iterator() {
					return Double2DoubleRBTreeMap.this.new ValueIterator();
				}

				@Override
				public boolean contains(double k) {
					return Double2DoubleRBTreeMap.this.containsValue(k);
				}

				public int size() {
					return Double2DoubleRBTreeMap.this.count;
				}

				public void clear() {
					Double2DoubleRBTreeMap.this.clear();
				}
			};
		}

		return this.values;
	}

	@Override
	public DoubleComparator comparator() {
		return this.actualComparator;
	}

	@Override
	public Double2DoubleSortedMap headMap(double to) {
		return new Double2DoubleRBTreeMap.Submap(0.0, true, to, false);
	}

	@Override
	public Double2DoubleSortedMap tailMap(double from) {
		return new Double2DoubleRBTreeMap.Submap(from, false, 0.0, true);
	}

	@Override
	public Double2DoubleSortedMap subMap(double from, double to) {
		return new Double2DoubleRBTreeMap.Submap(from, false, to, false);
	}

	public Double2DoubleRBTreeMap clone() {
		Double2DoubleRBTreeMap c;
		try {
			c = (Double2DoubleRBTreeMap)super.clone();
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
			Double2DoubleRBTreeMap.Entry rp = new Double2DoubleRBTreeMap.Entry();
			Double2DoubleRBTreeMap.Entry rq = new Double2DoubleRBTreeMap.Entry();
			Double2DoubleRBTreeMap.Entry p = rp;
			rp.left(this.tree);
			Double2DoubleRBTreeMap.Entry q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Double2DoubleRBTreeMap.Entry e = p.left.clone();
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
					Double2DoubleRBTreeMap.Entry e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Double2DoubleRBTreeMap.EntryIterator i = new Double2DoubleRBTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Double2DoubleRBTreeMap.Entry e = i.nextEntry();
			s.writeDouble(e.key);
			s.writeDouble(e.value);
		}
	}

	private Double2DoubleRBTreeMap.Entry readTree(ObjectInputStream s, int n, Double2DoubleRBTreeMap.Entry pred, Double2DoubleRBTreeMap.Entry succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Double2DoubleRBTreeMap.Entry top = new Double2DoubleRBTreeMap.Entry(s.readDouble(), s.readDouble());
			top.pred(pred);
			top.succ(succ);
			top.black(true);
			return top;
		} else if (n == 2) {
			Double2DoubleRBTreeMap.Entry top = new Double2DoubleRBTreeMap.Entry(s.readDouble(), s.readDouble());
			top.black(true);
			top.right(new Double2DoubleRBTreeMap.Entry(s.readDouble(), s.readDouble()));
			top.right.pred(top);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Double2DoubleRBTreeMap.Entry top = new Double2DoubleRBTreeMap.Entry();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = s.readDouble();
			top.value = s.readDouble();
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
			Double2DoubleRBTreeMap.Entry e = this.tree;

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
		Double2DoubleRBTreeMap.Entry left;
		Double2DoubleRBTreeMap.Entry right;
		int info;

		Entry() {
			super(0.0, 0.0);
		}

		Entry(double k, double v) {
			super(k, v);
			this.info = -1073741824;
		}

		Double2DoubleRBTreeMap.Entry left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Double2DoubleRBTreeMap.Entry right() {
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

		void pred(Double2DoubleRBTreeMap.Entry pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Double2DoubleRBTreeMap.Entry succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Double2DoubleRBTreeMap.Entry left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Double2DoubleRBTreeMap.Entry right) {
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

		Double2DoubleRBTreeMap.Entry next() {
			Double2DoubleRBTreeMap.Entry next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Double2DoubleRBTreeMap.Entry prev() {
			Double2DoubleRBTreeMap.Entry prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		@Override
		public double setValue(double value) {
			double oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Double2DoubleRBTreeMap.Entry clone() {
			Double2DoubleRBTreeMap.Entry c;
			try {
				c = (Double2DoubleRBTreeMap.Entry)super.clone();
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
				java.util.Map.Entry<Double, Double> e = (java.util.Map.Entry<Double, Double>)o;
				return Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)e.getKey())
					&& Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)e.getValue());
			}
		}

		@Override
		public int hashCode() {
			return HashCommon.double2int(this.key) ^ HashCommon.double2int(this.value);
		}

		@Override
		public String toString() {
			return this.key + "=>" + this.value;
		}
	}

	private class EntryIterator extends Double2DoubleRBTreeMap.TreeIterator implements ObjectListIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> {
		EntryIterator() {
		}

		EntryIterator(double k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry previous() {
			return this.previousEntry();
		}
	}

	private final class KeyIterator extends Double2DoubleRBTreeMap.TreeIterator implements DoubleListIterator {
		public KeyIterator() {
		}

		public KeyIterator(double k) {
			super(k);
		}

		@Override
		public double nextDouble() {
			return this.nextEntry().key;
		}

		@Override
		public double previousDouble() {
			return this.previousEntry().key;
		}
	}

	private class KeySet extends it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleSortedMap.KeySet {
		private KeySet() {
			super(Double2DoubleRBTreeMap.this);
		}

		@Override
		public DoubleBidirectionalIterator iterator() {
			return Double2DoubleRBTreeMap.this.new KeyIterator();
		}

		@Override
		public DoubleBidirectionalIterator iterator(double from) {
			return Double2DoubleRBTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractDouble2DoubleSortedMap implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		double from;
		double to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> entries;
		protected transient DoubleSortedSet keys;
		protected transient DoubleCollection values;

		public Submap(double from, boolean bottom, double to, boolean top) {
			if (!bottom && !top && Double2DoubleRBTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Double2DoubleRBTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Double2DoubleRBTreeMap.Submap.SubmapIterator i = new Double2DoubleRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(double k) {
			return (this.bottom || Double2DoubleRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2DoubleRBTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> double2DoubleEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> iterator(
						it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry from
					) {
						return Submap.this.new SubmapEntryIterator(from.getDoubleKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> comparator() {
						return Double2DoubleRBTreeMap.this.double2DoubleEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() == null || !(e.getKey() instanceof Double)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Double) {
								Double2DoubleRBTreeMap.Entry f = Double2DoubleRBTreeMap.this.findKey((Double)e.getKey());
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
							if (e.getKey() == null || !(e.getKey() instanceof Double)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Double) {
								Double2DoubleRBTreeMap.Entry f = Double2DoubleRBTreeMap.this.findKey((Double)e.getKey());
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

					public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> subSet(
						it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry from, it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry to
					) {
						return Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2DoubleEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> headSet(it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry to) {
						return Submap.this.headMap(to.getDoubleKey()).double2DoubleEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> tailSet(it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry from) {
						return Submap.this.tailMap(from.getDoubleKey()).double2DoubleEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public DoubleSortedSet keySet() {
			if (this.keys == null) {
				this.keys = new Double2DoubleRBTreeMap.Submap.KeySet();
			}

			return this.keys;
		}

		@Override
		public DoubleCollection values() {
			if (this.values == null) {
				this.values = new AbstractDoubleCollection() {
					@Override
					public DoubleIterator iterator() {
						return Submap.this.new SubmapValueIterator();
					}

					@Override
					public boolean contains(double k) {
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
		public boolean containsKey(double k) {
			return this.in(k) && Double2DoubleRBTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(double v) {
			Double2DoubleRBTreeMap.Submap.SubmapIterator i = new Double2DoubleRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				double ev = i.nextEntry().value;
				if (Double.doubleToLongBits(ev) == Double.doubleToLongBits(v)) {
					return true;
				}
			}

			return false;
		}

		@Override
		public double get(double k) {
			Double2DoubleRBTreeMap.Entry e;
			return this.in(k) && (e = Double2DoubleRBTreeMap.this.findKey(k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public double put(double k, double v) {
			Double2DoubleRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				double oldValue = Double2DoubleRBTreeMap.this.put(k, v);
				return Double2DoubleRBTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public double remove(double k) {
			Double2DoubleRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				return this.defRetValue;
			} else {
				double oldValue = Double2DoubleRBTreeMap.this.remove(k);
				return Double2DoubleRBTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Double2DoubleRBTreeMap.Submap.SubmapIterator i = new Double2DoubleRBTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Double2DoubleRBTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public DoubleComparator comparator() {
			return Double2DoubleRBTreeMap.this.actualComparator;
		}

		@Override
		public Double2DoubleSortedMap headMap(double to) {
			if (this.top) {
				return Double2DoubleRBTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Double2DoubleRBTreeMap.this.compare(to, this.to) < 0 ? Double2DoubleRBTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Double2DoubleSortedMap tailMap(double from) {
			if (this.bottom) {
				return Double2DoubleRBTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Double2DoubleRBTreeMap.this.compare(from, this.from) > 0 ? Double2DoubleRBTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Double2DoubleSortedMap subMap(double from, double to) {
			if (this.top && this.bottom) {
				return Double2DoubleRBTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Double2DoubleRBTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Double2DoubleRBTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Double2DoubleRBTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Double2DoubleRBTreeMap.Entry firstEntry() {
			if (Double2DoubleRBTreeMap.this.tree == null) {
				return null;
			} else {
				Double2DoubleRBTreeMap.Entry e;
				if (this.bottom) {
					e = Double2DoubleRBTreeMap.this.firstEntry;
				} else {
					e = Double2DoubleRBTreeMap.this.locateKey(this.from);
					if (Double2DoubleRBTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Double2DoubleRBTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Double2DoubleRBTreeMap.Entry lastEntry() {
			if (Double2DoubleRBTreeMap.this.tree == null) {
				return null;
			} else {
				Double2DoubleRBTreeMap.Entry e;
				if (this.top) {
					e = Double2DoubleRBTreeMap.this.lastEntry;
				} else {
					e = Double2DoubleRBTreeMap.this.locateKey(this.to);
					if (Double2DoubleRBTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Double2DoubleRBTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		@Override
		public double firstDoubleKey() {
			Double2DoubleRBTreeMap.Entry e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		@Override
		public double lastDoubleKey() {
			Double2DoubleRBTreeMap.Entry e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleSortedMap.KeySet {
			private KeySet() {
				super(Submap.this);
			}

			@Override
			public DoubleBidirectionalIterator iterator() {
				return Submap.this.new SubmapKeyIterator();
			}

			@Override
			public DoubleBidirectionalIterator iterator(double from) {
				return Submap.this.new SubmapKeyIterator(from);
			}
		}

		private class SubmapEntryIterator
			extends Double2DoubleRBTreeMap.Submap.SubmapIterator
			implements ObjectListIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(double k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Double2DoubleRBTreeMap.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(double k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Double2DoubleRBTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Double2DoubleRBTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Double2DoubleRBTreeMap.this.locateKey(k);
						if (Double2DoubleRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
				if (!Submap.this.bottom && this.prev != null && Double2DoubleRBTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Double2DoubleRBTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Double2DoubleRBTreeMap.Submap.SubmapIterator implements DoubleListIterator {
			public SubmapKeyIterator() {
			}

			public SubmapKeyIterator(double from) {
				super(from);
			}

			@Override
			public double nextDouble() {
				return this.nextEntry().key;
			}

			@Override
			public double previousDouble() {
				return this.previousEntry().key;
			}
		}

		private final class SubmapValueIterator extends Double2DoubleRBTreeMap.Submap.SubmapIterator implements DoubleListIterator {
			private SubmapValueIterator() {
			}

			@Override
			public double nextDouble() {
				return this.nextEntry().value;
			}

			@Override
			public double previousDouble() {
				return this.previousEntry().value;
			}
		}
	}

	private class TreeIterator {
		Double2DoubleRBTreeMap.Entry prev;
		Double2DoubleRBTreeMap.Entry next;
		Double2DoubleRBTreeMap.Entry curr;
		int index = 0;

		TreeIterator() {
			this.next = Double2DoubleRBTreeMap.this.firstEntry;
		}

		TreeIterator(double k) {
			if ((this.next = Double2DoubleRBTreeMap.this.locateKey(k)) != null) {
				if (Double2DoubleRBTreeMap.this.compare(this.next.key, k) <= 0) {
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

		Double2DoubleRBTreeMap.Entry nextEntry() {
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

		Double2DoubleRBTreeMap.Entry previousEntry() {
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
				Double2DoubleRBTreeMap.this.remove(this.curr.key);
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

	private final class ValueIterator extends Double2DoubleRBTreeMap.TreeIterator implements DoubleListIterator {
		private ValueIterator() {
		}

		@Override
		public double nextDouble() {
			return this.nextEntry().value;
		}

		@Override
		public double previousDouble() {
			return this.previousEntry().value;
		}
	}
}
