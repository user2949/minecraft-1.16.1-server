package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

public class Byte2ShortRBTreeMap extends AbstractByte2ShortSortedMap implements Serializable, Cloneable {
	protected transient Byte2ShortRBTreeMap.Entry tree;
	protected int count;
	protected transient Byte2ShortRBTreeMap.Entry firstEntry;
	protected transient Byte2ShortRBTreeMap.Entry lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> entries;
	protected transient ByteSortedSet keys;
	protected transient ShortCollection values;
	protected transient boolean modified;
	protected Comparator<? super Byte> storedComparator;
	protected transient ByteComparator actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;
	private transient Byte2ShortRBTreeMap.Entry[] nodePath;

	public Byte2ShortRBTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = ByteComparators.asByteComparator(this.storedComparator);
	}

	public Byte2ShortRBTreeMap(Comparator<? super Byte> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Byte2ShortRBTreeMap(Map<? extends Byte, ? extends Short> m) {
		this();
		this.putAll(m);
	}

	public Byte2ShortRBTreeMap(SortedMap<Byte, Short> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Byte2ShortRBTreeMap(Byte2ShortMap m) {
		this();
		this.putAll(m);
	}

	public Byte2ShortRBTreeMap(Byte2ShortSortedMap m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Byte2ShortRBTreeMap(byte[] k, short[] v, Comparator<? super Byte> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Byte2ShortRBTreeMap(byte[] k, short[] v) {
		this(k, v, null);
	}

	final int compare(byte k1, byte k2) {
		return this.actualComparator == null ? Byte.compare(k1, k2) : this.actualComparator.compare(k1, k2);
	}

	final Byte2ShortRBTreeMap.Entry findKey(byte k) {
		Byte2ShortRBTreeMap.Entry e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Byte2ShortRBTreeMap.Entry locateKey(byte k) {
		Byte2ShortRBTreeMap.Entry e = this.tree;
		Byte2ShortRBTreeMap.Entry last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[64];
		this.nodePath = new Byte2ShortRBTreeMap.Entry[64];
	}

	public short addTo(byte k, short incr) {
		Byte2ShortRBTreeMap.Entry e = this.add(k);
		short oldValue = e.value;
		e.value += incr;
		return oldValue;
	}

	@Override
	public short put(byte k, short v) {
		Byte2ShortRBTreeMap.Entry e = this.add(k);
		short oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Byte2ShortRBTreeMap.Entry add(byte k) {
		this.modified = false;
		int maxDepth = 0;
		Byte2ShortRBTreeMap.Entry e;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Byte2ShortRBTreeMap.Entry(k, this.defRetValue);
		} else {
			Byte2ShortRBTreeMap.Entry p = this.tree;
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
					e = new Byte2ShortRBTreeMap.Entry(k, this.defRetValue);
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
					e = new Byte2ShortRBTreeMap.Entry(k, this.defRetValue);
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
						Byte2ShortRBTreeMap.Entry y = this.nodePath[i - 1].right;
						if (this.nodePath[i - 1].succ() || y.black()) {
							if (!this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Byte2ShortRBTreeMap.Entry x = this.nodePath[i];
								y = x.right;
								x.right = y.left;
								y.left = x;
								this.nodePath[i - 1].left = y;
								if (y.pred()) {
									y.pred(false);
									x.succ(y);
								}
							}

							Byte2ShortRBTreeMap.Entry xx = this.nodePath[i - 1];
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
						Byte2ShortRBTreeMap.Entry y = this.nodePath[i - 1].left;
						if (this.nodePath[i - 1].pred() || y.black()) {
							if (this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								Byte2ShortRBTreeMap.Entry x = this.nodePath[i];
								y = x.left;
								x.left = y.right;
								y.right = x;
								this.nodePath[i - 1].right = y;
								if (y.succ()) {
									y.succ(false);
									x.pred(y);
								}
							}

							Byte2ShortRBTreeMap.Entry xxx = this.nodePath[i - 1];
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
	public short remove(byte k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Byte2ShortRBTreeMap.Entry p = this.tree;
			int i = 0;
			byte kk = k;

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
				Byte2ShortRBTreeMap.Entry r = p.right;
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
						Byte2ShortRBTreeMap.Entry s = r.left;
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
						Byte2ShortRBTreeMap.Entry x = this.dirPath[i - 1] ? this.nodePath[i - 1].right : this.nodePath[i - 1].left;
						if (!x.black()) {
							x.black(true);
							break;
						}
					}

					if (!this.dirPath[i - 1]) {
						Byte2ShortRBTreeMap.Entry w = this.nodePath[i - 1].right;
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
								Byte2ShortRBTreeMap.Entry y = w.left;
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
						Byte2ShortRBTreeMap.Entry wx = this.nodePath[i - 1].left;
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
								Byte2ShortRBTreeMap.Entry y = wx.right;
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
	public boolean containsValue(short v) {
		Byte2ShortRBTreeMap.ValueIterator i = new Byte2ShortRBTreeMap.ValueIterator();
		int j = this.count;

		while (j-- != 0) {
			short ev = i.nextShort();
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
	public boolean containsKey(byte k) {
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
	public short get(byte k) {
		Byte2ShortRBTreeMap.Entry e = this.findKey(k);
		return e == null ? this.defRetValue : e.value;
	}

	@Override
	public byte firstByteKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	@Override
	public byte lastByteKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> byte2ShortEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry>() {
				final Comparator<? super it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> comparator = (x, y) -> Byte2ShortRBTreeMap.this.actualComparator
						.compare(x.getByteKey(), y.getByteKey());

				public Comparator<? super it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> iterator() {
					return Byte2ShortRBTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> iterator(it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry from) {
					return Byte2ShortRBTreeMap.this.new EntryIterator(from.getByteKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Short) {
							Byte2ShortRBTreeMap.Entry f = Byte2ShortRBTreeMap.this.findKey((Byte)e.getKey());
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
						if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Short) {
							Byte2ShortRBTreeMap.Entry f = Byte2ShortRBTreeMap.this.findKey((Byte)e.getKey());
							if (f != null && f.getShortValue() == (Short)e.getValue()) {
								Byte2ShortRBTreeMap.this.remove(f.key);
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
					return Byte2ShortRBTreeMap.this.count;
				}

				public void clear() {
					Byte2ShortRBTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry first() {
					return Byte2ShortRBTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry last() {
					return Byte2ShortRBTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> subSet(
					it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry from, it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry to
				) {
					return Byte2ShortRBTreeMap.this.subMap(from.getByteKey(), to.getByteKey()).byte2ShortEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> headSet(it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry to) {
					return Byte2ShortRBTreeMap.this.headMap(to.getByteKey()).byte2ShortEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> tailSet(it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry from) {
					return Byte2ShortRBTreeMap.this.tailMap(from.getByteKey()).byte2ShortEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public ByteSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Byte2ShortRBTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ShortCollection values() {
		if (this.values == null) {
			this.values = new AbstractShortCollection() {
				@Override
				public ShortIterator iterator() {
					return Byte2ShortRBTreeMap.this.new ValueIterator();
				}

				@Override
				public boolean contains(short k) {
					return Byte2ShortRBTreeMap.this.containsValue(k);
				}

				public int size() {
					return Byte2ShortRBTreeMap.this.count;
				}

				public void clear() {
					Byte2ShortRBTreeMap.this.clear();
				}
			};
		}

		return this.values;
	}

	@Override
	public ByteComparator comparator() {
		return this.actualComparator;
	}

	@Override
	public Byte2ShortSortedMap headMap(byte to) {
		return new Byte2ShortRBTreeMap.Submap((byte)0, true, to, false);
	}

	@Override
	public Byte2ShortSortedMap tailMap(byte from) {
		return new Byte2ShortRBTreeMap.Submap(from, false, (byte)0, true);
	}

	@Override
	public Byte2ShortSortedMap subMap(byte from, byte to) {
		return new Byte2ShortRBTreeMap.Submap(from, false, to, false);
	}

	public Byte2ShortRBTreeMap clone() {
		Byte2ShortRBTreeMap c;
		try {
			c = (Byte2ShortRBTreeMap)super.clone();
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
			Byte2ShortRBTreeMap.Entry rp = new Byte2ShortRBTreeMap.Entry();
			Byte2ShortRBTreeMap.Entry rq = new Byte2ShortRBTreeMap.Entry();
			Byte2ShortRBTreeMap.Entry p = rp;
			rp.left(this.tree);
			Byte2ShortRBTreeMap.Entry q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Byte2ShortRBTreeMap.Entry e = p.left.clone();
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
					Byte2ShortRBTreeMap.Entry e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Byte2ShortRBTreeMap.EntryIterator i = new Byte2ShortRBTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Byte2ShortRBTreeMap.Entry e = i.nextEntry();
			s.writeByte(e.key);
			s.writeShort(e.value);
		}
	}

	private Byte2ShortRBTreeMap.Entry readTree(ObjectInputStream s, int n, Byte2ShortRBTreeMap.Entry pred, Byte2ShortRBTreeMap.Entry succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Byte2ShortRBTreeMap.Entry top = new Byte2ShortRBTreeMap.Entry(s.readByte(), s.readShort());
			top.pred(pred);
			top.succ(succ);
			top.black(true);
			return top;
		} else if (n == 2) {
			Byte2ShortRBTreeMap.Entry top = new Byte2ShortRBTreeMap.Entry(s.readByte(), s.readShort());
			top.black(true);
			top.right(new Byte2ShortRBTreeMap.Entry(s.readByte(), s.readShort()));
			top.right.pred(top);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Byte2ShortRBTreeMap.Entry top = new Byte2ShortRBTreeMap.Entry();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = s.readByte();
			top.value = s.readShort();
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
			Byte2ShortRBTreeMap.Entry e = this.tree;

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
		Byte2ShortRBTreeMap.Entry left;
		Byte2ShortRBTreeMap.Entry right;
		int info;

		Entry() {
			super((byte)0, (short)0);
		}

		Entry(byte k, short v) {
			super(k, v);
			this.info = -1073741824;
		}

		Byte2ShortRBTreeMap.Entry left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Byte2ShortRBTreeMap.Entry right() {
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

		void pred(Byte2ShortRBTreeMap.Entry pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Byte2ShortRBTreeMap.Entry succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Byte2ShortRBTreeMap.Entry left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Byte2ShortRBTreeMap.Entry right) {
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

		Byte2ShortRBTreeMap.Entry next() {
			Byte2ShortRBTreeMap.Entry next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Byte2ShortRBTreeMap.Entry prev() {
			Byte2ShortRBTreeMap.Entry prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		@Override
		public short setValue(short value) {
			short oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Byte2ShortRBTreeMap.Entry clone() {
			Byte2ShortRBTreeMap.Entry c;
			try {
				c = (Byte2ShortRBTreeMap.Entry)super.clone();
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
				java.util.Map.Entry<Byte, Short> e = (java.util.Map.Entry<Byte, Short>)o;
				return this.key == (Byte)e.getKey() && this.value == (Short)e.getValue();
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

	private class EntryIterator extends Byte2ShortRBTreeMap.TreeIterator implements ObjectListIterator<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> {
		EntryIterator() {
		}

		EntryIterator(byte k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry previous() {
			return this.previousEntry();
		}
	}

	private final class KeyIterator extends Byte2ShortRBTreeMap.TreeIterator implements ByteListIterator {
		public KeyIterator() {
		}

		public KeyIterator(byte k) {
			super(k);
		}

		@Override
		public byte nextByte() {
			return this.nextEntry().key;
		}

		@Override
		public byte previousByte() {
			return this.previousEntry().key;
		}
	}

	private class KeySet extends it.unimi.dsi.fastutil.bytes.AbstractByte2ShortSortedMap.KeySet {
		private KeySet() {
			super(Byte2ShortRBTreeMap.this);
		}

		@Override
		public ByteBidirectionalIterator iterator() {
			return Byte2ShortRBTreeMap.this.new KeyIterator();
		}

		@Override
		public ByteBidirectionalIterator iterator(byte from) {
			return Byte2ShortRBTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractByte2ShortSortedMap implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		byte from;
		byte to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> entries;
		protected transient ByteSortedSet keys;
		protected transient ShortCollection values;

		public Submap(byte from, boolean bottom, byte to, boolean top) {
			if (!bottom && !top && Byte2ShortRBTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Byte2ShortRBTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Byte2ShortRBTreeMap.Submap.SubmapIterator i = new Byte2ShortRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(byte k) {
			return (this.bottom || Byte2ShortRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Byte2ShortRBTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> byte2ShortEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> iterator(it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry from) {
						return Submap.this.new SubmapEntryIterator(from.getByteKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> comparator() {
						return Byte2ShortRBTreeMap.this.byte2ShortEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Short) {
								Byte2ShortRBTreeMap.Entry f = Byte2ShortRBTreeMap.this.findKey((Byte)e.getKey());
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
							if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Short) {
								Byte2ShortRBTreeMap.Entry f = Byte2ShortRBTreeMap.this.findKey((Byte)e.getKey());
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

					public it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> subSet(
						it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry from, it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry to
					) {
						return Submap.this.subMap(from.getByteKey(), to.getByteKey()).byte2ShortEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> headSet(it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry to) {
						return Submap.this.headMap(to.getByteKey()).byte2ShortEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> tailSet(it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry from) {
						return Submap.this.tailMap(from.getByteKey()).byte2ShortEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public ByteSortedSet keySet() {
			if (this.keys == null) {
				this.keys = new Byte2ShortRBTreeMap.Submap.KeySet();
			}

			return this.keys;
		}

		@Override
		public ShortCollection values() {
			if (this.values == null) {
				this.values = new AbstractShortCollection() {
					@Override
					public ShortIterator iterator() {
						return Submap.this.new SubmapValueIterator();
					}

					@Override
					public boolean contains(short k) {
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
		public boolean containsKey(byte k) {
			return this.in(k) && Byte2ShortRBTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(short v) {
			Byte2ShortRBTreeMap.Submap.SubmapIterator i = new Byte2ShortRBTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				short ev = i.nextEntry().value;
				if (ev == v) {
					return true;
				}
			}

			return false;
		}

		@Override
		public short get(byte k) {
			Byte2ShortRBTreeMap.Entry e;
			return this.in(k) && (e = Byte2ShortRBTreeMap.this.findKey(k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public short put(byte k, short v) {
			Byte2ShortRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				short oldValue = Byte2ShortRBTreeMap.this.put(k, v);
				return Byte2ShortRBTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public short remove(byte k) {
			Byte2ShortRBTreeMap.this.modified = false;
			if (!this.in(k)) {
				return this.defRetValue;
			} else {
				short oldValue = Byte2ShortRBTreeMap.this.remove(k);
				return Byte2ShortRBTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Byte2ShortRBTreeMap.Submap.SubmapIterator i = new Byte2ShortRBTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Byte2ShortRBTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public ByteComparator comparator() {
			return Byte2ShortRBTreeMap.this.actualComparator;
		}

		@Override
		public Byte2ShortSortedMap headMap(byte to) {
			if (this.top) {
				return Byte2ShortRBTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Byte2ShortRBTreeMap.this.compare(to, this.to) < 0 ? Byte2ShortRBTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Byte2ShortSortedMap tailMap(byte from) {
			if (this.bottom) {
				return Byte2ShortRBTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Byte2ShortRBTreeMap.this.compare(from, this.from) > 0 ? Byte2ShortRBTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Byte2ShortSortedMap subMap(byte from, byte to) {
			if (this.top && this.bottom) {
				return Byte2ShortRBTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Byte2ShortRBTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Byte2ShortRBTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Byte2ShortRBTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Byte2ShortRBTreeMap.Entry firstEntry() {
			if (Byte2ShortRBTreeMap.this.tree == null) {
				return null;
			} else {
				Byte2ShortRBTreeMap.Entry e;
				if (this.bottom) {
					e = Byte2ShortRBTreeMap.this.firstEntry;
				} else {
					e = Byte2ShortRBTreeMap.this.locateKey(this.from);
					if (Byte2ShortRBTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Byte2ShortRBTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Byte2ShortRBTreeMap.Entry lastEntry() {
			if (Byte2ShortRBTreeMap.this.tree == null) {
				return null;
			} else {
				Byte2ShortRBTreeMap.Entry e;
				if (this.top) {
					e = Byte2ShortRBTreeMap.this.lastEntry;
				} else {
					e = Byte2ShortRBTreeMap.this.locateKey(this.to);
					if (Byte2ShortRBTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Byte2ShortRBTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		@Override
		public byte firstByteKey() {
			Byte2ShortRBTreeMap.Entry e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		@Override
		public byte lastByteKey() {
			Byte2ShortRBTreeMap.Entry e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends it.unimi.dsi.fastutil.bytes.AbstractByte2ShortSortedMap.KeySet {
			private KeySet() {
				super(Submap.this);
			}

			@Override
			public ByteBidirectionalIterator iterator() {
				return Submap.this.new SubmapKeyIterator();
			}

			@Override
			public ByteBidirectionalIterator iterator(byte from) {
				return Submap.this.new SubmapKeyIterator(from);
			}
		}

		private class SubmapEntryIterator
			extends Byte2ShortRBTreeMap.Submap.SubmapIterator
			implements ObjectListIterator<it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(byte k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Byte2ShortRBTreeMap.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(byte k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Byte2ShortRBTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Byte2ShortRBTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Byte2ShortRBTreeMap.this.locateKey(k);
						if (Byte2ShortRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
				if (!Submap.this.bottom && this.prev != null && Byte2ShortRBTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Byte2ShortRBTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Byte2ShortRBTreeMap.Submap.SubmapIterator implements ByteListIterator {
			public SubmapKeyIterator() {
			}

			public SubmapKeyIterator(byte from) {
				super(from);
			}

			@Override
			public byte nextByte() {
				return this.nextEntry().key;
			}

			@Override
			public byte previousByte() {
				return this.previousEntry().key;
			}
		}

		private final class SubmapValueIterator extends Byte2ShortRBTreeMap.Submap.SubmapIterator implements ShortListIterator {
			private SubmapValueIterator() {
			}

			@Override
			public short nextShort() {
				return this.nextEntry().value;
			}

			@Override
			public short previousShort() {
				return this.previousEntry().value;
			}
		}
	}

	private class TreeIterator {
		Byte2ShortRBTreeMap.Entry prev;
		Byte2ShortRBTreeMap.Entry next;
		Byte2ShortRBTreeMap.Entry curr;
		int index = 0;

		TreeIterator() {
			this.next = Byte2ShortRBTreeMap.this.firstEntry;
		}

		TreeIterator(byte k) {
			if ((this.next = Byte2ShortRBTreeMap.this.locateKey(k)) != null) {
				if (Byte2ShortRBTreeMap.this.compare(this.next.key, k) <= 0) {
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

		Byte2ShortRBTreeMap.Entry nextEntry() {
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

		Byte2ShortRBTreeMap.Entry previousEntry() {
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
				Byte2ShortRBTreeMap.this.remove(this.curr.key);
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

	private final class ValueIterator extends Byte2ShortRBTreeMap.TreeIterator implements ShortListIterator {
		private ValueIterator() {
		}

		@Override
		public short nextShort() {
			return this.nextEntry().value;
		}

		@Override
		public short previousShort() {
			return this.previousEntry().value;
		}
	}
}
