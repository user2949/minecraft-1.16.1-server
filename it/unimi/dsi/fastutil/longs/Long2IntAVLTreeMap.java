package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntMap.BasicEntry;
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

public class Long2IntAVLTreeMap extends AbstractLong2IntSortedMap implements Serializable, Cloneable {
	protected transient Long2IntAVLTreeMap.Entry tree;
	protected int count;
	protected transient Long2IntAVLTreeMap.Entry firstEntry;
	protected transient Long2IntAVLTreeMap.Entry lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> entries;
	protected transient LongSortedSet keys;
	protected transient IntCollection values;
	protected transient boolean modified;
	protected Comparator<? super Long> storedComparator;
	protected transient LongComparator actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;

	public Long2IntAVLTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = LongComparators.asLongComparator(this.storedComparator);
	}

	public Long2IntAVLTreeMap(Comparator<? super Long> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Long2IntAVLTreeMap(Map<? extends Long, ? extends Integer> m) {
		this();
		this.putAll(m);
	}

	public Long2IntAVLTreeMap(SortedMap<Long, Integer> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Long2IntAVLTreeMap(Long2IntMap m) {
		this();
		this.putAll(m);
	}

	public Long2IntAVLTreeMap(Long2IntSortedMap m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Long2IntAVLTreeMap(long[] k, int[] v, Comparator<? super Long> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Long2IntAVLTreeMap(long[] k, int[] v) {
		this(k, v, null);
	}

	final int compare(long k1, long k2) {
		return this.actualComparator == null ? Long.compare(k1, k2) : this.actualComparator.compare(k1, k2);
	}

	final Long2IntAVLTreeMap.Entry findKey(long k) {
		Long2IntAVLTreeMap.Entry e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Long2IntAVLTreeMap.Entry locateKey(long k) {
		Long2IntAVLTreeMap.Entry e = this.tree;
		Long2IntAVLTreeMap.Entry last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[48];
	}

	public int addTo(long k, int incr) {
		Long2IntAVLTreeMap.Entry e = this.add(k);
		int oldValue = e.value;
		e.value += incr;
		return oldValue;
	}

	@Override
	public int put(long k, int v) {
		Long2IntAVLTreeMap.Entry e = this.add(k);
		int oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Long2IntAVLTreeMap.Entry add(long k) {
		this.modified = false;
		Long2IntAVLTreeMap.Entry e = null;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Long2IntAVLTreeMap.Entry(k, this.defRetValue);
			this.modified = true;
		} else {
			Long2IntAVLTreeMap.Entry p = this.tree;
			Long2IntAVLTreeMap.Entry q = null;
			Long2IntAVLTreeMap.Entry y = this.tree;
			Long2IntAVLTreeMap.Entry z = null;
			Long2IntAVLTreeMap.Entry w = null;
			int i = 0;

			while (true) {
				int cmp;
				if ((cmp = this.compare(k, p.key)) == 0) {
					return p;
				}

				if (p.balance() != 0) {
					i = 0;
					z = q;
					y = p;
				}

				if (this.dirPath[i++] = cmp > 0) {
					if (p.succ()) {
						this.count++;
						e = new Long2IntAVLTreeMap.Entry(k, this.defRetValue);
						this.modified = true;
						if (p.right == null) {
							this.lastEntry = e;
						}

						e.left = p;
						e.right = p.right;
						p.right(e);
						break;
					}

					q = p;
					p = p.right;
				} else {
					if (p.pred()) {
						this.count++;
						e = new Long2IntAVLTreeMap.Entry(k, this.defRetValue);
						this.modified = true;
						if (p.left == null) {
							this.firstEntry = e;
						}

						e.right = p;
						e.left = p.left;
						p.left(e);
						break;
					}

					q = p;
					p = p.left;
				}
			}

			p = y;

			for (int var15 = 0; p != e; p = this.dirPath[var15++] ? p.right : p.left) {
				if (this.dirPath[var15]) {
					p.incBalance();
				} else {
					p.decBalance();
				}
			}

			if (y.balance() == -2) {
				Long2IntAVLTreeMap.Entry x = y.left;
				if (x.balance() == -1) {
					w = x;
					if (x.succ()) {
						x.succ(false);
						y.pred(x);
					} else {
						y.left = x.right;
					}

					x.right = y;
					x.balance(0);
					y.balance(0);
				} else {
					assert x.balance() == 1;

					w = x.right;
					x.right = w.left;
					w.left = x;
					y.left = w.right;
					w.right = y;
					if (w.balance() == -1) {
						x.balance(0);
						y.balance(1);
					} else if (w.balance() == 0) {
						x.balance(0);
						y.balance(0);
					} else {
						x.balance(-1);
						y.balance(0);
					}

					w.balance(0);
					if (w.pred()) {
						x.succ(w);
						w.pred(false);
					}

					if (w.succ()) {
						y.pred(w);
						w.succ(false);
					}
				}
			} else {
				if (y.balance() != 2) {
					return e;
				}

				Long2IntAVLTreeMap.Entry x = y.right;
				if (x.balance() == 1) {
					w = x;
					if (x.pred()) {
						x.pred(false);
						y.succ(x);
					} else {
						y.right = x.left;
					}

					x.left = y;
					x.balance(0);
					y.balance(0);
				} else {
					assert x.balance() == -1;

					w = x.left;
					x.left = w.right;
					w.right = x;
					y.right = w.left;
					w.left = y;
					if (w.balance() == 1) {
						x.balance(0);
						y.balance(-1);
					} else if (w.balance() == 0) {
						x.balance(0);
						y.balance(0);
					} else {
						x.balance(1);
						y.balance(0);
					}

					w.balance(0);
					if (w.pred()) {
						y.succ(w);
						w.pred(false);
					}

					if (w.succ()) {
						x.pred(w);
						w.succ(false);
					}
				}
			}

			if (z == null) {
				this.tree = w;
			} else if (z.left == y) {
				z.left = w;
			} else {
				z.right = w;
			}
		}

		return e;
	}

	private Long2IntAVLTreeMap.Entry parent(Long2IntAVLTreeMap.Entry e) {
		if (e == this.tree) {
			return null;
		} else {
			Long2IntAVLTreeMap.Entry y = e;

			Long2IntAVLTreeMap.Entry x;
			for (x = e; !y.succ(); y = y.right) {
				if (x.pred()) {
					Long2IntAVLTreeMap.Entry p = x.left;
					if (p == null || p.right != e) {
						while (!y.succ()) {
							y = y.right;
						}

						p = y.right;
					}

					return p;
				}

				x = x.left;
			}

			Long2IntAVLTreeMap.Entry p = y.right;
			if (p == null || p.left != e) {
				while (!x.pred()) {
					x = x.left;
				}

				p = x.left;
			}

			return p;
		}
	}

	@Override
	public int remove(long k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Long2IntAVLTreeMap.Entry p = this.tree;
			Long2IntAVLTreeMap.Entry q = null;
			boolean dir = false;
			long kk = k;

			int cmp;
			while ((cmp = this.compare(kk, p.key)) != 0) {
				if (dir = cmp > 0) {
					q = p;
					if ((p = p.right()) == null) {
						return this.defRetValue;
					}
				} else {
					q = p;
					if ((p = p.left()) == null) {
						return this.defRetValue;
					}
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
					if (q != null) {
						if (dir) {
							q.succ(p.right);
						} else {
							q.pred(p.left);
						}
					} else {
						this.tree = dir ? p.right : p.left;
					}
				} else {
					p.prev().right = p.right;
					if (q != null) {
						if (dir) {
							q.right = p.left;
						} else {
							q.left = p.left;
						}
					} else {
						this.tree = p.left;
					}
				}
			} else {
				Long2IntAVLTreeMap.Entry r = p.right;
				if (r.pred()) {
					r.left = p.left;
					r.pred(p.pred());
					if (!r.pred()) {
						r.prev().right = r;
					}

					if (q != null) {
						if (dir) {
							q.right = r;
						} else {
							q.left = r;
						}
					} else {
						this.tree = r;
					}

					r.balance(p.balance());
					q = r;
					dir = true;
				} else {
					while (true) {
						Long2IntAVLTreeMap.Entry s = r.left;
						if (s.pred()) {
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

							s.right = p.right;
							s.succ(false);
							if (q != null) {
								if (dir) {
									q.right = s;
								} else {
									q.left = s;
								}
							} else {
								this.tree = s;
							}

							s.balance(p.balance());
							q = r;
							dir = false;
							break;
						}

						r = s;
					}
				}
			}

			while (q != null) {
				Long2IntAVLTreeMap.Entry y = q;
				q = this.parent(q);
				if (!dir) {
					dir = q != null && q.left != y;
					y.incBalance();
					if (y.balance() == 1) {
						break;
					}

					if (y.balance() == 2) {
						Long2IntAVLTreeMap.Entry x = y.right;

						assert x != null;

						if (x.balance() == -1) {
							assert x.balance() == -1;

							Long2IntAVLTreeMap.Entry w = x.left;
							x.left = w.right;
							w.right = x;
							y.right = w.left;
							w.left = y;
							if (w.balance() == 1) {
								x.balance(0);
								y.balance(-1);
							} else if (w.balance() == 0) {
								x.balance(0);
								y.balance(0);
							} else {
								assert w.balance() == -1;

								x.balance(1);
								y.balance(0);
							}

							w.balance(0);
							if (w.pred()) {
								y.succ(w);
								w.pred(false);
							}

							if (w.succ()) {
								x.pred(w);
								w.succ(false);
							}

							if (q != null) {
								if (dir) {
									q.right = w;
								} else {
									q.left = w;
								}
							} else {
								this.tree = w;
							}
						} else {
							if (q != null) {
								if (dir) {
									q.right = x;
								} else {
									q.left = x;
								}
							} else {
								this.tree = x;
							}

							if (x.balance() == 0) {
								y.right = x.left;
								x.left = y;
								x.balance(-1);
								y.balance(1);
								break;
							}

							assert x.balance() == 1;

							if (x.pred()) {
								y.succ(true);
								x.pred(false);
							} else {
								y.right = x.left;
							}

							x.left = y;
							y.balance(0);
							x.balance(0);
						}
					}
				} else {
					dir = q != null && q.left != y;
					y.decBalance();
					if (y.balance() == -1) {
						break;
					}

					if (y.balance() == -2) {
						Long2IntAVLTreeMap.Entry xx = y.left;

						assert xx != null;

						if (xx.balance() == 1) {
							assert xx.balance() == 1;

							Long2IntAVLTreeMap.Entry wx = xx.right;
							xx.right = wx.left;
							wx.left = xx;
							y.left = wx.right;
							wx.right = y;
							if (wx.balance() == -1) {
								xx.balance(0);
								y.balance(1);
							} else if (wx.balance() == 0) {
								xx.balance(0);
								y.balance(0);
							} else {
								assert wx.balance() == 1;

								xx.balance(-1);
								y.balance(0);
							}

							wx.balance(0);
							if (wx.pred()) {
								xx.succ(wx);
								wx.pred(false);
							}

							if (wx.succ()) {
								y.pred(wx);
								wx.succ(false);
							}

							if (q != null) {
								if (dir) {
									q.right = wx;
								} else {
									q.left = wx;
								}
							} else {
								this.tree = wx;
							}
						} else {
							if (q != null) {
								if (dir) {
									q.right = xx;
								} else {
									q.left = xx;
								}
							} else {
								this.tree = xx;
							}

							if (xx.balance() == 0) {
								y.left = xx.right;
								xx.right = y;
								xx.balance(1);
								y.balance(-1);
								break;
							}

							assert xx.balance() == -1;

							if (xx.succ()) {
								y.pred(true);
								xx.succ(false);
							} else {
								y.left = xx.right;
							}

							xx.right = y;
							y.balance(0);
							xx.balance(0);
						}
					}
				}
			}

			this.modified = true;
			this.count--;
			return p.value;
		}
	}

	@Override
	public boolean containsValue(int v) {
		Long2IntAVLTreeMap.ValueIterator i = new Long2IntAVLTreeMap.ValueIterator();
		int j = this.count;

		while (j-- != 0) {
			int ev = i.nextInt();
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
	public boolean containsKey(long k) {
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
	public int get(long k) {
		Long2IntAVLTreeMap.Entry e = this.findKey(k);
		return e == null ? this.defRetValue : e.value;
	}

	@Override
	public long firstLongKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	@Override
	public long lastLongKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> long2IntEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry>() {
				final Comparator<? super it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> comparator = (x, y) -> Long2IntAVLTreeMap.this.actualComparator
						.compare(x.getLongKey(), y.getLongKey());

				public Comparator<? super it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> iterator() {
					return Long2IntAVLTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> iterator(it.unimi.dsi.fastutil.longs.Long2IntMap.Entry from) {
					return Long2IntAVLTreeMap.this.new EntryIterator(from.getLongKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() == null || !(e.getKey() instanceof Long)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Integer) {
							Long2IntAVLTreeMap.Entry f = Long2IntAVLTreeMap.this.findKey((Long)e.getKey());
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
						if (e.getKey() == null || !(e.getKey() instanceof Long)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Integer) {
							Long2IntAVLTreeMap.Entry f = Long2IntAVLTreeMap.this.findKey((Long)e.getKey());
							if (f != null && f.getIntValue() == (Integer)e.getValue()) {
								Long2IntAVLTreeMap.this.remove(f.key);
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
					return Long2IntAVLTreeMap.this.count;
				}

				public void clear() {
					Long2IntAVLTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.longs.Long2IntMap.Entry first() {
					return Long2IntAVLTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.longs.Long2IntMap.Entry last() {
					return Long2IntAVLTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> subSet(
					it.unimi.dsi.fastutil.longs.Long2IntMap.Entry from, it.unimi.dsi.fastutil.longs.Long2IntMap.Entry to
				) {
					return Long2IntAVLTreeMap.this.subMap(from.getLongKey(), to.getLongKey()).long2IntEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> headSet(it.unimi.dsi.fastutil.longs.Long2IntMap.Entry to) {
					return Long2IntAVLTreeMap.this.headMap(to.getLongKey()).long2IntEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> tailSet(it.unimi.dsi.fastutil.longs.Long2IntMap.Entry from) {
					return Long2IntAVLTreeMap.this.tailMap(from.getLongKey()).long2IntEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public LongSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Long2IntAVLTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public IntCollection values() {
		if (this.values == null) {
			this.values = new AbstractIntCollection() {
				@Override
				public IntIterator iterator() {
					return Long2IntAVLTreeMap.this.new ValueIterator();
				}

				@Override
				public boolean contains(int k) {
					return Long2IntAVLTreeMap.this.containsValue(k);
				}

				public int size() {
					return Long2IntAVLTreeMap.this.count;
				}

				public void clear() {
					Long2IntAVLTreeMap.this.clear();
				}
			};
		}

		return this.values;
	}

	@Override
	public LongComparator comparator() {
		return this.actualComparator;
	}

	@Override
	public Long2IntSortedMap headMap(long to) {
		return new Long2IntAVLTreeMap.Submap(0L, true, to, false);
	}

	@Override
	public Long2IntSortedMap tailMap(long from) {
		return new Long2IntAVLTreeMap.Submap(from, false, 0L, true);
	}

	@Override
	public Long2IntSortedMap subMap(long from, long to) {
		return new Long2IntAVLTreeMap.Submap(from, false, to, false);
	}

	public Long2IntAVLTreeMap clone() {
		Long2IntAVLTreeMap c;
		try {
			c = (Long2IntAVLTreeMap)super.clone();
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
			Long2IntAVLTreeMap.Entry rp = new Long2IntAVLTreeMap.Entry();
			Long2IntAVLTreeMap.Entry rq = new Long2IntAVLTreeMap.Entry();
			Long2IntAVLTreeMap.Entry p = rp;
			rp.left(this.tree);
			Long2IntAVLTreeMap.Entry q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Long2IntAVLTreeMap.Entry e = p.left.clone();
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
					Long2IntAVLTreeMap.Entry e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Long2IntAVLTreeMap.EntryIterator i = new Long2IntAVLTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Long2IntAVLTreeMap.Entry e = i.nextEntry();
			s.writeLong(e.key);
			s.writeInt(e.value);
		}
	}

	private Long2IntAVLTreeMap.Entry readTree(ObjectInputStream s, int n, Long2IntAVLTreeMap.Entry pred, Long2IntAVLTreeMap.Entry succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Long2IntAVLTreeMap.Entry top = new Long2IntAVLTreeMap.Entry(s.readLong(), s.readInt());
			top.pred(pred);
			top.succ(succ);
			return top;
		} else if (n == 2) {
			Long2IntAVLTreeMap.Entry top = new Long2IntAVLTreeMap.Entry(s.readLong(), s.readInt());
			top.right(new Long2IntAVLTreeMap.Entry(s.readLong(), s.readInt()));
			top.right.pred(top);
			top.balance(1);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Long2IntAVLTreeMap.Entry top = new Long2IntAVLTreeMap.Entry();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = s.readLong();
			top.value = s.readInt();
			top.right(this.readTree(s, rightN, top, succ));
			if (n == (n & -n)) {
				top.balance(1);
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
			Long2IntAVLTreeMap.Entry e = this.tree;

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
		private static final int SUCC_MASK = Integer.MIN_VALUE;
		private static final int PRED_MASK = 1073741824;
		private static final int BALANCE_MASK = 255;
		Long2IntAVLTreeMap.Entry left;
		Long2IntAVLTreeMap.Entry right;
		int info;

		Entry() {
			super(0L, 0);
		}

		Entry(long k, int v) {
			super(k, v);
			this.info = -1073741824;
		}

		Long2IntAVLTreeMap.Entry left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Long2IntAVLTreeMap.Entry right() {
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

		void pred(Long2IntAVLTreeMap.Entry pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Long2IntAVLTreeMap.Entry succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Long2IntAVLTreeMap.Entry left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Long2IntAVLTreeMap.Entry right) {
			this.info &= Integer.MAX_VALUE;
			this.right = right;
		}

		int balance() {
			return (byte)this.info;
		}

		void balance(int level) {
			this.info &= -256;
			this.info |= level & 0xFF;
		}

		void incBalance() {
			this.info = this.info & -256 | (byte)this.info + 1 & 0xFF;
		}

		protected void decBalance() {
			this.info = this.info & -256 | (byte)this.info - 1 & 0xFF;
		}

		Long2IntAVLTreeMap.Entry next() {
			Long2IntAVLTreeMap.Entry next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Long2IntAVLTreeMap.Entry prev() {
			Long2IntAVLTreeMap.Entry prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		@Override
		public int setValue(int value) {
			int oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Long2IntAVLTreeMap.Entry clone() {
			Long2IntAVLTreeMap.Entry c;
			try {
				c = (Long2IntAVLTreeMap.Entry)super.clone();
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
				java.util.Map.Entry<Long, Integer> e = (java.util.Map.Entry<Long, Integer>)o;
				return this.key == (Long)e.getKey() && this.value == (Integer)e.getValue();
			}
		}

		@Override
		public int hashCode() {
			return HashCommon.long2int(this.key) ^ this.value;
		}

		@Override
		public String toString() {
			return this.key + "=>" + this.value;
		}
	}

	private class EntryIterator extends Long2IntAVLTreeMap.TreeIterator implements ObjectListIterator<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> {
		EntryIterator() {
		}

		EntryIterator(long k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.longs.Long2IntMap.Entry next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.longs.Long2IntMap.Entry previous() {
			return this.previousEntry();
		}

		public void set(it.unimi.dsi.fastutil.longs.Long2IntMap.Entry ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.longs.Long2IntMap.Entry ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class KeyIterator extends Long2IntAVLTreeMap.TreeIterator implements LongListIterator {
		public KeyIterator() {
		}

		public KeyIterator(long k) {
			super(k);
		}

		@Override
		public long nextLong() {
			return this.nextEntry().key;
		}

		@Override
		public long previousLong() {
			return this.previousEntry().key;
		}
	}

	private class KeySet extends it.unimi.dsi.fastutil.longs.AbstractLong2IntSortedMap.KeySet {
		private KeySet() {
			super(Long2IntAVLTreeMap.this);
		}

		@Override
		public LongBidirectionalIterator iterator() {
			return Long2IntAVLTreeMap.this.new KeyIterator();
		}

		@Override
		public LongBidirectionalIterator iterator(long from) {
			return Long2IntAVLTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractLong2IntSortedMap implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		long from;
		long to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> entries;
		protected transient LongSortedSet keys;
		protected transient IntCollection values;

		public Submap(long from, boolean bottom, long to, boolean top) {
			if (!bottom && !top && Long2IntAVLTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Long2IntAVLTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Long2IntAVLTreeMap.Submap.SubmapIterator i = new Long2IntAVLTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(long k) {
			return (this.bottom || Long2IntAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Long2IntAVLTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> long2IntEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> iterator(it.unimi.dsi.fastutil.longs.Long2IntMap.Entry from) {
						return Submap.this.new SubmapEntryIterator(from.getLongKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> comparator() {
						return Long2IntAVLTreeMap.this.long2IntEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() == null || !(e.getKey() instanceof Long)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Integer) {
								Long2IntAVLTreeMap.Entry f = Long2IntAVLTreeMap.this.findKey((Long)e.getKey());
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
							if (e.getKey() == null || !(e.getKey() instanceof Long)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Integer) {
								Long2IntAVLTreeMap.Entry f = Long2IntAVLTreeMap.this.findKey((Long)e.getKey());
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

					public it.unimi.dsi.fastutil.longs.Long2IntMap.Entry first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.longs.Long2IntMap.Entry last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> subSet(
						it.unimi.dsi.fastutil.longs.Long2IntMap.Entry from, it.unimi.dsi.fastutil.longs.Long2IntMap.Entry to
					) {
						return Submap.this.subMap(from.getLongKey(), to.getLongKey()).long2IntEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> headSet(it.unimi.dsi.fastutil.longs.Long2IntMap.Entry to) {
						return Submap.this.headMap(to.getLongKey()).long2IntEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> tailSet(it.unimi.dsi.fastutil.longs.Long2IntMap.Entry from) {
						return Submap.this.tailMap(from.getLongKey()).long2IntEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public LongSortedSet keySet() {
			if (this.keys == null) {
				this.keys = new Long2IntAVLTreeMap.Submap.KeySet();
			}

			return this.keys;
		}

		@Override
		public IntCollection values() {
			if (this.values == null) {
				this.values = new AbstractIntCollection() {
					@Override
					public IntIterator iterator() {
						return Submap.this.new SubmapValueIterator();
					}

					@Override
					public boolean contains(int k) {
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
		public boolean containsKey(long k) {
			return this.in(k) && Long2IntAVLTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(int v) {
			Long2IntAVLTreeMap.Submap.SubmapIterator i = new Long2IntAVLTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				int ev = i.nextEntry().value;
				if (ev == v) {
					return true;
				}
			}

			return false;
		}

		@Override
		public int get(long k) {
			Long2IntAVLTreeMap.Entry e;
			return this.in(k) && (e = Long2IntAVLTreeMap.this.findKey(k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public int put(long k, int v) {
			Long2IntAVLTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				int oldValue = Long2IntAVLTreeMap.this.put(k, v);
				return Long2IntAVLTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public int remove(long k) {
			Long2IntAVLTreeMap.this.modified = false;
			if (!this.in(k)) {
				return this.defRetValue;
			} else {
				int oldValue = Long2IntAVLTreeMap.this.remove(k);
				return Long2IntAVLTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Long2IntAVLTreeMap.Submap.SubmapIterator i = new Long2IntAVLTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Long2IntAVLTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public LongComparator comparator() {
			return Long2IntAVLTreeMap.this.actualComparator;
		}

		@Override
		public Long2IntSortedMap headMap(long to) {
			if (this.top) {
				return Long2IntAVLTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Long2IntAVLTreeMap.this.compare(to, this.to) < 0 ? Long2IntAVLTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Long2IntSortedMap tailMap(long from) {
			if (this.bottom) {
				return Long2IntAVLTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Long2IntAVLTreeMap.this.compare(from, this.from) > 0 ? Long2IntAVLTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Long2IntSortedMap subMap(long from, long to) {
			if (this.top && this.bottom) {
				return Long2IntAVLTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Long2IntAVLTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Long2IntAVLTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Long2IntAVLTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Long2IntAVLTreeMap.Entry firstEntry() {
			if (Long2IntAVLTreeMap.this.tree == null) {
				return null;
			} else {
				Long2IntAVLTreeMap.Entry e;
				if (this.bottom) {
					e = Long2IntAVLTreeMap.this.firstEntry;
				} else {
					e = Long2IntAVLTreeMap.this.locateKey(this.from);
					if (Long2IntAVLTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Long2IntAVLTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Long2IntAVLTreeMap.Entry lastEntry() {
			if (Long2IntAVLTreeMap.this.tree == null) {
				return null;
			} else {
				Long2IntAVLTreeMap.Entry e;
				if (this.top) {
					e = Long2IntAVLTreeMap.this.lastEntry;
				} else {
					e = Long2IntAVLTreeMap.this.locateKey(this.to);
					if (Long2IntAVLTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Long2IntAVLTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		@Override
		public long firstLongKey() {
			Long2IntAVLTreeMap.Entry e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		@Override
		public long lastLongKey() {
			Long2IntAVLTreeMap.Entry e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends it.unimi.dsi.fastutil.longs.AbstractLong2IntSortedMap.KeySet {
			private KeySet() {
				super(Submap.this);
			}

			@Override
			public LongBidirectionalIterator iterator() {
				return Submap.this.new SubmapKeyIterator();
			}

			@Override
			public LongBidirectionalIterator iterator(long from) {
				return Submap.this.new SubmapKeyIterator(from);
			}
		}

		private class SubmapEntryIterator
			extends Long2IntAVLTreeMap.Submap.SubmapIterator
			implements ObjectListIterator<it.unimi.dsi.fastutil.longs.Long2IntMap.Entry> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(long k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.longs.Long2IntMap.Entry next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.longs.Long2IntMap.Entry previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Long2IntAVLTreeMap.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(long k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Long2IntAVLTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Long2IntAVLTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Long2IntAVLTreeMap.this.locateKey(k);
						if (Long2IntAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
				if (!Submap.this.bottom && this.prev != null && Long2IntAVLTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Long2IntAVLTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Long2IntAVLTreeMap.Submap.SubmapIterator implements LongListIterator {
			public SubmapKeyIterator() {
			}

			public SubmapKeyIterator(long from) {
				super(from);
			}

			@Override
			public long nextLong() {
				return this.nextEntry().key;
			}

			@Override
			public long previousLong() {
				return this.previousEntry().key;
			}
		}

		private final class SubmapValueIterator extends Long2IntAVLTreeMap.Submap.SubmapIterator implements IntListIterator {
			private SubmapValueIterator() {
			}

			@Override
			public int nextInt() {
				return this.nextEntry().value;
			}

			@Override
			public int previousInt() {
				return this.previousEntry().value;
			}
		}
	}

	private class TreeIterator {
		Long2IntAVLTreeMap.Entry prev;
		Long2IntAVLTreeMap.Entry next;
		Long2IntAVLTreeMap.Entry curr;
		int index = 0;

		TreeIterator() {
			this.next = Long2IntAVLTreeMap.this.firstEntry;
		}

		TreeIterator(long k) {
			if ((this.next = Long2IntAVLTreeMap.this.locateKey(k)) != null) {
				if (Long2IntAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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

		Long2IntAVLTreeMap.Entry nextEntry() {
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

		Long2IntAVLTreeMap.Entry previousEntry() {
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
				Long2IntAVLTreeMap.this.remove(this.curr.key);
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

	private final class ValueIterator extends Long2IntAVLTreeMap.TreeIterator implements IntListIterator {
		private ValueIterator() {
		}

		@Override
		public int nextInt() {
			return this.nextEntry().value;
		}

		@Override
		public int previousInt() {
			return this.previousEntry().value;
		}
	}
}
